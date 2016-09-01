/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package ms.luna.web.control.platform;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.sc.LunaTradeApplicationService;
import ms.luna.biz.sc.ManageMerchantService;
import ms.luna.biz.table.LunaTradeApplicationTable;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.common.LunaUserSession;
import ms.luna.web.common.SessionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by SDLL18 on 16/8/31.
 */
@Controller
@RequestMapping("/platform/message")
public class CheckTradeApplicationController {

    @Autowired
    private LunaTradeApplicationService lunaTradeApplicationService;

    @Autowired
    private ManageMerchantService manageMerchantService;

    @RequestMapping(method = RequestMethod.POST, value = "/check/{applicationId}")
    @ResponseBody
    public JSONObject checkApplication(HttpServletRequest request,
                                       @PathVariable Integer applicationId,
                                       @RequestParam Integer checkResult) {
        LunaUserSession user = SessionHelper.getUser(request.getSession(false));
        if (user == null) {
            return FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "无权操作");
        }
        if (user.getRoleId() != 1 && user.getRoleId() != 2 && user.getRoleId() != 3) {
            return FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "无权操作");
        }
        JSONObject inData = new JSONObject();
        inData.put(LunaTradeApplicationTable.FIELD_APP_CHECK_RESULT, checkResult);
        inData.put(LunaTradeApplicationTable.FIELD_ID, applicationId);
        return lunaTradeApplicationService.checkApplication(inData);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get/{applicationId}")
    @ResponseBody
    public JSONObject getApplication(HttpServletRequest request,
                                     @PathVariable Integer applicationId) {
        LunaUserSession user = SessionHelper.getUser(request.getSession(false));
        if (user == null) {
            return FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "无权操作");
        }
        if (user.getRoleId() != 1 && user.getRoleId() != 2) {
            return FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "无权操作");
        }
        JSONObject inData = new JSONObject();
        inData.put(LunaTradeApplicationTable.FIELD_ID, applicationId);
        return lunaTradeApplicationService.getApplication(inData);
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    public ModelAndView init(HttpServletRequest request, HttpServletResponse response) {
//        SessionHelper.setSelectedMenu(request.getSession(false), menu);
        return new ModelAndView("/manage_merchant_apply.jsp");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/page/{applyID}")
    public ModelAndView applyDetail(HttpServletRequest request, HttpServletResponse response) {

//        SessionHelper.setSelectedMenu(request.getSession(false), menu);
        return new ModelAndView("/merchant_detail.jsp");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getList")
    @ResponseBody
    public JSONObject getApplicationList(HttpServletRequest request,
                                         @RequestParam(value = "offset", required = false) Integer offset,
                                         @RequestParam(value = "limit", required = false) Integer limit) {
        LunaUserSession user = SessionHelper.getUser(request.getSession(false));
        if (user == null) {
            return FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "无权操作");
        }
        if (user.getRoleId() != 1 && user.getRoleId() != 2) {
            return FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "无权操作");
        }
        if (offset != null && limit != null) {
            return lunaTradeApplicationService.getApplicationList(offset, limit);
        } else {
            return lunaTradeApplicationService.getApplicationList();
        }

    }
}
