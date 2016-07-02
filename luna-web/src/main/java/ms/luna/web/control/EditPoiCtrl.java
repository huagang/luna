package ms.luna.web.control;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import ms.luna.biz.cons.VbConstant;
import ms.luna.biz.model.MsUser;
import ms.luna.biz.sc.ManagePoiService;
import ms.luna.biz.util.CharactorUtil;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.common.PoiCommon;
import ms.luna.web.common.BasicCtrl;
import ms.luna.web.common.PulldownCtrl;
import ms.luna.web.model.common.SimpleModel;
import ms.luna.web.model.managepoi.PoiModel;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 非共有字段（由程序控制）；共有字段，由维护人员修改代码。
 */
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

	/**
	 * 页面初始化(附带初始值，属于编辑更新POI)<p>
	 * 中文或者英文输入页面
	 * @param _id mongodb中POI的ID
	 * @param lang 语言种类:null或者zh为中文，en为英文
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(params = "method=init")
	public ModelAndView init(
			@RequestParam(required = true, value = "_id")
			String _id,
			@RequestParam(required = false, value = "lang", defaultValue="zh")
			String lang,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		ModelAndView mav = this.initCommPart(null, _id, lang, request, response);
		mav.addObject("lang", lang);
		mav.addObject("_id", _id);
		return mav;
	}

	/**
	 * 页面初始化(附带初始值，属于新建添加POI)
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(params = "method=initOfPopupFixPoi")
	public ModelAndView initOfPopupFixPoi(
			@RequestParam(required = true, value="dataPoi") String unSavedPoi,
			@RequestParam(required = false, value = "lang", defaultValue="zh")
			String lang,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		return this.initCommPart(unSavedPoi, null, lang, request, response);
	}

	/**
	 * 页面初始化(只读模式)
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(params = "method=newBlankPoiReadOnly")
	public ModelAndView newBlankPoiReadOnly(
			@RequestParam(required = true)
			String _id,
			@RequestParam(required = false, value = "lang", defaultValue="zh")
			String lang,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		ModelAndView mav = this.init(_id, lang, request, response);
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
		JSONObject result = managePoiService.downloadPoiTemplete("{}");
		if (!"0".equals(result.get("code"))) {
			return FastJsonUtil.error("-1", result.getString("msg"));
		}
		JSONObject data = result.getJSONObject("data");
		JSONArray privateFieldsDef = data.getJSONArray("privateFieldsDef");

		return PoiCommon.getInstance().param2Json(request, privateFieldsDef, Boolean.FALSE);
	}

	/**
	 * 检查POI基础信息
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(params = "method=checkPoi")
	public void checkPoi(
			@RequestParam(required = true, value = "poiId")
			String _id,
			@RequestParam(defaultValue="0", required = false, value = "lang")
			String lang,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader(VbConstant.ACCESS_CONTROL_ALLOW_ORIGIN_KEY, VbConstant.ACCESS_CONTROL_ALLOW_ORIGIN_VALUE);
		response.setContentType(VbConstant.NORMAL_CONTENT_TYPE);

		if (PoiCommon.POI.EN.equals(lang)) {
			JSONObject result = managePoiService.downloadPoiTemplete("{}");
			if (!"0".equals(result.get("code"))) {
				response.getWriter().print(FastJsonUtil.error("-1", result.getString("msg")));
				return;
			}
			JSONObject data = result.getJSONObject("data");
			JSONArray privateFieldsDef = data.getJSONArray("privateFieldsDef");
			JSONObject param = PoiCommon.getInstance().checkParams(request, privateFieldsDef);
			response.getWriter().print(param.toJSONString());
			return;
		}
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
			@RequestParam(required = false, value = "lang")
			String lang,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		// Excel上传后数据检查有错误的POI，点击保存后以新建添加的方式增加POI(处理逻辑同新建添加)
		if (_id.isEmpty()) {
			addPoiCtrl.addPoi(request, response);
			return;
		}

		try {

			JSONObject param = this.param2Json(request);
			param.put("_id", _id);
			param.put("lang", lang);
			HttpSession session = request.getSession(false);
			MsUser msUser = (MsUser)session.getAttribute("msUser");
			JSONObject result = managePoiService.updatePoi(param.toString(), msUser);
			MsLogger.error(result.toJSONString());
			if ("0".equals(result.getString("code"))) {
				response.getWriter().print(FastJsonUtil.sucess("0"));
				response.setStatus(200);
				return;
			} else {
				response.getWriter().print(FastJsonUtil.error("-1", result.getString("msg")));
				response.setStatus(200);
				return;
			}
		} catch(IllegalArgumentException e) {
			MsLogger.error(e);
			response.getWriter().print(FastJsonUtil.error("-2", e.getMessage()));
			response.setStatus(200);
			return;
		}
	}

	/**
	 * 页面初始化(带值的页面初始化：新建和更新两种模式)<p>
	 * 更新的场合：unSavedPoi为空值，_id不为空<br/>
	 * 新建的场合:unSavedPoi为不为空值，_id为空<br/>
	 * 
	 * @param unSavedPoi
	 * @param _id
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	private ModelAndView initCommPart(
			String unSavedPoi,
			String _id,
			String lang,
			HttpServletRequest request, HttpServletResponse response)
					throws IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			MsLogger.error("session is null");
			return new ModelAndView("/error.jsp");
		}
		session.setAttribute("menu_selected", "manage_poi");

		ModelAndView mav = new ModelAndView();

		PoiModel poiModel = new PoiModel();
		try {
			JSONObject result = null;
			// initOfPopupFixPoi模式(不带ID属于新建添加)
			if (unSavedPoi != null) {
				unSavedPoi = unSavedPoi.replace("\n", "\\n").replace("\r", "\\r");
				result = managePoiService.initFixPoi(unSavedPoi);

			// 编辑模式(带ID属于修正)
			} else {
				JSONObject params = new JSONObject();
				params.put("_id", _id);
				// 语言种类
				params.put("lang", lang);
				result = managePoiService.initEditPoi(params.toString());
			}
			MsLogger.debug(result.toString());

			if ("0".equals(result.getString("code"))) {
				JSONObject data = result.getJSONObject("data");
				JSONObject common_fields_val = data.getJSONObject("common_fields_val");
				// init模式
				if (unSavedPoi == null) {
					poiModel.setPoiId(_id);
				}
				poiModel.setLongName(common_fields_val.getString("long_title"));
				poiModel.setShortName(common_fields_val.getString("short_title"));

				// 分类一级菜单
				JSONArray tags_values = common_fields_val.getJSONArray("tags_values");
				if (tags_values.size() > 0) {
					poiModel.setTopTag(tags_values.getInteger(0));
				} else {
					poiModel.setTopTag(0);
				}
				// 分类二级菜单
				poiModel.setSubTag(common_fields_val.getInteger("subTag"));
				managePoiCtrl.initTags(session, data.getJSONObject("common_fields_def"), poiModel.getTopTag());

				poiModel.setLat(new BigDecimal(common_fields_val.getDouble("lat")).setScale(6, BigDecimal.ROUND_HALF_UP));
				poiModel.setLng(new BigDecimal(common_fields_val.getDouble("lng")).setScale(6, BigDecimal.ROUND_HALF_UP));

				poiModel.setProvinceId(common_fields_val.getString("province_id"));
				poiModel.setCityId(common_fields_val.getString("city_id"));
				poiModel.setCountyId(common_fields_val.getString("county_id"));

				poiModel.setDetailAddress(common_fields_val.getString("detail_address"));
				poiModel.setBriefIntroduction(common_fields_val.getString("brief_introduction"));
				poiModel.setThumbnail(common_fields_val.getString("thumbnail"));

				// 8.全景类型
				poiModel.setPanoramaType(common_fields_val.getString("panorama_type"));
				// 8.全景数据ID
				poiModel.setPanorama(common_fields_val.getString("panorama"));
				// 9.联系电话
				poiModel.setContact_phone(common_fields_val.getString("contact_phone"));

				// 预览地址
				String preview_url = common_fields_val.getString("preview_url");
				mav.addObject("preview_url", preview_url);
				
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
			MsLogger.error(e);
			mav.setViewName("/error.jsp");
			return mav;
		}
//		mav.addObject("provinces", lstProvinces);
		session.setAttribute("provinces", lstProvinces);

		// 城市信息
		List<SimpleModel> lstCitys = new ArrayList<SimpleModel>();
		SimpleModel simpleModel = new SimpleModel();
		simpleModel.setValue(VbConstant.ZonePulldown.ALL);
		simpleModel.setLabel(VbConstant.ZonePulldown.ALL_CITY_NM);
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
		mav.addObject("tempPanoType", poiModel.getPanoramaType());//radiobutton被禁用后无法向form表单传递参数。此处设置了一个临时存取区域
		return mav;
	}
}
