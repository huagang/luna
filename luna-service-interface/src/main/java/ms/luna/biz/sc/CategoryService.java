package ms.luna.biz.sc;

import ms.luna.biz.model.MsUser;
import com.alibaba.fastjson.JSONObject;

public interface CategoryService {
	JSONObject getCategorys(String json);
	JSONObject deleteCategory(String json);
	JSONObject addCategory(String json);
	JSONObject updateCategory(String json);
}
