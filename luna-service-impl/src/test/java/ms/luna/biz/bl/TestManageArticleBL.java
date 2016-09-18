package ms.luna.biz.bl;

import ms.biz.common.ServiceConfig;
import ms.luna.biz.dao.custom.MsArticleDAO;
import ms.luna.biz.dao.custom.MsShowAppDAO;
import ms.luna.biz.util.COSUtil;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

public class TestManageArticleBL {
	
	private final static Logger logger = Logger.getLogger(TestManageArticleBL.class);
	
	private MsArticleDAO msArticleDAO;
	
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
		
		msArticleDAO = (MsArticleDAO) context.getBean("msArticleDAO");
	}
	
	@Test
	public void testSelectCategoryIdByBusinessId() {
		System.out.println(msArticleDAO.selectCategoryIdByBusinessId(44));
	}
}
