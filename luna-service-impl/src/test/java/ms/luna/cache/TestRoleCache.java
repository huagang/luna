package ms.luna.cache;

import ms.biz.common.ServiceConfig;
import ms.luna.biz.dao.custom.MsShowAppDAO;
import ms.luna.biz.dao.model.LunaRole;
import ms.luna.biz.util.COSUtil;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-08-07
 */
public class TestRoleCache {

    private final static Logger logger = Logger.getLogger(TestRoleCache.class);

    private RoleCache roleCache;

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

        roleCache = (RoleCache) context.getBean("roleCache");
    }

    @Test
    public void getChildRolesByRoleId() {
        List<LunaRole> childRolesByRoleId = roleCache.getChildRolesByRoleId(1);
        for(LunaRole lunaRole : childRolesByRoleId) {
            logger.info(lunaRole.getName());
        }
    }
}
