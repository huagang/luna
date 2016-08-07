package ms.luna.web.control.platform;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.sc.LunaUserService;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.common.LunaUserSession;
import ms.luna.web.common.SessionHelper;
import ms.luna.web.control.common.BasicController;
import ms.luna.web.util.RequestHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

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
    public JSONObject submitCreateUser(HttpServletRequest request) {

//        lunaUserService.inviteUser()
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

    @RequestMapping(method = RequestMethod.GET, value = "/invite")
    @ResponseBody
    public JSONObject inviteUser(HttpServletRequest request) {
        LunaUserSession user = SessionHelper.getUser(request.getSession(false));
        JSONObject userRoleForCreate = lunaUserService.getUserRoleForCreate(user.getRoleId());
        return userRoleForCreate;
    }

}
