package ms.luna.biz.dao;

import java.util.List;
import ms.luna.biz.dao.model.LunaModule;
import ms.luna.biz.dao.model.LunaModuleCriteria;

public interface LunaModuleDAOBase {
    int countByCriteria(LunaModuleCriteria example);

    int deleteByCriteria(LunaModuleCriteria example);

    int deleteByPrimaryKey(Integer id);

    Integer insert(LunaModule record);

    Integer insertSelective(LunaModule record);

    List<LunaModule> selectByCriteria(LunaModuleCriteria example);

    LunaModule selectByPrimaryKey(Integer id);

    int updateByCriteriaSelective(LunaModule record, LunaModuleCriteria example);

    int updateByCriteria(LunaModule record, LunaModuleCriteria example);

    int updateByPrimaryKeySelective(LunaModule record);

    int updateByPrimaryKey(LunaModule record);
}