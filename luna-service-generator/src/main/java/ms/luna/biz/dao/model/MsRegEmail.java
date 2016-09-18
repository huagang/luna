package ms.luna.biz.dao.model;

import java.io.Serializable;
import java.util.Date;

public class MsRegEmail implements Serializable {
    private String token;

    private String msRoleCode;

    private String bizModuleCode;

    private String email;

    private String status;

    private Date registHhmmss;

    private Date upHhmmss;

    private String updatedByUniqueId;

    private static final long serialVersionUID = 1L;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public String getMsRoleCode() {
        return msRoleCode;
    }

    public void setMsRoleCode(String msRoleCode) {
        this.msRoleCode = msRoleCode == null ? null : msRoleCode.trim();
    }

    public String getBizModuleCode() {
        return bizModuleCode;
    }

    public void setBizModuleCode(String bizModuleCode) {
        this.bizModuleCode = bizModuleCode == null ? null : bizModuleCode.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
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
        MsRegEmail other = (MsRegEmail) that;
        return (this.getToken() == null ? other.getToken() == null : this.getToken().equals(other.getToken()))
            && (this.getMsRoleCode() == null ? other.getMsRoleCode() == null : this.getMsRoleCode().equals(other.getMsRoleCode()))
            && (this.getBizModuleCode() == null ? other.getBizModuleCode() == null : this.getBizModuleCode().equals(other.getBizModuleCode()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getRegistHhmmss() == null ? other.getRegistHhmmss() == null : this.getRegistHhmmss().equals(other.getRegistHhmmss()))
            && (this.getUpHhmmss() == null ? other.getUpHhmmss() == null : this.getUpHhmmss().equals(other.getUpHhmmss()))
            && (this.getUpdatedByUniqueId() == null ? other.getUpdatedByUniqueId() == null : this.getUpdatedByUniqueId().equals(other.getUpdatedByUniqueId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getToken() == null) ? 0 : getToken().hashCode());
        result = prime * result + ((getMsRoleCode() == null) ? 0 : getMsRoleCode().hashCode());
        result = prime * result + ((getBizModuleCode() == null) ? 0 : getBizModuleCode().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getRegistHhmmss() == null) ? 0 : getRegistHhmmss().hashCode());
        result = prime * result + ((getUpHhmmss() == null) ? 0 : getUpHhmmss().hashCode());
        result = prime * result + ((getUpdatedByUniqueId() == null) ? 0 : getUpdatedByUniqueId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "MsRegEmail [token=" + token + ",msRoleCode=" + msRoleCode + ",bizModuleCode=" + bizModuleCode + ",email=" + email + ",status=" + status + ",registHhmmss=" + registHhmmss + ",upHhmmss=" + upHhmmss + ",updatedByUniqueId=" + updatedByUniqueId + "]";
    }
}