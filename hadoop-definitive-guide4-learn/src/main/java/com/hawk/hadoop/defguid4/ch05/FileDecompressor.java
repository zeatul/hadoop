package com.hawk.hadoop.defguid4.ch05;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;

/**
 * Example 5-2. A program to decompress a compressed file using a codec inferred from the file’s extension
 * @author pzhang1
 *
 */
public class FileDecompressor {
	
	public static void main(String[] args) throws Exception{
		
		System.err.println("..........start..................");
		System.err.println("..........start2..................");
		
		String uri = args[0];
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(uri),conf);
		
		Path inputPath = new Path(uri);
		
		System.err.println("uri="+uri);
		
		CompressionCodecFactory factory = new CompressionCodecFactory(conf);
		CompressionCodec codec  = factory.getCodec(inputPath);
		if (codec == null){
			System.err.println("No codec found for " + uri);
			System.exit(1);
		}
		
		String outputUri = CompressionCodecFactory.removeSuffix(uri, codec.getDefaultExtension());
		
		System.err.println("outputUri="+outputUri);
		
		InputStream in = null;
		OutputStream out = null;
		
		try{
			in = codec.createInputStream(fs.open(inputPath));
			out = fs.create(new Path(outputUri));
			IOUtils.copyBytes(in, out, conf);
		}finally{
			IOUtils.closeStream(in);
			IOUtils.closeStream(out);
		}
	}
}
