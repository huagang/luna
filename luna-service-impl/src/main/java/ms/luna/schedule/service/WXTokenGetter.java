package ms.luna.schedule.service;

import com.alibaba.fastjson.JSONObject;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: lee@visualbusiness.com
 * @Data: 2016-09-07
 */
public class WXTokenGetter {

    private static ConcurrentHashMap<String, WXTokenGetter> getterMap = new ConcurrentHashMap<>();

    /**
     * 根据微信支付默认服务号信息获取
     *
     * @return
     */
    public static WXTokenGetter getSingleInstance() {
        if (!getterMap.containsKey("wxf8884f6b1d84257d|ca19624bd5dc7c7937b229411d8eca18")) {
            WXTokenGetter getter = new WXTokenGetter();
            getterMap.putIfAbsent("wxf8884f6b1d84257d|ca19624bd5dc7c7937b229411d8eca18", getter);
        }
        return getterMap.get("wxf8884f6b1d84257d|ca19624bd5dc7c7937b229411d8eca18");
    }

    /**
     * 指定微信公众号/服务号信息获取
     *
     * @param appId
     * @param appSecret
     * @return
     */
    public static WXTokenGetter getSingleInstance(String appId, String appSecret) {
        if (!getterMap.containsKey(appId + "|" + appSecret)) {
            synchronized (getterMap) {
                WXTokenGetter getter = new WXTokenGetter(appId, appSecret);
                getterMap.put(appId + "|" + appSecret, getter);
            }
        }
        return getterMap.get(appId + "|" + appSecret);
    }

    private final static Logger logger = Logger.getLogger(WXTokenGetter.class);

    private String token;

    private Long tokenLastUpdateTime;

    private String appId;

    private String appSecret;

    private String ticket;

    private Long ticketLastUpdateTime;

    private WXTokenGetter() {
        token = "";
        tokenLastUpdateTime = 0L;
        ticket = "";
        ticketLastUpdateTime = 0L;
        appId = "wxf8884f6b1d84257d";
        appSecret = "ca19624bd5dc7c7937b229411d8eca18";
    }

    private WXTokenGetter(String appId, String appSecret) {
        token = "";
        tokenLastUpdateTime = 0L;
        ticket = "";
        ticketLastUpdateTime = 0L;
        this.appId = appId;
        this.appSecret = appSecret;
    }

    public String getToken() {
        if (token.equals("")) {
            synchronized (this) {
                getNewToken();
            }
        } else if (Calendar.getInstance().getTimeInMillis() - tokenLastUpdateTime > 100 * 60 * 1000) {
            synchronized (this) {
                getNewToken();
            }
        }
        return token;
    }

    public String getTicket() {
        if (ticket.equals("")) {
            synchronized (this) {
                getNewTicket();
            }
        } else if (Calendar.getInstance().getTimeInMillis() - ticketLastUpdateTime > 100 * 60 * 1000) {
            synchronized (this) {
                getNewTicket();
            }
        }
        return ticket;
    }

    private void getNewTicket() {
        try {
            HttpClient httpclient = new DefaultHttpClient();
            SSLContext ctx = SSLContext.getInstance("SSL");
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
            StringBuilder urlBuilder = new StringBuilder("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=");
            urlBuilder.append(getToken());
            urlBuilder.append("&type=jsapi");
            HttpGet httpget = new HttpGet(urlBuilder.toString());

            ResponseHandler responseHandler = new BasicResponseHandler();
            String responseBody;
            responseBody = (String) httpclient.execute(httpget, responseHandler);
            logger.info("receive from wx when get ticket:\n" + responseBody);

            JSONObject data = JSONObject.parseObject(responseBody);
            if (data.containsKey("errcode")) {
                logger.error("failed to get token from wx.\nInfo:" + data);
            } else {
                ticket = data.getString("ticket");
                ticketLastUpdateTime = Calendar.getInstance().getTimeInMillis();
            }
        } catch (NoSuchAlgorithmException e) {
            logger.error("failed to get token from wx.", e);
        } catch (ClientProtocolException e) {
            logger.error("failed to get token from wx.", e);
        } catch (IOException e) {
            logger.error("failed to get token from wx.", e);
        } catch (Exception ex) {
            logger.error("failed to get token from wx.", ex);
        }
    }

    private void getNewToken() {
        try {
            HttpClient httpclient = new DefaultHttpClient();
            SSLContext ctx = SSLContext.getInstance("SSL");
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
            StringBuilder urlBuilder = new StringBuilder("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=");
            urlBuilder.append(appId);
            urlBuilder.append("&secret=");
            urlBuilder.append(appSecret);
            HttpGet httpget = new HttpGet(urlBuilder.toString());
            ResponseHandler responseHandler = new BasicResponseHandler();
            String responseBody;
            responseBody = (String) httpclient.execute(httpget, responseHandler);
            logger.info("receive from wx when get token:\n" + responseBody);
            JSONObject data = JSONObject.parseObject(responseBody);
            if (data.containsKey("errcode")) {
                logger.error("failed to get token from wx.\nInfo:" + data);
            } else {
                token = data.getString("access_token");
                tokenLastUpdateTime = Calendar.getInstance().getTimeInMillis();
            }
        } catch (NoSuchAlgorithmException e) {
            logger.error("failed to get token from wx.", e);
        } catch (ClientProtocolException e) {
            logger.error("failed to get token from wx.", e);
        } catch (IOException e) {
            logger.error("failed to get token from wx.", e);
        } catch (Exception ex) {
            logger.error("failed to get token from wx.", ex);
        }
    }
}
