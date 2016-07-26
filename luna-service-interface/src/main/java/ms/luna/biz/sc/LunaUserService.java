package ms.luna.biz.sc;

import com.alibaba.fastjson.JSONObject;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-22
 */
public interface LunaUserService {

    JSONObject getUserList(int roleId, String query, int start, int limit);

    JSONObject inviteUser(String loginUserId, JSONObject jsonObject);

    JSONObject getUserRoleForEdit(int loginRole, String slaveUserId);

    JSONObject getChildRoleAndModuleByUserId(String userId);

    JSONObject getChildRoleAndModuleByRoleId(int roleId);

    JSONObject registerUser(JSONObject jsonObject);

    JSONObject loginUser(JSONObject jsonObject);

    JSONObject deleteUser(String userId);

    JSONObject updateUserRole(JSONObject jsonObject);

    JSONObject updateUserInfo(JSONObject jsonObject);

    JSONObject isTokenValid(String token);

}
