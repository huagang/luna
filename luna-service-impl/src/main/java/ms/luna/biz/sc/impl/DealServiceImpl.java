package ms.luna.biz.sc.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.DbConfig;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.dao.custom.*;
import ms.luna.biz.dao.custom.model.LunaGoodsCategoryNode;
import ms.luna.biz.dao.custom.model.LunaGoodsCategoryParameter;
import ms.luna.biz.dao.custom.model.RoomBasicInfo;
import ms.luna.biz.dao.model.FormFieldCriteria;
import ms.luna.biz.dao.model.FormFieldWithBLOBs;
import ms.luna.biz.dao.model.RoomDynamicInfo;
import ms.luna.biz.dao.model.RoomDynamicInfoCriteria;
import ms.luna.biz.sc.DealService;
import ms.luna.biz.serdes.deal.DealSerDes;
import ms.luna.biz.serdes.deal.DealSerDesFactory;
import ms.luna.biz.table.DealTable;
import ms.luna.biz.table.LunaGoodsTable;
import ms.luna.biz.table.MsBusinessTable;
import ms.luna.biz.table.RoomDynamicInfoTable;
import ms.luna.biz.util.DateHelper;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.cache.GoodsCategoryTableCache;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-09-14
 */
@Transactional(rollbackFor = Exception.class)
@Service("dealService")
public class DealServiceImpl implements DealService {

    private final static Logger logger = Logger.getLogger(DealServiceImpl.class);

    @Autowired
    private DealDAO dealDAO;
    @Autowired
    private GoodsCategoryTableCache goodsCategoryTableCache;
    @Autowired
    private FormFieldDAO formFieldDAO;

    @Autowired
    private RoomDynamicInfoDAO roomDynamicInfoDAO;
    @Autowired
    private LunaGoodsCategoryDAO lunaGoodsCategoryDAO;
    @Autowired
    private MsBusinessDAO msBusinessDAO;

    @Override
    public JSONObject getDealFieldsByCateId(int cateId) {

        try {
            String categoryTable = goodsCategoryTableCache.getCategoryTableByCategoryId(cateId);
            if (categoryTable != null) {
                FormFieldCriteria formFieldCriteria = new FormFieldCriteria();
                formFieldCriteria.createCriteria().andTableNameEqualTo(categoryTable);
                List<FormFieldWithBLOBs> formFieldWithBLOBses = formFieldDAO.selectByCriteriaWithBLOBs(formFieldCriteria);
                return FastJsonUtil.sucess("", JSON.toJSON(formFieldWithBLOBses));
            } else {
                logger.warn("no fields for cateId : " + cateId);
                return FastJsonUtil.sucess("", new JSONArray());
            }
        } catch (Exception ex) {
            logger.error("Failed to get deal fields by cateId :" + cateId);

            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取信息失败");
        }
    }

    @Override
    public JSONObject loadDeals(JSONObject query) {

        // load fields according to different categoryId
        Integer cateId = query.getInteger(DealTable.FIELD_CATEGORY_ID);

        List<Document> documents = dealDAO.loadDeal(query);

        List<JSONObject> dealList = new ArrayList<>(documents.size());
        for(Document document : documents) {
            dealList.add(JSON.parseObject(document.toJson()));
        }

        return FastJsonUtil.sucess("", dealList);
    }

    @Override
    public JSONObject getDealById(String dealId) {
        Document deal = dealDAO.getDeal(dealId);
        int cateId = deal.getInteger(DealTable.FIELD_CATEGORY_ID);
        try {
            JSONObject jsonObject = JSON.parseObject(deal.toJson());
            return FastJsonUtil.sucess("success", jsonObject);
        } catch (Exception e) {
            logger.error("Failed to get deal for id: " + dealId);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取商品失败");
        }
    }

    @Override
    public JSONObject createDeal(JSONObject dealInfo) {

        ObjectId objectId = ObjectId.get();
        int cateId = dealInfo.getInteger(DealTable.FIELD_CATEGORY_ID);
        try {
            int businessId = dealInfo.getInteger(MsBusinessTable.FIELD_BUSINESS_ID);
            String merchantId = msBusinessDAO.readMerchantId(businessId);
            if(StringUtils.isBlank(merchantId)) {
                logger.warn("no merchant info for business: " + businessId);
                return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "商户不存在");
            }

            // 先操作支持事务可以回滚的数据(mysql)
            dealInfo.put(DealTable.FIELD_ID, objectId.toString());
            createOtherInfo(cateId, dealInfo);

            DealSerDes dealSerDes = DealSerDesFactory.getDealSerDes(cateId);
            Document document = dealSerDes.toDocument(dealInfo);
            document.append(DealTable.FIELD_ID, objectId.toString());
            document.append(DealTable.FIELD_MERCHANT_ID, merchantId);
            dealDAO.insertDeal(document);

            return FastJsonUtil.sucess("success");
        } catch (Exception ex) {
            logger.error("Failed to create deal", ex);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "创建商品失败");
        }

    }

    private void createOtherInfo(int cateId, JSONObject jsonObject) {

        if(cateId == DbConfig.GOODS_CATEGORY_ROOM) {
            createRoomOtherInfo(cateId, jsonObject);
        }
    }

    private void updateOtherInfo(int cateId, JSONObject jsonObject) {
        if(cateId == DbConfig.GOODS_CATEGORY_ROOM) {
            updateRoomOtherInfo(cateId, jsonObject);
        }
    }

    private void updateRoomOtherInfo(int cateId, JSONObject jsonObject) {

        RoomBasicInfo roomBasicInfo = JSON.toJavaObject(jsonObject, RoomBasicInfo.class);
        Date startTime = roomBasicInfo.getStartTime();
        Date endTime = roomBasicInfo.getEndTime();
        if(startTime.getTime() < endTime.getTime()) {
            return;
        }
        int startDay = DateHelper.toDay(startTime.getTime());
        int endDay = DateHelper.toDay(endTime.getTime());
        int roomId = roomBasicInfo.getId();
        Integer count = roomBasicInfo.getCount();
        double workdayPrice = roomBasicInfo.getWorkdayPrice();
        double weekendPrice = roomBasicInfo.getWeekendPrice();
        List<Integer> workDays = roomBasicInfo.getWorkDays();
        List<Integer> weekendDays = roomBasicInfo.getWeekendDays();
        List<Integer> days = DateHelper.daysBetween(startDay, endDay);

        RoomDynamicInfoCriteria roomDynamicInfoCriteria = new RoomDynamicInfoCriteria();
        roomDynamicInfoCriteria.createCriteria().andRoomIdEqualTo(roomId);
        roomDynamicInfoCriteria.setOrderByClause(RoomDynamicInfoTable.FIELD_DAY + " asc");
        List<RoomDynamicInfo> roomDynamicInfos = roomDynamicInfoDAO.selectByCriteria(roomDynamicInfoCriteria);
//        List<RoomDynamicInfo> updateRoomDynamicInfos = new ArrayList<>();
        Set<Integer> existDays = new HashSet<>();
        if(roomDynamicInfos != null) {
            for(RoomDynamicInfo roomDynamicInfo : roomDynamicInfos) {
                RoomDynamicInfo updateRoomDynamicInfo = new RoomDynamicInfo();
                updateRoomDynamicInfo.setRoomId(roomDynamicInfo.getRoomId());
                updateRoomDynamicInfo.setDay(roomDynamicInfo.getDay());
                existDays.add(DateHelper.toDay(roomDynamicInfo.getDay()));
                if(workDays.contains(DateHelper.dayOfWeek(DateHelper.toDay(roomDynamicInfo.getDay())))) {
                    updateRoomDynamicInfo.setPrice(workdayPrice);
                } else {
                    updateRoomDynamicInfo.setPrice(weekendPrice);
                }
                updateRoomDynamicInfo.setCount(count);
                // TODO: update in batch
                roomDynamicInfoDAO.updateByPrimaryKeySelective(updateRoomDynamicInfo);
//                updateRoomDynamicInfos.add(updateRoomDynamicInfo);
            }
        }

        List<RoomDynamicInfo> roomDynamicInfoList = new ArrayList<>(days.size());
        Date crtDate = new Date();
        for(int day : days) {
            if(existDays.contains(day)) {
                continue;
            }
            RoomDynamicInfo roomDynamicInfo = new RoomDynamicInfo();
            roomDynamicInfo.setRoomId(roomId);
            roomDynamicInfo.setDay(DateHelper.date(day));
            roomDynamicInfo.setCount(count);
            if(workDays.contains(DateHelper.dayOfWeek(day))) {
                roomDynamicInfo.setPrice(workdayPrice);
            } else {
                roomDynamicInfo.setPrice(weekendPrice);
            }
            roomDynamicInfo.setCreateTime(crtDate);
            roomDynamicInfoList.add(roomDynamicInfo);
        }
        roomDynamicInfoDAO.insertBatchRoomDynamicInfo(roomDynamicInfoList);

    }

    private void createRoomOtherInfo(int cateId, JSONObject jsonObject) {

        RoomBasicInfo roomBasicInfo = JSON.toJavaObject(jsonObject, RoomBasicInfo.class);
        Date startTime = roomBasicInfo.getStartTime();
        Date endTime = roomBasicInfo.getEndTime();
        if(startTime.getTime() < endTime.getTime()) {
            return;
        }
        int startDay = DateHelper.toDay(startTime.getTime());
        int endDay = DateHelper.toDay(endTime.getTime());
        int roomId = roomBasicInfo.getId();
        Integer count = roomBasicInfo.getCount();
        double workdayPrice = roomBasicInfo.getWorkdayPrice();
        double weekendPrice = roomBasicInfo.getWeekendPrice();
        List<Integer> workDays = roomBasicInfo.getWorkDays();
        List<Integer> weekendDays = roomBasicInfo.getWeekendDays();
        List<Integer> days = DateHelper.daysBetween(startDay, endDay);

        List<RoomDynamicInfo> roomDynamicInfoList = new ArrayList<>(days.size());
        Date crtDate = new Date();
        for(int day : days) {
            RoomDynamicInfo roomDynamicInfo = new RoomDynamicInfo();
            roomDynamicInfo.setRoomId(roomId);
            roomDynamicInfo.setDay(DateHelper.date(day));
            roomDynamicInfo.setCount(count);
            if(workDays.contains(DateHelper.dayOfWeek(day))) {
                roomDynamicInfo.setPrice(workdayPrice);
            } else {
                roomDynamicInfo.setPrice(weekendPrice);
            }
            roomDynamicInfo.setCreateTime(crtDate);
            roomDynamicInfoList.add(roomDynamicInfo);
        }
        roomDynamicInfoDAO.insertBatchRoomDynamicInfo(roomDynamicInfoList);
    }

    @Override
    public JSONObject updateDeal(JSONObject dealInfo) {

        int cateId = dealInfo.getInteger(DealTable.FIELD_CATEGORY_ID);
        try {
            // 先更新可以回滚的数据(mysql中的数据)
            updateOtherInfo(cateId, dealInfo);
            DealSerDes dealSerDes = DealSerDesFactory.getDealSerDes(cateId);
            Document document = dealSerDes.toDocument(dealInfo);
            dealDAO.updateDeal(dealInfo.getString(DealTable.FIELD_ID), document);
            return FastJsonUtil.sucess("");
        } catch (Exception ex) {
            logger.error("Failed to update deal", ex);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "更新商品失败");
        }
    }

    @Override
    public JSONObject deleteDeal(String dealIds) {
        return null;
    }

    @Override
    public JSONObject checkDealName(String name, String id, Integer businessId) {
        return null;
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
