package ms.luna.web.control.merchant;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.sc.DealService;
import ms.luna.biz.sc.LunaGoodsService;
import ms.luna.biz.table.*;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.FormFieldValidator;
import ms.luna.biz.util.MsLogger;
import ms.luna.common.LunaUserSession;
import ms.luna.web.common.AuthHelper;
import ms.luna.web.common.SessionHelper;
import ms.luna.web.control.common.BasicController;
import ms.luna.web.util.RequestHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
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
    private DealService dealService;

    private final static String menu = "deal";

    // 页面初始化
    @RequestMapping(method = RequestMethod.GET, value = "")
    public ModelAndView init(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView model = new ModelAndView("/manage_goods.jsp");
        SessionHelper.setSelectedMenu(request.getSession(false), menu);
        return model;
    }

    // 添加页面初始化
    @RequestMapping(method = RequestMethod.GET, value = "", params = "create")
    public ModelAndView initAddPage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView model = new ModelAndView("/manage_goods.jsp");
        SessionHelper.setSelectedMenu(request.getSession(false), menu);
        return model;
    }

    // 编辑页面初始化
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ModelAndView initEditPage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView model = new ModelAndView("/manage_goods.jsp");
        SessionHelper.setSelectedMenu(request.getSession(false), menu);
        return model;
    }

    // 搜索
    @RequestMapping(method = RequestMethod.GET, value = "/search")
    @ResponseBody
    public JSONObject asyncSearchGoods(
            @RequestParam(required = false, value = "offset", defaultValue = "0") int offset,
            @RequestParam(required = false, value = "limit", defaultValue = "30") int limit,
            @RequestParam(required = false, value = "keyword", defaultValue = "") String keyword,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Integer business_id = RequestHelper.getInteger(request, "business_id");
            if(business_id <= 0 ) {
                return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "业务id不合法");
            }
            JSONObject param = new JSONObject();
            param.put("offset", offset);
            param.put("limit", limit);
            param.put("keyword", keyword);
            param.put("business_id", business_id);
            JSONObject result = dealService.loadDeals(param);
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
            int businessId = RequestHelper.getInteger(request, MsBusinessTable.FIELD_BUSINESS_ID);
            if(businessId < 0) {
                return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "业务Id不合法");
            }

            LunaUserSession user = SessionHelper.getUser(request.getSession(false));
            if(AuthHelper.hasBusinessAuth(user, businessId)) {
                return FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "没有业务权限");
            }

            int cateId = RequestHelper.getInteger(request, DealTable.FIELD_CATEGORY_ID);
            JSONObject dealFieldsByCateId = dealService.getDealFieldsByCateId(cateId);
            if(! dealFieldsByCateId.getString("code").equals("0")) {
                return dealFieldsByCateId;
            }

            JSONObject formObject = new JSONObject();
            JSONArray fields = dealFieldsByCateId.getJSONArray("data");
            for(int i = 0; i < fields.size(); i++) {
                JSONObject field = fields.getJSONObject(i);
                String fieldName = field.getString(FormFieldTable.FIELD_FIELD_NAME);
                formObject.put(fieldName, request.getParameter(fieldName));
            }
            Pair<Boolean, String> validate = FormFieldValidator.validate(fields, formObject);

            if(! validate.getKey()) {
                return FastJsonUtil.error(ErrorCode.INVALID_PARAM, validate.getRight());
            }

            String uniqueId = user.getUniqueId();
            formObject.put(LunaUserTable.FIELD_ID, uniqueId);
            formObject.put(MsBusinessTable.FIELD_BUSINESS_ID, businessId);

            JSONObject result = dealService.createDeal(formObject);
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
            JSONObject result = dealService.deleteDeal(ids);
            MsLogger.debug(request.toString());
            return result;
        } catch (Exception e) {
            MsLogger.error("Failed to delete goods. " + e.getMessage());
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to delete goods.");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @ResponseBody
    public JSONObject getGoodsInfo(@PathVariable("id") String id) {
        try {
            JSONObject result = dealService.getDealById(id);
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
            @PathVariable("id") String id,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            int businessId = RequestHelper.getInteger(request, MsBusinessTable.FIELD_BUSINESS_ID);
            if(businessId < 0) {
                return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "业务Id不合法");
            }

            LunaUserSession user = SessionHelper.getUser(request.getSession(false));
            if(AuthHelper.hasBusinessAuth(user, businessId)) {
                return FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "没有业务权限");
            }

            int cateId = RequestHelper.getInteger(request, DealTable.FIELD_CATEGORY_ID);
            JSONObject dealFieldsByCateId = dealService.getDealFieldsByCateId(cateId);
            if(! dealFieldsByCateId.getString("code").equals("0")) {
                return dealFieldsByCateId;
            }

            JSONObject formObject = new JSONObject();
            JSONArray fields = dealFieldsByCateId.getJSONArray("data");
            for(int i = 0; i < fields.size(); i++) {
                JSONObject field = fields.getJSONObject(i);
                String fieldName = field.getString(FormFieldTable.FIELD_FIELD_NAME);
                formObject.put(fieldName, request.getParameter(fieldName));
            }
            Pair<Boolean, String> validate = FormFieldValidator.validate(fields, formObject);

            if(! validate.getKey()) {
                return FastJsonUtil.error(ErrorCode.INVALID_PARAM, validate.getRight());
            }

            String uniqueId = user.getUniqueId();
            formObject.put(DealTable.FIELD_ID, id);
            formObject.put(LunaUserTable.FIELD_ID, uniqueId);
            formObject.put(MsBusinessTable.FIELD_BUSINESS_ID, businessId);

            JSONObject result = dealService.updateDeal(formObject);
            MsLogger.debug(request.toString());
            return result;
        } catch (Exception e) {
            MsLogger.error("Failed to create goods. " + e.getMessage());
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to create goods.");
        }
    }

    // 检查商品名称
    @RequestMapping(method = RequestMethod.GET, value = "/checkName")
    @ResponseBody
    public JSONObject checkName(
            @RequestParam(required = true, value = "name") String name,
            @RequestParam(required = false, value = "id") String id,
            @RequestParam(required = true, value = "business_id") int businessId,
            HttpServletRequest request, HttpServletResponse response) {
        try{
            JSONObject result = dealService.checkDealName(name, id, businessId);
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
            JSONObject result = dealService.getGoodsCategories(keyword);
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

        return null;
    }

}
