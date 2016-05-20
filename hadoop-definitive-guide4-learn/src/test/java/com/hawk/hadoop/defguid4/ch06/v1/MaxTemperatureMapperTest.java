package com.hawk.hadoop.defguid4.ch06.v1;

import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.*;

/**
 * Example 6-5. Unit test for MaxTemperatureMapper
 * 
 * @author pzhang1
 * 
 */
public class MaxTemperatureMapperTest {

	@Test
	public void processesValidRecord() throws IOException, InterruptedException {
		Text value = new Text("0043011990999991950051518004+68750+023550FM-12+0382"//
				+ "99999V0203201N00261220001CN9999999N9-00111+99999999999");
		new MapDriver<LongWritable, Text, Text, IntWritable>()//
				.withMapper(new MaxTemperatureMapper())//
				.withInput(new LongWritable(0), value)//
				.withOutput(new Text("1950"), new IntWritable(-11))//
				.runTest();
	}

	@Ignore
	@Test
	public void ignoresMissingTemperatureRecord() throws IOException, InterruptedException {
		Text value = new Text("0043011990999991950051518004+68750+023550FM-12+0382" + //
				"99999V0203201N00261220001CN9999999N9+99991+99999999999");
		
		new MapDriver<LongWritable, Text, Text, IntWritable>()//
		.withMapper(new MaxTemperatureMapper())//
		.withInput(new LongWritable(0), value)//
		.runTest();
	}

}
