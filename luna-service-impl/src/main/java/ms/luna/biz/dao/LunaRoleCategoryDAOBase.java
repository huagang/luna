package ms.luna.biz.dao;

import java.util.List;
import ms.luna.biz.dao.model.LunaRoleCategory;
import ms.luna.biz.dao.model.LunaRoleCategoryCriteria;

public interface LunaRoleCategoryDAOBase {
    int countByCriteria(LunaRoleCategoryCriteria example);

    int deleteByCriteria(LunaRoleCategoryCriteria example);

    int deleteByPrimaryKey(Integer id);

    Integer insert(LunaRoleCategory record);

    Integer insertSelective(LunaRoleCategory record);

    List<LunaRoleCategory> selectByCriteriaWithBLOBs(LunaRoleCategoryCriteria example);

    List<LunaRoleCategory> selectByCriteriaWithoutBLOBs(LunaRoleCategoryCriteria example);

    LunaRoleCategory selectByPrimaryKey(Integer id);

    int updateByCriteriaSelective(LunaRoleCategory record, LunaRoleCategoryCriteria example);

    int updateByCriteriaWithBLOBs(LunaRoleCategory record, LunaRoleCategoryCriteria example);

    int updateByCriteriaWithoutBLOBs(LunaRoleCategory record, LunaRoleCategoryCriteria example);

    int updateByPrimaryKeySelective(LunaRoleCategory record);

    int updateByPrimaryKeyWithBLOBs(LunaRoleCategory record);

    int updateByPrimaryKeyWithoutBLOBs(LunaRoleCategory record);
}