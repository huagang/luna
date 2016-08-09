package ms.luna.web.control.common;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-08-09
 */
@Controller
@RequestMapping("/common/test")
public class TestController {

    private final static Logger logger = Logger.getLogger(TestController.class);

    @RequestMapping(method = RequestMethod.GET, value = "/{name}")
    @ResponseBody
    public JSONObject testPath(@PathVariable String name) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("company", "微景天下");
        logger.info("name:" + name);
        return jsonObject;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public JSONObject testParam(@RequestParam(value = "name") String name) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("company", "微景天下");
        logger.info("name:" + name);
        return jsonObject;

    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public JSONObject testParamPost(HttpServletRequest request) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", request.getParameter("name"));
        jsonObject.put("company", "微景天下");
        logger.info("name:" + request.getParameter("name"));
        return jsonObject;
    }
}
