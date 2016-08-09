package ms.luna.web.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ms.luna.biz.model.MsUser;
import ms.luna.biz.model.MsUser.MsUserEnum;
import ms.luna.biz.util.MsLogger;
import ms.luna.web.control.LoginCtrl;

/**
 * Servlet Filter implementation class SSOFilter
 */
@WebFilter("/loginFilter")
public class LoginFilter implements Filter {
	
	private Pattern[] patterns;

	public static final String webRoot = "/luna-web";
//	private static final String LOGIN_URI = webRoot + "/login.jsp";
	private static final String ERROR_URI = webRoot + "/auth_failed.jsp";
	/**
	 * 无需登录就可以访问的URI
	 */
	private static Set<String> skipUris = new HashSet<>();
	private static Set<String> skipUriAfterLogin = new HashSet<>();
	private final String API_URI = webRoot + "/api/";  
	
	static {	
		skipUriAfterLogin.addAll(Arrays.asList(
				webRoot + "/uploadCtrl.do",
				webRoot + "/show_poi.do",
				webRoot + "/show_article.do",
				webRoot + "/api_vodPlay.do",
				webRoot + "/fileUpload.do",
				webRoot + "/menu.do"));

		skipUris.addAll(Arrays.asList(
				webRoot + "/wechat/login.do",
				webRoot + "/login.do",
				webRoot + "/userRegist.do",
				webRoot + "/merchantRegist.do",
				webRoot + "/pulldown.do"
				));
	}
	
	private static boolean checkSkipUris(String uri) {
		return skipUris.contains(uri);
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain)
			throws IOException, ServletException {
		if (arg0 instanceof HttpServletRequest && arg1 instanceof HttpServletResponse ) {

			HttpServletRequest request = (HttpServletRequest) arg0;
			HttpServletResponse response = (HttpServletResponse) arg1;
			// String userId = (String)request.getParameter("userId");
			String uri = new String(request.getRequestURI());
			
			//开放api暂时不做任何过滤
			if(uri.startsWith(API_URI)) {
				chain.doFilter(request, response);
				return;
			}
			MsLogger.debug("filter= "+uri);
			if (patterns!= null) {
				for (Pattern pattern : patterns) {
					if (pattern.matcher(uri).matches()) {
						MsLogger.debug("Avoid permission check for: " + uri);
						chain.doFilter(request, response);
						return;
					}
				}
			}
			HttpSession session = request.getSession(false);
			// 微信登录(未登录)
			if (session != null && session.getAttribute("openid") != null) {
				chain.doFilter(request, response);
				return;
				
				// 用户名密码登录(未登录)
			} else if (session == null || session.getAttribute("msUser") == null) {
				// 检查URI资源访问权限
				if (checkSkipUris(uri)) {
					chain.doFilter(request, response);
					return;
				}
				// 没有访问权限,跳转到登录页面
				response.sendRedirect(LoginCtrl.HTTP_DOMAIN);
				response.setStatus(200);
				return;

			} else {
				// 登录情况下， TODO:检查角色和访问请求的URI是否匹配
				MsUser msUser = (MsUser)session.getAttribute("msUser");
				if (msUser == null) {
					MsLogger.debug("no MsUser");
					session.invalidate();
					response.sendRedirect(ERROR_URI);
					response.setStatus(200);
					return;
				}
				// 检查URI资源访问权限
				if (checkSkipUris(uri)) {
					chain.doFilter(arg0, arg1);
					return;
				}
				
				if(skipUriAfterLogin.contains(uri)){
					chain.doFilter(request, response);
					return;
				}
				Map<String, MsUserEnum> msUserEnumMap = msUser.getAccessUris();
				String checkuri = uri;
				if (uri.endsWith(".do")) {
					String method = request.getParameter("method");
					if (method != null) {
						checkuri = uri+ "?method="+ method;
					}
				}
				if (msUserEnumMap.get(checkuri) != null) {
					chain.doFilter(request, response);
					return;
				}
				// TODO：[调试阶段]暂时不清理会话
//				session.invalidate();
				response.sendRedirect(ERROR_URI);
				response.setStatus(200);
				return;
			}
		} else {
			// pass the request along the filter chain
			chain.doFilter(arg0, arg1);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		String exclusionPattern = filterConfig.getInitParameter("EXCLUSIONS");
		if (exclusionPattern == null) {
			return;
		}
		String[] patternStrings = exclusionPattern.split(",");
		patterns = new Pattern[patternStrings.length];
		for (int i = 0; i < patternStrings.length; ++i) {
			patternStrings[i] = patternStrings[i].trim();
			patternStrings[i] = patternStrings[i].replace("*", "(.*)").replace("?", "(.{1})");
			patterns[i] = Pattern.compile(patternStrings[i].trim(), Pattern.CASE_INSENSITIVE);
		}
	}

}
