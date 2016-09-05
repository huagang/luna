package ms.luna.web.control.content;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.sc.ManageBusinessTreeService;
import ms.luna.biz.sc.ManagePoiService;
import ms.luna.biz.util.CharactorUtil;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.common.LunaUserSession;
import ms.luna.web.common.SessionHelper;
import ms.luna.web.control.common.BasicController;
import ms.luna.web.control.common.PulldownController;
import ms.luna.web.model.common.SimpleModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by greek on 16/8/5.
 */

/**
 * 关系树编辑页
 */
@Controller("businessTreeController")
@RequestMapping("/content/businessRelation")
public class BusinessTreeController extends BasicController {

    private final static Logger logger = Logger.getLogger(BusinessTreeController.class);

    public static String menu = "businessRelation";
    @Autowired
    private ManageBusinessTreeService manageBusinessTreeService;

    @Autowired
    private ManagePoiService managePoiService;

    @Autowired
    private PulldownController pulldownController;

    @RequestMapping(method = RequestMethod.GET, value = "")
    public ModelAndView init(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView view = new ModelAndView();
        try {
            SessionHelper.setSelectedMenu(request.getSession(false), menu);
            // 省份列表
            view.addObject("provinces", pulldownController.loadProvinces());
            JSONObject result = managePoiService.getTagsDef("{}");
            if ("0".equals(result.getString("code"))) {
                JSONObject data = result.getJSONObject("data");
                JSONArray tags = data.getJSONArray("tags_def");
                List<SimpleModel> lstTopTags = new ArrayList<SimpleModel>();
                for (int i = 0; i < tags.size(); i++) {
                    JSONObject tag = tags.getJSONObject(i);
                    SimpleModel simpleModel = new SimpleModel();
                    simpleModel.setValue(tag.getString("tag_id"));
                    simpleModel.setLabel(tag.getString("tag_name"));
                    lstTopTags.add(simpleModel);
                }
                view.addObject("topTags", lstTopTags);
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

    @RequestMapping(method = RequestMethod.GET, value = "/exist/{businessId}")
    @ResponseBody
    public JSONObject existBusinessTree(@PathVariable("businessId") int businessId) {

        try {
            JSONObject ret = manageBusinessTreeService.existBusinessTree(businessId);
            return ret;
        } catch (Exception ex) {
            logger.error("Failed to call existBusinessTree", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/businessId/{businessId}")
    @ResponseBody
    public JSONObject viewBusinessTree(
            @PathVariable("businessId") Integer businessId,
            HttpServletRequest request, HttpServletResponse response) throws IOException {

        MsLogger.info("viewBusinessTree start....");
        try {

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

    @RequestMapping(method = RequestMethod.POST, value = "/saveBusinessTree")
    @ResponseBody
    public JSONObject saveBusinessTree(
            @RequestParam(required = true, value="businessTree") String businessTree,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        MsLogger.info("saveBusinessTree start....");
        try {

            JSONObject param = JSON.parseObject(businessTree);

            HttpSession session = request.getSession(false);
            if(session == null) {
                throw new Exception("session is null");
            }
            LunaUserSession user = SessionHelper.getUser(request.getSession(false));
            String uniqueId = user.getUniqueId();
            param.put("uniqueId", uniqueId);
            JSONObject result = manageBusinessTreeService.saveBusinessTree(param.toString());
            MsLogger.info(result.toJSONString());
            return result;
        } catch (Exception e) {
            MsLogger.error("Failed to save Business Tree", e);
            return FastJsonUtil.error("-1", e);
        }
    }

}
