package com.hawk.hadoop.defguid4.ch09;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.hawk.hadoop.defguid4.ch05.TextPair;
import com.hawk.hadoop.defguid4.common.NcdcRecordParser;

/**
 * Example 9-10. Mapper for tagging weather records for a reduce-side join
 * @author pzhang1
 *
 */
public class JoinRecordMapper extends Mapper<LongWritable,Text,TextPair,Text> {
	private NcdcRecordParser parser = new NcdcRecordParser();
	
	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		parser.parse(value);
		context.write(new TextPair(parser.getStationId(),"1"), value);
	}
}
