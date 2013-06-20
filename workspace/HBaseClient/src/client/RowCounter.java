package client;

import java.io.IOException;

import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.client.Result;

import org.apache.hadoop.hbase.KeyValue;

public class RowCounter {
   public static final String NAME = "rowCounter";
   
   public static class RowCounterMapper extends TableMapper<ImmutableBytesWritable, Result>{
	   
	   public static enum Coounters  {ROWS};
	   
	   public void map(ImmutableBytesWritable row , Result values ,Context context)
	    throws IOException{
		   for(KeyValue value  : values.list()){
			  value.getBuffer();
		   }
	   }
   }
}
