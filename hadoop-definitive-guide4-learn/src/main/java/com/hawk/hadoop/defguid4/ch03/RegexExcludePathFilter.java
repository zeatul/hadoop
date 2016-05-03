package com.hawk.hadoop.defguid4.ch03;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;

/**
 * Example 3-7. A PathFilter for excluding paths that match a regular expression
 * 
 * @author pzhang1
 * 
 */
public class RegexExcludePathFilter implements PathFilter{
	private final String regex;

	public RegexExcludePathFilter(String regex) {
		this.regex = regex;
	}

	@Override
	public boolean accept(Path path) {
		return !path.toString().matches(regex);
	}
}
