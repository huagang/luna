package com.microscene.web.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: dean@visualbusiness.com
 * @Date: 2016-07-22
 */

@Controller
@RequestMapping("/farmhouse")
public class FarmHouseController extends BaseController {

    private final static Logger logger = Logger.getLogger(PoiController.class);


    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ModelAndView init(
            @PathVariable String id
            ) {
        logger.info("farmhouse init");
        ModelAndView modelAndView = buildModelAndView("showFarmHouse");
        return modelAndView;
    }

}
