package ms.luna.biz.serdes.deal;

import com.alibaba.fastjson.JSONObject;
import org.bson.Document;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-09-14
 */
public interface DealSerDes {

    Document toDocument(JSONObject obj);
    JSONObject toJSON(Document document);
}
