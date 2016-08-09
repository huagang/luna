package com.microscene.web.controller;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.sc.ManageArticleService;
import ms.luna.biz.table.MsArticleTable;
import ms.luna.biz.util.FastJsonUtil;
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
        ModelAndView modelAndView = buildModelAndView("article/article2");
        if(jsonObject.getIntValue("code") == 0) {
            JSONObject data = jsonObject.getJSONObject("data");
            modelAndView.addObject("title", data.getString(MsArticleTable.FIELD_TITLE));
            modelAndView.addObject("description", data.getString(MsArticleTable.FIELD_ABSTRACT_CONTENT));
            modelAndView.addObject("articleJson", jsonObject);
        } else {
            return buildModelAndView("404");
        }

        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/v1/{id}")
    public ModelAndView showArticle2(@PathVariable int id,
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
            modelAndView.addObject("articleJson", jsonObject);
        } else {
            return buildModelAndView("404");
        }

        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/data/{id}")
    @ResponseBody
    public JSONObject getArticleById(@PathVariable int id) {
        try {
            JSONObject jsonObject = manageArticleService.getOnlineArticleByIdForApi(id);
            return jsonObject;
        } catch (Exception ex) {
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取文章失败");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/businessName/{businessName}/columnNames/{columnNames}")
    @ResponseBody
    public JSONObject getArticleByBusinessAndColumnName(@PathVariable String businessName, @PathVariable String columnNames) {
        try {
            JSONObject jsonObject = manageArticleService.getArticleByBusinessAndColumnName(businessName, columnNames);
            logger.info(jsonObject);
            return jsonObject;
        } catch (Exception ex) {
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取文章失败");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/businessName/{businessName}")
    @ResponseBody
    public JSONObject getArticleByBusinessName(@PathVariable String businessName) {
        try {
            JSONObject jsonObject = manageArticleService.getArticleByBusinessAndColumnName(businessName, null);
            return jsonObject;
        } catch (Exception ex) {
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取文章失败");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/businessId/{businessId}/columnIds/{columnIds}")
    @ResponseBody
    public JSONObject getArticleByBusinessAndColumnId(@PathVariable int businessId, @PathVariable String columnIds) {
        try {
            JSONObject jsonObject = manageArticleService.getArticleByBusinessAndColumnId(businessId, columnIds);
            return jsonObject;
        } catch (Exception ex) {
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取文章失败");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/businessId/{businessId}")
    @ResponseBody
    public JSONObject getArticleByBusinessId(@PathVariable int businessId) {
        try {
            JSONObject jsonObject = manageArticleService.getArticleByBusinessAndColumnId(businessId, null);
            return jsonObject;
        } catch (Exception ex) {
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取文章失败");
        }
    }


}
