package ms.luna.web.control.merchant;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.sc.LunaGoodsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by SDLL18 on 16/8/24.
 */

@Controller
@RequestMapping("/merchant/goodsCategory")
public class LunaGoodsCategoryController {

    @Autowired
    private LunaGoodsCategoryService lunaGoodsCategoryService;


    @RequestMapping(method = RequestMethod.GET, value = "")
    @ResponseBody
    public JSONObject getCategories() {
        JSONObject toReturn = lunaGoodsCategoryService.getCategories();
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
                                     @RequestParam(value = "depth", required = true) Integer depth) {
        JSONObject inData = new JSONObject();
        inData.put("id", id);
        inData.put("name", name);
        inData.put("abbreviation", abbreviation);
        inData.put("root", root);
        inData.put("depth", depth);
        return lunaGoodsCategoryService.updateCategory(inData);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @ResponseBody
    public JSONObject deleteCategory(@PathVariable("id") Integer id) {
        JSONObject inData = new JSONObject();
        inData.put("id",id);
        return lunaGoodsCategoryService.deleteCategory(inData);
    }


}
