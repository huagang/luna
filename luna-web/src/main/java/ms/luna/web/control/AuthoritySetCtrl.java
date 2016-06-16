package ms.luna.web.control;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ms.luna.biz.model.MsUser;
import ms.luna.biz.sc.AuthoritySetService;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.web.model.authorityset.AuthoritySetFunctionModel;
import ms.luna.web.model.authorityset.AuthoritySetModel;
import ms.luna.web.model.authorityset.AuthoritySetModuleModel;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
@Component
@Controller
@RequestMapping("/authority_set.do")
public class AuthoritySetCtrl {

	@Autowired
	private AuthoritySetService authoritySetService;

	/**
	 * 页面初始化
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=init")
	public ModelAndView init(
			@RequestParam(required = true, value = "ms_role_code")
			String ms_role_code,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			// 查询业务模块和权限选项
			JSONObject param = JSONObject.parseObject("{}");
			param.put("ms_role_code", ms_role_code);

			HttpSession session = request.getSession(false);
			MsUser msUser = (MsUser)session.getAttribute("msUser");
			JSONObject result = authoritySetService.getModulesAndAuthsForSetting(param.toString(), msUser);

			AuthoritySetModel authoritySetModel = new AuthoritySetModel();
			List<AuthoritySetModuleModel> lstAuthoritySetModuleModel = authoritySetModel.getModules();

			String ms_role_name = null;
			List<String> checkeds = new ArrayList<String>();
			if ("0".equals(result.getString("code"))) {

				// 被操作人员的角色code
				session.setAttribute("ms_role_code", ms_role_code);
				List<AuthoritySetFunctionModel> editFunctions = new ArrayList<AuthoritySetFunctionModel>();

				JSONObject data = result.getJSONObject("data");
				JSONArray modules = data.getJSONArray("modules");
				ms_role_name = data.getString("ms_role_name");
				for (int i = 0; i < modules.size(); i++) {
					JSONObject module = modules.getJSONObject(i);

					AuthoritySetModuleModel authoritySetModuleModel = new AuthoritySetModuleModel();

					// 设置module的名称及Code
					String biz_module_name = module.getString("biz_module_name");
					String biz_module_code = module.getString("biz_module_code");

					JSONArray functions = module.getJSONArray("functions");

					List<AuthoritySetFunctionModel> lstFunctions = authoritySetModuleModel.getFunctions();
					for (int j = 0; j < functions.size(); j++) {
						JSONObject function = functions.getJSONObject(j);
						String enabled = function.getString("enabled");
						String editable = function.getString("editable");
						String function_name = function.getString("function_name");
						String function_code = function.getString("function_code"); //TODO: code

						// 设置function的名称及Code以及是否可用
						AuthoritySetFunctionModel authoritySetFunctionModel = new AuthoritySetFunctionModel();
						if ("true".equals(enabled)) {
							checkeds.add(function_code);
							authoritySetFunctionModel.setChecked(Boolean.TRUE);
						} else {
							authoritySetFunctionModel.setChecked(Boolean.FALSE);
							checkeds.add("false");
						}
						// 是否可以编辑
						authoritySetFunctionModel.setEditable(Boolean.parseBoolean(editable));

						authoritySetFunctionModel.setLabel(function_name);
						authoritySetFunctionModel.setValue(function_code);

						lstFunctions.add(authoritySetFunctionModel);
					}
					editFunctions.addAll(lstFunctions);
					authoritySetModuleModel.setLabel(biz_module_name);
					authoritySetModuleModel.setValue(biz_module_code);

					lstAuthoritySetModuleModel.add(authoritySetModuleModel);
				}
				session.setAttribute("editFunctions", editFunctions);

				authoritySetModel.setCheckeds(checkeds);

				ModelAndView mav = new ModelAndView("/authority-set.jsp");
				mav.addObject("authoritySetModel", authoritySetModel);
				mav.addObject("ms_role_name", ms_role_name);
				mav.addObject("ms_role_code", ms_role_code);
				return mav;
			} else {
				return new ModelAndView("/error.jsp");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("/error.jsp");
	}

	/**
	 * 页面初始化
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "method=save")
	public ModelAndView save(
			@ModelAttribute(value="authoritySetModel")
			AuthoritySetModel authoritySetModel,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			HttpSession session = request.getSession(false);

			// 变化前的功能列表
			List<AuthoritySetFunctionModel> editFunctions = (List<AuthoritySetFunctionModel>)session.getAttribute("editFunctions");
			String ms_role_code = (String)session.getAttribute("ms_role_code");
			if (editFunctions == null || ms_role_code == null) {
				return new ModelAndView("/error.jsp");
			}
 
			List<String> checkeds = authoritySetModel.getCheckeds();
			if (checkeds == null) {
				checkeds = new ArrayList<String>();
			}
			for (AuthoritySetFunctionModel authoritySetFunctionModel : editFunctions) {
				// 选中的checkbox
				if (checkeds.contains(authoritySetFunctionModel.getValue())) {

					// 可以编辑的情况(可能是新添加的值，也可能是原值)
					if (authoritySetFunctionModel.getEditable()) {
						authoritySetFunctionModel.setChecked(Boolean.TRUE);;
					}
					// 不可以编辑的情况,维持原值

					// checkbox没有选中
				} else {
					// 可以编辑的情况(原来可能就没有选中，也可能需要去除选中)
					if (authoritySetFunctionModel.getEditable()) {
						authoritySetFunctionModel.setChecked(Boolean.FALSE);;
					}
					// 不可以编辑的情况,维持原值
				}
			}

			JSONObject param = JSONObject.parseObject("{}");
			List<String> lstChecked = new ArrayList<String>();
			for (AuthoritySetFunctionModel authoritySetFunctionModel : editFunctions) {
				if (authoritySetFunctionModel.getChecked()) {
					lstChecked.add(authoritySetFunctionModel.getValue());
				}
			}
			JSONArray array = FastJsonUtil.parse2Array(lstChecked);
			param.put("checkeds", array);
			param.put("ms_role_code", ms_role_code);

			MsUser msUser = (MsUser)session.getAttribute("msUser");
			JSONObject result = authoritySetService.saveModulesAndAuthsForSetting(param.toString(), msUser);
			MsLogger.debug(result.toString());
			if ("0".equals(result.getString("code"))) {
				session.removeAttribute("editFunctions");
				session.removeAttribute("ms_role_code");
				return new ModelAndView("/authority.do?method=init_authority");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("/error.jsp");
	}
}
