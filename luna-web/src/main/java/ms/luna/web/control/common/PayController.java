/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package ms.luna.web.control.common;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.sc.WXPayService;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.RemoteIPUtil;
import ms.luna.model.adapter.AbstractWXPayProcess;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * Created by SDLL18 on 16/9/2.
 */
@Controller
@RequestMapping("/common/pay")
public class PayController {

    public static final Logger logger = Logger.getLogger(PayController.class);

    @Autowired
    private WXPayService wxPayService;


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
            String state = ""; //max 128 bytes
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

        JSONObject in = wxPayService.getOpenId(code);
        JSONObject out = new JSONObject();
        out.put("openId", in.getString("openId"));
        return FastJsonUtil.sucess("success", out);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/wx/app/getPrepayId")
    @ResponseBody
    public JSONObject appGetPrepayId(HttpServletRequest request,
                                     @RequestParam(value = "orderId") String orderId) {
        //TODO Is the order belong to the user?

        //TODO Is the order's status can be payed

        //TODO assemble requestJSON  including which way
        JSONObject inData = new JSONObject();
        inData.put("content", "test11");
        inData.put("extraParam", "");
        inData.put("detail", "");
        inData.put("notifyUrl", "");
        inData.put("tradeNo", "");
        inData.put("userIp", RemoteIPUtil.getAddr(request).split(",")[0]);
        inData.put("money", 1);
        //TODO return the message that front end needed
        return wxPayService.appPay(inData);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/wx/jsapi/getPrepayId")
    @ResponseBody
    public JSONObject jsapiGetPrepayId(HttpServletRequest request,
                                       @RequestParam(value = "orderId") String orderId,
                                       @RequestParam(value = "openId") String openId) {
        //TODO Is the order belong to the user?

        //TODO Is the order's status can be payed

        //TODO assemble requestJSON  including which way
        JSONObject inData = new JSONObject();
        inData.put("content", "test11");
        inData.put("extraParam", "");
        inData.put("detail", "");
        inData.put("notifyUrl", "");
        inData.put("tradeNo", "");
        inData.put("userIp", RemoteIPUtil.getAddr(request).split(",")[0]);
        inData.put("money", 1);
        inData.put("openId", openId);
        //TODO return the message that front end needed
        return wxPayService.jsapiPay(inData);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/wx/notify")
    @ResponseBody
    public String notifyCompletePayment(@RequestBody String xml) {
        JSONObject inData = wxPayService.payNotify(xml);
        logger.info(inData);
        //TODO deal with the payment done

        return WXPayService.XML_SEND_OK_TO_WX;
    }
}
