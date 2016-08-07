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
	
//	@Test
//	public void getPoisInFirstLevel(){// okay
//		try {
//			int biz_id = 46;
//			String poi_id = "5760bc073fc92303d8d2255a";
//			String fields = "";
//			String lang = "zh";
//			JSONObject param = new JSONObject();
//			param.put("biz_id", biz_id);
//			param.put("poi_id", poi_id);
//			param.put("lang", lang);
//			param.put("fields", fields);
//			System.out.println("getPoisInFirstLevel:"+poiApiBL.getPoisInFirstLevel(param.toString()));
//		} catch (Exception e) {
//			MsLogger.debug(e);
//		} 
//	}
	
//	@Test
//	public void getPoisByBizIdAndPoiIdAndCtgrId(){//okay
//		int biz_id = 46;
//		String poi_id = "5760bc073fc92303d8d2255a";
//		String fields = ""; 
//		String lang = "zh";
//		int ctgr_id = 2;
//		JSONObject param = new JSONObject();
//		param.put("biz_id", biz_id);
//		param.put("poi_id", poi_id);
//		param.put("lang", lang);
//		param.put("fields", fields);
//		param.put("ctgr_id", ctgr_id);
//		System.out.println("getPoisByBizIdAndPoiIdAndCtgrId:"+poiApiBL.getPoisByBizIdAndPoiIdAndCtgrId(param.toString()));
//	}
	
//	@Test
//	public void getPoisByBizIdAndPoiIdAndSubCtgrId(){// okay
//		int biz_id = 46;
//		String poi_id = "5760bc073fc92303d8d2255a";
//		String fields = ""; 
//		String lang = "zh";
//		int sub_ctgr_id = 9;
//		JSONObject param = new JSONObject();
//		param.put("biz_id", biz_id);
//		param.put("poi_id", poi_id);
//		param.put("lang", lang);
//		param.put("fields", fields);
//		param.put("ctgr_id", sub_ctgr_id);
//		System.out.println("getPoisByBizIdAndPoiIdAndSubCtgrId:"+poiApiBL.getPoisByBizIdAndPoiIdAndSubCtgrId(param.toString()));
//	}
	
	@Test
	public void getPoisByBizIdAndTags(){
		int biz_id = 46;
		String fields = ""; 
		String lang = "zh";
		String tags = "1,2";
		String type = "scene_type";
		JSONObject param = new JSONObject();
		param.put("biz_id", biz_id);
		param.put("fields", fields);
		param.put("tags", tags);
		param.put("lang", lang);
		System.out.println("getPoisByBizIdAndTags:"+poiApiBL.getPoisByBizIdAndTags(param.toString()));
	}
	
	@Test
	public void getPoisByBizIdAndTags2(){//有问题sub_category
		int biz_id = 46;
		String fields = ""; 
		String lang = "zh";
		String tags = "1,2";
		String type = "scene_type";
		JSONObject param = new JSONObject();
		param.put("biz_id", biz_id);
		param.put("fields", fields);
		param.put("tags", tags);
		param.put("lang", lang);
		System.out.println("getPoisByBizIdAndTags2:"+poiApiBL.getPoisByBizIdAndTags(param.toString()));
	}
	/**/
//	@Test
//	public void getPoiInfoById(){//okay
//		try{
//			String poi_id = "5760bc073fc92303d8d2255a";
//			String lang = "zh";
//			int biz_id = 46;
//			String fields = "";
//			JSONObject param = new JSONObject();
//			param.put("poi_id", poi_id);
//			param.put("lang", lang);
//			param.put("biz_id", biz_id);
//			param.put("fields", fields);
//			System.out.println("getPoiInfoById:"+poiApiBL.getPoiInfoById(param.toString()));
//		}catch(Exception e){
//			MsLogger.debug(e.toString());
//		}
//	}
	
//	@Test	
//	public void getCtgrsByBizIdAndPoiId(){// okay
//		String poi_id = "5760bc073fc92303d8d2255a";
//		int biz_id = 46;
//		JSONObject param = new JSONObject();
//		param.put("poi_id", poi_id);
//		param.put("biz_id", biz_id);
//		System.out.println("getCtgrsByBizIdAndPoiId:"+poiApiBL.getCtgrsByBizIdAndPoiId(param.toString()));
//	}
	
	@Test
	public void getSubCtgrsByBizIdAndPoiIdAndCtgrId(){//sub_category_id有问题
		int biz_id = 46;
		String poi_id = "5760bc073fc92303d8d2255a";
		int ctgr_id = 2;
		JSONObject param = new JSONObject();
		param.put("poi_id", poi_id);
		param.put("biz_id", biz_id);
		param.put("ctgr_id", ctgr_id);
		System.out.println("getSubCtgrsByBizIdAndPoiIdAndCtgrId:"+poiApiBL.getSubCtgrsByBizIdAndPoiId(param.toString()));
	}
	/**/


	@Test
	public void retrivePois() {
		String lang = "ALL";
		Integer limit= 5;
		String type = "ALL";
		String filterName = "greek";
		JSONObject param = new JSONObject();
		param.put("lang" ,lang);
		param.put("filterName", filterName);
		param.put("type",type);
		param.put("limit",limit);
		JSONObject result = poiApiBL.retrievePois(param.toString());
		System.out.println(result.toString());
	}
}
