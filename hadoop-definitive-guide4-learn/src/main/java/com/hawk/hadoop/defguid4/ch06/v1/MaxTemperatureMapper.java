package com.hawk.hadoop.defguid4.ch06.v1;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * Example 6-6. First version of a Mapper that passes MaxTemperatureMapperTest
 * @author pzhang1
 *
 */
public class MaxTemperatureMapper extends Mapper<LongWritable,Text,Text,IntWritable>{

	@Override
	public void map(LongWritable key, Text value, Context context)throws IOException, InterruptedException{
		String line = value.toString();
		String year = line.substring(15, 19);
		int airTemperature = Integer.parseInt(line.substring(87,92));
		context.write(new Text(year), new IntWritable(airTemperature));
	}
}
