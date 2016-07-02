package com.qcloud.vod.Module;

import java.util.TreeMap;

import org.springframework.web.multipart.MultipartFile;

import com.qcloud.vod.Common.ModifiedRequest;

/**
 * @author Greek
 * @date create time：2016年5月14日 下午7:14:56
 * @version 1.0
 */
public class ModifiedBase extends Base {

	private String ucFirst(String word){
		return word.replaceFirst(word.substring(0, 1),
				word.substring(0, 1).toUpperCase());
	}
	
	public String call(String actionName, TreeMap<String, Object> params, MultipartFile file) {
		actionName = ucFirst(actionName);
		if (params == null)
			params = new TreeMap<String, Object>();
		params.put("Action", actionName);
		if (!params.containsKey("Region")) {
			params.put("Region", defaultRegion);
		}
		String response = ModifiedRequest.send(params, secretId, secretKey, requestMethod, serverHost, serverUri, file);
		return response;
	}
}
