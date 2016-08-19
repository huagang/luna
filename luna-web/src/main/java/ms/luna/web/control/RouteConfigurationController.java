package ms.luna.web.control;

import com.alibaba.fastjson.JSONObject;
import com.sun.prism.impl.BaseMesh;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.sc.ManageRouteService;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.web.common.BasicCtrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by greek on 16/8/19.
 */
@Controller
@RequestMapping("/content/route")
public class RouteConfigurationController extends BasicCtrl {

    @Autowired
    private ManageRouteService manageRouteService;

    @RequestMapping(method = RequestMethod.GET, value = "/configuration")
    public ModelAndView initRouteConfigurationPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.setAttribute("menu_selected", "manage_router");
        }
        ModelAndView modelAndView = buildModelAndView("/manage_router_edit");

        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/configuration/{routeId}")
    @ResponseBody
    public JSONObject viewRouteConfiguration(@PathVariable("routeId") Integer routeId){
        try {
            JSONObject result = manageRouteService.viewRouteConfiguration(routeId);
            MsLogger.debug(result.toString());
            return result;
        } catch (Exception e) {
            MsLogger.error("Failed to get route configuration: " + e.getMessage() );
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to get route configuration");
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/configuration/{routeId}")
    @ResponseBody
    public JSONObject saveRouteConfiguration(
            @PathVariable("routeId") Integer routeId,
            @RequestParam(required = true, value = "data") String data){
        try {
            JSONObject param = new JSONObject();
            param.put("routeId", routeId);
            param.put("data", data);
            JSONObject result = manageRouteService.saveRouteConfiguration(param);
            MsLogger.debug(result.toString());
            return result;
        } catch (Exception e) {
            MsLogger.error("Failed to save route configuration: " + e.getMessage() );
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to save route configuration");
        }
    }

}
