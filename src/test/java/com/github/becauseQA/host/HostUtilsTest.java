package com.github.becauseQA.host;

import org.junit.Test;

public class HostUtilsTest {

	@Test
	public void test() {
		String username=HostUtils.getCurrentUserName();
		System.out.println(username);
	}

}