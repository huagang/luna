package ms.luna.web.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ms.luna.biz.sc.PulldownService;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.biz.util.VbUtility;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
@Component("pulldownCtrl")
@Controller
@RequestMapping("/pulldown.do")
public class PulldownCtrl {

	private final static Logger logger = Logger.getLogger(PulldownCtrl.class);
	private final static String COUNTRY_ID_CHINA = "100000";
	
	@Autowired
	private PulldownService pulldownService;

	private static List<Map<String, String>> lstProvinces = new ArrayList<Map<String, String>>();
	
	private static Map<String, List<Map<String, String>>> lstCitys = new LinkedHashMap<String, List<Map<String, String>>>();
	
	private static Map<String, List<Map<String, String>>> lstCountys = new LinkedHashMap<String, List<Map<String, String>>>();

	private static List<Map<String, String>> lstCategorys = new ArrayList<Map<String, String>>();

	private Map<String, JSONObject> cityMap = new LinkedHashMap<String, JSONObject>();
	private Map<String, JSONObject> countyMap = new TreeMap<>();
	
	public PulldownCtrl() {
	}

	public List<Map<String, String>> loadProvinces() throws Exception {
		if (!lstProvinces.isEmpty()) {
			return lstProvinces;
		}
		synchronized (PulldownCtrl.class) {
			if (lstProvinces.isEmpty()) {
				JSONObject result = pulldownService.loadProvnices(COUNTRY_ID_CHINA);
				JSONObject data = result.getJSONObject("data");
				JSONArray arrays = data.getJSONArray("provinces");
				for (int i = 0; i < arrays.size(); i++) {
					JSONObject provinceJson = arrays.getJSONObject(i);

					Map<String, String> province = new LinkedHashMap<String, String>();
					province.put("province_id", provinceJson.getString("province_id"));
					province.put("province_nm_zh", provinceJson.getString("nm_zh"));
					lstProvinces.add(province);
				}
			}
		}
		return lstProvinces;
	}

	public List<Map<String, String>> loadCitys(String provinceId) throws Exception {
		List<Map<String, String>> lstCity = lstCitys.get(provinceId);
		if (lstCity != null) {
			return lstCity;
		}
		synchronized (PulldownCtrl.class) {
			lstCity = lstCitys.get(provinceId);
			if (lstCity == null) {
				JSONObject result = pulldownService.loadCitys(provinceId);
				JSONObject data = result.getJSONObject("data");
				JSONArray arrays = data.getJSONArray("citys");
				List<Map<String, String>> citysOfProvince = new ArrayList<Map<String, String>>();
				for (int i = 0; i < arrays.size(); i++) {
					JSONObject cityJson = arrays.getJSONObject(i);

					Map<String, String> city = new LinkedHashMap<String, String>();
					city.put("city_id", cityJson.getString("city_id"));
					city.put("city_nm_zh", cityJson.getString("nm_zh"));
					citysOfProvince.add(city);
				}
				lstCitys.put(provinceId, citysOfProvince);
			} else {
				return lstCity;
			}
		}
		return lstCitys.get(provinceId);
	}
	
	public List<Map<String, String>> loadCountys(String cityId) throws Exception {
		List<Map<String, String>> lstCounty = lstCountys.get(cityId);
		if (lstCounty != null) {
			return lstCounty;
		}
		synchronized (PulldownCtrl.class) {
			lstCounty = lstCountys.get(cityId);
			if (lstCounty == null) {
				JSONObject result = pulldownService.loadCounties(cityId);
				JSONObject data = result.getJSONObject("data");
				JSONArray arrays = data.getJSONArray("counties");
				List<Map<String, String>> countysOfCity = new ArrayList<Map<String, String>>();
				for (int i = 0; i < arrays.size(); i++) {
					JSONObject cityJson = arrays.getJSONObject(i);

					Map<String, String> county = new LinkedHashMap<String, String>();
					county.put("county_id", cityJson.getString("county_id"));
					county.put("county_nm_zh", cityJson.getString("nm_zh"));
					countysOfCity.add(county);
				}
				lstCountys.put(cityId, countysOfCity);
			} else {
				return lstCounty;
			}
		}
		return lstCountys.get(cityId);
	}

	public List<Map<String, String>> loadCategorys() throws Exception {
		if (!lstCategorys.isEmpty()) {
			return lstCategorys;
		}
		synchronized (PulldownCtrl.class) {
			if (lstCategorys.isEmpty()) {
				JSONObject result = pulldownService.loadCategorys();
				JSONObject data = result.getJSONObject("data");
				JSONArray arrays = data.getJSONArray("categorys");
				for (int i = 0; i < arrays.size(); i++) {
					JSONObject categoryJson = arrays.getJSONObject(i);
					Map<String, String> category = new LinkedHashMap<String, String>();
					category.put("category_id", categoryJson.getString("category_id"));
					category.put("category_nm", categoryJson.getString("nm_zh"));
					lstCategorys.add(category);
				}
			}
		}
		return lstCategorys;
	}
	
	/**
	 * 加载所有的业务类别，返回Map(业务类别id->业务类别中文名称)
	 * 业务类别跟地区无关，不需要跟随地区选择联动
	 * @return
	 */
	public Map<String, String> loadBusinessCategories() {
		
		Map<String, String> businessCategory = new HashMap<String, String>();
		JSONObject jsonObject = pulldownService.loadCategorys();
		JSONObject data = jsonObject.getJSONObject("data");
		JSONArray categories = data.getJSONArray("categorys");
		for(int i = 0; i < categories.size(); i++) {
			JSONObject category = (JSONObject) categories.get(i);
			businessCategory.put(category.getString("category_id"), category.getString("nm_zh"));
		}
		
		return businessCategory;
	}

//	public List<Map<String, String>> loadRoles() throws Exception {
//		if (!lstRoles.isEmpty()) {
//			return lstRoles;
//		}
//		synchronized (PulldownCtrl.class) {
//			if (lstRoles.isEmpty()) {
//				JSONObject param = JSONObject.parseObject("{}");
//				param.put("login_wjnm", "dummy");
//				JSONObject result = pulldownService.loadRoles(param.toString());
//				JSONObject data = result.getJSONObject("data");
//				JSONArray arrays = data.getJSONArray("roles");
//				for (int i = 0; i < arrays.size(); i++) {
//					JSONObject roleJson = arrays.getJSONObject(i);
//					Map<String, String> role = new LinkedHashMap<String, String>();
//					role.put("role_auth", roleJson.getString("role_auth"));
//					role.put("role_id", roleJson.getString("role_id"));
//					role.put("role_nm", roleJson.getString("role_nm"));
//					lstRoles.add(role);
//				}
//			}
//		}
//		return lstRoles;
//	}
	
	
	private JSONObject loadCities(String provinceId) {
		JSONObject cities = null;
		synchronized (PulldownCtrl.class) {
			cities = cityMap.get(provinceId);
			if (cities == null) {
				try {
					JSONObject jsonObject = pulldownService.loadCitys(provinceId);
					if ("0".equals(jsonObject.getString("code"))) {
						cities = jsonObject;
						cityMap.put(provinceId, cities);
					}
				} catch (Exception e) {
					logger.error("Failed to load cities", e);
				}
			}
		}
		return cities;
	}
	
	private JSONObject loadCounties(String cityId) {
		JSONObject counties = null;
		synchronized (PulldownCtrl.class) {
			counties = countyMap.get(cityId);
			if(counties == null) {
				try{
					counties = pulldownService.loadCounties(cityId);	
					countyMap.put(cityId, counties);
				} catch (Exception e) {
					logger.error("Failed to load counties", e);
				}
			}
		}
		return counties;
	}

	@RequestMapping(params = "method=load_citys")
	public void loadCitys(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String provinceId = request.getParameter("province_id");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");

		if (provinceId == null || provinceId.isEmpty()) {
			try {
				response.getWriter().print(FastJsonUtil.error("-1", "参数不正确！"));
				response.setStatus(200);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
		JSONObject citys = loadCities(provinceId);
		if (citys == null) {
			citys = FastJsonUtil.error("-1", "城市信息检索失败");
		}
		response.getWriter().print(citys.toString());
		response.setStatus(200);
	}
	
	@RequestMapping(params = "method=load_counties")
	public void loadCounties(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		String cityId = request.getParameter("city_id");
		JSONObject counties = loadCounties(cityId);
		if(counties == null) {
			counties = FastJsonUtil.error("-1", "区县信息检索失败");
		}
		response.setStatus(200);
		response.getWriter().print(counties.toString());
//		return new ResponseEntity<String>(countiesObj.toString(), HttpStatus.OK);
	}

	@RequestMapping(params = "method=load_categorys")
	public void loadCategorys(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");

		JSONObject categorys = null;
		try{
			categorys = pulldownService.loadCategorys();
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print(FastJsonUtil.error("-1", VbUtility.printStackTrace(e)));
			response.setStatus(200);
			return;
		}
		MsLogger.debug(categorys.toString());
		response.getWriter().print(categorys.toString());
		response.setStatus(200);
		return;
	}
	
	/**
	 * @param categoryId 类别id
	 * @param nmZH 类别中文名
	 * @param mode 缓存更新模式——更新，删除，添加
	 */
	public void refreshCategorysCache(String categoryId, String nmZH, String mode){
		if(lstCategorys.isEmpty()){	// 如果缓存为空，不需要更新
			return;
		}
		synchronized(PulldownCtrl.class){
			try{
				Iterator<Map<String, String>> iter = lstCategorys.iterator();
				if(mode.equals(REFRESHMODE.UPDATE)){
					while(iter.hasNext()){
						Map<String, String> category = iter.next();
						if(category.containsValue(categoryId)){
							category.put("category_nm", nmZH);// 更新缓存中存储的名字
							break;
						}
					}
				} else if(mode.equals(REFRESHMODE.DELETE)){
					while(iter.hasNext()){
						Map<String, String> category = iter.next();
						if(category.containsValue(categoryId)){
							iter.remove();// 删除缓存中id为categoryId的类别
							break;
						}
					}
				} else if(mode.equals(REFRESHMODE.ADD)){
					Map<String, String> cate = new LinkedHashMap<>();
					cate.put("category_id", categoryId); // 添加一条新的类别到缓存中
					cate.put("category_nm", nmZH);
					lstCategorys.add(cate);
				} 
			} catch(Exception e){
				e.printStackTrace();	// 更新缓存过程中出现异常，清空缓存
				lstCategorys.clear();
			}
		}
	}

	/**
	 * 缓存更新模式——更新，删除，添加
	 */
	public static final class REFRESHMODE {
		public static final String ADD = "add";
		public static final String UPDATE = "update";
		public static final String DELETE = "delete";
	}

	/**
	 * 查找QQ地域名称对应的省、市、县ID
	 * @param json
	 * @return
	 */
	@RequestMapping(params = "method=findZoneIdsWithQQZoneName")
	public void findZoneIdsWithQQZoneName(
			@RequestParam(required=true, value="province")
			String province,
			@RequestParam(required=true, value="city")
			String city,
			@RequestParam(required=true, value="district")
			String county,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");

		JSONObject result = null;
		try{
			JSONObject param = new JSONObject();
			param.put("province", province);
			param.put("city", city);
			param.put("county", county);
			result = pulldownService.findZoneIdsWithQQZoneName(param.toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print(FastJsonUtil.error("-1", e));
			response.setStatus(200);
			return;
		}
		MsLogger.debug(result.toJSONString());
		response.getWriter().print(result.toJSONString());
		response.setStatus(200);
	}

}
