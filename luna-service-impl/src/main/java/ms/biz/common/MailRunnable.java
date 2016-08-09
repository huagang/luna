package ms.biz.common;

import ms.luna.biz.util.CreateHtmlUtil;
import ms.luna.biz.util.MailSender;
import ms.luna.biz.util.MsLogger;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-25
 */
public class MailRunnable implements Runnable {

    String HOST = "smtp.exmail.qq.com"; // 服务器
    String PORT = "25"; // 端口号
    String USERNAME = "luna@visualbusiness.com"; // 用户名
    String PASSWORD = "Vb12345"; // 邮箱密码
    String FROMADDRESS = "luna@visualbusiness.com"; // 邮箱名（发件人）
    String SUBJECT = "皓月平台-账户激活"; // 主题
    boolean VALIDATE = true;

    private String toAddress; // 发送地址
    private String token; // 链接token
    private String module_nm; // 业务模块
    private String currentDate; // 当前日期
    private String luna_nm; // 邀请人
    private String role_nm; // 权限名称
    private String webAddr; // 包含域名的web地址

    public MailRunnable(String toAddress, String token, String module_nm, String currentDate, String luna_nm,
                        String role_nm, String webAddr) {
        this.toAddress = toAddress;
        this.token = token;
        this.module_nm = module_nm;
        this.currentDate = currentDate;
        this.luna_nm = luna_nm;
        this.role_nm = role_nm;
        this.webAddr = webAddr;
    }

    @Override
    public void run() {
        try{
            MailSender.MailProperty mail = new MailSender().new MailProperty();

            mail.setMailServerHost(HOST);
            mail.setMailServerPort(PORT);
            mail.setValidate(VALIDATE);
            mail.setUserName(USERNAME);
            mail.setPassword(PASSWORD);
            mail.setFromAddress(FROMADDRESS);
            mail.setSubject(SUBJECT);
            mail.setToAddress(toAddress);
            String content = CreateHtmlUtil.getInstance().convert2EmailHtml(toAddress, token, module_nm, currentDate,
                    luna_nm, role_nm, webAddr);
            mail.setContent(content);

            MailSender send = new MailSender();
            send.sendHtmlMail(mail);
        } catch (Exception e){
            MsLogger.error("邮件发送出现异常，email:" + toAddress + e);
        }
    }
}
