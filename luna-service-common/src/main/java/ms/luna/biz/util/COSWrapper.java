package ms.luna.biz.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.qcloud.cosapi.api.CosCloud;
import com.qcloud.cosapi.api.CosCloud.FolderPattern;

import ms.luna.biz.util.VbMD5;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class COSWrapper {

	private final static Logger logger  = Logger.getLogger(COSWrapper.class);
	private static String PERMITTED_FILE_TYPES = "(\\.png|\\.bmp|\\.jpg|\\.tiff|\\.gif|\\.pcx|\\.tga|\\.exif|\\.fpx|\\.svg|\\.psd|\\.cdr|\\.pcd|\\.dxf|\\.ufo|\\.eps|\\.ai|\\.raw"
			+ "|\\.mp3|\\.wav|\\.wma|\\.ogg|\\.ape|\\.acc"
			+ "|\\.rm|\\.rmvb|\\.avi|\\.mp4|\\.3gp)";
	

	private static String LOCAL_BUCKET = "/data1/luna/";

	private static COSWrapper cosWrapper = null;
	public static synchronized COSWrapper getInstance(int app_id, String secret_id, String secret_key) {
		if (cosWrapper == null) {
			cosWrapper = new COSWrapper(app_id, secret_id, secret_key);
		}
		return cosWrapper;
	}
	private CosCloud COS = null;

	private COSWrapper(int app_id, String secret_id, String secret_key) {
		if (COS == null) {
			COS = new CosCloud(app_id, secret_id, secret_key);
		}
	}
	
	
	public JSONObject upload2CloudDirect(byte[] bytes, String bucket, String destPath, String fileName) throws Exception {
		
		JSONObject result = null;
		JSONObject data = JSONObject.parseObject("{}");
		
		if(bytes.length > COSUtil.图片上传大小分界线1M) {
			return FastJsonUtil.error(-1, "文件大小超过1M");
		}
		if(createFolder(bucket, destPath)) {
			String fileFullPath = destPath + "/" + fileName;
			String fileState = COS.getFileStat(bucket, fileFullPath);
			result = JSONObject.parseObject(fileState);
			if(result.getString("code").equals("0")) {
				fileState = COS.deleteFile(bucket, fileFullPath);
				result = JSONObject.parseObject(fileState);
				// 成功删除
				if (! "0".equals(result.getString("code"))) {
					return FastJsonUtil.error(-1, "文件已存在且删除失败");
				}
			}
			// 开始上传
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
			fileState = COS.uploadFile(bucket, fileFullPath, byteArrayInputStream);
			result = JSONObject.parseObject(fileState);
			// 上传成功
			if ("0".equals(result.getString("code"))) {
				// 成功
				data.put("access_url", 
						result.getJSONObject("data").getString("access_url").replace(
								COSUtil.COS_DOMAIN, COSUtil.LUNA_STATIC_RESOURCE_DOMAIN));
			}
		}
		if (data.isEmpty()) {
			return FastJsonUtil.error("-1", result == null ? "上传失败" : result.getString("message"));
		}
		return FastJsonUtil.sucess("上传成功", data);
		
	}
	
	/**
	 * for small file, upload directly(do not store locally)
	 * 
	 * @param file
	 * @param bucket
	 * @param dst_folder
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws Exception
	 */
	public JSONObject upload2CloudDirect(MultipartFile file, String bucket, String dst_folder) throws FileNotFoundException, IOException, Exception {
		JSONObject data = JSONObject.parseObject("{}");
		long size = file.getSize();
		String fileState = "";
		JSONObject result = null;
		if(size > COSUtil.图片上传大小分界线1M) {
			return FastJsonUtil.error(-1, "文件大小超过1M");
		}
		// 智能创建需要的文件夹
		if (createFolder(bucket, dst_folder)) {
			String randomfileName = VbMD5.generateToken();
			String name = file.getOriginalFilename();
			int point = name.lastIndexOf(".");
			if (point != -1 && point != name.length() - 1) {
				String pathAndName = dst_folder + randomfileName + name.substring(point).toLowerCase();
				fileState = COS.getFileStat(bucket, pathAndName);
				result = JSONObject.parseObject(fileState);
				// 文件存在，需要先删除后再传
				if ("0".equals(result.getString("code"))) {
					fileState = COS.deleteFile(bucket, pathAndName);
					result = JSONObject.parseObject(fileState);
					// 成功删除
					if (! "0".equals(result.getString("code"))) {
						return FastJsonUtil.error(-1, "文件已存在且删除失败");
					}				
				}
				// 开始上传
				fileState = COS.uploadFile(bucket, pathAndName, file.getInputStream());
				result = JSONObject.parseObject(fileState);
				// 上传成功
				if ("0".equals(result.getString("code"))) {
					// 成功
					data.put("access_url", 
							result.getJSONObject("data").getString("access_url").replace(
									COSUtil.COS_DOMAIN, COSUtil.LUNA_STATIC_RESOURCE_DOMAIN));
				}
			}
		}

		if (data.isEmpty()) {
			return FastJsonUtil.error("-1", result == null ? "文件上传失败" : result.getString("message"));
		}
		return FastJsonUtil.sucess("上传成功", data);
	}

	
	/**
	 * 
	 * @param appid
	 * @param srcFile
	 * @param dst_folder
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public JSONObject upload2Cloud(MultipartFile file, String bucket, String dst_folder) throws Exception {
		JSONObject data = JSONObject.parseObject("{}");
		long size = file.getSize();
		String fileState = "";
		JSONObject result = null;
		// COS.slicUpload
		if (size < COSUtil.图片上传大小分界线1M) {
			// 智能创建需要的文件夹
			if (createFolder(bucket, dst_folder)) {
				String randomfileName = VbMD5.generateToken();
				String name = file.getOriginalFilename();
				int point = name.lastIndexOf(".");
				if (point != -1 && point != name.length() - 1) {
					String pathAndName = dst_folder + randomfileName + name.substring(point).toLowerCase();
					// 本地保存
					String localFolder = LOCAL_BUCKET + dst_folder;
					String localFile = LOCAL_BUCKET + pathAndName;
					if (VbUtility.createLocalFolder(localFolder)) {
						File tempFile = new File(localFile);
						//if (tempFile.createNewFile()) {
							FileOutputStream fos = new FileOutputStream(tempFile);
							DataOutputStream dos = new DataOutputStream(
										new BufferedOutputStream(fos));  
							dos.write(file.getBytes());
							dos.flush();
							dos.close();
//						} else {
//							return FastJsonUtil.error("-1", "local文件创建失败");
//						}
					}

					fileState = COS.getFileStat(bucket, pathAndName);
					result = JSONObject.parseObject(fileState);
					// 文件存在，需要先删除后再传
					if ("0".equals(result.getString("code"))) {
						fileState = COS.deleteFile(bucket, pathAndName);
						result = JSONObject.parseObject(fileState);
						// 成功删除
						if ("0".equals(result.getString("code"))) {
							// 开始上传
							fileState = COS.sliceUploadFile(bucket, pathAndName, localFile);
							result = JSONObject.parseObject(fileState);
							// 上传成功
							if ("0".equals(result.getString("code"))) {
								// 成功
								data.put("access_url", 
										result.getJSONObject("data").getString("access_url").replace(
												COSUtil.COS_DOMAIN, COSUtil.LUNA_STATIC_RESOURCE_DOMAIN));
							}
						}

						// 文件不存在，可以直接上传
					} else {
						// 上传
						fileState = COS.sliceUploadFile(bucket, pathAndName, localFile);
						result = JSONObject.parseObject(fileState);
						// 上传成功
						if ("0".equals(result.getString("code"))) {
							// 成功
							System.out.println(result.toString());
							data.put("access_url", result.getJSONObject("data").getString("access_url")
									.replace(COSUtil.COS_DOMAIN, COSUtil.LUNA_STATIC_RESOURCE_DOMAIN));
						}
					}
				}
			}
		}
		if (data.isEmpty()) {
			return FastJsonUtil.error("-1", result == null ? "文件不能超过1M" : result.getString("message"));
		}
		return FastJsonUtil.sucess("上传成功", data);
	}

	/**
	 * 
	 * @param appid
	 * @param srcFile
	 * @param dst_folder
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public JSONObject upload2Cloud(String localpath, String bucket, String dst_folder, String fileName) throws Exception {
		JSONObject data = JSONObject.parseObject("{}");
		String fileState = "";
		JSONObject result = null;
		// 智能创建需要的文件夹
		if (createFolder(bucket, dst_folder)) {
			String pathAndName = dst_folder + fileName;
			fileState = COS.getFileStat(bucket, pathAndName);
			result = JSONObject.parseObject(fileState);
			// 文件存在，需要先删除后再传
			if ("0".equals(result.getString("code"))) {
				fileState = COS.deleteFile(bucket, pathAndName);
				result = JSONObject.parseObject(fileState);
				// 成功删除
				if ("0".equals(result.getString("code"))) {
					// 开始上传
					fileState = COS.sliceUploadFile(bucket, pathAndName, localpath);
					
					result = JSONObject.parseObject(fileState);
					// 上传成功
					if ("0".equals(result.getString("code"))) {
						// 成功
						data.put("access_url", result.getJSONObject("data").getString("access_url")
								.replace(COSUtil.COS_DOMAIN, COSUtil.LUNA_STATIC_RESOURCE_DOMAIN));
					}
				}

				// 文件不存在，可以直接上传
			} else {
				// 上传
				fileState = COS.sliceUploadFile(bucket, pathAndName, localpath);
				result = JSONObject.parseObject(fileState);
				// 上传成功
				if ("0".equals(result.getString("code"))) {
					// 成功
					System.out.println(result.toString());
					data.put("access_url", result.getJSONObject("data").getString("access_url")
							.replace(COSUtil.COS_DOMAIN, COSUtil.LUNA_STATIC_RESOURCE_DOMAIN));
				}
			}
		}
		if (data.isEmpty()) {
			return FastJsonUtil.error("-1", "上传失败");
		}
		return FastJsonUtil.sucess("上传成功", data);
	}

	/**
	 * @param bucket  COS上创建的bucket名称 "bucketname"
	 * @param folder  需要创建的路径字符串，会在bucket中创建 "path/to/your/dir"
	 * @return true 创建成功
	 */
	public boolean createFolder(String bucket, String folder) throws Exception {
		String fileSeparator = "/" ;
		String[] dirs = folder.split(fileSeparator, folder.length());
		StringBuilder path = new StringBuilder();
		for (int i = 0; i < dirs.length; i++) {
			path.append(dirs[i]);
			if (!path.toString().endsWith(fileSeparator)) {
				path.append("/");
			}
			if (dirs[i].length() == 0) {
				continue;
			}
			String json = COS.getFolderStat(bucket, path.toString());
			JSONObject result = JSONObject.parseObject(json);
			String code = result.getString("code");
			if (!"0".equals(code)) {
				json = COS.createFolder(bucket, path.toString());
				result = JSONObject.parseObject(json);
				code = result.getString("code");
				if (!"0".equals(code)) {
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * 
	 * @param localPath
	 * @param bucket
	 * @param folder
	 * @param fileName
	 * @return
	 */
	private String add2Cloud(String localPath,String bucket,String folder,String fileName) {

		String fileSeparator = File.separator;
		//tjl:
		fileSeparator = "/" ; 
		String remotePath = folder;
		if (!folder.endsWith(fileSeparator)) {
			remotePath += fileSeparator + fileName;
		} else {
			remotePath += fileName;
		}
		String json = null;
		try {
			json = COS.sliceUploadFile(bucket, remotePath, localPath);
			JSONObject result = JSONObject.parseObject(json);
			if ("0".equals(result.getString("code"))) {
				JSONObject data = result.getJSONObject("data");
				return data.getString("access_url")
						.replace(COSUtil.COS_DOMAIN, COSUtil.LUNA_STATIC_RESOURCE_DOMAIN);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	
	public boolean uploadDir2Cloud(String localPath, String bucket, String folder) throws Exception {
		//把当前目录下的文件全传，若有空文件夹则创建空文件夹。（递归建立非空文件夹）
		boolean ans = true ;
		String fileSeparator = File.separator;
		
		File file = new File(localPath) ;
		
		if(file.isDirectory()){
			String[] fileList = file.list() ;
			for(int i=0; fileList != null && i<fileList.length; i++){
				String tempLocalPath = localPath + fileSeparator + fileList[i] ;
				String tempRemotePath = folder + "/" + fileList[i] ;
//				String tempRemotePath = folder + fileSeparator + fileList[i] ;
				File tempFile = new File(tempLocalPath) ;
				if(tempFile.isDirectory()){
					//创建目录
					//若目录存在，则删除已存在目录，再进行添加操作
					System.out.println("dir: "+tempRemotePath+"/") ;
					String retStr = COS.getFolderStat(bucket, tempRemotePath+"/") ;
					JSONObject ret = JSONObject.parseObject(retStr) ;
					System.out.println("code:" + ret.getInteger("code")+" message:"+ret.getString("message")) ;
					if(ret.getInteger("code") == 0 ){
						//已存在目录，需要删除	
						deletedir(bucket, tempRemotePath);
					}
					System.out.println("code:"+Integer.toString(ret.getInteger("code"))+ tempRemotePath) ;
					ans = createFolder(bucket, tempRemotePath) ; //创建空目录
					if(ans == false) return false ;
//					System.out.println("tempFile:"+tempFile.getName() + " len:"+tempFile.length()) ;
					String[] templist = tempFile.list();
					if(templist != null && templist.length>0) {
						//若目录菲空递归建立
//						System.out.println("EMP tempLocalPath:"+tempLocalPath+ " bucket:"+" tempRemotePath:"+tempRemotePath) ;
						uploadDir2Cloud(tempLocalPath, bucket, tempRemotePath) ; //创建非空目录下的文件
					}
				}
				if(tempFile.isFile()){
					//创建文件
					//若文件存在，则先删除文件，再进行添加操作
					System.out.println("tempLocalPath: " + tempLocalPath + " folder: "+folder + " fileList: "+fileList[i]) ;
					String retStr = null ;
					try {	
						retStr = COS.getFileStat(bucket, tempRemotePath) ;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					JSONObject ret = JSONObject.parseObject(retStr) ;
					System.out.println("code:" + ret.getInteger("code")+"message:"+ret.getString("message")) ;
					if(ret.getInteger("code") == 0){
						//已存在文件，需要删除
						System.out.println("存在:"+ tempRemotePath) ;
						try {
							COS.deleteFile(bucket, tempRemotePath) ;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					String fileUrl = add2Cloud(tempLocalPath, bucket, folder, fileList[i]) ;
					if(fileUrl == null) return  false ;
				}
			}
		}
		
		return ans ;
		
	}

	public String save2LocalFile(byte[] bytes, String localFolder, String ext) throws Exception {
		localFolder = LOCAL_BUCKET + localFolder;
		if (VbUtility.createLocalFolder(localFolder)) {
			String randomFile = VbMD5.generateToken() + ext;
			File tempFile = new File(localFolder + randomFile);
//			if (tempFile.createNewFile()) {
				FileOutputStream fos = new FileOutputStream(tempFile);
				DataOutputStream dos = new DataOutputStream(
							new BufferedOutputStream(fos));  
				dos.write(bytes);
				dos.flush();
				dos.close();
				return localFolder + randomFile;
//			}
		}
		return null;
	}
	
	public void deletedir(String bucket, String folder){
		//func:删除folder文件夹
//		boolean ans = true ;
		
		String fileSeparator = File.separator;
		fileSeparator = "/" ;
		String context = "" ;
		String retStr = null;
		try {
			retStr = COS.getFolderList(bucket, folder, 50, context, 0, FolderPattern.Both);
			logger.debug("cos get folderList ret: " + retStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("getFolderList failed", e);
		}
		JSONObject ret = JSONObject.parseObject(retStr) ;

		if(ret.getInt("code") != 0) {
			return;
		}

		//进入了空目录
		if(ret.getJSONObject("data").getInteger("dircount")==0 && ret.getJSONObject("data").getInteger("filecount")==0){
			try {
				COS.deleteFolder(bucket, folder) ;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("delete Folder failed", e);
			}
			return ;
		} 	
			
		while(true){	
			//删除该文件夹的文件
			//删除目录
				//空目录直接删除
				//非空目录递归删除
			JSONArray names = ret.getJSONObject("data").getJSONArray("infos") ;
			
			for(int i = 0; i < names.size(); i++){
				JSONObject o = names.getJSONObject(i);
				String name = o.getString("name") ;
//				System.out.println("I got a file") ;
				if(o.containsKey("filesize")){
					//为文件时
					logger.trace("delFile: " + folder + fileSeparator + name); ;
					try {
						COS.deleteFile(bucket, folder+fileSeparator+name) ;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						logger.error("Delete file failed", e);
					}
				} else {
					//为目录时
//					System.out.println("Dir: "+folder+"/"+name) ;
					deletedir(bucket, folder+"/"+name) ;
				}
			}
			
			if(!ret.getJSONObject("data").getBoolean("has_more")){
				break ;
			}
			
			//下一页
			context = ret.getJSONObject("data").getString("context");
			try {
				retStr = COS.getFolderList(bucket, folder, 50, context, 0, FolderPattern.Both) ;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("Delete folder failed", e);
			}
			ret = JSONObject.parseObject(retStr) ;
			
		}
	
		
		//删除本目录
		try {
			COS.deleteFolder(bucket, folder) ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Delete folder failed", e);
		}
		
		return  ;
	}

	public JSONObject uploadFile2Cloud(byte[] bytes, String localServerTempPath, String bucket, String remotePath) {
		int lastIndex = remotePath.lastIndexOf(".");
		if (lastIndex  > 0) {
			String ext = remotePath.substring(lastIndex);
			String tempName = VbMD5.generateToken() + ext;
			String localPath = null;
			try {
				localPath = this.uploadFile2LocalServer(bytes, localServerTempPath, tempName, Boolean.TRUE);
			} catch (Exception e) {
				e.printStackTrace();
				return FastJsonUtil.error("-1", VbUtility.printStackTrace(e));
			}
			JSONObject result;
			try {
				result = this.uploadLocalFile2Cloud(bucket, localPath, remotePath);
			} catch (Exception e) {
				e.printStackTrace();
				return FastJsonUtil.error("-1", VbUtility.printStackTrace(e));
			}
			return result;
		}
		return FastJsonUtil.error("-1", "remotePath format is not correct["+ remotePath + "]");
	}
	
	public String uploadFile2LocalServer(byte[] bytes, String localFolder, String fileName, Boolean replace) throws Exception {
		String filePath = localFolder + fileName;
		Pattern pattern1 = Pattern.compile("/.+/", Pattern.CASE_INSENSITIVE);
		Matcher matcher1 = pattern1.matcher(localFolder);
		if (!matcher1.matches()) {
			throw new IllegalArgumentException("localFolder format is not correct+["+ localFolder+ "]");
		}
		Pattern pattern2 = Pattern.compile(".*//.*", Pattern.CASE_INSENSITIVE);
		Matcher matcher2 = pattern2.matcher(filePath);
		if (matcher2.matches()) {
			throw new IllegalArgumentException("localFolder or fileName format is not correct+["+ filePath+ "]");
		}

		if (VbUtility.createLocalFolder(localFolder)) {
			File tempFile = new File(filePath);
			if (replace || !tempFile.exists()) {
				FileOutputStream fos = new FileOutputStream(tempFile);
				DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(fos));  
				dos.write(bytes);
				dos.flush();
				dos.close();
			}
			return filePath;
		}
		throw new RuntimeException("creating localFolder, error was occurred["+ filePath+ "]");
	}
//	/dev/poi/pic/20160526/0P0O1x1h3v0F3m0F100N0o1F3V0F3z0O.mp3
	public JSONObject uploadLocalFile2Cloud(String bucket, String localFile, String remoteFile) throws Exception {
		// 检查地址是否正确
		Pattern pattern1 = Pattern.compile("/.+/.+"+ PERMITTED_FILE_TYPES, Pattern.CASE_INSENSITIVE);
		Matcher matcher1 = pattern1.matcher(remoteFile);
		if (!matcher1.matches()) {
			throw new IllegalArgumentException("remoteFile format is not correct+["+ remoteFile+ "]");
		}
		Pattern pattern2 = Pattern.compile(".*//.*", Pattern.CASE_INSENSITIVE);
		Matcher matcher2 = pattern2.matcher(remoteFile);
		if (matcher2.matches()) {
			throw new IllegalArgumentException("remoteFile format is not correct+["+ remoteFile+ "]");
		}

		String[] folders = remoteFile.split("/");
		if (folders == null || remoteFile.isEmpty()) {
			return null;
		}
		StringBuilder sb = new StringBuilder("/");
		int i = 0;
		for (i = 0; i < folders.length-1; i++ ) {
			if (folders[i].trim().length() == 0) {
				continue;
			}
			sb.append(folders[i]).append("/");
		}
		String dst_folder = sb.toString();
		String fileName = folders[i];

		return upload2Cloud(localFile, bucket, dst_folder, fileName);
	}
//	public static void main( String []args){
//		
//		new COSWrapper(10002033,"AKIDLx9DqZut6Gp8fdrw5g1X3DX4K7stFdZp","XmJgCf7xYjNhKbDkS6PQKL9Q50fAhBaL");
//		//cos.createFolder("luna", "/h5/testapp");
////		COSWrapper.upload2Cloud("/Users/yang/code/test_upload.txt", "luna", "/test/", "");
//		uploadDir2Cloud("C:\\Users\\tjl\\Desktop\\today","luna","/h5/testapp");
////		deletedir("luna", "/h5/testapp/haha");
//		System.out.println("finish");
//		return ;
//	}
	
}
