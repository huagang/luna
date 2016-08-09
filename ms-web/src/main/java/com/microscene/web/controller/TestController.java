package com.microscene.web.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-28
 */

@Controller
@RequestMapping("/testResponse")
public class TestController {

    private final static Logger logger = Logger.getLogger(TestController.class);

    @RequestMapping(method = RequestMethod.GET, value = "/{content}")
    @ResponseBody
    JSONObject index(@PathVariable String content, @RequestParam(required = false) String q) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("content", content);
        jsonObject.put("provider", "微景天下");
        jsonObject.put("query", q);

        logger.info("request content: " + content);
        return jsonObject;
    }
}
