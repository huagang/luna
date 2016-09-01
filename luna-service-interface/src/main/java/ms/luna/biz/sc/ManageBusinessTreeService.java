package ms.luna.biz.sc;

import com.alibaba.fastjson.JSONObject;

import ms.luna.biz.model.MsUser;

/**
 * @author: Mark
 *
 */
public interface ManageBusinessTreeService {

	/**
	 * 获取业务数列表
	 * @param json
	 * @return
	 */
	JSONObject loadBusinessTrees(String json);

	/**
	 * 获取业务列表
	 * @param json
	 * @return
	 */
	JSONObject searchBusinessList(String json);

	/**
	 * 创建一棵业务树
	 */
	JSONObject createBusinessTree(String json);

	/**
	 * 删除一棵业务树
	 */
	JSONObject deleteBusinessTree(String json);

	/**
	 * 获取某棵业务树
	 * @param json
	 * @return
	 */
	JSONObject viewBusinessTree(String json);

	/**
	 * 
	 * 搜索POI数据
	 */
	JSONObject searchPoisForBizTree(String json);
	/**
	 * 
	 * 保存业务树数据
	 */
	JSONObject saveBusinessTree(String json);

	JSONObject existBusinessTree(int businessId);
	
}
