package com.microscene.web.controller;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.sc.ManageBusinessService;
import ms.luna.biz.sc.ManageShowAppService;
import ms.luna.biz.sc.MsShowPageService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-05-24
 */

@Controller
@RequestMapping("/business")
public class BusinessController extends BaseController {

    private final static Logger logger = Logger.getLogger(BusinessController.class);
    @Autowired
    private ManageBusinessService manageBusinessService;
    @Autowired
    ManageShowAppService manageShowAppService;
    @Autowired
    private MsShowPageService msShowPageService;

    @RequestMapping(method = RequestMethod.GET, value = "/id/{businessId}")
    public ModelAndView getBusinessShowPageById(@PathVariable int businessId, HttpServletRequest request) {
        JSONObject businessInfoJson = manageBusinessService.getBusinessById(businessId);
        if(! businessInfoJson.getString("code").equals("0")) {
            return buildModelAndView("error");
        }
        logger.trace(businessInfoJson);
        JSONObject data = businessInfoJson.getJSONObject("data");
        ModelAndView modelAndView = getBusinessShowPage(data);
        modelAndView.addObject("share_info_link", request.getRequestURL());
        return modelAndView;
    }


    @RequestMapping(method = RequestMethod.GET, value = "/{businessCode}")
    public ModelAndView getBusinessShowPageByCode(@PathVariable String businessCode, HttpServletRequest request) {
        JSONObject businessInfoJson = manageBusinessService.getBusinessByCode(businessCode);
        if(! businessInfoJson.getString("code").equals("0")) {
            return buildModelAndView("error");
        }
        logger.trace(businessInfoJson);
        JSONObject data = businessInfoJson.getJSONObject("data");
        ModelAndView modelAndView = getBusinessShowPage(data);
        modelAndView.addObject("share_info_link", request.getRequestURL());
        return modelAndView;
    }

    private ModelAndView getBusinessShowPage(JSONObject businessInfoJson) {

        int appId = businessInfoJson.getInteger("app_id");
        int statId = businessInfoJson.getInteger("stat_id");
        JSONObject appSettingJson = manageShowAppService.getSettingOfApp(appId);
        ModelAndView modelAndView = buildModelAndView("appShowPage");
        if(appSettingJson.getString("code").equals("0")) {
            // share info
            JSONObject data = appSettingJson.getJSONObject("data");
            String appName = data.getString("app_name");
            String picThumb = data.getString("pic_thumb");
            String note = data.getString("note");
            String shareInfoTitle = data.getString("share_info_title");
            String shareInfoDes = data.getString("share_info_des");
            String shareInfoPic = data.getString("share_info_pic");
            modelAndView.addObject("share_info_title", StringUtils.isBlank(shareInfoTitle) ? appName : shareInfoTitle);
            modelAndView.addObject("share_info_des", StringUtils.isBlank(shareInfoDes) ? note : shareInfoDes);
            modelAndView.addObject("share_info_pic", StringUtils.isBlank(shareInfoPic) ? picThumb : shareInfoPic);
        }
        JSONObject indexPageJson = msShowPageService.getIndexPage(appId);
        modelAndView.addObject("pageData", indexPageJson.toJSONString());
        modelAndView.addObject("stat_id", statId);
        modelAndView.addObject("business_id", businessInfoJson.getInteger("business_id"));
        return modelAndView;
    }

}
