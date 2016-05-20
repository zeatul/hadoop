package com.hawk.hadoop.defguid4.ch09;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.hawk.hadoop.defguid4.ch05.TextPair;
import com.hawk.hadoop.defguid4.common.NcdcStationMetadataParser;

/**
 * Example 9-9. Mapper for tagging station records for a reduce-side join
 * @author pzhang1
 *
 */
public class JoinStationMapper extends Mapper<LongWritable,Text,TextPair,Text>{
	private NcdcStationMetadataParser parser = new NcdcStationMetadataParser();
	
	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		if (parser.parse(value)){
			context.write(new TextPair(parser.getStationId(),"0"), new Text(parser.getStationName()));
		}
	}
}
