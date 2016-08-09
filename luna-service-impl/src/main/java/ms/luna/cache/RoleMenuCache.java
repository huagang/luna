package ms.luna.cache;

import ms.luna.biz.dao.custom.LunaRoleMenuDAO;
import ms.luna.biz.dao.model.LunaRoleMenu;
import ms.luna.biz.dao.model.LunaRoleMenuCriteria;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-25
 */
@Repository("roleMenuCache")
public class RoleMenuCache {

    private final static Logger logger = Logger.getLogger(RoleMenuCache.class);

    @Autowired
    private LunaRoleMenuDAO lunaRoleMenuDAO;

    public List<LunaRoleMenu> getMenuForRole(int roleId) {
        LunaRoleMenuCriteria lunaRoleMenuCriteria = new LunaRoleMenuCriteria();
        lunaRoleMenuCriteria.createCriteria().andRoleIdEqualTo(roleId);
        List<LunaRoleMenu> lunaRoleMenuList = lunaRoleMenuDAO.selectByCriteria(lunaRoleMenuCriteria);
        return lunaRoleMenuList;
    }


}
