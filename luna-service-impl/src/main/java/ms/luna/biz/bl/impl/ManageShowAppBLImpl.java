package ms.luna.biz.bl.impl;

import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.bson.BsonDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import ms.biz.common.CommonQueryParam;
import ms.biz.common.ErrorCode;
import ms.biz.common.ServiceConfig;
import ms.luna.biz.bl.ManageShowAppBL;
import ms.luna.biz.cons.VbConstant.MsShowAppConfig;
import ms.luna.biz.dao.custom.MsBusinessDAO;
import ms.luna.biz.dao.custom.MsShowAppDAO;
import ms.luna.biz.dao.custom.MsShowPageDAO;
import ms.luna.biz.dao.custom.model.MsBusinessParameter;
import ms.luna.biz.dao.custom.model.MsBusinessResult;
import ms.luna.biz.dao.custom.model.MsShowAppParameter;
import ms.luna.biz.dao.custom.model.MsShowAppResult;
import ms.luna.biz.dao.custom.model.MsShowPage;
import ms.luna.biz.dao.model.MsBusiness;
import ms.luna.biz.dao.model.MsShowApp;
import ms.luna.biz.dao.model.MsShowAppCriteria;
import ms.luna.biz.model.MsUser;
import ms.luna.biz.util.COSUtil;
import ms.luna.biz.util.DateUtil;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.VbUtility;
import ms.luna.biz.util.VelocityUtil;


@Transactional(rollbackFor=Exception.class)
@Service("manageShowAppBL")
public class ManageShowAppBLImpl implements ManageShowAppBL {

	private final static Logger logger = Logger.getLogger(ManageShowAppBLImpl.class);
	private final static String LOGO_PATH = "/data1/luna/resources/logo.jpg";

	@Autowired
	private MsShowAppDAO msShowAppDAO;
	@Autowired
	private MsBusinessDAO msBusinessDAO;
	@Autowired
	private MsShowPageDAO msShowPageDAO;
	
	private String showPageUriTemplate = "/app/%d"; 
	private String businessUriTemplate = "/business/%s";

	@Override
	public JSONObject loadApps(String json) {

		JSONObject jsonObject = JSONObject.parseObject(json);
		MsShowAppParameter parameter = new MsShowAppParameter();
		MsShowAppCriteria example = new MsShowAppCriteria();
		MsShowAppCriteria.Criteria criteria = example.createCriteria();
		
		String keyword = FastJsonUtil.getString(jsonObject, "keyword", "");
		String min = FastJsonUtil.getString(jsonObject, CommonQueryParam.LIMIT_MIN);
		String max = FastJsonUtil.getString(jsonObject, CommonQueryParam.LIMIT_MAX);
		if(StringUtils.isNotEmpty(keyword)) {
			keyword = "%" + keyword + "%";
			parameter.setKeyword(keyword);
			criteria.andAppNameLike(keyword);
		}
		if(StringUtils.isNotEmpty(min) && StringUtils.isNotEmpty(max)) {
			parameter.setRange(true);
			parameter.setMax(Integer.parseInt(max));
			parameter.setMin(Integer.parseInt(min));
		}
		
		JSONObject data = JSONObject.parseObject("{}");
		int total = 0;
		try {
			List<MsShowAppResult> results = msShowAppDAO.selectShowAppWithFilter(parameter);
			if(results != null) {
				JSONArray rows = new JSONArray();
				String msWebUrl = ServiceConfig.getString(ServiceConfig.MS_WEB_URL);
				for(MsShowAppResult result : results) {
					JSONObject row = JSONObject.parseObject("{}");
					row.put(MsShowAppDAO.FIELD_APP_ID, result.getAppId());
					row.put(MsShowAppDAO.FIELD_APP_NAME,result.getAppName());
					row.put(MsShowAppDAO.FIELD_APP_CODE, result.getAppCode());
					String indexUrl = msWebUrl + String.format(showPageUriTemplate, result.getAppId());
					row.put(MsShowAppDAO.FIELD_APP_ADDR, indexUrl);
					row.put(MsShowAppDAO.FIELD_CREATE_TIME, DateUtil.format(new Date(result.getRegisthhmmss().getTime()), DateUtil.FORMAT_yyyy_MM_dd_HH_MM_SS));
					row.put(MsShowAppDAO.FIELD_UPDATE_TIME, DateUtil.format(new Date(result.getUphhmmss().getTime()), DateUtil.FORMAT_yyyy_MM_dd_HH_MM_SS));
					row.put(MsShowAppDAO.FIELD_OWNER, result.getOwner());
					row.put(MsShowAppDAO.FIELD_APP_STATUS, result.getAppStatus());
					row.put(MsShowAppDAO.FIELD_BUSINESS_ID, result.getBusinessId());
					row.put(MsBusinessDAO.FIELD_BUSINESS_NAME, result.getBusinessName() == null ? "" : result.getBusinessName());
					rows.add(row);
				}
				data.put("rows", rows);
			}
			total = msShowAppDAO.countByCriteria(example);
		} catch (Exception e) {
			logger.error("Failed to load apps", e);
		}
		
		data.put("total", total);
		return FastJsonUtil.sucess("检索成功！", data);
	}

	@Override
	public JSONObject createApp(String json) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = JSONObject.parseObject(json);
		String appName = FastJsonUtil.getString(jsonObject, "app_name");
		int businessId = FastJsonUtil.getInteger(jsonObject, "business_id");
		//用于存储用户微景展资源路径
		String appCode = appName;
		String owner = FastJsonUtil.getString(jsonObject, "owner");
		
		if(existAppName(appName)) {
			return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "微景展名称已经存在");
		}
		
		MsShowApp msShowApp = new MsShowApp();
		msShowApp.setAppName(appName);
		msShowApp.setAppCode(appCode);
		msShowApp.setBusinessId(businessId);
		msShowApp.setOwner(owner);
		msShowApp.setAppStatus(MsShowAppConfig.AppStatus.NOT_AUDIT);
		
		try {
			msShowAppDAO.insertSelective(msShowApp);
			// create index page for new app
			MsShowAppCriteria msShowAppCriteria = new MsShowAppCriteria();
			MsShowAppCriteria.Criteria criteria = msShowAppCriteria.createCriteria();
			criteria.andAppNameEqualTo(appName);
			int appId = msShowAppDAO.selectIdByName(appName);
			MsShowPage page = new MsShowPage();
			page.setAppId(appId);
			page.setPageName("首页");
			page.setPageCode("index");
			page.setPageContent(new HashMap<String, Object>());
			page.setPageOrder(1);
			page.setUpdateUser(owner);
			msShowPageDAO.createOnePage(page);
			
		} catch (Exception e) {
			logger.error("Failed to insert app: " + json, e);
		}
		return FastJsonUtil.sucess("成功添加微景展");
		
	}
	
	private boolean existAppName(int appId, String appName) {
		MsShowAppCriteria msShowAppCriteria = new MsShowAppCriteria();
		MsShowAppCriteria.Criteria criteria = msShowAppCriteria.createCriteria();
		criteria.andAppNameEqualTo(appName)
				.andAppIdNotEqualTo(appId);
		int count = msShowAppDAO.countByCriteria(msShowAppCriteria);
		return count != 0;
	}
	
	@Override
	public JSONObject updateApp(String json) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = JSONObject.parseObject(json);
		int appId = FastJsonUtil.getInteger(jsonObject, "app_id", -1);
		if(appId < 0) {
			return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "非法微景展Id");
		}
		String appName = FastJsonUtil.getString(jsonObject, "app_name");
		if(existAppName(appId, appName)) {
			return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "微景展名称已经存在");
		}
		String appCode = appName;
		int businessId = FastJsonUtil.getInteger(jsonObject, "business_id");
		String updateUser = FastJsonUtil.getString(jsonObject, "update_user");
		MsShowApp msShowApp = new MsShowApp();
		msShowApp.setAppId(appId);
		msShowApp.setAppName(appName);
		msShowApp.setAppCode(appCode);
		msShowApp.setBusinessId(businessId);
		msShowApp.setUpdatedByWjnm(updateUser);
		try {
			msShowAppDAO.updateByPrimaryKeySelective(msShowApp);
		} catch(Exception e) {
			logger.error("Failed to update app: " + json, e);
			return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "更新失败");
			
		}
		
		return FastJsonUtil.sucess("更新成功");
		
	}
	
	/**
	 * 需要考虑是否真删除，真删除还需要删除相关的微景资源,目前是非真删除
	 * @param json
	 * @return
	 */
	@Override
	public JSONObject deleteApp(String json) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = JSONObject.parseObject(json);
		int appId = FastJsonUtil.getInteger(jsonObject, "app_id", -1);
		if(appId < 0) {
			return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "非法微景展Id");
		}
		try {
			msShowAppDAO.deleteByPrimaryKey(appId);
			msShowPageDAO.deletePagesByAppId(appId);
		} catch (Exception e) {
			logger.error("Failed to delete app: " + json, e);
			return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "删除微景展失败");
		}
		return FastJsonUtil.sucess("成功删除微景展！");
	}


	@Override
	public JSONObject searchBusiness(String json) {
		// TODO Auto-generated method stub
		
		JSONObject jsonObject = JSONObject.parseObject(json);
		
		//暂时不会使用国家，目前只有中国
		String countryId = FastJsonUtil.getString(jsonObject, "country_id");
		String provinceId = FastJsonUtil.getString(jsonObject, "province_id");
		String cityId = FastJsonUtil.getString(jsonObject, "city_id");
		String countyId = FastJsonUtil.getString(jsonObject, "county_id");
		String categoryId = FastJsonUtil.getString(jsonObject, "category_id");
		String keyword = FastJsonUtil.getString(jsonObject, "keyword");
		MsBusinessParameter parameter = new MsBusinessParameter();
		
		if(StringUtils.isNotEmpty(provinceId)) {
			parameter.setProvinceId(provinceId.trim());
		}
		if(StringUtils.isNotEmpty(cityId)) {
			parameter.setCityId(cityId.trim());
		}
		if(StringUtils.isNotEmpty(countyId)) {
			parameter.setCountyId(countyId.trim());
		}
		if(StringUtils.isNotEmpty(categoryId)) {
			parameter.setCategoryId(categoryId);
		}
		if(StringUtils.isNotEmpty(keyword)) {
			parameter.setKeyword("%" + keyword + "%");
		}
						
		List<MsBusinessResult> results = msBusinessDAO.selectBusinessWithFilter(parameter);
		JSONObject data = new JSONObject();
		if(results != null) {
			JSONArray rows = new JSONArray();
			for(MsBusinessResult result : results) {
				JSONObject row = JSONObject.parseObject("{}");
				int businessId = result.getBusinessId();
				String businessName = result.getBusinessName();
				row.put("business_id", businessId);
				row.put("business_name", businessName);
				rows.add(row);
			}
			data.put("rows", rows);
		}
		return FastJsonUtil.sucess("检索成功", data);
	}

	@Override
	public boolean existAppName(String appName) {
		// TODO Auto-generated method stub
		MsShowAppCriteria msShowAppCriteria = new MsShowAppCriteria();
		MsShowAppCriteria.Criteria criteria = msShowAppCriteria.createCriteria();
		criteria.andAppNameEqualTo(appName);
		int count = msShowAppDAO.countByCriteria(msShowAppCriteria);
		return count != 0;
	}
	
	private boolean existOnlineAppByAppId(int appId) {
		MsShowApp record = msShowAppDAO.selectByPrimaryKey(appId);
		if(record == null) {
			return false;
		}
		int businessId = record.getBusinessId();
		MsShowAppCriteria example = new MsShowAppCriteria();
		MsShowAppCriteria.Criteria criteria = example.createCriteria();
		criteria.andBusinessIdEqualTo(businessId)
				.andAppIdNotEqualTo(appId)
				.andAppStatusEqualTo(MsShowAppConfig.AppStatus.ONLINE);
		int count = msShowAppDAO.countByCriteria(example);
		
		return count > 0;
	}

	@Override
	public JSONObject existOnlineApp(int appId) {
		// TODO Auto-generated method stub
		
		if(existOnlineAppByAppId(appId)) {
			return FastJsonUtil.sucess("", true);
		}
		return FastJsonUtil.sucess("", false);
	}
	
	/**
	 * 发布步骤：
	 * 发布微景展静态内容
	 * 变更数据表中业务当前使用的微景展
	 * copy当前微景展内容到应用对应的目录下 ？？（需要保持访问路径不变，软连跳转不太友好，拷贝操作又有些重）
	 * 
	 */
	public JSONObject publishApp(String json) {
		
		JSONObject jsonObject = JSONObject.parseObject(json);
		int appId = FastJsonUtil.getInteger(jsonObject, "app_id", -1);
		if(appId < 0) {
			return FastJsonUtil.error(-1, "appId不合法");
		}
		int forceFlag = FastJsonUtil.getInteger(jsonObject, "force", -1);
		MsShowApp record = msShowAppDAO.selectByPrimaryKey(appId);
		if(record == null) {
			return FastJsonUtil.error(-1, "appId不合法");
		}
		
		int businessId = record.getBusinessId();
		//自己已在线的可以重新发布并覆盖
//		if(record.getAppStatus() == MsShowAppConfig.AppStatus.ONLINE) {
//			return FastJsonUtil.error("-1", "此微景展已经在线");
//		}
		
		if(forceFlag == 1) {
			MsShowAppCriteria example = new MsShowAppCriteria();
			MsShowAppCriteria.Criteria criteria = example.createCriteria();
			//不管是否已有在线的微景展，都操作发布
			criteria.andBusinessIdEqualTo(businessId)
					.andAppStatusEqualTo(MsShowAppConfig.AppStatus.ONLINE);
			record = new MsShowApp();
			record.setAppStatus(MsShowAppConfig.AppStatus.OFFLINE);
			msShowAppDAO.updateByCriteriaSelective(record, example);
			
			example.clear();
			criteria = example.createCriteria();
			criteria.andAppIdEqualTo(appId);
			record = new MsShowApp();
			record.setAppStatus(MsShowAppConfig.AppStatus.ONLINE);
			record.setPublishTime(new Date());
			msShowAppDAO.updateByCriteriaSelective(record, example);
			
		} else {
			//判断是否存在
			if(existOnlineAppByAppId(appId)) {
				return FastJsonUtil.error(ErrorCode.ALREADY_EXIST, "已存在在线微景展");
			}
			//不存在则上线此微景展
			MsShowAppCriteria example = new MsShowAppCriteria();
			MsShowAppCriteria.Criteria criteria = example.createCriteria();
			criteria.andAppIdEqualTo(appId);
			record = new MsShowApp();
			record.setAppStatus(MsShowAppConfig.AppStatus.ONLINE);
			record.setPublishTime(new Date());
			msShowAppDAO.updateByCriteriaSelective(record, example);
		}
		// 更新business对应的appId
		MsBusiness business = new MsBusiness();
		business.setBusinessId(businessId);
		business.setAppId(appId);
		msBusinessDAO.updateByPrimaryKeySelective(business);
		
		business = msBusinessDAO.selectByPrimaryKey(businessId);
		
		String msWebUrl = ServiceConfig.getString(ServiceConfig.MS_WEB_URL);
		String businessUrl = msWebUrl + String.format(businessUriTemplate, business.getBusinessCode());
		String businessDir = String.format("/%s/business/%s", COSUtil.getLunaH5RootPath(), business.getBusinessCode());
		
		// TODO:已经存在二维码不一定每次都重新生成，url是固定的
		String qrImgUrl = generateQR(businessUrl, businessDir, "QRCode.jpg");
		
		if(StringUtils.isBlank(qrImgUrl)) {
			return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "生成二维码失败");
		}
		JSONObject data = new JSONObject();
		data.put("QRImg", qrImgUrl);
		data.put("link", businessUrl);
		
		return FastJsonUtil.sucess("发布成功", data);
	}
	
	@Deprecated
	private void generateHtml2Cos(int appId) {
		
		MsShowApp msShowApp = msShowAppDAO.selectByPrimaryKey(appId);
		List<MsShowPage> msShowPages = msShowPageDAO.readAllPageDetailByAppId(appId);
		String appCode = msShowApp.getAppCode();
		String indexFileName = "index.html";
		String appDir = String.format("/%s/%s", COSUtil.getLunaH5RootPath(), appCode);
		String indexFileFullPath = "";
		Template template = VelocityUtil.getTemplate("page.vm");
		for(MsShowPage page: msShowPages) {
			JSONObject jsonPage = (JSONObject) JSON.toJSON(page);
			VelocityContext context = new VelocityContext();
			context.put("page", jsonPage.toString());
			StringWriter sw = new StringWriter();
			template.merge(context, sw);
			String fileName = page.getPageCode() + ".html";
			if(page.getPageOrder() == 1) {
				fileName = indexFileName;
			}			
			com.alibaba.fastjson.JSONObject result = null;
			try {
				result = COSUtil.getInstance().upload2CloudDirect(sw.toString().getBytes(), appDir, fileName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("Failed to upload to cloud", e);
			}
			
			if(page.getPageOrder() == 1) {
				indexFileFullPath = result.getJSONObject("data").getString(COSUtil.ACCESS_URL);
			}			
		}
	}

	@Override
	public JSONObject generateShowApp(int appId) {
		// TODO Auto-generated method stub
		if(appId < 0) {
			return FastJsonUtil.error("-1", "appId不合法");
		}
		
		MsShowApp msShowApp = msShowAppDAO.selectByPrimaryKey(appId);
		if (msShowApp == null) {
			return FastJsonUtil.error("-1", "微景展不存在");
		}
		
		if(MsShowAppConfig.AppStatus.DELETE == msShowApp.getAppStatus()) {
			return FastJsonUtil.error("-1", "微景展已经被删除");
		}
		
		String msWebUrl = ServiceConfig.getString(ServiceConfig.MS_WEB_URL);
		String indexUrl = msWebUrl + String.format(showPageUriTemplate, appId);
		String appDir = String.format("/%s/app/%s", COSUtil.getLunaH5RootPath(), msShowApp.getAppCode());
		String qrImgUrl = generateQR(indexUrl, appDir, "QRCode.jpg");
		
		if(StringUtils.isBlank(qrImgUrl)) {
			return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "生成二维码失败");
		}
		JSONObject data = new JSONObject();
		data.put("QRImg", qrImgUrl);
		return FastJsonUtil.sucess("", data);
	}
	
	private void deleteCosResource(String appCode) {
		String appDir = String.format("/%s/app/%s", COSUtil.getLunaH5RootPath(), appCode);
//		COSUtil.getInstance().deleteRecursive();
		
	}
	
	private String generateQR(String url, String cosDir, String cosFileName) {
		
		byte[] bytes = VbUtility.createQRCode(url, LOGO_PATH);
		if(bytes == null) {
			return null;
		}
		com.alibaba.fastjson.JSONObject result;
		try {
			result = COSUtil.getInstance().upload2CloudDirect(bytes, cosDir, "QRCode.jpg");
			if("0".equals(result.getString("code"))) {
				return result.getJSONObject("data").getString(COSUtil.ACCESS_URL);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Failed to generate QR: ", e);
		}
		
		return null;
	}

	@Override
	public JSONObject getSettingOfApp(int appId) {
		// TODO Auto-generated method stub
		if(appId < 0) {
			return FastJsonUtil.error(-1, "appId不合法");
		}
		MsShowAppCriteria example = new MsShowAppCriteria();
		MsShowAppCriteria.Criteria criteria = example.createCriteria();
		criteria.andAppIdEqualTo(appId);
		MsShowApp msShowApp = msShowAppDAO.selectByPrimaryKey(appId);
		JSONObject data = JSONObject.parseObject("{}");
		data.put(MsShowAppDAO.FIELD_APP_NAME, msShowApp.getAppName());
		data.put(MsShowAppDAO.FIELD_PIC_THUMB, msShowApp.getPicThumb());
		data.put(MsShowAppDAO.FIELD_NOTE, msShowApp.getNote());
		data.put(MsShowAppDAO.FIELD_SHARE_INFO_TITLE, msShowApp.getShareInfoTitle());
		data.put(MsShowAppDAO.FIELD_SHARE_INFO_DES, msShowApp.getShareInfoDes());
		data.put(MsShowAppDAO.FIELD_SHARE_INFO_PIC, msShowApp.getShareInfoPic());
		
		return FastJsonUtil.sucess("", data);
	}

	@Override
	public JSONObject saveSettingOfApp(String json, MsUser msUser) {
		// TODO Auto-generated method stub
		
		JSONObject jsonObject = JSONObject.parseObject(json);
		
		int appId = FastJsonUtil.getInteger(jsonObject, MsBusinessDAO.FIELD_APP_ID, -1);
		if(appId < 0) {
			return FastJsonUtil.error(-1, "appId不合法");
		}
		
		String appName = FastJsonUtil.getString(jsonObject, MsShowAppDAO.FIELD_APP_NAME);
		String picThumb = FastJsonUtil.getString(jsonObject, MsShowAppDAO.FIELD_PIC_THUMB);
		String note = FastJsonUtil.getString(jsonObject, MsShowAppDAO.FIELD_NOTE);
		String shareInfoTitle = FastJsonUtil.getString(jsonObject, MsShowAppDAO.FIELD_SHARE_INFO_TITLE);
		String shareInfoDes = FastJsonUtil.getString(jsonObject, MsShowAppDAO.FIELD_SHARE_INFO_DES);
		String shareInfoPic = FastJsonUtil.getString(jsonObject, MsShowAppDAO.FIELD_SHARE_INFO_PIC);
		
		MsShowApp msShowApp = new MsShowApp();
		msShowApp.setAppId(appId);
		msShowApp.setAppName(appName);
		msShowApp.setPicThumb(picThumb);
		msShowApp.setNote(note);
		msShowApp.setShareInfoTitle(shareInfoTitle);
		msShowApp.setShareInfoDes(shareInfoDes);
		msShowApp.setShareInfoPic(shareInfoPic);
		
		msShowAppDAO.updateByPrimaryKeySelective(msShowApp);
		return FastJsonUtil.sucess("保存设置成功");
		
	}
}
