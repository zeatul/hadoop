package com.hawk.hadoop.defguid4.ch03;

import java.io.InputStream;
import java.net.URL;

import org.apache.hadoop.fs.FsUrlStreamHandlerFactory;
import org.apache.hadoop.io.IOUtils;

/**
 * Example 3-1. Displaying files from a Hadoop filesystem on standard output using a URLStreamHandler
 * @author pzhang1
 *
 */
public class URLCat {
	
	static{
		URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory());
	}
	
	public static void main(String[] args) throws Exception{
		InputStream in = null;
		try{
			in = new URL(args[0]).openStream();
			IOUtils.copyBytes(in, System.out, 4096,false);
		}finally{
			IOUtils.closeStream(in);
		}
	}

}
