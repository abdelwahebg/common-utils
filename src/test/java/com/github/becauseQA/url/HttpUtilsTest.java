/**
 * Project Name:commons
 * File Name:HttpUtilsTest.java
 * Package Name:com.github.becauseQA.url
 * Date:2016年4月16日下午7:15:27
 * Copyright (c) 2016, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.github.becauseQA.url;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.github.becauseQA.http.HttpUtils;

/**
 * ClassName:HttpUtilsTest  
 * Function: TODO ADD FUNCTION.  
 * Reason:	 TODO ADD REASON.  
 * Date:     2016年4月16日 下午7:15:27 
 * @author   Administrator
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class HttpUtilsTest {

	@Before
	public void setUp() throws Exception {
		new HttpUtils();
	}

	@Test
	public void testGetResponse() {
		try {
			HttpUtils.getRequestAsString(new URL("https://www.baidu.com"), null);
		
		Map<String,String> data=new HashMap<String,String>();
		
		data.put("search", "est");
		//httpUtils.postRequest(url, headers, data)(new URL("https://www.baidu.com"), data);
		
		} catch (IOException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}

}

