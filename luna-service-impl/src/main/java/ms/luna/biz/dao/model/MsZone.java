package ms.luna.biz.dao.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MsZone implements Serializable {
    private String id;

    private String name;

    private String parentId;

    private String shortNm;

    private String levelType;

    private String cityCode;

    private String zipCode;

    private String mergerName;

    private String qqFormatMergerName;

    private BigDecimal lat;

    private BigDecimal lng;

    private String pinyin;

    private String delFlg;

    private Date registHhmmss;

    private Date upHhmmss;

    private String updatedByWjnm;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getShortNm() {
        return shortNm;
    }

    public void setShortNm(String shortNm) {
        this.shortNm = shortNm == null ? null : shortNm.trim();
    }

    public String getLevelType() {
        return levelType;
    }

    public void setLevelType(String levelType) {
        this.levelType = levelType == null ? null : levelType.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode == null ? null : zipCode.trim();
    }

    public String getMergerName() {
        return mergerName;
    }

    public void setMergerName(String mergerName) {
        this.mergerName = mergerName == null ? null : mergerName.trim();
    }

    public String getQqFormatMergerName() {
        return qqFormatMergerName;
    }

    public void setQqFormatMergerName(String qqFormatMergerName) {
        this.qqFormatMergerName = qqFormatMergerName == null ? null : qqFormatMergerName.trim();
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin == null ? null : pinyin.trim();
    }

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg == null ? null : delFlg.trim();
    }

    public Date getRegistHhmmss() {
        return registHhmmss;
    }

    public void setRegistHhmmss(Date registHhmmss) {
        this.registHhmmss = registHhmmss;
    }

    public Date getUpHhmmss() {
        return upHhmmss;
    }

    public void setUpHhmmss(Date upHhmmss) {
        this.upHhmmss = upHhmmss;
    }

    public String getUpdatedByWjnm() {
        return updatedByWjnm;
    }

    public void setUpdatedByWjnm(String updatedByWjnm) {
        this.updatedByWjnm = updatedByWjnm == null ? null : updatedByWjnm.trim();
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
        MsZone other = (MsZone) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getParentId() == null ? other.getParentId() == null : this.getParentId().equals(other.getParentId()))
            && (this.getShortNm() == null ? other.getShortNm() == null : this.getShortNm().equals(other.getShortNm()))
            && (this.getLevelType() == null ? other.getLevelType() == null : this.getLevelType().equals(other.getLevelType()))
            && (this.getCityCode() == null ? other.getCityCode() == null : this.getCityCode().equals(other.getCityCode()))
            && (this.getZipCode() == null ? other.getZipCode() == null : this.getZipCode().equals(other.getZipCode()))
            && (this.getMergerName() == null ? other.getMergerName() == null : this.getMergerName().equals(other.getMergerName()))
            && (this.getQqFormatMergerName() == null ? other.getQqFormatMergerName() == null : this.getQqFormatMergerName().equals(other.getQqFormatMergerName()))
            && (this.getLat() == null ? other.getLat() == null : this.getLat().equals(other.getLat()))
            && (this.getLng() == null ? other.getLng() == null : this.getLng().equals(other.getLng()))
            && (this.getPinyin() == null ? other.getPinyin() == null : this.getPinyin().equals(other.getPinyin()))
            && (this.getDelFlg() == null ? other.getDelFlg() == null : this.getDelFlg().equals(other.getDelFlg()))
            && (this.getRegistHhmmss() == null ? other.getRegistHhmmss() == null : this.getRegistHhmmss().equals(other.getRegistHhmmss()))
            && (this.getUpHhmmss() == null ? other.getUpHhmmss() == null : this.getUpHhmmss().equals(other.getUpHhmmss()))
            && (this.getUpdatedByWjnm() == null ? other.getUpdatedByWjnm() == null : this.getUpdatedByWjnm().equals(other.getUpdatedByWjnm()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getParentId() == null) ? 0 : getParentId().hashCode());
        result = prime * result + ((getShortNm() == null) ? 0 : getShortNm().hashCode());
        result = prime * result + ((getLevelType() == null) ? 0 : getLevelType().hashCode());
        result = prime * result + ((getCityCode() == null) ? 0 : getCityCode().hashCode());
        result = prime * result + ((getZipCode() == null) ? 0 : getZipCode().hashCode());
        result = prime * result + ((getMergerName() == null) ? 0 : getMergerName().hashCode());
        result = prime * result + ((getQqFormatMergerName() == null) ? 0 : getQqFormatMergerName().hashCode());
        result = prime * result + ((getLat() == null) ? 0 : getLat().hashCode());
        result = prime * result + ((getLng() == null) ? 0 : getLng().hashCode());
        result = prime * result + ((getPinyin() == null) ? 0 : getPinyin().hashCode());
        result = prime * result + ((getDelFlg() == null) ? 0 : getDelFlg().hashCode());
        result = prime * result + ((getRegistHhmmss() == null) ? 0 : getRegistHhmmss().hashCode());
        result = prime * result + ((getUpHhmmss() == null) ? 0 : getUpHhmmss().hashCode());
        result = prime * result + ((getUpdatedByWjnm() == null) ? 0 : getUpdatedByWjnm().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "MsZone [id=" + id + ",name=" + name + ",parentId=" + parentId + ",shortNm=" + shortNm + ",levelType=" + levelType + ",cityCode=" + cityCode + ",zipCode=" + zipCode + ",mergerName=" + mergerName + ",qqFormatMergerName=" + qqFormatMergerName + ",lat=" + lat + ",lng=" + lng + ",pinyin=" + pinyin + ",delFlg=" + delFlg + ",registHhmmss=" + registHhmmss + ",upHhmmss=" + upHhmmss + ",updatedByWjnm=" + updatedByWjnm + "]";
    }
}