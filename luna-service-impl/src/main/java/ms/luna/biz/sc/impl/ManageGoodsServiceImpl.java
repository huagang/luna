package ms.luna.biz.sc.impl;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.dao.custom.LunaGoodsDAO;
import ms.luna.biz.dao.model.LunaGoods;
import ms.luna.biz.sc.ManageGoodsService;
import ms.luna.biz.sc.ManagePoiService;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.schedule.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created: by greek on 16/8/29.
 */
@Service("manageGoodsService")
public class ManageGoodsServiceImpl implements ManageGoodsService {

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
    public JSONObject checkGoodsName(String name, Integer id) {
        return null;
    }
}
