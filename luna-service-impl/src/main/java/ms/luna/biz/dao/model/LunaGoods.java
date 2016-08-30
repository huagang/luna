package ms.luna.biz.dao.model;

import com.alibaba.fastjson.annotation.JSONField;
import ms.luna.biz.table.LunaGoodsTable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class LunaGoods implements Serializable {
    @JSONField(name = LunaGoodsTable.FIELD_ID)
    private Integer id;
    @JSONField(name = LunaGoodsTable.FIELD_NAME)
    private String name;
    @JSONField(name = LunaGoodsTable.FIELD_CATEGORY_ID)
    private Integer categoryId;
    @JSONField(name = LunaGoodsTable.FIELD_DESCRIPTION)
    private String description;
    @JSONField(name = LunaGoodsTable.FIELD_PIC)
    private String pic;
    @JSONField(name = LunaGoodsTable.FIELD_PRICE)
    private BigDecimal price;
    @JSONField(name = LunaGoodsTable.FIELD_STOCK)
    private Integer stock;
    @JSONField(name = LunaGoodsTable.FIELD_TRANSPORT_FEE)
    private BigDecimal transportFee;
    @JSONField(name = LunaGoodsTable.FIELD_NOTE)
    private String note;
    @JSONField(name = LunaGoodsTable.FIELD_SALES)
    private Integer sales;
    @JSONField(name = LunaGoodsTable.FIELD_ONLINE_STATUS)
    private String onlineStatus;
    @JSONField(name = LunaGoodsTable.FIELD_MERCHANT_ID)
    private String merchantId;
    @JSONField(name = LunaGoodsTable.FIELD_BUSINESS_ID)
    private Integer businessId;
    @JSONField(name = LunaGoodsTable.FIELD_ACCOUNT)
    private BigDecimal account;
    @JSONField(name = LunaGoodsTable.FIELD_UNIQUE_ID)
    private String uniqueId;

    private Date updateTime;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public BigDecimal getTransportFee() {
        return transportFee;
    }

    public void setTransportFee(BigDecimal transportFee) {
        this.transportFee = transportFee;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus == null ? null : onlineStatus.trim();
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId == null ? null : merchantId.trim();
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId == null ? null : uniqueId.trim();
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
        LunaGoods other = (LunaGoods) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getCategoryId() == null ? other.getCategoryId() == null : this.getCategoryId().equals(other.getCategoryId()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getPic() == null ? other.getPic() == null : this.getPic().equals(other.getPic()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getStock() == null ? other.getStock() == null : this.getStock().equals(other.getStock()))
            && (this.getTransportFee() == null ? other.getTransportFee() == null : this.getTransportFee().equals(other.getTransportFee()))
            && (this.getNote() == null ? other.getNote() == null : this.getNote().equals(other.getNote()))
            && (this.getSales() == null ? other.getSales() == null : this.getSales().equals(other.getSales()))
            && (this.getOnlineStatus() == null ? other.getOnlineStatus() == null : this.getOnlineStatus().equals(other.getOnlineStatus()))
            && (this.getMerchantId() == null ? other.getMerchantId() == null : this.getMerchantId().equals(other.getMerchantId()))
            && (this.getBusinessId() == null ? other.getBusinessId() == null : this.getBusinessId().equals(other.getBusinessId()))
            && (this.getAccount() == null ? other.getAccount() == null : this.getAccount().equals(other.getAccount()))
            && (this.getUniqueId() == null ? other.getUniqueId() == null : this.getUniqueId().equals(other.getUniqueId()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getPic() == null) ? 0 : getPic().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getStock() == null) ? 0 : getStock().hashCode());
        result = prime * result + ((getTransportFee() == null) ? 0 : getTransportFee().hashCode());
        result = prime * result + ((getNote() == null) ? 0 : getNote().hashCode());
        result = prime * result + ((getSales() == null) ? 0 : getSales().hashCode());
        result = prime * result + ((getOnlineStatus() == null) ? 0 : getOnlineStatus().hashCode());
        result = prime * result + ((getMerchantId() == null) ? 0 : getMerchantId().hashCode());
        result = prime * result + ((getBusinessId() == null) ? 0 : getBusinessId().hashCode());
        result = prime * result + ((getAccount() == null) ? 0 : getAccount().hashCode());
        result = prime * result + ((getUniqueId() == null) ? 0 : getUniqueId().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "LunaGoods [id=" + id + ",name=" + name + ",categoryId=" + categoryId + ",description=" + description + ",pic=" + pic + ",price=" + price + ",stock=" + stock + ",transportFee=" + transportFee + ",note=" + note + ",sales=" + sales + ",onlineStatus=" + onlineStatus + ",merchantId=" + merchantId + ",businessId=" + businessId + ",account=" + account + ",uniqueId=" + uniqueId + ",updateTime=" + updateTime + ",createTime=" + createTime + "]";
    }
}