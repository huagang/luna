package ms.luna.schedule.service;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.util.CreateHtmlUtil;
import ms.luna.biz.util.MailSender;
import ms.luna.model.MailMessage;
import ms.luna.model.MailModel;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-21
 */

@Service("emailScheduler")
@Scope(value = "singleton")
public class EmailService extends Thread {

    private final static Logger logger = Logger.getLogger(EmailService.class);
    private ExecutorService executorService;
    private BlockingDeque<MailModel> sendQueue;
    private BlockingQueue<Runnable> queue;
    private final static int DEFAULT_QUEUE_SIZE = 1000;
    private final static int DEFAULT_QUEUE_TIME_MILLIS = 50;
    private boolean isStop = false;

    private EmailService() {
        executorService = Executors.newCachedThreadPool();
        queue = new LinkedBlockingQueue<>(DEFAULT_QUEUE_SIZE);
        sendQueue = new LinkedBlockingDeque<>(DEFAULT_QUEUE_SIZE);
        this.setName("thread-email-service");
        this.setDaemon(true);
    }

    /**
     * old interface
     * don't use anymore
     *
     * @param runnable
     */
    public void sendEmail(Runnable runnable) {
        try {
            queue.offer(runnable, DEFAULT_QUEUE_TIME_MILLIS, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            logger.error("Failed to insert email task into queue", e);
        }
    }

    public void sendEmail(MailMessage message) {
        MailModel mailModel = new MailModel();
        mailModel.setMailMessage(message);
        sendEmail(mailModel);
    }

    public void sendEmail(MailModel mailModel) {
        try {
            sendQueue.offer(mailModel, DEFAULT_QUEUE_TIME_MILLIS, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            logger.error("Failed to insert email task into queue", e);
        }
    }

    public void run() {
        while (!isStop) {
            try {
                Runnable runnable = queue.poll(DEFAULT_QUEUE_TIME_MILLIS, TimeUnit.MILLISECONDS);
                if (runnable != null) {
                    executorService.submit(runnable);
                }
                final MailModel mailModel = sendQueue.poll(DEFAULT_QUEUE_TIME_MILLIS, TimeUnit.MILLISECONDS);
                if (mailModel == null) continue;
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        mailModel.sendMail();
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

}
