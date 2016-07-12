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

import org.apache.log4j.Logger;
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
import ms.luna.biz.bl.MsZoneCacheBL;
import ms.luna.biz.bl.PoiApiBL;
import ms.luna.biz.dao.custom.MsBusinessDAO;
import ms.luna.biz.dao.custom.MsPoiFieldDAO;
import ms.luna.biz.dao.custom.MsPoiTagDAO;
import ms.luna.biz.dao.custom.MsRTagFieldDAO;
import ms.luna.biz.dao.custom.model.MsPoiFieldNameResult;
import ms.luna.biz.dao.custom.model.MsTagFieldParameter;
import ms.luna.biz.dao.custom.model.MsTagFieldResult;
import ms.luna.biz.dao.model.MsBusiness;
import ms.luna.biz.dao.model.MsPoiField;
import ms.luna.biz.dao.model.MsPoiFieldCriteria;
import ms.luna.biz.dao.model.MsPoiTag;
import ms.luna.biz.dao.model.MsPoiTagCriteria;
import ms.luna.biz.dao.model.MsRTagField;
import ms.luna.biz.dao.model.MsRTagFieldCriteria;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.common.PoiCommon;

/**
 * @author greek
 *
 */
@Transactional(rollbackFor = Exception.class)
@Service("poiApiBL")
public class PoiApiBLImpl implements PoiApiBL {

	private final static Logger logger = Logger.getLogger(PoiApiBLImpl.class);

	@Autowired
	private MongoConnector mongoConnector;

	@Autowired
	private MsBusinessDAO msBusinessDAO;

	@Autowired
	private MsPoiFieldDAO msPoiFieldDAO;

	@Autowired
	private MsZoneCacheBL msZoneCacheBL;
	
	@Autowired
	private MsPoiTagDAO msPoiTagDAO;
	
	@Autowired
	private MsRTagFieldDAO msRTagFieldDAO;
	
	private static Map<String, String> api2db_nm = new LinkedHashMap<>();// 外部接口字段  与  内部数据库字段 的映射

	private static Map<String, String> db2api_nm = new LinkedHashMap<>();// 内部数据库字段  与  外部接口字段 的映射
	
//	private static Map<String, String> fieldNamesLst = new LinkedHashMap<>();// (key：字段名称，value:字段显示名称)

	private static Map<Integer, String> poiTagsLst = new LinkedHashMap<>();// (key:类型id, value:类型名称)

	private static Map<Integer, String> poiTypeId2NmLst = new LinkedHashMap<>();// (key:type的id,value:type值)

	private static Map<Integer, String> poiSceneTypeId2NmLst = new LinkedHashMap<>();// (key:sub_type的id,value:sub_type值)

	private static Map<String, Integer> poiTypeNm2IdLst = new LinkedHashMap<>();// (key:type值,value:type的id)

	private static Map<String, Integer> poiSceneTypeNm2IdLst = new LinkedHashMap<>();// (key:type值,value:type的id)

	private static Map<Integer, String> poiPanoramaTypeId2NmLst = new LinkedHashMap<>(); // (key:全景类别id，value: 全景类别名称)

	private static List<String> commonFields = new ArrayList<>();// 公有字段集合

	private static Map<Integer, List<String>> poiTag2PrivateField = new LinkedHashMap<>(); // (key:类型id, value:类型下的私有字段名称)
	
	/**
	 * 获取二级分类为“其他”时 topTag与subTag的映射关系
	 */
	private static Map<Integer, Integer> topTag2SubTagOthersCache = new LinkedHashMap<>();// (旅游景点, 其他) --> (2,18)
	
	@Override
	public void init() {
		// ---------------数据库字段名称与对外接口名称的映射关系---------------
		List<MsPoiFieldNameResult> list = msPoiFieldDAO.selectFieldNames();
		if(list != null && !list.isEmpty()) {
			for(int i = 0; i < list.size(); i++){
				String field_name = list.get(i).getFieldName();
				String field_alias = list.get(i).getFieldAlias();
				db2api_nm.put(field_name, field_alias);
				api2db_nm.put(field_alias, field_name); 
			}
//			
//			// MySql有而Mongo没有的字段
//			db2api_nm.put("sub_tag", "sub_category"); // 二级分类
//			
//			// 聚合字段名称
//			db2api_nm.put("panarama", "panorama"); // 全景。包含全景标识，全景类型id，全景类型名称
//			db2api_nm.put("address", "address"); // 地址。包含zone_id,省，市，县名称，detail_address
//			
//			// 拆分字段
//			db2api_nm.put("tags", "tags");// tags：拆分成category_id和category_name
//			
//			// 修改字段
//			db2api_nm.put("lnglat", "lnglat");// lnglat:将原格式修改为直接包含lat和lng两个字段（不再包含Point）
		}
		
		// ---------------一级类别与下属私有字段集合的映射关系---------------
		// 获取一级类别
		MsPoiTagCriteria msPoiTagCriteria = new MsPoiTagCriteria();
		MsPoiTagCriteria.Criteria criteria = msPoiTagCriteria.createCriteria();
		criteria.andTagLevelEqualTo(1);
		List<MsPoiTag> tagList = msPoiTagDAO.selectByCriteria(msPoiTagCriteria);
		if(tagList != null && !tagList.isEmpty()) {
			for(int i = 0; i< tagList.size(); i++) {
				Integer tag_id = tagList.get(i).getTagId();
				List<String> privateFields = new ArrayList<>();
				// 获取一级类别下的私有字段privatefield
				MsRTagFieldCriteria msRTagFieldCriteria = new MsRTagFieldCriteria();
				MsRTagFieldCriteria.Criteria criteria2 = msRTagFieldCriteria.createCriteria();
				criteria2.andTagIdEqualTo(tag_id);
				List<MsRTagField> privateFieldLst= msRTagFieldDAO.selectByCriteria(msRTagFieldCriteria);
				if( privateFieldLst != null && !privateFieldLst.isEmpty()) {
					for(int m = 0; m < privateFieldLst.size(); m++) {
						String field_name = privateFieldLst.get(m).getFieldName();
						privateFields.add(field_name);
					}
				}
				poiTag2PrivateField.put(tag_id, privateFields);
			}
		}
		
		// ---------------获取所有的公有字段---------------
		MsRTagFieldCriteria msRTagFieldCriteria = new MsRTagFieldCriteria();
		MsRTagFieldCriteria.Criteria criteria3 = msRTagFieldCriteria.createCriteria();
		criteria3.andTagIdEqualTo(1);
		List<MsRTagField>  commonFieldlst = msRTagFieldDAO.selectByCriteria(msRTagFieldCriteria);
		if(commonFieldlst != null && !commonFieldlst.isEmpty()) {
			for(int i = 0; i < commonFieldlst.size(); i++){
				String field_name = commonFieldlst.get(i).getFieldName();
				commonFields.add(field_name);
			}
			// 公共字段有一个特例：sub_tag。sub_tag存在mongo中而不存在mysql中
			commonFields.add("sub_tag");
		}
		
	}
	// 获得第一层所有poi数据
	@Override
	public JSONObject getPoisInFirstLevel(String json) {
		JSONObject param = JSONObject.parseObject(json);
		int biz_id = param.getIntValue("biz_id");
		String fields = param.getString("fields");
		String lang = param.getString("lang");
		List<String> fieldLst;
		fieldLst = convertInputApiFields2DbFieldLst(fields);
		// 获取业务关系树
		Document tree = getBizTreeById(biz_id);
		if (tree != null) {
			List<String> poiIdLst = null;
			poiIdLst = getPoisWithLevelInBizTree(tree, 1);

			JSONObject data = new JSONObject();
			JSONArray poiArray = new JSONArray();
			if (poiIdLst != null) {
				poiArray = getPoiInfoWtihFields(poiIdLst, fieldLst, lang);
			}
			data.put("pois", poiArray);
			JSONObject resultdata = returnSuccessData("success", lang, data);
			resultdata.put("businessTree_name", getBizTreeNmById(biz_id));
			return resultdata;
		} else {
			return FastJsonUtil.errorWithMsg("LUNA.E0012", "业务关系树（business_id:"+biz_id+"）");
		}
	}

	// 根据业务获取一个层级的poi数据列表
	@Override
	public JSONObject getPoisWithLevel(String json) {
		// TODO
		return null;
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
		List<String> fieldLst;
		fieldLst = convertInputApiFields2DbFieldLst(fields);
		// 获取业务关系树
		Document tree = getBizTreeById(biz_id);
		if (tree != null) {
			// 获取给定poi结点下的子结点
			List<String> poiIdLst = new ArrayList<>();
			// 注：目前仅获得第一层下的poi结点
			getPoisByParentId(poiIdLst, tree, poi_id, 0, 0, Boolean.FALSE);

			JSONObject data = new JSONObject();
			JSONArray poiArray = new JSONArray();
			// 获取子结点中类型为ctgrId，且含有指定字段的集合
			if (!poiIdLst.isEmpty()) {
				poiArray = getPoiInfoByCtgrIdWithFields(poiIdLst, ctgr_id, fieldLst, lang);
			}
			data.put("pois", poiArray);
			JSONObject resultdata = returnSuccessData("success", lang, data);
			resultdata.put("businessTree_name", getBizTreeNmById(biz_id));
			resultdata.put("category_name", getPoiTagsLst().get(ctgr_id));
			return resultdata;
		} else {
			return FastJsonUtil.errorWithMsg("LUNA.E0012", "业务关系树（business_id:"+biz_id+"）");
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
		List<String> fieldLst;
		fieldLst = convertInputApiFields2DbFieldLst(fields);
		// 获取业务关系树
		Document tree = getBizTreeById(biz_id);
		if (tree != null) {
			// 获取给定poi结点下的子结点
			List<String> poiIdLst = new ArrayList<>();
			// 注：目前仅获得第一层下的poi结点
			getPoisByParentId(poiIdLst, tree, poi_id, 0, 0, Boolean.FALSE);

			JSONObject data = new JSONObject();
			JSONArray poiArray = new JSONArray();
			if (!poiIdLst.isEmpty()) {
				poiArray = getPoiInfoBySubCtgrIdWithFields(poiIdLst, sub_ctgr_id, fieldLst, lang);
			}
			data.put("pois", poiArray);
			JSONObject resultdata = returnSuccessData("success", lang, data);
			resultdata.put("businessTree_name", getBizTreeNmById(biz_id));
			resultdata.put("sub_category_name",  getPoiTagsLst().get(sub_ctgr_id));
			return resultdata;
		} else {
			return FastJsonUtil.errorWithMsg("LUNA.E0012", "业务关系树（business_id:"+biz_id+"）");
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
		List<String> fieldLst;
		fieldLst = convertInputApiFields2DbFieldLst(fields);
		// 获取业务关系树
		Document tree = getBizTreeById(biz_id);
		if (tree != null) {
			// 获取给定poi结点下的子结点
			List<String> poiIdLst = new ArrayList<>();
			// 注：目前仅获得第一层下的poi结点
			getPoisByParentId(poiIdLst, tree, poi_id, 0, 0, Boolean.FALSE);

			JSONObject data = new JSONObject();
			JSONArray poiArray = new JSONArray();
			// 获取子结点中类型为ctgrId，且含有指定字段的集合
			if (!poiIdLst.isEmpty()) {
				poiArray = getPoiInfoWtihFields(poiIdLst, fieldLst, lang);
			}
			data.put("pois", poiArray);
			JSONObject resultdata = returnSuccessData("success", lang, data);
			resultdata.put("businessTree_name", getBizTreeNmById(biz_id));
			return resultdata;
		} else {
			return FastJsonUtil.errorWithMsg("LUNA.E0012", "业务关系树（business_id:"+biz_id+"）");
		}
	}

	// 获取具体POI数据信息
	@Override
	public JSONObject getPoiInfoById(String json) {
		JSONObject param = JSONObject.parseObject(json);
		String poi_id = param.getString("poi_id");
		String lang = param.getString("lang");
		Document doc = getPoiById(poi_id, lang);
		List<String> fieldLst;
//		convertInputApiFields2DbFieldLst("", fieldLst);
		fieldLst = convertInputApiFields2DbFieldLst("");
		if (doc == null) {
			logger.error("Failed to get doc, request json: " + json);
			return returnSuccessData("success", lang, new JSONObject());
		}
		JSONObject result = getPoiInfoWithFields(doc, fieldLst, lang);
		return returnSuccessData("success", lang, result);
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
		List<String> fieldLst;
		fieldLst = convertInputApiFields2DbFieldLst(fields);
        String[] tagArr = tags.split(",");
		List<String> tagLst = new ArrayList<>();
		for(int i = 0; i < tagArr.length; i++){
		    tagLst.add(tagArr[i]);
		}
		Map<String, List<Integer>> typesAndIds = null;
		
		if (!"".equals(type)) {// web端传入的是标签id
			type = this.convertApiField2DbField(type);// 名称映射转换
		} else {// web端传入的是标签的名称
			typesAndIds = getTypesAndIdsByTypeNms(tags);// 返回的type值已经经过名称映射
		}
		
		// 获取业务关系树
		Document tree = getBizTreeById(biz_id);
	    if (tree != null) {
	    	// 获取子POI id集合
	    	Set<String> poiIdLst = new HashSet<>();
	    	getPoisFromBizTree(tree, poiIdLst);
	        // 根据标签和字段返回满足要求的poi
	    	JSONObject data = new JSONObject();
	        JSONArray poiArray = new JSONArray();
            if (!poiIdLst.isEmpty()) {
            	if (!"".equals(type)) {
            		poiArray = getPoiInfoByTagsWithFields(poiIdLst, type, tagLst, fieldLst, lang);
            	} else {
            		poiArray = getPoiInfoByTagsWithFields(poiIdLst, typesAndIds, fieldLst, lang);
            	}
            }
			data.put("pois", poiArray);
			JSONObject resultdata = returnSuccessData("success", lang, data);
			resultdata.put("businessTree_name", getBizTreeNmById(biz_id));
			return resultdata;
		} else {
			return FastJsonUtil.errorWithMsg("LUNA.E0012", "业务关系树（business_id:"+biz_id+"）");
		}
	}

	// 根据业务和POI获取下一层的一级类别列表
	@Override
	public JSONObject getCtgrsByBizIdAndPoiId(String json) {
		JSONObject param = JSONObject.parseObject(json);
		int biz_id = param.getIntValue("biz_id");
		String poi_id = param.getString("poi_id");
		// 获取业务关系树
		Document tree = getBizTreeById(biz_id);
		if (tree != null) {
			// 获取给定poi结点下的子结点
			List<String> poiIdLst = new ArrayList<>();
			getPoisByParentId(poiIdLst, tree, poi_id, 0, 0, Boolean.FALSE);

			JSONObject data = new JSONObject();
			Set<Integer> ctgrIdSet = new HashSet<>(); // 类别集合
			for (String _id : poiIdLst) {
				// 读取数据库
				Document doc = getPoiById(_id, PoiCommon.POI.ZH); // 默认读取中文库
				if (doc != null) {
					JSONArray tags = FastJsonUtil.parse2Array(doc.get("tags"));
					ctgrIdSet.add(tags.getInteger(0));
				}
			}
			// 根据类别id获取类别名称
			JSONArray array = new JSONArray();
			if (!ctgrIdSet.isEmpty()) {
				Map<Integer, String> map = getPoiTagsLst();
				for (Integer ctgrId : ctgrIdSet) {
					if(!map.containsKey(ctgrId)){
						continue;
					}
					JSONObject ctgr = new JSONObject();
					ctgr.put("category_id", ctgrId);
					ctgr.put("category_name", map.get(ctgrId));
					array.add(ctgr);
				}
			}
			data.put("categorys", array);
			JSONObject resultdata = FastJsonUtil.sucess("success", data);
			resultdata.put("businessTree_name", getBizTreeNmById(biz_id));
			return resultdata;
		} else {
			return FastJsonUtil.errorWithMsg("LUNA.E0012", "业务关系树（business_id:"+biz_id+"）");
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
		if (tree != null) {
			// 获取给定poi结点下的子结点
			List<String> poiIdLst = new ArrayList<>();
			getPoisByParentId(poiIdLst, tree, poi_id, 0, 0, Boolean.FALSE);

			JSONObject data = new JSONObject();
			Set<Integer> subCtgrIdSet = new HashSet<>(); // 类别集合
			for (String _id : poiIdLst) {
				// 读取数据库
				Document doc = getPoiById(_id, PoiCommon.POI.ZH);
				if (doc != null) {
					JSONArray tags = FastJsonUtil.parse2Array(doc.get("tags"));
					if (!tags.isEmpty() && tags.getInteger(0) == ctgr_id) {
						// 获取二级分类
						int sub_tag_id = doc.getInteger("sub_tag");
						subCtgrIdSet.add(sub_tag_id);
					}
				}
			}

			// 根据类别id获取类别名称
			JSONArray array = new JSONArray();
			if (!subCtgrIdSet.isEmpty()) {
				Map<Integer, String> map = getPoiTagsLst();
				for (Integer subCtgrId : subCtgrIdSet) {
					if(!map.containsKey(subCtgrId)){
						continue;
					}
					JSONObject subctgr = new JSONObject();
					subctgr.put("sub_category_id", subCtgrId);
					subctgr.put("sub_category_name", map.get(subCtgrId));
					array.add(subctgr);
				}
			}
			data.put("sub_categorys", array);
			JSONObject resultdata = FastJsonUtil.sucess("success", data);
			resultdata.put("businessTree_name", getBizTreeNmById(biz_id));
			return resultdata;
		} else {
			return FastJsonUtil.error("1", "biz_id:" + biz_id + "biz_id:" + biz_id + "业务关系树不存在");
		}
	}

	// ------------------------------------------------------------------------------------------------------------------
	
	public String convertApiField2DbField(String key){
		return api2db_nm.get(key);
	}
	
	public String convertDbField2ApiField(String key){
		return db2api_nm.get(key);
	}
	
	/**
	 * 缓存数据(key:类型id ,value:类型名称)
	 * 
	 * @return
	 */
	private Map<Integer, String> getPoiTagsLst() {
		if (!poiTagsLst.isEmpty()) {
			return poiTagsLst;
		}
		synchronized (PoiApiBLImpl.class) {
			if (poiTagsLst.isEmpty()) {
				List<MsTagFieldResult> msTagFieldResults = msPoiFieldDAO.selectPoiTags(new MsTagFieldParameter());
				for (MsTagFieldResult msTagFieldResult : msTagFieldResults) {
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
	private Map<Integer, String> getPoiTypeId2NmLst() {
		if (!poiTypeId2NmLst.isEmpty()) {
			return poiTypeId2NmLst;
		}
		synchronized (PoiApiBLImpl.class) {
			if (poiTypeId2NmLst.isEmpty()) {
				MsPoiFieldCriteria msPoiFieldCriteria = new MsPoiFieldCriteria();
				MsPoiFieldCriteria.Criteria criteria = msPoiFieldCriteria.createCriteria();
				criteria.andFieldNameEqualTo("type");
				List<MsPoiField> msPoiFields = msPoiFieldDAO.selectByCriteria(msPoiFieldCriteria);
				if (!msPoiFields.isEmpty()) {
					MsPoiField msPoiField = msPoiFields.get(0);
					String extensionAttrs = msPoiField.getExtensionAttrs();
					if (extensionAttrs == null) {
						return poiTypeId2NmLst;
					}
					JSONArray array = JSONArray.parseArray(extensionAttrs);
					for (int i = 0; i < array.size(); i++) {
						JSONObject type = array.getJSONObject(i);
						Set<Entry<String, Object>> entrySet = type.entrySet();
						for (Entry<String, Object> entry : entrySet) {
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
	private Map<Integer, String> getPoiSceneTypeId2NmLst() {
		if (!poiSceneTypeId2NmLst.isEmpty()) {
			return poiSceneTypeId2NmLst;
		}
		synchronized (PoiApiBLImpl.class) {
			if (poiSceneTypeId2NmLst.isEmpty()) {
				MsPoiFieldCriteria msPoiFieldCriteria = new MsPoiFieldCriteria();
				MsPoiFieldCriteria.Criteria criteria = msPoiFieldCriteria.createCriteria();
				criteria.andFieldNameEqualTo("scene_type");
				List<MsPoiField> msPoiFields = msPoiFieldDAO.selectByCriteria(msPoiFieldCriteria);
				if (!msPoiFields.isEmpty()) {
					MsPoiField msPoiField = msPoiFields.get(0);
					String extensionAttrs = msPoiField.getExtensionAttrs();
					if (extensionAttrs == null) {
						return poiSceneTypeId2NmLst;
					}
					JSONArray array = JSONArray.parseArray(extensionAttrs);
					for (int i = 0; i < array.size(); i++) {
						JSONObject type = array.getJSONObject(i);
						Set<Entry<String, Object>> entrySet = type.entrySet();
						for (Entry<String, Object> entry : entrySet) {
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
	private Map<String, Integer> getPoiTypeNm2IdLst() {
		if (!poiTypeNm2IdLst.isEmpty()) {
			return poiTypeNm2IdLst;
		}
		synchronized (PoiApiBLImpl.class) {
			if (poiTypeNm2IdLst.isEmpty()) {
				Map<Integer, String> typeId2Nms = getPoiTypeId2NmLst();
				Set<Entry<Integer, String>> entrys = typeId2Nms.entrySet();
				for (Entry<Integer, String> entry : entrys) {
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
	private Map<String, Integer> getPoiSceneTypeNm2IdLst() {
		if (!poiSceneTypeNm2IdLst.isEmpty()) {
			return poiSceneTypeNm2IdLst;
		}
		synchronized (PoiApiBLImpl.class) {
			if (poiSceneTypeNm2IdLst.isEmpty()) {
				Map<Integer, String> sceneTypeId2Nms = getPoiSceneTypeId2NmLst();
				Set<Entry<Integer, String>> entrys = sceneTypeId2Nms.entrySet();
				for (Entry<Integer, String> entry : entrys) {
					int id = entry.getKey();
					String nm = entry.getValue();
					poiSceneTypeNm2IdLst.put(nm, id);
				}
			}
		}
		return poiSceneTypeNm2IdLst;
	}

	private Map<Integer, String> getPoiPanoramaTypeId2NmLst() {
		if (!poiPanoramaTypeId2NmLst.isEmpty()) {
			return poiPanoramaTypeId2NmLst;
		}
		synchronized (PoiApiBLImpl.class) {
			if(poiPanoramaTypeId2NmLst.isEmpty()) {
				MsPoiFieldCriteria msPoiFieldCriteria = new MsPoiFieldCriteria();
				MsPoiFieldCriteria.Criteria criteria = msPoiFieldCriteria.createCriteria();
				criteria.andFieldNameEqualTo("panorama_type");
				List<MsPoiField> msPoiFields= msPoiFieldDAO.selectByCriteria(msPoiFieldCriteria);
				if (!msPoiFields.isEmpty()) {
					MsPoiField msPoiField = msPoiFields.get(0);
					String extensionAttrs = msPoiField.getExtensionAttrs();
					if (extensionAttrs == null) {
						return poiPanoramaTypeId2NmLst;
					}
					JSONArray array = JSONArray.parseArray(extensionAttrs);
					for (int i = 0; i < array.size(); i++) {
						JSONObject type = array.getJSONObject(i);
						Set<Entry<String, Object>> entrySet = type.entrySet();
						for (Entry<String, Object> entry : entrySet) {
							String key = entry.getKey();
							String value = (String) entry.getValue();
							poiPanoramaTypeId2NmLst.put(Integer.parseInt(key), value);
						}
					}
				}
			}
			
		}
		return poiPanoramaTypeId2NmLst;
	}

	/**
	 * 将输入字段名称转化为数据库内部名称
	 * 
	 * @param fields 输入字段集。如fields=poi_name,lnglat
	 * @param fieldLst
	 */
	private List<String> convertInputApiFields2DbFieldLst(String fields) {
		// 存在输入字段
		List<String> fieldLst = new ArrayList<>();
		if(!"".equals(fields)) {
			String[] fieldArr = fields.split(",");
			for(int i = 0; i < fieldArr.length; i++){
				String dbname = this.convertApiField2DbField(fieldArr[i]);
				if(dbname != null) {
					fieldLst.add(dbname);
				}
			}
			return fieldLst;
		// 不存在输入字段，则默认为所有字段
		} else {
			return null;
		}
	}
	
	/**
	 * 根据id获得业务关系树的名称
	 * 
	 * @param biz_id
	 * @return
	 */
	private String getBizTreeNmById(int biz_id) {
		MsBusiness msBusiness = msBusinessDAO.selectByPrimaryKey(biz_id);
		String biz_nm = null;
		if (msBusiness != null) {
			biz_nm = msBusiness.getBusinessName();
		}
		return biz_nm;
	}

	/**
	 * 根据poi 的id值获取poi信息
	 * 
	 * @param _id
	 * @return
	 */
	private Document getPoiById(String _id, String lang) {
		MongoCollection<Document> poi_collection = null;
		if (PoiCommon.POI.ZH.equals(lang)) {
			poi_collection = mongoConnector.getDBCollection(PoiCommon.MongoTable.TABLE_POI_ZH);
		} else if (PoiCommon.POI.EN.equals(lang)) {
			poi_collection = mongoConnector.getDBCollection(PoiCommon.MongoTable.TABLE_POI_EN);
		}
		if (poi_collection != null) {
			BasicDBObject keyId = new BasicDBObject();
			keyId.put("_id", new ObjectId(_id));
			return poi_collection.find(keyId).limit(1).first();
		} else {
			return null;
		}
	}

	/**
	 * 兼容不同的business_tree存储格式
	 */ 
	private void getPoisByParentId(List<String> pois, Document tree, String poi_id, int level, int order, boolean flag) {
		JSONObject aTree = JSONObject.parseObject(tree.toJson());
		Object oldClist = aTree.get("c_list");
		if(oldClist instanceof Map<?,?>) {
			getPoisByParentId(pois, (Document)tree.get("c_list"), poi_id, level, order);
		} else if(oldClist instanceof List<?>){
			getPoisByParentId(pois, aTree.getJSONArray("c_list"), poi_id, level, order);
		}
	}
	
	/**
	 * 获得某个poi的子poi结点
	 * 
	 * @param pois
	 *            子POI结点id列表
	 * @param jsoncList
	 *            业务树中poi结点下的c_list数据
	 * @param poi_id
	 *            poi id
	 * @param level
	 *            当前所在的层与目标POI（父POI）所在层的高度差。若level=0，表明当前层即为所要寻找的父poi结点所在的层。
	 * @param order
	 *            目标POI（父POI）所在层的顺序。目前暂不使用。
	 */
	private void getPoisByParentId(List<String> pois, Document jsoncList, String poi_id, int level, int order) {
		if (level < 0) {
			return;
		}
		Set<Entry<String, Object>> entrySet = jsoncList.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			String _id = entry.getKey();
			Document innerJsoncList = (Document) entry.getValue();
			Document subClist = (Document) innerJsoncList.get("c_list");// 下一层数据
			if (subClist.isEmpty()) {// 下一层无poi数据
				continue;
			}
			// 当前结点是查找结点
			if (_id.equals(poi_id) && level == 0) {
				pois.addAll(subClist.keySet());
				return;
			}
			// 递归向下，层级减1
			getPoisByParentId(pois, subClist, poi_id, level - 1, order);
		}
	}

	private void getPoisByParentId(List<String> pois, JSONArray jsoncList, String poi_id, int level, int order) {
		if (level < 0) {
			return;
		}
		for(int i = 0; i<jsoncList.size();i++){
			JSONObject sub = jsoncList.getJSONObject(i);
			String _id = sub.getString("_id");
			JSONArray subClist = sub.getJSONArray("c_list");
			if(subClist.size() == 0) {// 下一层无poi数据
				continue;
			}
			// 当前结点是查找结点, 拿下一层数据
			if (_id.equals(poi_id) && level == 0) {
				for(int j = 0; j < subClist.size(); j++){
					JSONObject poi = subClist.getJSONObject(j);
					String _sub_id = poi.getString("_id");
					pois.add(_sub_id);
				}
				return;
			}
			// 递归向下，层级减1
			getPoisByParentId(pois, subClist, poi_id, level - 1, order);
		}
	}
	
	/**
	 * mongodb数据库business_tree表，递归查找Poi集合
	 * 
	 * @param set
	 * @param jsoncList
	 */
	private void getPoisFromBizTree(Document docTree, Set<String> set ) {
		JSONObject tree = JSONObject.parseObject(docTree.toJson());
		Object oldClist = tree.get("c_list");
		if(oldClist instanceof Map<?,?>) {
			Document jsoncList = (Document)docTree.get("c_list");
			getPoisFromBizTree(set, jsoncList);
		} else {
			JSONArray jsoncList = tree.getJSONArray("c_list");
			getPoisFromBizTree(set, jsoncList);
		}
	}

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
	
	private void getPoisFromBizTree(Set<String> set, JSONArray jsoncList) {
		for(int i = 0; i < jsoncList.size(); i++) {
			JSONObject json = jsoncList.getJSONObject(i);
			String _id = json.getString("_id");
			set.add(_id);
			JSONArray subClist = json.getJSONArray("c_list");
			if (subClist.isEmpty()) {
				continue;
			}
			getPoisFromBizTree(set, subClist);
		}
	}
	
	/**
	 * 获取业务关系树
	 * 
	 * @param biz_id
	 *            业务树id
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
	 * 
	 * @param biz_id
	 *            业务树id
	 * @param level
	 *            树的层级
	 * @return
	 */
	List<String> getPoisWithLevelInBizTree(Document doctree, int level) {
		if (level != 1) { // 目前只获取第一层数据
			return null;
		}
		ArrayList<String> lst = null;
		JSONObject tree = JSONObject.parseObject(doctree.toJson());
		Object oldClist = tree.get("c_list");
		if(oldClist != null){
			if(oldClist instanceof Map<?,?>){
				JSONObject subTree = tree.getJSONObject("c_list");
				Set<String> keys = subTree.keySet();
				lst = new ArrayList<>(keys);
			} else {
				JSONArray subTree = tree.getJSONArray("c_list");
				lst = new ArrayList<>();
				for(int i = 0; i < subTree.size(); i++){
					lst.add(subTree.getJSONObject(i).getString("_id"));
				}
			}
		}
		if(lst == null || lst.isEmpty()){
			return null;
		}
		return lst;
	}

	/**
	 * 通过标签返回指定字段的poi列表
	 * 
	 * @param poiIdLst
	 *            poi id集
	 * @param type
	 *            标签类型
	 * @param tags
	 *            标签id集
	 * @param fields
	 *            字段集
	 * @param lang
	 *            语言
	 * @return
	 */
	private JSONArray getPoiInfoByTagsWithFields(Set<String> poiIdLst, String type, List<String> tags, List<String> fields,
			String lang) {
		MongoCollection<Document> poi_collection = null;
		if (PoiCommon.POI.ZH.equals(lang)) {
			poi_collection = mongoConnector.getDBCollection(PoiCommon.MongoTable.TABLE_POI_ZH);
		} else if (PoiCommon.POI.EN.equals(lang)) {
			poi_collection = mongoConnector.getDBCollection(PoiCommon.MongoTable.TABLE_POI_EN);
		}
		JSONArray array = new JSONArray();
		if (poi_collection != null) {
			BasicDBList valueList = new BasicDBList();
			for (String poi_id : poiIdLst) {
				BasicDBObject condition = new BasicDBObject();
				BasicDBList value = new BasicDBList();
				for (String tag : tags) {
					value.add(Integer.parseInt(tag));
				}
				BasicDBObject all = new BasicDBObject("$in", value);
				condition.append("_id", new ObjectId(poi_id)).append(type, all);
				valueList.add(condition);
			}
			BasicDBObject or = new BasicDBObject("$or", valueList);
			MongoCursor<Document> mongoCursor = poi_collection.find(or).iterator();
			while (mongoCursor.hasNext()) {
				Document docPoi = mongoCursor.next();
				JSONObject poi = getPoiInfoWithFields(docPoi, fields, lang);
				if (!poi.isEmpty()) {
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
	 * @param poiIdLst
	 *            poi id集
	 * @param typesAndIds
	 *            标签的属性与id集合
	 * @param fields
	 *            字段集
	 * @param lang
	 *            语言
	 * @return
	 */
	private JSONArray getPoiInfoByTagsWithFields(Set<String> poiIdLst, Map<String, List<Integer>> typesAndIds,
			List<String> fields, String lang) {

		MongoCollection<Document> poi_collection = null;
		if (PoiCommon.POI.ZH.equals(lang)) {
			poi_collection = mongoConnector.getDBCollection(PoiCommon.MongoTable.TABLE_POI_ZH);
		} else if (PoiCommon.POI.EN.equals(lang)) {
			poi_collection = mongoConnector.getDBCollection(PoiCommon.MongoTable.TABLE_POI_EN);
		}

		JSONArray array = new JSONArray();
		if (poi_collection != null) {
			BasicDBList idConditonList = new BasicDBList();
			for (String poi_id : poiIdLst) {
				Set<Entry<String, List<Integer>>> entrys = typesAndIds.entrySet();
				for (Entry<String, List<Integer>> entry : entrys) {
					String type = entry.getKey();
					List<Integer> tags = entry.getValue();// 标签
					BasicDBObject idCondition = new BasicDBObject();
					BasicDBList value = new BasicDBList();
					for (Integer tag : tags) {
						value.add(tag);
					}
					BasicDBObject in = new BasicDBObject("$in", value);
					idCondition.append("_id", new ObjectId(poi_id)).append(type, in);
					idConditonList.add(idCondition);
				}
			}
			BasicDBObject or = new BasicDBObject("$or", idConditonList);
			MongoCursor<Document> mongoCursor = poi_collection.find(or).iterator();
			while (mongoCursor.hasNext()) {
				Document docPoi = mongoCursor.next();
				JSONObject poi = getPoiInfoWithFields(docPoi, fields, lang);
				if (!poi.isEmpty()) {
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
	 * @param types
	 *            标签名称
	 * @return
	 */
	private Map<String, List<Integer>> getTypesAndIdsByTypeNms(String types) {
		Map<String, List<Integer>> list = new HashMap<>();
		list.put("type", new LinkedList<Integer>());
		list.put("scene_type", new LinkedList<Integer>());
		Map<String, Integer> poiTypeNm2Ids = getPoiTypeNm2IdLst();
		Map<String, Integer> poiSceneTypeNm2Ids = getPoiSceneTypeNm2IdLst();
		String[] tagArr = types.split(",");
		for (String tag : tagArr) {
			if (poiTypeNm2Ids.containsKey(tag)) {
				list.get("type").add(poiTypeNm2Ids.get(tag));
			}
			if (poiSceneTypeNm2Ids.containsKey(tag)) {
				list.get("scene_type").add(poiSceneTypeNm2Ids.get(tag));
			}
		}
		return list;
	}

	/**
	 * 获取给定字段和id的poi
	 * 
	 * @param poi_id
	 *            poi 的id
	 * @param fields
	 *            需要获取的字段
	 * @return
	 */
	private JSONArray getPoiInfoWtihFields(List<String> poiIdLst, List<String> fields, String lang) {
		MongoCollection<Document> poi_collection = null;
		if (PoiCommon.POI.ZH.equals(lang)) {
			poi_collection = mongoConnector.getDBCollection(PoiCommon.MongoTable.TABLE_POI_ZH);
		} else if (PoiCommon.POI.EN.equals(lang)) {
			poi_collection = mongoConnector.getDBCollection(PoiCommon.MongoTable.TABLE_POI_EN);
		}
		JSONArray array = new JSONArray();
		if (poi_collection != null) {
			BasicDBList value = new BasicDBList();
			for (String poiId : poiIdLst) {
				BasicDBObject condition = new BasicDBObject();
				condition.put("_id", new ObjectId(poiId));
				value.add(condition);
			}
			BasicDBObject or = new BasicDBObject("$or", value);
			MongoCursor<Document> mongoCursor = poi_collection.find(or).iterator();
			Map<String, JSONObject> pois = new LinkedHashMap<>();
			// 将搜索结果放入集合
			while (mongoCursor.hasNext()) {
				Document docPoi = mongoCursor.next();
				JSONObject poi = getPoiInfoWithFields(docPoi, fields, lang);
				if (!poi.isEmpty()) {
					poi.put("poi_id", docPoi.get("_id").toString());
					pois.put(docPoi.get("_id").toString(), poi);
				}
			}
			array = getPoiInOrder(poiIdLst, pois);
		}
		return array;
	}

	/**
	 * 获取父poi下满足一级分类id的poi子结点（poi仅包含给定字段数据）
	 * 
	 * @param poi_id
	 *            poi ID号
	 * @param ctgr_id
	 *            一级分类
	 * @param fields
	 *            需要获取的字段
	 * @return
	 */
	private JSONArray getPoiInfoByCtgrIdWithFields(List<String> poiIdLst, int ctgr_id, List<String> fields, String lang) {
		int[] ctgrArr = new int[1];
		ctgrArr[0] = ctgr_id;
		MongoCollection<Document> poi_collection = null;
		if (PoiCommon.POI.ZH.equals(lang)) {
			poi_collection = mongoConnector.getDBCollection(PoiCommon.MongoTable.TABLE_POI_ZH);
		} else if (PoiCommon.POI.EN.equals(lang)) {
			poi_collection = mongoConnector.getDBCollection(PoiCommon.MongoTable.TABLE_POI_EN);
		}
		JSONArray array = new JSONArray();
		if (poi_collection != null) {
			BasicDBList value = new BasicDBList();
			for (String poi_id : poiIdLst) {
				BasicDBObject condition = new BasicDBObject();
				condition.append("_id", new ObjectId(poi_id)).append("tags", ctgrArr);
				value.add(condition);
			}
			BasicDBObject or = new BasicDBObject("$or", value);
			MongoCursor<Document> mongoCursor = poi_collection.find(or).iterator();
			Map<String, JSONObject> pois = new LinkedHashMap<>();
			while (mongoCursor.hasNext()) {
				Document docPoi = mongoCursor.next();
				JSONObject poi = getPoiInfoWithFields(docPoi, fields, lang);
				if (!poi.isEmpty()) {
					poi.put("poi_id", docPoi.get("_id").toString());
					pois.put(docPoi.get("_id").toString(), poi);
				}
			}
			array = getPoiInOrder(poiIdLst, pois);
		}
		return array;
	}

	/**
	 * 获取父poi下满足二级分类id的poi子结点（poi仅包含给定字段数据）
	 * 
	 * @param poi_id
	 *            poi ID号
	 * @param sub_ctgr_id
	 *            二级分类
	 * @param fields
	 *            需要获取的字段
	 * @return
	 */
	private JSONArray getPoiInfoBySubCtgrIdWithFields(List<String> poiIdLst, int sub_ctgr_id, List<String> fields,
			String lang) {
		MongoCollection<Document> poi_collection = null;
		if (PoiCommon.POI.ZH.equals(lang)) {
			poi_collection = mongoConnector.getDBCollection(PoiCommon.MongoTable.TABLE_POI_ZH);
		} else if (PoiCommon.POI.EN.equals(lang)) {
			poi_collection = mongoConnector.getDBCollection(PoiCommon.MongoTable.TABLE_POI_EN);
		}
		JSONArray array = new JSONArray();
		if (poi_collection != null) {
			BasicDBList value = new BasicDBList();
			for (String poi_id : poiIdLst) {
				BasicDBObject condition = new BasicDBObject();
				condition.append("_id", new ObjectId(poi_id)).append("sub_tag", sub_ctgr_id);
				value.add(condition);
			}
			BasicDBObject or = new BasicDBObject("$or", value);
			MongoCursor<Document> mongoCursor = poi_collection.find(or).iterator();
			Map<String, JSONObject> pois = new LinkedHashMap<>();
			while (mongoCursor.hasNext()) {
				Document docPoi = mongoCursor.next();
				JSONObject poi = getPoiInfoWithFields(docPoi, fields, lang);
				if (!poi.isEmpty()) {
					poi.put("poi_id", docPoi.get("_id").toString());
					pois.put(docPoi.get("_id").toString(), poi);
				}
			}
			array = getPoiInOrder(poiIdLst, pois);
		}
		return array;
	}

	/**
	 * @param poiIdLst poi点顺序集合
	 * @param pois 通过mongo搜索出的满足条件的POI
	 * @return
	 */
	private JSONArray getPoiInOrder(List<String> poiIdLst, Map<String, JSONObject> pois){
		JSONArray array = new JSONArray();
		// 按照顺序放入数据
		if(pois.size() != 0) {
			for(int i = 0; i < poiIdLst.size(); i++){
				String id = poiIdLst.get(i);
				if(pois.containsKey(id)) {
					array.add(pois.get(id));
				}
			}
		}
		return array;
	}
	
	/**
	 * 获取POI的指定字段信息
	 * 
	 * @param poi 
	 * @param fieldLst 字段集
	 * @param lang
	 * @return
	 */
	private JSONObject getPoiInfoWithFields(Document poi, List<String> fieldLst, String lang) {
		JSONObject result = new JSONObject();
		// 前端输入字段全部错误
		if(fieldLst != null && fieldLst.isEmpty()){
			return result;
		}
		
		// 前端未输入字段参数
		if(fieldLst == null) {
			if (poi != null) {
				int tag_id = FastJsonUtil.parse2Array(poi.get("tags")).getIntValue(0);
				List<String> fields = getFieldsByDefault(tag_id);// 获得默认的返回字段
				logger.info("request fields: " + fields);
				for (String field : fields) {
					// 数据库存在字段信息
					Map<Integer, String> poiTags = getPoiTagsLst();
					Map<Integer, String> poiTypes = getPoiTypeId2NmLst();
					Map<Integer, String> poiSceneTypes = getPoiSceneTypeId2NmLst();
					Map<Integer, String> poiPanoTypes = getPoiPanoramaTypeId2NmLst();
					if(poi.containsKey(field)) {
						// ----------拆分字段----------
						// 一级类别
						if ("tags".equals(field)) {
							continue;
						}
						// 二级分类
						if ("sub_tag".equals(field)) {
							JSONArray array = FastJsonUtil.parse2Array(poi.get("tags"));
							JSONObject data = new JSONObject();
							int tag = array.getIntValue(0);
							if(tag == 0) {//未选择，默认为其他（兼容旧数据）
								tag = 8;//TODO
							}
							if (poiTags.containsKey(tag)) {
								data.put("category_name", poiTags.get(tag));
								data.put("category_id", tag);
							}
							result.put("category", data);
							
							int sub_tag = poi.getInteger("sub_tag");
							if(sub_tag == 0){
								sub_tag = getTopTag2SubTagOthersCache().get(tag);
							}
							JSONObject data2 = new JSONObject();
							if (poiTags.containsKey(sub_tag)) {
								data2.put("sub_category_name", poiTags.get(sub_tag));
								data2.put("sub_category_id", sub_tag);
							}
							result.put("sub_category", data2);
							continue;
						}
						// 经纬度
						if ("lnglat".equals(field)) {
							Document lnglat = (Document) poi.get("lnglat");
							JSONArray coordinates = FastJsonUtil.parse2Array(lnglat.get("coordinates"));
							JSONObject data = new JSONObject();
							data.put("lng", coordinates.getDoubleValue(0));
							data.put("lat", coordinates.getDoubleValue(1));
							result.put("lnglat", data);
							continue;
						}
						// 标签--type
						if ("type".equals(field)) {
							JSONArray types = FastJsonUtil.parse2Array(poi.get("type"));
							JSONArray array = new JSONArray();
							for (int i = 0; i < types.size(); i++) {
								if (poiTypes.containsKey(types.getIntValue(i))) {
									JSONObject data = new JSONObject();
									int id = types.getIntValue(i);
									data.put("hotel_type_id", id);
									data.put("hotel_type_name", poiTypes.get(id));
									array.add(data);
								}
							}
							result.put("hotel_types", array);
							continue;
						}
						// 标签--scene_type
						if ("scene_type".equals(field)) {
							JSONArray types = FastJsonUtil.parse2Array(poi.get("scene_type"));
							JSONArray array = new JSONArray();
							for (int i = 0; i < types.size(); i++) {
								if (poiSceneTypes.containsKey(types.getIntValue(i))) {
									JSONObject data = new JSONObject();
									int id = types.getIntValue(i);
									data.put("scene_type_id", id);
									data.put("scene_type_name", poiSceneTypes.get(id));
									array.add(data);
								}
							}
							result.put("scene_types", array);
							continue;
						}
						
						// ----------聚合字段----------
						// 区域id
						if ("zone_id".equals(field)) {
							String zone_id = poi.getString("zone_id");
							JSONObject data = new JSONObject();
							
							String country_id = "100000"; //默认为中国
							String province_id = poi.getString("province_id");
							String city_id = poi.getString("city_id");
							String county_id = poi.getString("county_id");
							String country = msZoneCacheBL.getZoneName(country_id, lang);
							String province = msZoneCacheBL.getZoneName(province_id, lang);
							String city = msZoneCacheBL.getZoneName(city_id, lang);
							String county = msZoneCacheBL.getZoneName(county_id, lang);
							String detail_address = poi.getString("detail_address");
							data.put("country", country);
							data.put("province", province);
							data.put("city", city);
							data.put("county", county);
							data.put(convertDbField2ApiField("zone_id"), zone_id);
							data.put(convertDbField2ApiField("detail_address"), detail_address);
							result.put("address", data);
							continue;
						}
						// 详细地址和聚合地址
						if ("detail_address".equals(field) || "merger_name".equals(field)){
							continue;
						}
						// 全景标识
						if("panorama".equals(field)) {
							String panorama_id = poi.getString("panorama");
							Integer panorama_type_id = poi.getInteger("panorama_type");
							if( panorama_type_id == null) { // 兼容之前没有全景类别的数据
								panorama_type_id = 2;// 默认为2
							}
							String panorama_type_name = null;
							if(poiPanoTypes.containsKey(panorama_type_id)){
								panorama_type_name = poiPanoTypes.get(panorama_type_id);
							}
							
							JSONObject data = new JSONObject();
							data.put("panorama_id", panorama_id);
							data.put("panorama_type_id", panorama_type_id);
							data.put("panorama_type_name", panorama_type_name);
							result.put("panorama", data);
							continue;
						}
						// 全景类别
						if("panorama_type".equals(field)) {
							continue;
						}
						result.put(convertDbField2ApiField(field), poi.get(field));
					// POI数据不存在字段信息
					} else {
						// 全景类别
						if("panorama_type".equals(field)) {
							continue;
						}
						result.put(convertDbField2ApiField(field), "");
					}
				}
			}
		}
		return result;
	}

	/**
	 * 获得默认输出字段 公有字段+类别对应的所有私有字段
	 * 
	 * @param tag_id 类别id
	 * @param fields 
	 */
	private List<String> getFieldsByDefault(int tag_id) {
		List<String> fields = new ArrayList<>();
		// 加入公共字段
		fields.addAll(commonFields);
		List<String> privateFields = poiTag2PrivateField.get(tag_id);
		if(privateFields != null) {
			fields.addAll(privateFields);
		}
		return fields;
	}

	private JSONObject returnSuccessData(String msg, String lang, JSONObject data) {
		JSONObject json = new JSONObject();
		json.put(lang, data);
		return FastJsonUtil.sucess(msg, json);
	}
	
	/**
	 * 获取二级分类为“其他”时 topTag与subTag的映射关系
	 * @return
	 */
	private Map<Integer, Integer> getTopTag2SubTagOthersCache() {
		if(!topTag2SubTagOthersCache.isEmpty()){
			return topTag2SubTagOthersCache;
		}
		synchronized (ManagePoiBLImpl.class) {
			if(topTag2SubTagOthersCache.isEmpty()){
				MsPoiTagCriteria msPoiTagCriteria = new MsPoiTagCriteria();
				MsPoiTagCriteria.Criteria criteria = msPoiTagCriteria.createCriteria();
				criteria.andTagNameEqualTo("其他").andParentTagIdNotEqualTo(0);
				List<MsPoiTag> msPoiTaglst = msPoiTagDAO.selectByCriteria(msPoiTagCriteria);
				if(!msPoiTaglst.isEmpty()) {
					for(int i = 0;i < msPoiTaglst.size(); i++){
						Integer tag_id = msPoiTaglst.get(i).getTagId();
						Integer parent_tag_id = msPoiTaglst.get(i).getParentTagId();
						topTag2SubTagOthersCache.put(parent_tag_id, tag_id);
					}
				}
				
			}
		}
		return topTag2SubTagOthersCache;
	}
}
