package ms.luna.biz.dao;

import java.util.List;
import ms.luna.biz.dao.model.GoodsCategoryTable;
import ms.luna.biz.dao.model.GoodsCategoryTableCriteria;

public interface GoodsCategoryTableDAOBase {
    int countByCriteria(GoodsCategoryTableCriteria example);

    int deleteByCriteria(GoodsCategoryTableCriteria example);

    int deleteByPrimaryKey(Integer categoryId);

    void insert(GoodsCategoryTable record);

    void insertSelective(GoodsCategoryTable record);

    List<GoodsCategoryTable> selectByCriteria(GoodsCategoryTableCriteria example);

    GoodsCategoryTable selectByPrimaryKey(Integer categoryId);

    int updateByCriteriaSelective(GoodsCategoryTable record, GoodsCategoryTableCriteria example);

    int updateByCriteria(GoodsCategoryTable record, GoodsCategoryTableCriteria example);

    int updateByPrimaryKeySelective(GoodsCategoryTable record);

    int updateByPrimaryKey(GoodsCategoryTable record);
}