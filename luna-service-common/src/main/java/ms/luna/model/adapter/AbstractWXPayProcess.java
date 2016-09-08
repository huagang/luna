package ms.luna.model.adapter;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.util.VbMD5;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * 本抽象类中实现初始化 默认策略
 *
 * 实现数据发送,以及所有微信支付公共流程
 *
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
        if (receive == null || receive.equals("") || receive.contains("FAIL")) {
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
            HttpClient httpclient = new DefaultHttpClient();
            // Secure Protocol implementation.
            SSLContext ctx = SSLContext.getInstance("SSL");
            // Implementation of a trust manager for X509 certificates
            X509TrustManager tm = new X509TrustManager() {

                public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {

                }

                public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);

            ClientConnectionManager ccm = httpclient.getConnectionManager();
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", 443, ssf));

            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader(HTTP.CONTENT_TYPE, "text/xml");
            StringEntity se = new StringEntity(data, "utf-8");
            se.setContentType("text/xml");
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_ENCODING, "utf-8"));
            httpPost.setEntity(se);
            String result = "";
            try {
                HttpResponse response = httpclient.execute(httpPost);
                if (response.getStatusLine().getStatusCode() == 200) {
                    result = EntityUtils.toString(response.getEntity(), "utf-8");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            logger.error("Failed to send message to wx 1:", e);
        } catch (Exception ex) {
            logger.error("Failed to send message to wx 2:", ex);
        }
        return "";
    }

    public String getRandomStr() {
        StringBuilder originStr = new StringBuilder();
        for (int i = 1; i <= 12; i++) {
            originStr.append(new Random().nextInt(10));
        }
        return VbMD5.getCommonMD5Str(originStr.toString()).toUpperCase();
    }


}
