/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package ms.luna.model;

import sun.applet.Main;

/**
 * Created by SDLL18 on 16/8/26.
 */
public class MailMessage {


    private String subject;

    private String toAddress;

    private String content;

    public MailMessage(){
        subject = "";
        toAddress = "";
        content = "";
    }

    public MailMessage(String toAddress,String subject) {
        this.toAddress = toAddress;
        this.subject = subject;
        this.content = "";
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
