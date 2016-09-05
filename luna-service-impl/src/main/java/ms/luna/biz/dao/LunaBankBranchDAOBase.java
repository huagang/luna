package ms.luna.biz.dao;

import java.util.List;
import ms.luna.biz.dao.model.LunaBankBranch;
import ms.luna.biz.dao.model.LunaBankBranchCriteria;

public interface LunaBankBranchDAOBase {
    int countByCriteria(LunaBankBranchCriteria example);

    int deleteByCriteria(LunaBankBranchCriteria example);

    int deleteByPrimaryKey(String bnkcode);

    void insert(LunaBankBranch record);

    void insertSelective(LunaBankBranch record);

    List<LunaBankBranch> selectByCriteria(LunaBankBranchCriteria example);

    LunaBankBranch selectByPrimaryKey(String bnkcode);

    int updateByCriteriaSelective(LunaBankBranch record, LunaBankBranchCriteria example);

    int updateByCriteria(LunaBankBranch record, LunaBankBranchCriteria example);

    int updateByPrimaryKeySelective(LunaBankBranch record);

    int updateByPrimaryKey(LunaBankBranch record);
}