package ms.luna.biz.dao.model;

import java.io.Serializable;
import java.util.Date;

public class MsRRoleFunction extends MsRRoleFunctionKey implements Serializable {
    private Date upHhmmss;

    private Date registHhmmss;

    private String updatedByUniqueId;

    private static final long serialVersionUID = 1L;

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
        MsRRoleFunction other = (MsRRoleFunction) that;
        return (this.getMsFunctionCode() == null ? other.getMsFunctionCode() == null : this.getMsFunctionCode().equals(other.getMsFunctionCode()))
            && (this.getMsRoleCode() == null ? other.getMsRoleCode() == null : this.getMsRoleCode().equals(other.getMsRoleCode()))
            && (this.getUpHhmmss() == null ? other.getUpHhmmss() == null : this.getUpHhmmss().equals(other.getUpHhmmss()))
            && (this.getRegistHhmmss() == null ? other.getRegistHhmmss() == null : this.getRegistHhmmss().equals(other.getRegistHhmmss()))
            && (this.getUpdatedByUniqueId() == null ? other.getUpdatedByUniqueId() == null : this.getUpdatedByUniqueId().equals(other.getUpdatedByUniqueId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getMsFunctionCode() == null) ? 0 : getMsFunctionCode().hashCode());
        result = prime * result + ((getMsRoleCode() == null) ? 0 : getMsRoleCode().hashCode());
        result = prime * result + ((getUpHhmmss() == null) ? 0 : getUpHhmmss().hashCode());
        result = prime * result + ((getRegistHhmmss() == null) ? 0 : getRegistHhmmss().hashCode());
        result = prime * result + ((getUpdatedByUniqueId() == null) ? 0 : getUpdatedByUniqueId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "MsRRoleFunction [upHhmmss=" + upHhmmss + ",registHhmmss=" + registHhmmss + ",updatedByUniqueId=" + updatedByUniqueId + "->" + super.toString() + "]";
    }
}