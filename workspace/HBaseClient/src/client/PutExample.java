package client;

// cc PutExample Example application inserting data into HBase
// vv PutExample
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
// ^^ PutExample
import util.HBaseHelper;
// vv PutExample
import java.io.IOException;

/**
 * 插入数据。
 * @author ia
 *
 */

public class PutExample {

  public static void main(String[] args) throws IOException {
    Configuration conf = HBaseConfiguration.create(); // co PutExample-1-CreateConf Create the required configuration.

    // ^^ PutExample
    HBaseHelper helper = HBaseHelper.getHelper(conf);
    helper.dropTable("testtable_01");
    helper.createTable("testtable_01", "colfam1");
    // vv PutExample
    HTable table = new HTable(conf, "testtable_01"); // co PutExample-2-NewTable Instantiate a new client.
    System.out.println("Auto flush : "+ table.isAutoFlush());
    System.out.println("修改表的自动刷新模式！");
    table.setAutoFlush(false);
    System.out.println("Auto flush : "+ table.isAutoFlush());
    /**
     * 插入一行数据，行名“row1”，列名“colfam1”  
     */
    Put put = new Put(Bytes.toBytes("row1")); // co PutExample-3-NewPut Create put with specific row.
    put.add(Bytes.toBytes("colfam1"), 
    		Bytes.toBytes("qual1"),
            Bytes.toBytes("val1")); // co PutExample-4-AddCol1 Add a column, whose name is "colfam1:qual1", to the put.
    put.add(Bytes.toBytes("colfam1"),
    		Bytes.toBytes("qual2"),
            Bytes.toBytes("val2")); // co PutExample-4-AddCol2 Add another column, whose name is "colfam1:qual2", to the put.
    put.add(Bytes.toBytes("colfam1"),
    		Bytes.toBytes("qual4"), 
    		Bytes.toBytes("value3"));
    /**
     * 插入一行数据
     */
    Put put1 = new Put(Bytes.toBytes("row2"));
    put1.add(Bytes.toBytes("colfam1"),
    	     Bytes.toBytes("qual1"),
    	     Bytes.toBytes("fuck!  fuck! fuck!"));
    table.put(put); // co PutExample-5-DoPut Store row with column into the HBase table.
    table.put(put1);
    table.flushCommits();
    
    Get get = new Get(Bytes.toBytes("row1"));
    Result result = table.get(get);
    System.out.println("Result : "+ result );
  }
}
// ^^ PutExample
