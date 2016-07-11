package ms.luna.biz.util;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.QCosConfig;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-08
 */
public class LunaQCosUtil {

    public static final String LUNA_STATIC_RESOURCE_DOMAIN = "http://view.luna.visualbusiness.cn";
    public static final String COS_DOMAIN = "http://luna-10002033.file.myqcloud.com";

    public static void replaceAccessUrl(JSONObject jsonObject) {

        JSONObject data = jsonObject.getJSONObject("data");
        if(data != null) {
            String accessUrl = data.getString(QCosConfig.ACCESS_URL);
            if(accessUrl != null) {
                accessUrl = accessUrl.replace(
                        COS_DOMAIN, LUNA_STATIC_RESOURCE_DOMAIN);
                data.put(QCosConfig.ACCESS_URL, accessUrl);
            }
        }
    }
}
