package ms.luna.web.control.content;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.sc.ManageRouteService;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.common.LunaUserSession;
import ms.luna.web.common.SessionHelper;
import ms.luna.web.control.common.BasicController;
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
 * @Date: 2016-07-11
 */

@Controller("routeController")
@RequestMapping("/content/route")
public class RouteController extends BasicController {

//	private static Logger logger = Logger.getLogger(ManageRouterCtrl.class);
	
	@Autowired
	ManageRouteService manageRouteService;

	//初始化
    @RequestMapping(method = RequestMethod.GET, value = "")
    public ModelAndView init(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.setAttribute("menu_selected", "manage_router");
        }
        ModelAndView modelAndView = buildModelAndView("/manage_router");

        return modelAndView;
    }

	// 创建线路
    @RequestMapping(method = RequestMethod.POST, value = "")
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
			LunaUserSession user = SessionHelper.getUser(request.getSession(false));
    		String unique_id = user.getUniqueId();
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

	// 编辑线路
    @RequestMapping(method = RequestMethod.PUT, value = "/{routeId}")
    @ResponseBody
    public JSONObject editRoute(
			@PathVariable("routeId") Integer id,
			@RequestParam(required = true, value = "name") String name,
			@RequestParam(required = true, value = "description") String description,
			@RequestParam(required = true, value = "cost_id") Integer cost_id,
			@RequestParam(required = true, value = "cover") String cover,
			HttpServletRequest request, HttpServletResponse response) {
    	
    	try{
    		HttpSession session = request.getSession(false);
			if(session == null){
				throw new RuntimeException("session is null");
			}
			LunaUserSession user = SessionHelper.getUser(request.getSession(false));
    		String unique_id = user.getUniqueId();
    		
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

	// 删除线路
    @RequestMapping(method = RequestMethod.DELETE, value = "/{routeId}")
    @ResponseBody
    public JSONObject delRoute(
			@PathVariable("routeId") Integer id,
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

	// 搜索线路
    @RequestMapping(method = RequestMethod.GET, value = "/search")
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

	// 检查线路名称
    @RequestMapping(method = RequestMethod.GET, value = "/checkName")
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

	@RequestMapping(method = RequestMethod.GET, value = "/configuration")
	public ModelAndView initRouteConfigurationPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.setAttribute("menu_selected", "manage_router");
		}
		ModelAndView modelAndView = buildModelAndView("/manage_router_edit");

		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/configuration/{routeId}")
	@ResponseBody
	public JSONObject viewRouteConfiguration(@PathVariable("routeId") Integer routeId){
		try {
			JSONObject result = manageRouteService.viewRouteConfiguration(routeId);
			MsLogger.debug(result.toString());
			return result;
		} catch (Exception e) {
			MsLogger.error("Failed to get route configuration: " + e.getMessage() );
			return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to get route configuration");
		}
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/configuration/{routeId}")
	@ResponseBody
	public JSONObject saveRouteConfiguration(
			@PathVariable("routeId") Integer routeId,
			@RequestParam(required = true, value = "data") String data,
			HttpServletRequest request){
		try {
			LunaUserSession user = SessionHelper.getUser(request.getSession(false));

			JSONObject param = new JSONObject();
			param.put("routeId", routeId);
			param.put("data", data);
			param.put("luna_name", user.getNickName());
			JSONObject result = manageRouteService.saveRouteConfiguration(param);
			MsLogger.debug(result.toString());
			return result;
		} catch (Exception e) {
			MsLogger.error("Failed to save route configuration: " + e.getMessage() );
			return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to save route configuration");
		}
	}


}
