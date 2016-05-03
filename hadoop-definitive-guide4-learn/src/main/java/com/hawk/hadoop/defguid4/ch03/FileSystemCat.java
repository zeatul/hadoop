package com.hawk.hadoop.defguid4.ch03;

import java.io.InputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

/**
 * Example 3-2. Displaying files from a Hadoop filesystem on standard output by using the FileSystem directly
 */
public class FileSystemCat {
	
	public static void main(String[] args) throws Exception{
		String uri = args[0];
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(uri),conf);
		InputStream in = null;
		
		try{
			in = fs.open(new Path(uri));
			IOUtils.copyBytes(in, System.out, 4096,false);
		}finally{
			IOUtils.closeStream(in);
		}
	}

}
