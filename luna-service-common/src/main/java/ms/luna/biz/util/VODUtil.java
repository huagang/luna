package ms.luna.biz.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.qcloud.vod.ModifiedQcloudApiModuleCenter;
import com.qcloud.vod.Module.ModifiedVod;
import com.qcloud.vod.Utilities.SHA1;

import ms.luna.biz.cons.VbConstant;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 视频点播
 * 
 * @author Administrator
 *
 */
public class VODUtil {
	private static final String SECRETID_VIDEO = "AKIDx3s5Ewr1t8NziCpG5bJMiN7DM4Hw3dRC";// 视频上传密匙
	private static final String SECRETKEY_VIDEO = "Sf21i2X8xup47BruSG1zq9xJ7C3phC3K";
	private static final String METHOD = "POST"; // 请求方法
	private static final String REGION = "bj"; // 区域参数
	private static final String URL = "/api_vodPlayApi.do?method=getVideoUrlsFromCallbackInfo&token="; // 转码回调地址
	private static final int DATASIZE = 1024 * 1024 * 5;// 视屏上传切片大小，默认5M
	private static final long MAX_VIDEO_SIZE = 1024 * 1024 * 5;// 最大上传大小，设置5M

	public static final String LUNA_BUCKET = "luna";
	public static String vodBaseDir = ""; // 在ApplicationListener中初始化
	public static final String LUNA_POI = "/poi";

	private static VODUtil vodUtil = new VODUtil();
	private TreeMap<String, Object> config = new TreeMap<String, Object>();

	private ModifiedQcloudApiModuleCenter getVod() {
		if (config.isEmpty()) {
			config.put("SecretId", SECRETID_VIDEO);
			config.put("SecretKey", SECRETKEY_VIDEO);
			config.put("RequestMethod", METHOD);
			config.put("DefaultRegion", REGION);
		}
		return new ModifiedQcloudApiModuleCenter(new ModifiedVod(), config);
	}

	private VODUtil() {
	}

	public static VODUtil getInstance() {
		if (vodUtil == null) {
			vodUtil = new VODUtil();
		}
		return vodUtil;
	}

	/**
	 * 获得POI相关视频文件在云中的存储路径
	 * 
	 * @return
	 */
	public static String getVODPoiVideoFolderPath() {
		return LUNA_BUCKET + vodBaseDir + LUNA_POI;
	}

	// --------------------------------------------------------------------------------------

	/**
	 * 获得视频文件的转码地址
	 * 
	 * @param fileId
	 *            视频文件id
	 * @return
	 */
	public JSONObject getVodPlayUrls(String fileId) {
		JSONObject result = describeVodPlayUrls(fileId);
		if (result.getInteger("code") != 0) {
			return FastJsonUtil.error(result.getInteger("code") + "", result.getString("message"));
		}
		JSONObject data = JSONObject.parseObject("{}");
		if (result.containsKey("playSet")) {// 若转码未完成消息形式为{"code":0,"message":""}
			JSONArray playSet = result.getJSONArray("playSet");
			for(int i = 0;i<playSet.size();i++){
				JSONObject play = playSet.getJSONObject(i);
				String definition = "definition" + play.getInteger("definition"); // 获得格式对应的编号
				String definition_nm = VbConstant.VOD_DEFINITION.ConvertName(definition); // 获得格式在数据库中的对应名称
				String url = play.getString("url");
				data.put(definition_nm, url);
			}
			return FastJsonUtil.sucess("视频url信息获取成功", data);
		}
		return FastJsonUtil.error("1", "转码未完成");
	}

	/**
	 * 根据path在VOD中创建新分类（目录），并返回分类id。若目录存在，则直接返回对应分类id
	 * 
	 * @param path
	 *            文件夹路径
	 * @return 成功返回对应分类id。若失败，返回Integer.MIN_VALUE
	 */
	public int createFolder(String path) {
		if (path == null) {
			return Integer.MIN_VALUE;
		}
		// a//b/c////d --> a/b/c/d
		String[] dirs = path.split("/");
		StringBuffer bu = new StringBuffer();
		for (int m = 0; m < dirs.length; m++) {
			if (dirs[m].length() == 0) {
				continue;
			}
			bu.append("/" + dirs[m]);
		}
		if (bu.toString().length() == 0) { // 根目录
			return -1;
		}
		String path2 = bu.toString().substring(1); // path2 = "a/b/c/d"
		JSONObject result = describeAllClass(); // 分类信息（目录信息）
		if (result == null || result.getInteger("code") != 0) { // 未获得目录信息，无法创建文件夹
			return Integer.MIN_VALUE;
		}

		return createFolderByPath(path2, result);
	}

	/**
	 * 根据path在VOD中创建新分类（目录），并返回分类id。若目录存在，则直接返回对应分类id
	 * 
	 * @return 成功返回对应分类id。若失败，返回Integer.MIN_VALUE
	 */
	public int createFolderByPath(String path, JSONObject classesInfo) {
		int classId = getClassIdByPath(path, classesInfo);
		// 存在当前路径,返回路径最后一个文件夹的id
		if (classId != Integer.MIN_VALUE) {
			return classId;
		}
		// 不存在，回退到路径上一个结点。
		int lastIndex = path.lastIndexOf("/");
		// 上一个结点为根结点，在根结点下创建文件夹( folder: a )
		if (lastIndex == -1) {
			String newFolder = path;
			JSONObject json = createClass(newFolder, -1);
			if (json.getInteger("code") != 0) {
				return Integer.MIN_VALUE;
			}
			int newClassId = json.getInteger("newClassId");// 获得创建文件夹的id
			return newClassId;
		}

		// 上一个结点为非根结点，则获得上一个结点的classId，并在此基础上创建文件夹
		String newPath = path.substring(0, lastIndex); // path = "a/b/c"
		classId = createFolderByPath(newPath, classesInfo);
		if (classId == Integer.MIN_VALUE) {
			return Integer.MIN_VALUE;
		}

		String newFolder = path.substring(lastIndex + 1);
		JSONObject json2 = createClass(newFolder, classId);
		if (json2.getInteger("code") != 0) {
			return Integer.MIN_VALUE;
		}
		int newClassId = json2.getInteger("newClassId");// 获得创建文件夹的id
		return newClassId;
	}

	/**
	 * 获得文件夹路径在VOD中对应的分类id
	 * 
	 * @param path
	 *            文件夹路径
	 * @return
	 */
	public int getClassIdByPath(String path) {
		JSONObject result = describeAllClass();
		if (result.getInteger("code") != 0) {
			return Integer.MIN_VALUE;
		}
		JSONArray subclass = JSONArray.parseArray("[]");
		String[] dirs = path.split("/"); // 目录名
		subclass = result.getJSONArray("data");// 子文件夹
		int classId = Integer.MIN_VALUE;// 分类id(目录对应的id)
		boolean flag = false;
		for (int m = 0; m < dirs.length; m++) {
			flag = false;
			for (int i = 0; i < subclass.size(); i++) {
				JSONObject json = subclass.getJSONObject(i);
				JSONObject info = json.getJSONObject("info");
				String name = info.getString("name");
				if (!name.equals(dirs[m])) {
					continue;
				}
				classId = info.getInteger("id");
				subclass = json.getJSONArray("subclass");
				flag = true;
				break;
			}
			if (flag == false) {
				return Integer.MIN_VALUE;
			}
		}
		return classId;
	}

	/**
	 * 获得文件夹路径在VOD中对应的分类id
	 * 
	 * @param path
	 *            文件夹路径
	 * @param classInfo
	 *            从VOD返回的分类（目录）信息
	 * @return 成功返回对应分类id。若失败，返回Integer.MIN_VALUE
	 */
	public int getClassIdByPath(String path, JSONObject classInfo) {
		if (path == null || classInfo == null) {// 路径错误/未返回信息
			return Integer.MIN_VALUE;
		}
		if (classInfo.getInteger("code") != 0) {
			return Integer.MIN_VALUE; // 未返回正确目录信息
		}
		if (path.length() == 0) {
			return -1;// 根目录
		}

		JSONArray subclass = JSONArray.parseArray("[]");
		String[] dirs = path.split("/"); // 目录名
		subclass = classInfo.getJSONArray("data");// 子文件夹
		int classId = Integer.MIN_VALUE;// 分类id(目录对应的id)
		boolean flag = false;
		for (int m = 0; m < dirs.length; m++) {
			flag = false;
			for (int i = 0; i < subclass.size(); i++) {
				JSONObject json = subclass.getJSONObject(i);
				JSONObject info = json.getJSONObject("info");
				String name = info.getString("name");
				if (!name.equals(dirs[m])) {
					continue;
				}
				classId = info.getInteger("id");
				subclass = json.getJSONArray("subclass");
				flag = true;
				break;
			}
			if (flag == false) {
				return Integer.MIN_VALUE;
			}
		}
		return classId;
	}

	public JSONObject upload2Cloud(MultipartFile file, String bucket, String path, String fileName, String webAddr) throws IOException {
		return upload2Cloud(file, bucket + path, fileName, webAddr);
	}
	
	/**
	 * 上传视频到腾讯云VOD
	 * 
	 * @param file
	 *            上传文件，MutipartFile形式
	 * @param path
	 *            上传路径
	 * @param fileName
	 *            上传后的显示文件名
	 * @param notifyUrl
	 *            转码回调地址,允许为null
	 * @param datasize
	 *            分块大小
	 * @param webAddr
	 * 			  url地址
	 * @return
	 * @throws IOException
	 */
	public JSONObject upload2Cloud(MultipartFile file, String path, String fileName, String webAddr) throws IOException {
		// 默认切片5M, 视频标签为null，转码，生成视频封面，无水印
		// 生成转码回调地址
		byte[] bytes = file.getBytes();
		if (bytes.length > MAX_VIDEO_SIZE) {
			return FastJsonUtil.error("2", "文件大小超过" + (MAX_VIDEO_SIZE >> 16) + "M");
		}
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String token = VbMD5.generateToken() + sdf.format(date);
//		String[] temp = request.getRequestURL().toString().split("/");
//		String webAddr = temp[0] + "//" + temp[2] + "/" + temp[3];
		String notifyUrl = null;
		if(webAddr != null){
			notifyUrl = webAddr + URL + token;
		}

		int classId = createFolder(path);
		if (classId == Integer.MIN_VALUE) {
			return FastJsonUtil.error("1", "文件夹创建失败");
		}

		JSONObject result = multipartUploadVodFile(file, fileName, DATASIZE, classId, notifyUrl, null, 1, 1, 0);

		if (result.getString("code").equals("0")) { // token用于缓存处理
			result.put("token", token);
		}
		return result;
	}

	/**
	 * @param file
	 *            视频文件路径名称，如"d:\\video.mp4"
	 * @param path
	 *            上传路径
	 * @param fileName
	 *            VOD上的文件名称
	 * @return
	 * @throws IOException
	 */
	public JSONObject upload2Cloud(String file, String path, String fileName) throws IOException {
		long fileSize = new File(file).length();
		if (fileSize > MAX_VIDEO_SIZE) {
			return FastJsonUtil.error("2", "文件大小超过" + (MAX_VIDEO_SIZE >> 16) + "M");
		}

		int classId = createFolder(path);// 文件夹id
		if (classId == Integer.MIN_VALUE) {
			return FastJsonUtil.error("1", "文件夹创建失败");
		}

		JSONObject result = multipartUploadVodFile(file, fileName, DATASIZE, classId, null, null, 1, 1, 0);

		return result;
	}
	// --------------------------------------------------------------------------------------

	/**
	 * 创建视频分类--用于管理视频文件，增加分类。该操作为全局操作，不涉及具体文件的分类关联。具体文件操作请参考ModifyVodInfo函数。
	 * 
	 * @param className
	 *            分类信息
	 * @param parentId
	 *            父分类的id号.parentId=Integer.MIN_VALUE时默认不选择
	 * 
	 * @return
	 */
	public JSONObject createClass(String className, int parentId) {
		try {
			TreeMap<String, Object> params = new TreeMap<String, Object>();
			params.put("className", className);
			if (parentId != Integer.MIN_VALUE) {
				params.put("parentId", parentId);
			}

			String result = getVod().call("CreateClass", params);
			MsLogger.debug(result.toString());
			JSONObject json_result = JSONObject.parseObject(result);
			return json_result;
		} catch (Exception e) {
			MsLogger.error(e);
			JSONObject resJson = JSONObject.parseObject("{}");
			resJson.put("code", -1);
			resJson.put("message", "处理过程中系统发生异常:" + VbUtility.printStackTrace(e));
			return resJson;
		}
	}

	/**
	 * 获取用户所有分类层级--获得当前用户所有的分类层级关系
	 * 
	 * @return
	 */
	public JSONObject describeAllClass() {
		try {
			TreeMap<String, Object> params = new TreeMap<String, Object>();

			String result = getVod().call("DescribeAllClass", params);
			MsLogger.debug(result.toString());
			JSONObject json_result = JSONObject.parseObject(result);
			return json_result;
		} catch (Exception e) {
			MsLogger.error(e);
			JSONObject resJson = JSONObject.parseObject("{}");
			resJson.put("code", -1);
			resJson.put("message", "处理过程中系统发生异常:" + VbUtility.printStackTrace(e));
			return resJson;
		}
	}

	/**
	 * 获取视频分类列表-- 获取全局分类列表，包括ID和分类描述的具体关系，和具体文件无关
	 * 
	 * @return
	 */
	public JSONObject describeClass() {
		try {
			TreeMap<String, Object> params = new TreeMap<String, Object>();

			String result = getVod().call("DescribeClass", params);
			MsLogger.debug(result.toString());
			JSONObject json_result = JSONObject.parseObject(result);
			return json_result;
		} catch (Exception e) {
			MsLogger.error(e);
			JSONObject resJson = JSONObject.parseObject("{}");
			resJson.put("code", -1);
			resJson.put("message", "处理过程中系统发生异常:" + VbUtility.printStackTrace(e));
			return resJson;
		}
	}

	/**
	 * 修改分类名
	 * 
	 * @param className
	 *            新的分类名
	 * @param classId
	 *            待修改的分类id
	 * @return
	 */
	public JSONObject modifyClass(String className, int classId) {
		try {
			TreeMap<String, Object> params = new TreeMap<String, Object>();
			params.put("className", className);
			params.put("classId", classId);

			String result = getVod().call("ModifyClass", params);
			MsLogger.debug(result.toString());
			JSONObject json_result = JSONObject.parseObject(result);
			return json_result;
		} catch (Exception e) {
			MsLogger.error(e);
			JSONObject resJson = JSONObject.parseObject("{}");
			resJson.put("code", -1);
			resJson.put("message", "处理过程中系统发生异常:" + VbUtility.printStackTrace(e));
			return resJson;
		}
	}

	/**
	 * 修改视频分类--修改视频文件对应分类
	 * 
	 * @param fileId
	 *            视频ID
	 * @param classId
	 *            分类ID
	 * @return
	 */
	public JSONObject modifyVodClass(String fileId, int classId) {
		try {
			TreeMap<String, Object> params = new TreeMap<String, Object>();
			params.put("fileId", fileId);
			params.put("classId", classId);
			String result = getVod().call("ModifyVodClass", params);
			MsLogger.debug(result.toString());
			JSONObject json_result = JSONObject.parseObject(result);
			return json_result;
		} catch (Exception e) {
			MsLogger.error(e);
			JSONObject resJson = JSONObject.parseObject("{}");
			resJson.put("code", -1);
			resJson.put("message", "处理过程中系统发生异常:" + VbUtility.printStackTrace(e));
			return resJson;
		}
	}

	/**
	 * 视频上传
	 * 
	 * @param file
	 *            视频文件
	 * @param fileName
	 *            视频文件名称,如"1234abcdefg.mp4"
	 * @param dataSize
	 *            上传的分片大小，单位字节Byte。建议不超过5MB
	 * @param classId
	 *            分类Id
	 * @param notifyUrl
	 *            转码回调地址,可以为null,只在isTranscode=1时有效
	 * @param tags
	 *            标签,可以为null
	 * @param isTranscode
	 *            是否转码0：否，1：是
	 * @param isScreenshot
	 *            是否截图0：否，1：是，只在isTranscode=1时有效
	 * @param isWatermark
	 *            是否打水印0：否，1：是，只在isTranscode=1时有效
	 * 
	 * @return
	 */
	public JSONObject multipartUploadVodFile(MultipartFile file, String fileName, int dataSize, int classId,
			String notifyUrl, String[] tags, int isTranscode, int isScreenshot, int isWatermark) {
		try {
			long fileSize = file.getSize(); // 视频文件的总大小，单位字节Byte
			String fileSHA1 = SHA1.streamToSHA(file.getInputStream()); // fileSha:视频文件的sha
			int lastIndex = fileName.lastIndexOf(".");
			String fileType = fileName.substring(lastIndex + 1); // 视频文件的类型，根据后缀区分
			String fileNm = fileName.substring(0, lastIndex);

			long remainderSize = fileSize; // 剩余待上传的视频大小
			int firstDataSize = 1024 * 10; // 第一次上传的切片字节数。
			int tmpDataSize = firstDataSize; // 记录本次上传的切片字节数
			int tmpOffset = 0; // 当前偏移量
			int code; // 上传结果返回的code。0：成功
			int flag; // 切片上传结束标志。1：结束 0：未结束
			String fileId; // 上传后获得的文件Id
			String result = null; // 上传结果
			int count = 0; // 重试上传的次数。设定为超过3次，则停止上传
			JSONObject data = JSONObject.parseObject("{}");

			if (remainderSize <= 0) {
				MsLogger.debug("wrong file path...");
				return FastJsonUtil.error("-2", "文件长度出错");
			}
			while (remainderSize > 0) {
				// 上传参数设置
				TreeMap<String, Object> params = new TreeMap<String, Object>();
				params.put("fileSha", fileSHA1);
				params.put("fileType", fileType);
				params.put("fileSize", fileSize);
				params.put("dataSize", tmpDataSize);
				params.put("offset", tmpOffset);
				params.put("fileName", fileNm);
				params.put("classId", classId);
				params.put("isTranscode", isTranscode);
				params.put("isScreenshot", isScreenshot);
				params.put("isWatermark", isWatermark);
				if (notifyUrl != null) {
					params.put("notifyUrl", notifyUrl);
				}
				if (tags != null && tags.length != 0) {
					for (int i = 0; i < tags.length; i++) {
						params.put("tags." + (i + 1), tags[i]);
					}
				}

				result = getVod().call("MultipartUploadVodFile", params, file);
				MsLogger.debug(result.toString());

				JSONObject json_result = JSONObject.parseObject(result);
				code = json_result.getInteger("code");
				if (code == -3002) { // 服务器异常返回，需要重试上传(offset=0,dataSize=10K,满足大多数视频的上传)
					tmpDataSize = firstDataSize;
					tmpOffset = 0;
					count++;
					if (count > 3) { // 重传超过3次，停止上传。每次重传从头开始
						throw new RuntimeException("code == -3002，腾讯云服务器异常");
					} else { //
						continue;
					}
					// 注：重新上传都是从最初位置开始，能否从当前上传位置重新上传？
					// 腾讯云没有给出code == -3002时支持的操作。只给出从最初位置开始上传的操作。
				} else if (code != 0) { // 上传失败。具体code查看API
					return json_result;
				}
				flag = json_result.getInteger("flag");
				if (flag == 1) { // 上传结束
					fileId = json_result.getString("fileId");
					data.put("vod_file_id", fileId);
					return FastJsonUtil.sucess("成功", data);
				} else { // 切片上传成功，但未结束。从返回结果中获得当前偏移量
					tmpOffset = Integer.parseInt(json_result.getString("offset"));
				}
				remainderSize = fileSize - tmpOffset;
				if (dataSize < remainderSize) {
					tmpDataSize = dataSize;
				} else {
					tmpDataSize = (int) remainderSize;
				}
			}
			return FastJsonUtil.error("-3", "未知错误");
		} catch (Exception e) {
			MsLogger.error(e);
			JSONObject resJson = JSONObject.parseObject("{}");
			resJson.put("code", -1);
			resJson.put("message", "处理过程中系统发生异常:" + VbUtility.printStackTrace(e));
			return resJson;
		}
	}

	/**
	 * 视频上传
	 * 
	 * @param file
	 *            视频文件路径名称，如"d:\\video.mp4"
	 * @param fileName
	 *            VOD中的视频文件名称,如"我要在VOD中存储的视频名称.mp4"
	 * @param dataSize
	 *            上传的分片大小，单位字节Byte。建议不超过5MB
	 * @param classId
	 *            分类Id
	 * @param notifyUrl
	 *            转码回调地址,可以为null,只在isTranscode=1时有效
	 * @param tags
	 *            标签,可以为null
	 * @param isTranscode
	 *            是否转码0：否，1：是
	 * @param isScreenshot
	 *            是否截图0：否，1：是，只在isTranscode=1时有效
	 * @param isWatermark
	 *            是否打水印0：否，1：是，只在isTranscode=1时有效
	 * 
	 * @return
	 */
	public JSONObject multipartUploadVodFile(String file, String fileName, int dataSize, int classId, String notifyUrl,
			String[] tags, int isTranscode, int isScreenshot, int isWatermark) {
		try {
			long fileSize = new File(file).length(); // 视频文件的总大小，单位字节Byte
			String fileSHA1 = SHA1.fileNameToSHA(file); // fileSha:视频文件的sha
			int lastIndex = fileName.lastIndexOf(".");
			String fileType = fileName.substring(lastIndex + 1); // 视频文件的类型，根据后缀区分
			String fileNm = fileName.substring(0, lastIndex);

			long remainderSize = fileSize; // 剩余待上传的视频大小
			int firstDataSize = 1024 * 10; // 第一次上传的切片字节数。
			int tmpDataSize = firstDataSize; // 记录本次上传的切片字节数
			int tmpOffset = 0; // 当前偏移量
			int code; // 上传结果返回的code。0：成功
			int flag; // 切片上传结束标志。1：结束 0：未结束
			String fileId; // 上传后获得的文件Id
			String result = null; // 上传结果
			int count = 0; // 重试上传的次数。设定为超过3次，则停止上传
			JSONObject data = JSONObject.parseObject("{}");

			if (remainderSize <= 0) {
				MsLogger.debug("wrong file path...");
				return FastJsonUtil.error("-2", "文件长度出错");
			}
			while (remainderSize > 0) {
				// 上传参数设置
				TreeMap<String, Object> params = new TreeMap<String, Object>();
				params.put("file", file);
				params.put("fileSha", fileSHA1);
				params.put("fileType", fileType);
				params.put("fileSize", fileSize);
				params.put("dataSize", tmpDataSize);
				params.put("offset", tmpOffset);
				params.put("fileName", fileNm);
				params.put("classId", classId);
				params.put("isTranscode", isTranscode);
				params.put("isScreenshot", isScreenshot);
				params.put("isWatermark", isWatermark);
				if (notifyUrl != null) {
					params.put("notifyUrl", notifyUrl);
				}
				if (tags != null && tags.length != 0) {
					for (int i = 0; i < tags.length; i++) {
						params.put("tags." + (i + 1), tags[i]);
					}
				}

				result = getVod().call("MultipartUploadVodFile2", params);
				MsLogger.debug(result.toString());

				JSONObject json_result = JSONObject.parseObject(result);
				code = json_result.getInteger("code");
				if (code == -3002) { // 服务器异常返回，需要重试上传(offset=0,dataSize=10K,满足大多数视频的上传)
					tmpDataSize = firstDataSize;
					tmpOffset = 0;
					count++;
					if (count > 3) { // 重传超过3次，停止上传。每次重传从头开始
						throw new RuntimeException("code == -3002，腾讯云服务器异常");
					} else { //
						continue;
					}
					// 注：重新上传都是从最初位置开始，能否从当前上传位置重新上传？
					// 腾讯云没有给出code == -3002时支持的操作。只给出从最初位置开始上传的操作。
				} else if (code != 0) { // 上传失败。具体code查看API
					return json_result;
				}
				flag = json_result.getInteger("flag");
				if (flag == 1) { // 上传结束
					fileId = json_result.getString("fileId");
					data.put("vod_file_id", fileId);
					return FastJsonUtil.sucess("成功", data);
				} else { // 切片上传成功，但未结束。从返回结果中获得当前偏移量
					tmpOffset = Integer.parseInt(json_result.getString("offset"));
				}
				remainderSize = fileSize - tmpOffset;
				if (dataSize < remainderSize) {
					tmpDataSize = dataSize;
				} else {
					tmpDataSize = (int) remainderSize;
				}
			}
			return FastJsonUtil.error("-3", "未知错误");
		} catch (Exception e) {
			MsLogger.error(e);
			JSONObject resJson = JSONObject.parseObject("{}");
			resJson.put("code", -1);
			resJson.put("message", "处理过程中系统发生异常:" + VbUtility.printStackTrace(e));
			return resJson;
		}
	}

	/**
	 * URL拉取视频上传--通过用户传递的URL，从已有的资源库批量拉取视频文件到腾讯云。此接口可以批量拉取多个视频文件，通过输入参数中的 n
	 * 值区分是第几个视频。
	 * 
	 * @param url
	 *            需要拉取的视频URL
	 * @param fileName
	 *            视频文件的名称
	 * @param fileMd5
	 *            视频文件的MD5,可以为null
	 * @param isTranscode
	 *            是否转码，0：否，1：是，默认为0,可以为null
	 * @param isScreenshot
	 *            是否截图，0：否，1：是，默认为0,可以为null
	 * @param isWatermark
	 *            是否打水印，0：否，1：是，默认为0,可以为null
	 * @param notifyUrl
	 *            腾讯云通过回调该URL地址通知；调用方该视频已经拉取完毕。,可以为null
	 * @param classId
	 *            视频的分类ID,可以为null
	 * @param tags
	 *            视频的标签,可以为null
	 * @param priority
	 *            优先级0:中 1：高 2：低,可以为null
	 * @param isReport
	 *            回调开关，是否需要回包给开发商，0：否，1：是，默认为1,可以为null
	 * @return
	 */
	public JSONObject multiPullVodFile(String[] url, String[] fileName, String[] fileMd5, int[] isTranscode,
			int[] isScreenshot, int[] isWatermark, String[] notifyUrl, int[] classId, String tags[], int[] priority,
			int[] isReport) {

		TreeMap<String, Object> params = new TreeMap<String, Object>();
		try {
			if (url == null || url.length == 0) {
				throw new RuntimeException("url参数有误");
			}
			if (fileName == null || fileName.length == 0) {
				throw new RuntimeException("fileName参数有误");
			}
			int len = fileName.length;
			for (int i = 0; i < fileName.length; i++) {
				params.put("pullset." + (i + 1) + ".url", url[i]);
			}

			if (len != fileName.length) {
				throw new RuntimeException("fileName数组长度不一致");
			}
			for (int i = 0; i < fileName.length; i++) {
				params.put("pullset." + (i + 1) + ".fileName", fileName[i]);
			}

			if (fileMd5 != null) {
				if (len != fileMd5.length) {
					throw new RuntimeException("fileMd5数组长度不一致");
				}
				for (int i = 0; i < fileMd5.length; i++) {
					params.put("pullset." + (i + 1) + ".fileMd5", fileMd5[i]);
				}
			}

			if (isTranscode != null) {
				if (len != isTranscode.length) {
					throw new RuntimeException("isTranscode数组长度不一致");
				}
				for (int i = 0; i < isTranscode.length; i++) {
					params.put("pullset." + (i + 1) + ".isTranscode", isTranscode[i]);
				}
			}

			if (isScreenshot != null) {
				if (len != isScreenshot.length) {
					throw new RuntimeException("isScreenshot数组长度不一致");
				}
				for (int i = 0; i < isScreenshot.length; i++) {
					params.put("pullset." + (i + 1) + ".isScreenshot", isScreenshot[i]);
				}
			}

			if (isWatermark != null) {
				if (len != isWatermark.length) {
					throw new RuntimeException("isWatermark数组长度不一致");
				}
				for (int i = 0; i < isWatermark.length; i++) {
					params.put("pullset." + (i + 1) + ".isWatermark", isWatermark[i]);
				}
			}

			if (notifyUrl != null) {
				if (len != notifyUrl.length) {
					throw new RuntimeException("notifyUrl数组长度不一致");
				}
				for (int i = 0; i < notifyUrl.length; i++) {
					params.put("pullset." + (i + 1) + ".notifyUrl", notifyUrl[i]);
				}
			}

			if (classId != null) {
				if (len != classId.length) {
					throw new RuntimeException("classId数组长度不一致");
				}
				for (int i = 0; i < classId.length; i++) {
					params.put("pullset." + (i + 1) + ".classId", classId[i]);
				}
			}

			if (tags != null) {
				if (len != tags.length) {
					throw new RuntimeException("tags数组长度不一致");
				}
				for (int i = 0; i < tags.length; i++) {
					params.put("pullset." + (i + 1) + ".tags", tags[i]);
				}
			}

			if (priority != null) {
				if (len != priority.length) {
					throw new RuntimeException("priority数组长度不一致");
				}
				for (int i = 0; i < priority.length; i++) {
					params.put("pullset." + (i + 1) + ".priority", priority[i]);
				}
			}

			if (isReport != null) {
				if (len != isReport.length) {
					throw new RuntimeException("isReport数组长度不一致");
				}
				for (int i = 0; i < isReport.length; i++) {
					params.put("pullset." + (i + 1) + ".isReport", isReport[i]);
				}
			}

			String result = getVod().call("MultiPullVodFile", params);
			MsLogger.debug(result.toString());
			JSONObject json_result = JSONObject.parseObject(result);
			return json_result;
		} catch (Exception e) {
			MsLogger.error(e);
			params.clear();
			JSONObject resJson = JSONObject.parseObject("{}");
			resJson.put("code", -1);
			resJson.put("message", "处理过程中系统发生异常:" + VbUtility.printStackTrace(e));
			return resJson;
		}
	}

	/**
	 * 获取视频信息列表--获取视频信息，可以根据视频ID、时间段或者状态等获得视频信息列表。
	 * 
	 * @param fileIds
	 *            视频ID列表，暂时不支持批量
	 * @param from
	 *            开始时间,允许为null
	 * @param to
	 *            结束时间,允许为null
	 * @param classId
	 *            视频分类ID，过滤使用。Integer.MIN_VALUE时默认不选择
	 * @param status
	 *            视频状态，过滤使用。Integer.MIN_VALUE时默认不选择
	 * @param orderby
	 *            结果排序，默认按时间降序。Integer.MIN_VALUE时默认不选择
	 * @param pageNo
	 *            分页页号。Integer.MIN_VALUE时默认不选择
	 * @param pageSize
	 *            分页大小，范围在10-100之间。Integer.MIN_VALUE时默认不选择
	 * @return
	 */
	public JSONObject describeVodInfo(String[] fileIds, String from, String to, int classId, int status, int orderby,
			int pageNo, int pageSize) {
		try {
			TreeMap<String, Object> params = new TreeMap<String, Object>();
			if (classId != Integer.MIN_VALUE) {
				params.put("classId", classId);
			}
			if (status != Integer.MIN_VALUE) {
				params.put("status", status);
			}
			if (orderby != Integer.MIN_VALUE) {
				params.put("orderby", orderby);
			}
			if (pageNo != Integer.MIN_VALUE) {
				params.put("pageNo", pageNo);
			}
			if (pageSize != Integer.MIN_VALUE) {
				params.put("pageSize", pageSize);
			}
			if (fileIds != null && fileIds.length != 0) {
				for (int i = 0; i < fileIds.length; i++) {
					params.put("fileIds." + (i + 1), fileIds[i]);
				}
			}
			if (from != null) {
				params.put("from", from);
			}
			if (to != null) {
				params.put("to", to);
			}

			String result = getVod().call("DescribeVodInfo", params);
			MsLogger.debug(result.toString());
			JSONObject json_result = JSONObject.parseObject(result);
			return json_result;
		} catch (Exception e) {
			MsLogger.error(e);
			JSONObject resJson = JSONObject.parseObject("{}");
			resJson.put("code", -1);
			resJson.put("message", "处理过程中系统发生异常:" + VbUtility.printStackTrace(e));
			return resJson;
		}

	}

	/**
	 * 获取视频播放信息列表--获取视频信息，可以根据视频文件名获得视频信息列表。
	 * 
	 * @param fileName
	 *            视频名称（前缀匹配）
	 * @param pageNo
	 *            页号。Integer.MIN_VALUE时默认不选择
	 * @param pageSize
	 *            分页大小。Integer.MIN_VALUE时默认不选择
	 * @return
	 */
	public JSONObject describeVodPlayInfo(String fileName, int pageNo, int pageSize) {
		try {
			TreeMap<String, Object> params = new TreeMap<String, Object>();
			if (pageNo != Integer.MIN_VALUE) {
				params.put("pageNo", pageNo);
			}
			if (pageSize != Integer.MIN_VALUE) {
				params.put("pageSize", pageSize);
			}
			params.put("fileName", fileName);

			String result = getVod().call("DescribeVodPlayInfo", params);
			MsLogger.debug(result.toString());
			JSONObject json_result = JSONObject.parseObject(result);
			return json_result;
		} catch (Exception e) {
			MsLogger.error(e);
			JSONObject resJson = JSONObject.parseObject("{}");
			resJson.put("code", -1);
			resJson.put("message", "处理过程中系统发生异常:" + VbUtility.printStackTrace(e));
			return resJson;
		}
	}

	/**
	 * 获取视频详细信息--获取当前视频所有播放地址、格式、码率、高度、宽度信息
	 * 
	 * @param fileId
	 *            视频ID
	 * @return
	 */
	public JSONObject describeVodPlayUrls(String fileId) {
		try {
			TreeMap<String, Object> params = new TreeMap<String, Object>();
			params.put("fileId", fileId);

			String result = getVod().call("DescribeVodPlayUrls", params);
			MsLogger.debug(result.toString());
			JSONObject json_result = JSONObject.parseObject(result);
			return json_result;
		} catch (Exception e) {
			MsLogger.error(e);
			JSONObject resJson = JSONObject.parseObject("{}");
			resJson.put("code", -1);
			resJson.put("message", "处理过程中系统发生异常:" + VbUtility.printStackTrace(e));
			return resJson;
		}
	}

	/**
	 * 修改视频信息--修改对应视频文件的描述信息，包括分类、名称、描述等
	 * 
	 * @param fileId
	 *            文件id
	 * @param className
	 *            分类信息,允许为null
	 * @param fileName
	 *            文件名称,允许为null
	 * @param fileIntro
	 *            文件描述,允许为null
	 * @param classId
	 *            分类id.Integer.MIN_VALUE时默认不选择
	 * @return
	 */
	public JSONObject modifyVodInfo(String fileId, String className, String fileName, String fileIntro, int classId) {
		try {
			TreeMap<String, Object> params = new TreeMap<String, Object>();
			if (classId != Integer.MIN_VALUE) {
				params.put("classId", classId);
			}
			if (className != null) {
				params.put("className", Integer.parseInt(className));
			}
			if (fileName != null) {
				params.put("fileName", Integer.parseInt(fileName));
			}
			if (fileIntro != null) {
				params.put("fileIntro", Integer.parseInt(fileIntro));
			}
			params.put("fileId", fileId);

			String result = getVod().call("ModifyVodInfo", params);
			MsLogger.debug(result.toString());
			JSONObject json_result = JSONObject.parseObject(result);
			return json_result;
		} catch (Exception e) {
			MsLogger.error(e);
			JSONObject resJson = JSONObject.parseObject("{}");
			resJson.put("code", -1);
			resJson.put("message", "处理过程中系统发生异常:" + VbUtility.printStackTrace(e));
			return resJson;
		}
	}

	/**
	 * 修改视频发布状态--视频暂停或者恢复播放
	 * 
	 * @param fileId
	 *            文件Id
	 * @param playStatus
	 *            视频播放状态，0为暂停，1为恢复
	 * @return
	 */
	public JSONObject setVodPlayStatus(String fileId, int playStatus) {
		try {
			TreeMap<String, Object> params = new TreeMap<String, Object>();
			params.put("classId", playStatus);
			params.put("fileId", fileId);

			String result = getVod().call("SetVodPlayStatus", params);
			MsLogger.debug(result.toString());
			JSONObject json_result = JSONObject.parseObject(result);
			return json_result;
		} catch (Exception e) {
			MsLogger.error(e);
			JSONObject resJson = JSONObject.parseObject("{}");
			resJson.put("code", -1);
			resJson.put("message", "处理过程中系统发生异常:" + VbUtility.printStackTrace(e));
			return resJson;
		}

	}

	/**
	 * 批量修改视频发布和分发状态--用于修改文件的发布状态，以及是否下发至CDN的状态 //注:pullset.n.fileId中n以0开始
	 * 
	 * @param fileId
	 *            文件id
	 * @param playStatus
	 *            文件状态，0为暂停，1为恢复
	 * @param isPushCDN
	 *            是否发布cdn，0不发布，1发布
	 * @return
	 */
	public JSONObject multiSetVodPlayStatus(String[] fileId, int[] playStatus, int[] isPushCDN) {
		try {
			if (fileId == null || fileId.length == 0) {
				throw new RuntimeException("fileId参数错误");
			}
			if (playStatus == null || playStatus.length == 0) {
				throw new RuntimeException("playStatus参数错误");
			}
			if (isPushCDN == null || isPushCDN.length == 0) {
				throw new RuntimeException("isPushCDN参数错误");
			}
			if (fileId.length != playStatus.length || fileId.length != isPushCDN.length) {
				throw new RuntimeException("参数长度不一致");
			}
			TreeMap<String, Object> params = new TreeMap<String, Object>();
			for (int i = 0; i < fileId.length; i++) {
				params.put("pullset." + i + ".fileId", fileId[i]);
				params.put("pullset." + i + ".playStatus", playStatus[i]);
				params.put("pullset." + i + ".isPushCDN", isPushCDN[i]);
			}

			String result = getVod().call("MultiSetVodPlayStatus", params);
			MsLogger.debug(result.toString());
			JSONObject json_result = JSONObject.parseObject(result);
			return json_result;
		} catch (Exception e) {
			MsLogger.error(e);
			JSONObject resJson = JSONObject.parseObject("{}");
			resJson.put("code", -1);
			resJson.put("message", "处理过程中系统发生异常:" + VbUtility.printStackTrace(e));
			return resJson;
		}
	}

	/**
	 * 批量获取视频截图地址--针对具体文件，获取其不同尺寸下多张截图URL地址。文件按照id顺序排列，分别对应时间轴0%位置、10%位置，20%位置，
	 * 至90%位置的截图。 //注:pullset.n.fileId中n以0开始
	 * 
	 * @param fileId
	 *            文件id
	 * @return
	 */
	public JSONObject createScreenShot(String[] fileId) {
		try {
			if (fileId == null || fileId.length == 0) {
				throw new RuntimeException("fileId参数错误");
			}
			TreeMap<String, Object> params = new TreeMap<String, Object>();
			for (int i = 0; i < fileId.length; i++) {
				params.put("pullset." + i + ".fileId", fileId[i]);
			}

			String result = getVod().call("CreateScreenShot", params);
			MsLogger.debug(result.toString());
			JSONObject json_result = JSONObject.parseObject(result);
			return json_result;
		} catch (Exception e) {
			MsLogger.error(e);
			JSONObject resJson = JSONObject.parseObject("{}");
			resJson.put("code", -1);
			resJson.put("message", "处理过程中系统发生异常:" + VbUtility.printStackTrace(e));
			return resJson;
		}
	}

	/**
	 * 获取播放器时间轴批量缩略图--用于获取用于播放器时间轴上的缩略图。指定大小后按照以10秒/张的频率按照指定高宽截取。截取后的图片，
	 * 将按照每100张组成为一张大图片的方式生成数个固定格式地址，用户可在操作后按照固定格式依次获取。 注意：该API开放为特定套餐用户可用。
	 * 
	 * @param fileId
	 *            文件id
	 * @param Width
	 *            截图的宽度（1-300）
	 * @param Height
	 *            截图的高度（1-300）
	 * @return
	 */
	public JSONObject describeScreenShot(int fileId, int Width, int Height) {
		try {
			TreeMap<String, Object> params = new TreeMap<String, Object>();
			params.put("fileId", fileId);
			params.put("Width", Width);
			params.put("Height", Height);

			String result = getVod().call("DescribeScreenShot", params);
			MsLogger.debug(result.toString());
			JSONObject json_result = JSONObject.parseObject(result);
			return json_result;
		} catch (Exception e) {
			MsLogger.error(e);
			JSONObject resJson = JSONObject.parseObject("{}");
			resJson.put("code", -1);
			resJson.put("message", "处理过程中系统发生异常:" + VbUtility.printStackTrace(e));
			return resJson;
		}
	}

	/**
	 * 为视频设置显示封面
	 * 
	 * @param fileId
	 *            视频文件的id
	 * @param type
	 *            封面设置方法（1：使用截图url, 非1：上传本地图片 ）
	 * @param paratype
	 *            值为1：para就是截图url，type非1值 ，para就是本地图片的本机路径）
	 * @param imageData
	 *            若type其他值，表示用本地图片作为封面，则本参数表示该图片的base64字符串数据（ 只有在type值不为1时才有效)
	 * @return
	 */
	public JSONObject describeVodCover(int fileId, int type, String para, String imageData) {
		try {
			TreeMap<String, Object> params = new TreeMap<String, Object>();
			params.put("fileId", fileId);
			params.put("type", type);
			params.put("para", para);
			if (imageData != null) {
				params.put("imageData", imageData);
			}
			String result = getVod().call("DescribeVodCover", params);
			MsLogger.debug(result.toString());
			JSONObject json_result = JSONObject.parseObject(result);
			return json_result;
		} catch (Exception e) {
			MsLogger.error(e);
			JSONObject resJson = JSONObject.parseObject("{}");
			resJson.put("code", -1);
			resJson.put("message", "处理过程中系统发生异常:" + VbUtility.printStackTrace(e));
			return resJson;
		}
	}

	/**
	 * 对视频文件转码--用于对已经上传的视频进行转码和添加水印。转码的具体配置和水印配置按照管理控制台的配置参数进行
	 * 
	 * @param fileId
	 *            视频文件id
	 * @param isScreenshot
	 *            是否截图，0不需要，1需要
	 * @param isWatermark
	 *            是否添加水印，0不需要，1需要
	 * @param notifyUrl
	 *            转码结果回调地址,允许为null
	 * @return
	 */
	public JSONObject convertVodFile(String fileId, int isScreenshot, int isWatermark, String notifyUrl) {
		try {
			TreeMap<String, Object> params = new TreeMap<String, Object>();
			params.put("fileId", fileId);
			params.put("isScreenshot", isScreenshot);
			params.put("isWatermark", isWatermark);
			if (notifyUrl != null) {
				params.put("notifyUrl", notifyUrl);
			}

			String result = getVod().call("ConvertVodFile", params);
			MsLogger.debug(result.toString());
			JSONObject json_result = JSONObject.parseObject(result);
			return json_result;
		} catch (Exception e) {
			MsLogger.error(e);
			JSONObject resJson = JSONObject.parseObject("{}");
			resJson.put("code", -1);
			resJson.put("message", "处理过程中系统发生异常:" + VbUtility.printStackTrace(e));
			return resJson;
		}
	}

	/**
	 * 批量获取转码时产生的截图
	 * 
	 * @param fileId
	 *            文件id
	 * @return
	 */
	public JSONObject describeAutoScreenShot(String fileId) {
		try {
			TreeMap<String, Object> params = new TreeMap<String, Object>();
			params.put("fileId", fileId);

			String result = getVod().call("DescribeAutoScreenShot", params);
			MsLogger.debug(result.toString());
			JSONObject json_result = JSONObject.parseObject(result);
			return json_result;
		} catch (Exception e) {
			MsLogger.error(e);
			JSONObject resJson = JSONObject.parseObject("{}");
			resJson.put("code", -1);
			resJson.put("message", "处理过程中系统发生异常:" + VbUtility.printStackTrace(e));
			return resJson;
		}
	}

	/**
	 * 批量增加视频的标签
	 * 
	 * @param fileId
	 *            文件id
	 * @param tags
	 *            标签组，不能为null
	 * @return
	 */
	public JSONObject createVodTags(String fileId, String[] tags) {
		try {
			if (tags == null || tags.length == 0) {
				throw new RuntimeException("tags参数错误");
			}
			TreeMap<String, Object> params = new TreeMap<String, Object>();
			params.put("fileId", fileId);
			for (int i = 0; i < tags.length; i++) {
				params.put("tags." + (i + 1), tags[i]);
			}

			String result = getVod().call("CreateVodTags", params);
			MsLogger.debug(result.toString());
			JSONObject json_result = JSONObject.parseObject(result);
			return json_result;
		} catch (Exception e) {
			MsLogger.error(e);
			JSONObject resJson = JSONObject.parseObject("{}");
			resJson.put("code", -1);
			resJson.put("message", "处理过程中系统发生异常:" + VbUtility.printStackTrace(e));
			return resJson;
		}
	}

	/**
	 * 批量删除视频标签
	 * 
	 * @param fileId
	 *            文件id
	 * @param tags
	 *            标签组，不能为null
	 * @return
	 */
	public JSONObject deleteVodTags(String fileId, String[] tags) {
		try {
			if (tags == null || tags.length == 0) {
				throw new RuntimeException("tags参数错误");
			}
			TreeMap<String, Object> params = new TreeMap<String, Object>();
			params.put("fileId", fileId);
			for (int i = 0; i < tags.length; i++) {
				params.put("tags." + (i + 1), tags[i]);
			}

			String result = getVod().call("DeleteVodTags", params);
			MsLogger.debug(result.toString());
			JSONObject json_result = JSONObject.parseObject(result);
			return json_result;
		} catch (Exception e) {
			MsLogger.error(e);
			JSONObject resJson = JSONObject.parseObject("{}");
			resJson.put("code", -1);
			resJson.put("message", "处理过程中系统发生异常:" + VbUtility.printStackTrace(e));
			return resJson;
		}
	}

	/**
	 * 用于删除已经上传的视频文件
	 * 
	 * @param fileId
	 *            文件id
	 * @param priority
	 *            优先级0:中 1：高 2：低
	 * @return
	 */
	public JSONObject deleteVodFile(String fileId, int priority) {
		try {
			TreeMap<String, Object> params = new TreeMap<String, Object>();
			params.put("fileId", fileId);
			params.put("fileId", priority);

			String result = getVod().call("DeleteVodFile", params);
			MsLogger.debug(result.toString());
			JSONObject json_result = JSONObject.parseObject(result);
			return json_result;
		} catch (Exception e) {
			MsLogger.error(e);
			JSONObject resJson = JSONObject.parseObject("{}");
			resJson.put("code", -1);
			resJson.put("message", "处理过程中系统发生异常:" + VbUtility.printStackTrace(e));
			return resJson;
		}
	}

	/**
	 * 获取录播视频播放信息-互动直播用户专用--该API为互动直播用户专用。用于帮助互动直播用户获取视频信息，可以根据视频文件名获得视频信息列表。
	 * 
	 * @param vid
	 *            互动直播录制返回的文件ID
	 * @param notifyUrl
	 *            回调地址，用于回报文件录制完成，转码是否完成。需要在相应动作完成之前传入该参数，则会回调相应内容，若在完成之后，
	 *            则没有回调内容。可以为null
	 * @return
	 */
	public JSONObject describeRecordPlayInfo(String vid, String notifyUrl) {
		try {
			TreeMap<String, Object> params = new TreeMap<String, Object>();
			params.put("vid", vid);
			if (notifyUrl != null) {
				params.put("notifyUrl", notifyUrl);
			}

			String result = getVod().call("DescribeRecordPlayInfo", params);
			MsLogger.debug(result.toString());
			JSONObject json_result = JSONObject.parseObject(result);
			return json_result;
		} catch (Exception e) {
			MsLogger.error(e);
			JSONObject resJson = JSONObject.parseObject("{}");
			resJson.put("code", -1);
			resJson.put("message", "处理过程中系统发生异常:" + VbUtility.printStackTrace(e));
			return resJson;
		}
	}
	/*
	 * public int createFolder(String path) { if (path == null) { return
	 * Integer.MIN_VALUE; } // a//b/c////d --> a/b/c/d String[] dirs =
	 * path.split("/"); StringBuffer bu = new StringBuffer(); for (int m = 0; m
	 * < dirs.length; m++) { if (dirs[m].length() == 0) { continue; }
	 * bu.append("/" + dirs[m]); } if (bu.toString().length() == 0) { return -1;
	 * } String path2 = bu.toString().substring(1); // path2 = "a/b/c/d"
	 * 
	 * int classId = getClassIdByPath(path2); // 存在当前路径,返回路径最后一个文件夹的id if
	 * (classId != Integer.MIN_VALUE) { return classId; } // 不存在，回退到路径上一个结点。 int
	 * lastIndex = path2.lastIndexOf("/"); // 上一个结点为根结点，在根结点下创建文件夹( folder: a )
	 * if (lastIndex == -1) { JSONObject json = vodWrapper.createClass(path2,
	 * -1); if (json.getInteger("code") != 0) { return Integer.MIN_VALUE; } int
	 * newClassId = json.getInteger("newClassId");// 获得创建文件夹的id return newClassId; }
	 * 
	 * // 上一个结点为非根结点，则获得上一个结点的classId，并在此基础上创建文件夹 String path3 =
	 * path2.substring(0, lastIndex); // path = "a/b/c" classId =
	 * createFolder(path3); if (classId == Integer.MIN_VALUE) { return
	 * Integer.MIN_VALUE; }
	 * 
	 * String newFolder = path2.substring(lastIndex + 1); JSONObject json2 =
	 * vodWrapper.createClass(newFolder, classId); if (json2.getInteger("code") !=
	 * 0) { return Integer.MIN_VALUE; } int newClassId =
	 * json2.getInteger("newClassId");// 获得创建文件夹的id return newClassId; }
	 * 
	 * public static void main(String[] args) { String path = "/a";//
	 * luna/dev/poi/a // int classId =
	 * VODUtil.getInstance().getClassIdByPath(path); int classId =
	 * VODUtil.getInstance().createFolder(path); MsLogger.debug(classId);
	 * 
	 * }
	 */
	public static void main(String[] args) {
		String file = "D:\\yingpin.mp3";
		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String path = VODUtil.getVODPoiVideoFolderPath() + "/" + date;
		String ext = ".mp3";
		String fileName = VbMD5.generateToken() + ext;
		try {
			VODUtil.getInstance().upload2Cloud(file, path, fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			MsLogger.error(e);
		}
	}
}
