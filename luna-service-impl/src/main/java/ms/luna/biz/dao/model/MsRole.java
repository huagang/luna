package ms.luna.biz.dao.model;

import java.io.Serializable;
import java.util.Date;

public class MsRole implements Serializable {
    private String msRoleCode;

    private String msRoleName;

    private String shortRoleName;

    private String msRoleType;

    private String msRoleAuth;

    private String bizModuleCode;

    private Integer dsOrder;

    private String note;

    private Date upHhmmss;

    private Date registHhmmss;

    private String delFlg;

    private String updatedByUniqueId;

    private static final long serialVersionUID = 1L;

    public String getMsRoleCode() {
        return msRoleCode;
    }

    public void setMsRoleCode(String msRoleCode) {
        this.msRoleCode = msRoleCode == null ? null : msRoleCode.trim();
    }

    public String getMsRoleName() {
        return msRoleName;
    }

    public void setMsRoleName(String msRoleName) {
        this.msRoleName = msRoleName == null ? null : msRoleName.trim();
    }

    public String getShortRoleName() {
        return shortRoleName;
    }

    public void setShortRoleName(String shortRoleName) {
        this.shortRoleName = shortRoleName == null ? null : shortRoleName.trim();
    }

    public String getMsRoleType() {
        return msRoleType;
    }

    public void setMsRoleType(String msRoleType) {
        this.msRoleType = msRoleType == null ? null : msRoleType.trim();
    }

    public String getMsRoleAuth() {
        return msRoleAuth;
    }

    public void setMsRoleAuth(String msRoleAuth) {
        this.msRoleAuth = msRoleAuth == null ? null : msRoleAuth.trim();
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
        MsRole other = (MsRole) that;
        return (this.getMsRoleCode() == null ? other.getMsRoleCode() == null : this.getMsRoleCode().equals(other.getMsRoleCode()))
            && (this.getMsRoleName() == null ? other.getMsRoleName() == null : this.getMsRoleName().equals(other.getMsRoleName()))
            && (this.getShortRoleName() == null ? other.getShortRoleName() == null : this.getShortRoleName().equals(other.getShortRoleName()))
            && (this.getMsRoleType() == null ? other.getMsRoleType() == null : this.getMsRoleType().equals(other.getMsRoleType()))
            && (this.getMsRoleAuth() == null ? other.getMsRoleAuth() == null : this.getMsRoleAuth().equals(other.getMsRoleAuth()))
            && (this.getBizModuleCode() == null ? other.getBizModuleCode() == null : this.getBizModuleCode().equals(other.getBizModuleCode()))
            && (this.getDsOrder() == null ? other.getDsOrder() == null : this.getDsOrder().equals(other.getDsOrder()))
            && (this.getNote() == null ? other.getNote() == null : this.getNote().equals(other.getNote()))
            && (this.getUpHhmmss() == null ? other.getUpHhmmss() == null : this.getUpHhmmss().equals(other.getUpHhmmss()))
            && (this.getRegistHhmmss() == null ? other.getRegistHhmmss() == null : this.getRegistHhmmss().equals(other.getRegistHhmmss()))
            && (this.getDelFlg() == null ? other.getDelFlg() == null : this.getDelFlg().equals(other.getDelFlg()))
            && (this.getUpdatedByUniqueId() == null ? other.getUpdatedByUniqueId() == null : this.getUpdatedByUniqueId().equals(other.getUpdatedByUniqueId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getMsRoleCode() == null) ? 0 : getMsRoleCode().hashCode());
        result = prime * result + ((getMsRoleName() == null) ? 0 : getMsRoleName().hashCode());
        result = prime * result + ((getShortRoleName() == null) ? 0 : getShortRoleName().hashCode());
        result = prime * result + ((getMsRoleType() == null) ? 0 : getMsRoleType().hashCode());
        result = prime * result + ((getMsRoleAuth() == null) ? 0 : getMsRoleAuth().hashCode());
        result = prime * result + ((getBizModuleCode() == null) ? 0 : getBizModuleCode().hashCode());
        result = prime * result + ((getDsOrder() == null) ? 0 : getDsOrder().hashCode());
        result = prime * result + ((getNote() == null) ? 0 : getNote().hashCode());
        result = prime * result + ((getUpHhmmss() == null) ? 0 : getUpHhmmss().hashCode());
        result = prime * result + ((getRegistHhmmss() == null) ? 0 : getRegistHhmmss().hashCode());
        result = prime * result + ((getDelFlg() == null) ? 0 : getDelFlg().hashCode());
        result = prime * result + ((getUpdatedByUniqueId() == null) ? 0 : getUpdatedByUniqueId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "MsRole [msRoleCode=" + msRoleCode + ",msRoleName=" + msRoleName + ",shortRoleName=" + shortRoleName + ",msRoleType=" + msRoleType + ",msRoleAuth=" + msRoleAuth + ",bizModuleCode=" + bizModuleCode + ",dsOrder=" + dsOrder + ",note=" + note + ",upHhmmss=" + upHhmmss + ",registHhmmss=" + registHhmmss + ",delFlg=" + delFlg + ",updatedByUniqueId=" + updatedByUniqueId + "]";
    }
}