/**
 * 用户登录
 */
package ms.luna.biz.bl.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.Set;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import ms.biz.common.MongoConnector;
import ms.luna.biz.bl.PoiApiBL;
import ms.luna.biz.dao.custom.MsPoiTagDAO;
import ms.luna.biz.dao.model.MsPoiTag;
import ms.luna.biz.dao.model.MsPoiTagCriteria;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;

/**
 * @author greek
 *
 */
@Transactional(rollbackFor = Exception.class)
@Service("poiApiBL")
public class PoiApiBLImpl implements PoiApiBL {

	@Autowired
	private MongoConnector mongoConnector;
	
	@Autowired
	private MsPoiTagDAO msPoiTagDAO;

	@Override
	public JSONObject getPoisWithLevel(String json) {
		JSONObject param = JSONObject.parseObject(json);
		int business_id = param.getIntValue("business_id");
		int level = param.getIntValue("level");
		String fields = param.getString("fields");
		String[] fieldsArr = fields.split(",");
		if(level != 1){
			return FastJsonUtil.error("99", "方法待完成");
		}
		// 获取业务关系树
		Document tree = getBusinessTreeById(business_id);
		if(tree != null){
			JSONObject JsonBizTree = JSONObject.parseObject(tree.toJson());
			List<String> poiIdLst = null;
			poiIdLst = getPoisWithLevelInBusinessTree(JsonBizTree, level);
			
			JSONObject data = JSONObject.parseObject("{}");
			JSONArray poiArray = JSONArray.parseArray("[]");
			if(poiIdLst != null){
				for(String poiId:poiIdLst){
					// 根据业务树poi节点，在poi_collection中获取具体poi信息
					JSONObject poi = getPoiInfoByIdWtihFields(poiId,fieldsArr);
					if(!poi.isEmpty()){
						poiArray.add(poi);
					}
				}
			}
			data.put("pois", poiArray);
			return FastJsonUtil.sucess("获取业务树(id:"+business_id+")第"+level+"层数据成功", data);
		} else {
			return FastJsonUtil.error("1", "业务关系树不存在");
		}
		
//		// 获取业务关系树
//		Document tree = getBusinessTreeById(business_id);
//		if(tree != null){
//			JSONObject JsonBizTree = JSONObject.parseObject(tree.toJson());
//			List<JSONObject> poiLst = null;
//			poiLst = getPoisWithLevelInBusinessTree(JsonBizTree, business_id, level);
//			
//			JSONObject data = JSONObject.parseObject("{}");
//			JSONArray poiArray = JSONArray.parseArray("[]");
//			if(poiLst != null){
//				for(JSONObject doc:poiLst){
//					// 根据业务树poi节点，在poi_collection中获取具体poi信息
//					JSONObject poi = getPoiInfoByPoiNode(doc,fieldsArr);
//					if(poi != null){// 存在poi不含给定字段的情况
//						poiArray.add(poi);
//					}
//				}
//			}
//			data.put("pois", poiArray);
//			return FastJsonUtil.sucess("获取业务树id:"+business_id+"第"+level+"层数据成功", data);
//		} else {
//			return FastJsonUtil.error("1", "业务关系树不存在");
//		}
//		return null;
	}

	@Override
	public JSONObject getCtgrsByBizIdAndPoiId(String json) {
		JSONObject param = JSONObject.parseObject(json);
		int business_id = param.getIntValue("business_id");
		String poi_id = param.getString("poi_id");
		// 获取业务关系树
		Document tree = getBusinessTreeById(business_id);
		if(tree != null){
			// 获取给定poi结点下的子结点
			JSONObject JsonBizTree = JSONObject.parseObject(tree.toJson());
			JSONObject jsoncList = (JSONObject)JsonBizTree.get("c_list");
			List<String> poiIdLst = new ArrayList<>();
			getPoisByParentId(poiIdLst,jsoncList, poi_id);
			
			JSONObject data = JSONObject.parseObject("{}");
			JSONArray ctgrArray = JSONArray.parseArray("[]");
			Set<Integer> ctgrIdSet = new HashSet<>();	// 类别集合
			for(String _id: poiIdLst){
				// 读取数据库
				Document doc = getPoiById(_id);
				if(doc == null){
					throw new RuntimeException("(Method: getCtgrsByBizIdAndPoiId)业务关系树（id:"+business_id+"）中，id为"+_id+"的poi数据信息读取失败!");
				}
				JSONObject poi = JSONObject.parseObject(doc.toJson());
				JSONArray tags = poi.getJSONArray("tags");
				ctgrIdSet.add(tags.getInteger(0));
			}
			// 根据类别id获取类别名称
			if(!ctgrIdSet.isEmpty()){
				for(Integer ctgrId : ctgrIdSet){
					// 读取数据库
					MsPoiTagCriteria msPoiTagCriteria = new MsPoiTagCriteria();
					MsPoiTagCriteria.Criteria criteria = msPoiTagCriteria.createCriteria();
					criteria.andTagIdEqualTo(ctgrId);
					MsPoiTag msPoiTag = msPoiTagDAO.selectByPrimaryKey(ctgrId);
					if(msPoiTag == null || msPoiTag.getTagName().isEmpty()){
						throw new RuntimeException("(Method: getCtgrsByBizIdAndPoiId)类别（id:"+ctgrId+"）名称获取失败！");
					}
					// 获取名称
					String ctgrNmZh = msPoiTag.getTagName();
					JSONObject category = JSONObject.parseObject("{}");
					
					category.put("ctgrId", ctgrId);
					category.put("ctgrNmZh", ctgrNmZh);
					ctgrArray.add(category);
				}
			}
			data.put("ctgrs", ctgrArray);
			return FastJsonUtil.sucess("分类列表信息获取成功！",data);
			
		} else {
			return FastJsonUtil.error("1", "业务关系树不存在");
		}
	}

	@Override
	public JSONObject getPoisByBizIdAndPoiIdAndCtgrId(String json) {
		JSONObject param = JSONObject.parseObject(json);
		int business_id = param.getIntValue("business_id");
		String poi_id = param.getString("poi_id");
		int ctgr_id = param.getIntValue("ctgr_id");
		String fields = param.getString("fields");
		String[] fieldArr = fields.split(",");
		// 获取业务关系树
		Document tree = getBusinessTreeById(business_id);
		if(tree != null){
			// 获取给定poi结点下的子结点
			JSONObject JsonBizTree = JSONObject.parseObject(tree.toJson());
			JSONObject jsoncList = (JSONObject)JsonBizTree.get("c_list");
			List<String> poiIdLst = new ArrayList<>();
			getPoisByParentId(poiIdLst,jsoncList, poi_id);
			
			JSONObject data = JSONObject.parseObject("{}");
			JSONArray poiArray = JSONArray.parseArray("[]");
			// 获取子结点中类型为ctgrId，且含有指定字段的集合
			for(String _id: poiIdLst){
				JSONObject poi = getPoiInfoByCtgrIdWithFields(_id, ctgr_id, fieldArr);
				if(!poi.isEmpty()){
					poiArray.add(poi);
				}
			}
			data.put("pois", poiArray);
			return FastJsonUtil.sucess("",data);
		} else {
			return FastJsonUtil.error("1", "业务关系树不存在");
		}
	}

	@Override
	public JSONObject getPoiInfoById(String json) {
		JSONObject param = JSONObject.parseObject(json);
		String poi_id = param.getString("poi_id");
		Document doc = getPoiById(poi_id);
		if (doc == null) {
			MsLogger.debug("poi ["+poi_id+"] is not found!");
			return FastJsonUtil.error("-1", "poi ["+poi_id+"] is not found!");
		}
		System.out.println(doc.toJson());
		return FastJsonUtil.sucess("获取POI数据成功", JSONObject.parse(doc.toJson())); 
	}

	@Override
	public JSONObject getPoisByBizIdAndTags(String json) {
		JSONObject param = JSONObject.parseObject(json);
		int business_id = param.getIntValue("business_id");
		String tags = param.getString("tags");
		String fields = param.getString("fields");
		String[] tagArr = tags.split(",");
		String[] fieldArr = fields.split(",");
		// 获取业务关系树
		Document tree = getBusinessTreeById(business_id);
		if(tree != null){
			// 获取子POI id集合
			JSONObject JsonBizTree = JSONObject.parseObject(tree.toJson());
			JSONObject jsoncList = (JSONObject)JsonBizTree.get("c_list");
			Set<String> poiIds = new HashSet<>();
			ManageBusinessTreeBLImpl manageBusinessTreeBLImpl = new ManageBusinessTreeBLImpl();
			manageBusinessTreeBLImpl.readPoiId2Set(poiIds,jsoncList);
			
			// 根据标签和字段返回满足要求的poi
			JSONObject data = JSONObject.parseObject("{}");
			JSONArray poiArray = JSONArray.parseArray("[]");
			for(String _id : poiIds){
				JSONObject poi = getPoiInfoByTagsWithFields(_id, tagArr, fieldArr); 
				if(!poi.isEmpty()){
					poiArray.add(poi);
				}
			}
			
//			String packageName = this.getClass().getPackage().getName(); // 获取包名
//			Class cls = Class.forName(packageName+"."+"ManageBusinessTreeBLImpl");
//			Method method = cls.getDeclaredMethod("",new Class[]{Set.class});  
//			method.invoke(poiIds);
			
			data.put("pois", poiArray);
			return FastJsonUtil.sucess("",data);
		} else {
			return FastJsonUtil.error("1", "业务关系树不存在");
		}
	}
	
	/**
	 * 根据poi 的id值获取poi信息
	 * @param _id 
	 * @return
	 */
	private Document getPoiById(String _id) {
		MongoCollection<Document> poi_collection = mongoConnector.getDBCollection("poi_collection");
		BasicDBObject keyId = new BasicDBObject();
		keyId.put("_id", new ObjectId(_id));
		return poi_collection.find(keyId).limit(1).first(); 
	}
	
	
	/**
	 * 根据父poi查找子poi结点
	 * @param pois 子POI结点id列表
	 * @param jsoncList 业务树中poi结点下的c_list数据
	 * @param poi_id poi id
	 */
	private void getPoisByParentId(List<String> pois, JSONObject jsoncList, String poi_id) {
		Set<Entry<String, Object>> entrySet = jsoncList.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			String _id = entry.getKey();
			JSONObject innerJsoncList = JSON.parseObject(entry.getValue().toString());
			JSONObject subClist = innerJsoncList.getJSONObject("c_list");
			// 当前结点是查找结点
			if(_id.equals(poi_id)){
				if(!subClist.isEmpty()){//存在子结点
					pois.addAll(subClist.keySet());
				}
				return;
			}
			// 当前结点不是查找结点
			if (subClist.isEmpty()) {
				continue;
			}
			getPoisByParentId(pois, subClist, poi_id);
		}
	}
	
	/**
	 * 获取业务关系树
	 * @param business_id 业务树id
	 * @return
	 */
	private Document getBusinessTreeById(int business_id) {
		MongoCollection<Document> business_tree = mongoConnector.getDBCollection("business_tree");
		BasicDBObject condition = new BasicDBObject();
		condition.put("business_id", business_id);
		Document tree = null;
		tree = business_tree.find(condition).first();
		return tree;
	}
	
	/**
	 * 获取树的同一层poi结点的id
	 * @param business_id 业务树id
	 * @param level 树的层级
	 * @return
	 */
	List<String> getPoisWithLevelInBusinessTree(JSONObject tree, int level){
		if(level != 1){//待完成
			return null;
		}
		JSONObject subTree = tree.getJSONObject("c_list");
		if(!subTree.isEmpty()){
			Set<String> keys = subTree.keySet();
			ArrayList<String> lst = new ArrayList<>(keys);
			return lst;
		}
		return null;
	}
	
	/**
	 *  获取给定字段,id和标签的poi
	 * @param poi_id poi 的id 
	 * @param tags 标签
	 * @param fields 需要获取的字段
	 * @return
	 */
	private JSONObject getPoiInfoByTagsWithFields(String poi_id, String[] scene_types, String[] fields) {
		MongoCollection<Document> poi_collection = mongoConnector.getDBCollection("poi_collection");
		BasicDBList value = new BasicDBList();
		for(String scene_type : scene_types){
			value.add(scene_type);
		}
		BasicDBObject in = new BasicDBObject("$all",value);
		
		BasicDBObject condition = new BasicDBObject();
		condition.append("_id", new ObjectId(poi_id)).append("scene_type", in);
		Document doc = poi_collection.find(condition).first();
		
		JSONObject result = JSONObject.parseObject("{}");
		if(doc != null){
			JSONObject resJson = JSONObject.parseObject(doc.toJson());
			for(String field : fields){
				if(resJson.containsKey(field)){
					result.put(field, resJson.get(field));
				}
			}
		}
		if(!result.isEmpty()){
			result.put("_id", poi_id);
		}
		return result;
	}
	
	/**
	 * 获取给定字段和id的poi
	 * @param poi_id poi 的id
	 * @param fields 需要获取的字段
	 * @return
	 */
	private JSONObject getPoiInfoByIdWtihFields(String poi_id, String[] fields) {
		MongoCollection<Document> poi_collection = mongoConnector.getDBCollection("poi_collection");
		BasicDBObject condition = new BasicDBObject();
		condition.put("_id", new ObjectId(poi_id));
		Document doc = poi_collection.find(condition).first();
		
		JSONObject result = JSONObject.parseObject("{}");
		if(doc != null){
			JSONObject resJson = JSONObject.parseObject(doc.toJson());
			for(String field : fields){
				if(resJson.containsKey(field)){
					result.put(field, resJson.get(field));
				}
			}
		}
		if(!result.isEmpty()){
			result.put("_id", poi_id);
		}
		return result;
	}
	
	/**
	 * 获取父poi下满足一级分类id的poi子结点（poi仅包含给定字段数据）
	 * @param poi_id poi ID号
	 * @param ctgr_id 一级分类
	 * @param fields 需要获取的字段
	 * @return
	 */
	private JSONObject getPoiInfoByCtgrIdWithFields(String poi_id, int ctgr_id, String[] fields) {
		int[] ctgrArr = new int[1];
		ctgrArr[0] = ctgr_id;
		MongoCollection<Document> poi_collection = mongoConnector.getDBCollection("poi_collection");
		BasicDBObject condition = new BasicDBObject();
		condition.append("_id", new ObjectId(poi_id)).append("tags", ctgrArr);
		Document doc = poi_collection.find(condition).first();
		
		JSONObject result = JSONObject.parseObject("{}");
		if(doc != null){
			JSONObject resJson = JSONObject.parseObject(doc.toJson());
			for(String field : fields){
				if(resJson.containsKey(field)){
					result.put(field, resJson.get(field));
				}
			}
		}
		if(!result.isEmpty()){
			result.put("_id", poi_id);
		}
		return result;
	}
	
//	/**
//	 * 获取树的高度
//	 * @param business_id 业务树id
//	 * @return
//	 */
//	int getHeightInBusinessTree(int business_id){
//		MongoCollection<Document> business_tree = mongoConnector.getDBCollection("business_tree");
//		BasicDBObject condition = new BasicDBObject();
//		condition.put("business_id", business_id);
//		Document doc = business_tree.find(condition).first();
//		
//		return 0;
//	}
}
