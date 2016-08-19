package ms.luna.biz.dao.custom;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.jar.JarException;

/**
 * Created by greek on 16/8/19.
 */
public interface MsRouteCollectionDAO {

    String ROUTE_COLLECTION = "route_collection";
    String POI_ROUTE_COLLECTION = "poi_route_collection";
    String POI_COLLECTION = "poi_collection";

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
     * @param document 线路数据
     * @param routeId 线路id
     * @param luna_name 用户名称
     */
    void saveRoute(Document document, Integer routeId, String luna_name);

    /**
     * 创建线路
     *
     * @param routeId 线路id
     * @param luna_name 用户名
     */
    void createRoute(Integer routeId, String luna_name);

    /**
     * 删除线路
     *
     * @param routeId
     */
    void deleteRoute(Integer routeId);

    /**
     * 根据poi id列表获取信息
     *
     * @param c_list
     * @return
     */
    JSONObject getPoiForRoute(List<ObjectId> c_list);
}
