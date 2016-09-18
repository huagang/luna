package ms.luna.biz.dao.model;

import java.io.Serializable;
import java.util.Date;

public class MsShowApp implements Serializable {
    private Integer appId;

    private String appName;

    private String appCode;

    private Integer businessId;

    private String shareInfoTitle;

    private String shareInfoDes;

    private String shareInfoPic;

    private Byte appStatus;

    private String appAddr;

    private String owner;

    private Date publishTime;

    private String picThumb;

    private String note;

    private Date upHhmmss;

    private Date registHhmmss;

    private String delFlg;

    private String updatedByWjnm;

    private Integer type;

    private static final long serialVersionUID = 1L;

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName == null ? null : appName.trim();
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode == null ? null : appCode.trim();
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public String getShareInfoTitle() {
        return shareInfoTitle;
    }

    public void setShareInfoTitle(String shareInfoTitle) {
        this.shareInfoTitle = shareInfoTitle == null ? null : shareInfoTitle.trim();
    }

    public String getShareInfoDes() {
        return shareInfoDes;
    }

    public void setShareInfoDes(String shareInfoDes) {
        this.shareInfoDes = shareInfoDes == null ? null : shareInfoDes.trim();
    }

    public String getShareInfoPic() {
        return shareInfoPic;
    }

    public void setShareInfoPic(String shareInfoPic) {
        this.shareInfoPic = shareInfoPic == null ? null : shareInfoPic.trim();
    }

    public Byte getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(Byte appStatus) {
        this.appStatus = appStatus;
    }

    public String getAppAddr() {
        return appAddr;
    }

    public void setAppAddr(String appAddr) {
        this.appAddr = appAddr == null ? null : appAddr.trim();
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner == null ? null : owner.trim();
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getPicThumb() {
        return picThumb;
    }

    public void setPicThumb(String picThumb) {
        this.picThumb = picThumb == null ? null : picThumb.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public Date getUpHhmmss() {
        return upHhmmss;
    }

    public void setUpHhmmss(Date upHhmmss) {
        this.upHhmmss = upHhmmss;
    }

    public Date getRegistHhmmss() {
        return registHhmmss;
    }

    public void setRegistHhmmss(Date registHhmmss) {
        this.registHhmmss = registHhmmss;
    }

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg == null ? null : delFlg.trim();
    }

    public String getUpdatedByWjnm() {
        return updatedByWjnm;
    }

    public void setUpdatedByWjnm(String updatedByWjnm) {
        this.updatedByWjnm = updatedByWjnm == null ? null : updatedByWjnm.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
        MsShowApp other = (MsShowApp) that;
        return (this.getAppId() == null ? other.getAppId() == null : this.getAppId().equals(other.getAppId()))
            && (this.getAppName() == null ? other.getAppName() == null : this.getAppName().equals(other.getAppName()))
            && (this.getAppCode() == null ? other.getAppCode() == null : this.getAppCode().equals(other.getAppCode()))
            && (this.getBusinessId() == null ? other.getBusinessId() == null : this.getBusinessId().equals(other.getBusinessId()))
            && (this.getShareInfoTitle() == null ? other.getShareInfoTitle() == null : this.getShareInfoTitle().equals(other.getShareInfoTitle()))
            && (this.getShareInfoDes() == null ? other.getShareInfoDes() == null : this.getShareInfoDes().equals(other.getShareInfoDes()))
            && (this.getShareInfoPic() == null ? other.getShareInfoPic() == null : this.getShareInfoPic().equals(other.getShareInfoPic()))
            && (this.getAppStatus() == null ? other.getAppStatus() == null : this.getAppStatus().equals(other.getAppStatus()))
            && (this.getAppAddr() == null ? other.getAppAddr() == null : this.getAppAddr().equals(other.getAppAddr()))
            && (this.getOwner() == null ? other.getOwner() == null : this.getOwner().equals(other.getOwner()))
            && (this.getPublishTime() == null ? other.getPublishTime() == null : this.getPublishTime().equals(other.getPublishTime()))
            && (this.getPicThumb() == null ? other.getPicThumb() == null : this.getPicThumb().equals(other.getPicThumb()))
            && (this.getNote() == null ? other.getNote() == null : this.getNote().equals(other.getNote()))
            && (this.getUpHhmmss() == null ? other.getUpHhmmss() == null : this.getUpHhmmss().equals(other.getUpHhmmss()))
            && (this.getRegistHhmmss() == null ? other.getRegistHhmmss() == null : this.getRegistHhmmss().equals(other.getRegistHhmmss()))
            && (this.getDelFlg() == null ? other.getDelFlg() == null : this.getDelFlg().equals(other.getDelFlg()))
            && (this.getUpdatedByWjnm() == null ? other.getUpdatedByWjnm() == null : this.getUpdatedByWjnm().equals(other.getUpdatedByWjnm()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getAppId() == null) ? 0 : getAppId().hashCode());
        result = prime * result + ((getAppName() == null) ? 0 : getAppName().hashCode());
        result = prime * result + ((getAppCode() == null) ? 0 : getAppCode().hashCode());
        result = prime * result + ((getBusinessId() == null) ? 0 : getBusinessId().hashCode());
        result = prime * result + ((getShareInfoTitle() == null) ? 0 : getShareInfoTitle().hashCode());
        result = prime * result + ((getShareInfoDes() == null) ? 0 : getShareInfoDes().hashCode());
        result = prime * result + ((getShareInfoPic() == null) ? 0 : getShareInfoPic().hashCode());
        result = prime * result + ((getAppStatus() == null) ? 0 : getAppStatus().hashCode());
        result = prime * result + ((getAppAddr() == null) ? 0 : getAppAddr().hashCode());
        result = prime * result + ((getOwner() == null) ? 0 : getOwner().hashCode());
        result = prime * result + ((getPublishTime() == null) ? 0 : getPublishTime().hashCode());
        result = prime * result + ((getPicThumb() == null) ? 0 : getPicThumb().hashCode());
        result = prime * result + ((getNote() == null) ? 0 : getNote().hashCode());
        result = prime * result + ((getUpHhmmss() == null) ? 0 : getUpHhmmss().hashCode());
        result = prime * result + ((getRegistHhmmss() == null) ? 0 : getRegistHhmmss().hashCode());
        result = prime * result + ((getDelFlg() == null) ? 0 : getDelFlg().hashCode());
        result = prime * result + ((getUpdatedByWjnm() == null) ? 0 : getUpdatedByWjnm().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "MsShowApp [appId=" + appId + ",appName=" + appName + ",appCode=" + appCode + ",businessId=" + businessId + ",shareInfoTitle=" + shareInfoTitle + ",shareInfoDes=" + shareInfoDes + ",shareInfoPic=" + shareInfoPic + ",appStatus=" + appStatus + ",appAddr=" + appAddr + ",owner=" + owner + ",publishTime=" + publishTime + ",picThumb=" + picThumb + ",note=" + note + ",upHhmmss=" + upHhmmss + ",registHhmmss=" + registHhmmss + ",delFlg=" + delFlg + ",updatedByWjnm=" + updatedByWjnm + ",type=" + type + "]";
    }
}