package ms.luna.web.control.merchant;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.sc.LunaOrderService;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.common.LunaUserSession;
import ms.luna.web.common.SessionHelper;
import ms.luna.web.control.common.BasicController;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: greek@visualbusiness.com
 * @Date: 2016-09-13
 */
@Controller
@RequestMapping("/merchant/order")
public class OrderController extends BasicController {

    private static final String menu = "order";

    private static final Logger logger = Logger.getLogger(OrderController.class);

    @Autowired
    private LunaOrderService lunaOrderService;

    // 订单页面初始化
    @RequestMapping(method = RequestMethod.GET, value = "")
    public ModelAndView initPage(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("manage_order.jsp");
        SessionHelper.setSelectedMenu(request.getSession(false), menu);
        return modelAndView;
    }

    // 订单详情页初始化
    @RequestMapping(method = RequestMethod.GET, value = "/info")
    public ModelAndView initInfoPage(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("manage_order_info.jsp");
        return modelAndView;
    }

    // 搜索列表页
    @RequestMapping(method = RequestMethod.GET, value = "/search")
    @ResponseBody
    public JSONObject searchOrders(
            @RequestParam(required = false, value = "keyword", defaultValue = "") String keyword,
            @RequestParam(required = false, value = "offset", defaultValue = "0") int offset,
            @RequestParam(required = false, value = "limit", defaultValue = "" + 30) int limit,
            HttpServletRequest request) {
        try {
            String unique_id = getUniqueId(request);
            JSONObject param = new JSONObject();
            param.put("offset", offset < 0 ? 0 : offset);
            param.put("limit", limit < 0 ? 0 : limit);
            param.put("keyword", (keyword == null) ? "" : keyword);
            param.put("unique_id", getUniqueId(request));
            JSONObject result = lunaOrderService.searchOrders(param);
            logger.debug(result.toString());
            return result;
        } catch (Exception e) {
            logger.error("Failed to search orders.", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to search orders.");
        }
    }

    // 订单详情
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @ResponseBody
    public JSONObject getOrderInfo(@PathVariable("id") int id, HttpServletRequest request) {
        try {
            if (id <= 0) {
                return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "order id is invalid");
            }
            LunaUserSession user = SessionHelper.getUser(request.getSession(false));
            String unique_id = user.getUniqueId();
            Integer role_id = user.getRoleId();
            JSONObject result = lunaOrderService.getOrderInfo(id, unique_id, role_id);
            logger.debug(result.toString());
            return result;
        } catch (Exception e) {
            logger.error("Failed to get order info.", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to get order info.");
        }
    }

    // 消费凭证验证
    @RequestMapping(method = RequestMethod.GET, value = "/checkCertificate")
    @ResponseBody
    public JSONObject checkCertificateNum(
            @RequestParam(required = true, value = "certificate_num") String certificate_num,
            HttpServletRequest request) {
        try {
            if(StringUtils.isBlank(certificate_num)) {
                return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "certificate_num is invalid");
            }
            JSONObject result = lunaOrderService.checkCertificateNum(certificate_num, getUniqueId(request));
            logger.debug(result.toString());
            return result;
        } catch (Exception e) {
            logger.error("Failed to check certificate number.", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to check certificate number.");
        }
    }

    // 消费凭证确认(入住办理)
    @RequestMapping(method = RequestMethod.PUT, value = "/regist")
    @ResponseBody
    public JSONObject registrationConfirm(
            @RequestParam(required = true, value = "id") int id,
            HttpServletRequest request) {
        try {
            if (id <= 0) {
                return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "order id is invalid");
            }
            JSONObject result = lunaOrderService.registrationConfirm(id, getUniqueId(request));
            logger.debug(result.toString());
            return result;
        } catch (Exception e) {
            logger.error("入住办理失败.", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "入住办理失败.");
        }
    }

    // 商户取消订单
    @RequestMapping(method = RequestMethod.PUT, value = "/cancel")
    @ResponseBody
    public JSONObject cancelOrder(
            @RequestParam(required = true, value = "id") int id,
            HttpServletRequest request) {
        try {
            if (id <= 0) {
                return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "order id is invalid");
            }
            JSONObject result = lunaOrderService.cancelOrder(id, getUniqueId(request));
            logger.debug(result.toString());
            return result;
        } catch (Exception e) {
            logger.error("Failed to cancel order.", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to cancel order.");
        }
    }

    // 订单逾期确认(商户释放房间)
    @RequestMapping(method = RequestMethod.PUT, value = "/release")
    @ResponseBody
    public JSONObject releaseOrder(
            @RequestParam(required = true, value = "id") int id,
            HttpServletRequest request) {
        try {
            if (id <= 0) {
                return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "order id is invalid");
            }
            JSONObject result = lunaOrderService.releaseOrder(id, getUniqueId(request));
            logger.debug(result.toString());
            return result;
        } catch (Exception e) {
            logger.error("Failed to release room status.", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to release room status.");
        }
    }

    private String getUniqueId(HttpServletRequest request) throws Exception {
        LunaUserSession user = SessionHelper.getUser(request.getSession(false));
        return user.getUniqueId();
    }

}
