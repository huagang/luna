package ms.luna.web.common;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.QCosConfig;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.cons.VbConstant.UploadFileRule;
import ms.luna.biz.sc.VodPlayService;
import ms.luna.biz.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
@Component("uploadCtrl")
@Controller
@RequestMapping("/uploadCtrl.do")
public class UploadCtrl {

	private final static Logger logger = Logger.getLogger(UploadCtrl.class);

	private Random random = new Random();
	private Set<String> validFileExtention;
	private QCosUtil qCosUtil;

	@Autowired
	private VodPlayService vodPlayService;

	@PostConstruct
	public void init() {
		validFileExtention = new HashSet<>();
		validFileExtention.add("jpg");
		validFileExtention.add("jpeg");
		validFileExtention.add("png");
		qCosUtil = qCosUtil.getInstance();
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
	 *
	 * cos path rule:
	 * 		bucket/{env}/{type}/{resource_type}/{resource_id}/{filename}
	 * vod path rule(path is limited to at most 4 level by vod):
	 * 		bucket/{env}/{resource_type}/{resource_id}/{filename}
	 */
	@RequestMapping(params = "method=uploadFile2Cloud")
	public void uploadFile2Cloud(@RequestParam(required = true, value = "file") MultipartFile file,
			@RequestParam(required = true, value = "type") String type,
			@RequestParam(required = true, value = "resource_type") String resourceType,
			@RequestParam(required = false, value = "resource_id") String resourceId,
			HttpServletResponse response) throws IOException {
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		try{
			// 类型检查
			if(! UploadFileRule.isValidFileType(type)) {
				response.getWriter().print(FastJsonUtil.error(ErrorCode.INVALID_PARAM,
						"文件类型不合法,合法的文件类型:" + UploadFileRule.getValidFileTypes()));
				response.setStatus(200);
				return;
			}
			// 后缀名获取和检查
			String ext = UploadFileRule.getFileExtention(file.getOriginalFilename());
			if (! UploadFileRule.isValidFormat(type, ext)) {
				response.getWriter().print(FastJsonUtil.error("-1", "文件格式不支持"));
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
			// TODO: should check UploadFileRule.isValidPath(resourceType) ?
			if(StringUtils.isBlank(resourceType)){
				resourceType = getDefaultPath(type);// 获取默认路径
			}
			String fileName = generateFileName(ext);
			JSONObject result = uploadFile2Cloud(file, type, QCosConfig.LUNA_BUCKET, resourceType, resourceId, fileName);
			logger.debug("method:uploadFile2Cloud, result from server: " + result.toString());
			
			response.getWriter().print(result);
			response.setStatus(200);
		} catch(Exception e){
			logger.error("Failed to upload file", e);
			response.getWriter().print(FastJsonUtil.error("-1", "上传文件失败"));
			response.setStatus(200);
		}
			
	}

	private JSONObject uploadFile2Cloud(MultipartFile file,
			String type, String bucket, String resourceType, String resourceId, String filename) throws Exception {
		JSONObject result = new JSONObject();
		if(type.equals(UploadFileRule.TYPE_PIC) || type.equals(UploadFileRule.TYPE_AUDIO)) {
			String realPath = String.format("/%s%s/%s/%s", bucket, QCosConfig.ENV, type, resourceType);
			if(StringUtils.isNotBlank(resourceId)) {
				realPath += "/" + resourceId;
			}
			realPath += "/" + filename;
			// current naming rule can ensure that file will not exist on cos
			result = qCosUtil.uploadFileFromStream(bucket, realPath, file.getInputStream(), false);
			if(result.getString("code").equals("0")) {
				if (type.equals(UploadFileRule.TYPE_PIC)) {
					BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
					if (result.containsKey("data")) {
						JSONObject data = result.getJSONObject("data");
						data.put("height", bufferedImage.getHeight());
						data.put("width", bufferedImage.getWidth());
					}
				}
				// replace access url
				LunaQCosUtil.replaceAccessUrl(result);
			}

		} else if(type.equals(UploadFileRule.TYPE_VIDEO)){
			String realPath = String.format("/%s%s/%s/%s", bucket, QCosConfig.ENV, resourceType);
			if(StringUtils.isNotBlank(resourceId)) {
				realPath += "/" + resourceId;
			}
			realPath += "/" + filename;
			JSONObject vodResult = VODUtil.getInstance().upload2Cloud(file, realPath, filename, "", 0);

			JSONObject retData = new JSONObject();
			retData.put("original", file.getOriginalFilename());
			retData.put("name", file.getOriginalFilename());
			retData.put("size", file.getSize());
			retData.put("type", UploadFileRule.getFileExtention(file.getOriginalFilename()));
			boolean success = false;

			if(vodResult.getString("code").equals("0")) {
				JSONObject data = vodResult.getJSONObject("data");
				String vodFileId = data.getString("vod_file_id");
				JSONObject urlResult = VODUtil.getInstance().getVodPlayUrls(vodFileId);
				if("0".equals(urlResult.getString("code"))) {
					String originFileUrl = urlResult.getJSONObject("data").getString("vod_original_file_url");
					JSONObject param = new JSONObject();
					param.put("vod_file_id", vodFileId);
					param.put("vod_original_file_url", originFileUrl);
					vodPlayService.createVodRecord(param.toString());
					retData.put(QCosConfig.ACCESS_URL, originFileUrl);
					retData.put("status", "SUCCESS");
					result = FastJsonUtil.sucess("", retData);
					success = true;
				}
			}
			if(! success) {
				retData.put("status", "FAIL");
				result = FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "");
				result.put("data", retData);
			}

		} else if(type.equals(UploadFileRule.TYPE_ZIP)){
			//TODO: do not process zip now
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
		if(type.equals(UploadFileRule.TYPE_PIC)){
			return VbUtility.getExtensionOfPicFileName(originalFilename);
		}
		if(type.equals(UploadFileRule.TYPE_AUDIO)){
			return VbUtility.getExtensionOfAudioFileName(originalFilename);
		}
		if(type.equals(UploadFileRule.TYPE_VIDEO)){
			return VbUtility.getExtensionOfVideoFileName(originalFilename);
		}
		if(type.equals(UploadFileRule.TYPE_ZIP)){
			return VbUtility.getExtensionOfZipFileName(originalFilename);
		}
		return null;
	}

}
