package util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Used by the book examples to generate tables and fill them with test data.
 * 构建一个类，将有关HBase的操作封装进去。
 */
public class HBaseHelper {
  /**
   * 每个HBase客户端操作都需要配置文件实例和HBase管理员实例。
   */
  private Configuration conf = null;
  private HBaseAdmin admin = null;
  /**
   * 构造函数，利用传入的系统配置对象创建一个实例。
   * @param conf
   * @throws IOException
   */
  protected HBaseHelper(Configuration conf) throws IOException {
     this.conf = conf;
     this.admin = new HBaseAdmin(conf);
  }
  /**
   * 利用传入的系统配置返回一个实例。
   * @param conf
   * @return
   * @throws IOException
   */
  public static HBaseHelper getHelper(Configuration conf) throws IOException {
     return new HBaseHelper(conf);
  }
  /**
   * 判断指定的表是否存在。
   * @param table
   * @return
   * @throws IOException
   */
  public boolean existsTable(String table)
  throws IOException {
    return admin.tableExists(table);
  }
   /**
    * 根据指定的表名和列簇创建一张表。
    * @param table
    * @param colfams
    * @throws IOException
    */
  public void createTable(String table, String... colfams)
  throws IOException {
    createTable(table, null, colfams);
  }
   /**
    * 根据指定的表名，分割符号，列簇创建一张表。
    * @param table
    * @param splitKeys
    * @param colfams
    * @throws IOException
    */
  public void createTable(String table, byte[][] splitKeys, String... colfams)
  throws IOException {
    HTableDescriptor desc = new HTableDescriptor(table);
    for (String cf : colfams) {
      HColumnDescriptor coldef = new HColumnDescriptor(cf);
      desc.addFamily(coldef);
    }
    if (splitKeys != null) {
      admin.createTable(desc, splitKeys);
    } else {
      admin.createTable(desc);
    }
  }
  /**
   * 丢弃一张表。
   * @param table
   * @throws IOException
   */
  
  public void disableTable(String table) throws IOException {
    admin.disableTable(table);
  }
  /**
   * 删除一张表。
   * @param table
   * @throws IOException
   */
  public void dropTable(String table) throws IOException {
	/**
	 *如果存在这张表，删除张表，否则不予处理。
	 */
    if (existsTable(table)) {
      disableTable(table);
      admin.deleteTable(table);
    }
  }
 /**
  * 将一张表填满值。
  * @param table
  * @param startRow
  * @param endRow
  * @param numCols
  * @param colfams
  * @throws IOException
  */
  public void fillTable(String table, int startRow, int endRow, int numCols,
                        String... colfams)
  throws IOException {
    fillTable(table, startRow, endRow, numCols, -1, false, colfams);
  }

  public void fillTable(String table, int startRow, int endRow, int numCols,
                        boolean setTimestamp, String... colfams)
  throws IOException {
    fillTable(table, startRow, endRow, numCols, -1, setTimestamp, colfams);
  }

  public void fillTable(String table, int startRow, int endRow, int numCols,
                        int pad, boolean setTimestamp, String... colfams)
  throws IOException {
    fillTable(table, startRow, endRow, numCols, pad, setTimestamp, false, colfams);
  }

  public void fillTable(String table, int startRow, int endRow, int numCols,
                        int pad, boolean setTimestamp, boolean random,
                        String... colfams)
  throws IOException {
    HTable tbl = new HTable(conf, table);
    Random rnd = new Random();
    for (int row = startRow; row <= endRow; row++) {
      for (int col = 0; col < numCols; col++) {
        Put put = new Put(Bytes.toBytes("row-" + padNum(row, pad)));
        for (String cf : colfams) {
          String colName = "col-" + padNum(col, pad);
          String val = "val-" + (random ?
            Integer.toString(rnd.nextInt(numCols)) :
            padNum(row, pad) + "." + padNum(col, pad));
          if (setTimestamp) {
            put.add(Bytes.toBytes(cf), Bytes.toBytes(colName),
              col, Bytes.toBytes(val));
          } else {
            put.add(Bytes.toBytes(cf), Bytes.toBytes(colName),
              Bytes.toBytes(val));
          }
        }
        tbl.put(put);
      }
    }
    tbl.close();
  }
  /**
   * 
   * @param num
   * @param pad
   * @return res
   */
  
  public String padNum(int num, int pad) {
    String res = Integer.toString(num);
    if (pad > 0) {
      while (res.length() < pad) {
        res = "0" + res;
      }
    }
    return res;
  }
  /**
   * 插入行数据
   * @param table 表名。
   * @param row  行名。
   * @param fam  列簇。
   * @param qual 列修饰名。
   * @param val  值。
   * @throws IOException
   */
  public void put(String table, String row, String fam, String qual,
                  String val) throws IOException {
    HTable tbl = new HTable(conf, table);
    Put put = new Put(Bytes.toBytes(row));
    put.add(Bytes.toBytes(fam), Bytes.toBytes(qual), Bytes.toBytes(val));
    tbl.put(put);
    tbl.close();
  }
  /**
   * 插入行数据
   * @param table
   * @param row
   * @param fam
   * @param qual
   * @param ts
   * @param val
   * @throws IOException
   */
  public void put(String table, String row, String fam, String qual, long ts,
                  String val) throws IOException {
    HTable tbl = new HTable(conf, table);
    Put put = new Put(Bytes.toBytes(row));
    put.add(Bytes.toBytes(fam), Bytes.toBytes(qual), ts,
            Bytes.toBytes(val));
    tbl.put(put);
    tbl.close();
  }
  //

  public void put(String table, String[] rows, String[] fams, String[] quals,
                  long[] ts, String[] vals) throws IOException {
    HTable tbl = new HTable(conf, table);
    for (String row : rows) {
      Put put = new Put(Bytes.toBytes(row));
      for (String fam : fams) {
        int v = 0;
        for (String qual : quals) {
          String val = vals[v < vals.length ? v : vals.length - 1];
          long t = ts[v < ts.length ? v : ts.length - 1];
          put.add(Bytes.toBytes(fam), Bytes.toBytes(qual), t,
            Bytes.toBytes(val));
          v++;
        }
      }
      tbl.put(put);
    }
    tbl.close();
  }
  /**
   * 批量输出数据。
   * @param table 输出的表名。
   * @param rows  输出的行名。
   * @param fams  输出的列簇。
   * @param quals 输出的列。
   * @throws IOException 
   * 
   */
  public void dump(String table, String[] rows, String[] fams, String[] quals)
  throws IOException {
	/**
	 * 
	 */
    HTable tbl = new HTable(conf, table);
    List<Get> gets = new ArrayList<Get>();
    for (String row : rows) {
      Get get = new Get(Bytes.toBytes(row));
      get.setMaxVersions();
      //默认输出所有的列簇和列。
      if (fams != null) {
        for (String fam : fams) {
          for (String qual : quals) {
            get.addColumn(Bytes.toBytes(fam), Bytes.toBytes(qual));
          }
        }
      }
      gets.add(get);
    }
    //批量输出获取的结果。
    Result[] results = tbl.get(gets);
    for (Result result : results) {
      for (KeyValue kv : result.raw()) {
        System.out.println("KV: " + kv + ", Value: " + Bytes.toString(kv.getValue()));
      }
    }
  }
}
