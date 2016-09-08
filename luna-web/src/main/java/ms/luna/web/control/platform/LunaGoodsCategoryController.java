package ms.luna.web.control.platform;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.sc.LunaGoodsCategoryService;
import ms.luna.web.control.common.BasicController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by SDLL18 on 16/8/24.
 */

@Controller
@RequestMapping("/platform/goodsCategory")
public class LunaGoodsCategoryController extends BasicController {

    @Autowired
    private LunaGoodsCategoryService lunaGoodsCategoryService;

    private JSONObject getJSONCategories(Integer offset, Integer limit) {
        JSONObject toReturn = null;
        if (offset == null && limit == null) {
            toReturn = lunaGoodsCategoryService.getCategories();
        } else {
            JSONObject inData = new JSONObject();
            inData.put("offset", offset);
            inData.put("limit", limit);
            toReturn = lunaGoodsCategoryService.getCategories(inData);
        }
        return toReturn;
    }


    @RequestMapping(method = RequestMethod.GET, value = "")
    public ModelAndView init() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/manage_goods_type.jsp");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get")
    @ResponseBody
    public JSONObject getCategories(@RequestParam(required = false) Integer offset,
                                    @RequestParam(required = false) Integer limit) {
        JSONObject toReturn = getJSONCategories(offset, limit);
        return toReturn;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/search")
    @ResponseBody
    public JSONObject searchCategories(@RequestParam(required = false) Integer offset,
                                       @RequestParam(required = false) Integer limit,
                                       @RequestParam(required = true) String searchWord) {
        JSONObject inData = new JSONObject();
        inData.put("searchWord", searchWord);
        inData.put("offset", offset);
        inData.put("limit", limit);
        JSONObject toReturn = lunaGoodsCategoryService.searchCategories(inData);
        return toReturn;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/searchRoot")
    @ResponseBody
    public JSONObject searchRootCategories(@RequestParam(required = true) String searchWord) {
        JSONObject inData = new JSONObject();
        inData.put("searchWord", searchWord);
        JSONObject toReturn = lunaGoodsCategoryService.searchCategories(inData);
        return toReturn;
    }

    @RequestMapping(method = RequestMethod.POST, value = "")
    @ResponseBody
    public JSONObject createCategory(@RequestParam(value = "name", required = true) String name,
                                     @RequestParam(value = "abbreviation", required = true) String abbreviation,
                                     @RequestParam(value = "root", required = false) Integer root,
                                     @RequestParam(value = "depth", required = true) Integer depth) {
        JSONObject inData = new JSONObject();
        inData.put("name", name);
        inData.put("abbreviation", abbreviation);
        inData.put("root", root);
        inData.put("depth", depth);
        return lunaGoodsCategoryService.createCategory(inData);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    @ResponseBody
    public JSONObject updateCategory(@RequestParam(value = "name", required = true) String name,
                                     @PathVariable("id") Integer id,
                                     @RequestParam(value = "abbreviation", required = true) String abbreviation,
                                     @RequestParam(value = "root", required = true) Integer root,
                                     @RequestParam(value = "depth", required = true) Integer depth,
                                     @RequestParam String children) {
        JSONObject inData = new JSONObject();
        inData.put("id", id);
        inData.put("name", name);
        inData.put("abbreviation", abbreviation);
        inData.put("root", root);
        inData.put("depth", depth);
        inData.put("children", JSONArray.parse(children));
        return lunaGoodsCategoryService.updateCategory(inData);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @ResponseBody
    public JSONObject deleteCategory(@PathVariable("id") Integer id) {
        JSONObject inData = new JSONObject();
        inData.put("id", id);
        return lunaGoodsCategoryService.deleteCategory(inData);
    }


}
