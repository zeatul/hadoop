package com.hawk.hadoop.defguid4.ch08;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

import com.hawk.hadoop.defguid4.common.NcdcRecordParser;

/**
 * No good
 * @author pzhang1
 *
 */
public class StationPartitioner extends Partitioner<LongWritable, Text> {

	private NcdcRecordParser parser = new NcdcRecordParser();

	@Override
	public int getPartition(LongWritable key, Text value, int numPartitions) {
		parser.parse(value);
		return getPartition(parser.getStationId());
	}

	private int getPartition(String stationId) {
		/* ... */
		// ^^ StationPartitioner
		return 0;
		// vv StationPartitioner
	}
}
