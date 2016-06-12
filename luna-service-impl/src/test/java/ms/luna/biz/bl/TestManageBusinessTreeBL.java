package ms.luna.biz.bl;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;


import ms.biz.common.ServiceConfig;
import ms.luna.biz.util.COSUtil;

public class TestManageBusinessTreeBL {
	
	private final static Logger logger = Logger.getLogger(TestManageBusinessTreeBL.class);
	
	private ManageBusinessTreeBL manageBusinessTreeBL;
	
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
			// TODO Auto-generated catch block
			logger.error("Failed to load config");;
		}
		
		manageBusinessTreeBL = (ManageBusinessTreeBL) context.getBean("manageBusinessTreeBL");
	}

	@Test
	public void testViewBusinessTree() {
		manageBusinessTreeBL.viewBusinessTree("{\"businessId\":31}");
	}
}
