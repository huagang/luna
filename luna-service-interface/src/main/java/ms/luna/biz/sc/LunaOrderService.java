package ms.luna.biz.sc;

import com.alibaba.fastjson.JSONObject;

import java.util.jar.JarEntry;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: greek@visualbusiness.com
 * @Date: 2016-09-13
 */
public interface LunaOrderService {

    /**
     * 创建订单接口
     *
     * @param param
     * @return
     */
    JSONObject createOrders(JSONObject param);

    /**
     * 订单完成支付
     *
     * @param param
     * @return
     */
    JSONObject finishPayment(JSONObject param);

    // 搜索列表页
    JSONObject searchOrders(JSONObject param);

    // 订单详情
    JSONObject getOrderInfo(int id, String unique_id, Integer role_id);

    // 消费凭证验证
    JSONObject checkCertificateNum(String certificate_num, String unique_id);

    // 消费凭证确认(入住办理)
    JSONObject registrationConfirm(int id, String unique_id);

    // 商户取消订单
    JSONObject cancelOrder(int id, String unique_id);

    // 订单逾期确认(商户释放房间)
    JSONObject releaseOrder(int id, String unique_id);

}
