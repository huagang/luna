package com.qcloud.vod.Module;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.TreeMap;

import org.springframework.web.multipart.MultipartFile;

import com.qcloud.vod.Utilities.SHA1;

/** 
 * @author  Greek 
 * @date create time：2016年5月14日 下午8:39:00 
 * @version 1.0 
 */
public class ModifiedVod extends ModifiedBase {
	
	public ModifiedVod(){
		serverHost = "vod.api.qcloud.com";
	}
	
	public String MultipartUploadVodFile(TreeMap<String, Object> params, MultipartFile file) throws NoSuchAlgorithmException, IOException {
		serverHost = "vod.qcloud.com";
		
		String actionName = "MultipartUploadVodFile";

        if (!params.containsKey("fileSize")){
        	params.put("fileSize", file.getSize());
        }
        if (!params.containsKey("fileSha")){
        	params.put("fileSha", SHA1.streamToSHA(file.getInputStream()));
        }
        
        return call(actionName, params, file);
	}
	
	public String MultipartUploadVodFile2(TreeMap<String, Object> params) throws NoSuchAlgorithmException, IOException {
		serverHost = "vod.qcloud.com";
		
		String actionName = "MultipartUploadVodFile";

        String fileName = params.get("file").toString();
        params.remove("file");
        File f= new File(fileName);  
        
        if (!params.containsKey("fileSize")){
        	params.put("fileSize", f.length());
        }
        if (!params.containsKey("fileSha")){
        	params.put("fileSha", SHA1.fileNameToSHA(fileName));
        }
        
        return call(actionName, params, fileName);
	}
	
	
}
