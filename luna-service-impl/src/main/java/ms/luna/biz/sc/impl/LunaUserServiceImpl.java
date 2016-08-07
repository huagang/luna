package ms.luna.biz.sc.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import ms.biz.common.MailRunnable;
import ms.biz.common.MenuHelper;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.dao.custom.*;
import ms.luna.biz.dao.custom.model.LunaUserRole;
import ms.luna.biz.dao.model.*;
import ms.luna.biz.sc.LunaUserService;
import ms.luna.biz.table.LunaRoleCategoryTable;
import ms.luna.biz.table.LunaRoleTable;
import ms.luna.biz.table.LunaUserRoleTable;
import ms.luna.biz.table.LunaUserTable;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.VbMD5;
import ms.luna.cache.ModuleMenuCache;
import ms.luna.cache.RoleCache;
import ms.luna.cache.RoleCategoryCache;
import ms.luna.common.LunaUserSession;
import ms.luna.schedule.service.EmailService;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-22
 */

@Service("lunaUserService")
public class LunaUserServiceImpl implements LunaUserService {

    private final static Logger logger = Logger.getLogger(LunaUserServiceImpl.class);
    @Autowired
    private LunaUserDAO lunaUserDAO;
    @Autowired
    private LunaRoleDAO lunaRoleDAO;
    @Autowired
    private LunaUserRoleDAO lunaUserRoleDAO;
    @Autowired
    private LunaRoleMenuDAO lunaRoleMenuDAO;
    @Autowired
    private ModuleMenuCache moduleMenuCache;
    @Autowired
    private LunaRegEmailDAO lunaRegEmailDAO;
    @Autowired
    private EmailService emailService;
    @Autowired
    private RoleCache roleCache;
    @Autowired
    private RoleCategoryCache roleCategoryCache;

    @Override
    public JSONObject getUserList(int roleId, String query, int start, int limit) {
        List<Integer> childRoleIdList = new ArrayList<>();
        // 当前角色不能设定自己角色的权限,但可以管理自己角色下的用户
        childRoleIdList.add(roleId);
        List<Integer> roleList = Lists.newArrayList(roleId);
        try {
            while (true) {
                LunaRoleCriteria lunaRoleCriteria = new LunaRoleCriteria();
                lunaRoleCriteria.createCriteria().andParentIdIn(roleList);
                List<LunaRole> lunaRoles = lunaRoleDAO.selectByCriteria(lunaRoleCriteria);
                if (lunaRoles == null || lunaRoles.size() == 0) {
                    break;
                }
                roleList.clear();
                for (LunaRole lunaRole : lunaRoles) {
                    roleList.add(lunaRole.getId());
                    childRoleIdList.add(lunaRole.getId());
                }
            }
            List<LunaUserRole> lunaUserRoleList = lunaUserRoleDAO.readUserInfoByRole(childRoleIdList, query, start, limit);
            JSONObject data = new JSONObject();
            int total = lunaUserRoleDAO.countUserByRole(childRoleIdList, query);
            JSONArray jsonArray = new JSONArray();

            Map<Integer, String> allRoleId2Name = roleCache.getAllRoleId2Name();

            for(LunaUserRole lunaUserRole : lunaUserRoleList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(LunaUserTable.FIELD_ID, lunaUserRole.getUserId());
                jsonObject.put(LunaUserTable.FIELD_LUNA_NAME, lunaUserRole.getLunaName());
                jsonObject.put("role_name", allRoleId2Name.get(lunaUserRole.getRoleId()));
                jsonArray.add(jsonObject);
            }

            data.put("rows", jsonArray);
            data.put("total", total);

            return FastJsonUtil.sucess("", data);
        } catch (Exception ex) {
            logger.error("Failed to get child role by roleId: " + roleId, ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
    }

    @Override
    public JSONObject inviteUser(String loginUserId, JSONObject jsonObject) {
        String[] emailArray = jsonObject.getString("emails").split(",|,");
        int roleId = jsonObject.getInteger("role_id");
        int categoryId = jsonObject.getInteger("category_id");
        String webAddr = jsonObject.getString("webAddr");

        String extra = jsonObject.getString(LunaRoleCategoryTable.FIELD_EXTRA);

        Set<String> emailSet = Sets.newHashSet(emailArray);

        LunaUserCriteria lunaUserCriteria = new LunaUserCriteria();
        lunaUserCriteria.createCriteria().andEmailIn(Lists.newArrayList(emailSet));

        List<LunaUser> lunaUserList = lunaUserDAO.selectByCriteria(lunaUserCriteria);
        Set<String> existEmailSet = null;
        Map<String, String> toInviteEmail = new HashMap<>();
        if(lunaUserList != null && lunaUserList.size() > 0) {
            existEmailSet = new HashSet<>(lunaUserList.size());
            for (LunaUser lunaUser : lunaUserList) {
                existEmailSet.add(lunaUser.getEmail());
            }

            for(String email : emailSet) {
                if(! existEmailSet.contains(email)) {
                    String token = UUID.randomUUID().toString().replace("-", "");
                    toInviteEmail.put(email, token);
                }
            }
        }

        if(toInviteEmail.size() < 1) {
            logger.warn("All invited emails have already registered");
            return FastJsonUtil.error(ErrorCode.ALREADY_EXIST, "邀请的用户已经注册");
        }

        LunaRegEmailCriteria lunaRegEmailCriteria = new LunaRegEmailCriteria();
        lunaRegEmailCriteria.createCriteria().andEmailIn(Lists.newArrayList(toInviteEmail.keySet()));
        lunaRegEmailDAO.deleteByCriteria(lunaRegEmailCriteria);

        // 获得当前日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String currentDate = sdf.format(new Date());
        LunaUser lunaUser = lunaUserDAO.selectByPrimaryKey(loginUserId);
        LunaRole lunaRole = lunaRoleDAO.selectByPrimaryKey(roleId);
        String categoryName = roleCategoryCache.getCategoryNameById(categoryId);

        for (Map.Entry<String, String> entry : toInviteEmail.entrySet()) {
            String email = entry.getKey();
            String token = entry.getValue();
            LunaRegEmail lunaRegEmail = new LunaRegEmail();
            lunaRegEmail.setToken(token);
            lunaRegEmail.setRoleId(roleId);
            lunaRegEmail.setEmail(email);
            lunaRegEmail.setExtra(extra);
            lunaRegEmail.setStatus(false);
            lunaRegEmail.setInviteUniqueId(loginUserId);
            lunaRegEmailDAO.insertSelective(lunaRegEmail);
            Runnable mailRunnable = new MailRunnable(email, token, categoryName, currentDate,
                    lunaUser.getLunaName(), lunaRole.getName(), webAddr);
            emailService.sendEmail(mailRunnable);
        }

        return FastJsonUtil.sucess("发送成功");
    }

    @Override
    public JSONObject getUserRoleForEdit(int loginRole, String slaveUserId) {

        LunaUserRole userRole = lunaUserRoleDAO.readUserRoleInfo(slaveUserId);
        if(userRole == null) {
            return FastJsonUtil.error(ErrorCode.NOT_FOUND, "用户不存在");
        }
        int roleId = userRole.getRoleId();

        List<LunaRole> childRolesByRoleId = roleCache.getChildRolesByRoleId(loginRole);

        LunaRole crtRole = null;
        Map<Integer, List<LunaRole>> category2LunaRole = new HashMap<>();
        for(LunaRole lunaRole : childRolesByRoleId) {
            if(lunaRole.getId() == roleId) {
                crtRole = lunaRole;
            }
            int categoryId = lunaRole.getCategoryId();
            List<LunaRole> crtLunaRoleList = category2LunaRole.get(categoryId);
            if(crtLunaRoleList == null) {
                crtLunaRoleList = new ArrayList<>();
                category2LunaRole.put(categoryId, crtLunaRoleList);
            }
            crtLunaRoleList.add(lunaRole);
        }
        if(crtRole == null) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "没有权限获取用户信息");
        }
        JSONObject userInfoJson = new JSONObject();
        userInfoJson.put(LunaUserRoleTable.FIELD_ROLE_ID, roleId);
        userInfoJson.put(LunaUserRoleTable.FIELD_EXTRA, JSON.toJSON(userRole.getExtra()));
        userInfoJson.put(LunaRoleTable.FIELD_CATEGORY_ID, crtRole.getCategoryId());
        List<LunaRoleCategory> roleCategoryList = roleCategoryCache.getAllRoleCategoryList();

        JSONArray jsonArray = new JSONArray();
        //category is well sorted, use it to sort category role
        for(LunaRoleCategory lunaRoleCategory : roleCategoryList) {
            int categoryId = lunaRoleCategory.getId();
            if(category2LunaRole.containsKey(categoryId)) {
                JSONObject jsonObject = (JSONObject) JSON.toJSON(lunaRoleCategory);
                jsonObject.put("roleArray", JSON.toJSON(category2LunaRole.get(categoryId)));
                jsonArray.add(jsonObject);
            }
        }
        userInfoJson.put("options", jsonArray);

        return FastJsonUtil.sucess("success", userInfoJson);
    }

    @Override
    public JSONObject getUserRoleForCreate(int loginRole) {

        List<LunaRole> childRolesByRoleId = roleCache.getChildRolesByRoleId(loginRole);

        Map<Integer, List<LunaRole>> category2LunaRole = new HashMap<>();
        for(LunaRole lunaRole : childRolesByRoleId) {
            int categoryId = lunaRole.getCategoryId();
            List<LunaRole> crtLunaRoleList = category2LunaRole.get(categoryId);
            if(crtLunaRoleList == null) {
                crtLunaRoleList = new ArrayList<>();
                category2LunaRole.put(categoryId, crtLunaRoleList);
            }
            crtLunaRoleList.add(lunaRole);
        }
        List<LunaRoleCategory> roleCategoryList = roleCategoryCache.getAllRoleCategoryList();

        JSONArray jsonArray = new JSONArray();
        //category is well sorted, use it to sort category role
        for(LunaRoleCategory lunaRoleCategory : roleCategoryList) {
            int categoryId = lunaRoleCategory.getId();
            if(category2LunaRole.containsKey(categoryId)) {
                JSONObject jsonObject = (JSONObject) JSON.toJSON(lunaRoleCategory);
                jsonObject.put("roleArray", JSON.toJSON(category2LunaRole.get(categoryId)));
                jsonArray.add(jsonObject);
            }
        }
        return FastJsonUtil.sucess("success", jsonArray);
    }

    @Override
    public JSONObject loginUser(JSONObject jsonObject) {

        String lunaName = jsonObject.getString(LunaUserTable.FIELD_LUNA_NAME);
        String password = jsonObject.getString(LunaUserTable.FIELD_PASSWORD);
        String md5Pwd = VbMD5.convertFixMD5Code(password);

        LunaUserCriteria lunaUserCriteria = new LunaUserCriteria();
        lunaUserCriteria.createCriteria().andLunaNameEqualTo(lunaName);

        List<LunaUser> lunaUsers = lunaUserDAO.selectByCriteria(lunaUserCriteria);
        if(lunaUsers == null || lunaUsers.size() == 0) {
            return FastJsonUtil.error(ErrorCode.NOT_FOUND, "用户名不存在");
        }
        LunaUser lunaUser = lunaUsers.get(0);
        if(! lunaUser.getPassword().equals(md5Pwd)) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "密码错误");
        }

        LunaUserRole userRole = lunaUserRoleDAO.readUserRoleInfo(lunaUser.getUniqueId());

        LunaUserSession lunaUserSession = new LunaUserSession();
        lunaUserSession.setUniqueId(lunaUser.getUniqueId());
        lunaUserSession.setLunaName(lunaUser.getLunaName());
        lunaUserSession.setNickName(lunaUser.getNickName());
        lunaUserSession.setEmail(lunaUser.getEmail());

        lunaUserSession.setRoleId(userRole.getRoleId());
        lunaUserSession.setExtra(userRole.getExtra());

        LunaRoleMenuCriteria lunaRoleMenuCriteria = new LunaRoleMenuCriteria();
        lunaRoleMenuCriteria.createCriteria().andRoleIdEqualTo(userRole.getRoleId());
        List<LunaRoleMenu> lunaRoleMenuList = lunaRoleMenuDAO.selectByCriteria(lunaRoleMenuCriteria);

        List<Integer> menuIdList = new ArrayList<>(lunaRoleMenuList.size());
        for(LunaRoleMenu lunaRoleMenu : lunaRoleMenuList) {
            menuIdList.add(lunaRoleMenu.getMenuId());
        }

        List<Pair<LunaModule, List<LunaMenu>>> moduleAndMenuInfoList =
                moduleMenuCache.getModuleAndMenuByMenuIds(menuIdList);

        Set<String> uriSet = new HashSet<>();
        for (Pair<LunaModule, List<LunaMenu>> moduleAndMenuInfo : moduleAndMenuInfoList) {
            LunaModule module = moduleAndMenuInfo.getLeft();
            List<LunaMenu> menuList = moduleAndMenuInfo.getRight();
            for (LunaMenu menu : menuList) {
                if (menu.getUrl() == null) {
                    uriSet.add(MenuHelper.formatMenuUrl(module, menu));
                } else {
                    uriSet.add(menu.getUrl());
                }
            }
        }
        lunaUserSession.setUriSet(uriSet);

        return FastJsonUtil.sucess("", JSON.toJSON(lunaUserSession));
    }

    @Override
    public JSONObject getChildRoleAndModuleByUserId(String userId) {
        LunaUserRole lunaUserRole = lunaUserRoleDAO.readUserRoleInfo(userId);
        if(lunaUserRole == null) {
            logger.warn("no role for user: " + userId);
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "用户不存在");
        }
        return getChildRoleAndModuleByRoleId(lunaUserRole.getRoleId());
    }

    @Override
    public JSONObject getChildRoleAndModuleByRoleId(int roleId) {

        Map<Integer, LunaRole> roleId2Role = new HashMap<>();
        Map<Integer, List<LunaRole>> module2RoleList = new HashMap<>();
        JSONArray jsonArray = new JSONArray();
        try {
            List<LunaRole> childRolesByRoleId = roleCache.getChildRolesByRoleId(roleId);
            for(LunaRole lunaRole : childRolesByRoleId) {
                roleId2Role.put(lunaRole.getId(), lunaRole);
            }

            LunaRoleMenuCriteria lunaRoleMenuCriteria = new LunaRoleMenuCriteria();
            lunaRoleMenuCriteria.createCriteria().andRoleIdIn(Lists.newArrayList(roleId2Role.keySet()));
            List<LunaRoleMenu> lunaRoleMenus = lunaRoleMenuDAO.selectByCriteria(lunaRoleMenuCriteria);

            Map<Integer, List<Integer>> roleId2MenuIdList = new HashMap<>(roleId2Role.keySet().size());

            for (LunaRoleMenu lunaRoleMenu : lunaRoleMenus) {
                int crtRoleId = lunaRoleMenu.getRoleId();
                int crtMenuId = lunaRoleMenu.getMenuId();

                List<Integer> crtMenuIdList = roleId2MenuIdList.get(crtRoleId);
                if(crtMenuIdList == null) {
                    crtMenuIdList = new ArrayList<>();
                    roleId2MenuIdList.put(crtRoleId, crtMenuIdList);
                }
                crtMenuIdList.add(crtMenuId);
            }

            for(Map.Entry<Integer, List<Integer>> entry : roleId2MenuIdList.entrySet()) {
                int crtRoleId = entry.getKey();
                List<LunaModule> moduleByMenuIds = moduleMenuCache.getModuleByMenuIds(entry.getValue());
                if (moduleByMenuIds != null) {
                    for (LunaModule lunaModule : moduleByMenuIds) {
                        int crtLunaModuleId = lunaModule.getId();
                        List<LunaRole> crtRoles = module2RoleList.get(crtLunaModuleId);
                        if (crtRoles == null) {
                            crtRoles = new ArrayList<>();
                            module2RoleList.put(crtLunaModuleId, crtRoles);
                        }
                        crtRoles.add(roleId2Role.get(crtRoleId));
                    }
                }
            }

            List<LunaModule> allModule = moduleMenuCache.getAllModule();
            Set<Integer> retModule = module2RoleList.keySet();

            // use well sorted module to sort result module
            for(LunaModule crtModule : allModule) {
                Integer id = crtModule.getId();
                if(! retModule.contains(id)) {
                    continue;
                }
                JSONObject jsonObject = (JSONObject) JSON.toJSON(crtModule);
                jsonObject.put("roles", JSON.toJSON(module2RoleList.get(id)));
                jsonArray.add(jsonObject);
            }

        } catch (Exception ex) {
            logger.error("Failed to get child role by roleId: " + roleId, ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
        return FastJsonUtil.sucess("success", jsonArray);
    }

    @Override
    public JSONObject registerUser(JSONObject jsonObject) {

        String lunaName = jsonObject.getString("luna_name");
        String password = jsonObject.getString("password");
        String token = jsonObject.getString("token");

        // 检查是否被注册。注册前有链接的检查isTokenValid()，但仍可能出现多个人共用一个链接
        // 同时进行注册时，出现抢占的情况

        try {
            LunaRegEmailCriteria lunaRegEmailCriteria = new LunaRegEmailCriteria();
            lunaRegEmailCriteria.createCriteria().andTokenEqualTo(token);
            List<LunaRegEmail> lunaRegEmailList = lunaRegEmailDAO.selectByCriteriaWithBLOBs(lunaRegEmailCriteria);
            if (lunaRegEmailList == null) {
                logger.warn("user already exist:" + lunaName);
                return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "非法邀请码");
            } else if (lunaRegEmailList.get(0).getStatus()){
                return FastJsonUtil.error(ErrorCode.ALREADY_EXIST, "该账户已被注册");
            }

            // 乐观锁——status
            LunaRegEmail lunaRegEmail = new LunaRegEmail();
            lunaRegEmail.setStatus(true);
            int rows = lunaRegEmailDAO.updateByCriteriaSelective(lunaRegEmail, lunaRegEmailCriteria);
            if (rows != 1) {
                logger.error("user already exist, concurrently create the same user");
                return FastJsonUtil.error(ErrorCode.ALREADY_EXIST, "该账户已被注册");
            }

            lunaRegEmail = lunaRegEmailList.get(0);
            Integer roleId = lunaRegEmail.getRoleId();
            String email = lunaRegEmail.getEmail();
            String extra = lunaRegEmail.getExtra();
            LunaUser lunaUser = new LunaUser();
            String userId = UUID.randomUUID().toString().replace("-", "");
            lunaUser.setUniqueId(userId);
            lunaUser.setLunaName(lunaName);
            lunaUser.setNickName(lunaName);
            lunaUser.setEmail(email);
            lunaUser.setCreateTime(new Date());
            String md5Pwd = VbMD5.convertFixMD5Code(password);
            lunaUser.setPassword(md5Pwd);
            lunaUserDAO.insert(lunaUser);

            LunaUserRole userRole = new LunaUserRole();
            userRole.setUserId(userId);
            userRole.setLunaName(lunaName);
            userRole.setRoleId(roleId);
            userRole.setExtra(JSON.parseObject(extra));
            lunaUserRoleDAO.createUserRoleInfo(userRole);
        } catch (Exception ex) {
            logger.error("Failed to create user", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }

        return FastJsonUtil.sucess("账户创建成功！");

    }

    @Override
    public JSONObject deleteUser(String userId) {
        try {
            lunaUserDAO.deleteByPrimaryKey(userId);
            lunaUserRoleDAO.deleteUserRoleInfoByUserId(userId);
        } catch (Exception ex) {
            logger.error("Failed to delete user: " + userId);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "删除失败");
        }
        return FastJsonUtil.sucess("删除用户成功");
    }

    @Override
    public JSONObject updateUserRole(JSONObject jsonObject) {
        try {
            LunaUserRole lunaUserRole = JSON.toJavaObject(jsonObject, LunaUserRole.class);
            lunaUserRoleDAO.updateUserRoleInfo(lunaUserRole);
        } catch (Exception ex) {
            logger.error("Failed to update user role: " + jsonObject, ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "更新用户角色失败");
        }
        return FastJsonUtil.sucess("更新用户角色成功");
    }

    @Override
    public JSONObject updateUserInfo(JSONObject jsonObject) {
        return null;
    }

    @Override
    public JSONObject isTokenValid(String token) {

        LunaRegEmailCriteria lunaRegEmailCriteria = new LunaRegEmailCriteria();
        lunaRegEmailCriteria.createCriteria().andTokenEqualTo(token).andStatusEqualTo(false);
        Integer count = lunaRegEmailDAO.countByCriteria(lunaRegEmailCriteria);
        if(count == 0){
            return FastJsonUtil.error("-2", "无有效链接！");//可能没有,也可能已注册
        }
        return FastJsonUtil.sucess("存在有效链接！");
    }
}
