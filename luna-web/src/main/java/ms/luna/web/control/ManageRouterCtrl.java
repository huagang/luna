package ms.luna.web.control;

import ms.luna.web.common.BasicCtrl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-11
 */

@Controller("manageRouterCtrl")
@RequestMapping("/manage_router.do")
public class ManageRouterCtrl extends BasicCtrl {

    public static final String INIT = "method=init";
    public static final String EDIT_ROUTER = "method=edit_router";
    
    @RequestMapping(params = INIT)
    public ModelAndView init(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.setAttribute("menu_selected", "manage_router");
        }
        ModelAndView modelAndView = buildModelAndView("/manage_router");

        return modelAndView;
    }
    
    @RequestMapping(params = EDIT_ROUTER)
    public ModelAndView editRouter(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.setAttribute("menu_selected", "manage_router");
        }
        ModelAndView modelAndView = buildModelAndView("/manage_router_edit");

        return modelAndView;
    }
}
