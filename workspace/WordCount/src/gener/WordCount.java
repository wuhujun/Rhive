package gener;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import gener.ChineseHandle;
/**
 * 新版本的API,1.0.4版本中的MapReduce接口。
 * 相对于之前的老版本，有些调整，但是整体思路没有变化。
 * 创建Map类，每个Map类必须继承于import org.apache.hadoop.mapreduce.Mapper
 * 并实现一个map方法。
 * 创建Reduce类，每个Reduce类必须继承于import org.apache.hadoop.mapreduce.Reducer
 * 并实现一个reduce方法。
 * @author ia
 *
 */
public class WordCount {

  public static class WordCountMapper 
       extends Mapper<Object, Text, Text, IntWritable>{
	private final static int FromStationIndex01 = 115 ;
    private final static int FromStationIndex02 = 123 ;
    private final static int ToStationIndex01 = 148 ;
    private final static int ToStationIndex02 =158 ;
	private  static String FromStation ; 
    
    private  static String ToStation ;
    
    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
      ChineseHandle handle1 = new ChineseHandle(
    		  value.toString().substring(FromStationIndex01, FromStationIndex02).trim());
      
      FromStation = handle1.ExtractChinese();
      
      ChineseHandle handle2 = new ChineseHandle(
    		  value.toString().substring(ToStationIndex01,ToStationIndex02).trim()) ;
      
      ToStation   =  handle2.ExtractChinese();
      
      if(FromStation.equals("上海")&&ToStation.equals("南京")){
    	  context.write(new Text(FromStation+"\t"+ToStation), new IntWritable(1)) ;
          System.out.print("The from station  is :"+ FromStation+"\t");
          System.out.println("The to station is : "+ ToStation);
      }
    }
  }
  
  public static class WordCountReducer 
       extends Reducer<Text,IntWritable,Text,IntWritable> {
    private IntWritable result = new IntWritable();

    public void reduce(Text key, Iterable<IntWritable> values, 
                       Context context
                       ) throws IOException, InterruptedException {
      int sum = 0;
      for (IntWritable val : values) {
        sum += val.get();
        System.out.println(val); 
      }
      result.set(sum);
      context.write(key, result);
    }
  }
  
  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
    if (otherArgs.length != 2) {
      System.err.println("Usage: wordcount <in> <out>");
      System.exit(2);
    }
    Job job = new Job(conf, "word count");
    job.setJarByClass(WordCount.class);
    job.setMapperClass(WordCountMapper.class);
    job.setCombinerClass(WordCountReducer.class);
    job.setReducerClass(WordCountReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
    FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
    FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
