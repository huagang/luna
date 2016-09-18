package ms.luna.biz.sc;

import com.alibaba.fastjson.JSONObject;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: lee@visualbusiness.com
 * @Data: 2016-09-07
 */
public interface WXSignatureService {

    JSONObject getToken(JSONObject jsonObject);

    JSONObject getTicket(JSONObject jsonObject);

    JSONObject getSignature(JSONObject jsonObject);

}
