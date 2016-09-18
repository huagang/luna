package ms.luna.biz.dao;

import java.util.List;
import ms.luna.biz.dao.model.MsShowApp;
import ms.luna.biz.dao.model.MsShowAppCriteria;

public interface MsShowAppDAOBase {
    int countByCriteria(MsShowAppCriteria example);

    int deleteByCriteria(MsShowAppCriteria example);

    int deleteByPrimaryKey(Integer appId);

    Integer insert(MsShowApp record);

    Integer insertSelective(MsShowApp record);

    List<MsShowApp> selectByCriteria(MsShowAppCriteria example);

    MsShowApp selectByPrimaryKey(Integer appId);

    int updateByCriteriaSelective(MsShowApp record, MsShowAppCriteria example);

    int updateByCriteria(MsShowApp record, MsShowAppCriteria example);

    int updateByPrimaryKeySelective(MsShowApp record);

    int updateByPrimaryKey(MsShowApp record);

    MsShowApp selectByPrimaryKeyWithoutDeleted(Integer appId);

    int selectCountByPrimaryKeyWithoutDeleted(Integer appId);
}