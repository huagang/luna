package ms.luna.biz.bl.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ms.biz.common.CloudConfig;
import ms.biz.common.CommonQueryParam;
import ms.biz.common.ServiceConfig;
import ms.luna.biz.cons.DbConfig;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.bl.ManageBusinessBL;
import ms.luna.biz.cons.LunaRoleCategoryExtra;
import ms.luna.biz.dao.custom.*;
import ms.luna.biz.dao.custom.model.*;
import ms.luna.biz.dao.model.*;
import ms.luna.biz.table.LunaRoleCategoryTable;
import ms.luna.biz.table.LunaUserTable;
import ms.luna.biz.table.MsBusinessTable;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MtaWrapper;
import ms.luna.cache.MerchantCategoryCache;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional(rollbackFor = Exception.class)
@Service("manageBusinessBL")
public class ManageBusinessBLImpl implements ManageBusinessBL {

	private final static Logger logger = Logger.getLogger(ManageBusinessBLImpl.class);
	@Autowired
	private MsMerchantManageDAO msMerchantManageDAO;
	
	@Autowired
	private MsBusinessDAO msBusinessDAO;

	@Autowired
	private MerchantCategoryCache merchantCategoryCache;
	
	@Autowired
	private MsShowAppDAO msShowAppDAO;
	@Autowired
	private LunaUserRoleDAO lunaUserRoleDAO;

	private boolean existBusiness(String json) {
		JSONObject jsonObject = JSONObject.parseObject(json);
		String businessName = FastJsonUtil.getString(jsonObject, "business_name");
		String businessCode = FastJsonUtil.getString(jsonObject, "business_code");
		MsBusinessCriteria msBusinessCriteria = new MsBusinessCriteria();
		MsBusinessCriteria.Criteria criteria1 = msBusinessCriteria.createCriteria();
		criteria1.andBusinessNameEqualTo(businessName);
		MsBusinessCriteria.Criteria criteria2 = msBusinessCriteria.or();
		criteria2.andBusinessCodeEqualTo(businessCode);
		
		List<MsBusiness> msBusinesses = msBusinessDAO.selectByCriteria(msBusinessCriteria);
		if(msBusinesses == null || msBusinesses.size() == 0) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean existBusinessName(String json) {
		JSONObject jsonObject = JSONObject.parseObject(json);
		String businessName = FastJsonUtil.getString(jsonObject, "business_name");
		MsBusinessCriteria msBusinessCriteria = new MsBusinessCriteria();
		MsBusinessCriteria.Criteria criteria = msBusinessCriteria.createCriteria();
		criteria.andBusinessNameEqualTo(businessName);
		List<MsBusiness> msBusinesses = msBusinessDAO.selectByCriteria(msBusinessCriteria);
		if(msBusinesses == null || msBusinesses.size() == 0) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean existBusinessCode(String json) {
		JSONObject jsonObject = JSONObject.parseObject(json);
		String businessCode = FastJsonUtil.getString(jsonObject, "business_code");
		MsBusinessCriteria msBusinessCriteria = new MsBusinessCriteria();	
		MsBusinessCriteria.Criteria criteria = msBusinessCriteria.createCriteria();
		criteria.andBusinessNameEqualTo(businessCode);
		List<MsBusiness> msBusinesses = msBusinessDAO.selectByCriteria(msBusinessCriteria);
		if(msBusinesses == null || msBusinesses.size() == 0) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public JSONObject createBusiness(String json) {
		// TODO Auto-generated method stub
		if(existBusiness(json)) {
			return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "业务名称或简称已经存在");
		}
		JSONObject jsonObject = JSONObject.parseObject(json);
		String businessName = FastJsonUtil.getString(jsonObject, "business_name");
		String businessCode = FastJsonUtil.getString(jsonObject, "business_code");
		String merchantId = FastJsonUtil.getString(jsonObject, "merchant_id");
		String createUser = FastJsonUtil.getString(jsonObject, "create_user");
		
		// 假设前端已经做了数据校验
		MsBusiness business = new MsBusiness();
		business.setBusinessName(businessName);
		business.setBusinessCode(businessCode);
		business.setMerchantId(merchantId);
		business.setCreateUser(createUser);
		try {
			String statInfo = MtaWrapper.createApp(businessCode, CloudConfig.H5_TYPE, CloudConfig.H5_DOMAIN, ServiceConfig.getLong(ServiceConfig.MTA_QQ));
			// str:{"code":0,"info":"success","data":{"app_id":500032916,"secret_key":"4ead8d782c516966b64424ab52500412"}}
			if(statInfo == null) {
				return FastJsonUtil.error("-1", "创建腾讯统计账号失败");
			}
			JSONObject jsonStat = JSONObject.parseObject(statInfo);
			if ("0".equals(jsonStat.getString("code"))) {
				JSONObject statData = jsonStat.getJSONObject("data");
				business.setStatId(statData.getIntValue("app_id"));
				business.setSecretKey(statData.getString("secret_key"));
			} else {
				logger.error("Failed to create qq stats account");
				return FastJsonUtil.error("-1", "创建腾讯统计账号失败");
			}
		} catch (Exception e) {
			logger.error("Failed to create business", e);
			return FastJsonUtil.error("-1", e.getMessage());
		}
		msBusinessDAO.insertSelective(business);
		
		return FastJsonUtil.sucess("新商户创建成功！");
		
	}

	@Override
	public JSONObject searchMerchant(String json) {
		
		JSONObject jsonObject = JSONObject.parseObject(json);
		
		//暂时不会使用国家，目前只有中国
		String countryId = FastJsonUtil.getString(jsonObject, "country_id");
		String provinceId = FastJsonUtil.getString(jsonObject, "province_id");
		String cityId = FastJsonUtil.getString(jsonObject, "city_id");
		String countyId = FastJsonUtil.getString(jsonObject, "county_id");
		String keyword = FastJsonUtil.getString(jsonObject, "keyword");
		
		MerchantsParameter parameter = new MerchantsParameter();
		if(StringUtils.isNotEmpty(provinceId)) {
			parameter.setProvinceId(provinceId.trim());
		}
		if(StringUtils.isNotEmpty(cityId)) {
			parameter.setCityId(cityId.trim());
		}
		if(StringUtils.isNotEmpty(countyId)) {
			parameter.setCountyId(countyId.trim());
		}
		if(StringUtils.isNotEmpty(keyword)) {
			parameter.setLikeFilterNm("%" + keyword + "%");
		}
		logger.debug(parameter.toString());
						
		List<MerchantsResult> merchantsResults = msMerchantManageDAO.searchMerchantWithFilter(parameter);
		JSONObject data = new JSONObject();
		if(merchantsResults != null) {
			JSONArray rows = new JSONArray();
			for(MerchantsResult merchant : merchantsResults) {
				JSONObject row = JSONObject.parseObject("{}");
				String merchantId = merchant.getMerchant_id();
				String merchantName = merchant.getMerchant_nm();
				row.put("merchant_id",merchantId);
				row.put("merchant_name", merchantName);
				rows.add(row);
			}
			data.put("rows", rows);
		}
		return FastJsonUtil.sucess("检索成功", data);
	}

	@Override
	public JSONObject loadBusinesses(String json) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = JSONObject.parseObject(json);
		String min = FastJsonUtil.getString(jsonObject, CommonQueryParam.LIMIT_MIN);
		String max = FastJsonUtil.getString(jsonObject, CommonQueryParam.LIMIT_MAX);
		String uniqueId = jsonObject.getString(LunaUserTable.FIELD_ID);
		LunaUserRole lunaUserRole = lunaUserRoleDAO.readUserRoleInfo(uniqueId);
		if(lunaUserRole == null) {
			logger.warn("user not found, unique_id: " + uniqueId);
			return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "用户不存在");
		}
		Map<String, Object> extra = lunaUserRole.getExtra();
		String type = extra.get("type").toString();
		if(! type.equals(LunaRoleCategoryExtra.TYPE_BUSINESS)) {
			// current user might not have business
			logger.warn(String.format("no business for current user[%s], type[%s] ", uniqueId, type));
			return FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "没有业务权限");
		}
		MsBusinessParameter msBusinessParameter = new MsBusinessParameter();
		List<Integer> businessIdList = (List<Integer>) extra.get("value");
		if(businessIdList.size() == 1 && businessIdList.get(0) == DbConfig.BUSINESS_ALL) {

		} else if(businessIdList.size() > 0){
			msBusinessParameter.setBusinessIds(businessIdList);
		} else {
			logger.warn(String.format("no business for current user[%s], type[%s] ", uniqueId, type));
			return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "没有业务权限");
		}

		if(StringUtils.isNotEmpty(min) && StringUtils.isNotEmpty(max)) {
			msBusinessParameter.setRange(true);
			msBusinessParameter.setMax(Integer.parseInt(max));
			msBusinessParameter.setMin(Integer.parseInt(min));
		}

		Map<String, String> categoryId2Name = merchantCategoryCache.getCategoryId2Name();
		int total = msBusinessDAO.selectBusinessCountWithFilter(msBusinessParameter);
		List<MsBusinessResult> results = msBusinessDAO.selectBusinessWithFilter(msBusinessParameter);
		JSONObject data = new JSONObject();
		if(results != null) {
			JSONArray rows = new JSONArray();
			for(MsBusinessResult result : results) {
				JSONObject row = JSONObject.parseObject("{}");
				row.put("business_id", result.getBusinessId());
				row.put("business_code", result.getBusinessCode());
				row.put("business_name", result.getBusinessName());
				row.put("business_category", categoryId2Name.get(result.getCategory()));
				row.put("merchant_name", result.getMerchantName());
				rows.add(row);
			}
			data.put("rows", rows);
		}
		data.put("total", total);
		return FastJsonUtil.sucess("检索成功", data);
	}

	@Override
	public JSONObject updateBusinessById(String json) {
		
		// TODO Auto-generated method stub
		JSONObject jsonObject = JSONObject.parseObject(json);
		int businessId = FastJsonUtil.getInteger(jsonObject, "business_id", -1);
		if(businessId < 0) {
			return FastJsonUtil.error("-1", "非法业务id");
		}
		String businessName = FastJsonUtil.getString(jsonObject, "business_name");
		String businessCode = FastJsonUtil.getString(jsonObject, "business_code");
		MsBusiness msBusiness = new MsBusiness();
		msBusiness.setBusinessId(businessId);
		msBusiness.setBusinessName(businessName);
		msBusiness.setBusinessCode(businessCode);
		try{
			msBusinessDAO.updateByPrimaryKeySelective(msBusiness);
		} catch (Exception e) {
			logger.error("更新业务失败, businessId: " + businessId, e);
			return FastJsonUtil.error("-1", "更新失败");
		}
		return FastJsonUtil.sucess("更新业务信息成功");
	}

	@Override
	public JSONObject deleteBusinessById(int id) {
		// TODO Auto-generated method stub
		MsBusiness msBusiness = msBusinessDAO.selectByPrimaryKey(id);
		String ret = MtaWrapper.deleteApp(msBusiness.getStatId(), msBusiness.getSecretKey());
		if(ret == null) {
			logger.error("Failed to delete qq stats for business: " + id);
		} else {
			JSONObject jsonObject = JSON.parseObject(ret);
			if(0 != jsonObject.getIntValue("code")) {
				logger.error("Failed to delete qq stats for business: " +
						id + ", info " + jsonObject.getString("info"));
			}

		}
		msBusinessDAO.deleteByPrimaryKey(id);
		return FastJsonUtil.sucess("成功删除业务信息！");
	}

	private JSONObject toJson(MsBusiness business) {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(MsBusinessTable.FIELD_BUSINESS_ID, business.getBusinessId());
		jsonObject.put(MsBusinessTable.FIELD_BUSINESS_NAME, business.getBusinessName());
		jsonObject.put(MsBusinessTable.FIELD_BUSINESS_CODE, business.getBusinessCode());
		jsonObject.put(MsBusinessTable.FIELD_APP_ID, business.getAppId());
		jsonObject.put(MsBusinessTable.FIELD_STAT_ID, business.getStatId());
		
		return jsonObject;
		
	}
	@Override
	public JSONObject getBusinessByName(String businessName) {
		// TODO Auto-generated method stub
		MsBusinessCriteria msBusinessCriteria = new MsBusinessCriteria();
		MsBusinessCriteria.Criteria criteria = msBusinessCriteria.createCriteria();
		criteria.andBusinessNameEqualTo(businessName);
		List<MsBusiness> businesses = msBusinessDAO.selectByCriteria(msBusinessCriteria);
		if(businesses.size() > 0) {
			MsBusiness business = businesses.get(0);
			return FastJsonUtil.sucess("", toJson(business));
		}		
		return FastJsonUtil.error(ErrorCode.NOT_FOUND, "业务名称不存在");
	}

	@Override
	public JSONObject getBusinessByCode(String businessCode) {
		// TODO Auto-generated method stub
		MsBusinessCriteria msBusinessCriteria = new MsBusinessCriteria();
		MsBusinessCriteria.Criteria criteria = msBusinessCriteria.createCriteria();
		criteria.andBusinessCodeEqualTo(businessCode);
		List<MsBusiness> businesses = msBusinessDAO.selectByCriteria(msBusinessCriteria);
		if(businesses != null && businesses.size() > 0) {
			MsBusiness business = businesses.get(0);
			return FastJsonUtil.sucess("", toJson(business));
		}
		
		return FastJsonUtil.error(ErrorCode.NOT_FOUND, "业务简称不存在");
	}

	@Override
	public JSONObject getBusinessById(int businessId) {
		// TODO Auto-generated method stub
		MsBusiness business = msBusinessDAO.selectByPrimaryKey(businessId);
		if(business != null) {
			return FastJsonUtil.sucess("", toJson(business));
		}
		return FastJsonUtil.error(ErrorCode.NOT_FOUND, "业务Id不存在");
	}

	@Override
	public JSONObject getBusinessByAppId(int appId) {
		// TODO Auto-generated method stub
		MsBusinessCriteria msBusinessCriteria = new MsBusinessCriteria();
		MsBusinessCriteria.Criteria criteria = msBusinessCriteria.createCriteria();
		criteria.andAppIdEqualTo(appId);
		List<MsBusiness> businesses = msBusinessDAO.selectByCriteria(msBusinessCriteria);
		if(businesses != null && businesses.size() > 0){
			return FastJsonUtil.sucess("", toJson(businesses.get(0)));
		}
		return FastJsonUtil.error(ErrorCode.NOT_FOUND, "业务不存在");
	}

	@Override
	public JSONObject checkBusinessNameExist(String business_name, String merchant_id) {
		MsBusinessCriteria msBusinessCriteria = new MsBusinessCriteria();
		MsBusinessCriteria.Criteria criteria = msBusinessCriteria.createCriteria();
		Integer count;
		if(merchant_id == null) {// 创建
			criteria.andBusinessNameEqualTo(business_name);
			count = msBusinessDAO.countByCriteria(msBusinessCriteria);
		} else {// 编辑
			criteria.andBusinessNameEqualTo(business_name).andMerchantIdNotEqualTo(merchant_id);
			count = msBusinessDAO.countByCriteria(msBusinessCriteria);
		}
		if(count > 0) {
			return FastJsonUtil.error(ErrorCode.ALREADY_EXIST, "business name exists");
		}
		return FastJsonUtil.sucess("business name does not exit");
	}

	@Override
	public JSONObject checkBusinessCodeExist(String business_code, String merchant_id) {
		MsBusinessCriteria msBusinessCriteria = new MsBusinessCriteria();
		MsBusinessCriteria.Criteria criteria = msBusinessCriteria.createCriteria();
		Integer count;
		if(merchant_id == null) {// 创建
			criteria.andBusinessCodeEqualTo(business_code);
			count = msBusinessDAO.countByCriteria(msBusinessCriteria);
		} else {// 编辑
			criteria.andBusinessCodeEqualTo(business_code).andMerchantIdNotEqualTo(merchant_id);
			count = msBusinessDAO.countByCriteria(msBusinessCriteria);
		}
		if(count > 0) {
			return FastJsonUtil.error(ErrorCode.ALREADY_EXIST, "business code exists");
		}
		return FastJsonUtil.sucess("business code does not exit");
	}
}
