package ms.luna.web.control.api;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.web.util.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-08-03
 */

@Controller
@RequestMapping("/api")
public class TokenController {

    private final static Logger logger = Logger.getLogger(TokenController.class);
    @RequestMapping(method = RequestMethod.GET, value = "/verifyToken")
    @ResponseBody
    public JSONObject verifyToken(@RequestParam(required = true) String token,
                                  @RequestParam(required = false) String uniqueId) {


        boolean isValid = false;
        try {
            if (StringUtils.isBlank(uniqueId)) {
                isValid = TokenUtil.verifyToken(token);
            } else {
                isValid = TokenUtil.verifyToken(uniqueId, token);
            }
        } catch (Exception ex) {
            logger.error("Failed to verify token", ex);
        }

        if(isValid) {
            return FastJsonUtil.sucess("success");
        }
        return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "Invalid token");
    }

//    @RequestMapping(method = RequestMethod.GET, value = "/generateToken")
//    @ResponseBody
//    public JSONObject generateToken() {
//
//        String token = "";
//        try {
//            token = TokenUtil.generateRandomToken(20000);
//        } catch (Exception ex) {
//            logger.error("Failed to verify token", ex);
//        }
//
//        if(StringUtils.isNotBlank(token)) {
//            return FastJsonUtil.sucess("success", token);
//        }
//        return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "Failed to generate token");
//    }

}
