package ms.luna.biz.bl;

import com.alibaba.fastjson.JSONObject;

/**
 * @author Mark
 *
 */
public interface PulldownBL {

	/**
	 * 获取区域Cach信息
	 * @param json
	 * @return {code, msg, data{}}
	 */
	JSONObject loadCoutrys();

	/**
	 * 获取区域Cach信息
	 * @param json
	 * @return {code, msg, data{}}
	 */
	JSONObject loadProvinces(String countryId);
	
	/**
	 * 获取区域Cach信息
	 * @param json
	 * @return {code, msg, data{}}
	 */
	JSONObject loadCitys(String provinceId);
	
	
	JSONObject loadCounties(String cityId);
	
	/**
	 * 获取区域Cach信息
	 * @param json
	 * @return {code, msg, data{}}
	 */
	JSONObject loadCategorys();

	/**
	 * 查找QQ地域名称对应的省、市、县ID
	 * @param json
	 * @return
	 */
	public JSONObject findZoneIdsWithQQZoneName(String json);
}
