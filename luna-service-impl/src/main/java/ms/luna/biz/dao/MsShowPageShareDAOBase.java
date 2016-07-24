package ms.luna.biz.dao;

import java.util.List;
import ms.luna.biz.dao.model.MsShowPageShare;
import ms.luna.biz.dao.model.MsShowPageShareCriteria;

public interface MsShowPageShareDAOBase {
    int countByCriteria(MsShowPageShareCriteria example);

    int deleteByCriteria(MsShowPageShareCriteria example);

    int deleteByPrimaryKey(Integer id);

    Integer insert(MsShowPageShare record);

    Integer insertSelective(MsShowPageShare record);

    List<MsShowPageShare> selectByCriteria(MsShowPageShareCriteria example);

    MsShowPageShare selectByPrimaryKey(Integer id);

    int updateByCriteriaSelective(MsShowPageShare record, MsShowPageShareCriteria example);

    int updateByCriteria(MsShowPageShare record, MsShowPageShareCriteria example);

    int updateByPrimaryKeySelective(MsShowPageShare record);

    int updateByPrimaryKey(MsShowPageShare record);
}