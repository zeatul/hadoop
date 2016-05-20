package com.hawk.hadoop.defguid4.ch08;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;

/**
 * ensure that an existing file is not split
 * @author pzhang1
 *
 */
public class NonSplittableTextInputFormat extends TextInputFormat {
	@Override
	protected boolean isSplitable(JobContext context, Path file) {
		return false;
	}
}
