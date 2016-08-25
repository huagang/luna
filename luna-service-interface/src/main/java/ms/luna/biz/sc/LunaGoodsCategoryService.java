package ms.luna.biz.sc;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by SDLL18 on 16/8/23.
 */
public interface LunaGoodsCategoryService {

    JSONObject getCategories();

    JSONObject getCategories(JSONObject jsonObject);

    JSONObject searchCategories(JSONObject jsonObject);

    JSONObject searchRootCategories(JSONObject jsonObject);

    JSONObject createCategory(JSONObject jsonObject);

    JSONObject deleteCategory(JSONObject jsonObject);

    JSONObject updateCategory(JSONObject jsonObject);
}
