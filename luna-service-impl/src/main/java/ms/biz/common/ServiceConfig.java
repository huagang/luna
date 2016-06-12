package ms.biz.common;

import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

public class ServiceConfig {

	public static String COS_BASE_DIR = "cosBaseDir";
	public static String MS_WEB_URL = "msWebUrl";
	
	private static Properties properties;
	
	public static void setConfig(Properties properties) {
		ServiceConfig.properties = properties;
	}
	
	public static String getString(String key) {
		return properties.getProperty(key, "/dev");
	}
	
	public static int getInt(String key) {
		String value = properties.getProperty(key);
		if(! StringUtils.isNumeric(value)) {
			return Integer.parseInt(value);
		}
		return -1;
	}
	
}
