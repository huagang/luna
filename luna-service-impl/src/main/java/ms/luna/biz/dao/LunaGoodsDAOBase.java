package ms.luna.biz.dao;

import java.util.List;
import ms.luna.biz.dao.model.LunaGoods;
import ms.luna.biz.dao.model.LunaGoodsCriteria;

public interface LunaGoodsDAOBase {
    int countByCriteria(LunaGoodsCriteria example);

    int deleteByCriteria(LunaGoodsCriteria example);

    int deleteByPrimaryKey(Integer id);

    Integer insert(LunaGoods record);

    Integer insertSelective(LunaGoods record);

    List<LunaGoods> selectByCriteria(LunaGoodsCriteria example);

    LunaGoods selectByPrimaryKey(Integer id);

    int updateByCriteriaSelective(LunaGoods record, LunaGoodsCriteria example);

    int updateByCriteria(LunaGoods record, LunaGoodsCriteria example);

    int updateByPrimaryKeySelective(LunaGoods record);

    int updateByPrimaryKey(LunaGoods record);
}