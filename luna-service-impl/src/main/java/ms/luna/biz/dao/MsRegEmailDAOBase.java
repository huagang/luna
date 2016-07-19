package ms.luna.biz.dao;

import ms.luna.biz.dao.model.MsRegEmail;
import ms.luna.biz.dao.model.MsRegEmailCriteria;

import java.util.List;

public interface MsRegEmailDAOBase {
    int countByCriteria(MsRegEmailCriteria example);

    int deleteByCriteria(MsRegEmailCriteria example);

    int deleteByPrimaryKey(String token);

    void insert(MsRegEmail record);

    void insertSelective(MsRegEmail record);

    List<MsRegEmail> selectByCriteria(MsRegEmailCriteria example);

    MsRegEmail selectByPrimaryKey(String token);

    int updateByCriteriaSelective(MsRegEmail record, MsRegEmailCriteria example);

    int updateByCriteria(MsRegEmail record, MsRegEmailCriteria example);

    int updateByPrimaryKeySelective(MsRegEmail record);

    int updateByPrimaryKey(MsRegEmail record);
}