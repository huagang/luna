package ms.luna.web.control.api;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.sc.LunaUserService;
import ms.luna.biz.table.LunaUserTable;
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

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    @ResponseBody
    public JSONObject login(@RequestParam(value="luna_name", required=true) String lunaName,
                                  @RequestParam(value="password", required=true) String password) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(LunaUserTable.FIELD_LUNA_NAME, lunaName);
            jsonObject.put(LunaUserTable.FIELD_PASSWORD, password);
            JSONObject result = lunaUserService.loginUser(jsonObject);

            if(! result.getString("code").equals("0")) {
                return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "用户名或密码错误");
            }
            JSONObject data = result.getJSONObject("data");
            return FastJsonUtil.sucess("success", data);

        } catch (Exception ex) {
            logger.error("Failed to login", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }

    }
}
