package com.hawk.hadoop.defguid4.ch06;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.junit.Test;

public class SingleResourceConfigurationTest {
	@Test
	public void get() throws IOException {
		Configuration conf = new Configuration();
		conf.addResource("com/hawk/hadoop/defguid4/ch06/configuration-1.xml");
		assertThat(conf.get("color"), is("yellow"));
		assertThat(conf.getInt("size", 0), is(10));
		assertThat(conf.get("breadth", "wide"), is("wide"));
	}
}
