package ms.luna.common;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;

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

    @JSONField(name = "email")
    private String email;
    @JSONField(name = "unique_id")
    private String uniqueId;
    @JSONField(name = "luna_name")
    private String lunaName;
    @JSONField(name = "nick_name")
    private String nickName;
    @JSONField(name = "role_ids")
    private List<Integer> roleIds;
    @JSONField(name = "extra")
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
