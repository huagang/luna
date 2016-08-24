package ms.luna.biz.sc;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by SDLL18 on 16/8/23.
 */
public interface LunaGoodsCategoryService {

    JSONObject getCategories();

    JSONObject createCategory(JSONObject jsonObject);

    JSONObject deleteCategory(JSONObject jsonObject);

    JSONObject updateCategory(JSONObject jsonObject);
}
