package ms.luna.biz.dao;

import java.util.List;
import ms.luna.biz.dao.model.LunaRegEmail;
import ms.luna.biz.dao.model.LunaRegEmailCriteria;

public interface LunaRegEmailDAOBase {
    int countByCriteria(LunaRegEmailCriteria example);

    int deleteByCriteria(LunaRegEmailCriteria example);

    int deleteByPrimaryKey(String token);

    void insert(LunaRegEmail record);

    void insertSelective(LunaRegEmail record);

    List<LunaRegEmail> selectByCriteriaWithBLOBs(LunaRegEmailCriteria example);

    List<LunaRegEmail> selectByCriteriaWithoutBLOBs(LunaRegEmailCriteria example);

    LunaRegEmail selectByPrimaryKey(String token);

    int updateByCriteriaSelective(LunaRegEmail record, LunaRegEmailCriteria example);

    int updateByCriteriaWithBLOBs(LunaRegEmail record, LunaRegEmailCriteria example);

    int updateByCriteriaWithoutBLOBs(LunaRegEmail record, LunaRegEmailCriteria example);

    int updateByPrimaryKeySelective(LunaRegEmail record);

    int updateByPrimaryKeyWithBLOBs(LunaRegEmail record);

    int updateByPrimaryKeyWithoutBLOBs(LunaRegEmail record);
}