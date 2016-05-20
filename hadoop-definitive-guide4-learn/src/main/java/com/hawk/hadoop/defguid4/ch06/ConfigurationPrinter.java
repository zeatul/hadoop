package com.hawk.hadoop.defguid4.ch06;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.util.Map.Entry;

/**
 * Example 6-4. An example Tool implementation for printing the properties in a
 * Configuration
 * 
 * @author pzhang1
 * 
 */
public class ConfigurationPrinter extends Configured implements Tool {

	static {
		Configuration.addDefaultResource("hdfs-default.xml");
		Configuration.addDefaultResource("hdfs-site.xml");
		Configuration.addDefaultResource("yarn-default.xml");
		Configuration.addDefaultResource("yarn-site.xml");
		Configuration.addDefaultResource("mapred-default.xml");
		Configuration.addDefaultResource("mapred-site.xml");
		
		
		Configuration.addDefaultResource("hadoop-localhost.xm");
	}

	@Override
	public int run(String[] arg0) throws Exception {
		Configuration conf = getConf();
		for (Entry<String,String> entry : conf){
			System.out.printf("%s=%s\n", entry.getKey(), entry.getValue());
		}
		return 0;
	}
	
	public static void main(String[] args) throws Exception{
		int exitCode = ToolRunner.run(new ConfigurationPrinter(), args);
		System.exit(exitCode);
	}

}