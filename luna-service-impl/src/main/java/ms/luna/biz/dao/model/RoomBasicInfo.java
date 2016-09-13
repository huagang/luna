package ms.luna.biz.dao.model;

import java.io.Serializable;
import java.util.Date;

public class RoomBasicInfo implements Serializable {
    private Integer id;

    private String name;

    private String merchantId;

    private Integer cateId;

    private Date startTime;

    private Date endTime;

    private Double workdayPrice;

    private Double weekendPrice;

    private Integer count;

    private Double area;

    private Integer floor;

    private Integer persons;

    private Integer decoration;

    private Integer landscape;

    private Byte breakfast;

    private Integer roomNum;

    private String basicEquip;

    private String bedroomEquip;

    private String bathEquip;

    private String cookEquip;

    private String entertainmentEquip;

    private Integer picType;

    private Byte isOnline;

    private Byte isDel;

    private Date publishTime;

    private Date createTime;

    private Date updateTime;

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

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId == null ? null : merchantId.trim();
    }

    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Double getWorkdayPrice() {
        return workdayPrice;
    }

    public void setWorkdayPrice(Double workdayPrice) {
        this.workdayPrice = workdayPrice;
    }

    public Double getWeekendPrice() {
        return weekendPrice;
    }

    public void setWeekendPrice(Double weekendPrice) {
        this.weekendPrice = weekendPrice;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getPersons() {
        return persons;
    }

    public void setPersons(Integer persons) {
        this.persons = persons;
    }

    public Integer getDecoration() {
        return decoration;
    }

    public void setDecoration(Integer decoration) {
        this.decoration = decoration;
    }

    public Integer getLandscape() {
        return landscape;
    }

    public void setLandscape(Integer landscape) {
        this.landscape = landscape;
    }

    public Byte getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(Byte breakfast) {
        this.breakfast = breakfast;
    }

    public Integer getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(Integer roomNum) {
        this.roomNum = roomNum;
    }

    public String getBasicEquip() {
        return basicEquip;
    }

    public void setBasicEquip(String basicEquip) {
        this.basicEquip = basicEquip == null ? null : basicEquip.trim();
    }

    public String getBedroomEquip() {
        return bedroomEquip;
    }

    public void setBedroomEquip(String bedroomEquip) {
        this.bedroomEquip = bedroomEquip == null ? null : bedroomEquip.trim();
    }

    public String getBathEquip() {
        return bathEquip;
    }

    public void setBathEquip(String bathEquip) {
        this.bathEquip = bathEquip == null ? null : bathEquip.trim();
    }

    public String getCookEquip() {
        return cookEquip;
    }

    public void setCookEquip(String cookEquip) {
        this.cookEquip = cookEquip == null ? null : cookEquip.trim();
    }

    public String getEntertainmentEquip() {
        return entertainmentEquip;
    }

    public void setEntertainmentEquip(String entertainmentEquip) {
        this.entertainmentEquip = entertainmentEquip == null ? null : entertainmentEquip.trim();
    }

    public Integer getPicType() {
        return picType;
    }

    public void setPicType(Integer picType) {
        this.picType = picType;
    }

    public Byte getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Byte isOnline) {
        this.isOnline = isOnline;
    }

    public Byte getIsDel() {
        return isDel;
    }

    public void setIsDel(Byte isDel) {
        this.isDel = isDel;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
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
        RoomBasicInfo other = (RoomBasicInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getMerchantId() == null ? other.getMerchantId() == null : this.getMerchantId().equals(other.getMerchantId()))
            && (this.getCateId() == null ? other.getCateId() == null : this.getCateId().equals(other.getCateId()))
            && (this.getStartTime() == null ? other.getStartTime() == null : this.getStartTime().equals(other.getStartTime()))
            && (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()))
            && (this.getWorkdayPrice() == null ? other.getWorkdayPrice() == null : this.getWorkdayPrice().equals(other.getWorkdayPrice()))
            && (this.getWeekendPrice() == null ? other.getWeekendPrice() == null : this.getWeekendPrice().equals(other.getWeekendPrice()))
            && (this.getCount() == null ? other.getCount() == null : this.getCount().equals(other.getCount()))
            && (this.getArea() == null ? other.getArea() == null : this.getArea().equals(other.getArea()))
            && (this.getFloor() == null ? other.getFloor() == null : this.getFloor().equals(other.getFloor()))
            && (this.getPersons() == null ? other.getPersons() == null : this.getPersons().equals(other.getPersons()))
            && (this.getDecoration() == null ? other.getDecoration() == null : this.getDecoration().equals(other.getDecoration()))
            && (this.getLandscape() == null ? other.getLandscape() == null : this.getLandscape().equals(other.getLandscape()))
            && (this.getBreakfast() == null ? other.getBreakfast() == null : this.getBreakfast().equals(other.getBreakfast()))
            && (this.getRoomNum() == null ? other.getRoomNum() == null : this.getRoomNum().equals(other.getRoomNum()))
            && (this.getBasicEquip() == null ? other.getBasicEquip() == null : this.getBasicEquip().equals(other.getBasicEquip()))
            && (this.getBedroomEquip() == null ? other.getBedroomEquip() == null : this.getBedroomEquip().equals(other.getBedroomEquip()))
            && (this.getBathEquip() == null ? other.getBathEquip() == null : this.getBathEquip().equals(other.getBathEquip()))
            && (this.getCookEquip() == null ? other.getCookEquip() == null : this.getCookEquip().equals(other.getCookEquip()))
            && (this.getEntertainmentEquip() == null ? other.getEntertainmentEquip() == null : this.getEntertainmentEquip().equals(other.getEntertainmentEquip()))
            && (this.getPicType() == null ? other.getPicType() == null : this.getPicType().equals(other.getPicType()))
            && (this.getIsOnline() == null ? other.getIsOnline() == null : this.getIsOnline().equals(other.getIsOnline()))
            && (this.getIsDel() == null ? other.getIsDel() == null : this.getIsDel().equals(other.getIsDel()))
            && (this.getPublishTime() == null ? other.getPublishTime() == null : this.getPublishTime().equals(other.getPublishTime()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getMerchantId() == null) ? 0 : getMerchantId().hashCode());
        result = prime * result + ((getCateId() == null) ? 0 : getCateId().hashCode());
        result = prime * result + ((getStartTime() == null) ? 0 : getStartTime().hashCode());
        result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
        result = prime * result + ((getWorkdayPrice() == null) ? 0 : getWorkdayPrice().hashCode());
        result = prime * result + ((getWeekendPrice() == null) ? 0 : getWeekendPrice().hashCode());
        result = prime * result + ((getCount() == null) ? 0 : getCount().hashCode());
        result = prime * result + ((getArea() == null) ? 0 : getArea().hashCode());
        result = prime * result + ((getFloor() == null) ? 0 : getFloor().hashCode());
        result = prime * result + ((getPersons() == null) ? 0 : getPersons().hashCode());
        result = prime * result + ((getDecoration() == null) ? 0 : getDecoration().hashCode());
        result = prime * result + ((getLandscape() == null) ? 0 : getLandscape().hashCode());
        result = prime * result + ((getBreakfast() == null) ? 0 : getBreakfast().hashCode());
        result = prime * result + ((getRoomNum() == null) ? 0 : getRoomNum().hashCode());
        result = prime * result + ((getBasicEquip() == null) ? 0 : getBasicEquip().hashCode());
        result = prime * result + ((getBedroomEquip() == null) ? 0 : getBedroomEquip().hashCode());
        result = prime * result + ((getBathEquip() == null) ? 0 : getBathEquip().hashCode());
        result = prime * result + ((getCookEquip() == null) ? 0 : getCookEquip().hashCode());
        result = prime * result + ((getEntertainmentEquip() == null) ? 0 : getEntertainmentEquip().hashCode());
        result = prime * result + ((getPicType() == null) ? 0 : getPicType().hashCode());
        result = prime * result + ((getIsOnline() == null) ? 0 : getIsOnline().hashCode());
        result = prime * result + ((getIsDel() == null) ? 0 : getIsDel().hashCode());
        result = prime * result + ((getPublishTime() == null) ? 0 : getPublishTime().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "RoomBasicInfo [id=" + id + ",name=" + name + ",merchantId=" + merchantId + ",cateId=" + cateId + ",startTime=" + startTime + ",endTime=" + endTime + ",workdayPrice=" + workdayPrice + ",weekendPrice=" + weekendPrice + ",count=" + count + ",area=" + area + ",floor=" + floor + ",persons=" + persons + ",decoration=" + decoration + ",landscape=" + landscape + ",breakfast=" + breakfast + ",roomNum=" + roomNum + ",basicEquip=" + basicEquip + ",bedroomEquip=" + bedroomEquip + ",bathEquip=" + bathEquip + ",cookEquip=" + cookEquip + ",entertainmentEquip=" + entertainmentEquip + ",picType=" + picType + ",isOnline=" + isOnline + ",isDel=" + isDel + ",publishTime=" + publishTime + ",createTime=" + createTime + ",updateTime=" + updateTime + "]";
    }
}