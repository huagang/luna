package ms.luna.web.control.common;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-08-09
 */
@Controller
@RequestMapping("/common/test")
public class TestController {

    @RequestMapping(method = RequestMethod.GET, value = "/{name}")
    @ResponseBody
    public JSONObject testPath(@PathVariable String name) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("company", "微景天下");

        return jsonObject;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public JSONObject testParam(@RequestParam(value = "name") String name) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("company", "微景天下");

        return jsonObject;

    }
}
