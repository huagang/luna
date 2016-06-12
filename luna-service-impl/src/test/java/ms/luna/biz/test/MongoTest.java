package ms.luna.biz.test;

import java.util.Date;

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
		Bson query = Filters.eq("_id", "57320e91781f0f2908232b0d");
		testCollection.find(query).forEach(new Block<Document>() {

			@Override
			public void apply(Document t) {
				System.out.println(t);
			}
			
		});
	}

}
