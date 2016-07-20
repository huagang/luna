package ms.luna.biz.dao;

import ms.luna.biz.dao.model.MsRFunctionResourceUri;
import ms.luna.biz.dao.model.MsRFunctionResourceUriCriteria;

import java.util.List;

public interface MsRFunctionResourceUriDAOBase {
    int countByCriteria(MsRFunctionResourceUriCriteria example);

    int deleteByCriteria(MsRFunctionResourceUriCriteria example);

    int deleteByPrimaryKey(Integer id);

    void insert(MsRFunctionResourceUri record);

    void insertSelective(MsRFunctionResourceUri record);

    List<MsRFunctionResourceUri> selectByCriteria(MsRFunctionResourceUriCriteria example);

    MsRFunctionResourceUri selectByPrimaryKey(Integer id);

    int updateByCriteriaSelective(MsRFunctionResourceUri record, MsRFunctionResourceUriCriteria example);

    int updateByCriteria(MsRFunctionResourceUri record, MsRFunctionResourceUriCriteria example);

    int updateByPrimaryKeySelective(MsRFunctionResourceUri record);

    int updateByPrimaryKey(MsRFunctionResourceUri record);
}