package ms.luna.biz.sc;

import com.alibaba.fastjson.JSONObject;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-09-14
 */
public interface DealService {

    JSONObject getDealFieldsByCateId(int cateId);

    JSONObject loadDeals(JSONObject query);

    JSONObject getDealById(String dealId);

    JSONObject createDeal(JSONObject dealInfo);

    JSONObject updateDeal(JSONObject dealInfo);

    JSONObject deleteDeal(String dealIds);

    JSONObject checkDealName(String name, String id, Integer businessId);

    // 获取商品类别目录
    JSONObject getGoodsCategories(String keyword);

}
