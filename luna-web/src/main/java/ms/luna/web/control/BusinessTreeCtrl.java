package ms.luna.web.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import ms.luna.biz.model.MsUser;
import ms.luna.biz.sc.ManageBusinessTreeService;
import ms.luna.biz.sc.ManagePoiService;
import ms.luna.biz.util.CharactorUtil;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.web.common.PulldownCtrl;
import ms.luna.web.model.common.SimpleModel;

/**
 * @author Mark
 */

@Component
@Controller
@RequestMapping("/business_tree.do")
public class BusinessTreeCtrl {
	@Autowired
	private ManageBusinessTreeService manageBusinessTreeService;

	@Autowired
	private ManagePoiService managePoiService;

	@Resource(name="pulldownCtrl")
	private PulldownCtrl pulldownCtrl;

	@RequestMapping(params = "method=init")
	public ModelAndView init(
			@RequestParam(required = true, value="business_id")
			Integer businessId,
			@RequestParam(required = false, value="province_id")
			String provinceId,
			@RequestParam(required = false, value="city_id")
			String cityId,
			@RequestParam(required = false, value="county_id")
			String countyId,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView();
		try {

			// 省份列表
			view.addObject("provinces", pulldownCtrl.loadProvinces());

			// 城市信息
			if (!CharactorUtil.isEmpyty(provinceId)) {
				view.addObject("citys", pulldownCtrl.loadCitys(provinceId));
			} else {
				view.addObject("citys", new ArrayList<SimpleModel>());
			}

			// 区/县信息
			if (!CharactorUtil.isEmpyty(cityId)) {
				view.addObject("countys", pulldownCtrl.loadCountys(cityId));
			} else {
				view.addObject("countys", new ArrayList<SimpleModel>());
			}

			// 下拉菜单的初始值
			view.addObject("provinceId", provinceId);
			view.addObject("cityId", cityId);
			view.addObject("countyId", countyId);
			com.alibaba.fastjson.JSONObject result = managePoiService.getTagsDef("{}");
			if ("0".equals(result.getString("code"))) {
				com.alibaba.fastjson.JSONObject data = result.getJSONObject("data");
				com.alibaba.fastjson.JSONArray tags = data.getJSONArray("tags_def");
				List<SimpleModel> lstTopTags = new ArrayList<SimpleModel>();
				for (int i = 0; i < tags.size(); i++) {
					com.alibaba.fastjson.JSONObject tag = tags.getJSONObject(i);
					SimpleModel simpleModel = new SimpleModel();
					simpleModel.setValue(tag.getString("tag_id"));
					simpleModel.setLabel(tag.getString("tag_name"));
					lstTopTags.add(simpleModel);
				}
				view.addObject("topTags", lstTopTags);
				view.addObject("businessId", businessId);
				view.setViewName("/business_tree.jsp");
				return view;
			} else {
				view.setViewName("/error.jsp");
				return view;
			}
		} catch (Exception e) {
			MsLogger.error(e);
		}
		view.setViewName("/error.jsp");
		return view;
	}

	@RequestMapping(params = "method=view_business_tree")
	public void viewBusinessTree(
			@RequestParam(required = true, value="business_id")
			Integer businessId,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		MsLogger.info("viewBusinessTree start....");
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/html; charset=UTF-8");

			JSONObject param = JSONObject.parseObject("{}");
			param.put("businessId", businessId);

			JSONObject result = manageBusinessTreeService.viewBusinessTree(param.toString());
			MsLogger.info(result.toJSONString());
			response.getWriter().print(result.toJSONString());
			response.setStatus(200);
			return;
		} catch (Exception e) {
			MsLogger.error("Failed to load Business Trees", e);
			response.getWriter().print(FastJsonUtil.error("-1", e.getMessage()));
			response.setStatus(200);
			return;
		}
	}

	@RequestMapping(params = "method=searchPoisForBizTree")
	public void searchPoisForBizTree(
			@RequestParam(required = false) Integer offset,
			@RequestParam(required = false) Integer limit,
			@RequestParam(required = true, value="province_id") String provinceId,
			@RequestParam(required = false, value="city_id") String cityId,
			@RequestParam(required = false, value="county_id") String countId,
			@RequestParam(required = false, value="keyWord") String keyWord,
			@RequestParam(required = false, value="tag_id") String tagId,

			HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		MsLogger.info("asyncSearchPoiList start....");
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/html; charset=UTF-8");

			JSONObject param = JSONObject.parseObject("{}");
			param.put("offset", offset);
			param.put("limit", limit);
			param.put("provinceId", provinceId);
			param.put("cityId", cityId);
			param.put("countyId", countId);
			param.put("keyWord", keyWord);
			param.put("tagId", tagId);

			JSONObject result = manageBusinessTreeService.searchPoisForBizTree(param.toString());
			MsLogger.info(result.toJSONString());
			response.getWriter().print(result.toJSONString());
			response.setStatus(200);
			return;
		} catch (Exception e) {
			MsLogger.error("Failed to search pois", e);
			response.getWriter().print(FastJsonUtil.error("-1", e));
			response.setStatus(200);
			return;
		}
	}

	@RequestMapping(params = "method=saveBusinessTree")
	public void saveBusinessTree(
			@RequestParam(required = true, value="businessTree") String businessTree,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		MsLogger.info("saveBusinessTree start....");
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/html; charset=UTF-8");

			JSONObject param = JSON.parseObject(businessTree);
			
			HttpSession session = request.getSession(false);
			MsUser msUser = (MsUser)session.getAttribute("msUser");

			JSONObject result = manageBusinessTreeService.saveBusinessTree(param.toString(), msUser);
			MsLogger.info(result.toJSONString());
			response.getWriter().print(result.toJSONString());
			response.setStatus(200);
			return;
		} catch (Exception e) {
			MsLogger.error("Failed to save Business Tree", e);
			response.getWriter().print(FastJsonUtil.error("-1", e));
			response.setStatus(200);
			return;
		}
	}
//
//	@RequestMapping(params = "method=async_search_businesses")
//	public void asyncSearchBusinesses(
//			@RequestParam(required = true, value="province_id") String provinceId,
//			@RequestParam(required = false, value="city_id") String cityId,
//			@RequestParam(required = false, value="county_id") String countId,
//			@RequestParam(required = false, value="business_tree_name") String keyWord,
//			HttpServletRequest request,
//			HttpServletResponse response) throws IOException {
//
//		MsLogger.info("async_search_businesses start....");
//		try {
//			response.setHeader("Access-Control-Allow-Origin", "*");
//			response.setContentType("text/html; charset=UTF-8");
//
//			JSONObject param = JSONObject.parseObject("{}");
//			param.put("provinceId", provinceId);
//			param.put("cityId", cityId);
//			param.put("countyId", countId);
//			param.put("keyWord", keyWord);
//
//			JSONObject result = manageBusinessTreeService.loadBusinesses(param.toString());
//			MsLogger.info(result.toString());
//			response.getWriter().print(result.toString());
//			response.setStatus(200);
//			return;
//		} catch (Exception e) {
//			MsLogger.error("Failed to load Businessees", e);
//		}
//
//		response.getWriter().print(FastJsonUtil.error(-1, "inner err"));
//		response.setStatus(200);
//		return;
//	}
//
//	@RequestMapping(params = "method=async_create_business_tree")
//	public void asyncCreateBusinessTree(
//			@RequestParam(required = true, value="business_id")
//			Integer businessId,
//			HttpServletRequest request,
//			HttpServletResponse response) throws IOException {
//
//		MsLogger.info("async_search_businesses start....");
//		try {
//			response.setHeader("Access-Control-Allow-Origin", "*");
//			response.setContentType("text/html; charset=UTF-8");
//
//			JSONObject param = JSONObject.parseObject("{}");
//			param.put("businessId", businessId);
//			HttpSession session = request.getSession(false);
//			MsUser msUser = (MsUser)session.getAttribute("msUser");
//			JSONObject result = manageBusinessTreeService.createBusinessTree(param.toString(), msUser);
//
//			response.getWriter().print(result.toString());
//			response.setStatus(200);
//			return;
//		} catch (Exception e) {
//			MsLogger.error("Failed to create Business Tree", e);
//		}
//
//		response.getWriter().print(FastJsonUtil.error(-1, "inner err"));
//		response.setStatus(200);
//		return;
//	}
//
////	@RequestMapping(params = "method=create_business")
////	public void createBusiness(HttpServletRequest request, HttpServletResponse response) throws IOException {
////		
////		try {
////			response.setHeader("Access-Control-Allow-Origin", "*");
////			response.setContentType("text/html; charset=UTF-8");
////
////			HttpSession session = request.getSession(false);
////			MsUser msUser = (MsUser)session.getAttribute("msUser");
////
////			String businessName = RequestHelper.getString(request, "business_name");
////			if(StringUtils.isBlank(businessName) || businessName.length() > 32) {
////				response.getWriter().print(FastJsonUtil.error(-1, "业务名称长度不合法"));
////				return;
////			}
////			String businessCode = RequestHelper.getString(request, "business_code");
////			if(StringUtils.isBlank(businessCode) || businessCode.length() > 16) {
////				response.getWriter().print(FastJsonUtil.error(-1, "业务简称长度不合法"));
////				return;
////			}
////			String merchantId = RequestHelper.getString(request, "merchant_id");
////			if(StringUtils.isBlank(merchantId)) {
////				response.getWriter().print(FastJsonUtil.error(-1, "商户不能为空"));
////				return;
////			}
////			
////			String createUser = msUser.getUniqueId();
////			JSONObject param = JSONObject.parseObject("{}");
////			param.put("business_name", businessName);
////			param.put("business_code", businessCode);
////			param.put("merchant_id", merchantId);
////			param.put("create_user", createUser);
////			// manageAppService
////			JSONObject result = manageBusinessService.createBusinessTree(param.toString());
////			
////			if (!"0".equals(result.getString("code"))) {
////				response.getWriter().print(FastJsonUtil.error("-1", result.getString("msg")));
////			} else {
////				response.getWriter().print(result.toString());
////			}
////			response.setStatus(200);
////		} catch (Exception e) {
////			response.getWriter().print(FastJsonUtil.error("-1", "处理异常"));
////			MsLogger.error("Failed to create business", e);
////			response.setStatus(200);
////		}
////	}
//	
////	@RequestMapping(params = "method=search_merchant")
////	public void searchMerchant(HttpServletRequest request, HttpServletResponse response) throws IOException {
////	
////		response.setHeader("Access-Control-Allow-Origin", "*");
////		response.setContentType("text/html; charset=UTF-8");
////		
////		try {
////			AreaOptionQueryBuilder.Builder builder = AreaOptionQueryBuilder.builder();
////			builder.newStringParam("province_id", request.getParameter("province_id"));
////			builder.newStringParam("city_id", request.getParameter("city_id"));
////			builder.newStringParam("county_id", request.getParameter("county_id"));
////			builder.newStringParam("keyword", request.getParameter("keyword"));
////			
////			JSONObject jsonObject = builder.buildJsonQuery();
////			JSONObject result = manageBusinessService.searchMerchant(jsonObject.toString());
////		
////			if (!"0".equals(result.getString("code"))) {
////				response.getWriter().print(FastJsonUtil.error("-1", result.getString("msg")));
////			} else {
////				response.getWriter().print(result.toString());
////			}
////			response.setStatus(200);
////		} catch (Exception e) {
////			response.getWriter().print(FastJsonUtil.error("-1", "处理异常"));
////			MsLogger.error("Failed to search merchant", e);
////			response.setStatus(200);
////		}
////	
////	}
////	
////	@RequestMapping(params = "method=update_business")
////	public void updateBusiness(HttpServletRequest request, HttpServletResponse response) throws IOException {
////		response.setHeader("Access-Control-Allow-Origin", "*");
////		response.setContentType("text/html; charset=UTF-8");
////		response.setStatus(200);
////		int businessId = RequestHelper.getInteger(request, "business_id");
////		if(businessId < 0) {
////			response.getWriter().print(FastJsonUtil.error("-1", "非法业务Id"));
////			return;
////		}
////		
////		String businessName = RequestHelper.getString(request, "business_name");
////		String businessCode = RequestHelper.getString(request, "business_code");
////		if(StringUtils.isBlank(businessName) || businessName.length() > 32) {
////			response.getWriter().print(FastJsonUtil.error(-1, "业务名称长度不合法"));
////			return;
////		}
////		if(StringUtils.isBlank(businessCode) || businessCode.length() > 16) {
////			response.getWriter().print(FastJsonUtil.error(-1, "业务简称长度不合法"));
////			return;
////		}
////		
////		JSONObject param = JSONObject.parseObject("{}");
////		param.put("business_id", businessId);
////		param.put("business_name", businessName);
////		param.put("business_code", businessCode);
////		
////		MsLogger.debug("update business info: " + param.toString());
////		
////		try {
////			JSONObject result = manageBusinessService.updateBusinessById(param.toString());
////			response.getWriter().print(result.toString());
////		} catch(Exception e) {
////			response.getWriter().print(FastJsonUtil.error("-1", "处理异常"));
////			MsLogger.error("Failed to update business", e);
////		}
////		
////	}
////	
//	@RequestMapping(params = "method=delete_business_tree")
//	public void asyncDeleteBusinessTree(
//			@RequestParam(required = true, value="business_id")
//			Integer businessId,
//			HttpServletRequest request, HttpServletResponse response) throws IOException {
//		response.setHeader("Access-Control-Allow-Origin", "*");
//		response.setContentType("text/html; charset=UTF-8");
//		try {
//			JSONObject json = new JSONObject();
//			json.put("businessId", businessId);
//			JSONObject jsonObject = manageBusinessTreeService.deleteBusinessTree(json.toJSONString());
//			MsLogger.debug(jsonObject.toString());
//			response.getWriter().print(jsonObject.toString());
//		} catch(Exception e) {
//			response.getWriter().print(FastJsonUtil.error("-1", "处理异常"));
//			MsLogger.error("Failed to delete business", e);
//		}
//		response.setStatus(200);
//	}
}
