package ms.luna.biz.sc.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import ms.luna.biz.cons.LunaRoleCategoryExtra;
import ms.luna.biz.dao.custom.LunaUserRoleDAO;
import ms.luna.biz.dao.custom.model.LunaUserRole;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.bl.ManageBusinessBL;
import ms.luna.biz.sc.ManageBusinessService;
import ms.luna.biz.util.FastJsonUtil;

import java.util.Map;

/**
 * 
 * Copyright (C) 2015 - 2016 Microscene Inc., All Rights Reserved.
 *
 * @author: shawn@visualbusiness.com
 * @Date: Mar 30, 2016
 *
 */

@Service("manageBusinessService")
public class ManageBusinessServiceImpl implements ManageBusinessService {
	
	private final static Logger logger = Logger.getLogger(ManageBusinessServiceImpl.class);

	@Autowired 
	private ManageBusinessBL manageBusinessBL;
	@Autowired
	private LunaUserRoleDAO lunaUserRoleDAO;
	
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

		LunaUserRole loginUserRole = lunaUserRoleDAO.readUserRoleInfo(loginUserId);
		LunaUserRole slaveUserRole = lunaUserRoleDAO.readUserRoleInfo(slaveUserId);
		if(loginUserRole == null || slaveUserRole == null) {
			return FastJsonUtil.error(ErrorCode.NOT_FOUND, "用户不存在");
		}

		Map<String, Object> extra = loginUserRole.getExtra();
		String type = extra.get("type").toString();
		if(! type.equals(LunaRoleCategoryExtra.TYPE_BUSINESS)) {
			// current user might not have business
			logger.warn(String.format("no business for current user[%s], type[%s] ", loginUserId, type));
			return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "当前用户没有业务权限");
		}

		return FastJsonUtil.sucess("", JSON.toJSON(extra));
	}

	@Override
	public JSONObject getBusinessForSelect(JSONObject jsonObject) {
		String userId = jsonObject.getString("userId");
		if(StringUtils.isBlank(userId)) {
			return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "参数不合法");
		}
		LunaUserRole lunaUserRole = lunaUserRoleDAO.readUserRoleInfo(userId);
		if(lunaUserRole == null) {
			return FastJsonUtil.error(ErrorCode.NOT_FOUND, "用户不存在");
		}
		Map<String, Object> extra = lunaUserRole.getExtra();
		String type = extra.get("type").toString();
		if(! type.equals(LunaRoleCategoryExtra.TYPE_BUSINESS)) {
			// current user might not have business
			logger.warn(String.format("no business for current user[%s], type[%s] ", userId, type));
			return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "当前用户没有业务权限");
		}

		return FastJsonUtil.sucess("", JSON.toJSON(extra));
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
