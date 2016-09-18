package ms.luna.biz.dao;

import java.util.List;
import ms.luna.biz.dao.model.MsRRoleFunction;
import ms.luna.biz.dao.model.MsRRoleFunctionCriteria;
import ms.luna.biz.dao.model.MsRRoleFunctionKey;

public interface MsRRoleFunctionDAOBase {
    int countByCriteria(MsRRoleFunctionCriteria example);

    int deleteByCriteria(MsRRoleFunctionCriteria example);

    int deleteByPrimaryKey(MsRRoleFunctionKey _key);

    void insert(MsRRoleFunction record);

    void insertSelective(MsRRoleFunction record);

    List<MsRRoleFunction> selectByCriteria(MsRRoleFunctionCriteria example);

    MsRRoleFunction selectByPrimaryKey(MsRRoleFunctionKey _key);

    int updateByCriteriaSelective(MsRRoleFunction record, MsRRoleFunctionCriteria example);

    int updateByCriteria(MsRRoleFunction record, MsRRoleFunctionCriteria example);

    int updateByPrimaryKeySelective(MsRRoleFunction record);

    int updateByPrimaryKey(MsRRoleFunction record);
}