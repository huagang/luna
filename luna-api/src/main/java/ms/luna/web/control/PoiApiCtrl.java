package ms.luna.web.control;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;

import ms.luna.biz.sc.PoiApiService;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;

@Component
@Controller
@RequestMapping("/servicepoi.do")
public class PoiApiCtrl {

	@Autowired
	private PoiApiService poiApiService;

	private static String[] LANG = {"zh", "en"}; // 语言
	
	/**
	 * 根据业务获取一个层级的poi数据列表
	 * @param biz_id 业务树id
	 * @param level 层级
	 * @param fields 字段（eg: "longNm,shortNm,title"）
	 * @param lang 语言
	 */
	@RequestMapping(params = "method=getPoisInFirstLevel")
	public void getPoisInFirstLevel(
			@RequestParam(required = true, value = "business_id") Integer biz_id, 
			@RequestParam(required = false, value = "fields") String fields,
			@RequestParam(required = false, value = "lang") String lang,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
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
				JSONObject result = null;
				for(String language : LANG){
					param.put("lang", language);
					result = poiApiService.getPoisInFirstLevel(param.toString());
					MsLogger.debug("获取lang:"+language+"数据"+result.toString());
					if("0".equals(result.getString("code"))){
						JSONObject data = result.getJSONObject("data");
						datas.put(language, data.getJSONObject(language));
					} else {
						response.getWriter().print(result);
						response.setStatus(200);
						return;
					}
				}
				response.getWriter().print(FastJsonUtil.sucess(result.getString("msg"),datas));
				response.setStatus(200);
				return;
			} else {
				param.put("lang", lang);
				JSONObject result = poiApiService.getPoisInFirstLevel(param.toString());
				MsLogger.debug("获取lang:"+lang+"数据"+result.toString());
				response.getWriter().print(result);
				response.setStatus(200);
				return;
			}
		} catch (Exception e){
			response.getWriter().print(FastJsonUtil.error("-1", e));
			response.setStatus(200);
			return;
		}
		
	}

	/**
	 * 根据业务和POI获取下一层的一级类别列表
	 * @param biz_id 业务id
	 * @param poi_id poi ID数据
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params = "method=getCtgrsByBizIdAndPoiId")
	public void getCtgrsByBizIdAndPoiId(
			@RequestParam(required = true, value = "business_id") Integer biz_id,
			@RequestParam(required = true, value = "poi_id") String poi_id, 
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		try{
			JSONObject param = new JSONObject();
			param.put("biz_id", biz_id);
			param.put("poi_id", poi_id);
			
			JSONObject result = poiApiService.getCtgrsByBizIdAndPoiId(param.toString());
			MsLogger.debug(result.toString());
			response.getWriter().print(result);
			response.setStatus(200);
			return;
		} catch (Exception e ){
			response.getWriter().print(FastJsonUtil.error("-1", e));
			response.setStatus(200);
			return;
		}
	}

	
	/**
	 * 根据业务和POI获取下一层的二级类别列表
	 * @param biz_id
	 * @param poi_id
	 * @param ctgr_id
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params = "method=getSubCtgrsByBizIdAndPoiIdAndCtgrId")
	public void getSubCtgrsByBizIdAndPoiIdAndCtgrId(
			@RequestParam(required = true, value = "business_id") Integer biz_id,
			@RequestParam(required = true, value = "poi_id") String poi_id, 
			@RequestParam(required = true, value = "ctgr_id") Integer ctgr_id, 
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		
		try{
			JSONObject param = new JSONObject();
			param.put("biz_id", biz_id);
			param.put("poi_id", poi_id);
			param.put("ctgr_id", ctgr_id);
			
			JSONObject result = poiApiService.getSubCtgrsByBizIdAndPoiId(param.toString());
			MsLogger.debug(result.toString());
			response.getWriter().print(result);
			response.setStatus(200);
			return;
			
		} catch (Exception e ){
			response.getWriter().print(FastJsonUtil.error("-1", e));
			response.setStatus(200);
			return;
		}
	}
	
	/**
	 * 根据业务和poi获取下一层的POI列表
	 * 
	 * @param biz_id
	 * @param poi_id
	 * @param fields
	 * @param lang
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params = "method=getPoisByBizIdAndPoiId")
	public void getPoisByBizIdAndPoiId(
			@RequestParam(required = true, value = "business_id") Integer biz_id,
			@RequestParam(required = true, value = "poi_id") String poi_id, 
			@RequestParam(required = false, value= "fields") String fields,
			@RequestParam(required = false, value = "lang") String lang,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
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
				JSONObject result = null;
				for(String language : LANG){
					param.put("lang", language);
					result = poiApiService.getPoisByBizIdAndPoiId(param.toString());
					MsLogger.debug("获取lang:"+language+"数据"+result.toString());
					if("0".equals(result.getString("code"))){
						JSONObject data = result.getJSONObject("data");
						datas.put(language, data.getJSONObject(language));
					} else {
						response.getWriter().print(result);
						response.setStatus(200);
						return;
					}
				}
				response.getWriter().print(FastJsonUtil.sucess(result.getString("msg"),datas));
				response.setStatus(200);
				return;
			} else {
				param.put("lang", lang);
				JSONObject result = poiApiService.getPoisByBizIdAndPoiId(param.toString());
				MsLogger.debug("获取lang:"+lang+"数据"+result.toString());
				response.getWriter().print(result);
				response.setStatus(200);
				return;
			}
		} catch (Exception e){
			response.getWriter().print(FastJsonUtil.error("-1", e));
			response.setStatus(200);
			return;
		}
	}
	
	/**
	 * 根据业务，POI和一级类别获取下一层POI数据列表
	 * @param biz_id 业务id
	 * @param poi_id poi id
	 * @param ctgr_id 一级类别id
	 * @param fields 字段（eg: "longNm,shortNm,title"）
	 * @param lang 语言
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params = "method=getPoisByBizIdAndPoiIdAndCtgrId")
	public void getPoisByBizIdAndPoiIdAndCtgrId(
			@RequestParam(required = true, value = "business_id") Integer biz_id,
			@RequestParam(required = true, value = "poi_id") String poi_id, 
			@RequestParam(required = true, value = "ctgr_id") int ctgr_id,
			@RequestParam(required = false, value= "fields") String fields,
			@RequestParam(required = false, value = "lang") String lang,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		try{
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
				JSONObject result = null;
				for(String language : LANG){
					param.put("lang", language);
					result = poiApiService.getPoisByBizIdAndPoiIdAndCtgrId(param.toString());
					MsLogger.debug("获取lang:"+language+"数据"+result.toString());
					if("0".equals(result.getString("code"))){
						JSONObject data = result.getJSONObject("data");
						datas.put(language, data.getJSONObject(language));
					} else {
						response.getWriter().print(result);
						response.setStatus(200);
						return;
					}
				}
				response.getWriter().print(FastJsonUtil.sucess(result.getString("msg"),datas));
				response.setStatus(200);
				return;
			} else {
				param.put("lang", lang);
				JSONObject result = poiApiService.getPoisByBizIdAndPoiIdAndCtgrId(param.toString());
				MsLogger.debug("获取lang:"+lang+"数据"+result.toString());
				response.getWriter().print(result);
				response.setStatus(200);
				return;
			}
		} catch (Exception e){
			response.getWriter().print(FastJsonUtil.error("-1", e));
			response.setStatus(200);
			return;
		}
	}
	
	/**
	 * 根据业务，POI和二级类别获取下一层POI数据列表
	 * @param biz_id 业务id
	 * @param poi_id poi id
	 * @param sub_ctgr_id 一级类别id
	 * @param fields 字段（eg: "longNm,shortNm,title"）
	 * @param lang 语言
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params = "method=getPoisByBizIdAndPoiIdAndSubCtgrId")
	public void getPoisByBizIdAndPoiIdAndSubCtgrId(
			@RequestParam(required = true, value = "business_id") Integer biz_id,
			@RequestParam(required = true, value = "poi_id") String poi_id, 
			@RequestParam(required = true, value = "sub_ctgr_id") int sub_ctgr_id,
			@RequestParam(required = false, value= "fields") String fields,
			@RequestParam(required = false, value = "lang") String lang,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		try{
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
				JSONObject result = null;
				for(String language : LANG){
					param.put("lang", language);
					result = poiApiService.getPoisByBizIdAndPoiIdAndSubCtgrId(param.toString());
					MsLogger.debug("获取lang:"+language+"数据"+result.toString());
					if("0".equals(result.getString("code"))){
						JSONObject data = result.getJSONObject("data");
						datas.put(language, data.getJSONObject(language));
					} else {
						response.getWriter().print(result);
						response.setStatus(200);
						return;
					}
				}
				response.getWriter().print(FastJsonUtil.sucess(result.getString("msg"),datas));
				response.setStatus(200);
				return;
			} else {
				param.put("lang", lang);
				JSONObject result = poiApiService.getPoisByBizIdAndPoiIdAndSubCtgrId(param.toString());
				MsLogger.debug("获取lang:"+lang+"数据"+result.toString());
				response.getWriter().print(result);
				response.setStatus(200);
				return;
			}
		} catch (Exception e){
			response.getWriter().print(FastJsonUtil.error("-1", e));
			response.setStatus(200);
			return;
		}
	}
	
	/**
	 * 获取具体POI数据信息
	 * @param poi_id poi id
	 * @param lang 语言
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params = "method=getPoiById")
	public void getPoiById(
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
				JSONObject result = null;
				for(String language : LANG){
					param.put("lang", language);
					result = poiApiService.getPoiInfoById(param.toString());
					MsLogger.debug("获取lang:"+language+"数据"+result.toString());
					if("0".equals(result.getString("code"))){
						JSONObject data = result.getJSONObject("data");
						datas.put(language, data.getJSONObject(language));
					} else {
						response.getWriter().print(result);
						response.setStatus(200);
						return;
					}
				}
				response.getWriter().print(FastJsonUtil.sucess(result.getString("msg"),datas));
				response.setStatus(200);
				return;
			} else {
				param.put("lang", lang);
				JSONObject result = poiApiService.getPoiInfoById(param.toString());
				MsLogger.debug("获取lang:"+lang+"数据"+result.toString());
				response.getWriter().print(result);
				response.setStatus(200);
				return;
			}
		} catch (Exception e) {
			response.getWriter().print(FastJsonUtil.error("-1", e));
			response.setStatus(200);
			return;
		}
	}
	
	/**
	 * 获取某个业务某个/几个标签下所有poi数据
	 * @param biz_id 业务id
	 * @param tags 标签（eg:"1,2,3"）
	 * @param lang 语言
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params = "method=getPoisByBizIdAndTags")
	public void getPoisByBizIdAndTags(
			@RequestParam(required = true, value = "business_id") Integer biz_id,
			@RequestParam(required = true, value = "tags") String tags,
			@RequestParam(required = false, value = "type") String type,
			@RequestParam(required = false, value = "fields") String fields,
			@RequestParam(required = false, value = "lang") String lang,
			HttpServletRequest request, HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		tags = string2ChineseChar(tags);
		MsLogger.debug(tags);
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
					response.getWriter().print(FastJsonUtil.error("-1", "标签:"+tags+"格式错误"));
					response.setStatus(200);
					return;
				} 
			} else {
				if("".equals(tags)){ // 标签格式检测
					response.getWriter().print(FastJsonUtil.error("-1", "标签:"+tags+"格式错误"));
					response.setStatus(200);
					return;
				}
				type = "";
				
			}
			param.put("biz_id", biz_id);
			param.put("fields", fields);
			param.put("tags", tags);
			param.put("type", type);
			
			if(lang == null){
				JSONObject datas = new JSONObject();
				JSONObject result = null;
				for(String language : LANG){
					param.put("lang", language);
					result = poiApiService.getPoisByBizIdAndTags(param.toString());
					MsLogger.debug("获取lang:"+language+"数据"+result.toString());
					if("0".equals(result.getString("code"))){
						JSONObject data = result.getJSONObject("data");
						datas.put(language, data.getJSONObject(language));
					} else {
						response.getWriter().print(result);
						response.setStatus(200);
						return;
					}
				}
				response.getWriter().print(FastJsonUtil.sucess(result.getString("msg"),datas));
				response.setStatus(200);
				return;
			} else {
				param.put("lang", lang);
				JSONObject result = poiApiService.getPoisByBizIdAndTags(param.toString());
				MsLogger.debug("获取lang:"+lang+"数据"+result.toString());
				response.getWriter().print(result);
				response.setStatus(200);
				return;
			}
		} catch (Exception e){
			response.getWriter().print(FastJsonUtil.error("-1", e));
			response.setStatus(200);
			return;
		}

	}
	
	/**
	 * 检测标签是否传入正确
	 * @param tags 标签
	 * @return
	 */
	static public boolean checkTags(String tags){
		Pattern pattern = Pattern.compile("((\\d+),)*(\\d+)");
		Matcher matcher = pattern.matcher(tags);
		if(matcher.matches()){
			return true;
		}
		return false;
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
	}
	
    public static String string2ChineseChar(String string) throws UnsupportedEncodingException {
          
        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < string.length(); i++) {
            // 取出每一个字符
            char c = string.charAt(i);
            // 转换为unicode(用于解码)
            unicode.append("%" + Integer.toHexString(c));
        }
        // 16进制数据
        String result = unicode.toString();
        // 转汉字
        return URLDecoder.decode(
        		URLDecoder.decode(result, "UTF-8"), // Unicode
        		"UTF-8");
    }
}
