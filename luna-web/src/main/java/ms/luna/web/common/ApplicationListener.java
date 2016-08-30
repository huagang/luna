package ms.luna.web.common;

import ms.luna.biz.cons.QCosConfig;
import ms.luna.biz.cons.QRedisConfig;
import ms.luna.biz.util.COSUtil;
import ms.luna.biz.util.QRedisUtil;
import ms.luna.biz.util.VODUtil;
import ms.luna.web.control.api.AppApiController;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;

public class ApplicationListener implements ServletContextListener {

	private final static Logger logger = Logger.getLogger(ApplicationListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		QRedisUtil.close();
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		String cosBaseDir = event.getServletContext().getInitParameter("cosBaseDir");
		COSUtil.cosBaseDir = cosBaseDir;
		QCosConfig.ENV = cosBaseDir;
		String vodBaseDir = event.getServletContext().getInitParameter("vodBaseDir");
		VODUtil.vodBaseDir = vodBaseDir;
		AppApiController.APP_DEVELOP_ADDRESS = event.getServletContext().getInitParameter("appDevelopAddr");

		try {
			QRedisConfig.loadRedisConf();
			QRedisUtil.init();
			logger.info("init redis: " + QRedisConfig.HOST);
		} catch (IOException e) {
			logger.error("Failed to load redis conf");
			throw new RuntimeException("Failed to load conf");
		}
	}
}