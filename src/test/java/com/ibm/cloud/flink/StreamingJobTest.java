package com.ibm.cloud.flink;

import static com.ibm.cloud.flink.StreamingJob.*;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.connectors.fs.AvroKeyValueSinkWriter;
import java.io.File;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.*;
import org.junit.rules.TemporaryFolder;

/**
 *
 * @author snowch
 *
 */
public class StreamingJobTest {

    @ClassRule
    public static TemporaryFolder tempFolder = new TemporaryFolder();

    public static Configuration conf = null;

    public static File dataDir = null;

    public static FileSystem fs = null;

    public static Path path = null;

    public static String key = "5639281840180123";

    public static String value = "{\n" +
            "  \"InvoiceNo\": 5370812,\n" +
            "  \"StockCode\": 22409,\n" +
            "  \"Description\": \"MONEY BOX BISCUITS DESIGN\",\n" +
            "  \"Quantity\": 12,\n" +
            "  \"InvoiceDate\": 1517270400000,\n" +
            "  \"UnitPrice\": 1.25,\n" +
            "  \"CustomerID\": 15332,\n" +
            "  \"Country\": \"Lithuania\",\n" +
            "  \"LineNo\": 1,\n" +
            "  \"InvoiceTime\": \"00:00:00\",\n" +
            "  \"StoreID\": 0,\n" +
            "  \"TransactionID\": \"537081210180130\"\n" +
            "}";

    @Before
    public void setup() throws Exception {
        conf = new Configuration();
        dataDir = tempFolder.newFolder();
        fs = FileSystem.get(dataDir.toURI(), conf);
        path = new Path( dataDir.toURI() + "/test.avro");
        System.out.println(path);
    }

    @After
    public void teardown() throws Exception {
        dataDir.delete();
    }

    /**
     * For now, just test that we don't throw an exception
     *
     * @throws Exception ex
     */
    @Test(expected = Test.None.class) // Don't expect an exception
    public void testWriter() throws Exception {

        AvroKeyValueSinkWriter<String, Object> writer = getWriter();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode result = mapper.readTree(value);

        Transaction tx = StreamingJob.convertToAvro(result);

        writer.setSyncOnFlush(true);
        writer.open(fs, path);
        writer.write(new Tuple2<String, Object>(key, tx));
        writer.flush();
    }
}