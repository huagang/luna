package ms.luna.web.control.merchant;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.DbConfig;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.sc.LunaTradeApplicationService;
import ms.luna.biz.sc.ManageMerchantService;
import ms.luna.biz.sc.SMSService;
import ms.luna.biz.table.*;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.common.LunaUserSession;
import ms.luna.web.common.AuthHelper;
import ms.luna.web.common.SessionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ms.luna.web.control.common.BasicController;
import ms.luna.web.common.SessionHelper;

/**
 * Created by SDLL18 on 16/8/25.
 */

@Controller
@RequestMapping("/merchant/tradeApplication")
public class LunaTradeApplicationController extends BasicController {
    public static final String menu = "tradeApplication";
    @Autowired
    private LunaTradeApplicationService lunaTradeApplicationService;

    @Autowired
    private ManageMerchantService manageMerchantService;

    @Autowired
    private SMSService smsService;

    /**
     * 交易直通车开通界面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "")
    public ModelAndView init(HttpServletRequest request, HttpServletResponse response) {
        SessionHelper.setSelectedMenu(request.getSession(false), menu);
        return buildModelAndView("/trade_train");
    }

    /**
     * 交易直通车协议界面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/serveprotocol")
    public ModelAndView serveProtocol(HttpServletRequest request, HttpServletResponse response) {

        SessionHelper.setSelectedMenu(request.getSession(false), menu);
        return buildModelAndView("/tradeserve_protocol");
    }

    /**
     * 查看申请详情界面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/detail")
    public ModelAndView merchantDetail(HttpServletRequest request, HttpServletResponse response) {

        SessionHelper.setSelectedMenu(request.getSession(false), menu);
        return buildModelAndView("/trade_train_detail");
    }

    private boolean checkAuth(HttpServletRequest request) {
        LunaUserSession user = SessionHelper.getUser(request.getSession(false));
        if (user == null) return false;
        if (user.getRoleId() != 6 && user.getRoleId() != 7) {
            return false;
        }
        return true;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    @ResponseBody
    public JSONObject createApplication(HttpServletRequest request,
                                        @RequestParam String contactName,
                                        @RequestParam String contactPhone,
                                        @RequestParam String email,
                                        @RequestParam String idcardPicUrl,
                                        @RequestParam String idcardPeriod,
                                        @RequestParam String merchantName,
                                        @RequestParam String merchantPhone,
                                        @RequestParam String merchantNo,
                                        @RequestParam String licencePicUrl,
                                        @RequestParam String licencePeriod,
                                        @RequestParam Integer accountType,
                                        @RequestParam String accountName,
                                        @RequestParam String accountBank,
                                        @RequestParam String accountCity,
                                        @RequestParam String accountProvince,
                                        @RequestParam String accountAddress,
                                        @RequestParam String accountNo,
                                        @RequestParam String idcardNo,
                                        @RequestParam String smsCode) {
        if (!checkAuth(request)) {
            return FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "无权操作");
        }

        JSONObject checkData = new JSONObject();
        checkData.put("uniqueId", contactPhone.split("\\|")[1]);
        checkData.put("code", smsCode);
        checkData.put("target", SMSCodeTarget.TRADE_APPLICATION.toString());
        checkData.put("isRemove", true);
        JSONObject r = smsService.checkCode(checkData);
        if (r.getInteger("code").intValue() != 0) {
            return FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "短信验证码错误");
        }

        JSONObject inData = new JSONObject();
        inData.put(LunaUserTable.FIELD_ID, SessionHelper.getUser(request.getSession(false)).getUniqueId());
        inData.put(LunaTradeApplicationTable.FIELD_IDCARD_NO, idcardNo);
        inData.put(LunaTradeApplicationTable.FIELD_ACCOUNT_ADDRESS, accountAddress);
        inData.put(LunaTradeApplicationTable.FIELD_ACCOUNT_BANK, accountBank);
        inData.put(LunaTradeApplicationTable.FIELD_ACCOUNT_CITY, accountCity);
        inData.put(LunaTradeApplicationTable.FIELD_ACCOUNT_PROVINCE, accountProvince);
        inData.put(LunaTradeApplicationTable.FIELD_ACCOUNT_NAME, accountName);
        inData.put(LunaTradeApplicationTable.FIELD_ACCOUNT_NO, accountNo);
        inData.put(LunaTradeApplicationTable.FIELD_ACCOUNT_TYPE, accountType);
        inData.put(LunaTradeApplicationTable.FIELD_CONTACT_NAME, contactName);
        inData.put(LunaTradeApplicationTable.FIELD_CONTACT_PHONE, contactPhone);
        inData.put(LunaTradeApplicationTable.FIELD_EMAIL, email);
        inData.put(LunaTradeApplicationTable.FIELD_IDCARD_PERIOD, idcardPeriod);
        inData.put(LunaTradeApplicationTable.FIELD_IDCARD_PIC_URL, idcardPicUrl);
        inData.put(LunaTradeApplicationTable.FIELD_LICENCE_PERIOD, licencePeriod);
        inData.put(LunaTradeApplicationTable.FIELD_LICENCE_PIC_URL, licencePicUrl);
        inData.put(LunaTradeApplicationTable.FIELD_MERCHANT_NAME, merchantName);
        inData.put(LunaTradeApplicationTable.FIELD_MERCHANT_NO, merchantNo);
        inData.put(LunaTradeApplicationTable.FIELD_MERCHANT_PHONE, merchantPhone);

        JSONObject result = lunaTradeApplicationService.createApplication(inData);

        return result;
    }


    @RequestMapping(method = RequestMethod.POST, value = "/recreate")
    @ResponseBody
    public JSONObject recreateApplication(HttpServletRequest request,
                                          @RequestParam String contactName,
                                          @RequestParam String contactPhone,
                                          @RequestParam String email,
                                          @RequestParam String idcardPicUrl,
                                          @RequestParam String idcardPeriod,
                                          @RequestParam String merchantName,
                                          @RequestParam String merchantPhone,
                                          @RequestParam String merchantNo,
                                          @RequestParam String licencePicUrl,
                                          @RequestParam String licencePeriod,
                                          @RequestParam Integer accountType,
                                          @RequestParam String accountName,
                                          @RequestParam String accountBank,
                                          @RequestParam String accountCity,
                                          @RequestParam String accountProvince,
                                          @RequestParam String accountAddress,
                                          @RequestParam String idcardNo,
                                          @RequestParam String accountNo) {
        if (!checkAuth(request)) {
            return FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "无权操作");
        }

        JSONObject inData = new JSONObject();
        inData.put(LunaUserTable.FIELD_ID, SessionHelper.getUser(request.getSession(false)).getUniqueId());
        inData.put(LunaTradeApplicationTable.FIELD_IDCARD_NO, idcardNo);
        inData.put(LunaTradeApplicationTable.FIELD_ACCOUNT_ADDRESS, accountAddress);
        inData.put(LunaTradeApplicationTable.FIELD_ACCOUNT_BANK, accountBank);
        inData.put(LunaTradeApplicationTable.FIELD_ACCOUNT_CITY, accountCity);
        inData.put(LunaTradeApplicationTable.FIELD_ACCOUNT_PROVINCE, accountProvince);
        inData.put(LunaTradeApplicationTable.FIELD_ACCOUNT_NAME, accountName);
        inData.put(LunaTradeApplicationTable.FIELD_ACCOUNT_NO, accountNo);
        inData.put(LunaTradeApplicationTable.FIELD_ACCOUNT_TYPE, accountType);
        inData.put(LunaTradeApplicationTable.FIELD_CONTACT_NAME, contactName);
        inData.put(LunaTradeApplicationTable.FIELD_CONTACT_PHONE, contactPhone);
        inData.put(LunaTradeApplicationTable.FIELD_EMAIL, email);
        inData.put(LunaTradeApplicationTable.FIELD_IDCARD_PERIOD, idcardPeriod);
        inData.put(LunaTradeApplicationTable.FIELD_IDCARD_PIC_URL, idcardPicUrl);
        inData.put(LunaTradeApplicationTable.FIELD_LICENCE_PERIOD, licencePeriod);
        inData.put(LunaTradeApplicationTable.FIELD_LICENCE_PIC_URL, licencePicUrl);
        inData.put(LunaTradeApplicationTable.FIELD_MERCHANT_NAME, merchantName);
        inData.put(LunaTradeApplicationTable.FIELD_MERCHANT_NO, merchantNo);
        inData.put(LunaTradeApplicationTable.FIELD_MERCHANT_PHONE, merchantPhone);

        JSONObject result = lunaTradeApplicationService.recreateApplication(inData);

        return result;
    }


    @RequestMapping(method = RequestMethod.PUT, value = "/update/{applicationId}")
    @ResponseBody
    public JSONObject updateApplication(HttpServletRequest request,
                                        @PathVariable Integer applicationId,
                                        @RequestParam String contactName,
                                        @RequestParam String contactPhone,
                                        @RequestParam String email,
                                        @RequestParam String idcardPicUrl,
                                        @RequestParam String idcardPeriod,
                                        @RequestParam String merchantName,
                                        @RequestParam String merchantPhone,
                                        @RequestParam String merchantNo,
                                        @RequestParam String licencePicUrl,
                                        @RequestParam String licencePeriod,
                                        @RequestParam Integer accountType,
                                        @RequestParam String accountName,
                                        @RequestParam String accountBank,
                                        @RequestParam String accountCity,
                                        @RequestParam String accountProvince,
                                        @RequestParam String accountAddresss,
                                        @RequestParam String idcardNo,
                                        @RequestParam String accountNo) {
        if (!checkAuth(request)) {
            return FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "无权操作");
        }
        JSONObject inData = new JSONObject();
        inData.put(LunaTradeApplicationTable.FIELD_ID, applicationId);
        inData.put(LunaUserTable.FIELD_ID, SessionHelper.getUser(request.getSession(false)).getUniqueId());
        inData.put(LunaTradeApplicationTable.FIELD_IDCARD_NO, idcardNo);
        inData.put(LunaTradeApplicationTable.FIELD_ACCOUNT_ADDRESS, accountAddresss);
        inData.put(LunaTradeApplicationTable.FIELD_ACCOUNT_BANK, accountBank);
        inData.put(LunaTradeApplicationTable.FIELD_ACCOUNT_CITY, accountCity);
        inData.put(LunaTradeApplicationTable.FIELD_ACCOUNT_PROVINCE, accountProvince);
        inData.put(LunaTradeApplicationTable.FIELD_ACCOUNT_NAME, accountName);
        inData.put(LunaTradeApplicationTable.FIELD_ACCOUNT_NO, accountNo);
        inData.put(LunaTradeApplicationTable.FIELD_ACCOUNT_TYPE, accountType);
        inData.put(LunaTradeApplicationTable.FIELD_CONTACT_NAME, contactName);
        inData.put(LunaTradeApplicationTable.FIELD_CONTACT_PHONE, contactPhone);
        inData.put(LunaTradeApplicationTable.FIELD_EMAIL, email);
        inData.put(LunaTradeApplicationTable.FIELD_IDCARD_PERIOD, idcardPeriod);
        inData.put(LunaTradeApplicationTable.FIELD_IDCARD_PIC_URL, idcardPicUrl);
        inData.put(LunaTradeApplicationTable.FIELD_LICENCE_PERIOD, licencePeriod);
        inData.put(LunaTradeApplicationTable.FIELD_LICENCE_PIC_URL, licencePicUrl);
        inData.put(LunaTradeApplicationTable.FIELD_MERCHANT_NAME, merchantName);
        inData.put(LunaTradeApplicationTable.FIELD_MERCHANT_NO, merchantNo);
        inData.put(LunaTradeApplicationTable.FIELD_MERCHANT_PHONE, merchantPhone);

        return lunaTradeApplicationService.updateApplication(inData);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/status")
    @ResponseBody
    public JSONObject getApplicationStatus(HttpServletRequest request) {
        if (!checkAuth(request)) {
            return FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "无权操作");
        }
        JSONObject inData = new JSONObject();
        inData.put(LunaUserTable.FIELD_ID, SessionHelper.getUser(request.getSession(false)).getUniqueId());
        return lunaTradeApplicationService.getApplicationStatus(inData);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getMerchantInfo")
    @ResponseBody
    public JSONObject getMerchantInfo(HttpServletRequest request) {
        if (!checkAuth(request)) {
            return FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "无权操作");
        }
        JSONObject inData = new JSONObject();
        inData.put(LunaUserTable.FIELD_ID, SessionHelper.getUser(request.getSession(false)).getUniqueId());
        return manageMerchantService.loadMerchantByUserId(inData.toString());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/status/{applicationId}")
    @ResponseBody
    public JSONObject getApplicationStatusByAppId(HttpServletRequest request,
                                                  @PathVariable Integer applicationId) {
        LunaUserSession user = SessionHelper.getUser(request.getSession(false));
        if (user == null) {
            return FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "无权操作");
        }
        if (user.getRoleId() != 1 && user.getRoleId() != 2 && user.getRoleId() != 3) {
            return FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "无权操作");
        }
        JSONObject inData = new JSONObject();
        inData.put(LunaTradeApplicationTable.FIELD_ID, applicationId);
        return lunaTradeApplicationService.getApplicationStatusByApplicationId(inData);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/merchantStatus")
    @ResponseBody
    public JSONObject getMerchantTradeStatus(HttpServletRequest request) {
        if (!checkAuth(request)) {
            return FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "无权操作");
        }
        JSONObject inData = new JSONObject();
        inData.put(LunaUserTable.FIELD_ID, SessionHelper.getUser(request.getSession(false)).getUniqueId());
        return manageMerchantService.getMerchantTradeStatus(inData.toString());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get")
    @ResponseBody
    public JSONObject getApplication(HttpServletRequest request) {
        if (!checkAuth(request)) {
            return FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "无权操作");
        }
        JSONObject inData = new JSONObject();
        inData.put(LunaUserTable.FIELD_ID, SessionHelper.getUser(request.getSession(false)).getUniqueId());
        return lunaTradeApplicationService.getApplication(inData);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/sign")
    @ResponseBody
    public JSONObject signAgreement(HttpServletRequest request) {
        if (!checkAuth(request)) {
            return FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "无权操作");
        }
        JSONObject inData = new JSONObject();
        HttpSession session = request.getSession(false);
        inData.put(LunaUserTable.FIELD_ID, SessionHelper.getUser(session).getUniqueId());
        JSONObject ret = manageMerchantService.signAgreement(inData);
        if (ret.getString("code").equals("0")) {
            JSONArray moduleAndMenuArray = SessionHelper.getMenu(session);
            switchMerchantTradeOnMenu(moduleAndMenuArray);
            SessionHelper.setMenu(session, moduleAndMenuArray);
            SessionHelper.setSelectedMenu(session, "");
        }
        return ret;
    }

    private void switchMerchantTradeOnMenu(JSONArray moduleAndMenuArray) {

        for (int i = 0; i < moduleAndMenuArray.size(); i++) {
            JSONObject moduleAndMenu = moduleAndMenuArray.getJSONObject(i);
            JSONArray menuArray = moduleAndMenu.getJSONArray("menuArray");
            for (int j = 0; j < menuArray.size(); j++) {
                JSONObject menu = menuArray.getJSONObject(i);
                int menuId = menu.getInteger(LunaMenuTable.FIELD_ID);
                if (DbConfig.INVISIBLE_MENU_TRADE_OFF.contains(menuId)) {
                    menu.put(LunaMenuTable.FEILD_VISIBLE, false);
                }
                if (DbConfig.INVISIBLE_MENU_TRADE_ON.contains(menuId)) {
                    menu.put(LunaMenuTable.FEILD_VISIBLE, true);
                }
            }
        }

    }

}
