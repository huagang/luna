package ms.luna.biz.bl;

import java.util.List;
import java.util.Map;

import ms.luna.biz.dao.model.MsZone;

public interface MsZoneCacheBL {
	void init();
	Map<String, MsZone> getMsZoneCache();
	String getCityName(String zoneId);
	String getProvinceName(String zoneId);
	String getMergerName(String zoneId);
	
	String getCountryId(String zoneId);
	String getProvinceId(String zoneId);
	String getCityId(String zoneId);
	String getCountyId(String zoneId);
	List<String> getSubZoneIds(String zoneId);
}
