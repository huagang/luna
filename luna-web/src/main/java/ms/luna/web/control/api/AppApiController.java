package ms.luna.web.control.api;

import com.alibaba.fastjson.JSONObject;
import ms.luna.web.control.content.AppEditController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-08-04
 */

@Controller
@RequestMapping("/api")
public class AppApiController {

    @Autowired
    private AppEditController appEditController;

    @RequestMapping(method = RequestMethod.PUT, value = "/app/publish/{appId}")
    @ResponseBody
    public JSONObject publishApp(@PathVariable int appId,
                                 @RequestParam(required = true, value = "token") String token,
                                 @RequestParam(required = true, value = "app_addr") String appAddr,
                                 HttpServletRequest request) throws IOException {


        return appEditController.publishApp(appId, request);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/app/setting/{appId}")
    @ResponseBody
    public JSONObject getSettingOfApp(@PathVariable int appId) throws IOException {
        return appEditController.getSettingOfApp(appId);
    }
}
