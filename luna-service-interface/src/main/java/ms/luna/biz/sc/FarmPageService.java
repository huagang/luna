package ms.luna.biz.sc;

import com.alibaba.fastjson.JSONObject;

/**
 * Created: by greek on 16/7/25.
 */
public interface FarmPageService {

    // 获取页面字段定义和数据
    JSONObject getPageDefAndInfo(Integer appId);

    // 更新页面数据
    JSONObject updatePage(String json, Integer appId, String lunaName);

    // 删除页面
    JSONObject delPage(Integer appId);

    // 获取页面字段定义
    JSONObject getFarmFields();

    // 获取页面数据
    JSONObject getPageInfo(Integer appId);

    // 预览
    JSONObject previewPage(Integer appId);

    // 发布
    JSONObject publishPage(String json);


}
