package ms.luna.common;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import ms.luna.biz.table.LunaUserRoleTable;
import ms.luna.biz.table.LunaUserTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-08-05
 */
@JSONType
public class LunaUserSession {

    @JSONField(name = LunaUserTable.FIELD_ID)
    private String uniqueId;
    @JSONField(name = LunaUserTable.FIELD_LUNA_NAME)
    private String lunaName;
    @JSONField(name = LunaUserTable.FIELD_NICK_NAME)
    private String nickName;
    @JSONField(name = LunaUserTable.FIELD_EMAIL)
    private String email;
    @JSONField(name = LunaUserRoleTable.FIELD_ROLE_IDS)
    private List<Integer> roleIds;
    @JSONField(name = LunaUserRoleTable.FIELD_EXTRA)
    private Map<String, Object> extra;

    @JSONField(name = "uri_set")
    private Set<String> uriSet;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getLunaName() {
        return lunaName;
    }

    public void setLunaName(String lunaName) {
        this.lunaName = lunaName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public Set<String> getUriSet() {
        return uriSet;
    }

    public void setUriSet(Set<String> uriSet) {
        this.uriSet = uriSet;
    }
}
