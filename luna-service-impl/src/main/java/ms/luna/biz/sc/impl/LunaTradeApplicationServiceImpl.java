package ms.luna.biz.sc.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.bl.ManageMerchantBL;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.dao.custom.LunaTradeApplicationDAO;
import ms.luna.biz.dao.custom.LunaUserMerchantDAO;
import ms.luna.biz.dao.custom.MsMerchantManageDAO;
import ms.luna.biz.dao.custom.model.LunaTradeApplicationParameter;
import ms.luna.biz.dao.model.*;
import ms.luna.biz.sc.LunaTradeApplicationService;
import ms.luna.biz.table.LunaTradeApplicationTable;
import ms.luna.biz.table.LunaUserTable;
import ms.luna.biz.table.MsMerchantManageTable;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.schedule.service.EmailService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Calendar;
import java.util.List;

/**
 * Created by SDLL18 on 16/8/25.
 */

@Service("lunaTradeApplicationService")
public class LunaTradeApplicationServiceImpl implements LunaTradeApplicationService {

    private static final Logger logger = Logger.getLogger(LunaTradeApplicationServiceImpl.class);

    @Autowired
    private LunaTradeApplicationDAO lunaTradeApplicationDAO;

    @Autowired
    private LunaUserMerchantDAO lunaUserMerchantDao;

    @Autowired
    private ManageMerchantBL manageMerchantBL;

    @Autowired
    private MsMerchantManageDAO msMerchantManageDAO;

    @Autowired
    private EmailService emailService;

    @Override
    public JSONObject createApplication(JSONObject jsonObject) {
        try {
            String userUniqueId = jsonObject.getString(LunaUserTable.FIELD_ID);

            LunaUserMerchant lunaUserMerchant = lunaUserMerchantDao.selectByPrimaryKey(userUniqueId);
            LunaTradeApplication lunaTradeApplication = new LunaTradeApplication();

            lunaTradeApplication.setMerchantId(lunaUserMerchant.getMerchantId());
            lunaTradeApplication.setUpdateTime(Calendar.getInstance().getTime());
            lunaTradeApplication.setAccountAddress(jsonObject.getString(LunaTradeApplicationTable.FIELD_ACCOUNT_ADDRESS));
            lunaTradeApplication.setAccountBank(jsonObject.getString(LunaTradeApplicationTable.FIELD_ACCOUNT_BANK));
            lunaTradeApplication.setAccountCity(jsonObject.getString(LunaTradeApplicationTable.FIELD_ACCOUNT_CITY));
            lunaTradeApplication.setAccountProvince(jsonObject.getString(LunaTradeApplicationTable.FIELD_ACCOUNT_PROVINCE));
            lunaTradeApplication.setAccountName(jsonObject.getString(LunaTradeApplicationTable.FIELD_ACCOUNT_NAME));
            lunaTradeApplication.setAccountNo(jsonObject.getString(LunaTradeApplicationTable.FIELD_ACCOUNT_NO));
            lunaTradeApplication.setAccountType(jsonObject.getInteger(LunaTradeApplicationTable.FIELD_ACCOUNT_TYPE));
            lunaTradeApplication.setAppStatus(LunaTradeApplicationTable.APP_STATUS_CHECKING);
            lunaTradeApplication.setContactName(jsonObject.getString(LunaTradeApplicationTable.FIELD_CONTACT_NAME));
            lunaTradeApplication.setContactPhone(jsonObject.getString(LunaTradeApplicationTable.FIELD_CONTACT_PHONE));
            lunaTradeApplication.setEmail(jsonObject.getString(LunaTradeApplicationTable.FIELD_EMAIL));
            lunaTradeApplication.setIdcardPeriod(jsonObject.getString(LunaTradeApplicationTable.FIELD_IDCARD_PERIOD));
            lunaTradeApplication.setIdcardPicUrl(jsonObject.getString(LunaTradeApplicationTable.FIELD_IDCARD_PIC_URL));
            lunaTradeApplication.setLicencePeriod(jsonObject.getString(LunaTradeApplicationTable.FIELD_LICENCE_PERIOD));
            lunaTradeApplication.setLicencePicUrl(jsonObject.getString(LunaTradeApplicationTable.FIELD_LICENCE_PIC_URL));
            lunaTradeApplication.setMerchantName(jsonObject.getString(LunaTradeApplicationTable.FIELD_MERCHANT_NAME));
            lunaTradeApplication.setMerchantNo(jsonObject.getString(LunaTradeApplicationTable.FIELD_MERCHANT_NO));
            lunaTradeApplication.setMerchantPhone(jsonObject.getString(LunaTradeApplicationTable.FIELD_MERCHANT_PHONE));
            lunaTradeApplicationDAO.insert(lunaTradeApplication);
            JSONObject data = new JSONObject();

            data.put(MsMerchantManageTable.FIELD_MERCHANT_ID, lunaUserMerchant.getMerchantId());
            data.put(MsMerchantManageTable.FIELD_TRADE_STATUS, MsMerchantManageTable.TRADE_STATUS_CHECKING);
            manageMerchantBL.changeMerchantTradeStatus(data.toString());

            //SEND EMAIL
            //sendEmail(TYPE_TO_MANAGER);
        } catch (Exception ex) {
            logger.error("Failed to create lunaTradeApplication", ex);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
        return FastJsonUtil.sucess("success");
    }

//    private static final int TYPE_TO_MANAGER = 0;

//    private static final int TYPE_TO_USER = 1;

//    private void sendEmail(int type) {
//        if (type == TYPE_TO_MANAGER) {
//            MailMessage message = new MailMessage("luna@visualbusiness.com", "交易直通车申请审核");
//            message.setContent("");
//            //CreateHtmlUtil.getInstance().convert2EmailHtml(toAddress, token, module_nm, currentDate,
//            //luna_nm, role_nm, webAddr)
//            emailService.sendEmail(message);
//        } else if (type == TYPE_TO_USER) {
//            MailMessage message = new MailMessage("luna@visualbusiness.com", "交易直通车申请审核");
//            message.setContent(CreateHtmlUtil.getInstance().conver2EmailPassTradeHtml());
//            emailService.sendEmail(message);
//        }
//    }

    @Override
    public JSONObject recreateApplication(JSONObject jsonObject) {
        try {
            String userUniqueId = jsonObject.getString(LunaUserTable.FIELD_ID);

            LunaUserMerchant lunaUserMerchant = lunaUserMerchantDao.selectByPrimaryKey(userUniqueId);
            LunaTradeApplicationCriteria lunaTradeApplicationCriteria = new LunaTradeApplicationCriteria();

            lunaTradeApplicationCriteria.createCriteria().andMerchantIdEqualTo(lunaUserMerchant.getMerchantId());
            List<LunaTradeApplication> applicationList = lunaTradeApplicationDAO.selectByCriteria(lunaTradeApplicationCriteria);
            if (applicationList == null || applicationList.size() == 0)
                return FastJsonUtil.error(ErrorCode.NOT_FOUND, "该业务对应商户没有提交过申请");
            LunaTradeApplication application = applicationList.get(0);
            if (application.getAppStatus().intValue() != LunaTradeApplicationTable.APP_CHECK_REFUSE) {
                return FastJsonUtil.error(ErrorCode.STATUS_ERROR, "当前申请状态不可重新申请");
            }
            application.setAppStatus(LunaTradeApplicationTable.APP_STATUS_CHECKING);
            updateApplication(application, jsonObject);
            lunaTradeApplicationDAO.updateByPrimaryKey(application);
            JSONObject data = new JSONObject();
            data.put(MsMerchantManageTable.FIELD_MERCHANT_ID, lunaUserMerchant.getMerchantId());
            data.put(MsMerchantManageTable.FIELD_TRADE_STATUS, MsMerchantManageTable.TRADE_STATUS_CHECKING);
            manageMerchantBL.changeMerchantTradeStatus(data.toString());

            //SEND EMAIL
            //sendEmail(TYPE_TO_MANAGER);

            return FastJsonUtil.sucess("success");
        } catch (Exception ex) {
            logger.error("Failed to update the application", ex);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
    }

    @Override
    public JSONObject getApplication(JSONObject jsonObject) {
        try {
            if (jsonObject.containsKey(LunaUserTable.FIELD_ID)) {
                String userUniqueId = jsonObject.getString(LunaUserTable.FIELD_ID);

                LunaUserMerchant lunaUserMerchant = lunaUserMerchantDao.selectByPrimaryKey(userUniqueId);
                LunaTradeApplicationCriteria lunaTradeApplicationCriteria = new LunaTradeApplicationCriteria();

                lunaTradeApplicationCriteria.createCriteria().andMerchantIdEqualTo(lunaUserMerchant.getMerchantId());
                List<LunaTradeApplication> applicationList = lunaTradeApplicationDAO.selectByCriteria(lunaTradeApplicationCriteria);
                if (applicationList == null || applicationList.size() == 0) {
                    return FastJsonUtil.error(ErrorCode.NOT_FOUND, "该业务对应商户没有提交申请");
                }
                JSONObject result = new JSONObject();

                assembleApplication(result, applicationList.get(0));

                return FastJsonUtil.sucess("success", result);
            } else if (jsonObject.containsKey(LunaTradeApplicationTable.FIELD_ID)) {
                Integer applicationId = jsonObject.getInteger(LunaTradeApplicationTable.FIELD_ID);
                LunaTradeApplication application = lunaTradeApplicationDAO.selectByPrimaryKey(applicationId);
                if (application == null) {
                    return FastJsonUtil.error(ErrorCode.NOT_FOUND, "该业务对应商户没有提交申请");
                }
                JSONObject result = new JSONObject();

                assembleApplication(result, application);

                return FastJsonUtil.sucess("success", result);
            } else {
                return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
            }

        } catch (Exception ex) {
            logger.error("Failed to get lunaTradeApplication", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
    }

    private void assembleApplication(JSONObject result, LunaTradeApplication application) {
        result.put(LunaTradeApplicationTable.FIELD_ID, application.getApplicationId());
        result.put(LunaTradeApplicationTable.FIELD_LICENCE_PERIOD, application.getLicencePeriod());
        result.put(LunaTradeApplicationTable.FIELD_CONTACT_PHONE, application.getContactPhone());
        result.put(LunaTradeApplicationTable.FIELD_CONTACT_NAME, application.getContactName());
        result.put(LunaTradeApplicationTable.FIELD_ACCOUNT_TYPE, application.getAccountType());
        result.put(LunaTradeApplicationTable.FIELD_ACCOUNT_NO, application.getAccountNo());
        result.put(LunaTradeApplicationTable.FIELD_ACCOUNT_NAME, application.getAccountName());
        result.put(LunaTradeApplicationTable.FIELD_ACCOUNT_ADDRESS, application.getAccountAddress());
        result.put(LunaTradeApplicationTable.FIELD_ACCOUNT_BANK, application.getAccountBank());
        result.put(LunaTradeApplicationTable.FIELD_IDCARD_PERIOD, application.getIdcardPeriod());
        result.put(LunaTradeApplicationTable.FIELD_IDCARD_PIC_URL, application.getIdcardPicUrl());
        result.put(LunaTradeApplicationTable.FIELD_ACCOUNT_CITY, application.getAccountCity());
        result.put(LunaTradeApplicationTable.FIELD_ACCOUNT_PROVINCE, application.getAccountProvince());
        result.put(LunaTradeApplicationTable.FIELD_APP_STATUS, application.getAppStatus());
        result.put(LunaTradeApplicationTable.FIELD_EMAIL, application.getEmail());
        result.put(LunaTradeApplicationTable.FIELD_LICENCE_PIC_URL, application.getLicencePicUrl());
        result.put(LunaTradeApplicationTable.FIELD_MERCHANT_ID, application.getMerchantId());
        result.put(LunaTradeApplicationTable.FIELD_MERCHANT_NAME, application.getMerchantName());
        result.put(LunaTradeApplicationTable.FIELD_MERCHANT_NO, application.getMerchantNo());
        result.put(LunaTradeApplicationTable.FIELD_MERCHANT_PHONE, application.getMerchantPhone());
        result.put(LunaTradeApplicationTable.FIELD_UPDATE_TIME, application.getUpdateTime());
    }

    @Override
    public JSONObject getApplicationList(int offset, int limit) {
        try {
            LunaTradeApplicationParameter lunaTradeApplicationParameter = new LunaTradeApplicationParameter();
            lunaTradeApplicationParameter.setMax(limit);
            lunaTradeApplicationParameter.setMin(offset);
            lunaTradeApplicationParameter.setRange("true");
            List<LunaTradeApplication> applicationList = lunaTradeApplicationDAO.selectTradeApplicationListWithLimit(
                    lunaTradeApplicationParameter);
            JSONArray r = assembleApplicationForManager(applicationList);
            int count = lunaTradeApplicationDAO.countLunaTradeApplication();
            JSONObject result = new JSONObject();
            result.put("rows", r);
            result.put("total", count);
            return FastJsonUtil.sucess("success", result);
        } catch (Exception e) {
            logger.error("Failed to get application list with limit", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
    }

    private JSONArray assembleApplicationForManager(List<LunaTradeApplication> applicationList) {
        JSONArray toSend = new JSONArray(applicationList.size());
        for (LunaTradeApplication application : applicationList) {
            JSONObject object = new JSONObject();
            String merchantId = application.getMerchantId();
            MsMerchantManage msMerchantManage = msMerchantManageDAO.selectByPrimaryKey(merchantId);
            object.put("merchantName", msMerchantManage.getMerchantNm());
            assembleApplication(object, application);
            toSend.add(object);
        }
        return toSend;
    }

    @Override
    public JSONObject getApplicationList() {
        try {
            LunaTradeApplicationCriteria criteria = new LunaTradeApplicationCriteria();
            criteria.setOrderByClause("app_status ASC, update_time DESC");
            List<LunaTradeApplication> applicationList = lunaTradeApplicationDAO.selectByCriteria(criteria);
            JSONArray toSend = assembleApplicationForManager(applicationList);
            int count = lunaTradeApplicationDAO.countLunaTradeApplication();
            JSONObject result = new JSONObject();
            result.put("rows", toSend);
            result.put("total", count);
            return FastJsonUtil.sucess("success", result);
        } catch (Exception ex) {
            logger.error("Failed to get the application list", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
    }


    private void updateApplication(LunaTradeApplication application, JSONObject jsonObject) {
        application.setUpdateTime(Calendar.getInstance().getTime());
        application.setAccountAddress(jsonObject.getString(LunaTradeApplicationTable.FIELD_ACCOUNT_ADDRESS));
        application.setAccountBank(jsonObject.getString(LunaTradeApplicationTable.FIELD_ACCOUNT_BANK));
        application.setAccountCity(jsonObject.getString(LunaTradeApplicationTable.FIELD_ACCOUNT_CITY));
        application.setAccountProvince(jsonObject.getString(LunaTradeApplicationTable.FIELD_ACCOUNT_PROVINCE));
        application.setAccountName(jsonObject.getString(LunaTradeApplicationTable.FIELD_ACCOUNT_NAME));
        application.setAccountNo(jsonObject.getString(LunaTradeApplicationTable.FIELD_ACCOUNT_NO));
        application.setAccountType(jsonObject.getInteger(LunaTradeApplicationTable.FIELD_ACCOUNT_TYPE));
        application.setAppStatus(LunaTradeApplicationTable.APP_STATUS_CHECKING);
        application.setContactName(jsonObject.getString(LunaTradeApplicationTable.FIELD_CONTACT_NAME));
        application.setContactPhone(jsonObject.getString(LunaTradeApplicationTable.FIELD_CONTACT_PHONE));
        application.setEmail(jsonObject.getString(LunaTradeApplicationTable.FIELD_EMAIL));
        application.setIdcardPeriod(jsonObject.getString(LunaTradeApplicationTable.FIELD_IDCARD_PERIOD));
        application.setIdcardPicUrl(jsonObject.getString(LunaTradeApplicationTable.FIELD_IDCARD_PIC_URL));
        application.setLicencePeriod(jsonObject.getString(LunaTradeApplicationTable.FIELD_LICENCE_PERIOD));
        application.setLicencePicUrl(jsonObject.getString(LunaTradeApplicationTable.FIELD_LICENCE_PIC_URL));
        application.setMerchantName(jsonObject.getString(LunaTradeApplicationTable.FIELD_MERCHANT_NAME));
        application.setMerchantNo(jsonObject.getString(LunaTradeApplicationTable.FIELD_MERCHANT_NO));
        application.setMerchantPhone(jsonObject.getString(LunaTradeApplicationTable.FIELD_MERCHANT_PHONE));
    }

    @Override
    public JSONObject updateApplication(JSONObject jsonObject) {
        try {
            String userUniqueId = jsonObject.getString(LunaUserTable.FIELD_ID);

            LunaUserMerchant lunaUserMerchant = lunaUserMerchantDao.selectByPrimaryKey(userUniqueId);


            Integer applicationId = jsonObject.getInteger(LunaTradeApplicationTable.FIELD_ID);
            LunaTradeApplication application = lunaTradeApplicationDAO.selectByPrimaryKey(applicationId);

            if (!lunaUserMerchant.getMerchantId().equals(application.getMerchantId())) {
                return FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "无权操作当前申请");
            }


            if (application.getAppStatus().intValue() != LunaTradeApplicationTable.APP_STATUS_CHECKING) {
                return FastJsonUtil.error(ErrorCode.STATUS_ERROR, "当前申请状态不可更改申请");
            }
            updateApplication(application, jsonObject);
            lunaTradeApplicationDAO.updateByPrimaryKey(application);
            return FastJsonUtil.sucess("success");
        } catch (Exception ex) {
            logger.error("Failed to update the application", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
    }


    @Override
    public JSONObject checkApplication(JSONObject jsonObject) {
        try {
            Integer checkResult = jsonObject.getInteger(LunaTradeApplicationTable.FIELD_APP_CHECK_RESULT);
            Integer applicationId = jsonObject.getInteger(LunaTradeApplicationTable.FIELD_ID);
            LunaTradeApplication application = lunaTradeApplicationDAO.selectByPrimaryKey(applicationId);
            if (application == null) return FastJsonUtil.error(ErrorCode.NOT_FOUND, "申请ID不存在");
            if (application.getAppStatus().intValue() != LunaTradeApplicationTable.APP_STATUS_CHECKING)
                return FastJsonUtil.error(ErrorCode.STATUS_ERROR, "申请已经审核完毕,不可重复审核");
            if (checkResult.intValue() == LunaTradeApplicationTable.APP_CHECK_ACCEPT) {
                application.setAppStatus(LunaTradeApplicationTable.APP_STATUS_OK);
                JSONObject data = new JSONObject();
                data.put(MsMerchantManageTable.FIELD_MERCHANT_ID, application.getMerchantId());
                data.put(MsMerchantManageTable.FIELD_TRADE_STATUS, MsMerchantManageTable.TRADE_STATUS_SUCCESS);
                manageMerchantBL.changeMerchantTradeStatus(data.toString());
                //sendEmail(TYPE_TO_USER);
            } else if (checkResult.intValue() == LunaTradeApplicationTable.APP_CHECK_REFUSE) {
                application.setAppStatus(LunaTradeApplicationTable.APP_STATUS_REFUSE);
                JSONObject data = new JSONObject();
                data.put(MsMerchantManageTable.FIELD_MERCHANT_ID, application.getMerchantId());
                data.put(MsMerchantManageTable.FIELD_TRADE_STATUS, MsMerchantManageTable.TRADE_STATUS_FAILED);
                manageMerchantBL.changeMerchantTradeStatus(data.toString());
            } else {
                return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "参数错误");
            }
            application.setUpdateTime(Calendar.getInstance().getTime());
            lunaTradeApplicationDAO.updateByPrimaryKey(application);
            return FastJsonUtil.sucess("success");
        } catch (Exception ex) {
            logger.error("Failed to check the application", ex);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
    }


    @Override
    public JSONObject deleteApplication(JSONObject jsonObject) {
        try {
            Integer applicationId = jsonObject.getInteger(LunaTradeApplicationTable.FIELD_ID);
            lunaTradeApplicationDAO.deleteByPrimaryKey(applicationId);
            return FastJsonUtil.sucess("success");
        } catch (Exception ex) {
            logger.error("Failed to delete the application", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
    }

    @Override
    public JSONObject getApplicationStatusByApplicationId(JSONObject jsonObject) {
        try {
            Integer applicationId = jsonObject.getInteger(LunaTradeApplicationTable.FIELD_ID);
            LunaTradeApplication application = lunaTradeApplicationDAO.selectByPrimaryKey(applicationId);
            if (application == null) {
                return FastJsonUtil.error(ErrorCode.NOT_FOUND, "申请不存在");
            }
            JSONObject result = new JSONObject();
            result.put(LunaTradeApplicationTable.FIELD_APP_STATUS, application.getAppStatus());
            return FastJsonUtil.sucess("success", result);
        } catch (Exception ex) {
            logger.error("Failed to get the application status", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
    }

    @Override
    public JSONObject getApplicationStatus(JSONObject jsonObject) {
        try {
            String userUniqueId = jsonObject.getString(LunaUserTable.FIELD_ID);

            LunaUserMerchant lunaUserMerchant = lunaUserMerchantDao.selectByPrimaryKey(userUniqueId);
            LunaTradeApplicationCriteria lunaTradeApplicationCriteria = new LunaTradeApplicationCriteria();

            List<LunaTradeApplication> applicationList = lunaTradeApplicationDAO.selectByCriteria(lunaTradeApplicationCriteria);
            if (applicationList == null || applicationList.size() == 0)
                return FastJsonUtil.error(ErrorCode.NOT_FOUND, "该业务对应商户没有提交申请");
            JSONObject result = new JSONObject();
            result.put(LunaTradeApplicationTable.FIELD_APP_STATUS, applicationList.get(0).getAppStatus());
            return FastJsonUtil.sucess("success", result);
        } catch (Exception ex) {
            logger.error("Failed to get the application status", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
    }

}
