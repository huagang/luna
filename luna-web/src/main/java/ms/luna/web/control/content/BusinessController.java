package ms.luna.web.control.content;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.cons.VbConstant;
import ms.luna.biz.sc.ManageBusinessService;
import ms.luna.biz.table.LunaUserTable;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.common.LunaUserSession;
import ms.luna.web.common.AreaOptionQueryBuilder;
import ms.luna.web.common.SessionHelper;
import ms.luna.web.control.common.BasicController;
import ms.luna.web.control.common.PulldownController;
import ms.luna.web.util.RequestHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-27
 */
@Controller
@RequestMapping("/content/business")
public class BusinessController extends BasicController {

    private final static Logger logger = Logger.getLogger(BusinessController.class);

    public static final String menu = "business";

    @Autowired
    private ManageBusinessService manageBusinessService;
    @Autowired
    private PulldownController pulldownController;

    @RequestMapping(method = RequestMethod.GET, value = "")
    public ModelAndView init(HttpServletRequest request, HttpServletResponse response) {

        try {
            SessionHelper.setSelectedMenu(request.getSession(false), menu);
            ModelAndView modelAndView = buildModelAndView("manage_business");
            modelAndView.addObject("provinces", pulldownController.loadProvinces());
            modelAndView.addObject("businessCategories", pulldownController.loadBusinessCategories());
//			modelAndView.addObject("businesses", businesses);
            return modelAndView;

        } catch (Exception e) {
            logger.error("Failed to initialize", e);
        }
        return buildModelAndView("/error");

    }

    @RequestMapping(method = RequestMethod.GET, value = "/select")
    public ModelAndView selectBusiness(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null) {
            logger.warn("User not login, should not happen");
            return buildModelAndView("login");
        }
        LunaUserSession user = SessionHelper.getUser(session);
        if(user == null) {
            logger.warn("User info not set, should not happen");
            return buildModelAndView("login");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(LunaUserTable.FIELD_ID, user.getUniqueId());
        JSONObject businessForSelect = manageBusinessService.getBusinessForSelect(jsonObject);
        if(businessForSelect.getString("code").equals("0")) {
            ModelAndView modelAndView = buildModelAndView("select_business");
            modelAndView.addObject("businessMap", businessForSelect.getJSONObject("data"));
            return modelAndView;
        } else {
            return buildModelAndView("500");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/selectForEdit")
    @ResponseBody
    public JSONObject selectBusinessForEdit(@RequestParam(required = false, value = "unique_id") String slaveUserId,
                                            HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        LunaUserSession user = SessionHelper.getUser(request.getSession(false));
        jsonObject.put("loginUserId", user.getUniqueId());
        if(StringUtils.isNotBlank(slaveUserId)) {
            jsonObject.put("slaveUserId", slaveUserId);
        }
        try {
            return manageBusinessService.getBusinessForEdit(jsonObject);
        } catch (Exception ex) {
            logger.error("Failed to get business for edit", ex);

            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/search")
    @ResponseBody
    public JSONObject asyncSearchBusiness(@RequestParam(required = false) Integer offset,
                                    @RequestParam(required = false) Integer limit,
                                    HttpServletRequest request) throws IOException {

        JSONObject resJSON = new JSONObject();
        resJSON.put("total", 0);
        try {

            JSONObject param = JSONObject.parseObject("{}");

            if (offset != null) {
                param.put("min", offset);
            }
            if (limit != null) {
                param.put("max", limit);
            }

            JSONObject result = manageBusinessService.loadBusinesses(param.toString());
            logger.debug(result.toString());
            if ("0".equals(result.getString("code"))) {
                JSONObject data = result.getJSONObject("data");
                resJSON = data;
            }
            return resJSON;
        } catch (Exception e) {
            logger.error("Failed to load businesses", e);
        }

        return resJSON;
    }

    @RequestMapping(method = RequestMethod.POST, value = "")
    @ResponseBody
    public JSONObject createBusiness(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            HttpSession session = request.getSession(false);
            LunaUserSession msUser = (LunaUserSession)session.getAttribute("msUser");

            String businessName = RequestHelper.getString(request, "business_name");
            if(StringUtils.isBlank(businessName) || businessName.length() > 32) {
                return FastJsonUtil.error(-1, "业务名称长度不合法");
            }
            String businessCode = RequestHelper.getString(request, "business_code");
            if(StringUtils.isBlank(businessCode) || businessCode.length() > 16) {
                return FastJsonUtil.error(-1, "业务简称长度不合法");
            }
            String merchantId = RequestHelper.getString(request, "merchant_id");
            if(StringUtils.isBlank(merchantId)) {
                return FastJsonUtil.error(-1, "商户不能为空");
            }

            String createUser = msUser.getNickName();
            JSONObject param = JSONObject.parseObject("{}");
            param.put("business_name", businessName);
            param.put("business_code", businessCode);
            param.put("merchant_id", merchantId);
            param.put("create_user", createUser);
            // manageAppService
            JSONObject result = manageBusinessService.createBusiness(param.toString());

            return result;
        } catch (Exception e) {
            logger.error("Failed to create business", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "处理异常");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/searchMerchant")
    @ResponseBody
    public JSONObject searchMerchant(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            AreaOptionQueryBuilder.Builder builder = AreaOptionQueryBuilder.builder();
            builder.newStringParam("province_id", request.getParameter("province_id"));
            builder.newStringParam("city_id", request.getParameter("city_id"));
            builder.newStringParam("county_id", request.getParameter("county_id"));
            builder.newStringParam("keyword", request.getParameter("keyword"));

            JSONObject jsonObject = builder.buildJsonQuery();
            JSONObject result = manageBusinessService.searchMerchant(jsonObject.toString());
            return result;
        } catch (Exception e) {
            logger.error("Failed to search merchant", e);
            return FastJsonUtil.error("-1", "处理异常");
        }

    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{businessId}")
    @ResponseBody
    public JSONObject updateBusiness(@PathVariable int businessId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(businessId < 0) {
            return FastJsonUtil.error("-1", "非法业务Id");
        }
        String businessName = RequestHelper.getString(request, "business_name");
        String businessCode = RequestHelper.getString(request, "business_code");
        if(StringUtils.isBlank(businessName) || businessName.length() > 32) {
            return FastJsonUtil.error(-1, "业务名称长度不合法");
        }
        if(StringUtils.isBlank(businessCode) || businessCode.length() > 16) {
            return FastJsonUtil.error(-1, "业务简称长度不合法");
        }

        JSONObject param = JSONObject.parseObject("{}");
        param.put("business_id", businessId);
        param.put("business_name", businessName);
        param.put("business_code", businessCode);

        try {
            JSONObject result = manageBusinessService.updateBusinessById(param.toString());
            return result;
        } catch(Exception e) {
            logger.error("Failed to update business", e);
            return FastJsonUtil.error("-1", "处理异常");
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{businessId}")
    @ResponseBody
    public JSONObject deleteBusiness(@PathVariable int businessId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            JSONObject jsonObject = manageBusinessService.deleteBusinessById(businessId);
            return jsonObject;
        } catch(Exception e) {
            logger.error("Failed to delete business", e);
            return FastJsonUtil.error("-1", "处理异常");
        }
    }
}
