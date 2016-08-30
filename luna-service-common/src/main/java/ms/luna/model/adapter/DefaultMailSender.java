/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package ms.luna.model.adapter;

import ms.luna.biz.util.MailSender;
import ms.luna.model.MailModel;

/**
 * Created by SDLL18 on 16/8/30.
 */
public class DefaultMailSender implements  MailSendAdapter{
    @Override
    public void sendMail(MailModel mailModel) {
        MailSender.MailProperty mail = new MailSender().new MailProperty();
        mail.setMailServerHost(mailModel.getHost());
        mail.setMailServerPort(mailModel.getPort());
        mail.setValidate(mailModel.getValidate());
        mail.setUserName(mailModel.getUserName());
        mail.setPassword(mailModel.getPassword());
        mail.setFromAddress(mailModel.getFromAddress());
        mail.setSubject(mailModel.getMailMessage().getSubject());
        mail.setToAddress(mailModel.getMailMessage().getToAddress());
        mail.setContent(mailModel.getMailMessage().getContent());
        MailSender send = new MailSender();
        send.sendHtmlMail(mail);
    }
}
