package ms.luna.biz.bl;

import com.alibaba.fastjson.JSONObject;

public interface ManagePoiBL {

	/**
	 * 获取页面初始化信息
	 * @param json
	 * @return
	 */
	JSONObject getInitInfo(String json);

	/**
	 * POI列表初始化
	 * @param json
	 * @return
	 */
	JSONObject getPois(String json);

	/**
	 * 添加POI到MongoDB
	 * @param json
	 * @return
	 */
	JSONObject addPoi(String json);

	/**
	 * 更新POI
	 * @param json
	 * @return
	 */
	JSONObject updatePoi(String json);
	/**
	 * 删除POI
	 * @param json
	 * @return
	 */
	JSONObject asyncDeletePoi(String json);

	/**
	 * 初始化
	 * @return
	 */
	JSONObject initAddPoi(String json);

	/**
	 * 初始化
	 * @return
	 */
	JSONObject initEditPoi(String json);

	/**
	 * 初始化
	 * @return
	 */
	JSONObject initFixPoi(String json);

	/**
	 * 下载poi模板
	 * @param json
	 * @return
	 */
	JSONObject downloadPoiTemplete(String json);

	/**
	 * 批量添加poi(POI的前提：除重复性检查以外的检查都已经通过)
	 * @param json
	 * @return
	 */
	JSONObject savePois(String json);

	/**
	 * 获取一级和二级分类列表
	 * @param json
	 * @return
	 */
	JSONObject getTagsDef(String json);

	/**
	 * 检查POI是否可以被删除
	 * @param json
	 * @return
	 */
	JSONObject checkPoiCanBeDeleteOrNot(String json);
	
	/**
	 * 获取POI预览页信息
	 * @param json
	 * @return
	 */
	JSONObject initPoiPreview(String json);
}
