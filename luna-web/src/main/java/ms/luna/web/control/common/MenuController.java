package ms.luna.web.control.common;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.sc.MenuService;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.common.LunaUserSession;
import ms.luna.web.common.SessionHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-08-05
 */

@Controller
@RequestMapping("/common/menu")
public class MenuController extends BasicController {

    private final static Logger logger = Logger.getLogger(MenuController.class);

    @Autowired
    private MenuService menuService;

    @RequestMapping(method = RequestMethod.GET, value = "")
    @ResponseBody
    public JSONObject init(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if(session != null) {
            LunaUserSession lunaUserSession = (LunaUserSession) session.getAttribute(SessionHelper.KEY_USER);
            List<Integer> roleIds = lunaUserSession.getRoleIds();
            if(roleIds != null && roleIds.size() > 0) {
                JSONObject moduleAndMenuByRoleId = menuService.getModuleAndMenuByRoleId(roleIds.get(0));
                logger.trace(moduleAndMenuByRoleId);
                return moduleAndMenuByRoleId;
            } else {
                return FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "没有权限获取菜单");
            }
        } else {
            return FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "没有权限获取菜单");
        }

    }

}
