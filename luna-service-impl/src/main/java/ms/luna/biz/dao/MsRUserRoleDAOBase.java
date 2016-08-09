package ms.luna.biz.dao;

import ms.luna.biz.dao.model.MsRUserRole;
import ms.luna.biz.dao.model.MsRUserRoleCriteria;
import ms.luna.biz.dao.model.MsRUserRoleKey;

import java.util.List;

public interface MsRUserRoleDAOBase {
    int countByCriteria(MsRUserRoleCriteria example);

    int deleteByCriteria(MsRUserRoleCriteria example);

    int deleteByPrimaryKey(MsRUserRoleKey _key);

    void insert(MsRUserRole record);

    void insertSelective(MsRUserRole record);

    List<MsRUserRole> selectByCriteria(MsRUserRoleCriteria example);

    MsRUserRole selectByPrimaryKey(MsRUserRoleKey _key);

    int updateByCriteriaSelective(MsRUserRole record, MsRUserRoleCriteria example);

    int updateByCriteria(MsRUserRole record, MsRUserRoleCriteria example);

    int updateByPrimaryKeySelective(MsRUserRole record);

    int updateByPrimaryKey(MsRUserRole record);
}