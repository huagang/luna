/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package ms.luna.schedule.service;

import ms.luna.biz.util.MailSender;
import ms.luna.model.MailMessage;
import ms.luna.model.MailModel;
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by SDLL18 on 16/8/29.
 */
@Service("smsScheduler")
@Scope(value = "singleton")
public class SMSService extends Thread {

    private final static Logger logger = Logger.getLogger(SMSService.class);
    private ExecutorService executorService;
    private BlockingDeque<SMSModel> sendQueue;
    private final static int DEFAULT_QUEUE_SIZE = 1000;
    private final static int DEFAULT_QUEUE_TIME_MILLIS = 50;
    private boolean isStop = false;

    private SMSService() {
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

    public void sendSMS(SMSModel smsModel) {
        try {
            sendQueue.offer(smsModel, DEFAULT_QUEUE_TIME_MILLIS, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            logger.error("Failed to insert email task into queue", e);
        }
    }


    private void doSend(SMSModel smsModel) {
        //TODO SEND SMS MESSAGE

    }

    public void run() {
        while (!isStop) {
            try {
                final SMSModel smsModel = sendQueue.poll(DEFAULT_QUEUE_TIME_MILLIS, TimeUnit.MILLISECONDS);
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        doSend(smsModel);
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

    private static String post(String url, Map<String, String> paramsMap) {
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
                method.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));
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
                response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseText;
    }
}
