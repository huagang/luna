package ms.luna.biz.dao.model;

import java.io.Serializable;

public class MsRUserRoleKey implements Serializable {
    private String msRoleCode;

    private String uniqueId;

    private static final long serialVersionUID = 1L;

    public String getMsRoleCode() {
        return msRoleCode;
    }

    public void setMsRoleCode(String msRoleCode) {
        this.msRoleCode = msRoleCode == null ? null : msRoleCode.trim();
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId == null ? null : uniqueId.trim();
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
        MsRUserRoleKey other = (MsRUserRoleKey) that;
        return (this.getMsRoleCode() == null ? other.getMsRoleCode() == null : this.getMsRoleCode().equals(other.getMsRoleCode()))
            && (this.getUniqueId() == null ? other.getUniqueId() == null : this.getUniqueId().equals(other.getUniqueId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getMsRoleCode() == null) ? 0 : getMsRoleCode().hashCode());
        result = prime * result + ((getUniqueId() == null) ? 0 : getUniqueId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "MsRUserRoleKey [msRoleCode=" + msRoleCode + ",uniqueId=" + uniqueId + "]";
    }
}