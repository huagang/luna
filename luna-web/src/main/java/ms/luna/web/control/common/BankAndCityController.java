/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package ms.luna.web.control.common;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.sc.LunaBankBranchService;
import ms.luna.biz.sc.LunaBankService;
import ms.luna.biz.sc.LunaCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by SDLL18 on 16/8/31.
 */
@Controller
@RequestMapping("/common/bnkAndCity")
public class BankAndCityController {

    @Autowired
    private LunaCityService lunaCityService;

    @Autowired
    private LunaBankService lunaBankService;

    @Autowired
    private LunaBankBranchService lunaBankBranchService;

    @RequestMapping(method = RequestMethod.GET, value = "/city/{provinceCode}")
    @ResponseBody
    public JSONObject getCityList(@PathVariable(value = "provinceCode") Integer provinceCode,
                                  @RequestParam(value = "cityName") String cityName) {
        JSONObject inData = new JSONObject();
        inData.put("cityName", cityName);
        inData.put("provinceCode", provinceCode);
        return lunaCityService.getCityListByProvinceCodeAndName(inData);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/branch/{bankCode}")
    @ResponseBody
    public JSONObject getBranchList(@PathVariable(value = "bankCode") String bankCode,
                                    @RequestParam(value = "branchName") String branchName,
                                    @RequestParam(value = "cityCode") String cityCode) {
        JSONObject inData = new JSONObject();
        inData.put("bankCode", bankCode);
        inData.put("branchName", branchName);
        inData.put("cityCode", cityCode);
        return lunaBankBranchService.getBranchListByNameAndBank(inData);
    }
}
