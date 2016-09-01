package ms.luna.web.control.merchant;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.sc.LunaTradeApplicationService;
import ms.luna.biz.sc.ManageMerchantService;
import ms.luna.biz.table.LunaTradeApplicationTable;
import ms.luna.biz.table.MsBusinessTable;
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
    public static final String menu = "merchant";
    @Autowired
    private LunaTradeApplicationService lunaTradeApplicationService;

    @Autowired
    private ManageMerchantService manageMerchantService;

    @RequestMapping(method = RequestMethod.GET, value = "")
    public ModelAndView init(HttpServletRequest request, HttpServletResponse response) {
        SessionHelper.setSelectedMenu(request.getSession(false), menu);
        return buildModelAndView("/merchant_direct");
    }


    @RequestMapping(method = RequestMethod.GET, value = "/serveprotocol")
    public ModelAndView serveProtocol(HttpServletRequest request, HttpServletResponse response) {

        SessionHelper.setSelectedMenu(request.getSession(false), menu);
        return buildModelAndView("/tradeserve_protocol");
    }



    @RequestMapping(method = RequestMethod.GET, value = "/detail")
    public ModelAndView merchantDetail(HttpServletRequest request, HttpServletResponse response) {

        SessionHelper.setSelectedMenu(request.getSession(false), menu);
        return buildModelAndView("/merchant_detail");
    }

    private boolean checkAuth(HttpServletRequest request, Integer businessId) {

        LunaUserSession user = SessionHelper.getUser(request.getSession(false));
        if (user == null) return false;
        if (!AuthHelper.hasBusinessAuth(user, businessId)) {
            return false;
        }
        if (user.getRoleId() != 10 && user.getRoleId() != 11) {
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
                                        @RequestParam String accountAddresss,
                                        @RequestParam String accountNo,
                                        @RequestParam Integer businessId) {
        if (!checkAuth(request, businessId)) {
            return FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "无权操作");
        }

        JSONObject inData = new JSONObject();
        inData.put(MsBusinessTable.FIELD_BUSINESS_ID, businessId);
        inData.put(LunaTradeApplicationTable.FIELD_ACCOUNT_ADDRESS, accountAddresss);
        inData.put(LunaTradeApplicationTable.FIELD_ACCOUNT_BANK, accountBank);
        inData.put(LunaTradeApplicationTable.FIELD_ACCOUNT_CITY, accountCity);
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
                                          @RequestParam String accountAddresss,
                                          @RequestParam String accountNo,
                                          @RequestParam Integer businessId) {
        if (!checkAuth(request, businessId)) {
            return FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "无权操作");
        }

        JSONObject inData = new JSONObject();
        inData.put(MsBusinessTable.FIELD_BUSINESS_ID, businessId);
        inData.put(LunaTradeApplicationTable.FIELD_ACCOUNT_ADDRESS, accountAddresss);
        inData.put(LunaTradeApplicationTable.FIELD_ACCOUNT_BANK, accountBank);
        inData.put(LunaTradeApplicationTable.FIELD_ACCOUNT_CITY, accountCity);
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
                                        @RequestParam String accountAddresss,
                                        @RequestParam String accountNo,
                                        @RequestParam Integer businessId) {
        if (!checkAuth(request, businessId)) {
            return FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "无权操作");
        }
        JSONObject inData = new JSONObject();
        inData.put(LunaTradeApplicationTable.FIELD_ID, applicationId);
        inData.put(MsBusinessTable.FIELD_BUSINESS_ID, businessId);
        inData.put(LunaTradeApplicationTable.FIELD_ACCOUNT_ADDRESS, accountAddresss);
        inData.put(LunaTradeApplicationTable.FIELD_ACCOUNT_BANK, accountBank);
        inData.put(LunaTradeApplicationTable.FIELD_ACCOUNT_CITY, accountCity);
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
    public JSONObject getApplicationStatus(HttpServletRequest request,
                                           @RequestParam Integer businessId) {
        if (!checkAuth(request, businessId)) {
            return FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "无权操作");
        }
        JSONObject inData = new JSONObject();
        inData.put(MsBusinessTable.FIELD_BUSINESS_ID, businessId);
        return lunaTradeApplicationService.getApplicationStatusByBusinessId(inData);
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

    @RequestMapping(method = RequestMethod.GET, value = "/merchantStatus/{businessId}")
    @ResponseBody
    public JSONObject getMerchantTradeStatus(HttpServletRequest request,
                                             @PathVariable Integer businessId) {
        if (checkAuth(request, businessId)) {
            return FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "无权操作");
        }
        JSONObject inData = new JSONObject();
        inData.put(MsBusinessTable.FIELD_BUSINESS_ID, businessId);
        return manageMerchantService.getMerchantTradeStatus(inData.toString());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get/{applicationId}")
    @ResponseBody
    public JSONObject getApplication(HttpServletRequest request,
                                     @PathVariable Integer applicationId,
                                     @RequestParam Integer businessId) {
        if (checkAuth(request, businessId)) {
            return FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "无权操作");
        }
        JSONObject inData = new JSONObject();
        inData.put(LunaTradeApplicationTable.FIELD_ID, applicationId);
        return lunaTradeApplicationService.getApplication(inData);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/sign/{businessId}")
    @ResponseBody
    public JSONObject signAgreement(HttpServletRequest request,
                                    @PathVariable Integer businessId) {
        if (checkAuth(request, businessId)) {
            return FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "无权操作");
        }
        JSONObject inData = new JSONObject();
        inData.put(MsBusinessTable.FIELD_BUSINESS_ID, businessId);
        return manageMerchantService.signAgreement(inData);
    }

}
