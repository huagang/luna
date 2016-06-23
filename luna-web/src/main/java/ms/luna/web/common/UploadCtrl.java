package ms.luna.web.common;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.biz.util.VODUtil;
import ms.luna.biz.util.VbMD5;
import ms.luna.biz.util.VbUtility;
import ms.luna.biz.cons.VbConstant.UploadFileRule;
import ms.luna.biz.util.COSUtil;
import com.alibaba.fastjson.JSONObject;
@Component("uploadCtrl")
@Controller
@RequestMapping("/uploadCtrl.do")
public class UploadCtrl {

	private final static Logger logger = Logger.getLogger(UploadCtrl.class);

	private Random random = new Random();
	private Set<String> validFileExtention;

	@PostConstruct
	public void init() {
		validFileExtention = new HashSet<>();
		validFileExtention.add("jpg");
		validFileExtention.add("jpeg");
		validFileExtention.add("png");
	}
	
	@RequestMapping(params = "method=aync_upload_pic")
	public void ayncUploadPic(
			@RequestParam(required = true, value ="pic")
			MultipartFile file,
			@RequestParam(required = true, value="app_id")
			String appId,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");

		if (file == null || file.isEmpty()) {
			response.getWriter().print(FastJsonUtil.error("-1", "文件不能为空"));
			response.setStatus(200);
			return;
		}
		// 文件太大超过预定的10M
		if (file.getSize() > COSUtil.图片上传大小分界线1M) {
			response.getWriter().print(FastJsonUtil.error("-1", "文件大小不能超过1M"));
			response.setStatus(200);
			return;
		}
		
		String fileName = file.getOriginalFilename();
		int point = fileName.lastIndexOf(".");
		if (point == -1 || point == fileName.length()-1) {
			response.getWriter().print(FastJsonUtil.error("-1", "文件类型不正确"));
			response.setStatus(200);
			return;
		}
		String extName = fileName.substring(point+1);
		extName = extName.toLowerCase();
		if (extName.length() == 0 || (! validFileExtention.contains(extName))) {
			response.getWriter().print(FastJsonUtil.error("-1", "文件类型必须是" + validFileExtention.toString()));
			response.setStatus(200);
			return;
		}

		try {
			JSONObject result = COSUtil.getInstance().upload2CloudDirect(file, COSUtil.getLunaImgRootPath() + "/" + appId + "/");
			BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
			if(result.containsKey("data")) {
				JSONObject data = result.getJSONObject("data");
				data.put("height", bufferedImage.getHeight());
				data.put("width", bufferedImage.getWidth());
			}
			
			logger.debug(result.toString());
			response.getWriter().print(result.toString());
			response.setStatus(200);
			return;
		} catch (Exception e) {
			response.getWriter().print(FastJsonUtil.error("-1", VbUtility.printStackTrace(e)));
			response.setStatus(200);
			return;
		}
	}

	/**
	 * 最终cos路径构成：bucket/{env}/{type}/{path}/{filename}
	 */
	@RequestMapping(params = "method=uploadFile2Cloud")
	public void uploadFile2Cloud(@RequestParam(required = true, value = "file") MultipartFile file,
			@RequestParam(required = true, value = "type") String type,  		// 上传类型
			@RequestParam(required = false, value = "path") String path,		// 路径
			@RequestParam(required = false, value = "filename") String fileName,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		try{
			// 类型检查
			if(! UploadFileRule.isValidFileType(type)){
				response.getWriter().print(FastJsonUtil.error("-1", "type must be image, audio, video or zip"));
				response.setStatus(200);
				return;
			}
			// 后缀名获取和检查
			String ext = getExt(type,file.getOriginalFilename());// 
			if (ext == null) {
				response.getWriter().print(FastJsonUtil.error("-1", "filename extension is wrong"));
				response.setStatus(200);
				return;
			}
			// 文件大小检查
			if(! UploadFileRule.isValidSize(type, file.getSize())){
				response.getWriter().print(FastJsonUtil.error("-1", "file size is larger"));
				response.setStatus(200);
				return;
			}
			// 目录
			if(StringUtils.isBlank(path) || (! UploadFileRule.isValidPath(path))){
				path = getDefaultPath(type);// 获取默认路径
			}
			// TODO: 文件名，存在保留文件名称的需求? 这种情况下底层实现需要判断文件名是否已经存在
			if(StringUtils.isBlank(fileName)) {
				fileName = generateFileName(ext);
			}
			JSONObject result = uploadFile2Cloud(file, type, COSUtil.LUNA_BUCKET, path, fileName);
			MsLogger.debug("method:uploadFile2Cloud, result from server: " + result.toString());
			
			response.getWriter().print(result);
			response.setStatus(200);
		} catch(Exception e){
			response.getWriter().print(FastJsonUtil.error("-1", "Failed to upload file: " + e));
			response.setStatus(200);
		}
			
	}

	private JSONObject uploadFile2Cloud(MultipartFile file,
			String type, String bucket, String path, String filename) throws Exception {
		JSONObject result = new JSONObject();
		String realPath = type + "/" + path;
		if(type.equals(UploadFileRule.PIC) || type.equals(UploadFileRule.AUDIO)){
			result = COSUtil.getInstance().upload2Cloud(file, bucket, realPath, filename);
		} else if(type.equals(UploadFileRule.VIDEO)){
			result = VODUtil.getInstance().upload2Cloud(file, bucket, realPath, filename, "");
		} else if(type.equals(UploadFileRule.ZIP)){
			//TODO
		}
		
		return result;
	}

	/**
	 * 获得默认文件名(带后缀)
	 * 
	 * @param ext 文件后缀
	 * @return
	 */
	private String generateFileName(String ext) {
		String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String fileNameInCloud = date + "_" + StringUtils.leftPad(String.valueOf(random.nextInt()), 10) + ext;
		return fileNameInCloud;
	}
	
	/**
	 * 获得默认的文件路径
	 * 
	 * @param type 文件类型
	 * @return
	 */
	private String getDefaultPath(String type) {
		return COSUtil.DEFAULT_PATH;
	}

	/**
	 * 返回文件的后缀
	 * 
	 * @param type 文件类型
	 * @param originalFilename 文件名（带后缀）
	 * @return
	 */
	private String getExt(String type, String originalFilename) {
		if(type.equals(UploadFileRule.PIC)){
			return VbUtility.getExtensionOfPicFileName(originalFilename);
		}
		if(type.equals(UploadFileRule.AUDIO)){
			return VbUtility.getExtensionOfAudioFileName(originalFilename);
		}
		if(type.equals(UploadFileRule.VIDEO)){
			return VbUtility.getExtensionOfVideoFileName(originalFilename);
		}
		if(type.equals(UploadFileRule.ZIP)){
			return VbUtility.getExtensionOfZipFileName(originalFilename);
		}
		return null;
	}

}
