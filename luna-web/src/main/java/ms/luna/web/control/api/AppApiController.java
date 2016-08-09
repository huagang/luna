package ms.luna.web.control.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.web.control.content.AppEditController;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-08-04
 */

@Controller
@RequestMapping("/api")
public class AppApiController {

    private final static Logger logger = Logger.getLogger(AppApiController.class);
    @Autowired
    private AppEditController appEditController;
    private final static String TOKEN_VERIRY_URL = "http://webapp.visualbusiness.cn/app/api/verifyToken.do";

    @RequestMapping(method = RequestMethod.PUT, value = "/app/publish/{appId}")
    @ResponseBody
    public JSONObject publishApp(@PathVariable int appId,
                                 @RequestParam(required = true, value = "token") String token,
                                 @RequestParam(required = true, value = "app_addr") String appAddr,
                                 HttpServletRequest request) throws IOException {

        Pair<Boolean, JSONObject> pair = verifyToken(token);
        try {
            if (pair.getLeft()) {
                return appEditController.publishApp(appId, request);
            } else {
                return pair.getRight();
            }
        } catch (Exception ex) {
            logger.error("Failed to publish app", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
    }

    private Pair<Boolean, JSONObject> verifyToken(String token) {

        HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod(TOKEN_VERIRY_URL + "?token=" + token);
        HttpMethodParams params = new HttpMethodParams();
        params.setSoTimeout(1000);
        getMethod.setParams(params);
        try {
            int statusCode = httpClient.executeMethod(getMethod);
            if(statusCode == HttpStatus.SC_OK) {
                JSONObject jsonObject = JSON.parseObject(new String(getMethod.getResponseBody(), "utf-8"));
                if(jsonObject.getIntValue("code") == 0) {
                    return Pair.of(true, null);
                } else {
                    return Pair.of(false, FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "token invalid"));
                }
            } else {
                return Pair.of(false, FastJsonUtil.error(statusCode, "Failed to validate token")) ;
            }
        } catch (Exception ex) {
            logger.error("Failed to verify token", ex);
            return Pair.of(false, FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, ex.getMessage()));
        }

    }

    @RequestMapping(method = RequestMethod.GET, value = "/app/setting/{appId}")
    @ResponseBody
    public JSONObject getSettingOfApp(@PathVariable int appId) throws IOException {
        return appEditController.getSettingOfApp(appId);
    }
}
