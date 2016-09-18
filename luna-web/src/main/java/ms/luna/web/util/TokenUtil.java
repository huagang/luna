package ms.luna.web.util;

import ms.luna.biz.cons.QRedisConfig;
import ms.luna.biz.util.QRedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;

import java.util.UUID;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-08-03
 */
public class TokenUtil {

    public final static Logger logger = Logger.getLogger(TokenUtil.class);
    public final static Long DEFAULT_EXPIRE_TIME_MILLIS = 10000l;

    public static String generateTokenByUserId(String uniqueId, long timeMillis) throws Exception {

        Jedis jedis = QRedisUtil.getJedis();
        if(jedis == null) {
            throw new Exception("Failed to get redis instance");
        }
        String key = QRedisUtil.generateLunaRedisKey(uniqueId);
        if(jedis.exists(key)) {
            String value = jedis.get(key);
            jedis.close();
            return value;
        }
        UUID uuid = UUID.randomUUID();
        String token = uuid.toString();
        jedis.set(key, token, "NX", "PX", timeMillis);
        jedis.close();

        return token;
    }

    public static String generateTokenByUserId(String uniqueId) throws Exception {
        return generateTokenByUserId(uniqueId, DEFAULT_EXPIRE_TIME_MILLIS);
    }

    public static String generateRandomToken(long timeMillis) throws Exception {

        Jedis jedis = QRedisUtil.getJedis();
        if(jedis == null) {
            throw new Exception("Failed to get redis instance");
        }

        UUID uuid = UUID.randomUUID();
        String token = uuid.toString();

        String key = QRedisUtil.generateLunaRedisKey(token);
        jedis.set(key, "1", "NX", "PX", timeMillis);
        jedis.close();

        return token;
    }

    public static String generateRandomToken() throws Exception {

        return generateRandomToken(DEFAULT_EXPIRE_TIME_MILLIS);
    }

    public static boolean verifyToken(String token) throws Exception {
        Jedis jedis = QRedisUtil.getJedis();
        if(jedis == null) {
            throw new Exception("Failed to get redis instance");
        }
        String key = QRedisUtil.generateLunaRedisKey(token);

        boolean exist = jedis.exists(key);
        if(exist) {
            jedis.del(key);
        }
        jedis.close();

        return exist;
    }

    public static boolean verifyToken(String uniqueId, String token) throws Exception {

        Jedis jedis = QRedisUtil.getJedis();
        if(jedis == null) {
            throw new Exception("Failed to get redis instance");
        }
        String key = QRedisUtil.generateLunaRedisKey(uniqueId);

        String value = jedis.get(key);
        if(StringUtils.isBlank(value)) {
            jedis.close();
            return false;
        }
        jedis.del(key);
        jedis.close();

        return value.equals(token);
    }

    public static void main(String[] args) throws Exception {

        QRedisConfig.HOST = "115.159.87.160";
        QRedisConfig.PASSWORD = "ssdoiw32237ejcu#R%#@";
        QRedisConfig.DEFAULT_MAX_WAIT_MILLIS = 5000;
        QRedisConfig.DEFAULT_TIME_OUT = 5000;

        QRedisUtil.init();

        String token = generateTokenByUserId("1", 5000);

        System.out.println(token);

    }


}
