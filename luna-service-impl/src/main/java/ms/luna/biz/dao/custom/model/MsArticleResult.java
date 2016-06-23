package ms.luna.biz.dao.custom.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-06-15
 */
public class MsArticleResult {

    @JSONField(name="id")
    private int id;
    @JSONField(name="title")
    private String title;
    @JSONField(name="author")
    private String author;
    @JSONField(name="status")
    private short status;
    @JSONField(name="column_name")
    private String columnName;
    @JSONField(name="business_name")
    private String businessName;
    @JSONField(name="regist_hhmmss", format="yyyy-MM-dd HH:mm:ss")
    private Date registhhmmss;
    @JSONField(name="up_hhmmss", format="yyyy-MM-dd HH:mm:ss")
    private Date uphhmmss;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Date getRegisthhmmss() {
        return registhhmmss;
    }

    public void setRegisthhmmss(Date registhhmmss) {
        this.registhhmmss = registhhmmss;
    }

    public Date getUphhmmss() {
        return uphhmmss;
    }

    public void setUphhmmss(Date uphhmmss) {
        this.uphhmmss = uphhmmss;
    }
}
