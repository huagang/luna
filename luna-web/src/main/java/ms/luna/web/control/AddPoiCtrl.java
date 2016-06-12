package ms.luna.web.control;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ms.luna.biz.cons.VbConstant;
import ms.luna.biz.model.MsUser;
import ms.luna.biz.sc.ManagePoiService;
import ms.luna.biz.sc.VodPlayService;
import ms.luna.biz.util.COSUtil;
import ms.luna.biz.util.CharactorUtil;
import ms.luna.biz.util.JsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.biz.util.VODUtil;
import ms.luna.biz.util.VbMD5;
import ms.luna.biz.util.VbUtility;
import ms.luna.web.common.BasicCtrl;
import ms.luna.web.common.PulldownCtrl;
import ms.luna.web.control.api.VodPlayCtrl;
import ms.luna.web.control.api.VodPlayCtrl.REFRESHMODE;
import ms.luna.web.model.common.SimpleModel;
import ms.luna.web.model.managepoi.PoiModel;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Component("addPoiCtrl")
@Controller
@RequestMapping("/add_poi.do")
public class AddPoiCtrl extends BasicCtrl{

	@Autowired
	private ManagePoiService managePoiService;

	@Resource(name="pulldownCtrl")
	private PulldownCtrl pulldownCtrl;

	@Autowired
	private VodPlayService vodPlayService;

	@Resource(name="managePoiCtrl")
	private ManagePoiCtrl managePoiCtrl;

	/**
	 * 页面上属性列表
	 */
	private List<SimpleModel> checkBoxTags;

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
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			MsLogger.error("session is null");
			return new ModelAndView("/error.jsp");
		}
		session.setAttribute("menu_selected", "manage_poi");
		PoiModel poiModel = new PoiModel();

		checkBoxTags = poiModel.getPoiTags();

		JSONObject params = new JSONObject();
		JSONObject result = managePoiService.initAddPoi(params.toString());

		if ("0".equals(result.getString("code"))) {
			JSONObject data = result.getJSONObject("data");
			JSONObject common_fields_def = data.getJSONObject("common_fields_def");

			JSONArray tags = common_fields_def.getJSONArray("tags_def");
			for (int i = 0; i < tags.size(); i++) {
				JSONObject tag = tags.getJSONObject(i);
				if (VbConstant.POI.公共TAGID.compareTo(tag.getInt("tag_id")) == 0) {
					continue;
				}
				SimpleModel simpleModel = new SimpleModel();
				simpleModel.setValue(tag.getString("tag_id"));
				simpleModel.setLabel(tag.getString("tag_name"));
				checkBoxTags.add(simpleModel);
			}
			JSONArray private_fields_def = data.getJSONArray("private_fields_def");
			session.setAttribute("private_fields", private_fields_def);
		}
		session.setAttribute("sessionCheckBoxTags", checkBoxTags);

		// 区域信息的下拉列表
		// 国家信息
		List<SimpleModel> lstCountrys = new ArrayList<SimpleModel>();
		SimpleModel simpleModel = new SimpleModel();
		simpleModel.setValue("100000");
		simpleModel.setLabel("中国");
		lstCountrys.add(simpleModel);
//		mav.addObject("countrys", lstCountrys);
		session.setAttribute("countrys", lstCountrys);

		// 省份信息
		List<SimpleModel> lstProvinces = new ArrayList<SimpleModel>();
		try {
			for (Map<String, String> map : pulldownCtrl.loadProvinces()) {
				simpleModel = new SimpleModel();
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
		simpleModel = new SimpleModel();
		simpleModel.setValue("");
		simpleModel.setLabel("请选择市");
		lstCitys.add(simpleModel);
//		mav.addObject("citys", lstCitys);
		try {
			// 北京城市信息（需要初始化）
			for (Map<String, String> map : pulldownCtrl.loadCitys("110000")) {
				simpleModel = new SimpleModel();
				simpleModel.setValue(map.get("city_id"));
				simpleModel.setLabel(map.get("city_nm_zh"));
				lstCitys.add(simpleModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.setAttribute("citys", lstCitys);

		// 区/县信息
		List<SimpleModel> lstCountys = new ArrayList<SimpleModel>();
		simpleModel = new SimpleModel();
		simpleModel.setValue("");
		simpleModel.setLabel("请选择区/县");
		lstCountys.add(simpleModel);
//		mav.addObject("countys", lstCountys);
		session.setAttribute("countys", lstCountys);

		ModelAndView mav = new ModelAndView("/add_poi.jsp");
		mav.addObject("poiModel", poiModel);
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

		String long_title = null;
		String short_title = null;
		String lat = null;
		String lng = null;
		String zone_id = null;
		String detail_address = null;
		String brief_introduction = null;
		String thumbnail = null;
		// 8.全景数据ID
		String panorama = null;
		// 9.联系电话
		String contact_phone = null;

		// 1.名称
		String[] values = paramMaps.get("longName");
		if (values == null || values.length == 0) {
			throw new IllegalArgumentException("名称不能为空！");
		}
		long_title = values[0];
		param.put("long_title", long_title);

		// 2.别名
		values = paramMaps.get("shortName");
		if (values == null || values.length == 0) {
//			throw new IllegalArgumentException("别名不能为空！");
			short_title = "";
		} else {
			short_title = values[0];
		}
		
		param.put("short_title", short_title);

		// 3.类别（tags）
		values = paramMaps.get("checkeds");
		if (values == null || values.length == 0) {
			param.put("tags", "[]");
		} else {
			param.put("tags", values);
		}

		// 4.坐标（lat,lng）
		values = paramMaps.get("lat");
		if (values == null || values.length == 0) {
			throw new IllegalArgumentException("纬度不能为空！");
		}
		lat = values[0];
		param.put("lat", lat);

		// 4.坐标（lat,lng）
		values = paramMaps.get("lng");
		if (values == null || values.length == 0) {
			throw new IllegalArgumentException("经度不能为空！");
		}
		lng = values[0];
		param.put("lng", lng);

		// 5.区域地址
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
			detail_address = "";
		} else {
			detail_address = values[0];
		}
		param.put("detail_address", detail_address);

		// 6.简介
		values = paramMaps.get("briefIntroduction");
		if (values == null || values.length == 0) {
			brief_introduction = "";
		} else {
			brief_introduction = values[0];
		}
		param.put("brief_introduction", brief_introduction);

		// 7.缩略图
		values = paramMaps.get("thumbnail");
		if (values == null || values.length == 0) {
			thumbnail = "";
		} else {
			thumbnail = values[0];
		}
		if (!VbUtility.checkCOSPicIsOK(thumbnail)) {
			throw new IllegalArgumentException("缩略图地址不正确，或者是没有上传的图片");
		}
		param.put("thumbnail", thumbnail);
		
		param.put("thumbnail_1_1", "");
		param.put("thumbnail_16_9", "");

		// 8.全景数据ID
		values = paramMaps.get("panorama");
		if (values == null || values.length == 0) {
			panorama = "";
		} else {
			panorama = values[0];
		}
		param.put("panorama", panorama);

		if (CharactorUtil.hasChineseChar(panorama)) {
			throw new IllegalArgumentException("全景数据ID不能含有中文字符！");
		}
		if (CharactorUtil.checkPoiDefaultStr(panorama, 32)) {
			throw new IllegalArgumentException("全景数据ID长度不能超过" + 32 +"字节");
		}

		// 9.联系电话
		values = paramMaps.get("contact_phone");
		if (values == null || values.length == 0) {
			contact_phone = "";
		} else {
			contact_phone = values[0];
		}
		param.put("contact_phone", contact_phone);
		if (CharactorUtil.hasChineseChar(contact_phone)) {
			throw new IllegalArgumentException("联系电话中不能含有中文字符！");
		}

		// Common field Check
		if (CharactorUtil.checkPoiDefaultStr(long_title, 64)) {
			throw new IllegalArgumentException("名称长度不能超过" + 64 +"字节");
		}
		if (CharactorUtil.checkPoiDefaultStr(short_title, 64)) {
			throw new IllegalArgumentException("别名长度不能超过" + 64 +"字节");
		}
		if (CharactorUtil.checkPoiLat(lat)) {
			throw new IllegalArgumentException("纬度范围错误或者非数字");
		}
		if (CharactorUtil.checkPoiLng(lng)) {
			throw new IllegalArgumentException("经度范围错误或者非数字");
		}
		if (CharactorUtil.checkPoiDefaultStr(detail_address)) {
			throw new IllegalArgumentException("详细地址过长");
		}
		if (CharactorUtil.checkPoiDefaultStr(brief_introduction, ManagePoiCtrl.介绍最大长度)) {
			throw new IllegalArgumentException("简介内容过长");
		}

		JSONObject result = managePoiService.downloadPoiTemplete("{}");
		if (!"0".equals(result.get("code"))) {
			return JsonUtil.error("-1", result.getString("msg"));
		}
		JSONObject data = result.getJSONObject("data");
		JSONArray privateFieldsDef = data.getJSONArray("privateFieldsDef");

		Map<String, JSONObject> fieldDefMap = new LinkedHashMap<String, JSONObject>();

		for (int i = 0; i < privateFieldsDef.size(); i++) {
			JSONObject field = privateFieldsDef.getJSONObject(i);
			JSONObject field_def = field.getJSONObject("field_def");
			fieldDefMap.put(field_def.getString("field_name"), field_def);
		}

		for (Entry<String, String[]> entry : set) {
			if (!fieldDefMap.containsKey(entry.getKey())) {
				continue;
			}

			String value = null;
			if (entry.getValue().length > 1) {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < entry.getValue().length; i++) {
					if (i != 0) {
						sb.append(",");
					}
					sb.append(entry.getValue()[i]);
				}
				value = sb.toString();
			} else {
				value = entry.getValue()[0];
			}
			JSONObject privateJson = fieldDefMap.get(entry.getKey());
			if (CharactorUtil.isPoiDataHasError(value, fieldDefMap.get(entry.getKey()), Boolean.FALSE)) {
				String field_show_name = "";
				if (privateJson != null) {
					field_show_name = privateJson.getString("field_show_name");
				}
				throw new IllegalArgumentException("[" + field_show_name + "] 数据错误，请认真确认数据！");
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
	@RequestMapping(params = "method=addPoi")
	public void addPoi(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		try {
			HttpSession session = request.getSession(false);
			JSONObject param = this.param2Json(request);
			MsUser msUser = (MsUser)session.getAttribute("msUser");
			JSONObject result = managePoiService.addPoi(param.toString(), msUser);

			if ("0".equals(result.getString("code"))) {
				response.getWriter().print(JsonUtil.sucess("成功上传"));
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

	/**
	 * 添加POI基础信息
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(params = "method=addPoiAsyncCheck")
	public void addPoiAsyncCheck(
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		try {
			this.param2Json(request);
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
			response.getWriter().print(JsonUtil.error("-2", e.getMessage()));
			response.setStatus(200);
			return;
		}
		response.getWriter().print(JsonUtil.sucess("0"));
		response.setStatus(200);
		return;
	}

	/**
	 * 异步上传图片
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(params = "method=upload_thumbnail")
	public void uploadThumbnail(
			@RequestParam(required = true, value = "thumbnail_fileup") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		String ext = VbUtility.getExtensionOfPicFileName(file.getOriginalFilename());
		if (ext == null) {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().print(JsonUtil.error("-1", "文件扩展名有错误"));
			response.setStatus(200);
			return;
		}
		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String fileNameInCloud = VbMD5.generateToken() + ext;
		super.uploadLocalFile2Cloud(request, response, file, COSUtil.getCosPoiPicFolderPath() + "/" + date, fileNameInCloud);
	}

	/**
	 * 异步上传音频
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(params = "method=upload_audio")
	public void uploadAudio(
			@RequestParam(required = true, value = "audio_fileup") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		String ext = VbUtility.getExtensionOfAudioFileName(file.getOriginalFilename());
		if (ext == null) {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().print(JsonUtil.error("-1", "文件扩展名有错误"));
			response.setStatus(200);
			return;
		}

		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String fileNameInCloud = VbMD5.generateToken() + ext;
		super.uploadLocalFile2Cloud(request, response, file, COSUtil.getCosPoiPicFolderPath() + "/" + date, fileNameInCloud);
	}

	/**
	 * 异步上传音频
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(params = "method=upload_video")
	public void uploadVideo(
			@RequestParam(required = true, value = "video_fileup") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		// super.uploadLocalFile2Cloud(request, response, file, pic_address);
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		try {
			String ext = VbUtility.getExtensionOfVideoFileName(file.getOriginalFilename());
			if (ext == null) {
				response.getWriter().print(JsonUtil.error("4", "文件扩展名有错误"));
				response.setStatus(200);
				return;
			}
			String fileName = VbMD5.generateToken() + ext;// 生成文件名
			String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
			JSONObject result = VODUtil.getInstance().upload2Cloud(request, response, file,
					VODUtil.getVODPoiVideoFolderPath() + "/" + date, fileName);

			// 文件上传
			if (result.getString("code").equals("0")) {
				String token = result.getString("token");
				JSONObject data = result.getJSONObject("data");
				String vod_file_id = data.getString("fileId");

				// 记录写入数据库
				JSONObject param = JSONObject.fromObject("{}");
				param.put("vod_file_id", vod_file_id);
				JSONObject resJson = vodPlayService.createVodFile(param.toString());
				
				if (resJson.getString("code").equals("0")) {// 成功
					// 存入缓存
					VodPlayCtrl.refreshVodFileTokenCache(token, REFRESHMODE.ADD);
					response.getWriter().print(JsonUtil.sucess("成功", param));// 注：result中文件id的表示为fileId，非file_id
				} else {
					response.getWriter().print(JsonUtil.error("3", "写入数据库失败"));
				}
			} else if (result.getString("code").equals("2")) {
				response.getWriter().print(JsonUtil.error("2", "文件大小超过5M"));
			} else {
				response.getWriter().print(JsonUtil.error("4", "文件传输失败"));
			}
			response.setStatus(200);
			return;
		} catch (Exception e) {
			try {
				response.getWriter().print(JsonUtil.error("-1", "处理过程中系统发生异常:" + VbUtility.printStackTrace(e)));
				response.setStatus(200);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return;
		}
	}
}
