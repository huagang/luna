package ms.luna.biz.dao.custom.model;

import java.util.List;

public class MsShowAppParameter extends BasicModel {

    private List<String> categoryIds;

    private List<Integer> types;

    public Integer status;

    public List<String> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<String> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public List<Integer> getTypes() {
        return types;
    }

    public void setTypes(List<Integer> types) {
        this.types = types;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
