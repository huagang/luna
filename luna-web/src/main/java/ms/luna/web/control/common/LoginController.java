package ms.luna.web.control.common;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.model.MsUser;
import ms.luna.biz.sc.LunaUserService;
import ms.luna.biz.table.LunaUserTable;
import ms.luna.biz.util.FastJsonUtil;
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

        MsUser msUser = new MsUser();
        logger.trace(result);

        return FastJsonUtil.sucess("登录成功");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/logout")
    public ModelAndView logout(HttpServletRequest request) {

        HttpSession session = request.getSession();
        if(session != null) {
            session.invalidate();
        }

        return buildModelAndView("login");

    }
}
