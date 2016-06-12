package ms.biz.common;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

import net.sf.json.JSONObject;

public final class MongoUtility {

	static final Double maxDistance = 0.05*0.621/3963.192;
//	/**
//	 * 
//	 * @param param
//	 * @return
//	 */
//	public static Document simpleJson2SimpleBson(JSONObject param) {
//		@SuppressWarnings("unchecked")
//		Iterator<String> itr = param.keys();
//		Document doc = new Document();
//
//		// 构建经纬度索引
//		// latlng : { type: "Point", coordinates: [ -73.88, 40.78 ] }
//		List<Double> latlngArray = new ArrayList<Double>();
//		latlngArray.add(param.getDouble("lng"));
//		latlngArray.add(param.getDouble("lat"));
//		BasicDBObject latlng = new BasicDBObject();
//		latlng.append("type", "Point")
//		.append("coordinates", latlngArray);
//		doc.put("latlng", latlng);
//
//		while (itr.hasNext()) {
//			String key = itr.next();
//			if (key.matches("lat|lng|_id")) {
//				continue;
//			}
//			doc.put(key, param.get(key));
//		}
//		Date date = new Date();
//		doc.put("regist_hhmmss", date);
//		doc.put("update_hhmmss", date);
//		MsUser msUser = (MsUser)AuthenticatedUserHolder.get();
//		if (msUser != null) {
//			doc.put("updated_by_unique_id", msUser.getUniqueId());
//		}
//		return doc;
//	}

	/**
	 * 判断POI重复的策略
	 * @param collection
	 * @param param
	 * @return
	 */
	public static boolean isPoiExsit(MongoCollection<Document> collection, JSONObject param) {
		String long_title = param.getString("long_title");
		String short_title = param.getString("short_title");
		double lng = param.getDouble("lng");
		double lat = param.getDouble("lat");
		MongoCursor<Document> mongoCursor = collection.find(
				Filters.and(
						Filters.eq("long_title", long_title)
						,Filters.eq("short_title", short_title)
						,Filters.geoWithinCenter("lnglat", lng, lat, maxDistance)
				)).limit(1).iterator();
		return mongoCursor.hasNext();
	}

	/**
	 * 查询长别名相同，并且距离相差不超过50米的POI
	 * @param collection
	 * @param param
	 * @return
	 */
	public static Document findOnePoi(MongoCollection<Document> collection, JSONObject param) {
		String long_title = param.getString("long_title");
		String short_title = param.getString("short_title");
		double lng = param.getDouble("lng");
		double lat = param.getDouble("lat");
		MongoCursor<Document> mongoCursor = collection.find(
				Filters.and(
						Filters.eq("long_title", long_title)
						,Filters.eq("short_title", short_title)
						,Filters.geoWithinCenter("lnglat", lng, lat, maxDistance)
				)).limit(1).iterator();
		if (mongoCursor.hasNext()) {
			return mongoCursor.next();
		}
		return null;
	}
}
