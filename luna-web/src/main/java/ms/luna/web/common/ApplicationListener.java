package ms.luna.web.common;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import ms.luna.biz.cons.QCosConfig;
import ms.luna.biz.util.COSUtil;
import ms.luna.biz.util.VODUtil;
import ms.luna.web.util.WebHelper;

public class ApplicationListener implements ServletContextListener {

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}
	/*
	 * (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		// webHelper
		event.getServletContext().setAttribute("webHelper", new WebHelper());
		String cosBaseDir = event.getServletContext().getInitParameter("cosBaseDir");
		COSUtil.cosBaseDir = cosBaseDir;
		QCosConfig.ENV = cosBaseDir;
		String vodBaseDir = event.getServletContext().getInitParameter("vodBaseDir");
		VODUtil.vodBaseDir = vodBaseDir; 
	}
}