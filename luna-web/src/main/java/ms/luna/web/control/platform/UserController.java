package ms.luna.web.control.platform;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.sc.LunaUserService;
import ms.luna.web.control.common.BasicController;
import ms.luna.web.util.RequestHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/platform/user")
public class UserController extends BasicController {

    private final static Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private LunaUserService lunaUserService;

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ModelAndView indexPage() {
        ModelAndView modelAndView = buildModelAndView("manage_user");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/search")
    @ResponseBody
    public JSONObject searchUser(@RequestParam(required = false) Integer offset,
                                 @RequestParam(required = false) Integer limit,
                                 HttpServletRequest request) {

        String keyword = RequestHelper.getString(request, "keyword");
        JSONObject userList = lunaUserService.getUserList(0, keyword, offset, limit);

        return userList;
    }

}
