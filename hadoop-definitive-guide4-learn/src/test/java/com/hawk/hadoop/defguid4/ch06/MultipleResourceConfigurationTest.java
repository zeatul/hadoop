package com.hawk.hadoop.defguid4.ch06;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.junit.Test;

public class MultipleResourceConfigurationTest {

	@Test
	public void get() throws IOException {

		Configuration conf = new Configuration();
		conf.addResource("com/hawk/hadoop/defguid4/ch06/configuration-1.xml");
		conf.addResource("com/hawk/hadoop/defguid4/ch06/configuration-2.xml");

		assertThat(conf.get("color"), is("yellow"));
		assertThat(conf.getInt("size", 0), is(12));
		assertThat(conf.get("weight"), is("heavy"));
		assertThat(conf.get("size-weight"), is("12,heavy"));

		/**
		 * variable expansion with system properties
		 */
		System.setProperty("size", "14");
		assertThat(conf.get("size-weight"), is("14,heavy"));

		/**
		 * system properties are not picked up , "length" is not in the xml file.
		 */
		System.setProperty("length", "2");
		assertThat(conf.get("length"), is((String) null));

	}

}
