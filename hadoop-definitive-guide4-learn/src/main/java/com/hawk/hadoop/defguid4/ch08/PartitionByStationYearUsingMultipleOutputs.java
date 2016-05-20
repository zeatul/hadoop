package com.hawk.hadoop.defguid4.ch08;

import java.io.IOException;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.hawk.hadoop.defguid4.common.NcdcRecordParser;

/**
 * For example, the following modification partitions the data by station and year 
 * so that each year’s data is contained in 
 * a directory named by the station ID (such as 029070-99999/1901/part-r-00000):
 * 
 * @author pzhang1
 * 
 */
public class PartitionByStationYearUsingMultipleOutputs extends Configured implements Tool {

	static class StationMapper extends Mapper<LongWritable, Text, Text, Text> {
		private NcdcRecordParser parser = new NcdcRecordParser();

		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			parser.parse(value);
			if (parser.isValidTemperature()) {
				context.write(new Text(parser.getStationId()), value);
			}
		}
	}

	static class MultipleOutputsReducer extends Reducer<Text, Text, NullWritable, Text> {
		private MultipleOutputs<NullWritable, Text> multipleOutputs;
		private NcdcRecordParser parser = new NcdcRecordParser();

		@Override
		protected void setup(Context context) throws IOException, InterruptedException {
			multipleOutputs = new MultipleOutputs<NullWritable, Text>(context);
		}

		@Override
		protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			for (Text value : values) {
				parser.parse(value);
				String basePath = String.format("%s/%s/part", parser.getStationId(), parser.getYear());
				multipleOutputs.write(NullWritable.get(), value, basePath);
			}
		}

		@Override
		protected void cleanup(Context context) throws IOException, InterruptedException {
			multipleOutputs.close();
		}
	}

	@Override
	public int run(String[] args) throws Exception {
		if (args.length != 2) {
			System.err.printf("Usage: %s [generic options] <input> <output>\n", getClass().getSimpleName());
			ToolRunner.printGenericCommandUsage(System.err);
			return -1;
		}

		Job job = Job.getInstance(getConf());

		job.setMapperClass(StationMapper.class);
		job.setMapOutputKeyClass(Text.class);

		job.setReducerClass(MultipleOutputsReducer.class);
		job.setOutputKeyClass(NullWritable.class);

		return job.waitForCompletion(true) ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new PartitionByStationYearUsingMultipleOutputs(), args);
		System.exit(exitCode);
	}
}