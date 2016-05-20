package com.hawk.hadoop.defguid4.ch09;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.hawk.hadoop.defguid4.ch05.TextPair;

/**
 * Example 9-11. Reducer for joining tagged station records with tagged weather records
 * @author pzhang1
 *
 */
public class JoinReducer extends Reducer<TextPair,Text,Text,Text> {

	@Override
	protected void reduce(TextPair key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		Iterator<Text> iter = values.iterator();
		Text stationName = new Text(iter.next());
		while(iter.hasNext()){
			Text record = iter.next();
			Text outValue = new Text(stationName.toString() + "\t" + record.toString());
			context.write(key.getFirst(), outValue);
		}
	}
}
