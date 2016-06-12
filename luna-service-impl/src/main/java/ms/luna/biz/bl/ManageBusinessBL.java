package ms.luna.biz.bl;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * Copyright (C) 2015 - 2016 Microscene Inc., All Rights Reserved.
 *
 * @author: shawn@visualbusiness.com
 * @Date: Mar 30, 2016
 *
 */
public interface ManageBusinessBL {
	
	JSONObject createBusiness(String json);
	boolean existBusinessName(String json);
	boolean existBusinessCode(String json);
	
	JSONObject getBusinessByName(String businessName);
	JSONObject getBusinessByCode(String businessCode);
	JSONObject getBusinessById(int businessId);
	JSONObject getBusinessByAppId(int appId);
	
	//创建业务时根据查询条件找到商户
	JSONObject searchMerchant(String json);

	JSONObject loadBusinesses(String json);

	JSONObject updateBusinessById(String json);

	JSONObject deleteBusinessById(int id);

}
