package ms.luna.web.control.content;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.sc.ManageShowAppService;
import ms.luna.biz.table.LunaUserTable;
import ms.luna.biz.table.MsShowAppTable;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.common.LunaUserSession;
import ms.luna.web.common.AuthHelper;
import ms.luna.web.common.SessionHelper;
import ms.luna.web.control.common.BasicController;
import ms.luna.web.util.RequestHelper;
import ms.luna.web.util.TokenUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-27
 */

@Controller
@RequestMapping("/content/app")
public class AppController extends BasicController {

    private final static Logger logger = Logger.getLogger(AppController.class);

    @Autowired
    private ManageShowAppService manageShowAppService;

    public static final String menu = "app";

    @RequestMapping(method = RequestMethod.GET, value = "")
    public ModelAndView init(HttpServletRequest request) {

        SessionHelper.setSelectedMenu(request.getSession(false), menu);
        ModelAndView modelAndView = buildModelAndView("/manage_app");
        return modelAndView;
    }

    private boolean isValidName(String appName) {

        if(StringUtils.isEmpty(appName)) {
            return false;
        }

        return appName.length() <= 16;
    }

    @RequestMapping(method = RequestMethod.POST, value = "")
    @ResponseBody
    public JSONObject createApp(HttpServletRequest request) throws IOException {

        String appName = RequestHelper.getString(request, MsShowAppTable.FIELD_APP_NAME);
        int businessId = RequestHelper.getInteger(request, MsShowAppTable.FIELD_BUSINESS_ID);
        if(AuthHelper.hasBusinessAuth(request, businessId)) {
            return FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "没有此业务权限");
        }
        int type = RequestHelper.getInteger(request, MsShowAppTable.FIELD_TYPE);
        if(businessId < 0) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "业务Id不合法");
        }
        if(! isValidName(appName)) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "微景展名称不合法");
        }
        String token = null;
        //高级版调用子系统
        if(type == 1) {
            try {
                token = TokenUtil.generateRandomToken();
            } catch (Exception ex) {
                logger.error("Failed to generate token", ex);
                return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "生成开发版认证失败");
            }
        }

        LunaUserSession user = SessionHelper.getUser(request.getSession(false));
        JSONObject jsonObject = JSONObject.parseObject("{}");
        jsonObject.put(MsShowAppTable.FIELD_APP_NAME, appName);
        jsonObject.put(MsShowAppTable.FIELD_BUSINESS_ID, businessId);
        jsonObject.put(MsShowAppTable.FIELD_TYPE, type);
        jsonObject.put("owner", user.getLunaName());
        JSONObject result = manageShowAppService.createApp(jsonObject.toString());
        if(result.getString("code").equals("0") && token != null) {
            result.getJSONObject("data").put("token", token);
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "token")
    @ResponseBody
    public JSONObject generateToken(HttpServletRequest request) {
        try {
            String token = TokenUtil.generateRandomToken();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("token", token);
            return FastJsonUtil.sucess("", jsonObject);

        } catch (Exception ex) {
            logger.error("Failed to generate token", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "生成开发版认证失败");
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{appId}")
    @ResponseBody
    public JSONObject updateApp(@PathVariable int appId, HttpServletRequest request) throws IOException {

        String appName = RequestHelper.getString(request, "app_name");
        if(! isValidName(appName)) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "微景展名称不合法");
        }
        LunaUserSession user = SessionHelper.getUser(request.getSession(false));

        JSONObject jsonObject = JSONObject.parseObject("{}");
        jsonObject.put("app_id", appId);
        jsonObject.put("app_name", appName);
        jsonObject.put("update_user", user.getLunaName());

        JSONObject result = manageShowAppService.updateApp(jsonObject.toString());

        return result;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{appId}")
    @ResponseBody
    public JSONObject deleteApp(@PathVariable int appId) throws IOException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("app_id", appId);
        try {
            JSONObject result = manageShowAppService.deleteApp(jsonObject.toString());
            return result;
        } catch (Exception ex) {
            logger.error("Failed to delete app", ex);
        }

        return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "删除微景展失败");

    }

    @RequestMapping(method = RequestMethod.GET,  value = "/search")
    @ResponseBody
    public JSONObject asyncSearchApps(@RequestParam(required = false) Integer offset,
                                @RequestParam(required = false) Integer limit, HttpServletRequest request)
            throws IOException {

        JSONObject resJSON = new JSONObject();
        resJSON.put("total", 0);
        try {

            JSONObject param = new JSONObject();
            if (offset != null) {
                param.put("min", offset);
            }
            if (limit != null) {
                param.put("max", limit);
            }
            String keyword = RequestHelper.getString(request, "like_filter_nm");
            if(keyword != null) {
                keyword = URLDecoder.decode(keyword, "utf-8");
                param.put("keyword", keyword.trim());
            }
            LunaUserSession user = SessionHelper.getUser(request.getSession());
            param.put(LunaUserTable.FIELD_ID, user.getUniqueId());

            JSONObject result = manageShowAppService.loadApps(param.toString());
            if(result == null) {
                logger.info("no result returned");
            } else if ("0".equals(result.getString("code"))) {
                JSONObject data = result.getJSONObject("data");
                resJSON = data;
            }
        } catch (Exception e) {
            logger.error("Failed to load apps", e);
        }

        return resJSON;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/copy")
    @ResponseBody
    public JSONObject copyApp(HttpServletRequest request) throws IOException {

        String appName = RequestHelper.getString(request, "app_name");
        int businessId = RequestHelper.getInteger(request, "business_id");
        int sourceAppId = RequestHelper.getInteger(request, "source_app_id");
        String type = RequestHelper.getString(request, "type");
        if(businessId < 0) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "业务Id不合法");
        }
        if(! isValidName(appName)) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "业务名称不合法");
        }
        if(sourceAppId < 0) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "源微景展不合法");
        }

        LunaUserSession user = SessionHelper.getUser(request.getSession(false));

        JSONObject jsonObject = JSONObject.parseObject("{}");
        jsonObject.put("app_name", appName);
        jsonObject.put("business_id", businessId);
        jsonObject.put("source_app_id", sourceAppId);
        jsonObject.put("owner", user.getLunaName());
        jsonObject.put("type", type);
        JSONObject result = manageShowAppService.copyApp(jsonObject.toString());

        return result;

    }


}
