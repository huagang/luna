package ms.luna.web.control;

import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.model.MsUser;
import ms.luna.biz.sc.ManageRouteService;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.web.common.BasicCtrl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.config.FastJsonConfig;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-11
 */

@Controller("manageRouterCtrl")
@RequestMapping("/manage_router.do")
public class ManageRouterCtrl extends BasicCtrl {

//	private static Logger logger = Logger.getLogger(ManageRouterCtrl.class);
	
	@Autowired
	ManageRouteService manageRouteService;

    private static final String INIT = "method=init";//初始化
    private static final String EDIT_ROUTER = "method=edit_router_page";// 线路编辑页面初始化
    private static final String ASYNC_SEARCH_ROUTES = "method=async_search_routes";// 搜索线路
    private static final String EDIT_ROUTE = "method=edit_route";//线路属性编辑
    private static final String DEL_ROUTE = "method=del_route"; //删除线路
    private static final String CREATE_ROUTE = "method=create_route"; // 创建线路
    private static final String CHECK_ROUTE_NM = "method=check_route_nm"; // 检测线路名称是否合法
    
    @RequestMapping(params = INIT)
    public ModelAndView init(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.setAttribute("menu_selected", "manage_router");
        }
        ModelAndView modelAndView = buildModelAndView("/manage_router");

        return modelAndView;
    }
    
    @RequestMapping(params = CREATE_ROUTE)
    @ResponseBody
    public JSONObject createRoute(
    		@RequestParam(required=true, value="name") String name,
    		@RequestParam(required=true, value="description") String description,
    		@RequestParam(required=true, value="cover") String cover,
    		@RequestParam(required=true, value="cost_id") int cost_id,
    		@RequestParam(required=true, value="business_id") int business_id,
    		HttpServletRequest request, HttpServletResponse response){
    	
    	try{
    		HttpSession session = request.getSession(false);
			if(session == null){
				throw new RuntimeException("session is null");
			}
    		MsUser msUser = (MsUser)session.getAttribute("msUser");
    		String unique_id = msUser.getUniqueId();
    		JSONObject param = new JSONObject();
    		param.put("name", name);
    		param.put("description", description);
    		param.put("cover", cover);
    		param.put("cost_id", cost_id);
    		param.put("business_id", business_id);
    		param.put("unique_id", unique_id);
    		
    		// 异常会怎么处理?
    		JSONObject result = manageRouteService.createRoute(param);
    		MsLogger.debug(result.toString());
    		return result;
    		
    	} catch (Exception e){
			MsLogger.error("Failed to create route: " + e.getMessage());
    		return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to create route");
    	}
    }
    
    @RequestMapping(params = EDIT_ROUTE)
    @ResponseBody
    public JSONObject editRoute(
    		@RequestParam(required=true, value="id") Integer id,
    		@RequestParam(required=true, value="name") String name,
    		@RequestParam(required=true, value="description") String description,
    		@RequestParam(required=true, value="cost_id") Integer cost_id,
    		@RequestParam(required=true, value="cover") String cover,
    		HttpServletRequest request, HttpServletResponse response) {
    	
    	try{
    		HttpSession session = request.getSession(false);
			if(session == null){
				throw new RuntimeException("session is null");
			}
    		MsUser msUser = (MsUser)session.getAttribute("msUser");
    		String unique_id = msUser.getUniqueId();
    		
    		JSONObject param = new JSONObject();
    		param.put("id", id);
    		param.put("name", name);
    		param.put("description", description);
    		param.put("cost_id", cost_id);
    		param.put("cover", cover);
    		param.put("unique_id", unique_id);
    		
    		JSONObject result = manageRouteService.editRoute(param);
    		MsLogger.debug(result.toString());
    		return result;
    	} catch (Exception e) {
			MsLogger.error("Failed to edit route: " + e.getMessage());
    		return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "线路属性编辑失败"+e.getMessage());
    	}
    }
    
    @RequestMapping(params = DEL_ROUTE)
    @ResponseBody
    public JSONObject delRoute(
    		@RequestParam(required=true, value="id") Integer id,
    		HttpServletRequest request, HttpServletResponse response){
    	try{
	    	JSONObject param = new JSONObject();
	    	param.put("id", id);
	    	
	    	JSONObject result = manageRouteService.delRoute(id);
	    	MsLogger.debug(result.toString());
	    	return result;
    	} catch (Exception e){
			MsLogger.error("Failed to delete route: " + e.getMessage() );
    		return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "线路删除失败"+e.getMessage());
    	}
    }
    
    @RequestMapping(params = ASYNC_SEARCH_ROUTES)
    @ResponseBody
    public JSONObject async_search_routes(
    		@RequestParam(required=false, value="offset", defaultValue = "0") Integer offset,
    		@RequestParam(required=false, value="limit", defaultValue = "" + Integer.MAX_VALUE) Integer limit,
    		HttpServletRequest request, HttpServletResponse response){
    	try {
    		JSONObject param = new JSONObject();
			param.put("offset", offset);
			param.put("limit", limit);
    		JSONObject result = manageRouteService.loadRoutes(param);
    		MsLogger.debug(result.toString());
    		return result;
    	} catch (Exception e) {
			MsLogger.error("Failed to search routes: " + e.getMessage());
    		return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to search routes.");
    	}
    }
    
    @RequestMapping(params = CHECK_ROUTE_NM)
    @ResponseBody  
    public JSONObject checkRouteNm(
    		@RequestParam(required=true, value="name") String name,
    		@RequestParam(required=false, value="id") Integer id,

    		HttpServletRequest request, HttpServletResponse response) {
    	try {
    		JSONObject param = new JSONObject();
    		param.put("name", name);
			if(id != null) {
				param.put("id" , id);
			}
    		
    		JSONObject result = manageRouteService.isRouteNmExist(param.toString());
    		MsLogger.debug(result.toString());
    		return result;
    	} catch (Exception e) {
			MsLogger.error("Failed to check route name: " + e.getMessage());
    		return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to check route name.");
    	}
    }

	@RequestMapping(params = EDIT_ROUTER)
	public ModelAndView initRouteConfigurationPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.setAttribute("menu_selected", "manage_router");
		}
		ModelAndView modelAndView = buildModelAndView("/manage_router_edit");

		return modelAndView;
	}
}
