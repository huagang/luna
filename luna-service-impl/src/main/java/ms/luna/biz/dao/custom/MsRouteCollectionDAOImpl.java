package ms.luna.biz.dao.custom;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Projections;
import ms.luna.biz.dao.model.MsRoute;
import ms.luna.biz.table.MsRouteTable;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

/**
 * Created by greek on 16/8/19.
 */
@Repository("msRouteCollectionDAO")
public class MsRouteCollectionDAOImpl extends MongoBaseDAO implements MsRouteCollectionDAO {

    MongoCollection<Document> routeCollection;

    MongoCollection<Document> poiCollection;

    @PostConstruct
    public void init() {
        routeCollection = mongoConnector.getDBCollection(ROUTE_COLLECTION);
        poiCollection = mongoConnector.getDBCollection(MsRouteCollectionDAO.POI_COLLECTION);
    }

    @Override
    public void createRoute(Integer routeId, String luna_name) {
        Document document = new Document();
        document.put(MsRouteTable.FIELD_CREATE_TIME, new Date());
        document.put(MsRouteTable.FIELD_UPDATE_TIME, new Date());
        document.put(MsRouteTable.FIELD_UPDATE_USER, luna_name);
        document.put(MsRouteTable.FIELD_ROUTE_ID, routeId);
        document.put(MsRouteTable.C_LIST, new JSONArray());
        routeCollection.insertOne(document);
    }

    @Override
    public Document getRoute(Integer routeId) {
        Document filter = new Document().append(MsRouteTable.FIELD_ROUTE_ID, routeId);
        Document result = routeCollection.find(filter).first();
        return result;
    }

    @Override
    public void saveRoute(Document document, Integer routeId, String luna_name) {
        document.put(MsRouteTable.FIELD_UPDATE_USER, luna_name);
        document.put(MsRouteTable.FIELD_UPDATE_TIME, new Date());
        Document filter = new Document().append(MsRouteTable.FIELD_ROUTE_ID, routeId);
        routeCollection.updateOne(filter, new Document().append("$set", document));
    }

    @Override
    public void deleteRoute(Integer routeId) {
        Document filter = new Document().append(MsRouteTable.FIELD_ROUTE_ID, routeId);
        routeCollection.deleteOne(filter);
    }

    @Override
    public JSONObject getPoiForRoute(List<ObjectId> c_list) {
        String[] includes = new String[]{MsRouteTable.FIELD_LONG_TITLE};
        Document in = new Document().append("_id", new Document().append("$in", c_list));
        MongoCursor<Document> cursor = poiCollection.find(in).projection(Projections.include(includes)).iterator();
        JSONObject result = new JSONObject();
        while(cursor.hasNext()) {
            Document doc = cursor.next();
            JSONObject poi = new JSONObject();
            poi.put(MsRouteTable.FIELD_LONG_TITLE, doc.getString(MsRouteTable.FIELD_LONG_TITLE));
            result.put(doc.getObjectId("_id").toString(), poi);
        }
        return result;
    }
}
