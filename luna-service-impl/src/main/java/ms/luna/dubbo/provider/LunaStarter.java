package ms.luna.dubbo.provider;

import ms.biz.common.ServiceConfig;
import ms.luna.biz.bl.MsZoneCacheBL;
import ms.luna.biz.bl.PoiApiBL;
import ms.luna.biz.cons.QRedisConfig;
import ms.luna.biz.util.COSUtil;
import ms.luna.biz.util.QRedisUtil;
import ms.luna.schedule.service.CacheService;
import ms.luna.schedule.service.EmailService;
import ms.luna.schedule.service.SMSScheduler;
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
    private EmailService emailService;
    private SMSScheduler smsService;
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
            } catch (IOException e) {
                // TODO Auto-generated catch block
                logger.error("Failed to load config", e);
            }
            MsZoneCacheBL msZoneCacheBL = (MsZoneCacheBL) context.getBean("msZoneCacheBL");
            msZoneCacheBL.init();
            PoiApiBL poiApiBL = (PoiApiBL) context.getBean("poiApiBL");
            poiApiBL.init();

            CacheService cacheService = new CacheService(context);
            cacheService.start();

            //redis 初始化
            try {
                QRedisConfig.loadRedisConf();
                QRedisUtil.init();
                logger.info("init redis: " + QRedisConfig.HOST);
            } catch (IOException e) {
                logger.error("Failed to load redis conf");
                throw new RuntimeException("Failed to load conf");
            }

            //初始化发送邮件线程池
            emailService = (EmailService) context.getBean("emailScheduler");
            emailService.start();
            logger.info("init email scheduler");

            //初始化发送短信线程池
            smsService = (SMSScheduler) context.getBean("smsScheduler");
            smsService.start();
            logger.info("init SMS scheduler");

            System.out.println("LunaProvider Service is started!");
            shutdownLatch.await();
        } catch (InterruptedException e) {
            logger.error("Failed to start", e);
            shutdown();
        }

    }

    public void shutdown() {
        emailService.shutdown();
        smsService.shutdown();
        shutdownLatch.countDown();
        logger.info("shutdown luna starter");
    }
}
