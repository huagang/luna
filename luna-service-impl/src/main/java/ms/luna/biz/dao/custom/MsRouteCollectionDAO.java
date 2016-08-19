package ms.luna.biz.dao.custom;

import org.bson.Document;

/**
 * Created by greek on 16/8/19.
 */
public interface MsRouteCollectionDAO {

    String ROUTE_COLLECTION = "route_collection";
    String POI_ROUTE_COLLECTION = "poi_route_collection";


    /**
     * 获取线路
     *
     * @param routeId
     * @return
     */
    Document getRoute(Integer routeId);

    /**
     * 保存线路
     *
     * @param document
     * @return
     */
    Document saveRoute(Document document);

}
