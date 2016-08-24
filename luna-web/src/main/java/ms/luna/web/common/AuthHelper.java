package ms.luna.web.common;

import com.alibaba.fastjson.JSONArray;
import ms.luna.biz.cons.LunaRoleCategoryExtra;
import ms.luna.common.LunaUserSession;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-20
 */
public class AuthHelper {

    private final static Logger logger = Logger.getLogger(AuthHelper.class);

    public static boolean hasBusinessAuth(HttpServletRequest request, int businessId) {

        HttpSession session = request.getSession(false);
        if(session == null) {
            return false;
        }

        LunaUserSession user = SessionHelper.getUser(session);

        return hasBusinessAuth(user, businessId);

    }

    public static boolean hasBusinessAuth(LunaUserSession lunaUserSession, int businessId) {

        if(lunaUserSession == null) {
            return false;
        }

        Map<String, Object> extra = lunaUserSession.getExtra();
        logger.debug("extra info: " + extra);

        if(! extra.get("type").toString().equals(LunaRoleCategoryExtra.TYPE_BUSINESS)) {
            return false;
        }

        Object value = extra.get("value");
        if(value == null) {
            return false;
        }

        if(value instanceof List<?>) {
            List<Integer> businessList = (List<Integer>) value;
            if(businessList.contains(businessId) || businessList.get(0) == 0) {
                return true;
            }

        } else if(value instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) value;
            if(jsonArray.contains(businessId) || jsonArray.getIntValue(0) == 0) {
                return true;
            }
        }

        return false;
    }
}
