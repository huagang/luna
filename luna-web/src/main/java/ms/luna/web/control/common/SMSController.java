/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package ms.luna.web.control.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.sc.PicCodeService;
import ms.luna.biz.sc.SMSService;
import ms.luna.biz.util.FastJsonUtil;
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

    @Autowired
    private PicCodeService picCodeService;

    @RequestMapping(method = RequestMethod.POST, value = "/getCode")
    @ResponseBody
    public JSONObject getCode(@RequestParam String key,
                              @RequestParam String code,
                              @RequestParam String phoneNo,
                              @RequestParam String target) {
        JSONObject r = picCodeService.checkCode(key, code);
        if (r.getJSONObject("data").getBoolean("result")) {
            JSONObject inData = new JSONObject();
            inData.put("uniqueId", phoneNo);
            inData.put("phoneNo", phoneNo);
            inData.put("target", target);
            inData.put("time", 120000L);
            JSONObject result = smsService.sendCode(inData);
            return result;
        } else {
            return FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "图片验证码错误");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/checkCode")
    @ResponseBody
    public JSONObject checkCode(@RequestParam String phone,
                                @RequestParam String target,
                                @RequestParam String code) {
        JSONObject inData = new JSONObject();
        inData.put("uniqueId", phone);
        inData.put("target", target);
        inData.put("code", code);
        JSONObject result = smsService.checkCode(inData);
        return result;
    }

}
