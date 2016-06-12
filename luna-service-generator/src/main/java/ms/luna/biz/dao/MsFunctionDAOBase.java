package ms.luna.biz.dao;

import java.util.List;
import ms.luna.biz.dao.model.MsFunction;
import ms.luna.biz.dao.model.MsFunctionCriteria;

public interface MsFunctionDAOBase {
    int countByCriteria(MsFunctionCriteria example);

    int deleteByCriteria(MsFunctionCriteria example);

    int deleteByPrimaryKey(String msFunctionCode);

    void insert(MsFunction record);

    void insertSelective(MsFunction record);

    List<MsFunction> selectByCriteria(MsFunctionCriteria example);

    MsFunction selectByPrimaryKey(String msFunctionCode);

    int updateByCriteriaSelective(MsFunction record, MsFunctionCriteria example);

    int updateByCriteria(MsFunction record, MsFunctionCriteria example);

    int updateByPrimaryKeySelective(MsFunction record);

    int updateByPrimaryKey(MsFunction record);
}