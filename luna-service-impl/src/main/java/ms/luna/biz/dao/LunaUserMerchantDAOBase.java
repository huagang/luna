package ms.luna.biz.dao;

import java.util.List;
import ms.luna.biz.dao.model.LunaUserMerchant;
import ms.luna.biz.dao.model.LunaUserMerchantCriteria;

public interface LunaUserMerchantDAOBase {
    int countByCriteria(LunaUserMerchantCriteria example);

    int deleteByCriteria(LunaUserMerchantCriteria example);

    int deleteByPrimaryKey(String uniqueId);

    void insert(LunaUserMerchant record);

    void insertSelective(LunaUserMerchant record);

    List<LunaUserMerchant> selectByCriteria(LunaUserMerchantCriteria example);

    LunaUserMerchant selectByPrimaryKey(String uniqueId);

    int updateByCriteriaSelective(LunaUserMerchant record, LunaUserMerchantCriteria example);

    int updateByCriteria(LunaUserMerchant record, LunaUserMerchantCriteria example);

    int updateByPrimaryKeySelective(LunaUserMerchant record);

    int updateByPrimaryKey(LunaUserMerchant record);
}