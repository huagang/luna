/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package ms.luna.schedule.service;

import com.alibaba.fastjson.JSONObject;
import ms.luna.model.adapter.AbstractWXPayProcess;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Calendar;

/**
 * Created by SDLL18 on 16/9/2.
 *
 * @author SDLL18
 */
@Service("wxAppPayProcess")
@Scope(value = "singleton")
public class WXAppPayProcess extends AbstractWXPayProcess {

    @Override
    protected String assembleRequest(String inData, String kind) {
        JSONObject inJSON = JSONObject.parseObject(inData);
        JSONObject sendJSON = new JSONObject();
        sendJSON.put("appid", APP_ID);
        sendJSON.put("attach", inJSON.getString("extraParam"));
        sendJSON.put("body", inJSON.getString("content"));
      //  sendJSON.put("detail", "<![CDATA[" + inJSON.getString("detail") + "]]>");
        sendJSON.put("mch_id", PARTNER);
        sendJSON.put("nonce_str", getRandomStr());
        sendJSON.put("notify_url", inJSON.getString("notifyUrl"));
        sendJSON.put("out_trade_no", inJSON.getString("tradeNo"));
        sendJSON.put("spbill_create_ip", inJSON.getString("userIp"));
        sendJSON.put("total_fee", inJSON.getString("money"));
        sendJSON.put("trade_type", "APP");

        sendJSON.put("sign", getPaySignStrategy().signMessage(sendJSON.toJSONString()));
        return getPayDataParseStrategy().getForTransfer(sendJSON.toString());
    }

    @Override
    protected String assembleSendResponse(String inData) {
        JSONObject object = JSONObject.parseObject(inData);
        JSONObject toReturn = new JSONObject();
        toReturn.put("appid", APP_ID);
        toReturn.put("partnerid", PARTNER);
        toReturn.put("package", "Sign=WXPay");
        toReturn.put("noncestr", getRandomStr());
        toReturn.put("timestamp", Calendar.getInstance().getTimeInMillis() / 1000);
        toReturn.put("prepayid", object.getString("prepay_id"));
        toReturn.put("sign", getPaySignStrategy().signMessage(toReturn.toJSONString()));
        return toReturn.toJSONString();
    }
}
