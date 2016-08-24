package ms.luna.biz.sc;

import com.alibaba.fastjson.JSONObject;

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
	
	JSONObject createOnePage(String json, String lunaName);
	
	JSONObject updatePageName(String json, String lunaName);
	
	JSONObject updatePage(String json, String lunaName);
	
	JSONObject updatePages(String json, String lunaName);
	
	JSONObject deletePageById(String pageId);
	
	JSONObject deletePageByAppId(int appId);
	
	JSONObject updatePageOrder(String json);
	
	JSONObject getIndexPage(int appId);

	JSONObject duplicateOnePage(String json, String lunaName);
	
}
