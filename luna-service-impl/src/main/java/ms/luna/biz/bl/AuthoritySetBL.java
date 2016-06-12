package ms.luna.biz.bl;

import net.sf.json.JSONObject;

public interface AuthoritySetBL {
	/**
	 * 获取角色权限设置
	 * @param json
	 * @return
	 */
	JSONObject getModulesAndAuthsForSetting(String json);

	/**
	 * 保存角色权限设置
	 * @param json
	 * @return
	 */
	JSONObject saveModulesAndAuthsForSetting(String json);
}
