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

public class TestPoiApiBlImpl {

	private final static Logger logger = Logger.getLogger(TestManageShowAppBL.class);
	
	
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
		
		poiApiBL = (PoiApiBL)context.getBean("poiApiBL");
	}
	
	@Test
	public void getPreviewInfo(){
//		for(int i = 0;i<5;i++){
		try{
			String poi_id = "5760bc073fc92303d8d2255a";// _id = "577a504b5971a175bbe3c44a";
			String lang = "zh";
			int biz_id = 46;
			String fields = "";
			JSONObject param = new JSONObject();
			param.put("poi_id", poi_id);
			param.put("lang", lang);
			param.put("biz_id", biz_id);
			param.put("fields", fields);
			System.out.println(poiApiBL.getPoisInFirstLevel(param.toString()));
		}catch(Exception e){
			MsLogger.debug(e);
		}
//		}
		
	}
}
