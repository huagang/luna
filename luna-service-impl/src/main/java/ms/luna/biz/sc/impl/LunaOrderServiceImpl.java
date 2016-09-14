package ms.luna.biz.sc.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.DbConfig;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.dao.custom.LunaOrderDAO;
import ms.luna.biz.dao.custom.LunaUserMerchantDAO;
import ms.luna.biz.dao.model.LunaOrder;
import ms.luna.biz.dao.model.LunaUserMerchant;
import ms.luna.biz.sc.LunaOrderService;
import ms.luna.biz.util.FastJsonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
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

    private static final Logger logger = Logger.getLogger(LunaOrderServiceImpl.class);

    @Autowired
    private LunaUserMerchantDAO lunaUserMerchantDAO;

    @Autowired
    private LunaOrderDAO lunaOrderDAO;

    @Override
    public JSONObject searchOrders(JSONObject param) {
        return null;
    }

    @Override
    public JSONObject getOrderInfo(int id, String unique_id, Integer role_id) {
        try {
            Pair<Boolean, Object> pair = getLunaOrder(id, unique_id, role_id);
            if(!pair.getLeft()) {
                return (JSONObject)pair.getRight();
            }
            LunaOrder lunaOrder = (LunaOrder) pair.getRight();

            JSONObject orderInfo = (JSONObject) JSONObject.toJSON(lunaOrder);
            JSONArray goodsInfo = getGoodsInfo(id);
            orderInfo.put("goods", goodsInfo);
            return FastJsonUtil.sucess("success", orderInfo);
        } catch (Exception e) {
            logger.error("Failed to get order info.", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to get order info.");
        }
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

    private Pair<Boolean, Object> getLunaOrder(int id, String unique_id, Integer role_id) {
        Object obj;
        LunaOrder lunaOrder;
        if (role_id == DbConfig.ROOT_ROLE_ID) {// luna高级管理员
            lunaOrder = lunaOrderDAO.selectByPrimaryKey(id);
        } else {// 商户
            String merchant_id = getMerchantIdByUniqueId(unique_id);
            if (StringUtils.isBlank(merchant_id)) {
                obj =  FastJsonUtil.error(ErrorCode.NOT_FOUND, "无法根据unique_id:" + unique_id + "获取merchant_id,请检查用户id和商户id关联关系!");
                return Pair.of(false, obj);
            }
            lunaOrder = getLunaOrderByIdAndMerchantId(id, merchant_id);
        }
        if (lunaOrder == null) {
            obj =  FastJsonUtil.error(ErrorCode.INVALID_PARAM, "无法获取订单号信息(id:" + id + "),请检查当前用户的关联商户是否存在该订单号!");
            return Pair.of(false, obj);
        }
        obj = lunaOrder;
        return Pair.of(true, obj);
    }

    /**
     * 根据用户id获取商户id
     *
     * @param unique_id 用户id
     * @return
     */
    private String getMerchantIdByUniqueId(String unique_id) {
        LunaUserMerchant lunaUserMerchant = lunaUserMerchantDAO.selectByPrimaryKey(unique_id);
        if (lunaUserMerchant == null) {
            return null;
        }
        return lunaUserMerchant.getMerchantId();
    }

    /**
     * 根据订单id和商户id获取订单信息
     *
     * @param id 订单id
     * @param merchant_id 商户id
     * @return
     */
    private LunaOrder getLunaOrderByIdAndMerchantId(int id, String merchant_id) {
        LunaOrder lunaOrder = lunaOrderDAO.selectByPrimaryKey(id);
        if (lunaOrder != null && merchant_id.equals(lunaOrder.getMerchantId())) {
            return lunaOrder;
        }
        return null;
    }

    /**
     * 获取商品详细信息
     *
     * @param id 订单id
     * @return
     */
    private JSONArray getGoodsInfo(int id) {
        JSONArray info = new JSONArray();
        return info;
    }
}
