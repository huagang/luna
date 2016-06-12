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
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
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
import ms.luna.biz.util.JsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.biz.util.VbMD5;
import ms.luna.biz.util.VbUtility;
import ms.luna.biz.util.ZipUtil;
import ms.luna.web.common.BasicCtrl;
import ms.luna.web.common.PulldownCtrl;
import ms.luna.web.model.common.SimpleModel;
import ms.luna.web.model.managepoi.PoiModel;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Component("managePoiCtrl")
@Controller
@RequestMapping("/manage_poi.do")
public class ManagePoiCtrl extends BasicCtrl{

	private final static String uploadedFilePath = "/data1/luna/uploadedFile/";

	private Map<String, String> ZONENAME_2_CODE_MAP = new HashMap<String, String>();

	public static final Integer 介绍最大长度 = 1024*20;

	@Autowired
	private ManagePoiService managePoiService;

	@Resource(name="pulldownCtrl")
	private PulldownCtrl pulldownCtrl;

	public ManagePoiCtrl() {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("poi_templete.xlsx");
		try {
			Workbook wb = WorkbookFactory.create(is);
			Sheet sheet = wb.getSheet("Templete_(地域_备注)");
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

		List<SimpleModel> checkBoxTags = poiModel.getPoiTags();

		JSONObject params = JSONObject.fromObject("{}");
		JSONObject result = managePoiService.getInitInfo(params.toString());

		if ("0".equals(result.getString("code"))) {
			JSONObject data = result.getJSONObject("data");

			JSONArray tags = data.getJSONArray("tags");
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
		}

		session.setAttribute("sessionCheckBoxTags", checkBoxTags);
		ModelAndView mav = new ModelAndView("/manage_poi.jsp");

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
		session.setAttribute("citys", lstCitys);

		// 区/县信息
		List<SimpleModel> lstCountys = new ArrayList<SimpleModel>();
		simpleModel = new SimpleModel();
		simpleModel.setValue("");
		simpleModel.setLabel("请选择区/县");
		lstCountys.add(simpleModel);
//		mav.addObject("countys", lstCountys);
		session.setAttribute("countys", lstCountys);

		mav.addObject("addPoiModel", poiModel);
		mav.addObject("editPoiModel", new PoiModel());
		
		session.setAttribute("poi_tags_length", poiModel.getPoiTags().size());
		
		return mav;
	}

	@RequestMapping(params = "method=asyncSearchPois")
	public void asyncSearchPois(@RequestParam(required = false) Integer offset,
			@RequestParam(required = false) Integer limit,
			@RequestParam(required = false) String filterName,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		JSONObject resJSON = JSONObject.fromObject("{}");
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/html; charset=UTF-8");
			JSONObject param = JSONObject.fromObject("{}");
			
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
			if ("0".equals(poisResult.getString("code"))) {
				JSONObject data = poisResult.getJSONObject("data");

				if (offset == null) {
					offset = 0;
				}
				JSONArray arrays = data.getJSONArray("pois");
				JSONArray rows = JSONArray.fromObject("[]");
				for (int i = 0; i < arrays.size(); i++) {
					JSONObject poiJson = arrays.getJSONObject(i);
					
					JSONObject row = JSONObject.fromObject("{}");
					row.put("number", (i+1)+offset);
					String short_title = poiJson.getString("short_title");
					String long_title = poiJson.getString("long_title");
					String poi_name = long_title.isEmpty() ? short_title : long_title;
					row.put("poi_name", poi_name);

					row.put("_id", poiJson.getString("_id"));
					row.put("lng", poiJson.getString("lng"));
					row.put("lat", poiJson.getString("lat"));
					row.put("poi_tags", poiJson.getString("tags"));
					row.put("city_name", poiJson.getString("city_name"));
					row.put("province_name", poiJson.getString("province_name"));

					rows.add(row);
				}
				resJSON.put("rows", rows);
				resJSON.put("total", data.getInt("total"));
			} else {
				resJSON.put("total", 0);
			}

			response.getWriter().print(resJSON.toString());
			response.setStatus(200);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.getWriter().print(JsonUtil.error("-1", "异常").toString());
		response.setStatus(200);
		return;
	}

	@RequestMapping(params = "method=asyncDeletePoi")
	public void asyncDeletePoi(@RequestParam(required = true, value="_id") String _id,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/html; charset=UTF-8");
			JSONObject param = JSONObject.fromObject("{}");
			param.put("_id", _id);
			JSONObject poisResult = managePoiService.asyncDeletePoi(param.toString());
			response.getWriter().print(poisResult.toString());
			response.setStatus(200);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.getWriter().print(JsonUtil.error("-1", "发生异常").toString());
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
			@RequestParam(required = true, value = "thumbnail") MultipartFile file,
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
		String fileNameInCloud = date + "_" + VbMD5.generateToken() + ext;
		super.uploadLocalFile2Cloud(request, response, file, COSUtil.getCosPoiPicFolderPath(), fileNameInCloud);
	}

	/**
	 * 异步上传音频
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(params = "method=upload_audio")
	public void uploadAudio(
			@RequestParam(required = true, value = "audio") MultipartFile file,
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
		String fileNameInCloud = date + "_" + VbMD5.generateToken() + ext;
		super.uploadLocalFile2Cloud(request, response, file, COSUtil.getCosPoiPicFolderPath(), fileNameInCloud);
	}

	/**
	 * 异步上传音频
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(params = "method=upload_video")
	public void uploadVideo(
			@RequestParam(required = true, value = "video") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
//			super.uploadLocalFile2Cloud(request, response, file, COSUtil.picAddress);
	}

	@RequestMapping(params = "method=asyncUploadPoisByExcel")
	public void asyncUploadPoisByExcel(
			@RequestParam(required = true, value = "excel_fileup")
			MultipartFile excel_fileup,
			@RequestParam(required = true, value = "imgzip_fileup")
			MultipartFile zip_fileup,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");

		String date = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

		String pathOfDate = uploadedFilePath + date + "/";
		String savedExcel = VbUtility.saveFile(pathOfDate, excel_fileup, ".xlsx", "poi_excel.xlsx");

		String savedZip = null;
		String unZipped = null;
		if (!zip_fileup.isEmpty()) {

			// zip文件大于100M
			if (zip_fileup.getSize() > 100*1024*1024) {
				response.getWriter().print(JsonUtil.error("-3", "文件过大(>100M)，请将文件拆分小或者使用特殊通道上传!"));
				response.setStatus(200);
				return;
			}

			savedZip = VbUtility.saveFile(pathOfDate, zip_fileup, ".zip", "poi_excel.zip");
			JSONObject jsonResult = ZipUtil.decompressZip(savedZip, pathOfDate + "unziped/");
			if (!"0".equals(jsonResult.getString("code"))) {
				response.getWriter().print(JsonUtil.error("-2", jsonResult.getString("msg")));
				response.setStatus(200);
				return;
			}
			unZipped = pathOfDate + "unziped/";
		}
		
		try {
			HttpSession session = request.getSession(false);
			MsUser msUser = (MsUser)session.getAttribute("msUser");
			JSONObject result = this.savePois(savedExcel, unZipped, msUser);
			response.getWriter().print(result.toString());
			response.setStatus(200);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print(JsonUtil.error("-3", "failed"));
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
					e.printStackTrace();
				} 
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(JsonUtil.error("-1", "发生异常").toString());
		response.setStatus(200);
	}

	private JSONObject savePois(String savedExcel, String unzipedDir, MsUser msUser)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		
		JSONObject result = managePoiService.downloadPoiTemplete("{}");
		if (!"0".equals(result.get("code"))) {
			return JsonUtil.error("-1", result.getString("msg"));
		}
		JSONObject data = result.getJSONObject("data");
		JSONArray privateFieldsDef = data.getJSONArray("privateFieldsDef");

		Map<String, Map<String, JSONObject>> fieldsGroupByTagName = new LinkedHashMap<String, Map<String, JSONObject>>();

		Map<String, String> tagname_2_tags = new HashMap<String, String>();
		for (int i = 0; i < privateFieldsDef.size(); i++) {
			JSONObject field = privateFieldsDef.getJSONObject(i);
			JSONObject field_def = field.getJSONObject("field_def");
			String tag_name = field_def.getString("tag_name");
			String tags = "[" + field_def.getString("tag_id") + "]";
			tagname_2_tags.put(tag_name, tags);
			String field_show_name = field_def.getString("field_show_name");

			Map<String, JSONObject> fieldJSONObject = new LinkedHashMap<String, JSONObject>();
			fieldJSONObject.put(field_show_name, field_def);

			fieldJSONObject = fieldsGroupByTagName.put(tag_name, fieldJSONObject);
			if (fieldJSONObject != null) {
				fieldJSONObject.put(field_show_name, field_def);
				fieldsGroupByTagName.put(tag_name, fieldJSONObject);
			}
		}

		JSONArray checkErrors = new JSONArray();
		JSONArray saveErrors = new JSONArray();
		JSONArray noCheckErrorPois = new JSONArray();

		InputStream inp = new FileInputStream(savedExcel);
		Workbook wb = WorkbookFactory.create(inp);
		int sheetNum = wb.getNumberOfSheets();
		for (int i = 0; i < sheetNum; i++) {
			Sheet sheet = wb.getSheetAt(i);
			if ("Templete_(地域_备注)".equals(sheet.getSheetName())) {
				continue;
			}
			String tag_name = sheet.getSheetName();

			Map<String, JSONObject> fieldsJsonByTag = fieldsGroupByTagName.get(tag_name); 

			if (fieldsJsonByTag == null) {
				// TODO:
				continue;
			}

			int rowStart = Math.max(3, sheet.getFirstRowNum());
			int rowEnd = Math.max(2, sheet.getLastRowNum());
			Row row0 = sheet.getRow(0);
			for (int j = rowStart; j <= rowEnd; j++) {
				Row row = sheet.getRow(j);
				Boolean allBlank = true;
				for (int z = 0; allBlank && z < fieldsJsonByTag.size() + 10; z++) {
					if (!CharactorUtil.isEmpyty(getCellValueAsString(row.getCell(0)).trim())) {
						allBlank = false;
					}
				}
				if (allBlank) {
					continue;
				}
				// 公共字段
				String long_title = getCellValueAsString(row.getCell(0)).trim();
				String short_title = getCellValueAsString(row.getCell(1)).trim();
//				if (CharactorUtil.isEmpyty(short_title)) {
//					short_title = long_title;
//				}
				String lat = getCellValueAsString(row.getCell(2)).trim();
				String lng = getCellValueAsString(row.getCell(3)).trim();
				String mergerName = getCellValueAsString(row.getCell(4)).trim().replace("，", ",");
				String zone_id = ZONENAME_2_CODE_MAP.get(mergerName);
				String detail_address = getCellValueAsString(row.getCell(5)).trim();
				String brief_introduction = getCellValueAsString(row.getCell(6)).trim();
				String thumbnail = getCellValueAsString(row.getCell(7)).trim();
				// 8.全景数据ID
				String panorama = getCellValueAsString(row.getCell(8)).trim();
				// 9.联系电话
				String contact_phone = getCellValueAsString(row.getCell(9)).trim();

				Boolean isError = (zone_id == null ? Boolean.TRUE : Boolean.FALSE);
				isError = isError || CharactorUtil.isEmpyty(long_title);
				isError = isError || CharactorUtil.checkPoiDefaultStr(long_title, 64);
				isError = isError || CharactorUtil.checkPoiDefaultStr(short_title, 64);
				isError = isError || CharactorUtil.checkPoiLat(lat);
				isError = isError || CharactorUtil.checkPoiLng(lng);
				isError = isError || CharactorUtil.checkPoiDefaultStr(detail_address);
				isError = isError || CharactorUtil.checkPoiDefaultStr(brief_introduction, ManagePoiCtrl.介绍最大长度);
//				isError = isError || CharactorUtil.isEmpyty(thumbnail);

				// 8.全景数据ID
				isError = isError || CharactorUtil.hasChineseChar(panorama);
				isError = isError || CharactorUtil.checkPoiDefaultStr(panorama, 32);

				// 9.联系电话
				isError = isError || CharactorUtil.hasChineseChar(contact_phone);

				isError = isError || CharactorUtil.fileFieldIsError(thumbnail, unzipedDir);
				// 检查私有字段
				for (int z = 10; !isError && z < fieldsJsonByTag.size() + 10; z++) {
					String field_show_name = getCellValueAsString(row0.getCell(z)).trim();
					String value = getCellValueAsString(row.getCell(z)).trim();
					isError = isError || CharactorUtil.isPoiDataHasError(value, fieldsJsonByTag.get(field_show_name), Boolean.TRUE);
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

					checkErrorPoi.put("short_title", short_title);
					checkErrorPoi.put("zone_id", CharactorUtil.nullToBlank(zone_id));
					checkErrorPoi.put("detail_address", detail_address);
					checkErrorPoi.put("brief_introduction", brief_introduction);
					checkErrorPoi.put("thumbnail", thumbnail);

					// 8.全景数据ID 
					checkErrorPoi.put("panorama", panorama);
					// 9.联系电话
					checkErrorPoi.put("contact_phone", contact_phone);

					// 记录其他私有字段，页面编辑用
					for (int z = 10; z < fieldsJsonByTag.size() + 10; z++) {
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
							e.printStackTrace();
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

							checkErrorPoi.put("short_title", short_title);
							checkErrorPoi.put("zone_id", CharactorUtil.nullToBlank(zone_id));
							checkErrorPoi.put("detail_address", detail_address);
							checkErrorPoi.put("brief_introduction", brief_introduction);
							checkErrorPoi.put("thumbnail", thumbnail);

							// 8.全景数据ID
							checkErrorPoi.put("panorama", panorama);
							// 9.联系电话
							checkErrorPoi.put("contact_phone", contact_phone);

							// 记录其他私有字段，页面编辑用
							for (int z = 10; z < fieldsJsonByTag.size() + 10; z++) {
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

					// 8.全景数据ID
					noCheckErrorPoi.put("panorama", panorama);
					noCheckErrorPoi.put("contact_phone", contact_phone);

					try {
						// 记录其他私有字段，页面编辑用
						for (int z = 10; z < fieldsJsonByTag.size() + 10; z++) {
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

						checkErrorPoi.put("short_title", short_title);
						checkErrorPoi.put("zone_id", CharactorUtil.nullToBlank(zone_id));
						checkErrorPoi.put("detail_address", detail_address);
						checkErrorPoi.put("brief_introduction", brief_introduction);
						checkErrorPoi.put("thumbnail", thumbnail);

						// 8.全景数据ID
						checkErrorPoi.put("panorama", panorama);
						// 9.联系电话
						checkErrorPoi.put("contact_phone", contact_phone);
						// 记录其他私有字段，页面编辑用
						for (int z = 10; z < fieldsJsonByTag.size() + 10; z++) {
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
			return JsonUtil.error("-1", result.getString("msg"));
		}
		JSONObject dataOfAddedResult = addedResult.getJSONObject("data");
		saveErrors = dataOfAddedResult.getJSONArray("saveErrors");

		JSONObject returnResult = new JSONObject();
		returnResult.put("checkErrors", checkErrors);
		returnResult.put("saveErrors", saveErrors);
		return JsonUtil.sucess("OK", returnResult);
	}

	private Workbook generatePoiTemplete(JSONArray privateFieldsDef)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		//		Workbook wb = new XSSFWorkbook();
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("poi_templete.xlsx");
		Workbook wb = WorkbookFactory.create(is);
		is.close();

		Map<String, List<JSONObject>> fieldsGroupByTagName = new LinkedHashMap<String, List<JSONObject>>();
		for (int i = 0; i < privateFieldsDef.size(); i++) {
			JSONObject field = privateFieldsDef.getJSONObject(i);
			JSONObject field_def = field.getJSONObject("field_def");
			String tag_name = field_def.getString("tag_name");
			List<JSONObject> lstJSONObject = new ArrayList<JSONObject>();
			lstJSONObject.add(field_def);
			lstJSONObject = fieldsGroupByTagName.put(tag_name, lstJSONObject);
			if (lstJSONObject != null) {
				lstJSONObject.add(field_def);
				fieldsGroupByTagName.put(tag_name, lstJSONObject);
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

		//		titleStyle.setBorderBottom(CellStyle.BORDER_THIN);
		//		titleStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		//		titleStyle.setBorderLeft(CellStyle.BORDER_THIN);
		//		titleStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		//		titleStyle.setBorderRight(CellStyle.BORDER_THIN);
		//		titleStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		//		titleStyle.setBorderTop(CellStyle.BORDER_MEDIUM_DASHED);
		//		titleStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		//
		//		titleStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		//		titleStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);


		XSSFFont tipsFont = (XSSFFont)wb.createFont();
		tipsFont.setColor(IndexedColors.RED.index);
		tipsFont.setFontHeightInPoints((short)10);

		CellStyle tipsStyle = wb.createCellStyle(); // 样式对象    
		tipsStyle.setFont(tipsFont);
		tipsStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直

		//		tipsStyle.setBorderBottom(CellStyle.BORDER_THIN);
		//		tipsStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		//		tipsStyle.setBorderLeft(CellStyle.BORDER_THIN);
		//		tipsStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		//		tipsStyle.setBorderRight(CellStyle.BORDER_THIN);
		//		tipsStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		//		tipsStyle.setBorderTop(CellStyle.BORDER_THIN);
		//		tipsStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		//		tipsStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		//		tipsStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		tipsStyle.setWrapText(true);

		for (Entry<String, List<JSONObject>> entry : entrySet) {
			String tag_name = entry.getKey();
			List<JSONObject> privateFields = entry.getValue();
			this.createCommonField(wb, tag_name, titleStyle, tipsStyle);
			this.createPrivateField(wb, tag_name, privateFields, titleStyle, tipsStyle);
		}
		wb.setActiveSheet(1);
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

		// title1
		Row row = sheet.createRow(0);
		for (int j = 0; j < 10; j++) {
			Cell cell = row.createCell(j, Cell.CELL_TYPE_STRING);
			cell.setCellStyle(titleStyle);
		}
		// title2
		row = sheet.createRow(1);
		for (int j = 0; j < 10; j++) {

			Cell cell = row.createCell(j, Cell.CELL_TYPE_STRING);
			if (j > 3 && j < 6) {
				cell.setCellStyle(titleStyle);
			}
		}
		// tips
		row = sheet.createRow(2);
		for (int j = 0; j < 10; j++) {
			Cell cell = row.createCell(j, Cell.CELL_TYPE_STRING);
			cell.setCellStyle(tipsStyle);
		}
		// 合并单元格
		// POI名称
		sheet.addMergedRegion(new CellRangeAddress(1, 2, 0, 0));
		// POI别名
		sheet.addMergedRegion(new CellRangeAddress(1, 2, 1, 1));
		// 纬度
		sheet.addMergedRegion(new CellRangeAddress(1, 2, 2, 2));
		// 经度
		sheet.addMergedRegion(new CellRangeAddress(1, 2, 3, 3));
		// 地址
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 4, 5));
		// 简介
		sheet.addMergedRegion(new CellRangeAddress(1, 2, 6, 6));
		// 缩略图
		sheet.addMergedRegion(new CellRangeAddress(1, 2, 7, 7));
		// 8.全景数据ID
		sheet.addMergedRegion(new CellRangeAddress(1, 2, 8, 8));
		// 9.联系电话
		sheet.addMergedRegion(new CellRangeAddress(1, 2, 9, 9));

		// title1设值
		Row row0 = sheet.getRow(0);
		row0.getCell(0).setCellValue("名称");
		row0.getCell(1).setCellValue("别名");
		row0.getCell(2).setCellValue("纬度");
		row0.getCell(3).setCellValue("经度");
		row0.getCell(4).setCellValue("地址");
		row0.getCell(6).setCellValue("详细介绍");
		row0.getCell(7).setCellValue("缩略图");

		// 8.全景数据ID
		row0.getCell(8).setCellValue("全景数据ID");
		// 9.联系电话
		row0.getCell(9).setCellValue("联系电话");

		row0.getCell(0).setCellStyle(titleStyle);
		row0.getCell(1).setCellStyle(titleStyle);
		row0.getCell(2).setCellStyle(titleStyle);
		row0.getCell(3).setCellStyle(titleStyle);
		row0.getCell(4).setCellStyle(titleStyle);
		row0.getCell(6).setCellStyle(titleStyle);
		row0.getCell(7).setCellStyle(titleStyle);

		// 8.全景数据ID
		row0.getCell(8).setCellStyle(titleStyle);
		// 9.联系电话
		row0.getCell(9).setCellStyle(titleStyle);

		// title2设值
		Row row1 = sheet.getRow(1);
		row1.getCell(4).setCellValue("省,市,区/县");
		row1.getCell(5).setCellValue("详细地址");
		row1.getCell(4).setCellStyle(titleStyle);
		row1.getCell(5).setCellStyle(titleStyle);

		// tips设值
		row1.getCell(0).setCellValue("POI点全称，必填");
		row1.getCell(1).setCellValue("可为空（POI其它叫法）");
		row1.getCell(0).setCellStyle(tipsStyle);
		row1.getCell(1).setCellStyle(tipsStyle);

		// 8.全景数据ID
		// 9.联系电话
		row1.getCell(8).setCellValue("panoID或者页卡集标识");
		row1.getCell(9).setCellValue("可为空\r\n：格式：(国家区号)-省市区号-具体号码");

		row1.getCell(8).setCellStyle(tipsStyle);
		row1.getCell(9).setCellStyle(tipsStyle);

		Row row2 = sheet.getRow(2);
		row2.getCell(4).setCellValue("XX省,XX市,XX区/县（值取自地域表格中）");

		// 简介
		row1.getCell(6).setCellValue("POI点的详细介绍");
		// 缩略图
		row1.getCell(7).setCellValue("输入缩略图名称，不能包含中文");
		row1.getCell(6).setCellStyle(tipsStyle);
		row1.getCell(7).setCellStyle(tipsStyle);
		
		for (int i = 0; i < 10; i++) {
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
		case HSSFCell.CELL_TYPE_STRING:
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
		int field_type = fieldDef.getInt("field_type");
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
									COSUtil.LUNA_BUCKET, COSUtil.getCosPoiPicFolderPath() + "/" + date, fileName);
							if ("0".equals(uploadResult.getString("code"))) {
								JSONObject uploadedData = uploadResult.getJSONObject("data");
								value = uploadedData.getString("access_url");
							} else {
								uploadError = true;
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
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
						e.printStackTrace();
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
