package ms.luna.web.control.common;

import ms.luna.web.common.CommonURI;
import ms.luna.web.common.MenuHelper;
import ms.luna.web.common.SessionHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession(false);

        if(SessionHelper.getUser(session) == null) {
            return buildModelAndView("login");
        } else {
            String selectedMenu = SessionHelper.getSelectedMenu(session);
            if(StringUtils.isNotBlank(selectedMenu)) {
                String contextPath = request.getContextPath();
                String menuUrl = MenuHelper.getMenuUrl(session, selectedMenu);
                response.sendRedirect(contextPath + menuUrl);
            }
            SessionHelper.setSelectedMenu(session, "");
            return buildModelAndView("home");
        }

    }

    @RequestMapping(method = RequestMethod.GET, value = "index")
    public ModelAndView home(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession(false);

        if(SessionHelper.getUser(session) == null) {
            return buildModelAndView("login");
        } else {
            SessionHelper.setSelectedMenu(session, "");
            return buildModelAndView("home");
        }
    }
}
