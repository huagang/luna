package ms.luna.biz.sc.impl;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.sc.WXSignatureService;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.WXSignUtil;
import ms.luna.schedule.service.WXTokenGetter;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: lee@visualbusiness.com
 * @Data: 2016-09-07
 */
@Service("wxSignatureService")
public class WXSignatureServiceImpl implements WXSignatureService {

    private static final Logger logger = Logger.getLogger(WXSignatureServiceImpl.class);


    @Override
    public JSONObject getToken(JSONObject jsonObject) {
        try {
            JSONObject result = new JSONObject();
            String token = "";
            if (jsonObject.containsKey("appId") && (jsonObject.containsKey("appSecret"))) {
                String appId = jsonObject.getString("appId");
                String appSecret = jsonObject.getString("appSecret");
                WXTokenGetter getter = WXTokenGetter.getSingleInstance(appId, appSecret);
                token = getter.getToken();
            } else {
                WXTokenGetter getter = WXTokenGetter.getSingleInstance();
                token = getter.getToken();
            }
            if (token == null || token.equals("")) {
                return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
            } else {
                result.put("token", token);
            }
            return FastJsonUtil.sucess("success", result);
        } catch (Exception e) {
            logger.error("Failed to get token.", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
    }

    @Override
    public JSONObject getTicket(JSONObject jsonObject) {
        try {
            JSONObject result = new JSONObject();
            String ticket = "";
            if (jsonObject.containsKey("appId") && (jsonObject.containsKey("appSecret"))) {
                String appId = jsonObject.getString("appId");
                String appSecret = jsonObject.getString("appSecret");
                WXTokenGetter getter = WXTokenGetter.getSingleInstance(appId, appSecret);
                ticket = getter.getTicket();

            } else {
                WXTokenGetter getter = WXTokenGetter.getSingleInstance();
                ticket = getter.getTicket();
            }
            if (ticket == null || ticket.equals("")) {
                return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
            } else {
                result.put("ticket", ticket);
            }
            return FastJsonUtil.sucess("success", result);
        } catch (Exception e) {
            logger.error("Failed to get ticket.", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
    }

    @Override
    public JSONObject getSignature(JSONObject jsonObject) {
        try {
            String ticket = jsonObject.getString("ticket");
            String url = jsonObject.getString("url");
            JSONObject result = WXSignUtil.sign(ticket, url);
            return FastJsonUtil.sucess("success", result);
        } catch (Exception e) {
            logger.error("Failed to get signature.", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
    }
}
