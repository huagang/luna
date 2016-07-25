package ms.luna.biz.dao;

import ms.luna.biz.dao.model.MsUserPw;
import ms.luna.biz.dao.model.MsUserPwCriteria;

import java.util.List;

public interface MsUserPwDAOBase {
    int countByCriteria(MsUserPwCriteria example);

    int deleteByCriteria(MsUserPwCriteria example);

    int deleteByPrimaryKey(String lunaName);

    void insert(MsUserPw record);

    void insertSelective(MsUserPw record);

    List<MsUserPw> selectByCriteria(MsUserPwCriteria example);

    MsUserPw selectByPrimaryKey(String lunaName);

    int updateByCriteriaSelective(MsUserPw record, MsUserPwCriteria example);

    int updateByCriteria(MsUserPw record, MsUserPwCriteria example);

    int updateByPrimaryKeySelective(MsUserPw record);

    int updateByPrimaryKey(MsUserPw record);
}