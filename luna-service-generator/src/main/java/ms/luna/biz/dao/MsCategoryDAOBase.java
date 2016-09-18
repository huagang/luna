package ms.luna.biz.dao;

import java.util.List;
import ms.luna.biz.dao.model.MsCategory;
import ms.luna.biz.dao.model.MsCategoryCriteria;

public interface MsCategoryDAOBase {
    int countByCriteria(MsCategoryCriteria example);

    int deleteByCriteria(MsCategoryCriteria example);

    int deleteByPrimaryKey(String categoryId);

    void insert(MsCategory record);

    void insertSelective(MsCategory record);

    List<MsCategory> selectByCriteria(MsCategoryCriteria example);

    MsCategory selectByPrimaryKey(String categoryId);

    int updateByCriteriaSelective(MsCategory record, MsCategoryCriteria example);

    int updateByCriteria(MsCategory record, MsCategoryCriteria example);

    int updateByPrimaryKeySelective(MsCategory record);

    int updateByPrimaryKey(MsCategory record);
}