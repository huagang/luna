package ms.luna.web.control;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ms.luna.biz.sc.LoginService;
import ms.luna.biz.util.CharactorUtil;
import ms.luna.biz.util.JsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.biz.util.VbUtility;
import net.sf.json.JSONObject;

@Component
@Controller
@RequestMapping("/luna_api.do")
public class LoginCtrl {
	@Autowired
	private LoginService loginService;
	/**
	 * 用户名密码登录
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(params = "method=getUserInfo", method=RequestMethod.POST)
	public void getUserInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

			response.setContentType("text/html; charset=UTF-8");
			InputStream in = request.getInputStream();

			String params = IOUtils.toString(in, "UTF-8");

			JSONObject jsonParam = JSONObject.fromObject(params);

			String username = jsonParam.getString("username");
			String password = jsonParam.getString("password");

			// check luna_name
			JSONObject jsonNickNm = CharactorUtil.checkNickNm(username);
			String nickCode = JsonUtil.getString(jsonNickNm, "code");
			if (!"0".equals(nickCode)) {
				response.getWriter().print(JsonUtil.error("-1", "user or passsword is invalid"));
				response.setStatus(200);
				return;
			}

			// check密码
			JSONObject jsonPw = CharactorUtil.checkPw(password);
			String pwCode = JsonUtil.getString(jsonPw, "code");
			if (!"0".equals(pwCode)) {
				response.getWriter().print(JsonUtil.error("-1", "user or passsword is invalid"));
				response.setStatus(200);
				return;
			}

			/*
			 * 微景用户登录(luna_name,pw)
			 * @param json
			 * @return {code, msg, data{luna_name, wjid, regist_time}}
			 */
			JSONObject param = JSONObject.fromObject("{}");
			param.put("luna_name", username);
			param.put("pw", password);
			param.put("ipaddress", VbUtility.getRemortIP(request));
			MsLogger.debug(param.toString());

			JSONObject loggingResult = loginService.logonPwUser(param.toString());
			response.getWriter().print(loggingResult.toString());
			response.setStatus(200);
			return;
	}
}
