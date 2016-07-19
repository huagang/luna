package ms.luna.biz.dao.model;

import java.io.Serializable;

public class MsRRoleFunctionKey implements Serializable {
    private String msFunctionCode;

    private String msRoleCode;

    private static final long serialVersionUID = 1L;

    public String getMsFunctionCode() {
        return msFunctionCode;
    }

    public void setMsFunctionCode(String msFunctionCode) {
        this.msFunctionCode = msFunctionCode == null ? null : msFunctionCode.trim();
    }

    public String getMsRoleCode() {
        return msRoleCode;
    }

    public void setMsRoleCode(String msRoleCode) {
        this.msRoleCode = msRoleCode == null ? null : msRoleCode.trim();
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
        MsRRoleFunctionKey other = (MsRRoleFunctionKey) that;
        return (this.getMsFunctionCode() == null ? other.getMsFunctionCode() == null : this.getMsFunctionCode().equals(other.getMsFunctionCode()))
            && (this.getMsRoleCode() == null ? other.getMsRoleCode() == null : this.getMsRoleCode().equals(other.getMsRoleCode()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getMsFunctionCode() == null) ? 0 : getMsFunctionCode().hashCode());
        result = prime * result + ((getMsRoleCode() == null) ? 0 : getMsRoleCode().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "MsRRoleFunctionKey [msFunctionCode=" + msFunctionCode + ",msRoleCode=" + msRoleCode + "]";
    }
}