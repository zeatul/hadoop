package com.hawk.hadoop.defguid4.ch09;

import java.io.IOException;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;



import com.hawk.hadoop.defguid4.ch06.v1.MaxTemperatureReducer;
import com.hawk.hadoop.defguid4.common.JobBuilder;
import com.hawk.hadoop.defguid4.common.NcdcRecordParser;

/**
 * Example 9-1. Application to run the maximum temperature job, including counting 
 * missing and malformed fields and quality codes
 * @author pzhang1
 *
 */
public class MaxTemperatureWithCounters extends Configured implements Tool{

	enum Temperature{
		MISSING,MALFORMED
	}
	
	static class MaxTemperatureMapperWithCounters extends Mapper<LongWritable,Text,Text,IntWritable>{
		private NcdcRecordParser parser = new NcdcRecordParser();
		
		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			parser.parse(value);
			if (parser.isMalformedTemperature()){
				int airTemperature = parser.getAirTemperature();
				context.write(new Text(parser.getYear()), new IntWritable(airTemperature));
			}else if(parser.isMalformedTemperature()){
				System.err.println("Ignoring possibly corrupt input: " + value);
				context.getCounter(Temperature.MALFORMED).increment(1);
			}else if(parser.isMissingTemperature()){
				context.getCounter(Temperature.MISSING).increment(1);
			}
			
			//dynamic counter
			context.getCounter("TemperatureQuality",parser.getQuality()).increment(1);
		}
	}
	
	@Override
	public int run(String[] args) throws Exception {
		Job job = JobBuilder.parseInputAndOutput(this, getConf(), args);
		if (job == null){
			return -1;
		}
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		job.setMapperClass(MaxTemperatureMapperWithCounters.class);
		job.setCombinerClass(MaxTemperatureReducer.class);
		job.setReducerClass(MaxTemperatureReducer.class);
		
		return job.waitForCompletion(true)?0:1;
	}

	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new MaxTemperatureWithCounters(), args);
		System.exit(exitCode);
	}
}
