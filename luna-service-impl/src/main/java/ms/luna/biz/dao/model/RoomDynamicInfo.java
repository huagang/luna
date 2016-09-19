package ms.luna.biz.dao.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import ms.luna.biz.table.RoomDynamicInfoTable;

import java.io.Serializable;
import java.util.Date;
@JSONType
public class RoomDynamicInfo extends RoomDynamicInfoKey implements Serializable {
    @JSONField(name = RoomDynamicInfoTable.FIELD_IS_SPECIAL)
    private Byte isSpecial;
    @JSONField(name = RoomDynamicInfoTable.FIELD_COUNT)
    private Integer count;
    @JSONField(name = RoomDynamicInfoTable.FIELD_SOLD)
    private Integer sold;
    @JSONField(name = RoomDynamicInfoTable.FIELD_PRICE)
    private Double price;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Byte getIsSpecial() {
        return isSpecial;
    }

    public void setIsSpecial(Byte isSpecial) {
        this.isSpecial = isSpecial;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getSold() {
        return sold;
    }

    public void setSold(Integer sold) {
        this.sold = sold;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
        RoomDynamicInfo other = (RoomDynamicInfo) that;
        return (this.getRoomId() == null ? other.getRoomId() == null : this.getRoomId().equals(other.getRoomId()))
            && (this.getDay() == null ? other.getDay() == null : this.getDay().equals(other.getDay()))
            && (this.getIsSpecial() == null ? other.getIsSpecial() == null : this.getIsSpecial().equals(other.getIsSpecial()))
            && (this.getCount() == null ? other.getCount() == null : this.getCount().equals(other.getCount()))
            && (this.getSold() == null ? other.getSold() == null : this.getSold().equals(other.getSold()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRoomId() == null) ? 0 : getRoomId().hashCode());
        result = prime * result + ((getDay() == null) ? 0 : getDay().hashCode());
        result = prime * result + ((getIsSpecial() == null) ? 0 : getIsSpecial().hashCode());
        result = prime * result + ((getCount() == null) ? 0 : getCount().hashCode());
        result = prime * result + ((getSold() == null) ? 0 : getSold().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "RoomDynamicInfo [isSpecial=" + isSpecial + ",count=" + count + ",sold=" + sold + ",price=" + price + ",createTime=" + createTime + ",updateTime=" + updateTime + "->" + super.toString() + "]";
    }
}