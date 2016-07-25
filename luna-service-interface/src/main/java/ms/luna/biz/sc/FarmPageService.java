package ms.luna.biz.sc;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by greek on 16/7/25.
 */
public interface FarmPageService {

    JSONObject initPage(String json);

    JSONObject editPage(String json);

    JSONObject delPage(Integer app_id);

    JSONObject loadPage(String json);

    JSONObject previewPage(String json);

}
