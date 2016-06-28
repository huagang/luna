package ms.luna.biz.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.swetake.util.Qrcode;

/**
 * VbUtility
 *
 * @author Mark
 *
 */
public final class VbUtility {
	public static final int BUFFER_SIZE = 1024;
	private static String PICS = "(\\.png|\\.bmp|\\.jpg|\\.tiff|\\.gif|\\.pcx|\\.tga|\\.exif|\\.fpx|\\.svg|\\.psd|\\.cdr|\\.pcd|\\.dxf|\\.ufo|\\.eps|\\.ai|\\.raw)";
	private static String AUDIOS = "(\\.mp3|\\.wav|\\.wma|\\.ogg|\\.ape|\\.acc)";
	private static String VIDEOS = "(\\.rm|\\.rmvb|\\.avi|\\.mp4|\\.3gp)";
	private static String ZIPS = "(\\.zip)";
	
	public static String printStackTrace(Exception e) {
		MsLogger.error(e);
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw, true));
		return sw.toString();
	}

	/**
	 * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public final static String getRemortIP(HttpServletRequest request) throws IOException {
		// 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址

		String ip = request.getHeader("X-Forwarded-For");
		MsLogger.info("getIpAddress(HttpServletRequest) - X-Forwarded-For - String ip=" + ip);

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
				MsLogger.info("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip=" + ip);
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
				MsLogger.info("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip=" + ip);
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
				MsLogger.info("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip=" + ip);
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
				MsLogger.info("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip=" + ip);
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
				MsLogger.info("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=" + ip);
			}
		} else if (ip.length() > 15) {
			String[] ips = ip.split(",");
			for (int index = 0; index < ips.length; index++) {
				String strIp = (String) ips[index];
				if (!("unknown".equalsIgnoreCase(strIp))) {
					ip = strIp;
					break;
				}
			}
		}
		return ip;
	}

	/**
	 */
	private VbUtility() {
	}

	public static HttpSession timeOut(HttpServletRequest request, HttpServletResponse response, String key) {
		HttpSession session = request.getSession(false);
		if (session == null || key != null && session.getAttribute(key) == null) {
			try {
				response.setHeader("Access-Control-Allow-Origin", "*");
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter().print(FastJsonUtil.error("-101", "处理已经超时，请重新尝试"));
				response.setStatus(200);
				return null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return session;
	}

	/**
	 * 生成二维码(QRCode)图片
	 * 
	 * @param content
	 *            二维码图片的内容
	 * @param imgPath
	 *            生成二维码图片完整的路径
	 * @param ccbpath
	 *            二维码图片中间的logo路径
	 */
	public static byte[] createQRCode(String url, String logo) {
		try {
			Qrcode qrcodeHandler = new Qrcode();
			qrcodeHandler.setQrcodeErrorCorrect('M');
			qrcodeHandler.setQrcodeEncodeMode('B');
			qrcodeHandler.setQrcodeVersion(7);
			byte[] contentBytes = url.getBytes("gb2312");
			BufferedImage bufImg = new BufferedImage(280, 280, BufferedImage.TYPE_INT_RGB);
			Graphics2D gs = bufImg.createGraphics();
			gs.setBackground(Color.WHITE);
			gs.clearRect(0, 0, 280, 280);

			// 设定图像颜色 > BLACK
			gs.setColor(Color.BLACK);
			// 设置偏移量 不设置可能导致解析出错
			int pixoff = 2;
			// 输出内容 > 二维码
			if (contentBytes.length > 0 && contentBytes.length < 120) {
				boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
				for (int i = 0; i < codeOut.length; i++) {
					for (int j = 0; j < codeOut.length; j++) {
						if (codeOut[j][i]) {
							gs.fillRect(j * 6 + pixoff, i * 6 + pixoff, 6, 6);
						}
					}
				}
			} else {
				MsLogger.debug("QRCode content bytes length = " + contentBytes.length + " not in [ 0,120 ]. ");
				return null;
			}
			Image img = ImageIO.read(new File(logo));
			// 实例化一个Image对象。
			gs.drawImage(img, 110, 110, 60, 60, null);
			gs.dispose();
			bufImg.flush();

			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			ImageOutputStream imOut;

			// 生成二维码QRCode图片
			imOut = ImageIO.createImageOutputStream(bs);
			ImageIO.write(bufImg, "jpg", imOut);
			return bs.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean createLocalFolder(String folder) {
		String[] folders = folder.split("/", folder.length());
		StringBuffer sb = new StringBuffer("/");
		for (int i = 0; i < folders.length; i++) {
			if (folders[i].length() == 0) {
				continue;
			}
			sb.append(folders[i]).append("/");
			File file = new File(sb.toString());
			if (!file.exists()) {
				if (!file.mkdir()) {
					return false;
				}
			}
		}
		return true;
	}

	public static Boolean checkPicIsOK(String value) {
		if (value == null || value.isEmpty()) {
			return true;
		}
		Pattern pattern1 = Pattern.compile(".+" + PICS, Pattern.CASE_INSENSITIVE);

		Matcher matcher1 = pattern1.matcher(value);
		if (!matcher1.matches()) {
			MsLogger.debug("[" + value + "]图片格式不正确 ");
			return false;
		}
		return true;
	}

	public static Boolean checkAudioIsOK(String value) {
		if (value == null || value.isEmpty()) {
			return true;
		}
		Pattern pattern1 = Pattern.compile(".+" + AUDIOS, Pattern.CASE_INSENSITIVE);

		Matcher matcher1 = pattern1.matcher(value);
		if (!matcher1.matches()) {
			MsLogger.debug("[" + value + "]音频格式不正确 ");
			return false;
		}
		return true;
	}

	public static Boolean checkVideoIsOK(String value) {
		if (value == null || value.isEmpty()) {
			return true;
		}
		Pattern pattern1 = Pattern.compile(".+" + VIDEOS, Pattern.CASE_INSENSITIVE);

		Matcher matcher1 = pattern1.matcher(value);
		if (!matcher1.matches()) {
			MsLogger.debug("[" + value + "]视频格式不正确 ");
			return false;
		}
		return true;
	}

	public static Boolean checkCOSPicIsOK(String value) {
		if (value == null || value.isEmpty()) {
			return true;
		}
		Pattern pattern1 = Pattern.compile("http://view.luna.visualbusiness.cn/.+" + PICS, Pattern.CASE_INSENSITIVE);

		Matcher matcher1 = pattern1.matcher(value);
		if (!matcher1.matches()) {
			MsLogger.debug("[" + value + "]图片格式不正确或者是没有上传的图片地址 ");
			return false;
		}
		return true;
	}

	public static Boolean checkCOSAudioIsOK(String value) {
		if (value == null || value.isEmpty()) {
			return true;
		}
		Pattern pattern1 = Pattern.compile("http://view.luna.visualbusiness.cn/.+" + AUDIOS, Pattern.CASE_INSENSITIVE);

		Matcher matcher1 = pattern1.matcher(value);
		if (!matcher1.matches()) {
			MsLogger.debug("[" + value + "]音频格式不正确或者是没有上传的图片地址 ");
			return false;
		}
		return true;
	}

	public static Boolean checkCOSVideoIsOK(String value) {
		if (value == null || value.isEmpty()) {
			return true;
		}
//		Pattern pattern1 = Pattern.compile("http://view.luna.visualbusiness.cn/.+" + VIDEOS, Pattern.CASE_INSENSITIVE);
		Pattern pattern1 = Pattern.compile("\\d+");// 只匹配数字
		
		Matcher matcher1 = pattern1.matcher(value);
		if (!matcher1.matches()) {
			MsLogger.debug("[" + value + "]视频格式不正确或者是没有上传的图片地址 ");
			return false;
		}
		return true;
	}

	public static String getExtensionOfFileName(String filename) {
		String ext = null;
		if (filename == null || filename.isEmpty()) {
			return ext;
		}
		int index = filename.lastIndexOf(".");
		if (index <= 0 || index == filename.length() - 1) {
			return null;
		}
		ext = filename.substring(index);
		return ext;
	}

	public static String getExtensionOfPicFileName(String filename) {
		String ext = getExtensionOfFileName(filename);
		Pattern pattern = Pattern.compile(PICS, Pattern.CASE_INSENSITIVE);

		Matcher matcher = pattern.matcher(ext);
		if (matcher.matches()) {
			return ext;
		}
		return null;
	}

	public static String getExtensionOfAudioFileName(String filename) {
		String ext = getExtensionOfFileName(filename);
		Pattern pattern = Pattern.compile(AUDIOS, Pattern.CASE_INSENSITIVE);

		Matcher matcher = pattern.matcher(ext);
		if (matcher.matches()) {
			return ext;
		}
		return null;
	}
	public static String getFileName(String path) {
		int lastIndex = path.lastIndexOf("/");
		return lastIndex < 0 || path.length() == 1 ? path : path.substring(lastIndex + 1); 
	}

	public static String getExtensionOfVideoFileName(String filename) {
		String ext = getExtensionOfFileName(filename);
		Pattern pattern = Pattern.compile(VIDEOS, Pattern.CASE_INSENSITIVE);

		Matcher matcher = pattern.matcher(ext);
		if (matcher.matches()) {
			return ext;
		}
		return null;
	}

	public static String getExtensionOfZipFileName(String filename) {
		String ext = getExtensionOfFileName(filename);
		Pattern pattern = Pattern.compile(ZIPS, Pattern.CASE_INSENSITIVE);

		Matcher matcher = pattern.matcher(ext);
		if (matcher.matches()) {
			return ext;
		}
		return null;
	}
	
	/**
	 * 判断文件名是否以.zip为后缀
	 * @param fileName 需要判断的文件名
	 * @return 是zip文件返回true,否则返回false
	 */
	public static boolean isEndsWithZip(String fileName) {
		boolean flag = false;
		if(fileName != null && !"".equals(fileName.trim())) {
			fileName = fileName.toLowerCase();
			if(fileName.endsWith(".zip")){
				flag = true;
			}
		}
		return flag;
	}
	
	public static String saveFile(String filePath, MultipartFile uploadedFile, String extension, String fileNme) {
		if (uploadedFile == null || uploadedFile.isEmpty()) {
			return null;
		}

		if (!uploadedFile.getOriginalFilename().toLowerCase().endsWith(extension)) {
			return null;
		}

		InputStream is = null;
		FileOutputStream fos = null;
		try {
			if (!VbUtility.createLocalFolder(filePath)) {
				return null;
			}
			is = uploadedFile.getInputStream();
			fos = new FileOutputStream(filePath + fileNme);
			byte[] b = new byte[BUFFER_SIZE];
			int count = 0;
			while((count = is.read(b, 0, BUFFER_SIZE)) != -1){
				fos.write(b, 0, count);
			}
			is.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
			if (is != null) {
				try {
					is.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			return null;
		}
		return filePath + fileNme;
	}
}
