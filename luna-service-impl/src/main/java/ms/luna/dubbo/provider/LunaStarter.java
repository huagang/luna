package ms.luna.dubbo.provider;

import ms.biz.common.ServiceConfig;
import ms.luna.biz.bl.MsZoneCacheBL;
import ms.luna.biz.bl.PoiApiBL;
import ms.luna.biz.util.COSUtil;
import ms.luna.schedule.service.CacheService;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-21
 */
public class LunaStarter {

    private static final Logger logger = Logger.getLogger(LunaStarter.class);

    private String contextFile;
    private CountDownLatch shutdownLatch = new CountDownLatch(1);

    public LunaStarter(String contextFile) {
        this.contextFile = contextFile;
    }

    public void startup() {
        try {
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                    new String[]{contextFile});
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
            MsZoneCacheBL msZoneCacheBL = (MsZoneCacheBL)context.getBean("msZoneCacheBL");
            msZoneCacheBL.init();
            PoiApiBL poiApiBL = (PoiApiBL) context.getBean("poiApiBL");
            poiApiBL.init();

            CacheService cacheService = new CacheService(context);
            cacheService.start();

            System.out.println("LunaProvider Service is started!");
            shutdownLatch.await();
        } catch (InterruptedException e) {
            logger.error("Failed to start", e);
            shutdown();
        }

    }

    public void shutdown() {


        shutdownLatch.countDown();
        logger.info("shut down luna starter");
    }
}
