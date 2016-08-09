package ms.luna.biz.sc;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-22
 */
public interface LunaRoleService {

    public JSONObject getChildRolesByRoleId(int roleId);

    public JSONObject getEditableModuleAndMenu(int loginRoleId, int slaveRoleId);

    public JSONObject updateMenuForRole(int loginRoleId, int slaveRoleId, JSONArray menuArray);

    public JSONObject getAllRoleList(int roleId, int offset, int limit);

    public JSONObject getRoleInfo(int roleId);
}
