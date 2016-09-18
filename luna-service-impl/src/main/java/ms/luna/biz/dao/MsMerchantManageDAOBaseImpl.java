package ms.luna.biz.dao;

import java.util.List;
import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.MsMerchantManage;
import ms.luna.biz.dao.model.MsMerchantManageCriteria;

public abstract class MsMerchantManageDAOBaseImpl extends MsBaseDAO implements MsMerchantManageDAOBase {

    public MsMerchantManageDAOBaseImpl() {
        super();
    }

    public int countByCriteria(MsMerchantManageCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("ms_merchant_manage.countByExample", example);
        return count;
    }

    public int deleteByCriteria(MsMerchantManageCriteria example) {
        int rows = getSqlMapClientTemplate().delete("ms_merchant_manage.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(String merchantId) {
        MsMerchantManage _key = new MsMerchantManage();
        _key.setMerchantId(merchantId);
        int rows = getSqlMapClientTemplate().delete("ms_merchant_manage.deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(MsMerchantManage record) {
        getSqlMapClientTemplate().insert("ms_merchant_manage.insert", record);
    }

    public void insertSelective(MsMerchantManage record) {
        getSqlMapClientTemplate().insert("ms_merchant_manage.insertSelective", record);
    }

    @SuppressWarnings("unchecked")
    public List<MsMerchantManage> selectByCriteria(MsMerchantManageCriteria example) {
        List<MsMerchantManage> list = getSqlMapClientTemplate().queryForList("ms_merchant_manage.selectByExample", example);
        return list;
    }

    public MsMerchantManage selectByPrimaryKey(String merchantId) {
        MsMerchantManage _key = new MsMerchantManage();
        _key.setMerchantId(merchantId);
        MsMerchantManage record = (MsMerchantManage) getSqlMapClientTemplate().queryForObject("ms_merchant_manage.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(MsMerchantManage record, MsMerchantManageCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_merchant_manage.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(MsMerchantManage record, MsMerchantManageCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_merchant_manage.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(MsMerchantManage record) {
        int rows = getSqlMapClientTemplate().update("ms_merchant_manage.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MsMerchantManage record) {
        int rows = getSqlMapClientTemplate().update("ms_merchant_manage.updateByPrimaryKey", record);
        return rows;
    }

    public MsMerchantManage selectByPrimaryKeyWithoutDeleted(String merchantId) {
        MsMerchantManageCriteria criteria = new MsMerchantManageCriteria();
        criteria.createCriteria()
        .andMerchantIdEqualTo(merchantId)
        .andDelFlgEqualTo("0");
        MsMerchantManage record = (MsMerchantManage) getSqlMapClientTemplate().queryForObject("ms_merchant_manage.selectByExample", criteria);
        return record;
    }

    public int selectCountByPrimaryKeyWithoutDeleted(String merchantId) {
        MsMerchantManageCriteria criteria = new MsMerchantManageCriteria();
        criteria.createCriteria()
        .andMerchantIdEqualTo(merchantId)
        .andDelFlgEqualTo("0");
        Integer count = (Integer) getSqlMapClientTemplate().queryForObject("ms_merchant_manage.countByExample", criteria);
        return count;
    }

    protected static class UpdateByExampleParms extends MsMerchantManageCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, MsMerchantManageCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}