package ms.luna.biz.sc.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.dao.custom.LunaGoodsCategoryDAO;
import ms.luna.biz.dao.custom.LunaGoodsDAO;
import ms.luna.biz.dao.custom.model.LunaGoodsCategoryNode;
import ms.luna.biz.dao.custom.model.LunaGoodsCategoryParameter;
import ms.luna.biz.dao.custom.model.LunaGoodsParameter;
import ms.luna.biz.dao.custom.model.LunaGoodsResult;
import ms.luna.biz.dao.model.LunaGoods;
import ms.luna.biz.dao.model.LunaGoodsCriteria;
import ms.luna.biz.sc.LunaGoodsService;
import ms.luna.biz.table.LunaGoodsTable;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

/**
 * Created: by greek on 16/8/29.
 */
@Service("lunaGoodsService")
public class LunaGoodsServiceImpl implements LunaGoodsService {

    @Autowired
    private LunaGoodsDAO lunaGoodsDAO;

    @Autowired
    private LunaGoodsCategoryDAO lunaGoodsCategoryDAO;

    @Override
    public JSONObject loadGoods(JSONObject param) {
        // id, merchant_id, business_id, pic, name, price, stock, sales, create_time, online_status
        try {
            LunaGoodsParameter lunaGoodsParameter = new LunaGoodsParameter();
            String keyword = "%" + param.getString(LunaGoodsTable.KEYWORD) + "%";
            lunaGoodsParameter.setMin(param.getInteger(LunaGoodsTable.OFFSET));
            lunaGoodsParameter.setMax(param.getInteger(LunaGoodsTable.LIMIT));
            lunaGoodsParameter.setKeyword(keyword);
            lunaGoodsParameter.setRange(true);

            Integer count = lunaGoodsDAO.countGoodsWithFilter(lunaGoodsParameter);
            JSONArray rows = new JSONArray();
            if(count > 0) {
                List<LunaGoodsResult> list = lunaGoodsDAO.selectGoodsWithFilter(lunaGoodsParameter);
                if(list != null && list.size() != 0) {
                    for(LunaGoodsResult lunaGoodsResult : list) {
                        JSONObject row = (JSONObject)JSONObject.toJSON(lunaGoodsResult);
                        JSONArray pics = FastJsonUtil.parse2Array(lunaGoodsResult.getPic().split(","));
                        row.put(LunaGoodsTable.FIELD_PIC, pics);
                        rows.add(row);
                    }
                }
            }
            JSONObject result = new JSONObject();
            result.put("count", count);
            result.put("rows", rows);
            result.put("code", "0");
            result.put("msg", "success");
            return result;
        } catch (Exception e) {
            MsLogger.error("Failed to load goods. " + e.getMessage());
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to load goods.");
        }
    }

    @Override
    public JSONObject createGoods(JSONObject param) {
        try{
            // id, account, update_time, create_time, sales, online_status
            LunaGoods lunaGoods = JSONObject.toJavaObject(param, LunaGoods.class);
            lunaGoods.setCreateTime(new Date());
            lunaGoodsDAO.insertSelective(lunaGoods);
            return FastJsonUtil.sucess("success");
        } catch (Exception e) {
            MsLogger.error("Failed to create goods. " + e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to create goods.");
        }
    }

    @Override
    public JSONObject updateGoods(JSONObject param, Integer id) {
        // id, account, update_time, create_time, sales, online_status
        try {
            LunaGoods lunaGoods = JSONObject.toJavaObject(param, LunaGoods.class);
            lunaGoods.setId(id);
            lunaGoodsDAO.updateByPrimaryKeySelective(lunaGoods);
            return FastJsonUtil.sucess("success");
        } catch (Exception e) {
            MsLogger.error("Failed to update goods. " + e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to update goods. id = " + id);
        }
    }

    @Override
    public JSONObject deleteGoods(Integer id) {
        try {
            lunaGoodsDAO.deleteByPrimaryKey(id);
            return FastJsonUtil.sucess("success");
        } catch (Exception e) {
            MsLogger.error("Failed to delete goods by id. " + e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to delete goods by id. id = " + id);
        }
    }

    @Override
    public JSONObject getGoodsInfo(Integer id) {
        //
        try {
            LunaGoods lunaGoods = lunaGoodsDAO.selectByPrimaryKey(id);
            if(lunaGoods == null) {
                return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "goods id doesn't exist. id = " + id);
            }
            JSONObject data = (JSONObject)JSONObject.toJSON(lunaGoods);
            String pic = lunaGoods.getPic();
            JSONArray picArray = FastJsonUtil.parse2Array(pic.split(","));
            data.put(LunaGoodsTable.FIELD_PIC, picArray);
            return FastJsonUtil.sucess("success", data);
        } catch (Exception e) {
            MsLogger.error("Failed to get goods info by id. " + e.getMessage());
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to get goods info by id.");
        }
    }

    @Override
    public JSONObject checkGoodsName(String name, Integer id, Integer business_id) {
        LunaGoodsCriteria lunaGoodsCriteria = new LunaGoodsCriteria();
        LunaGoodsCriteria.Criteria criteria = lunaGoodsCriteria.createCriteria();
        if (id == null) { // 创建
            criteria.andNameEqualTo(name).andBusinessIdEqualTo(business_id);
        } else {// 编辑
            criteria.andNameEqualTo(name).andBusinessIdEqualTo(business_id).andIdNotEqualTo(id);
        }
        Integer count = lunaGoodsDAO.countByCriteria(lunaGoodsCriteria);
        if(count > 0) {
            return FastJsonUtil.error(ErrorCode.ALREADY_EXIST, "goods name has already existed.");
        }
        return FastJsonUtil.sucess("goods name has not already existed.");
    }

    @Override
    public JSONObject getGoodsCategories(String keyword) {
        try{

            LunaGoodsCategoryParameter parameter = new LunaGoodsCategoryParameter();
            parameter.setKeyword("%" + keyword +"%");
            List<LunaGoodsCategoryNode> categories = lunaGoodsCategoryDAO.selectLunaGoodsCategoryNodes(parameter);// 根据keyword获取直接相关的结点
            if (categories == null || categories.isEmpty()) {
                // todo
                return FastJsonUtil.sucess("success", new JSONArray());
            }
            
            // 1.取结点信息,构建商品类目树
            List<LunaGoodsCategoryNode> nodes = lunaGoodsCategoryDAO.selectLunaGoodsCategoryNodes(new LunaGoodsCategoryParameter()); // 按depth排序
            Map<Integer, LunaGoodsCategoryNode> nodesMap = new LinkedHashMap<>();// map -- 用于快速寻找某个id对应的结点
            for(LunaGoodsCategoryNode node : nodes){
                nodesMap.put(node.getId(), node);
            }
            LunaGoodsCategoryNode root = new LunaGoodsCategoryNode();// 根结点
            for(LunaGoodsCategoryNode node : nodes){
                if(node.getId() == node.getParent()) {// 第一层. 第一层node 的parent还是本身(数据库决定),额外添加一个root指向第一层
                    root.getChilds().add(node);
                } else { // 第i层
                    Integer parent = node.getParent();
                    nodesMap.get(parent).getChilds().add(node);
                }
            }

            // 2.过滤结点.取所选结点的子树和上级,上上级,...
            for(LunaGoodsCategoryNode category : categories) {
                clearBrotherNodes(root, category, nodesMap);
            }

            // 3.获取结点具体信息(当前树仅保留与keyword有关的结点)
            JSONObject data = convertNode2Json(root);

            JSONArray childList = data.getJSONArray(LunaGoodsTable.CHILDLIST);
            return FastJsonUtil.sucess("success", childList);
        } catch (Exception e) {
            MsLogger.error("Failed to get goods categories. " + e.getMessage());
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to get goods categories.");
        }

    }

    @Override
    public JSONObject updateOnlineStatus(JSONObject param) {
        try {
            String ids = param.getString("ids");
            Byte status = param.getByte(LunaGoodsTable.FIELD_ONLINE_STATUS);
            String[] idStr = ids.split(",");
            List<Integer> array = new ArrayList<>();
            for(String id : idStr) {
                array.add(Integer.parseInt(id));
            }
            LunaGoodsCriteria lunaGoodsCriteria = new LunaGoodsCriteria();
            LunaGoodsCriteria.Criteria criteria = lunaGoodsCriteria.createCriteria();
            criteria.andIdIn(array);
            LunaGoods lunaGoods = new LunaGoods();
            lunaGoods.setOnlineStatus(status);
            lunaGoodsDAO.updateByCriteriaSelective(lunaGoods, lunaGoodsCriteria);
            return FastJsonUtil.sucess("success");
        } catch (Exception e) {
            MsLogger.error("Failed to update online status. " + e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to update online status.");
        }
    }

    /**
     * 从父节点中清除兄弟结点信息(如果兄弟结点不属于keyword搜索范围)
     *
     * @param root 顶层结点
     * @param category 当前结点
     * @param nodesMap 结点映射表 id -- node
     */
    private void clearBrotherNodes(LunaGoodsCategoryNode root, LunaGoodsCategoryNode category, Map<Integer, LunaGoodsCategoryNode> nodesMap) {
        if(category.getId() == 0)// 当前结点为root
            return;
        LunaGoodsCategoryNode node = nodesMap.get(category.getId());//由category找到类目树中对应结点
        node.setIsSelected(true);//

        // 1.判断当前结点的上级,上上级...是否有被keyword直接选中的结点.
        boolean flag = isInSelectedChildTree(node, nodesMap);
        if(flag) return;

        // 2.迭代,仅保留本结点的子树及上级,上上级...信息
        LunaGoodsCategoryNode parent;// 父节点
        if(category.getId() == category.getParent()) {// 第一层节点.
            parent = root;
        } else {
            parent = nodesMap.get(category.getParent());
        }

        // 若兄弟结点已经做过操作,则不需要继续迭代向上一层.由于本结点在父节点中的信息已经被清除,需要重新添加. 目的:清除所有与keyword无关的结点
        if (parent.ischildCleared()) {
            if(!parent.getChilds().contains(node)) {
                parent.getChilds().add(node);
            }
        } else {
            parent.getChilds().clear();
            parent.setIschildCleared(true);
            parent.getChilds().add(node);
            clearBrotherNodes(root, parent, nodesMap);
        }
    }

    /**
     * 判断当前结点的上级,上上级...是否有被keyword直接选中的结点,如果有,则表明当前结点处于另一个选中结点的子树下.
     *
     * @param node 当前结点
     * @param nodesMap 结点映射表 id -- node
     * @return
     */
    private boolean isInSelectedChildTree(LunaGoodsCategoryNode node, Map<Integer, LunaGoodsCategoryNode> nodesMap) {
        // root结点或第一层结点,不处于任何选中点的子树下
        if(node.getId() == 0 || node.getId() == node.getParent()) {
            return false;
        }
        // 父节点为被keyword直接选中的结点
        LunaGoodsCategoryNode parent = nodesMap.get(node.getParent());
        if(parent.isSelected()) {
            return true;
        }
        return isInSelectedChildTree(parent, nodesMap);
    }

    /**
     * 将某个结点和其子树转换为json结构数据
     *
     * @param node 树结点
     * @return
     */
    private JSONObject convertNode2Json(LunaGoodsCategoryNode node) {
        JSONObject result = new JSONObject();
        JSONArray childList = new JSONArray();
        List<LunaGoodsCategoryNode> childs = node.getChilds();
        for(LunaGoodsCategoryNode child : childs) {
            JSONObject category = convertNode2Json(child);
            childList.add(category);
        }
        result.put(LunaGoodsTable.FIELD_ID, node.getId());
        result.put(LunaGoodsTable.FIELD_NAME, node.getName());
        result.put(LunaGoodsTable.CHILDLIST, childList);
        return result;
    }

}
