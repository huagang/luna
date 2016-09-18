package ms.luna.model.adapter;

import java.text.SimpleDateFormat;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: lee@visualbusiness.com
 * @Data: 2016-09-11
 */
public interface WXPayProcess extends PayProcess {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    String getOpenId(String code);
}
