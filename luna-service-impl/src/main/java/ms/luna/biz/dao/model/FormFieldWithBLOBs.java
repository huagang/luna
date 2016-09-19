package ms.luna.biz.dao.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import ms.luna.biz.table.FormFieldTable;

import java.io.Serializable;

@JSONType
public class FormFieldWithBLOBs extends FormField implements Serializable {
    @JSONField(name = FormFieldTable.FIELD_VALIDATOR)
    private String validator;
    @JSONField(name = FormFieldTable.FIELD_FIELD_VALIDATOR)
    private String fieldValidator;
    @JSONField(name = FormFieldTable.FIELD_DATA)
    private String data;

    private static final long serialVersionUID = 1L;

    public String getValidator() {
        return validator;
    }

    public void setValidator(String validator) {
        this.validator = validator == null ? null : validator.trim();
    }

    public String getFieldValidator() {
        return fieldValidator;
    }

    public void setFieldValidator(String fieldValidator) {
        this.fieldValidator = fieldValidator == null ? null : fieldValidator.trim();
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data == null ? null : data.trim();
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
        FormFieldWithBLOBs other = (FormFieldWithBLOBs) that;
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
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getValidator() == null ? other.getValidator() == null : this.getValidator().equals(other.getValidator()))
                && (this.getFieldValidator() == null ? other.getFieldValidator() == null : this.getFieldValidator().equals(other.getFieldValidator()))
            && (this.getData() == null ? other.getData() == null : this.getData().equals(other.getData()));
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
        result = prime * result + ((getValidator() == null) ? 0 : getValidator().hashCode());
        result = prime * result + ((getFieldValidator() == null) ? 0 : getFieldValidator().hashCode());
        result = prime * result + ((getData() == null) ? 0 : getData().hashCode());
        return result;
    }
}