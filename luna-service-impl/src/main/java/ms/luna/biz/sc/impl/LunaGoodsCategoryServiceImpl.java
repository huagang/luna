package ms.luna.biz.sc.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.dao.custom.LunaGoodsCategoryDAO;
import ms.luna.biz.dao.model.LunaGoodsCategory;
import ms.luna.biz.dao.model.LunaGoodsCategoryCriteria;
import ms.luna.biz.sc.LunaGoodsCategoryService;
import ms.luna.biz.util.FastJsonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Calendar;
import java.util.List;

/**
 * Created by SDLL18 on 16/8/23.
 */
@Service("lunaGoodsCategoryService")
public class LunaGoodsCategoryServiceImpl implements LunaGoodsCategoryService {

    private static final Logger logger = Logger.getLogger(LunaGoodsCategoryServiceImpl.class);

    @Autowired
    private LunaGoodsCategoryDAO lunaGoodsCategoryDAO;

    private JSONArray getCategoriesByCriteria(LunaGoodsCategoryCriteria lunaGoodsCategoryCriteria) {
        List<LunaGoodsCategory> categories = lunaGoodsCategoryDAO.selectByCriteria(lunaGoodsCategoryCriteria);
        return assembleCategories(categories);
    }

    private JSONArray assembleCategories(List<LunaGoodsCategory> categories) {
        JSONArray result = new JSONArray();
        if (categories == null) {
            return result;
        }
        JSONArray nowDepth = new JSONArray();
        JSONArray nextDepth = new JSONArray();
        int depth = 0;
        for (LunaGoodsCategory category : categories) {
            if (category.getDepth().intValue() != depth) {
                if (depth != 0) {
                    JSONArray temp = nowDepth;
                    nowDepth = nextDepth;
                    nextDepth = temp;
                    nextDepth.clear();
                }
                depth++;
            }
            if (depth == 0) {
                JSONObject object = new JSONObject();
                object.put("id", category.getId());
                object.put("name", category.getName());
                object.put("child", new JSONArray());
                object.put("abbreviation", category.getAbbreviation());
                result.add(object);
                nowDepth.add(object);
            } else {
                for (int i = 0; i < nowDepth.size(); i++) {
                    JSONObject rootObject = nowDepth.getJSONObject(i);
                    if (rootObject.getInteger("id").intValue() == category.getRoot().intValue()) {
                        JSONObject object = new JSONObject();
                        object.put("id", category.getId());
                        object.put("name", category.getName());
                        object.put("child", new JSONArray());
                        object.put("abbreviation", category.getAbbreviation());
                        rootObject.getJSONArray("child").add(object);
                        nextDepth.add(object);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public JSONObject getCategories() {
        try {
            LunaGoodsCategoryCriteria lunaGoodsCategoryCriteria = new LunaGoodsCategoryCriteria();
            lunaGoodsCategoryCriteria.setOrderByClause("depth");
            JSONArray result = getCategoriesByCriteria(lunaGoodsCategoryCriteria);
            return FastJsonUtil.sucess("success", result);
        } catch (Exception ex) {
            logger.error("Failed to get lunaGoodsCategories", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
    }

    @Override
    public JSONObject getCategories(JSONObject jsonObject) {
        try {
            LunaGoodsCategoryCriteria lunaGoodsCategoryCriteria = new LunaGoodsCategoryCriteria();
            lunaGoodsCategoryCriteria.setOrderByClause("depth");
            JSONArray temp = getCategoriesByCriteria(lunaGoodsCategoryCriteria);
            Integer max = jsonObject.getInteger("limit");
            Integer min = jsonObject.getInteger("offset");
            max = max.intValue() < 1 ? 1 : max;
            min = min.intValue() < 0 ? 0 : min;
            if (max <= min) temp.clear();
            JSONArray result = new JSONArray();
            for (int i = (min < temp.size() ? min : temp.size()); i < (max > temp.size() ? temp.size() : max); i++) {
                result.add(temp.get(i));
            }
            return FastJsonUtil.sucess("success", result);
        } catch (Exception ex) {
            logger.error("Failed to get lunaGoodsCategories", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
//        LunaGoodsCategoryParameter lunaGoodsCategoryParameter = new LunaGoodsCategoryParameter();
//        lunaGoodsCategoryParameter.setMax(max);
//        lunaGoodsCategoryParameter.setMin(min);
//        lunaGoodsCategoryParameter.setRange("true");
//        List<LunaGoodsCategoryResult> categories = lunaGoodsCategoryDAO.selectLunaGoodsCategory(lunaGoodsCategoryParameter);
//        List<>

    }

    @Override
    public JSONObject searchCategories(JSONObject jsonObject) {
        try {
            LunaGoodsCategoryCriteria lunaGoodsCategoryCriteria = new LunaGoodsCategoryCriteria();
            lunaGoodsCategoryCriteria.createCriteria().andNameLikeInsensitive("%" + jsonObject.getString("searchWord") + "%");
            JSONArray temp = getCategoriesByCriteria(lunaGoodsCategoryCriteria);
            Integer max = jsonObject.getInteger("limit");
            Integer min = jsonObject.getInteger("offset");
            if (max == null && min == null) {
                return FastJsonUtil.sucess("success", temp);
            } else {
                max = max.intValue() < 1 ? 1 : max;
                min = min.intValue() < 0 ? 0 : min;
                if (max <= min) temp.clear();
                JSONArray result = new JSONArray();
                for (int i = (min < temp.size() ? min : temp.size()); i < (max > temp.size() ? temp.size() : max); i++) {
                    result.add(temp.get(i));
                }
                return FastJsonUtil.sucess("success", result);
            }
        } catch (Exception ex) {
            logger.error("Failed to get lunaGoodsCategories", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
    }

    @Override
    public JSONObject createCategory(JSONObject jsonObject) {
        try {
            LunaGoodsCategory lunaGoodsCategory = new LunaGoodsCategory();
            lunaGoodsCategory.setName(jsonObject.getString("name"));
            lunaGoodsCategory.setDepth(jsonObject.getInteger("depth"));
            lunaGoodsCategory.setRoot(jsonObject.getInteger("root"));
            lunaGoodsCategory.setAbbreviation(jsonObject.getString("abbreviation"));
            lunaGoodsCategory.setUpdateTime(Calendar.getInstance().getTime());
            Integer id = lunaGoodsCategoryDAO.insert(lunaGoodsCategory);
            if (lunaGoodsCategory.getDepth() == 0) {
                lunaGoodsCategory.setRoot(id);
                lunaGoodsCategoryDAO.updateByPrimaryKey(lunaGoodsCategory);
            }
        } catch (Exception ex) {
            logger.error("Failed to insert lunaGoodsCategory", ex);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
        return FastJsonUtil.sucess("success");
    }

    @Override
    public JSONObject deleteCategory(JSONObject jsonObject) {
        try {
            lunaGoodsCategoryDAO.deleteByPrimaryKey(jsonObject.getInteger("id"));
        } catch (Exception ex) {
            logger.error("Failed to delete lunaGoodsCategory", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
        return FastJsonUtil.sucess("success");
    }

    @Override
    public JSONObject updateCategory(JSONObject jsonObject) {
        try {
            LunaGoodsCategory category = lunaGoodsCategoryDAO.selectByPrimaryKey(jsonObject.getInteger("id"));
            if (category == null) {
                return FastJsonUtil.error(ErrorCode.NOT_FOUND, "分类id不存在");
            }
            category.setUpdateTime(Calendar.getInstance().getTime());
            category.setAbbreviation(jsonObject.getString("abbreviation"));
            category.setName(jsonObject.getString("name"));
            category.setRoot(jsonObject.getInteger("root"));
            category.setDepth(jsonObject.getInteger("depth"));
            lunaGoodsCategoryDAO.updateByPrimaryKey(category);
        } catch (Exception ex) {
            logger.error("Failed to update lunaGoodsCategory", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
        return FastJsonUtil.sucess("success");
    }
}
