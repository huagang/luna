package ms.luna.biz.dao.custom;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import ms.luna.biz.table.MsFarmFieldTable;
import ms.luna.biz.table.MsShowAppTable;
import ms.luna.biz.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
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
    public void insertPage(Document document, String lunaName) {
        document.put(MsFarmFieldTable.CREATE_TIME, new Date());
        document.put(MsFarmFieldTable.UPDATE_TIME, new Date());
        document.put(MsFarmFieldTable.UPDATE_USER, lunaName);
        collection.insertOne(document);
    }

    @Override
    public void updatePage(Document document, Integer appId, String lunaName) {
        document.put(MsFarmFieldTable.UPDATE_TIME, new Date());
        document.put(MsFarmFieldTable.UPDATE_USER, lunaName);
        Document filter = new Document().append(MsShowPageDAO.FIELD_APP_ID, appId);
        collection.updateOne(filter, new Document().append("$set", document));
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

    @Override
    public JSONObject getPageInfo(Document document, List<String> fields) {
        JSONObject result = new JSONObject();
        for(String field : fields) {
            result.put(field, document.containsKey(field)? document.get(field) : null);
        }
        return result;
    }

}
