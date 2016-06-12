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
import ms.luna.biz.util.JsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.biz.util.VODUtil;
import ms.luna.biz.util.VbMD5;
import ms.luna.biz.util.VbUtility;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 视频上传
 * 
 * @author Greek
 * @date create time：2016年5月16日 上午11:11:50
 * @version 1.0
 */
@Component
@Controller
@RequestMapping("/api/vodPlay.do")
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
	 */
	@RequestMapping(params = GET_VIDEO_URLS)
	public void getVideoUrls(HttpServletRequest request, HttpServletResponse response) {
		try {
			String token = request.getParameter("token");

//			// 测试-------
//			vodFileTokenCache.add(token);
//			// -----------

			// 检测token是否在缓存中
			boolean flag = vodFileTokenCache.contains(token);
			if (flag == false) {
				return;
			}
			// request 是否含有内容
			String result = getRequestContent(request);
			if (result.length() == 0) {
				return;
			}
			JSONObject params = JSONObject.fromObject(result);

			int status = params.getInt("status");// 返回状态
			if (status != 0) {
				// 转码失败
				refreshVodFileTokenCache(token, REFRESHMODE.DELETE);
				return;
			}
			String task = params.getString("task");

			// 第一次回调
			if (task.equals("file_upload")) {
				JSONObject json1 = params.getJSONObject("data");
				int ret = json1.getInt("ret");
				if (ret != -303) {
					// 转码失败
					refreshVodFileTokenCache(token, REFRESHMODE.DELETE);
				}
				return;
			}

			// 第二次回调
			if (task.equals("transcode")) {
				JSONObject json2 = params.getJSONObject("data");
				int ret = json2.getInt("ret");
				String vod_file_id = json2.getString("file_id");// 腾讯云有时用file_id有时fileId

				if (ret != 0) {
					// 转码失败
					refreshVodFileTokenCache(token, REFRESHMODE.DELETE);
					return;
				}
				// 转码成功
				// 通过fileId查询转码信息(单纯从回调信息能获取url但是无法获知是哪一个视频格式对应的url)
				JSONObject result2 = VODUtil.getInstance().getVodPlayUrls(vod_file_id);

				JSONObject params2 = JSONObject.fromObject("{}");
				if (result2.getString("code").equals("0")) {
					JSONObject data = result2.getJSONObject("data");
					JSONArray playSet = data.getJSONArray("playSet");
					for (int i = 0; i < playSet.size(); i++) {
						JSONObject play = (JSONObject) playSet.get(i);
						String definition = "definition" + play.getInt("definition"); // 获得格式对应的编号
						String definition_nm = VbConstant.VOD_DEFINITION.ConvertName(definition); // 获得格式在数据库中的对应名称
						String url = play.getString("url");
						params2.put(definition_nm, url);
					}
					params2.put("vod_file_id", vod_file_id);

					// 写入数据库(更新记录)
					JSONObject resJson = vodPlayService.updateVodFileById(params2.toString());
					refreshVodFileTokenCache(token, REFRESHMODE.DELETE);
					if (resJson.getString("code").equals("0")) {
						MsLogger.debug("转码成功");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
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
			e.printStackTrace();
		}
		return sb.toString();
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
