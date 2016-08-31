package ms.luna.biz.sc.impl;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.sc.SMSService;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.model.SMSMessage;
import ms.luna.schedule.service.IdentifyCodeService;
import ms.luna.schedule.service.SMSScheduler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by SDLL18 on 16/8/29.
 */
@Service("smsService")
public class SMSServiceImpl implements SMSService {

    private static final Logger logger = Logger.getLogger(SMSServiceImpl.class);

    @Autowired
    private SMSScheduler smsScheduler;


    @Override
    public JSONObject sendMessage(JSONObject jsonObject) {
        try {
            String phoneNo = jsonObject.getString("phoneNo");
            SMSMessage message = new SMSMessage(phoneNo);
            message.setContent(jsonObject.getString("content"));
            smsScheduler.sendSMS(message);
            return FastJsonUtil.sucess("success");
        } catch (Exception e) {
            logger.error("Failed to send the SMS message.", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
    }

    /**
     * @param jsonObject the JSON Object include: uniqueId, phoneNo, target;
     *                   the param uniqueId can be user's Id or phone number
     * @return
     */
    @Override
    public JSONObject sendCode(JSONObject jsonObject) {
        try {
            String uniqueId = (jsonObject.containsKey("uniqueId") ? jsonObject.getString("uniqueId") : null);
            String phoneNo = jsonObject.getString("phoneNo");
            String target = jsonObject.getString("target");
            Long time = jsonObject.getLong("time");
            SMSMessage smsMessage = new SMSMessage(phoneNo);
            String code = IdentifyCodeService.getCode((uniqueId == null ? phoneNo : uniqueId), target, time);
            smsMessage.setContent("【微景皓月】您的验证码是" + code);
            smsScheduler.sendSMS(smsMessage);
            return FastJsonUtil.sucess("success");
        } catch (Exception e) {
            logger.error("Failed to send the SMS code.", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
    }

    /**
     * @param jsonObject the JSON Object include: uniqueId, code, target;
     *                   the param uniqueId can be user's Id or phone number
     * @return
     */
    @Override
    public JSONObject checkCode(JSONObject jsonObject) {
        try {
            String uniqueId = jsonObject.getString("uniqueId");
            String code = jsonObject.getString("code");
            String target = jsonObject.getString("target");
            boolean result = IdentifyCodeService.checkCode(uniqueId, target, code);
            if (result) {
                return FastJsonUtil.sucess("success");
            } else {
                return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "验证码错误");
            }
        } catch (Exception e) {
            logger.error("Failed to check the code.", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
    }
}
