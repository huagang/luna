package ms.luna.biz.dao.model;

import java.io.Serializable;

public class LunaBank implements Serializable {
    private String bankcode;

    private String bankname;

    private static final long serialVersionUID = 1L;

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode == null ? null : bankcode.trim();
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname == null ? null : bankname.trim();
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
        LunaBank other = (LunaBank) that;
        return (this.getBankcode() == null ? other.getBankcode() == null : this.getBankcode().equals(other.getBankcode()))
            && (this.getBankname() == null ? other.getBankname() == null : this.getBankname().equals(other.getBankname()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getBankcode() == null) ? 0 : getBankcode().hashCode());
        result = prime * result + ((getBankname() == null) ? 0 : getBankname().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "LunaBank [bankcode=" + bankcode + ",bankname=" + bankname + "]";
    }
}