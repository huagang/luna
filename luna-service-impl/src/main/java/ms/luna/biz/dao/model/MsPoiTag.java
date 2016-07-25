package ms.luna.biz.dao.model;

import java.io.Serializable;
import java.util.Date;

public class MsPoiTag implements Serializable {
    private Integer tagId;

    private String tagName;

    private Integer dsOrder;

    private Integer tagLevel;

    private Integer parentTagId;

    private String editableFlag;

    private Date registHhmmss;

    private Date upHhmmss;

    private String updatedByUniqueId;

    private static final long serialVersionUID = 1L;

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName == null ? null : tagName.trim();
    }

    public Integer getDsOrder() {
        return dsOrder;
    }

    public void setDsOrder(Integer dsOrder) {
        this.dsOrder = dsOrder;
    }

    public Integer getTagLevel() {
        return tagLevel;
    }

    public void setTagLevel(Integer tagLevel) {
        this.tagLevel = tagLevel;
    }

    public Integer getParentTagId() {
        return parentTagId;
    }

    public void setParentTagId(Integer parentTagId) {
        this.parentTagId = parentTagId;
    }

    public String getEditableFlag() {
        return editableFlag;
    }

    public void setEditableFlag(String editableFlag) {
        this.editableFlag = editableFlag == null ? null : editableFlag.trim();
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

    public String getUpdatedByUniqueId() {
        return updatedByUniqueId;
    }

    public void setUpdatedByUniqueId(String updatedByUniqueId) {
        this.updatedByUniqueId = updatedByUniqueId == null ? null : updatedByUniqueId.trim();
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
        MsPoiTag other = (MsPoiTag) that;
        return (this.getTagId() == null ? other.getTagId() == null : this.getTagId().equals(other.getTagId()))
            && (this.getTagName() == null ? other.getTagName() == null : this.getTagName().equals(other.getTagName()))
            && (this.getDsOrder() == null ? other.getDsOrder() == null : this.getDsOrder().equals(other.getDsOrder()))
            && (this.getTagLevel() == null ? other.getTagLevel() == null : this.getTagLevel().equals(other.getTagLevel()))
            && (this.getParentTagId() == null ? other.getParentTagId() == null : this.getParentTagId().equals(other.getParentTagId()))
            && (this.getEditableFlag() == null ? other.getEditableFlag() == null : this.getEditableFlag().equals(other.getEditableFlag()))
            && (this.getRegistHhmmss() == null ? other.getRegistHhmmss() == null : this.getRegistHhmmss().equals(other.getRegistHhmmss()))
            && (this.getUpHhmmss() == null ? other.getUpHhmmss() == null : this.getUpHhmmss().equals(other.getUpHhmmss()))
            && (this.getUpdatedByUniqueId() == null ? other.getUpdatedByUniqueId() == null : this.getUpdatedByUniqueId().equals(other.getUpdatedByUniqueId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTagId() == null) ? 0 : getTagId().hashCode());
        result = prime * result + ((getTagName() == null) ? 0 : getTagName().hashCode());
        result = prime * result + ((getDsOrder() == null) ? 0 : getDsOrder().hashCode());
        result = prime * result + ((getTagLevel() == null) ? 0 : getTagLevel().hashCode());
        result = prime * result + ((getParentTagId() == null) ? 0 : getParentTagId().hashCode());
        result = prime * result + ((getEditableFlag() == null) ? 0 : getEditableFlag().hashCode());
        result = prime * result + ((getRegistHhmmss() == null) ? 0 : getRegistHhmmss().hashCode());
        result = prime * result + ((getUpHhmmss() == null) ? 0 : getUpHhmmss().hashCode());
        result = prime * result + ((getUpdatedByUniqueId() == null) ? 0 : getUpdatedByUniqueId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "MsPoiTag [tagId=" + tagId + ",tagName=" + tagName + ",dsOrder=" + dsOrder + ",tagLevel=" + tagLevel + ",parentTagId=" + parentTagId + ",editableFlag=" + editableFlag + ",registHhmmss=" + registHhmmss + ",upHhmmss=" + upHhmmss + ",updatedByUniqueId=" + updatedByUniqueId + "]";
    }
}