package ms.luna.biz.cons;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-29
 */
public class QRedisConfig {

    private final static Logger logger = Logger.getLogger(QRedisConfig.class);

    public final static String KEY_PREFIX = "LUNA";

    public static String HOST = "115.159.87.160";
    public static int PORT = 6379;
    public static String INSTANCE_ID = "";
    public static String PASSWORD = "";

    public static int DEFAULT_TIME_OUT = 1000;
    public static int DEFAULT_MAX_IDLE = 5;
    public static int DEFAULT_MAX_TOTAL = 20;
    public static long DEFAULT_MAX_WAIT_MILLIS = 1000l;

    public static void loadRedisConf() throws IOException {

        Properties properties = new Properties();
        properties.load(QRedisConfig.class.getResourceAsStream("/redis.properties"));

        HOST = properties.getProperty("host");
        INSTANCE_ID = properties.getProperty("instance_id", "");
        PASSWORD = properties.getProperty("password");

        logger.info("redis host: " + HOST);

        String timeOut = properties.getProperty("timeout");
        if(StringUtils.isNotBlank(timeOut)) {
            DEFAULT_TIME_OUT = Integer.parseInt(timeOut);
        }

        String maxIdle = properties.getProperty("max_idle");
        if(StringUtils.isNotBlank(maxIdle)) {
            DEFAULT_MAX_IDLE = Integer.parseInt(maxIdle);
        }

        String maxTotal = properties.getProperty("max_total");
        if(StringUtils.isNotBlank(maxTotal)) {
            DEFAULT_MAX_TOTAL = Integer.parseInt(maxTotal);
        }

        String maxWaitMillis = properties.getProperty("max_wait_millis");
        if(StringUtils.isNotBlank(maxWaitMillis)) {
            DEFAULT_MAX_WAIT_MILLIS = Long.parseLong(maxWaitMillis);
        }
    }

    public static void main(String[] args) throws IOException {
        QRedisConfig.loadRedisConf();
    }

}