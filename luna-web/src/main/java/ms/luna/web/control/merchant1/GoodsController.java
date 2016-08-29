package ms.luna.web.control.merchant1;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.sc.LunaGoodsService;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.common.LunaUserSession;
import ms.luna.web.common.SessionHelper;
import ms.luna.web.control.common.BasicController;
import ms.luna.web.util.RequestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;

/**
 * Created: by greek on 16/8/29.
 */
@Controller
@RequestMapping("/merchant/goods")
public class GoodsController extends BasicController {

    @Autowired
    private LunaGoodsService lunaGoodsService;

    private final static String menu = "goods";

    // 页面初始化
    @RequestMapping(method = RequestMethod.GET, value = "")
    public ModelAndView init(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView model = new ModelAndView("/manage_goods.jsp");
        SessionHelper.setSelectedMenu(request.getSession(false), menu);
        return model;
    }

    // 搜索
    @RequestMapping(method = RequestMethod.GET, value = "/search")
    @ResponseBody
    public JSONObject asyncSearchGoods(
            @RequestParam(required = false, defaultValue = "0") Integer offset,
            @RequestParam(required = false, defaultValue = Integer.MAX_VALUE + "") Integer limit,
            @RequestParam(required = false, defaultValue = "") String keyword,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            JSONObject param = new JSONObject();
            param.put("offset", offset);
            param.put("limit", limit);
            param.put("keyword", keyword);
            JSONObject result = lunaGoodsService.loadGoods(param);
            MsLogger.debug(result.toString());
            return result;
        } catch (Exception e) {
            MsLogger.error("Failed to search goods. " + e.getMessage());
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to search goods.");
        }

    }

    // 创建商品
    @RequestMapping(method = RequestMethod.POST, value = "")
    @ResponseBody
    public JSONObject createGoods(HttpServletRequest request) {
        try {
            String name = RequestHelper.getString(request, "name");
            Integer category_id = RequestHelper.getInteger(request, "category_id");
            String description = RequestHelper.getString(request, "description");
            String pic = RequestHelper.getString(request, "pic");
            String price = RequestHelper.getString(request, "price");
            Integer stock = RequestHelper.getInteger(request, "stock");
            String transport_fee = RequestHelper.getString(request, "transport_fee");
            String note = RequestHelper.getString(request, "note");
            Integer sales = RequestHelper.getInteger(request, "sales");
            String online_status = RequestHelper.getString(request, "online_status");
            String merchant_id = RequestHelper.getString(request, "merchant_id");
            Integer business_id = RequestHelper.getInteger(request, "business_id");
            // id, account, unique_id, update_time, create_time

            String message = checkParameters(name, category_id, description, pic, price, stock, transport_fee, note, sales, online_status, merchant_id, business_id);
            if (message != null && message.length() != 0) {
                return FastJsonUtil.error(ErrorCode.INVALID_PARAM, message);
            }

            LunaUserSession user = SessionHelper.getUser(request.getSession(false));
            String unique_id = user.getUniqueId();

            String[] keys = new String[]{"name", "category_id", "description", "pic", "price", "stock", "transport_fee", "note", "sales", "online_status", "merchant_id", "business_id", "unique_id"};
            String[] values = new String[]{name, category_id + "", description, pic, price, stock + "", transport_fee, note, sales + "", online_status, merchant_id, business_id + "", unique_id};
            JSONObject param = setParameters2Json(keys, values);
            JSONObject result = lunaGoodsService.createGoods(param);
            MsLogger.debug(request.toString());
            return result;
        } catch (Exception e) {
            MsLogger.error("Failed to create goods. " + e.getMessage());
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to create goods.");
        }
    }

    // 删除商品
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @ResponseBody
    public JSONObject deleteGoods(
            @PathVariable("id") Integer id,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            JSONObject result = lunaGoodsService.deleteGoods(id);
            MsLogger.debug(request.toString());
            return result;
        } catch (Exception e) {
            MsLogger.error("Failed to delete goods. " + e.getMessage());
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to delete goods.");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @ResponseBody
    public JSONObject getGoodsInfo(@PathVariable("id") Integer id) {
        try {
            JSONObject result = lunaGoodsService.getGoodsInfo(id);
            MsLogger.debug(result.toString());
            return result;
        } catch (Exception e) {
            MsLogger.error("Failed to get goods info. " + e.getMessage());
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to get goods info.");
        }
    }

    // 编辑商品
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    @ResponseBody
    public JSONObject updateGoods(
            @PathVariable("id") Integer id,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            String name = RequestHelper.getString(request, "name");
            Integer category_id = RequestHelper.getInteger(request, "category_id");
            String description = RequestHelper.getString(request, "description");
            String pic = RequestHelper.getString(request, "pic");
            String price = RequestHelper.getString(request, "price");
            Integer stock = RequestHelper.getInteger(request, "stock");
            String transport_fee = RequestHelper.getString(request, "transport_fee");
            String note = RequestHelper.getString(request, "note");
            Integer sales = RequestHelper.getInteger(request, "sales");
            String online_status = RequestHelper.getString(request, "online_status");
            String merchant_id = RequestHelper.getString(request, "merchant_id");
            Integer business_id = RequestHelper.getInteger(request, "business_id");
            // id, account, unique_id, update_time, create_time

            String message = checkParameters(name, category_id, description, pic, price, stock, transport_fee, note, sales, online_status, merchant_id, business_id);
            if (message != null && message.length() != 0) {
                return FastJsonUtil.error(ErrorCode.INVALID_PARAM, message);
            }

            LunaUserSession user = SessionHelper.getUser(request.getSession(false));
            String unique_id = user.getUniqueId();

            String[] keys = new String[]{"id", "name", "category_id", "description", "pic", "price", "stock", "transport_fee", "note", "sales", "online_status", "merchant_id", "business_id", "unique_id"};
            String[] values = new String[]{id+"", name, category_id + "", description, pic, price, stock + "", transport_fee, note, sales + "", online_status, merchant_id, business_id + "", unique_id};
            JSONObject param = setParameters2Json(keys, values);
            JSONObject result = lunaGoodsService.updateGoods(param);
            MsLogger.debug(request.toString());
            return result;
        } catch (Exception e) {
            MsLogger.error("Failed to update goods. " + e.getMessage());
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to update goods.");
        }
    }

    // 检查商品名称
    @RequestMapping(method = RequestMethod.GET, value = "/checkName")
    @ResponseBody
    public JSONObject checkName(
            @RequestParam(required = true, value = "name") String name,
            @RequestParam(required = false, value = "id") Integer id,
            @RequestParam(required = true, value = "business_id") Integer business_id,
            HttpServletRequest request, HttpServletResponse response) {
        try{
            JSONObject result = lunaGoodsService.checkGoodsName(name, id, business_id);
            MsLogger.debug(result.toString());
            return result;
        } catch (Exception e) {
            MsLogger.error("Failed to check goods name. " + e.getMessage());
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "Failed to check goods name");
        }
    }

    // 参数检查
    private String checkParameters(String name, Integer category_id, String description, String pic, String price, Integer stock, String transport_fee, String note, Integer sales, String online_status, String merchant_id, Integer business_id) {
        return "";
    }

    // 将参数对放入json
    private JSONObject setParameters2Json(String[] keys, String[] values) {
        JSONObject param = new JSONObject();
        if (keys == null || values == null || keys.length != values.length) {
            throw new RuntimeException("keys doesn't consistent with values");
        }
        for (int i = 0; i < keys.length; i++) {
            param.put(keys[i], values[i]);
        }
        return param;
    }

}
