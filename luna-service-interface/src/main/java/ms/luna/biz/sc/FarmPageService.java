package ms.luna.biz.sc;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by greek on 16/7/25.
 */
public interface FarmPageService {

    JSONObject getPageDefAndInfo(Integer appId);

    JSONObject updatePage(String json, Integer appId, String lunaName);

    JSONObject delPage(Integer appId);

    JSONObject getFarmFields();

    JSONObject loadPage(Integer appId);

    JSONObject previewPage(Integer appId);

    JSONObject publishPage(String json);

    JSONObject getPageInfo(Integer appId);

}
