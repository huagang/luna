package com.microscene.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.sc.FarmPageService;
import ms.luna.biz.sc.ManageBusinessService;
import ms.luna.biz.sc.ManageShowAppService;
import ms.luna.biz.sc.MsShowPageService;
import ms.luna.biz.table.MsShowAppTable;
import ms.luna.biz.table.MsShowPageShareTable;
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
import java.util.Random;

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

        ModelAndView modelAndView = new ModelAndView();
        fillAppShareInfo(appId, modelAndView);
        int type = Integer.parseInt(modelAndView.getModel().get(MsShowAppTable.FIELD_TYPE).toString());
        if(type == 0) {
            JSONObject indexPageJson = msShowPageService.getIndexPage(appId);
            modelAndView.addObject("pageData", indexPageJson.toJSONString());
            modelAndView.setViewName("appShowPage.jsp");
        } else if(type == 2) {
            JSONObject result = farmPageService.getPageInfo(appId);
            modelAndView.addObject("pageData", result.getJSONObject("data"));
            modelAndView.setViewName("showFarmHouse.jsp");
        }
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

            JSONArray shareArray = data.getJSONArray("shareArray");

            //新的分享规则, app中带的老的继续兼容
            if(shareArray != null) {
                int size = shareArray.size();
                Random random = new Random();
                int idx = random.nextInt(size);
                JSONObject jsonObject = shareArray.getJSONObject(idx);
                shareInfoTitle = jsonObject.getString(MsShowPageShareTable.FIELD_TITLE);
                shareInfoDes = jsonObject.getString(MsShowPageShareTable.FIELD_DESCRIPTION);
                shareInfoPic = jsonObject.getString(MsShowPageShareTable.FIELD_PIC);
            }
            modelAndView.addObject("appName", appName);
            modelAndView.addObject(MsShowAppTable.FIELD_TYPE, data.getIntValue(MsShowAppTable.FIELD_TYPE));
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
