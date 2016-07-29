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

	JSONObject getAppInfo(int appId);

	JSONObject deleteApp(String json);
	
	JSONObject createApp(String json);
	
	JSONObject updateApp(String json);
	
	boolean existAppName(String appName);

	JSONObject copyApp(String json);
	
	JSONObject searchBusiness(String json);
	
	JSONObject existOnlineApp(int appId);
	
	JSONObject publishApp(String json);

	JSONObject generateShowApp(int appId);

	JSONObject getSettingOfApp(int appId);

	JSONObject saveSettingOfApp(String json, MsUser msUser);

}
