package ms.luna.biz.bl.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
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
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import ms.biz.common.AuthenticatedUserHolder;
import ms.biz.common.MongoConnector;
import ms.luna.biz.bl.ManageBusinessTreeBL;
import ms.luna.biz.cons.VbConstant;
import ms.luna.biz.dao.custom.MsBusinessDAO;
import ms.luna.biz.dao.custom.MsMerchantManageDAO;
import ms.luna.biz.dao.custom.MsPoiTagDAO;
import ms.luna.biz.dao.custom.model.MsBusinessParameter;
import ms.luna.biz.dao.custom.model.MsBusinessResult;
import ms.luna.biz.dao.model.MsBusiness;
import ms.luna.biz.dao.model.MsMerchantManage;
import ms.luna.biz.dao.model.MsPoiTag;
import ms.luna.biz.dao.model.MsPoiTagCriteria;
import ms.luna.biz.model.MsUser;
import ms.luna.biz.util.CharactorUtil;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;

/**
 * @author Mark
 *
 */
@Transactional(rollbackFor = Exception.class)
@Service("manageBusinessTreeBL")
public class ManageBusinessTreeBLImpl implements ManageBusinessTreeBL {

	@Autowired
	private MsPoiTagDAO msPoiTagDAO;

	@Autowired
	private MsMerchantManageDAO msMerchantManageDAO;
	
	@Autowired
	private MsBusinessDAO msBusinessDAO;

	@Autowired
	private MongoConnector mongoConnector;

	@Override
	public JSONObject saveBusinessTree(String json) {
		JSONObject param = JSON.parseObject(json);
		// 检查并获取业务名称
		Integer businessId = param.getInteger("business_id");
		String businessName = this.getBusinessNameExist(businessId);
		if (businessName == null) {
			return FastJsonUtil.error("-199", "business is not exist");
		}

		// 在mongoDB中查找业务树的数据
		MongoCollection<Document> biz_tree = mongoConnector.getDBCollection("business_tree");

		BasicDBObject condition = new BasicDBObject();
		condition.put("business_id", businessId);

		Document document = new Document();
		Date date = new Date();
		document.put("c_list", param.get("c_list"));
		document.put("update_hhmmss", date);
		MsUser msUser = (MsUser)AuthenticatedUserHolder.get();
		document.put("updated_by_unique_id", msUser.getUniqueId());

		BasicDBObject updateDocument = new BasicDBObject();
		updateDocument.append("$set", document);

		this.updatePoiBusinessTreeIndex(param.getJSONObject("c_list"), biz_tree, businessId);

		// 更新保存业务树
		UpdateResult updateResult = biz_tree.updateOne(condition, updateDocument);
		if (updateResult.getModifiedCount() == 0) {
			return FastJsonUtil.error("-1", "保存失败!");
		}

		return FastJsonUtil.sucess("success: save bussiness tree");
	}

	@Override
	public JSONObject viewBusinessTree(String json) {
		JSONObject param = JSON.parseObject(json);

		// 检查并获取业务名称
		Integer businessId = param.getInteger("businessId");
		String businessName = this.getBusinessNameExist(businessId);
		if (businessName == null) {
			return FastJsonUtil.error("-199", "business is not exist");
		}

		// 在mongoDB中查找业务树的数据
		MongoCollection<Document> biz_tree = mongoConnector.getDBCollection("business_tree");
		Document document = this.findWithBusinessIdInBussinessPoiTree(biz_tree, businessId);
		if (document == null) {
			return FastJsonUtil.error("-2", "bussiness_id creating failed");
		}

		// 1.获取列表
		JSONObject treeDate = new JSONObject();
//		treeDate.put("_id", document.getObjectId("_id").toString());
		treeDate.put("business_id", document.getInteger("business_id"));
		treeDate.put("business_name", businessName);
		treeDate.put("c_list", document.get("c_list"));

		// 2.获取POI定义列表
		JSONObject jsoncList = treeDate.getJSONObject("c_list");
		MsLogger.info(jsoncList.toJSONString());
		Set<String> set = new LinkedHashSet<String>();
		readPoiId2Set(set, jsoncList);
		JSONObject poiDef = this.getPoisForBusinessTree(set);

		// 3.获取tag定义列表
		JSONObject tags = this.getJsonTags();

		JSONObject data = new JSONObject();
		data.put("treeDate", treeDate);
		data.put("poiDef", poiDef);
		data.put("tags", tags);

		return FastJsonUtil.sucess("success: load bussiness tree data", data);
	}

	/**
	 * 通过businessId创建业务树
	 */
	@Override
	public JSONObject createBusinessTree(String json) {
		JSONObject param = JSON.parseObject(json);

		Integer businessId = param.getInteger("businessId");
		if (!this.isBusinessIdExist(businessId)) {
			return FastJsonUtil.error("-199", "该业务不存在，不能创建POI数据关系配置！");
		}

		MongoCollection<Document> biz_tree = mongoConnector.getDBCollection("business_tree");

		Document document = new Document();
		Date date = new Date();
		document.put("business_id", businessId);
		document.put("c_list", JSONObject.parse("{}"));
		document.put("regist_hhmmss", date);
		document.put("update_hhmmss", date);
		MsUser msUser = (MsUser)AuthenticatedUserHolder.get();
		document.put("updated_by_unique_id", msUser.getUniqueId());

		// mongo中有唯一性约束，所以直接插入即可
		try {
			biz_tree.insertOne(document);
		} catch (Exception e) {
			MsLogger.error("bussiness is already existing or bussiness_id creating failed!", e);
			return FastJsonUtil.error("-2", "该业务的POI数据关系配置已经存在，不能重复创建！");
		}
		document = this.findWithBusinessIdInBussinessPoiTree(biz_tree, businessId);
		if (document == null) {
			return FastJsonUtil.error("-2", "创建POI数据关系配置失败！");
		}
//		JSONObject data = new JSONObject();
//		data.put("_id", document.getObjectId("_id").toString());
//		data.put("business_id", document.getInteger("business_id"));
//		document.put("c_list", document.get("c_list"));
//		data.put("regist_hhmmss", document.getDate("regist_hhmmss"));
//		data.put("update_hhmmss", document.getDate("update_hhmmss"));
//		data.put("updated_by_unique_id", document.getString("updated_by_unique_id"));
		return FastJsonUtil.sucess("success: 成功创建POI数据关系配置！");
	}

	@Override
	public JSONObject deleteBusinessTree(String json) {
		JSONObject param = JSON.parseObject(json);
		Integer businessId = param.getInteger("businessId");
		MongoCollection<Document> business_tree = mongoConnector.getDBCollection("business_tree");
		BasicDBObject condition = new BasicDBObject();
		condition.put("business_id", businessId);

		// 维护POI到业务的索引树
		this.deletePoiBusinessTreeIndex(business_tree, businessId);

		DeleteResult deleteResult = business_tree.deleteOne(condition);
		if (deleteResult.getDeletedCount() > 0) {
			return FastJsonUtil.sucess("成功删除POI数据关系配置!");
		}
		return FastJsonUtil.error("-1", "删除POI数据关系配置失败!");
	}

	@Override
	public JSONObject searchPoisForBizTree(String json) {
		JSONObject param = JSONObject.parseObject(json);
		Integer offset = param.getInteger("offset");
		Integer limit = param.getInteger("limit");

		String provinceId = FastJsonUtil.getString(param, "provinceId");
		String cityId = FastJsonUtil.getString(param, "cityId");
		String countyId = FastJsonUtil.getString(param, "countyId");
		provinceId = "ALL".equals(provinceId) ? "110000" : provinceId;
		cityId = "ALL".equals(cityId) ? null : cityId;
		countyId = "ALL".equals(countyId) ? null : countyId;

		Integer tagId = param.getInteger("tagId");

		String keyWord = null;

		MongoCollection<Document> poi_collection = mongoConnector.getDBCollection("poi_collection");
		Long total = 0L;
		if (offset == null) {
			offset = 0;
		}
		if (limit == null) {
			limit = VbConstant.DEFAULT_MAX_LIMT;
		}
		BasicDBObject condition = new BasicDBObject();

		// POI名称(过滤)
		if (param.containsKey("keyWord")) {
			keyWord = param.getString("keyWord");
		}
		if (keyWord != null && !keyWord.isEmpty()) {
			Pattern pattern = Pattern.compile("^.*" + keyWord+ ".*$", Pattern.CASE_INSENSITIVE);
			condition.append("long_title", pattern);
		}

		// zone地域(过滤)
		if (countyId != null) {
			condition.append("county_id", countyId);
		} else if (cityId != null) {
			condition.append("city_id", cityId);
		} else {
			condition.append("province_id", provinceId);
		}

		// 标签(过滤)
		if (tagId != null) {
			condition.append("tags", tagId);
		}

		total = poi_collection.count(condition);

		BasicDBObject sort = new BasicDBObject();
		sort.put("regist_hhmmss", -1);

		MongoCursor<Document> mongoCursor = poi_collection.
				find(condition)
				.sort(sort)
				.skip(offset).
				limit(limit)
				.iterator();

		JSONArray pois = new JSONArray();
		while (mongoCursor.hasNext()) {
			Document docPoi= mongoCursor.next();
			JSONObject poi = new JSONObject();
			// 名称
			poi.put("name", docPoi.get("long_title"));

			// 标签(类别)
			poi.put("tags", docPoi.get("tags"));

			// Poi ID
			poi.put("_id", docPoi.getObjectId("_id").toString());
			pois.add(poi);
		}
		JSONObject data = new JSONObject();
		data.put("row", pois);
		data.put("total", total);
		return FastJsonUtil.sucess("success", data);
	}

	/**
	 * 
	 * @param businessId
	 * @return
	 */
	private Boolean isBusinessIdExist(Integer businessId) {
		MsBusiness msBusiness = msBusinessDAO.selectByPrimaryKey(businessId);
		if (msBusiness == null) {
			return Boolean.FALSE;
		}
		String merchantId = msBusiness.getMerchantId();
		if (CharactorUtil.isEmpyty(merchantId)) {
			return Boolean.FALSE;
		}
		MsMerchantManage msMerchantManage = msMerchantManageDAO.selectByPrimaryKey(merchantId);
		// 暂定商户被逻辑删除后业务将不应该继续维护
		if (msMerchantManage == null
				|| VbConstant.DEL_FLG.删除.equals(msMerchantManage.getDelFlg())
			) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	/**
	 * 
	 * @param businessId
	 * @return
	 */
	private String getBusinessNameExist(Integer businessId) {
		MsBusiness msBusiness = msBusinessDAO.selectByPrimaryKey(businessId);
		if (msBusiness == null) {
			return null;
		}
		String merchantId = msBusiness.getMerchantId();
		if (CharactorUtil.isEmpyty(merchantId)) {
			return null;
		}
		MsMerchantManage msMerchantManage = msMerchantManageDAO.selectByPrimaryKey(merchantId);
		// 暂定商户被逻辑删除后业务将不应该继续维护
		if (msMerchantManage == null
				|| VbConstant.DEL_FLG.删除.equals(msMerchantManage.getDelFlg())
			) {
			return null;
		}
		return msBusiness.getBusinessName();
	}

	/**
	 * 
	 * @param business_tree
	 * @param businessId
	 * @return
	 */
	 private Document findWithBusinessIdInBussinessPoiTree(MongoCollection<Document> business_tree, Integer businessId) {

		BasicDBObject condition = new BasicDBObject();
		condition.put("business_id", businessId);

		MongoCursor<Document> mongoCursor = business_tree.find().
				filter(condition).
				limit(1).iterator();
		if (mongoCursor.hasNext()) {
			return mongoCursor.next();
		}
		return null;
	}

	@Override
	public JSONObject loadBusinessTrees(String json) {
		JSONObject param = JSONObject.parseObject(json);
		Integer offset = FastJsonUtil.getInteger(param, "offset");
		Integer limit = FastJsonUtil.getInteger(param, "limit");

		MongoCollection<Document> poi_collection = mongoConnector.getDBCollection("business_tree");
		Long total = 0L;
		if (offset == null) {
			offset = 0;
		}
		if (limit == null) {
			limit = VbConstant.DEFAULT_MAX_LIMT;
		}
		JSONArray bizTrees = new JSONArray();

		List<MsBusinessResult> lstMsBusinessResult = this.findBusiness(param);
		BasicDBObject condition = new BasicDBObject();
		if (lstMsBusinessResult == null || lstMsBusinessResult.isEmpty()) {
			JSONObject data = JSONObject.parseObject("{}");
			data.put("bizTrees", bizTrees);
			data.put("total", total);
			return FastJsonUtil.sucess("success", data);
		}

		BasicDBList filterBizIds = new BasicDBList();
		Map<Integer, MsBusinessResult> mapMsBusinessResult = new HashMap<Integer, MsBusinessResult>();

		for (MsBusinessResult msBusinessResult : lstMsBusinessResult) {
			filterBizIds.add(new BasicDBObject("business_id", msBusinessResult.getBusinessId()));
			mapMsBusinessResult.put(msBusinessResult.getBusinessId(), msBusinessResult);
		}
		condition.put("$or", filterBizIds);

		total = poi_collection.count(condition);

		BasicDBObject sort = new BasicDBObject();
		sort.put("regist_hhmmss", -1);

		MongoCursor<Document> mongoCursor = poi_collection.
				find(condition)
				.sort(sort)
				.skip(offset).
				limit(limit)
				.iterator();

		while (mongoCursor.hasNext()) {
			Document docBizTree = mongoCursor.next();
			JSONObject bizTree = new JSONObject();

			// bizTree的ID
			String _id = docBizTree.getObjectId("_id").toString();
			bizTree.put("_id", _id);

			Integer businessId = docBizTree.getInteger("business_id");
			bizTree.put("business_id", businessId);
			MsBusinessResult msBusinessResult = mapMsBusinessResult.get(businessId);
			bizTree.put("business_name", msBusinessResult.getBusinessName());
			bizTree.put("province_id", CharactorUtil.nullToBlank(msBusinessResult.getProvinceId()));
			bizTree.put("city_id", CharactorUtil.nullToBlank(msBusinessResult.getCityId()));
			bizTree.put("county_id", CharactorUtil.nullToBlank(msBusinessResult.getCountyId()));

			bizTrees.add(bizTree);
		}
		JSONObject data = JSONObject.parseObject("{}");
		data.put("rows", bizTrees);
		data.put("total", total);
		return FastJsonUtil.sucess("success", data);
	}

	/**
	 * 
	 * 找到区域以及业务名称过滤的业务列表（必须为业务表和商户表中存在的有效业务）。
	 * @param param
	 * @return
	 */
	private List<MsBusinessResult> findBusiness(JSONObject param) {
		MsBusinessParameter msBusinessParameter = new MsBusinessParameter();
		String keyword = FastJsonUtil.getString(param, "keyWord");
		String provinceId = FastJsonUtil.getString(param, "provinceId");
		String cityId = FastJsonUtil.getString(param, "cityId");
		String countyId = FastJsonUtil.getString(param, "countyId");
		if (CharactorUtil.isEmpyty(provinceId) || "ALL".equals(provinceId)) {
			throw new IllegalArgumentException("provinceId can not be empty or ALL [" + provinceId + "]");
		}

		cityId = "ALL".equals(cityId) ? null: cityId;
		countyId = "ALL".equals(countyId) ? null: countyId;
		msBusinessParameter.setProvinceId(provinceId);
		msBusinessParameter.setCityId(cityId);
		msBusinessParameter.setCountyId(countyId);

		if (!CharactorUtil.isEmpyty(keyword)) {
			msBusinessParameter.setKeyword("%"+keyword+"%");
		}

		return msBusinessDAO.selectBusinessWithFilter(msBusinessParameter);
	}

	@Override
	public JSONObject searchBusinessList(String json) {
		JSONObject param = JSONObject.parseObject(json);
		List<MsBusinessResult> lstMsBusinessResult = this.findBusiness(param);
		JSONArray array = new JSONArray();
		if (lstMsBusinessResult != null) {
			for (MsBusinessResult msBusinessResult : lstMsBusinessResult) {
				JSONObject biz = new JSONObject();
				Integer bizId = msBusinessResult.getBusinessId();
				String bizName = msBusinessResult.getBusinessName();
				biz.put("business_id", bizId);
				biz.put("business_name", bizName);
				array.add(biz);
			}
		}
		JSONObject data = new JSONObject();
		data.put("bizList", array);
		return FastJsonUtil.sucess("OK", data);
	}

	/**
	 * 获取单棵业务树Poi定义列表
	 * @param _ids
	 * @return
	 */
	private JSONObject getPoisForBusinessTree(Set<String> _ids) {
		/**
		 * poi列表
		 */
		JSONObject pois = new JSONObject();
		if (_ids.isEmpty()) {
			return pois;
		}
		MongoCollection<Document> poi_collection = mongoConnector.getDBCollection("poi_collection");

		BasicDBObject condition = new BasicDBObject();
		BasicDBList _poiIds = new BasicDBList();
		for (String _id : _ids) {
			_poiIds.add(new BasicDBObject("_id", new ObjectId(_id)));
		}
		condition.put("$or", _poiIds);

		BasicDBObject sort = new BasicDBObject();
		sort.put("regist_hhmmss", -1);

		MongoCursor<Document> mongoCursor = poi_collection.
				find(condition)
				.sort(sort)
				.iterator();

		while (mongoCursor.hasNext()) {
			Document docPoi= mongoCursor.next();
//			 "01":{
//		        "_id":"01",
//		        "name":"濯水古镇1",
//		        "tags":"1,2,3"
//		    },
			// poi的ID
			String _id = docPoi.getObjectId("_id").toString();
			String name = docPoi.getString("long_title");
			JSONObject data = new JSONObject();
			data.put("_id", _id);
			data.put("name", name);
			data.put("tags", docPoi.get("tags"));

			pois.put(_id, data);
		}
		
		return pois;
	}

	/**
	 * 递归查找Poi集合
	 * @param set
	 * @param jsoncList
	 */
	private void readPoiId2Set(Set<String> set, JSONObject jsoncList) {
		Set<Entry<String, Object>> entrySet = jsoncList.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			// _id
			String _id = entry.getKey();
			MsLogger.debug(entry.getValue().toString());

			// _id对应的c_list
			JSONObject innerJsoncList = JSON.parseObject(entry.getValue().toString());
			set.add(_id);
			JSONObject subClist = innerJsoncList.getJSONObject("c_list");
			if (subClist.isEmpty()) {
				continue;
			}
			this.readPoiId2Set(set, subClist);
		}
	}

	private void updatePoiBusinessTreeIndex(JSONObject jsoncList, MongoCollection<Document> business_tree, Integer businessId) {
		Set<String> newPoiIds = new HashSet<String>();
		this.readPoiId2Set(newPoiIds, jsoncList);

		BasicDBObject condition = new BasicDBObject();
		condition.put("business_id", businessId);

		// 获取现有业务树中的poi列表
		MongoCursor<Document> mongoCursor = business_tree.find(condition).limit(1).iterator();
		Document bizTreeDoc = null;
		if (mongoCursor.hasNext()) {
			bizTreeDoc = mongoCursor.next();
		}
		JSONObject json = new JSONObject();
		if (bizTreeDoc != null) {
			json.put("c_list", bizTreeDoc.get("c_list"));
		}
		// poiIds(old)
		Set<String> oldPoiIds = new LinkedHashSet<String>();
		if (!json.isEmpty()) {
			this.readPoiId2Set(oldPoiIds, json.getJSONObject("c_list"));
		}

		MongoCollection<Document> poi_business_tree_index = mongoConnector.getDBCollection("poi_business_tree_index");

		long addCount = 0;
		for (String newId : newPoiIds) {
			if (!oldPoiIds.contains(newId)) {
				BasicDBObject addCondition = new BasicDBObject();
				addCondition.put("_id", new ObjectId(newId));
				BasicDBObject addBizId = new BasicDBObject();
				addBizId.append("$addToSet", new BasicDBObject("used_in_business", businessId));
				addBizId.put("$set", new BasicDBObject("_id", new ObjectId(newId)));

				UpdateResult updateResult = poi_business_tree_index.updateOne(addCondition, addBizId, new UpdateOptions().upsert(true));
				if (updateResult.getModifiedCount() == 0) {
					if (updateResult.getUpsertedId() != null) {
						addCount++;
					}
				} else {
					addCount += updateResult.getModifiedCount();
				}
			}
		}
		MsLogger.info("added bizId ["+ businessId+" to poi. Total =["+addCount+"]件");

		long deleteCount = 0;
		for (String oldId : oldPoiIds) {
			if (!newPoiIds.contains(oldId)) {
				BasicDBObject deleteCondition = new BasicDBObject();
				deleteCondition.put("_id", new ObjectId(oldId));
				BasicDBObject deleteBizId = new BasicDBObject();
				deleteBizId.append("$pull", new BasicDBObject("used_in_business", businessId));

				UpdateResult updateResult = poi_business_tree_index.updateOne(deleteCondition, deleteBizId);
				if (updateResult.getModifiedCount() == 0) {
					if (updateResult.getUpsertedId() != null) {
						deleteCount++;
					}
				} else {
					deleteCount += updateResult.getModifiedCount();
				}
			}
		}
		MsLogger.info("deleted bizId ["+ businessId+" to poi. Total =["+deleteCount+"]件");
	}

	private void deletePoiBusinessTreeIndex(MongoCollection<Document> business_tree, Integer businessId) {
		BasicDBObject condition = new BasicDBObject();
		condition.put("business_id", businessId);

		// 获取现有业务树中的poi列表
		MongoCursor<Document> mongoCursor = business_tree.find(condition).limit(1).iterator();
		Document bizTreeDoc = null;
		if (mongoCursor.hasNext()) {
			bizTreeDoc = mongoCursor.next();
		}
		JSONObject json = new JSONObject();
		if (bizTreeDoc != null) {
			json.put("c_list", bizTreeDoc.get("c_list"));
		}
		// poiIds(old)
		Set<String> oldPoiIds = new LinkedHashSet<String>();
		if (!json.isEmpty()) {
			this.readPoiId2Set(oldPoiIds, json.getJSONObject("c_list"));
		}

		MongoCollection<Document> poi_business_tree_index = mongoConnector.getDBCollection("poi_business_tree_index");

		long count = 0;
		for (String oldId : oldPoiIds) {
			BasicDBObject deleteCondition = new BasicDBObject();
			deleteCondition.put("_id", new ObjectId(oldId));
			BasicDBObject deleteBizId = new BasicDBObject();
			deleteBizId.append("$pull", new BasicDBObject("used_in_business", businessId));

			UpdateResult updateResult = poi_business_tree_index.updateOne(deleteCondition, deleteBizId);
			if (updateResult.getModifiedCount() == 0) {
				if (updateResult.getUpsertedId() != null) {
					count++;
				}
			} else {
				count += updateResult.getModifiedCount();
			}
		}
		MsLogger.info("deleted bizId ["+ businessId+" to poi. Total =["+count+"]件");
	}

	/**
	 * 获取tag定义列表
	 * @return
	 */
	private JSONObject getJsonTags() {
		JSONObject jsonTags = new JSONObject();
		MsPoiTagCriteria msPoiTagCriteria = new MsPoiTagCriteria();
		List<MsPoiTag> lstMsPoiTag = msPoiTagDAO.selectByCriteria(msPoiTagCriteria);
		if (lstMsPoiTag != null) {
			for (MsPoiTag msPoiTag : lstMsPoiTag) {
				jsonTags.put(msPoiTag.getTagId().toString(), msPoiTag.getTagName());
			}
		}
		return jsonTags;
	}
}
