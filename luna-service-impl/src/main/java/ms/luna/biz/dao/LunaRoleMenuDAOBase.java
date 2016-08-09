package ms.luna.biz.dao;

import java.util.List;
import ms.luna.biz.dao.model.LunaRoleMenu;
import ms.luna.biz.dao.model.LunaRoleMenuCriteria;
import ms.luna.biz.dao.model.LunaRoleMenuKey;

public interface LunaRoleMenuDAOBase {
    int countByCriteria(LunaRoleMenuCriteria example);

    int deleteByCriteria(LunaRoleMenuCriteria example);

    int deleteByPrimaryKey(LunaRoleMenuKey _key);

    void insert(LunaRoleMenu record);

    void insertSelective(LunaRoleMenu record);

    List<LunaRoleMenu> selectByCriteria(LunaRoleMenuCriteria example);

    LunaRoleMenu selectByPrimaryKey(LunaRoleMenuKey _key);

    int updateByCriteriaSelective(LunaRoleMenu record, LunaRoleMenuCriteria example);

    int updateByCriteria(LunaRoleMenu record, LunaRoleMenuCriteria example);

    int updateByPrimaryKeySelective(LunaRoleMenu record);

    int updateByPrimaryKey(LunaRoleMenu record);
}