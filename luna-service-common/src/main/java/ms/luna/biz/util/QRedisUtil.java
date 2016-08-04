package ms.luna.biz.util;

import ms.luna.biz.cons.QRedisConfig;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-29
 */
public class QRedisUtil {

    private final static Logger logger = Logger.getLogger(QRedisUtil.class);

    public static QRedisUtil instance = new QRedisUtil();
    private static JedisPool pool;

    private QRedisUtil() {

    }

    public static void init() {

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(QRedisConfig.DEFAULT_MAX_IDLE);
        jedisPoolConfig.setMaxTotal(QRedisConfig.DEFAULT_MAX_TOTAL);
        jedisPoolConfig.setMaxWaitMillis(QRedisConfig.DEFAULT_MAX_WAIT_MILLIS);
        jedisPoolConfig.setTestOnCreate(true);
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setBlockWhenExhausted(false);

        String password = QRedisConfig.PASSWORD;
        if(StringUtils.isNotBlank(QRedisConfig.INSTANCE_ID)) {
            password = QRedisConfig.INSTANCE_ID + ":" + QRedisConfig.PASSWORD;
        }

        pool = new JedisPool(jedisPoolConfig, QRedisConfig.HOST, QRedisConfig.PORT,
                QRedisConfig.DEFAULT_TIME_OUT, password);
    }



    public static Jedis getJedis() {
        try {
            Jedis jedis = pool.getResource();
            return jedis;
        } catch (RuntimeException ex) {
            logger.error("Failed to get jedis", ex);
        }
        return null;
    }

    public static void close() {
        if(pool != null) {
            pool.destroy();
        }
    }

    public static String generateLunaRedisKey(String key) {
        return QRedisConfig.KEY_PREFIX + key;
    }


}
