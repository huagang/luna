package ms.luna.biz.dao.custom;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import ms.luna.biz.dao.custom.model.MsShowPage;
import org.bson.Document;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * Created by greek on 16/7/28.
 */
public class MsFarmPageDAOImpl extends MongoBaseDAO implements MsFarmPageDAO{

    MongoCollection<Document> greektestCollection;

    @PostConstruct
    public void init() {
        greektestCollection = mongoConnector.getDBCollection(COLLECTION_NAME);
    }

    @Override
    public void insertPage(JSONObject data) {
        greektestCollection.insertOne(convertJson2Document(null, data));
    }

    @Override
    public void updatePage(JSONObject data) {
        Integer app_id = data.getInteger(MsShowPageDAO.FIELD_APP_ID);
        Document filter = new Document().append(MsShowPageDAO.FIELD_APP_ID, app_id);
        Document document = greektestCollection.find(filter).limit(1).first();
        greektestCollection.updateOne(filter, new Document().append("$set", convertJson2Document(document, data)));
    }

    @Override
    public Document selectPageByAppId(Integer app_id) {
        Document filter = new Document().append(MsShowPageDAO.FIELD_APP_ID, app_id);
        return greektestCollection.find(filter).limit(1).first();
    }

    /**
     * JSON数据转化为Document
     *
     * @param document 需要更新的数据ß
     * @param data 更新数据
     * @return Document
     */
    private Document convertJson2Document(Document document, JSONObject data) {
        if(document == null) {
            document = new Document();
        }
        Set<String> keys = data.keySet();
        for(String key : keys) {
            document.put(key, data.get(key));
        }
        return document;
    }

}
