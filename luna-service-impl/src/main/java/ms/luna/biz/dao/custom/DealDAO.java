package ms.luna.biz.dao.custom;

import com.alibaba.fastjson.JSONObject;
import org.bson.Document;

import java.util.List;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-09-14
 */
public interface DealDAO {

    void insertDeal(Document document);
    Document getDeal(String dealId);
    Document getDeal(String dealId, List<String> fields);
    List<Document> loadDeal(JSONObject jsonQuery);
    void deleteDeal(String dealId);
    void updateDeal(String dealId, Document document);

}
