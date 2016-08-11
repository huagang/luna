package ms.luna.web.control.common;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.sc.ManageBusinessService;
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
 * @Date: 2016-08-11
 */
@Controller
@RequestMapping("/common/business")
public class CommonBusinessController extends BasicController {

    private final static Logger logger = Logger.getLogger(CommonBusinessController.class);
    @Autowired
    private ManageBusinessService manageBusinessService;

    @RequestMapping(method = RequestMethod.GET, value = "/select")
    public ModelAndView selectBusiness(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null) {
            logger.warn("User not login, should not happen");
            return buildModelAndView("login");
        }
        LunaUserSession user = SessionHelper.getUser(session);
        if(user == null) {
            logger.warn("User info not set, should not happen");
            return buildModelAndView("login");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(LunaUserTable.FIELD_ID, user.getUniqueId());
        JSONObject businessForSelect = manageBusinessService.getBusinessForSelect(jsonObject);
        ModelAndView modelAndView = null;
        if(businessForSelect.getString("code").equals("0")) {
            modelAndView = buildModelAndView("select_business");
            modelAndView.addObject("businessMap", businessForSelect.getJSONObject("data"));
        } else {
            // 没有业务权限的用户直接展示home页
            modelAndView = buildModelAndView("home");
        }
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/selectForEdit")
    @ResponseBody
    public JSONObject selectBusinessForEdit(@RequestParam(required = false, value = "unique_id") String slaveUserId,
                                            HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        LunaUserSession user = SessionHelper.getUser(request.getSession(false));
        jsonObject.put("loginUserId", user.getUniqueId());
        if(StringUtils.isNotBlank(slaveUserId)) {
            /**
             * provide user id when modify user business auth;
             * create user do not need
             */
            jsonObject.put("slaveUserId", slaveUserId);
        }
        try {
            return manageBusinessService.getBusinessForEdit(jsonObject);
        } catch (Exception ex) {
            logger.error("Failed to get business for edit", ex);

            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
    }
}
