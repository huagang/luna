package ms.luna.biz.dao.custom.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-21
 */
public class LunaUserRole {

    @JSONField(name="unique_id")
    private String userId;
    @JSONField(name="role_ids")
    private List<Integer> roleIds;
    @JSONField(name="extra")
    private Map<String, Object> extra;
    @JSONField(name="update_time")
    private Timestamp updateTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    public Map<String, Object> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, Object> extra) {
        this.extra = extra;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
