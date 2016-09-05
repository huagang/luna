package ms.luna.biz.dao.model;

import java.io.Serializable;

public class LunaCity implements Serializable {
    private Integer cityNo;

    private Integer cityKind;

    private String cityName;

    private Integer no1;

    private Integer cityRoot;

    private Integer no2;

    private static final long serialVersionUID = 1L;

    public Integer getCityNo() {
        return cityNo;
    }

    public void setCityNo(Integer cityNo) {
        this.cityNo = cityNo;
    }

    public Integer getCityKind() {
        return cityKind;
    }

    public void setCityKind(Integer cityKind) {
        this.cityKind = cityKind;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public Integer getNo1() {
        return no1;
    }

    public void setNo1(Integer no1) {
        this.no1 = no1;
    }

    public Integer getCityRoot() {
        return cityRoot;
    }

    public void setCityRoot(Integer cityRoot) {
        this.cityRoot = cityRoot;
    }

    public Integer getNo2() {
        return no2;
    }

    public void setNo2(Integer no2) {
        this.no2 = no2;
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
        LunaCity other = (LunaCity) that;
        return (this.getCityNo() == null ? other.getCityNo() == null : this.getCityNo().equals(other.getCityNo()))
            && (this.getCityKind() == null ? other.getCityKind() == null : this.getCityKind().equals(other.getCityKind()))
            && (this.getCityName() == null ? other.getCityName() == null : this.getCityName().equals(other.getCityName()))
            && (this.getNo1() == null ? other.getNo1() == null : this.getNo1().equals(other.getNo1()))
            && (this.getCityRoot() == null ? other.getCityRoot() == null : this.getCityRoot().equals(other.getCityRoot()))
            && (this.getNo2() == null ? other.getNo2() == null : this.getNo2().equals(other.getNo2()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCityNo() == null) ? 0 : getCityNo().hashCode());
        result = prime * result + ((getCityKind() == null) ? 0 : getCityKind().hashCode());
        result = prime * result + ((getCityName() == null) ? 0 : getCityName().hashCode());
        result = prime * result + ((getNo1() == null) ? 0 : getNo1().hashCode());
        result = prime * result + ((getCityRoot() == null) ? 0 : getCityRoot().hashCode());
        result = prime * result + ((getNo2() == null) ? 0 : getNo2().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "LunaCity [cityNo=" + cityNo + ",cityKind=" + cityKind + ",cityName=" + cityName + ",no1=" + no1 + ",cityRoot=" + cityRoot + ",no2=" + no2 + "]";
    }
}