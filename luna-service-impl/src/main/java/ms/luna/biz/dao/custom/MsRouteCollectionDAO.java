package ms.luna.biz.dao.custom;

import com.alibaba.fastjson.JSONObject;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Set;

/**
 * Created: by greek on 16/8/19.
 */
public interface MsRouteCollectionDAO {

    String ROUTE_COLLECTION = "route_collection";
    String POI_ROUTE_INDEX = "poi_business_tree_index";
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
    JSONObject getPoiForRoute(Set<ObjectId> c_list);

    /**
     * 更新线路索引表
     *
     * @param oldPoiIds 旧线路Poi id集合
     * @param newPoiIds 新线路Poi id集合
     */
    void updateRouteIndex(Set<ObjectId> oldPoiIds, Set<ObjectId> newPoiIds, Integer routeId);

    /**
     * 删除线路索引数据
     *
     * @param routeId 线路id
     */
    void deleteRouteIndex(Integer routeId);
}
