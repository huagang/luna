/**
 * 
 */
package ms.luna.biz.sc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import ms.luna.biz.bl.ManageShowAppBL;
import ms.luna.biz.model.MsUser;
import ms.luna.biz.sc.ManageShowAppService;
import ms.luna.biz.util.JsonUtil;

/**
 * 
 * Copyright (C) 2015 - 2016 Microscene Inc., All Rights Reserved.
 *
 * @author: shawn@visualbusiness.com
 * @Date: May 8, 2016
 *
 */
@Service("manageShowAppService")
public class ManageShowAppServiceImpl implements ManageShowAppService {
	
	@Autowired
	private ManageShowAppBL manageShowAppBL;

	@Override
	public JSONObject loadApps(String json) {
		// TODO Auto-generated method stub
		return manageShowAppBL.loadApps(json);
	}

	@Override
	public JSONObject deleteApp(String json) {
		// TODO Auto-generated method stub
		return manageShowAppBL.deleteApp(json);
	}

	@Override
	public JSONObject createApp(String json) {
		// TODO Auto-generated method stub
		return manageShowAppBL.createApp(json);
	}

	@Override
	public JSONObject updateApp(String json) {
		// TODO Auto-generated method stub
		return manageShowAppBL.updateApp(json);
	}

	@Override
	public JSONObject searchBusiness(String json) {
		// TODO Auto-generated method stub
		return manageShowAppBL.searchBusiness(json);
	}

	@Override
	public boolean existAppName(String appName) {
		// TODO Auto-generated method stub
		return manageShowAppBL.existAppName(appName);
	}

	@Override
	public JSONObject publishApp(String json) {
		return manageShowAppBL.publishApp(json);
	}

	@Override
	public JSONObject generateShowApp(int appId) {
		// TODO Auto-generated method stub
		return manageShowAppBL.generateShowApp(appId);
	}

	@Override
	public JSONObject getSettingOfApp(int appId) {
		// TODO Auto-generated method stub
		return manageShowAppBL.getSettingOfApp(appId);
	}

	@Override
	public JSONObject saveSettingOfApp(String json, MsUser msUser) {
		// TODO Auto-generated method stub
		return manageShowAppBL.saveSettingOfApp(json, msUser);
	}

	@Override
	public JSONObject existOnlineApp(int appId) {
		// TODO Auto-generated method stub
		return manageShowAppBL.existOnlineApp(appId);
	}

}
