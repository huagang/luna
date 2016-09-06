package ms.luna.web.control.merchant1;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.sc.LunaGoodsService;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.common.LunaUserSession;
import ms.luna.web.common.SessionHelper;
import ms.luna.web.control.common.BasicController;
import ms.luna.web.util.RequestHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created: by greek on 16/8/29.
 */
@Controller
@RequestMapping("/merchant/deal")
public class GoodsController extends BasicController {

    @Autowired
    private LunaGoodsService lunaGoodsService;

    private final static String menu = "deal";

    // 页面初始化
    @RequestMapping(method = RequestMethod.GET, value = "")
    public ModelAndView init(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView model = new ModelAndView("/manage_merchant.jsp");
        SessionHelper.setSelectedMenu(request.getSession(false), menu);
        return model;
    }

    // 搜索
    @RequestMapping(method = RequestMethod.GET, value = "/search")
    @ResponseBody
    public JSONObject asyncSearchGoods(
            @RequestParam(required = false, value = "offset", defaultValue = "0") int offset,
            @RequestParam(required = false, value = "limit", defaultValue = Integer.MAX_VALUE + "") int limit,
            @RequestParam(required = false, value = "keyword", defaultValue = "") String keyword,
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
            String merchant_id = RequestHelper.getString(request, "merchant_id");
            Integer business_id = RequestHelper.getInteger(request, "business_id");
//            Integer sales = RequestHelper.getInteger(request, "sales");
//            String online_status = RequestHelper.getString(request, "online_status");
            // id, account, unique_id, update_time, create_time

            boolean flag = checkParameters(name, category_id, description, pic, price, stock, transport_fee, note, merchant_id, business_id);
            if (!flag) {
                return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "valid parameters!");
            }

            LunaUserSession user = SessionHelper.getUser(request.getSession(false));
            String unique_id = user.getUniqueId();

            String[] keys = new String[]{"name", "category_id", "description", "pic", "price", "stock", "transport_fee", "note", "merchant_id", "business_id", "unique_id"};
            String[] values = new String[]{name, category_id + "", description, pic, price, stock + "", transport_fee, note, merchant_id, business_id + "", unique_id};
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
    @RequestMapping(method = RequestMethod.DELETE, value = "")
    @ResponseBody
    public JSONObject deleteGoods(
            @RequestParam(required = true, value = "ids") String ids,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            // 检查ids是否正确
            String pattern = "((\\d+),)*(\\d+)";
            boolean flag = Pattern.matches(pattern, ids.trim());
            if(!flag) {
                return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "goods ids are invalid");
            }
            JSONObject param = new JSONObject();
            param.put("ids", ids);
            JSONObject result = lunaGoodsService.deleteGoods(param);
            MsLogger.debug(request.toString());
            return result;
        } catch (Exception e) {
            MsLogger.error("Failed to delete goods. " + e.getMessage());
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to delete goods.");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @ResponseBody
    public JSONObject getGoodsInfo(@PathVariable("id") int id) {
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
            @PathVariable("id") int id,
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
            String merchant_id = RequestHelper.getString(request, "merchant_id");
            Integer business_id = RequestHelper.getInteger(request, "business_id");
//            Integer sales = RequestHelper.getInteger(request, "sales");
//            String online_status = RequestHelper.getString(request, "online_status");
            // id, account, unique_id, update_time, create_time

            boolean flag = checkParameters(name, category_id, description, pic, price, stock, transport_fee, note, merchant_id, business_id);
            if (!flag) {
                return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "valid parameters!");
            }

            LunaUserSession user = SessionHelper.getUser(request.getSession(false));
            String unique_id = user.getUniqueId();

            String[] keys = new String[]{"name", "category_id", "description", "pic", "price", "stock", "transport_fee", "note", "merchant_id", "business_id", "unique_id"};
            String[] values = new String[]{name, category_id + "", description, pic, price, stock + "", transport_fee, note, merchant_id, business_id + "", unique_id};
            JSONObject param = setParameters2Json(keys, values);
            JSONObject result = lunaGoodsService.updateGoods(param, id);
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
            @RequestParam(required = false, value = "id") int id,
            @RequestParam(required = true, value = "business_id") int business_id,
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

    // 获取类别列表
    @RequestMapping(method = RequestMethod.GET, value = "/category")
    @ResponseBody
    public JSONObject getGoodsCatgories(
            @RequestParam(required = false, value = "keyword", defaultValue = "") String keyword) {
        try{
            if(keyword == null){
                keyword = "";
            }
            JSONObject result = lunaGoodsService.getGoodsCategories(keyword);
            MsLogger.debug(result.toString());
            return result;
        } catch (Exception e) {
            MsLogger.error("Failed to get goods categories. " + e.getMessage());
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "Failed to get goods categories.");
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/onlineStatus")
    @ResponseBody
    public JSONObject updateOnlineStatus(
            @RequestParam(required = true, value = "ids") String ids,
            @RequestParam(required = false, value = "status", defaultValue = "0") byte status) {
        try {
            // 检查ids是否正确
            String pattern = "((\\d+),)*(\\d+)";
            boolean flag = Pattern.matches(pattern, ids.trim());
            if(!flag) {
                return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "goods ids are invalid");
            }

            JSONObject param = new JSONObject();
            param.put("ids", ids);
            param.put("online_status", status);
            JSONObject result = lunaGoodsService.updateOnlineStatus(param);
            MsLogger.debug(result.toString());
            return result;
        } catch (Exception e) {
            MsLogger.error("Failed to update online status. " + e.getMessage());
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "Failed to update online status.");
        }
    }

    // 参数检查
    private boolean checkParameters(String name, Integer category_id, String description, String pic, String price, Integer stock, String transport_fee, String note, String merchant_id, Integer business_id) {
        return true;
//        return isGoodsNameValid(name) && isPicValid(pic) && isStockValid(stock) && isPriceValid(price)
//                && isTransportfeeValid(transport_fee) && isBusinessIdValid(business_id) && isMerchantIdValid(merchant_id)
//                && isCategoryIdValid(category_id);

    }

    private boolean isCategoryIdValid(Integer category_id) {
        return (category_id > 0);
    }

    private boolean isMerchantIdValid(String merchant_id) {
        return !StringUtils.isBlank(merchant_id);
    }

    private boolean isBusinessIdValid(Integer business_id) {
        return (business_id > 0);
    }

    private boolean isTransportfeeValid(String transport_fee) {
        return isPicValid(transport_fee);
    }

    public static boolean isPriceValid(String price) {
        String pattern = "(\\d+)(.\\d{1,2})?";
        return Pattern.matches(pattern, price);
    }

    private boolean isStockValid(Integer stock) {
        // 待讨论,无数量限制时stock数组为多少
        return (stock > 0);
    }

    private boolean isPicValid(String pic) {
        // 允许为空.不为空时图片数量不超过5
        return (pic == null || pic.trim().split(",").length <= 5);
    }

    private boolean isGoodsNameValid(String name) {
        return ( !StringUtils.isBlank(name) && StringUtils.length(name) <= 64);
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

    public static void main(String[] args) {
        String a = "0.0";
        System.out.println(isPriceValid(a));
    }

}
