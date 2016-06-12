package ms.luna.biz.sc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ms.luna.biz.bl.LoginBL;
import ms.luna.biz.sc.LoginService;
import ms.luna.biz.util.JsonUtil;
import net.sf.json.JSONObject;

/**
 * @author Mark
 *
 */
@Service("loginService")
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginBL loginBL;
	/**
	 * 注册第三方用户(unionid, nickname, mode, headimgurl,ipaddress)
	 * @param json
	 * @return {code, msg, data{unique_id,nickname,headimgurl,mode}}
	 */
	@Override
	public JSONObject registThirdUser(String json) {

		JSONObject jSONObject = null;
		try {
			jSONObject = loginBL.registThirdUser(json);
		} catch (Exception e) {
			
			return JsonUtil.error("102", "注册失败：重名");
		}
		return jSONObject;
	}

	/**
	 * 微信用户登录(unionid, ipaddress, mode)
	 * @param json
	 * @return {code, msg, data{MsUser}}
	 */
	@Override
	public JSONObject logonThirdUser(String json) {
		JSONObject jSONObject = null;
		try {
			jSONObject = loginBL.logonThirdUser(json);
		} catch (Exception e) {
			
			return JsonUtil.error("-1", e);
		}
		return jSONObject;
	}

	/**
	 * 用户名密码登录
	 */
	@Override
	public JSONObject logonPwUser(String json) {
		/*
		 * 微景用户登录(luna_name,pw,ipaddress)
		 * @param json
		 * @return {code, msg, data{MsUser}}
		 */
		JSONObject jSONObject = null;
		try {
			jSONObject = loginBL.logonPwUser(json);
		} catch (Exception e) {
			
			return JsonUtil.error("-1", e);
		}
		return jSONObject;
	}

	/**
	 * 注册用户名密码登录
	 */
	@Override
	public JSONObject registPwUser(String json) {
		JSONObject jSONObject = null;
		try {
			jSONObject = loginBL.registPwUser(json);
		} catch (Exception e) {
			
			return JsonUtil.error("-1", e);
		}
		return jSONObject;
	}

	/**
	 * 微景用户名是否存在(luna_name)
	 */
	@Override
	public JSONObject isLunaUserExsit(String json) {
		/*
		 * 微景用户登录(luna_name,pw,ipaddress)
		 * @param json
		 * @return {code, msg, data{MsUser}}
		 */
		JSONObject jSONObject = null;
		try {
			jSONObject = loginBL.isLunaUserExsit(json);
		} catch (Exception e) {
			
			return JsonUtil.error("-1", e);
		}
		return jSONObject;
	}
}
