package ms.luna.web.control.data;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.table.LunaMenuTable;
import ms.luna.common.LunaUserSession;
import ms.luna.web.common.SessionHelper;
import ms.luna.web.control.common.BasicController;
import ms.luna.web.util.TokenUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-27
 */

@Controller
@RequestMapping("/data/album")
public class AlbumController extends BasicController {

    private final static Logger logger = Logger.getLogger(AlbumController.class);

    public static final String menu = "album";

    @RequestMapping(method = RequestMethod.GET, value = "")
    public ModelAndView init(HttpServletRequest request) {
        ModelAndView modelAndView = buildModelAndView("album_system");
        SessionHelper.setSelectedMenu(request.getSession(false), menu);
        LunaUserSession user = SessionHelper.getUser(request.getSession(false));
        Map<String, JSONObject> menuAuth = user.getMenuAuth();
        JSONObject jsonObject = menuAuth.get("/data/album");
        String url = jsonObject.getString(LunaMenuTable.FIELD_OUTER_URL);
        String uniqueId = user.getUniqueId();
        try {
            String token = TokenUtil.generateTokenByUserId(uniqueId, 30000);
            String albumUrl = String.format("%s?token=%s&unique_id=%s", url, token, uniqueId);
            modelAndView.addObject("albumUrl", albumUrl);
        } catch (Exception e) {
            logger.error("Failed to generate token", e);
            return buildModelAndView("error");
        }
        return modelAndView;
    }
}
