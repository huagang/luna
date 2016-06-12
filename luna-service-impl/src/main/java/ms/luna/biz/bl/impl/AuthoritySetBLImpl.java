package ms.luna.biz.bl.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ms.biz.common.AuthenticatedUserHolder;
import ms.luna.biz.bl.AuthoritySetBL;
import ms.luna.biz.dao.custom.MsBizModuleDAO;
import ms.luna.biz.dao.custom.MsFunctionDAO;
import ms.luna.biz.dao.custom.MsRRoleFunctionDAO;
import ms.luna.biz.dao.custom.MsRoleDAO;
import ms.luna.biz.dao.model.MsBizModule;
import ms.luna.biz.dao.model.MsBizModuleCriteria;
import ms.luna.biz.dao.model.MsFunction;
import ms.luna.biz.dao.model.MsFunctionCriteria;
import ms.luna.biz.dao.model.MsRRoleFunction;
import ms.luna.biz.dao.model.MsRRoleFunctionCriteria;
import ms.luna.biz.dao.model.MsRole;
import ms.luna.biz.model.MsUser;
import ms.luna.biz.util.JsonUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author Mark
 *
 */
@Transactional(rollbackFor=Exception.class)
@Service("authoritySetBL")
public class AuthoritySetBLImpl implements AuthoritySetBL {

	/**
	 * 1.全部显示<p>
	 * <li>业务模块</li>
	 * <li>功能</li>
	 * 
	 * 2.根据角色内容获取已经拥有的权限
	 * <li>checkbox选中状态</li>
	 */
	@Autowired
	private MsBizModuleDAO msBizModuleDAO;

	@Autowired
	private MsFunctionDAO msFunctionDAO;

	@Autowired
	private MsRoleDAO msRoleDAO;

	@Autowired
	private MsRRoleFunctionDAO msRRoleFunctionDAO;

	@Override
	public JSONObject getModulesAndAuthsForSetting(String json) {

		MsUser msUser = (MsUser)AuthenticatedUserHolder.get();

		String logonRoleCode = msUser.getMsRoleCode();

		JSONObject param = JSONObject.fromObject(json);
		String ms_role_code = param.getString("ms_role_code");

		// 登录人员具有的权限
		Set<String> loginFunctionCodeSet = new HashSet<String>();
		{
			MsRRoleFunctionCriteria msRRoleFunctionCriteria = new MsRRoleFunctionCriteria();
			msRRoleFunctionCriteria.createCriteria()
			.andMsRoleCodeEqualTo(logonRoleCode);
			List<MsRRoleFunction> lstMsRRoleFunction = msRRoleFunctionDAO.selectByCriteria(msRRoleFunctionCriteria);

			if (lstMsRRoleFunction != null) {
				for (MsRRoleFunction msRRoleFunction : lstMsRRoleFunction) {
					loginFunctionCodeSet.add(msRRoleFunction.getMsFunctionCode());
				}
			}
		}

		MsBizModuleCriteria msBizModuleCriteria = new MsBizModuleCriteria();
		msBizModuleCriteria.createCriteria();
		msBizModuleCriteria.setOrderByClause("ds_order asc");
		List<MsBizModule> lstMsBizModule = msBizModuleDAO.selectByCriteria(msBizModuleCriteria);

		MsFunctionCriteria msFunctionCriteria = new MsFunctionCriteria();
		msFunctionCriteria.createCriteria();
		msFunctionCriteria.setOrderByClause("ds_order asc");
		List<MsFunction> lstMsFunction = msFunctionDAO.selectByCriteria(msFunctionCriteria );

		MsRRoleFunctionCriteria msRRoleFunctionCriteria = new MsRRoleFunctionCriteria();
		msRRoleFunctionCriteria.createCriteria()
		.andMsRoleCodeEqualTo(ms_role_code);
		List<MsRRoleFunction> lstMsRRoleFunction = msRRoleFunctionDAO.selectByCriteria(msRRoleFunctionCriteria);

		// 被操作人员有的权限
		Set<String> functionCodeSet = new HashSet<String>();
		if (lstMsRRoleFunction != null) {
			for (MsRRoleFunction msRRoleFunction : lstMsRRoleFunction) {
				functionCodeSet.add(msRRoleFunction.getMsFunctionCode());
			}
		}

		JSONArray modules = JSONArray.fromObject("[]");
		for (MsBizModule msBizModule : lstMsBizModule) {
			JSONObject module = JSONObject.fromObject("{}");
			JSONArray functions = JSONArray.fromObject("[]");
			for (MsFunction msFunction : lstMsFunction) {
				if (msBizModule.getBizModuleCode().equals(msFunction.getBizModuleCode())) {
					JSONObject function = JSONObject.fromObject("{}");
					function.put("function_code", msFunction.getMsFunctionCode());
					function.put("function_name", msFunction.getMsFunctionName());
					if (functionCodeSet.contains(msFunction.getMsFunctionCode())) {
						function.put("enabled", "true");
					} else {
						function.put("enabled", "false");
					}

					if (logonRoleCode.equals(ms_role_code)) {
						function.put("editable", "false");
					} else {
						// 只能编辑自己有的权限
						if (loginFunctionCodeSet.contains(msFunction.getMsFunctionCode())) {
							function.put("editable", "true");
						} else {
							function.put("editable", "false");
						}
					}
					functions.add(function);
				}
			}
			module.put("biz_module_code", msBizModule.getBizModuleCode());
			module.put("biz_module_name", msBizModule.getBizModuleName());
			module.put("functions", functions);
			modules.add(module);
		}
		MsRole msRole = msRoleDAO.selectByPrimaryKey(ms_role_code);
		JSONObject data =  JSONObject.fromObject("{}");
		data.put("modules", modules);
		data.put("ms_role_name", msRole.getMsRoleName());

		return JsonUtil.sucess("OK", data);
	}

	@Override
	public JSONObject saveModulesAndAuthsForSetting(String json) {

		JSONObject param = JSONObject.fromObject(json);
		String ms_role_code = param.getString("ms_role_code");
		JSONArray checkeds = param.getJSONArray("checkeds");

		MsUser msUser = (MsUser)AuthenticatedUserHolder.get();
		String logonRoleCode = msUser.getMsRoleCode();
		if (logonRoleCode.equals(ms_role_code)) {
			return JsonUtil.error("-1", "您不能对自己的角色更改权限[" + logonRoleCode + "]");
		}
		/*
		 * 获取登录人具有的权限列表
		 */
		MsRRoleFunctionCriteria msRRoleFunctionCriteria = new MsRRoleFunctionCriteria();
		msRRoleFunctionCriteria.createCriteria()
		.andMsRoleCodeEqualTo(logonRoleCode);
		List<MsRRoleFunction> lstMsRRoleFunction = msRRoleFunctionDAO.selectByCriteria(msRRoleFunctionCriteria);
		Set<String> funcsOfoperator = new HashSet<String>();
		if (lstMsRRoleFunction != null) {
			for (MsRRoleFunction msRRoleFunction : lstMsRRoleFunction) {
				funcsOfoperator.add(msRRoleFunction.getMsFunctionCode());
			}
		}

		/*
		 * 备操作人的权限列表
		 */
		msRRoleFunctionCriteria.clear();
		msRRoleFunctionCriteria.createCriteria()
		.andMsRoleCodeEqualTo(ms_role_code);
		lstMsRRoleFunction = msRRoleFunctionDAO.selectByCriteria(msRRoleFunctionCriteria);
		if (lstMsRRoleFunction != null) {
			for (MsRRoleFunction msRRoleFunction : lstMsRRoleFunction) {
				if (!funcsOfoperator.contains(msRRoleFunction.getMsFunctionCode())) {
					return JsonUtil.error("-1", "您没有权限操作 [" + msRRoleFunction.getMsFunctionCode() + "]");
				}
			}
		}
		if (funcsOfoperator.isEmpty()) {
			return JsonUtil.error("-1", "您没有权限操作");
		}

		// 清除已有权限
		msRRoleFunctionDAO.deleteByCriteria(msRRoleFunctionCriteria);

		// 添加现有权限
		MsRRoleFunction msRRoleFunction = new MsRRoleFunction();
		msRRoleFunction.setMsRoleCode(ms_role_code);
		for (int i = 0; i < checkeds.size(); i++) {
			msRRoleFunction.setMsFunctionCode(checkeds.getString(i));
			msRRoleFunctionDAO.insertSelective(msRRoleFunction);
		}
		return JsonUtil.sucess("OK");
	}

}
