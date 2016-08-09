package ms.luna.web.control;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.model.MsUser;
import ms.luna.biz.sc.FarmPageService;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.common.FarmCommon;
import ms.luna.web.common.BasicCtrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sun.management.snmp.jvminstr.JvmOSImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * Created by greek on 16/7/25.
 */
@Controller("FarmPageCtrl")
@RequestMapping("/content/app")
public class FarmPageController extends BasicCtrl {

    @Autowired
    private FarmPageService farmPageService;

    private static final String INIT = "method=init";

    private static final String CHREATEPAGE = "method=create_page";

    private static final String SAVEPAGE = "method=save_page";

    private static final String DELPAGE = "method=del_page";

    private static final String LOADPAGE = "method=load_page";

    private static final String PREVIEW = "method=preview_page";

//    private static final String

//    @RequestMapping(params = INIT)
    @RequestMapping(method = RequestMethod.GET, value = "GET")
    public ModelAndView init(
            @RequestParam(required = true, value = "app_name") String app_name,
            HttpServletRequest request, HttpServletResponse response) throws IOException {

        // TODO 初始化页面
        ModelAndView modelAndView = buildModelAndView("/manage_router");

        try {
            // TODO 名称检查--基础版,开发版和数据版需要名称检查,应写成同一个.
            boolean flag = checkAppName();
            if(!flag) {
                return buildModelAndView("/error");
            }

            // TODO 获取业务id--get from session
            Integer business_id = 46;

            // TODO 获取创建人信息--get from session
            HttpSession session = request.getSession(false);
            if (session == null) {
                throw new RuntimeException("session is null");
            }
            MsUser msUser = (MsUser) session.getAttribute("msUser");
            String owner = msUser.getNickName();

            JSONObject param = new JSONObject();
            param.put("business_id", business_id);
            param.put("owner", owner);
            param.put("app_name", app_name);

            JSONObject result = farmPageService.initPage(param.toString());
            MsLogger.debug(result.toString());

            if ("0".equals(result.get("code"))) {
                JSONObject data = result.getJSONObject("data");
                modelAndView.addObject("fields", data.getJSONObject("fields"));
                return modelAndView;
            }
        } catch (Exception e) {
            MsLogger.error("Failed to init MsShow" + e.getMessage());
        }
        return buildModelAndView("/error");
    }


//    @RequestMapping(params = SAVEPAGE)
    @RequestMapping(method = RequestMethod.PUT, value = "")
    @ResponseBody
    public JSONObject savePage(
            @RequestParam(required = true, value = "value") String fieldsVal,
            HttpServletRequest request, HttpServletResponse response ) {
         try{
             // 获取字段定义
             JSONObject result1 = farmPageService.getFarmFields();
             if (!"0".equals(result1)) {
                 return result1;
             }
             JSONObject data = result1.getJSONObject("data");
             JSONArray fields = data.getJSONArray("fields");

             // 检查字段数值
             FarmCommon.getInStance().checkFieldsVal(JSONObject.parseObject(fieldsVal), fields);

             // 保存
             JSONObject result = farmPageService.editPage(fieldsVal);
             MsLogger.debug(result.toString());
             return result;
         } catch (Exception e) {
             MsLogger.error("Falied to save page." + e.getMessage());
             return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Falied to save page");
         }

    }

//    @RequestMapping(params = DELPAGE)
    @RequestMapping(method = RequestMethod.DELETE, value = "/{app_id}")
    @ResponseBody
    public JSONObject delPage(
        @RequestParam(required = true, value = "app_id") Integer app_id,
        HttpServletRequest request, HttpServletResponse response) {
        try{
            JSONObject result = farmPageService.delPage(app_id);
            MsLogger.debug(result.toString());
            return result;
        } catch (Exception e) {
            MsLogger.error("Failed to del page." + e.getMessage());
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Falied to del page.");
        }
    }

//    @RequestMapping(params = LOADPAGE)
    @RequestMapping(method = RequestMethod.GET, value = "/{app_id}")
    @ResponseBody
    public JSONObject loadPage(
            @PathVariable("app_id") Integer app_id,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            JSONObject result = farmPageService.loadPage(app_id);
            MsLogger.debug(result.toString());
            return result;
        } catch (Exception e) {
            MsLogger.error("Fail to load page." + e.getMessage());
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Fail to load page.");
        }

    }

//    @RequestMapping(params = PREVIEW)
    @RequestMapping(method = RequestMethod.GET, value = "/preview")
    @ResponseBody
    public JSONObject previewPage(
            HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    /**
     * 检查微景展名称
     *
     * @return Boolean
     */
    private boolean checkAppName() {
        // TODO
        return true;
    }

}
