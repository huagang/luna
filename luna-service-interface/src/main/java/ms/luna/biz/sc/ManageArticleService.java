package ms.luna.biz.sc;

import com.alibaba.fastjson.JSONObject;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-06-14
 */
public interface ManageArticleService {

    JSONObject createArticle(String json);
    JSONObject getArticleById(int id);
    JSONObject updateArticle(String json);
    JSONObject deleteArticle(int id);
    JSONObject loadArticle(String json);
    JSONObject searchBusiness(String json);
}
