package ms.luna.web.control;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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

import ms.luna.biz.model.MsUser;
import ms.luna.biz.sc.ManagePoiService;
import ms.luna.biz.util.CharactorUtil;
import ms.luna.biz.util.JsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.biz.util.VbUtility;
import ms.luna.web.common.BasicCtrl;
import ms.luna.web.common.PulldownCtrl;
import ms.luna.web.model.common.SimpleModel;
import ms.luna.web.model.managepoi.PoiModel;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Component
@Controller
@RequestMapping("/edit_poi.do")
public class EditPoiCtrl extends BasicCtrl{

	@Autowired
	private ManagePoiService managePoiService;

	@Resource(name="pulldownCtrl")
	private PulldownCtrl pulldownCtrl;

	@Resource(name="managePoiCtrl")
	private ManagePoiCtrl managePoiCtrl;

	@Resource(name="addPoiCtrl")
	private AddPoiCtrl addPoiCtrl;

//	/**
//	 * 页面上属性列表
//	 */
//	private List<SimpleModel> checkBoxTags;

	/**
	 * 非共有字段（由程序控制）；共有字段，由维护人员修改代码。
	 */
//	private Map<String, List<TagFieldModel>> tagFieldListMap = new LinkedHashMap<String, List<TagFieldModel>>();

	/**
	 * 页面初始化
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=init")
	public ModelAndView init(
			@RequestParam(required = true, value = "_id")
			String _id,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			MsLogger.error("session is null");
			return new ModelAndView("/error.jsp");
		}
		session.setAttribute("menu_selected", "manage_poi");
		
		ModelAndView mav = new ModelAndView();
		
		PoiModel poiModel = new PoiModel();
		try {
			JSONObject params = new JSONObject();
			params.put("_id", _id);
			JSONObject result = managePoiService.initEditPoi(params.toString());
			MsLogger.debug(result.toString());

			if ("0".equals(result.getString("code"))) {
				JSONObject data = result.getJSONObject("data");
				JSONObject common_fields_val = data.getJSONObject("common_fields_val");
				poiModel.setPoiId(_id);
				poiModel.setLongName(common_fields_val.getString("long_title"));
				poiModel.setShortName(common_fields_val.getString("short_title"));

				// 分类一级菜单
				JSONArray tags_values = common_fields_val.getJSONArray("tags_values");
				if (tags_values.size() > 0) {
					poiModel.setTopTag(tags_values.getInt(0));
				} else {
					poiModel.setTopTag(0);
				}
				// 分类二级菜单
				poiModel.setSubTag(common_fields_val.getInt("subTag"));
				managePoiCtrl.initTags(session, data.getJSONObject("common_fields_def"), poiModel.getTopTag());

//				List<String> checkeds = new ArrayList<String>();
//				for (int i = 0; i < tags_values.size(); i++) {
//					checkeds.add(tags_values.getString(i));
//				}
//				poiModel.setCheckeds(checkeds);

				poiModel.setLat(new BigDecimal(common_fields_val.getDouble("lat")).setScale(6, BigDecimal.ROUND_HALF_UP));
				poiModel.setLng(new BigDecimal(common_fields_val.getDouble("lng")).setScale(6, BigDecimal.ROUND_HALF_UP));

				poiModel.setProvinceId(common_fields_val.getString("province_id"));
				poiModel.setCityId(common_fields_val.getString("city_id"));
				poiModel.setCountyId(common_fields_val.getString("county_id"));

				poiModel.setDetailAddress(common_fields_val.getString("detail_address"));
				poiModel.setBriefIntroduction(common_fields_val.getString("brief_introduction"));
				poiModel.setThumbnail(common_fields_val.getString("thumbnail"));

				// 8.全景数据ID
				poiModel.setPanorama(common_fields_val.getString("panorama"));
				// 9.联系电话
				poiModel.setContact_phone(common_fields_val.getString("contact_phone"));

				JSONArray privateFields = data.getJSONArray("private_fields");
				session.setAttribute("private_fields", privateFields);
			} else {
				mav.setViewName("/error.jsp");
				return mav;
			}

		} catch (Exception e) {
			MsLogger.error(e);
			mav.setViewName("/error.jsp");
			return mav;
		}

		// 省份信息
		List<SimpleModel> lstProvinces = new ArrayList<SimpleModel>();
		try {
			for (Map<String, String> map : pulldownCtrl.loadProvinces()) {
				SimpleModel simpleModel = new SimpleModel();
				simpleModel.setValue(map.get("province_id"));
				simpleModel.setLabel(map.get("province_nm_zh"));
				lstProvinces.add(simpleModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		mav.addObject("provinces", lstProvinces);
		session.setAttribute("provinces", lstProvinces);

		// 城市信息
		List<SimpleModel> lstCitys = new ArrayList<SimpleModel>();
		SimpleModel simpleModel = new SimpleModel();
		simpleModel.setValue("ALL");
		simpleModel.setLabel("请选择市");
		lstCitys.add(simpleModel);
		try {
			if (!"ALL".equals(poiModel.getProvinceId())
					&& !poiModel.getProvinceId().isEmpty()) {
				for (Map<String, String> map : pulldownCtrl.loadCitys(poiModel.getProvinceId())) {
					simpleModel = new SimpleModel();
					simpleModel.setValue(map.get("city_id"));
					simpleModel.setLabel(map.get("city_nm_zh"));
					lstCitys.add(simpleModel);
				}
			}
		} catch (Exception e) {
			MsLogger.error(e);
			mav.setViewName("/error.jsp");
			return mav;
		}
		session.setAttribute("citys", lstCitys);

		// 区/县信息
		List<SimpleModel> lstCountys = new ArrayList<SimpleModel>();
		simpleModel = new SimpleModel();
		simpleModel.setValue("ALL");
		simpleModel.setLabel("请选择区/县");
		lstCountys.add(simpleModel);

		try {
			if (!"ALL".equals(poiModel.getCityId())
					&& !poiModel.getCityId().isEmpty()) {
				for (Map<String, String> map : pulldownCtrl.loadCountys(poiModel.getCityId())) {
					simpleModel = new SimpleModel();
					simpleModel.setValue(map.get("county_id"));
					simpleModel.setLabel(map.get("county_nm_zh"));
					lstCountys.add(simpleModel);
				}
			}
		} catch (Exception e) {
			MsLogger.error(e);
			mav.setViewName("/error.jsp");
			return mav;
		}
		session.setAttribute("countys", lstCountys);
		mav.setViewName("/edit_poi.jsp");
		mav.addObject("poiReadOnly", Boolean.FALSE);
		mav.addObject("poiModel", poiModel);
		return mav;
	}

	/**
	 * 页面初始化
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(params = "method=initOfPopupFixPoi")
	public ModelAndView initOfPopupFixPoi(
			@RequestParam(required = true, value="dataPoi") String unSavedPoi,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			MsLogger.error("session is null");
			return new ModelAndView("/error.jsp");
		}
		session.setAttribute("menu_selected", "manage_poi");

		PoiModel poiModel = new PoiModel();
		try {
			unSavedPoi = unSavedPoi.replace("\n", "\\n").replace("\r", "\\r");
			JSONObject.fromObject(unSavedPoi);

			JSONObject result = managePoiService.initFixPoi(unSavedPoi);
			MsLogger.debug(result.toString());

			if ("0".equals(result.getString("code"))) {
				JSONObject data = result.getJSONObject("data");
				JSONObject common_fields_val = data.getJSONObject("common_fields_val");
				poiModel.setLongName(common_fields_val.getString("long_title"));
				poiModel.setShortName(common_fields_val.getString("short_title"));

				// 分类一级菜单
				JSONArray tags_values = common_fields_val.getJSONArray("tags_values");
				if (tags_values.size() > 0) {
					poiModel.setTopTag(tags_values.getInt(0));
				} else {
					poiModel.setTopTag(0);
				}
				// 分类二级菜单
				poiModel.setSubTag(common_fields_val.getInt("subTag"));
				managePoiCtrl.initTags(session, data.getJSONObject("common_fields_def"), poiModel.getTopTag());

//				JSONArray tags_values = common_fields_val.getJSONArray("tags_values");
//				List<String> checkeds = new ArrayList<String>();
//				for (int i = 0; i < tags_values.size(); i++) {
//					checkeds.add(tags_values.getString(i));
//				}
//				poiModel.setCheckeds(checkeds);

				poiModel.setLat(new BigDecimal(common_fields_val.getDouble("lat")).setScale(6, BigDecimal.ROUND_HALF_UP));
				poiModel.setLng(new BigDecimal(common_fields_val.getDouble("lng")).setScale(6, BigDecimal.ROUND_HALF_UP));

				poiModel.setProvinceId(common_fields_val.getString("province_id"));
				poiModel.setCityId(common_fields_val.getString("city_id"));
				poiModel.setCountyId(common_fields_val.getString("county_id"));

				poiModel.setDetailAddress(common_fields_val.getString("detail_address"));
				poiModel.setBriefIntroduction(common_fields_val.getString("brief_introduction"));
				poiModel.setThumbnail(common_fields_val.getString("thumbnail"));

				// 8.全景数据ID
				poiModel.setPanorama(common_fields_val.getString("panorama"));
				// 9.联系电话
				poiModel.setContact_phone(common_fields_val.getString("contact_phone"));

				JSONArray privateFields = data.getJSONArray("private_fields");
				session.setAttribute("private_fields", privateFields);
			} else {
				return new ModelAndView("/error.jsp");
			}

		} catch (Exception e) {
			MsLogger.error(e);
			ModelAndView mav = new ModelAndView("/error.jsp");
			return mav;
		}

		// 省份信息
		List<SimpleModel> lstProvinces = new ArrayList<SimpleModel>();
		try {
			for (Map<String, String> map : pulldownCtrl.loadProvinces()) {
				SimpleModel simpleModel = new SimpleModel();
				simpleModel.setValue(map.get("province_id"));
				simpleModel.setLabel(map.get("province_nm_zh"));
				lstProvinces.add(simpleModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		mav.addObject("provinces", lstProvinces);
		session.setAttribute("provinces", lstProvinces);

		// 城市信息
		List<SimpleModel> lstCitys = new ArrayList<SimpleModel>();
		SimpleModel simpleModel = new SimpleModel();
		simpleModel.setValue("ALL");
		simpleModel.setLabel("市");
		lstCitys.add(simpleModel);
		try {
			if (!"ALL".equals(poiModel.getProvinceId())
					&& !CharactorUtil.isEmpyty(poiModel.getProvinceId())) {
				for (Map<String, String> map : pulldownCtrl.loadCitys(poiModel.getProvinceId())) {
					simpleModel = new SimpleModel();
					simpleModel.setValue(map.get("city_id"));
					simpleModel.setLabel(map.get("city_nm_zh"));
					lstCitys.add(simpleModel);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ModelAndView mav = new ModelAndView("/error.jsp");
			return mav;
		}
		session.setAttribute("citys", lstCitys);

		// 区/县信息
		List<SimpleModel> lstCountys = new ArrayList<SimpleModel>();
		simpleModel = new SimpleModel();
		simpleModel.setValue("ALL");
		simpleModel.setLabel("区/县");
		lstCountys.add(simpleModel);

		try {
			if (!"ALL".equals(poiModel.getCityId())
					&& !poiModel.getCityId().isEmpty()) {
				for (Map<String, String> map : pulldownCtrl.loadCountys(poiModel.getCityId())) {
					simpleModel = new SimpleModel();
					simpleModel.setValue(map.get("county_id"));
					simpleModel.setLabel(map.get("county_nm_zh"));
					lstCountys.add(simpleModel);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ModelAndView mav = new ModelAndView("/error.jsp");
			return mav;
		}
		session.setAttribute("countys", lstCountys);

		ModelAndView mav = new ModelAndView("/edit_poi.jsp");
		mav.addObject("poiReadOnly", Boolean.FALSE);
		mav.addObject("poiModel", poiModel);
		return mav;
	

	}

	/**
	 * 页面初始化
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(params = "method=newBlankPoiReadOnly")
	public ModelAndView newBlankPoiReadOnly(
			@RequestParam(required = true) String _id,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		ModelAndView mav = init(_id, request, response);
		mav.addObject("poiReadOnly", Boolean.TRUE);
		return mav;
	}

	/**
	 * 1.接收参数检查<p>
	 * 2.封装参数到json格式
	 * @param request
	 * @return
	 */
	private JSONObject param2Json(HttpServletRequest request) {
		Map<String, String[]> paramMaps = request.getParameterMap();
		Set<Entry<String, String[]>> set = paramMaps.entrySet();
		JSONObject param = new JSONObject();

		// 1.名称
		String[] values = paramMaps.get("longName");
		if (values == null || values.length == 0) {
			throw new IllegalArgumentException("名称不能为空！");
		}
		param.put("long_title", values[0]);
		if (CharactorUtil.checkPoiDefaultStr(values[0], 64)) {
			throw new IllegalArgumentException("名称长度不能超过" + 64 +"字节");
		}

		// 2.别名
		values = paramMaps.get("shortName");
		if (values == null || values.length == 0) {
			//throw new IllegalArgumentException("别名不能为空！");
			param.put("short_title", "");
		} else {
			param.put("short_title", values[0]);
			if (CharactorUtil.checkPoiDefaultStr(values[0], 64)) {
				throw new IllegalArgumentException("别名长度不能超过" + 64 +"字节");
			}
		}

		// 3.一级类别（topTag）
		values = paramMaps.get("topTag");
		if (values == null || values.length == 0) {
			param.put("tags", "[]");
		} else {
			param.put("tags", values);
		}
		// 3.二级类别(subTag)
		values = paramMaps.get("subTag");
		if (values == null || values.length == 0) {
			param.put("subTag", 0);
		} else {
			param.put("subTag", values[0]);
		}

		// 4.坐标（lat,lng）
		values = paramMaps.get("lat");
		if (values == null || values.length == 0) {
			throw new IllegalArgumentException("纬度不能为空！");
		}
		param.put("lat", values[0]);

		// 4.坐标（lat,lng）
		values = paramMaps.get("lng");
		if (values == null || values.length == 0) {
			throw new IllegalArgumentException("经度不能为空！");
		}
		param.put("lng", values[0]);

		// 5.区域地址
		String zone_id = null;
		// 省
		values = paramMaps.get("provinceId");
		if (values == null || values.length == 0 && !"ALL".equals(values[0]) && !"".equals(values[0])) {
			throw new IllegalArgumentException("省份不能为空！");
		}
		zone_id = values[0];
		// 市
		values = paramMaps.get("cityId");
		if (values != null && values.length != 0 && !"ALL".equals(values[0]) && !"".equals(values[0])) {
			zone_id = values[0];
		}
		// 区/县
		values = paramMaps.get("countyId");
		if (values != null && values.length != 0 && !"ALL".equals(values[0]) && !"".equals(values[0])) {
			zone_id = values[0];
		}
		param.put("zone_id", zone_id);
		// 详细地址
		values = paramMaps.get("detailAddress");
		if (values == null || values.length == 0) {
			param.put("detail_address", "");
		} else {
			param.put("detail_address", values[0]);
			if (CharactorUtil.checkPoiDefaultStr(values[0])) {
				throw new IllegalArgumentException("详细地址过长");
			}
		}

		// 6.简介
		values = paramMaps.get("briefIntroduction");
		if (values == null || values.length == 0) {
			param.put("brief_introduction", "");
		} else {
			param.put("brief_introduction", values[0]);
			if (CharactorUtil.checkPoiDefaultStr(values[0], ManagePoiCtrl.介绍最大长度)) {
				throw new IllegalArgumentException("简介内容过长");
			}
		}

		// 7.缩略图
		values = paramMaps.get("thumbnail");
		if (values == null || values.length == 0) {
			param.put("thumbnail", "");
		} else {
			param.put("thumbnail", values[0]);
			if (!VbUtility.checkCOSPicIsOK(values[0])) {
				throw new IllegalArgumentException("缩略图地址不正确，或者是没有上传的图片");
			}
		}

		param.put("thumbnail_1_1", "");
		param.put("thumbnail_16_9", "");

		// 8.全景数据ID
		values = paramMaps.get("panorama");
		if (values == null || values.length == 0) {
			param.put("panorama", "");
		} else {
			param.put("panorama", values[0]);
			if (CharactorUtil.hasChineseChar(values[0])) {
				throw new IllegalArgumentException("全景数据ID不能含有中文字符！");
			}
			if (CharactorUtil.checkPoiDefaultStr(values[0], 32)) {
				throw new IllegalArgumentException("全景数据ID长度不能超过" + 32 +"字节");
			}
		}

		// 9.联系电话
		values = paramMaps.get("contact_phone");
		if (values == null || values.length == 0) {
			param.put("contact_phone", "");
		} else {
			param.put("contact_phone", values[0]);
			if (CharactorUtil.hasChineseChar(values[0])) {
				throw new IllegalArgumentException("联系电话中不能含有中文字符！");
			}
		}

		for (Entry<String, String[]> entry : set) {
			MsLogger.debug(entry.getKey() + "\t");
			if (param.has(entry.getKey())) {
				continue;
			}
			if (entry.getValue().length > 1) {
				param.put(entry.getKey(), entry.getValue());
			} else {
				param.put(entry.getKey(), entry.getValue()[0]);
			}
		}
		return param;
	}

	/**
	 * 添加POI基础信息
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(params = "method=updatePoi")
	public void updatePoi(
			@RequestParam(required = true, value = "poiId")
			String _id,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		// 修正方式添加新的POI
		if (_id.isEmpty()) {
			addPoiCtrl.addPoi(request, response);
			return;
		}

		try {
			JSONObject param = this.param2Json(request);
			param.put("_id", _id);
			HttpSession session = request.getSession(false);
			MsUser msUser = (MsUser)session.getAttribute("msUser");
			JSONObject result = managePoiService.updatePoi(param.toString(), msUser);
			if ("0".equals(result.getString("code"))) {
				response.getWriter().print(JsonUtil.sucess("0"));
				response.setStatus(200);
				return;
			} else {
				response.getWriter().print(JsonUtil.error("-1", result.getString("msg")));
				response.setStatus(200);
				return;
			}
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
			response.getWriter().print(JsonUtil.error("-2", e.getMessage()));
			response.setStatus(200);
			return;
		}
	}
//
//	/**
//	 * 更新POI基础信息
//	 * @param request
//	 * @param response
//	 */
//	@RequestMapping(params = "method=updatePoi")
//	public ModelAndView updatePoi(
//			@ModelAttribute("editPoiModel")
//			PoiModel editPoiModel,
//			BindingResult bindingResult,
//			HttpServletRequest request, HttpServletResponse response) {
//		HttpSession session = request.getSession(false);
//		JSONObject param = new JSONObject();
//		String _id = editPoiModel.getPoiId();
//		if (_id == null || editPoiModel.getPoiId().trim().isEmpty()) {
//			session.invalidate();
//			MsLogger.debugln("_id is missing");
//			return new ModelAndView("/error.jsp");
//		}
//		// 名称
//		param.put("_id", editPoiModel.getPoiId().trim());
//
//		// 名称
//		param.put("long_title", editPoiModel.getLongName());
//		// 别名
//		param.put("short_title", editPoiModel.getShortName());
//		// 类别
//		param.put("tags", editPoiModel.getCheckeds());
//		// 坐标(纬度)
//		param.put("lat", editPoiModel.getLat());
//		// 坐标(经度)
//		param.put("lng", editPoiModel.getLng());
//
//		// 区域Id
//		String zone_id = editPoiModel.getProvinceId();
//		if (editPoiModel.getCityId() != null && !editPoiModel.getCityId().isEmpty()
//				&& !"ALL".equals(editPoiModel.getCityId())) {
//			zone_id = editPoiModel.getCityId();
//		}
//		if (editPoiModel.getCountyId() != null && !editPoiModel.getCountyId().isEmpty()
//				&& !"ALL".equals(editPoiModel.getCountyId())) {
//			zone_id = editPoiModel.getCountyId();
//		}
//		param.put("zone_id", zone_id);
//
//		// 详细地址
//		param.put("detail_address", editPoiModel.getDetailAddress());
//
//		// 简介
//		param.put("brief_introduction", editPoiModel.getBriefIntroduction());
//		// 缩略图原图
//		param.put("thumbnail", editPoiModel.getThumbnail());
////		// 缩略图原图(1:1) 处理方法TODO:
//		param.put("thumbnail_1_1", "");
////		// 缩略图原图(16:9) 处理方法TODO:
//		param.put("thumbnail_16_9", "");
//		// 音频
//		param.put("audio", editPoiModel.getAudio());
//		param.put("video", editPoiModel.getVideo());
//
//		MsUser msUser = (MsUser)session.getAttribute("msUser");
//		managePoiService.updatePoi(param.toString(), msUser);
//		return init(request, response);
//	}

//
//	@RequestMapping(params = "method=asyncSearchPois")
//	public void asyncSearchPois(@RequestParam(required = false) Integer offset,
//			@RequestParam(required = false) Integer limit,
//			@RequestParam(required = false) String filterName,
//			HttpServletRequest request, HttpServletResponse response) throws IOException {
//
//		JSONObject resJSON = JSONObject.fromObject("{}");
//		try {
//			response.setHeader("Access-Control-Allow-Origin", "*");
//			response.setContentType("text/html; charset=UTF-8");
//			JSONObject param = JSONObject.fromObject("{}");
//			
//			if (offset != null) {
//				param.put("offset", offset);
//			}
//			if (limit != null) {
//				param.put("limit", limit);
//			}
//			if (filterName != null) {
//				filterName = URLDecoder.decode(filterName, "UTF-8");
//				if (!filterName.trim().isEmpty()) {
//					param.put("filterName", filterName.trim());
//				}
//			}
//
//			JSONObject poisResult = managePoiService.getPois(param.toString());
//			if ("0".equals(poisResult.getString("code"))) {
//				JSONObject data = poisResult.getJSONObject("data");
//
//				JSONArray arrays = data.getJSONArray("pois");
//				JSONArray rows = JSONArray.fromObject("[]");
//				for (int i = 0; i < arrays.size(); i++) {
//					JSONObject poiJson = arrays.getJSONObject(i);
//					
//					JSONObject row = JSONObject.fromObject("{}");
//					row.put("number", (i+1));
//					String short_title = poiJson.getString("short_title");
//					String long_title = poiJson.getString("long_title");
//					String poi_name = long_title.isEmpty() ? short_title : long_title;
//					row.put("poi_name", poi_name);
//
//					row.put("_id", poiJson.getString("_id"));
//					row.put("lng", poiJson.getString("lng"));
//					row.put("lat", poiJson.getString("lat"));
//					row.put("poi_tags", poiJson.getString("tags"));
//					row.put("city_name", poiJson.getString("city_name"));
//					row.put("province_name", poiJson.getString("province_name"));
//
//					rows.add(row);
//				}
//				resJSON.put("rows", rows);
//				resJSON.put("total", data.getInt("total"));
//			} else {
//				resJSON.put("total", 0);
//			}
//			
//			response.getWriter().print(resJSON.toString());
//			response.setStatus(200);
//			return;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		response.getWriter().print(JsonUtil.error("-1", "异常").toString());
//		response.setStatus(200);
//		return;
//	}
//
//	@RequestMapping(params = "method=asyncDeletePoi")
//	public void asyncDeletePoi(@RequestParam(required = true, value="_id") String _id,
//			HttpServletRequest request, HttpServletResponse response) throws IOException {
//
//		try {
//			response.setHeader("Access-Control-Allow-Origin", "*");
//			response.setContentType("text/html; charset=UTF-8");
//			JSONObject param = JSONObject.fromObject("{}");
//			param.put("_id", _id);
//			JSONObject poisResult = managePoiService.asyncDeletePoi(param.toString());
//			response.getWriter().print(poisResult.toString());
//			response.setStatus(200);
//			return;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		response.getWriter().print(JsonUtil.error("-1", "发生异常").toString());
//		response.setStatus(200);
//		return;
//	}

//	@RequestMapping(params = "method=asyncSearchPoiById")
//	public void asyncSearchPoiById(@RequestParam(required = true) String _id,
//			HttpServletRequest request, HttpServletResponse response) throws IOException {
//
//		JSONObject resJSON = JSONObject.fromObject("{}");
//		try {
//			response.setHeader("Access-Control-Allow-Origin", "*");
//			response.setContentType("text/html; charset=UTF-8");
//			JSONObject param = JSONObject.fromObject("{}");
//			param.put("_id", _id);
//			resJSON = managePoiService.getPoi(param.toString());
//			HttpSession session = request.getSession(false);
//
//			Integer poi_tags_length = (Integer)session.getAttribute("poi_tags_length");
//			if ("0".equals(resJSON.getString("code"))) {
//				JSONObject data = resJSON.getJSONObject("data");
//				data.put("poi_tags_length", poi_tags_length);
//				resJSON = JsonUtil.sucess("success", data);
//			}
//			response.getWriter().print(resJSON.toString());
//			response.setStatus(200);
//			return;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		response.getWriter().print(resJSON.toString());
//		response.setStatus(200);
//		return;
//	}

//	/**
//	 * 异步上传图片
//	 * @param request
//	 * @param response
//	 * @throws IOException 
//	 */
//	@RequestMapping(params = "method=upload_thumbnail")
//	public void uploadThumbnail(
//			@RequestParam(required = true, value = "thumbnail_fileup") MultipartFile file,
//			HttpServletRequest request, HttpServletResponse response) throws IOException {
//			super.uploadLocalFile2Cloud(request, response, file, COSUtil.picAddress);
//	}
//
//	/**
//	 * 异步上传音频
//	 * @param request
//	 * @param response
//	 * @throws IOException 
//	 */
//	@RequestMapping(params = "method=upload_audio")
//	public void uploadAudio(
//			@RequestParam(required = true, value = "audio") MultipartFile file,
//			HttpServletRequest request, HttpServletResponse response) throws IOException {
//			super.uploadLocalFile2Cloud(request, response, file, COSUtil.picAddress);
//	}
//
//	/**
//	 * 异步上传音频
//	 * @param request
//	 * @param response
//	 * @throws IOException 
//	 */
//	@RequestMapping(params = "method=upload_video")
//	public void uploadVideo(
//			@RequestParam(required = true, value = "video") MultipartFile file,
//			HttpServletRequest request, HttpServletResponse response) throws IOException {
////			super.uploadLocalFile2Cloud(request, response, file, pic_address);
//	}
}
