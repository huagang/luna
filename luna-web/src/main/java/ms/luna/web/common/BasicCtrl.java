package ms.luna.web.common;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
/**
 * 
 * Copyright (C) 2015 - 2016 Microscene Inc., All Rights Reserved.
 *
 * @author: shawn@visualbusiness.com
 * @Date: Apr 5, 2016
 *
 */

import ms.luna.biz.util.COSUtil;
import ms.luna.biz.util.JsonUtil;
import ms.luna.biz.util.VbUtility;
import net.sf.json.JSONObject;

public abstract class BasicCtrl {

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
				// access_url
				response.getWriter().print(result.toString());
				response.setStatus(200);
				return;

			} else {
				response.getWriter().print(JsonUtil.error("-1", result.getString("msg")));
				response.setStatus(200);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print(JsonUtil.error("-1", VbUtility.printStackTrace(e)));
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
