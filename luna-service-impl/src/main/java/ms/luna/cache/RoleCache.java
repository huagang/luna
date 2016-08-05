package ms.luna.cache;

import ms.luna.biz.dao.custom.LunaRoleDAO;
import ms.luna.biz.dao.model.LunaRole;
import ms.luna.biz.dao.model.LunaRoleCriteria;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-25
 */
@Repository("roleCache")
public class RoleCache {

    /**
     * TODO: not implement cache now, do it later
     */

    private final static Logger logger = Logger.getLogger(RoleCache.class);

    @Autowired
    private LunaRoleDAO lunaRoleDAO;

    public List<LunaRole> getChildRolesByRoleId(int roleId) {
        List<LunaRole> childRoleList = new ArrayList<>();
        List<Integer> roleList = Arrays.asList(roleId);
        try {
            while (true) {
                LunaRoleCriteria lunaRoleCriteria = new LunaRoleCriteria();
                lunaRoleCriteria.createCriteria().andParentIdIn(roleList);
                List<LunaRole> lunaRoles = lunaRoleDAO.selectByCriteria(lunaRoleCriteria);
                if (lunaRoles == null || lunaRoles.size() == 0) {
                    break;
                }
                childRoleList.addAll(lunaRoles);
                roleList.clear();
                for (LunaRole lunaRole : lunaRoles) {
                    roleList.add(lunaRole.getId());
                }
            }
        } catch (Exception ex) {
            logger.error("Failed to get child role by roleId: " + roleId, ex);
        }
        return childRoleList;
    }

    public LunaRole getRoleByRoleId(int roleId) {
        return lunaRoleDAO.selectByPrimaryKey(roleId);
    }
}
