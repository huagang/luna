package ms.luna.web.control.content;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.model.MsUser;
import ms.luna.biz.sc.ManageBusinessTreeService;
import ms.luna.biz.sc.ManagePoiService;
import ms.luna.biz.util.CharactorUtil;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.web.common.PulldownCtrl;
import ms.luna.web.control.common.BasicController;
import ms.luna.web.model.common.SimpleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by greek on 16/8/5.
 */
@Controller("businessTreeCtrl")
@RequestMapping("/content/businessRelation")
public class BusinessTreeCtrl extends BasicController {

    @Autowired
    private ManageBusinessTreeService manageBusinessTreeService;

    @Autowired
    private ManagePoiService managePoiService;

    @Resource(name="pulldownCtrl")
    private PulldownCtrl pulldownCtrl;

    @RequestMapping(method = RequestMethod.GET, value = "")
    public ModelAndView init(
            @RequestParam(required = true, value="business_id") Integer businessId,
            @RequestParam(required = false, value="province_id") String provinceId,
            @RequestParam(required = false, value="city_id") String cityId,
            @RequestParam(required = false, value="county_id") String countyId,
            HttpServletRequest request, HttpServletResponse response) {
        ModelAndView view = new ModelAndView();
        try {

            // 省份列表
            view.addObject("provinces", pulldownCtrl.loadProvinces());

            // 城市信息
            if (!CharactorUtil.isEmpyty(provinceId)) {
                view.addObject("citys", pulldownCtrl.loadCitys(provinceId));
            } else {
                view.addObject("citys", new ArrayList<SimpleModel>());
            }

            // 区/县信息
            if (!CharactorUtil.isEmpyty(cityId)) {
                view.addObject("countys", pulldownCtrl.loadCountys(cityId));
            } else {
                view.addObject("countys", new ArrayList<SimpleModel>());
            }

            // 下拉菜单的初始值
            view.addObject("provinceId", provinceId);
            view.addObject("cityId", cityId);
            view.addObject("countyId", countyId);
            com.alibaba.fastjson.JSONObject result = managePoiService.getTagsDef("{}");
            if ("0".equals(result.getString("code"))) {
                com.alibaba.fastjson.JSONObject data = result.getJSONObject("data");
                com.alibaba.fastjson.JSONArray tags = data.getJSONArray("tags_def");
                List<SimpleModel> lstTopTags = new ArrayList<SimpleModel>();
                for (int i = 0; i < tags.size(); i++) {
                    com.alibaba.fastjson.JSONObject tag = tags.getJSONObject(i);
                    SimpleModel simpleModel = new SimpleModel();
                    simpleModel.setValue(tag.getString("tag_id"));
                    simpleModel.setLabel(tag.getString("tag_name"));
                    lstTopTags.add(simpleModel);
                }
                view.addObject("topTags", lstTopTags);
                view.addObject("businessId", businessId);
                view.setViewName("/business_tree.jsp");
                return view;
            } else {
                view.setViewName("/error.jsp");
                return view;
            }
        } catch (Exception e) {
            MsLogger.error(e);
        }
        view.setViewName("/error.jsp");
        return view;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/businessId/{businessId}")
    @ResponseBody
    public JSONObject viewBusinessTree(
            @PathVariable("businessId") Integer businessId,
            HttpServletRequest request, HttpServletResponse response) throws IOException {

        MsLogger.info("viewBusinessTree start....");
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setContentType("text/html; charset=UTF-8");

            JSONObject param = JSONObject.parseObject("{}");
            param.put("businessId", businessId);

            JSONObject result = manageBusinessTreeService.viewBusinessTree(param.toString());
            MsLogger.info(result.toJSONString());
            return result;
        } catch (Exception e) {
            MsLogger.error("Failed to load Business Trees", e);
            return FastJsonUtil.error("-1", e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/searchPois")
    @ResponseBody
    public JSONObject searchPoisForBizTree(
            @RequestParam(required = false) Integer offset,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false, value="province_id") String provinceId,
            @RequestParam(required = false, value="city_id") String cityId,
            @RequestParam(required = false, value="county_id") String countId,
            @RequestParam(required = false, value="keyWord") String keyWord,
            @RequestParam(required = false, value="tag_id") String tagId,
            HttpServletRequest request, HttpServletResponse response) throws IOException {

        MsLogger.info("asyncSearchPoiList start....");
        try {

            JSONObject param = JSONObject.parseObject("{}");
            param.put("offset", offset);
            param.put("limit", limit);
            param.put("provinceId", provinceId);
            param.put("cityId", cityId);
            param.put("countyId", countId);
            param.put("keyWord", keyWord);
            param.put("tagId", tagId);

            JSONObject result = manageBusinessTreeService.searchPoisForBizTree(param.toString());
            MsLogger.info(result.toJSONString());
            return result;
        } catch (Exception e) {
            MsLogger.error("Failed to search pois", e);
            return FastJsonUtil.error("-1", e);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/saveBusinessTree")
    @ResponseBody
    public JSONObject saveBusinessTree(
            @RequestParam(required = true, value="businessTree") String businessTree,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        MsLogger.info("saveBusinessTree start....");
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setContentType("text/html; charset=UTF-8");

            JSONObject param = JSON.parseObject(businessTree);

            HttpSession session = request.getSession(false);
            MsUser msUser = (MsUser) session.getAttribute("msUser");

            JSONObject result = manageBusinessTreeService.saveBusinessTree(param.toString(), msUser);
            MsLogger.info(result.toJSONString());
            return result;
        } catch (Exception e) {
            MsLogger.error("Failed to save Business Tree", e);
            return FastJsonUtil.error("-1", e);
        }
    }

}
