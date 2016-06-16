package ms.luna.web.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import ms.luna.biz.util.CharactorUtil;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.biz.cons.VbConstant;
import ms.luna.biz.model.MsUser;
import ms.luna.biz.model.MsUser.MsUserEnum;
import ms.luna.biz.sc.LoginService;
import ms.luna.biz.util.VbUtility;
import ms.luna.common.MsLunaResource;
import ms.luna.web.filter.LoginFilter;
import ms.luna.web.util.WebHelper;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Component("loginCtrl")
@Controller
@RequestMapping("/login.do")
public class LoginCtrl {
	@Autowired
	private LoginService loginService;

	public static final String LOGIN_URI = "/login.jsp";

	public static String HTTP_DOMAIN = MsLunaResource.getResource("luna").getValue("HttpDomain");

	@Resource(name="webHelper")
	private WebHelper webHelper;

//	/**
//	 * 登录页面初始化
//	 * @param request
//	 * @param response
//	 */
//	@RequestMapping(params = "method=init")
//	public String init(HttpServletRequest request, HttpServletResponse response) {
//		return LOGIN_URI;
//	}

	/**
	 * 用户名密码登录
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(params = "method=logon_pwuser")
	public ModelAndView logonPwUser(
			@RequestParam(value="luna_name", required=true)
			String luna_name,
			@RequestParam(value="password", required=true)
			String password,
			HttpServletRequest request) throws IOException {

		MsLogger.debug("user["+ luna_name +"] was attempt to login!");
		// check luna_name
		JSONObject jsonNickNm = CharactorUtil.checkNickNm(luna_name);
		String nickCode = FastJsonUtil.getString(jsonNickNm,"code");
		if (!"0".equals(nickCode)) {
			MsLogger.debug("user["+luna_name +"] format is not correct!");
			return errorJsonResult("-1", "用户名或者密码错误！");
		}

		// check密码
		JSONObject jsonPw = CharactorUtil.checkPw(password);
		String pwCode = FastJsonUtil.getString(jsonPw, "code");
		if (!"0".equals(pwCode)) {
			MsLogger.debug("user' pw ["+password +"] format is not correct!");
			return errorJsonResult("-1", "用户名或者密码错误！");
		}

		/*
		 * 微景用户登录(luna_name,pw)
		 * @param json
		 * @return {code, msg, data{luna_name, wjid, regist_time}}
		 */
		JSONObject param = JSONObject.parseObject("{}");
		param.put("luna_name", luna_name);
		param.put("pw", password);
		param.put("ipaddress", VbUtility.getRemortIP(request));
		JSONObject loggingResult = loginService.logonPwUser(param.toString());

		String code = loggingResult.getString("code");
		// 用户名或者密码错误
		if ("-199".equals(code)) {
			MsLogger.debug("user ["+luna_name +"] or password ["+password + "] is not correct!");
			return errorJsonResult("-1", "用户名或者密码错误！");

			// 正常登录
		} else if ("0".equals(code)) {
			JSONObject data = loggingResult.getJSONObject("data");
			MsUser msUser = new MsUser();
			String msRoleCode = data.getString("ms_role_code");
			String msRoleName = data.getString("ms_role_name");
			String nickName = data.getString("nick_name");
			String uniqueId = data.getString("unique_id");
			JSONArray uri_arrays = data.getJSONArray("access_uris");

			Integer mode = data.getInteger("mode");

			// 检查是否有头像
			if (data.containsKey("head_img_url")) {
				String headImgUrl = data.getString("head_img_url");
				msUser.setHeadImgUrl(headImgUrl);
			}
			msUser.setMsRoleCode(msRoleCode);
			msUser.setMsRoleName(msRoleName);
			msUser.setNickName(nickName);
			msUser.setUniqueId(uniqueId);
			msUser.setMode(mode);
			Map<String, MsUserEnum> accessUriMap = msUser.getAccessUris();
			for (int i = 0; i < uri_arrays.size(); i++) {
				accessUriMap.put(LoginFilter.webRoot +uri_arrays.getString(i), MsUserEnum.读写);
			}
			if (webHelper.hasRoles(msUser,
					VbConstant.Module.Luna.皓月平台高级管理员_Code
					,VbConstant.Module.Luna.皓月平台管理员_Code
					,VbConstant.Module.Luna.皓月平台运营员_Code
					,VbConstant.Module.Vbpano.VBPano管理员_Code
					,VbConstant.Module.Merchant.商家管理员_Code
					,VbConstant.Module.Activity.活动管理员_Code
					,VbConstant.Module.Poi.信息数据管理员_Code
					,VbConstant.Module.Merchant.商家运营员_Code
					,VbConstant.Module.Poi.信息数据运营员_Code)) {
				request.getSession(true).setAttribute("msUser", msUser);
				return errorJsonResult("0", "permitted");
			} else if (webHelper.hasRoles(msUser,
					VbConstant.Module.Vbpano.VBPano运营员_Code)) {
				return errorJsonResult("-1", "全景管理页链接努力开发中，尽请期待！");
			} else {
				return errorJsonResult("-1", "您的权限暂时不能访问系统资源，请联系皓月高级管理员！");
			}
		} else if ("-198".equals(code)) {
			return errorJsonResult("-1", loggingResult.getString("msg"));
		} else {
			MsLogger.error(loggingResult.getString("msg"));
			return errorJsonResult("-1", "您的角色目前没有担当的业务！");
		}
	}

	/**
	 * 用户退出
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(params = "method=logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		return goDomainHomeRoot();
	}

	public ModelAndView goDomainHomeRoot() {
		ModelAndView view = new ModelAndView();
		view.setViewName("redirect:" + HTTP_DOMAIN);
		return view;
	}

	public ModelAndView errorJsonResult(String code, String msg) {
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		Map<String, String> attributes = new HashMap<String, String>();
		attributes.put("code", code);
		attributes.put("msg", msg);
		view.setContentType("text/html;charset=UTF-8");
		view.setAttributesMap(attributes);
		ModelAndView mav = new ModelAndView();
		mav.setView(view);
		return mav;
	}

	public ModelAndView errorJsonResult(String code, String msg, String token) {
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		Map<String, String> attributes = new HashMap<String, String>();
		attributes.put("code", code);
		attributes.put("msg", msg);
		attributes.put("token", token);
		view.setContentType("text/html;charset=UTF-8");
		view.setAttributesMap(attributes);
		ModelAndView mav = new ModelAndView();
		mav.setView(view);
		return mav;
	}
}
