package ms.luna.web.control;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import ms.luna.biz.sc.LoginService;
import ms.luna.biz.sc.RegistService;
import ms.luna.biz.util.CharactorUtil;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.biz.util.VbUtility;
@Component
@Controller
@RequestMapping("/userRegist.do")
public class RegistCtrl {
//	private static final Logger log = LoggerFactory.getLogger(RegistCtrl.class);
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private RegistService registService;

	private static int TOKEN_LENGTH = 46; //token长度
	
	@RequestMapping(params = "method=init_regPage")
	public ModelAndView init_regist(@RequestParam(required = true)String token, HttpServletRequest request, HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		
		try {
			JSONObject json = JSONObject.parseObject("{}");
			json.put("token", token);
			// token检查
			if(token.isEmpty() || token.length() != TOKEN_LENGTH){
				return new ModelAndView("/error.jsp");
			}
			
			JSONObject result = registService.isTokenValid(json.toString());
			MsLogger.debug("method:isTokenValid, result from service: "+result.toString());
			
			if("0".equals(result.get("code"))){
				ModelAndView model = new ModelAndView("/register.jsp");
				model.addObject("token", token);
				return model;
			}
			return new ModelAndView("/login_overtime.jsp");
		} catch (Exception e) {
			return new ModelAndView("/error.jsp");
		}
	}
	
	@RequestMapping(params = "method=regist_wjuser")
	public void regist_wjuser(
			@RequestParam(required = true)String token,
			@RequestParam(required = true)String nickname,
			@RequestParam(required = true)String password,
			HttpServletRequest request, HttpServletResponse response){
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		try {
			
			// 检测token
			if(token.isEmpty() || token.length() != TOKEN_LENGTH){//token长度不正确
				response.getWriter().print(FastJsonUtil.error("4","token不正确,token:"+token).toString());
				response.setStatus(200);
				return;
			}
			//检查账户名
			JSONObject result = check_wjnm(nickname);
			if("1".equals(result.get("code"))){
				response.getWriter().print(FastJsonUtil.error("1","账户格式不正确,nickname:"+nickname).toString());
				response.setStatus(200);
				return;
			}else if("2".equals(result.get("code"))){
				response.getWriter().print(FastJsonUtil.error("2","账户名称存在,nickname:"+nickname).toString());
				response.setStatus(200);
				return;
			}else if("3".equals(result.get("code"))){
				response.getWriter().print(FastJsonUtil.error("3","账户已被注册,nickname:"+nickname).toString());
				response.setStatus(200);
				return;
			}
			//检查账户密码
			result = check_pw(password);
			if(!"0".equals(result.get("code"))){
				response.getWriter().print(FastJsonUtil.error("1","密码格式不正确,password:"+password).toString());
				response.setStatus(200);
				return;
			}
			//注册
			JSONObject json = JSONObject.parseObject("{}");
			json.put("nickname", nickname);
			json.put("password", password);
			json.put("token", token);
			result = registService.registPwUser(json.toString());
			MsLogger.debug("method:registPwUser, result from service: "+result.toString());
			
			if("1".equals(result.get("code"))){
				result = FastJsonUtil.error("3", "账户已被注册,nickname:"+nickname);
			}
			response.getWriter().print(result.toString());
			response.setStatus(200);
			
		} catch (Exception e) {
			String jsonResult = FastJsonUtil.error("-101", "Failed to regist user: "+ VbUtility.printStackTrace(e)).toString();
			try {
				response.getWriter().print(jsonResult);
				response.setStatus(200);
			} catch (IOException e1) {
				MsLogger.error(e1);
			}
		}
	}/**/
	
	/**
	 * 检查账户名
	 * @param nickname
	 * @return
	 */
	private JSONObject check_wjnm(String nickname) {
		//检查长度
		if (nickname == null || nickname.length() == 0 || nickname.getBytes().length > 32) {
			return FastJsonUtil.error("1", "账户格式（长度）不正确,nickname:"+nickname);//以后需要更加详细的信息时可设定不同code值
		}
		//检查格式
		JSONObject jsonNickNm = CharactorUtil.checkNickNm(nickname);
		if(!"0".equals(jsonNickNm.get("code"))){
			return FastJsonUtil.error("1", "账户格式（字符）不正确,nickname:"+nickname); 
		}
		//检查重名
		JSONObject json = JSONObject.parseObject("{}");
		json.put("luna_name", nickname);
		JSONObject result = loginService.isLunaUserExsit(json.toString());
		if("0".equals(result.get("code"))){
			return FastJsonUtil.error("3", "账户已被注册,nickname:"+nickname);
		}
		return FastJsonUtil.sucess("账户格式正确!");
	}

	/**
	 * 检查密码
	 * @param password
	 * @return
	 */
	private JSONObject check_pw(String password) {
		JSONObject jsonPw = CharactorUtil.checkPw(password);
		if(!"0".equals(jsonPw.getString("code"))){
			return FastJsonUtil.error("1", "密码格式不正确,password:"+password);
		}
		return FastJsonUtil.sucess("密码格式正确,password:"+password);
	}

//	/**
//	 * 异步请求昵称是否合法
//	 * @param request
//	 * @param response
//	 */
//	@RequestMapping(params = "method=check_nickname")
//	public void check_wjnm(HttpServletRequest request, HttpServletResponse response) {
//
//		response.setHeader("Access-Control-Allow-Origin", "*");
//		response.setContentType("text/html; charset=UTF-8");
//		try {
//			String nickname = request.getParameter("nickname");
//			nickname = CharactorUtil.trim(nickname);
//			if (nickname == null || nickname.length() == 0
//					|| nickname.getBytes().length > 32) {
//				String jsonResult = FastJsonUtil.error("201", "账户格式不正确").toString();
//				System.out.println(jsonResult);
//				response.getWriter().print(jsonResult);
//				response.setStatus(200);
//				return;
//			}
//			JSONObject json = JSONObject.parseObject("{}");
//			json.put("luna_name", nickname);
//			JSONObject result = loginService.isLunaUserExsit(json.toString());
//			String code = result.getString("code");
//			if ("-1".equals(code)) {
//				String jsonResult = FastJsonUtil.sucess("可以使用").toString();
//				System.out.println(jsonResult);
//				response.getWriter().print(jsonResult);
//				response.setStatus(200);
//				return;
//			} else if (!"0".equals(code)) {
//				response.getWriter().print(result.toString());
//				response.setStatus(200);
//				return;
//			}
//			String jsonResult = FastJsonUtil.error("202", "账户重名").toString();
//			System.out.println(jsonResult);
//			response.getWriter().print(jsonResult);
//			response.setStatus(200);
//			return;
//		} catch (Exception e) {
//			String jsonResult = FastJsonUtil.error("-101", "系统出现异常,请稍后重试:"+ VbUtility.printStackTrace(e)).toString();
//			try {
//				response.getWriter().print(jsonResult);
//				response.setStatus(200);
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
//		}
//	}
//
//	/**
//	 * regist(已经通过微信认证)
//	 * @param request
//	 * @param response
//	 */
//	@RequestMapping(params = "method=regist_wjuser")
//	public ModelAndView regist_wjuser(String nickname,
//			String password,
//			HttpServletRequest request, HttpServletResponse response) {
//
//		response.setHeader("Access-Control-Allow-Origin", "*");
//		response.setContentType("text/html; charset=UTF-8");
//		try {
//			// 检查session超时或者没有openid
//			HttpSession session = VbUtility.timeOut(request, response, "openid");
//			if (session == null) {
////				response.getWriter().print(FastJsonUtil.error("-1", "请求超时"));
////				response.setStatus(200);
//				Map<String, String> msg = new LinkedHashMap<String, String>();
//				msg.put("red_msg", "请求超时");
//				return new ModelAndView("forward:/login_200.jsp", msg);
//			}
//
//			// check昵称
//			String wjnm = nickname;
//			JSONObject jsonNickNm = CharactorUtil.checkNickNm(wjnm);
//			String nickCode = jsonNickNm.getString("code");
//			if (!"0".equals(nickCode)) {
//				Map<String, String> msg = new LinkedHashMap<String, String>();
//				msg.put("red_msg", jsonNickNm.getString("msg"));
//				return new ModelAndView("forward:/login_200.jsp", msg);
//			}
//
//			// check密码
//			String pw = password;
//			JSONObject jsonPw = CharactorUtil.checkPw(pw);
//			String pwCode = jsonNickNm.getString("code");
//			if (!"0".equals(pwCode)) {
//				Map<String, String> msg = new LinkedHashMap<String, String>();
//				msg.put("red_msg", jsonPw.getString("msg"));
//				return new ModelAndView("forward:/login_200.jsp", msg);
//			}
//
//			// 获取先前保存的微信信息
//			JSONObject wxInfoJson = (JSONObject)session.getAttribute("openid");
//			// openid, wxappid, unionid, nickname, sex, province, city, country, headimgurl, wjnm, pw,
//			// ipaddress
//			wxInfoJson.put("wjnm", wjnm);
//			wxInfoJson.put("pw", pw);
//			System.out.println(wxInfoJson.toString());
//			// 注册微信信息以及昵称信息
//			JSONObject result = loginService.registThirdUser(wxInfoJson.toString());
//
//			System.out.println("regist_result="+result.toString());
//
//			/*
//			 * 注册微信用户(openid, wxappid, unionid, nickname, sex, province, city, country, headimgurl, wjnm, pw,
//			 * ipaddress,)
//			 * @param json
//			 * @return {code, msg, data{wjid}}
//			 */
//			String code = result.getString("code");
//			// 注册成功
//			if ("0".equals(code)) {
//				session.invalidate();
//				return new ModelAndView("forward:/login_201.jsp");
//
//			} else {
//				Map<String, String> msg = new LinkedHashMap<String, String>();
//				msg.put("red_msg", jsonNickNm.getString("msg"));
//				return new ModelAndView("forward:/login_200.jsp", msg);
//			}
//		} catch (Exception e) {
//			return new ModelAndView("redirect:/error.jsp");
//		}
//	}
}
