package ms.luna.biz.sc;

import ms.luna.biz.model.MsUser;
import net.sf.json.JSONObject;

public interface CategoryService {
	JSONObject getCategorys(String json);
	JSONObject deleteCategory(String json, MsUser msUser);
	JSONObject addCategory(String json, MsUser msUser);
	JSONObject updateCategory(String json, MsUser msUser);
}
