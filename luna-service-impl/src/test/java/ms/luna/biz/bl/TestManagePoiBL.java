package ms.luna.biz.bl;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

import com.alibaba.fastjson.JSONObject;

import ms.biz.common.ServiceConfig;
import ms.luna.biz.util.COSUtil;

public class TestManagePoiBL {

	private final static Logger logger = Logger.getLogger(TestManagePoiBL.class);
	
	
	private ManagePoiBL managePoiBL;
	
	private MsZoneCacheBL msZoneCacheBL;
	
	@Before
	public void setUp() {
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[]{"dubbo_provider_dev.xml"});
		context.start();
		Resource resource = context.getResource("classpath:config.properties");
		Properties properties = new Properties();
		try {
			properties.load(resource.getInputStream());
			ServiceConfig.setConfig(properties);
			COSUtil.cosBaseDir = ServiceConfig.getString(ServiceConfig.COS_BASE_DIR);
		} catch (IOException e1) {
			logger.error("Failed to load config");
		}
		msZoneCacheBL = (MsZoneCacheBL)context.getBean("msZoneCacheBL");
		msZoneCacheBL.init();
		System.out.println("LunaProvider Service is started!");
		
		managePoiBL = (ManagePoiBL) context.getBean("managePoiBL");
	}
	
	@Test
	public void getPreviewInfo(){
		String _id = "5760bc073fc92303d8d2255a";
		String lang = "en";
		JSONObject param = new JSONObject();
		param.put("_id", _id);
		param.put("lang", lang);
		System.out.println(managePoiBL.initPoiPreview(param.toString()));
		
		_id = "577a504b5971a175bbe3c44a";
		lang = "zh";
		param = new JSONObject();
		param.put("_id", _id);
		param.put("lang", lang);
		System.out.println(managePoiBL.initPoiPreview(param.toString()));
		
	}
}
