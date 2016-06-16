package ms.luna.biz.bl;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

import com.mongodb.client.MongoCollection;

import ms.biz.common.MongoConnector;
import ms.biz.common.MongoUtility;
import ms.biz.common.ServiceConfig;
import ms.luna.biz.util.COSUtil;
import com.alibaba.fastjson.JSONObject;

public class TestManageBusinessTreeBL {
	
	private final static Logger logger = Logger.getLogger(TestManageBusinessTreeBL.class);
	
//	private ManageBusinessTreeBL manageBusinessTreeBL;
	
	@Before
	public void setUp() {
		
//		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
//				new String[]{"dubbo_provider_dev.xml"});
//		context.start();
//		Resource resource = context.getResource("classpath:config.properties");
//		Properties properties = new Properties();
//		try {
//			properties.load(resource.getInputStream());
//			ServiceConfig.setConfig(properties);
//			COSUtil.cosBaseDir = ServiceConfig.getString(ServiceConfig.COS_BASE_DIR);
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			logger.error("Failed to load config");;
//		}
		
//		manageBusinessTreeBL = (ManageBusinessTreeBL) context.getBean("manageBusinessTreeBL");
	}

//	@Test
//	public void testViewBusinessTree() {
//		manageBusinessTreeBL.viewBusinessTree("{\"businessId\":31}");
//	}
	
	@Test
	public void testFindPoi() {
		MongoConnector mongoConnector = new MongoConnector("mongo.properties");
		MongoCollection<Document> poi_collection = mongoConnector.getDBCollection("poi_collection");
		JSONObject param = new JSONObject();
		param.put("long_title", "测试无私有字段");
		param.put("short_title", "别名而已");
		param.put("tags", new Integer[]{7});
		param.put("lat", 39.123456);
		param.put("lng", 115.123456);
		param.put("subTag", 54);

		System.out.println(MongoUtility.isPoiExsit(poi_collection, param));
	}
}
