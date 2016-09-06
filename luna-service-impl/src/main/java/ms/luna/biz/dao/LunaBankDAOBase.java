package ms.luna.biz.dao;

import java.util.List;
import ms.luna.biz.dao.model.LunaBank;
import ms.luna.biz.dao.model.LunaBankCriteria;

public interface LunaBankDAOBase {
    int countByCriteria(LunaBankCriteria example);

    int deleteByCriteria(LunaBankCriteria example);

    int deleteByPrimaryKey(String bankcode);

    void insert(LunaBank record);

    void insertSelective(LunaBank record);

    List<LunaBank> selectByCriteria(LunaBankCriteria example);

    LunaBank selectByPrimaryKey(String bankcode);

    int updateByCriteriaSelective(LunaBank record, LunaBankCriteria example);

    int updateByCriteria(LunaBank record, LunaBankCriteria example);

    int updateByPrimaryKeySelective(LunaBank record);

    int updateByPrimaryKey(LunaBank record);
}