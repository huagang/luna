package ms.luna.biz.dao.custom.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created: by greek on 16/8/30.
 */
public class LunaGoodsCategoryNode {
    private Integer id;

    private Integer parent;

    private boolean ischildCleared = false;

    private List<LunaGoodsCategoryNode> childs = new ArrayList<>();

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public boolean ischildCleared() {
        return ischildCleared;
    }

    public void setIschildCleared(boolean ischildCleared) {
        this.ischildCleared = ischildCleared;
    }

    public List<LunaGoodsCategoryNode> getChilds() {
        return childs;
    }

    public void setChilds(List<LunaGoodsCategoryNode> childs) {
        this.childs = childs;
    }

}