package ms.luna.biz.sc;

import com.alibaba.fastjson.JSONObject;

/**
 * @author Greek
 *
 */
public interface ManageRouteService {

	/**
	 * 创建线路
	 * 
	 * @param json
	 * @return
	 */
	JSONObject createRoute(JSONObject json);

	/**
	 * 属性编辑
	 * 
	 * @param json
	 * @return
	 */
	JSONObject editRoute(JSONObject json);

	/**
	 * 删除线路
	 * @param id
	 * @return
	 */
	JSONObject delRoute(int id);

	/**
	 * 搜索线路
	 * 
	 * @param json
	 * @return
	 */
	JSONObject loadRoutes(JSONObject json);

	/**
	 * 判断线路名称是否存在
	 * 
	 * @param json
	 * @return
	 */
	JSONObject isRouteNmExist(String json);
}
