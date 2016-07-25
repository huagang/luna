package ms.luna.biz.dao.model;

import java.io.Serializable;
import java.util.Date;

public class MsFarmField implements Serializable {
    private String fieldName;

    private String fieldShowName;

    private Integer displayOrder;

    private String fieldType;

    private String fieldLimit;

    private String placeholder;

    private String extensionAttrs;

    private Date registHhmmss;

    private Date upHhmmss;

    private static final long serialVersionUID = 1L;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName == null ? null : fieldName.trim();
    }

    public String getFieldShowName() {
        return fieldShowName;
    }

    public void setFieldShowName(String fieldShowName) {
        this.fieldShowName = fieldShowName == null ? null : fieldShowName.trim();
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType == null ? null : fieldType.trim();
    }

    public String getFieldLimit() {
        return fieldLimit;
    }

    public void setFieldLimit(String fieldLimit) {
        this.fieldLimit = fieldLimit == null ? null : fieldLimit.trim();
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder == null ? null : placeholder.trim();
    }

    public String getExtensionAttrs() {
        return extensionAttrs;
    }

    public void setExtensionAttrs(String extensionAttrs) {
        this.extensionAttrs = extensionAttrs == null ? null : extensionAttrs.trim();
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
        MsFarmField other = (MsFarmField) that;
        return (this.getFieldName() == null ? other.getFieldName() == null : this.getFieldName().equals(other.getFieldName()))
            && (this.getFieldShowName() == null ? other.getFieldShowName() == null : this.getFieldShowName().equals(other.getFieldShowName()))
            && (this.getDisplayOrder() == null ? other.getDisplayOrder() == null : this.getDisplayOrder().equals(other.getDisplayOrder()))
            && (this.getFieldType() == null ? other.getFieldType() == null : this.getFieldType().equals(other.getFieldType()))
            && (this.getFieldLimit() == null ? other.getFieldLimit() == null : this.getFieldLimit().equals(other.getFieldLimit()))
            && (this.getPlaceholder() == null ? other.getPlaceholder() == null : this.getPlaceholder().equals(other.getPlaceholder()))
            && (this.getExtensionAttrs() == null ? other.getExtensionAttrs() == null : this.getExtensionAttrs().equals(other.getExtensionAttrs()))
            && (this.getRegistHhmmss() == null ? other.getRegistHhmmss() == null : this.getRegistHhmmss().equals(other.getRegistHhmmss()))
            && (this.getUpHhmmss() == null ? other.getUpHhmmss() == null : this.getUpHhmmss().equals(other.getUpHhmmss()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getFieldName() == null) ? 0 : getFieldName().hashCode());
        result = prime * result + ((getFieldShowName() == null) ? 0 : getFieldShowName().hashCode());
        result = prime * result + ((getDisplayOrder() == null) ? 0 : getDisplayOrder().hashCode());
        result = prime * result + ((getFieldType() == null) ? 0 : getFieldType().hashCode());
        result = prime * result + ((getFieldLimit() == null) ? 0 : getFieldLimit().hashCode());
        result = prime * result + ((getPlaceholder() == null) ? 0 : getPlaceholder().hashCode());
        result = prime * result + ((getExtensionAttrs() == null) ? 0 : getExtensionAttrs().hashCode());
        result = prime * result + ((getRegistHhmmss() == null) ? 0 : getRegistHhmmss().hashCode());
        result = prime * result + ((getUpHhmmss() == null) ? 0 : getUpHhmmss().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "MsFarmField [fieldName=" + fieldName + ",fieldShowName=" + fieldShowName + ",displayOrder=" + displayOrder + ",fieldType=" + fieldType + ",fieldLimit=" + fieldLimit + ",placeholder=" + placeholder + ",extensionAttrs=" + extensionAttrs + ",registHhmmss=" + registHhmmss + ",upHhmmss=" + upHhmmss + "]";
    }
}