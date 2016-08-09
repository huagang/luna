package ms.luna.biz.dao;

import ms.luna.biz.dao.model.MsUserLuna;
import ms.luna.biz.dao.model.MsUserLunaCriteria;

import java.util.List;

public interface MsUserLunaDAOBase {
    int countByCriteria(MsUserLunaCriteria example);

    int deleteByCriteria(MsUserLunaCriteria example);

    int deleteByPrimaryKey(String uniqueId);

    void insert(MsUserLuna record);

    void insertSelective(MsUserLuna record);

    List<MsUserLuna> selectByCriteria(MsUserLunaCriteria example);

    MsUserLuna selectByPrimaryKey(String uniqueId);

    int updateByCriteriaSelective(MsUserLuna record, MsUserLunaCriteria example);

    int updateByCriteria(MsUserLuna record, MsUserLunaCriteria example);

    int updateByPrimaryKeySelective(MsUserLuna record);

    int updateByPrimaryKey(MsUserLuna record);
}