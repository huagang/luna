package ms.luna.biz.sc;

import com.alibaba.fastjson.JSONObject;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-22
 */
public interface LunaUserService {

    JSONObject getUserList(JSONObject jsonObject);

    JSONObject inviteUser(JSONObject jsonObject);

    JSONObject getUserInfo(String userId);

    JSONObject getChildRoleAndModuleByUserId(String userId);

    JSONObject getChildRoleAndModuleByRoleId(int roleId);

    JSONObject createUser(JSONObject jsonObject);

    JSONObject deleteUser(String userId);

    JSONObject updateUserRole(JSONObject jsonObject);

    JSONObject updateUserInfo(JSONObject jsonObject);

}
