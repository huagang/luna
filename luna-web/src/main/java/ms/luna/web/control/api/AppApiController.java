package ms.luna.web.control.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.sc.FarmPageService;
import ms.luna.biz.util.CharactorUtil;
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
    @Autowired
    private FarmPageService farmPageService;
    // TODO: set value in application listener, should provide a config service to do such thing
    public static String APP_DEVELOP_ADDRESS = "";

    @RequestMapping(value = "/app/publish/{appId}")
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
        String tokenVerifyUrl = String.format("http://%s/app/api/verifyToken.do", APP_DEVELOP_ADDRESS);
        GetMethod getMethod = new GetMethod(tokenVerifyUrl + "?token=" + token);
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


    // 根据category id获取show app
    @RequestMapping(method = RequestMethod.GET, value = "/category/app")
    @ResponseBody
    public JSONObject getShowAppByCtgrId(
            @RequestParam(required = false, value = "categoryIds") String categoryIds,
            @RequestParam(required = false, value = "types") String types,
            @RequestParam(required = false, value = "app_status", defaultValue = "1") Integer status,
            @RequestParam(required = false, value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(required = false, value = "limit", defaultValue = "30") Integer limit) {
        try {
            // 检查types类型
            if(types != null && !CharactorUtil.checkIntegerList(types))
                return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "types is valid");
            JSONObject param = new JSONObject();
            param.put("categoryIds", categoryIds);
            param.put("types", types);
            param.put("app_status", status);
            param.put("offset", offset);
            param.put("limit", limit);
            JSONObject result = farmPageService.getShowAppsByCtgrId(param);
            logger.debug(result.toString());
            return result;
        } catch (Exception e) {
            logger.error("Failed to get show apps by category id:" + e.getMessage());
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to get show apps by category id");
        }
    }

}
