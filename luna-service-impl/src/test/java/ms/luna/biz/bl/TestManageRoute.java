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
import ms.luna.biz.sc.ManageRouteService;
import ms.luna.biz.util.COSUtil;
import ms.luna.biz.util.MsLogger;

public class TestManageRoute {

	private ManageRouteService manageRouteService;
	
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
			MsLogger.error("Failed to load config");
		}
		
		manageRouteService = (ManageRouteService) context.getBean("manageRouteService");
	}
	
//	@Test
//	public void createRoute(){
//		JSONObject param = new JSONObject();
//		
//		String name = "线路管理3";
//		String description = "test";
//		String cover = "";
//		String unique_id = "3f1n0E1E003o1J2A0X1b3q0B3P1y1q3s";
//		Integer cost_id = 1;
//		Integer business_id = 46;
//		
//		param.put("name", name);
//		param.put("description", description);
//		param.put("cover", cover);
//		param.put("unique_id", unique_id);
//		param.put("cost_id", cost_id);
//		param.put("business_id", business_id);
//		
//		JSONObject result = manageRouteService.createRoute(param.toString());
//		System.out.println(result.toString());
//		
//		// ---------------------------------
//		name = "线路管理2";
//		
//		param.put("name", name);
//		param.put("description", description);
//		param.put("cover", cover);
//		param.put("unique_id", unique_id);
//		param.put("cost_id", cost_id);
//		param.put("business_id", business_id);
//		
//		result = manageRouteService.createRoute(param.toString());
//		System.out.println(result.toString());
//		
//	}
	
//	@Test
//	public void delRoute(){
//		Integer id = 7;
//		JSONObject result = manageRouteService.delRoute(id);
//		System.out.println(result.toString());
//	}
	
//	@Test
//	public void createRoute(){
//		JSONObject param = new JSONObject();
//		
//		Integer id = 4;
//		String name = "线路管理3";
//		String description = "test2";
//		String cover = "";
//		String unique_id = "3f1n0E1E003o1J2A0X1b3q0B3P1y1q3s";
//		Integer cost_id = 2;
//		
//		param.put("id", id);
//		param.put("name", name);
//		param.put("description", description);
//		param.put("cover", cover);
//		param.put("unique_id", unique_id);
//		param.put("cost_id", cost_id);
//		
//		JSONObject result = manageRouteService.editRoute(param.toString());
//		System.out.println(result.toString());
//	}
	
	@Test
	public void createRoute(){
		JSONObject param = new JSONObject();
		JSONObject result = manageRouteService.loadRoutes(param.toString());
		System.out.println(result.toString());
		
		Integer offset = 1;
		Integer limit = 3;
		
		param.put("offset", offset);
		param.put("limit", limit);
		
		result = manageRouteService.loadRoutes(param.toString());
		System.out.println(result.toString());
	}
}
