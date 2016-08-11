package ms.luna.web.control.common;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
@RequestMapping("/")
public class IndexController extends BasicController {

    public static final Logger logger = Logger.getLogger(IndexController.class);

    @RequestMapping(method = RequestMethod.GET, value = "")
    public ModelAndView index(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if(session == null) {
            logger.trace("go to login");
            return buildModelAndView("login");
        } else {
            logger.trace("go to home " + session.getId());
            return buildModelAndView("home");
        }

    }
}
