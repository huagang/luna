package ms.luna.biz.dao;

import ms.luna.biz.dao.model.MsResourceUri;
import ms.luna.biz.dao.model.MsResourceUriCriteria;

import java.util.List;

public interface MsResourceUriDAOBase {
    int countByCriteria(MsResourceUriCriteria example);

    int deleteByCriteria(MsResourceUriCriteria example);

    int deleteByPrimaryKey(Integer resourceId);

    void insert(MsResourceUri record);

    void insertSelective(MsResourceUri record);

    List<MsResourceUri> selectByCriteria(MsResourceUriCriteria example);

    MsResourceUri selectByPrimaryKey(Integer resourceId);

    int updateByCriteriaSelective(MsResourceUri record, MsResourceUriCriteria example);

    int updateByCriteria(MsResourceUri record, MsResourceUriCriteria example);

    int updateByPrimaryKeySelective(MsResourceUri record);

    int updateByPrimaryKey(MsResourceUri record);
}