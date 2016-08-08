package ms.luna.web.control.platform;

import com.alibaba.dubbo.common.json.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.sc.LunaUserService;
import ms.luna.biz.table.LunaRoleCategoryTable;
import ms.luna.biz.table.LunaRoleTable;
import ms.luna.biz.table.LunaUserRoleTable;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.common.LunaUserSession;
import ms.luna.web.common.CommonURI;
import ms.luna.web.common.SessionHelper;
import ms.luna.web.control.common.BasicController;
import ms.luna.web.util.RequestHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-27
 */

@Controller
@RequestMapping("/platform/user")
public class UserController extends BasicController {

    private final static Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private LunaUserService lunaUserService;

    @RequestMapping(method = RequestMethod.GET, value = "")
    public ModelAndView indexPage() {
        ModelAndView modelAndView = buildModelAndView("manage_user");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/search")
    @ResponseBody
    public JSONObject searchUser(HttpServletRequest request) {

        LunaUserSession user = SessionHelper.getUser(request.getSession(false));
        if(user == null) {
            logger.warn("user not login, should not happen");
            return FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "用户未登录");
        }
        String keyword = RequestHelper.getString(request, "keyword");

        int offset = RequestHelper.getInteger(request, "offset");
        int limit = RequestHelper.getInteger(request, "limit");
        if(offset < 0) {
            offset = 0;
        }
        if(limit < 0) {
            limit = 20;
        }
        JSONObject jsonObject = lunaUserService.getUserList(user.getRoleId(), keyword, offset, limit);
        if(jsonObject.getString("code").equals("0")) {
            return jsonObject.getJSONObject("data");
        } else {
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取用户失败");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "")
    @ResponseBody
    public JSONObject submitInviteUser(HttpServletRequest request) {

        LunaUserSession user = SessionHelper.getUser(request.getSession(false));
        if(user == null) {
            return FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "没有权限");
        }

        String emails = RequestHelper.getString(request, "emails");
        int roleId = RequestHelper.getInteger(request, "role_id");
        int categoryId = RequestHelper.getInteger(request, LunaRoleTable.FIELD_CATEGORY_ID);
        String extra = RequestHelper.getString(request, LunaRoleCategoryTable.FIELD_EXTRA);

        if(StringUtils.isBlank(emails)) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "邮件列表不能为空");
        }

        if(roleId < 0 || categoryId < 0) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "角色或权限模块不合法");
        }

        if(StringUtils.isBlank(extra)) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "选项不合法");
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("emails", emails);
        jsonObject.put("role_id", roleId);
        jsonObject.put("webAddr", CommonURI.getAbsoluteUrlForServletPath(request, CommonURI.REGITSTER_SERVLET_PATH));
        jsonObject.put(LunaRoleTable.FIELD_CATEGORY_ID, categoryId);
        jsonObject.put(LunaRoleCategoryTable.FIELD_EXTRA, extra);
        logger.error(jsonObject);
        logger.error(extra);
        try {
            lunaUserService.inviteUser(user.getUniqueId(), jsonObject);
        } catch (Exception ex) {
            logger.error("邀请用户失败", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
        return FastJsonUtil.sucess("");
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @ResponseBody
    public JSONObject deleteUser(@PathVariable String id) {
        try {
            JSONObject jsonObject = lunaUserService.deleteUser(id);
            return jsonObject;
        } catch (Exception ex) {
            logger.error("Failed to delete user");
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/invite", params = "data")
    @ResponseBody
    public JSONObject inviteUser(HttpServletRequest request) {
        LunaUserSession user = SessionHelper.getUser(request.getSession(false));
        JSONObject userRoleForCreate = lunaUserService.getUserRoleForCreate(user.getRoleId());
        return userRoleForCreate;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/invite")
    public ModelAndView createUser() {
        return buildModelAndView("add_user");
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    @ResponseBody
    public JSONObject submitUpdateUserRole(@PathVariable String id, HttpServletRequest request) {

        int roleId = RequestHelper.getInteger(request, "role_id");
        String extra = RequestHelper.getString(request, "extra");

        if(roleId < 0) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "角色不合法");
        }
        if(StringUtils.isBlank(extra)) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "选项不合法");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(LunaUserRoleTable.FIELD_USER_ID, id);
        jsonObject.put(LunaUserRoleTable.FIELD_ROLE_ID, roleId);
        jsonObject.put(LunaUserRoleTable.FIELD_EXTRA, extra);
        try {
            return lunaUserService.updateUserRole(jsonObject);
        } catch (Exception ex) {
            logger.error("Failed to submit update user role", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}", params = "edit")
    public ModelAndView updateUserRole(@PathVariable String id, HttpServletRequest request,
                                       HttpServletResponse response) throws IOException {

        ModelAndView modelAndView = buildModelAndView("add_user");
        LunaUserSession user = SessionHelper.getUser(request.getSession(false));
        JSONObject userRoleForEdit = lunaUserService.getUserRoleForEdit(user.getRoleId(), id);
        if(! userRoleForEdit.getString("code").equals("0")) {
            response.sendRedirect(request.getContextPath() + "/500.jsp");
        }
        modelAndView.addObject("roleData", userRoleForEdit.getJSONObject("data"));
        return modelAndView;
    }




}
