package ms.luna.web.control.merchant;

import ms.luna.web.common.SessionHelper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ms.luna.web.control.common.BasicController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Created by herrdu on 16/8/29.
 */
@Controller
@RequestMapping("/merchant/tradeApplication")
public class MerchantDirectController extends BasicController{
    private final static Logger logger = Logger.getLogger(MerchantDirectController.class);

    public static final String menu = "merchant";

    @RequestMapping(method = RequestMethod.GET, value = "")
    public ModelAndView  tradeApplication (HttpServletRequest request, HttpServletResponse response) {

        SessionHelper.setSelectedMenu(request.getSession(false), menu);
        return buildModelAndView("/merchant_direct");
    }

}
