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
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import ms.biz.common.AuthenticatedUserHolder;
import ms.biz.common.MongoConnector;
import ms.biz.common.MongoUtility;
import ms.biz.common.ServiceConfig;
import ms.luna.biz.bl.ManagePoiBL;
import ms.luna.biz.bl.MsZoneCacheBL;
import ms.luna.biz.cons.VbConstant;
import ms.luna.biz.dao.custom.MsPoiFieldDAO;
import ms.luna.biz.dao.custom.MsPoiTagDAO;
import ms.luna.biz.dao.custom.model.MsTagFieldParameter;
import ms.luna.biz.dao.custom.model.MsTagFieldResult;
import ms.luna.biz.dao.model.MsPoiField;
import ms.luna.biz.dao.model.MsPoiFieldCriteria;
import ms.luna.biz.dao.model.MsPoiTag;
import ms.luna.biz.dao.model.MsPoiTagCriteria;
import ms.luna.biz.model.MsUser;
import ms.luna.biz.util.CharactorUtil;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.biz.util.VbUtility;
import ms.luna.common.MsLunaMessage;
import ms.luna.common.PoiCommon;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

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
		return FastJsonUtil.sucess("OK", data);
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

		String lang = param.getString("lang");

		if (!force) {
			// 1.名称
			doc.put("long_title", param.getString("long_title"));
			// 2.别名
			doc.put("short_title", param.getString("short_title"));

			// 3.类别一级菜单列表		
			doc.put("tags", FastJsonUtil.castStrNumArray2IntNumArray(
					this.convert2JsonArray(param, "tags")));

			// 3.类别二级菜单
			doc.put("sub_tag", param.getInteger("subTag"));

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

			// 英文地域合并后的名称
			String mergerName = null;
			if (PoiCommon.POI.EN.equals(lang)) {
				mergerName = msZoneCacheBL.getMergerNameEn(zoneId);
			} else {
				mergerName = msZoneCacheBL.getMergerName(zoneId);
			}

			// zone id
			doc.put("zone_id", zoneId);

			doc.put("province_id", this.getProvinceId(zoneId));
			doc.put("city_id", this.getCityId(zoneId));
			doc.put("county_id", this.getCountyId(zoneId));

			// 合并后的国，省，市，区、县名称
			doc.put("merger_name", mergerName);
			// 详细地址
			doc.put("detail_address", param.getString("detail_address"));

			// 6.详细介绍
			doc.put("brief_introduction", param.getString("brief_introduction"));

			// 7.缩略图
			doc.put("thumbnail", param.getString("thumbnail"));
			doc.put("thumbnail_1_1", param.getString("thumbnail_1_1"));
			doc.put("thumbnail_16_9", param.getString("thumbnail_16_9"));

			// 8.全景数据ID
			doc.put("panorama", param.getString("panorama"));
			
			// 8.全景类型
			doc.put("panorama_type", Integer.parseInt(param.getString("panorama_type")));
			
			// 9.联系电话
			doc.put("contact_phone", param.getString("contact_phone"));

			tag_ids = param.getString("tags");

		} else {
			// 1.名称
			if (param.containsKey("long_title")) {
				doc.put("long_title", param.getString("long_title"));
			} else {
				doc.put("long_title", "");
			}

			// 2.别名
			if (param.containsKey("short_title")) {
				doc.put("short_title", param.getString("short_title"));
			} else {
				doc.put("short_title", "");
			}

			// 3.属性列表
			if (param.containsKey("tags")) {
				doc.put("tags", FastJsonUtil.castStrNumArray2IntNumArray(
						this.convert2JsonArray(param, "tags")));
				tag_ids = param.getString("tags");
			} else {
				doc.put("tags", FastJsonUtil.createBlankIntegerJsonArray());
				tag_ids = "[]";
			}
			// 3.类别二级菜单
			if (param.containsKey("subTag")) {
				doc.put("sub_tag", param.getInteger("subTag"));
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
			if (param.containsKey("zone_id")) {
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
			if (param.containsKey("detail_address")) {
				doc.put("detail_address", param.getString("detail_address"));
			} else {
				doc.put("detail_address", "");
			}

			// 6.简介
			if (param.containsKey("detail_address")) {
				doc.put("brief_introduction", param.getString("brief_introduction"));
			} else {
				doc.put("brief_introduction", "");
			}

			// 7.缩略图
			if (param.containsKey("thumbnail")) {
				doc.put("thumbnail", param.getString("thumbnail"));
			} else {
				doc.put("thumbnail", "");
			}
			doc.put("thumbnail_1_1", "");
			doc.put("thumbnail_16_9", "");

			// 8.全景数据ID
			doc.put("panorama", param.getString("panorama"));
			
			// 8.全景类型
			doc.put("panorama_type", Integer.parseInt(param.getString("panorama_type")));
						
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
			String fieldShowName = field_def.getString("field_show_name");
			String field_name_def = field_def.getString("field_name");
			int field_type = field_def.getInteger("field_type");
			int field_size = field_def.getInteger("field_size");
			String field_tag_ids = field_def.getString("tag_id");
			Object field_name_val = null;

			switch (field_type) {
				case VbConstant.POI_FIELD_TYPE.复选框列表:
					field_name_val = FastJsonUtil.castStrNumArray2IntNumArray(
							this.convert2JsonArray(param, field_name_def));
					break;
				case VbConstant.POI_FIELD_TYPE.文本框:
				case VbConstant.POI_FIELD_TYPE.文本域:
				case VbConstant.POI_FIELD_TYPE.图片:
				case VbConstant.POI_FIELD_TYPE.音频:
				case VbConstant.POI_FIELD_TYPE.视频:
					if (!param.containsKey(field_name_def)) {
						field_name_val = "";
					} else {
						field_name_val = param.getString(field_name_def);
					}
					break;
				default:
					String msg = MsLunaMessage.getInstance().getMessage("LUNA.E0001", fieldShowName);
					MsLogger.error(msg);
					if (!force) {
						throw new RuntimeException(msg);
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
								String msg = MsLunaMessage.getInstance().getMessage("LUNA.E0002", fieldShowName, field_size);
								MsLogger.error(msg);
								throw new RuntimeException(msg);
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
						String msg = MsLunaMessage.getInstance().getMessage("FW.E0004", field_type);
						MsLogger.error(msg);
						throw new RuntimeException(msg);
					}
					break;
				case VbConstant.POI_FIELD_TYPE.图片:
					if (needSave(tag_ids, field_tag_ids)) {
						if (!force) {
							if (!VbUtility.checkCOSPicIsOK((String)field_name_val)) {
								String msg = MsLunaMessage.getInstance().getMessage("LUNA.E0009", field_name_val, "图片");
								MsLogger.error(msg);
								throw new RuntimeException(msg);
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
								String msg = MsLunaMessage.getInstance().getMessage("LUNA.E0009", field_name_val, "音频");
								MsLogger.error(msg);
								throw new RuntimeException(msg);
							}
						}
						doc.put(field_name_def, field_name_val);
					} else {
						doc.put(field_name_def, "");
					}
					break;
				case VbConstant.POI_FIELD_TYPE.视频:
					if (needSave(tag_ids, field_tag_ids)) {
						if (!force) {
							if (!VbUtility.checkCOSVideoIsOK((String)field_name_val)) {
								String msg = MsLunaMessage.getInstance().getMessage("LUNA.E0009", field_name_val, "视频");
								MsLogger.error(msg);
								throw new RuntimeException(msg);
							}
						}
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
					String msg = MsLunaMessage.getInstance().getMessage("FW.E0003");
					MsLogger.error(msg);
					throw new RuntimeException(msg);
//					break;
				default:
					msg = MsLunaMessage.getInstance().getMessage("FW.E0004", field_type);
					MsLogger.error(msg);
					throw new RuntimeException(msg);
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

		JSONObject param = JSONObject.parseObject(json);
		Document doc = this.json2BsonForInsertOrUpdate(param, Boolean.TRUE, Boolean.FALSE);
		MongoCollection<Document> poi_collection = mongoConnector.getDBCollection(PoiCommon.MongoTable.TABLE_POI_ZH);

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
				return FastJsonUtil.sucessWithMsg("LUNA.I0002", new Object[]{"POI["+param.getString("long_title")+"]"});
			}
			return FastJsonUtil.errorWithMsg("LUNA.E0007", "POI["+param.getString("long_title")+"]");
		} else {
			return FastJsonUtil.errorWithMsg("LUNA.E0008", "POI["+param.getString("long_title")+"]");
		}
	}

	@Override
	public JSONObject getPois(String json) {
		JSONObject param = JSONObject.parseObject(json);
		Integer offset = null;
		if (param.containsKey("offset")) {
			offset = param.getInteger("offset");
		}
		Integer limit = null;
		if (param.containsKey("limit")) {
			limit = param.getInteger("limit");
		}

		MongoCollection<Document> poi_collection = mongoConnector.getDBCollection(PoiCommon.MongoTable.TABLE_POI_ZH);

		Long total = 0L;

		if (offset == null) {
			offset = 0;
		}
		if (limit == null) {
			limit = VbConstant.DEFAULT_MAX_LIMT;
		}

		BasicDBObject condition = new BasicDBObject();
		String filterName = null;
		if (param.containsKey("filterName")) {
			filterName = param.getString("filterName");
		}
		if (filterName != null && !filterName.isEmpty()) {
			Pattern pattern = Pattern.compile("^.*" + filterName+ ".*$", Pattern.CASE_INSENSITIVE);
			condition.append("long_title", pattern);
		}
		String zoneId = null;
		if (param.containsKey("zoneId")) {
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
		// 检查是否有英文版
		MongoCollection<Document> poi_collection_en = mongoConnector.getDBCollection(PoiCommon.MongoTable.TABLE_POI_EN);
		JSONArray pois = new JSONArray();
		while (mongoCursor.hasNext()) {
			Document docPoi= mongoCursor.next();
			JSONObject poi = new JSONObject();
			// 别名
			String short_title = docPoi.getString("short_title");
			// 名称
			String long_title = docPoi.getString("long_title");
			// 经纬度
			JSONObject lnglat = FastJsonUtil.parse2Json(docPoi.get("lnglat"));
			JSONArray coordinates = lnglat.getJSONArray("coordinates");
			// 经度
			Double lng = coordinates.getDouble(0);
			// 纬度
			Double lat = coordinates.getDouble(1);

			JSONArray tags = new JSONArray();
			// 标签(类别)
			Object poiTags = docPoi.get("tags");
			if (poiTags != null) {
				tags = FastJsonUtil.parse2Array(poiTags);
			}

			// 地域Id
			String zone_id = docPoi.getString("zone_id");

			// poi的ID
			String _id = docPoi.getObjectId("_id").toString();
			
			BasicDBObject keyId = new BasicDBObject();
			keyId.put("_id", new ObjectId(_id));
			if (poi_collection_en.find(keyId).limit(1).first() != null) {
				poi.put("lang", PoiCommon.POI.EN);
			} else {
				poi.put("lang", PoiCommon.POI.ZH);
			}
			poi.put("short_title", short_title);
			poi.put("long_title", long_title);
			poi.put("lng", lng);
			poi.put("lat", lat);

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < tags.size(); i++) {
				if (i != 0) {
					sb.append("、");
				}
				String label = tagMap.get(tags.getString(i));
				if (label != null) {
					sb.append(label);
				}
			}
			poi.put("tags", sb.toString());
			String province_name = msZoneCacheBL.getProvinceName(zone_id);
			String city_name = msZoneCacheBL.getCityName(zone_id);
			poi.put("province_name", province_name);
			poi.put("city_name", city_name);
			poi.put("_id", _id);
			pois.add(poi);
		}
		JSONObject data = new JSONObject();
		data.put("pois", pois);
		data.put("total", total);
		return FastJsonUtil.sucess("success", data);
	}

	@Override
	public JSONObject updatePoi(String json) {

		JSONObject param = JSONObject.parseObject(json);
		String _id = param.getString("_id");

		// 语言种类
		String lang = param.getString("lang");
		Document doc = this.json2BsonForInsertOrUpdate(param, Boolean.FALSE, Boolean.FALSE);

		BasicDBObject keyId = new BasicDBObject();
		keyId.put("_id", new ObjectId(_id));
		BasicDBObject updateDocument = new BasicDBObject();
		updateDocument.append("$set", doc);

		MongoCollection<Document> poi_collection = null;
		// 英文
		if (PoiCommon.POI.EN.equals(lang)) {
			poi_collection = mongoConnector.getDBCollection(PoiCommon.MongoTable.TABLE_POI_EN);
			UpdateResult updateResult = poi_collection.updateOne(keyId, updateDocument);
			if (updateResult.getModifiedCount() == 0) {
				updateDocument = new BasicDBObject();
				doc.put("regist_hhmmss", doc.get("update_hhmmss"));
				updateDocument.append("$set", doc);
				updateResult = poi_collection.updateOne(keyId, updateDocument, new UpdateOptions().upsert(true));
			}
			if (updateResult.getModifiedCount() > 0 || updateResult.getUpsertedId() != null) {
				return FastJsonUtil.sucess("success");
			}

			// 中文
		} else {
			poi_collection = mongoConnector.getDBCollection(PoiCommon.MongoTable.TABLE_POI_ZH);
			UpdateResult updateResult = poi_collection.updateOne(keyId, updateDocument);
			if (updateResult.getModifiedCount() > 0) {
				// 尝试同步更新英文表
				Document oldEnDoc = this.getPoiById(_id, PoiCommon.POI.EN, Boolean.FALSE);
				if (oldEnDoc != null) {
					poi_collection = mongoConnector.getDBCollection(PoiCommon.MongoTable.TABLE_POI_EN);
					Document enDoc = new Document();
					// 一级分类
					enDoc.put("tags", doc.get("tags"));
					// 二级分类
					enDoc.put("sub_tag", doc.get("sub_tag"));
					// 经纬度
					enDoc.put("lnglat", doc.get("lnglat"));
					// zone_id
					enDoc.put("zone_id", doc.get("zone_id"));
					// 省份
					enDoc.put("province_id", doc.get("province_id"));
					// 城市
					enDoc.put("city_id", doc.get("city_id"));
					// 区县
					enDoc.put("county_id", doc.get("county_id"));
					// 英文合并名称
					enDoc.put("merger_name", msZoneCacheBL.getMergerNameEn((String)doc.get("zone_id")));
					// 全景类型
					enDoc.put("panorama_type", doc.get("panorama_type"));
					// 全景ID
					enDoc.put("panorama", doc.get("panorama"));
					// 更新时间
					enDoc.put("update_hhmmss", doc.get("update_hhmmss"));

					String zhTags = FastJsonUtil.castStrNumArray2IntNumArray(doc.get("tags")).toJSONString();
					String enTags = FastJsonUtil.castStrNumArray2IntNumArray(oldEnDoc.get("tags")).toJSONString();

					JSONArray privagteFields = this.getPrivateFields(null);
					for (int i = 0; i < privagteFields.size(); i++) {
						JSONObject privateField = privagteFields.getJSONObject(i);
						JSONObject field_def = privateField.getJSONObject("field_def");
						Integer fieldType = field_def.getInteger("field_type");
						switch (fieldType) {
						case VbConstant.POI_FIELD_TYPE.复选框列表:
							enDoc.put((String)field_def.get("field_name"), doc.get(field_def.get("field_name")));
							break;
						default:
							// 一级分类有变化，需要清空checkbox以外私有字段值
							if (!zhTags.equals(enTags)) {
								enDoc.put((String)field_def.get("field_name"), "");
							}
							break;
						}
					}
					updateDocument = new BasicDBObject();
					updateDocument.append("$set", enDoc);
					// 私有字段维护
					poi_collection.updateOne(keyId, updateDocument);
				}

				return FastJsonUtil.sucess("success");
			}
		}

		return FastJsonUtil.errorWithMsg("LUNA.E0010", param.getString("long_title"));
	}

	@Override
	public JSONObject asyncDeletePoi(String json) {
		JSONObject param = JSONObject.parseObject(json);
		String _id = param.getString("_id");
		BasicDBObject keyId = new BasicDBObject();
		keyId.put("_id", new ObjectId(_id));

		MongoCollection<Document> poi_business_tree_index = mongoConnector.getDBCollection(PoiCommon.MongoTable.TABLE_POI_USED_INDEX);
		Document document = poi_business_tree_index.find(keyId).limit(1).first();
		Boolean isUsing = false;
		if (document != null) {
			JSONArray used_in_business = FastJsonUtil.parse2Array(document.get("used_in_business"));
			if (used_in_business.size() > 0) {
				isUsing = true;
			}
		}
		if (isUsing) {
			return FastJsonUtil.errorWithMsg("LUNA.E0006");
		}
		MongoCollection<Document> poi_collection = mongoConnector.getDBCollection(PoiCommon.MongoTable.TABLE_POI_ZH);
		DeleteResult deleteResult = poi_collection.deleteOne(keyId);
		if (deleteResult.getDeletedCount() > 0) {
			return FastJsonUtil.sucess("success");
		}

		return FastJsonUtil.errorWithMsg("LUNA.E0011", _id);
	}

	/**
	 * 获取公共属性定义(tags定义)
	 * @return
	 */
	private JSONObject getCommonFieldsDef() {

		MsPoiTagCriteria msPoiTagCriteria = new MsPoiTagCriteria();
		// 去除公共字段的标签
		msPoiTagCriteria.createCriteria()
		.andTagIdNotEqualTo(PoiCommon.POI.公共TAGID);
		msPoiTagCriteria.setOrderByClause("tag_level asc, parent_tag_id asc, ds_order asc");
		List<MsPoiTag> lstMsPoiTag = msPoiTagDAO.selectByCriteria(msPoiTagCriteria);

		JSONArray tags = FastJsonUtil.createBlankIntegerJsonArray();
		Map<Integer, List<MsPoiTag>> tagMapList = new LinkedHashMap<Integer, List<MsPoiTag>>();
		if (lstMsPoiTag == null) {
			JSONObject data = new JSONObject();
			data.put("tags", tags);
			return FastJsonUtil.sucess("OK", data);
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
			JSONObject tag = new JSONObject();
			tag.put("tag_id", firstLevel.getTagId());
			tag.put("tag_name", firstLevel.getTagName());
			JSONArray subTags = new JSONArray();
			// 跳过首个
			for (int i = 1; i < lst.size(); i++) {
				JSONObject subTag = new JSONObject();
				subTag.put("tag_id", lst.get(i).getTagId());
				subTag.put("tag_name", lst.get(i).getTagName());
				subTags.add(subTag);
			}
			tag.put("sub_tags", subTags);
			tags.add(tag);
		}

		JSONObject data = new JSONObject();
		data.put("tags_def", tags);
		
		// 获得全景类别
		JSONArray types = new JSONArray();
		
		MsPoiFieldCriteria msPoiFieldCriteria = new MsPoiFieldCriteria();
		MsPoiFieldCriteria.Criteria criteria = msPoiFieldCriteria.createCriteria();
		criteria.andFieldNameEqualTo("panorama_type");
		List<MsPoiField> list = msPoiFieldDAO.selectByCriteria(msPoiFieldCriteria);
		if(list == null) {
			data.put("panorama_type", types);
		} else{
			String type = list.get(0).getExtensionAttrs();
			JSONArray typeArray = JSONArray.parseArray(type != null? type:"[]");
			for(int i = 0; i < typeArray.size(); i++){
				JSONObject panoType = new JSONObject();
				panoType.put("panorama_type_id", i + 1 + "");// 考虑到simpleModel的使用，将取出来的数据转化为string类型，存进去的时候变为Integer
				panoType.put("panorama_type_name", typeArray.getJSONObject(i).get(i + 1 + ""));
				types.add(panoType);
			}
			data.put("panorama_type", types);
		}
		return data;
	}

	private Document getPoiById(String _id, String lang, Boolean defaultInZh) {
		MongoCollection<Document> poi_collection = null;
		// 其他语种的POI(英文)
		if (PoiCommon.POI.EN.equals(lang)) {
			poi_collection = mongoConnector.getDBCollection(PoiCommon.MongoTable.TABLE_POI_EN);
			BasicDBObject keyId = new BasicDBObject();
			keyId.put("_id", new ObjectId(_id));
			Document doc = poi_collection.find(keyId).limit(1).first();
			if(doc != null) {
				return doc;
			}
			if (!defaultInZh) {
				return null;
			}
		}
		// 中文版POI
		poi_collection = mongoConnector.getDBCollection(PoiCommon.MongoTable.TABLE_POI_ZH);
		BasicDBObject keyId = new BasicDBObject();
		keyId.put("_id", new ObjectId(_id));
		return poi_collection.find(keyId).limit(1).first(); 
	}

	private JSONObject getCommmFieldVal(Document docPoi) {
		JSONObject commonFieldsVal = new JSONObject();
		String _id = docPoi.getObjectId("_id").toString();
		commonFieldsVal.put("_id" ,docPoi.getObjectId("_id".toString()));
		commonFieldsVal.put("preview_url", ServiceConfig.getString(ServiceConfig.MS_WEB_URL) + "/poi/" + _id);
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
		JSONArray tags_values = FastJsonUtil.createBlankIntegerJsonArray();
		Object poiTags = docPoi.get("tags");
		if (poiTags != null) {
			tags_values = FastJsonUtil.castStrNumArray2IntNumArray(
					FastJsonUtil.parse2Array(poiTags));
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
		JSONObject lnglat = FastJsonUtil.parse2Json(docPoi.get("lnglat")); 
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
		
		// 8.全景类型
		if(docPoi.containsKey("panorama_type")) {
			commonFieldsVal.put("panorama_type", docPoi.getInteger("panorama_type")+"");
		} else {
			commonFieldsVal.put("panorama_type", "2");
		}
		
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
		return FastJsonUtil.sucess("success", data);
	}

	@Override
	public JSONObject initEditPoi(String json) {
		JSONObject param = JSONObject.parseObject(json);

		// 语言种类
		String lang = param.getString("lang");

		String _id = param.getString("_id");
		Document docPoi = this.getPoiById(_id, lang, Boolean.TRUE);
		if (docPoi == null) {
			return FastJsonUtil.errorWithMsg("LUNA.E0012", "POI["+_id+"]");
		}

		JSONObject data = new JSONObject();
		data.put("common_fields_def", this.getCommonFieldsDef());
		data.put("common_fields_val", this.getCommmFieldVal(docPoi));

		data.put("private_fields", this.getPrivateFields(docPoi));
		return FastJsonUtil.sucess("success", data);
	}

	private JSONArray getPrivateFields(Document docPoi) {
		// 查找私有字段属性
		MsTagFieldParameter msTagFieldParameter = new MsTagFieldParameter();
		msTagFieldParameter.setTagId(PoiCommon.POI.公共TAGID);
		List<MsTagFieldResult> lstMsTagFieldResult = msPoiFieldDAO.selectFieldTags(msTagFieldParameter);
		JSONArray privateFields = new JSONArray();

		Map<String, StringBuffer> tagIdMaps = new HashMap<String, StringBuffer>();

		for (MsTagFieldResult msTagFieldResult : lstMsTagFieldResult) {
			
			// 没有私有字段的一级分类
			if (msTagFieldResult.getFieldName() == null) {
				continue;
			}

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
			
			// 没有私有字段的一级分类
			if (msTagFieldResult.getFieldName() == null) {
				continue;
			}
			fieldSet.add(msTagFieldResult.getFieldName());

			JSONObject field_def = new JSONObject();
			field_def.put("field_name", msTagFieldResult.getFieldName());
			field_def.put("tag_id", tagIdMaps.get(msTagFieldResult.getFieldName()).toString());
			field_def.put("tag_name", msTagFieldResult.getTagName());
			field_def.put("field_show_name", msTagFieldResult.getFieldShowName());
			field_def.put("display_order", msTagFieldResult.getDisplayOrder());
			field_def.put("field_type", msTagFieldResult.getFieldType());
			field_def.put("field_size", msTagFieldResult.getFieldSize());
			field_def.put("placeholder", CharactorUtil.nullToBlank(msTagFieldResult.getPlaceholder()));
			if (msTagFieldResult.getFieldType().intValue() == VbConstant.POI_FIELD_TYPE.复选框列表) {
				field_def.put("extension_attrs", JSONArray.parseArray(msTagFieldResult.getExtensionAttrs()));
			} else {
				field_def.put("extension_attrs", CharactorUtil.nullToBlank(msTagFieldResult.getExtensionAttrs()));
			}
			JSONObject field_val = new JSONObject();
			if (docPoi != null) {
				// 新增加的私有字段但是DB中还没有值
				if (docPoi.get(msTagFieldResult.getFieldName()) == null) {
					switch(msTagFieldResult.getFieldType()) {
						case VbConstant.POI_FIELD_TYPE.文本框:
						case VbConstant.POI_FIELD_TYPE.文本域:
						case VbConstant.POI_FIELD_TYPE.图片:
						case VbConstant.POI_FIELD_TYPE.视频:
						case VbConstant.POI_FIELD_TYPE.音频:
							field_val.put("value", "");
							break;
						case VbConstant.POI_FIELD_TYPE.复选框列表:
							field_val.put("value", new int[]{});
							break;
						default:
							break;
					}
				} else{
					field_val.put("value", docPoi.get(msTagFieldResult.getFieldName()));
				}
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

			JSONObject field = new JSONObject();

			field.put("field_def", field_def);
			field.put("field_val", field_val);
			privateFields.add(field);
		}
		return privateFields;
	}

	private JSONArray getPrivateFieldsForTemplete() {
		// 查找私有字段属性
		MsTagFieldParameter msTagFieldParameter = new MsTagFieldParameter();
		msTagFieldParameter.setTagId(PoiCommon.POI.公共TAGID);
		List<MsTagFieldResult> lstMsTagFieldResult = msPoiFieldDAO.selectFieldTags(msTagFieldParameter);
		JSONArray privateFields = new JSONArray();
		for (MsTagFieldResult msTagFieldResult : lstMsTagFieldResult) {

			JSONObject field_def = new JSONObject();

			// 没有私有字段的一级分类
			if (msTagFieldResult.getFieldName() == null) {
				field_def.put("tag_id", msTagFieldResult.getTagId());
				field_def.put("tag_name", msTagFieldResult.getTagName());
				JSONObject field = new JSONObject();
				field.put("tag_without_field", field_def);
				privateFields.add(field);
				continue;
			}

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
				field_def.put("extension_attrs", JSONArray.parseArray(msTagFieldResult.getExtensionAttrs()));
			} else {
				field_def.put("extension_attrs", CharactorUtil.nullToBlank(msTagFieldResult.getExtensionAttrs()));
			}

			JSONObject field = new JSONObject();

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
		return FastJsonUtil.sucess("OK", data);
	}

	@Override
	public JSONObject savePois(String json) {
		JSONObject param = JSONObject.parseObject(json);
		JSONArray saveErrors = new JSONArray();
		JSONArray no_check_errors = param.getJSONArray("no_check_errors");
		for (int i = 0; i < no_check_errors.size(); i++) {
			JSONObject poiJson = no_check_errors.getJSONObject(i);
			try {
				Document doc = this.json2BsonForInsertOrUpdate(poiJson, Boolean.TRUE, Boolean.FALSE);
				MongoCollection<Document> poi_collection = mongoConnector.getDBCollection(PoiCommon.MongoTable.TABLE_POI_ZH);

				boolean inserted = false;
				Document document = null;
				// 保证插入数据，在判重策略下是唯一的
				synchronized (MongoConnector.class) {
					document = MongoUtility.findOnePoi(poi_collection, poiJson);// 返回相似POI
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
		return FastJsonUtil.sucess("OK", data);
	}

	@Override
	public JSONObject initFixPoi(String json) {
		JSONObject param = JSONObject.parseObject(json);

		Document docPoi = this.json2BsonForInsertOrUpdate(param, Boolean.TRUE, Boolean.TRUE);

		JSONObject data = new JSONObject();
		data.put("common_fields_def", this.getCommonFieldsDef());
		data.put("common_fields_val", this.getCommmFieldVal(docPoi));

		data.put("private_fields", this.getPrivateFields(docPoi));
		return FastJsonUtil.sucess("success", data);
	}

	private JSONArray convert2JsonArray(JSONObject param, String key) {
		String val = null;
		if (param.containsKey(key)) {
			val = param.getString(key);
		}
		return convert2JsonArray(val);
	}

	private JSONArray convert2JsonArray(String val) {
		if (val == null) {
			return new JSONArray();
		}
		if (val.startsWith("[")) {
			return JSONArray.parseArray(val);
		}
		return JSONArray.parseArray("["+val+"]");
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

	@Override
	public JSONObject getTagsDef(String json) {
		return FastJsonUtil.sucess("OK", this.getCommonFieldsDef());
	}

	@Override
	public JSONObject checkPoiCanBeDeleteOrNot(String json) {
		JSONObject param = JSONObject.parseObject(json);
		String _id = param.getString("_id");
		BasicDBObject keyId = new BasicDBObject();
		keyId.put("_id", new ObjectId(_id));

		MongoCollection<Document> poi_business_tree_index = mongoConnector.getDBCollection(PoiCommon.MongoTable.TABLE_POI_USED_INDEX);
		Document document = poi_business_tree_index.find(keyId).limit(1).first();
		Boolean isUsing = false;
		if (document != null) {
			JSONArray used_in_business = FastJsonUtil.parse2Array(document.get("used_in_business"));
			if (used_in_business.size() > 0) {
				isUsing = true;
			}
		}
		if (isUsing) {
			return FastJsonUtil.errorWithMsg("LUNA.E0006");
		}
		return FastJsonUtil.sucess(MsLunaMessage.getInstance().getMessage("LUNA.I0001"));
	}

	@Override
	public JSONObject initPoiPreview(String json) {
		JSONObject param = JSONObject.parseObject(json);

		String _id = param.getString("_id");
		String lang = param.getString("lang");
		Document docPoi = this.getPoiById(_id, lang, Boolean.TRUE);
		if (docPoi == null) {
			return FastJsonUtil.errorWithMsg("LUNA.E0012", "POI["+_id+"]");
		}

		JSONObject data = new JSONObject();
		String long_title = docPoi.getString("long_title");// poi 名称
		
		JSONObject lnglat = FastJsonUtil.parse2Json(docPoi.get("lnglat")); 
		JSONArray coordinates = lnglat.getJSONArray("coordinates");
		Double lng = coordinates.getDouble(0);// 经度
		Double lat = coordinates.getDouble(1);// 纬度
		
		String short_title = docPoi.getString("short_title");
		String brief_introduction = docPoi.getString("brief_introduction");
		String thumbnail = docPoi.getString("thumbnail");
		String audio = docPoi.getString("audio");
		String video = docPoi.getString("video");
		String panorama = docPoi.getString("panorama");
		Integer panorama_type;
		if(docPoi.containsKey("panorama_type")){
			panorama_type = docPoi.getInteger("panorama_type");
		} else {
			// TODO 默认数值在common中定义
			panorama_type = 2; // 默认为专辑 
		}
		
		data.put("short_title", short_title);
		data.put("long_title", long_title);
		data.put("lng", lng);
		data.put("lat", lat);
		data.put("brief_introduction", brief_introduction);
		data.put("thumbnail", thumbnail);
		data.put("video", video);
		data.put("audio", audio);
		data.put("panorama", panorama);
		data.put("panorama_type", panorama_type);
		
		return FastJsonUtil.sucess("success", data);
	}
}
