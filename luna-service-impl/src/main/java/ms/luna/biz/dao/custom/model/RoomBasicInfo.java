package ms.luna.biz.dao.custom.model;

import com.alibaba.fastjson.annotation.JSONField;
import ms.luna.biz.table.RoomBasicInfoTable;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class RoomBasicInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @JSONField(name = RoomBasicInfoTable.FIELD_ID)
    private Integer id;
    @JSONField(name = RoomBasicInfoTable.FIELD_NAME)
    private String name;
    @JSONField(name = RoomBasicInfoTable.FIELD_MERCHANT_ID)
    private String merchantId;
    @JSONField(name = RoomBasicInfoTable.FIELD_CATEGORY_ID)
    private Integer cateId;
    @JSONField(name = RoomBasicInfoTable.FIELD_START_TIME, format="yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @JSONField(name = RoomBasicInfoTable.FIELD_END_TIME, format="yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    @JSONField(name = RoomBasicInfoTable.FIELD_WORKDAY_PRICE)
    private Double workdayPrice;
    @JSONField(name = RoomBasicInfoTable.FIELD_WEEKEND_PRICE)
    private Double weekendPrice;
    @JSONField(name = RoomBasicInfoTable.FIELD_WORK_DAYS)
    private List<Integer> workDays;
    @JSONField(name = RoomBasicInfoTable.FIELD_WEEKEND_DAYS)
    private List<Integer> weekendDays;
    @JSONField(name = RoomBasicInfoTable.FIELD_COUNT)
    private Integer count;
    @JSONField(name = RoomBasicInfoTable.FIELD_AREA)
    private Double area;
    @JSONField(name = RoomBasicInfoTable.FIELD_FLOOR)
    private Integer floor;
    @JSONField(name = RoomBasicInfoTable.FIELD_PERSONS)
    private Integer persons;
    @JSONField(name = RoomBasicInfoTable.FIELD_DECORATION)
    private Integer decoration;
    @JSONField(name = RoomBasicInfoTable.FIELD_LANDSCAPE)
    private Integer landscape;
    @JSONField(name = RoomBasicInfoTable.FIELD_BREAKFAST)
    private Byte breakfast;
    @JSONField(name = RoomBasicInfoTable.FIELD_ROOM_NUM)
    private Integer roomNum;
    @JSONField(name = RoomBasicInfoTable.FIELD_BED_INFO)
    private Map<Integer, String> bedInfo;
    @JSONField(name = RoomBasicInfoTable.FIELD_BASIC_EQUIP)
    private List<Integer> basicEquip;
    @JSONField(name = RoomBasicInfoTable.FIELD_BEDROOM_EQUIP)
    private List<Integer> bedroomEquip;
    @JSONField(name = RoomBasicInfoTable.FIELD_BATH_EQUIP)
    private List<Integer> bathEquip;
    @JSONField(name = RoomBasicInfoTable.FIELD_COOK_EQUIP)
    private List<Integer> cookEquip;
    @JSONField(name = RoomBasicInfoTable.FIELD_ENTERTAINMENT_EQUIP)
    private List<Integer> entertainmentEquip;
    @JSONField(name = RoomBasicInfoTable.FIELD_PIC_TYPE)
    private Integer picType;
    @JSONField(name = RoomBasicInfoTable.FIELD_PIC_INFO)
    private List<String> picInfo;
    @JSONField(name = RoomBasicInfoTable.FIELD_IS_ONLINE)
    private Byte isOnline;
    private Byte isDel;
    @JSONField(name = RoomBasicInfoTable.FIELD_PUBLISH_TIME, format="yyyy-MM-dd HH:mm:ss")
    private Date publishTime;

    private Date createTime;

    private Date updateTime;

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
        this.name = name;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
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

    public List<Integer> getWorkDays() {
        return workDays;
    }

    public void setWorkDays(List<Integer> workDays) {
        this.workDays = workDays;
    }

    public List<Integer> getWeekendDays() {
        return weekendDays;
    }

    public void setWeekendDays(List<Integer> weekendDays) {
        this.weekendDays = weekendDays;
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

    public Map<Integer, String> getBedInfo() {
        return bedInfo;
    }

    public void setBedInfo(Map<Integer, String> bedInfo) {
        this.bedInfo = bedInfo;
    }

    public List<Integer> getBasicEquip() {
        return basicEquip;
    }

    public void setBasicEquip(List<Integer> basicEquip) {
        this.basicEquip = basicEquip;
    }

    public List<Integer> getBedroomEquip() {
        return bedroomEquip;
    }

    public void setBedroomEquip(List<Integer> bedroomEquip) {
        this.bedroomEquip = bedroomEquip;
    }

    public List<Integer> getBathEquip() {
        return bathEquip;
    }

    public void setBathEquip(List<Integer> bathEquip) {
        this.bathEquip = bathEquip;
    }

    public List<Integer> getCookEquip() {
        return cookEquip;
    }

    public void setCookEquip(List<Integer> cookEquip) {
        this.cookEquip = cookEquip;
    }

    public List<Integer> getEntertainmentEquip() {
        return entertainmentEquip;
    }

    public void setEntertainmentEquip(List<Integer> entertainmentEquip) {
        this.entertainmentEquip = entertainmentEquip;
    }

    public Integer getPicType() {
        return picType;
    }

    public void setPicType(Integer picType) {
        this.picType = picType;
    }

    public List<String> getPicInfo() {
        return picInfo;
    }

    public void setPicInfo(List<String> picInfo) {
        this.picInfo = picInfo;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoomBasicInfo that = (RoomBasicInfo) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (merchantId != null ? !merchantId.equals(that.merchantId) : that.merchantId != null) return false;
        if (cateId != null ? !cateId.equals(that.cateId) : that.cateId != null) return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;
        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) return false;
        if (workdayPrice != null ? !workdayPrice.equals(that.workdayPrice) : that.workdayPrice != null) return false;
        if (weekendPrice != null ? !weekendPrice.equals(that.weekendPrice) : that.weekendPrice != null) return false;
        if (workDays != null ? !workDays.equals(that.workDays) : that.workDays != null) return false;
        if (weekendDays != null ? !weekendDays.equals(that.weekendDays) : that.weekendDays != null) return false;
        if (count != null ? !count.equals(that.count) : that.count != null) return false;
        if (area != null ? !area.equals(that.area) : that.area != null) return false;
        if (floor != null ? !floor.equals(that.floor) : that.floor != null) return false;
        if (persons != null ? !persons.equals(that.persons) : that.persons != null) return false;
        if (decoration != null ? !decoration.equals(that.decoration) : that.decoration != null) return false;
        if (landscape != null ? !landscape.equals(that.landscape) : that.landscape != null) return false;
        if (breakfast != null ? !breakfast.equals(that.breakfast) : that.breakfast != null) return false;
        if (roomNum != null ? !roomNum.equals(that.roomNum) : that.roomNum != null) return false;
        if (bedInfo != null ? !bedInfo.equals(that.bedInfo) : that.bedInfo != null) return false;
        if (basicEquip != null ? !basicEquip.equals(that.basicEquip) : that.basicEquip != null) return false;
        if (bedroomEquip != null ? !bedroomEquip.equals(that.bedroomEquip) : that.bedroomEquip != null) return false;
        if (bathEquip != null ? !bathEquip.equals(that.bathEquip) : that.bathEquip != null) return false;
        if (cookEquip != null ? !cookEquip.equals(that.cookEquip) : that.cookEquip != null) return false;
        if (entertainmentEquip != null ? !entertainmentEquip.equals(that.entertainmentEquip) : that.entertainmentEquip != null)
            return false;
        if (picType != null ? !picType.equals(that.picType) : that.picType != null) return false;
        if (picInfo != null ? !picInfo.equals(that.picInfo) : that.picInfo != null) return false;
        if (isOnline != null ? !isOnline.equals(that.isOnline) : that.isOnline != null) return false;
        if (isDel != null ? !isDel.equals(that.isDel) : that.isDel != null) return false;
        if (publishTime != null ? !publishTime.equals(that.publishTime) : that.publishTime != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        return !(updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (merchantId != null ? merchantId.hashCode() : 0);
        result = 31 * result + (cateId != null ? cateId.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (workdayPrice != null ? workdayPrice.hashCode() : 0);
        result = 31 * result + (weekendPrice != null ? weekendPrice.hashCode() : 0);
        result = 31 * result + (workDays != null ? workDays.hashCode() : 0);
        result = 31 * result + (weekendDays != null ? weekendDays.hashCode() : 0);
        result = 31 * result + (count != null ? count.hashCode() : 0);
        result = 31 * result + (area != null ? area.hashCode() : 0);
        result = 31 * result + (floor != null ? floor.hashCode() : 0);
        result = 31 * result + (persons != null ? persons.hashCode() : 0);
        result = 31 * result + (decoration != null ? decoration.hashCode() : 0);
        result = 31 * result + (landscape != null ? landscape.hashCode() : 0);
        result = 31 * result + (breakfast != null ? breakfast.hashCode() : 0);
        result = 31 * result + (roomNum != null ? roomNum.hashCode() : 0);
        result = 31 * result + (bedInfo != null ? bedInfo.hashCode() : 0);
        result = 31 * result + (basicEquip != null ? basicEquip.hashCode() : 0);
        result = 31 * result + (bedroomEquip != null ? bedroomEquip.hashCode() : 0);
        result = 31 * result + (bathEquip != null ? bathEquip.hashCode() : 0);
        result = 31 * result + (cookEquip != null ? cookEquip.hashCode() : 0);
        result = 31 * result + (entertainmentEquip != null ? entertainmentEquip.hashCode() : 0);
        result = 31 * result + (picType != null ? picType.hashCode() : 0);
        result = 31 * result + (picInfo != null ? picInfo.hashCode() : 0);
        result = 31 * result + (isOnline != null ? isOnline.hashCode() : 0);
        result = 31 * result + (isDel != null ? isDel.hashCode() : 0);
        result = 31 * result + (publishTime != null ? publishTime.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RoomBasicInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", cateId=" + cateId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", workdayPrice=" + workdayPrice +
                ", weekendPrice=" + weekendPrice +
                ", workDays=" + workDays +
                ", weekendDays=" + weekendDays +
                ", count=" + count +
                ", area=" + area +
                ", floor=" + floor +
                ", persons=" + persons +
                ", decoration=" + decoration +
                ", landscape=" + landscape +
                ", breakfast=" + breakfast +
                ", roomNum=" + roomNum +
                ", bedInfo=" + bedInfo +
                ", basicEquip=" + basicEquip +
                ", bedroomEquip=" + bedroomEquip +
                ", bathEquip=" + bathEquip +
                ", cookEquip=" + cookEquip +
                ", entertainmentEquip=" + entertainmentEquip +
                ", picType=" + picType +
                ", picInfo=" + picInfo +
                ", isOnline=" + isOnline +
                ", isDel=" + isDel +
                ", publishTime=" + publishTime +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}