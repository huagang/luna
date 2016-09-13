package ms.luna.biz.dao;

import java.util.List;
import ms.luna.biz.dao.model.LunaOrder;
import ms.luna.biz.dao.model.LunaOrderCriteria;

public interface LunaOrderDAOBase {
    int countByCriteria(LunaOrderCriteria example);

    int deleteByCriteria(LunaOrderCriteria example);

    int deleteByPrimaryKey(Integer id);

    Integer insert(LunaOrder record);

    Integer insertSelective(LunaOrder record);

    List<LunaOrder> selectByCriteria(LunaOrderCriteria example);

    LunaOrder selectByPrimaryKey(Integer id);

    int updateByCriteriaSelective(LunaOrder record, LunaOrderCriteria example);

    int updateByCriteria(LunaOrder record, LunaOrderCriteria example);

    int updateByPrimaryKeySelective(LunaOrder record);

    int updateByPrimaryKey(LunaOrder record);
}