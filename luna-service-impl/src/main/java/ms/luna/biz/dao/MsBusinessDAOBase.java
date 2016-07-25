package ms.luna.biz.dao;

import ms.luna.biz.dao.model.MsBusiness;
import ms.luna.biz.dao.model.MsBusinessCriteria;

import java.util.List;

public interface MsBusinessDAOBase {
    int countByCriteria(MsBusinessCriteria example);

    int deleteByCriteria(MsBusinessCriteria example);

    int deleteByPrimaryKey(Integer businessId);

    void insert(MsBusiness record);

    void insertSelective(MsBusiness record);

    List<MsBusiness> selectByCriteria(MsBusinessCriteria example);

    MsBusiness selectByPrimaryKey(Integer businessId);

    int updateByCriteriaSelective(MsBusiness record, MsBusinessCriteria example);

    int updateByCriteria(MsBusiness record, MsBusinessCriteria example);

    int updateByPrimaryKeySelective(MsBusiness record);

    int updateByPrimaryKey(MsBusiness record);
}