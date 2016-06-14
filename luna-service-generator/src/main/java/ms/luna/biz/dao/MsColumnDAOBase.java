package ms.luna.biz.dao;

import java.util.List;
import ms.luna.biz.dao.model.MsColumn;
import ms.luna.biz.dao.model.MsColumnCriteria;

public interface MsColumnDAOBase {
    int countByCriteria(MsColumnCriteria example);

    int deleteByCriteria(MsColumnCriteria example);

    int deleteByPrimaryKey(Integer id);

    void insert(MsColumn record);

    void insertSelective(MsColumn record);

    List<MsColumn> selectByCriteria(MsColumnCriteria example);

    MsColumn selectByPrimaryKey(Integer id);

    int updateByCriteriaSelective(MsColumn record, MsColumnCriteria example);

    int updateByCriteria(MsColumn record, MsColumnCriteria example);

    int updateByPrimaryKeySelective(MsColumn record);

    int updateByPrimaryKey(MsColumn record);
}