package ms.luna.biz.sc.impl;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.dao.custom.LunaGoodsDAO;
import ms.luna.biz.dao.model.LunaGoodsCriteria;
import ms.luna.biz.sc.LunaGoodsService;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created: by greek on 16/8/29.
 */
@Service("lunaGoodsService")
public class LunaGoodsServiceImpl implements LunaGoodsService {

    @Autowired
    private LunaGoodsDAO lunaGoodsDAO;

    @Override
    public JSONObject loadGoods(JSONObject param) {
        return null;
    }

    @Override
    public JSONObject createGoods(JSONObject param) {
        return null;
    }

    @Override
    public JSONObject updateGoods(JSONObject param) {
        return null;
    }

    @Override
    public JSONObject deleteGoods(Integer id) {
        try {
            lunaGoodsDAO.deleteByPrimaryKey(id);
            return FastJsonUtil.sucess("success");
        } catch (Exception e) {
            MsLogger.error("Failed to delete goods by id. " + e.getMessage());
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to delete goods by id.");
        }
    }

    @Override
    public JSONObject getGoodsInfo(Integer id) {
        return null;
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
}
