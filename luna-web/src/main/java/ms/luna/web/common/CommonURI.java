package ms.luna.web.common;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-08-08
 */
public class CommonURI {

    public static final String LOGIN_SERVLET_PATH = "/";
    public static final String AUTH_FAIL_SERVLET_PATH = "/common/authFail";
    public static final String REGITSTER_SERVLET_PATH = "/common/register";

    public static String getAbsoluteUrlForServletPath(HttpServletRequest request, String servletPath) {

        String url = request.getRequestURL().toString();
        String currentServletPath = request.getServletPath();
        String basePath = url.substring(0, url.indexOf(currentServletPath));

        return basePath + servletPath;
    }

    public static String getUriForServlet(HttpServletRequest request, String servletPath) {
        return request.getContextPath() + servletPath;
    }
}
