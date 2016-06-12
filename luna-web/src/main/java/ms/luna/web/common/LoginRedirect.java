package ms.luna.web.common;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletResponse;

import ms.luna.biz.util.JsonUtil;
import ms.luna.biz.util.MsLogger;
import net.sf.json.JSONObject;

public class LoginRedirect {
 
	public static void wxloginredirect(JSONObject loggingResult, String redirecturi, HttpServletResponse response) throws IOException {
		
		String status = loggingResult.getString("code");

		JSONObject data = null;
		if (loggingResult.has("data")) {
			data = loggingResult.getJSONObject("data");
		}
		StringBuilder sb = new StringBuilder(redirecturi);
		MsLogger.debug("loginredirect=" + loggingResult.toString());
		// code:201注册，且有无权限
		if ("201".equals(status)) {
			sb.append("?code=3&hynickname=")
			.append(data.getString("wjnm"))
			.append("&date=")
			.append(data.getString("regist_time"));
			// code:211注册,且有权限
		} else if ("211".equals(status)) {
			sb.append("?code=1&hynickname=")
			.append(data.getString("wjnm"))
			.append("&date=")
			.append(data.getString("regist_time"));
			// TODO:是否需要跳转
			// code:200未注册，无权限
		} else if ("200".equals(status)) {
			sb.append("?code=2");
		}
		MsLogger.debug(sb.toString());
		response.setStatus(200);
		response.sendRedirect(URLDecoder.decode(sb.toString(),"UTF-8"));
	}

	public static void wjloginredirect(JSONObject loggingResult, HttpServletResponse response) throws IOException {
		
		String code = loggingResult.getString("code");
		String redirecturi = "http://luna-test.visualbusiness.cn/luna/user-manage.html";
		String redirecturi2 = "http://luna-test.visualbusiness.cn/luna/login.html";
		JSONObject data = null;
		if (loggingResult.has("data")) {
			data = loggingResult.getJSONObject("data");
		}
		StringBuilder sb = new StringBuilder();
		MsLogger.debug("loginredirect=" + loggingResult.toString());
		response.setStatus(200);
		// 用户名或者密码错误
		if ("-199".equals(code)) {
			JSONObject result = JsonUtil.error("-199", "用户名或者密码不正确");
			MsLogger.debug(result.toString());
			response.getWriter().print(result.toString());

			// code:201注册,无权限
		} else if ("201".equals(code)) {
			sb.append(redirecturi2);
			sb.append("?code=3&hynickname=")
			.append(data.getString("wjnm"))
			.append("&date=")
			.append(data.getString("regist_time"));
			// TODO:是否需要跳转
			MsLogger.debug(sb.toString());
			response.sendRedirect(URLDecoder.decode(sb.toString(), "UTF-8"));

			// code:211注册,且有权限
		} else if ("211".equals(code)) {
			sb.append(redirecturi);
			sb.append("?code=1&hynickname=")
			.append(data.getString("wjnm"))
			.append("&date=")
			.append(data.getString("regist_time"));
			// TODO:是否需要跳转
			MsLogger.debug(sb.toString());
			response.sendRedirect(URLDecoder.decode(sb.toString(), "UTF-8"));

			// code:200未注册
		} else if ("200".equals(code)) {
			JSONObject result = JsonUtil.error("200", "用户没有注册");
			MsLogger.debug(result.toString());
			response.getWriter().print(result.toString());
		}
	}

	public static void checkwjlogin(JSONObject loggingResult, HttpServletResponse response) throws IOException {

		String status = loggingResult.getString("code");
		MsLogger.debug("loginredirect=" + loggingResult.toString());
		response.setStatus(200);
		// 用户名或者密码错误
		if ("-199".equals(status)) {
			JSONObject result = JsonUtil.error("-199", "用户名或者密码不正确");
			MsLogger.debug(result.toString());
			response.getWriter().print(result.toString());

			// code:201注册,无权限
		} else if ("201".equals(status)) {
			JSONObject result = JsonUtil.error("201", "注册,无权限");
			MsLogger.debug(result.toString());
			response.getWriter().print(result.toString());

			// code:211注册,且有权限
		} else if ("211".equals(status)) {
			JSONObject result = JsonUtil.error("211", "注册,且有权限");
			MsLogger.debug(result.toString());
			response.getWriter().print(result.toString());

			// code:200未注册
		} else if ("200".equals(status)) {
			JSONObject result = JsonUtil.error("200", "用户没有注册");
			MsLogger.debug(result.toString());
			response.getWriter().print(result.toString());
		} else {
			JSONObject result = JsonUtil.error("-198", "其他错误");
			MsLogger.debug(result.toString());
			response.getWriter().print(result.toString());
		}
	}
}
