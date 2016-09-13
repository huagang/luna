package ms.luna.biz.dao.model;

import java.io.Serializable;

public class RoomBasicInfoWithBLOBs extends RoomBasicInfo implements Serializable {
    private String bedInfo;

    private String picInfo;

    private static final long serialVersionUID = 1L;

    public String getBedInfo() {
        return bedInfo;
    }

    public void setBedInfo(String bedInfo) {
        this.bedInfo = bedInfo == null ? null : bedInfo.trim();
    }

    public String getPicInfo() {
        return picInfo;
    }

    public void setPicInfo(String picInfo) {
        this.picInfo = picInfo == null ? null : picInfo.trim();
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
        RoomBasicInfoWithBLOBs other = (RoomBasicInfoWithBLOBs) that;
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
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getBedInfo() == null ? other.getBedInfo() == null : this.getBedInfo().equals(other.getBedInfo()))
            && (this.getPicInfo() == null ? other.getPicInfo() == null : this.getPicInfo().equals(other.getPicInfo()));
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
        result = prime * result + ((getBedInfo() == null) ? 0 : getBedInfo().hashCode());
        result = prime * result + ((getPicInfo() == null) ? 0 : getPicInfo().hashCode());
        return result;
    }
}