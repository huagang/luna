/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package ms.luna.web.control.common;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.sc.SMSService;
import ms.luna.common.LunaUserSession;
import ms.luna.web.common.SessionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by SDLL18 on 16/8/29.
 */
@Controller
@RequestMapping("/common/sms")
public class SMSController {

    @Autowired
    private SMSService smsService;

    @RequestMapping(method = RequestMethod.POST, value = "/getCode")
    @ResponseBody
    public JSONObject getCode(HttpServletRequest request,
                              @RequestParam String phoneNo,
                              @RequestParam String target) {
        HttpSession session = request.getSession(false);
        JSONObject inData = new JSONObject();
        if (session != null) {
            LunaUserSession user = SessionHelper.getUser(session);
            inData.put("uniqueId", user.getUniqueId());
        }
        inData.put("phoneNo", phoneNo);
        inData.put("target", target);
        return smsService.sendCode(inData);
    }
    
}
