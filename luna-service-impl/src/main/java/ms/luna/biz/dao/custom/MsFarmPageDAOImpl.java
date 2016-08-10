package ms.luna.biz.dao.custom;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * Created by greek on 16/7/28.
 */
@Repository("MsFarmPageDAO")
public class MsFarmPageDAOImpl extends MongoBaseDAO implements MsFarmPageDAO{

    MongoCollection<Document> collection;

    @PostConstruct
    public void init() {
        collection = mongoConnector.getDBCollection(COLLECTION_NAME);
    }

    @Override
    public void insertPage(Document data) {
        collection.insertOne(data);
    }

    @Override
    public void updatePage(Document data, Integer appId) {
        Document filter = new Document().append(MsShowPageDAO.FIELD_APP_ID, appId);
        collection.updateOne(filter, new Document().append("$set", data));
    }

    @Override
    public void deletePage(Integer appId) {
        collection.deleteMany(Filters.eq(MsShowPageDAO.FIELD_APP_ID, appId));
    }

    @Override
    public Document selectPageByAppId(Integer app_id) {
        Document filter = new Document().append(MsShowPageDAO.FIELD_APP_ID, app_id);
        return collection.find(filter).limit(1).first();
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
