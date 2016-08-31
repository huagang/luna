package ms.luna.biz.dao;

import java.util.List;
import ms.luna.biz.dao.model.LunaCity;
import ms.luna.biz.dao.model.LunaCityCriteria;

public interface LunaCityDAOBase {
    int countByCriteria(LunaCityCriteria example);

    int deleteByCriteria(LunaCityCriteria example);

    int deleteByPrimaryKey(Integer cityNo);

    void insert(LunaCity record);

    void insertSelective(LunaCity record);

    List<LunaCity> selectByCriteria(LunaCityCriteria example);

    LunaCity selectByPrimaryKey(Integer cityNo);

    int updateByCriteriaSelective(LunaCity record, LunaCityCriteria example);

    int updateByCriteria(LunaCity record, LunaCityCriteria example);

    int updateByPrimaryKeySelective(LunaCity record);

    int updateByPrimaryKey(LunaCity record);
}