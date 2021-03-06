package ms.luna.biz.sc.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ms.biz.common.MenuHelper;
import ms.luna.biz.cons.DbConfig;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.dao.custom.*;
import ms.luna.biz.dao.custom.model.LunaUserRole;
import ms.luna.biz.dao.model.*;
import ms.luna.biz.sc.MenuService;
import ms.luna.biz.table.LunaMenuTable;
import ms.luna.biz.table.LunaModuleTable;
import ms.luna.biz.table.MsMerchantManageTable;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.cache.ModuleMenuCache;
import ms.luna.cache.RoleMenuCache;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-21
 */

@Service("menuService")
public class MenuServiceImpl implements MenuService {

    private static final Logger logger = Logger.getLogger(MenuServiceImpl.class);

    @Autowired
    private LunaUserDAO lunaUserDAO;
    @Autowired
    private LunaUserRoleDAO lunaUserRoleDAO;
    @Autowired
    private LunaRoleMenuDAO lunaRoleMenuDAO;
    @Autowired
    private ModuleMenuCache moduleMenuCache;
    @Autowired
    private RoleMenuCache roleMenuCache;
    @Autowired
    private LunaUserMerchantDAO lunaUserMerchantDAO;
    @Autowired
    private MsMerchantManageDAO msMerchantManageDAO;

    @Override
    public JSONObject getModuleAndMenuByUserId(String userId) {
        try {
            LunaUserRole lunaUserRole = lunaUserRoleDAO.readUserRoleInfo(userId);
            if (lunaUserRole == null) {
                return FastJsonUtil.error(ErrorCode.NOT_FOUND, "用户不存在");
            }
            JSONObject moduleAndMenuByRoleId;
            int roleId = lunaUserRole.getRoleId();
            if(DbConfig.MERCHANT_ROLE_ID_SET.contains(lunaUserRole.getRoleId())) {
                LunaUserMerchant lunaUserMerchant = lunaUserMerchantDAO.selectByPrimaryKey(userId);
                if(lunaUserMerchant != null) {
                    Integer tradeStatus = msMerchantManageDAO.selectMerchantTradeStatus(lunaUserMerchant.getMerchantId());
                    if (tradeStatus.intValue() == MsMerchantManageTable.TRADE_STATUE_ON.intValue()) {
                        moduleAndMenuByRoleId = getModuleAndMenuByRoleId(roleId, DbConfig.INVISIBLE_MENU_TRADE_ON);
                    } else {
                        moduleAndMenuByRoleId = getModuleAndMenuByRoleId(roleId, DbConfig.INVISIBLE_MENU_TRADE_OFF);
                    }
                } else {
                    logger.warn(String.format("merchant info not exist for user[%s], this should not happen", userId));
                    moduleAndMenuByRoleId = getModuleAndMenuByRoleId(lunaUserRole.getRoleId(), DbConfig.INVISIBLE_MENU_TRADE_OFF);
                }
            } else {
                moduleAndMenuByRoleId = getModuleAndMenuByRoleId(lunaUserRole.getRoleId(), new HashSet<Integer>());
            }

            return moduleAndMenuByRoleId;
        } catch (Exception ex) {
            logger.error("Failed to get module and menu by userId: " + userId, ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }

    }

    @Override
    public JSONObject getModuleAndMenuByUserName(String userName) {
        try {
            LunaUserCriteria criteria = new LunaUserCriteria();
            criteria.createCriteria().andLunaNameEqualTo(userName);
            List<LunaUser> lunaUsers = lunaUserDAO.selectByCriteria(criteria);
            if (lunaUsers == null) {
                return FastJsonUtil.error(ErrorCode.NOT_FOUND, "用户不存在");
            }
            LunaUser lunaUser = lunaUsers.get(0);
            String userId = lunaUser.getUniqueId();
            return getModuleAndMenuByUserId(userId);
        } catch (Exception ex) {
            logger.error("Failed to get module and menu by userName", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
    }

    public JSONObject getModuleAndMenuByRoleId(int roleId, Set<Integer> invisibleMenuIdSet) {

        JSONArray jsonArray = new JSONArray();
        try {
            List<LunaRoleMenu> lunaRoleMenus = roleMenuCache.getMenuForRole(roleId);
            List<Integer> menuIdList = new ArrayList<>();
            for (LunaRoleMenu lunaRoleMenu : lunaRoleMenus) {
                menuIdList.add(lunaRoleMenu.getMenuId());
            }
            List<Pair<LunaModule, List<LunaMenu>>> moduleAndMenuInfoList =
                    moduleMenuCache.getModuleAndMenuByMenuIds(menuIdList);

            for (Pair<LunaModule, List<LunaMenu>> moduleAndMenuInfo : moduleAndMenuInfoList) {
                LunaModule module = moduleAndMenuInfo.getLeft();
                List<LunaMenu> menuList = moduleAndMenuInfo.getRight();
                JSONArray menuArray = new JSONArray();
                for (LunaMenu menu : menuList) {
                    JSONObject menuJson = (JSONObject) JSON.toJSON(menu);
                    if(invisibleMenuIdSet.contains(menu.getId())) {
                        menuJson.put(LunaMenuTable.FEILD_VISIBLE, false);
                    } else {
                        menuJson.put(LunaMenuTable.FEILD_VISIBLE, true);
                    }
                    if (StringUtils.isBlank(menu.getUrl())) {
                        menuJson.put(LunaMenuTable.FIELD_IS_OUTER, 0);
                    } else {
                        menuJson.put(LunaMenuTable.FIELD_IS_OUTER, 1);
                        menuJson.put(LunaMenuTable.FIELD_OUTER_URL, menu.getUrl());
                    }
                    menuJson.put(LunaMenuTable.FIELD_URL, MenuHelper.formatMenuUrl(module, menu));
                    menuArray.add(menuJson);
                }

                JSONObject moduleJson = new JSONObject();
                moduleJson.put(LunaModuleTable.FIELD_NAME, module.getName());
                moduleJson.put(LunaModuleTable.FIELD_ID, module.getId());
                moduleJson.put(LunaModuleTable.FIELD_CODE, module.getCode());
                moduleJson.put("menuArray", menuArray);
                jsonArray.add(moduleJson);
            }
        } catch (Exception ex) {
            logger.error("Failed to get module and menu by roleId", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
        logger.debug(jsonArray);
        return FastJsonUtil.sucess("success", jsonArray);

    }

    @Override
    public JSONObject getModuleAndMenuByRoleId(int roleId) {
        JSONArray jsonArray = new JSONArray();
        try {
            List<LunaRoleMenu> lunaRoleMenus = roleMenuCache.getMenuForRole(roleId);
            List<Integer> menuIdList = new ArrayList<>();
            for (LunaRoleMenu lunaRoleMenu : lunaRoleMenus) {
                menuIdList.add(lunaRoleMenu.getMenuId());
            }
            List<Pair<LunaModule, List<LunaMenu>>> moduleAndMenuInfoList =
                    moduleMenuCache.getModuleAndMenuByMenuIds(menuIdList);

            for (Pair<LunaModule, List<LunaMenu>> moduleAndMenuInfo : moduleAndMenuInfoList) {
                LunaModule module = moduleAndMenuInfo.getLeft();
                List<LunaMenu> menuList = moduleAndMenuInfo.getRight();
                JSONArray menuArray = new JSONArray();
                for (LunaMenu menu : menuList) {
                    JSONObject menuJson = (JSONObject) JSON.toJSON(menu);
                    if (StringUtils.isBlank(menu.getUrl())) {
                        menuJson.put(LunaMenuTable.FIELD_IS_OUTER, 0);
                    } else {
                        menuJson.put(LunaMenuTable.FIELD_IS_OUTER, 1);
                        menuJson.put(LunaMenuTable.FIELD_OUTER_URL, menu.getUrl());
                    }
                    menuJson.put(LunaMenuTable.FIELD_URL, MenuHelper.formatMenuUrl(module, menu));
                    menuArray.add(menuJson);
                }

                JSONObject moduleJson = new JSONObject();
                moduleJson.put(LunaModuleTable.FIELD_NAME, module.getName());
                moduleJson.put(LunaModuleTable.FIELD_ID, module.getId());
                moduleJson.put(LunaModuleTable.FIELD_CODE, module.getCode());
                moduleJson.put("menuArray", menuArray);
                jsonArray.add(moduleJson);
            }
        } catch (Exception ex) {
            logger.error("Failed to get module and menu by roleId", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }

        return FastJsonUtil.sucess("success", jsonArray);
    }

}
