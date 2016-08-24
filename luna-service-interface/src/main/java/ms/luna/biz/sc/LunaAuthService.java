package ms.luna.biz.sc;

import com.alibaba.fastjson.JSONObject;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-08-24
 */
public interface LunaAuthService {

    JSONObject hasBusinessAuth(String uniqueId, int businessId);
    JSONObject hasAppAuth(String uniqueId, int appId);
    JSONObject hasArticleAuth(String uniqueId, int articleId);
}
