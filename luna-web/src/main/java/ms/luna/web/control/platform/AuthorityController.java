package ms.luna.web.control.platform;

import ms.luna.web.control.common.BasicController;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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

    @RequestMapping(method = RequestMethod.GET, value = "")
    public ModelAndView init() {
        ModelAndView modelAndView = buildModelAndView("authority");
        return modelAndView;
    }
}
