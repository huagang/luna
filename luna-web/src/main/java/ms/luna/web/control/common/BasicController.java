package ms.luna.web.control.common;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.util.COSUtil;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.biz.util.VbUtility;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Copyright (C) 2015 - 2016 Microscene Inc., All Rights Reserved.
 *
 * @author: shawn@visualbusiness.com
 * @Date: Apr 5, 2016
 */

public abstract class BasicController {

	public String localServerTempPath = "/data1/luna/";
	
	protected ModelAndView buildModelAndView(String view) {
		
		// jsp后缀可以通过框架层面统一配置suffix，此处为了兼容，后续框架添加了此配置，都不需要添加"suffix"
		if(! view.endsWith(".jsp")) {
			view = view + ".jsp";
		}
		ModelAndView modelAndView = new ModelAndView(view);
		
		return modelAndView;
		
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param file
	 * @param localServerTempPath
	 * @param addressInCloud
	 * @param fileNameInCloud
	 * @throws IOException
	 */
	protected void uploadLocalFile2Cloud(HttpServletRequest request, HttpServletResponse response,
			MultipartFile file,
			String localServerTempPath,
			String addressInCloud,
			String fileNameInCloud) throws IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		JSONObject result;
		try {
			result = COSUtil.getInstance().uploadLocalFile2Cloud(COSUtil.LUNA_BUCKET,
					file.getBytes(), localServerTempPath, addressInCloud + "/" + fileNameInCloud);

			if ("0".equals(result.getString("code"))) {
				String url = result.getJSONObject("data").getString("access_url");
				result.put("original", file.getOriginalFilename());
				result.put("name", VbUtility.getFileName(url));
				result.put("url", url);
				result.put("size", file.getSize());
				result.put("type", VbUtility.getExtensionOfPicFileName(url));
				result.put("state", "SUCCESS");
				// access_url
				response.getWriter().print(result.toString());
				response.setStatus(200);
				return;

			} else {
				result.put("original", file.getOriginalFilename());
				result.put("name", file.getOriginalFilename());
				result.put("url", "");
				result.put("size", file.getSize());
				result.put("type", "");
				result.put("state", "FAIL");

				response.getWriter().print(FastJsonUtil.error("-1", result.getString("msg")));
				response.setStatus(200);
				return;
			}
		} catch (Exception e) {
			MsLogger.error(e);
			result = new JSONObject();
			result.put("original", file.getOriginalFilename());
			result.put("name", file.getOriginalFilename());
			result.put("url", "");
			result.put("size", file.getSize());
			result.put("type", "");
			result.put("state", "FAIL");
			result.putAll(FastJsonUtil.error("-1", VbUtility.printStackTrace(e)));
			response.getWriter().print(result.toJSONString());
			response.setStatus(200);
			return;
		}
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param file
	 * @param addressInCloud
	 * @param fileNameInCloud
	 * @throws IOException
	 */
	protected void uploadLocalFile2Cloud(HttpServletRequest request, HttpServletResponse response,
			MultipartFile file,
			String addressInCloud,
			String fileNameInCloud) throws IOException {
		uploadLocalFile2Cloud(request,
				response,
				file,
				localServerTempPath, 
				addressInCloud,
				fileNameInCloud);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param file
	 * @param addressInCloud
	 * @throws IOException
	 */
	protected void uploadLocalFile2Cloud(HttpServletRequest request, HttpServletResponse response,
			MultipartFile file,
			String addressInCloud) throws IOException {
		uploadLocalFile2Cloud(request,
				response,
				file,
				localServerTempPath, 
				addressInCloud,
				file.getOriginalFilename());
	}

}
