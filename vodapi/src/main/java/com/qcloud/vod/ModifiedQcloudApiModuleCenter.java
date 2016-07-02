package com.qcloud.vod;

import java.lang.reflect.Method;
import java.util.TreeMap;

import org.springframework.web.multipart.MultipartFile;

import com.qcloud.vod.Module.ModifiedBase;

/**
 * @brief 模块调用类
 * @author Administrator
 *
 */
public class ModifiedQcloudApiModuleCenter {

	private ModifiedBase module;

	/**
	 * 构造模块调用类
	 * 
	 * @param module实际模块实例
	 * @param config模块配置参数
	 */
	public ModifiedQcloudApiModuleCenter(ModifiedBase module, TreeMap<String, Object> config) {
		this.module = module;
		this.module.setConfig(config);
	}

	/**
	 * 生成Api调用地址
	 * 
	 * @param actionName模块动作名称
	 * @param params模块请求参数
	 * @return Api调用地址
	 */
	public String generateUrl(String actionName, TreeMap<String, Object> params) {
		return module.generateUrl(actionName, params);
	}

	/**
	 * Api调用
	 * 
	 * @param actionName模块动作名称
	 * @param params模块请求参数
	 * @param file上传文件
	 * @return json字符串
	 * @throws Exception
	 */
	public String call(String actionName, TreeMap<String, Object> params, MultipartFile file) throws Exception {
		for (Method method : module.getClass().getMethods()) {
			if (method.getName().equals(actionName)) {
				try {
					return (String) method.invoke(module, params, file);
				} catch (Exception e) {
					throw e;
				}
			}
		}
		return module.call(actionName, params, file);
	}

	/**
	 * Api调用
	 * 
	 * @param actionName模块动作名称
	 * @param params模块请求参数
	 * @return json字符串
	 * @throws Exception
	 */
	public String call(String actionName, TreeMap<String, Object> params) throws Exception {
		for (Method method : module.getClass().getMethods()) {
			if (method.getName().equals(actionName)) {
				try {
					return (String) method.invoke(module, params);
				} catch (Exception e) {
					throw e;
				}
			}
		}
		return module.call(actionName, params);
	}

	public void setConfigSecretId(String secretId) {
		module.setConfigSecretId(secretId);
	}

	public void setConfigSecretKey(String secretKey) {
		module.setConfigSecretKey(secretKey);
	}

	public void setConfigDefaultRegion(String region) {
		module.setConfigDefaultRegion(region);
	}

	public void setConfigRequestMethod(String method) {
		module.setConfigRequestMethod(method);
	}

}
