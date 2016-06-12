package ms.luna.biz.dao;

import java.util.List;
import ms.luna.biz.dao.model.MsRole;
import ms.luna.biz.dao.model.MsRoleCriteria;

public interface MsRoleDAOBase {
    int countByCriteria(MsRoleCriteria example);

    int deleteByCriteria(MsRoleCriteria example);

    int deleteByPrimaryKey(String msRoleCode);

    void insert(MsRole record);

    void insertSelective(MsRole record);

    List<MsRole> selectByCriteria(MsRoleCriteria example);

    MsRole selectByPrimaryKey(String msRoleCode);

    int updateByCriteriaSelective(MsRole record, MsRoleCriteria example);

    int updateByCriteria(MsRole record, MsRoleCriteria example);

    int updateByPrimaryKeySelective(MsRole record);

    int updateByPrimaryKey(MsRole record);
}