package ms.luna.biz.bl;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

import ms.biz.common.ServiceConfig;
import ms.luna.biz.dao.custom.MsShowAppDAO;
import ms.luna.biz.util.COSUtil;

public class TestManageShowAppBL {
	
	private final static Logger logger = Logger.getLogger(TestManageShowAppBL.class);
	
	private MsShowAppDAO msShowAppDAO;
	
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
			logger.error("Failed to load config");
		}
		
		msShowAppDAO = (MsShowAppDAO) context.getBean("msShowAppDAO");
	}
	
	@Test
	public void testSelectIdByName() {
		msShowAppDAO.selectIdByName("第一个微景展测试");
	}
}
