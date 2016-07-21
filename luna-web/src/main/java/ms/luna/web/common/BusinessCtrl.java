package ms.luna.web.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.sc.ManageBusinessService;
import ms.luna.biz.table.MsBusinessTable;
import ms.luna.biz.util.FastJsonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-20
 */

@Controller("businessCtrl")
@RequestMapping("/common/business")
public class BusinessCtrl extends BasicCtrl {

    private final static Logger logger = Logger.getLogger(BusinessCtrl.class);

    @Autowired
    private ManageBusinessService manageBusinessService;

    @RequestMapping(method = RequestMethod.GET, value = "/init")
    public ModelAndView init() {
        ModelAndView modelAndView = buildModelAndView("/businessSelector");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public JSONObject readAllBusinessForUser(HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {

        logger.info("Read all business");
        JSONObject jsonQuery = new JSONObject();
        JSONObject businessResult = manageBusinessService.loadBusinesses(jsonQuery.toString());
        if(! businessResult.getString("code").equals("0")) {
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取业务失败");
        }

        JSONObject data = businessResult.getJSONObject("data");
        JSONArray rows = data.getJSONArray("rows");
        Map<String, List<JSONObject>> businessMap = new HashMap<>();
        for(int i = 0; i < rows.size(); i++) {
            JSONObject row = rows.getJSONObject(i);
            JSONObject businessInfo = new JSONObject();
            businessInfo.put(MsBusinessTable.FIELD_BUSINESS_ID, row.getInteger(MsBusinessTable.FIELD_BUSINESS_ID));
            businessInfo.put(MsBusinessTable.FIELD_BUSINESS_NAME, row.getString(MsBusinessTable.FIELD_BUSINESS_NAME));
            String categoryName = row.getString("business_category");
            if(categoryName == null) {
                logger.warn("Invalid business category");
                continue;
            }
            List<JSONObject> businessInfoList = businessMap.get(categoryName);
            if(businessInfoList == null) {
                businessInfoList = new ArrayList<JSONObject>();
                businessMap.put(categoryName, businessInfoList);
            }
            businessInfoList.add(businessInfo);
        }
        return FastJsonUtil.sucess("success", businessMap);
    }
}
