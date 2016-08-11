package ms.luna.biz.test;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import ms.luna.biz.cons.LunaRoleCategoryExtra;
import ms.luna.biz.dao.custom.model.LunaUserRole;
import ms.luna.biz.table.LunaUserRoleTable;
import ms.luna.common.LunaUserSession;
import org.bson.BsonDateTime;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

import ms.biz.common.MongoConnector;
import ms.luna.biz.util.DateUtil;

public class MongoTest {

	public static void main(String[] args) {
		
		MongoConnector mongoConnector = new MongoConnector("mongo.properties");
		
		MongoCollection<Document> testCollection = mongoConnector.getDBCollection("show_page");
//		Document document = new Document();
//		document.append("ctime", new BsonDateTime(System.currentTimeMillis()));
//		testCollection.insertOne(document);
//		Bson query = Filters.eq("_id", new ObjectId("57206a16a6a5551473fe8088"));
		Bson query = Filters.eq("_id", "5756367f5971a164164fcc51");

		testCollection.find(query).forEach(new Block<Document>() {

			@Override
			public void apply(Document document) {
				System.out.println(document);
			}

		});
	}

	private static Document userRole2Document(LunaUserRole lunaUserRole) {
		Document document = new Document();
		document.put(LunaUserRoleTable.FIELD_ROLE_ID, lunaUserRole.getRoleId());
		document.put(LunaUserRoleTable.FIELD_EXTRA, lunaUserRole.getExtra());
		document.put(LunaUserRoleTable.FIELD_UPDATE_TIME, new BsonDateTime(System.currentTimeMillis()));
		return document;

	}

	public static boolean hasBusinessAuth(Map<String, Object> extra, int businessId) {

		if(! extra.get("type").toString().equals(LunaRoleCategoryExtra.TYPE_BUSINESS)) {
			return false;
		}

		Object value = extra.get("value");
		if(value == null) {
			return false;
		}

		if(value instanceof List<?>) {
			System.out.println("type List");
			List<Integer> businessList = (List<Integer>) value;
			if(businessList.contains(businessId) || businessList.get(0) == 0) {
				return true;
			}

		} else if(value instanceof JSONArray) {
			System.out.println("type JSONArray");
			JSONArray jsonArray = (JSONArray) value;
			if(jsonArray.contains(businessId) || jsonArray.getIntValue(0) == 0) {
				return true;
			}
		}

		return false;
	}

}
