package ms.luna.biz.dao;

import java.util.List;
import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.LunaUserMerchant;
import ms.luna.biz.dao.model.LunaUserMerchantCriteria;

public abstract class LunaUserMerchantDAOBaseImpl extends MsBaseDAO implements LunaUserMerchantDAOBase {

    public LunaUserMerchantDAOBaseImpl() {
        super();
    }

    public int countByCriteria(LunaUserMerchantCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("luna_user_merchant.countByExample", example);
        return count;
    }

    public int deleteByCriteria(LunaUserMerchantCriteria example) {
        int rows = getSqlMapClientTemplate().delete("luna_user_merchant.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(String uniqueId) {
        LunaUserMerchant _key = new LunaUserMerchant();
        _key.setUniqueId(uniqueId);
        int rows = getSqlMapClientTemplate().delete("luna_user_merchant.deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(LunaUserMerchant record) {
        getSqlMapClientTemplate().insert("luna_user_merchant.insert", record);
    }

    public void insertSelective(LunaUserMerchant record) {
        getSqlMapClientTemplate().insert("luna_user_merchant.insertSelective", record);
    }

    @SuppressWarnings("unchecked")
    public List<LunaUserMerchant> selectByCriteria(LunaUserMerchantCriteria example) {
        List<LunaUserMerchant> list = getSqlMapClientTemplate().queryForList("luna_user_merchant.selectByExample", example);
        return list;
    }

    public LunaUserMerchant selectByPrimaryKey(String uniqueId) {
        LunaUserMerchant _key = new LunaUserMerchant();
        _key.setUniqueId(uniqueId);
        LunaUserMerchant record = (LunaUserMerchant) getSqlMapClientTemplate().queryForObject("luna_user_merchant.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(LunaUserMerchant record, LunaUserMerchantCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("luna_user_merchant.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(LunaUserMerchant record, LunaUserMerchantCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("luna_user_merchant.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(LunaUserMerchant record) {
        int rows = getSqlMapClientTemplate().update("luna_user_merchant.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(LunaUserMerchant record) {
        int rows = getSqlMapClientTemplate().update("luna_user_merchant.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends LunaUserMerchantCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, LunaUserMerchantCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}