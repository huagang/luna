package ms.luna.web.control;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.model.MsUser;
import ms.luna.biz.sc.FarmPageService;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.web.common.BasicCtrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import sun.management.snmp.jvminstr.JvmOSImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-11
 */

@Controller("FarmPageCtrl")
@RequestMapping("/farm.do")
public class FarmPageCtrl extends BasicCtrl {

    @Autowired
    private FarmPageService farmPageService;

    private static final String INIT = "method=init";

    private static final String CHREATEPAGE = "method=create_page";

    private static final String SAVEPAGE = "method=save_page";

    private static final String DELPAGE = "method=del_page";

    private static final String LOADPAGE = "method=load_page";

    private static final String PREVIEW = "method=preview_page";

//    private static final String

    @RequestMapping(params = INIT)
    public ModelAndView init(
            @RequestParam(required = true, value = "app_name") String app_name,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            session.setAttribute("menu_selected", "manage_router");
//        }

        // TODO 初始化页面
        ModelAndView modelAndView = buildModelAndView("/manage_router");

        try {
            // 名称检查
            boolean flag = checkAppName();
            if(!flag) {
                return buildModelAndView("/error");
            }

            // TODO 获取业务id
            Integer business_id = 46;

            // TODO 获取创建人信息;
            if (session == null) {
                throw new RuntimeException("session is null");
            }
            MsUser msUser = (MsUser) session.getAttribute("msUser");
            String unique_id = msUser.getUniqueId();

            JSONObject param = new JSONObject();
            param.put("business_id", business_id);
            param.put("unique_id", unique_id);
            param.put("app_name", app_name);

            JSONObject result = farmPageService.initPage(param.toString());
            MsLogger.debug(result.toString());

            // TODO POI字段定义和初始化数值 fields


            return modelAndView;
        } catch (Exception e) {
            MsLogger.error("Failed to init." + e.getMessage());
            return buildModelAndView("/error");
        }
    }


    @RequestMapping(params = SAVEPAGE)
    @ResponseBody
    public JSONObject savePage(
            @RequestParam(required = true, value = "app_id") String app_id,
            HttpServletRequest request, HttpServletResponse response) {
         try{
             // 获取字段定义
             JSONObject fields = getField();

             // 获取具体字段数值
             JSONObject data = convertParams2Json(request, fields);

             data.put("app_id", app_id);
             JSONObject result = farmPageService.editPage(data.toString());
             MsLogger.debug(result.toString());

             return result;

         } catch (Exception e) {
             return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Falied to save page." + e.getMessage());
         }

    }

    @RequestMapping(params = DELPAGE)
    @ResponseBody
    public JSONObject delPage(
        @RequestParam(required = true, value = "app_id") Integer app_id,
        HttpServletRequest request, HttpServletResponse response) {
        try{
            JSONObject result = farmPageService.delPage(app_id);
            MsLogger.debug(result.toString());

            return result;

        } catch (Exception e) {
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Falied to del page." + e.getMessage());
        }
    }

    public JSONObject previewPage(

    )


}
