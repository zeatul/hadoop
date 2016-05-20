package com.hawk.hadoop.defguid4.ch06.v1;

import java.io.IOException;
import java.util.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.*;

public class MaxTemperatureReducerTest {

	@Test
	public void returnsMaximumIntegerInValues() throws IOException, InterruptedException {
		new ReduceDriver<Text,IntWritable,Text,IntWritable>()//
			.withReducer(new MaxTemperatureReducer())//
			.withInput(new Text("1950"),Arrays.asList(new IntWritable(10),new IntWritable(5)))//
			.withOutput(new Text("1950"),new IntWritable(10))//
			.runTest();
	}

}
