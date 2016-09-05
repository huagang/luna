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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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
public abstract class WXPayProcess implements PayProcessAdapter, PaySignStrategy {

    private final static Logger logger = Logger.getLogger(WXPayProcess.class);

    //初始化
    public static String APP_ID = "wxa0c7da25637df906";//微信公众账号应用id
    public static String APP_SECRET = "b10ae7e4197d1c16064ca8d54f7cf94f";//应用对应的凭证
    //应用对应的密钥
    public static String PARTNER = "1253829901";//财付通商户号
    public static String CERT_PASSWD = PARTNER;
    public static String PARTNER_KEY = "1c3O0T3v3s2k0o2S33171s3K2e1I2i0u";//商户号对应的密钥(微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置)
    public static String CERT_FILE = "/data1/data/wxpay/apiclient_cert.p12";
    public static String TOKENURL = "https://api.weixin.qq.com/cgi-bin/token";//获取access_token对应的url
    public static String GATEURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";//获取预支付id的接口url
    public static String REFUNDURL = "https://api.mch.weixin.qq.com/secapi/pay/refund";//获取预支付id的接口url

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
        url.append("&secret");
        url.append(APP_SECRET);
        url.append("&code");
        url.append(code);
        url.append("&grant_type=authorization_code");
        return sendMessage(url.toString(), "");
    }

    @Override
    public Boolean checkSign(String data) throws ParserConfigurationException, SAXException, IOException {
        String sign = getSignFromResponseString(data);
        String signFromWX = getContent(data, "sign");
        if (signFromWX.equals(sign)) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public String sendPayMessage(String data) throws IOException, SAXException, ParserConfigurationException {
        String toSend = assembleRequest(data, "send");
        String receive = sendMessage(GATEURL, toSend);
        if (checkSign(receive)) {
            JSONObject object = getJSONFromXML(receive);
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
        if (checkSign(data)) {
            JSONObject inData = getJSONFromXML(data);
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

    @Override
    public String signMessage(String data) {
        JSONObject json = JSONObject.parseObject(data);
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, Object> entry : json.entrySet()) {
            if (!entry.getValue().equals("")) {
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + PARTNER_KEY;
        // Util.log("Sign Before MD5:" + result);
        result = VbMD5.getCommonMD5Str(result).toUpperCase();
        // Util.log("Sign Result:" + result);
        return result;
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

    private String getContent(String xml, String key) {
        int start = xml.indexOf("<" + key + ">") + 2 + key.length();
        int end = xml.indexOf("</" + key + ">");
        String content = xml.substring(start, end);
        if (content.startsWith("<![CDATA[") && content.endsWith("]]>")) {
            content = content.substring(9, content.length() - 3);
        }
        return content;
    }

    private String getSignFromResponseString(String responseString) throws IOException, SAXException, ParserConfigurationException {
        JSONObject object = getJSONFromXML(responseString);
        // 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
        object.put("sign", "");
        // 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
        return signMessage(object.toJSONString());
    }

    protected final String getXMLFromJSON(JSONObject json) {
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, Object> entry : json.entrySet()) {
            if (!entry.getValue().equals("")) {
                list.add("<" + entry.getKey() + ">" + entry.getValue() + "<" + entry.getKey() + "/>");
            }
        }
        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<xml>");
        for (int i = 0; i < list.size(); i++) {
            xmlBuilder.append(list.get(i));
        }
        xmlBuilder.append("</xml>");
        return xmlBuilder.toString();
    }

    protected final JSONObject getJSONFromXML(String xmlString) throws ParserConfigurationException, IOException, SAXException {
        // 这里用Dom的方式解析回包的最主要目的是防止API新增回包字段
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputStream is = getStringStream(xmlString);
        Document document = builder.parse(is);

        // 获取到document里面的全部结点
        NodeList allNodes = document.getFirstChild().getChildNodes();
        Node node;
        JSONObject jsonObject = new JSONObject();
        int i = 0;
        while (i < allNodes.getLength()) {
            node = allNodes.item(i);
            if (node instanceof Element) {
                jsonObject.put(node.getNodeName(), node.getTextContent());
            }
            i++;
        }
        return jsonObject;
    }

    private InputStream getStringStream(String sInputString) {
        ByteArrayInputStream tInputStringStream = null;
        if (sInputString != null && !sInputString.trim().equals("")) {
            tInputStringStream = new ByteArrayInputStream(sInputString.getBytes());
        }
        return tInputStringStream;
    }


}
