package ms.luna.biz.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;


public final class FastJsonUtil {
	
	public static JSONObject checkParamsAnd(String json, String[] params) {
		
		JSONObject jsonObject = null;
		
		try {
			jsonObject = JSON.parseObject(json);
		} catch (Exception e) {
			return error("-1", "json 数据格式错误:[" + json + "]", e);
		}
		if (params == null) {
			return null;
		}
		for (int i = 0; i < params.length; i++) {
			if (!jsonObject.containsKey(params[i])) {
				JSONObject result = JSON.parseObject("{}");
				result.put("code", "-1");
				result.put("msg", "缺少参数:"+params[i]);
				result.put("data", "{}");
				MsLogger.error("缺少参数:"+params[i], 1);
				return result;
			}
			String value = jsonObject.getString(params[i]);
			if (value == null || value.length() == 0) {
				JSONObject result = JSON.parseObject("{}");
				result.put("code", "-2");
				result.put("msg", "参数值为空:"+params[i]);
				result.put("data", "{}");
				MsLogger.error("参数值为空:"+params[i], 1);
				return result;
			}
		}
		return null;
	}
	public static String getString(JSONObject json, String key) {
		if (json.containsKey(key)) {
			return json.getString(key);
		}
		return "";
	}
	
	/**
	 * 返回json对象的字符串，可指定默认值
	 * @param json
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getString(JSONObject json, String key, String defaultValue) {
		String result = defaultValue;
		
		if (json.containsKey(key)) {
			result = json.getString(key);
		}
		
		return result;
	}
	
	/**
	 * 获取json对象的整型数值
	 * @param json
	 * @param key
	 * @return
	 */
	public static Integer getInteger(JSONObject json, String key) {
		Integer result = null;
		if (json.containsKey(key)) {
			try {
				result = Integer.parseInt(json.getString(key));
			} catch(Exception e) {
			}
		}
		
		return result;
	}
	
	/**
	 * 获取json对象的整型数值
	 * @param json
	 * @param key
	 * @return
	 */
	public static Integer getInteger(JSONObject json, String key, int defaultValue) {
		Integer result = defaultValue;
		if (json.containsKey(key)) {
			try {
				result = Integer.parseInt(json.getString(key));
			} catch(Exception e) {
			}
		}
		
		return result;
	}
	
	/**
	 * 获取json对象的浮点型数值
	 * @param json
	 * @param key
	 * @return
	 */
	public static Float getFloat(JSONObject json, String key, float defaultValue) {
		Float result = defaultValue;
		if (json.containsKey(key)) {
			try {
				result = Float.parseFloat(json.getString(key));
			} catch(Exception e) {
			}
		}
		
		return result;
	}
	
	/**
	 * 获取json对象的short型数值
	 * @param json
	 * @param key
	 * @return
	 */
	public static Short getShort(JSONObject json, String key, short defaultValue) {
		Short result = defaultValue;
		if (json.containsKey(key)) {
			try {
				result = Short.parseShort(json.getString(key));
			} catch(Exception e) {
			}
		}
		
		return result;
	}
	
	/**
	 * 设置值
	 * @param json
	 * @param key
	 * @param value
	 */
	public static void setValue(JSONObject json, String key, Integer value) {
		json.put(key, String.valueOf(value));
	}
	

	public static void setValue(JSONObject json, String key, String value) {
		
		json.put(key, value);
	}
	
	public static void setValue(JSONObject json, String key, short value) {
		
		json.put(key, String.valueOf(value));
	}
	
	public static void setValue(JSONObject json, String key, float value) {
		
		json.put(key, String.valueOf(value));
	}
	
	public static JSONObject error(String code, String msg) {
		MsLogger.error(msg, 1);
		JSONObject result = JSON.parseObject("{}");
		result.put("code", code);
		result.put("msg", msg);
		result.put("data", "{}");
		return result;
	}

	public static JSONObject error(String code, Throwable th) {
		MsLogger.error(th.getMessage(), 1, th);
		JSONObject result = JSON.parseObject("{}");
		result.put("code", code);
		result.put("msg", th.getMessage());
		result.put("data", "{}");
		return result;
	}

	public static JSONObject error(String code, String msg, Throwable th) {
		MsLogger.error(msg, 1, th);
		JSONObject result = JSON.parseObject("{}");
		result.put("code", code);
		result.put("msg", msg);
		result.put("data", "{}");
		return result;
	}
	
	public static JSONObject error(int code, String msg) {
		MsLogger.error(msg, 2);
		JSONObject result = JSON.parseObject("{}");
		result.put("code", String.valueOf(code));
		result.put("msg", msg);
		result.put("data", "{}");
		return result;
	}
	public static JSONObject error(int code, String msg, Throwable th) {
		MsLogger.error(msg, 2, th);
		JSONObject result = JSON.parseObject("{}");
		result.put("code", String.valueOf(code));
		result.put("msg", msg);
		result.put("data", "{}");
		return result;
	}
	
	public static JSONObject sucess(String msg, Object data) {
		MsLogger.info(msg, 1);
		JSONObject result = JSON.parseObject("{}");
		result.put("code", "0");
		result.put("msg", msg);
		result.put("data", data);
		return result;
	}
	public static JSONObject sucess(String msg) {
		MsLogger.info(msg, 1);
		JSONObject result = JSON.parseObject("{}");
		result.put("code", "0");
		result.put("msg", msg);
		result.put("data", "{}");
		return result;
	}
	public static JSONObject sucess(String msg, String data) {
		MsLogger.info(msg, 1);
		JSONObject result = JSON.parseObject("{}");
		result.put("code", "0");
		result.put("msg", msg);
		result.put("data", data);
		return result;
	}
}
