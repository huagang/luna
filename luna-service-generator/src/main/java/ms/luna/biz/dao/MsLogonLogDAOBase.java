package ms.luna.biz.dao;

import java.util.List;
import ms.luna.biz.dao.model.MsLogonLog;
import ms.luna.biz.dao.model.MsLogonLogCriteria;

public interface MsLogonLogDAOBase {
    int countByCriteria(MsLogonLogCriteria example);

    int deleteByCriteria(MsLogonLogCriteria example);

    void insert(MsLogonLog record);

    void insertSelective(MsLogonLog record);

    List<MsLogonLog> selectByCriteria(MsLogonLogCriteria example);

    int updateByCriteriaSelective(MsLogonLog record, MsLogonLogCriteria example);

    int updateByCriteria(MsLogonLog record, MsLogonLogCriteria example);
}