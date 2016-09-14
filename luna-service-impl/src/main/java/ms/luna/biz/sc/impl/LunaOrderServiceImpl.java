package ms.luna.biz.sc.impl;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.dao.custom.LunaUserMerchantDAO;
import ms.luna.biz.dao.model.LunaUserMerchant;
import ms.luna.biz.sc.LunaOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: greek@visualbusiness.com
 * @Date: 2016-09-13
 */
@Service("lunaOrderService")
public class LunaOrderServiceImpl implements LunaOrderService {

    @Autowired
    private LunaUserMerchantDAO lunaUserMerchantDAO;

    @Override
    public JSONObject searchOrders(JSONObject param) {
        return null;
    }

    @Override
    public JSONObject getOrderInfo(int id, String unique_id) {
        return null;
    }

    @Override
    public JSONObject checkCertificateNum(String certificate_num, String unique_id) {
        return null;
    }

    @Override
    public JSONObject registrationConfirm(int id, String unique_id) {
        return null;
    }

    @Override
    public JSONObject cancelOrder(int id, String unique_id) {
        return null;
    }

    @Override
    public JSONObject releaseOrder(int id, String unique_id) {
        return null;
    }

    private String getMerchantIdByUniqueId(String unique_id) {
        LunaUserMerchant lunaUserMerchant = lunaUserMerchantDAO.selectByPrimaryKey(unique_id);
        if(lunaUserMerchant == null) {
            return null;
        }
        return lunaUserMerchant.getMerchantId();
    }
}
