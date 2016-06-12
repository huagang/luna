package ms.luna.biz.bl;

import net.sf.json.JSONObject;

public interface CategoryBL {
	JSONObject getCategorys(String json);
	JSONObject deleteCategory(String json);
	JSONObject addCategory(String json);
	JSONObject updateCategory(String json);
}
