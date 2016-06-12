package ms.luna.biz.bl.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ms.luna.biz.bl.MsZoneCacheBL;
import ms.luna.biz.dao.custom.MsZoneDAO;
import ms.luna.biz.dao.model.MsZone;
import ms.luna.biz.dao.model.MsZoneCriteria;

@Service("msZoneCacheBL")
public class MsZoneCacheBLImpl implements MsZoneCacheBL {

	private static Map<String, String> provinceNameOfZoneIdMap = new LinkedHashMap<String, String>();
	private static Map<String, String> cityNameOfZoneIdMap = new LinkedHashMap<String, String>();
	private static Map<String, String> mergerNameOfZoneIdMap = new LinkedHashMap<String, String>();

	private static Map<String, List<String>> subZoneIdsMap = new LinkedHashMap<String, List<String>>();

	/**
	 * 地域缓存
	 */
	private static Map<String, MsZone> msZoneCache = new HashMap<String, MsZone>();

	@Autowired
	private MsZoneDAO msZoneDAO;

	@Override
	public void init() {
		if (!msZoneCache.isEmpty()) {
			return;
		}
		MsZoneCriteria msZoneCriteria = new MsZoneCriteria();
		msZoneCriteria.setOrderByClause("ID");
		List<MsZone> lstMsZone = msZoneDAO.selectByCriteria(msZoneCriteria);
		if (lstMsZone != null) {
			for (MsZone msZone : lstMsZone) {
				msZoneCache.put(msZone.getId(), msZone);
			}
		}
	}

	public String getProvinceName(String zoneId) {
		String provinceName = provinceNameOfZoneIdMap.get(zoneId);
		if (provinceName != null) {
			return provinceName;
		}

		MsZone msZone = msZoneCache.get(zoneId);
		if (msZone == null) {
			return "";
		}
		int level = Integer.parseInt(msZone.getLevelType());
		if (level < 1) {
			return "";
		} else if (level == 1) {
			provinceNameOfZoneIdMap.put(zoneId, msZone.getName());
			return msZone.getName();
		} else {
			return getProvinceName(msZone.getParentId());
		}
	}

	public String getCityName(String zoneId) {
		String cityName = cityNameOfZoneIdMap.get(zoneId);
		if (cityName != null) {
			return cityName;
		}

		MsZone msZone = msZoneCache.get(zoneId);
		if (msZone == null) {
			return "";
		}
		int level = Integer.parseInt(msZone.getLevelType());
		if (level < 2) {
			return "";
		} else if (level == 2) {
			cityNameOfZoneIdMap.put(zoneId, msZone.getName());
			return msZone.getName();
		} else {
			return getCityName(msZone.getParentId());
		}
	}

	/**
	 * @return the msZoneCache
	 */
	public Map<String, MsZone> getMsZoneCache() {
		return msZoneCache;
	}

	@Override
	public String getMergerName(String zoneId) {
		String mergerName = mergerNameOfZoneIdMap.get(zoneId);
		if (mergerName != null) {
			return mergerName;
		}

		MsZone msZone = msZoneCache.get(zoneId);
		if (msZone == null) {
			return "";
		}
		mergerNameOfZoneIdMap.put(zoneId, msZone.getMergerName());
		return msZone.getMergerName();
	}

	@Override
	public String getCountryId(String zoneId) {
		return "100000";
	}

	@Override
	public String getProvinceId(String zoneId) {
		MsZone msZone = msZoneCache.get(zoneId);
		if (msZone == null) {
			return "ALL";
		}
		Integer level = Integer.parseInt(msZone.getLevelType());
		if (level < 1) {
			return "ALL";
		} else if (level > 1) {
			return getProvinceId(msZone.getParentId());
		} else {
			return msZone.getId();
		}
	}

	@Override
	public String getCityId(String zoneId) {
		MsZone msZone = msZoneCache.get(zoneId);
		if (msZone == null) {
			return "ALL";
		}
		Integer level = Integer.parseInt(msZone.getLevelType());
		if (level < 2) {
			return "ALL";
		} else if (level > 2) {
			return getCityId(msZone.getParentId());
		} else {
			return msZone.getId();
		}
	}

	@Override
	public String getCountyId(String zoneId) {
		MsZone msZone = msZoneCache.get(zoneId);
		if (msZone == null) {
			return "ALL";
		}
		Integer level = Integer.parseInt(msZone.getLevelType());
		if (level < 3) {
			return "ALL";
		} else if (level > 3) {
			return getCountyId(msZone.getParentId());
		} else {
			return msZone.getId();
		}
	}

	@Override
	public List<String> getSubZoneIds(String zoneId) {
		List<String> zone_ids = subZoneIdsMap.get(zoneId);
		if (zone_ids != null) {
			return zone_ids;
		}
		zone_ids = new ArrayList<String>();
		MsZone msZone = msZoneCache.get(zoneId);
		if (msZone == null) {
			return zone_ids;
		}
		zone_ids.add(msZone.getId());
		Integer level = Integer.parseInt(msZone.getLevelType());
		if (level == 3) {
			subZoneIdsMap.put(zoneId, zone_ids);
			return zone_ids;
		}

		Set<Entry<String, MsZone>> set = msZoneCache.entrySet();
		for (Entry<String, MsZone> entry : set) {
			msZone = entry.getValue();
			if (msZone.getParentId().equals(zoneId)) {
				level = Integer.parseInt(msZone.getLevelType());
				if (level == 3) {
					zone_ids.add(msZone.getId());
				} else {
					zone_ids.addAll(getSubZoneIds(msZone.getId()));
				}
			}
		}
		subZoneIdsMap.put(zoneId, zone_ids);
		return zone_ids;
	}
}
