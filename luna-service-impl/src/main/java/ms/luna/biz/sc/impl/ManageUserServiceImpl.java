package ms.luna.biz.sc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ms.luna.biz.bl.ManageUserBL;
import ms.luna.biz.model.MsUser;
import ms.luna.biz.sc.ManageUserService;
import ms.luna.biz.util.JsonUtil;
import net.sf.json.JSONObject;

@Service("manageUserService")
public class ManageUserServiceImpl implements ManageUserService {

	@Autowired
	private ManageUserBL manageUserBL;
	@Override
	public JSONObject loadUsers(String json) {
		JSONObject result = null;
		try {
			result = manageUserBL.loadUsers(json);
		} catch (RuntimeException e) {
			
			return JsonUtil.error("-1", e);
		}
		return result;
	}

	@Override
	public JSONObject loadRolesByModulecode(String json, MsUser msUser) {
		JSONObject result = null;
		try {
			result = manageUserBL.loadRolesByModulecode(json);
		} catch (Exception e) {
			
			return JsonUtil.error("-1", e);
		}
		return result;
	}

	@Override
	public JSONObject inviteUsers(String json, MsUser msUser) {
		JSONObject result = null;
		try {
			result = manageUserBL.inviteUsers(json);
		} catch (Exception e) {
			
			return JsonUtil.error("-1", e);
		}
		return result;
	}
	@Override
	public JSONObject updateUser(String json, MsUser msUser) {
		JSONObject result = null;
		try {
			result = manageUserBL.updateUser(json);
		} catch (Exception e) {
		
			return JsonUtil.error("-1", e);
		}
		return result;
	}

	@Override
	public JSONObject delUser(String json, MsUser msUser) {
		JSONObject result = null;
		try {
			result = manageUserBL.delUser(json);
		} catch (Exception e) {
			
			return JsonUtil.error("-1", e);
		}
		return result;
	}

	@Override
	public JSONObject getModuleByRoleCode(String json) {
		JSONObject result = null;
		try {
			result = manageUserBL.getModuleByRoleCode(json);
		} catch (Exception e) {
			
			return JsonUtil.error("-1", e);
		}
		return result;
	}

	@Override
	public JSONObject getAuthByRoleCodeAndModuleCode(String json) {
		JSONObject result = null;
		try {
			result = manageUserBL.getAuthByRoleCodeAndModuleCode(json);
		} catch (Exception e) {
			
			return JsonUtil.error("-1", e);
		}
		return result;
	}

	@Override
	public JSONObject getAuthByRoleCode(String json) {
		JSONObject result = null;
		try {
			result = manageUserBL.getAuthByRoleCode(json);
		} catch (Exception e) {
			
			return JsonUtil.error("-1", e);
		}
		return result;
	}
	
//	@Override
//	public JSONObject likeSearchByCategory(String json) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
}
