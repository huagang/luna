package ms.luna.biz.dao.custom.model;

import com.alibaba.fastjson.annotation.JSONField;
import ms.luna.biz.table.LunaUserRoleTable;

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

    @JSONField(name= LunaUserRoleTable.FIELD_USER_ID_ALIAS)
    private String userId;
    @JSONField(name = LunaUserRoleTable.FIELD_LUNA_NAME)
    private String lunaName;
    @JSONField(name= LunaUserRoleTable.FIELD_ROLE_ID)
    private int roleId;
    @JSONField(name=LunaUserRoleTable.FIELD_EXTRA)
    private Map<String, Object> extra;
    @JSONField(name=LunaUserRoleTable.FIELD_UPDATE_TIME)
    private Timestamp updateTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLunaName() {
        return lunaName;
    }

    public void setLunaName(String lunaName) {
        this.lunaName = lunaName;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
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
