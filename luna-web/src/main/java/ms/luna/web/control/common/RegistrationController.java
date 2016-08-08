package ms.luna.web.control.common;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.sc.LoginService;
import ms.luna.biz.sc.LunaUserService;
import ms.luna.biz.sc.RegistService;
import ms.luna.biz.util.CharactorUtil;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.biz.util.VbUtility;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by greek on 16/8/7.
 */
@Controller
@RequestMapping("/common/register")
public class RegistrationController extends BasicController {

//    @Autowired
//    private LoginService loginService;

    @Autowired
    private LunaUserService lunaUserService;

    @RequestMapping(method = RequestMethod.GET, value = "")
    public ModelAndView register(
            @RequestParam(required = true, value = "token") String token,
            HttpServletRequest request, HttpServletResponse response){
        try {

            JSONObject result = lunaUserService.isTokenValid(token);
            MsLogger.debug("method:isTokenValid, result from service: " + result.toString());

            if("0".equals(result.get("code"))){
                ModelAndView model = buildModelAndView("register");
                model.addObject("token", token);
                return model;
            }
            return buildModelAndView("login_overtime");
        } catch (Exception e) {
            return buildModelAndView("error");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "")
    @ResponseBody
    public JSONObject regist_wjuser(
            @RequestParam(required = true)String token,
            @RequestParam(required = true)String nickname,
            @RequestParam(required = true)String password,
            HttpServletRequest request, HttpServletResponse response){

        try {

            // 检测token
            if(StringUtils.isBlank(token)){//token长度不正确
                return FastJsonUtil.error("4", "token不正确,token:" + token);
            }
            //检查账户名
            JSONObject result = check_wjnm(nickname);
            if("1".equals(result.get("code"))){
                return FastJsonUtil.error("1","账户格式不正确,nickname:"+nickname);
            }else if("2".equals(result.get("code"))){
                return FastJsonUtil.error("2","账户名称存在,nickname:"+nickname);
            }else if("3".equals(result.get("code"))){
                return FastJsonUtil.error("3","账户已被注册,nickname:"+nickname);
            }
            //检查账户密码
            result = check_pw(password);
            if(!"0".equals(result.get("code"))){
                return FastJsonUtil.error("1","密码格式不正确,password:"+password);
            }
            //注册
            JSONObject json = JSONObject.parseObject("{}");
            json.put("luna_name", nickname);
            json.put("password", password);
            json.put("token", token);
            result = lunaUserService.registerUser(json);
            MsLogger.debug("method:registPwUser, result from service: " + result.toString());

            if("1".equals(result.get("code"))){
                result = FastJsonUtil.error("3", "账户已被注册,nickname:"+nickname);
            }
            return result;

        } catch (Exception e) {
            MsLogger.error("Failed to regist user." + e.getMessage());
            return FastJsonUtil.error("-101", "Failed to regist user: ");
        }
    }/**/

    /**
     * 检查账户名
     * @param nickname
     * @return
     */
    private JSONObject check_wjnm(String nickname) {
        //检查长度
        if (nickname == null || nickname.length() == 0 || nickname.getBytes().length > 32) {
            return FastJsonUtil.error("1", "账户格式（长度）不正确,nickname:"+nickname);//以后需要更加详细的信息时可设定不同code值
        }
        //检查格式
        JSONObject jsonNickNm = CharactorUtil.checkNickNm(nickname);
        if(!"0".equals(jsonNickNm.get("code"))){
            return FastJsonUtil.error("1", "账户格式（字符）不正确,nickname:"+nickname);
        }
        //检查重名
        JSONObject json = JSONObject.parseObject("{}");
        json.put("luna_name", nickname);
        // 目前没有loginService,因此这一块暂时不做检查.
//        JSONObject result = loginService.isLunaUserExsit(json.toString());
//        if("0".equals(result.get("code"))){
//            return FastJsonUtil.error("3", "账户已被注册,nickname:"+nickname);
//        }
        return FastJsonUtil.sucess("账户格式正确!");
    }

    /**
     * 检查密码
     * @param password
     * @return
     */
    private JSONObject check_pw(String password) {
        JSONObject jsonPw = CharactorUtil.checkPw(password);
        if(!"0".equals(jsonPw.getString("code"))){
            return FastJsonUtil.error("1", "密码格式不正确,password:"+password);
        }
        return FastJsonUtil.sucess("密码格式正确,password:"+password);
    }

}
