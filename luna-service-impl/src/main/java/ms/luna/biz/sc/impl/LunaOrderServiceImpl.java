package ms.luna.biz.sc.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import ms.luna.biz.cons.DbConfig;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.cons.VbConstant.OrderStatus;
import ms.luna.biz.dao.custom.LunaOrderDAO;
import ms.luna.biz.dao.custom.LunaUserMerchantDAO;
import ms.luna.biz.dao.custom.model.LunaOrderParameter;
import ms.luna.biz.dao.custom.model.LunaOrderResult;
import ms.luna.biz.dao.model.LunaOrder;
import ms.luna.biz.dao.model.LunaOrderCriteria;
import ms.luna.biz.dao.model.LunaUserMerchant;
import ms.luna.biz.sc.LunaOrderService;
import ms.luna.biz.table.LunaOrderTable;
import ms.luna.biz.util.FastJsonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;
import java.util.List;

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
        try {
            // 搜索参数设置
            LunaOrderParameter parameter = JSONObject.toJavaObject(param, LunaOrderParameter.class);
            parameter.setKeyword("%" + parameter.getKeyword() + "%");
            String unique_id = param.getString("unique_id");
            Integer role_id = param.getInteger("role_id");
            String merhant_id = getMerchantIdByUniqueId(unique_id);
            if (role_id != DbConfig.ROOT_ROLE_ID && merhant_id == null) {// 商户
                return FastJsonUtil.error(ErrorCode.NOT_FOUND, "无法根据unique_id:" + unique_id + "获取merchant_id,请检查用户id和商户id关联关系!");
            }
            if (role_id != DbConfig.ROOT_ROLE_ID) {// 高级管理员搜索时无须附加merchant_id条件
                parameter.setMerchant_id(merhant_id);
            }

            JSONObject result = new JSONObject();
            JSONArray rows = new JSONArray();
            Integer count = lunaOrderDAO.countOrdersWithFilter(parameter);
            if (count != 0) {
                List<LunaOrderResult> list = lunaOrderDAO.selectOrdersWithFilter(parameter);
                if (list != null && !list.isEmpty()) {
                    for (LunaOrderResult order : list) {
                        JSONObject row = (JSONObject) JSONObject.toJSON(order);
                        JSONArray goodsInfo = getGoodsInfoByOrderId(order.getId());
                        row.put(LunaOrderTable.GOODS, goodsInfo);
                        rows.add(row);
                    }
                }
            }
            result.put("total", count);
            result.put("rows", rows);
            result.put("msg", "success");
            result.put("code", "0");
            return result;
        } catch (Exception e) {
            logger.error("Failed to search orders.", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to search orders.");
        }
    }

    @Override
    public JSONObject getOrderInfo(int id, String unique_id, Integer role_id) {
        try {
            Pair<Boolean, Object> pair = getLunaOrder(id, unique_id, role_id);
            if (!pair.getLeft()) {
                return (JSONObject) pair.getRight();
            }
            LunaOrder lunaOrder = (LunaOrder) pair.getRight();

            JSONObject orderInfo = (JSONObject) JSON.parse(JSON.toJSONString(lunaOrder, SerializerFeature.WriteDateUseDateFormat));
            JSONArray goodsInfo = getGoodsInfoByOrderId(id);
            orderInfo.put("goods", goodsInfo);
            return FastJsonUtil.sucess("success", orderInfo);
        } catch (Exception e) {
            logger.error("Failed to get order info.", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to get order info.");
        }
    }


    @Override
    public JSONObject checkCertificateNum(String certificate_num, String unique_id) {
        try {
            String merhant_id = getMerchantIdByUniqueId(unique_id);
            if (merhant_id == null) {// 商户
                return FastJsonUtil.error(ErrorCode.NOT_FOUND, "无法根据unique_id:" + unique_id + "获取merchant_id,请检查用户id和商户id关联关系!");
            }
            LunaOrderCriteria lunaOrderCriteria = new LunaOrderCriteria();
            LunaOrderCriteria.Criteria criteria = lunaOrderCriteria.createCriteria();
            criteria.andMerchantIdEqualTo(merhant_id).andCertificateNumEqualTo(certificate_num);
            List<LunaOrder> lunaOrders = lunaOrderDAO.selectByCriteria(lunaOrderCriteria);
            if (lunaOrders == null || lunaOrders.isEmpty()) {
                return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "消费凭证不存在!");
            }
            LunaOrder order = lunaOrders.get(0);
            int status = order.getStatus();
            if (status != OrderStatus.CONSUME_WAIT || status != OrderStatus.DATE_OUT) {// 只有在等待消费获取已过期状态下才能办理入住
                return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "消费凭证不可用,请检查当前消费状态!");
            }

            // 获取订单信息
            JSONObject orderInfo = (JSONObject) JSON.parse(JSON.toJSONString(order, SerializerFeature.WriteDateUseDateFormat));
            JSONArray goodsInfo = getGoodsInfoByOrderId(order.getId());
            orderInfo.put("goods", goodsInfo);
            return FastJsonUtil.sucess("success", orderInfo);
        } catch (Exception e) {
            logger.error("Failed to check certificate number.", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to check certificate number.");
        }
    }

    @Override
    public JSONObject registrationConfirm(int id, String unique_id) {
        try {
            String merhant_id = getMerchantIdByUniqueId(unique_id);
            if (merhant_id == null) {// 商户
                return FastJsonUtil.error(ErrorCode.NOT_FOUND, "无法根据unique_id:" + unique_id + "获取merchant_id,请检查用户id和商户id关联关系!");
            }
            LunaOrder order = getLunaOrderByIdAndMerchantId(id, merhant_id);
            if(order == null) {
                return FastJsonUtil.error(ErrorCode.NOT_FOUND, "无法获取订单号信息(id:" + id + "),请检查当前用户的关联商户是否存在该订单号!");
            }
            int status = order.getStatus();
            if (status != OrderStatus.CONSUME_WAIT || status != OrderStatus.DATE_OUT) {// 只有在等待消费获取已过期状态下才能办理入住
                return FastJsonUtil.error(ErrorCode.STATUS_ERROR, "请检查当前房间状态,status:" + order.getStatus());
            }

            // 修改房间状态
            order.setStatus(OrderStatus.CONSUME_SUCCESS);// 房间状态: 等待消费 --> 消费成功
            order.setUpdateTime(new Date());
            lunaOrderDAO.updateByPrimaryKeySelective(order);
            return FastJsonUtil.sucess("success");
        } catch (Exception e) {
            logger.error("Failed to confirm registration.", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to confirm registration.");
        }
    }

    @Override
    public JSONObject cancelOrder(int id, String unique_id) {
        try {
            String merhant_id = getMerchantIdByUniqueId(unique_id);
            if (merhant_id == null) {// 商户
                return FastJsonUtil.error(ErrorCode.NOT_FOUND, "无法根据unique_id:" + unique_id + "获取merchant_id,请检查用户id和商户id关联关系!");
            }
            LunaOrder order = getLunaOrderByIdAndMerchantId(id, merhant_id);
            if(order == null) {
                return FastJsonUtil.error(ErrorCode.NOT_FOUND, "无法获取订单号信息(id:" + id + "),请检查当前用户的关联商户是否存在该订单号!");
            }
            if (order.getStatus().intValue() != OrderStatus.CONSUME_WAIT) {// 只有等待消费时才能取消
                return FastJsonUtil.error(ErrorCode.STATUS_ERROR, "请检查当前房间状态,status:" + order.getStatus());
            }
            // 改变房间状态: 等待消费-->等待退款
            order.setStatus(OrderStatus.REFUND_WAIT);
            order.setUpdateTime(new Date());
            lunaOrderDAO.updateByPrimaryKeySelective(order);
            // 退款,释放房间
            // todo

            return FastJsonUtil.sucess("success");
        } catch (Exception e) {
           logger.error("Failed to cancel order.", e);
           TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
           return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to cancel order.");
        }
    }

    @Override
    public JSONObject releaseOrder(int id, String unique_id) {
        try {
            String merhant_id = getMerchantIdByUniqueId(unique_id);
            if (merhant_id == null) {// 商户
                return FastJsonUtil.error(ErrorCode.NOT_FOUND, "无法根据unique_id:" + unique_id + "获取merchant_id,请检查用户id和商户id关联关系!");
            }
            LunaOrder order = getLunaOrderByIdAndMerchantId(id, merhant_id);
            if(order == null) {
                return FastJsonUtil.error(ErrorCode.NOT_FOUND, "无法获取订单号信息(id:" + id + "),请检查当前用户的关联商户是否存在该订单号!");
            }
            if (order.getStatus().intValue() != OrderStatus.DATE_OUT) {// 只有已过期状态才能释放房间
                return FastJsonUtil.error(ErrorCode.STATUS_ERROR, "请检查当前房间状态,status:" + order.getStatus());
            }
            // 改变房间状态: 已过期-->已释放
            order.setStatus(OrderStatus.FINISHED);
            order.setUpdateTime(new Date());
            lunaOrderDAO.updateByPrimaryKeySelective(order);
            return FastJsonUtil.sucess("success");
        } catch (Exception e) {
            logger.error("Failed to release order.", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to release order.");
        }
    }

    private Pair<Boolean, Object> getLunaOrder(int id, String unique_id, Integer role_id) {
        // left==false --> invalid param, right-->return FastJsonUtil.error()
        // left==true --> valid param, right-->order info
        Object obj;
        LunaOrder lunaOrder;
        if (role_id == DbConfig.ROOT_ROLE_ID) {// luna高级管理员
            lunaOrder = lunaOrderDAO.selectByPrimaryKey(id);
        } else {// 商户
            String merchant_id = getMerchantIdByUniqueId(unique_id);
            if (StringUtils.isBlank(merchant_id)) {
                obj = FastJsonUtil.error(ErrorCode.NOT_FOUND, "无法根据unique_id:" + unique_id + "获取merchant_id,请检查用户id和商户id关联关系!");
                return Pair.of(false, obj);
            }
            lunaOrder = getLunaOrderByIdAndMerchantId(id, merchant_id);
        }
        if (lunaOrder == null) {
            obj = FastJsonUtil.error(ErrorCode.INVALID_PARAM, "无法获取订单号信息(id:" + id + "),请检查当前用户的关联商户是否存在该订单号!");
            return Pair.of(false, obj);
        }
        obj = lunaOrder;
        return Pair.of(true, obj);
    }

    /**
     * 根据用户id获取商户id
     *
     * @param unique_id 用户id
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
     * @param id          订单id
     * @param merchant_id 商户id
     */
    private LunaOrder getLunaOrderByIdAndMerchantId(int id, String merchant_id) {
        LunaOrder lunaOrder = lunaOrderDAO.selectByPrimaryKey(id);
        if (lunaOrder != null && merchant_id.equals(lunaOrder.getMerchantId())) {
            return lunaOrder;
        }
        return null;
    }

    /**
     * 根据订单获取商品详细信息
     *
     * @param id 订单id
     */
    // todo
    private JSONArray getGoodsInfoByOrderId(int id) {
        JSONArray info = new JSONArray();
        return info;
    }
}
