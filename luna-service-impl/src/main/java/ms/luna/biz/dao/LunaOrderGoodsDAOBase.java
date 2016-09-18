package ms.luna.biz.dao;

import java.util.List;
import ms.luna.biz.dao.model.LunaOrderGoods;
import ms.luna.biz.dao.model.LunaOrderGoodsCriteria;

public interface LunaOrderGoodsDAOBase {
    int countByCriteria(LunaOrderGoodsCriteria example);

    int deleteByCriteria(LunaOrderGoodsCriteria example);

    void insert(LunaOrderGoods record);

    void insertSelective(LunaOrderGoods record);

    List<LunaOrderGoods> selectByCriteria(LunaOrderGoodsCriteria example);

    int updateByCriteriaSelective(LunaOrderGoods record, LunaOrderGoodsCriteria example);

    int updateByCriteria(LunaOrderGoods record, LunaOrderGoodsCriteria example);
}