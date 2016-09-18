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

    private boolean isSelected = false; // 是否为被keyword直接选择的结点.若是,则其子树都应该被纳入到结果中.

    private boolean ischildCleared = false;// 标识子节点是否被清空过<==>该节点是否被子结点访问过,第一个来访问的子结点会清空其所有兄弟结点,即清空当前结点的子结点

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

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
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