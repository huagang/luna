package ms.luna.biz.sc;

import com.alibaba.fastjson.JSONObject;

/**
 * @author Mark
 *
 */
public interface PulldownService {

	/**
	 * 获取国家信息
	 * @param json
	 * @return {code, msg, data{}}
	 */
	JSONObject loadCountrys();

	/**
	 * 获取省份信息
	 * @param json
	 * @return {code, msg, data{}}
	 */
	JSONObject loadProvnices(String countryId);

	/**
	 * 获取城市信息
	 * @param json
	 * @return {code, msg, data{}}
	 */
	JSONObject loadCitys(String provinceId);

	/**
	 * 获取分类信息
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
	
	/**
	 * 获取区/县列表
	 * @param cityId
	 * @return
	 */
	JSONObject loadCounties(String cityId);

}
