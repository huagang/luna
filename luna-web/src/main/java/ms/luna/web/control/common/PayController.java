/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package ms.luna.web.control.common;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.sc.LunaOrderService;
import ms.luna.biz.sc.WXPayService;
import ms.luna.biz.sc.WXSignatureService;
import ms.luna.biz.table.LunaOrderTable;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.RemoteIPUtil;
import ms.luna.common.LunaUserSession;
import ms.luna.model.adapter.AbstractWXPayProcess;
import ms.luna.web.common.SessionHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.DecimalFormat;

/**
 * Created by SDLL18 on 16/9/2.
 */
@Controller
@RequestMapping("/common/pay")
public class PayController {

    /**
     * 支付整个调用实例在testwxpay.jsp中都有存在
     */

    public static final Logger logger = Logger.getLogger(PayController.class);

    private static DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @Autowired
    private WXPayService wxPayService;

    @Autowired
    private WXSignatureService wxSignatureService;

    @Autowired
    private LunaOrderService lunaOrderService;

    @RequestMapping(method = RequestMethod.GET, value = "/wx/jsapi/getSignature")
    @ResponseBody
    public JSONObject getSignature(@RequestParam(value = "url") String url) {
        try {
            JSONObject object = wxSignatureService.getTicket(new JSONObject());
            if (object.getInteger("code").intValue() == 0) {
                String ticket = object.getJSONObject("data").getString("ticket");
                JSONObject in = new JSONObject();
                in.put("ticket", ticket);
                in.put("url", url);
                return wxSignatureService.getSignature(in);
            } else {
                return object;
            }
        } catch (Exception e) {
            logger.error("Failed to get signature", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
    }


    @RequestMapping(value = "/wx/jsapi/getCode")
    public void jsapiGetCode(HttpServletResponse response,
                             @RequestParam(value = "redirectUrl") String url) {
        try {
            StringBuilder redirectUrl = new StringBuilder();
            redirectUrl.append("https://open.weixin.qq.com/connect/oauth2/authorize?appid=");
            redirectUrl.append(AbstractWXPayProcess.APP_ID);
            redirectUrl.append("&redirect_uri=");
            redirectUrl.append(URLEncoder.encode(url, "utf-8"));
            redirectUrl.append("&response_type=code&scope=snsapi_base");
            redirectUrl.append("&state=");
            String state = ""; //max 128 bytes 自定义字段,会原样返回
            redirectUrl.append(state);
            redirectUrl.append("#wechat_redirect");
            response.sendRedirect(redirectUrl.toString());
        } catch (Exception e) {
            logger.error("Failed to get openId", e);
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } catch (Exception e2) {
                logger.error("response error", e2);
            }
        }
    }

    @RequestMapping(value = "/wx/jsapi/getOpenId")
    @ResponseBody
    public JSONObject jsapiGetOpenId(@RequestParam(value = "code") String code,
                                     @RequestParam(value = "state") String state) {
        //TODO deal with state(send in the last step[getCode api])

        return wxPayService.getOpenId(code);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/wx/app/getPrepayId")
    @ResponseBody
    public JSONObject appGetPrepayId(HttpServletRequest request,
                                     @RequestParam(value = "orderId") Integer orderId) {
        LunaUserSession userSession = SessionHelper.getUser(request.getSession(false));
        JSONObject info = lunaOrderService.getOrderInfo(orderId, userSession.getUniqueId(), userSession.getRoleId());
        if (info.getInteger("code").intValue() == 0) {
            JSONObject orderInfo = info.getJSONObject("data");
            if (orderInfo.getInteger(LunaOrderTable.FIELD_STATUS).intValue() == 100) {
                JSONObject inData = new JSONObject();
                inData.put("content", "微景农+订单");//订单名称
                inData.put("extraParam", "");//额外信息 notify中会原样返回
                // inData.put("detail", "{\"test11\":\"123\"}");
                inData.put("notifyUrl", "http://luna-pre.visualbusiness.cn/luna-web/common/pay/wx/notify");//设置notify地址
                inData.put("tradeNo", orderInfo.getString(LunaOrderTable.FIELD_TRADE_NO));
                inData.put("userIp", RemoteIPUtil.getAddr(request).split(",")[0]);
                inData.put("money", Double.parseDouble(decimalFormat.format(orderInfo.getDoubleValue(LunaOrderTable.FIELD_PAY_MONEY))) * 100);
                return wxPayService.appPay(inData);
            } else {
                return FastJsonUtil.error(ErrorCode.STATUS_ERROR, "当前订单状态不可支付");
            }

        } else {
            return info;
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/wx/jsapi/getPrepayId")
    @ResponseBody
    public JSONObject jsapiGetPrepayId(HttpServletRequest request,
                                       @RequestParam(value = "orderId") Integer orderId,
                                       @RequestParam(value = "openId") String openId) {
        LunaUserSession userSession = SessionHelper.getUser(request.getSession(false));
        JSONObject info = lunaOrderService.getOrderInfo(orderId, userSession.getUniqueId(), userSession.getRoleId());
        if (info.getInteger("code").intValue() == 0) {
            JSONObject orderInfo = info.getJSONObject("data");
            if (orderInfo.getInteger(LunaOrderTable.FIELD_STATUS).intValue() == 100) {
                JSONObject inData = new JSONObject();
                inData.put("content", "微景农+订单");//订单名称
                inData.put("extraParam", "");//额外信息 notify中会原样返回
                // inData.put("detail", "{\"test11\":\"123\"}");
                inData.put("notifyUrl", "http://luna-pre.visualbusiness.cn/luna-web/common/pay/wx/notify");//设置notify地址
                inData.put("tradeNo", orderInfo.getString(LunaOrderTable.FIELD_TRADE_NO));
                inData.put("userIp", RemoteIPUtil.getAddr(request).split(",")[0]);
                inData.put("money", (int) (Double.parseDouble(decimalFormat.format(orderInfo.getDoubleValue(LunaOrderTable.FIELD_PAY_MONEY))) * 100));
                inData.put("openId", openId);
                return wxPayService.appPay(inData);
            } else {
                return FastJsonUtil.error(ErrorCode.STATUS_ERROR, "当前订单状态不可支付");
            }

        } else {
            return info;
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/wx/notify")
    @ResponseBody
    public String notifyCompletePayment(@RequestBody String xml) {
        JSONObject inData = wxPayService.payNotify(xml);
        if (inData.getIntValue("code") == 0) {
            JSONObject result = lunaOrderService.finishPayment(inData.getJSONObject("data"));
            if (result.getIntValue("code") == 0) {
                logger.info("wx finish payment successfully.\ndetail:" + inData);
                //完成支付成功
                //do nothing
            } else {
                logger.error("wx finish payment process error\n" + inData.getString("msg") + "\ndetail:" + xml);
            }
        } else {
            logger.error("wx finish payment return error\n" + inData.getString("msg") + "\ndetail:" + xml);
        }
        return WXPayService.XML_SEND_OK_TO_WX;
    }
}
