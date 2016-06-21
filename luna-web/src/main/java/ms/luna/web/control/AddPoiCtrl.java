package ms.luna.web.control;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ms.luna.biz.model.MsUser;
import ms.luna.biz.sc.ManagePoiService;
import ms.luna.biz.sc.VodPlayService;
import ms.luna.biz.util.COSUtil;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.biz.util.VODUtil;
import ms.luna.biz.util.VbMD5;
import ms.luna.biz.util.VbUtility;
import ms.luna.common.PoiCommon;
import ms.luna.web.common.BasicCtrl;
import ms.luna.web.common.PulldownCtrl;
import ms.luna.web.control.api.VodPlayCtrl;
import ms.luna.web.control.api.VodPlayCtrl.REFRESHMODE;
import ms.luna.web.model.common.SimpleModel;
import ms.luna.web.model.managepoi.PoiModel;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			MsLogger.error("session is null");
			return new ModelAndView("/error.jsp");
		}
		session.setAttribute("menu_selected", "manage_poi");
		PoiModel poiModel = new PoiModel();

		JSONObject params = new JSONObject();
		JSONObject result = managePoiService.initAddPoi(params.toString());
		MsLogger.debug(result.toString());

		if ("0".equals(result.getString("code"))) {
			JSONObject data = result.getJSONObject("data");
			JSONArray private_fields_def = data.getJSONArray("private_fields_def");
			managePoiCtrl.initTags(session, data.getJSONObject("common_fields_def"), null);
			session.setAttribute("private_fields", private_fields_def);
		} else {
			return new ModelAndView("/error.jsp");
		}

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
			MsLogger.error(e);
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
			MsLogger.error(e);
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
		JSONObject result = managePoiService.downloadPoiTemplete("{}");
		if (!"0".equals(result.get("code"))) {
			return FastJsonUtil.error("-1", result.getString("msg"));
		}
		JSONObject data = result.getJSONObject("data");
		JSONArray privateFieldsDef = data.getJSONArray("privateFieldsDef");

		return PoiCommon.getInstance().param2Json(request, privateFieldsDef, Boolean.FALSE);
//
//		Map<String, JSONObject> fieldDefMap = new LinkedHashMap<String, JSONObject>();
//
//		for (int i = 0; i < privateFieldsDef.size(); i++) {
//			JSONObject field = privateFieldsDef.getJSONObject(i);
//			if (!field.containsKey("field_def")) {
//				continue;
//			}
//			JSONObject field_def = field.getJSONObject("field_def");
//			fieldDefMap.put(field_def.getString("field_name"), field_def);
//		}
//
//		for (Entry<String, String[]> entry : set) {
//			if (!fieldDefMap.containsKey(entry.getKey())) {
//				continue;
//			}
//
//			String value = null;
//			if (entry.getValue().length > 1) {
//				StringBuilder sb = new StringBuilder();
//				for (int i = 0; i < entry.getValue().length; i++) {
//					if (i != 0) {
//						sb.append(",");
//					}
//					sb.append(entry.getValue()[i]);
//				}
//				value = sb.toString();
//			} else {
//				value = entry.getValue()[0];
//			}
//			JSONObject privateJson = fieldDefMap.get(entry.getKey());
//			if (CharactorUtil.isPoiDataHasError(value, privateJson, Boolean.FALSE)) {
//				String field_show_name = "";
//				if (privateJson != null) {
//					field_show_name = privateJson.getString("field_show_name");
//				}
//				throw new IllegalArgumentException("[" + field_show_name + "] 数据错误，请认真确认数据！");
//			}
//		}
//		for (Entry<String, String[]> entry : set) {
//			MsLogger.debug(entry.getKey() + "\t");
//			if (param.containsKey(entry.getKey())) {
//				continue;
//			}
//			if (entry.getValue().length > 1) {
//				param.put(entry.getKey(), entry.getValue());
//			} else {
//				param.put(entry.getKey(), entry.getValue()[0]);
//			}
//		}
//		return param;
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
				response.getWriter().print(FastJsonUtil.sucess("成功上传"));
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
			MsLogger.error(e);
			response.getWriter().print(FastJsonUtil.error("-2", e.getMessage()));
			response.setStatus(200);
			return;
		}
		response.getWriter().print(FastJsonUtil.sucess("0"));
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
			response.getWriter().print(FastJsonUtil.error("-1", "文件扩展名有错误"));
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
			response.getWriter().print(FastJsonUtil.error("-1", "文件扩展名有错误"));
			response.setStatus(200);
			return;
		}

		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String fileNameInCloud = VbMD5.generateToken() + ext;
		super.uploadLocalFile2Cloud(request, response, file, COSUtil.getCosPoiPicFolderPath() + "/" + date, fileNameInCloud);
	}

	/**
	 * 异步上传视频
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
				response.getWriter().print(FastJsonUtil.error("4", "文件扩展名有错误"));
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
				JSONObject param = new JSONObject();
				param.put("vod_file_id", vod_file_id);
				JSONObject resJson = vodPlayService.createVodRecord(param.toString());
				
				if (resJson.getString("code").equals("0")) {// 成功
					// 存入缓存
					VodPlayCtrl.refreshVodFileTokenCache(token, REFRESHMODE.ADD);
					response.getWriter().print(FastJsonUtil.sucess("成功", param));// 注：result中文件id的表示为fileId，非file_id
				} else {
					response.getWriter().print(FastJsonUtil.error("3", "写入数据库失败"));
				}
			} else if (result.getString("code").equals("2")) {
				response.getWriter().print(FastJsonUtil.error("2", "文件大小超过5M"));
			} else {
				response.getWriter().print(FastJsonUtil.error("4", "文件传输失败"));
			}
			response.setStatus(200);
			return;
		} catch (Exception e) {
			try {
				response.getWriter().print(FastJsonUtil.error("-1", "处理过程中系统发生异常:" + VbUtility.printStackTrace(e)));
				response.setStatus(200);
			} catch (IOException e1) {
				MsLogger.error(e1);
			}
			return;
		}
	}
}
