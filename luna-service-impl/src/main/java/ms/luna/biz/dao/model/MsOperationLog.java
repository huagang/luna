package ms.luna.biz.dao.model;

import com.alibaba.fastjson.annotation.JSONField;
import ms.luna.biz.table.MsOperationLogTable;

import java.io.Serializable;
import java.util.Date;

public class MsOperationLog implements Serializable {
    @JSONField(name = MsOperationLogTable.ID)
    private Integer id;
    @JSONField(name = MsOperationLogTable.RESOURCE_ID)
    private String resourceId;
    @JSONField(name = MsOperationLogTable.TYPE)
    private String type;
    @JSONField(name = MsOperationLogTable.NOTE)
    private String note;
    @JSONField(name = MsOperationLogTable.UNIQUE_ID)
    private String uniqueId;
    @JSONField(name = MsOperationLogTable.REGIST_HHMMSS)
    private Date registHhmmss;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId == null ? null : resourceId.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId == null ? null : uniqueId.trim();
    }

    public Date getRegistHhmmss() {
        return registHhmmss;
    }

    public void setRegistHhmmss(Date registHhmmss) {
        this.registHhmmss = registHhmmss;
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
        MsOperationLog other = (MsOperationLog) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getResourceId() == null ? other.getResourceId() == null : this.getResourceId().equals(other.getResourceId()))
                && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
                && (this.getNote() == null ? other.getNote() == null : this.getNote().equals(other.getNote()))
                && (this.getUniqueId() == null ? other.getUniqueId() == null : this.getUniqueId().equals(other.getUniqueId()))
                && (this.getRegistHhmmss() == null ? other.getRegistHhmmss() == null : this.getRegistHhmmss().equals(other.getRegistHhmmss()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getResourceId() == null) ? 0 : getResourceId().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getNote() == null) ? 0 : getNote().hashCode());
        result = prime * result + ((getUniqueId() == null) ? 0 : getUniqueId().hashCode());
        result = prime * result + ((getRegistHhmmss() == null) ? 0 : getRegistHhmmss().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "MsOperationLog [id=" + id + ",resourceId=" + resourceId + ",type=" + type + ",note=" + note + ",uniqueId=" + uniqueId + ",registHhmmss=" + registHhmmss + "]";
    }
}