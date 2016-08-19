package ms.luna.biz.dao;

import java.util.List;
import ms.luna.biz.dao.model.MsOperationLog;
import ms.luna.biz.dao.model.MsOperationLogCriteria;

public interface MsOperationLogDAOBase {
    int countByCriteria(MsOperationLogCriteria example);

    int deleteByCriteria(MsOperationLogCriteria example);

    int deleteByPrimaryKey(Integer id);

    Integer insert(MsOperationLog record);

    Integer insertSelective(MsOperationLog record);

    List<MsOperationLog> selectByCriteria(MsOperationLogCriteria example);

    MsOperationLog selectByPrimaryKey(Integer id);

    int updateByCriteriaSelective(MsOperationLog record, MsOperationLogCriteria example);

    int updateByCriteria(MsOperationLog record, MsOperationLogCriteria example);

    int updateByPrimaryKeySelective(MsOperationLog record);

    int updateByPrimaryKey(MsOperationLog record);
}