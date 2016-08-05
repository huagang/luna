package ms.luna.biz.dao.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import ms.luna.biz.table.MsFarmFieldTable;

import java.io.Serializable;
import java.util.Date;

@JSONType(ignores={"registHhmmss","upHhmmss","options","placeholder","limits"})
public class MsFarmField implements Serializable {
    @JSONField(name = MsFarmFieldTable.FIELD_NAME)
    private String name;
    @JSONField(name = MsFarmFieldTable.FIELD_SHOW_NAME)
    private String showName;
    @JSONField(name = MsFarmFieldTable.FIELD_DISPLAY_ORDER)
    private Integer displayOrder;
    @JSONField(name = MsFarmFieldTable.FIELD_TYPE)
    private String type;
    @JSONField(name = MsFarmFieldTable.FIELD_LIMITS)
    private String limits;
    @JSONField(name = MsFarmFieldTable.FIELD_PLACEHOLDER)
    private String placeholder;
    @JSONField(name = MsFarmFieldTable.FIELD_OPTIONS)
    private String options;
    @JSONField(name = MsFarmFieldTable.FIELD_REGIST_HHMMSS)
    private Date registHhmmss;
    @JSONField(name = MsFarmFieldTable.FIELD_UP_HHMMSS)
    private Date upHhmmss;

    private static final long serialVersionUID = 1L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName == null ? null : showName.trim();
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

    public String getLimits() {
        return limits;
    }

    public void setLimits(String limits) {
        this.limits = limits == null ? null : limits.trim();
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder == null ? null : placeholder.trim();
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options == null ? null : options.trim();
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
        return (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getShowName() == null ? other.getShowName() == null : this.getShowName().equals(other.getShowName()))
            && (this.getDisplayOrder() == null ? other.getDisplayOrder() == null : this.getDisplayOrder().equals(other.getDisplayOrder()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getLimits() == null ? other.getLimits() == null : this.getLimits().equals(other.getLimits()))
            && (this.getPlaceholder() == null ? other.getPlaceholder() == null : this.getPlaceholder().equals(other.getPlaceholder()))
            && (this.getOptions() == null ? other.getOptions() == null : this.getOptions().equals(other.getOptions()))
            && (this.getRegistHhmmss() == null ? other.getRegistHhmmss() == null : this.getRegistHhmmss().equals(other.getRegistHhmmss()))
            && (this.getUpHhmmss() == null ? other.getUpHhmmss() == null : this.getUpHhmmss().equals(other.getUpHhmmss()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getShowName() == null) ? 0 : getShowName().hashCode());
        result = prime * result + ((getDisplayOrder() == null) ? 0 : getDisplayOrder().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getLimits() == null) ? 0 : getLimits().hashCode());
        result = prime * result + ((getPlaceholder() == null) ? 0 : getPlaceholder().hashCode());
        result = prime * result + ((getOptions() == null) ? 0 : getOptions().hashCode());
        result = prime * result + ((getRegistHhmmss() == null) ? 0 : getRegistHhmmss().hashCode());
        result = prime * result + ((getUpHhmmss() == null) ? 0 : getUpHhmmss().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "MsFarmField [name=" + name + ",showName=" + showName + ",displayOrder=" + displayOrder + ",type=" + type + ",limits=" + limits + ",placeholder=" + placeholder + ",options=" + options + ",registHhmmss=" + registHhmmss + ",upHhmmss=" + upHhmmss + "]";
    }
}