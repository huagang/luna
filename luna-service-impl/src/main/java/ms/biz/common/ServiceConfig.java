package ms.biz.common;

import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class ServiceConfig {

	private final static Logger logger = Logger.getLogger(ServiceConfig.class);

	public static final String COS_BASE_DIR = "cosBaseDir";
	public static final String MS_WEB_URL = "msWebUrl";
	public static final String MTA_QQ = "mtaQQ";
	
	private static Properties properties;
	
	public static void setConfig(Properties properties) {
		ServiceConfig.properties = properties;
	}
	
	public static String getString(String key) {
		return properties.getProperty(key, "/dev");
	}
	
	public static int getInt(String key) {
		String value = properties.getProperty(key);
		if(StringUtils.isNumeric(value)) {
			return Integer.parseInt(value);
		}
		return -1;
	}

	public static long getLong(String key) {
		String value = properties.getProperty(key);
		if(StringUtils.isNumeric(value)) {
			return Long.parseLong(value);
		}
		return -1;
	}

	
}
