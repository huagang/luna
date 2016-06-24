package ms.luna.web.control;

import java.io.IOException;
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
	 */
	@RequestMapping(params = "method=getPoisInFirstLevel")
	public void getPoisInFirstLevel(
			@RequestParam(required = true, value = "biz_id") Integer biz_id, 
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
			@RequestParam(required = true, value = "biz_id") Integer biz_id,
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
			@RequestParam(required = true, value = "biz_id") Integer biz_id,
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
	 * 根据业务，POI和一级类别获取下一层POI数据列表
	 * @param biz_id 业务id
	 * @param poi_id poi id
	 * @param ctgr_id 一级类别id
	 * @param fields 字段（eg: "longNm,shortNm,title"）
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params = "method=getPoisByBizIdAndPoiIdAndCtgrId")
	public void getPoisByBizIdAndPoiIdAndCtgrId(
			@RequestParam(required = true, value = "biz_id") Integer biz_id,
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
	 * 获取具体POI数据信息
	 * @param poi_id poi id
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
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params = "method=getPoisByBizIdAndTags")
	public void getPoisByBizIdAndTags(
			@RequestParam(required = true, value = "biz_id") Integer biz_id,
			@RequestParam(required = true, value = "type") String type,
			@RequestParam(required = true, value = "tags") String tags,
			@RequestParam(required = false, value = "fields") String fields,
			@RequestParam(required = false, value = "lang") String lang,
			HttpServletRequest request, HttpServletResponse response) throws IOException{

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		try{
			tags = tags.trim();
			boolean flag = checkTags(tags);
			if(!flag){
				response.getWriter().print(FastJsonUtil.error("-1", "标签:"+tags+"格式错误"));
				response.setStatus(200);
				return;
			}
			if(fields == null){
				fields = "";
			} else {
				fields = fields.trim();
			}
			JSONObject param = new JSONObject();
			param.put("biz_id", biz_id);
			param.put("tags", tags);
			param.put("fields", fields);
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
	
//	public static void main(String[] args) {
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
//	}
}
