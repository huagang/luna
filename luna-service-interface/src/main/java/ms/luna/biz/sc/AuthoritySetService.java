package ms.luna.biz.sc;

import ms.luna.biz.model.MsUser;
import net.sf.json.JSONObject;

/**
 * 
 * @author Mark
 *
 */
public interface AuthoritySetService {
	
	/**
	 * 获取角色权限设置
	 * @param json
	 * @param msUser
	 * @return
	 */
	JSONObject getModulesAndAuthsForSetting(String json, MsUser msUser);

	/**
	 * 保存角色权限设置
	 * @param json
	 * @param msUser
	 * @return
	 */
	JSONObject saveModulesAndAuthsForSetting(String json, MsUser msUser);
}
