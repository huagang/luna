package ms.luna.biz.dao.model;

import java.io.Serializable;
import java.util.Date;

public class LunaRegEmail implements Serializable {
    private String token;

    private Integer roleId;

    private String email;

    private Boolean status;

    private Date registHhmmss;

    private Date upHhmmss;

    private String inviteUniqueId;

    private String extra;

    private static final long serialVersionUID = 1L;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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

    public String getInviteUniqueId() {
        return inviteUniqueId;
    }

    public void setInviteUniqueId(String inviteUniqueId) {
        this.inviteUniqueId = inviteUniqueId == null ? null : inviteUniqueId.trim();
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra == null ? null : extra.trim();
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
        LunaRegEmail other = (LunaRegEmail) that;
        return (this.getToken() == null ? other.getToken() == null : this.getToken().equals(other.getToken()))
            && (this.getRoleId() == null ? other.getRoleId() == null : this.getRoleId().equals(other.getRoleId()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getRegistHhmmss() == null ? other.getRegistHhmmss() == null : this.getRegistHhmmss().equals(other.getRegistHhmmss()))
            && (this.getUpHhmmss() == null ? other.getUpHhmmss() == null : this.getUpHhmmss().equals(other.getUpHhmmss()))
            && (this.getInviteUniqueId() == null ? other.getInviteUniqueId() == null : this.getInviteUniqueId().equals(other.getInviteUniqueId()))
            && (this.getExtra() == null ? other.getExtra() == null : this.getExtra().equals(other.getExtra()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getToken() == null) ? 0 : getToken().hashCode());
        result = prime * result + ((getRoleId() == null) ? 0 : getRoleId().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getRegistHhmmss() == null) ? 0 : getRegistHhmmss().hashCode());
        result = prime * result + ((getUpHhmmss() == null) ? 0 : getUpHhmmss().hashCode());
        result = prime * result + ((getInviteUniqueId() == null) ? 0 : getInviteUniqueId().hashCode());
        result = prime * result + ((getExtra() == null) ? 0 : getExtra().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "LunaRegEmail [token=" + token + ",roleId=" + roleId + ",email=" + email + ",status=" + status + ",registHhmmss=" + registHhmmss + ",upHhmmss=" + upHhmmss + ",inviteUniqueId=" + inviteUniqueId + ",extra=" + extra + "]";
    }
}