/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package ms.luna.biz.sc.impl;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.sc.PicCodeService;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.schedule.service.IdentifyCodeService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Created by SDLL18 on 16/8/29.
 */
@Service("picCodeService")
public class PicCodeServiceImpl implements PicCodeService {

    private final static Logger logger = Logger.getLogger(PicCodeServiceImpl.class);


    @Override
    public Boolean saveCode(String key, String code) {
        try {
            IdentifyCodeService.saveCode(key, "picValidate", code, 600000L);
            return true;
        } catch (Exception e) {
            logger.error("Failed to save picCode into redis", e);
            return false;
        }
    }

    @Override
    public JSONObject checkCode(String key, String code) {
        try {
            IdentifyCodeService.checkCode(key, "picValidate", code);
            JSONObject toSend = new JSONObject();
            toSend.put("result", true);
            return FastJsonUtil.sucess("success", toSend);
        } catch (Exception e) {
            logger.error("Failed to check picCode in redis", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
    }
}
