package ms.luna.web.control.data;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.VbConstant;
import ms.luna.biz.model.MsUser;
import ms.luna.biz.sc.ManagePoiService;
import ms.luna.biz.sc.VodPlayService;
import ms.luna.biz.util.*;
import ms.luna.common.PoiCommon;
import ms.luna.web.common.PulldownCtrl;
import ms.luna.web.control.ManagePoiCtrl;
import ms.luna.web.control.common.BasicController;
import ms.luna.web.model.common.SimpleModel;
import ms.luna.web.model.managepoi.PoiModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by greek on 16/8/5.
 */
@Controller("poiAddController")
@RequestMapping("/data/poi")
public class PoiAddController extends BasicController {
    @Autowired
    private ManagePoiService managePoiService;

    @Resource(name="pulldownCtrl")
    private PulldownCtrl pulldownCtrl;

    @Autowired
    private VodPlayService vodPlayService;

    @Autowired
    private PoiController poiController;

    /**
     * 非共有字段（由程序控制）；共有字段，由维护人员修改代码。
     */

    /**
     * 页面初始化
     * @param request
     * @param response
     */
    @RequestMapping(method = RequestMethod.GET, value = "/addPage")
    public ModelAndView init(
            HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        ModelAndView mav = new ModelAndView();
        if (session == null) {
            MsLogger.error("session is null");
            mav.setViewName("/error.jsp");
            return mav;
        }
        session.setAttribute("menu_selected", "manage_poi");
        PoiModel poiModel = new PoiModel();

        JSONObject params = new JSONObject();
        JSONObject result = managePoiService.initAddPoi(params.toString());
        MsLogger.debug(result.toString());

        if ("0".equals(result.getString("code"))) {
            JSONObject data = result.getJSONObject("data");
            JSONArray private_fields_def = data.getJSONArray("private_fields_def");
            poiController.initTags(session, data.getJSONObject("common_fields_def"), null);
            session.setAttribute("private_fields", private_fields_def);
        } else {
            mav.setViewName("/error.jsp");
            return mav;
        }

        // 区域信息的下拉列表
        // 国家信息
        SimpleModel simpleModel = null;

        // 省份信息
        List<SimpleModel> lstProvinces = new ArrayList<SimpleModel>();
        try {
            for (Map<String, String> map : pulldownCtrl.loadProvinces()) {
                simpleModel = new SimpleModel();
                simpleModel.setValue(map.get("province_id"));
                simpleModel.setLabel(map.get("province_nm_zh"));
                lstProvinces.add(simpleModel);
            }
        } catch (Exception e) {
            MsLogger.error(e);
            mav.setViewName("/error.jsp");
            return mav;
        }
        session.setAttribute("provinces", lstProvinces);

        // 城市信息
        List<SimpleModel> lstCitys = new ArrayList<SimpleModel>();
        simpleModel = new SimpleModel();
        simpleModel.setValue(VbConstant.ZonePulldown.ALL);
        simpleModel.setLabel(VbConstant.ZonePulldown.ALL_CITY_NM);
        lstCitys.add(simpleModel);
        try {
            // 北京城市信息（需要初始化）
            for (Map<String, String> map : pulldownCtrl.loadCitys("110000")) {
                simpleModel = new SimpleModel();
                simpleModel.setValue(map.get("city_id"));
                simpleModel.setLabel(map.get("city_nm_zh"));
                lstCitys.add(simpleModel);
            }
        } catch (Exception e) {
            MsLogger.error(e);
            mav.setViewName("/error.jsp");
            return mav;
        }
        session.setAttribute("citys", lstCitys);

        // 区/县信息
        List<SimpleModel> lstCountys = new ArrayList<SimpleModel>();
        simpleModel = new SimpleModel();
        simpleModel.setValue(VbConstant.ZonePulldown.ALL);
        simpleModel.setLabel(VbConstant.ZonePulldown.ALL_CITY_NM);
        lstCountys.add(simpleModel);
        session.setAttribute("countys", lstCountys);

        // 设置默认全景类型
        poiModel.setPanoramaType("2");
        mav.addObject("poiModel", poiModel);
        mav.setViewName("/add_poi.jsp");
        return mav;
    }

    /**
     * 添加POI基础信息
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.POST, value = "")
    @ResponseBody
    public JSONObject addPoi(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            HttpSession session = request.getSession(false);
            JSONObject param = this.param2Json(request);
            MsUser msUser = (MsUser)session.getAttribute("msUser");
            JSONObject result = managePoiService.addPoi(param.toString(), msUser);

            if ("0".equals(result.getString("code"))) {
                return FastJsonUtil.sucess("成功上传");
            }
            return FastJsonUtil.error("-1", result.getString("msg"));

        } catch(IllegalArgumentException e) {
            MsLogger.error(e);
            return FastJsonUtil.error("-2", e.getMessage());
        }
    }

    /**
     * 1.接收参数检查<p>
     * 2.封装参数到json格式
     * @param request
     * @return
     */
    private JSONObject param2Json(HttpServletRequest request) {
        JSONObject result = managePoiService.downloadPoiTemplete("{}");
        if (!"0".equals(result.get("code"))) {
            return FastJsonUtil.error("-1", result.getString("msg"));
        }
        JSONObject data = result.getJSONObject("data");
        JSONArray privateFieldsDef = data.getJSONArray("privateFieldsDef");

        return PoiCommon.getInstance().param2Json(request, privateFieldsDef, Boolean.FALSE);
    }

}
