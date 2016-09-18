package ms.luna.web.filter;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import ms.luna.biz.util.MsLogger;
import ms.luna.common.LunaUserSession;
import ms.luna.web.common.CommonURI;
import ms.luna.web.common.SessionHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-08-06
 */
public class LunaLoginFilter implements Filter {

    private final static Logger logger = Logger.getLogger(LunaLoginFilter.class);

    private Set<String> skipUrlAfterLogin;

    private List<Pattern> excludePatternList;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        String exclude = filterConfig.getInitParameter("exclude");
        if (exclude != null) {
            Set<String> tokens = Sets.newHashSet(exclude.split(","));
            excludePatternList = new ArrayList<>(tokens.size());
            for(String token : tokens) {
                if(StringUtils.isBlank(token)) {
                    continue;
                }
                token = token.trim().replace("*", ".*").replace("?", ".{1}");
                Pattern pattern = Pattern.compile(token, Pattern.CASE_INSENSITIVE);
                excludePatternList.add(pattern);
            }
        }
        skipUrlAfterLogin = new HashSet<>();
        skipUrlAfterLogin.add("/inner");

    }

    private boolean isExclude(String uri) {

        if(excludePatternList == null) {
            return false;
        }
        for(Pattern pattern : excludePatternList) {
            if (pattern.matcher(uri).matches()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse ) {

            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            String contextPath = httpServletRequest.getContextPath();
            String servletPath = httpServletRequest.getServletPath();

            if(isExclude(servletPath)) {
                chain.doFilter(request, response);
                return;
            }
            if(servletPath.equals("/") || servletPath.equals("")) {
                chain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }
            HttpSession session = httpServletRequest.getSession(false);
            LunaUserSession user = SessionHelper.getUser(session);
            if(user == null) {
                httpServletResponse.sendRedirect(contextPath + CommonURI.LOGIN_SERVLET_PATH);
                return;
            }

            for(String uri : skipUrlAfterLogin) {
                if(servletPath.startsWith(uri)) {
                    chain.doFilter(httpServletRequest, httpServletResponse);
                    return;
                }
            }

            TreeMap<String, JSONObject> menuAuth = (TreeMap<String, JSONObject>) user.getMenuAuth();
            String menuPath = menuAuth.floorKey(servletPath);
            logger.debug(String.format("servlet[%s] mapping menu path[%s]: ", servletPath, menuPath));
            if(servletPath.startsWith(menuPath)) {
                chain.doFilter(request, response);
                return;
            } else {
                httpServletResponse.sendRedirect(contextPath + CommonURI.AUTH_FAIL_SERVLET_PATH);
            }

        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
