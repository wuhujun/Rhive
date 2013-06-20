package client;

// cc CheckAndPutExample Example application using the atomic compare-and-set operations
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import util.HBaseHelper;

import java.io.IOException;
/**
 * 根据行键和列名检查指定的数据是否存在，如果不存在，插入数据。
 * @author ia
 *
 */
public class CheckAndPutExample {

  public static void main(String[] args) throws IOException {
    Configuration conf = HBaseConfiguration.create();

    HBaseHelper helper = HBaseHelper.getHelper(conf);
    helper.dropTable("testtable");
    helper.createTable("testtable", "colfam1");
    
    HTable table = new HTable(conf, "testtable");

    // vv CheckAndPutExample
    Put put1 = new Put(Bytes.toBytes("row1"));
    //添加数据,由于HBase中没有指定的记录，插入数据成功。
    put1.add(Bytes.toBytes("colfam1"), Bytes.toBytes("qual1"),Bytes.toBytes("val1")); 
    
    boolean res1 = table.checkAndPut(Bytes.toBytes("row1"), Bytes.toBytes("colfam1"), Bytes.toBytes("qual1"),
    null, put1); // co CheckAndPutExample-2-CAS1 Check if column does not exist and perform optional put operation.
    System.out.println("Put applied: " + res1); // co CheckAndPutExample-3-SOUT1 Print out the result, should be "Put applied: true".
    //HBase数据库中插入数据，由于数据表中已经有该记录，插入失败。
    
    boolean res2 = table.checkAndPut(Bytes.toBytes("row1"),
    Bytes.toBytes("colfam1"), Bytes.toBytes("qual1"), null, put1); // co CheckAndPutExample-4-CAS2 Attempt to store same cell again.
    System.out.println("Put applied: " + res2); // co CheckAndPutExample-5-SOUT2 Print out the result, should be "Put applied: false" as the column now already exists.
    
    //HBase中没有指定的{row1 , colfam:qual2}对应的数据，操作成功。
    Put put2 = new Put(Bytes.toBytes("row1"));
    put2.add(Bytes.toBytes("colfam1"), Bytes.toBytes("qual2"),
      Bytes.toBytes("val2")); // co CheckAndPutExample-6-Put2 Create another Put instance, but using a different column qualifier.
    
    boolean res3 = table.checkAndPut(Bytes.toBytes("row1"),
      Bytes.toBytes("colfam1"), Bytes.toBytes("qual1"), // co CheckAndPutExample-7-CAS3 Store new data only if the previous data has been saved.
      Bytes.toBytes("val1"), put2);
    System.out.println("Put applied: " + res3); // co CheckAndPutExample-8-SOUT3 Print out the result, should be "Put applied: true" as the checked column already exists.
    
    Put put3 = new Put(Bytes.toBytes("row1"));
    put3.add(Bytes.toBytes("colfam1"), Bytes.toBytes("qual1"),
    Bytes.toBytes("val3")); // co CheckAndPutExample-9-Put3 Create yet another Put instance, but using a different row.
    //判断{row2 , colfam1:qual1}不存在 ，
    //但是插入的是{row1,colfam1:qual1},后者已经存在，所以在运行过程中出现异常。
    boolean res4 = table.checkAndPut(Bytes.toBytes("row2"),
      Bytes.toBytes("colfam1"), Bytes.toBytes("qual1"), // co CheckAndPutExample-a-CAS4 Store new data while checking a different row.
      Bytes.toBytes("val3"), put3);
    System.out.println("Put applied: " + res4); // co CheckAndPutExample-b-SOUT4 We will not get here as an exception is thrown beforehand!
    // ^^ CheckAndPutExample
    
  }
}
