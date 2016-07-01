package ms.luna.web.control;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
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
import ms.luna.biz.util.COSUtil;
import ms.luna.biz.util.CharactorUtil;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.biz.util.VODUtil;
import ms.luna.biz.util.VbMD5;
import ms.luna.biz.util.VbUtility;
import ms.luna.biz.util.ZipUtil;
import ms.luna.common.MsLunaResource;
import ms.luna.common.PoiCommon;
import ms.luna.web.common.BasicCtrl;
import ms.luna.web.common.PulldownCtrl;
import ms.luna.web.model.common.SimpleModel;
import ms.luna.web.model.managepoi.PoiModel;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
@Component("managePoiCtrl")
@Controller
@RequestMapping("/manage_poi.do")
public class ManagePoiCtrl extends BasicCtrl{

	private final static String uploadedFilePath = MsLunaResource.getResource("luna").getValue("uploadedFilePath");;

	private Map<String, String> ZONENAME_2_CODE_MAP = new HashMap<String, String>();

	@Autowired
	private ManagePoiService managePoiService;

	@Resource(name="pulldownCtrl")
	private PulldownCtrl pulldownCtrl;

	/**
	 * 二级菜单缓存
	 */
	private volatile Map<Integer, JSONObject> subTagsCache = new ConcurrentHashMap<Integer, JSONObject>();

	/**
	 * 加载模板资源
	 */
	public ManagePoiCtrl() {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(PoiCommon.Excel.模板文件名称);
		try {
			Workbook wb = WorkbookFactory.create(is);
			Sheet sheet = wb.getSheet(PoiCommon.Excel.模板Sheet名称);
			int rowStart = Math.max(1, sheet.getFirstRowNum());
			int rowEnd = Math.max(1, sheet.getLastRowNum());
			for (int i = rowStart; i <= rowEnd; i++) {
				Row row = sheet.getRow(i);
				String merger_name = getCellValueAsString(row.getCell(5));
				String zone_id = getCellValueAsString(row.getCell(0));
				ZONENAME_2_CODE_MAP.put(merger_name, zone_id);
			}
		} catch (EncryptedDocumentException e) {
			MsLogger.error(e);
		} catch (InvalidFormatException e) {
			MsLogger.error(e);
		} catch (IOException e) {
			MsLogger.error(e);
		}
		try {
			is.close();
		} catch (IOException e) {
			MsLogger.error(e);
		}
	}

	/**
	 * 缓存中查找二级菜单选项
	 * @param topTagId
	 * @return
	 */
	@RequestMapping(params = "method=ayncSearchSubTag")
	public void ayncSearchSubTag(
			@RequestParam(required = true) Integer subTag,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject data = null;
		if (subTag != null) {
			data = subTagsCache.get(subTag);
		}
		if (data == null) {
			data = new JSONObject();
			data.put("sub_tags", FastJsonUtil.createBlankIntegerJsonArray());
		}

		response.setHeader(VbConstant.ACCESS_CONTROL_ALLOW_ORIGIN_KEY, VbConstant.ACCESS_CONTROL_ALLOW_ORIGIN_VALUE);
		response.setContentType(VbConstant.NORMAL_CONTENT_TYPE);
		response.getWriter().print(data.toString());
		response.setStatus(200);
		return;
	}

	public JSONObject searchSubTags(Integer topTagId) {
		return subTagsCache.get(topTagId);
	}

	/**
	 * 初始化一级、二级分类列表和全景类型
	 * @param request
	 */
	public void initTags(HttpSession session, JSONObject common_fields_def, Integer topTag) {
		List<SimpleModel> topTags = new ArrayList<SimpleModel>();
		JSONArray tags = common_fields_def.getJSONArray("tags_def");
		List<SimpleModel> panoramaTypes = new ArrayList<SimpleModel>();
		JSONArray types = common_fields_def.getJSONArray("panorama_type");

		List<SimpleModel> lstSubTag = new ArrayList<SimpleModel>();
		for (int i = 0; i < tags.size(); i++) {
			JSONObject tag = tags.getJSONObject(i);
			SimpleModel simpleModel = new SimpleModel();
			simpleModel.setValue(tag.getString("tag_id"));
			simpleModel.setLabel(tag.getString("tag_name"));
			topTags.add(simpleModel);

			JSONArray subTags = tag.getJSONArray("sub_tags");
			JSONObject data = new JSONObject();
			data.put("sub_tags", subTags);
			subTagsCache.put(tag.getInteger("tag_id"), data);
			if (topTag != null && topTag != 0 && tag.getInteger("tag_id") == topTag.intValue()) {
				for (int j = 0; j < subTags.size(); j++) {
					simpleModel = new SimpleModel();
					simpleModel.setValue(subTags.getJSONObject(j).getString("tag_id"));
					simpleModel.setLabel(subTags.getJSONObject(j).getString("tag_name"));
					lstSubTag.add(simpleModel);
				}
			}
		}
		
		for (int i = 0; i < types.size(); i++) {
			JSONObject tag = types.getJSONObject(i);
			SimpleModel simpleModel = new SimpleModel();
			simpleModel.setValue(tag.getString("panorama_type_id"));
			simpleModel.setLabel(tag.getString("panorama_type_name"));
			panoramaTypes.add(simpleModel);
		}

		// 一级下拉列表
		session.setAttribute("topTags", topTags);
		// 二级下拉列表
		session.setAttribute("subTags", lstSubTag);
		// 全景类型列表
		session.setAttribute("panoramaTypes", panoramaTypes);
	}
	
	/**
	 * 页面初始化
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=init")
	public ModelAndView init(
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		ModelAndView mav = new ModelAndView();
		if (session == null) {
			MsLogger.error("session is null");
			mav.setViewName("/error.jsp");
			return mav;
		}
		session.setAttribute("menu_selected", "manage_poi");

		PoiModel poiModel = new PoiModel();

		// 省份信息
		List<SimpleModel> lstProvinces = new ArrayList<SimpleModel>();
		SimpleModel simpleModel = null;
		try {
			for (Map<String, String> map : pulldownCtrl.loadProvinces()) {
				simpleModel = new SimpleModel();
				simpleModel.setValue(map.get("province_id"));
				simpleModel.setLabel(map.get("province_nm_zh"));
				lstProvinces.add(simpleModel);
			}
		} catch (Exception e) {
			MsLogger.error(e);
			mav.setViewName("/error.jsp");
			return mav;
		}
		session.setAttribute("provinces", lstProvinces);

		// 城市信息
		List<SimpleModel> lstCitys = new ArrayList<SimpleModel>();
		simpleModel = new SimpleModel();
		simpleModel.setValue(VbConstant.ZonePulldown.ALL);
		simpleModel.setLabel(VbConstant.ZonePulldown.ALL_CITY_NM);
		lstCitys.add(simpleModel);
		session.setAttribute("citys", lstCitys);

		// 区/县信息
		List<SimpleModel> lstCountys = new ArrayList<SimpleModel>();
		simpleModel = new SimpleModel();
		simpleModel.setValue(VbConstant.ZonePulldown.ALL);
		simpleModel.setLabel(VbConstant.ZonePulldown.ALL_COUNTY_NM);
		lstCountys.add(simpleModel);
		session.setAttribute("countys", lstCountys);

		mav.addObject("addPoiModel", poiModel);
		mav.addObject("editPoiModel", new PoiModel());

		session.setAttribute("poi_tags_length", poiModel.getPoiTags().size());
		mav.setViewName("/manage_poi.jsp");
		return mav;
	}

	@RequestMapping(params = "method=asyncSearchPois")
	public void asyncSearchPois(@RequestParam(required = false) Integer offset,
			@RequestParam(required = false) Integer limit,
			@RequestParam(required = false) String filterName,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		JSONObject resJSON = new JSONObject();
		try {
			response.setHeader(VbConstant.ACCESS_CONTROL_ALLOW_ORIGIN_KEY, VbConstant.ACCESS_CONTROL_ALLOW_ORIGIN_VALUE);
			response.setContentType(VbConstant.NORMAL_CONTENT_TYPE);
			JSONObject param = new JSONObject();

			if (offset != null) {
				param.put("offset", offset);
			}
			if (limit != null) {
				param.put("limit", limit);
			}
			if (filterName != null) {
				filterName = URLDecoder.decode(filterName, "UTF-8");
				if (!filterName.trim().isEmpty()) {
					param.put("filterName", filterName.trim());
				}
			}

			JSONObject poisResult = managePoiService.getPois(param.toString());
			MsLogger.info(poisResult.toJSONString());
			if ("0".equals(poisResult.getString("code"))) {
				JSONObject data = poisResult.getJSONObject("data");

				if (offset == null) {
					offset = 0;
				}
				JSONArray arrays = data.getJSONArray("pois");
				JSONArray rows = new JSONArray();
				for (int i = 0; i < arrays.size(); i++) {
					JSONObject poiJson = arrays.getJSONObject(i);

					JSONObject row = new JSONObject();
					row.put("number", (i+1)+offset);
					String short_title = poiJson.getString("short_title");
					String long_title = poiJson.getString("long_title");
					String poi_name = long_title.isEmpty() ? short_title : long_title;
					row.put("poi_name", poi_name);
					row.put("lang", poiJson.getString("lang"));

					row.put("_id", poiJson.getString("_id"));
					row.put("lng", poiJson.getString("lng"));
					row.put("lat", poiJson.getString("lat"));
					row.put("poi_tags", poiJson.getString("tags"));
					row.put("city_name", poiJson.getString("city_name"));
					row.put("province_name", poiJson.getString("province_name"));

					rows.add(row);
				}
				resJSON.put("rows", rows);
				resJSON.put("total", data.getInteger("total"));
			} else {
				resJSON.put("total", 0);
			}
			response.getWriter().print(resJSON.toString());
			response.setStatus(200);
			return;
		} catch (Exception e) {
			MsLogger.error(e);
			response.getWriter().print(FastJsonUtil.error("-1", "异常").toString());
			response.setStatus(200);
			return;
		}
	}

	@RequestMapping(params = "method=asyncDeletePoi")
	public void asyncDeletePoi(@RequestParam(required = true, value="_id") String _id,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		try {
			response.setHeader(VbConstant.ACCESS_CONTROL_ALLOW_ORIGIN_KEY, VbConstant.ACCESS_CONTROL_ALLOW_ORIGIN_VALUE);
			response.setContentType(VbConstant.NORMAL_CONTENT_TYPE);
			JSONObject param = new JSONObject();
			param.put("_id", _id);
			JSONObject poisResult = managePoiService.asyncDeletePoi(param.toString());
			MsLogger.info(poisResult.toJSONString());
			response.getWriter().print(poisResult.toJSONString());
			response.setStatus(200);
			return;
		} catch (Exception e) {
			MsLogger.debug(e);
			response.getWriter().print(FastJsonUtil.error("-1", "发生异常").toString());
			response.setStatus(200);
			return;
		}
	}

	@RequestMapping(params = "method=checkPoiCanBeDeleteOrNot")
	public void checkPoiCanBeDeleteOrNot(@RequestParam(required = true, value="_id") String _id,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		try {
			response.setHeader(VbConstant.ACCESS_CONTROL_ALLOW_ORIGIN_KEY, VbConstant.ACCESS_CONTROL_ALLOW_ORIGIN_VALUE);
			response.setContentType(VbConstant.NORMAL_CONTENT_TYPE);
			JSONObject param = new JSONObject();
			param.put("_id", _id);
			JSONObject poisResult = managePoiService.checkPoiCanBeDeleteOrNot(param.toString());
			MsLogger.info(poisResult.toJSONString());
			response.getWriter().print(poisResult.toJSONString());
			response.setStatus(200);
			return;
		} catch (Exception e) {
			MsLogger.debug(e);
			response.getWriter().print(FastJsonUtil.error("-1", "发生异常").toString());
			response.setStatus(200);
			return;
		}
	}

	@RequestMapping(params = "method=asyncUploadPoisByExcel")
	public void asyncUploadPoisByExcel(
			@RequestParam(required = true, value = "excel_fileup")
			MultipartFile excel_fileup,
			@RequestParam(required = true, value = "imgzip_fileup")
			MultipartFile zip_fileup,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setHeader(VbConstant.ACCESS_CONTROL_ALLOW_ORIGIN_KEY, VbConstant.ACCESS_CONTROL_ALLOW_ORIGIN_VALUE);
		response.setContentType(VbConstant.NORMAL_CONTENT_TYPE);

		String date = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

		String pathOfDate = uploadedFilePath + date + "/";// eg: /data1/luna/uploadedFile/20160630205121714/
		String savedExcel = VbUtility.saveFile(pathOfDate, excel_fileup, ".xlsx", "poi_excel.xlsx");// eg: /data1/luna/uploadedFile/20160630205121714/poi_excel.xlsx

		String savedZip = null;
		String unZipped = null;
		if (!zip_fileup.isEmpty()) {

			// zip文件大于100M
			if (zip_fileup.getSize() > 100*1024*1024) {
				response.getWriter().print(FastJsonUtil.error("-3", "文件过大(>100M)，请将文件拆分小或者使用特殊通道上传!"));
				response.setStatus(200);
				return;
			}

			savedZip = VbUtility.saveFile(pathOfDate, zip_fileup, ".zip", "poi_excel.zip");
			JSONObject jsonResult = ZipUtil.decompressZip(savedZip, pathOfDate + "unziped/");
			if (!"0".equals(jsonResult.getString("code"))) {
				response.getWriter().print(FastJsonUtil.error("-2", jsonResult.getString("msg")));
				response.setStatus(200);
				return;
			}
			unZipped = pathOfDate + "unziped/";
		}

		try {
			HttpSession session = request.getSession(false);
			MsUser msUser = (MsUser)session.getAttribute("msUser");
			JSONObject result = this.savePois(savedExcel, unZipped, msUser);
			MsLogger.info(result.toJSONString());
			response.getWriter().print(result.toJSONString());
			response.setStatus(200);
			return;
		} catch (Exception e) {
			MsLogger.error(e);
			response.getWriter().print(FastJsonUtil.error("-3", "failed"));
			response.setStatus(200);
			return;
		}
	}

	/**
	 * 下载poi模板
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(params = "method=downloadPoiTemplete")
	public void downloadPoiTemplete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			JSONObject result = managePoiService.downloadPoiTemplete("{}");
			if ("0".equals(result.get("code"))) {
				JSONObject data = result.getJSONObject("data");
				JSONArray privateFieldsDef = data.getJSONArray("privateFieldsDef");
				Workbook wb = this.generatePoiTemplete(privateFieldsDef);

				String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".xlsx";
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				try {
					wb.write(os);
					byte[] bytes = os.toByteArray();

					response.reset();
					response.setContentType("application/msexcel;charset=utf-8");
					response.setHeader("Content-disposition", "attachment;filename= "+ fileName); 
					response.getOutputStream().write(bytes);
					response.getOutputStream().flush();
					response.getOutputStream().close();
					wb.close();
				} catch (IOException e) {
					MsLogger.error(e);
				} 
				return;
			}
		} catch (Exception e) {
			MsLogger.error(e);
		}
		response.setHeader(VbConstant.ACCESS_CONTROL_ALLOW_ORIGIN_KEY, VbConstant.ACCESS_CONTROL_ALLOW_ORIGIN_VALUE);
		response.setContentType(VbConstant.NORMAL_CONTENT_TYPE);
		response.getWriter().print(FastJsonUtil.error("-1", "发生异常").toString());
		response.setStatus(200);
	}

	/**
	 * 真正的保存Excel逻辑（含POI数据检查）
	 * @param savedExcel
	 * @param unzipedDir
	 * @param msUser
	 * @return
	 * @throws EncryptedDocumentException
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	private JSONObject savePois(String savedExcel, String unzipedDir, MsUser msUser)
			throws EncryptedDocumentException, InvalidFormatException, IOException {

		JSONObject tagsDef = managePoiService.getInitInfo("{}"); // 获取公共属性定义(tags定义)
		if (!"0".equals(tagsDef.getString("code"))) {
			return FastJsonUtil.error("-1", "没能正确获得分类定义信息");
		}
		tagsDef = tagsDef.getJSONObject("data");

		JSONArray array = tagsDef.getJSONArray("tags_def");
		
		Map<String, String> panaramaTypeName2Id = new LinkedHashMap<>();
		JSONArray panaramaTypes = tagsDef.getJSONArray("panorama_type");
		for(int i = 0;i < panaramaTypes.size(); i++) {
			String panorama_type_id = panaramaTypes.getJSONObject(i).getString("panorama_type_id");
			String panorama_type_name = panaramaTypes.getJSONObject(i).getString("panorama_type_name");
			panaramaTypeName2Id.put(panorama_type_name, panorama_type_id);
		}
		
		// 子分类名称到ID的转换Map
		Map<String, Integer> 分类名称加子分类名称To子分类编号Map = new LinkedHashMap<String, Integer>();
		for (int i = 0; i < array.size(); i++) {
			JSONObject json = array.getJSONObject(i);
			String topTagName = json.getString("tag_name");
			JSONArray subTags = json.getJSONArray("sub_tags");
			for (int j = 0; j < subTags.size(); j++) {
				JSONObject subTag = subTags.getJSONObject(j);
				分类名称加子分类名称To子分类编号Map.put(topTagName+"#"+subTag.getString("tag_name"),
						subTag.getInteger("tag_id"));
			}
		}

		JSONObject result = managePoiService.downloadPoiTemplete("{}");
		if (!"0".equals(result.get("code"))) {
			return FastJsonUtil.error("-1", result.getString("msg"));
		}
		JSONObject data = result.getJSONObject("data");
		JSONArray privateFieldsDef = data.getJSONArray("privateFieldsDef");

		Map<String, Map<String, JSONObject>> fieldsGroupByTagName = new LinkedHashMap<String, Map<String, JSONObject>>();// 根据一级类别将不同的私有字段划分到对应类别下

		Map<String, String> tagname_2_tags = new HashMap<String, String>();// 一级类别名称与id的映射
		for (int i = 0; i < privateFieldsDef.size(); i++) {
			JSONObject field = privateFieldsDef.getJSONObject(i);
			JSONObject field_def = null;
			// 有私有字段的场合
			if (field.containsKey("field_def")) {
				field_def = field.getJSONObject("field_def");
				// 没有私有字段 的场合
			} else {
				field_def = field.getJSONObject("tag_without_field");
			}

			String tag_name = field_def.getString("tag_name");
			String tags = "[" + field_def.getString("tag_id") + "]";
			tagname_2_tags.put(tag_name, tags);

			if (field.containsKey("field_def")) {
				String field_show_name = field_def.getString("field_show_name");

				Map<String, JSONObject> fieldJSONObject = new LinkedHashMap<String, JSONObject>();
				fieldJSONObject.put(field_show_name, field_def);

				fieldJSONObject = fieldsGroupByTagName.put(tag_name, fieldJSONObject);
				if (fieldJSONObject != null) {
					fieldJSONObject.put(field_show_name, field_def);
					fieldsGroupByTagName.put(tag_name, fieldJSONObject);
				}
			} else {

				// 没有私有字段，只有公有字段
				fieldsGroupByTagName.put(tag_name, new LinkedHashMap<String, JSONObject>());
			}
		}

		JSONArray checkErrors = new JSONArray();// 初步检查时的错误POIs
		JSONArray saveErrors = new JSONArray(); // 保存时出错的POIs
		JSONArray noCheckErrorPois = new JSONArray();

		InputStream inp = new FileInputStream(savedExcel);
		Workbook wb = WorkbookFactory.create(inp);
		int sheetNum = wb.getNumberOfSheets();// sheet: excel的sheet页
		for (int i = 0; i < sheetNum; i++) {
			Sheet sheet = wb.getSheetAt(i);
			if ("Templete_(备注)".equals(sheet.getSheetName())) { // Templete_(备注)": 第一页sheet页的名称
				continue;
			}
			String tag_name = sheet.getSheetName();

			Map<String, JSONObject> fieldsJsonByTag = fieldsGroupByTagName.get(tag_name); 

			if (fieldsJsonByTag == null) {
				MsLogger.debug("工作簿中的工作表名称不是合法的一级分类名称["+ tag_name + "]");
				continue;
			}

			int rowStart = Math.max(3, sheet.getFirstRowNum());	// poi数据excle中开始行
			int rowEnd = Math.max(2, sheet.getLastRowNum());	// poi数据excle中结束行
			Row row0 = sheet.getRow(0);
			for (int j = rowStart; j <= rowEnd; j++) {
				Row row = sheet.getRow(j);
				Boolean allBlank = true;
				for (int z = 0; allBlank && z < fieldsJsonByTag.size() + PoiCommon.Excel.公有字段个数; z++) {// fieldsJsonByTag.size() + PoiCommon.Excel.公有字段个数： 私有字段格式+公有字段个数
					if (!CharactorUtil.isEmpyty(getCellValueAsString(row.getCell(0)).trim())) { // 检测该行从1-fieldsJsonByTag.size() + PoiCommon.Excel.公有字段个数 列是否都是空
						allBlank = false;														// excel中可能存在超出输入范围的数据
					}
				}
				if (allBlank) {
					continue;
				}
				// 公共字段
				// POI名称
				String long_title = getCellValueAsString(row.getCell(0)).trim();
				// POI别名
				String short_title = getCellValueAsString(row.getCell(1)).trim();

				// 纬度
				String lat = getCellValueAsString(row.getCell(2)).trim();
				// 经度
				String lng = getCellValueAsString(row.getCell(3)).trim();
				// 地域名称
				String mergerName = getCellValueAsString(row.getCell(4)).trim().replace("，", ",");
				String zone_id = ZONENAME_2_CODE_MAP.get(mergerName);

				// 详细地址
				String detail_address = getCellValueAsString(row.getCell(5)).trim();

				// 二级分类名称
				String subTagName = getCellValueAsString(row.getCell(6)).trim();
				Integer subTag = 0;

				// 详细介绍
				String brief_introduction = getCellValueAsString(row.getCell(7)).trim();
				// 缩略图
				String thumbnail = getCellValueAsString(row.getCell(8)).trim();
				// 8.全景类别.panorama_type默认为"专辑"
				String panorama_type = "2";
				String panorama_type_name = getCellValueAsString(row.getCell(9)).trim();
				// 8.全景数据ID
				String panorama = getCellValueAsString(row.getCell(10)).trim();
				// 9.联系电话
				String contact_phone = getCellValueAsString(row.getCell(11)).trim();

				Boolean isError = (zone_id == null ? Boolean.TRUE : Boolean.FALSE);

				// 检查二级分类是否合法
				if (!CharactorUtil.isEmpyty(subTagName)) {
					subTag = 分类名称加子分类名称To子分类编号Map.get(tag_name + "#" + subTagName);
				}
				if (subTag == null) {
					isError = true;
				}
				
				// 检查全景类型是否合法
				if(!CharactorUtil.isEmpyty(panorama_type_name)){
					panorama_type = panaramaTypeName2Id.get(panorama_type_name);
				}
				if(panorama_type == null){
					isError = true;
				}
				
				// 检查共有字段是否合法
				isError = isError || null != PoiCommon.getInstance().checkPoiName(long_title);
				isError = isError || null != PoiCommon.getInstance().checkPoiOtherName(short_title);
				isError = isError || null != PoiCommon.getInstance().checkLat(lat);
				isError = isError || null != PoiCommon.getInstance().checkLng(lng);
				isError = isError || null != PoiCommon.getInstance().checkDetailAddress(detail_address);
				isError = isError || null != PoiCommon.getInstance().checkPanoRama(panorama);
				isError = isError || null != PoiCommon.getInstance().checkContactPhone(contact_phone);
				isError = isError || CharactorUtil.fileFieldIsError(thumbnail, unzipedDir);

				// 检查私有字段是否合法(Excel模式)
				for (int z = PoiCommon.Excel.公有字段个数; !isError && z < fieldsJsonByTag.size() + PoiCommon.Excel.公有字段个数; z++) {
					String field_show_name = getCellValueAsString(row0.getCell(z)).trim();
					String value = getCellValueAsString(row.getCell(z)).trim();
					isError = isError || PoiCommon.getInstance().checkPrivateField(value, fieldsJsonByTag.get(field_show_name), Boolean.TRUE);
				}

				// check检查有错误
				if (isError) {
					JSONObject checkErrorPoi = new JSONObject();
					checkErrorPoi.put("long_title", long_title);
					checkErrorPoi.put("tag_name", tag_name);
					checkErrorPoi.put("province_name", this.getProvinceName(mergerName));
					checkErrorPoi.put("city_name", this.getCityName(mergerName));
					checkErrorPoi.put("lat", lat);
					checkErrorPoi.put("lng", lng);
					checkErrorPoi.put("tags", tagname_2_tags.get(tag_name));
					checkErrorPoi.put("subTag", subTag);

					checkErrorPoi.put("short_title", short_title);
					checkErrorPoi.put("zone_id", CharactorUtil.nullToBlank(zone_id));
					checkErrorPoi.put("detail_address", detail_address);
					checkErrorPoi.put("brief_introduction", brief_introduction);
					checkErrorPoi.put("thumbnail", thumbnail);

					// 8.全景标识
					checkErrorPoi.put("panorama_type", panorama_type);
					// 8.全景数据ID 
					checkErrorPoi.put("panorama", panorama);
					// 9.联系电话
					checkErrorPoi.put("contact_phone", contact_phone);

					// 记录其他私有字段，页面编辑用
					for (int z = PoiCommon.Excel.公有字段个数; z < fieldsJsonByTag.size() + PoiCommon.Excel.公有字段个数; z++) {
						String field_show_name = getCellValueAsString(row0.getCell(z)).trim();
						String value = getCellValueAsString(row.getCell(z)).trim();
						JSONObject fieldDef = fieldsJsonByTag.get(field_show_name);
						if (fieldDef != null) {
							String field_name = fieldDef.getString("field_name");
							value = this.getValueFromFieldDef(fieldDef, null, value);
							checkErrorPoi.put(field_name, value);
						}
					}

					// 记录有错误的Poi
					checkErrors.add(checkErrorPoi);
				} else {
					// 上传公共字段的图片
					if (!CharactorUtil.isEmpyty(thumbnail)) {
						boolean uploadError = false;
						try {
							String ext = VbUtility.getExtensionOfPicFileName(thumbnail);
							if (ext != null) {
								String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
								String fileName = "/" + VbMD5.generateToken() + ext;
								JSONObject uploadResult = COSUtil.getInstance().upload2Cloud(unzipedDir + thumbnail,
										COSUtil.LUNA_BUCKET, COSUtil.getCosPoiPicFolderPath() + "/" + date, fileName);
								if ("0".equals(uploadResult.getString("code"))) {
									JSONObject uploadedData = uploadResult.getJSONObject("data");
									thumbnail = uploadedData.getString("access_url");
								} else {
									// 图片上传错误，记入检查列表
									uploadError = true;
								}
							}
						} catch (Exception e) {
							MsLogger.debug(e);
							uploadError = true;
						}
						if (uploadError) {
							JSONObject checkErrorPoi = new JSONObject();
							checkErrorPoi.put("long_title", long_title);
							checkErrorPoi.put("tag_name", tag_name);
							checkErrorPoi.put("province_name", this.getProvinceName(mergerName));
							checkErrorPoi.put("city_name", this.getCityName(mergerName));
							checkErrorPoi.put("lat", lat);
							checkErrorPoi.put("lng", lng);
							checkErrorPoi.put("tags", tagname_2_tags.get(tag_name));
							checkErrorPoi.put("subTag", subTag);

							checkErrorPoi.put("short_title", short_title);
							checkErrorPoi.put("zone_id", CharactorUtil.nullToBlank(zone_id));
							checkErrorPoi.put("detail_address", detail_address);
							checkErrorPoi.put("brief_introduction", brief_introduction);
							checkErrorPoi.put("thumbnail", thumbnail);

							// 8.全景类型
							checkErrorPoi.put("panorama_type", panorama_type);
							// 8.全景数据ID
							checkErrorPoi.put("panorama", panorama);
							// 9.联系电话
							checkErrorPoi.put("contact_phone", contact_phone);

							// 记录其他私有字段，页面编辑用
							for (int z = PoiCommon.Excel.公有字段个数; z < fieldsJsonByTag.size() + PoiCommon.Excel.公有字段个数; z++) {
								String field_show_name = getCellValueAsString(row0.getCell(z)).trim();
								String value = getCellValueAsString(row.getCell(z)).trim();
								JSONObject fieldDef = fieldsJsonByTag.get(field_show_name);
								if (fieldDef != null) {
									String field_name = fieldDef.getString("field_name");
									value = this.getValueFromFieldDef(fieldDef, null, value);
									checkErrorPoi.put(field_name, value);
								}
							}
							// 记录有错误的Poi
							checkErrors.add(checkErrorPoi);
							continue;
						}
					}
					JSONObject noCheckErrorPoi = new JSONObject();
					noCheckErrorPoi.put("thumbnail", thumbnail);

					noCheckErrorPoi.put("long_title", long_title);
					noCheckErrorPoi.put("tag_name", tag_name);
					noCheckErrorPoi.put("province_name", this.getProvinceName(mergerName));
					noCheckErrorPoi.put("city_name", this.getCityName(mergerName));
					noCheckErrorPoi.put("lat", lat);
					noCheckErrorPoi.put("lng", lng);

					noCheckErrorPoi.put("short_title", short_title);
					noCheckErrorPoi.put("zone_id", CharactorUtil.nullToBlank(zone_id));
					noCheckErrorPoi.put("detail_address", detail_address);
					noCheckErrorPoi.put("brief_introduction", brief_introduction);

					noCheckErrorPoi.put("thumbnail_1_1", "");
					noCheckErrorPoi.put("thumbnail_16_9", "");
					noCheckErrorPoi.put("merger_name", "中国," + mergerName);
					noCheckErrorPoi.put("tags", tagname_2_tags.get(tag_name));
					noCheckErrorPoi.put("subTag", subTag);

					// 8.全景类型
					noCheckErrorPoi.put("panorama_type", panorama_type);
					// 8.全景数据ID
					noCheckErrorPoi.put("panorama", panorama);
					noCheckErrorPoi.put("contact_phone", contact_phone);

					try {
						// 记录其他私有字段，页面编辑用
						for (int z = PoiCommon.Excel.公有字段个数; z < fieldsJsonByTag.size() + PoiCommon.Excel.公有字段个数; z++) {
							String field_show_name = getCellValueAsString(row0.getCell(z)).trim();
							String value = getCellValueAsString(row.getCell(z)).trim();
							JSONObject fieldDef = fieldsJsonByTag.get(field_show_name);
							if (fieldDef != null) {
								String field_name = fieldDef.getString("field_name");
								// 需要上传私有字段的图片
								value = this.getValueFromFieldDef(fieldDef, unzipedDir, value);
								noCheckErrorPoi.put(field_name, value);
							}
						}
					} catch (IllegalArgumentException lae) {
						JSONObject checkErrorPoi = new JSONObject();
						checkErrorPoi.put("long_title", long_title);
						checkErrorPoi.put("tag_name", tag_name);
						checkErrorPoi.put("province_name", this.getProvinceName(mergerName));
						checkErrorPoi.put("city_name", this.getCityName(mergerName));
						checkErrorPoi.put("lat", lat);
						checkErrorPoi.put("lng", lng);
						checkErrorPoi.put("tags", tagname_2_tags.get(tag_name));
						checkErrorPoi.put("subTag", subTag);

						checkErrorPoi.put("short_title", short_title);
						checkErrorPoi.put("zone_id", CharactorUtil.nullToBlank(zone_id));
						checkErrorPoi.put("detail_address", detail_address);
						checkErrorPoi.put("brief_introduction", brief_introduction);
						checkErrorPoi.put("thumbnail", thumbnail);

						// 8.全景类型
						checkErrorPoi.put("panorama_type",panorama_type);
						// 8.全景数据ID
						checkErrorPoi.put("panorama", panorama);
						// 9.联系电话
						checkErrorPoi.put("contact_phone", contact_phone);
						// 记录其他私有字段，页面编辑用
						for (int z = PoiCommon.Excel.公有字段个数; z < fieldsJsonByTag.size() + PoiCommon.Excel.公有字段个数; z++) {
							String field_show_name = getCellValueAsString(row0.getCell(z)).trim();
							String value = getCellValueAsString(row.getCell(z)).trim();
							JSONObject fieldDef = fieldsJsonByTag.get(field_show_name);
							if (fieldDef != null) {
								String field_name = fieldDef.getString("field_name");
								value = this.getValueFromFieldDef(fieldDef, null, value);
								checkErrorPoi.put(field_name, value);
							}
						}
						// 记录有错误的Poi
						checkErrors.add(checkErrorPoi);
						continue;
					}

					// 记录无错误的Poi
					noCheckErrorPois.add(noCheckErrorPoi);
				}
			}
		}

		JSONObject param = new JSONObject();
		param.put("no_check_errors", noCheckErrorPois);
		JSONObject addedResult = managePoiService.savePois(param.toString(), msUser);
		if (!"0".equals(addedResult.getString("code"))) {
			return FastJsonUtil.error("-1", result.getString("msg"));
		}
		JSONObject dataOfAddedResult = addedResult.getJSONObject("data");
		saveErrors = dataOfAddedResult.getJSONArray("saveErrors");

		JSONObject returnResult = new JSONObject();
		returnResult.put("checkErrors", checkErrors);
		returnResult.put("saveErrors", saveErrors);
		return FastJsonUtil.sucess("OK", returnResult);
	}

	private Workbook generatePoiTemplete(JSONArray privateFieldsDef)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(PoiCommon.Excel.模板文件名称);
		Workbook wb = WorkbookFactory.create(is);
		is.close();

		Map<String, List<JSONObject>> fieldsGroupByTagName = new LinkedHashMap<String, List<JSONObject>>();
		for (int i = 0; i < privateFieldsDef.size(); i++) {
			JSONObject field = privateFieldsDef.getJSONObject(i);
			JSONObject field_def = null;
			// 有私有字段的场合
			if (field.containsKey("field_def")) {
				field_def = field.getJSONObject("field_def");
				String tag_name = field_def.getString("tag_name");
				List<JSONObject> lstJSONObject = new ArrayList<JSONObject>();
				lstJSONObject.add(field_def);
				lstJSONObject = fieldsGroupByTagName.put(tag_name, lstJSONObject);
				if (lstJSONObject != null) {
					lstJSONObject.add(field_def);
					fieldsGroupByTagName.put(tag_name, lstJSONObject);
				}

				// 没有私有字段 的场合
			} else {
				field_def = field.getJSONObject("tag_without_field");
				String tag_name = field_def.getString("tag_name");
				fieldsGroupByTagName.put(tag_name, new ArrayList<JSONObject>());
			}
		}

		Set<Entry<String, List<JSONObject>>> entrySet = fieldsGroupByTagName.entrySet();

		XSSFFont titleFont = (XSSFFont)wb.createFont();
		titleFont.setBold(true);
		titleFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		titleFont.setFontHeightInPoints((short)12);

		CellStyle titleStyle = wb.createCellStyle();
		titleStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直    
		titleStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 水平
		titleStyle.setFont(titleFont);
		titleStyle.setWrapText(true);

		XSSFFont tipsFont = (XSSFFont)wb.createFont();
		tipsFont.setColor(IndexedColors.RED.index);
		tipsFont.setFontHeightInPoints((short)10);

		CellStyle tipsStyle = wb.createCellStyle(); // 样式对象    
		tipsStyle.setFont(tipsFont);
		tipsStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直

		tipsStyle.setWrapText(true);

		for (Entry<String, List<JSONObject>> entry : entrySet) {
			String tag_name = entry.getKey();
			List<JSONObject> privateFields = entry.getValue();
			this.createCommonField(wb, tag_name, titleStyle, tipsStyle);
			// 存在私有字段的场合再生成私有字段内容
			if (!privateFields.isEmpty()) {
				this.createPrivateField(wb, tag_name, privateFields, titleStyle, tipsStyle);
			}
		}
		wb.setActiveSheet(0);
		return wb;
	}

	/**
	 * 创建每个sheet页的公共字段模板
	 * @param wb
	 * @param sheetName
	 */
	private void createCommonField(Workbook wb, String sheetName,
			CellStyle titleStyle,
			CellStyle tipsStyle) {

		Sheet sheet = wb.createSheet(sheetName);
		// 默认的zoom值
		sheet.setZoom(PoiCommon.Excel.INIT_ZOOM);

		// 名称行
		int rowIndex = 0;
		// 创建名称行1
		Row row0 = sheet.createRow(rowIndex);
		// 创建名称行2或说明行
		Row row1 = sheet.createRow(rowIndex + 1);
		// 创建说明行
		Row row2 = sheet.createRow(rowIndex + 2);
		// 相对布局从G列开始(建议G列以前内容保持不变)
		int colBase = PoiCommon.Excel.INDEX_COL_BASE;

		int i = 0;
		for (i = 0; i < PoiCommon.Excel.公有字段个数; i++) {
			Cell cell = row0.createCell(i, Cell.CELL_TYPE_STRING);
			cell.setCellStyle(titleStyle);
			cell = row1.createCell(i, Cell.CELL_TYPE_STRING);
			if (i > 3 && i < 6) {
				cell.setCellStyle(titleStyle);
			}
			cell = row2.createCell(i, Cell.CELL_TYPE_STRING);
			cell.setCellStyle(tipsStyle);
		}

		// 合并单元格
		// POI名称(纵向合并)
		sheet.addMergedRegion(new CellRangeAddress(rowIndex + 1, rowIndex + 2,
				PoiCommon.Excel.INDEX_COL_A, PoiCommon.Excel.INDEX_COL_A));
		// POI别名(纵向合并)
		sheet.addMergedRegion(new CellRangeAddress(rowIndex + 1, rowIndex + 2,
				PoiCommon.Excel.INDEX_COL_B, PoiCommon.Excel.INDEX_COL_B));
		// 纬度(纵向合并)
		sheet.addMergedRegion(new CellRangeAddress(rowIndex + 1, rowIndex + 2,
				PoiCommon.Excel.INDEX_COL_C, PoiCommon.Excel.INDEX_COL_C));
		// 经度(纵向合并)
		sheet.addMergedRegion(new CellRangeAddress(rowIndex + 1, rowIndex + 2,
				PoiCommon.Excel.INDEX_COL_D, PoiCommon.Excel.INDEX_COL_D));
		// 地址(横向合并单元格)
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex,
				PoiCommon.Excel.INDEX_COL_E, PoiCommon.Excel.INDEX_COL_F));
		for (i = 0, colBase = PoiCommon.Excel.INDEX_COL_BASE; i < PoiCommon.Excel.NM_COL_BASES.length; i++, colBase++) {
			sheet.addMergedRegion(new CellRangeAddress(rowIndex + 1, rowIndex + 2, colBase, colBase));
		}

		// 标题名称
		row0.getCell(PoiCommon.Excel.INDEX_COL_A).setCellValue(PoiCommon.Excel.NM_COL_A);
		row0.getCell(PoiCommon.Excel.INDEX_COL_B).setCellValue(PoiCommon.Excel.NM_COL_B);
		row0.getCell(PoiCommon.Excel.INDEX_COL_C).setCellValue(PoiCommon.Excel.NM_COL_C);
		row0.getCell(PoiCommon.Excel.INDEX_COL_D).setCellValue(PoiCommon.Excel.NM_COL_D);
		row0.getCell(PoiCommon.Excel.INDEX_COL_E).setCellValue(PoiCommon.Excel.NM_COL_E_F);
		// 基准列重置
		for (i = 0, colBase = PoiCommon.Excel.INDEX_COL_BASE; i < PoiCommon.Excel.NM_COL_BASES.length; i++, colBase++) {
			row0.getCell(colBase).setCellValue(PoiCommon.Excel.NM_COL_BASES[i]);
		}

		// 标题样式
		row0.getCell(PoiCommon.Excel.INDEX_COL_A).setCellStyle(titleStyle);
		row0.getCell(PoiCommon.Excel.INDEX_COL_B).setCellStyle(titleStyle);
		row0.getCell(PoiCommon.Excel.INDEX_COL_C).setCellStyle(titleStyle);
		row0.getCell(PoiCommon.Excel.INDEX_COL_D).setCellStyle(titleStyle);
		row0.getCell(PoiCommon.Excel.INDEX_COL_E).setCellStyle(titleStyle);
		// 基准列重置
		for (i = 0, colBase = PoiCommon.Excel.INDEX_COL_BASE; i < PoiCommon.Excel.NM_COL_BASES.length; i++, colBase++) {
			row0.getCell(colBase).setCellStyle(titleStyle);
		}

		// 提示内容(文字和样式)
		// POI名称
		row1.getCell(PoiCommon.Excel.INDEX_COL_A).setCellValue("POI点全称，必填");
		row1.getCell(PoiCommon.Excel.INDEX_COL_A).setCellStyle(tipsStyle);

		// POI别名
		row1.getCell(PoiCommon.Excel.INDEX_COL_B).setCellValue("可为空（POI其它叫法）");
		row1.getCell(PoiCommon.Excel.INDEX_COL_B).setCellStyle(tipsStyle);

		// 省、市、区/县
		row1.getCell(PoiCommon.Excel.INDEX_COL_E).setCellValue("省,市,区/县");
		row1.getCell(PoiCommon.Excel.INDEX_COL_E).setCellStyle(titleStyle);
		row2.getCell(PoiCommon.Excel.INDEX_COL_E).setCellValue("XX省,XX市,XX区/县（值取自地域表格中）");

		// 详细地址
		row1.getCell(PoiCommon.Excel.INDEX_COL_F).setCellValue("详细地址");
		row1.getCell(PoiCommon.Excel.INDEX_COL_F).setCellStyle(titleStyle);

		// 基准列重置
		for (i = 0, colBase = PoiCommon.Excel.INDEX_COL_BASE; i < PoiCommon.Excel.NM_COL_BASES.length; i++, colBase++) {
			row1.getCell(colBase).setCellValue(PoiCommon.Excel.DESC_COL_BASES[i]);
			row1.getCell(colBase).setCellStyle(tipsStyle);
		}

		for (i = 0; i < PoiCommon.Excel.公有字段个数; i++) {
			row2.getCell(i).setCellStyle(tipsStyle);
		}
	}

	/**
	 * 创建每个sheet页的公共字段模板
	 * @param wb
	 * @param sheetName
	 */
	private void createPrivateField(Workbook wb, String sheetName, List<JSONObject> privateFields,
			CellStyle titleStyle,
			CellStyle tipsStyle) {

		Sheet sheet = wb.getSheet(sheetName);
		Row row0 = sheet.getRow(0);
		Row row1 = sheet.getRow(1);
		Row row2 = sheet.getRow(2);
		int lastCell = row0.getLastCellNum();

		for (int i = 0; i < privateFields.size(); i++) {
			JSONObject privateField = privateFields.get(i);
			String field_show_name = privateField.getString("field_show_name");
			Cell title = row0.createCell(lastCell, Cell.CELL_TYPE_STRING);
			title.setCellValue(field_show_name);
			title.setCellStyle(titleStyle);

			String field_tips_for_templete = privateField.getString("field_tips_for_templete");
			Cell tips = row1.createCell(lastCell, Cell.CELL_TYPE_STRING);
			tips.setCellValue(field_tips_for_templete);
			tips.setCellStyle(tipsStyle);
			Cell tips_2 = row2.createCell(lastCell, Cell.CELL_TYPE_STRING);
			tips_2.setCellStyle(tipsStyle);

			sheet.addMergedRegion(new CellRangeAddress(1, 2, lastCell, lastCell));

			lastCell++;
		}
		for (int i = 0; i < lastCell; i++) {
			sheet.autoSizeColumn(i, true);
		}
	}
	private static String getCellValueAsString(Cell cell) {
		if (cell == null) {
			return "";
		}
		String result = "";
		int type = cell.getCellType();
		switch (type) {
		case HSSFCell.CELL_TYPE_BLANK:
			result = "";
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			BigDecimal big = new BigDecimal(cell.getNumericCellValue());
			BigDecimal intBig = big.setScale(0, BigDecimal.ROUND_DOWN);
			if (intBig.compareTo(big) == 0) {
				result = String.valueOf(intBig.longValue());
			} else {
				result =big.setScale(6, BigDecimal.ROUND_HALF_UP).toPlainString();
			}
			break;
		case HSSFCell.CELL_TYPE_STRING:// 输入值为string类型
			result = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			result = String.valueOf(cell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			result = cell.getCellFormula();
			break;
		case HSSFCell.CELL_TYPE_ERROR:
			result = "";
			break;
		default:
			result = "";
			break;
		}
		return result;
	}

	private String getProvinceName(String mergerName) {
		if (mergerName == null || mergerName.isEmpty()) {
			return "";
		}
		String[] names = mergerName.split(",");
		if (names.length < 1) {
			return "";
		}
		return names[0];
	}

	private String getCityName(String mergerName) {
		if (mergerName == null || mergerName.isEmpty()) {
			return "";
		}
		String[] names = mergerName.split(",");
		if (names.length < 2) {
			return "";
		}
		return names[1];
	}
	private String getValueFromFieldDef(JSONObject fieldDef, String unzipedDir, String value) {
		int field_type = fieldDef.getInteger("field_type");
		boolean uploadError = false;
		switch (field_type) {
		case VbConstant.POI_FIELD_TYPE.复选框列表:
			JSONArray extension_attr = fieldDef.getJSONArray("extension_attrs");
			Map<String, String> labelMap = new HashMap<String, String>();
			for (int i = 0; i < extension_attr.size(); i++) {
				JSONObject json = extension_attr.getJSONObject(i);
				String label = json.getString(String.valueOf(i+1));
				labelMap.put(label, String.valueOf(i+1));
			}
			StringBuilder sb = new StringBuilder();
			String[] vals = value.split("," , extension_attr.size());
			for (int i = 0; i < vals.length; i++) {
				String checkVal = labelMap.remove(vals[i]);
				if (checkVal != null) {
					if (sb.length() != 0) {
						sb.append(",");
					}
					sb.append(checkVal);
				}
			}
			return sb.toString();
		case VbConstant.POI_FIELD_TYPE.图片:
			if (unzipedDir == null) {
				return value;
			}
			if (!CharactorUtil.isEmpyty(value)) {
				try {
					String ext = VbUtility.getExtensionOfPicFileName(value);
					if (ext != null) {
						String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
						String fileName = "/" + VbMD5.generateToken() + ext;
						JSONObject uploadResult = COSUtil.getInstance().upload2Cloud(unzipedDir + value,
								COSUtil.LUNA_BUCKET, VODUtil.getVODPoiVideoFolderPath() + "/" + date, fileName);
						if ("0".equals(uploadResult.getString("code"))) {
							JSONObject uploadedData = uploadResult.getJSONObject("data");
							value = uploadedData.getString("access_url");
						} else {
							uploadError = true;
						}
					}
				} catch (Exception e) {
					MsLogger.debug(e);
					uploadError = true;
				}
			}
			if (uploadError) {
				throw new IllegalArgumentException("文件上传有错误发生");
			}
			return value;
		case VbConstant.POI_FIELD_TYPE.音频:
			if (unzipedDir == null) {
				return value;
			}
			if (!CharactorUtil.isEmpyty(value)) {
				try {
					String ext = VbUtility.getExtensionOfAudioFileName(value);
					if (ext != null) {
						String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
						String fileName = "/" + VbMD5.generateToken() + ext;
						JSONObject uploadResult = COSUtil.getInstance().upload2Cloud(unzipedDir + value,
								COSUtil.LUNA_BUCKET, COSUtil.getCosPoiAudioFolderPath() + "/" + date, fileName);
						if ("0".equals(uploadResult.getString("code"))) {
							JSONObject uploadedData = uploadResult.getJSONObject("data");
							value = uploadedData.getString("access_url");
						} else {
							uploadError = true;
						}
					}
				} catch (Exception e) {
					MsLogger.debug(e);
					uploadError = true;
				}
			}
			if (uploadError) {
				throw new IllegalArgumentException("文件上传有错误发生");
			}
			return value;
		case VbConstant.POI_FIELD_TYPE.视频:
			if (unzipedDir == null) {
				if(value == null || value.trim().isEmpty()){
					return "";
				} else {
					return "没有上传压缩文件";
				}
			}
			if (!CharactorUtil.isEmpyty(value)) {
				try {
					String ext = VbUtility.getExtensionOfVideoFileName(value);
					if (ext != null) {
						String fileName = VbMD5.generateToken() + ext;// 生成文件名
						String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
						JSONObject uploadResult = VODUtil.getInstance().upload2Cloud(
								unzipedDir + value, VODUtil.getVODPoiVideoFolderPath() + "/" + date, fileName);
						if ("0".equals(uploadResult.getString("code"))) {
							JSONObject uploadedData = uploadResult.getJSONObject("data");
							value = uploadedData.getString("vod_file_id");
						} else {
							uploadError = true;
						}
					}
				} catch (Exception e) {
					MsLogger.debug(e);
					uploadError = true;
				}
			}
			if (uploadError) {
				throw new IllegalArgumentException("文件上传有错误发生");
			}
			return value;
		default:
			return value;
		}
	}
}
