package ms.luna.web.control;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ms.luna.biz.cons.VbConstant;
import ms.luna.biz.model.MsUser;
import ms.luna.biz.sc.ManageUserService;
import ms.luna.biz.sc.PulldownService;
import ms.luna.biz.util.CharactorUtil;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.biz.util.VbUtility;
import ms.luna.web.common.PulldownCtrl;
import ms.luna.web.model.SinglePulldownModel;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Component
@Controller
@RequestMapping("/manage_user.do")
public class ManageUserCtrl {
	// private static final Logger log =
	// LoggerFactory.getLogger(ManageUserCtrl.class);

	@Autowired
	private ManageUserService manageUserService;
	@Autowired
	private PulldownService pulldownService;
	@Resource(name = "pulldownCtrl")
	private PulldownCtrl pulldownCtrl;

	public ManageUserCtrl() {
	}

	private Map<String, String> loadCategorys() throws Exception {
		JSONObject result = null;
		result = pulldownService.loadCategorys();
		Map<String, String> categoryMap = new LinkedHashMap<String, String>();
		if ("0".equals(result.getString("code"))) {
			JSONObject data = result.getJSONObject("data");
			JSONArray arrays = data.getJSONArray("categorys");
			categoryMap.put(VbConstant.ZonePulldown.ALL, "全部");
			for (int i = 0; i < arrays.size(); i++) {
				JSONObject categoryJson = arrays.getJSONObject(i);
				categoryMap.put(categoryJson.getString("category_id"), categoryJson.getString("nm_zh"));
			}
		}
		return categoryMap;
	}

	/**
	 * 用户管理页面初始化
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=init")
	public ModelAndView init(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession(false);
			if (session == null) {
				MsLogger.error("session is null");
				return new ModelAndView("/error.jsp");
			}
			session.setAttribute("menu_selected", "user");
			Map<String, String> categoryMap = this.loadCategorys();

			Map<String, Object> model = new LinkedHashMap<String, Object>();

			SinglePulldownModel pulldown_cate = new SinglePulldownModel();
			model.put("pulldown_cate", pulldown_cate);
			model.put("categoryMap", categoryMap);

			String[] module = getModuleByRoleCode(request, response).split(";");
			model.put("module_code", module[0]);
			model.put("module_nm", module[1]);

			return new ModelAndView("/manage_user.jsp", model);
		} catch (Exception e) {
			MsLogger.error("Failed to init UsersManager page: ", e);
			return new ModelAndView("/error.jsp");
		}
		
	}

	// /**
	// * 分类页面初始化
	// *
	// * @param request
	// * @param response
	// */
	// @RequestMapping(params = "method=search_users")
	// public @ResponseBody ModelAndView search(String like_filter_nm, String
	// selectedValue,
	// @RequestParam(required = false) Integer pageNum, @RequestParam(required =
	// false) Integer pageSize,
	// HttpServletRequest request, HttpServletResponse response) {
	// try {
	// JSONObject param = JSONObject.parseObject("{}");
	// if (CharactorUtil.checkAlphaAndNumber(like_filter_nm, new char[] { '_'
	// })) {
	// like_filter_nm = like_filter_nm.toLowerCase();
	// param.put("like_filter_nm", like_filter_nm);
	// }
	//
	// if (selectedValue == null) {
	// selectedValue = VbConstant.ALL;
	// }
	// param.put("category_id", selectedValue);
	//
	// Map<String, String> categoryMap = this.loadCategorys();
	//
	// Map<String, Object> model = new LinkedHashMap<String, Object>();
	// model.put("like_filter_nm", like_filter_nm);
	//
	// SinglePulldownModel pulldown_cate = new SinglePulldownModel();
	// pulldown_cate.setSelectedValue(selectedValue);
	// model.put("pulldown_cate", pulldown_cate);
	// model.put("categoryMap", categoryMap);
	//
	// String[] module = getModuleByRoleCode(request, response).split(";");
	// model.put("module_code", module[0]);
	// model.put("module_nm", module[1]);
	//
	// // model.put("lstProvinces", pulldownCtrl.loadProvinces());
	// // model.put("lstRoles", pulldownCtrl.loadRoles());
	//
	// return new ModelAndView("/manage_user.jsp", model);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return new ModelAndView("/error.jsp");
	// }

	/**
	 * 用户管理页面列表搜索
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params = "method=async_search_users")
	public void asyncSearchUsers(String like_filter_nm, String selectedValue,
			@RequestParam(required = false) Integer offset, 
			@RequestParam(required = false) Integer limit,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		JSONObject resJSON = JSONObject.parseObject("{}");
		resJSON.put("total", 0);
		
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/html; charset=UTF-8");
			JSONObject param = JSONObject.parseObject("{}");
			if (like_filter_nm != null && !like_filter_nm.trim().isEmpty()) {
				like_filter_nm = URLDecoder.decode((like_filter_nm.trim()), "UTF-8");
				like_filter_nm = like_filter_nm.toLowerCase();
				param.put("like_filter_nm", like_filter_nm);
			}

			if (selectedValue == null) {
				selectedValue = VbConstant.ZonePulldown.ALL;
			}
			param.put("category_id", selectedValue);
			if (offset != null) {
				param.put("min", offset);
			}
			if (limit != null) {
				param.put("max", limit);
			}

			HttpSession session = request.getSession(false);
			if(session == null){
				throw new RuntimeException("session is null");
			}
			MsUser msUser = (MsUser) session.getAttribute("msUser");
			String role_code = msUser.getMsRoleCode();
			
			param.put("role_code", role_code);

			JSONObject result = manageUserService.loadUsers(param.toString());
			MsLogger.debug("result from service: "+result.toString());

			if ("0".equals(result.getString("code"))) {
				JSONObject data = result.getJSONObject("data");
				JSONArray arrays = data.getJSONArray("users");
				JSONArray rows = JSONArray.parseArray("[]");
				for (int i = 0; i < arrays.size(); i++) {
					JSONObject row = JSONObject.parseObject("{}");
					JSONObject userJson = arrays.getJSONObject(i);
					row.put("luna_name", userJson.getString("luna_name"));
					row.put("module_code", userJson.getString("module_code"));
					row.put("module_name", userJson.getString("module_name"));
					row.put("role_code", userJson.getString("role_code"));
					row.put("role_name", userJson.getString("role_name"));
					rows.add(row);
				}
				resJSON.put("rows", rows);
				resJSON.put("total", data.getLong("total"));
			}
		} catch (Exception e) {
			MsLogger.error("Failed to search users: ",e);
		}
		response.getWriter().print(resJSON.toString());
		response.setStatus(200);
		return;
	}

	/**
	 * 获取msUser的业务模块信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String getModuleByRoleCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			HttpSession session = request.getSession(false);
			if(session == null){
				throw new RuntimeException("session is null");
			}
			MsUser msUser = (MsUser) session.getAttribute("msUser");
			String role_code = msUser.getMsRoleCode();
			JSONObject param = JSONObject.parseObject("{}");
			param.put("role_code", role_code);

			JSONObject result = manageUserService.getModuleByRoleCode(param.toString());
			MsLogger.debug("result from service: "+result.toString());
			
			if ("0".equals(result.getString("code"))) {
				JSONObject json = result.getJSONObject("data");
				String module_code = json.getString("module_code");
				String module_nm = json.getString("module_nm");
				return module_code + ";" + module_nm;
			}
		} catch (Exception e) {
			try {
				response.getWriter().print(FastJsonUtil.error("-1", "Failed to get module by role_code: " + VbUtility.printStackTrace(e)));
				response.setStatus(200);
			} catch (IOException e1) {
				MsLogger.error(e1);
			}
		}
		return ";";
	}

	/**
	 * 根据modulecode加载相应role
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params = "method=load_roles")
	public void loadRolesByModuleCode(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/html; charset=UTF-8");

			HttpSession session = request.getSession(false);
			if(session == null){
				throw new RuntimeException("session is null");
			}
			MsUser msUser = (MsUser) session.getAttribute("msUser");

			String module_code = request.getParameter("module_code");
			JSONObject param = JSONObject.parseObject("{}");
			param.put("module_code", module_code);

			JSONObject result = manageUserService.loadRolesByModulecode(param.toString(), msUser);
			MsLogger.debug("result from service: "+result.toString());
			response.getWriter().print(result.toString());
			response.setStatus(200);
			return;
		} catch (Exception e) {
			try {
				response.getWriter().print(FastJsonUtil.error("-1", "Failed to load roles by module_code: " + VbUtility.printStackTrace(e)));
				response.setStatus(200);
			} catch (IOException e1) {
				MsLogger.error(e1);
			}
			return;
		}
	}

	/**
	 * 用户邀请
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 *             code 0:成功 1:邮箱格式不正确 4、邮箱已注册 5、
	 */
	@RequestMapping(params = "method=invite_users")
	public void inviteUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/html; charset=UTF-8");
			HttpSession session = request.getSession(false);
			if(session == null){
				throw new RuntimeException("session is null");
			}
			MsUser msUser = (MsUser) session.getAttribute("msUser");
			
			// 得到域名。如http://www.baidu.com/luna-web/regist.do?,则得到http://www.baidu.com/luna-web
			String[] temp = request.getRequestURL().toString().split("/");
			String webAddr = temp[0] + "//" + temp[2] + "/" + temp[3];

			String emails = request.getParameter("emails");
			String module_code = request.getParameter("module_code");
			String role_code = request.getParameter("role_code");

			// 邮箱名格式检测
			String[] mailAddrs = emails.split(";");
			for (int i = 0; i < mailAddrs.length; i++) {
				if (!CharactorUtil.checkEmail(mailAddrs[i])) {
					response.getWriter().print(FastJsonUtil.error("1", "邮箱格式不正确,email:"+mailAddrs[i]));
					response.setStatus(200);
					return;
				}
			}

			// 输入权限和业务模块的一致性检测（如何非法，说明已绕过前端页面向后台发送数据，认为其是攻击性行为）
			boolean checkFlag = checkModuleAndRole(module_code, role_code, msUser.getMsRoleCode());
			if (checkFlag == false) {
				response.getWriter().print(FastJsonUtil.error("5", "输入有误,module_code:"+module_code+",role_code:"+role_code));
				response.setStatus(200);
				return;
			}

			// 用户邀请
			JSONObject params = JSONObject.parseObject("{}");
			params.put("emails", emails);
			params.put("module_code", module_code);
			params.put("role_code", role_code);
			params.put("webAddr", webAddr);

			JSONObject results = manageUserService.inviteUsers(params.toString(), msUser);
			MsLogger.debug("result from service: "+results.toString());
			
			// {"code":"4","msg":"邮箱已注册","data":"[1259431236@qq.com,chinagrowing@yeah.net]"}
			String code = results.getString("code");
			JSONObject result = JSONObject.parseObject("{}");
			if ("0".equals(code)) {
				response.getWriter().print(FastJsonUtil.sucess("成功"));
			} else if ("1".equals(code)) {
				result.put("code", "4");
				result.put("msg", "邮箱已注册");
				String data = results.getString("data");
				data = data.substring(1, data.length() - 1);
				result.put("data", data);
				response.getWriter().print(result);//
			} else {
				response.getWriter().print(FastJsonUtil.error("-1", "异常"));
			}
			response.setStatus(200);
			return;
		} catch (Exception e) {
			try {
				response.getWriter().print(FastJsonUtil.error("-1", "Failed to invite users: " + VbUtility.printStackTrace(e)));
				response.setStatus(200);
			} catch (IOException e1) {
				MsLogger.error(e1);
			}
			return;
		}
	}

	/**
	 * 用户权限编辑
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params = "method=edit_user")
	public void editUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/html; charset=UTF-8");

			HttpSession session = request.getSession(false);
			if(session == null){
				throw new RuntimeException("session is null");
			}
			MsUser msUser = (MsUser) session.getAttribute("msUser");

			String luna_name = request.getParameter("luna_name");
			String module_code = request.getParameter("module_code");
			String role_code = request.getParameter("role_code");

			// 输入权限和业务模块的一致性检测（如何非法，说明已绕过前端页面向后台发送数据，认为其是攻击性行为）
			boolean checkFlag = checkModuleAndRole(module_code, role_code, msUser.getMsRoleCode());
			if (checkFlag == false) {
				response.getWriter().print(FastJsonUtil.error("5", "输入有误,module_code:"+module_code+",role_code:"+role_code));
				response.setStatus(200);
				return;
			}

			JSONObject json = JSONObject.parseObject("{}");
			json.put("luna_name", luna_name);
			json.put("module_code", module_code);
			json.put("role_code", role_code);

			JSONObject result = manageUserService.updateUser(json.toString(), msUser);
			MsLogger.debug("result from service: "+result.toString());
			
			JSONObject resJson = JSONObject.parseObject("{}");
			String code = result.getString("code");
			if ("0".equals(code)) {
				resJson = FastJsonUtil.sucess("更新成功");
			} else {
				resJson = FastJsonUtil.error("1", "更新失败!");
			}
			response.getWriter().print(resJson.toString());
			response.setStatus(200);

		} catch (Exception e) {
			response.getWriter().print(FastJsonUtil.error("-1", "Failed to edit user: "+e));
			response.setStatus(200);
		}
		return;
	}

	@RequestMapping(params = "method=del_user")
	public void delUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/html; charset=UTF-8");

			HttpSession session = request.getSession(false);
			if(session == null){
				throw new RuntimeException("session is null");
			}
			MsUser msUser = (MsUser) session.getAttribute("msUser");

			String luna_name = request.getParameter("luna_name");
			String module_code = request.getParameter("module_code");
			String role_code = request.getParameter("role_code");
			
			// 利用“邀请用户”和“编辑用户”的检测模块。原理一致，同样需要检测被删除用户与当前用户权限的一致性
			boolean checkFlag = checkModuleAndRole(module_code, role_code, msUser.getMsRoleCode());
			if (checkFlag == false) {
				response.getWriter().print(FastJsonUtil.error("5", "输入有误,module_code:"+module_code+",role_code:"+role_code));
				response.setStatus(200);
				return;
			}
			
			JSONObject json = JSONObject.parseObject("{}");
			json.put("luna_name", luna_name);
			// json.put("module_code", module_code);
			// json.put("role_code", role_code);

			JSONObject result = manageUserService.delUser(json.toString(), msUser);
			MsLogger.debug("result from service: "+result.toString());
			
			JSONObject resJson = JSONObject.parseObject("{}");
			String code = result.getString("code");
			if ("0".equals(code)) {
				resJson = FastJsonUtil.sucess("删除成功,luna_name:"+luna_name);
			} else {
				resJson = FastJsonUtil.error("1", "删除失败,luna_name:"+luna_name);
			}
			response.getWriter().print(resJson.toString());
			response.setStatus(200);

		} catch (Exception e) {
			response.getWriter().print(FastJsonUtil.error("-1", "Failed to del user: "+e));
			response.setStatus(200);
		}
		return;
	}

	/**
	 * 检测被邀请人的业务模块和权限是否一致
	 * 
	 * @param module_code_invitee被邀请人的业务模块
	 * @param role_code_invitee被邀请人的权限
	 * @param role_code_user邀请人的权限
	 * @return
	 */
	private boolean checkModuleAndRole(String module_code_invitee, String role_code_invitee, String role_code_inviter) {
		// 业务模块和权限一致性的条件:
		// 1、业务模块和权限输入非空
		// 2、邀请人具有邀请权限
		// 3、被邀请人的业务模块和权限匹配（即权限属于该业务模块）
		// 4、被邀请人权限不超过邀请人权限范围(前提：被邀请人业务模块和邀请人相同)

		// 1、业务模块和权限输入非空(module_code, role_code非空)
		if (module_code_invitee == null || role_code_invitee == null || module_code_invitee.isEmpty() || role_code_invitee.isEmpty()) {
			return false;
		}

		// 2、邀请人具有邀请权限（role_code 必须为admin）----删除用户是否具有删除权限
		if (!role_code_inviter.equals("luna_senior_admin") && !role_code_inviter.equals("luna_admin")
				&& !role_code_inviter.equals("luna_operator") && !role_code_inviter.equals("merchant_admin")
				&& !role_code_inviter.equals("poi_admin") && !role_code_inviter.equals("vbpano_admin")
				&& !role_code_inviter.equals("activity_admin")) {
			return false;
		}

		// 3、被邀请人的业务模块和权限匹配
		JSONObject param = JSONObject.parseObject("{}");
		param.put("module_code", module_code_invitee);
		param.put("role_code", role_code_invitee);
		JSONObject result = manageUserService.getAuthByRoleCodeAndModuleCode(param.toString());
		MsLogger.debug("result from service: "+result.toString());
		
		if (!result.getString("code").equals("0")) {
			return false;
		}
		JSONObject data = result.getJSONObject("data");
		String auth_invitee = data.getString("role_auth");

		// 4、被邀请人权限不超过邀请人权限范围----删除用户权限不超过当前用户权限
		// luna_senior_admin有绝对的邀请权限
		if (role_code_inviter.equals("luna_senior_admin")) {
			return true;
		}
		// luna_admin有有除了邀请luna_senior_admin外的其他任何邀请权限
		if(role_code_inviter.equals("luna_admin")){
			if(role_code_invitee.equals("luna_senior_admin")){
				return false;
			}
			return true;
		}
		// luna_admin有有除了邀请luna_senior_admin外的其他任何邀请权限
		if(role_code_inviter.equals("luna_operator")){
			if(role_code_invitee.equals("luna_senior_admin") || role_code_invitee.equals("luna_admin")){
				return false;
			}
			return true;
		}

		param = JSONObject.parseObject("{}");
		param.put("role_code", role_code_inviter);
		result = manageUserService.getAuthByRoleCode(param.toString());
		MsLogger.debug("result from service: "+result.toString());

		if (!result.getString("code").equals("0")) {
			throw new RuntimeException(result.getString("msg"));
		}
		data = result.getJSONObject("data");
		String auth_inviter = data.getString("role_auth");
		
		// 业务模块检测（是否同一个模块）
		String module_invitee = auth_invitee.substring(0, 1);
		String module_inviter = auth_inviter.substring(0, 1);
		
		if (!module_invitee.equals(module_inviter)) {
			return false;
		}

		// 权限检测
		int role_invitee = Integer.parseInt(auth_invitee.substring(1));
		int role_inviter = Integer.parseInt(auth_inviter.substring(1));
		if (role_inviter > role_invitee) {// 数据库权限倒序排列
			return false;
		}

		return true;
	}
}
