package com.ibm.cloud.flink;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileConstants;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.fs.AvroKeyValueSinkWriter;
import org.apache.flink.streaming.connectors.fs.bucketing.BucketingSink;
import org.apache.flink.streaming.connectors.fs.bucketing.DateTimeBucketer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.streaming.util.serialization.JSONKeyValueDeserializationSchema;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


//CHECKSTYLE.OFF: HideUtilityClassConstructor
public class StreamingJob {
//CHECKSTYLE.ON: HideUtilityClassConstructor

    /**
     * The name of the parameter for the kafka topic to consume.
     */
    private static final String PARAM_KAFKA_USERNAME = "kafka-username";

    /**
     * The name of the parameter for the kafka topic to consume.
     */
    private static final String PARAM_KAFKA_PASSWORD = "kafka-password";

    /**
     * The name of the parameter for the kafka topic to consume.
     */
    private static final String PARAM_KAFKA_TOPIC = "kafka-topic";

    /**
     * The name of the parameter for the kafka brokers.
     */
    private static final String PARAM_KAFKA_BROKERS = "kafka-brokers";

    /**
     * The name of the parameter for the kafka group id.
     */
    private static final String PARAM_KAFKA_GROUP_ID = "kafka-group-id";

    /**
     * The name of the parameter of the output file path.
     */
    private static final String PARAM_OUTPUT_FOLDER = "output-folder";

    /**
     * The time window slide amount.
     */
    private static final Time TIME_WINDOW_SLIDE = Time.minutes(1);

    /**
     * The size of the time window.
     */
    private static final Time TIME_WINDOW_SIZE = Time.minutes(10);

    /**
     * The <code>org.apache.log4j.logger</code>.
     */
    private static Logger log = Logger.getLogger(StreamingJob.class);

    /**
     * The main entry point for this class.
     *
     * @param args are documented in the Class documentation @see {@link StreamingJob}.
     * @throws Exception which occurs during job execution.
     */
    public static void main(final String[] args) throws Exception {

        final StreamExecutionEnvironment env =
                StreamExecutionEnvironment.getExecutionEnvironment();

        env.enableCheckpointing(2000, CheckpointingMode.EXACTLY_ONCE);

        // The source writes data with 'SourceContext.collectWithTimestamp'
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        final ParameterTool pt = ParameterTool.fromArgs(args);

        StreamingJob.validateParams(pt);

        final FlinkKafkaConsumer011 kafkaConsumer = getKafkaConsumer(pt);

        DataStream<ObjectNode> objStream = env.addSource(kafkaConsumer);

        //objStream.print(); // 1> {"key":5639281840180123,"value":{"InvoiceNo":5639281,"StockCode":"47593B","Description":"SCOTTIE DOGS BABY BIB","Quantity":4,"InvoiceDate":1516724220000,"UnitPrice":0.39,"CustomerID":17059,"Country":"United Kingdom","LineNo":84,"InvoiceTime":"16:17:00","StoreID":0,"TransactionID":"5639281840180123"}}

        DataStream<Tuple2<String, String>> avroStream = objStream
                .map(new MapFunction<ObjectNode, Tuple2<String, String>>() {
                    @Override
                    public Tuple2<String, String> map(ObjectNode value) throws Exception {
                        return new Tuple2(
                                value.get("key").toString(),
                                value.get("value").toString()
                        );
                    }
                });

        avroStream.addSink(getSink(pt.get(PARAM_OUTPUT_FOLDER), "yyyy-MM-dd--HHmm"));

        // execute program
        env.execute("Streaming Analytics");
    }

    private static FlinkKafkaConsumer011 getKafkaConsumer(final ParameterTool pt) {

        String jaas = String.format(
                "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"%s\" password=\"%s\";",
                pt.get(PARAM_KAFKA_USERNAME),
                pt.get(PARAM_KAFKA_PASSWORD));

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", pt.get(PARAM_KAFKA_BROKERS));
        properties.setProperty("group.id", pt.get(PARAM_KAFKA_GROUP_ID, "test"));
        properties.setProperty("sasl.jaas.config", jaas);

        // Constant parameters
        properties.setProperty("security.protocol", "SASL_SSL");
        properties.setProperty("sasl.mechanism", "PLAIN");
        properties.setProperty("ssl.protocol", "TLSv1.2");
        properties.setProperty("ssl.enabled.protocols", "TLSv1.2");
        properties.setProperty("ssl.endpoint.identification.algorithm", "HTTPS");

        FlinkKafkaConsumer011 kafkaConsumer = new FlinkKafkaConsumer011(
                pt.get(PARAM_KAFKA_TOPIC),
                new JSONKeyValueDeserializationSchema(false),
                properties
                );
        return kafkaConsumer;
    }

    /**
     * Utility method to validate the ParameterTool parameters contain the sink folder parameters.
     *
     * @param pt the parameterTool instance.
     */
    private static void validateParams(final ParameterTool pt) {

        // Use the parameter tool's 'Required' error reporting to notify if any of these params are missing

        pt.getRequired(PARAM_OUTPUT_FOLDER);

        pt.getRequired(PARAM_KAFKA_TOPIC);
        pt.getRequired(PARAM_KAFKA_BROKERS);
        pt.getRequired(PARAM_KAFKA_USERNAME);
        pt.getRequired(PARAM_KAFKA_PASSWORD);
    }

    /**
     * Utility method for creating a <code>BucketingSink</code>.
     *
     * @param path The file path where to save the files
     * @param formatString The formatString for the <code>DateTimeBucketer</code>
     * @return the <code>BucketingSink</code>
     */
    private static SinkFunction<Tuple2<String, String>> getSink(final String path, final String formatString) {

        Map<String, String> properties = new HashMap<>();
        Schema keySchema = Schema.create(Schema.Type.STRING);
        Schema valueSchema = Schema.create(Schema.Type.STRING);
        properties.put(AvroKeyValueSinkWriter.CONF_OUTPUT_KEY_SCHEMA, keySchema.toString());
        properties.put(AvroKeyValueSinkWriter.CONF_OUTPUT_VALUE_SCHEMA, valueSchema.toString());
        properties.put(AvroKeyValueSinkWriter.CONF_COMPRESS, String.valueOf(true));
        properties.put(AvroKeyValueSinkWriter.CONF_COMPRESS_CODEC, DataFileConstants.SNAPPY_CODEC);

        AvroKeyValueSinkWriter<String, String> writer = new AvroKeyValueSinkWriter(properties);

        return new BucketingSink<Tuple2<String, String>>(path)
                 .setWriter(writer)
                 .setBucketer(new DateTimeBucketer<Tuple2<String, String>>(formatString));
    }

}
