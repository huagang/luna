package ms.luna.biz.util;

import java.util.UUID;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-08-08
 */
public class UUIDGenerator {

    public static String generateUUID() {

        return UUID.randomUUID().toString().replace("-", "");
    }
}
