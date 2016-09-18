package ms.luna.biz.bl;

import com.alibaba.fastjson.JSONObject;

public interface LoginBL {

	/**
	 * 第三方注册
	 * @param json
	 * @return
	 */
	JSONObject registThirdUser(String json);

	/**
	 * 第三方登录
	 * @param json
	 * @return
	 */
	JSONObject logonThirdUser(String json);

	/**
	 * 微景用户注册
	 * @param json
	 * @return
	 */
	JSONObject registPwUser(String json);

	/**
	 * 微景用户登录(luna_name,pw,ipaddress)
	 * @param json
	 * @return {code, msg, data{MsUser}}
	 */
	JSONObject logonPwUser(String json);

	/**
	 * 检查皓月用户是否已经存在{存在:code=0,不存在:code!=0}
	 * @param json
	 * @return
	 */
	JSONObject isLunaUserExsit(String json);
}
