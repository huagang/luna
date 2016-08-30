/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package ms.luna.model;

/**
 * Created by SDLL18 on 16/8/26.
 */
public class MailModel {

    private String host;

    private String port;

    private Boolean validate;

    private String userName;

    private String password;

    private String fromAddress;

    private MailMessage mailMessage;

    private MailSendAdapter mailSendAdapter;

    public MailModel() {
        host = "smtp.exmail.qq.com"; // 服务器
        port = "25"; // 端口号
        userName = "luna@visualbusiness.com"; // 用户名
        password = "Vb12345"; // 邮箱密码
        fromAddress = "luna@visualbusiness.com"; // 邮箱名（发件人）
        validate = true;
        mailSendAdapter = new DefaultMailSender();
    }

    public MailModel(String host, String port, String userName, String password, String fromAddress) {
        this.host = host;
        this.port = port;
        this.userName = userName;
        this.password = password;
        this.fromAddress = fromAddress;
        validate = true;
        mailSendAdapter = new DefaultMailSender();
    }

    public void sendMail() {
        mailSendAdapter.sendMail(this);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Boolean getValidate() {
        return validate;
    }

    public void setValidate(Boolean validate) {
        this.validate = validate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public MailMessage getMailMessage() {
        return mailMessage;
    }

    public void setMailMessage(MailMessage mailMessage) {
        this.mailMessage = mailMessage;
    }

    public MailSendAdapter getMailSendAdapter() {
        return mailSendAdapter;
    }

    public void setMailSendAdapter(MailSendAdapter mailSendAdapter) {
        this.mailSendAdapter = mailSendAdapter;
    }

    @Override
    public String toString() {
        return "MailModel{" +
                "host='" + host + '\'' +
                ", port='" + port + '\'' +
                ", validate=" + validate +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", fromAddress='" + fromAddress + '\'' +
                ", mailMessage=" + mailMessage +
                ", mailSendAdapter=" + mailSendAdapter +
                '}';
    }
}
