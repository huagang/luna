/**
 * 用户登录
 */
package ms.luna.biz.bl.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ms.luna.biz.bl.LoginBL;
import ms.luna.biz.cons.VbConstant;
import ms.luna.biz.dao.custom.MsLogonLogDAO;
import ms.luna.biz.dao.custom.MsRFunctionResourceUriDAO;
import ms.luna.biz.dao.custom.MsRRoleFunctionDAO;
import ms.luna.biz.dao.custom.MsRUserRoleDAO;
import ms.luna.biz.dao.custom.MsResourceUriDAO;
import ms.luna.biz.dao.custom.MsRoleDAO;
import ms.luna.biz.dao.custom.MsUserLunaDAO;
import ms.luna.biz.dao.custom.MsUserPwDAO;
import ms.luna.biz.dao.model.MsLogonLog;
import ms.luna.biz.dao.model.MsRFunctionResourceUri;
import ms.luna.biz.dao.model.MsRFunctionResourceUriCriteria;
import ms.luna.biz.dao.model.MsRRoleFunction;
import ms.luna.biz.dao.model.MsRRoleFunctionCriteria;
import ms.luna.biz.dao.model.MsRUserRole;
import ms.luna.biz.dao.model.MsRUserRoleCriteria;
import ms.luna.biz.dao.model.MsResourceUri;
import ms.luna.biz.dao.model.MsResourceUriCriteria;
import ms.luna.biz.dao.model.MsRole;
import ms.luna.biz.dao.model.MsUserLuna;
import ms.luna.biz.dao.model.MsUserPw;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.biz.util.VbMD5;
import com.alibaba.fastjson.JSONObject;

/**
 * @author Mark
 *
 */
@Transactional(rollbackFor=Exception.class)
@Service("loginBL")
public class LoginBLImpl implements LoginBL {

	private final static Logger logger = Logger.getLogger(LoginBLImpl.class);

	@Autowired
	private MsUserPwDAO msUserPwDAO;

	@Autowired
	private MsUserLunaDAO msUserLunaDAO;

	@Autowired
	private MsRUserRoleDAO msRUserRoleDAO;
	
	@Autowired
	private MsRoleDAO msRoleDAO;
	
	@Autowired
	private MsLogonLogDAO msLogonLogDAO;

	@Autowired
	private MsRRoleFunctionDAO msRRoleFunctionDAO;

	@Autowired
	private MsRFunctionResourceUriDAO msRFunctionResourceUriDAO;

	@Autowired
	private MsResourceUriDAO msResourceUriDAO;

	/**
	 * 检查皓月用户是否存在
	 */
	@Override
	public JSONObject isLunaUserExsit(String json) {
		JSONObject param = JSONObject.parseObject(json);
		String luna_name = param.getString("luna_name");
		MsUserPw msUserPw = msUserPwDAO.selectByPrimaryKey(luna_name);
		if (msUserPw == null) {
			return FastJsonUtil.error("-1", "皓月用户不存在");
		}
		return FastJsonUtil.sucess("皓月用户存在");
	}

	/**
	 * 用户名密码登录(luna_name,pw,ipaddress)
	 * @param json
	 * @return {code, msg, data{MsUser}}
	 */
	@Override
	public JSONObject logonPwUser(String json) {
		JSONObject param = JSONObject.parseObject(json);
		String lunaName = param.getString("luna_name");
		String pw = param.getString("pw");
		final String ipAddress = param.getString("ipaddress");

		MsUserPw msUserPw = msUserPwDAO.selectByPrimaryKey(lunaName);
		// 用户名不存在
		if (msUserPw == null) {
			return FastJsonUtil.error("-199", "用户名或者密码错误");
		}

		final String uniqueId = msUserPw.getUniqueId();
		// 密码错误
		String md5Pw = VbMD5.convertFixMD5Code(pw);
		if (!msUserPw.getPwLunaMd5().equals(md5Pw)) {
			return FastJsonUtil.error("-199", "用户名或者密码错误");
		}

		// 检查用户状态是否正常（是否被列入黑名单）
		MsUserLuna msUserLuna = msUserLunaDAO.selectByPrimaryKey(msUserPw.getUniqueId());
		if (!VbConstant.USER_STATUS.正常.equals(msUserLuna.getStatus())) {
			return FastJsonUtil.error("-198", "用户状态不正确[" + msUserLuna.getStatus() + "]");
		}
//		String msRoleCode = data.getString("ms_role_code");
//		String msRoleName = data.getString("ms_role_name");
//		String nickName = data.getString("nick_name");
//		String uniqueId = data.getString("unique_id");
//		Integer mode = data.getInt("mode");
		// 获取用户权限
		MsRUserRoleCriteria msRUserRoleCriteria = new MsRUserRoleCriteria();
		msRUserRoleCriteria.createCriteria()
		.andUniqueIdEqualTo(msUserPw.getUniqueId());
		List<MsRUserRole> lstMsRUserRole = msRUserRoleDAO.selectByCriteria(msRUserRoleCriteria);

		if (lstMsRUserRole == null
				|| lstMsRUserRole.size() > 1) {
			return FastJsonUtil.error("-197", "用户担当角色有兼职");
		}

		MsRRoleFunctionCriteria msRRoleFunctionCriteria = new MsRRoleFunctionCriteria();
		msRRoleFunctionCriteria.createCriteria()
		.andMsRoleCodeEqualTo(lstMsRUserRole.get(0).getMsRoleCode());
		List<MsRRoleFunction> lstMsRRoleFunction = msRRoleFunctionDAO.selectByCriteria(msRRoleFunctionCriteria);
		if (lstMsRRoleFunction == null || lstMsRRoleFunction.isEmpty()) {
			return FastJsonUtil.error("-196", "没有任何担当的功能");
		}
//		MsRFunctionResourceUriDAO
		List<String> hasFunctions = new ArrayList<String>();
		for (MsRRoleFunction msRRoleFunction : lstMsRRoleFunction) {
			hasFunctions.add(msRRoleFunction.getMsFunctionCode());
		}
		MsRFunctionResourceUriCriteria msRFunctionResourceUriCriteria = new MsRFunctionResourceUriCriteria();
		msRFunctionResourceUriCriteria.createCriteria()
		.andMsFunctionCodeIn(hasFunctions)
		.andDelFlgEqualTo(VbConstant.DEL_FLG.未删除);
		List<MsRFunctionResourceUri> lstMsRFunctionResourceUri = msRFunctionResourceUriDAO.selectByCriteria(
				msRFunctionResourceUriCriteria);
		
		if (lstMsRFunctionResourceUri == null || lstMsRFunctionResourceUri.isEmpty()) {
			return FastJsonUtil.error("-195", "没有任何资源可访问");
		}

		Set<String> accessUris = new HashSet<String>();
		Set<Integer> resourceIdSet = new HashSet<>();
		for (MsRFunctionResourceUri msRFunctionResourceUri : lstMsRFunctionResourceUri) {
			resourceIdSet.add(msRFunctionResourceUri.getResourceId());
		}

		this.loadUris(accessUris, resourceIdSet);
		// 由于有外键关联，角色一定有值，且不为空值
		MsRole msRole = msRoleDAO.selectByPrimaryKey(lstMsRUserRole.get(0).getMsRoleCode());
		JSONObject data = JSONObject.parseObject("{}");
		data.put("ms_role_code", lstMsRUserRole.get(0).getMsRoleCode());
		data.put("ms_role_name", msRole.getMsRoleName());
		data.put("nick_name", msUserPw.getLunaName());
		data.put("unique_id", msUserPw.getUniqueId());
		data.put("mode", VbConstant.USER_LOGIN_MODE.用户名密码);

		// 获取角色下可以访问的URI权限列表设置
		data.put("access_uris", accessUris);
		logger.debug(accessUris);

		//记录访问日志, TODO: 单独service异步记录
		MsLogonLog msLogonLog =  new MsLogonLog();
		msLogonLog.setIpAddress(ipAddress);
		msLogonLog.setMode(VbConstant.USER_LOGIN_MODE.用户名密码);
		msLogonLog.setUniqueId(uniqueId);
		msLogonLogDAO.insertSelective(msLogonLog);

		return FastJsonUtil.sucess("OK", data);
	}

	/**
	 *
	 * @param accessUris
	 * @param resourceIdSet
	 * @return
	 */
	private void loadUris(Set<String> accessUris, Set<Integer> resourceIdSet) {
		MsResourceUriCriteria msResourceUriCriteria = new MsResourceUriCriteria();
		msResourceUriCriteria.createCriteria().andResourceIdIn(Lists.newArrayList(resourceIdSet)).andStatusEqualTo(Boolean.TRUE);
		List<MsResourceUri> msResourceUriList = msResourceUriDAO.selectByCriteria(msResourceUriCriteria);
		if (msResourceUriList == null || msResourceUriList.isEmpty()) {
			return;
		}

		Set<Integer> parentIdSet = new HashSet<>();
		for (MsResourceUri msResourceUri : msResourceUriList) {
			// 叶子节点是内容
			if (!StringUtils.isBlank(msResourceUri.getResourceUri())) {
				accessUris.add(msResourceUri.getResourceUri());
			} else {
				parentIdSet.add(msResourceUri.getResourceId());
			}
		}
		while(true) {
			if (parentIdSet.size() == 0) {
				return;
			}

			msResourceUriCriteria.clear();
			msResourceUriCriteria.createCriteria().andParentIdIn(Lists.newArrayList(parentIdSet)).andStatusEqualTo(Boolean.TRUE);

			msResourceUriList = msResourceUriDAO.selectByCriteria(msResourceUriCriteria);
			if (msResourceUriList == null || msResourceUriList.isEmpty()) {
				return;
			}
			// clear parentId set
			parentIdSet.clear();
			for (MsResourceUri msResourceUri : msResourceUriList) {
				if (!StringUtils.isBlank(msResourceUri.getResourceUri())) {
					accessUris.add(msResourceUri.getResourceUri());
				} else {
					parentIdSet.add(msResourceUri.getResourceId());
				}
			}
		}
	}

	@Override
	public JSONObject registThirdUser(String json) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject logonThirdUser(String json) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject registPwUser(String json) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		System.out.println(VbMD5.convertFixMD5Code("niubihaoyue"));
	}

}
