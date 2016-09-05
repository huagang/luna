package ms.luna.web.common;

import com.alibaba.fastjson.JSONObject;
import ms.luna.common.LunaUserSession;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-08-31
 */
public class MenuHelper {

    /**
     *
     * @param session
     * @param menu session中存的menu是带着模块信息的(如:/data/poi),菜单不会同名,暂时通过子字符串匹配找对应的url
     * @return
     */
    public static String getMenuUrl(HttpSession session, String menu) {
        if(session == null || StringUtils.isBlank(menu)) {
            return "";
        }

        LunaUserSession user = SessionHelper.getUser(session);
        Map<String, JSONObject> menuAuth = user.getMenuAuth();

        for(String menuUrl : menuAuth.keySet()) {
            if(menuUrl.contains(menu)) {
                return menuUrl;
            }
        }
        return "";
    }
}
