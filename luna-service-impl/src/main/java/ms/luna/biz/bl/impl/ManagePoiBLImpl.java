package ms.luna.biz.bl.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import ms.biz.common.AuthenticatedUserHolder;
import ms.biz.common.MongoConnector;
import ms.biz.common.MongoUtility;
import ms.luna.biz.bl.ManagePoiBL;
import ms.luna.biz.bl.MsZoneCacheBL;
import ms.luna.biz.cons.VbConstant;
import ms.luna.biz.dao.custom.MsPoiFieldDAO;
import ms.luna.biz.dao.custom.MsPoiTagDAO;
import ms.luna.biz.dao.custom.model.MsTagFieldParameter;
import ms.luna.biz.dao.custom.model.MsTagFieldResult;
import ms.luna.biz.dao.model.MsPoiTag;
import ms.luna.biz.dao.model.MsPoiTagCriteria;
import ms.luna.biz.model.MsUser;
import ms.luna.biz.util.CharactorUtil;
import ms.luna.biz.util.JsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.biz.util.VbUtility;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Transactional(rollbackFor=Exception.class)
@Service("managePoiBL")
public class ManagePoiBLImpl implements ManagePoiBL {

	@Autowired
	private MsPoiTagDAO msPoiTagDAO;

	@Autowired
	private MsPoiFieldDAO msPoiFieldDAO; 

	@Autowired
	private MongoConnector mongoConnector;

	@Autowired
	private MsZoneCacheBL msZoneCacheBL;

	/**
	 * tag标签的level=1的部分
	 */
	private final static int TAG_LEVEL_TOP = 1;

	/**
	 * 获取分类信息<br>
	 */
	@Override
	public JSONObject getInitInfo(String json) {
		JSONObject data = this.getCommonFieldsDef();
		return JsonUtil.sucess("OK", data);
	}

	private boolean needSave(String tagsOfPage, String tagsOfField) {
		if (tagsOfPage == null || tagsOfPage.isEmpty() || "[]".equals(tagsOfPage)
				|| tagsOfField == null || tagsOfField.isEmpty() || "[]".equals(tagsOfField)) {
			return false;
		}
		tagsOfPage = tagsOfPage.replace("[", "").replace("]", "");
		tagsOfField = tagsOfField.replace("[", "").replace("]", "");
		String[] tagsOfFields = tagsOfField.split(",");
		for (String tag : tagsOfFields) {
			if (tagsOfPage.contains(tag)) {
				return true;
			}
		}
		return false;
	}
	private Document json2BsonForInsertOrUpdate(JSONObject param, boolean isInsert, boolean force) {
		Document doc = new Document();
		/* 
		 * 固定的公共部分
		 */
		String tag_ids = null;
		List<Double> lnglatArray = null;
		String zoneId = null;

		if (!force) {
			// 1.名称
			doc.put("long_title", param.getString("long_title"));
			// 2.别名
			doc.put("short_title", param.getString("short_title"));

			// 3.类别一级菜单列表
			doc.put("tags", this.convert2JsonArray(param, "tags"));

			// 3.类别二级菜单
			doc.put("sub_tag", param.getInt("subTag"));

			lnglatArray = new ArrayList<Double>();
			// 4.经纬度Point(先经度后纬度), lnglat : { type: "Point", coordinates: [ -73.88, 40.78 ] }
			lnglatArray.add(param.getDouble("lng"));
			lnglatArray.add(param.getDouble("lat"));
			BasicDBObject lnglat = new BasicDBObject();
			lnglat.append("type", "Point")
			.append("coordinates", lnglatArray);
			doc.put("lnglat", lnglat);

			// 5.地址
			zoneId = param.getString("zone_id");
			String mergerName = msZoneCacheBL.getMergerName(zoneId);
			// zone id
			doc.put("zone_id", zoneId);

			doc.put("province_id", this.getProvinceId(zoneId));
			doc.put("city_id", this.getCityId(zoneId));
			doc.put("county_id", this.getCountyId(zoneId));

			// 合并后的国，省，市，区、县名称
			doc.put("merger_name", mergerName);
			// 详细地址
			doc.put("detail_address", param.getString("detail_address"));

			// 6.简介
			doc.put("brief_introduction", param.getString("brief_introduction"));

			// 7.缩略图
			doc.put("thumbnail", param.getString("thumbnail"));
			doc.put("thumbnail_1_1", param.getString("thumbnail_1_1"));
			doc.put("thumbnail_16_9", param.getString("thumbnail_16_9"));

			// 8.全景数据ID
			doc.put("panorama", param.getString("panorama"));
			// 9.联系电话
			doc.put("contact_phone", param.getString("contact_phone"));

			tag_ids = param.getString("tags");

		} else {
//			// 1.名称
			if (param.has("long_title")) {
				doc.put("long_title", param.getString("long_title"));
			} else {
				doc.put("long_title", "");
			}
			
			// 2.别名
			if (param.has("short_title")) {
				doc.put("short_title", param.getString("short_title"));
			} else {
				doc.put("short_title", "");
			}

			// 3.属性列表
			if (param.has("tags")) {
				doc.put("tags", this.convert2JsonArray(param, "tags"));
				tag_ids = param.getString("tags");
			} else {
				doc.put("tags", new JSONArray());
				tag_ids = "[]";
			}
			// 3.类别二级菜单
			if (param.has("subTag")) {
				doc.put("sub_tag", param.getInt("subTag"));
			} else {
				doc.put("sub_tag", 0);
			}

			// 4.经纬度Point(先经度后纬度), lnglat : { type: "Point", coordinates: [ -73.88, 40.78 ] }
			lnglatArray = new ArrayList<Double>();
			try {
				lnglatArray.add(param.getDouble("lng"));
			} catch (Exception e) {
				lnglatArray.add(0D);
			}

			try {
				lnglatArray.add(param.getDouble("lat"));
			} catch (Exception e) {
				lnglatArray.add(0D);
			}

			BasicDBObject lnglat = new BasicDBObject();
			lnglat.append("type", "Point")
			.append("coordinates", lnglatArray);
			doc.put("lnglat", lnglat);

			// 5.地址
			if (param.has("zone_id")) {
				zoneId = param.getString("zone_id");
			} else {
				zoneId = "110000";
			}
			String mergerName = msZoneCacheBL.getMergerName(zoneId);
			// zone id
			doc.put("zone_id", zoneId);

			doc.put("province_id", this.getProvinceId(zoneId));
			doc.put("city_id", this.getCityId(zoneId));
			doc.put("county_id", this.getCountyId(zoneId));

			// 合并后的国，省，市，区、县名称
			doc.put("merger_name", CharactorUtil.nullToBlank(mergerName));
			// 详细地址
			if (param.has("detail_address")) {
				doc.put("detail_address", param.getString("detail_address"));
			} else {
				doc.put("detail_address", "");
			}

			// 6.简介
			if (param.has("detail_address")) {
				doc.put("brief_introduction", param.getString("brief_introduction"));
			} else {
				doc.put("brief_introduction", "");
			}

			// 7.缩略图
			if (param.has("thumbnail")) {
				doc.put("thumbnail", param.getString("thumbnail"));
			} else {
				doc.put("thumbnail", "");
			}
			doc.put("thumbnail_1_1", "");
			doc.put("thumbnail_16_9", "");

			// 8.全景数据ID
			doc.put("panorama", param.getString("panorama"));
			// 9.联系电话
			doc.put("contact_phone", param.getString("contact_phone"));
		}

		/*
		 * 私有字段处理
		 */
		JSONArray privateFields = this.getPrivateFields(null);
		for (int i = 0; i < privateFields.size(); i++) {
			JSONObject privateField = privateFields.getJSONObject(i);
			JSONObject field_def = privateField.getJSONObject("field_def");

			String field_name_def = field_def.getString("field_name");
			int field_type = field_def.getInt("field_type");
			int field_size = field_def.getInt("field_size");
			String field_tag_ids = field_def.getString("tag_id");
			Object field_name_val = null;
			
			switch (field_type) {
				case VbConstant.POI_FIELD_TYPE.复选框列表:
					field_name_val = this.convert2JsonArray(param, field_name_def);
					break;
				case VbConstant.POI_FIELD_TYPE.文本框:
				case VbConstant.POI_FIELD_TYPE.文本域:
				case VbConstant.POI_FIELD_TYPE.图片:
				case VbConstant.POI_FIELD_TYPE.音频:
				case VbConstant.POI_FIELD_TYPE.视频:
					if (!param.has(field_name_def)) {
						field_name_val = "";
					} else {
						field_name_val = param.getString(field_name_def);
					}
					break;
				default:
					MsLogger.debug("field [" + field_name_def + "] is missing!");
					if (!force) {
						throw new RuntimeException("field [" +field_name_def + "] is missing!");
					}
					break;
			}

			// 按照类型check检查
			switch (field_type) {
				case VbConstant.POI_FIELD_TYPE.文本框:
				case VbConstant.POI_FIELD_TYPE.文本域:
					if (field_size != -1) {
						if (!force) {
							if (CharactorUtil.countChineseEn((String)field_name_val) > field_size) {
								MsLogger.debug("field [" +field_name_def + "], is too long!");
								throw new RuntimeException("field [" +field_name_def + "], is too long!");
							}
						}
					}
					if (needSave(tag_ids, field_tag_ids)) {
						doc.put(field_name_def, field_name_val);
					} else {
						doc.put(field_name_def, "");
					}
					break;
				case VbConstant.POI_FIELD_TYPE.复选框列表:
					if (needSave(tag_ids, field_tag_ids)) {
						doc.put(field_name_def, field_name_val);
					} else {
						doc.put(field_name_def, new JSONArray());
					}
					break;
				case VbConstant.POI_FIELD_TYPE.POINT:
					if (!force) {
						throw new RuntimeException("POINT 类型的私有字段， 需要实现，暂时没有实现");
					}
					break;
				case VbConstant.POI_FIELD_TYPE.图片:
					if (needSave(tag_ids, field_tag_ids)) {
						if (!force) {
							if (!VbUtility.checkCOSPicIsOK((String)field_name_val)) {
								throw new RuntimeException("图片地址不正确或者不是已上传的地址["+field_name_val+"]");
							}
						}
						doc.put(field_name_def, field_name_val);
					} else {
						doc.put(field_name_def, "");
					}
					break;
				case VbConstant.POI_FIELD_TYPE.音频:
					if (needSave(tag_ids, field_tag_ids)) {
						if (!force) {
							if (!VbUtility.checkCOSAudioIsOK((String)field_name_val)) {
								throw new RuntimeException("音频地址不正确或者不是已上传的地址["+field_name_val+"]");
							}
						}
						doc.put(field_name_def, field_name_val);
					} else {
						doc.put(field_name_def, "");
					}
					break;
				case VbConstant.POI_FIELD_TYPE.视频:
					if (needSave(tag_ids, field_tag_ids)) {
//						if (!VbUtility.checkCOSVideoIsOK(field_name_val)) {
//							throw new RuntimeException("视频地址不正确或者不是已上传的地址");
//						}
						//-----------------------------------------------------------------------
						// 视频需要通过转码得到url，上传后返回值为id。因此不进行视频地址的检测。
						//-----------------------------------------------------------------------
						
						doc.put(field_name_def, field_name_val);
					} else {
						doc.put(field_name_def, "");
					}
					break;
				case VbConstant.POI_FIELD_TYPE.下拉列表:
					// TODO:
					throw new RuntimeException("下拉列表 类型的私有字段， 需要实现，暂时没有实现");
//					break;
				default:
					throw new RuntimeException("暂时没有扩展的私有字段类型["+ field_type +"]");
//					break;
			}
		}

		/*
		 * 公共的时间和更新者者 
		 */
		Date date = new Date();
		if (!force) {
			if (isInsert) {
				doc.put("regist_hhmmss", date);
			}
		} else {
			doc.put("regist_hhmmss", date);
		}

		doc.put("update_hhmmss", date);
		MsUser msUser = (MsUser)AuthenticatedUserHolder.get();
		if (msUser != null) {
			doc.put("updated_by_unique_id", msUser.getUniqueId());
		}
		return doc;
	}

	/**
	 * 查找共有字段和私有字段，分步记录
	 */
	@Override
	public JSONObject addPoi(String json) {

		JSONObject param = JSONObject.fromObject(json);
		Document doc = json2BsonForInsertOrUpdate(param, Boolean.TRUE, Boolean.FALSE);
		MongoCollection<Document> poi_collection = mongoConnector.getDBCollection("poi_collection");

		boolean inserted = false;
		Document document = null;
		// 保证插入数据，在判重策略下是唯一的
		synchronized (MongoConnector.class) {
			document = MongoUtility.findOnePoi(poi_collection, param);
			if (document == null) {
				poi_collection.insertOne(doc);
				inserted = true;
			}
		}
		if (document == null) {
			document = MongoUtility.findOnePoi(poi_collection, param);
		}
		if (document != null) {
			if (inserted) {
				return JsonUtil.sucess("新建插入成功");
			}
			return JsonUtil.error("-1", "已经存在");
		} else {
			return JsonUtil.error("-2", "插入失败");
		}
	}

	@Override
	public JSONObject getPois(String json) {
		JSONObject param = JSONObject.fromObject(json);
		Integer offset = null;
		if (param.has("offset")) {
			offset = param.getInt("offset");
		}
		Integer limit = null;
		if (param.has("limit")) {
			limit = param.getInt("limit");
		}

		MongoCollection<Document> poi_collection = mongoConnector.getDBCollection("poi_collection");

		Long total = 0L;

		if (offset == null) {
			offset = 0;
		}
		if (limit == null) {
			limit = VbConstant.DEFAULT_MAX_LIMT;
		}

		BasicDBObject condition = new BasicDBObject();
		String filterName = null;
		if (param.has("filterName")) {
			filterName = param.getString("filterName");
		}
		if (filterName != null && !filterName.isEmpty()) {
			Pattern pattern = Pattern.compile("^.*" + filterName+ ".*$", Pattern.CASE_INSENSITIVE);
			condition.append("long_title", pattern);
		}
		String zoneId = null;
		if (param.has("zoneId")) {
			zoneId = param.getString("zoneId");
			List<String> subZoneIds = msZoneCacheBL.getSubZoneIds(zoneId);
			BasicDBList filterSubZoneIds = new BasicDBList();
			for (String subZoneId : subZoneIds) {
				filterSubZoneIds.add(new BasicDBObject("zone_id", subZoneId));  
			}
			condition.put("$or", filterSubZoneIds);
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

		MsPoiTagCriteria msPoiTagCriteria = new MsPoiTagCriteria();
		List<MsPoiTag> lstMsPoiTag = msPoiTagDAO.selectByCriteria(msPoiTagCriteria);
		Map<String, String> tagMap = new HashMap<String, String>();
		if (lstMsPoiTag != null) {
			for (MsPoiTag msPoiTag : lstMsPoiTag) {
				tagMap.put(msPoiTag.getTagId().toString(), msPoiTag.getTagName());
			}
		}

		JSONArray pois = new JSONArray();
		while (mongoCursor.hasNext()) {
			Document docPoi= mongoCursor.next();
			JSONObject poi = new JSONObject();
			// 别名
			String short_title = docPoi.getString("short_title");
			// 名称
			String long_title = docPoi.getString("long_title");
			// 经纬度
			JSONObject lnglat = JSONObject.fromObject(docPoi.get("lnglat"));
			JSONArray coordinates = lnglat.getJSONArray("coordinates");
			// 经度
			Double lng = coordinates.getDouble(0);
			// 纬度
			Double lat = coordinates.getDouble(1);

			JSONArray tags = new JSONArray();
			// 标签(类别)
			Object poiTags = docPoi.get("tags");
			if (poiTags != null) {
				tags = JSONArray.fromObject(poiTags);
			}

			// 地域Id
			String zone_id = docPoi.getString("zone_id");

			// poi的ID
			String _id = docPoi.getObjectId("_id").toString();

			poi.put("short_title", short_title);
			poi.put("long_title", long_title);
			poi.put("lng", lng);
			poi.put("lat", lat);

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < tags.size(); i++) {
				if (i != 0) {
					sb.append("、");
				}
				sb.append(tagMap.get(tags.getString(i)));
			}
			poi.put("tags", sb.toString());
			String province_name = msZoneCacheBL.getProvinceName(zone_id);
			String city_name = msZoneCacheBL.getCityName(zone_id);
			poi.put("province_name", province_name);
			poi.put("city_name", city_name);
			poi.put("_id", _id);
			pois.add(poi);
		}
		JSONObject data = JSONObject.fromObject("{}");
		data.put("pois", pois);
		data.put("total", total);
		return JsonUtil.sucess("success", data);
	}

	@Override
	public JSONObject updatePoi(String json) {

		JSONObject param = JSONObject.fromObject(json);
		Document doc = json2BsonForInsertOrUpdate(param, Boolean.FALSE, Boolean.FALSE);

		String _id = param.getString("_id");
		BasicDBObject keyId = new BasicDBObject();
		keyId.put("_id", new ObjectId(_id));
		BasicDBObject updateDocument = new BasicDBObject();
		updateDocument.append("$set", doc);

		MongoCollection<Document> poi_collection = mongoConnector.getDBCollection("poi_collection");
		UpdateResult updateResult = poi_collection.updateOne(keyId, updateDocument);

		if (updateResult.getModifiedCount() > 0) {
			return JsonUtil.sucess("success");
		}
		return JsonUtil.error("-1", "fature");
	}

	@Override
	public JSONObject asyncDeletePoi(String json) {
		JSONObject param = JSONObject.fromObject(json);
		String _id = param.getString("_id");
		BasicDBObject keyId = new BasicDBObject();
		keyId.put("_id", new ObjectId(_id));
		MongoCollection<Document> poi_collection = mongoConnector.getDBCollection("poi_collection");
		DeleteResult deleteResult = poi_collection.deleteOne(keyId);
		if (deleteResult.getDeletedCount() > 0) {
			return JsonUtil.sucess("success");
		}

		return JsonUtil.error("-1", "not existing");
	}

	/**
	 * 获取公共属性定义(tags定义)
	 * @return
	 */
	private JSONObject getCommonFieldsDef() {

		MsPoiTagCriteria msPoiTagCriteria = new MsPoiTagCriteria();
		// 去除公共字段的标签
		msPoiTagCriteria.createCriteria()
		.andTagIdNotEqualTo(VbConstant.POI.公共TAGID);
		msPoiTagCriteria.setOrderByClause("tag_level asc, parent_tag_id asc, ds_order asc");
		List<MsPoiTag> lstMsPoiTag = msPoiTagDAO.selectByCriteria(msPoiTagCriteria);

		JSONArray tags = JSONArray.fromObject("[]");
		Map<Integer, List<MsPoiTag>> tagMapList = new LinkedHashMap<Integer, List<MsPoiTag>>();
		if (lstMsPoiTag == null) {
			JSONObject data = JSONObject.fromObject("{}");
			data.put("tags", tags);
			return JsonUtil.sucess("OK", data);
		}

		// 以一级tag先进行分组
		for (MsPoiTag msPoiTag : lstMsPoiTag) {
			if (TAG_LEVEL_TOP == msPoiTag.getTagLevel()) {
				List<MsPoiTag> lst = new ArrayList<MsPoiTag>();
				lst.add(msPoiTag);
				tagMapList.put(msPoiTag.getTagId(), lst);
				continue;
			}
			List<MsPoiTag> lst = tagMapList.get(msPoiTag.getParentTagId());
			lst.add(msPoiTag);
		}

		/*
		 *  [{tag_id,tag_name,sub_tags[{tag_id,tag_name},...]]
		 */
		Set<Entry<Integer, List<MsPoiTag>>> set = tagMapList.entrySet();
		for (Entry<Integer, List<MsPoiTag>> entry : set) {
			List<MsPoiTag> lst = entry.getValue();
			// 一定存在首个
			MsPoiTag firstLevel = lst.get(0);
			JSONObject tag = JSONObject.fromObject("{}");
			tag.put("tag_id", firstLevel.getTagId());
			tag.put("tag_name", firstLevel.getTagName());
			JSONArray subTags = new JSONArray();
			// 跳过首个
			for (int i = 1; i < lst.size(); i++) {
				JSONObject subTag = JSONObject.fromObject("{}");
				subTag.put("tag_id", lst.get(i).getTagId());
				subTag.put("tag_name", lst.get(i).getTagName());
				subTags.add(subTag);
			}
			tag.put("sub_tags", subTags);
			tags.add(tag);
		}

		JSONObject data = JSONObject.fromObject("{}");
		data.put("tags_def", tags);
		return data;
	}

	private Document getPoiById(String _id) {
		MongoCollection<Document> poi_collection = mongoConnector.getDBCollection("poi_collection");
		BasicDBObject keyId = new BasicDBObject();
		keyId.put("_id", new ObjectId(_id));
		return poi_collection.find(keyId).limit(1).first(); 
	}

	private JSONObject getCommmFieldVal(Document docPoi) {
		JSONObject commonFieldsVal = new JSONObject();
		commonFieldsVal.put("_id", docPoi.getObjectId("_id".toString()));
		/*
		 * 公共字段值
		 */
		// 1.名称
		String long_title = docPoi.getString("long_title");
		commonFieldsVal.put("long_title", long_title);
		// 2.别名
		String short_title = docPoi.getString("short_title");
		commonFieldsVal.put("short_title", short_title);

		// 3.标签一级类别值
		JSONArray tags_values = new JSONArray();
		Object poiTags = docPoi.get("tags");
		if (poiTags != null) {
			tags_values = JSONArray.fromObject(poiTags);
		} else {
			tags_values.add(0);
		}
		commonFieldsVal.put("tags_values", tags_values);

		// 3.标签二级类别值
		if (docPoi.containsKey("sub_tag")) {
			commonFieldsVal.put("subTag", docPoi.getInteger("sub_tag"));
		} else {
			commonFieldsVal.put("subTag", 0);
		}

		// 4.经纬度
		JSONObject lnglat = JSONObject.fromObject(docPoi.get("lnglat"));
		JSONArray coordinates = lnglat.getJSONArray("coordinates");
		// 经度
		Double lng = coordinates.getDouble(0);
		// 纬度
		Double lat = coordinates.getDouble(1);
		commonFieldsVal.put("lat", lat);
		commonFieldsVal.put("lng", lng);

		// 5.地址地域
		String zone_id = docPoi.getString("zone_id");
		String county_id = msZoneCacheBL.getCountyId(zone_id);
		commonFieldsVal.put("county_id", county_id);
		String city_id = msZoneCacheBL.getCityId(zone_id);
		commonFieldsVal.put("city_id", city_id);
		String province_id = msZoneCacheBL.getProvinceId(zone_id);
		commonFieldsVal.put("province_id", province_id);
//		commonFieldsVal.put("country_id", msZoneCacheBL.getCountryId(zone_id));
		// 详细地址
		String detail_address = docPoi.getString("detail_address");
		commonFieldsVal.put("detail_address", detail_address);

		// 6.简介
		String brief_introduction = docPoi.getString("brief_introduction");
		commonFieldsVal.put("brief_introduction", brief_introduction);

		// 7.缩略图
		String thumbnail = docPoi.getString("thumbnail");
		commonFieldsVal.put("thumbnail", thumbnail);
		String thumbnail_1_1 = docPoi.getString("thumbnail_1_1");
		commonFieldsVal.put("thumbnail_1_1", thumbnail_1_1);
		String thumbnail_16_9 = docPoi.getString("thumbnail_16_9");
		commonFieldsVal.put("thumbnail_16_9", thumbnail_16_9);

		// 8.全景数据ID
		String panorama = "";
		if (docPoi.containsKey("panorama")) {
			panorama = docPoi.getString("panorama");
		}
		commonFieldsVal.put("panorama", panorama);

		// 9.联系电话
		String contact_phone = "";
		if (docPoi.containsKey("contact_phone")) {
			contact_phone = docPoi.getString("contact_phone");
		}
		commonFieldsVal.put("contact_phone", contact_phone);

		return commonFieldsVal;
	}

	/**
	 * 取POI定义
	 */
	@Override
	public JSONObject initAddPoi(String json) {
		JSONObject data = new JSONObject();
		data.put("common_fields_def", this.getCommonFieldsDef());
		data.put("private_fields_def", this.getPrivateFields(null));
		return JsonUtil.sucess("success", data);
	}

	@Override
	public JSONObject initEditPoi(String json) {
		JSONObject param = JSONObject.fromObject(json);
		String _id = param.getString("_id");
		Document docPoi = this.getPoiById(_id);
		if (docPoi == null) {
			MsLogger.debug("poi ["+_id+"] is not found!");
			return JsonUtil.error("-1", "poi ["+_id+"] is not found!");
		}

		JSONObject data = new JSONObject();
		data.put("common_fields_def", this.getCommonFieldsDef());
		data.put("common_fields_val", this.getCommmFieldVal(docPoi));

		data.put("private_fields", this.getPrivateFields(docPoi));
		return JsonUtil.sucess("success", data);
	}

	private JSONArray getPrivateFields(Document docPoi) {
		// 查找私有字段属性
		MsTagFieldParameter msTagFieldParameter = new MsTagFieldParameter();
		msTagFieldParameter.setTagId(VbConstant.POI.公共TAGID);
		List<MsTagFieldResult> lstMsTagFieldResult = msPoiFieldDAO.selectFieldTags(msTagFieldParameter);
		JSONArray privateFields = JSONArray.fromObject("[]");

		Map<String, StringBuffer> tagIdMaps = new HashMap<String, StringBuffer>();

		for (MsTagFieldResult msTagFieldResult : lstMsTagFieldResult) {
			StringBuffer tagIds = tagIdMaps.get(msTagFieldResult.getFieldName());
			if (tagIds == null) {
				tagIds = new StringBuffer();
			} else {
				tagIds.append(",");
			}
			tagIds.append(msTagFieldResult.getTagId());
			tagIdMaps.put(msTagFieldResult.getFieldName(), tagIds);
		}

		Set<String> fieldSet = new HashSet<String>();
		for (MsTagFieldResult msTagFieldResult : lstMsTagFieldResult) {
			if (fieldSet.contains(msTagFieldResult.getFieldName())) {
				continue;
			}
			fieldSet.add(msTagFieldResult.getFieldName());

			JSONObject field_def = JSONObject.fromObject("{}");
			field_def.put("field_name", msTagFieldResult.getFieldName());
			field_def.put("tag_id", tagIdMaps.get(msTagFieldResult.getFieldName()).toString());
			field_def.put("tag_name", msTagFieldResult.getTagName());
			field_def.put("field_show_name", msTagFieldResult.getFieldShowName());
			field_def.put("display_order", msTagFieldResult.getDisplayOrder());
			field_def.put("field_type", msTagFieldResult.getFieldType());
			field_def.put("field_size", msTagFieldResult.getFieldSize());
			field_def.put("placeholder", CharactorUtil.nullToBlank(msTagFieldResult.getPlaceholder()));
			if (msTagFieldResult.getFieldType().intValue() == VbConstant.POI_FIELD_TYPE.复选框列表) {
				field_def.put("extension_attrs", JSONArray.fromObject(msTagFieldResult.getExtensionAttrs()));
			} else {
				field_def.put("extension_attrs", CharactorUtil.nullToBlank(msTagFieldResult.getExtensionAttrs()));
			}
			JSONObject field_val = JSONObject.fromObject("{}");
			if (docPoi != null) {
				field_val.put("value", docPoi.get(msTagFieldResult.getFieldName()));
			} else {
				switch(msTagFieldResult.getFieldType()) {
					case VbConstant.POI_FIELD_TYPE.文本框:
					case VbConstant.POI_FIELD_TYPE.文本域:
					case VbConstant.POI_FIELD_TYPE.图片:
					case VbConstant.POI_FIELD_TYPE.视频:
					case VbConstant.POI_FIELD_TYPE.音频:
						field_val.put("value", "");
						break;
					default:
						break;
				}
			}

			JSONObject field = JSONObject.fromObject("{}");

			field.put("field_def", field_def);
			field.put("field_val", field_val);
			privateFields.add(field);
		}
		return privateFields;
	}

	private JSONArray getPrivateFieldsForTemplete() {
		// 查找私有字段属性
		MsTagFieldParameter msTagFieldParameter = new MsTagFieldParameter();
		msTagFieldParameter.setTagId(VbConstant.POI.公共TAGID);
		List<MsTagFieldResult> lstMsTagFieldResult = msPoiFieldDAO.selectFieldTags(msTagFieldParameter);
		JSONArray privateFields = JSONArray.fromObject("[]");
		for (MsTagFieldResult msTagFieldResult : lstMsTagFieldResult) {

			JSONObject field_def = JSONObject.fromObject("{}");
			field_def.put("field_name", msTagFieldResult.getFieldName());
			field_def.put("tag_id", msTagFieldResult.getTagId());
			field_def.put("tag_name", msTagFieldResult.getTagName());
			field_def.put("field_show_name", msTagFieldResult.getFieldShowName());
			field_def.put("display_order", msTagFieldResult.getDisplayOrder());
			field_def.put("field_type", msTagFieldResult.getFieldType());
			field_def.put("field_size", msTagFieldResult.getFieldSize());
			field_def.put("placeholder", CharactorUtil.nullToBlank(msTagFieldResult.getPlaceholder()));
			field_def.put("field_tips_for_templete", CharactorUtil.nullToBlank(msTagFieldResult.getFieldTipsForTemplete()));
			if (msTagFieldResult.getFieldType().intValue() == VbConstant.POI_FIELD_TYPE.复选框列表) {
				field_def.put("extension_attrs", JSONArray.fromObject(msTagFieldResult.getExtensionAttrs()));
			} else {
				field_def.put("extension_attrs", CharactorUtil.nullToBlank(msTagFieldResult.getExtensionAttrs()));
			}

			JSONObject field = JSONObject.fromObject("{}");

			field.put("field_def", field_def);
			privateFields.add(field);
		}
		return privateFields;
	}

	@Override
	public JSONObject downloadPoiTemplete(String json) {
		JSONArray privateFieldsDef = this.getPrivateFieldsForTemplete();
		JSONObject data = new JSONObject();
		data.put("privateFieldsDef", privateFieldsDef);
		return JsonUtil.sucess("OK", data);
	}

	@Override
	public JSONObject savePois(String json) {
		JSONObject param = JSONObject.fromObject(json);
		JSONArray saveErrors = new JSONArray();
		JSONArray no_check_errors = param.getJSONArray("no_check_errors");
		for (int i = 0; i < no_check_errors.size(); i++) {
			JSONObject poiJson = no_check_errors.getJSONObject(i);
			try {
				Document doc = json2BsonForInsertOrUpdate(poiJson, Boolean.TRUE, Boolean.FALSE);
				MongoCollection<Document> poi_collection = mongoConnector.getDBCollection("poi_collection");

				boolean inserted = false;
				Document document = null;
				// 保证插入数据，在判重策略下是唯一的
				synchronized (MongoConnector.class) {
					document = MongoUtility.findOnePoi(poi_collection, poiJson);
					if (document == null) {
						poi_collection.insertOne(doc);
						inserted = true;
					}
				}
				if (document == null) {
					document = MongoUtility.findOnePoi(poi_collection, poiJson);
				}
				if (document != null) {
					if (!inserted) {
						// error
						poiJson.put("_id", document.getObjectId("_id").toString());
						saveErrors.add(poiJson);
					}
				} else {
					// error
					saveErrors.add(poiJson);
				}
			} catch (Exception e) {
				// error
				MsLogger.debug(e);
				saveErrors.add(poiJson);
			}
		}
		JSONObject data = new JSONObject();
		data.put("saveErrors", saveErrors);
		return JsonUtil.sucess("OK", data);
	}

	@Override
	public JSONObject initFixPoi(String json) {
		JSONObject param = JSONObject.fromObject(json);

		Document docPoi = json2BsonForInsertOrUpdate(param, Boolean.TRUE, Boolean.TRUE);

		JSONObject data = new JSONObject();
		data.put("common_fields_def", this.getCommonFieldsDef());
		data.put("common_fields_val", this.getCommmFieldVal(docPoi));

		data.put("private_fields", this.getPrivateFields(docPoi));
		return JsonUtil.sucess("success", data);
	}

	@Override
	public JSONObject getPoi(String json) {
		return initEditPoi(json);
	}

	private JSONArray convert2JsonArray(JSONObject param, String key) {
		String val = null;
		if (param.has(key)) {
			val = param.getString(key);
		}
		return convert2JsonArray(val);
	}

	private JSONArray convert2JsonArray(String val) {
		if (val == null) {
			return new JSONArray();
		}
		if (val.startsWith("[")) {
			return JSONArray.fromObject(val);
		}
		return JSONArray.fromObject("["+val+"]");
	}
	private String getProvinceId(String zoneId) {
		String provinceId = msZoneCacheBL.getProvinceId(zoneId);
		return "ALL".equals(provinceId) || provinceId == null?"": provinceId;
	}
	private String getCityId(String zoneId) {
		String cityId = msZoneCacheBL.getCityId(zoneId);
		return "ALL".equals(cityId) || cityId == null?"": cityId;
	}
	private String getCountyId(String zoneId) {
		String countyId = msZoneCacheBL.getCountyId(zoneId);
		return "ALL".equals(countyId) || countyId == null?"": countyId;
	}
}
