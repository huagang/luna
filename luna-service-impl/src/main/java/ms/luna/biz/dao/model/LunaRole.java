package ms.luna.biz.dao.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import ms.luna.biz.table.LunaRoleTable;

import java.io.Serializable;
import java.util.Date;

@JSONType(includes = {LunaRoleTable.FIELD_ID, LunaRoleTable.FIELD_NAME,
        LunaRoleTable.FIELD_CODE, "isAdmin", "extraValue"})
public class LunaRole implements Serializable {
    @JSONField(name = LunaRoleTable.FIELD_ID)
    private Integer id;
    @JSONField(name = LunaRoleTable.FIELD_NAME)
    private String name;
    @JSONField(name = LunaRoleTable.FIELD_CODE)
    private String code;
    @JSONField(name = LunaRoleTable.FIELD_IS_ADMIN)
    private Boolean isAdmin;

    private Integer parentId;

    private Integer categoryId;

    @JSONField(name = LunaRoleTable.FIELD_EXTRA_VALUE)
    private Integer extraValue;

    private Date updateTime;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getExtraValue() {
        return extraValue;
    }

    public void setExtraValue(Integer extraValue) {
        this.extraValue = extraValue;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
        LunaRole other = (LunaRole) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getCode() == null ? other.getCode() == null : this.getCode().equals(other.getCode()))
                && (this.getIsAdmin() == null ? other.getIsAdmin() == null : this.getIsAdmin().equals(other.getIsAdmin()))
            && (this.getParentId() == null ? other.getParentId() == null : this.getParentId().equals(other.getParentId()))
            && (this.getCategoryId() == null ? other.getCategoryId() == null : this.getCategoryId().equals(other.getCategoryId()))
            && (this.getExtraValue() == null ? other.getExtraValue() == null : this.getExtraValue().equals(other.getExtraValue()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getCode() == null) ? 0 : getCode().hashCode());
        result = prime * result + ((getIsAdmin() == null) ? 0 : getIsAdmin().hashCode());
        result = prime * result + ((getParentId() == null) ? 0 : getParentId().hashCode());
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getExtraValue() == null) ? 0 : getExtraValue().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "LunaRole [id=" + id + ",name=" + name + ",code=" + code + ",isAdmin=" + isAdmin + ",parentId=" + parentId + ",categoryId=" + categoryId + ",extraValue=" + extraValue + ",updateTime=" + updateTime + "]";
    }
}