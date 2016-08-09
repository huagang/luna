package ms.luna.biz.sc;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by greek on 16/7/25.
 */
public interface FarmPageService {

    JSONObject getPageInfo(Integer appId);

    JSONObject updatePage(String json, Integer appId);

    JSONObject delPage(Integer appId);

    JSONObject getFarmFields();

    JSONObject loadPage(Integer appId);

    JSONObject previewPage(Integer appId);

    JSONObject publishPage(Integer appId);

}
