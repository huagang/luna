package ms.luna.web.common;


import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;

import ms.luna.biz.util.MsLogger;


/**
 * 
 * Copyright (C) 2015 - 2016 Microscene Inc., All Rights Reserved.
 *
 * @author: shawn@visualbusiness.com
 * @Date: Apr 1, 2016
 *
 */

public class AreaOptionQueryBuilder {
	
	public final static String DEFAULT_VALUE = "ALL";
	
	private AreaOptionQueryBuilder() {
		
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder {
		
		private Map<String, Object> params;
		
		public Builder() {
			params = new HashMap<String, Object>();
		}
		
		private boolean isValid(String value) {
			if(StringUtils.isNotEmpty(value) && (!value.equalsIgnoreCase(DEFAULT_VALUE))) {
				return true;
			}
			return false;
		}
		
		public void newStringParam(String name, String value) {
			if(isValid(value)) {
				params.put(name, value);
			}
		}
		
		public void newIntParam(String name, int value) {
			params.put(name, value);
		}
		
		public JSONObject buildJsonQuery() {
			JSONObject jsonObject = (JSONObject) JSONObject.toJSON(params);
			return jsonObject;
		}
		
	}
	
	public static void main(String[] args) {
		
		Builder builder = AreaOptionQueryBuilder.builder();
		builder.newStringParam("province_id", "111");
		builder.newIntParam("city_id", 10);
		JSONObject jsonObject = builder.buildJsonQuery();
		jsonObject.put("countyId", 11);
		MsLogger.debug(jsonObject.toString());
		
		
	}
}
