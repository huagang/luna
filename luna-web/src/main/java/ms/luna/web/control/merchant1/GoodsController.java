package ms.luna.web.control.merchant1;

import com.alibaba.fastjson.JSONObject;
import ms.luna.web.control.common.BasicController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created: by greek on 16/8/29.
 */
@Controller
@RequestMapping("/merchant/goods")
public class GoodsController extends BasicController {

    // 页面初始化
    @RequestMapping(method = RequestMethod.GET, value = "")
    public ModelAndView init(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    // 搜索
    @RequestMapping(method = RequestMethod.GET, value = "/search")
    @ResponseBody
    public JSONObject asyncSearchGoods(
            @RequestParam(required = false) Integer offset,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) String keyword,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        return null;
    }

    // 创建商品
    @RequestMapping(method = RequestMethod.POST, value = "")
    @ResponseBody
    public JSONObject createGoods(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    // 删除商品
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}" )
    @ResponseBody
    public JSONObject deleteGoods(
            @PathVariable("id") Integer id,
            HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    // 编辑商品
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    @ResponseBody
    public JSONObject updateGoods(
            @PathVariable("id") Integer id,
            HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    // 检查商品名称
    @RequestMapping(method = RequestMethod.GET, value = "/checkName")
    @ResponseBody
    public JSONObject checkName(
        @RequestParam(required = true, value = "goods_name") String name,
        @RequestParam(required = false, value = "goods_id") Integer id,
        HttpServletRequest request, HttpServletResponse response) {

        return null;
    }
}
