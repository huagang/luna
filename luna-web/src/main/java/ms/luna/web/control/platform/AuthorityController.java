package ms.luna.web.control.platform;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.sc.LunaRoleService;
import ms.luna.biz.table.LunaRoleTable;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.common.LunaUserSession;
import ms.luna.web.common.SessionHelper;
import ms.luna.web.control.common.BasicController;
import ms.luna.web.util.RequestHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-27
 */
@Controller
@RequestMapping("/platform/authority")
public class AuthorityController extends BasicController {

    private final static Logger logger = Logger.getLogger(AuthorityController.class);

    @Autowired
    private LunaRoleService lunaRoleService;

    @RequestMapping(method = RequestMethod.GET, value = "")
    public ModelAndView init() {
        ModelAndView modelAndView = buildModelAndView("authority");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/search")
    @ResponseBody
    public JSONObject searchAuthority(HttpServletRequest request) {
        LunaUserSession user = SessionHelper.getUser(request.getSession(false));
        if(user == null) {
            logger.warn("user not login, should not happen");
            return FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "用户未登录");
        }


        int offset = RequestHelper.getInteger(request, "offset");
        int limit = RequestHelper.getInteger(request, "limit");
        if(offset < 0) {
            offset = 0;
        }
        if(limit < 0) {
            limit = 20;
        }

        try {
            JSONObject childRolesByRoleId = lunaRoleService.getAllRoleList(user.getRoleId(), offset, limit);
            if (childRolesByRoleId.getString("code").equals("0")) {
                return childRolesByRoleId.getJSONObject("data");
            } else {
                return childRolesByRoleId;
            }
        } catch (Exception ex) {
            logger.error("Failed to search authority", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ModelAndView updateAuthority(@PathVariable int id, HttpServletRequest request) {
        LunaUserSession user = SessionHelper.getUser(request.getSession(false));
        try {
            JSONObject roleInfo = lunaRoleService.getRoleInfo(id);
            JSONObject editableModuleAndMenu = lunaRoleService.getEditableModuleAndMenu(user.getRoleId(), id);
            if(roleInfo.getString("code").equals("0") && editableModuleAndMenu.getString("code").equals("0")) {
                ModelAndView modelAndView = buildModelAndView("authority-set");
                modelAndView.addObject("moduleAndMenu", editableModuleAndMenu.getJSONArray("data"));
                modelAndView.addObject("roleName", roleInfo.getJSONObject("data").getString(LunaRoleTable.FIELD_NAME));
                return modelAndView;
            }
        } catch (Exception ex) {
            logger.error("Failed to load module and menu for role: " + id, ex);
        }
        return buildModelAndView("500");
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    @ResponseBody
    public JSONObject submitUpdateAuthority(@PathVariable int id, HttpServletRequest request) {
        LunaUserSession user = SessionHelper.getUser(request.getSession(false));
        String menus = RequestHelper.getString(request, "menuArray");
        try {
            return lunaRoleService.updateMenuForRole(user.getRoleId(), id, JSON.parseArray(menus));
        } catch (Exception ex) {
            logger.error("Failed to update authority", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
    }
}
