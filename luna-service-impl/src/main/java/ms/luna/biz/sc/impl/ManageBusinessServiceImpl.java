package ms.luna.biz.sc.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import ms.biz.common.ErrorCode;
import ms.luna.biz.bl.ManageBusinessBL;
import ms.luna.biz.sc.ManageBusinessService;
import ms.luna.biz.util.FastJsonUtil;

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
