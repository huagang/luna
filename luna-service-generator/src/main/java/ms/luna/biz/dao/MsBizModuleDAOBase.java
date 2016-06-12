package ms.luna.biz.dao;

import java.util.List;
import ms.luna.biz.dao.model.MsBizModule;
import ms.luna.biz.dao.model.MsBizModuleCriteria;

public interface MsBizModuleDAOBase {
    int countByCriteria(MsBizModuleCriteria example);

    int deleteByCriteria(MsBizModuleCriteria example);

    int deleteByPrimaryKey(String bizModuleCode);

    void insert(MsBizModule record);

    void insertSelective(MsBizModule record);

    List<MsBizModule> selectByCriteria(MsBizModuleCriteria example);

    MsBizModule selectByPrimaryKey(String bizModuleCode);

    int updateByCriteriaSelective(MsBizModule record, MsBizModuleCriteria example);

    int updateByCriteria(MsBizModule record, MsBizModuleCriteria example);

    int updateByPrimaryKeySelective(MsBizModule record);

    int updateByPrimaryKey(MsBizModule record);
}