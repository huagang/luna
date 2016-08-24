package ms.luna.biz.dao;

import java.util.List;
import ms.luna.biz.dao.model.LunaGoodsCategory;
import ms.luna.biz.dao.model.LunaGoodsCategoryCriteria;

public interface LunaGoodsCategoryDAOBase {
    int countByCriteria(LunaGoodsCategoryCriteria example);

    int deleteByCriteria(LunaGoodsCategoryCriteria example);

    int deleteByPrimaryKey(Integer id);

    Integer insert(LunaGoodsCategory record);

    Integer insertSelective(LunaGoodsCategory record);

    List<LunaGoodsCategory> selectByCriteria(LunaGoodsCategoryCriteria example);

    LunaGoodsCategory selectByPrimaryKey(Integer id);

    int updateByCriteriaSelective(LunaGoodsCategory record, LunaGoodsCategoryCriteria example);

    int updateByCriteria(LunaGoodsCategory record, LunaGoodsCategoryCriteria example);

    int updateByPrimaryKeySelective(LunaGoodsCategory record);

    int updateByPrimaryKey(LunaGoodsCategory record);
}