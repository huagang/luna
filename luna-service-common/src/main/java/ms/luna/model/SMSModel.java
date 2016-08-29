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
public class SMSModel {

    private String url;

    private String userName;

    private String password;

    private String encode;

    private SMSMessage message;

    public SMSModel() {
        //TODO fill the detail information
        url = "";
        userName = "";
        password = "";
        encode = "";
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
}
