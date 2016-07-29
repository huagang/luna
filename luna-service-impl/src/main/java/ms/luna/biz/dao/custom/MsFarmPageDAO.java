package ms.luna.biz.dao.custom;


import com.alibaba.fastjson.JSONObject;

/**
 * Created by greek on 16/7/28.
 */
public interface MsFarmPageDAO {

    String COLLECTION_NAME = "greektest";

    /**
     * 检查mongoDB微景展ID是否存在
     *
     * @param app_id 微景展ID
     * @return
     */
    boolean isPageExist(Integer app_id);

    void insertPage(JSONObject data);

    void updatePage(JSONObject data);

}
