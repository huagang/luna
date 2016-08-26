package ms.luna.web.control.api;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.sc.LunaUserService;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.web.util.TokenUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-08-26
 */

@Controller
@RequestMapping("/api")
public class AuthController {

    private final static Logger logger = Logger.getLogger(AuthController.class);

    @Autowired
    private LunaUserService lunaUserService;

    @RequestMapping(method = RequestMethod.GET, value = "/userInfo")
    @ResponseBody
    public JSONObject getUserInfo(@RequestParam(required = true, value = "token") String token,
                                  @RequestParam(required = true, value = "unique_id") String uniqueId) {
        try {
            if(TokenUtil.verifyToken(uniqueId, token)) {
                JSONObject userAuth = lunaUserService.getUserAuth(uniqueId);
                if(userAuth.getString("code").equals("0")) {
                    return FastJsonUtil.sucess("success", userAuth.getJSONObject("data"));
                } else {
                    return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
                }
            } else {
                return FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "token不合法");
            }
        } catch (Exception ex) {
            logger.error("Failed to verify token", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }

    }
}
