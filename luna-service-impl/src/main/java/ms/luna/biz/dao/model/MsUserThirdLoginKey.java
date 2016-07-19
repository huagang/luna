package ms.luna.biz.dao.model;

import java.io.Serializable;

public class MsUserThirdLoginKey implements Serializable {
    private Integer mode;

    private String thirdUnionid;

    private static final long serialVersionUID = 1L;

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public String getThirdUnionid() {
        return thirdUnionid;
    }

    public void setThirdUnionid(String thirdUnionid) {
        this.thirdUnionid = thirdUnionid == null ? null : thirdUnionid.trim();
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
        MsUserThirdLoginKey other = (MsUserThirdLoginKey) that;
        return (this.getMode() == null ? other.getMode() == null : this.getMode().equals(other.getMode()))
            && (this.getThirdUnionid() == null ? other.getThirdUnionid() == null : this.getThirdUnionid().equals(other.getThirdUnionid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getMode() == null) ? 0 : getMode().hashCode());
        result = prime * result + ((getThirdUnionid() == null) ? 0 : getThirdUnionid().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "MsUserThirdLoginKey [mode=" + mode + ",thirdUnionid=" + thirdUnionid + "]";
    }
}