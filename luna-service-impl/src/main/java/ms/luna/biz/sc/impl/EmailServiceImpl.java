/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package ms.luna.biz.sc.impl;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.sc.EmailService;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.model.MailMessage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by SDLL18 on 16/8/29.
 */
@Service("emailService")
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = Logger.getLogger(SMSServiceImpl.class);

    @Autowired
    private ms.luna.schedule.service.EmailService emailService;

    @Override
    public JSONObject sendMessage(JSONObject jsonObject) {
        try {
            String content = jsonObject.getString("content");
            String toAddress = jsonObject.getString("toAddress");
            String subject = jsonObject.getString("subject");
            MailMessage message = new MailMessage();
            message.setContent(content);
            message.setToAddress(toAddress);
            message.setSubject(subject);
            emailService.sendEmail(message);
            return FastJsonUtil.sucess("success");
        } catch (Exception e) {
            logger.error("Failed to send Email.", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
    }
}
