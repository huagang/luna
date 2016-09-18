package ms.luna.biz.bl;

import com.alibaba.fastjson.JSONObject;
import ms.biz.common.ServiceConfig;
import ms.luna.biz.sc.FarmPageService;
import ms.luna.biz.util.COSUtil;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
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

	@Test
	public void getShowAppsByCategoryIds() {
		JSONObject param = new JSONObject();
		Integer limit = 3;
		Integer offset = 0;
		String categoryId = "CATE000000000002";
		param.put("limit",limit);
		param.put("offset", offset);
//		param.put()
//		param.put("categoryId", categoryId);
//		JSONObject result = farmPageService.getShowAppsByCtgrId(param.toString());
//		System.out.println(result.toString());
	}

	public static void main(String[] args) {
		List<String> categoryList = Arrays.asList("".split(","));
		System.out.print(categoryList.toString());
	}

}
