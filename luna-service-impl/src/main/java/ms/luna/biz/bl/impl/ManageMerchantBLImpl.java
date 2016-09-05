package ms.luna.biz.bl.impl;

import java.util.Date;
import java.util.List;

import ms.biz.common.CloudConfig;
import ms.biz.common.ServiceConfig;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.dao.custom.MsBusinessDAO;
import ms.luna.biz.dao.model.*;
import ms.luna.biz.table.LunaUserTable;
import ms.luna.biz.table.MsBusinessTable;
import ms.luna.biz.table.MsCRMTable;
import ms.luna.biz.table.MsMerchantManageTable;
import ms.luna.biz.util.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.visualbusiness.gennum.service.GenNumService;

import ms.luna.biz.bl.ManageMerchantBL;
import ms.luna.biz.cons.VbConstant;
import ms.luna.biz.dao.custom.MsMerchantManageDAO;
import ms.luna.biz.dao.custom.MsUserPwDAO;
import ms.luna.biz.dao.custom.model.MerchantsParameter;
import ms.luna.biz.dao.custom.model.MerchantsResult;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author Greek
 * @date create time：2016年3月23日 下午8:09:03
 * @version 1.0
 */
@Transactional(rollbackFor = Exception.class)
@Service("manageMerchantBL")
public class ManageMerchantBLImpl implements ManageMerchantBL {

	@Autowired
	private GenNumService genNumService;

	@Autowired
	private MsMerchantManageDAO msMerchantManageDAO;
 
	@Autowired
	private MsUserPwDAO msUserPwDAO;

	@Autowired
	private MsBusinessDAO msBusinessDAO;

	@Override
	public JSONObject createMerchant(String json) {
		// 创建商户和创建业务合并——创建商户
		JSONObject param = JSONObject.parseObject(json);
		MsMerchantManage msMerchantManage = JSONObject.toJavaObject(param, MsMerchantManage.class);
		String merchant_nm = msMerchantManage.getMerchantNm(); // 商户名字
		String salesman_nm = msMerchantManage.getSalesmanNm(); // 业务员名字
		String businessName = FastJsonUtil.getString(param, "business_name");
		String businessCode = FastJsonUtil.getString(param, "business_code");
		String createUser = FastJsonUtil.getString(param, "create_user");

		// 检测业务员是否存在
		if (!isLunaNmExit(salesman_nm)) {
			return FastJsonUtil.error("4", "业务员不存在！salesman_nm:" + salesman_nm);
		}
		// 检测商户是否重名
		if (isMerchantNmExit(merchant_nm)) {
			return FastJsonUtil.error("3", "商户重名(下手慢了)！merchant_nm:" + merchant_nm);
		}
		// 检查业务是否重名
		if(existBusiness(businessName, businessCode)) {
			return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "下手慢了,业务名称或简称已经存在");
		}
		// 根据业务员名字得到业务员id
		String salesman_id = msUserPwDAO.selectByPrimaryKey(salesman_nm).getUniqueId();// 业务员id
		String merchant_id = UUIDGenerator.generateUUID();
		msMerchantManage.setSalesmanId(salesman_id);
		msMerchantManage.setMerchantId(merchant_id);
		msMerchantManage.setRegistHhmmss(new Date());
		msMerchantManage.setUpHhmmss(new Date());
		msMerchantManage.setDelFlg(VbConstant.DEL_FLG.未删除);

		msMerchantManageDAO.insertSelective(msMerchantManage);
		// 创建业务
		this.createBusiness(businessName, businessCode, merchant_id, createUser);

		return FastJsonUtil.sucess("success");
	}


	@Override
	public JSONObject registMerchant(String json) {
		JSONObject param = JSONObject.parseObject(json);
		MsMerchantManage msMerchantManage = JSONObject.toJavaObject(param, MsMerchantManage.class);
		String merchant_nm = msMerchantManage.getMerchantNm();
		String status_id = VbConstant.MERCHANT_STATUS.CODE.待处理 + ""; // 处理状态
		String merchant_id = UUIDGenerator.generateUUID();

		// 检测商户是否重名
		if (isMerchantNmExit(merchant_nm)) {
			return FastJsonUtil.error("1", "商户重名(下手慢了)！merchant_nm:" + merchant_nm);
		}
		msMerchantManage.setStatusId(status_id);
		msMerchantManage.setMerchantId(merchant_id);
		msMerchantManage.setDelFlg(VbConstant.DEL_FLG.未删除);
		msMerchantManage.setRegistHhmmss(new Date());
		msMerchantManage.setUpHhmmss(new Date());
        msMerchantManage.setTradeStatus(MsMerchantManageTable.TRADE_STATUS_NORMAL);

        msMerchantManageDAO.insertSelective(msMerchantManage);
		return FastJsonUtil.sucess("新商户创建成功！");
	}

    @Override
    public JSONObject signAgreement(JSONObject jsonObject) {
        Integer businessId = jsonObject.getInteger(MsBusinessTable.FIELD_BUSINESS_ID);
        MsBusiness msBusiness = msBusinessDAO.selectByPrimaryKey(businessId);
        if (msBusiness == null) {
            return FastJsonUtil.error(ErrorCode.NOT_FOUND, "业务ID不存在");
        }
        MsMerchantManage msMerchantManage = msMerchantManageDAO.selectByPrimaryKey(msBusiness.getMerchantId());
        msMerchantManage.setTradeStatus(MsMerchantManageTable.TRADE_STATUE_ON);
        return FastJsonUtil.sucess("success");
    }

    @Override
    public JSONObject getMerchantTradeStatus(String json) {
        JSONObject param = JSONObject.parseObject(json);
        Integer businessId = param.getInteger(LunaUserTable.FIELD_ID);
        //TODO 中间表查出商户ID
        //TODO 传入商户ID
        //MsMerchantManage msMerchantManage = msMerchantManageDAO.selectByPrimaryKey(msBusiness.getMerchantId());
        JSONObject result = new JSONObject();
        //result.put(MsMerchantManageTable.FIELD_TRADE_STATUS, msMerchantManage.getTradeStatus());
        return FastJsonUtil.sucess("success", result);
    }

    @Override
    public JSONObject changeMerchantTradeStatus(String json) {
        JSONObject param = JSONObject.parseObject(json);
        String merchantId = param.getString(MsMerchantManageTable.FIELD_MERCHANT_ID);
        MsMerchantManage msMerchantManage = msMerchantManageDAO.selectByPrimaryKey(merchantId);
        msMerchantManage.setTradeStatus(param.getInteger(MsMerchantManageTable.FIELD_TRADE_STATUS));
        return FastJsonUtil.sucess("success");
    }

    @Override
    public JSONObject loadMerchants(String json) {

		JSONObject param = JSONObject.parseObject(json);
		MerchantsParameter merchantsParameter = new MerchantsParameter();

		Integer limit = param.getInteger("limit");
		Integer offset = param.getInteger("offset");
		merchantsParameter.setMin(offset);
		merchantsParameter.setMax(limit);
		merchantsParameter.setRange("true");

		if (param.containsKey("like_filter_nm")) {
			String likeFilterNm = param.getString("like_filter_nm");
			likeFilterNm = "%" + likeFilterNm.toLowerCase() + "%";
			merchantsParameter.setLikeFilterNm(likeFilterNm);
		}

		List<MerchantsResult> list = null;
		int total = msMerchantManageDAO.countMerchants(merchantsParameter);
		if (total > 0) {
			list = msMerchantManageDAO.selectMerchants(merchantsParameter);
		}
		JSONArray merchants = JSONArray.parseArray("[]");
		if (list != null) {
			for (MerchantsResult merchantsResult : list) {
				JSONObject merchant = JSONObject.parseObject("{}");
				merchant.put(MsCRMTable.FIELD_MERCHANT_ID, merchantsResult.getMerchant_id());
				merchant.put(MsCRMTable.FIELD_MERCHANT_NM, merchantsResult.getMerchant_nm());
				merchant.put(MsCRMTable.FIELD_CATEGORY_ID, merchantsResult.getCategory_id());
				merchant.put(MsCRMTable.FIELD_CONTACT_NM, merchantsResult.getContact_nm());
				merchant.put(MsCRMTable.FIELD_CONTACT_PHONENUM, merchantsResult.getContact_phonenum());
				merchant.put(MsCRMTable.FIELD_SALESMAN_NM, merchantsResult.getSalesman_nm());
				Byte status_id = Byte.parseByte(merchantsResult.getStatus_id());
				merchant.put(MsCRMTable.FIELD_STATUS, VbConstant.MERCHANT_STATUS.ConvertStauts(status_id));
				merchant.put(MsCRMTable.FIELD_PROVINCE_ID, merchantsResult.getProvince_id());
				merchant.put(MsCRMTable.FIELD_CITY_ID, merchantsResult.getCity_id());
				merchant.put(MsCRMTable.FIELD_DEL_FLG, merchantsResult.getDel_flg());
				merchant.put(MsCRMTable.FIELD_BUSINESS_ID, merchantsResult.getBusiness_id());
				merchant.put(MsCRMTable.FIELD_BUSINESS_NAME, merchantsResult.getBusiness_name());
				merchant.put(MsCRMTable.FIELD_BUSINESS_CODE, merchantsResult.getBusiness_code());
				merchant.put(MsCRMTable.FIELD_COUNTY_ID, merchantsResult.getCounty_id());
				merchant.put(MsCRMTable.FIELD_CATEGORY_NM, merchantsResult.getCategory_nm());
				merchants.add(merchant);
			}
		}

		JSONObject data = new JSONObject();
		data.put("total", total);
		data.put("rows", merchants);
		return FastJsonUtil.sucess("检索成功！", data);
		//注：新加入province_id，city_id和county_id，作用：编辑商户时的地理位置加载
	}

	@Override
	// 目前没有删除用户选项
	public JSONObject deleteMerchantById(String json) {
		JSONObject param = JSONObject.parseObject(json);
		String merchant_id = param.getString("merchant_id");
		String salesman_id = param.getString("salesman_id");

		MsMerchantManage msMerchantManage = msMerchantManageDAO.selectByPrimaryKey(merchant_id);
		if(msMerchantManage == null){
			return FastJsonUtil.error("2", "商户id不存在,merchant_id:" + merchant_id);
		}

		// 只有业务员和登录账户相同时才可删除
		if (msMerchantManage.getSalesmanId().equals(salesman_id)) {
			msMerchantManageDAO.deleteByPrimaryKey(merchant_id);
			return FastJsonUtil.sucess("成功删除商户信息！merchant_id:" + merchant_id);
		}
		return FastJsonUtil.error("1", "非本商户业务员无权删除！salesman_id:" + salesman_id + ", merchant_id:" + merchant_id);
	}

	@Override
	public JSONObject loadMerchantById(String json) {

		JSONObject param = JSONObject.parseObject(json);
		String merchant_id = param.getString("merchant_id");

		JSONObject merchant = JSONObject.parseObject("{}");
		MsMerchantManage msMerchantManage = msMerchantManageDAO.selectByPrimaryKey(merchant_id);
		if(msMerchantManage == null) {
			return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "invalid merchant id: " + merchant_id);
		}

		merchant.put(MsCRMTable.FIELD_MERCHANT_ID, msMerchantManage.getMerchantId());
		merchant.put(MsCRMTable.FIELD_MERCHANT_NM, msMerchantManage.getMerchantNm());
		merchant.put(MsCRMTable.FIELD_MERCHANT_PHONENUM, msMerchantManage.getMerchantPhonenum());
		merchant.put(MsCRMTable.FIELD_CATEGORY_ID, msMerchantManage.getCategoryId());
		merchant.put(MsCRMTable.FIELD_PROVINCE_ID, msMerchantManage.getProvinceId());
		merchant.put(MsCRMTable.FIELD_CITY_ID, msMerchantManage.getCityId());
		merchant.put(MsCRMTable.FIELD_MERCHANT_ADDR, msMerchantManage.getMerchantAddr());
		merchant.put(MsCRMTable.FIELD_MERCHANT_INFO, msMerchantManage.getMerchantInfo());
		merchant.put(MsCRMTable.FIELD_CONTACT_NM, msMerchantManage.getContactNm());
		merchant.put(MsCRMTable.FIELD_CONTACT_PHONENUM, msMerchantManage.getContactPhonenum());
		merchant.put(MsCRMTable.FIELD_CONTACT_MAIL, msMerchantManage.getContactMail());
		merchant.put(MsCRMTable.FIELD_SALESMAN_ID, msMerchantManage.getSalesmanId());
		merchant.put(MsCRMTable.FIELD_SALESMAN_NM, msMerchantManage.getSalesmanNm());
		merchant.put(MsCRMTable.FIELD_STATUS_ID, msMerchantManage.getStatusId());
		merchant.put(MsCRMTable.FIELD_LAT, msMerchantManage.getLat());
		merchant.put(MsCRMTable.FIELD_LNG, msMerchantManage.getLng());
		merchant.put(MsCRMTable.FIELD_COUNTY_ID, msMerchantManage.getCountyId());
		merchant.put(MsCRMTable.FIELD_RESOURCE_CONTENT, msMerchantManage.getResourceContent());

		// 获取业务名称
		MsBusinessCriteria msBusinessCriteria = new MsBusinessCriteria();
		MsBusinessCriteria.Criteria criteria = msBusinessCriteria.createCriteria();
		criteria.andMerchantIdEqualTo(merchant_id);
		List<MsBusiness> msBusinessList = msBusinessDAO.selectByCriteria(msBusinessCriteria);

		String business_code = "";
		String business_name = "";
		Integer business_id = null;
		if(msBusinessList != null && msBusinessList.size() != 0) {
			business_code = msBusinessList.get(0).getBusinessCode();
			business_name = msBusinessList.get(0).getBusinessName();
			business_id = msBusinessList.get(0).getBusinessId();
		}
		merchant.put("business_code", business_code);
		merchant.put("business_name", business_name);
		merchant.put("business_id", business_id);
		return FastJsonUtil.sucess("success", merchant);
	}

	/**
	 * @param json
	 * @return
	 */
	@Override
	public JSONObject updateMerchant(String json) {
		JSONObject param = JSONObject.parseObject(json);
		MsMerchantManage msMerchantManage = JSONObject.toJavaObject(param, MsMerchantManage.class);
		String merchant_id = msMerchantManage.getMerchantId();
		String merchant_nm = msMerchantManage.getMerchantNm(); // 商户名字
		String salesman_nm = msMerchantManage.getSalesmanNm(); // 业务员名字
		String create_user = param.getString("create_user");
		// 检测商户是否重名
		if (isMerchantNmExit(merchant_id,merchant_nm)) {
			return FastJsonUtil.error("3", "重名，下手慢了！merchant_nm:" + merchant_nm);
		}
		// 检测业务员是否存在
		if (!isLunaNmExit(salesman_nm)) {
			return FastJsonUtil.error("4", "无此业务员！salesman_nm:" + salesman_nm);
		}
		//根据业务员名字得到业务员id
		String salesman_id = msUserPwDAO.selectByPrimaryKey(salesman_nm).getUniqueId();
		msMerchantManage.setSalesmanId(salesman_id);
		msMerchantManage.setUpHhmmss(new Date());

		// 商户基本信息更新
		MsMerchantManageCriteria msMerchantManageCriteria = new MsMerchantManageCriteria();
		MsMerchantManageCriteria.Criteria criteria = msMerchantManageCriteria.createCriteria();
		criteria.andMerchantIdEqualTo(merchant_id);
		msMerchantManageDAO.updateByCriteriaSelective(msMerchantManage, msMerchantManageCriteria);

		// 业务更新 -- 首先判断业务是否存在
		String businessName = FastJsonUtil.getString(param, MsCRMTable.FIELD_BUSINESS_NAME);
		String businessCode = FastJsonUtil.getString(param, MsCRMTable.FIELD_BUSINESS_CODE);
		// 检查业务是否重名
		if(existBusiness(merchant_id)) {
			this.updateBusiness(businessName, businessCode, merchant_id);
		} else {
			this.createBusiness(businessName, businessCode, merchant_id, create_user);
		}

		return FastJsonUtil.sucess("success");
	}

	@Override
	public JSONObject closeMerchantById(String json) {
		JSONObject param = JSONObject.parseObject(json);
		String merchant_id = param.getString("merchant_id");

		MsMerchantManage msMerchant = msMerchantManageDAO.selectByPrimaryKey(merchant_id);
		if(msMerchant == null){
			return FastJsonUtil.error("1", "商户id不存在,merchant_id:" + merchant_id);
		}

		MsMerchantManage msMerchantManage = new MsMerchantManage();
		msMerchantManage.setMerchantId(merchant_id);
		msMerchantManage.setDelFlg("1");// 非物理删除
		msMerchantManageDAO.updateByPrimaryKeySelective(msMerchantManage);
		return FastJsonUtil.sucess("success");
	}

	@Override
	public JSONObject openMerchantById(String json) {
		JSONObject param = JSONObject.parseObject(json);
		String merchant_id = param.getString("merchant_id");

		MsMerchantManage msMerchantManage = new MsMerchantManage();
		msMerchantManage.setMerchantId(merchant_id);
		msMerchantManage.setDelFlg("0");
		msMerchantManageDAO.updateByPrimaryKeySelective(msMerchantManage);
		return FastJsonUtil.sucess("success");
	}

	// ---------------------------------------------------------

	/**
	 * 检测业务员是否存在
	 *
	 * @param salesman_nm
	 * @return
	 */
	private boolean isLunaNmExit(String salesman_nm) {
		MsUserPw msUserPw = null;
		msUserPw = msUserPwDAO.selectByPrimaryKey(salesman_nm);
		if(msUserPw == null){
			return false;
		}
		return true;
	}

	/**
	 * 检测商户名字是否重名
	 *
	 * @param merchant_nm
	 * @return
	 */
	private boolean isMerchantNmExit(String merchant_nm) {
		MsMerchantManageCriteria msMerchantManageCriteria = new MsMerchantManageCriteria();
		MsMerchantManageCriteria.Criteria criteria = msMerchantManageCriteria.createCriteria();
		criteria.andMerchantNmEqualTo(merchant_nm);
		int count = msMerchantManageDAO.countByCriteria(msMerchantManageCriteria);
		if (count == 0)
			return false;
		return true;
	}

	/**
	 * 检测当前商户名字是否和其他商户名字重名
	 *
	 * @param merchant_id 商户id
	 * @param merchant_nm 商户名称
	 * @return
	 */
	private boolean isMerchantNmExit(String merchant_id, String merchant_nm) {
		boolean flag = isMerchantNmExit(merchant_nm);
		if(flag == true){//该用户名存在，则检测是否是当前merchant_id对应的name(即没有改动)
			MsMerchantManageCriteria msMerchantManageCriteria = new MsMerchantManageCriteria();
			MsMerchantManageCriteria.Criteria criteria = msMerchantManageCriteria.createCriteria();
			criteria.andMerchantNmEqualTo(merchant_nm).andMerchantIdEqualTo(merchant_id);
			int count = msMerchantManageDAO.countByCriteria(msMerchantManageCriteria);
			if (count == 0)//不是merchant_id对应的name
				return true;
			else
				return false;
		}
		return false;
	}

	@Override
	public Integer countMerchantNm(String merchantNm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject isSalesmanNmExit(String json) {
		JSONObject param = JSONObject.parseObject(json);
		String salesman_nm = param.getString("salesman_nm");
		MsUserPw msUserPw = null;
		msUserPw = msUserPwDAO.selectByPrimaryKey(salesman_nm);
		if(msUserPw == null){
			return FastJsonUtil.error("1","业务员不存在");
		}
		return FastJsonUtil.sucess("业务员存在");
	}

	@Override
	public JSONObject isAddedMerchantNmEist(String json){
		JSONObject param = JSONObject.parseObject(json);
		String merchant_nm = param.getString("merchant_nm");
		MsMerchantManageCriteria msMerchantManageCriteria = new MsMerchantManageCriteria();
		MsMerchantManageCriteria.Criteria criteria = msMerchantManageCriteria.createCriteria();
		criteria.andMerchantNmEqualTo(merchant_nm);
		int count = msMerchantManageDAO.countByCriteria(msMerchantManageCriteria);
		if (count == 0)
			return FastJsonUtil.error("1","商户名称不存在");
		return FastJsonUtil.sucess("商户名称存在");
	}

	@Override
	public JSONObject isEditedMerchantNmEist(String json){
		JSONObject param = JSONObject.parseObject(json);
		JSONObject result = isAddedMerchantNmEist(json);
		if(result.getString("code").equals("0")){//该用户名存在，则检测是否是当前merchant_id对应的name(即没有改动)
			String merchant_id = param.getString("merchant_id");
			String merchant_nm = param.getString("merchant_nm");
			MsMerchantManageCriteria msMerchantManageCriteria = new MsMerchantManageCriteria();
			MsMerchantManageCriteria.Criteria criteria = msMerchantManageCriteria.createCriteria();
			criteria.andMerchantNmEqualTo(merchant_nm).andMerchantIdEqualTo(merchant_id);
			int count = msMerchantManageDAO.countByCriteria(msMerchantManageCriteria);
			if (count == 0)//不是merchant_id对应的name
				return FastJsonUtil.sucess("商户名称存在");
		}
		return FastJsonUtil.error("1", "商户名称不存在");
	}

	@Override
	public JSONObject getMerchantEmail(String id) {
		MsMerchantManage msMerchantManage = msMerchantManageDAO.selectByPrimaryKey(id);
		if(msMerchantManage == null ) {
			return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "merchant id not exist.");
		}
		String email = msMerchantManage.getContactMail();
		if(StringUtils.isBlank(email)) {
			return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "contact mail not exist");
		}
		JSONObject data = new JSONObject();
		data.put(MsCRMTable.FIELD_CONTACT_MAIL, email);
		return FastJsonUtil.sucess("success", data);
	}

	private void createBusiness(String businessName, String businessCode, String merchant_id, String createUser) {
		String statInfo = MtaWrapper.createApp(businessCode, CloudConfig.H5_TYPE, CloudConfig.H5_DOMAIN, ServiceConfig.getLong(ServiceConfig.MTA_QQ));
		// str:{"code":0,"info":"success","data":{"app_id":500032916,"secret_key":"4ead8d782c516966b64424ab52500412"}}
		if(statInfo == null) {
//			return FastJsonUtil.error("-1", "创建腾讯统计账号失败");
			throw new RuntimeException("创建腾讯统计账号失败");
		}

		JSONObject jsonStat = JSONObject.parseObject(statInfo);
		MsBusiness business = new MsBusiness();
		if ("0".equals(jsonStat.getString("code"))) {
			JSONObject statData = jsonStat.getJSONObject("data");
			business.setStatId(statData.getIntValue("app_id"));
			business.setSecretKey(statData.getString("secret_key"));
		} else {
			MsLogger.error("Failed to create qq stats account");
			throw new RuntimeException("创建腾讯统计账号失败");
		}

		// 假设前端已经做了数据校验
		business.setBusinessName(businessName);
		business.setBusinessCode(businessCode);
		business.setMerchantId(merchant_id);
		business.setCreateUser(createUser);
		msBusinessDAO.insertSelective(business);
	}

	private void updateBusiness(String businessName, String businessCode, String merchant_id) {
		MsBusinessCriteria msBusinessCriteria = new MsBusinessCriteria();
		MsBusinessCriteria.Criteria criteria2 = msBusinessCriteria.createCriteria();
		criteria2.andMerchantIdEqualTo(merchant_id);
		MsBusiness msBusiness = new MsBusiness();
		msBusiness.setBusinessName(businessName);
		msBusiness.setBusinessCode(businessCode);
		msBusinessDAO.updateByCriteriaSelective(msBusiness, msBusinessCriteria);
	}

	private boolean existBusiness(String businessName, String businessCode) {
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

	private boolean existBusiness(String merchant_id) {
		MsBusinessCriteria msBusinessCriteria = new MsBusinessCriteria();
		MsBusinessCriteria.Criteria criteria = msBusinessCriteria.createCriteria();
		criteria.andMerchantIdEqualTo(merchant_id);
		List<MsBusiness> msBusinessList = msBusinessDAO.selectByCriteria(msBusinessCriteria);
		if(msBusinessList == null || msBusinessList.size() == 0) {
			return false;
		}
		return true;
	}
}
