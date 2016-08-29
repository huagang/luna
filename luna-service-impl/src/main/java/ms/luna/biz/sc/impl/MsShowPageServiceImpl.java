package ms.luna.biz.sc.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import ms.luna.biz.bl.MsShowPageBL;
import ms.luna.biz.model.MsUser;
import ms.luna.biz.sc.MsShowPageService;

@Service("msShowPageService")
public class MsShowPageServiceImpl implements MsShowPageService{
	
	private static Logger logger = Logger.getLogger(MsShowPageServiceImpl.class);
	
	@Autowired
	private MsShowPageBL msShowPageBL;
	
	@Override
	public JSONObject getAllPageSummary(int appId) {
		// TODO Auto-generated method stub
		return msShowPageBL.getAllPageSummary(appId);
	}
	@Override
	public JSONObject getAllPageDetail(int appId) {
		// TODO Auto-generated method stub
		return msShowPageBL.getAllPageDetail(appId);
	}
	@Override
	public JSONObject getOnePageDetail(String pageId) {
		// TODO Auto-generated method stub
		return msShowPageBL.getOnePageDetail(pageId);
	}
	@Override
	public JSONObject deletePageById(String pageId) {
		// TODO Auto-generated method stub
		return msShowPageBL.deletePageById(pageId);
	}
	@Override
	public JSONObject deletePageByAppId(int appId) {
		// TODO Auto-generated method stub
		return msShowPageBL.deletePageByAppId(appId);
	}
	@Override
	public JSONObject createOnePage(String json, String lunaName) {
		// TODO Auto-generated method stub
		return msShowPageBL.createOnePage(json, lunaName);
	}
	@Override
	public JSONObject updatePage(String json, String lunaName) {
		// TODO Auto-generated method stub
		return msShowPageBL.updatePage(json, lunaName);
	}
	@Override
	public JSONObject updatePages(String json, String lunaName) {
		// TODO Auto-generated method stub
		return msShowPageBL.updatePages(json, lunaName);
	}
	@Override
	public JSONObject updatePageName(String json, String lunaName) {
		// TODO Auto-generated method stub
		return msShowPageBL.updatePageName(json, lunaName);
	}
	@Override
	public JSONObject updatePageOrder(String json) {
		// TODO Auto-generated method stub
		return msShowPageBL.updatePageOrder(json);
	}
	@Override
	public JSONObject getIndexPage(int appId) {
		// TODO Auto-generated method stub
		return msShowPageBL.getIndexPage(appId);
	}

	@Override
	public JSONObject duplicateOnePage(String json, String lunaName) {
		// TODO Auto-generated method stub
		return msShowPageBL.duplicateOnePage(json, lunaName);
	}

}
