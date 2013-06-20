package client;

// cc DeleteExample Example application deleting data from HBase
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.util.Bytes;
import util.HBaseHelper;

import java.io.IOException;
/**
 * 删除数据
 * @author ia
 *
 */
public class DeleteExample {

  public static void main(String[] args) throws IOException {
	  
    Configuration conf = HBaseConfiguration.create();
    HBaseHelper helper = HBaseHelper.getHelper(conf);
    //删除数据。
    helper.dropTable("testtable");
    helper.createTable("testtable", "colfam1", "colfam2");
    HTable table = new HTable(conf, "testtable");
    
    helper.put("testtable",
      new String[] { "row1" },
      new String[] { "colfam1", "colfam2" },
      new String[] { "qual1", "qual1", "qual2", "qual2", "qual3", "qual3" },
      new long[]   { 1, 2, 3, 4, 5, 6 },
      //设置记录的timestamp。
      new String[] { "val1", "val2", "val3", "val4", "val5", "val6" });
    System.out.println("Before delete call...");
    //批量查入数据。
    helper.dump("testtable", new String[]{ "row1" }, null, null);
    // vv DeleteExample
    //创建删除操作的引用和实例。
    Delete delete = new Delete(Bytes.toBytes("row1")); // co DeleteExample-1-NewDel Create delete with specific row.
    //设置删除操作的时间戳。
    delete.setTimestamp(1); // co DeleteExample-2-SetTS Set timestamp for row deletes.
    //删除{row1,colfam1,qual1,1}数据。
    delete.deleteColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("qual1")); // co DeleteExample-3-DelColNoTS Delete the latest version only in one column.
    //删除{row1,colfam1,qual3,5}数据。
    delete.deleteColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("qual3"), 5); // co DeleteExample-4-DelColTS Delete specific version in one column.
    //删除所有版本的{row1,colfam1,qual1}的数据。
    delete.deleteColumns(Bytes.toBytes("colfam1"), Bytes.toBytes("qual1")); // co DeleteExample-5-DelColsNoTS Delete all versions in one column.
    //删除{row1,colfam1, qual3 ,6}数据。
    delete.deleteColumns(Bytes.toBytes("colfam1"), Bytes.toBytes("qual3"), 6); // co DeleteExample-6-DelColsTS Delete the given and all older versions in one column.
    //删除{colfam1}所有列的数据。
    delete.deleteFamily(Bytes.toBytes("colfam1")); // co DeleteExample-7-AddCol Delete entire family, all columns and versions.
    delete.deleteFamily(Bytes.toBytes("colfam1"), 3); // co DeleteExample-8-AddCol Delete the given and all older versions in the entire column family, i.e., from all columns therein.

    table.delete(delete); // co DeleteExample-9-DoDel Delete the data from the HBase table.

    table.close();
    // ^^ DeleteExample
    System.out.println("After delete call...");
    helper.dump("testtable", new String[] { "row1" }, null, null);
  }
}
