package ms.luna.biz.sc.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ms.biz.common.MenuHelper;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.dao.custom.LunaRoleDAO;
import ms.luna.biz.dao.custom.LunaRoleMenuDAO;
import ms.luna.biz.dao.model.*;
import ms.luna.biz.sc.LunaRoleService;
import ms.luna.biz.sc.MenuService;
import ms.luna.biz.table.LunaMenuTable;
import ms.luna.biz.table.LunaModuleTable;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.cache.ModuleMenuCache;
import ms.luna.cache.RoleCache;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-22
 */

@Service("lunaRoleService")
public class LunaRoleServiceImpl implements LunaRoleService {

    private final static Logger logger = Logger.getLogger(LunaRoleServiceImpl.class);

    @Autowired
    private LunaRoleDAO lunaRoleDAO;
    @Autowired
    private LunaRoleMenuDAO lunaRoleMenuDAO;
    @Autowired
    private ModuleMenuCache moduleMenuCache;
    @Autowired
    private RoleCache roleCache;

    /**
     * read role list, only access children role for current user
     * @param roleId
     * @return
     */
    @Override
    public JSONObject getChildRolesByRoleId(int roleId) {
        List<LunaRole> childRoleList = roleCache.getChildRolesByRoleId(roleId);
        return FastJsonUtil.sucess("", JSON.toJSON(childRoleList));
    }

    @Override
    public JSONObject getEditableModuleAndMenu(int loginRoleId, int slaveRoleId) {

        JSONArray jsonArray = new JSONArray();
        try {
            LunaRoleMenuCriteria lunaRoleMenuCriteria = new LunaRoleMenuCriteria();
            lunaRoleMenuCriteria.createCriteria().andRoleIdEqualTo(loginRoleId);
            List<LunaRoleMenu> lunaRoleMenus = lunaRoleMenuDAO.selectByCriteria(lunaRoleMenuCriteria);
            List<Integer> menuIdList = new ArrayList<>();
            for (LunaRoleMenu lunaRoleMenu : lunaRoleMenus) {
                menuIdList.add(lunaRoleMenu.getMenuId());
            }
            List<Pair<LunaModule, List<LunaMenu>>> moduleAndMenuInfoList =
                    moduleMenuCache.getModuleAndMenuByMenuIds(menuIdList);


            lunaRoleMenuCriteria.clear();
            lunaRoleMenuCriteria.createCriteria().andRoleIdEqualTo(slaveRoleId);
            lunaRoleMenus = lunaRoleMenuDAO.selectByCriteria(lunaRoleMenuCriteria);
            Set<Integer> slaveMenuIdSet = new HashSet<>();
            for(LunaRoleMenu lunaRoleMenu : lunaRoleMenus) {
                slaveMenuIdSet.add(lunaRoleMenu.getMenuId());
            }

            for (Pair<LunaModule, List<LunaMenu>> moduleAndMenuInfo : moduleAndMenuInfoList) {
                LunaModule module = moduleAndMenuInfo.getLeft();
                List<LunaMenu> menuList = moduleAndMenuInfo.getRight();
                JSONArray menuArray = new JSONArray();
                for (LunaMenu menu : menuList) {
                    JSONObject menuJson = (JSONObject) JSON.toJSON(menu);
                    if (menu.getUrl() == null) {
                        menuJson.put(LunaMenuTable.FIELD_IS_OUTER, 0);
                        menuJson.put(LunaMenuTable.FIELD_URL, MenuHelper.formatMenuUrl(module, menu));
                    } else {
                        menuJson.put(LunaMenuTable.FIELD_IS_OUTER, 1);
                    }
                    if(slaveMenuIdSet.contains(menu.getId())) {
                        menuJson.put("selected", true);
                    } else {
                        menuJson.put("selected", false);
                    }
                    menuArray.add(menuJson);
                }

                JSONObject moduleJson = new JSONObject();
                moduleJson.put(LunaModuleTable.FIELD_NAME, module.getName());
                moduleJson.put(LunaModuleTable.FIELD_ID, module.getId());
                moduleJson.put("menuArray", menuArray);
                jsonArray.add(moduleJson);
            }
        } catch (Exception ex) {
            logger.error("Failed to get module and menu by roleId", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }

        return FastJsonUtil.sucess("", jsonArray);
    }

    @Override
    public JSONObject updateMenuForRole(int loginRoleId, int slaveRoleId, JSONArray menuArray) {
        try {
            LunaRoleMenuCriteria lunaRoleMenuCriteria = new LunaRoleMenuCriteria();
            lunaRoleMenuCriteria.createCriteria().andRoleIdEqualTo(loginRoleId);
            List<LunaRoleMenu> lunaRoleMenus = lunaRoleMenuDAO.selectByCriteria(lunaRoleMenuCriteria);
            Set<Integer> validMenuIdSet = new HashSet<>(lunaRoleMenus.size());
            for (LunaRoleMenu lunaRoleMenu : lunaRoleMenus) {
                validMenuIdSet.add(lunaRoleMenu.getMenuId());
            }

            for (int i = 0; i < menuArray.size(); i++) {
                int menuId = menuArray.getInteger(i);
                if (!validMenuIdSet.contains(menuId)) {
                    logger.warn(String.format("[%s] do not has auth for menu [%d]", loginRoleId, menuId));
                    return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "没有权限修改");
                }
            }

            lunaRoleMenuCriteria.clear();
            lunaRoleMenuCriteria.createCriteria().andRoleIdEqualTo(slaveRoleId);
            lunaRoleMenuDAO.deleteByCriteria(lunaRoleMenuCriteria);
            /**
             * TODO: should insert in batch
             */
            for (int i = 0; i < menuArray.size(); i++) {
                LunaRoleMenu lunaRoleMenu = new LunaRoleMenu();
                lunaRoleMenu.setRoleId(slaveRoleId);
                lunaRoleMenu.setMenuId(menuArray.getInteger(i));
                lunaRoleMenuDAO.insert(lunaRoleMenu);
            }
        } catch (Exception ex) {
            logger.error("Failed to update menu for role: " + slaveRoleId, ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }

        return FastJsonUtil.sucess("更新权限成功");

    }

    /**
     * 根据权限限制,正常用户只能看到和编辑自己角色之下的角色权限,通过内置root可以编辑所有的角色
     * @return
     */
    @Override
    public JSONObject getAllRoleList(int roleId) {
        if(roleId != 10000) {
            // super role id, hardcode here
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "没有权限");
        }
        LunaRoleCriteria lunaRoleCriteria = new LunaRoleCriteria();
        lunaRoleCriteria.createCriteria();
        try {
            List<LunaRole> lunaRoles = lunaRoleDAO.selectByCriteria(lunaRoleCriteria);
            return FastJsonUtil.sucess("", JSON.toJSON(lunaRoles));
        } catch (Exception ex) {
            logger.error("Failed to get all role list", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
    }
}
