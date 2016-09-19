package ms.luna.biz.dao.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import ms.luna.biz.table.FormFieldTable;

import java.io.Serializable;
import java.util.Date;

@JSONType
public class FormField implements Serializable {

    private Integer id;
    @JSONField(name= FormFieldTable.FIELD_TABLE_NAME)
    private String tableName;
    @JSONField(name = FormFieldTable.FIELD_LABEL_NAME)
    private String lableName;
    @JSONField(name = FormFieldTable.FIELD_FIELD_NAME)
    private String fieldName;
    @JSONField(name = FormFieldTable.FIELD_DISPLAY_ORDER)
    private Integer displayOrder;
    @JSONField(name = FormFieldTable.FIELD_TYPE)
    private String type;
    @JSONField(name = FormFieldTable.FIELD_DESCRIPTION)
    private String description;
    @JSONField(name = FormFieldTable.FIELD_DEFAULT_VALUE)
    private String defaultValue;
    @JSONField(name = FormFieldTable.FIELD_PLACE_HOLDER)
    private String placeholder;
    @JSONField(name = FormFieldTable.FIELD_EDITABLE)
    private Byte editable;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    public String getLableName() {
        return lableName;
    }

    public void setLableName(String lableName) {
        this.lableName = lableName == null ? null : lableName.trim();
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName == null ? null : fieldName.trim();
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue == null ? null : defaultValue.trim();
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder == null ? null : placeholder.trim();
    }

    public Byte getEditable() {
        return editable;
    }

    public void setEditable(Byte editable) {
        this.editable = editable;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
        FormField other = (FormField) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTableName() == null ? other.getTableName() == null : this.getTableName().equals(other.getTableName()))
            && (this.getLableName() == null ? other.getLableName() == null : this.getLableName().equals(other.getLableName()))
            && (this.getFieldName() == null ? other.getFieldName() == null : this.getFieldName().equals(other.getFieldName()))
            && (this.getDisplayOrder() == null ? other.getDisplayOrder() == null : this.getDisplayOrder().equals(other.getDisplayOrder()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getDefaultValue() == null ? other.getDefaultValue() == null : this.getDefaultValue().equals(other.getDefaultValue()))
            && (this.getPlaceholder() == null ? other.getPlaceholder() == null : this.getPlaceholder().equals(other.getPlaceholder()))
            && (this.getEditable() == null ? other.getEditable() == null : this.getEditable().equals(other.getEditable()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTableName() == null) ? 0 : getTableName().hashCode());
        result = prime * result + ((getLableName() == null) ? 0 : getLableName().hashCode());
        result = prime * result + ((getFieldName() == null) ? 0 : getFieldName().hashCode());
        result = prime * result + ((getDisplayOrder() == null) ? 0 : getDisplayOrder().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getDefaultValue() == null) ? 0 : getDefaultValue().hashCode());
        result = prime * result + ((getPlaceholder() == null) ? 0 : getPlaceholder().hashCode());
        result = prime * result + ((getEditable() == null) ? 0 : getEditable().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "FormField [id=" + id + ",tableName=" + tableName + ",lableName=" + lableName + ",fieldName=" + fieldName + ",displayOrder=" + displayOrder + ",type=" + type + ",description=" + description + ",defaultValue=" + defaultValue + ",placeholder=" + placeholder + ",editable=" + editable + ",createTime=" + createTime + ",updateTime=" + updateTime + "]";
    }
}