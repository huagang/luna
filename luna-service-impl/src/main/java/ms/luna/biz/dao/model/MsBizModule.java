package ms.luna.biz.dao.model;

import java.io.Serializable;
import java.util.Date;

public class MsBizModule implements Serializable {
    private String bizModuleCode;

    private String bizModuleName;

    private String status;

    private String dsOrder;

    private Date registHhmmss;

    private Date upHhmmss;

    private String updatedByUniqueId;

    private static final long serialVersionUID = 1L;

    public String getBizModuleCode() {
        return bizModuleCode;
    }

    public void setBizModuleCode(String bizModuleCode) {
        this.bizModuleCode = bizModuleCode == null ? null : bizModuleCode.trim();
    }

    public String getBizModuleName() {
        return bizModuleName;
    }

    public void setBizModuleName(String bizModuleName) {
        this.bizModuleName = bizModuleName == null ? null : bizModuleName.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getDsOrder() {
        return dsOrder;
    }

    public void setDsOrder(String dsOrder) {
        this.dsOrder = dsOrder == null ? null : dsOrder.trim();
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
        MsBizModule other = (MsBizModule) that;
        return (this.getBizModuleCode() == null ? other.getBizModuleCode() == null : this.getBizModuleCode().equals(other.getBizModuleCode()))
            && (this.getBizModuleName() == null ? other.getBizModuleName() == null : this.getBizModuleName().equals(other.getBizModuleName()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getDsOrder() == null ? other.getDsOrder() == null : this.getDsOrder().equals(other.getDsOrder()))
            && (this.getRegistHhmmss() == null ? other.getRegistHhmmss() == null : this.getRegistHhmmss().equals(other.getRegistHhmmss()))
            && (this.getUpHhmmss() == null ? other.getUpHhmmss() == null : this.getUpHhmmss().equals(other.getUpHhmmss()))
            && (this.getUpdatedByUniqueId() == null ? other.getUpdatedByUniqueId() == null : this.getUpdatedByUniqueId().equals(other.getUpdatedByUniqueId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getBizModuleCode() == null) ? 0 : getBizModuleCode().hashCode());
        result = prime * result + ((getBizModuleName() == null) ? 0 : getBizModuleName().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getDsOrder() == null) ? 0 : getDsOrder().hashCode());
        result = prime * result + ((getRegistHhmmss() == null) ? 0 : getRegistHhmmss().hashCode());
        result = prime * result + ((getUpHhmmss() == null) ? 0 : getUpHhmmss().hashCode());
        result = prime * result + ((getUpdatedByUniqueId() == null) ? 0 : getUpdatedByUniqueId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "MsBizModule [bizModuleCode=" + bizModuleCode + ",bizModuleName=" + bizModuleName + ",status=" + status + ",dsOrder=" + dsOrder + ",registHhmmss=" + registHhmmss + ",upHhmmss=" + upHhmmss + ",updatedByUniqueId=" + updatedByUniqueId + "]";
    }
}