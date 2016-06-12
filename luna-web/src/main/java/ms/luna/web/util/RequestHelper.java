package ms.luna.web.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
/**
 * 
 * Copyright (C) 2015 - 2016 Microscene Inc., All Rights Reserved.
 *
 * @author: shawn@visualbusiness.com
 * @Date: Apr 5, 2016
 *
 */
public class RequestHelper {
	
	public static String getString(HttpServletRequest request, String name) {
		return request.getParameter(name);
	}
	
	public static int getInt(HttpServletRequest request, String name) {
		String value = request.getParameter(name);
		if(StringUtils.isNumeric(value)) {
			return Integer.parseInt(value);
		}
		
		return -1;
	}
	
	public static long getLong(HttpServletRequest request, String name) {
		String value = request.getParameter(name);
		if(StringUtils.isNumeric(value)) {
			return Long.parseLong(value);
		}
		
		return -1;
	}

}
