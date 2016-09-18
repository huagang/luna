package ms.luna.web.common;

import com.alibaba.fastjson.JSONArray;
import ms.luna.common.LunaUserSession;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpSession;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-08-06
 */
public class SessionHelper {

    public static final String KEY_USER = "user";
    public static final String KEY_MENU = "menu";
    public static final String KEY_SELECTED_MENU = "menu_selected";
    public static final String KEY_IS_ROLE_BUSINESS = "is_business";

    public static LunaUserSession getUser(HttpSession session) {
        if(session == null) {
            return null;
        }
        LunaUserSession lunaUserSession = (LunaUserSession) session.getAttribute(KEY_USER);
        return lunaUserSession;
    }

    public static void setUser(HttpSession session, LunaUserSession lunaUserSession) {
        if(session == null || lunaUserSession == null) {
            return;
        }
        session.setAttribute(KEY_USER, lunaUserSession);
    }

    public static JSONArray getMenu(HttpSession session) {
        if(session == null) {
            return null;
        }
        JSONArray jsonArray = (JSONArray) session.getAttribute(KEY_MENU);
        return jsonArray;
    }

    public static void setMenu(HttpSession session, JSONArray menu) {
        if(session == null || menu == null) {
            return;
        }
        session.setAttribute(KEY_MENU, menu);
    }

    public static void setSelectedMenu(HttpSession session, String menu) {
        if(session == null) {
            return;
        }
        session.setAttribute(KEY_SELECTED_MENU, menu);
     }

    public static String getSelectedMenu(HttpSession session) {
        if(session == null) {
            return "";
        }
        return (String)session.getAttribute(KEY_SELECTED_MENU);
    }

    public static void setIsRoleBusiness(HttpSession session, boolean isRoleBusiness) {
        if(session == null) {
           return;
        }
        session.setAttribute(KEY_IS_ROLE_BUSINESS, isRoleBusiness);
    }

    public static boolean getIsRoleBusiness(HttpSession session) {
        if(session == null) {
            return false;
        }

        return Boolean.parseBoolean(session.getAttribute(KEY_IS_ROLE_BUSINESS).toString());
    }
}
