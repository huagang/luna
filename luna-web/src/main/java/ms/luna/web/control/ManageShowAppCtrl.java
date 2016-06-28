package ms.luna.web.control;

import java.io.IOException;
import java.net.URLDecoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.tools.ant.taskdefs.condition.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import org.apache.commons.lang.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;

import ms.luna.biz.model.MsUser;
import ms.luna.biz.sc.ManageShowAppService;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.web.common.AreaOptionQueryBuilder;
import ms.luna.web.common.BasicCtrl;
import ms.luna.web.common.PulldownCtrl;
import ms.luna.web.util.RequestHelper;

/**
 * 
 * Copyright (C) 2015 - 2016 Microscene Inc., All Rights Reserved.
 *
 * @author: shawn@visualbusiness.com
 * @Date: Apr 6, 2016
 *
 */

@Component
@Controller
@RequestMapping("/manage/app.do")
public class ManageShowAppCtrl extends BasicCtrl {
	
	private final static Logger logger = Logger.getLogger(ManageShowAppCtrl.class);

	private final static String INIT = "method=init";
	private final static String CREATE_APP = "method=create_app";
	private final static String UPDATE_APP = "method=update_app";
	private final static String DELETE_APP = "method=delete_app";
	private final static String SEARCH_APP = "method=async_search_apps";
	private final static String SEARCH_BUSINESS = "method=search_business";
	private final static String COPY_APP = "method=copy_app";


	@Autowired
	private ManageShowAppService manageShowAppService;
	
	@Resource(name="pulldownCtrl")
	private PulldownCtrl pulldownCtrl;

	/**
	 * 分类页面初始化
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = INIT)
	public ModelAndView init(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.setAttribute("menu_selected", "manage_app");
			}
			ModelAndView modelAndView = buildModelAndView("/manage_app");
			modelAndView.addObject("provinces", pulldownCtrl.loadProvinces());
			modelAndView.addObject("businessCategories", pulldownCtrl.loadBusinessCategories());
			return modelAndView;
			
		} catch (Exception e) {
			logger.error("Failed to initialize", e);
		}
		return buildModelAndView("/error");
		
	}
	
	private boolean isValidName(String appName) {
		
		if(StringUtils.isEmpty(appName)) {
			return false;
		}
		
		return appName.length() <= 16;
	}
	
	@RequestMapping(params = CREATE_APP)
	public void createApp(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		
		String appName = RequestHelper.getString(request, "app_name");
		int businessId = RequestHelper.getInteger(request, "business_id");
		if(businessId < 0) {
			response.getWriter().print("业务id不合法");
			return;
		}
		if(! isValidName(appName)) {
			response.getWriter().print("业务名称不合法");
			return;
		}
		HttpSession session = request.getSession(false);
		MsUser msUser = (MsUser)session.getAttribute("msUser");
		String owner = msUser.getNickName();
		
		JSONObject jsonObject = JSONObject.parseObject("{}");
		jsonObject.put("app_name", appName);
		jsonObject.put("business_id", businessId);
		jsonObject.put("owner", owner);
		JSONObject result = manageShowAppService.createApp(jsonObject.toString());
		
		response.getWriter().print(result.toString());
		
	}
	
	@RequestMapping(params = UPDATE_APP)
	public void updateApp(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		
		int appId = RequestHelper.getInteger(request, "app_id");
		String appName = RequestHelper.getString(request, "app_name");
		int businessId = RequestHelper.getInteger(request, "business_id");
		if(businessId < 0) {
			response.getWriter().print(FastJsonUtil.error(-1, "业务Id不合法"));
			return;
		}
		if(! isValidName(appName)) {
			response.getWriter().print("业务名称不合法");
			return;
		}
		HttpSession session = request.getSession(false);
		MsUser msUser = (MsUser)session.getAttribute("msUser");
		String owner = msUser.getNickName();
		
		JSONObject jsonObject = JSONObject.parseObject("{}");
		jsonObject.put("app_id", appId);
		jsonObject.put("app_name", appName);
		jsonObject.put("business_id", businessId);
		jsonObject.put("update_user", owner);
		JSONObject result = manageShowAppService.updateApp(jsonObject.toString());
		
		response.getWriter().print(result.toString());
	}
	
	@RequestMapping(params = DELETE_APP)
	public void deleteApp(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		
		int appId = RequestHelper.getInteger(request, "app_id");
		JSONObject jsonObject = JSONObject.parseObject("{}");
		jsonObject.put("app_id", appId);
		JSONObject result = manageShowAppService.deleteApp(jsonObject.toString());
		
		response.getWriter().print(result.toString());
	}

	@RequestMapping(params = SEARCH_APP)
	public void asyncSearchApps(@RequestParam(required = false) Integer offset,
			@RequestParam(required = false) Integer limit, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

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
			String keyword = RequestHelper.getString(request, "like_filter_nm");
			if(keyword != null) {
				keyword = URLDecoder.decode(keyword, "utf-8");
				param.put("keyword", keyword.trim());
			}
			
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
		
		response.getWriter().print(resJSON.toString());
		response.setStatus(200);
	}
	
	@RequestMapping(params = SEARCH_BUSINESS)
	public void searchBusiness(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		
		AreaOptionQueryBuilder.Builder builder = AreaOptionQueryBuilder.builder();
		builder.newStringParam("province_id", request.getParameter("province_id"));
		builder.newStringParam("city_id", request.getParameter("city_id"));
		builder.newStringParam("county_id", request.getParameter("county_id"));
		builder.newStringParam("category_id", request.getParameter("category_id"));
		builder.newStringParam("keyword", request.getParameter("keyword"));
		
		JSONObject param = builder.buildJsonQuery();
		JSONObject result = manageShowAppService.searchBusiness(param.toString());
		response.getWriter().print(result.toString());
		response.setStatus(200);		
		
	}

	@RequestMapping(params = COPY_APP)
	public void copyApp(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");

		String appName = RequestHelper.getString(request, "app_name");
		int businessId = RequestHelper.getInteger(request, "business_id");
		int sourceAppId = RequestHelper.getInteger(request, "source_app_id");
		if(businessId < 0) {
			response.getWriter().print("业务id不合法");
			return;
		}
		if(! isValidName(appName)) {
			response.getWriter().print("业务名称不合法");
			return;
		}
		if(sourceAppId < 0) {
			response.getWriter().print("源微景展不合法");
			return;
		}

		HttpSession session = request.getSession(false);
		MsUser msUser = (MsUser)session.getAttribute("msUser");
		String owner = msUser.getNickName();

		JSONObject jsonObject = JSONObject.parseObject("{}");
		jsonObject.put("app_name", appName);
		jsonObject.put("business_id", businessId);
		jsonObject.put("source_app_id", sourceAppId);
		jsonObject.put("owner", owner);
		JSONObject result = manageShowAppService.copyApp(jsonObject.toString());

		response.getWriter().print(result.toString());

	}
	
}
