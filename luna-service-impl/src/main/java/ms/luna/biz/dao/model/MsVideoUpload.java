package ms.luna.biz.dao.model;

import java.io.Serializable;
import java.util.Date;

public class MsVideoUpload implements Serializable {
    private String vodFileId;

    private String vodOriginalFileUrl;

    private String vodNormalMp4Url;

    private String vodPhoneHlsUrl;

    private String status;

    private Date registHhmmss;

    private Date upHhmmss;

    private String updatedByUniqueId;

    private static final long serialVersionUID = 1L;

    public String getVodFileId() {
        return vodFileId;
    }

    public void setVodFileId(String vodFileId) {
        this.vodFileId = vodFileId == null ? null : vodFileId.trim();
    }

    public String getVodOriginalFileUrl() {
        return vodOriginalFileUrl;
    }

    public void setVodOriginalFileUrl(String vodOriginalFileUrl) {
        this.vodOriginalFileUrl = vodOriginalFileUrl == null ? null : vodOriginalFileUrl.trim();
    }

    public String getVodNormalMp4Url() {
        return vodNormalMp4Url;
    }

    public void setVodNormalMp4Url(String vodNormalMp4Url) {
        this.vodNormalMp4Url = vodNormalMp4Url == null ? null : vodNormalMp4Url.trim();
    }

    public String getVodPhoneHlsUrl() {
        return vodPhoneHlsUrl;
    }

    public void setVodPhoneHlsUrl(String vodPhoneHlsUrl) {
        this.vodPhoneHlsUrl = vodPhoneHlsUrl == null ? null : vodPhoneHlsUrl.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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

    public String getUpdatedByUniqueId() {
        return updatedByUniqueId;
    }

    public void setUpdatedByUniqueId(String updatedByUniqueId) {
        this.updatedByUniqueId = updatedByUniqueId == null ? null : updatedByUniqueId.trim();
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
        MsVideoUpload other = (MsVideoUpload) that;
        return (this.getVodFileId() == null ? other.getVodFileId() == null : this.getVodFileId().equals(other.getVodFileId()))
            && (this.getVodOriginalFileUrl() == null ? other.getVodOriginalFileUrl() == null : this.getVodOriginalFileUrl().equals(other.getVodOriginalFileUrl()))
            && (this.getVodNormalMp4Url() == null ? other.getVodNormalMp4Url() == null : this.getVodNormalMp4Url().equals(other.getVodNormalMp4Url()))
            && (this.getVodPhoneHlsUrl() == null ? other.getVodPhoneHlsUrl() == null : this.getVodPhoneHlsUrl().equals(other.getVodPhoneHlsUrl()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getRegistHhmmss() == null ? other.getRegistHhmmss() == null : this.getRegistHhmmss().equals(other.getRegistHhmmss()))
            && (this.getUpHhmmss() == null ? other.getUpHhmmss() == null : this.getUpHhmmss().equals(other.getUpHhmmss()))
            && (this.getUpdatedByUniqueId() == null ? other.getUpdatedByUniqueId() == null : this.getUpdatedByUniqueId().equals(other.getUpdatedByUniqueId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getVodFileId() == null) ? 0 : getVodFileId().hashCode());
        result = prime * result + ((getVodOriginalFileUrl() == null) ? 0 : getVodOriginalFileUrl().hashCode());
        result = prime * result + ((getVodNormalMp4Url() == null) ? 0 : getVodNormalMp4Url().hashCode());
        result = prime * result + ((getVodPhoneHlsUrl() == null) ? 0 : getVodPhoneHlsUrl().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getRegistHhmmss() == null) ? 0 : getRegistHhmmss().hashCode());
        result = prime * result + ((getUpHhmmss() == null) ? 0 : getUpHhmmss().hashCode());
        result = prime * result + ((getUpdatedByUniqueId() == null) ? 0 : getUpdatedByUniqueId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "MsVideoUpload [vodFileId=" + vodFileId + ",vodOriginalFileUrl=" + vodOriginalFileUrl + ",vodNormalMp4Url=" + vodNormalMp4Url + ",vodPhoneHlsUrl=" + vodPhoneHlsUrl + ",status=" + status + ",registHhmmss=" + registHhmmss + ",upHhmmss=" + upHhmmss + ",updatedByUniqueId=" + updatedByUniqueId + "]";
    }
}