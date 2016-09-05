/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package ms.luna.biz.sc.impl;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.sc.WXPayService;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.model.adapter.PayProcessAdapter;
import ms.luna.model.adapter.WXPayProcess;
import org.apache.log4j.Logger;

import javax.annotation.Resource;

/**
 * Created by SDLL18 on 16/9/2.
 *
 * @author SDLL18
 */
public class WXPayServiceImpl implements WXPayService {

    private static final Logger logger = Logger.getLogger(WXPayServiceImpl.class);

    @Resource(name = "wxAppPayMethod")
    private PayProcessAdapter wxAppPayMethod;

    @Resource(name = "wxJSAPIPayMethod")
    private PayProcessAdapter wxJSAPIPayMethod;

    @Override
    public JSONObject getOpenId(String code) {
        String r = WXPayProcess.getOpenId(code);
        if (r == null) {
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取openid时发生内部错误");
        }
        JSONObject in = JSONObject.parseObject(r);
        JSONObject out = new JSONObject();
        out.put("openId", in.getString("openid"));
        return FastJsonUtil.sucess("success", out);
    }

    @Override
    public JSONObject appPay(JSONObject jsonObject) {
        try {
            String r = wxAppPayMethod.sendPayMessage(jsonObject.toJSONString());
            if (r == null) {
                logger.error("Failed to get prepayId for app.");
                return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取prepayId时发生内部错误");
            }
            return FastJsonUtil.sucess("success", JSONObject.parseObject(r));
        } catch (Exception e) {
            logger.error("Failed to get prepayId for app.", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取prepayId时发生内部错误");
        }
    }

    @Override
    public JSONObject jsapiPay(JSONObject jsonObject) {
        try {
            String r = wxJSAPIPayMethod.sendPayMessage(jsonObject.toJSONString());
            if (r == null) {
                logger.error("Failed to get prepayId for jsapi.");
                return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取prepayId时发生内部错误");
            }
            return FastJsonUtil.sucess("success", JSONObject.parseObject(r));
        } catch (Exception e) {
            logger.error("Failed to get prepayId for jsapi.", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取prepayId时发生内部错误");
        }
    }

    @Override
    public JSONObject payNotify(String xmlData) {
        try {
            String r = wxAppPayMethod.parseNotificationMessage(xmlData);
            if (r == null) {
                logger.error("Failed to parse the notification message.");
                return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取prepayId时发生内部错误");
            }
            return FastJsonUtil.sucess("success", JSONObject.parseObject(r));
        } catch (Exception e) {
            logger.error("Failed to parse the notification message.", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "解析xml失败");
        }
    }
}
