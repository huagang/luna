package ms.luna.biz.dao.model;

import java.io.Serializable;
import java.util.Date;

public class MsUserLuna implements Serializable {
    private String uniqueId;

    private String org;

    private String status;

    private Date registHhmmss;

    private Date upHhmmss;

    private String updatedByUniqueId;

    private static final long serialVersionUID = 1L;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId == null ? null : uniqueId.trim();
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org == null ? null : org.trim();
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
        MsUserLuna other = (MsUserLuna) that;
        return (this.getUniqueId() == null ? other.getUniqueId() == null : this.getUniqueId().equals(other.getUniqueId()))
            && (this.getOrg() == null ? other.getOrg() == null : this.getOrg().equals(other.getOrg()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getRegistHhmmss() == null ? other.getRegistHhmmss() == null : this.getRegistHhmmss().equals(other.getRegistHhmmss()))
            && (this.getUpHhmmss() == null ? other.getUpHhmmss() == null : this.getUpHhmmss().equals(other.getUpHhmmss()))
            && (this.getUpdatedByUniqueId() == null ? other.getUpdatedByUniqueId() == null : this.getUpdatedByUniqueId().equals(other.getUpdatedByUniqueId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUniqueId() == null) ? 0 : getUniqueId().hashCode());
        result = prime * result + ((getOrg() == null) ? 0 : getOrg().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getRegistHhmmss() == null) ? 0 : getRegistHhmmss().hashCode());
        result = prime * result + ((getUpHhmmss() == null) ? 0 : getUpHhmmss().hashCode());
        result = prime * result + ((getUpdatedByUniqueId() == null) ? 0 : getUpdatedByUniqueId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "MsUserLuna [uniqueId=" + uniqueId + ",org=" + org + ",status=" + status + ",registHhmmss=" + registHhmmss + ",upHhmmss=" + upHhmmss + ",updatedByUniqueId=" + updatedByUniqueId + "]";
    }
}