package com.hawk.hadoop.defguid4.ch09;

import java.io.File;
import java.io.IOException;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.hawk.hadoop.defguid4.ch06.v1.MaxTemperatureReducer;
import com.hawk.hadoop.defguid4.common.JobBuilder;
import com.hawk.hadoop.defguid4.common.NcdcRecordParser;
import com.hawk.hadoop.defguid4.common.NcdcStationMetadata;

/**
 * Example 9-13. Application to find the maximum temperature by station, showing
 * station names from a lookup table passed as a distributed cache file
 * 
 * @author pzhang1
 * 
 */
public class MaxTemperatureByStationNameUsingDistributedCacheFile extends Configured implements Tool {

	static class StationTemperatureMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
		private NcdcRecordParser parser = new NcdcRecordParser();

		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			parser.parse(value);
			if (parser.isValidTemperature()) {
				context.write(new Text(parser.getStationId()), new IntWritable(parser.getAirTemperature()));
			}
		}
	}

	static class MaxTemperatureReducerWithStationLookup extends Reducer<Text, IntWritable, Text, IntWritable> {
		private NcdcStationMetadata metadata;

		@Override
		protected void setup(Context context) throws IOException, InterruptedException {
			metadata = new NcdcStationMetadata();
			/**
			 * hadoop jar hadoop-examples.jar
			 * MaxTemperatureByStationNameUsingDistributedCacheFile -files
			 * input/ncdc/metadata/stations-fixed-width.txt input/ncdc/all
			 * output
			 * 
			 * 
			 * stations-fixed-width.txt is a distributed cache file
			 */
			metadata.initialize(new File("stations-fixed-width.txt"));
		}

		@Override
		protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
			String stationName = metadata.getStationName(key.toString());

			int maxValue = Integer.MIN_VALUE;
			for (IntWritable value : values) {
				maxValue = Math.max(maxValue, value.get());
			}

			context.write(new Text(stationName), new IntWritable(maxValue));
		}
	}

	@Override
	public int run(String[] args) throws Exception {
		Job job = JobBuilder.parseInputAndOutput(this, getConf(), args);
		if (job == null) {
			return -1;
		}

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		job.setMapperClass(StationTemperatureMapper.class);
		job.setCombinerClass(MaxTemperatureReducer.class);
		job.setReducerClass(MaxTemperatureReducerWithStationLookup.class);

		return job.waitForCompletion(true) ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new MaxTemperatureByStationNameUsingDistributedCacheFile(), args);
		System.exit(exitCode);
	}

}
