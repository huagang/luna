package ms.luna.biz.dao.custom;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

/**
 * Created by greek on 16/8/19.
 */
@Repository("msRouteCollectionDAO")
public class MsRouteCollectionDAOImpl extends MongoBaseDAO implements MsRouteCollectionDAO {

    MongoCollection<Document> routeCollection;

    @PostConstruct
    public void init() {
        routeCollection = mongoConnector.getDBCollection(ROUTE_COLLECTION);
    }


    @Override
    public Document getRoute(Integer routeId) {
        return null;
    }

    @Override
    public Document saveRoute(Document document) {
        return null;
    }
}
