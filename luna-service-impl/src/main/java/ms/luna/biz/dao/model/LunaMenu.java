package ms.luna.biz.dao.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import ms.luna.biz.table.LunaMenuTable;

import java.io.Serializable;
import java.util.Date;

@JSONType(ignores={})
public class LunaMenu implements Serializable {
    @JSONField(name = LunaMenuTable.FIELD_ID)
    private Integer id;
    @JSONField(name = LunaMenuTable.FIELD_NAME)
    private String name;
    @JSONField(name = LunaMenuTable.FIELD_CODE)
    private String code;
    @JSONField(name = LunaMenuTable.FIELD_URL)
    private String url;

    private Integer moduleId;

    private Integer displayOrder;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
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
        LunaMenu other = (LunaMenu) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getCode() == null ? other.getCode() == null : this.getCode().equals(other.getCode()))
            && (this.getUrl() == null ? other.getUrl() == null : this.getUrl().equals(other.getUrl()))
            && (this.getModuleId() == null ? other.getModuleId() == null : this.getModuleId().equals(other.getModuleId()))
            && (this.getDisplayOrder() == null ? other.getDisplayOrder() == null : this.getDisplayOrder().equals(other.getDisplayOrder()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getCode() == null) ? 0 : getCode().hashCode());
        result = prime * result + ((getUrl() == null) ? 0 : getUrl().hashCode());
        result = prime * result + ((getModuleId() == null) ? 0 : getModuleId().hashCode());
        result = prime * result + ((getDisplayOrder() == null) ? 0 : getDisplayOrder().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "LunaMenu [id=" + id + ",name=" + name + ",code=" + code + ",url=" + url + ",moduleId=" + moduleId + ",displayOrder=" + displayOrder + ",updateTime=" + updateTime + "]";
    }
}