package ms.luna.biz.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesReader {
	
	private static Logger logger = Logger.getLogger(PropertiesReader.class);

	private Properties properties;
	
	public PropertiesReader()
	{
		this.properties = new Properties();
	}
	
	public void load(String filePath) throws IOException
	{
		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream(filePath);
			properties.load(fis);
		} catch (Exception e) {
			logger.error("Read config file error.", e);
		} finally {
			if(fis != null)
			{
				fis.close();
			}
		}
	}
	
	public String getString(String name, String defaultValue)
	{
		if(properties == null)
		{
			logger.error("Property is null.");
			return defaultValue;
		}
		
		String result = properties.getProperty(name);
		if(result == null)
		{
			logger.error("Property not exist: " + name);
			return defaultValue;
		}
		
		return result;
	}

	public String[] getStrings(String name, String defaultValue)
	{
		if(properties == null)
		{
			logger.error("Property is null.");
			return (defaultValue != null) ? defaultValue.split(",") : null;
		}
		
		String result = properties.getProperty(name);
		if(result == null)
		{
			logger.error("Property not exist: " + name);
			return (defaultValue != null) ? defaultValue.split(",") : null;
		}
		
		return result.split(",");
	}

	public int getInteger(String name, int defaultValue)
	{
		if(properties == null)
		{
			logger.error("Property is null.");
			return defaultValue;
		}
		
		String result = properties.getProperty(name);
		if(result == null)
		{
			logger.error("Property not exist: " + name);
			return defaultValue;
		}
		
		try {
			return Integer.valueOf(result);
		} catch(Exception e) {
			logger.error("Property value format error: " + result, e);
			return defaultValue;
		}
	}

}
