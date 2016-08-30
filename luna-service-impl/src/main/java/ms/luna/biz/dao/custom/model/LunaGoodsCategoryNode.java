package ms.luna.biz.dao.custom.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created: by greek on 16/8/30.
 */
public class LunaGoodsCategoryNode {
    private int id = 0;

    private int parent = 0;

    private String name;

    private boolean ischildCleared = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    private List<LunaGoodsCategoryNode> childs = new ArrayList<>();



}