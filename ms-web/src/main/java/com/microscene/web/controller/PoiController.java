package com.microscene.web.controller;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.sc.ManagePoiService;
import ms.luna.biz.table.MsArticleTable;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-01
 */

@Controller
@RequestMapping("/poi")
public class PoiController extends BaseController {

    private final static Logger logger = Logger.getLogger(PoiController.class);

    @Autowired
    private ManagePoiService managePoiService;

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ModelAndView poiDetail(@PathVariable String id, @RequestParam(required = false, value = "lang") String lang) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("_id", id);
        if(lang == null) {
            lang = "zh";
        }
        jsonObject.put("lang", lang);
        JSONObject ret = managePoiService.initPoiPreview(jsonObject.toJSONString());
        ModelAndView modelAndView = buildModelAndView("showPoi");
        if(ret.getString("code").equals("0")) {
            JSONObject data = ret.getJSONObject("data");
            modelAndView.addObject("title", data.getString("long_title"));
            modelAndView.addObject("description", data.getString("long_title"));
            modelAndView.addObject("poiJson", ret);
        } else {
            return buildModelAndView("404");
        }

        return modelAndView;
    }

}
