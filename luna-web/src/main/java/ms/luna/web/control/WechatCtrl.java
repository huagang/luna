package ms.luna.web.control;

import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ms.luna.biz.util.DateUtil;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsHttpRequest;
import ms.luna.biz.util.MsLogger;
import ms.luna.biz.sc.LoginService;
import ms.luna.biz.cons.VbConstant;
import ms.luna.biz.model.MsUser;
import ms.luna.biz.util.VbMD5;
import ms.luna.biz.util.VbUtility;
import ms.luna.web.distr.session.VbDistrSessionUtil;
import com.alibaba.fastjson.JSONObject;
@Component
@Controller
@RequestMapping("wechat/login.do")
public class WechatCtrl {
//	private static final Logger log = LoggerFactory.getLogger(WechatCtrl.class);

	@Autowired
	private LoginService loginService;
	private static final String LOGIN_URI = "/login.jsp";
	private static final String wxappid = "wx81bab6b59781b95f";
	private static final String secret = "2202a30919a1d5f4e537a79ee9b50721";

	private static final String 微信回调的RUI = "http://luna-test.visualbusiness.cn/luna-web/wechat/login.do?method=wxlogin";
	private static final String accesstoken = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
			+ wxappid
			+ "&secret="
			+ secret
			+ "&grant_type=authorization_code"
			+ "&code=";
	private static final String userinfo = "https://api.weixin.qq.com/sns/userinfo?access_token=";

	/**
	 * (外部可以访问)
	 * login
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=wxlogin")
	public ModelAndView wxlogin(HttpServletRequest request, HttpServletResponse response) {

		Map<String, String> map = new HashMap<String, String>();
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		try {
			String key = request.getParameter("state");
			String code = request.getParameter("code");
			if (code == null) {
				map.put("red_msg", "请授权后登录!");
				return new ModelAndView(LOGIN_URI, map);
			}
			JSONObject jsonObject = VbDistrSessionUtil.getInstance().get(key);
			// 无token值
			if (jsonObject == null ) {
				map.put("red_msg", "请从登录页面进入");
				return new ModelAndView(LOGIN_URI, map);
			}
			Long tokenRegistTime = jsonObject.getLong("regist_time");
			// token超过时效（5分钟）
			if (DateUtil.isTimeOut(tokenRegistTime, System.currentTimeMillis(), 5)) {
				map.put("red_msg", "二维码超时");
				return new ModelAndView(LOGIN_URI, map);
			}

			// 调用微信接口，获取用户的openid以及unionid
			JSONObject openidInfo = this.getWxOpenIdInfo(code, request);
			// 将微信获取来的信息保存到session中
			HttpSession session = request.getSession();
			session.setAttribute("openid", openidInfo);
			// 获取皓月平台的登录信息，查看用户是否已经注册过或者已经注册淡水没有审批通过
			// TODO:权限是否已经审批
			// 因为是微信登录，所以要检查微信登录信息表
			/*
			 * 微信用户登录(openid, wxappid, unionid, ipaddress, mode)
			 * @param json
			 * @return {code, msg, data{wjnm, mode, regist_time, appnm, appid, rolenm, roleid}}
			 * code:201注册，且有无权限
			 * code:211注册,且有权限
			 * code:200未注册，无权限
			 */
			JSONObject loggingwx = JSONObject.parseObject("{}");
			loggingwx.put("openid", openidInfo.getString("openid"));
			loggingwx.put("wxappid", openidInfo.getString("wxappid"));
			loggingwx.put("unionid", openidInfo.getString("unionid"));
			loggingwx.put("mode", "1"); // 微信登录
			loggingwx.put("ipaddress", VbUtility.getRemortIP(request)); // 微信登录
			JSONObject loggingResult = loginService.logonThirdUser(loggingwx.toString());

			String status = loggingResult.getString("code");
			if ("0".equals(status)) {
				JSONObject data = loggingResult.getJSONObject("data");

				int auth = data.getInteger("role_auth");
				if (auth == VbConstant.AUTH_YOU_KE) {
					return new ModelAndView("forward:/login_201.jsp", map);
				}
				MsUser msUser = new MsUser();
				msUser.setNickName(data.getString("nick_name"));
				String msRoleCode = data.getString("ms_role_code");
				msUser.setMsRoleCode(msRoleCode);
				session.setAttribute("msUser", msUser);
				return new ModelAndView("forward:/manage/user.do?method=init");

				// 首次使用系统，没有注册过
			} else if ("200".equals(status)) {
				// TODO:
				return new ModelAndView("forward:/login_200.jsp", map);

			} else {
				map.put("red_msg",  loggingResult.getString("msg"));
				return new ModelAndView(LOGIN_URI, map);
			}
		} catch (Exception e) {
			map.put("red_msg","发生系统异常:"+VbUtility.printStackTrace(e));
			return new ModelAndView(LOGIN_URI, map);
		}
	}

	/**
	 * (外部可以访问)
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=generatetoken")
	public void generatetoken(HttpServletRequest request, HttpServletResponse response) {

		response.setHeader("Access-Control-Allow-Origin", "*");
//		response.setHeader("Content-Encoding","gzip");
		response.setContentType("text/html; charset=UTF-8");
		try {

			String callback_uri = request.getParameter("callback_uri");

			String token = VbMD5.generateToken();
			if (callback_uri == null || callback_uri.length() == 0) {
				response.getWriter().print(FastJsonUtil.error("-1", "没有回调的uri").toString());
				response.setStatus(200);
				return;
			}
			VbDistrSessionUtil.getInstance().put(token, URLDecoder.decode(callback_uri, "UTF-8"));
			JSONObject json = JSONObject.parseObject("{}");
			JSONObject data = JSONObject.parseObject("{}");
			data.put("appid", "wx81bab6b59781b95f");
			// 微信回调的url(登录验证的地址)
			data.put("redirect_uri", URLEncoder.encode(微信回调的RUI, "UTF-8"));
			data.put("token", token);
			json.put("code", "0");
			json.put("msg", "OK");
			json.put("data", data);
			MsLogger.debug(json.toString());
			response.getWriter().print(json.toString());
			response.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(500);
			return;
		}
	}

	private JSONObject getWxOpenIdInfo(String code, HttpServletRequest request) throws Exception {
		JSONObject result = JSONObject.parseObject("{}");
		String getAccessToken = accesstoken + code;

		// user-agent
		String userAgent = request.getHeader("user-agent");
		// 获取openid
		URLConnection connection = MsHttpRequest.sendPost(getAccessToken, userAgent);
		JSONObject jsonObject = JSONObject.parseObject(MsHttpRequest.conver2JsonString(connection.getInputStream()));
		MsLogger.debug(jsonObject.toString());
		String access_token = jsonObject.getString("access_token");
		//String refresh_token = jsonObject.getString("refresh_token");
		String openid = jsonObject.getString("openid");

		String unionid = "";
		if (jsonObject.containsKey("unionid")) {
			unionid = jsonObject.getString("unionid");
		}
		String user_info = userinfo + access_token
				+ "&openid="
				+ openid;
		connection = MsHttpRequest.sendPost(user_info, userAgent);

		JSONObject userInfoObject = JSONObject.parseObject(MsHttpRequest.conver2JsonString(connection.getInputStream()));
		MsLogger.debug(userInfoObject.toString());
		String nickname = userInfoObject.getString("nickname");
		// openid, wxappid, unionid, nickname, sex, province, city, country, headimgurl, wjnm, pw,
					// ipaddress
		result.put("openid", openid);
		result.put("wxappid", wxappid);
		result.put("unionid", unionid);
		result.put("nickname", nickname);
		result.put("sex", FastJsonUtil.getString(userInfoObject, "sex"));
		result.put("province", FastJsonUtil.getString(userInfoObject, "province"));
		result.put("city", FastJsonUtil.getString(userInfoObject, "city"));
		result.put("country", FastJsonUtil.getString(userInfoObject, "country"));
		result.put("headimgurl", FastJsonUtil.getString(userInfoObject, "headimgurl"));
		result.put("ipaddress", VbUtility.getRemortIP(request));

		return result;
	}

}
