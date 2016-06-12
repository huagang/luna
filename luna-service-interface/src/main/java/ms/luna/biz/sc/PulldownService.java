package ms.luna.biz.sc;

import net.sf.json.JSONObject;

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

//	/**
//	 * 获取区域信息
//	 * @param json
//	 * @return {code, msg, data{}}
//	 */
//	JSONObject loadRegions(String json);
//
//	/**
//	 * 获取权限列表
//	 * @param json
//	 * @return {code, msg, data{}}
//	 */
//	JSONObject loadRoles(String json);
	
	/**
	 * 获取区/县列表
	 * @param cityId
	 * @return
	 */
	JSONObject loadCounties(String cityId);

}
