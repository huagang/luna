package ms.luna.web.control.merchant1;

import ms.luna.web.control.common.BasicController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    // 异步

}
