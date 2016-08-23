package ms.luna.biz.dao.custom;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.UpdateOptions;
import ms.luna.biz.dao.model.MsRoute;
import ms.luna.biz.table.MsRouteTable;
import ms.luna.biz.util.FastJsonUtil;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created: by greek on 16/8/19.
 */
@Repository("msRouteCollectionDAO")
public class MsRouteCollectionDAOImpl extends MongoBaseDAO implements MsRouteCollectionDAO {

    MongoCollection<Document> routeCollection;

    MongoCollection<Document> poiCollection;

    MongoCollection<Document> poiRouteIndex;

    @PostConstruct
    public void init() {
        routeCollection = mongoConnector.getDBCollection(ROUTE_COLLECTION);
        poiCollection = mongoConnector.getDBCollection(MsRouteCollectionDAO.POI_COLLECTION);
        poiRouteIndex = mongoConnector.getDBCollection(MsRouteCollectionDAO.POI_ROUTE_INDEX);
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
    public JSONObject getPoiForRoute(Set<ObjectId> c_list) {
        String[] includes = new String[]{MsRouteTable.FIELD_LONG_TITLE, MsRouteTable.FIELD_CATEGORY};
        Document in = new Document().append("_id", new Document().append("$in", c_list));
        MongoCursor<Document> cursor = poiCollection.find(in).projection(Projections.include(includes)).iterator();
        JSONObject result = new JSONObject();
        while(cursor.hasNext()) {
            Document doc = cursor.next();
            JSONObject poi = new JSONObject();
            poi.put(MsRouteTable.FIELD_LONG_TITLE, doc.getString(MsRouteTable.FIELD_LONG_TITLE));
            JSONArray array = FastJsonUtil.parse2Array(doc.get(MsRouteTable.FIELD_CATEGORY));
            poi.put("category_id", array.getInteger(0));
            result.put(doc.getObjectId("_id").toString(), poi);
        }
        return result;
    }

    @Override
    public void updateRouteIndex(Set<ObjectId> oldPoiIds, Set<ObjectId> newPoiIds, Integer routeId) {
        // 求交集
        Set<ObjectId> intersection = getIntersection(oldPoiIds, newPoiIds);
        // 删除的索引id
        Set<ObjectId> removedPoiIds = oldPoiIds;
        removedPoiIds.removeAll(intersection);
        // 新增的索引id
        Set<ObjectId> addPoiIds = newPoiIds;
        addPoiIds.removeAll(intersection);
        Document filter = new Document();
        Document update = new Document();
        // 索引表中删除id
        if(removedPoiIds.size() != 0) {
            filter.append("_id", new Document().append("$in", removedPoiIds));
            update.append("$pull", new Document(MsRouteTable.USED_IN_ROUTE, routeId));
            poiRouteIndex.updateMany(filter, update);
        }
        // 索引表中增加id
        if(addPoiIds.size() != 0) {
            for(ObjectId _id : addPoiIds) {
                filter = new Document().append("_id", _id);
                update = new Document().append("$addToSet", new Document(MsRouteTable.USED_IN_ROUTE, routeId));
                poiRouteIndex.updateOne(filter, update, new UpdateOptions().upsert(true));
            }
        }
    }

    @Override
    public void deleteRouteIndex(Integer routeId) {
        Document update = new Document().append("$pull", new Document(MsRouteTable.USED_IN_ROUTE, routeId));
        poiRouteIndex.updateMany(new Document(), update);
    }

    private Set<ObjectId> getIntersection(Set<ObjectId> oldPoiIds, Set<ObjectId> newPoiIds) {
        Set<ObjectId> interserction = new HashSet<>();
        for(ObjectId id : oldPoiIds) {
            if (newPoiIds.contains(id)) {
                interserction.add(id);
            }
        }
        return interserction;
    }


}
