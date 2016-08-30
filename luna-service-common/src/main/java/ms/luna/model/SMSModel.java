/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package ms.luna.model;

import ms.luna.model.adapter.DefaultSMSSender;
import ms.luna.model.adapter.SMSSendAdapter;

/**
 * Created by SDLL18 on 16/8/26.
 */
public class SMSModel {

    private String url;

    private String userName;

    private String password;

    private String encode;

    private SMSMessage message;

    private SMSSendAdapter smsSendAdapter;

    public SMSModel() {
        url = "https://sms.yunpian.com/v2/sms/single_send.json";
        userName = "2fc24b5a28afcd4af2209a6f32421a43";
        password = "";
        encode = "UTF-8";
        smsSendAdapter = new DefaultSMSSender();
    }

    public String sendMsg() {
        return smsSendAdapter.sendSMS(this);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public SMSMessage getMessage() {
        return message;
    }

    public void setMessage(SMSMessage message) {
        this.message = message;
    }

    public SMSSendAdapter getSmsSendAdapter() {
        return smsSendAdapter;
    }

    public void setSmsSendAdapter(SMSSendAdapter smsSendAdapter) {
        this.smsSendAdapter = smsSendAdapter;
    }

    @Override
    public String toString() {
        return "SMSModel{" +
                "url='" + url + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", encode='" + encode + '\'' +
                ", message=" + message +
                ", smsSendAdapter=" + smsSendAdapter +
                '}';
    }
}
