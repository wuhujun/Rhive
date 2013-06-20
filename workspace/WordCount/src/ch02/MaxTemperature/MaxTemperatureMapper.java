package ch02.MaxTemperature;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;


/**
 * 
 * @author ia
 *  每个MapReduce程序必须有实现Mapper接口中的map方法。
 */
public class MaxTemperatureMapper extends MapReduceBase
implements Mapper<LongWritable, Text, Text, IntWritable>
{
	private static final int MISSING = 9999;

	public void map(LongWritable key, Text value, 
		            OutputCollector<Text, IntWritable> output,
		            Reporter reporter)
		            		throws IOException {
	/**
	 * map方法中传入四个参数，分别指定map函数的输入键，输入值，输出键和输出值的类型，
	 * 
	 */
	String line = value.toString();

	String year = line.substring(15, 19);

	int airTemperature;

	if (line.charAt(87) == '+') {
		
		// parseInt doesn't like leading plus signs
	
		  airTemperature = Integer.parseInt(line.substring(88, 92));

	} else {
		
		airTemperature = Integer.parseInt(line.substring(87, 92));

	}
	
	String quality = line.substring(92, 93);

	if (airTemperature != MISSING && quality.matches("[01459]")) {
            //Map过程的输出值。
	     	output.collect(new Text(year), new IntWritable(airTemperature));
     }
}
}