package ms.luna.web.common;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import ms.luna.biz.cons.VbConstant.UPLOAD_FILE_TYPE;
import ms.luna.biz.util.COSUtil;
import com.alibaba.fastjson.JSONObject;
@Component("uploadCtrl")
@Controller
@RequestMapping("/uploadCtrl.do")
public class UploadCtrl {

	private final static Logger logger = Logger.getLogger(UploadCtrl.class);
	
	private Set<String> validFileExtention;
	
	public UploadCtrl() {

	}

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
			response.getWriter().print(FastJsonUtil.error("-1", "file can not be empty"));
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

	@RequestMapping(params = "method=upload_audio")
	public void uploadAudio(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

	}
	
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(params = "method=uploadFile2Cloud")
	public void uploadFile2Cloud(@RequestParam(required = true, value = "file") MultipartFile file,
			@RequestParam(required = true, value = "type") String type,  		// 上传类型
			@RequestParam(required = false, value = "bucket") String bucket,	// bucket
			@RequestParam(required = false, value = "path") String path,		// 路径
			@RequestParam(required = false, value = "filename") String filename,// 文件名
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		try{
			// 类型检查
			boolean flag = checkType(type);
			if(!flag){
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
			flag = checkFileSize(file,type);
			if(!flag){
				response.getWriter().print(FastJsonUtil.error("-1", "file size is larger"));
				response.setStatus(200);
				return;
			}
			// bucket
			if(bucket == null){
				bucket = getDefaultBucket(type);// 获取默认bucket
			}
			// 目录
			if(path == null){
				path = getDefaultPath(type);// 获取默认路径
			}
			// 文件名
			if(filename == null){
				filename = getDefaultName(ext);
			}
			
			JSONObject result = uploadFile2Cloud(request, response, file, type, bucket, path, filename);
			MsLogger.debug("method:uploadFile2Cloud, result from server: " + result.toString());
			
			response.getWriter().print(result);
			response.setStatus(200);
		} catch(Exception e){
			response.getWriter().print(FastJsonUtil.error("-1", "Failed to upload file: " + e));
			response.setStatus(200);
		}
			
	}

	private JSONObject uploadFile2Cloud(HttpServletRequest request, HttpServletResponse response, MultipartFile file,
			String type, String bucket, String path, String filename) throws Exception {
		JSONObject result = new JSONObject();
		if(type.equals(UPLOAD_FILE_TYPE.PIC) || type.equals(UPLOAD_FILE_TYPE.AUDIO)){
			result = COSUtil.getInstance().upload2Cloud(file, bucket, path, filename);
		} else if(type.equals(UPLOAD_FILE_TYPE.VIDEO)){
			result = VODUtil.getInstance().upload2Cloud(request, response, file, bucket, path, filename);
		} else if(type.equals(UPLOAD_FILE_TYPE.ZIP)){
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
	private String getDefaultName(String ext) {
		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String fileNameInCloud = VbMD5.generateToken() + ext;
		return fileNameInCloud;
	}
	
	/**
	 * 获得默认的文件路径
	 * 
	 * @param type 文件类型
	 * @return
	 */
	private String getDefaultPath(String type) {
		// TODO
		return null;
	}

	/**
	 * 获得默认的bucket
	 * 
	 * @param type 文件类型
	 * @return
	 */
	private String getDefaultBucket(String type) {
		// TODO
		return null;
	}

	/**
	 * 检查文件的大小是否符合要求
	 * 
	 * @param file 上传文件
	 * @param type 文件类型
	 * @return
	 * @throws IOException 
	 */
	private boolean checkFileSize(MultipartFile file, String type) throws IOException {
		// TODO
		return false;
	}

	/**
	 * 返回文件的后缀
	 * 
	 * @param type 文件类型
	 * @param originalFilename 文件名（带后缀）
	 * @return
	 */
	private String getExt(String type, String originalFilename) {
		if(type.equals(UPLOAD_FILE_TYPE.PIC)){
			return VbUtility.getExtensionOfPicFileName(originalFilename);
		}
		if(type.equals(UPLOAD_FILE_TYPE.AUDIO)){
			return VbUtility.getExtensionOfAudioFileName(originalFilename);
		}
		if(type.equals(UPLOAD_FILE_TYPE.VIDEO)){
			return VbUtility.getExtensionOfVideoFileName(originalFilename);
		}
		if(type.equals(UPLOAD_FILE_TYPE.ZIP)){
			return VbUtility.getExtensionOfZipFileName(originalFilename);
		}
		return null;
	}

	/**
	 * 检查文件类型是否满足要求
	 * 
	 * @param type 文件类型
	 * @return
	 */
	private boolean checkType(String type) {
		if(type.equals(UPLOAD_FILE_TYPE.PIC) || type.equals(UPLOAD_FILE_TYPE.AUDIO) || type.equals(UPLOAD_FILE_TYPE.VIDEO) || type.equals(UPLOAD_FILE_TYPE.ZIP)){
			return true;
		}
		return false;
	}

}
