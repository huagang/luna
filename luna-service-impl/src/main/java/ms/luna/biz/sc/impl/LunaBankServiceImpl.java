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
import ms.luna.biz.dao.custom.LunaBankDAO;
import ms.luna.biz.dao.model.LunaBank;
import ms.luna.biz.dao.model.LunaBankCriteria;
import ms.luna.biz.sc.LunaBankService;
import ms.luna.biz.util.FastJsonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by SDLL18 on 16/8/31.
 */
@Service("lunaBankService")
public class LunaBankServiceImpl implements LunaBankService {

    private final static Logger logger = Logger.getLogger(LunaBankServiceImpl.class);

    @Autowired
    private LunaBankDAO lunaBankDAO;

    @Override
    public JSONObject getBankByCode(JSONObject jsonObject) {
        try {
            String bankCode = jsonObject.getString("bankCode");
            LunaBankCriteria criteria = new LunaBankCriteria();
            criteria.createCriteria().andBankcodeEqualTo(bankCode);
            List<LunaBank> lunaBankList = lunaBankDAO.selectByCriteria(criteria);
            if (lunaBankList.size() == 0) {
                return FastJsonUtil.error(ErrorCode.NOT_FOUND, "总行行号不存在");
            }
            JSONObject object = new JSONObject();
            object.put("bankCode", lunaBankList.get(0).getBankcode());
            object.put("bankName", lunaBankList.get(0).getBankname());
            return FastJsonUtil.sucess("success", object);
        } catch (Exception e) {
            logger.error("Failed to get bank", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
    }
}
