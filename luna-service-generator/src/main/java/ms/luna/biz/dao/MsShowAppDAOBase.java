package ms.luna.biz.dao;

import java.util.List;
import ms.luna.biz.dao.model.MsShowApp;
import ms.luna.biz.dao.model.MsShowAppCriteria;

public interface MsShowAppDAOBase {
    int countByCriteria(MsShowAppCriteria example);

    int deleteByCriteria(MsShowAppCriteria example);

    int deleteByPrimaryKey(Integer appId);

    void insert(MsShowApp record);

    void insertSelective(MsShowApp record);

    List<MsShowApp> selectByCriteria(MsShowAppCriteria example);

    MsShowApp selectByPrimaryKey(Integer appId);

    int updateByCriteriaSelective(MsShowApp record, MsShowAppCriteria example);

    int updateByCriteria(MsShowApp record, MsShowAppCriteria example);

    int updateByPrimaryKeySelective(MsShowApp record);

    int updateByPrimaryKey(MsShowApp record);
}