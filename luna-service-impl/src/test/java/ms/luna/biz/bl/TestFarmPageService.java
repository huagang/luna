package ms.luna.biz.bl;

import com.alibaba.fastjson.JSONObject;
import jdk.nashorn.internal.parser.JSONParser;
import ms.biz.common.ServiceConfig;
import ms.luna.biz.dao.custom.MsFarmFieldDAO;
import ms.luna.biz.sc.FarmPageService;
import ms.luna.biz.util.COSUtil;
import ms.luna.biz.util.MsLogger;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

public class TestFarmPageService {

	private final static Logger logger = Logger.getLogger(TestFarmPageService.class);
	
	private MsZoneCacheBL msZoneCacheBL;

	private FarmPageService farmPageService;

	private PoiApiBL poiApiBL;
	
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
		poiApiBL = (PoiApiBL) context.getBean("poiApiBL");
		poiApiBL.init();
		farmPageService = (FarmPageService)context.getBean("farmPageService");
	}
	

	@Test
	public void test(){
		JSONObject param = new JSONObject();
		Integer business_id = 46;
		String app_name = "greek test5";
		String owner = "greek";

		param.put("business_id", business_id);
		param.put("app_name", app_name);
		param.put("owner", owner);

		Integer appId = 268;
		JSONObject result = farmPageService.getPageInfo(appId);
		logger.debug(result.toString());
		System.out.println(result.toString());

	}

	@Test
	public void getPageInfo(){
		Integer appId = 298;
		JSONObject result = farmPageService.getPageInfo(appId);
		System.out.println(result.toString());



	}

}
