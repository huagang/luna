/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package ms.luna.model.adapter;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.util.VbMD5;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * Created by SDLL18 on 16/9/2.
 *
 * @author SDLL18
 */
public abstract class AbstractWXPayProcess implements PayProcess {

    private final static Logger logger = Logger.getLogger(AbstractWXPayProcess.class);

    //初始化
    public static String APP_ID = "wxf8884f6b1d84257d";//微信公众账号应用id
    public static String APP_SECRET = "ca19624bd5dc7c7937b229411d8eca18";//应用对应的凭证
    //应用对应的密钥
    public static String PARTNER = "1332993401";//财付通商户号
    public static String CERT_PASSWD = PARTNER;
    public static String CERT_FILE = "/data1/data/wxpay/apiclient_cert.p12";
    public static String TOKENURL = "https://api.weixin.qq.com/cgi-bin/token";//获取access_token对应的url
    public static String GATEURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";//获取预支付id的接口url
    public static String REFUNDURL = "https://api.mch.weixin.qq.com/secapi/pay/refund";//获取预支付id的接口url

    private PaySignStrategy paySignStrategy;

    private PayDataParseStrategy payDataParseStrategy;

    public AbstractWXPayProcess() {
        paySignStrategy = new WXPaySignStrategy();
        payDataParseStrategy = new WXPayDataParseStrategy();
    }

    public AbstractWXPayProcess(PaySignStrategy strategy, PayDataParseStrategy strategy2) {
        paySignStrategy = strategy;
        payDataParseStrategy = strategy2;
    }

    public PaySignStrategy getPaySignStrategy() {
        return paySignStrategy;
    }

    public void setPaySignStrategy(PaySignStrategy paySignStrategy) {
        this.paySignStrategy = paySignStrategy;
    }

    public PayDataParseStrategy getPayDataParseStrategy() {
        return payDataParseStrategy;
    }

    public void setPayDataParseStrategy(PayDataParseStrategy payDataParseStrategy) {
        this.payDataParseStrategy = payDataParseStrategy;
    }

    protected abstract String assembleRequest(String inData, String kind);

    protected abstract String assembleSendResponse(String inData);

    protected String assembleNotifyResponse(String inData) {
        JSONObject object = JSONObject.parseObject(inData);
        JSONObject toReturn = new JSONObject();
        toReturn.put("timeEnd", object.getString("time_end"));
        toReturn.put("attach", object.getString("attach"));
        toReturn.put("tradeNo", object.getString("out_trade_no"));
        toReturn.put("transactionId", object.getString("transaction_id"));
        toReturn.put("totalFee", object.getString("total_fee"));
        toReturn.put("tradeType", object.getString("trade_type"));
        toReturn.put("bankType", object.getString("bank_type"));
        return toReturn.toJSONString();
    }

    public static String getOpenId(String code) {
        StringBuilder url = new StringBuilder("https://api.weixin.qq.com/sns/oauth2/access_token?");
        url.append("appid=");
        url.append(APP_ID);
        url.append("&secret=");
        url.append(APP_SECRET);
        url.append("&code=");
        url.append(code);
        url.append("&grant_type=authorization_code");
        return sendMessage(url.toString(), "");
    }


    @Override
    public String sendPayMessage(String data) throws IOException, SAXException, ParserConfigurationException {
        String toSend = assembleRequest(data, "send");
        logger.info("send to wx: " + toSend);
        String receive = sendMessage(GATEURL, toSend);
        logger.info("receive from wx pay: " + receive);
        if (receive.contains("FAIL")) {
            logger.error("get xml error info from WX when send message to Wx.\nInfo:\n" + receive);
            return null;
        }
        if (paySignStrategy.checkSign(receive)) {
            JSONObject object = JSONObject.parseObject(payDataParseStrategy.getFromTransfer(receive));
            if (object.getString("return_code").equals("SUCCESS")) {
                if (object.getString("result_code").equals("SUCCESS")) {
                    return assembleSendResponse(object.toJSONString());
                } else {
                    logger.error("get business error info from WX when send message to Wx.\nInfo:\n" + object.getString("err_code"));
                    return null;
                }
            } else {
                logger.error("get status error info from WX when send message to Wx.\nInfo:\n" + object.getString("return_msg"));
                return null;
            }
        } else {
            logger.error("Check WX message sign failed when send message to Wx.\nInfo:\n" + receive);
            return null;
        }
    }

    /**
     * @param data
     * @return
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    @Override
    public String parseNotificationMessage(String data) throws IOException, SAXException, ParserConfigurationException {
        if (paySignStrategy.checkSign(data)) {
            JSONObject inData = JSONObject.parseObject(payDataParseStrategy.getFromTransfer(data));
            if (inData.getString("return_code").equals("SUCCESS")) {
                if (inData.getString("result_code").equals("SUCCESS")) {
                    return assembleNotifyResponse(inData.toJSONString());
                } else {
                    logger.error("get business error info from WX when parse notification message from Wx.\nInfo:\n" + inData.getString("err_code"));
                    return null;
                }
            } else {
                logger.error("get status error info from WX when parse notification message from Wx.\nInfo:\n" + inData.getString("return_msg"));
                return null;
            }
        } else {
            logger.error("Check WX message sign failed when parse notification message from Wx.\nInfo:\n" + data);
            return null;
        }
    }

    public static String sendMessage(String url, String data) {
        try {
            URL urlInstance = new URL(url);
            URLConnection con = urlInstance.openConnection();

            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setConnectTimeout(1000 * 10);
            con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows XP)");
            con.setRequestProperty("Content-Type", "text/xml");
            con.setRequestProperty("Content-length", String.valueOf(data.getBytes().length));

            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.write(data.getBytes());
            out.flush();
            out.close();

            DataInputStream in = new DataInputStream(con.getInputStream());
            byte[] result;
            java.io.ByteArrayOutputStream ou = new java.io.ByteArrayOutputStream();
            byte[] bufferByte = new byte[256];
            int l = -1;
            while ((l = in.read(bufferByte, 0, 256)) > -1) {
                ou.write(bufferByte, 0, l);
                ou.flush();
            }
            result = ou.toByteArray();
            String strResult = new String(result, "UTF-8");
            return strResult;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getRandomStr() {
        StringBuilder originStr = new StringBuilder();
        for (int i = 1; i <= 12; i++) {
            originStr.append(new Random().nextInt(10));
        }
        return VbMD5.getCommonMD5Str(originStr.toString()).toUpperCase();
    }


}
