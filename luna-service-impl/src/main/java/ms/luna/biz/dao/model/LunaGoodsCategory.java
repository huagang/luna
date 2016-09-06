package ms.luna.biz.dao.model;

import java.io.Serializable;
import java.util.Date;

public class LunaGoodsCategory implements Serializable {
    private Integer id;

    private String name;

    private Integer root;

    private Date updateTime;

    private Integer depth;

    private String abbreviation;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getRoot() {
        return root;
    }

    public void setRoot(Integer root) {
        this.root = root;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation == null ? null : abbreviation.trim();
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
        LunaGoodsCategory other = (LunaGoodsCategory) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getRoot() == null ? other.getRoot() == null : this.getRoot().equals(other.getRoot()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getDepth() == null ? other.getDepth() == null : this.getDepth().equals(other.getDepth()))
            && (this.getAbbreviation() == null ? other.getAbbreviation() == null : this.getAbbreviation().equals(other.getAbbreviation()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getRoot() == null) ? 0 : getRoot().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getDepth() == null) ? 0 : getDepth().hashCode());
        result = prime * result + ((getAbbreviation() == null) ? 0 : getAbbreviation().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "LunaGoodsCategory [id=" + id + ",name=" + name + ",root=" + root + ",updateTime=" + updateTime + ",depth=" + depth + ",abbreviation=" + abbreviation + "]";
    }
}