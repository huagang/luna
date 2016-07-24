package ms.luna.biz.dao.custom;

import ms.luna.biz.dao.custom.model.LunaUserRole;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-21
 */
public interface LunaUserRoleDAO {

    public LunaUserRole readUserRoleInfo(String userId);

    public void createUserRoleInfo(LunaUserRole lunaUserRole);

    public void updateUserRoleInfo(LunaUserRole lunaUserRole);

    public void deleteUserRoleInfoByUserId(String userId);
}
