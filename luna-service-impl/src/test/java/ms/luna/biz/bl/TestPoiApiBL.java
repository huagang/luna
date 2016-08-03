package ms.luna.biz.bl;

import java.io.IOException;
import java.util.Properties;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.fastjson.JSONArray;
import ms.luna.biz.util.FastJsonUtil;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

import com.alibaba.fastjson.JSONObject;

import ms.biz.common.ServiceConfig;
import ms.luna.biz.cons.VbConstant.Module.Poi;
import ms.luna.biz.util.COSUtil;
import ms.luna.biz.util.MsLogger;

public class TestPoiApiBL {

	private final static Logger logger = Logger.getLogger(TestPoiApiBL.class);
	
	
	private ManagePoiBL managePoiBL;
	
	private MsZoneCacheBL msZoneCacheBL;
	
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
	}

	@Test
	public void getPoisAround(){
		JSONObject param = new JSONObject();
		Double lng = 116.3568879999999979;
		Double lat = 39.9633070000000004;
		Double radius = Double.parseDouble("1000");
		String lang = "zh";
		String fields = "poi_name,lnglat,address";
		Integer number = 20;
		param.put("lng", lng);
		param.put("lat", lat);
		param.put("lang", lang);
		param.put("fields", fields);
		param.put("radius", radius);
		param.put("number", number);
		JSONObject result = poiApiBL.getPoisAround(param.toString());
		MsLogger.debug(result.toString());
		System.out.println(result.toString());

	}

	@Test
	public void getPoisByActivityId(){
		JSONObject param = new JSONObject();
		String activity_id = "111111,222222";
		String lang = "ALL";
		String fields = "poi_name";
		param.put("activity_id", activity_id);
		param.put("lang", lang);
		param.put("fields", fields);
		JSONObject result = poiApiBL.getPoisByActivityId(param.toString());
		System.out.println(result.toString());
	}

	public static void main(String[] args) {
		JSONObject jsonObject = new JSONObject();
		String[] tags = new String[]{"1", "2"};
		jsonObject.put("tags", tags);
		System.out.println(jsonObject.toString());

		FastJsonUtil.parse2Array(jsonObject.get("tags")).getInteger(0);

	}
}
