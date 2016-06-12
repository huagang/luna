package ms.luna.biz.bl;

import net.sf.json.JSONObject;

/**
 * @author Mark
 *
 */
public interface ManageUserBL {

	/**
	 * 获取国家信息
	 * @param json
	 * @return {code, msg, data{}}
	 */
	JSONObject loadUsers(String json);

	/**
	 * 获取业务模块的code对应的权限（role）
	 * @param json
	 * @return
	 */
	JSONObject loadRolesByModulecode(String json);
	
	/**
	 * 邀请用户，保存用户业务内容（module）和权限信息（role）
	 * @param json
	 * @return
	 */
	JSONObject inviteUsers(String json);

	/**
	* 更新用户权限
	* @param json
	* @param msUser
	* @return
	*/
	JSONObject updateUser(String json);

	/**
	* 删除用户
	* @param json
	* @param msUser
	* @return
	*/
	JSONObject delUser(String json);

	/**
	 * 根据权限获取业务模块信息
	 * @param json
	 * @return
	 */
	JSONObject getModuleByRoleCode (String json);
	
	/**
	 * 找到同时满足权限码和业务码的权限级别（auth）
	 * @param json
	 * @return
	 */
	JSONObject getAuthByRoleCodeAndModuleCode(String json);
	
	/**
	 * 通过权限码获取权限级别（auth）
	 * @param json
	 * @return
	 */
	JSONObject getAuthByRoleCode(String json);
	
}
