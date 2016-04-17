/**
 * Project Name:commons
 * File Name:RegexpUtils.java
 * Package Name:com.github.becausetesting.regexp
 * Date:Apr 16, 201610:52:32 PM
 * Copyright (c) 2016, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.github.becausetesting.regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName:RegexpUtils  
 * Function: TODO ADD FUNCTION.  
 * Reason:	 TODO ADD REASON.  
 * Date:     Apr 16, 2016 10:52:32 PM 
 * @author   Administrator
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class RegexpUtils {

	private Pattern pattern;
	private Matcher matcher;
	
	public boolean validate(final String inputtester,String patterns) {
		pattern=Pattern.compile(patterns, Pattern.DOTALL);
		matcher = pattern.matcher(inputtester);
		return matcher.matches();

	}

	public String validateString(final String inputtester,String patterns) {
		pattern=Pattern.compile(patterns, Pattern.DOTALL);
		matcher = pattern.matcher(inputtester);
		String matchedstr = "";
		while (matcher.find()) {
			matchedstr = matchedstr + matcher.group();
			// System.out.println("Matched content is:"+matcher.group());
		}

		return matchedstr;
	}
}
