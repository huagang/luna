package ms.luna.biz.util;

import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

public class COSUtil {
	public static final long 图片上传大小分界线1M = 1024*1024;

	private static final int APP_ID = 10002033;
	private static final String SECRET_ID = "AKIDLx9DqZut6Gp8fdrw5g1X3DX4K7stFdZp";
	private static final String SECRET_KEY = "XmJgCf7xYjNhKbDkS6PQKL9Q50fAhBaL";

	/**
	 * 所有的资源路径格式规范：带前导"/"，不带结尾"/"，避免重复或者缺少分割符
	 */
	public static final String LUNA_BUCKET = "luna";
	public static final String LUNA_VIEW_ROOT = "/view";
	public static final String LUNA_H5_ROOT = "/h5";
	public static final String LUNA_IMG_ROOT = "/img";
	public static final String LUNA_CRM_ROOT = "/crm";
	public static final String LUNA_POI = "/poi";
	public static final String LUNA_PIC = "/pic";
	public static final String LUNA_AUDIO = "/audio";
	public static final String LUNA_VIDEO = "/video";
	public static final String LUNA_STATIC_RESOURCE_DOMAIN = "http://view.luna.visualbusiness.cn";
	public static final String COS_DOMAIN = "http://luna-10002033.file.myqcloud.com";
	
	public static final String ACCESS_URL = "access_url";
	private static COSWrapper cosWrapper = COSWrapper.getInstance(APP_ID, SECRET_ID, SECRET_KEY);
	private static COSUtil cosHelper = new COSUtil();

	/**
	 * 在ApplicationListener中初始化其真实值（开发环境、生产环境对应值不同）
	 */
	public static String cosBaseDir = "";

	/**
	 * 图片地址
	 */
	public static String picAddress = "/uploadfiles";
	
	/**
	 * 音频地址
	 */
	public static String audioAddress = "/uploadfiles";
	
	
	public static String getPicAddress() {
		
		return cosBaseDir + picAddress;
	}
	
	public static String getAudioAddress () {
		return cosBaseDir + audioAddress;
	}
	
	/**
	 * 
	 * @return 公司绑定的外部访问h5地址，上传后通过替换默认路径获得此地址
	 */
	public static String getLunaH5Root() {
		return LUNA_STATIC_RESOURCE_DOMAIN + cosBaseDir + LUNA_H5_ROOT;
	}
	
	public static String getLunaViewRoot() {
		return LUNA_STATIC_RESOURCE_DOMAIN + cosBaseDir + LUNA_VIEW_ROOT;
	}
	
	/**
	 * 
	 * @return cos默认分配的外部访问h5地址
	 */
	public static String getCosH5Root() {
		return COS_DOMAIN + cosBaseDir + LUNA_H5_ROOT;
	}
	
	public static String getLunaImgRoot() {
		return LUNA_STATIC_RESOURCE_DOMAIN + cosBaseDir + LUNA_IMG_ROOT;
	}
	
	/**
	 * 
	 * @return CRM商户上传图片存储地址
	 */
	public static String getLunaCRMRoot(){
		return cosBaseDir + LUNA_CRM_ROOT;
		}
	/**
	 * 
	 * @return cos上luna h5的路径
	 */
	public static String getLunaH5RootPath() {
		return cosBaseDir + LUNA_H5_ROOT;
	}
	
	public static String getLunaViewRootPath() {
		return cosBaseDir + LUNA_VIEW_ROOT;
	}
	
	public static String getLunaImgRootPath() {
		return cosBaseDir + LUNA_IMG_ROOT;
	}

	public static String getCosPoiPicFolderPath() {
		return cosBaseDir + LUNA_POI + LUNA_PIC;
	}
	public static String getCosPoiAudioFolderPath() {
		return cosBaseDir + LUNA_POI + LUNA_AUDIO;
	}
	public static String getCosPoiVideoFolderPath() {
		return cosBaseDir + LUNA_POI + LUNA_VIDEO;
	}

	private COSUtil() {
		if (cosWrapper == null) {
			cosWrapper = COSWrapper.getInstance(APP_ID, SECRET_ID, SECRET_KEY);
		}
	}

	public static COSUtil getInstance() {
		if (cosHelper == null) {
			cosHelper = new COSUtil();
		}
		return cosHelper;
	}

	/**
	 * 
	 * @param appid
	 * @param folder
	 * @return
	 * @throws Exception 
	 */
	public boolean createFolder(String appid, String folder) throws Exception {
		return cosWrapper.createFolder(LUNA_BUCKET, LUNA_H5_ROOT + "/" + appid + "/" + folder);
	}
	
	public JSONObject upload2CloudDirect(byte[] bytes, String destPath, String fileName) throws Exception {
		return cosWrapper.upload2CloudDirect(bytes, LUNA_BUCKET, destPath, fileName);
	}
	public JSONObject upload2CloudDirect(MultipartFile file, String dst_folder) throws Exception {
		return cosWrapper.upload2CloudDirect(file, LUNA_BUCKET, dst_folder);
	}
	

	/**
	 * 
	 * @param appid
	 * @param srcFile
	 * @param dst_folder
	 * @param fileName
	 * @return
	 */
	public JSONObject upload2Cloud(String localFile, String bucket, String dst_folder, String fileName) throws Exception {
		return cosWrapper.upload2Cloud(localFile, bucket, dst_folder, fileName);
	}

	/**
	 * 上传图片
	 * @param file
	 * @param dst_folder
	 * @return
	 * @throws Exception
	 */
	public JSONObject upload2Cloud(MultipartFile file, String dst_folder) throws Exception {
		return cosWrapper.upload2Cloud(file, LUNA_BUCKET, dst_folder);
	}

	/**
	 * 保存本地图片或者html
	 * @param srcHtml
	 * @param localFolder
	 * @param ext
	 * @return
	 * @throws Exception
	 */
	public String save2LocalFile(byte[] bytes,  String localFolder, String ext) throws Exception {
		return cosWrapper.save2LocalFile(bytes, localFolder, ext);
	}

	public JSONObject uploadLocalFile2Cloud(String bucket, byte[] bytes, String localServerTempPath, String remotePath) {
		return cosWrapper.uploadFile2Cloud(bytes, localServerTempPath, bucket, remotePath);
	}
}
