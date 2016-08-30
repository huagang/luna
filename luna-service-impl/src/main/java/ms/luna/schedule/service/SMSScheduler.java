/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package ms.luna.schedule.service;

import com.alibaba.fastjson.JSONObject;
import ms.luna.model.SMSMessage;
import ms.luna.model.SMSModel;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by SDLL18 on 16/8/29.
 */
@Service("smsScheduler")
@Scope(value = "singleton")
public class SMSScheduler extends Thread {

    private final static Logger logger = Logger.getLogger(SMSScheduler.class);
    private ExecutorService executorService;
    private BlockingDeque<SMSModel> sendQueue;
    private final static int DEFAULT_QUEUE_SIZE = 1000;
    private final static int DEFAULT_QUEUE_TIME_MILLIS = 50;
    private boolean isStop = false;

    private SMSScheduler() {
        executorService = Executors.newCachedThreadPool();
        sendQueue = new LinkedBlockingDeque<>(DEFAULT_QUEUE_SIZE);
        this.setName("thread-sms-service");
        this.setDaemon(true);
    }


    public void sendSMS(SMSMessage message) {
        SMSModel smsModel = new SMSModel();
        smsModel.setMessage(message);
        sendSMS(smsModel);
    }

    public static void main(String[] args) {
        SMSModel smsModel = new SMSModel();
        SMSMessage message = new SMSMessage();
        message.setToPhoneNumber("15659831720");
        message.setContent("【微景皓月】您的验证码是098766");
        smsModel.setMessage(message);
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", smsModel.getUserName());
        params.put("text", smsModel.getMessage().getContent());
        params.put("mobile", smsModel.getMessage().getToPhoneNumber());
        System.out.println(post(smsModel.getUrl(), smsModel.getEncode(), params));
    }

    public void sendSMS(SMSModel smsModel) {
        try {
            sendQueue.offer(smsModel, DEFAULT_QUEUE_TIME_MILLIS, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            logger.error("Failed to insert email task into queue", e);
        }
    }


    private String doSend(SMSModel smsModel) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", smsModel.getUserName());
        params.put("text", smsModel.getMessage().getContent());
        params.put("mobile", smsModel.getMessage().getToPhoneNumber());
        String result = post(smsModel.getUrl(), smsModel.getEncode(), params);
        return result;
    }

    public void run() {
        while (!isStop) {
            try {
                final SMSModel smsModel = sendQueue.poll(DEFAULT_QUEUE_TIME_MILLIS, TimeUnit.MILLISECONDS);
                if (smsModel == null) continue;
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        String result = doSend(smsModel);
                        JSONObject object = JSONObject.parseObject(result);
                        if (object.getInteger("code").intValue() != 0) {
                            logger.error("Send SMS failed. Detail:\n" + result);
                        } else {
                            logger.info("SMS message sended successfully. Info:\n" + result);
                        }
                    }
                });
            } catch (InterruptedException e) {
                logger.error("Failed to take task", e);
            }
        }
    }

    public void shutdown() {
        isStop = true;
        if (executorService != null) {
            executorService.shutdownNow();
        }
    }

    private static String post(String url, String encode, Map<String, String> paramsMap) {
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            HttpPost method = new HttpPost(url);
            if (paramsMap != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
                    paramList.add(pair);
                }
                method.setEntity(new UrlEncodedFormEntity(paramList, encode));
            }
            response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseText;
    }
}
