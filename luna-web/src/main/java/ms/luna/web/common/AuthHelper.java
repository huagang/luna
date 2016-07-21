package ms.luna.web.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-20
 */
public class AuthHelper {

    public static boolean hasBusinessAuth(HttpServletRequest request, int businessId) {

        HttpSession session = request.getSession(false);
        if(session == null) {
            return false;
        }

        // TODO: fetch user auth model in session
        return true;
    }
}
