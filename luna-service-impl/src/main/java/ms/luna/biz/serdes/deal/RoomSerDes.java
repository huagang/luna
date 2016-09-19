package ms.luna.biz.serdes.deal;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.table.RoomBasicInfoTable;
import org.bson.Document;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-09-14
 */
public class RoomSerDes implements DealSerDes {


    @Override
    public Document toDocument(JSONObject jsonObject) {

        Document document = new Document();
//        document.append(RoomBasicInfoTable.FIELD_ID, jsonObject.getString(RoomBasicInfoTable.FIELD_ID));
//        document.append(RoomBasicInfoTable.FIELD_NAME, jsonObject.getString(RoomBasicInfoTable.FIELD_NAME));

        document.putAll(jsonObject);

        return document;
    }

    @Override
    public JSONObject toJSON(Document document) {
        return JSON.parseObject(document.toJson());
    }
}
