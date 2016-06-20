package ms.biz.common;

import org.bson.BasicBSONObject;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.BasicBSONList;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

import ms.luna.biz.util.FastJsonUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public final class MongoUtility {

	static final Double maxDistance = 0.05*0.621/3963.192;

	/**
	 * 判断POI重复的策略
	 * @param collection
	 * @param param
	 * @return
	 */
	public static boolean isPoiExsit(MongoCollection<Document> collection, JSONObject param) {
		return findOnePoi(collection, param) != null;
	}

	/**
	 * 查询长别名相同，并且距离相差不超过50米的POI
	 * @param collection
	 * @param param
	 * @return
	 */
	public static Document findOnePoi(MongoCollection<Document> collection, JSONObject param) {
		BasicDBObject condition = new BasicDBObject();
		// 名称
		String long_title = param.getString("long_title");
		condition.append("long_title", long_title);

		// 别名
		String short_title = param.getString("short_title");
		condition.append("short_title", short_title);

		// 一级分类、须完全匹配
		// tags: {$all: [7]},
		JSONArray tags = param.getJSONArray("tags");
		tags = FastJsonUtil.castStrNumArray2IntNumArray(tags);
		condition.append("tags", new BasicBSONObject("$all", tags));

		// 存在二级分类的话，则作为检索条件,如果不存在的话，认为已经匹配
		// $or:[{sub_tag:54},{sub_tag:{$exists:false}}]}
		BasicBSONList subTags = new BasicBSONList();
		Integer subTag = param.getInteger("subTag");
		subTags.add(new BasicBSONObject("sub_tag",
				new BasicBSONObject("$exists", false)));
		subTags.add(new BasicBSONObject("sub_tag", subTag));
		condition.append("$or", subTags);

		// 经度
		double lng = param.getDouble("lng");
		// 纬度
		double lat = param.getDouble("lat");

		// 检索:中文名称、中文别名、一级分类、以及二级分类(可能不存在)相同,并且经纬度距离相差不超过50米的POI数据
		Bson filter = Filters.and(
				condition
				,Filters.geoWithinCenter("lnglat", lng, lat, maxDistance)
				);
		MongoCursor<Document> mongoCursor = collection.find(filter).limit(1).iterator();
		if (mongoCursor.hasNext()) {
			return mongoCursor.next();
		}
		return null;
	}
}
