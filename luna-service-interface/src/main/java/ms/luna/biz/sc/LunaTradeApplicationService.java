package ms.luna.biz.sc;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by SDLL18 on 16/8/25.
 */
public interface LunaTradeApplicationService {

    JSONObject createApplication(JSONObject jsonObject);

    JSONObject recreateApplication(JSONObject jsonObject);

    JSONObject getApplication(JSONObject jsonObject);

    JSONObject getApplicationList(int offset, int limit);

    JSONObject getApplicationList();

    JSONObject updateApplication(JSONObject jsonObject);

    JSONObject checkApplication(JSONObject jsonObject);

    JSONObject deleteApplication(JSONObject jsonObject);

    JSONObject getApplicationStatusByApplicationId(JSONObject jsonObject);

    JSONObject getApplicationStatus(JSONObject jsonObject);
}
