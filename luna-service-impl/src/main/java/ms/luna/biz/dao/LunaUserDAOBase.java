package ms.luna.biz.dao;

import java.util.List;
import ms.luna.biz.dao.model.LunaUser;
import ms.luna.biz.dao.model.LunaUserCriteria;

public interface LunaUserDAOBase {
    int countByCriteria(LunaUserCriteria example);

    int deleteByCriteria(LunaUserCriteria example);

    int deleteByPrimaryKey(String uniqueId);

    void insert(LunaUser record);

    void insertSelective(LunaUser record);

    List<LunaUser> selectByCriteria(LunaUserCriteria example);

    LunaUser selectByPrimaryKey(String uniqueId);

    int updateByCriteriaSelective(LunaUser record, LunaUserCriteria example);

    int updateByCriteria(LunaUser record, LunaUserCriteria example);

    int updateByPrimaryKeySelective(LunaUser record);

    int updateByPrimaryKey(LunaUser record);
}