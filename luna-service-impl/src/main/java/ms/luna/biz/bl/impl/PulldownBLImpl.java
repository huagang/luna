package ms.luna.biz.bl.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ms.luna.biz.bl.MsZoneCacheBL;
import ms.luna.biz.bl.PulldownBL;
import ms.luna.biz.cons.VbConstant;
import ms.luna.biz.dao.custom.MsCategoryDAO;
import ms.luna.biz.dao.custom.MsZoneDAO;
import ms.luna.biz.dao.model.MsCategory;
import ms.luna.biz.dao.model.MsCategoryCriteria;
import ms.luna.biz.dao.model.MsZone;
import ms.luna.biz.dao.model.MsZoneCriteria;
import ms.luna.biz.util.FastJsonUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Transactional(rollbackFor=Exception.class)
@Service("pulldownBL")
public class PulldownBLImpl implements PulldownBL {

	@Autowired
	private MsZoneDAO msZoneDAO;

	@Autowired
	private MsCategoryDAO msCategoryDAO;
	@Autowired
	private MsZoneCacheBL msZoneCacheBL;
	
	static private Map<String, JSONArray> cacheCountrysData = new HashMap<String, JSONArray>() ;
	static private Map<String, JSONArray> cacheProvincesData = new HashMap<String, JSONArray>() ;
	static private Map<String, JSONArray> cacheCityData = new HashMap<String, JSONArray>() ;
	static private Map<String, JSONArray> cacheCountyData = new HashMap<String, JSONArray>();

	/**
	 * 查找QQ地域名称对应的省、市、县ID
	 * @param json
	 * @return
	 */
	@Override
	public JSONObject findZoneIdsWithQQZoneName(String json) {
		return msZoneCacheBL.findZoneIdsWithQQZoneName(json);
	}

	@Override
	public JSONObject loadCoutrys() {

		JSONObject data = JSONObject.parseObject("{}");
		JSONArray countrys = null;
		
		//TODO
		synchronized (PulldownBLImpl.class) {
			countrys = cacheCountrysData.get("100000") ;
		}
		if(countrys == null) {
			countrys = JSONArray.parseArray("[]");
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
					JSONObject countrydata = JSONObject.parseObject("{}");
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
		return FastJsonUtil.sucess("检索成功", data);
	}

	@Override
	public JSONObject loadProvinces(String countryId) {
		JSONObject data = JSONObject.parseObject("{}");
		JSONArray provinces = null ;
		
		//TODO
		synchronized (PulldownBLImpl.class) {
			provinces = cacheProvincesData.get(countryId) ;
			if(provinces == null) {
				provinces = JSONArray.parseArray("[]");
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
						JSONObject provincedata = JSONObject.parseObject("{}");
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
		return FastJsonUtil.sucess("检索成功", data);
	}

	@Override
	public JSONObject loadCitys(String provinceId) {
		JSONObject data = JSONObject.parseObject("{}");
		JSONArray citys = null ;
		
		//TODO
		synchronized (PulldownBLImpl.class) {
			citys = cacheCityData.get(provinceId) ;
		}
		if(citys == null) {
			citys = JSONArray.parseArray("[]");
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
					JSONObject citydata = JSONObject.parseObject("{}");
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
		return FastJsonUtil.sucess("检索成功", data);
	}
	
	@Override
	public JSONObject loadCounties(String cityId) {
		
		JSONObject data = JSONObject.parseObject("{}");
		JSONArray counties = null ;
		counties = cacheCountyData.get(cityId) ;
		if(counties == null) {
			synchronized (PulldownBLImpl.class) {
				counties = cacheCountyData.get(cityId) ;
				if(counties == null) {
					counties = JSONArray.parseArray("[]");
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
							JSONObject countydata = JSONObject.parseObject("{}");
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
		return FastJsonUtil.sucess("检索成功", data);
	}

	@Override
	public JSONObject loadCategorys() {
		MsCategoryCriteria categoryCriteria = new MsCategoryCriteria();
		categoryCriteria.createCriteria()
		.andDelFlgEqualTo(VbConstant.DEL_FLG.未删除);
		categoryCriteria.setOrderByClause("NM_ZH DESC");
		List<MsCategory> list = msCategoryDAO.selectByCriteria(categoryCriteria);
		JSONObject data = JSONObject.parseObject("{}");
		JSONArray categorys = JSONArray.parseArray("[]");
		if (list != null) {
			for (MsCategory category : list) {
				JSONObject categorydata = JSONObject.parseObject("{}");
				categorydata.put("category_id", category.getCategoryId());
				categorydata.put("nm_zh", category.getNmZh());
				categorydata.put("nm_en", category.getNmEn());
				categorys.add(categorydata);
			}
		}
		data.put("categorys", categorys);
		return FastJsonUtil.sucess("检索成功", data);
	}

}
