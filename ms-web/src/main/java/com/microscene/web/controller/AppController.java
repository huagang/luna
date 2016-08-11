package com.microscene.web.controller;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.sc.FarmPageService;
import ms.luna.biz.sc.ManageBusinessService;
import ms.luna.biz.sc.ManageShowAppService;
import ms.luna.biz.sc.MsShowPageService;
import ms.luna.biz.util.MsLogger;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-05-24
 */

@Controller
@RequestMapping("/app")
public class AppController extends BaseController {

    private final static Logger logger = Logger.getLogger(AppController.class);

    @Autowired
    private FarmPageService farmPageService;

    @Autowired
    private ManageShowAppService manageShowAppService;
    @Autowired
    private MsShowPageService msShowPageService;
    @Autowired
    private ManageBusinessService manageBusinessService;

    @RequestMapping(method = RequestMethod.GET, value = "/{appId}")
    public ModelAndView indexPage(@PathVariable int appId, HttpServletRequest request) {
        ModelAndView modelAndView = buildModelAndView("appShowPage");
        JSONObject indexPageJson = msShowPageService.getIndexPage(appId);
        modelAndView.addObject("pageData", indexPageJson.toJSONString());
        fillAppShareInfo(appId, modelAndView);
        modelAndView.addObject("share_info_link", request.getRequestURL());
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{appId}/page/{pageId}")
    public ModelAndView getPage(@PathVariable int appId, @PathVariable String pageId, HttpServletRequest request) {
        JSONObject onePageDetail = msShowPageService.getOnePageDetail(pageId);
        ModelAndView modelAndView = buildModelAndView("appShowPage");
        modelAndView.addObject("pageData", onePageDetail.toJSONString());
        fillAppShareInfo(appId, modelAndView);
        String requestURL = request.getRequestURL().toString();
        modelAndView.addObject("share_info_link", requestURL.substring(0, requestURL.indexOf("/page/")));
        return modelAndView;

    }

    // 方便前端开发,临时代码.
    @RequestMapping(method = RequestMethod.GET, value = "/farm/{appId}")
    public ModelAndView showpage(
            @PathVariable("appId") Integer appId,
            HttpServletRequest request, HttpServletResponse response) {

        ModelAndView modelAndView = buildModelAndView("showFarmHouse");
        JSONObject result = farmPageService.getPageInfo(appId);
        modelAndView.addObject("pageData", result.getJSONObject("data"));
        modelAndView.addObject("appId", appId);
        return modelAndView;
    }

    private void fillAppShareInfo(int appId, ModelAndView modelAndView) {
        JSONObject appSettingJson = manageShowAppService.getSettingOfApp(appId);
        if(appSettingJson.getString("code").equals("0")) {
            JSONObject data = appSettingJson.getJSONObject("data");
            String appName = data.getString("app_name");
            String picThumb = data.getString("pic_thumb");
            String note = data.getString("note");
            String shareInfoTitle = data.getString("share_info_title");
            String shareInfoDes = data.getString("share_info_des");
            String shareInfoPic = data.getString("share_info_pic");
            modelAndView.addObject("appName", appName);
            modelAndView.addObject("share_info_title", StringUtils.isBlank(shareInfoTitle) ? appName : shareInfoTitle);
            modelAndView.addObject("share_info_des", StringUtils.isBlank(shareInfoDes) ? note : shareInfoDes);
            modelAndView.addObject("share_info_pic", StringUtils.isBlank(shareInfoPic) ? picThumb : shareInfoPic);
        }
        JSONObject businessJson = manageBusinessService.getBusinessByAppId(appId);
        if(businessJson.getString("code").equals("0")) {
            modelAndView.addObject("stat_id", businessJson.getJSONObject("data").getInteger("stat_id"));
        }
        JSONObject appInfoJson = manageShowAppService.getAppInfo(appId);
        if(appInfoJson.getString("code").equals("0")) {
            modelAndView.addObject("business_id", appInfoJson.getJSONObject("data").getInteger("business_id"));
        } else {
            modelAndView.addObject("business_id", 0);
        }
    }
}
