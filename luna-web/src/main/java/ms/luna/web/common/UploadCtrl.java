package ms.luna.web.common;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ms.luna.biz.util.JsonUtil;
import ms.luna.biz.util.VbUtility;
import ms.luna.biz.util.COSUtil;
import net.sf.json.JSONObject;
@Component("uploadCtrl")
@Controller
@RequestMapping("/uploadCtrl.do")
public class UploadCtrl {

	private final static Logger logger = Logger.getLogger(UploadCtrl.class);
	
	private Set<String> validFileExtention;
	
	public UploadCtrl() {
		// TODO Auto-generated constructor stub
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
			response.getWriter().print(JsonUtil.error("-1", "file can not be empty"));
			response.setStatus(200);
			return;
		}
		// 文件太大超过预定的10M
		if (file.getSize() > COSUtil.图片上传大小分界线1M) {
			response.getWriter().print(JsonUtil.error("-1", "file is too large( size > 1M)"));
			response.setStatus(200);
			return;
		}
		
		String fileName = file.getOriginalFilename();
		int point = fileName.lastIndexOf(".");
		if (point == -1 || point == fileName.length()-1) {
			response.getWriter().print(JsonUtil.error("-1", "extension name is not correct"));
			response.setStatus(200);
			return;
		}
		String extName = fileName.substring(point+1);
		extName = extName.toLowerCase();
		if (extName.length() == 0 || (! validFileExtention.contains(extName))) {
			response.getWriter().print(JsonUtil.error("-1", "extension must be jpg or png"));
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
			response.getWriter().print(JsonUtil.error("-1", VbUtility.printStackTrace(e)));
			response.setStatus(200);
			return;
		}
	}

	@RequestMapping(params = "method=upload_audio")
	public void uploadAudio(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

	}

}
