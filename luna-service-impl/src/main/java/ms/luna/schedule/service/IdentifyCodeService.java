/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package ms.luna.schedule.service;

import ms.luna.biz.util.QRedisUtil;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;

import java.util.Random;
import java.util.UUID;

/**
 * Created by SDLL18 on 16/8/26.
 */
public class IdentifyCodeService {


    public final static Logger logger = Logger.getLogger(IdentifyCodeService.class);
    public final static Long DEFAULT_EXPIRE_TIME_MILLIS = 10000l;

    private IdentifyCodeService() {

    }

    /**
     * @param uniqueId   This param can be user's uniqueId or phone number or any other unique no
     * @param target
     * @param timeMillis
     * @return
     * @throws Exception
     */
    public static String getCode(String uniqueId, String target, long timeMillis) throws Exception {
        Jedis jedis = QRedisUtil.getJedis();
        if (jedis == null) {
            throw new Exception("Failed to get redis instance");
        }
        String key = "LUNA_CODE_" + uniqueId + "_" + target;
        if (jedis.exists(key)) {
            return jedis.get(key);
        }
        String code = generateCode(6, true);
        jedis.set(key, code, "NX", "PX", timeMillis);
        jedis.close();
        return code;
    }

    public static void saveCode(String uniqueId, String target, String code, long timeMillis) throws Exception {
        Jedis jedis = QRedisUtil.getJedis();
        if (jedis == null) {
            throw new Exception("Failed to get redis instance");
        }
        String key = "LUNA_CODE_" + uniqueId + "_" + target;
        jedis.set(key, code, "NX", "PX", timeMillis);
        jedis.close();
    }

    /**
     * @param uniqueId   This param can be user's uniqueId or phone number or any other unique no
     * @param target
     * @return
     * @throws Exception
     */
    public static boolean checkCode(String uniqueId, String target, String code) throws Exception {
        Jedis jedis = QRedisUtil.getJedis();
        if (jedis == null) {
            throw new Exception("Failed to get redis instance");
        }
        String key = "LUNA_CODE_" + uniqueId + "_" + target;
        if (jedis.exists(key)) {
            String value = jedis.get(key);
            jedis.close();
            if (code.equals(value)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    private static String generateCode(int length, boolean onlyNumber) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        if (onlyNumber) {
            for (int i = 0; i < length; i++) {
                int t = random.nextInt(10);
                sb.append(t);
            }
        } else {
            for (int i = 0; i < length; i++) {
                int t = random.nextInt(62);
                if (t < 10) {
                    sb.append(t);
                } else if (t < 36) {
                    sb.append((char) (65 + t - 10));
                } else {
                    sb.append((char) (97 + t - 36));
                }
            }
        }
        return sb.toString();
    }

}
