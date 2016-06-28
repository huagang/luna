/**
 * 用户登录
 */
package ms.luna.biz.bl.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import ms.biz.common.MongoConnector;
import ms.biz.common.PoiApiNameMap;
import ms.luna.biz.bl.PoiApiBL;
import ms.luna.biz.dao.custom.MsPoiFieldDAO;
import ms.luna.biz.dao.custom.MsPoiTagDAO;
import ms.luna.biz.dao.custom.model.MsTagFieldParameter;
import ms.luna.biz.dao.custom.model.MsTagFieldResult;
import ms.luna.biz.dao.model.MsPoiField;
import ms.luna.biz.dao.model.MsPoiFieldCriteria;
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
	
	@Autowired 
	MsPoiFieldDAO msPoiFieldDAO;
	
	@Autowired
	private PoiApiNameMap poiApiNameMap;
	
	private static Map<String,String> fieldNamesLst = new LinkedHashMap<>();// (key：字段名称，value:字段显示名称)
	
	private static Map<Integer, String> poiTagsLst = new LinkedHashMap<>();// (key:类型id ,value:类型名称)
	
	private static Map<Integer, String> poiTypeId2NmLst = new LinkedHashMap<>();// (key:type的id,value:type值)
	
	private static Map<Integer, String> poiSceneTypeId2NmLst = new LinkedHashMap<>();// (key:sub_type的id,value:sub_type值)
	
	private static Map<String, Integer> poiTypeNm2IdLst = new LinkedHashMap<>();// (key:type值,value:type的id)
	
	private static Map<String, Integer> poiSceneTypeNm2IdLst = new LinkedHashMap<>();// (key:type值,value:type的id)
	
	// 获得第一层所有poi数据
	@Override
	public JSONObject getPoisInFirstLevel(String json) {
		JSONObject param = JSONObject.parseObject(json);
		int biz_id = param.getIntValue("biz_id");
		String fields = param.getString("fields");
		String lang = param.getString("lang");
		String[] fieldArr;
		if("".equals(fields)){// 检测是否含有字段信息
			fieldArr = null;
		} else {
			fields = fields.replaceAll("zone", "zone_id,zone_name");
			fieldArr = fields.split(",");
		}
		// 获取业务关系树
		Document tree = getBizTreeById(biz_id);
		if(tree != null){
			List<String> poiIdLst = null;
			poiIdLst = getPoisWithLevelInBizTree(tree, 1);
			
			JSONObject data = new JSONObject();
			JSONArray poiArray = new JSONArray();
//			if(poiIdLst != null){
//				for(String poiId:poiIdLst){
//					// 根据业务树poi节点，在poi_collection中获取具体poi信息
//					JSONObject poi = getPoiInfoWtihFields(poiId,fieldArr,lang);
//					if(!poi.isEmpty()){
//						poiArray.add(poi);
//					}
//				}
//			}
			if(poiIdLst != null){
				poiArray = getPoiInfoWtihFields(poiIdLst,fieldArr,lang);
			}
			data.put("pois", poiArray);
			return returnSuccessData("id:"+biz_id+"业务树第1层数据成功", lang, data);
		} else {
			return FastJsonUtil.error("1", "biz_id:"+biz_id+"业务关系树不存在");
		}
	}

	// 根据业务获取一个层级的poi数据列表
	@Override
	public JSONObject getPoisWithLevel(String json) {
		// TODO
		return null;
	}

	// 根据业务和POI获取下一层的一级类别列表
	@Override
	public JSONObject getCtgrsByBizIdAndPoiId(String json) {
		JSONObject param = JSONObject.parseObject(json);
		int biz_id = param.getIntValue("biz_id");
		String poi_id = param.getString("poi_id");
		// 获取业务关系树
		Document tree = getBizTreeById(biz_id);
		if(tree != null){
			// 获取给定poi结点下的子结点
			List<String> poiIdLst = new ArrayList<>();
			Document jsoncList = (Document) tree.get("c_list");
			getPoisByParentId(poiIdLst,jsoncList, poi_id, 0, 0);
			
			JSONObject data = new JSONObject();
			Set<Integer> ctgrIdSet = new HashSet<>();	// 类别集合
			for(String _id: poiIdLst){
				// 读取数据库
				Document doc = getPoiById(_id, "zh");	// 默认读取中文库
				if(doc != null){
//					ArrayList<Integer> tags = (ArrayList<Integer>) doc.get("tags");
					JSONArray tags = FastJsonUtil.parse2Array(doc.get("tags"));
					ctgrIdSet.add(tags.getInteger(0));
				}
			}
			// 根据类别id获取类别名称
			JSONArray array = new JSONArray();
			if(!ctgrIdSet.isEmpty()){
				Map<Integer, String> map = getPoiTagsLst();
				for(Integer ctgrId : ctgrIdSet){
					JSONObject ctgr = new JSONObject();
					ctgr.put("ctgr_id", ctgrId);
					ctgr.put("ctgr_nm", map.get(ctgrId));
					array.add(ctgr);
				}
			}
			data.put("ctgrs", array);
			return FastJsonUtil.sucess("biz_id:"+biz_id+",poi_id:"+poi_id+"一级分类列表获取成功",data);
			
		} else {
			return FastJsonUtil.error("1", "biz_id:"+biz_id+"业务关系树不存在");
		}
	}

	// 根据业务和POI获取下一层的二级类别列表
	@Override
	public JSONObject getSubCtgrsByBizIdAndPoiId(String json) {
		JSONObject param = JSONObject.parseObject(json);
		int biz_id = param.getIntValue("biz_id");
		String poi_id = param.getString("poi_id");
		int ctgr_id = param.getInteger("ctgr_id");
		// 获取业务关系树
		Document tree = getBizTreeById(biz_id);
		if(tree != null){
			// 获取给定poi结点下的子结点
			List<String> poiIdLst = new ArrayList<>();
			Document jsoncList = (Document) tree.get("c_list");
			getPoisByParentId(poiIdLst,jsoncList, poi_id, 0, 0);
			
			JSONObject data = new JSONObject();
			Set<Integer> subCtgrIdSet = new HashSet<>();	// 类别集合
			for(String _id: poiIdLst){
				// 读取数据库
				Document doc = getPoiById(_id, "zh");
				if(doc != null){
//					ArrayList<Integer> tags = (ArrayList<Integer>) doc.get("tags");
					JSONArray tags = FastJsonUtil.parse2Array(doc.get("tags"));
					if(!tags.isEmpty() && tags.getInteger(0) == ctgr_id){
						// 获取二级分类
						int sub_tag_id = doc.getInteger("sub_tag");
						subCtgrIdSet.add(sub_tag_id);
					}
				}
			}
			
			// 根据类别id获取类别名称
			JSONArray array = new JSONArray(); 
			if(!subCtgrIdSet.isEmpty()){
				Map<Integer, String> map = getPoiTagsLst();
				for(Integer subCtgrId : subCtgrIdSet){
					JSONObject subctgr = new JSONObject();
					subctgr.put("sub_ctgr_id", subCtgrId);
					subctgr.put("sub_ctgr_nm", map.get(subCtgrId));
					array.add(subctgr);
				}
			}
			data.put("sub_ctgrs", array);
			return FastJsonUtil.sucess("biz_id:"+biz_id+",poi_id:"+poi_id+",ctgr_id:"+ctgr_id+"二级分类列表信息获取成功",data);
		}else {
			return FastJsonUtil.error("1", "biz_id:"+biz_id+"biz_id:"+biz_id+"业务关系树不存在");
		}
	}
	
	// 根据业务，POI和一级类别获取下一层POI数据列表
	@Override
	public JSONObject getPoisByBizIdAndPoiIdAndCtgrId(String json) {
		JSONObject param = JSONObject.parseObject(json);
		int biz_id = param.getIntValue("biz_id");
		String poi_id = param.getString("poi_id");
		int ctgr_id = param.getIntValue("ctgr_id");
		String fields = param.getString("fields");
		String lang = param.getString("lang");
		String[] fieldArr;
		if("".equals(fields)){// 检测是否含有字段信息
			fieldArr = null;
		} else {
			fields = fields.replaceAll("zone", "zone_id,zone_name");
			fieldArr = fields.split(",");
		}
		// 获取业务关系树
		Document tree = getBizTreeById(biz_id);
		if(tree != null){
			// 获取给定poi结点下的子结点
			List<String> poiIdLst = new ArrayList<>();
			Document jsoncList = (Document) tree.get("c_list");
			// 注：目前仅获得第一层下的poi结点
			getPoisByParentId(poiIdLst,jsoncList, poi_id, 0, 0);
			
			JSONObject data = new JSONObject();
			JSONArray poiArray = new JSONArray();
			// 获取子结点中类型为ctgrId，且含有指定字段的集合
//			for(String _id: poiIdLst){
//				JSONObject poi = getPoiInfoByCtgrIdWithFields(poiIdLst, _id, ctgr_id, fieldArr, lang);
//				if(!poi.isEmpty()){
//					poiArray.add(poi);
//				}
//			}
			if(!poiIdLst.isEmpty()){
				poiArray = getPoiInfoByCtgrIdWithFields(poiIdLst, ctgr_id, fieldArr, lang);
			}
			data.put("pois", poiArray);
			return returnSuccessData("biz_id:"+biz_id+",poi_id:"+poi_id+",ctgr_id:"+ctgr_id+" poi数据列表获取成功", lang, data);
		} else {
			return FastJsonUtil.error("1", "biz_id:"+biz_id+"业务关系树不存在");
		}
	}

	// 根据业务，POI和二级类别获取下一层POI数据列表
	@Override
	public JSONObject getPoisByBizIdAndPoiIdAndSubCtgrId(String json) {
		JSONObject param = JSONObject.parseObject(json);
		int biz_id = param.getIntValue("biz_id");
		String poi_id = param.getString("poi_id");
		int sub_ctgr_id = param.getIntValue("sub_ctgr_id");
		String fields = param.getString("fields");
		String lang = param.getString("lang");
		String[] fieldArr;
		if("".equals(fields)){// 检测是否含有字段信息
			fieldArr = null;
		} else {
			fields = fields.replaceAll("zone", "zone_id,zone_name");
			fieldArr = fields.split(",");
		}
		// 获取业务关系树
		Document tree = getBizTreeById(biz_id);
		if(tree != null){
			// 获取给定poi结点下的子结点
			List<String> poiIdLst = new ArrayList<>();
			Document jsoncList = (Document) tree.get("c_list");
			// 注：目前仅获得第一层下的poi结点
			getPoisByParentId(poiIdLst,jsoncList, poi_id, 0, 0);
			
			JSONObject data = new JSONObject();
			JSONArray poiArray = new JSONArray();
			if(!poiIdLst.isEmpty()){
				poiArray = getPoiInfoBySubCtgrIdWithFields(poiIdLst, sub_ctgr_id, fieldArr, lang);
			}
			data.put("pois", poiArray);
			return returnSuccessData("biz_id:"+biz_id+",poi_id:"+poi_id+",sub_ctgr_id:"+sub_ctgr_id+" poi数据列表获取成功", lang, data);
		} else {
			return FastJsonUtil.error("1", "biz_id:"+biz_id+"业务关系树不存在");
		}
	}
	
	// 根据业务和poi获取下一层的POI列表
	@Override
	public JSONObject getPoisByBizIdAndPoiId(String json) {
		JSONObject param = JSONObject.parseObject(json);
		int biz_id = param.getIntValue("biz_id");
		String poi_id = param.getString("poi_id");
		String fields = param.getString("fields");
		String lang = param.getString("lang");
		String[] fieldArr;
		if("".equals(fields)){// 检测是否含有字段信息
			fieldArr = null;
		} else {
			fields = fields.replaceAll("zone", "zone_id,zone_name");
			fieldArr = fields.split(",");
		}
		// 获取业务关系树
		Document tree = getBizTreeById(biz_id);
		if(tree != null){
			// 获取给定poi结点下的子结点
			List<String> poiIdLst = new ArrayList<>();
			Document jsoncList = (Document) tree.get("c_list");
			// 注：目前仅获得第一层下的poi结点
			getPoisByParentId(poiIdLst,jsoncList, poi_id, 0, 0);
			
			JSONObject data = new JSONObject();
			JSONArray poiArray = new JSONArray();
			// 获取子结点中类型为ctgrId，且含有指定字段的集合
			if(!poiIdLst.isEmpty()){
				poiArray = getPoiInfoWtihFields(poiIdLst, fieldArr, lang);
			}
			data.put("pois", poiArray);
			return returnSuccessData("biz_id:"+biz_id+",poi_id:"+poi_id+" poi数据列表获取成功", lang, data);
		} else {
			return FastJsonUtil.error("1", "biz_id:"+biz_id+"业务关系树不存在");
		}
	}
	
	// 获取具体POI数据信息
	@Override
	public JSONObject getPoiInfoById(String json) {
		JSONObject param = JSONObject.parseObject(json);
		String poi_id = param.getString("poi_id");
		String lang = param.getString("lang");
		Document doc = getPoiById(poi_id, lang);
		if (doc == null) {
			MsLogger.debug("poi ["+poi_id+"] is not found!");
			return FastJsonUtil.error("-1", "poi ["+poi_id+"] is not found!");
		}

		// 从poi数据中挑选数据
		Set<Entry<String, Object>> entrySet = doc.entrySet();
		JSONObject poi = new JSONObject();
		for(Entry<String, Object> entry : entrySet){
			String key = entry.getKey();
			Map<String, String> fieldNames = getFieldNamesLst();
			Map<Integer, String> poiTags = getPoiTagsLst();
			Map<Integer, String> poiTypes = getPoiTypeId2NmLst();
			Map<Integer, String> poiSceneTypes = getPoiSceneTypeId2NmLst();
			
			if(fieldNames.containsKey(key) || "sub_tag".equals(key)){
				// 一级类别--tags
				if("tags".equals(key)){
					JSONArray array = FastJsonUtil.parse2Array(entry.getValue());
//					ArrayList<Integer> array = (ArrayList<Integer>) entry.getValue();
					int tag = array.getIntValue(0);
					JSONObject data = new JSONObject();
					if(poiTags.containsKey(tag)){
						data.put("ctgr_nm", poiTags.get(tag));
						data.put("ctgr_id", tag);
					}
					poi.put("ctgr", data);
					continue;
				}
				// 二级类别--sub_tag
				if("sub_tag".equals(key)){
					int keyval = (int) entry.getValue();
					JSONObject data = new JSONObject();
					if(poiTags.containsKey(keyval)){
						data.put("sub_ctgr_id", keyval);
						data.put("sub_ctgr_nm", poiTags.get(keyval));
					} 
					poi.put("sub_ctgr", data);
					continue;
				}
				// 标签--type
				if("type".equals(key)){
//					ArrayList<Integer> types = (ArrayList<Integer>) entry.getValue();
					JSONArray types = FastJsonUtil.parse2Array(entry.getValue());
					JSONArray data = new JSONArray();
					for(int i = 0; i < types.size(); i++){
						if(poiTypes.containsKey(types.getIntValue(i))){
							JSONObject type = new JSONObject();
							int id = types.getIntValue(i);
							type.put("hotel_type_id", id);
							type.put("hotel_type_nm", poiTypes.get(id));
							data.add(type);
						}
					}
					poi.put("hotel_types", data);
					continue;
				}
				// 标签--scene_type
				if("scene_type".equals(key)){
//					ArrayList<Integer> types = (ArrayList<Integer>) entry.getValue();
					JSONArray types = FastJsonUtil.parse2Array(entry.getValue());
					JSONArray data = new JSONArray();
					for(int i = 0; i < types.size(); i++){
						if(poiSceneTypes.containsKey(types.getIntValue(i))){
							JSONObject type = new JSONObject();
							int id = types.getIntValue(i);
							type.put("scene_type_id", id);
							type.put("scene_type_nm", poiTypes.get(id));
							data.add(type);
						}
					}
					poi.put("scene_types", data);
					continue;
				}
				// 经纬度--lnglat
				if("lnglat".equals(key)){
					Document lnglat = (Document) entry.getValue();
//					ArrayList<Double> coordinates = (ArrayList<Double>) lnglat.get("coordinates");
					JSONArray coordinates = FastJsonUtil.parse2Array(lnglat.get("coordinates"));
					JSONObject data = new JSONObject();
					data.put("lng", coordinates.getDouble(0));
					data.put("lat", coordinates.getDouble(1));
					poi.put("lnglat", data);
					continue;
				}
				if("merger_name".equals(key)){
					String merger_name = (String) entry.getValue();
					JSONObject data = poi.getJSONObject("zone");
					if(data == null){
						data = new JSONObject();
					}
					data.put(poiApiNameMap.getOuterVal("merger_name"), merger_name);
					poi.put("zone", data);
					continue;
				}
				if("zone_id".equals(key)){
					String zone_id = (String) entry.getValue();
					JSONObject data = poi.getJSONObject("zone");
					if(data == null){
						data = new JSONObject();
					}
					data.put(poiApiNameMap.getOuterVal("zone_id"), zone_id);
					poi.put("zone", data);
					continue;
				}
				key = poiApiNameMap.getOuterVal(key);
				// if(key != null){// 判断映射表是否存在对应名称。fieldNames.containsKey(key)表明是存在的，不需要此判断
					poi.put(poiApiNameMap.getOuterVal(key), entry.getValue());
				// }
			}
		}
		return returnSuccessData("poi_id:"+poi_id+"poi数据获取成功", lang, poi);
	}

	// 获取某个/几个标签下所有poi数据
	@Override
	public JSONObject getPoisByBizIdAndTags(String json) {
		JSONObject param = JSONObject.parseObject(json);
		int biz_id = param.getIntValue("biz_id");
		String tags = param.getString("tags");
		String fields = param.getString("fields");
		String lang = param.getString("lang");

		String type = param.getString("type");
		if(!"".equals(type)){// web端传入的是标签id
			return getPoisByBizIdAndTagIds(biz_id, type, tags, fields, lang);
		} else {// web端传入的是标签的名称
			return getPoisByBizIdAndTagNms(biz_id, tags, fields, lang);
		}
	}

	//------------------------------------------------------------------------------------------------------------------
	/**
	 * 缓存数据(key：字段名称，value:字段显示名称)
	 * 
	 * @return
	 */
	private Map<String, String> getFieldNamesLst() {
		if(!fieldNamesLst.isEmpty()){
			return fieldNamesLst;
		}
		synchronized (PoiApiBLImpl.class) {
			if(fieldNamesLst.isEmpty()){
				List<MsTagFieldResult> msTagFieldResults = msPoiFieldDAO.selectFieldNames(new MsTagFieldParameter());
				for(MsTagFieldResult msTagFieldResult : msTagFieldResults){
					String fieldName = msTagFieldResult.getFieldName();
					String fieldShowName = msTagFieldResult.getFieldShowName();
					fieldNamesLst.put(fieldName, fieldShowName);
				}
			}
		}
		return fieldNamesLst;
	}
	
	/**
	 * 缓存数据(key:类型id ,value:类型名称)
	 * 
	 * @return
	 */
	private Map<Integer, String> getPoiTagsLst(){
		if(!poiTagsLst.isEmpty()){
			return poiTagsLst;
		}
		synchronized (PoiApiBLImpl.class) {
			if(poiTagsLst.isEmpty()){
				List<MsTagFieldResult> msTagFieldResults = msPoiFieldDAO.selectPoiTags(new MsTagFieldParameter());
				for(MsTagFieldResult msTagFieldResult : msTagFieldResults){
					int tagId = msTagFieldResult.getTagId();
					String tagName = msTagFieldResult.getTagName();
					poiTagsLst.put(tagId, tagName);
				}
			}
		}
		return poiTagsLst;
	}
	
	/**
	 * 缓存数据(key:type的id,value:type值)
	 * 
	 * @return
	 */
	private Map<Integer, String> getPoiTypeId2NmLst(){
		if(!poiTypeId2NmLst.isEmpty()){
			return poiTypeId2NmLst;
		}
		synchronized (PoiApiBLImpl.class) {
			if(poiTypeId2NmLst.isEmpty()){
				MsPoiFieldCriteria msPoiFieldCriteria = new MsPoiFieldCriteria();
				MsPoiFieldCriteria.Criteria criteria = msPoiFieldCriteria.createCriteria();
				criteria.andFieldNameEqualTo("type");
				List<MsPoiField> msPoiFields = msPoiFieldDAO.selectByCriteria(msPoiFieldCriteria);
				if(!msPoiFields.isEmpty()){
					MsPoiField msPoiField = msPoiFields.get(0);
					String extensionAttrs = msPoiField.getExtensionAttrs(); 
					if(extensionAttrs == null){
						return poiTypeId2NmLst;
					}
					JSONArray array = JSONArray.parseArray(extensionAttrs);
					for(int i = 0;i<array.size();i++){
						JSONObject type = array.getJSONObject(i);
						Set<Entry<String, Object>> entrySet = type.entrySet();
						for(Entry<String, Object> entry : entrySet){
							String key = entry.getKey();
							String value = (String) entry.getValue();
							poiTypeId2NmLst.put(Integer.parseInt(key), value);
						}
					}
				}
			}
		}
		return poiTypeId2NmLst;
	}
	
	/**
	 * 缓存数据(key:sub_type的id,value:sub_type值)
	 * 
	 * @return
	 */
	private Map<Integer, String> getPoiSceneTypeId2NmLst(){
		if(!poiSceneTypeId2NmLst.isEmpty()){
			return poiSceneTypeId2NmLst;
		}
		synchronized (PoiApiBLImpl.class) {
			if(poiSceneTypeId2NmLst.isEmpty()){
				MsPoiFieldCriteria msPoiFieldCriteria = new MsPoiFieldCriteria();
				MsPoiFieldCriteria.Criteria criteria = msPoiFieldCriteria.createCriteria();
				criteria.andFieldNameEqualTo("scene_type");
				List<MsPoiField> msPoiFields = msPoiFieldDAO.selectByCriteria(msPoiFieldCriteria);
				if(!msPoiFields.isEmpty()){
					MsPoiField msPoiField = msPoiFields.get(0);
					String extensionAttrs = msPoiField.getExtensionAttrs(); 
					if(extensionAttrs == null){
						return poiSceneTypeId2NmLst;
					}
					JSONArray array = JSONArray.parseArray(extensionAttrs);
					for(int i = 0;i<array.size();i++){
						JSONObject type = array.getJSONObject(i);
						Set<Entry<String, Object>> entrySet = type.entrySet();
						for(Entry<String, Object> entry : entrySet){
							String key = entry.getKey();
							String value = (String) entry.getValue();
							poiSceneTypeId2NmLst.put(Integer.parseInt(key), value);
						}
					}
				}
			}
		}
		return poiSceneTypeId2NmLst;
	}
	
	/**
	 * 缓存数据(key:type值,value:type的id)
	 * 
	 * @return
	 */
	private Map<String, Integer> getPoiTypeNm2IdLst(){
		if(!poiTypeNm2IdLst.isEmpty()){
			return poiTypeNm2IdLst;
		}
		synchronized (PoiApiBLImpl.class) {
			if(poiTypeNm2IdLst.isEmpty()){
				Map<Integer, String> typeId2Nms = getPoiTypeId2NmLst();
				Set<Entry<Integer, String>> entrys = typeId2Nms.entrySet();
				for(Entry<Integer, String> entry : entrys){
					int id = entry.getKey();
					String nm = entry.getValue();
					poiTypeNm2IdLst.put(nm, id);
				}
			}
		}
		return poiTypeNm2IdLst;
	}
	
	/**
	 * 缓存数据(key:scene_type值,value:scene_type的id)
	 * 
	 * @return
	 */
	private Map<String, Integer> getPoiSceneTypeNm2IdLst(){
		if(!poiSceneTypeNm2IdLst.isEmpty()){
			return poiSceneTypeNm2IdLst;
		}
		synchronized (PoiApiBLImpl.class) {
			if(poiSceneTypeNm2IdLst.isEmpty()){
				Map<Integer, String> sceneTypeId2Nms = getPoiSceneTypeId2NmLst();
				Set<Entry<Integer, String>> entrys = sceneTypeId2Nms.entrySet();
				for(Entry<Integer, String> entry : entrys){
					int id = entry.getKey();
					String nm = entry.getValue();
					poiSceneTypeNm2IdLst.put(nm, id);
				}
			}
		}
		return poiSceneTypeNm2IdLst;
	}
	
	/**
	 * 根据poi 的id值获取poi信息
	 * @param _id 
	 * @return
	 */
	private Document getPoiById(String _id, String lang) {
		MongoCollection<Document> poi_collection = null;
		if("zh".equals(lang)){
			poi_collection = mongoConnector.getDBCollection("poi_collection");
		} else if("en".equals(lang)){
			poi_collection = mongoConnector.getDBCollection("poi_collection_en");
		}
		if(poi_collection != null) {
			BasicDBObject keyId = new BasicDBObject();
			keyId.put("_id", new ObjectId(_id));
			return poi_collection.find(keyId).limit(1).first(); 
		} else {
			return null;
		}
	}
	
	/**
	 * 获得某个poi的子poi结点
	 * @param pois 子POI结点id列表
	 * @param jsoncList 业务树中poi结点下的c_list数据
	 * @param poi_id poi id
	 * @param level 当前所在的层与目标POI（父POI）所在层的高度差。若level=0，表明当前层即为所要寻找的父poi结点所在的层。
	 * @param order 目标POI（父POI）所在层的顺序。目前暂不使用。
	 */
	private void getPoisByParentId(List<String> pois, Document jsoncList, String poi_id, int level, int order) {
		if(level < 0){
			return;
		}
		Set<Entry<String, Object>> entrySet = jsoncList.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			String _id = entry.getKey();
			Document innerJsoncList = (Document) entry.getValue();
			Document subClist = (Document) innerJsoncList.get("c_list");//下一层数据
			if (subClist.isEmpty()) {// 下一层无poi数据
				continue;
			}
			// 当前结点是查找结点
			if(_id.equals(poi_id) && level == 0){
				pois.addAll(subClist.keySet());
				return;
			}
			// 递归向下，层级减1
			getPoisByParentId(pois, subClist, poi_id, level-1, order);
		}
	}
	
	/**
	 * mongodb数据库business_tree表，递归查找Poi集合
	 * @param set
	 * @param jsoncList
	 */
	private void getPoisFromBizTree(Set<String> set, Document jsoncList) {
		Set<Entry<String, Object>> entrySet = jsoncList.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			String _id = entry.getKey();
			Document innerJsoncList = (Document) entry.getValue();
			set.add(_id);
			Document subClist = (Document) innerJsoncList.get("c_list");
			if (subClist.isEmpty()) {
				continue;
			}
			getPoisFromBizTree(set, subClist);
		}
	}
	
	/**
	 * 获取业务关系树
	 * @param biz_id 业务树id
	 * @return
	 */
	private Document getBizTreeById(int biz_id) {
		MongoCollection<Document> business_tree = mongoConnector.getDBCollection("business_tree");
		BasicDBObject condition = new BasicDBObject();
		condition.put("business_id", biz_id);
		Document tree = null;
		tree = business_tree.find(condition).first();
		return tree;
	}
	
	/**
	 * 获取树的同一层poi结点的id
	 * @param biz_id 业务树id
	 * @param level 树的层级
	 * @return
	 */
	List<String> getPoisWithLevelInBizTree(Document tree, int level){
		if(level != 1){ //目前只获取第一层数据
			return null;
		}
		Document subTree = (Document) tree.get("c_list");
		if(!subTree.isEmpty()){
			Set<String> keys = subTree.keySet();
			ArrayList<String> lst = new ArrayList<>(keys);
			return lst;
		}
		return null;
	}
	
	/**
	 * 通过标签返回指定字段的poi列表
	 * 
	 * @param poiIdLst poi id集
	 * @param type 标签类型
	 * @param tags 标签id集
	 * @param fields 字段集
	 * @param lang 语言
	 * @return
	 */
	private JSONArray getPoiInfoByTagsWithFields(Set<String> poiIdLst, String type, String[] tags, String[] fields, String lang) {
		MongoCollection<Document> poi_collection = null;
		if("zh".equals(lang)){
			poi_collection = mongoConnector.getDBCollection("poi_collection");
		} else if("en".equals(lang)){
			poi_collection = mongoConnector.getDBCollection("poi_collection_en");
		}
		JSONArray array = new JSONArray();
		if(poi_collection != null){
			BasicDBList valueList = new BasicDBList();
			for(String poi_id : poiIdLst){
				BasicDBObject condition = new BasicDBObject();
				BasicDBList value = new BasicDBList();
				for(String tag : tags){
					value.add(Integer.parseInt(tag));
				}
				BasicDBObject all = new BasicDBObject("$in",value);
				condition.append("_id", new ObjectId(poi_id)).append(type, all);
				valueList.add(condition);
			}
			BasicDBObject or = new BasicDBObject("$or", valueList);
			MongoCursor<Document> mongoCursor = poi_collection.find(or).iterator();
			while (mongoCursor.hasNext()) {
				Document docPoi= mongoCursor.next();
				JSONObject poi = getPoiInfoByFields(docPoi, fields);
				if(!poi.isEmpty()){
					poi.put("poi_id", docPoi.get("_id").toString());
					array.add(poi);
				}
			}
		}
		return array;
	}
	
	/**
	 * 通过标签返回指定字段的poi列表
	 * 
	 * @param poiIdLst poi id集
	 * @param typesAndIds 标签的属性与id集合
	 * @param fields 字段集
	 * @param lang 语言
	 * @return
	 */
	private JSONArray getPoiInfoByTagsWithFields(Set<String> poiIdLst, Map<String, List<Integer>> typesAndIds,
			String[] fields, String lang) {
		
		MongoCollection<Document> poi_collection = null;
		if("zh".equals(lang)){
			poi_collection = mongoConnector.getDBCollection("poi_collection");
		} else if("en".equals(lang)){
			poi_collection = mongoConnector.getDBCollection("poi_collection_en");
		}
		
		JSONArray array = new JSONArray();
		if(poi_collection != null){
			BasicDBList idConditonList = new BasicDBList();
			for(String poi_id : poiIdLst){
				Set<Entry<String, List<Integer>>> entrys = typesAndIds.entrySet();
				for(Entry<String, List<Integer>> entry : entrys){
					String type = entry.getKey();
					List<Integer> tags = entry.getValue();// 标签
					BasicDBObject idCondition = new BasicDBObject();
					BasicDBList value = new BasicDBList();
					for(Integer tag : tags){
						value.add(tag);
					}
					BasicDBObject in = new BasicDBObject("$in",value);
					idCondition.append("_id", new ObjectId(poi_id)).append(type, in);
					idConditonList.add(idCondition);
				}
			}
			BasicDBObject or = new BasicDBObject("$or", idConditonList);
			MongoCursor<Document> mongoCursor = poi_collection.find(or).iterator();
			while (mongoCursor.hasNext()) {
				Document docPoi= mongoCursor.next();
				JSONObject poi = getPoiInfoByFields(docPoi, fields);
				if(!poi.isEmpty()){
					poi.put("poi_id", docPoi.get("_id").toString());
					array.add(poi);
				}
			}
		}
		return array;
	}

	/**
	 * 根据标签名称解析出标签对应的标签属性和id值
	 * 
	 * @param types 标签名称
	 * @return
	 */
	private Map<String, List<Integer>> getTypesAndIdsByTypeNms(String types) { 
		Map<String, List<Integer>> list = new HashMap<>();
		list.put("type", new LinkedList<Integer>());
		list.put("scene_type", new LinkedList<Integer>());
		Map<String, Integer> poiTypeNm2Ids = getPoiTypeNm2IdLst();
		Map<String, Integer> poiSceneTypeNm2Ids = getPoiSceneTypeNm2IdLst();
		String[] tagArr = types.split(",");
		for(String tag : tagArr){
			if(poiTypeNm2Ids.containsKey(tag)){
				list.get("type").add(poiTypeNm2Ids.get(tag));
			}
			if(poiSceneTypeNm2Ids.containsKey(tag)){
				list.get("scene_type").add(poiSceneTypeNm2Ids.get(tag));
			}
		}
		return list;
	}
	
	/**
	 * 获取给定字段和id的poi
	 * @param poi_id poi 的id
	 * @param fields 需要获取的字段
	 * @return
	 */
	private JSONArray getPoiInfoWtihFields(List<String> poiIdLst, String[] fields, String lang) {
		MongoCollection<Document> poi_collection = null;
		if("zh".equals(lang)){
			poi_collection = mongoConnector.getDBCollection("poi_collection");
		} else if("en".equals(lang)){
			poi_collection = mongoConnector.getDBCollection("poi_collection_en");
		}
		JSONArray array = new JSONArray();
		if(poi_collection != null){
			BasicDBList value = new BasicDBList();
			for(String poiId : poiIdLst){
				BasicDBObject condition = new BasicDBObject();
				condition.put("_id", new ObjectId(poiId));
				value.add(condition);
			}
			BasicDBObject or = new BasicDBObject("$or", value);
			MongoCursor<Document> mongoCursor = poi_collection.find(or).iterator();
			while (mongoCursor.hasNext()) {
				Document docPoi= mongoCursor.next();
				JSONObject poi = getPoiInfoByFields(docPoi, fields);
				if(!poi.isEmpty()){
					poi.put("poi_id", docPoi.get("_id").toString());
					array.add(poi);
				}
			}
		} 
		return array;
	}

	/**
	 * 获取父poi下满足一级分类id的poi子结点（poi仅包含给定字段数据）
	 * @param poi_id poi ID号
	 * @param ctgr_id 一级分类
	 * @param fields 需要获取的字段
	 * @return
	 */
	private JSONArray getPoiInfoByCtgrIdWithFields(List<String> poiIdLst, int ctgr_id, String[] fields, String lang) {
		int[] ctgrArr = new int[1];
		ctgrArr[0] = ctgr_id;
		MongoCollection<Document> poi_collection = null;
		if("zh".equals(lang)){
			poi_collection = mongoConnector.getDBCollection("poi_collection");
		} else if("en".equals(lang)){
			poi_collection = mongoConnector.getDBCollection("poi_collection_en");
		}
		JSONArray array = new JSONArray();
		if(poi_collection != null) {
			BasicDBList value = new BasicDBList();
			for(String poi_id : poiIdLst){
				BasicDBObject condition = new BasicDBObject();
				condition.append("_id", new ObjectId(poi_id)).append("tags", ctgrArr);
				value.add(condition);
			}
			BasicDBObject or = new BasicDBObject("$or", value);
			MongoCursor<Document> mongoCursor = poi_collection.find(or).iterator();
			while (mongoCursor.hasNext()) {
				Document docPoi= mongoCursor.next();
				JSONObject poi = getPoiInfoByFields(docPoi, fields);
				if(!poi.isEmpty()){
					poi.put("poi_id", docPoi.get("_id").toString());
					array.add(poi);
				}
			}
		}
		return array;
	}

	/**
	 * 获取父poi下满足二级分类id的poi子结点（poi仅包含给定字段数据）
	 * @param poi_id poi ID号
	 * @param sub_ctgr_id 二级分类
	 * @param fields 需要获取的字段
	 * @return
	 */
	private JSONArray getPoiInfoBySubCtgrIdWithFields(List<String> poiIdLst, int sub_ctgr_id, String[] fields, String lang) {
		MongoCollection<Document> poi_collection = null;
		if("zh".equals(lang)){
			poi_collection = mongoConnector.getDBCollection("poi_collection");
		} else if("en".equals(lang)){
			poi_collection = mongoConnector.getDBCollection("poi_collection_en");
		}
		JSONArray array = new JSONArray();
		if(poi_collection != null) {
			BasicDBList value = new BasicDBList();
			for(String poi_id : poiIdLst){
				BasicDBObject condition = new BasicDBObject();
				condition.append("_id", new ObjectId(poi_id)).append("sub_tag", sub_ctgr_id);
				value.add(condition);
			}
			BasicDBObject or = new BasicDBObject("$or", value);
			MongoCursor<Document> mongoCursor = poi_collection.find(or).iterator();
			while (mongoCursor.hasNext()) {
				Document docPoi= mongoCursor.next();
				JSONObject poi = getPoiInfoByFields(docPoi, fields);
				if(!poi.isEmpty()){
					poi.put("poi_id", docPoi.get("_id").toString());
					array.add(poi);
				}
			}
		}
		return array;
	}
	
	private JSONObject getPoiInfoByFields(Document doc, String[] fields) {
		JSONObject result = new JSONObject();
		if(fields == null || fields.length == 0){
			fields = poiApiNameMap.getApiFields();
		}
		if(doc != null){
			Document resJson = doc;
			for(String field : fields){
				field = poiApiNameMap.getInnerVal(field); 
				String key = field;
				if(resJson.containsKey(key) || "sub_tag".equals(key)){
					Map<String, String> fieldNames = getFieldNamesLst();
					Map<Integer, String> poiTags = getPoiTagsLst();
					Map<Integer, String> poiTypes = getPoiTypeId2NmLst();
					Map<Integer, String> poiSceneTypes = getPoiSceneTypeId2NmLst();
					
					if(fieldNames.containsKey(key) || "sub_tag".equals(key)){
						// 一级类别--tags
						if("tags".equals(key)){
//							ArrayList<Integer> array = (ArrayList<Integer>) resJson.get("tags");
							JSONArray array = FastJsonUtil.parse2Array(resJson.get("tags"));
							JSONObject data = new JSONObject();
							int tag = array.getIntValue(0);
							if(poiTags.containsKey(tag)){
								data.put("ctgr_nm", poiTags.get(tag));
								data.put("ctgr_id", tag);
							}
							result.put("ctgr", data);
							continue;
						}
						// 二级类别--sub_tag
						if("sub_tag".equals(key)){
							int keyval = resJson.getInteger("sub_tag");
							JSONObject data = new JSONObject();
							if(poiTags.containsKey(keyval)){
								data.put("sub_ctgr_nm", poiTags.get(keyval));
								data.put("sub_ctgr_id", keyval);
							}
							result.put("sub_ctgr", data);
							continue;
						}
						// 标签--type
						if("type".equals(key)){
//							ArrayList<Integer> types = (ArrayList<Integer>) resJson.get("type");
							JSONArray types = FastJsonUtil.parse2Array(resJson.get("type"));
							JSONArray array = new JSONArray();
							for(int i = 0; i < types.size(); i++){
								if(poiTypes.containsKey(types.getIntValue(i))){
									JSONObject data = new JSONObject();
									int id = types.getIntValue(i);
									data.put("hotel_type_id",id);
									data.put("hotel_type_nm", poiTypes.get(id));
									array.add(data);
								}
							}
							result.put("hotel_types", array);
							continue;
						}
						// 标签--scene_type
						if("scene_type".equals(key)){
//							ArrayList<Integer> types = (ArrayList<Integer>) resJson.get("scene_type");
							JSONArray types = FastJsonUtil.parse2Array(resJson.get("scene_type"));
							JSONArray array = new JSONArray();
							for(int i = 0; i < types.size(); i++){
								if(poiSceneTypes.containsKey(types.getIntValue(i))){
									JSONObject data = new JSONObject();
									int id = types.getIntValue(i);
									data.put("scene_type_id",id);
									data.put("scene_type_nm", poiSceneTypes.get(id));
									array.add(data);
								}
							}
							result.put("scene_types", array);
							continue;
						}
						// 经纬度--lnglat
						if("lnglat".equals(key)){
							Document lnglat = (Document) resJson.get("lnglat");
//							ArrayList<Double> coordinates = (ArrayList<Double>) lnglat.get("coordinates");
							JSONArray coordinates = FastJsonUtil.parse2Array(lnglat.get("coordinates"));
							JSONObject data = new JSONObject();
							data.put("lng", coordinates.getDoubleValue(0));
							data.put("lat", coordinates.getDoubleValue(1));
							result.put("lnglat", data);
							continue;
						}
						if("merger_name".equals(key)){
							String merger_name = resJson.getString("merger_name");
							JSONObject data = result.getJSONObject("zone");
							if(data == null){
								data = new JSONObject();
							}
							data.put(poiApiNameMap.getOuterVal("merger_name"), merger_name);
							result.put("zone", data);
							continue;
						}
						if("zone_id".equals(key)){
							String zone_id =  resJson.getString("zone_id");
							JSONObject data = result.getJSONObject("zone");
							if(data == null){
								data = new JSONObject();
							}
							data.put(poiApiNameMap.getOuterVal("zone_id"), zone_id);
							result.put("zone", data);
							continue;
						}
						result.put(poiApiNameMap.getOuterVal(key), resJson.get(key));
					}
				}
			}
		}
		return result;
	}

	private JSONObject getPoisByBizIdAndTagIds(int biz_id, String type, String tags, String fields, String lang) {
		// 字段集
		String[] fieldArr;
		if("".equals(fields)){// 检测是否含有字段信息
			fieldArr = null;
		} else {
			fields = fields.replaceAll("zone", "zone_id,zone_name");
			fieldArr = fields.split(",");
		}
		
		String[] tagArr = tags.split(",");
		// 名称映射转换
		type = poiApiNameMap.getInnerVal(type);

		// 获取业务关系树
		Document tree = getBizTreeById(biz_id);
		if(tree != null){
			// 获取子POI id集合
			Document jsoncList = (Document)tree.get("c_list");
			Set<String> poiIdLst = new HashSet<>();
			getPoisFromBizTree(poiIdLst,jsoncList);
			
			// 根据标签和字段返回满足要求的poi
			JSONObject data = new JSONObject();
			JSONArray poiArray = new JSONArray();
			if(!poiIdLst.isEmpty()){
				poiArray = getPoiInfoByTagsWithFields(poiIdLst, type, tagArr, fieldArr, lang); 
			}
			data.put("pois", poiArray);
			return returnSuccessData("biz_id:"+biz_id+",tags:"+tags+"poi数据列表获取成功", lang, data);
		} else {
			return FastJsonUtil.error("1", "biz_id:"+biz_id+"业务关系树不存在");
		}
	}

	private JSONObject getPoisByBizIdAndTagNms(int biz_id, String tags, String fields, String lang) {
		// 根据标签名称获取tag属性和tag的id值
		Map<String, List<Integer>> typesAndIds = getTypesAndIdsByTypeNms(tags);// 返回的type值已经经过名称映射
		
		// 字段集
		String[] fieldArr;
		if("".equals(fields)){// 检测是否含有字段信息
			fieldArr = null;
		} else {
			fields = fields.replaceAll("zone", "zone_id,zone_name");
			fieldArr = fields.split(",");
		}
		
		// 获取业务关系树
		Document tree = getBizTreeById(biz_id);
		if(tree != null){
			// 获取子POI id集合
			Document jsoncList = (Document)tree.get("c_list");
			Set<String> poiIdLst = new HashSet<>();
			getPoisFromBizTree(poiIdLst,jsoncList);
			
			// 根据标签和字段返回满足要求的poi
			JSONObject data = new JSONObject();
			JSONArray poiArray = new JSONArray();
			if(!poiIdLst.isEmpty()){
				poiArray = getPoiInfoByTagsWithFields(poiIdLst, typesAndIds, fieldArr, lang);
			}
			data.put("pois", poiArray);
			return returnSuccessData("biz_id:"+biz_id+",tags:"+tags+"poi数据列表获取成功", lang, data);
		} else {
			return FastJsonUtil.error("1", "biz_id:"+biz_id+"业务关系树不存在");
		}
	}
	
	private JSONObject returnSuccessData(String msg, String lang, JSONObject data){
		JSONObject json = new JSONObject();
		json.put(lang, data);
		return FastJsonUtil.sucess(msg, json);
	}

//	public static void main(String[] args) {
//		Document doc = new Document();
//		System.out.println(doc.containsKey(null));
//	}
}
