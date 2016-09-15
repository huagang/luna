package ms.luna.biz.dao.custom.model;

import com.alibaba.fastjson.annotation.JSONField;
import ms.luna.biz.table.LunaOrderTable;

import java.util.Date;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: greek@visualbusiness.com
 * @Date: 2016-09-14
 */
public class LunaOrderResult {
    @JSONField(name = LunaOrderTable.FIELD_ID)
    private Integer id;
    @JSONField(name = LunaOrderTable.FIELD_DEAL_TIME)
    private Date deal_time;
    @JSONField(name = LunaOrderTable.FIELD_STATUS)
    private Integer status;
    @JSONField(name = LunaOrderTable.FIELD_TOTAL_MONEY)
    private Double total_money;
    @JSONField(name = LunaOrderTable.FIELD_PAY_MONEY)
    private Double pay_money;
    @JSONField(name = LunaOrderTable.FIELD_REFUND)
    private Double refund;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDeal_time() {
        return deal_time;
    }

    public void setDeal_time(Date deal_time) {
        this.deal_time = deal_time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getTotal_money() {
        return total_money;
    }

    public void setTotal_money(Double total_money) {
        this.total_money = total_money;
    }

    public Double getPay_money() {
        return pay_money;
    }

    public void setPay_money(Double pay_money) {
        this.pay_money = pay_money;
    }

    public Double getRefund() {
        return refund;
    }

    public void setRefund(Double refund) {
        this.refund = refund;
    }
}
