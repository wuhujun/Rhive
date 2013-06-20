package client;

// cc GetExample Example application retrieving data from HBase
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import util.HBaseHelper;

import java.io.IOException;
/**
 * 
 * @author ia
 *
 */

public class CRUDExample {

  public static void main(String[] args) throws IOException {
    // vv GetExample
    Configuration conf = HBaseConfiguration.create(); // co GetExample-1-CreateConf Create the configuration.

    // ^^ GetExample
    HBaseHelper helper = HBaseHelper.getHelper(conf);
    if (!helper.existsTable("testtable")) {
      helper.createTable("testtable", "colfam1");
    }
    // vv GetExample
    //创建一个表的引用和实例。
    HTable table = new HTable(conf, "testtable"); // co GetExample-2-NewTable Instantiate a new table reference.
    //插入数据操作的引用和实例。
    Put put1 = new Put(Bytes.toBytes("row1"));
    put1.add(Bytes.toBytes("colfam1"), Bytes.toBytes("qual1"), Bytes.toBytes("val_123_1090000"));
    // 插入数据。
    table.put(put1) ;
    Put put2 = new Put(Bytes.toBytes("row2"));
    put2.add(Bytes.toBytes("colfam1"), Bytes.toBytes("qual1"), Bytes.toBytes("val_123_1090000"));
    table.put(put2) ;
    //取数据的引用和实例。
    Get get = new Get(Bytes.toBytes("row2")); // co GetExample-3-NewGet Create get with specific row.
    
    get.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("qual1")); // co GetExample-4-AddCol Add a column to the get.
    
    Result result = table.get(get); // co GetExample-5-DoGet Retrieve row with selected columns from HBase.

    byte[] val = result.getValue(Bytes.toBytes("colfam1"),
      Bytes.toBytes("qual1")); // co GetExample-6-GetValue Get a specific value for the given column.

    System.out.println("Value: " + Bytes.toString(val)); // co GetExample-7-Print Print out the value while converting it back.
    // ^^ GetExample
  }
}
