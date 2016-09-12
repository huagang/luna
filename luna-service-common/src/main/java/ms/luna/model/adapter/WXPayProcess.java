package ms.luna.model.adapter;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: lee@visualbusiness.com
 * @Data: 2016-09-11
 */
public interface WXPayProcess extends PayProcess {

    String getOpenId(String code);
}
