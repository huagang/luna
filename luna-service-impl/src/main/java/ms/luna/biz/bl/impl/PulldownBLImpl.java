package ms.luna.biz.bl.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ms.luna.biz.bl.PulldownBL;
import ms.luna.biz.cons.VbConstant;
import ms.luna.biz.dao.custom.MsCategoryDAO;
import ms.luna.biz.dao.custom.MsZoneDAO;
import ms.luna.biz.dao.model.MsCategory;
import ms.luna.biz.dao.model.MsCategoryCriteria;
import ms.luna.biz.dao.model.MsZone;
import ms.luna.biz.dao.model.MsZoneCriteria;
import ms.luna.biz.util.JsonUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Transactional(rollbackFor=Exception.class)
@Service("pulldownBL")
public class PulldownBLImpl implements PulldownBL {

	@Autowired
	private MsZoneDAO msZoneDAO;

	@Autowired
	private MsCategoryDAO msCategoryDAO;
	
	static private Map<String, JSONArray> cacheCountrysData = new HashMap<String, JSONArray>() ;
	static private Map<String, JSONArray> cacheProvincesData = new HashMap<String, JSONArray>() ;
	static private Map<String, JSONArray> cacheCityData = new HashMap<String, JSONArray>() ;
	static private Map<String, JSONArray> cacheCountyData = new HashMap<String, JSONArray>();

	@Override
	public JSONObject loadCoutrys() {

		JSONObject data = JSONObject.fromObject("{}");
		JSONArray countrys = null;
		
		//TODO
		synchronized (PulldownBLImpl.class) {
			countrys = cacheCountrysData.get("100000") ;
		}
		if(countrys == null) {
			countrys = JSONArray.fromObject("[]");
			//如果没有缓存数据，则从数据库查询
			MsZoneCriteria msZoneCriteria = new MsZoneCriteria();
			msZoneCriteria.createCriteria()
			.andLevelTypeEqualTo("0")
			.andDelFlgEqualTo(VbConstant.DEL_FLG.未删除);
			msZoneCriteria.setOrderByClause("ID ASC");
			List<MsZone> list = msZoneDAO.selectByCriteria(msZoneCriteria);
			//解析查询结果
			if (list != null) {
				for (MsZone msZone : list) {
					JSONObject countrydata = JSONObject.fromObject("{}");
					countrydata.put("country_id", msZone.getId());
					countrydata.put("nm_zh", msZone.getName());
					countrys.add(countrydata);
				}
			}
			synchronized (PulldownBLImpl.class) {
				cacheCountrysData.put("100000", countrys) ;	
			}
		}

		data.put("countrys", countrys);
		return JsonUtil.sucess("检索成功", data);
	}

	@Override
	public JSONObject loadProvinces(String countryId) {
		JSONObject data = JSONObject.fromObject("{}");
		JSONArray provinces = null ;
		
		//TODO
		synchronized (PulldownBLImpl.class) {
			provinces = cacheProvincesData.get(countryId) ;
			if(provinces == null) {
				provinces = JSONArray.fromObject("[]");
				//如果没有缓存数据，则从数据库查询
				MsZoneCriteria msZoneCriteria = new MsZoneCriteria();
				msZoneCriteria.createCriteria()
				.andParentIdEqualTo(countryId)
				.andLevelTypeEqualTo("1")
				.andDelFlgEqualTo(VbConstant.DEL_FLG.未删除);
				msZoneCriteria.setOrderByClause("ID ASC");
				List<MsZone> list = msZoneDAO.selectByCriteria(msZoneCriteria);
				//解析查询结果
				if (list != null) {
					for (MsZone msZone : list) {
						JSONObject provincedata = JSONObject.fromObject("{}");
						provincedata.put("province_id", msZone.getId());
						provincedata.put("nm_zh", msZone.getName());
						provincedata.put("country_id", countryId);
						provinces.add(provincedata);
					}
				}
					cacheProvincesData.put(countryId, provinces) ;	
			}
		}
		
		data.put("provinces", provinces);
		return JsonUtil.sucess("检索成功", data);
	}

	@Override
	public JSONObject loadCitys(String provinceId) {
		JSONObject data = JSONObject.fromObject("{}");
		JSONArray citys = null ;
		
		//TODO
		synchronized (PulldownBLImpl.class) {
			citys = cacheCityData.get(provinceId) ;
		}
		if(citys == null) {
			citys = JSONArray.fromObject("[]");
			//如果没有缓存数据，则从数据库查询
			MsZoneCriteria msZoneCriteria = new MsZoneCriteria();
			msZoneCriteria.createCriteria()
			.andParentIdEqualTo(provinceId)
			.andLevelTypeEqualTo("2")
			.andDelFlgEqualTo(VbConstant.DEL_FLG.未删除);
			msZoneCriteria.setOrderByClause("ID ASC");
			List<MsZone> list = msZoneDAO.selectByCriteria(msZoneCriteria);
			//解析查询结果
			if (list != null) {
				for (MsZone msZone : list) {
					JSONObject citydata = JSONObject.fromObject("{}");
					citydata.put("city_id", msZone.getId());
					citydata.put("nm_zh", msZone.getName());
					citydata.put("province_id", provinceId);
					citys.add(citydata);
				}
			}
			synchronized (PulldownBLImpl.class) {
				cacheCityData.put(provinceId, citys) ;	
			}
		}
		

		data.put("citys", citys);
		return JsonUtil.sucess("检索成功", data);
	}
	
	@Override
	public JSONObject loadCounties(String cityId) {
		
		JSONObject data = JSONObject.fromObject("{}");
		JSONArray counties = null ;
		counties = cacheCountyData.get(cityId) ;
		if(counties == null) {
			synchronized (PulldownBLImpl.class) {
				counties = cacheCountyData.get(cityId) ;
				if(counties == null) {
					counties = JSONArray.fromObject("[]");
					//如果没有缓存数据，则从数据库查询
					MsZoneCriteria msZoneCriteria = new MsZoneCriteria();
					msZoneCriteria.createCriteria()
					.andParentIdEqualTo(cityId)
					.andLevelTypeEqualTo("3")
					.andDelFlgEqualTo(VbConstant.DEL_FLG.未删除);
					msZoneCriteria.setOrderByClause("ID ASC");
					List<MsZone> list = msZoneDAO.selectByCriteria(msZoneCriteria);
					//解析查询结果
					if (list != null) {
						for (MsZone msZone : list) {
							JSONObject countydata = JSONObject.fromObject("{}");
							countydata.put("county_id", msZone.getId());
							countydata.put("nm_zh", msZone.getName());
							counties.add(countydata);
						}
					}
					cacheCountyData.put(cityId, counties) ;	
				}
			}
		}

		data.put("counties", counties);
		return JsonUtil.sucess("检索成功", data);
	}

//	@Override
//	public JSONObject loadCategorys() {
//		VbCategoryCriteria categoryCriteria = new VbCategoryCriteria();
//		categoryCriteria.createCriteria()
//		.andDelFlgEqualTo(VbConstant.DEL_FLG.未删除);
//		categoryCriteria.setOrderByClause("NM_ZH DESC");
//		List<VbCategory> list = vbCategoryDAO.selectByCriteria(categoryCriteria);
//		JSONObject data = JSONObject.fromObject("{}");
//		JSONArray categorys = JSONArray.fromObject("[]");
//		if (list != null) {
//			for (VbCategory category : list) {
//				JSONObject categorydata = JSONObject.fromObject("{}");
//				categorydata.put("category_id", category.getCategoryId());
//				categorydata.put("nm_zh", category.getNmZh());
//				categorydata.put("nm_en", category.getNmEn());
//				categorys.add(categorydata);
//			}
//		}
//		data.put("categorys", categorys);
//		return JsonUtil.sucess("检索成功", data);
//	}

	@Override
	public JSONObject loadCategorys() {
		MsCategoryCriteria categoryCriteria = new MsCategoryCriteria();
		categoryCriteria.createCriteria()
		.andDelFlgEqualTo(VbConstant.DEL_FLG.未删除);
		categoryCriteria.setOrderByClause("NM_ZH DESC");
		List<MsCategory> list = msCategoryDAO.selectByCriteria(categoryCriteria);
		JSONObject data = JSONObject.fromObject("{}");
		JSONArray categorys = JSONArray.fromObject("[]");
		if (list != null) {
			for (MsCategory category : list) {
				JSONObject categorydata = JSONObject.fromObject("{}");
				categorydata.put("category_id", category.getCategoryId());
				categorydata.put("nm_zh", category.getNmZh());
				categorydata.put("nm_en", category.getNmEn());
				categorys.add(categorydata);
			}
		}
		data.put("categorys", categorys);
		return JsonUtil.sucess("检索成功", data);
	}

//	/**
//	 * 查找业务区域(三台山、石梅湾....等)
//	 * @param json(city_id,category_id)
//	 * @return -1:无数据 , 0:[{category_id, nm_zh, nm_en}]
//	 */
//	@Override
//	public JSONObject loadRegions(String json) {
//		JSONObject param = JSONObject.fromObject(json);
//		String cityId = param.getString("city_id");
//		String categoryId = param.getString("category_id");
//		VbRegionCriteria vbRegionCriteria = new VbRegionCriteria();
//		vbRegionCriteria.createCriteria()
//		.andCategoryIdEqualTo(categoryId)
//		.andCityIdEqualTo(cityId)
//		.andDelFlgEqualTo(VbConstant.DEL_FLG.未删除);
//		List<VbRegion> list = vbRegionDAO.selectByCriteria(vbRegionCriteria);
//		JSONObject data = JSONObject.fromObject("{}");
//		JSONArray vbRegions = JSONArray.fromObject("[]");
//		if (list != null) {
//			for (VbRegion vbRegion : list) {
//				JSONObject vbRegiondata = JSONObject.fromObject("{}");
//				vbRegiondata.put("region_id", vbRegion.getRegionId());
//				vbRegiondata.put("category_id", vbRegion.getCategoryId());
//				vbRegiondata.put("nm_zh", vbRegion.getNmZh());
//				vbRegiondata.put("nm_en", vbRegion.getNmEn());
//				vbRegions.add(vbRegiondata);
//			}
//		}
//		data.put("regions", vbRegions);
//		return JsonUtil.sucess("检索成功", data);
//	}
//
//	@Override
//	public JSONObject loadRoles(String json) {
////		JSONObject param = JSONObject.fromObject(json);
////		String login_wjnm = param.getString("login_wjnm");
//		// TODO:获取小于等于自己权限的角色列表
//		MsRoleCriteria msRoleCriteria = new MsRoleCriteria();
//		msRoleCriteria.createCriteria()
//		.andDelFlgEqualTo(VbConstant.DEL_FLG.未删除);
//		msRoleCriteria.setOrderByClause("ROLE_AUTH DESC");
//		List<MsRole> list = msRoleDAO.selectByCriteria(msRoleCriteria);
//		JSONObject data = JSONObject.fromObject("{}");
//		JSONArray msRoles = JSONArray.fromObject("[]");
//		if (list != null) {
//			for (MsRole msRole : list) {
//				JSONObject vbRoledata = JSONObject.fromObject("{}");
//				vbRoledata.put("role_auth", msRole.getRoleAuth());
//				vbRoledata.put("role_id", msRole.getRoleId());
//				vbRoledata.put("role_nm", msRole.getRoleNm());
//				msRoles.add(vbRoledata);
//			}
//		}
//		data.put("roles", msRoles);
//		return JsonUtil.sucess("检索成功", data);
//	}
}
