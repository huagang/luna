package ms.luna.biz.dao.model;

import java.io.Serializable;
import java.util.Date;

public class MsFunction implements Serializable {
    private String msFunctionCode;

    private String msFunctionName;

    private String msFunctionStatus;

    private String bizModuleCode;

    private Integer dsOrder;

    private String status;

    private String note;

    private Date upHhmmss;

    private Date registHhmmss;

    private String updatedByUniqueId;

    private static final long serialVersionUID = 1L;

    public String getMsFunctionCode() {
        return msFunctionCode;
    }

    public void setMsFunctionCode(String msFunctionCode) {
        this.msFunctionCode = msFunctionCode == null ? null : msFunctionCode.trim();
    }

    public String getMsFunctionName() {
        return msFunctionName;
    }

    public void setMsFunctionName(String msFunctionName) {
        this.msFunctionName = msFunctionName == null ? null : msFunctionName.trim();
    }

    public String getMsFunctionStatus() {
        return msFunctionStatus;
    }

    public void setMsFunctionStatus(String msFunctionStatus) {
        this.msFunctionStatus = msFunctionStatus == null ? null : msFunctionStatus.trim();
    }

    public String getBizModuleCode() {
        return bizModuleCode;
    }

    public void setBizModuleCode(String bizModuleCode) {
        this.bizModuleCode = bizModuleCode == null ? null : bizModuleCode.trim();
    }

    public Integer getDsOrder() {
        return dsOrder;
    }

    public void setDsOrder(Integer dsOrder) {
        this.dsOrder = dsOrder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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
        MsFunction other = (MsFunction) that;
        return (this.getMsFunctionCode() == null ? other.getMsFunctionCode() == null : this.getMsFunctionCode().equals(other.getMsFunctionCode()))
            && (this.getMsFunctionName() == null ? other.getMsFunctionName() == null : this.getMsFunctionName().equals(other.getMsFunctionName()))
            && (this.getMsFunctionStatus() == null ? other.getMsFunctionStatus() == null : this.getMsFunctionStatus().equals(other.getMsFunctionStatus()))
            && (this.getBizModuleCode() == null ? other.getBizModuleCode() == null : this.getBizModuleCode().equals(other.getBizModuleCode()))
            && (this.getDsOrder() == null ? other.getDsOrder() == null : this.getDsOrder().equals(other.getDsOrder()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getNote() == null ? other.getNote() == null : this.getNote().equals(other.getNote()))
            && (this.getUpHhmmss() == null ? other.getUpHhmmss() == null : this.getUpHhmmss().equals(other.getUpHhmmss()))
            && (this.getRegistHhmmss() == null ? other.getRegistHhmmss() == null : this.getRegistHhmmss().equals(other.getRegistHhmmss()))
            && (this.getUpdatedByUniqueId() == null ? other.getUpdatedByUniqueId() == null : this.getUpdatedByUniqueId().equals(other.getUpdatedByUniqueId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getMsFunctionCode() == null) ? 0 : getMsFunctionCode().hashCode());
        result = prime * result + ((getMsFunctionName() == null) ? 0 : getMsFunctionName().hashCode());
        result = prime * result + ((getMsFunctionStatus() == null) ? 0 : getMsFunctionStatus().hashCode());
        result = prime * result + ((getBizModuleCode() == null) ? 0 : getBizModuleCode().hashCode());
        result = prime * result + ((getDsOrder() == null) ? 0 : getDsOrder().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getNote() == null) ? 0 : getNote().hashCode());
        result = prime * result + ((getUpHhmmss() == null) ? 0 : getUpHhmmss().hashCode());
        result = prime * result + ((getRegistHhmmss() == null) ? 0 : getRegistHhmmss().hashCode());
        result = prime * result + ((getUpdatedByUniqueId() == null) ? 0 : getUpdatedByUniqueId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "MsFunction [msFunctionCode=" + msFunctionCode + ",msFunctionName=" + msFunctionName + ",msFunctionStatus=" + msFunctionStatus + ",bizModuleCode=" + bizModuleCode + ",dsOrder=" + dsOrder + ",status=" + status + ",note=" + note + ",upHhmmss=" + upHhmmss + ",registHhmmss=" + registHhmmss + ",updatedByUniqueId=" + updatedByUniqueId + "]";
    }
}