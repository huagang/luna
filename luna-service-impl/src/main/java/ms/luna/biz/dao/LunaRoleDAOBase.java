package ms.luna.biz.dao;

import java.util.List;
import ms.luna.biz.dao.model.LunaRole;
import ms.luna.biz.dao.model.LunaRoleCriteria;

public interface LunaRoleDAOBase {
    int countByCriteria(LunaRoleCriteria example);

    int deleteByCriteria(LunaRoleCriteria example);

    int deleteByPrimaryKey(Integer id);

    Integer insert(LunaRole record);

    Integer insertSelective(LunaRole record);

    List<LunaRole> selectByCriteria(LunaRoleCriteria example);

    LunaRole selectByPrimaryKey(Integer id);

    int updateByCriteriaSelective(LunaRole record, LunaRoleCriteria example);

    int updateByCriteria(LunaRole record, LunaRoleCriteria example);

    int updateByPrimaryKeySelective(LunaRole record);

    int updateByPrimaryKey(LunaRole record);
}