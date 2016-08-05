package ms.luna.web.control.content;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.model.MsUser;
import ms.luna.biz.sc.ManageBusinessTreeService;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.web.common.PulldownCtrl;
import ms.luna.web.control.common.BasicController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-27
 */

@Controller
@RequestMapping("/content/businessRelation")
public class BusinessRelationController extends BasicController {

    @Autowired
    private ManageBusinessTreeService manageBusinessTreeService;

    @Resource(name="pulldownCtrl")
    private PulldownCtrl pulldownCtrl;

    @RequestMapping(method = RequestMethod.GET, value = "")
    public ModelAndView init(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView view = new ModelAndView();
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.setAttribute("menu_selected", "manage_business_tree");
            }
            view.addObject("provinces", pulldownCtrl.loadProvinces());
            view.addObject("citys", pulldownCtrl.loadCitys("110000"));
            view.addObject("provinceId", "110000");
            view.setViewName("/manage_business_tree.jsp");
            return view;
        } catch (Exception e) {
            MsLogger.error(e);
        }
        view.setViewName("/error.jsp");
        return view;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/searchBusinessTree")
    @ResponseBody
    public JSONObject asyncSearchBusinessTrees(
            @RequestParam(required = false) Integer offset,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false, value="province_id") String provinceId,
            @RequestParam(required = false, value="city_id") String cityId,
            @RequestParam(required = false, value="county_id") String countId,
            @RequestParam(required = false, value="filterLikeName") String keyWord,
            HttpServletRequest request, HttpServletResponse response) throws IOException {

        MsLogger.info("asyncSearchBusinessTrees start....");
        JSONObject resJSON = new JSONObject();
        resJSON.put("total", 0);
        try {
            JSONObject param = new JSONObject();
            param.put("offset", offset);
            param.put("limit", limit);
            param.put("provinceId", provinceId);
            param.put("cityId", cityId);
            param.put("countyId", countId);
            param.put("keyWord", keyWord);

            JSONObject result = manageBusinessTreeService.loadBusinessTrees(param.toString());
            MsLogger.info(result.toString());
            if ("0".equals(result.getString("code"))) {
                return result.getJSONObject("data");
            }
        } catch (Exception e) {
            MsLogger.error("Failed to load Business Trees", e);
        }

        return resJSON;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/searchBusiness")
    public JSONObject asyncSearchBusinesses(
            @RequestParam(required = true, value="province_id") String provinceId,
            @RequestParam(required = false, value="city_id") String cityId,
            @RequestParam(required = false, value="county_id") String countId,
            @RequestParam(required = false, value="business_tree_name") String keyWord,
            HttpServletRequest request, HttpServletResponse response) throws IOException {

        MsLogger.info("async_search_businesses start....");
        try {
            JSONObject param = new JSONObject();
            param.put("provinceId", provinceId);
            param.put("cityId", cityId);
            param.put("countyId", countId);
            param.put("keyWord", keyWord);

            JSONObject result = manageBusinessTreeService.searchBusinessList(param.toString());
            MsLogger.info(result.toString());
            return result;
        } catch (Exception e) {
            MsLogger.error("Failed to load Businessees", e);
            return FastJsonUtil.error(-1, "inner err");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/businessId/{businessId}")
    public JSONObject asyncCreateBusinessTree(
            @PathVariable("businessId") Integer businessId,
            HttpServletRequest request, HttpServletResponse response) throws IOException {

        MsLogger.info("async_search_businesses start....");
        try {
            JSONObject param = new JSONObject();
            param.put("businessId", businessId);
            HttpSession session = request.getSession(false);
            MsUser msUser = (MsUser)session.getAttribute("msUser");
            JSONObject result = manageBusinessTreeService.createBusinessTree(param.toString(), msUser);
            return result;
        } catch (Exception e) {
            MsLogger.error("Failed to create Business Tree", e);
            return FastJsonUtil.error(-1, "inner err");
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/businessId/{businessId}")
    public JSONObject asyncDeleteBusinessTree(
            @PathVariable("businessId") Integer businessId,
            HttpServletRequest request, HttpServletResponse response ) throws IOException {
        try {
            JSONObject json = new JSONObject();
            json.put("businessId", businessId);
            JSONObject jsonObject = manageBusinessTreeService.deleteBusinessTree(json.toJSONString());
            MsLogger.debug(jsonObject.toString());
            return jsonObject;
        } catch(Exception e) {
            MsLogger.error("Failed to delete business", e);
            return FastJsonUtil.error("-1", "处理异常");
        }
    }

}
