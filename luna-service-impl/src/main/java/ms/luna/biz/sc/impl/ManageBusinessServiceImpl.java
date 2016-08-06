package ms.luna.biz.sc.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.bl.ManageBusinessBL;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.cons.LunaRoleCategoryExtra;
import ms.luna.biz.dao.custom.LunaUserRoleDAO;
import ms.luna.biz.dao.custom.MsBusinessDAO;
import ms.luna.biz.dao.custom.model.LunaUserRole;
import ms.luna.biz.dao.model.MsBusiness;
import ms.luna.biz.dao.model.MsBusinessCriteria;
import ms.luna.biz.sc.ManageBusinessService;
import ms.luna.biz.table.LunaUserTable;
import ms.luna.biz.table.MsBusinessTable;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.cache.MerchantCategoryCache;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * Copyright (C) 2015 - 2016 Microscene Inc., All Rights Reserved.
 *
 * @author: shawn@visualbusiness.com
 * @Date: Mar 30, 2016
 *
 */

@Transactional(rollbackFor = Exception.class)
@Service("manageBusinessService")
public class ManageBusinessServiceImpl implements ManageBusinessService {
	
	private final static Logger logger = Logger.getLogger(ManageBusinessServiceImpl.class);

	@Autowired 
	private ManageBusinessBL manageBusinessBL;
	@Autowired
	private LunaUserRoleDAO lunaUserRoleDAO;
	@Autowired
	private MsBusinessDAO msBusinessDAO;
	@Autowired
	private MerchantCategoryCache merchantCategoryCache;
	
	@Override
	public JSONObject createBusiness(String json) {
		// TODO Auto-generated method stub
		try {
			return manageBusinessBL.createBusiness(json);
		} catch (Throwable th) {
			logger.error("Failed to create business", th);
			return FastJsonUtil.error("-1", "创建业务失败");
		}
	}

	@Override
	public JSONObject loadBusinesses(String json) {
		// TODO Auto-generated method stub
		try {
			return manageBusinessBL.loadBusinesses(json);
		} catch(Throwable th) {
			logger.error("Failed to load business", th);
			return FastJsonUtil.error("-1", "获取业务失败");
		}
	}

	@Override
	public JSONObject updateBusinessById(String json) {
		// TODO Auto-generated method stub
		try {
			return manageBusinessBL.updateBusinessById(json);
		} catch(Throwable th) {
			logger.error("Failed to update business", th);
			return FastJsonUtil.error("-1", "更新业务信息失败");
		}
	}

	@Override
	public JSONObject deleteBusinessById(int id) {
		// TODO Auto-generated method stub
		try {
			return manageBusinessBL.deleteBusinessById(id);
		} catch(Throwable th) {
			logger.error("Failed to delete business", th);
			return FastJsonUtil.error("-1", "删除业务失败");
		}
	}

	@Override
	public JSONObject getBusinessForEdit(JSONObject jsonObject) {
		String loginUserId = jsonObject.getString("loginUserId");
		String slaveUserId = jsonObject.getString("slaveUserId");
		if(StringUtils.isBlank(loginUserId) || StringUtils.isBlank(slaveUserId)) {
			return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "参数不合法");
		}
		List<MsBusiness> allValidBusiness = getBusinessForUser(loginUserId);
		List<MsBusiness> selectedBusiness = getBusinessForUser(slaveUserId);

		if(allValidBusiness == null) {
			return FastJsonUtil.error(ErrorCode.NOT_FOUND, "没有任何业务");
		}

		Set<Integer> businessIdSet = new HashSet<>(allValidBusiness.size());
		for(MsBusiness msBusiness : allValidBusiness) {
			businessIdSet.add(msBusiness.getBusinessId());
		}

		Set<Integer> selectedBusinessIdSet = new HashSet<>(selectedBusiness.size());
		if(selectedBusiness != null) {
			for (MsBusiness msBusiness : selectedBusiness) {
				selectedBusinessIdSet.add(msBusiness.getBusinessId());
			}
		}

		Map<Integer, String> businessCategoryIdMap = msBusinessDAO.readBusinessCategoryId(businessIdSet);
		Map<String, String> categoryId2NameMap = merchantCategoryCache.getCategoryId2Name();

		JSONObject resJson = new JSONObject();
		for(MsBusiness msBusiness : allValidBusiness) {
			String categoryId = businessCategoryIdMap.get(msBusiness.getBusinessId());
			if(categoryId == null) {
				logger.warn("Failed to get categoryId for business: " + msBusiness.getBusinessId());
			}
			String categotryName = categoryId2NameMap.get(categoryId);
			JSONArray jsonArray = resJson.getJSONArray(categotryName);
			if(jsonArray == null) {
				jsonArray = new JSONArray();
				resJson.put(categotryName, jsonArray);
			}
			JSONObject businessJson = new JSONObject();
			businessJson.put(MsBusinessTable.FIELD_BUSINESS_ID, msBusiness.getBusinessId());
			businessJson.put(MsBusinessTable.FIELD_BUSINESS_NAME, msBusiness.getBusinessName());
			if(selectedBusinessIdSet.contains(msBusiness.getBusinessId())) {
				businessJson.put("selected", true);
			} else {
				businessJson.put("selected", false);
			}
			jsonArray.add(businessJson);
		}

		return FastJsonUtil.sucess("", resJson);
	}

	private List<MsBusiness> getBusinessForUser(String userId) {

		LunaUserRole lunaUserRole = lunaUserRoleDAO.readUserRoleInfo(userId);
		if(lunaUserRole == null) {
			logger.warn("Failed to get business for user, user not found, userId: " + userId);
			return null;
		}
		Map<String, Object> extra = lunaUserRole.getExtra();
		String type = extra.get("type").toString();
		if(! type.equals(LunaRoleCategoryExtra.TYPE_BUSINESS)) {
			// current user might not have business
			logger.warn(String.format("no business for current user[%s], type[%s] ", userId, type));
			return null;
		}
		List<Integer> businessIdList = (List<Integer>) extra.get("value");
		MsBusinessCriteria msBusinessCriteria = new MsBusinessCriteria();
		if(businessIdList.size() == 1 && businessIdList.get(0) == 0) {
			// select all business
		} else {
			msBusinessCriteria.createCriteria().andBusinessIdIn(businessIdList);
		}
		List<MsBusiness> msBusinessList = msBusinessDAO.selectByCriteria(msBusinessCriteria);

		return msBusinessList;

	}

	@Override
	public JSONObject getBusinessForSelect(JSONObject jsonObject) {
		// TODO: in future, should get business in pages
		String userId = jsonObject.getString(LunaUserTable.FIELD_ID);
		if(StringUtils.isBlank(userId)) {
			return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "参数不合法");
		}
		List<MsBusiness> msBusinessList = getBusinessForUser(userId);
		if(msBusinessList == null) {
			return FastJsonUtil.error(ErrorCode.NOT_FOUND, "没有任何业务");
		}
		Set<Integer> businessIdSet = new HashSet<>(msBusinessList.size());
		for(MsBusiness msBusiness : msBusinessList) {
			businessIdSet.add(msBusiness.getBusinessId());
		}
		Map<Integer, String> businessCategoryIdMap = msBusinessDAO.readBusinessCategoryId(businessIdSet);
		Map<String, String> categoryId2NameMap = merchantCategoryCache.getCategoryId2Name();
		JSONObject resJson = new JSONObject();
		for(MsBusiness msBusiness : msBusinessList) {
			String categoryId = businessCategoryIdMap.get(msBusiness.getBusinessId());
			if(categoryId == null) {
				logger.warn("Failed to get categoryId for business: " + msBusiness.getBusinessId());
			}
			String categoryName = categoryId2NameMap.get(categoryId);
			if(categoryName == null) {
				logger.warn("category not exist, id: " + categoryId);
				continue;
			}
			JSONArray jsonArray = resJson.getJSONArray(categoryName);
			if(jsonArray == null) {
				jsonArray = new JSONArray();
				resJson.put(categoryName, jsonArray);
			}
			JSONObject businessJson = new JSONObject();
			businessJson.put(MsBusinessTable.FIELD_BUSINESS_ID, msBusiness.getBusinessId());
			businessJson.put(MsBusinessTable.FIELD_BUSINESS_NAME, msBusiness.getBusinessName());
			jsonArray.add(businessJson);
		}

		return FastJsonUtil.sucess("", resJson);
	}

	@Override
	public JSONObject searchMerchant(String json) {
		// TODO Auto-generated method stub
		try {
			return manageBusinessBL.searchMerchant(json);
		} catch(Throwable th) {
			logger.error("Failed to search merchant", th);
			return FastJsonUtil.error("-1", "检索商家失败");
		}
	}

	@Override
	public JSONObject existBusinessName(String json) {
		// TODO Auto-generated method stub
		try{
			if(! manageBusinessBL.existBusinessName(json)) {
				return FastJsonUtil.sucess("");
			}
			return FastJsonUtil.error(ErrorCode.ALREADY_EXIST, "业务名称已经存在");
		} catch(Throwable th) {
			logger.error("Failed to check business name exist", th);
			return FastJsonUtil.error("-1", "检查业务名称是否存在失败"); 
		}
	}

	@Override
	public JSONObject existBusinessCode(String json) {
		// TODO Auto-generated method stub
		try{
			if(! manageBusinessBL.existBusinessCode(json)) {
				return FastJsonUtil.sucess("");
			}
			return FastJsonUtil.error(ErrorCode.ALREADY_EXIST, "业务简称已经存在");
		} catch(Throwable th) {
			logger.error("Failed to check business code exist", th);
			return FastJsonUtil.error("-1", "检查业务简称是否存在失败"); 
		}
	}

	@Override
	public JSONObject getBusinessByName(String businessName) {
		// TODO Auto-generated method stub
		try{
			return manageBusinessBL.getBusinessByName(businessName);
		} catch(Throwable th) {
			logger.error("Failed to get business");
			return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取业务信息失败");
		}
	}

	@Override
	public JSONObject getBusinessByCode(String businessCode) {
		// TODO Auto-generated method stub
		try{
			return manageBusinessBL.getBusinessByCode(businessCode);
		} catch(Throwable th) {
			logger.error("Failed to get business");
			return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取业务信息失败");
		}
	}

	@Override
	public JSONObject getBusinessById(int businessId) {
		// TODO Auto-generated method stub
		try{
			return manageBusinessBL.getBusinessById(businessId);
		} catch(Throwable th) {
			logger.error("Failed to get business");
			return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取业务信息失败");
		}
	}

	@Override
	public JSONObject getBusinessByAppId(int appId) {
		// TODO Auto-generated method stub
		try{
			return manageBusinessBL.getBusinessByAppId(appId);
		} catch(Throwable th) {
			logger.error("Failed to get business");
			return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取业务信息失败");
		}
	}

}
