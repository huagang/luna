package ms.luna.biz.sc;

import com.alibaba.fastjson.JSONObject;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-21
 */
public interface MenuService {

    public JSONObject getModuleAndMenuByUserId(String userId);

    public JSONObject getModuleAndMenuByUserName(String userName);

    public JSONObject getModuleAndMenuByRoleId(int roleId);

}
