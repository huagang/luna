package ms.luna.biz.dao.custom;


import com.alibaba.fastjson.JSONObject;
import org.bson.Document;

import java.util.List;

/**
 * Created by greek on 16/7/28.
 */
public interface MsFarmPageDAO {

    String COLLECTION_NAME = "greektest";

    String POI_INFO = "poi_info";

    String FACILITY = "facility";

    void insertPage(Document data, String lunaName);

    void updatePage(Document data, Integer appId, String lunaName);

    void deletePage(Integer appId);

    Document selectPageByAppId(Integer app_id);

    JSONObject getPageInfo(Document document, List<String> fields);

}
