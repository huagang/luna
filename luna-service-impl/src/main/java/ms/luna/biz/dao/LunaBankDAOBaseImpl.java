package ms.luna.biz.dao;

import java.util.List;
import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.LunaBank;
import ms.luna.biz.dao.model.LunaBankCriteria;

public abstract class LunaBankDAOBaseImpl extends MsBaseDAO implements LunaBankDAOBase {

    public LunaBankDAOBaseImpl() {
        super();
    }

    public int countByCriteria(LunaBankCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("luna_bank.countByExample", example);
        return count;
    }

    public int deleteByCriteria(LunaBankCriteria example) {
        int rows = getSqlMapClientTemplate().delete("luna_bank.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(String bankcode) {
        LunaBank _key = new LunaBank();
        _key.setBankcode(bankcode);
        int rows = getSqlMapClientTemplate().delete("luna_bank.deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(LunaBank record) {
        getSqlMapClientTemplate().insert("luna_bank.insert", record);
    }

    public void insertSelective(LunaBank record) {
        getSqlMapClientTemplate().insert("luna_bank.insertSelective", record);
    }

    @SuppressWarnings("unchecked")
    public List<LunaBank> selectByCriteria(LunaBankCriteria example) {
        List<LunaBank> list = getSqlMapClientTemplate().queryForList("luna_bank.selectByExample", example);
        return list;
    }

    public LunaBank selectByPrimaryKey(String bankcode) {
        LunaBank _key = new LunaBank();
        _key.setBankcode(bankcode);
        LunaBank record = (LunaBank) getSqlMapClientTemplate().queryForObject("luna_bank.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(LunaBank record, LunaBankCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("luna_bank.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(LunaBank record, LunaBankCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("luna_bank.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(LunaBank record) {
        int rows = getSqlMapClientTemplate().update("luna_bank.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(LunaBank record) {
        int rows = getSqlMapClientTemplate().update("luna_bank.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends LunaBankCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, LunaBankCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}