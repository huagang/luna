package ms.luna.web.control;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ms.luna.web.common.BasicCtrl;
import ms.luna.biz.util.COSUtil;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.biz.util.VODUtil;
import ms.luna.biz.util.VbMD5;
import ms.luna.biz.util.VbUtility;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
/**
 * 
 * Copyright (C) 2015 - 2016 Microscene Inc., All Rights Reserved.
 *
 * @author: wumengqiang
 * @email: dean@visualbusiness.com
 * @Date: June 15, 2016
 *
 *
 *
 */

@Component
@Controller
@RequestMapping("/add_article.do")
public class AddArticle extends BasicCtrl{
	
	@RequestMapping(params = "method=init", method={RequestMethod.GET})
	public ModelAndView init(HttpServletRequest request, HttpServletResponse response){
		ModelAndView view = new ModelAndView();
		try{			
			view.setViewName("/add_article.jsp");
			return view;
		} catch(Exception e){
			MsLogger.error(e);
			view.setViewName("/error.jsp");
			return view;
		}
	}
	
	/**
	 * 异步上传图片
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(params = "method=upload_img")
	public void uploadThumbnail(
			@RequestParam(required = true, value = "thumbnail_fileup") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 后缀名校验
		String ext = VbUtility.getExtensionOfPicFileName(file.getOriginalFilename());
		if (ext == null) {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().print(FastJsonUtil.error("-1", "文件扩展名有错误"));
			response.setStatus(200);
			return;
		}
		// 路径名（这里用的是poi的路径 COSUtil.getCosPoiPicFolderPath() + "/" + date，可以替换成微景展的）
		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String fileNameInCloud = VbMD5.generateToken() + ext;// 文件名 
		super.uploadLocalFile2Cloud(request, response, file, COSUtil.getCosPoiPicFolderPath() + "/" + date, fileNameInCloud);
	}
	
	/**
	 * 异步上传视频
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(params = "method=upload_video")
	public void uploadVideo(
			@RequestParam(required = true, value = "video_fileup") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		try {
			String ext = VbUtility.getExtensionOfVideoFileName(file.getOriginalFilename());
			if (ext == null) {
				response.getWriter().print(FastJsonUtil.error("4", "文件扩展名有错误"));
				response.setStatus(200);
				return;
			}
			String fileName = VbMD5.generateToken() + ext;// 生成文件名
			String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
			JSONObject result = VODUtil.getInstance().upload2Cloud(request, response, file,
					VODUtil.getVODPoiVideoFolderPath() + "/" + date, fileName);
			MsLogger.debug("method:upload2Cloud, result from service: " + result.toString());
			response.getWriter().print(result);
			
			response.setStatus(200);
			return;
		} catch (Exception e) {
			response.getWriter().print(FastJsonUtil.error("-1", "Failed to upload video: " + e));
			response.setStatus(200);
			return;
		}
	}
}	

