package ch02.MaxTemperature;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobConf;


public class MaxTemperature {

	public static void main(String[] args) throws IOException {
	
		if (args.length != 2) {
		
			System.err.println("Usage: MaxTemperature <input path> <output path>");
		
			System.exit(-1);
	}
	    //构建一个配置对象。
		JobConf conf = new JobConf(MaxTemperature.class);
	    //设置任务名称。
		conf.setJobName("Max temperature");
        //设置数据输入目录。
		FileInputFormat.addInputPath(conf, new Path(args[0]));
	    //设置数据的输出目录。
		FileOutputFormat.setOutputPath(conf, new Path(args[1]));
	    //Map任务的类。
		conf.setMapperClass(MaxTemperatureMapper.class);
	    //Reduce任务的类。
		conf.setReducerClass(MaxTemperatureReducer.class);
	    //输出键的类型
		conf.setOutputKeyClass(Text.class);
	    //输出值的类型。
		conf.setOutputValueClass(IntWritable.class);
  }
}