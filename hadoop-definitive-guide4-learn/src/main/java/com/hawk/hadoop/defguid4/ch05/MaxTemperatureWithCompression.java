package com.hawk.hadoop.defguid4.ch05;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.hawk.hadoop.defguid4.ch02.MaxTemperatureMapper;
import com.hawk.hadoop.defguid4.ch02.MaxTemperatureReducer;

/**
 * Example 5-4. Application to run the maximum temperature job producing
 * compressed output
 * 
 * @author pzhang1
 * 
 */
public class MaxTemperatureWithCompression {
	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.err.println("Usage: MaxTemperatureWithCompression <input path> " + "<output path>");
			System.exit(-1);
		}

		/**
		 * enable gzip map output compression in your job
		 */
		Configuration conf = new Configuration();
//		conf.setBoolean(Job.MAP_OUTPUT_COMPRESS, true);
//		conf.setClass(Job.MAP_OUTPUT_COMPRESS_CODEC, GzipCodec.class, CompressionCodec.class);
//		System.out.println("enable gzip map output compression in your job");
		
		Job job = Job.getInstance(conf);
		
	

		job.setJarByClass(MaxTemperatureWithCompression.class);

		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		FileOutputFormat.setCompressOutput(job, true);
		FileOutputFormat.setOutputCompressorClass(job, GzipCodec.class);

		job.setMapperClass(MaxTemperatureMapper.class);
		job.setCombinerClass(MaxTemperatureReducer.class);
		job.setReducerClass(MaxTemperatureReducer.class);

		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
