package ms.luna.biz.sc.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.dao.custom.LunaGoodsDAO;
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

import java.util.List;

/**
 * Created: by greek on 16/8/29.
 */
@Service("lunaGoodsService")
public class LunaGoodsServiceImpl implements LunaGoodsService {

    @Autowired
    private LunaGoodsDAO lunaGoodsDAO;

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
                        JSONArray pics = JSONArray.parseArray(lunaGoodsResult.getPic());
                        row.put("pic", pics);
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
            lunaGoodsDAO.insert(lunaGoods);
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
            lunaGoodsDAO.updateByPrimaryKey(lunaGoods);
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
            // 整体时间复杂度 o(n)
            // 取结点信息,建树


            // 过滤结点.取所选结点的子树和上级,上上级,...

            // 获取结点具体信息


        } catch (Exception e) {
            MsLogger.error("Failed to get goods categories. " + e.getMessage());
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to get goods categories.");
        }
    }


}
