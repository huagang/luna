package ms.luna.biz.dao;

import java.util.List;
import ms.luna.biz.dao.model.MsMerchantManage;
import ms.luna.biz.dao.model.MsMerchantManageCriteria;

public interface MsMerchantManageDAOBase {
    int countByCriteria(MsMerchantManageCriteria example);

    int deleteByCriteria(MsMerchantManageCriteria example);

    int deleteByPrimaryKey(String merchantId);

    void insert(MsMerchantManage record);

    void insertSelective(MsMerchantManage record);

    List<MsMerchantManage> selectByCriteria(MsMerchantManageCriteria example);

    MsMerchantManage selectByPrimaryKey(String merchantId);

    int updateByCriteriaSelective(MsMerchantManage record, MsMerchantManageCriteria example);

    int updateByCriteria(MsMerchantManage record, MsMerchantManageCriteria example);

    int updateByPrimaryKeySelective(MsMerchantManage record);

    int updateByPrimaryKey(MsMerchantManage record);

    MsMerchantManage selectByPrimaryKeyWithoutDeleted(String merchantId);

    int selectCountByPrimaryKeyWithoutDeleted(String merchantId);
}