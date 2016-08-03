package ms.luna.web.control.platform;

import com.alibaba.fastjson.JSONObject;
import ms.luna.web.control.common.BasicController;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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

    @RequestMapping(method = RequestMethod.GET, value = "")
    public ModelAndView indexPage() {
        ModelAndView modelAndView = buildModelAndView("login");
        logger.info("user index page");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, params = "q")
    @ResponseBody
    public JSONObject searchUser(String q,
                                 @RequestParam(required = false) Integer offset,
                                 @RequestParam(required = false) Integer limit) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "陈尚安");
        jsonObject.put("q", q);

        logger.info("search user");

        return jsonObject;
    }

}
