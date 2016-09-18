package ms.luna.biz.dao.model;

import com.alibaba.fastjson.annotation.JSONField;
import ms.luna.biz.table.LunaOrderTable;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LunaOrder implements Serializable {

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @JSONField(name = LunaOrderTable.FIELD_ID)
    private Integer id;
    @JSONField(name = LunaOrderTable.FIELD_TRANSACTION_ID)
    private String transactionId;
    @JSONField(name = LunaOrderTable.FIELD_CERTIFICATE_NUM)
    private String certificateNum;
    @JSONField(name = LunaOrderTable.FIELD_TOTAL_MONEY)
    private Double totalMoney;
    @JSONField(name = LunaOrderTable.FIELD_PAY_MONEY)
    private Double payMoney;
    @JSONField(name = LunaOrderTable.FIELD_REFUND)
    private Double refund;
    @JSONField(name = LunaOrderTable.FIELD_STATUS)
    private Integer status;
    @JSONField(name = LunaOrderTable.FIELD_PAY_METHOD)
    private Byte payMethod;
    @JSONField(name = LunaOrderTable.FIELD_MERCHANT_ID, serialize = false)
    private String merchantId;
    @JSONField(name = LunaOrderTable.FIELD_CUSTOMER_NAME)
    private String customerName;
    @JSONField(name = LunaOrderTable.FIELD_CUSTOMER_PHONE)
    private String customerPhone;
    @JSONField(name = LunaOrderTable.FIELD_UPDATE_TIME)
    private Date updateTime;
    @JSONField(name = LunaOrderTable.FIELD_CREATE_TIME, serialize = false)
    private Date createTime;
    @JSONField(name = LunaOrderTable.FIELD_PAY_TIME, format = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;
    @JSONField(name = LunaOrderTable.FIELD_DEAL_TIME, format = "yyyy-MM-dd HH:mm:ss")
    private Date dealTime;
    @JSONField(name = LunaOrderTable.FIELD_TRADE_NO)
    private String tradeNo;
    @JSONField(name = LunaOrderTable.FIELD_PAID_MONEY)
    private Double paidMoney;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId == null ? null : transactionId.trim();
    }

    public String getCertificateNum() {
        return certificateNum;
    }

    public void setCertificateNum(String certificateNum) {
        this.certificateNum = certificateNum == null ? null : certificateNum.trim();
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Double payMoney) {
        this.payMoney = payMoney;
    }

    public Double getRefund() {
        return refund;
    }

    public void setRefund(Double refund) {
        this.refund = refund;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Byte getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Byte payMethod) {
        this.payMethod = payMethod;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId == null ? null : merchantId.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone == null ? null : customerPhone.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo == null ? null : tradeNo.trim();
    }

    public Double getPaidMoney() {
        return paidMoney;
    }

    public void setPaidMoney(Double paidMoney) {
        this.paidMoney = paidMoney;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        LunaOrder other = (LunaOrder) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTransactionId() == null ? other.getTransactionId() == null : this.getTransactionId().equals(other.getTransactionId()))
            && (this.getCertificateNum() == null ? other.getCertificateNum() == null : this.getCertificateNum().equals(other.getCertificateNum()))
            && (this.getTotalMoney() == null ? other.getTotalMoney() == null : this.getTotalMoney().equals(other.getTotalMoney()))
            && (this.getPayMoney() == null ? other.getPayMoney() == null : this.getPayMoney().equals(other.getPayMoney()))
            && (this.getRefund() == null ? other.getRefund() == null : this.getRefund().equals(other.getRefund()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getPayMethod() == null ? other.getPayMethod() == null : this.getPayMethod().equals(other.getPayMethod()))
            && (this.getMerchantId() == null ? other.getMerchantId() == null : this.getMerchantId().equals(other.getMerchantId()))
            && (this.getCustomerName() == null ? other.getCustomerName() == null : this.getCustomerName().equals(other.getCustomerName()))
            && (this.getCustomerPhone() == null ? other.getCustomerPhone() == null : this.getCustomerPhone().equals(other.getCustomerPhone()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getPayTime() == null ? other.getPayTime() == null : this.getPayTime().equals(other.getPayTime()))
            && (this.getDealTime() == null ? other.getDealTime() == null : this.getDealTime().equals(other.getDealTime()))
            && (this.getTradeNo() == null ? other.getTradeNo() == null : this.getTradeNo().equals(other.getTradeNo()))
            && (this.getPaidMoney() == null ? other.getPaidMoney() == null : this.getPaidMoney().equals(other.getPaidMoney()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTransactionId() == null) ? 0 : getTransactionId().hashCode());
        result = prime * result + ((getCertificateNum() == null) ? 0 : getCertificateNum().hashCode());
        result = prime * result + ((getTotalMoney() == null) ? 0 : getTotalMoney().hashCode());
        result = prime * result + ((getPayMoney() == null) ? 0 : getPayMoney().hashCode());
        result = prime * result + ((getRefund() == null) ? 0 : getRefund().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getPayMethod() == null) ? 0 : getPayMethod().hashCode());
        result = prime * result + ((getMerchantId() == null) ? 0 : getMerchantId().hashCode());
        result = prime * result + ((getCustomerName() == null) ? 0 : getCustomerName().hashCode());
        result = prime * result + ((getCustomerPhone() == null) ? 0 : getCustomerPhone().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getPayTime() == null) ? 0 : getPayTime().hashCode());
        result = prime * result + ((getDealTime() == null) ? 0 : getDealTime().hashCode());
        result = prime * result + ((getTradeNo() == null) ? 0 : getTradeNo().hashCode());
        result = prime * result + ((getPaidMoney() == null) ? 0 : getPaidMoney().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "LunaOrder [id=" + id + ",transactionId=" + transactionId + ",certificateNum=" + certificateNum + ",totalMoney=" + totalMoney + ",payMoney=" + payMoney + ",refund=" + refund + ",status=" + status + ",payMethod=" + payMethod + ",merchantId=" + merchantId + ",customerName=" + customerName + ",customerPhone=" + customerPhone + ",updateTime=" + updateTime + ",createTime=" + createTime + ",payTime=" + payTime + ",dealTime=" + dealTime + ",tradeNo=" + tradeNo + ",paidMoney=" + paidMoney + "]";
    }
}