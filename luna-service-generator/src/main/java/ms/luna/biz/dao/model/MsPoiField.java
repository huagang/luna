package ms.luna.biz.dao.model;

import java.io.Serializable;
import java.util.Date;

public class MsPoiField implements Serializable {
    private String fieldName;

    private String fieldShowName;

    private Integer displayOrder;

    private Integer fieldType;

    private Integer fieldSize;

    private String placeholder;

    private String fieldTipsForTemplete;

    private String extensionAttrs;

    private String fieldIntroduction;

    private Date registHhmmss;

    private Date upHhmmss;

    private String updatedByUniqueId;

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

    public Integer getFieldType() {
        return fieldType;
    }

    public void setFieldType(Integer fieldType) {
        this.fieldType = fieldType;
    }

    public Integer getFieldSize() {
        return fieldSize;
    }

    public void setFieldSize(Integer fieldSize) {
        this.fieldSize = fieldSize;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder == null ? null : placeholder.trim();
    }

    public String getFieldTipsForTemplete() {
        return fieldTipsForTemplete;
    }

    public void setFieldTipsForTemplete(String fieldTipsForTemplete) {
        this.fieldTipsForTemplete = fieldTipsForTemplete == null ? null : fieldTipsForTemplete.trim();
    }

    public String getExtensionAttrs() {
        return extensionAttrs;
    }

    public void setExtensionAttrs(String extensionAttrs) {
        this.extensionAttrs = extensionAttrs == null ? null : extensionAttrs.trim();
    }

    public String getFieldIntroduction() {
        return fieldIntroduction;
    }

    public void setFieldIntroduction(String fieldIntroduction) {
        this.fieldIntroduction = fieldIntroduction == null ? null : fieldIntroduction.trim();
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
        MsPoiField other = (MsPoiField) that;
        return (this.getFieldName() == null ? other.getFieldName() == null : this.getFieldName().equals(other.getFieldName()))
            && (this.getFieldShowName() == null ? other.getFieldShowName() == null : this.getFieldShowName().equals(other.getFieldShowName()))
            && (this.getDisplayOrder() == null ? other.getDisplayOrder() == null : this.getDisplayOrder().equals(other.getDisplayOrder()))
            && (this.getFieldType() == null ? other.getFieldType() == null : this.getFieldType().equals(other.getFieldType()))
            && (this.getFieldSize() == null ? other.getFieldSize() == null : this.getFieldSize().equals(other.getFieldSize()))
            && (this.getPlaceholder() == null ? other.getPlaceholder() == null : this.getPlaceholder().equals(other.getPlaceholder()))
            && (this.getFieldTipsForTemplete() == null ? other.getFieldTipsForTemplete() == null : this.getFieldTipsForTemplete().equals(other.getFieldTipsForTemplete()))
            && (this.getExtensionAttrs() == null ? other.getExtensionAttrs() == null : this.getExtensionAttrs().equals(other.getExtensionAttrs()))
            && (this.getFieldIntroduction() == null ? other.getFieldIntroduction() == null : this.getFieldIntroduction().equals(other.getFieldIntroduction()))
            && (this.getRegistHhmmss() == null ? other.getRegistHhmmss() == null : this.getRegistHhmmss().equals(other.getRegistHhmmss()))
            && (this.getUpHhmmss() == null ? other.getUpHhmmss() == null : this.getUpHhmmss().equals(other.getUpHhmmss()))
            && (this.getUpdatedByUniqueId() == null ? other.getUpdatedByUniqueId() == null : this.getUpdatedByUniqueId().equals(other.getUpdatedByUniqueId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getFieldName() == null) ? 0 : getFieldName().hashCode());
        result = prime * result + ((getFieldShowName() == null) ? 0 : getFieldShowName().hashCode());
        result = prime * result + ((getDisplayOrder() == null) ? 0 : getDisplayOrder().hashCode());
        result = prime * result + ((getFieldType() == null) ? 0 : getFieldType().hashCode());
        result = prime * result + ((getFieldSize() == null) ? 0 : getFieldSize().hashCode());
        result = prime * result + ((getPlaceholder() == null) ? 0 : getPlaceholder().hashCode());
        result = prime * result + ((getFieldTipsForTemplete() == null) ? 0 : getFieldTipsForTemplete().hashCode());
        result = prime * result + ((getExtensionAttrs() == null) ? 0 : getExtensionAttrs().hashCode());
        result = prime * result + ((getFieldIntroduction() == null) ? 0 : getFieldIntroduction().hashCode());
        result = prime * result + ((getRegistHhmmss() == null) ? 0 : getRegistHhmmss().hashCode());
        result = prime * result + ((getUpHhmmss() == null) ? 0 : getUpHhmmss().hashCode());
        result = prime * result + ((getUpdatedByUniqueId() == null) ? 0 : getUpdatedByUniqueId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "MsPoiField [fieldName=" + fieldName + ",fieldShowName=" + fieldShowName + ",displayOrder=" + displayOrder + ",fieldType=" + fieldType + ",fieldSize=" + fieldSize + ",placeholder=" + placeholder + ",fieldTipsForTemplete=" + fieldTipsForTemplete + ",extensionAttrs=" + extensionAttrs + ",fieldIntroduction=" + fieldIntroduction + ",registHhmmss=" + registHhmmss + ",upHhmmss=" + upHhmmss + ",updatedByUniqueId=" + updatedByUniqueId + "]";
    }
}