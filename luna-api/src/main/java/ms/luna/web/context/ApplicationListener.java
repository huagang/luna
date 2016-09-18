package ms.luna.web.context;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-01
 */
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
//        String msWebUrl = event.getServletContext().getInitParameter("msWebUrl");
    }
}
