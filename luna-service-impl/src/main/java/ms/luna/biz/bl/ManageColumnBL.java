package ms.luna.biz.bl;

import com.alibaba.fastjson.JSONObject;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-06-14
 */
public interface ManageColumnBL {

    JSONObject createColumn(String json);
    JSONObject getColumnById(int id);
    JSONObject updateColumn(String json);
    JSONObject deleteColumn(int id);
    JSONObject loadColumn(String json);
    JSONObject readAllColumn();
}
