package ms.luna.biz.sc;

import com.alibaba.fastjson.JSONObject;

import ms.luna.biz.model.MsUser;

/**
 * 
 * Copyright (C) 2015 - 2016 Microscene Inc., All Rights Reserved.
 *
 * @author: shawn@visualbusiness.com
 * @Date: Apr 26, 2016
 *
 */
public interface MsShowPageService {

	JSONObject getAllPageSummary(int appId);
	
	JSONObject getAllPageDetail(int appId);
		
	JSONObject getOnePageDetail(String pageId);
	
	JSONObject createOnePage(String json, MsUser msUser);
	
	JSONObject updatePageName(String json, MsUser msUser);
	
	JSONObject updatePage(String json, MsUser msUser);
	
	JSONObject updatePages(String json, MsUser msUser);
	
	JSONObject deletePageById(String pageId);
	
	JSONObject deletePageByAppId(int appId);
	
	JSONObject updatePageOrder(String json);
	
	JSONObject getIndexPage(int appId);
	
}
