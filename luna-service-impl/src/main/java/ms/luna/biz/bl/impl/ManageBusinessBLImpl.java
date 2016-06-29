package ms.luna.biz.bl.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ms.biz.common.CloudConfig;
import ms.biz.common.CommonQueryParam;
import ms.biz.common.ServiceConfig;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.bl.ManageBusinessBL;
import ms.luna.biz.dao.custom.MsBusinessDAO;
import ms.luna.biz.dao.custom.MsCategoryDAO;
import ms.luna.biz.dao.custom.MsMerchantManageDAO;
import ms.luna.biz.dao.custom.MsShowAppDAO;
import ms.luna.biz.dao.custom.model.MerchantsParameter;
import ms.luna.biz.dao.custom.model.MerchantsResult;
import ms.luna.biz.dao.custom.model.MsBusinessParameter;
import ms.luna.biz.dao.custom.model.MsBusinessResult;
import ms.luna.biz.dao.model.MsBusiness;
import ms.luna.biz.dao.model.MsBusinessCriteria;
import ms.luna.biz.dao.model.MsCategory;
import ms.luna.biz.dao.model.MsCategoryCriteria;
import ms.luna.biz.table.MsBusinessTable;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MtaWrapper;
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
	private MsCategoryDAO msCategoryDAO;
	
	@Autowired
	private MsShowAppDAO msShowAppDAO;
	
	public boolean existBusiness(String json) {
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
		
		MsBusinessParameter msBusinessParameter = new MsBusinessParameter();
		if(StringUtils.isNotEmpty(min) && StringUtils.isNotEmpty(max)) {
			msBusinessParameter.setRange(true);
			msBusinessParameter.setMax(Integer.parseInt(max));
			msBusinessParameter.setMin(Integer.parseInt(min));
		}
		List<MsCategory> msCategories = msCategoryDAO.selectByCriteria(new MsCategoryCriteria());
		Map<String, String> categoryId2Name = new HashMap<String, String>();
		for(MsCategory category : msCategories) {
			categoryId2Name.put(category.getCategoryId(), category.getNmZh());
		}
		
		int total = msBusinessDAO.readTotalBusinessCount();
		List<MsBusinessResult> results = msBusinessDAO.selectBusinessWithFilter(msBusinessParameter);
		JSONObject data = JSONObject.parseObject("{}");;
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
		MtaWrapper.deleteApp(msBusiness.getStatId(), msBusiness.getSecretKey());
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

}
