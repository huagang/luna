package ms.luna.biz.dao.model;

import java.io.Serializable;

public class LunaBankBranch implements Serializable {
    private String bnkcode;

    private String clscode;

    private String citycode;

    private String lname;

    private static final long serialVersionUID = 1L;

    public String getBnkcode() {
        return bnkcode;
    }

    public void setBnkcode(String bnkcode) {
        this.bnkcode = bnkcode == null ? null : bnkcode.trim();
    }

    public String getClscode() {
        return clscode;
    }

    public void setClscode(String clscode) {
        this.clscode = clscode == null ? null : clscode.trim();
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode == null ? null : citycode.trim();
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname == null ? null : lname.trim();
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
        LunaBankBranch other = (LunaBankBranch) that;
        return (this.getBnkcode() == null ? other.getBnkcode() == null : this.getBnkcode().equals(other.getBnkcode()))
            && (this.getClscode() == null ? other.getClscode() == null : this.getClscode().equals(other.getClscode()))
            && (this.getCitycode() == null ? other.getCitycode() == null : this.getCitycode().equals(other.getCitycode()))
            && (this.getLname() == null ? other.getLname() == null : this.getLname().equals(other.getLname()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getBnkcode() == null) ? 0 : getBnkcode().hashCode());
        result = prime * result + ((getClscode() == null) ? 0 : getClscode().hashCode());
        result = prime * result + ((getCitycode() == null) ? 0 : getCitycode().hashCode());
        result = prime * result + ((getLname() == null) ? 0 : getLname().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "LunaBankBranch [bnkcode=" + bnkcode + ",clscode=" + clscode + ",citycode=" + citycode + ",lname=" + lname + "]";
    }
}