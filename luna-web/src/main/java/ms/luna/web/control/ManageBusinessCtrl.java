package ms.luna.web.control;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.model.MsUser;
import ms.luna.biz.sc.ManageBusinessService;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.VbUtility;
import ms.luna.web.common.AreaOptionQueryBuilder;
import ms.luna.web.common.BasicCtrl;
import ms.luna.web.common.PulldownCtrl;
import ms.luna.web.util.RequestHelper;

/**
 * 
 * Copyright (C) 2015 - 2016 Microscene Inc., All Rights Reserved.
 *
 * @author: shawn@visualbusiness.com
 * @Date: Mar 30, 2016
 *
 */

@Component
@Controller
@RequestMapping("/manage/business.do")
public class ManageBusinessCtrl extends BasicCtrl {
	private final static Logger logger = Logger.getLogger(ManageBusinessCtrl.class);
	
	@Autowired
	private ManageBusinessService manageBusinessService;
	
	@Resource(name="pulldownCtrl")
	private PulldownCtrl pulldownCtrl;
	
	@RequestMapping(params = "method=init")
	public ModelAndView init(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.setAttribute("menu_selected", "manage_business");
			}
			ModelAndView modelAndView = buildModelAndView("/manage_business");
			modelAndView.addObject("provinces", pulldownCtrl.loadProvinces());
			modelAndView.addObject("businessCategories", pulldownCtrl.loadBusinessCategories());
//			modelAndView.addObject("businesses", businesses);
			return modelAndView;
			
		} catch (Exception e) {
			logger.error("Failed to initialize", e);
		}
		return buildModelAndView("/error");
	}
	
	@RequestMapping(params = "method=async_search_business")
	public void asyncSearchBusiness(@RequestParam(required = false) Integer offset,
			@RequestParam(required = false) Integer limit, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		logger.debug("asyncSearchBusiness start....");
		JSONObject resJSON = JSONObject.parseObject("{}");
		resJSON.put("total", 0);
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/html; charset=UTF-8");

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
			
			response.getWriter().print(resJSON.toString());
			response.setStatus(200);
			return;
		} catch (Exception e) {
			logger.error("Failed to load businesses", e);
		}
		
		response.getWriter().print(resJSON.toString());
		response.setStatus(200);
		return;
	}
	
	@RequestMapping(params = "method=create_business")
	public void createBusiness(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/html; charset=UTF-8");

			HttpSession session = request.getSession(false);
			MsUser msUser = (MsUser)session.getAttribute("msUser");

			String businessName = RequestHelper.getString(request, "business_name");
			if(StringUtils.isBlank(businessName) || businessName.length() > 32) {
				response.getWriter().print(FastJsonUtil.error(-1, "业务名称长度不合法"));
				return;
			}
			String businessCode = RequestHelper.getString(request, "business_code");
			if(StringUtils.isBlank(businessCode) || businessCode.length() > 16) {
				response.getWriter().print(FastJsonUtil.error(-1, "业务简称长度不合法"));
				return;
			}
			String merchantId = RequestHelper.getString(request, "merchant_id");
			if(StringUtils.isBlank(merchantId)) {
				response.getWriter().print(FastJsonUtil.error(-1, "商户不能为空"));
				return;
			}
			
			String createUser = msUser.getNickName();
			JSONObject param = JSONObject.parseObject("{}");
			param.put("business_name", businessName);
			param.put("business_code", businessCode);
			param.put("merchant_id", merchantId);
			param.put("create_user", createUser);
			// manageAppService
			JSONObject result = manageBusinessService.createBusiness(param.toString());
			
			if (!"0".equals(result.getString("code"))) {
				response.getWriter().print(FastJsonUtil.error("-1", result.getString("msg")));
			} else {
				response.getWriter().print(result.toString());
			}
			response.setStatus(200);
		} catch (Exception e) {
			response.getWriter().print(FastJsonUtil.error("-1", "处理异常"));
			logger.error("Failed to create business", e);
			response.setStatus(200);
		}
	}
	
	@RequestMapping(params = "method=search_merchant")
	public void searchMerchant(HttpServletRequest request, HttpServletResponse response) throws IOException {
	
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		
		try {
			AreaOptionQueryBuilder.Builder builder = AreaOptionQueryBuilder.builder();
			builder.newStringParam("province_id", request.getParameter("province_id"));
			builder.newStringParam("city_id", request.getParameter("city_id"));
			builder.newStringParam("county_id", request.getParameter("county_id"));
			builder.newStringParam("keyword", request.getParameter("keyword"));
			
			JSONObject jsonObject = builder.buildJsonQuery();
			JSONObject result = manageBusinessService.searchMerchant(jsonObject.toString());
		
			if (!"0".equals(result.getString("code"))) {
				response.getWriter().print(FastJsonUtil.error("-1", result.getString("msg")));
			} else {
				response.getWriter().print(result.toString());
			}
			response.setStatus(200);
		} catch (Exception e) {
			response.getWriter().print(FastJsonUtil.error("-1", "处理异常"));
			logger.error("Failed to search merchant", e);
			response.setStatus(200);
		}
	
	}
	
	@RequestMapping(params = "method=update_business")
	public void updateBusiness(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		response.setStatus(200);
		int businessId = RequestHelper.getInteger(request, "business_id");
		if(businessId < 0) {
			response.getWriter().print(FastJsonUtil.error("-1", "非法业务Id"));
			return;
		}
		
		String businessName = RequestHelper.getString(request, "business_name");
		String businessCode = RequestHelper.getString(request, "business_code");
		if(StringUtils.isBlank(businessName) || businessName.length() > 32) {
			response.getWriter().print(FastJsonUtil.error(-1, "业务名称长度不合法"));
			return;
		}
		if(StringUtils.isBlank(businessCode) || businessCode.length() > 16) {
			response.getWriter().print(FastJsonUtil.error(-1, "业务简称长度不合法"));
			return;
		}
		
		JSONObject param = JSONObject.parseObject("{}");
		param.put("business_id", businessId);
		param.put("business_name", businessName);
		param.put("business_code", businessCode);
		
		logger.debug("update business info: " + param.toString());
		
		try {
			JSONObject result = manageBusinessService.updateBusinessById(param.toString());
			response.getWriter().print(result.toString());
		} catch(Exception e) {
			response.getWriter().print(FastJsonUtil.error("-1", "处理异常"));
			logger.error("Failed to update business", e);
		}
		
	}
	
	@RequestMapping(params = "method=delete_business")
	public void deleteBusiness(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		
		try {
			int businessId = RequestHelper.getInteger(request, "business_id");
			JSONObject jsonObject = manageBusinessService.deleteBusinessById(businessId);
			logger.debug(jsonObject.toString());
			response.getWriter().print(jsonObject.toString());
		} catch(Exception e) {
			response.getWriter().print(FastJsonUtil.error("-1", "处理异常"));
			logger.error("Failed to delete business", e);
		}
		response.setStatus(200);
	}
}
