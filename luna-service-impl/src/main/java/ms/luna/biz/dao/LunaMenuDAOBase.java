package ms.luna.biz.dao;

import java.util.List;
import ms.luna.biz.dao.model.LunaMenu;
import ms.luna.biz.dao.model.LunaMenuCriteria;

public interface LunaMenuDAOBase {
    int countByCriteria(LunaMenuCriteria example);

    int deleteByCriteria(LunaMenuCriteria example);

    int deleteByPrimaryKey(Integer id);

    Integer insert(LunaMenu record);

    Integer insertSelective(LunaMenu record);

    List<LunaMenu> selectByCriteria(LunaMenuCriteria example);

    LunaMenu selectByPrimaryKey(Integer id);

    int updateByCriteriaSelective(LunaMenu record, LunaMenuCriteria example);

    int updateByCriteria(LunaMenu record, LunaMenuCriteria example);

    int updateByPrimaryKeySelective(LunaMenu record);

    int updateByPrimaryKey(LunaMenu record);
}