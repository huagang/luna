package ms.luna.biz.dao.custom;


import com.alibaba.fastjson.JSONObject;
import org.bson.Document;

/**
 * Created by greek on 16/7/28.
 */
public interface MsFarmPageDAO {

    String COLLECTION_NAME = "greektest";

    void insertPage(Document data, String lunaName);

    void updatePage(Document data, Integer appId, String lunaName);

    void deletePage(Integer appId);

    Document selectPageByAppId(Integer app_id);

}
