package ms.luna.biz.sc.impl;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.dao.custom.LunaRoleDAO;
import ms.luna.biz.dao.custom.LunaRoleMenuDAO;
import ms.luna.biz.dao.model.LunaRole;
import ms.luna.biz.dao.model.LunaRoleCriteria;
import ms.luna.biz.sc.LunaRoleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-22
 */
public class LunaRoleServiceImpl implements LunaRoleService {

    @Autowired
    private LunaRoleDAO lunaRoleDAO;
    @Autowired
    private LunaRoleMenuDAO lunaRoleMenuDAO;


    @Override
    public JSONObject getChildRolesByRoleId(int roleId) {

        LunaRoleCriteria lunaRoleCriteria = new LunaRoleCriteria();
        lunaRoleCriteria.createCriteria().andParentIdEqualTo(roleId);
        List<LunaRole> lunaRoles = lunaRoleDAO.selectByCriteria(lunaRoleCriteria);

        JSONObject jsonObject = new JSONObject();

        return jsonObject;
    }

    @Override
    public JSONObject getEditableModuleAndMenu(int loginRoleId, int slaveRoleId) {
        return null;
    }

    @Override
    public JSONObject updateMenuForRole(int roleId) {
        return null;
    }
}
