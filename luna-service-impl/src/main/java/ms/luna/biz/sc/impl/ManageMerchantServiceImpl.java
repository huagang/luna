package ms.luna.biz.sc.impl;

import ms.luna.biz.dao.custom.LunaUserMerchantDAO;
import ms.luna.biz.dao.model.LunaUserMerchant;
import ms.luna.biz.table.LunaUserTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ms.luna.biz.bl.ManageMerchantBL;
import ms.luna.biz.sc.ManageMerchantService;
import ms.luna.biz.util.FastJsonUtil;
import com.alibaba.fastjson.JSONObject;

/**
 * @author Greek
 * @version 1.0
 * @date create time：2016年3月23日 下午8:23:00
 */
@Service("manageMerchantService")
public class ManageMerchantServiceImpl implements ManageMerchantService {

    @Autowired
    private ManageMerchantBL manageMerchantBL;

    @Autowired
    private LunaUserMerchantDAO lunaUserMerchantDAO;

    @Override
    public JSONObject createMerchant(String json) {
        JSONObject result = null;
        try {
            result = manageMerchantBL.createMerchant(json);
        } catch (RuntimeException e) {

            return FastJsonUtil.error("-1", e);
        }
        return result;
    }

    @Override
    public JSONObject registMerchant(String json) {
        JSONObject result = null;
        try {
            result = manageMerchantBL.registMerchant(json);
        } catch (RuntimeException e) {

            return FastJsonUtil.error("-1", e);
        }
        return result;
    }

    @Override
    public JSONObject loadMerchantById(String json) {
        JSONObject result = null;
        try {
            result = manageMerchantBL.loadMerchantById(json);
        } catch (RuntimeException e) {

            return FastJsonUtil.error("-1", e);
        }
        return result;
    }

    @Override
    public JSONObject loadMerchantByUserId(String json) {
        JSONObject result = null;
        try {
            JSONObject object = JSONObject.parseObject(json);
            LunaUserMerchant lunaUserMerchant = lunaUserMerchantDAO.selectByPrimaryKey(object.getString(LunaUserTable.FIELD_ID));
            JSONObject d = new JSONObject();
            d.put("merchant_id", lunaUserMerchant.getMerchantId());
            result = manageMerchantBL.loadMerchantById(d.toString());
        } catch (RuntimeException e) {

            return FastJsonUtil.error("-1", e);
        }
        return result;
    }

    @Override
    public JSONObject loadMerchants(String json) {
        JSONObject result = null;
        try {
            result = manageMerchantBL.loadMerchants(json);
        } catch (RuntimeException e) {

            return FastJsonUtil.error("-1", e);
        }
        return result;
    }

    @Override
    public JSONObject updateMerchantById(String json) {
        JSONObject result = null;
        try {
            result = manageMerchantBL.updateMerchant(json);
        } catch (RuntimeException e) {

            return FastJsonUtil.error("-1", e);
        }
        return result;
    }

    @Override
    public JSONObject deleteMerchantById(String json) {
        JSONObject result = null;
        try {
            result = manageMerchantBL.deleteMerchantById(json);
        } catch (Exception e) {

            return FastJsonUtil.error("-1", e);
        }

        return result;
    }

    @Override
    public JSONObject isSalesmanNmExit(String json) {
        JSONObject result = null;
        try {
            result = manageMerchantBL.isSalesmanNmExit(json);
        } catch (RuntimeException e) {

            return FastJsonUtil.error("-1", e);
        }
        return result;
    }

    @Override
    public JSONObject isAddedMerchantNmEist(String json) {
        JSONObject result = null;
        try {
            result = manageMerchantBL.isAddedMerchantNmEist(json);
        } catch (RuntimeException e) {

            return FastJsonUtil.error("-1", e);
        }
        return result;
    }

    @Override
    public JSONObject isEditedMerchantNmEist(String json) {
        JSONObject result = null;
        try {
            result = manageMerchantBL.isEditedMerchantNmEist(json);
        } catch (RuntimeException e) {

            return FastJsonUtil.error("-1", e);
        }
        return result;
    }

    @Override
    public JSONObject closeMerchantById(String json) {
        JSONObject result = null;
        try {
            result = manageMerchantBL.closeMerchantById(json);
        } catch (RuntimeException e) {

            return FastJsonUtil.error("-1", e);
        }
        return result;
    }

    @Override
    public JSONObject openMerchantById(String json) {
        JSONObject result = null;
        try {
            result = manageMerchantBL.openMerchantById(json);
        } catch (RuntimeException e) {

            return FastJsonUtil.error("-1", e);
        }
        return result;
    }

    @Override
    public JSONObject getMerchantEmail(String id) {
        JSONObject result = null;
        try {
            result = manageMerchantBL.getMerchantEmail(id);
        } catch (RuntimeException e) {

            return FastJsonUtil.error("-1", e);
        }
        return result;
    }

    @Override
    public JSONObject getMerchantTradeStatus(String json) {
        JSONObject result = null;
        try {
            result = manageMerchantBL.getMerchantTradeStatus(json);
        } catch (RuntimeException e) {
            return FastJsonUtil.error("-1", e);
        }
        return result;
    }

    @Override
    public JSONObject signAgreement(JSONObject json) {
        try {
            return manageMerchantBL.signAgreement(json);
        } catch (RuntimeException e) {
            return FastJsonUtil.error("-1", e);
        }
    }
}
