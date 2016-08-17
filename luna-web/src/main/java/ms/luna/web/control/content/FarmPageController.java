package ms.luna.web.control.content;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.sc.FarmPageService;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.common.FarmCommon;
import ms.luna.common.LunaUserSession;
import ms.luna.web.common.SessionHelper;
import ms.luna.web.control.common.BasicController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created: by greek on 16/7/25.
 */
@Controller
@RequestMapping("/content/app")
public class FarmPageController extends BasicController {

    @Autowired
    private FarmPageService farmPageService;

    @RequestMapping(method = RequestMethod.GET, value = "/farm/{appId}")
    public ModelAndView init(@PathVariable int appId, HttpServletRequest request) {
        try {
            ModelAndView modelAndView = buildModelAndView("edit_farmhouse");
            modelAndView.addObject("appId", appId);
            return modelAndView;

        } catch (Exception e) {
            MsLogger.error("Failed to load all pages", e);
        }

        return new ModelAndView("/error.jsp");
    }

    // 获取编辑页面信息(字段定义和值)
    @RequestMapping(method = RequestMethod.GET, value = "/farm/page/{appId}")
    @ResponseBody
    public JSONObject getEditPageInfo(@PathVariable Integer appId) throws IOException {
        try {
            JSONObject result = farmPageService.getPageDefAndInfo(appId);
            return result;
        } catch (Exception e) {
            MsLogger.error("Failed to init MsShow" + e.getMessage());
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to init MsShow");
        }

    }

    // 编辑页面
    @RequestMapping(method = RequestMethod.PUT, value = "/farm/{appId}")
    @ResponseBody
    public JSONObject savePage(
            @PathVariable("appId") Integer appId,
            @RequestParam(required = true, value = "data") String fieldsVal,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            // 获取字段定义
            if (StringUtils.isBlank(fieldsVal)) {
                return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "page数据不能为空!");
            }
            JSONObject result1 = farmPageService.getFarmFields();
            if (!"0".equals(result1.getString("code"))) {
                return result1;
            }
            JSONObject data = result1.getJSONObject("data");
            JSONArray fields = data.getJSONArray("fields");

            // 检查字段数值 -- 暂时先不检查
            FarmCommon.getInStance().checkFieldsVal(JSONObject.parseObject(fieldsVal), fields);

            // 获取用户信息
            LunaUserSession user = SessionHelper.getUser(request.getSession(false));

            // 保存
            JSONObject result = farmPageService.updatePage(fieldsVal, appId, user.getLunaName());
            MsLogger.debug(result.toString());
            return result;
        } catch (Exception e) {
            MsLogger.error("Falied to save page." + e.getMessage());
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Falied to save page");
        }

    }

    // 删除用户
    @RequestMapping(method = RequestMethod.DELETE, value = "/farm/{appId}")
    @ResponseBody
    public JSONObject delPage(@PathVariable("appId") Integer appId) {
        try {
            JSONObject result = farmPageService.delPage(appId);
            MsLogger.debug(result.toString());
            return result;
        } catch (Exception e) {
            MsLogger.error("Failed to del page." + e.getMessage());
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Falied to del page.");
        }
    }

    // 根据id获取
    @RequestMapping(method = RequestMethod.GET, value = "/categoryIds/{categoryIds}")
    @ResponseBody
    public JSONObject getShowAppByCtgrId(
            @PathVariable("categoryIds") String categoryIds,
            @RequestParam(required = false, value = "types") String types,
            @RequestParam(required = false, value = "app_status", defaultValue = "1") Integer status,
            @RequestParam(required = false, value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(required = false, value = "limit", defaultValue = "30") Integer limit) {
        try {
            // 检查types类型
            if(types != null && !checkIntegerList(types))
                return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "types is valid");
            JSONObject param = new JSONObject();
            param.put("categoryIds", categoryIds);
            param.put("types", types);
            param.put("app_status", status);
            param.put("offset", offset);
            param.put("limit", limit);
            JSONObject result = farmPageService.getShowAppsByCtgrId(param);
            MsLogger.debug(result.toString());
            return result;
        } catch (Exception e) {
            MsLogger.error("Failed to get show apps by category id" + e.getMessage());
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to get show apps by category id");
        }
    }

    /**
     * 检查整数序列,如 "1,4,3,56,9"
     */
    private boolean checkIntegerList(String tags){
        Pattern pattern = Pattern.compile("((\\d+),)*(\\d+)");
        Matcher matcher = pattern.matcher(tags);
        return matcher.matches();
    }
}
