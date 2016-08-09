package ms.luna.biz.dao.custom;


import com.alibaba.fastjson.JSONObject;
import org.bson.Document;

/**
 * Created by greek on 16/7/28.
 */
public interface MsFarmPageDAO {

    String COLLECTION_NAME = "greektest";

    void insertPage(JSONObject data);

    void updatePage(JSONObject data, Integer appId);

    Document selectPageByAppId(Integer app_id);

}
