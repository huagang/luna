package ms.luna.biz.dao;

import ms.luna.biz.dao.model.MsRTagField;
import ms.luna.biz.dao.model.MsRTagFieldCriteria;
import ms.luna.biz.dao.model.MsRTagFieldKey;

import java.util.List;

public interface MsRTagFieldDAOBase {
    int countByCriteria(MsRTagFieldCriteria example);

    int deleteByCriteria(MsRTagFieldCriteria example);

    int deleteByPrimaryKey(MsRTagFieldKey _key);

    void insert(MsRTagField record);

    void insertSelective(MsRTagField record);

    List<MsRTagField> selectByCriteria(MsRTagFieldCriteria example);

    MsRTagField selectByPrimaryKey(MsRTagFieldKey _key);

    int updateByCriteriaSelective(MsRTagField record, MsRTagFieldCriteria example);

    int updateByCriteria(MsRTagField record, MsRTagFieldCriteria example);

    int updateByPrimaryKeySelective(MsRTagField record);

    int updateByPrimaryKey(MsRTagField record);
}