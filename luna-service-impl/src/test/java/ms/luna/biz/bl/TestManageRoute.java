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
	
	@Test
	public void createRoute(){
		JSONObject param = new JSONObject();

		String name = "线路管理测试1";
		String description = "test";
		String cover = "";
		String unique_id = "3f1n0E1E003o1J2A0X1b3q0B3P1y1q3s";
		Integer cost_id = 1;
		Integer business_id = 46;

		param.put("name", name);
		param.put("description", description);
		param.put("cover", cover);
		param.put("unique_id", unique_id);
		param.put("cost_id", cost_id);
		param.put("business_id", business_id);
		param.put("luna_name", "greek");

		JSONObject result = manageRouteService.createRoute(param);
		System.out.println(result.toString());

	}
	
	@Test
	public void delRoute(){
		Integer id = 35;
		JSONObject result = manageRouteService.delRoute(id);
		System.out.println(result.toString());
	}
	
	@Test
	public void viewRouteConfiguration() {
		Integer id = 30;
		JSONObject result = manageRouteService.viewRouteConfiguration(id);
		System.out.println(result.toString());
	}

	@Test
	public void saveRouteConfiguration(){
		String c_list = "{\"routeData\":[{\"start_time\":\"11:00\",\"end_time\":\"12:45\",\"poi_id\":\"5772311b8ccb783280163939\"},{\"start_time\":\"11:00\",\"end_time\":\"12:45\",\"poi_id\":\"5760bc433fc92303d8d2255b\"}]}";
		String luna_name = "greek test";
		Integer id = 35;
		JSONObject param = new JSONObject();
		param.put("data", c_list);
		param.put("route_id", id);
		param.put("luna_name", luna_name);
		JSONObject result = manageRouteService.saveRouteConfiguration(param);
		System.out.println(result.toString());
	}

	@Test
	public void test() {
		delRoute();
		createRoute();
	}
}
