package ms.luna.biz.bl;

import com.alibaba.fastjson.JSONObject;

public interface ManageBusinessTreeBL {
	/**
	 * 获取业务树列表
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
	 * 插入业务树中的POI点
	 * @param json
	 * @return
	 */
	JSONObject saveBusinessTree(String json);

//	/**
//	 * 删除业务树中的Poi点
//	 * @param json
//	 * @return
//	 */
//	JSONObject removeBizTreePoi(String json);
//
//	/**
//	 * 主要用于增加或者删除全景数据
//	 * @param json
//	 * @return
//	 */
//	JSONObject changeBizTreePoi(String json);

}
