package ms.luna.biz.sc;

import com.alibaba.fastjson.JSONObject;

import ms.luna.biz.model.MsUser;

/**
 * 
 * Copyright (C) 2015 - 2016 Microscene Inc., All Rights Reserved.
 *
 * @author: shawn@visualbusiness.com
 * @Date: Apr 27, 2016
 *
 */
public interface ManageShowAppService {

	JSONObject loadApps(String json);

	JSONObject deleteApp(String json);
	
	JSONObject createApp(String json);
	
	JSONObject updateApp(String json);
	
	boolean existAppName(String appName);

	JSONObject copyApp(String json);
	
	JSONObject searchBusiness(String json);
	
	JSONObject existOnlineApp(int appId);
	
	JSONObject publishApp(String json);
	
	/**
	 * 生成微景展并返回二维码地址
	 * @param json
	 * @return
	 */
	JSONObject generateShowApp(int appId);

	/**
	 * 获取微景展设置信息
	 * @param json
	 * @return
	 */
	JSONObject getSettingOfApp(int appId);

	/**
	 * 保存微景展设置信息
	 * @param json
	 * @return
	 */
	JSONObject saveSettingOfApp(String json, MsUser msUser);

}
