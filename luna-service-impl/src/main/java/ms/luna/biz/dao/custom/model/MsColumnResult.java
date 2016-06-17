package ms.luna.biz.dao.custom.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-06-15
 */
public class MsColumnResult {

    @JSONField(name="id")
    private int id;
    @JSONField(name="name")
    private String name;
    @JSONField(name="code")
    private String code;
    @JSONField(name="category_name")
    private String categoryName;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
