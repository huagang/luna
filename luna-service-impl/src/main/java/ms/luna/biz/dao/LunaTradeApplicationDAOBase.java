package ms.luna.biz.dao;

import java.util.List;
import ms.luna.biz.dao.model.LunaTradeApplication;
import ms.luna.biz.dao.model.LunaTradeApplicationCriteria;

public interface LunaTradeApplicationDAOBase {
    int countByCriteria(LunaTradeApplicationCriteria example);

    int deleteByCriteria(LunaTradeApplicationCriteria example);

    int deleteByPrimaryKey(Integer id);

    Integer insert(LunaTradeApplication record);

    Integer insertSelective(LunaTradeApplication record);

    List<LunaTradeApplication> selectByCriteria(LunaTradeApplicationCriteria example);

    LunaTradeApplication selectByPrimaryKey(Integer id);

    int updateByCriteriaSelective(LunaTradeApplication record, LunaTradeApplicationCriteria example);

    int updateByCriteria(LunaTradeApplication record, LunaTradeApplicationCriteria example);

    int updateByPrimaryKeySelective(LunaTradeApplication record);

    int updateByPrimaryKey(LunaTradeApplication record);
}