package com.microscene.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ms.luna.biz.cons.ErrorCode;
import ms.luna.common.PoiCommon;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;

import ms.luna.biz.sc.PoiApiService;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;

@Controller
@RequestMapping("/servicepoi.do")
public class PoiApiController {

	private final static Logger logger = Logger.getLogger(PoiApiController.class);

	@Autowired
	private PoiApiService poiApiService;

	private static String[] RetrieveType = {"name", "id"}; // 检索类型

	private static String[] LANG = {PoiCommon.POI.ZH, PoiCommon.POI.EN}; // 语言


	/**
	 * 根据业务获取一个层级的poi数据列表
	 * @param biz_id 业务树id
	 * @param fields 字段（eg: "longNm,shortNm,title"）
	 * @param lang 语言
	 */
	@RequestMapping(params = "method=getPoisInFirstLevel")
	@ResponseBody
	public JSONObject getPoisInFirstLevel(
			@RequestParam(required = true, value = "business_id") Integer biz_id,
			@RequestParam(required = false, value = "fields") String fields,
			@RequestParam(required = false, value = "lang") String lang,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		try{
			if(fields == null){
				fields = "";
			} else {
				fields = fields.trim();
			}
			JSONObject param = new JSONObject();
			param.put("biz_id", biz_id);
			param.put("fields", fields);

			if(lang == null){
				JSONObject datas = new JSONObject();
				JSONObject result = new JSONObject();
				for(String language : LANG){
					param.put("lang", language);
					result = poiApiService.getPoisInFirstLevel(param.toString());
					MsLogger.debug("获取lang:"+language+"数据"+result.toString());
					if(!"0".equals(result.getString("code"))) {
						return result;
					}
					JSONObject data = result.getJSONObject("data");
					datas.put(language, data.getJSONObject(language));
				}
				result.put("data", datas);
				return result;
			} else {
				param.put("lang", lang);
				JSONObject result = poiApiService.getPoisInFirstLevel(param.toString());
				MsLogger.debug("获取lang:" + lang + "数据" + result.toString());
				return result;
			}
		} catch (Exception e){
			return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "服务器内部错误");
		}

	}

	/**
	 * 根据业务和POI获取下一层的一级类别列表
	 * @param biz_id 业务id
	 * @param poi_id poi ID数据
	 * @throws IOException
	 */
	@RequestMapping(params = "method=getCtgrsByBizIdAndPoiId")
	@ResponseBody
	public JSONObject getCtgrsByBizIdAndPoiId(
			@RequestParam(required = true, value = "business_id") Integer biz_id,
			@RequestParam(required = true, value = "poi_id") String poi_id,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		try{
			JSONObject param = new JSONObject();
			param.put("biz_id", biz_id);
			param.put("poi_id", poi_id);

			JSONObject result = poiApiService.getCtgrsByBizIdAndPoiId(param.toString());
			MsLogger.debug(result.toString());
			return result;
		} catch (Exception e ){
			return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "服务器内部错误");
		}
	}


	/**
	 * 根据业务和POI获取下一层的二级类别列表
	 * @param biz_id 业务id
	 * @param poi_id poi i
	 * @param ctgr_id 一级分类id
	 * @throws IOException
	 */
	@RequestMapping(params = "method=getSubCtgrsByBizIdAndPoiIdAndCtgrId")
	@ResponseBody
	public JSONObject getSubCtgrsByBizIdAndPoiIdAndCtgrId(
			@RequestParam(required = true, value = "business_id") Integer biz_id,
			@RequestParam(required = true, value = "poi_id") String poi_id,
			@RequestParam(required = true, value = "category_id") Integer ctgr_id,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		try{
			JSONObject param = new JSONObject();
			param.put("biz_id", biz_id);
			param.put("poi_id", poi_id);
			param.put("ctgr_id", ctgr_id);

			JSONObject result = poiApiService.getSubCtgrsByBizIdAndPoiId(param.toString());
			MsLogger.debug(result.toString());

			return result;

		} catch (Exception e ){
			logger.error("Failed to getSubCtgrsByBizIdAndPoiIdAndCtgrId", e);
			return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "服务器内部错误");
		}
	}

	/**
	 * 根据业务和poi获取下一层的POI列表
	 *
	 * @param biz_id 业务id
	 * @param poi_id poi id
	 * @param fields 返回字段
	 * @param lang language
	 * @throws IOException
	 */
	@RequestMapping(params = "method=getPoisByBizIdAndPoiId")
	@ResponseBody
	public JSONObject getPoisByBizIdAndPoiId(
			@RequestParam(required = true, value = "business_id") Integer biz_id,
			@RequestParam(required = true, value = "poi_id") String poi_id,
			@RequestParam(required = false, value= "fields") String fields,
			@RequestParam(required = false, value = "lang") String lang,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		try{
			if(fields == null){
				fields = "";
			} else {
				fields = fields.trim();
			}
			JSONObject param = new JSONObject();
			param.put("biz_id", biz_id);
			param.put("poi_id", poi_id);
			param.put("fields", fields);

			if(lang == null){
				JSONObject datas = new JSONObject();
				JSONObject result = new JSONObject();
				for(String language : LANG){
					param.put("lang", language);
					result = poiApiService.getPoisByBizIdAndPoiId(param.toString());
					MsLogger.debug("获取lang:"+language+"数据"+result.toString());
					if(!"0".equals(result.getString("code"))) {
						return result;
					}
					JSONObject data = result.getJSONObject("data");
					datas.put(language, data.getJSONObject(language));
				}
				result.put("data", datas);
				return result;
			} else {
				param.put("lang", lang);
				JSONObject result = poiApiService.getPoisByBizIdAndPoiId(param.toString());
				MsLogger.debug("获取lang:" + lang + "数据" + result.toString());
				return result;
			}
		} catch (Exception e){
			logger.error("Failed to getPoisByBizIdAndPoiId", e);
			return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "服务器内部错误");
		}
	}

	/**
	 * 根据业务，POI和一级类别获取下一层POI数据列表
	 * @param biz_id 业务id
	 * @param poi_id poi id
	 * @param ctgr_id 一级类别id
	 * @param fields 字段（eg: "longNm,shortNm,title"）
	 * @param lang 语言
	 * @throws IOException
	 */
	@RequestMapping(params = "method=getPoisByBizIdAndPoiIdAndCtgrId")
	@ResponseBody
	public JSONObject getPoisByBizIdAndPoiIdAndCtgrId(
			@RequestParam(required = true, value = "business_id") Integer biz_id,
			@RequestParam(required = true, value = "poi_id") String poi_id,
			@RequestParam(required = true, value = "category_id") String ctgr_id,
			@RequestParam(required = false, value= "fields") String fields,
			@RequestParam(required = false, value = "lang") String lang,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		try{
			// category检查
			boolean flag = checkTags(ctgr_id);//允许一级分类以字符串的形式输入多个。 和标签id字符串同样的检查方式
			if(!flag){
				return FastJsonUtil.error("-1", "一级分类:"+ctgr_id+"格式错误");
			}

			if(fields == null){
				fields = "";
			} else {
				fields = fields.trim();
			}
			JSONObject param = new JSONObject();
			param.put("biz_id", biz_id);
			param.put("poi_id", poi_id);
			param.put("ctgr_id", ctgr_id);
			param.put("fields", fields);

			if(lang == null){
				JSONObject datas = new JSONObject();
				JSONObject result = new JSONObject();
				for(String language : LANG){
					param.put("lang", language);
					result = poiApiService.getPoisByBizIdAndPoiIdAndCtgrId(param.toString());
					MsLogger.debug("获取lang:"+language+"数据"+result.toString());
					if(!"0".equals(result.getString("code"))) {
						return result;
					}
					JSONObject data = result.getJSONObject("data");
					datas.put(language, data.getJSONObject(language));
				}
				result.put("data", datas);
				return result;
			} else {
				param.put("lang", lang);
				JSONObject result = poiApiService.getPoisByBizIdAndPoiIdAndCtgrId(param.toString());
				MsLogger.debug("获取lang:" + lang + "数据" + result.toString());
				return result;
			}
		} catch (Exception e){
			return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "服务器内部错误");
		}
	}

	/**
	 * 根据业务，POI和二级类别获取下一层POI数据列表
	 * @param biz_id 业务id
	 * @param poi_id poi id
	 * @param sub_ctgr_id 一级类别id
	 * @param fields 字段（eg: "longNm,shortNm,title"）
	 * @param lang 语言
	 * @throws IOException
	 */
	@RequestMapping(params = "method=getPoisByBizIdAndPoiIdAndSubCtgrId")
	@ResponseBody
	public JSONObject getPoisByBizIdAndPoiIdAndSubCtgrId(
			@RequestParam(required = true, value = "business_id") Integer biz_id,
			@RequestParam(required = true, value = "poi_id") String poi_id,
			@RequestParam(required = true, value = "sub_category_id") String sub_ctgr_id,
			@RequestParam(required = false, value= "fields") String fields,
			@RequestParam(required = false, value = "lang") String lang,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		try{
			// category检查
			boolean flag = checkTags(sub_ctgr_id);//允许一级分类以字符串的形式输入多个。 和标签id字符串同样的检查方式
			if(!flag){
				return FastJsonUtil.error("-1", "二级分类:"+sub_ctgr_id+"格式错误");
			}

			if(fields == null){
				fields = "";
			} else {
				fields = fields.trim();
			}
			JSONObject param = new JSONObject();
			param.put("biz_id", biz_id);
			param.put("poi_id", poi_id);
			param.put("sub_ctgr_id", sub_ctgr_id);
			param.put("fields", fields);

			if(lang == null){
				JSONObject datas = new JSONObject();
				JSONObject result = new JSONObject();
				for(String language : LANG){
					param.put("lang", language);
					result = poiApiService.getPoisByBizIdAndPoiIdAndSubCtgrId(param.toString());
					MsLogger.debug("获取lang:" + language + "数据" + result.toString());
					if(!"0".equals(result.getString("code"))) {
						return result;
					}
					JSONObject data = result.getJSONObject("data");
					datas.put(language, data.getJSONObject(language));
				}
				result.put("data", datas);
				return result;
			} else {
				param.put("lang", lang);
				JSONObject result = poiApiService.getPoisByBizIdAndPoiIdAndSubCtgrId(param.toString());
				MsLogger.debug("获取lang:" + lang + "数据" + result.toString());
				return result;
			}
		} catch (Exception e){
			return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "服务器内部错误");
		}
	}

	/**
	 * 获取具体POI数据信息
	 * @param poi_id poi id
	 * @param lang 语言
	 * @throws IOException
	 */
	@RequestMapping(params = "method=getPoiById")
	@ResponseBody
	public JSONObject getPoiById(
			@RequestParam(required = true, value = "poi_id") String poi_id,
			@RequestParam(required = false, value = "lang") String lang,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		try{
			JSONObject param = new JSONObject();
			param.put("poi_id", poi_id);

			if(lang == null){
				JSONObject datas = new JSONObject();
				JSONObject result = new JSONObject();
				for(String language : LANG){
					param.put("lang", language);
					result = poiApiService.getPoiInfoById(param.toString());
					MsLogger.debug("获取lang:"+language+"数据"+result.toString());
					if(!"0".equals(result.getString("code"))) {
						return result;
					}
					JSONObject data = result.getJSONObject("data");
					datas.put(language, data.getJSONObject(language));
				}
				result.put("data", datas);
				return result;
			} else {
				param.put("lang", lang);
				JSONObject result = poiApiService.getPoiInfoById(param.toString());
				MsLogger.debug("获取lang:" + lang + "数据" + result.toString());
				return result;
			}
		} catch (Exception e) {
			return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "服务器内部错误");
		}
	}

	/**
	 * 获取某个业务某个/几个标签下所有poi数据
	 * @param biz_id 业务id
	 * @param tags 标签（eg:"1,2,3"）
	 * @param lang 语言
	 * @throws IOException
	 */
	@RequestMapping(params = "method=getPoisByBizIdAndTags")
	@ResponseBody
	public JSONObject getPoisByBizIdAndTags(
			@RequestParam(required = true, value = "business_id") Integer biz_id,
			@RequestParam(required = true, value = "tags") String tags,
			@RequestParam(required = false, value = "type") String type,
			@RequestParam(required = false, value = "fields") String fields,
			@RequestParam(required = false, value = "lang") String lang,
			HttpServletRequest request, HttpServletResponse response) throws IOException{
		MsLogger.debug("before decoding:" + tags);
		//tags = string2ChineseChar(tags);
		tags = URLDecoder.decode(tags,"utf-8");
		MsLogger.debug("after decoding utf-8:" + tags);
		try{
			JSONObject param = new JSONObject();
			if(fields == null){
				fields = "";
			} else {
				fields = fields.trim();
			}

			// 如果传入了type参数，则认为传入的是id。否则认为传入的是tagNm
			tags = tags.trim();
			if(type != null){
				boolean flag = checkTags(tags);// 标签格式检测
				if(!flag){
					return FastJsonUtil.error("-1", "标签:"+tags+"格式错误");
				}
			} else {
				if("".equals(tags)){ // 标签格式检测
					return FastJsonUtil.error("-1", "标签:"+tags+"格式错误");
				}
				type = "";
			}
			param.put("biz_id", biz_id);
			param.put("fields", fields);
			param.put("tags", tags);
			param.put("type", type);

			if(lang == null){
				JSONObject datas = new JSONObject();
				JSONObject result = new JSONObject();
				for(String language : LANG){
					param.put("lang", language);
					result = poiApiService.getPoisByBizIdAndTags(param.toString());
					MsLogger.debug("获取lang:"+language+"数据"+result.toString());
					if(!"0".equals(result.getString("code"))) {
						return result;
					}
					JSONObject data = result.getJSONObject("data");
					datas.put(language, data.getJSONObject(language));
				}
				result.put("data", datas);
				return result;
			} else {
				param.put("lang", lang);
				JSONObject result = poiApiService.getPoisByBizIdAndTags(param.toString());
				MsLogger.debug("获取lang:" + lang + "数据" + result.toString());
				return result;
			}
		} catch (Exception e){
			return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "服务器内部错误");
		}

	}

	/**
	 * POI检索(目前可根据名称或者id进行检索).返回结果中包含poi的名称和id
	 *
	 * @param filterName 过滤名称
	 * @param limit 返回数量上限
	 * @param lang 语言
	 * @param type 搜索类型,如名称,id等
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(params = "method=retrievePois")
	@ResponseBody
	public JSONObject retrievePois(
			@RequestParam(required = false, value = "filterName", defaultValue = "") String filterName,
			@RequestParam(required = false, value = "limit", defaultValue = "10") Integer limit,
			@RequestParam(required = false, value = "lang") String lang,
			@RequestParam(required = false, value = "type") String type,
			HttpServletRequest request, HttpServletResponse response) throws IOException{
		try {
			if(type == null || !checkParamExist(type, RetrieveType)) {
				type = "ALL";
			}
			if(lang == null || !checkParamExist(lang, LANG)) {
				lang = "ALL";
			}
			JSONObject param = new JSONObject();
			param.put("filterName", filterName);
			param.put("limit", limit);
			param.put("lang", lang);
			param.put("type", type);
			JSONObject result = poiApiService.retrievePois(param.toString());
			MsLogger.debug(result.toString());
			return result;
		} catch (Exception e) {
			MsLogger.error("Failed to retrieve pois. " + e.getMessage());
			return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to retrieve pois.");
		}
	}
	/*
	 * 获取周边poi数据
	 *
	 * @param longitude 经度
	 * @param latitude 纬度
	 * @param radius 半径范围
	 * @param number 返回列表最大长度
	 * @param fields 返回字段
	 * @param lang language
	 * @throws IOException
	 */
	@RequestMapping(params = "method=getPoisAround")
	@ResponseBody
	public JSONObject getPoisAroundById(
			@RequestParam(required = true, value = "longitude") Double longitude,
			@RequestParam(required = true, value = "latitude") Double latitude,
			@RequestParam(required = false, value = "radius") Double radius,
			@RequestParam(required = false, value = "number") Integer number,
			@RequestParam(required = false, value = "fields") String fields,
			@RequestParam(required = false, value = "lang") String lang,
			HttpServletRequest request, HttpServletResponse response) throws IOException{
		try{
			JSONObject param = new JSONObject();
			if(radius == null) {
				radius = (double) PoiCommon.POI.RADIUS_AROUND_DEFAULT;
			}
			if(number == null) {
				number = PoiCommon.POI.NUM_AROUND_DEFAULT;
			}
			if(fields == null){
				fields = "";
			} else {
				fields = fields.trim();
			}
			param.put("lng", longitude);
			param.put("lat", latitude);
			param.put("radius", radius);
			param.put("number", number);
			param.put("fields", fields);

			// 指定语言
			if(lang != null) {
				param.put("lang", lang);
				JSONObject result = poiApiService.getPoisAround(param.toString());
				MsLogger.debug("获取lang:" + lang + "数据" + result.toString());
				return result;
			}

			// 未指定语言
			JSONObject datas = new JSONObject();
			JSONObject result = new JSONObject();
			for(String language : LANG){
				param.put("lang", language);
				result = poiApiService.getPoisAround(param.toString());
				MsLogger.debug("获取lang:"+language+"数据"+result.toString());
				if(!"0".equals(result.getString("code"))) {
					return result;
				}
				JSONObject data = result.getJSONObject("data");
				datas.put(language, data.getJSONObject(language));
			}
			result.put("data", datas);
			return result;

		} catch (Exception e) {
			MsLogger.error("Fail to get pois around." + e.getMessage());
			return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Fail to get pois around");
		}

	}


	/**
	 * 根据活动id获取POI列表信息
	 *
	 * @param activity_id 活动id
	 * @param fields 字段
	 * @param lang language
	 * @throws IOException
	 */
	@RequestMapping(params = "method=getPoisByActivityId")
	@ResponseBody
	public JSONObject getPoisByActivityId(
			@RequestParam(required = true, value = "activity_id") String activity_id,
			@RequestParam(required = false, value = "fields") String fields,
			@RequestParam(required = false, value = "lang") String lang,
			HttpServletRequest request, HttpServletResponse response) throws IOException{
		/*
		  需求: 只要POI的中文版或英文版中含有活动id,则返回该poi数据
		  如果POI只有中文版或者只有英文版含有该id, 例如, poi 英文版含有该id,而中文版含有另一个id, lang=zh时也需要将该poi中文版数据输出.反之亦然
		 */
		try{
			if(!checkActivityId(activity_id)) {
				return FastJsonUtil.error(ErrorCode.INVALID_PARAM, activity_id);
			}
			activity_id = activity_id.trim();
			fields = getValidFields(fields);
			if(lang == null) {
				lang = "ALL";
			}
			JSONObject param = new JSONObject();
			param.put("activity_id", activity_id);
			param.put("fields", fields);
			param.put("lang", lang);

			JSONObject result = poiApiService.getPoisByActivityId(param.toString());
			MsLogger.debug(result.toString());
			return result;
		} catch (Exception e) {
			MsLogger.debug("Fail to get pois by activity id." + e.getMessage());
			return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Fail to get pois by activity id");

		}

	}

	/**
	 * 检查参数是否符合要求
	 *
	 * @param type 检索类型
	 */
	private boolean checkParamExist(String type, String[] container) {
		for(String tp : container) {
			if (tp.equals(type)) {
				return true;
			}
		}
		return false;
	}

	/*
	 * 获取符合要求的字段
	 *
	 * @param fields 字段
	 * @return String
	 */
	private String getValidFields(String fields) {
		return (fields == null) ? "":fields.trim();
	}

	/**
	 * 判断活动id合法性
	 *
	 * @param activity_id 活动id
	 * @return boolean
	 */
	private boolean checkActivityId(String activity_id) {
		activity_id = activity_id.trim();
		return activity_id.length() != 0;
	}

	/**
	 * 检测标签是否传入正确
	 * @param tags 标签
	 * @return boolean
	 */
	static public boolean checkTags(String tags){
		Pattern pattern = Pattern.compile("((\\d+),)*(\\d+)");
		Matcher matcher = pattern.matcher(tags);
		return matcher.matches();
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		String s = "%E7%89%B9%E8%89%B2";
		s = string2ChineseChar(s);
		System.out.println(s);
		//System.out.println(URLDecoder.decode("%E7%89%B9%E8%89%B2","UTF-8"));
//		String[] tags = {
//				"124232",
//				"1,2,3",
//				"12,232,12",
//				" 12",
//				"1 2",
//				" 1 2",
//				"2 , 2",
//				"1,2213,3 ",
//				"1,22 13,3 "
//		};
//		for(String tag : tags)
//			System.out.println(checkTags(tag));
////		String tags = "特色,农家";//%E7%89%B9%E8%89%B2%2C%E5%86%9C%E5%AE%B6
//		System.out.println(URLEncoder.encode("特色", "utf-8"));
//		System.out.println( tags );
//		tags = URLDecoder.decode(tags, "utf-8");
//		System.out.println(tags);
//		String[] a = tags.split(",");
//		for(String aa : a){
//			System.out.println(aa);
//		}
		//String s = "特色,其他";
		//System.out.print(string2ChineseChar(s));
	}

	public static String string2ChineseChar(String string) throws UnsupportedEncodingException {

		StringBuilder unicode = new StringBuilder();
		for (int i = 0; i < string.length(); i++) {
			// 取出每一个字符
			char c = string.charAt(i);
			// 转换为unicode(用于解码)
			unicode.append("%").append(Integer.toHexString(c));
		}
		// 16进制数据
		String result = unicode.toString();
		// 转汉字
		return URLDecoder.decode(
				URLDecoder.decode(result, "UTF-8"), // Unicode
				"UTF-8");
	}
}

