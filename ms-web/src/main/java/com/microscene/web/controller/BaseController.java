package com.microscene.web.controller;

import org.springframework.web.servlet.ModelAndView;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-05-24
 */
public class BaseController {

    protected ModelAndView buildModelAndView(String view) {

        if(view.indexOf(".") < 0) {
            view = view + ".jsp";
        }
        ModelAndView modelAndView = new ModelAndView(view);

        return modelAndView;
    }

}
