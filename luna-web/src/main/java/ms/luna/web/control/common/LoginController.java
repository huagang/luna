package ms.luna.web.control.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.sc.LunaUserService;
import ms.luna.biz.sc.MenuService;
import ms.luna.biz.table.LunaUserTable;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.common.LunaUserSession;
import ms.luna.web.common.SessionHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-08-05
 */
@Controller
@RequestMapping("/common")
public class LoginController extends BasicController {

    private final static Logger logger = Logger.getLogger(LoginController.class);

    @Autowired
    private LunaUserService lunaUserService;
    @Autowired
    private MenuService menuService;

    @RequestMapping(method = RequestMethod.GET, value = "/login")
    public ModelAndView init() {
        ModelAndView modelAndView = buildModelAndView("login");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    @ResponseBody
    public JSONObject login(@RequestParam(value="luna_name", required=true) String lunaName,
                            @RequestParam(value="password", required=true) String password,
                            HttpServletRequest request) {


        HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute(SessionHelper.KEY_USER) != null) {
            // 重新登录不要求显式退出,自动退出
            session.invalidate();
            logger.trace("invalid exist session, session id: " + session.getId());
//            return FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "已经登录");
        }
        if(StringUtils.isBlank(lunaName) || StringUtils.isBlank(password)) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "用户名或密码不能为空");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(LunaUserTable.FIELD_LUNA_NAME, lunaName);
        jsonObject.put(LunaUserTable.FIELD_PASSWORD, password);
        JSONObject result = lunaUserService.loginUser(jsonObject);

        if(! result.getString("code").equals("0")) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "用户名或密码错误");
        }

        logger.trace(result);

        JSONObject data = result.getJSONObject("data");
        LunaUserSession lunaUserSession = JSON.toJavaObject(data, LunaUserSession.class);
        session = request.getSession(true);
        if(session == null) {
            logger.trace("create session failed");
        } else {
            logger.trace("create session success");
        }
        SessionHelper.setUser(session, lunaUserSession);
        logger.trace("session user: " + JSON.toJSON(SessionHelper.getUser(session)));
        logger.trace("session id: " + session.getId());
        JSONObject moduleAndMenuByRoleId = menuService.getModuleAndMenuByRoleId(lunaUserSession.getRoleId());
        logger.trace(moduleAndMenuByRoleId);
        if(moduleAndMenuByRoleId.getString("code").equals("0")) {
            SessionHelper.setMenu(session, moduleAndMenuByRoleId.getJSONArray("data"));
        }

        return FastJsonUtil.sucess("登录成功");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/logout")
    public ModelAndView logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }

        return buildModelAndView("login");

    }

    @RequestMapping(method = RequestMethod.GET, value = "/authFail")
    public ModelAndView authFail(HttpServletRequest request) {

        return buildModelAndView("auth_failed");

    }
}
