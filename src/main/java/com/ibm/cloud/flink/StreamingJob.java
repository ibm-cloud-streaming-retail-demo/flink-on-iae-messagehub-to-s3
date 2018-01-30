package com.ibm.cloud.flink;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileConstants;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.connectors.fs.AvroKeyValueSinkWriter;
import org.apache.flink.streaming.connectors.fs.bucketing.BucketingSink;
import org.apache.flink.streaming.connectors.fs.bucketing.DateTimeBucketer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.streaming.util.serialization.JSONKeyValueDeserializationSchema;
import org.apache.log4j.Logger;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.*;


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
     * The name of the parameter of the output file path.
     */
    private static final String PARAM_OUTPUT_BUCKET_FORMAT_STRING = "output-bucket-format-string";

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

        DataStream<Tuple2<String, Object>> avroStream = objStream
                .map(new MapFunction<ObjectNode, Tuple2<String, Object>>() {

                    @Override
                    public Tuple2<String, Object> map(ObjectNode value) throws Exception {

                        JsonNode result = value.get("value");

                        Transaction tx = convertToAvro(result);

                        return new Tuple2(value.get("key").toString(), tx);
                    }
                });

        avroStream.addSink(getSink(pt.get(PARAM_OUTPUT_FOLDER), pt.get(PARAM_OUTPUT_BUCKET_FORMAT_STRING)));

        // execute program
        env.execute("Streaming Analytics");
    }

    public static Transaction convertToAvro(JsonNode result) {
        return Transaction.newBuilder()
                                    .setInvoiceNo(result.get("InvoiceNo").intValue())
                                    .setStockCode(result.get("StockCode").intValue())
                                    .setDescription(result.get("Description").toString())
                                    .setQuantity(result.get("Quantity").intValue())
                                    .setInvoiceDate(result.get("InvoiceDate").longValue())
                                    .setUnitPrice(result.get("UnitPrice").floatValue())
                                    .setCustomerID(result.get("CustomerID").intValue())
                                    .setCountry(result.get("Country").toString())
                                    .setLineNo(result.get("LineNo").intValue())
                                    .setInvoiceTime(result.get("InvoiceTime").toString())
                                    .setStoreID(result.get("StoreID").intValue())
                                    .setTransactionID(result.get("TransactionID").toString())
                                    .build();
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
        pt.getRequired(PARAM_OUTPUT_BUCKET_FORMAT_STRING);

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
    private static SinkFunction<Tuple2<String, Object>> getSink(final String path, final String formatString) {

        AvroKeyValueSinkWriter<String, Object> writer = getWriter();

        return new BucketingSink<Tuple2<String, Object>>(path)
                 .setWriter(writer)
                 .setBucketer(new DateTimeBucketer<Tuple2<String, Object>>(formatString));
    }

    public static Schema makeSchema() {

        // TODO read from src/main/avro/transaction.avsc

        String valSchemaJson = "\n" +
                "        {\"namespace\": \"transaction.avsc\",\n" +
                "         \"type\": \"record\",\n" +
                "         \"name\": \"Transaction\",\n" +
                "         \"fields\": [\n" +
                "             {\"name\": \"InvoiceNo\",     \"type\": \"int\"    },\n" +
                "             {\"name\": \"StockCode\",     \"type\": \"int\" },\n" +
                "             {\"name\": \"Description\",   \"type\": \"string\" },\n" +
                "             {\"name\": \"Quantity\",      \"type\": \"int\"    },\n" +
                "             {\"name\": \"InvoiceDate\",   \"type\": \"long\"   },\n" +
                "             {\"name\": \"UnitPrice\",     \"type\": \"float\"  },\n" +
                "             {\"name\": \"CustomerID\",    \"type\": \"int\"    },\n" +
                "             {\"name\": \"Country\",       \"type\": \"string\" },\n" +
                "             {\"name\": \"LineNo\",        \"type\": \"int\"    },\n" +
                "             {\"name\": \"InvoiceTime\",   \"type\": \"string\" },\n" +
                "             {\"name\": \"StoreID\",       \"type\": \"int\"    },\n" +
                "             {\"name\": \"TransactionID\", \"type\": \"string\" }\n" +
                "         ]\n" +
                "        }";

        Schema.Parser parser = new Schema.Parser();
        return parser.parse(valSchemaJson);
    }

    /**
     *
     * @return a <code>AvroKeyValueSinkWriter</code>
     */
    protected static AvroKeyValueSinkWriter<String, Object> getWriter() {

        Schema keySchema = Schema.create(Schema.Type.STRING);
        Schema valSchema = makeSchema();

        Map<String, String> properties = new HashMap<>();
        properties.put(AvroKeyValueSinkWriter.CONF_OUTPUT_KEY_SCHEMA, keySchema.toString());
        properties.put(AvroKeyValueSinkWriter.CONF_OUTPUT_VALUE_SCHEMA, valSchema.toString());
        properties.put(AvroKeyValueSinkWriter.CONF_COMPRESS, String.valueOf(true));
        properties.put(AvroKeyValueSinkWriter.CONF_COMPRESS_CODEC, DataFileConstants.SNAPPY_CODEC);

        return new AvroKeyValueSinkWriter<String, Object>(properties);
    }

}
