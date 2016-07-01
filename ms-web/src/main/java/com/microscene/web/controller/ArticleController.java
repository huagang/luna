package com.microscene.web.controller;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.sc.ManageArticleService;
import ms.luna.biz.table.MsArticleTable;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-06-29
 */

@Controller
@RequestMapping("/article")
public class ArticleController extends BaseController {

    private final static Logger logger = Logger.getLogger(ArticleController.class);

    @Autowired
    private ManageArticleService manageArticleService;

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ModelAndView showArticle(@PathVariable int id,
                                    @RequestParam(required = false, value = "preview") String preview) {

        JSONObject jsonObject;
        if(preview != null) {
            jsonObject = manageArticleService.getArticleById(id);
        } else {
            jsonObject = manageArticleService.getOnlineArticleById(id);
        }
        ModelAndView modelAndView = buildModelAndView("showArticle");
        if(jsonObject.getIntValue("code") == 0) {
            JSONObject data = jsonObject.getJSONObject("data");
            modelAndView.addObject("title", data.getString(MsArticleTable.FIELD_TITLE));
            modelAndView.addObject("description", data.getString(MsArticleTable.FIELD_ABSTRACT_CONTENT));
        }
        modelAndView.addObject("articleJson", jsonObject);
        return modelAndView;
    }



}
