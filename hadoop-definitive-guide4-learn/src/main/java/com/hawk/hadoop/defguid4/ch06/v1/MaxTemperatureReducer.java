package com.hawk.hadoop.defguid4.ch06.v1;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * Example 6-9. Reducer for the maximum temperature example
 * @author pzhang1
 *
 */
public class MaxTemperatureReducer extends Reducer<Text,IntWritable,Text,IntWritable>{
	
	@Override
	protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
		int maxValue = Integer.MIN_VALUE;
		for (IntWritable value : values){
			maxValue = Math.max(maxValue, value.get());
		}
		context.write(key, new IntWritable(maxValue));
	}
}
