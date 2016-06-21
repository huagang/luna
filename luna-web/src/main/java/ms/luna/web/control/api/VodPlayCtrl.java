package ms.luna.web.control.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ms.luna.biz.cons.VbConstant;
import ms.luna.biz.sc.VodPlayService;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.biz.util.VODUtil;
import ms.luna.biz.util.VbMD5;
import ms.luna.biz.util.VbUtility;

import com.alibaba.dubbo.remoting.exchange.Request;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 视频上传
 * 
 * @author Greek
 * @date create time：2016年5月16日 上午11:11:50
 * @version 1.0
 */
@Component
@Controller
@RequestMapping("/api_vodPlay.do")
public class VodPlayCtrl {

	private static final String GET_VIDEO_URLS = "method=getVideoUrls";
	private static final String UPLOAD_VIDEO = "method=upload_video";

	// 缓存。每上传一个视频文件，生成一个对应token放在缓存中，该token对应转码地址notifyUrl的token参数
	// 当转码回调地址接收到请求时，检查url中的token是否存在缓存中，不存在则不进行操作。若存在，则获取请求中的视频转码信息。
	// 若转码成功，则从缓存中删除对应token，同时更新数据库。若转码失败，则从缓存中删除对应token，但不更新数据库
	private static Set<String> vodFileTokenCache = new LinkedHashSet<>();

	@Autowired
	private VodPlayService vodPlayService;

	/**
	 * 获得视频各种格式的url并写入数据库(由腾讯云进行回调)
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(params = "method=getVideoUrlsFromCallbackInfo")
	public void getVideoUrlsFromCallbackInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/html; charset=UTF-8");
			
			String token = request.getParameter("token");

//			// 测试-------
//			vodFileTokenCache.add(token);
//			// -----------

			// 检测token是否在缓存中
			boolean flag = vodFileTokenCache.contains(token);
			if (flag == false) {
				MsLogger.debug("url的token不在缓存中");
				response.getWriter().print("url的token不在缓存中");
				response.setStatus(200);
				return;
			}
			// request 是否含有内容
			String result = getRequestContent(request);
			if (result.length() == 0) {
				MsLogger.debug("request中没有信息内容");
				response.getWriter().print("request中没有信息内容");
				response.setStatus(200);
				return;
			}
			MsLogger.debug("出现转码回调信息:"+result);//
			JSONObject params = JSONObject.parseObject(result);

			int status = params.getInteger("status");// 返回状态
			if (status != 0) {
				// 转码失败
				refreshVodFileTokenCache(token, REFRESHMODE.DELETE);
				MsLogger.debug("返回状态不为0。token:"+token);
				response.getWriter().print("返回状态不为0，token:"+token);
				response.setStatus(200);
				return;
			}
			String task = params.getString("task");

			// 第一次回调
			if (task.equals("file_upload")) {
				JSONObject json1 = params.getJSONObject("data");
				int ret = json1.getInteger("ret");
				if (ret != -303) {
					// 转码失败
					refreshVodFileTokenCache(token, REFRESHMODE.DELETE);
					MsLogger.debug("第一次转码回调，ret!=-303。token:"+token);
					response.getWriter().print("第一次转码回调，ret!=-303，token:"+token);
				} else {
					// 正在转码
					MsLogger.debug("第一次转码回调，ret=-303。token:"+token);
					response.getWriter().print("第一次转码回调，ret=-303，token:"+token);
				}
				response.setStatus(200);
				return;
			}

			// 第二次回调
			if (task.equals("transcode")) {
				JSONObject json2 = params.getJSONObject("data");
				int ret = json2.getInteger("ret");
				String vod_file_id = json2.getString("file_id");// 腾讯云有时用file_id有时fileId

				if (ret != 0) {
					// 转码失败
					refreshVodFileTokenCache(token, REFRESHMODE.DELETE);
					MsLogger.debug("第二次转码回调，ret!=0。token:"+token);
					response.getWriter().print("第二次转码回调，ret!=0，token:"+token);
					response.setStatus(200);
					return;
				}
				// 转码成功
				// 通过fileId查询转码信息(单纯从回调信息能获取url但是无法获知是哪一个视频格式对应的url)
				
				MsLogger.debug("转码信息返回成功，token:"+token+"fileId:"+vod_file_id);
				
				JSONObject result2 = VODUtil.getInstance().getVodPlayUrls(vod_file_id);
				JSONObject params2 = JSONObject.parseObject("{}");
				String code = result2.getString("code");
				if ("0".equals(code)) {
					JSONObject data = result2.getJSONObject("data");
					JSONArray playSet = data.getJSONArray("playSet");
					for (int i = 0; i < playSet.size(); i++) {
						JSONObject play = (JSONObject) playSet.get(i);
						String definition = "definition" + play.getInteger("definition"); // 获得格式对应的编号
						String definition_nm = VbConstant.VOD_DEFINITION.ConvertName(definition); // 获得格式在数据库中的对应名称
						String url = play.getString("url");
						params2.put(definition_nm, url);
					}
					params2.put("vod_file_id", vod_file_id);

					// 写入数据库(更新记录)
					JSONObject resJson = vodPlayService.updateVodRecordById(params2.toString());
					refreshVodFileTokenCache(token, REFRESHMODE.DELETE);
					code = resJson.getString("code");
					if ("0".equals(code)) {
						MsLogger.debug("转码地址写入数据库成功,fileId:"+vod_file_id);
						response.getWriter().print("转码地址写入数据库成功，fileId:"+vod_file_id);
						response.setStatus(200);
						return;
					} else {
						MsLogger.debug("转码地址写入数据库失败，"+resJson.toString()+" fileId:"+vod_file_id);
						response.getWriter().print("转码地址写入数据库失败，"+resJson.toString()+" fileId:"+vod_file_id);
						response.setStatus(200);
						return;
					}
				} else {
					MsLogger.debug("获取视频转码地址失败，"+params2.toString()+" fileId:"+vod_file_id);
					response.getWriter().print("获取视频转码地址失败，"+params2.toString()+" fileId:"+vod_file_id);
					response.setStatus(200);
					return;
				}
			} 
		} catch (Exception e) {
			response.getWriter().print(FastJsonUtil.error("-1", e));
			response.setStatus(200);
			return;
		}
	}

	/**
	 * 获取request请求中的内容
	 * 
	 * @param request
	 * @return
	 */
	public String getRequestContent(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		InputStream in = null;
		InputStreamReader reader = null;
		BufferedReader buffer = null;
		String temp = null;
		try {
			in = request.getInputStream();
			reader = new InputStreamReader(in, request.getCharacterEncoding());
			buffer = new BufferedReader(reader);
			while ((temp = buffer.readLine()) != null) {
				sb.append(temp);
			}
			MsLogger.debug(sb.toString());
			buffer.close();
			reader.close();
			in.close();
			in = null;
		} catch (IOException e) {
			MsLogger.debug(e);
		}
		return sb.toString();
	}

	@RequestMapping(params = GET_VIDEO_URLS)
	public void getVideoUrls(@RequestParam(required = true, value = "fileId") String fileId,
			HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		try{
			JSONObject param = JSONObject.parseObject("{}");
			param.put("vod_file_id", fileId);
			
			JSONObject result1 = vodPlayService.getVodRecordById(param.toString());
			MsLogger.debug(result1.toString());
			if(!"1".equals(result1.getString("code"))){
				response.getWriter().print(result1);
				response.setStatus(200);
				return;
			}
			// code="1"表明未得到视频url信息，则继续访问腾讯云
			JSONObject result = VODUtil.getInstance().getVodPlayUrls(fileId);
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
	 * 缓存更新
	 * 
	 * @param idTokenCache
	 * @param token
	 * @return
	 */
	public static boolean refreshVodFileTokenCache(String token, String mode) {
		boolean flag = false;
		if (token == null || token.length() == 0)
			return false;

		if (mode.equals(REFRESHMODE.DELETE)) {
			synchronized (VodPlayCtrl.class) {
				flag = vodFileTokenCache.remove(token);
			}
		} else if (mode.equals(REFRESHMODE.ADD)) {
			synchronized (VodPlayCtrl.class) {
				flag = vodFileTokenCache.add(token);
			}
		}
		return flag;
	}

	/**
	 * 缓存更新模式——更新，添加
	 */
	public static final class REFRESHMODE {
		public static final String ADD = "add";
		public static final String DELETE = "delete";
	}
}
