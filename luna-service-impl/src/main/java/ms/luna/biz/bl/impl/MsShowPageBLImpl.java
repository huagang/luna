package ms.luna.biz.bl.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import ms.biz.common.ServiceConfig;
import ms.luna.biz.bl.MsShowPageBL;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.dao.custom.MsShowPageDAO;
import ms.luna.biz.dao.custom.model.MsShowPage;
import ms.luna.biz.util.FastJsonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 
 * Copyright (C) 2015 - 2016 Microscene Inc., All Rights Reserved.
 *
 * @author: shawn@visualbusiness.com
 * @Date: Apr 28, 2016
 *
 */
@Transactional(rollbackFor=Exception.class)
@Service("msShowPageBL")
public class MsShowPageBLImpl implements MsShowPageBL {
	
	private final static Logger logger = Logger.getLogger(MsShowPageBLImpl.class);

	private static String page_share_url= "/app/%s/page/%s";

	@Autowired
	MsShowPageDAO msShowPageDAO;
	private List<String> summaryFields;
	
	@PostConstruct
	public void init() {
		summaryFields = Lists.newArrayList(MsShowPageDAO.FIELD_APP_ID, MsShowPageDAO.FIELD_PAGE_ID,
										   MsShowPageDAO.FIELD_PAGE_NAME, MsShowPageDAO.FIELD_PAGE_CODE,
										   MsShowPageDAO.FIELD_PAGE_ORDER);
	}
	
	@Override
	public JSONObject getAllPageSummary(int appId) {
		// TODO: should check if the app exist
		List<MsShowPage> msShowPages = msShowPageDAO.readAllPageSummaryByAppId(appId, summaryFields);
		if(msShowPages != null) {
			Map<String, MsShowPage> pageId2Page = new LinkedHashMap<>(msShowPages.size());
			for(MsShowPage page : msShowPages) {
				pageId2Page.put(page.getPageId(), page);
			}
			return FastJsonUtil.sucess("", JSON.toJSON(pageId2Page));
		}
		return FastJsonUtil.error(ErrorCode.NOT_FOUND, "页面不存在");
	}

	@Override
	public JSONObject getAllPageDetail(int appId) {
		// TODO Auto-generated method stub
		List<MsShowPage> msShowPages = msShowPageDAO.readAllPageDetailByAppId(appId);

		if(msShowPages != null) {
			Map<String, MsShowPage> pageId2Page = new LinkedHashMap<>(msShowPages.size());
			for(MsShowPage page : msShowPages) {
				pageId2Page.put(page.getPageId(), page);
			}
			
			return FastJsonUtil.sucess("", JSON.toJSON(pageId2Page));
		}
		
		return FastJsonUtil.error(ErrorCode.NOT_FOUND, "页面不存在");
	}

	@Override
	public JSONObject getOnePageDetail(String pageId) {
		// TODO Auto-generated method stub
		MsShowPage page = msShowPageDAO.readPageDetail(pageId);
		// 单页分享.通过pageId获得分享链接
		if(page.getShareInfo() != null) { // 存在单页分享信息
			String share_link = (String) page.getShareInfo().get(MsShowPageDAO.FIELD_SHARE_LINK);
			if(StringUtils.isBlank(share_link)) { // 非自定义分享链接
				share_link = ServiceConfig.getString(ServiceConfig.MS_WEB_URL) + String.format(page_share_url, page.getAppId(),pageId);
				page.getShareInfo().put(MsShowPageDAO.FIELD_SHARE_LINK, share_link);
			}
		}
		if(page != null) {
			return FastJsonUtil.sucess("", JSON.toJSON(page));
		}
		return FastJsonUtil.error(ErrorCode.NOT_FOUND, "页面未找到");
	}

	@Override
	public JSONObject createOnePage(String json, String lunaName) {
		// TODO Auto-generated method stub
		// check page name & page code not exist
		MsShowPage page = JSONObject.parseObject(json, MsShowPage.class);
		if(page.getPageContent() == null) {
			//给前端返回page detail时，需要含有此字段, 创建时预创建
			page.setPageContent(new HashMap<String, Object>());
		}
		page.setUpdateUser(lunaName);
		String pageId = msShowPageDAO.createOnePage(page);
		page.setPageId(pageId);
		return FastJsonUtil.sucess("创建成功", JSON.toJSON(page));
	}

	// 前端实现单页面更新复用了多个页面的接口
	@Override
	public JSONObject updatePage(String json, String lunaName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject updatePages(String json, String lunaName) {
		// TODO Auto-generated method stub
		JSONObject jsonObject;
		
		try{
			jsonObject = JSON.parseObject(json);	
		} catch (Exception e) {
			logger.error("Parse json object failed" ,e);
			return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "数据不合法");
		}
		
		try{
			Collection<Object> pageJsons = jsonObject.values();
			List<MsShowPage> pages = new ArrayList<>(pageJsons.size());
			for(Object pageJson : pageJsons) {
				MsShowPage page = (MsShowPage) JSONObject.toJavaObject((JSONObject)pageJson, MsShowPage.class);
				page.setUpdateUser(lunaName);
				pages.add(page);
			}
			msShowPageDAO.updatePages(pages);
			return FastJsonUtil.sucess("更新成功");
		} catch (Exception e) {
			logger.error("Parse and update page failed", e);
			return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "数据处理出错");
		}

	}
	
	@Override
	public JSONObject deletePageById(String pageId) {
		// TODO Auto-generated method stub
		msShowPageDAO.deletePageById(pageId);
		return FastJsonUtil.sucess("删除页面成功");
	}

	@Override
	public JSONObject deletePageByAppId(int appId) {
		// TODO Auto-generated method stub
		msShowPageDAO.deletePagesByAppId(appId);
		return FastJsonUtil.sucess("删除单个微景展所有页面成功");
	}
	
	private void isValid(MsShowPage page) throws Exception {
		String pageId = page.getPageId();
		if(StringUtils.isBlank(pageId)) {
			throw new Exception("pageId can not be null");
		}
	}

	@Override
	public JSONObject updatePageName(String json, String lunaName) {
		// TODO Auto-generated method stub
		try{
			MsShowPage msShowPage = JSON.parseObject(json, MsShowPage.class);
			msShowPage.setUpdateUser(lunaName);
			msShowPageDAO.updatePageName(msShowPage);
			return FastJsonUtil.sucess("更新页面名称成功");
		} catch(Exception ex) {
			logger.error("Failed to update page name", ex);
		}		
		return FastJsonUtil.error(-1, "更新页面名称失败");
	}

	@Override
	public JSONObject updatePageOrder(String json) {
		// TODO Auto-generated method stub
		try{
			if(json != null) {
				Map<String, Integer> pageOrders = JSON.parseObject(json, new TypeReference<Map<String, Integer>>() {});
				msShowPageDAO.updatePageOrder(pageOrders);
				return FastJsonUtil.sucess("更新页面顺序f保存成功");
			}
		} catch(Exception ex) {
			logger.error("Failed to update page orders", ex);
		}
		return FastJsonUtil.error(-1, "更新页面顺序失败");
	}

	@Override
	public JSONObject getIndexPage(int appId) {
		// TODO Auto-generated method stub
		
		List<MsShowPage> msShowPages = msShowPageDAO.readIndexPageDetailByAppId(appId);
		if(msShowPages != null) {
			return FastJsonUtil.sucess("", JSON.toJSON(msShowPages));
		}
		return FastJsonUtil.error(ErrorCode.NOT_FOUND, "页面未找到");
	}

	@Override
	public JSONObject duplicateOnePage(String json, String lunaName) {
		// 先取page_id内容,修改基本信息后存储
		MsShowPage pageParam = JSONObject.parseObject(json, MsShowPage.class);
		MsShowPage page = msShowPageDAO.readPageDetail(pageParam.getPageId());
		page.setPageName(pageParam.getPageName());
		page.setPageCode(pageParam.getPageCode());
		page.setPageOrder(pageParam.getPageOrder());
		page.setUpdateUser(lunaName);
		page.setShareInfo(pageParam.getShareInfo());
		String pageId = msShowPageDAO.createOnePage(page);
		page.setPageId(pageId);
		return FastJsonUtil.sucess("创建成功", JSON.toJSON(page));
	}

}
