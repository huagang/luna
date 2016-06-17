package ms.luna.biz.bl.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
import ms.luna.biz.dao.model.MsMerchantManage;
import ms.luna.biz.dao.model.MsMerchantManageCriteria;
import ms.luna.biz.dao.model.MsUserPw;
import ms.luna.biz.util.CharactorUtil;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;
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
	
	@Override
	public JSONObject createMerchant(String json) {

		JSONObject param = JSONObject.parseObject(json);

		String merchant_nm = param.getString("merchant_nm"); // 商户名字
		String merchant_phonenum = param.getString("merchant_phonenum"); // 商户电话
		String category_id = param.getString("category_id"); // 商户类型id
		String province_id = param.getString("province_id"); // 省份id
		String city_id = param.getString("city_id"); // 城市id
		String merchant_addr = param.getString("merchant_addr"); // 商户地址
		String merchant_info = param.getString("merchant_info"); // 商户概况
		String contact_nm = param.getString("contact_nm"); // 联系人姓名
		String contact_phonenum = param.getString("contact_phonenum"); // 联系人电话
		String contact_mail = param.getString("contact_mail"); // 联系人邮箱
		String county_id = null;
		String resource_content = null;
		String lat = null;
		String lng = null;
		if(param.containsKey("lat")){
			lat = param.getString("lat"); // 纬度
		}
		if(param.containsKey("lng")){
			lng = param.getString("lng"); // 经度
		}
		if(param.containsKey("county_id")){
			county_id = param.getString("county_id"); // 区/县id
		}
		if(param.containsKey("resource_content")){
			resource_content = param.getString("resource_content");
		}

		String salesman_id = ""; // 业务员名字
		String salesman_nm = ""; // 业务员id
		String status_id = VbConstant.MERCHANT_STATUS.CODE.待处理 + ""; // 处理状态
		if (param.containsKey("salesman_nm")) {
			salesman_nm = param.getString("salesman_nm"); // 业务员名字
			// 检测业务员是否存在
			boolean flag = isLunaNmExit(salesman_nm);
			if (flag == false) {
				return FastJsonUtil.error("2", "无此业务员！");
			}
			//根据业务员名字得到业务员id
			salesman_id = msUserPwDAO.selectByPrimaryKey(salesman_nm).getUniqueId();
		}
		if (param.containsKey("status_id")) {
			status_id = param.getString("status_id"); // 商户状态
		}
		
		String merchant_id = genNumService.generateNum("MERCH");
		if (CharactorUtil.isEmpyty(merchant_id)) {
			MsLogger.debug("编号生成失败：[CRM]");
			throw new RuntimeException("编号生成失败：[CRM]");
		}
		
		// 检测商户是否重名
		boolean flag = isMerchantNmExit(merchant_nm);
		if (flag == true) {
			return FastJsonUtil.error("1", "商户重名(下手慢了)！");
		}

		Date date = new Date();
		MsMerchantManage msMerchantManage = new MsMerchantManage();
		msMerchantManage.setMerchantId(merchant_id);
		msMerchantManage.setMerchantNm(merchant_nm);
		msMerchantManage.setMerchantPhonenum(merchant_phonenum);
		msMerchantManage.setCategoryId(category_id);
		msMerchantManage.setProvinceId(province_id);
		msMerchantManage.setCityId(city_id);
		msMerchantManage.setMerchantAddr(merchant_addr);
		msMerchantManage.setMerchantInfo(merchant_info);
		msMerchantManage.setContactNm(contact_nm);
		msMerchantManage.setContactPhonenum(contact_phonenum);
		msMerchantManage.setContactMail(contact_mail);
		msMerchantManage.setStatusId(status_id);
		msMerchantManage.setSalesmanId(salesman_id);
		msMerchantManage.setSalesmanNm(salesman_nm);
		msMerchantManage.setDelFlg(VbConstant.DEL_FLG.未删除);
		msMerchantManage.setRegistHhmmss(date);
		msMerchantManage.setUpHhmmss(date);
		msMerchantManage.setUpdatedByUniqueId(salesman_id);
		if(lat != null){
			msMerchantManage.setLat(new BigDecimal(lat));
		}
		if(lng != null){
			msMerchantManage.setLng(new BigDecimal(lng));
		}
		if(county_id != null){
			msMerchantManage.setCountyId(county_id);
		}
		if(resource_content != null){
			msMerchantManage.setResourceContent(resource_content);
		}

		msMerchantManageDAO.insertSelective(msMerchantManage);
		return FastJsonUtil.sucess("新商户创建成功！");
	}

	@Override
	public JSONObject loadMerchants(String json) {

		JSONObject param = JSONObject.parseObject(json);
		MerchantsParameter merchantsParameter = new MerchantsParameter();
		Integer max = null;
		Integer min = null;
		if (param.containsKey("max")) {
			max = param.getInteger("max");
		}
		if (param.containsKey("min")) {
			min = param.getInteger("min");
		}
		if (max != null || min != null) {
			merchantsParameter.setRange("true");
		}
		if (min == null) {
			min = 0;
		}
		if (max == null) {
			max = Integer.MAX_VALUE;
		}
		merchantsParameter.setMin(min);
		merchantsParameter.setMax(max);

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
				merchant.put("merchant_id", merchantsResult.getMerchant_id());
				merchant.put("merchant_nm", merchantsResult.getMerchant_nm());
				merchant.put("category_id", merchantsResult.getCategory_id());
				merchant.put("contact_nm", merchantsResult.getContact_nm());
				merchant.put("contact_phonenum", merchantsResult.getContact_phonenum());
				merchant.put("salesman_nm", merchantsResult.getSalesman_nm());
				merchant.put("status_id", merchantsResult.getStatus_id());
				merchant.put("province_id", merchantsResult.getProvince_id());
				merchant.put("city_id", merchantsResult.getCity_id());
				merchant.put("del_flg", merchantsResult.getDel_flg());
				if(merchantsResult.getCounty_id() != null){
					merchant.put("county_id", merchantsResult.getCounty_id());
				}
				// 可能出现某些category类别被删除后category_nm 为null的情况
				if( merchantsResult.getCategory_nm() != null){
					merchant.put("category_nm", merchantsResult.getCategory_nm());
				}else{
					merchant.put("category_nm", "");
				}
				merchants.add(merchant);
			}
		}

		JSONObject data = new JSONObject();
		data.put("total", total);
		data.put("merchants", merchants);
		return FastJsonUtil.sucess("检索成功！", data);
		//注：新加入province_id，city_id和county_id，作用：编辑商户时的地理位置加载
	}

	@Override
	public JSONObject deleteMerchantById(String json) {
		JSONObject param = JSONObject.parseObject(json);
		String merchant_id = param.getString("merchant_id");
		String salesman_id = param.getString("salesman_id");

		MsMerchantManage msMerchantManage = msMerchantManageDAO.selectByPrimaryKey(merchant_id);
		if(msMerchantManage == null){
			return FastJsonUtil.error("2", "商户id不存在");
		}

		// 只有业务员和登录账户相同时才可删除
		if (msMerchantManage.getSalesmanId().equals(salesman_id)) {
			msMerchantManageDAO.deleteByPrimaryKey(merchant_id);
			return FastJsonUtil.sucess("成功删除商户信息！");
		}
		return FastJsonUtil.error("1", "非本商户业务员无权删除！");
	}

	@Override
	public JSONObject loadMerchantById(String json) {

		JSONObject param = JSONObject.parseObject(json);
		String merchant_id = param.getString("merchant_id");

		MsMerchantManageCriteria msMerchantManageCriteria = new MsMerchantManageCriteria();
		MsMerchantManageCriteria.Criteria criteria = msMerchantManageCriteria.createCriteria();
		criteria.andMerchantIdLike(merchant_id);

		List<MsMerchantManage> list = null;
		JSONObject merchant = JSONObject.parseObject("{}");
		MsMerchantManage msMerchantManage = null;

		list = msMerchantManageDAO.selectByCriteria(msMerchantManageCriteria);

		if (list != null && !list.isEmpty()) {// 此处list长度为1
			msMerchantManage = list.get(0);
			merchant.put("merchant_id", msMerchantManage.getMerchantId());
			merchant.put("merchant_nm", msMerchantManage.getMerchantNm());
			merchant.put("merchant_phonenum", msMerchantManage.getMerchantPhonenum());
			merchant.put("category_id", msMerchantManage.getCategoryId());
			merchant.put("province_id", msMerchantManage.getProvinceId());
			merchant.put("city_id", msMerchantManage.getCityId());
			merchant.put("merchant_addr", msMerchantManage.getMerchantAddr());
			merchant.put("merchant_info", msMerchantManage.getMerchantInfo());
			merchant.put("contact_nm", msMerchantManage.getContactNm());
			merchant.put("contact_phonenum", msMerchantManage.getContactPhonenum());
			merchant.put("contact_mail", msMerchantManage.getContactMail());
			merchant.put("salesman_id", msMerchantManage.getSalesmanId());
			merchant.put("salesman_nm", msMerchantManage.getSalesmanNm());
			merchant.put("status_id", msMerchantManage.getStatusId());
			if(msMerchantManage.getLat() != null){
				merchant.put("lat", msMerchantManage.getLat());
			}
			if(msMerchantManage.getLng() != null){
				merchant.put("lng", msMerchantManage.getLng());
			}
			if(msMerchantManage.getCountyId() != null){
				merchant.put("county_id", msMerchantManage.getCountyId());
			}
			if(msMerchantManage.getResourceContent() != null){
				merchant.put("resource_content", msMerchantManage.getResourceContent());
			}
		}
		return FastJsonUtil.sucess("用户信息检索成功！", merchant);
	}
	
	@Override
	public JSONObject updateMerchant(String json) {
		JSONObject param = JSONObject.parseObject(json);

		String merchant_id = param.getString("merchant_id");
		String merchant_nm = param.getString("merchant_nm"); // 商户名字
		String salesman_nm = param.getString("salesman_nm"); // 业务员名字

		// 检测商户是否重名
		boolean flag = isMerchantNmExit(merchant_id,merchant_nm);
		if (flag == true) {
			return FastJsonUtil.error("1", "重名，下手慢了！");
		}
		// 检测业务员是否存在
		boolean flag2 = isLunaNmExit(salesman_nm);
		if (flag2 == false) {
			return FastJsonUtil.error("2", "无此业务员！");
		}
		//根据业务员名字得到业务员id
		String salesman_id = msUserPwDAO.selectByPrimaryKey(salesman_nm).getUniqueId();
		String merchant_phonenum = param.getString("merchant_phonenum"); // 商户电话
		String category_id = param.getString("category_id"); // 商户类型id
		String province_id = param.getString("province_id"); // 省份id
		String city_id = param.getString("city_id"); // 城市id
		String merchant_addr = param.getString("merchant_addr"); // 商户地址
		String merchant_info = param.getString("merchant_info"); // 商户概况
		String contact_nm = param.getString("contact_nm"); // 联系人姓名
		String contact_phonenum = param.getString("contact_phonenum"); // 联系人电话
		String contact_mail = param.getString("contact_mail"); // 联系人邮箱
		String status_id = param.getString("status_id");//状态码
		String county_id = null; // 区/县id
		String resource_content = null;// 图片地址
		String lat = null;
		String lng = null;
		if(param.containsKey("lat")){
			lat = param.getString("lat");  // 纬度
		}
		if(param.containsKey("lng")){
			lng = param.getString("lng");// 经度
		}
		if(param.containsKey("county_id")){
			county_id = param.getString("county_id"); // 区/县id
		}
		if(param.containsKey("resource_content")){
			resource_content = param.getString("resource_content");
		}
		
		// 取出该条数据
		MsMerchantManage msMerchantManage1 = msMerchantManageDAO.selectByPrimaryKey(merchant_id);
		MsMerchantManage msMerchantManage2 = new MsMerchantManage();
		msMerchantManage2.setMerchantId(merchant_id);
		msMerchantManage2.setMerchantNm(merchant_nm);
		msMerchantManage2.setMerchantPhonenum(merchant_phonenum);
		msMerchantManage2.setCategoryId(category_id);
		msMerchantManage2.setProvinceId(province_id);
		msMerchantManage2.setCityId(city_id);
		msMerchantManage2.setMerchantAddr(merchant_addr);
		msMerchantManage2.setMerchantInfo(merchant_info);
		msMerchantManage2.setContactNm(contact_nm);
		msMerchantManage2.setContactPhonenum(contact_phonenum);
		msMerchantManage2.setContactMail(contact_mail);
		msMerchantManage2.setStatusId(status_id);
		msMerchantManage2.setSalesmanId(salesman_id);
		msMerchantManage2.setSalesmanNm(salesman_nm);
		msMerchantManage2.setUpdatedByUniqueId(salesman_id);
		
		msMerchantManage2.setDelFlg(msMerchantManage1.getDelFlg());
		msMerchantManage2.setRegistHhmmss(msMerchantManage1.getRegistHhmmss());
		msMerchantManage2.setUpHhmmss(msMerchantManage1.getUpHhmmss());
		
		if(lat != null){
			msMerchantManage2.setLat(new BigDecimal(lat));
		} else {
			msMerchantManage2.setLat(null);
		}
		if(lng != null){
			msMerchantManage2.setLng(new BigDecimal(lng));
		} else {
			msMerchantManage2.setLng(null);
		}
		if (county_id != null) {
			msMerchantManage2.setCountyId(county_id);
		} else {
			msMerchantManage2.setCountyId(null);
		}
		if (resource_content != null) {
			msMerchantManage2.setResourceContent(resource_content);
		} else {
			msMerchantManage2.setResourceContent(null);
		}

		MsMerchantManageCriteria msMerchantManageCriteria = new MsMerchantManageCriteria();
		MsMerchantManageCriteria.Criteria criteria = msMerchantManageCriteria.createCriteria();
		criteria.andMerchantIdEqualTo(merchant_id).andUpHhmmssEqualTo(msMerchantManage1.getUpHhmmss());
		msMerchantManageDAO.updateByCriteria(msMerchantManage2, msMerchantManageCriteria);
		
//		msMerchantManageDAO.updateByPrimaryKeySelective(msMerchantManage2,msMerchantManageCriteria);
		return FastJsonUtil.sucess("更新成功！");
	}
	
	@Override
	public JSONObject closeMerchantById(String json) {
		JSONObject param = JSONObject.parseObject(json);
		String merchant_id = param.getString("merchant_id");

		MsMerchantManage msMerchant = msMerchantManageDAO.selectByPrimaryKey(merchant_id);
		if(msMerchant == null){
			return FastJsonUtil.error("1", "商户id不存在");
		}
		
		MsMerchantManage msMerchantManage = new MsMerchantManage();
		msMerchantManage.setMerchantId(merchant_id);
		msMerchantManage.setDelFlg("1");// 非物理删除
		msMerchantManageDAO.updateByPrimaryKeySelective(msMerchantManage);
		
		return FastJsonUtil.sucess("成功关闭商户！");
	}

	@Override
	public JSONObject openMerchantById(String json) {
		JSONObject param = JSONObject.parseObject(json);
		String merchant_id = param.getString("merchant_id");

		MsMerchantManage msMerchantManage = new MsMerchantManage();
		msMerchantManage.setMerchantId(merchant_id);
		msMerchantManage.setDelFlg("0");
		int count = msMerchantManageDAO.updateByPrimaryKeySelective(msMerchantManage);

		if (count == 1) {
			return FastJsonUtil.sucess("成功开启商户！");
		} else {
			throw new RuntimeException("无法根据ID关闭商户，数据库异常");
		}
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
	 * @param merchant_id当前商户id
	 * @param merchant_nm
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
		return FastJsonUtil.error("1","商户名称不存在");
	}

}
