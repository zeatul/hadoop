package com.hawk.hadoop.defguid4.ch05;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.junit.Test;

public class TextTest extends WritableTestBase {

	@Test
	public void test() throws IOException {
		Text t = new Text("hadoop");
		assertThat(t.getLength(), is(6));
		assertThat(t.getBytes().length, is(6));
		assertThat(t.charAt(2), is((int) 'd'));
		assertThat("Out of bounds", t.charAt(100), is(-1));
	}

	@Test
	public void find() throws IOException {
		Text t = new Text("hadoop");
		assertThat("Find a substring", t.find("do"), is(2));
		assertThat("Finds first 'o'", t.find("o"), is(3));
		assertThat("Finds 'o' from position 4 or later", t.find("o", 4), is(4));
		assertThat("No match", t.find("pig"), is(-1));
	}

	@Test
	public void mutability() throws IOException {
		Text t = new Text("hadoop");
		t.set("pig");
		assertThat(t.getLength(), is(3));
		assertThat(t.getBytes().length, is(3));
	}

	@Test
	public void byteArrayNotShortened() throws IOException {
		Text t = new Text("hadoop");
		/*
		 * In some situations, the byte array returned by the getBytes() method may be longer than the length returned bygetLength()
		 */
		t.set(new Text("pig"));
		assertThat(t.getLength(), is(3));
		assertThat("Byte length not shortened", t.getBytes().length, is(6));
	}
}
