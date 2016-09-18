package ms.luna.biz.dao;

import ms.luna.biz.dao.model.MsUserThirdLogin;
import ms.luna.biz.dao.model.MsUserThirdLoginCriteria;
import ms.luna.biz.dao.model.MsUserThirdLoginKey;

import java.util.List;

public interface MsUserThirdLoginDAOBase {
    int countByCriteria(MsUserThirdLoginCriteria example);

    int deleteByCriteria(MsUserThirdLoginCriteria example);

    int deleteByPrimaryKey(MsUserThirdLoginKey _key);

    void insert(MsUserThirdLogin record);

    void insertSelective(MsUserThirdLogin record);

    List<MsUserThirdLogin> selectByCriteria(MsUserThirdLoginCriteria example);

    MsUserThirdLogin selectByPrimaryKey(MsUserThirdLoginKey _key);

    int updateByCriteriaSelective(MsUserThirdLogin record, MsUserThirdLoginCriteria example);

    int updateByCriteria(MsUserThirdLogin record, MsUserThirdLoginCriteria example);

    int updateByPrimaryKeySelective(MsUserThirdLogin record);

    int updateByPrimaryKey(MsUserThirdLogin record);
}