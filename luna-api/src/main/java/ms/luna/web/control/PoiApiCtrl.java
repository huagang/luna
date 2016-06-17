package ms.luna.web.control;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.zookeeper.Op.Check;
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

	/**
	 * 根据业务获取一个层级的poi数据列表
	 * @param business_id 业务树id
	 * @param level 层级
	 * @param fields 字段（eg: "longNm,shortNm,title"）
	 */
	@RequestMapping(params = "method=getPoisWithLevel")
	public void getPoisWithLevel(
			@RequestParam(required = true, value = "business_id") Integer business_id, 
			@RequestParam(required = false, value = "level") Integer level,
			@RequestParam(required = false, value = "fields") String fields,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		try{
			if(level == null) {
				level = 1;
			}
			if(level < 1){
				response.getWriter().print(FastJsonUtil.error("-1", "level错误，不能小于1"));
				response.setStatus(200);
				return;
			}
			if(fields == null){
				fields = "";
			} else {
				fields = fields.trim();
			}
			JSONObject param = JSONObject.parseObject("{}");
			param.put("business_id", business_id);
			param.put("level", level);
			param.put("fields", fields);
			
			JSONObject result = poiApiService.getPoisWithLevel(param.toString());
			MsLogger.debug(result.toString());
			response.getWriter().print(result);
			response.setStatus(200);
			return;
		} catch (Exception e){
			response.getWriter().print(FastJsonUtil.error("-1", e));
			response.setStatus(200);
			return;
		}
		
	}

	/**
	 * 根据业务和POI获取下一层的一级类别列表
	 * @param business_id 业务id
	 * @param poi_id poi ID数据
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params = "method=getCtgrsByBizIdAndPoiId")
	public void getCtgrsByBizIdAndPoiId(
			@RequestParam(required = true, value = "business_id") Integer business_id,
			@RequestParam(required = true, value = "poi_id") String poi_id, 
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		try{
			JSONObject param = JSONObject.parseObject("{}");
			param.put("business_id", business_id);
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
	 * 根据业务，POI和一级类别获取下一层POI数据列表
	 * @param business_id 业务id
	 * @param poi_id poi id
	 * @param ctgr_id 一级类别id
	 * @param fields 字段（eg: "longNm,shortNm,title"）
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params = "method=getPoisByBizIdAndPoiIdAndCtgrId")
	public void getPoisByBizIdAndPoiIdAndCtgrId(
			@RequestParam(required = true, value = "business_id") Integer business_id,
			@RequestParam(required = true, value = "poi_id") String poi_id, 
			@RequestParam(required = true, value = "ctgr_id") int ctgr_id,
			@RequestParam(required = false, value="fields") String fields,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		try{
			if(fields == null){
				fields = "";
			} else {
				fields = fields.trim();
			}
			JSONObject param = JSONObject.parseObject("{}");
			param.put("business_id", business_id);
			param.put("poi_id", poi_id);
			param.put("ctgr_id", ctgr_id);
			param.put("fields", fields);
			
			JSONObject result = poiApiService.getPoisByBizIdAndPoiIdAndCtgrId(param.toString());
			MsLogger.debug(result.toString());
			response.getWriter().print(result.toString());
			response.setStatus(200);
			return;
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
			@RequestParam(required = true) String poi_id, 
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		try{
			JSONObject param = JSONObject.parseObject("{}");
			param.put("poi_id", poi_id);
			
			JSONObject result = poiApiService.getPoiInfoById(param.toString());
			MsLogger.debug(result.toString());
			response.getWriter().print(result);
			response.setStatus(200);
			return;
		} catch (Exception e) {
			response.getWriter().print(FastJsonUtil.error("-1", e));
			response.setStatus(200);
			return;
		}
	}
	
	/**
	 * 获取某个业务某个/几个标签下所有poi数据
	 * @param business_id 业务id
	 * @param tags 标签（eg:"1,2,3"）
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params = "method=getPoisByBizIdAndTags")
	public void getPoisByBizIdAndTags(
			@RequestParam(required = true, value = "business_id") Integer business_id,
			@RequestParam(required = true, value = "tags") String tags,
			@RequestParam(required = false, value = "fields") String fields,
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
			JSONObject param = JSONObject.parseObject("{}");
			param.put("business_id", business_id);
			param.put("tags", tags);
			param.put("fields", fields);
			
			JSONObject result = poiApiService.getPoisByBizIdAndTags(param.toString());
			MsLogger.debug(result.toString());
			response.getWriter().print(result);
			response.setStatus(200);
			return;
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
	
	public static void main(String[] args) {
		String[] tags = {
				"124232",
				"1,2,3",
				"12,232,12",
				" 12",
				"1 2",
				" 1 2",
				"2 , 2",
				"1,2213,3 ",
				"1,22 13,3 "
		};
		for(String tag : tags)
			System.out.println(checkTags(tag));
	}
}
