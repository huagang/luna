package ms.luna.biz.dao;

import java.util.List;
import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.LunaRegEmail;
import ms.luna.biz.dao.model.LunaRegEmailCriteria;

public abstract class LunaRegEmailDAOBaseImpl extends MsBaseDAO implements LunaRegEmailDAOBase {

    public LunaRegEmailDAOBaseImpl() {
        super();
    }

    public int countByCriteria(LunaRegEmailCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("luna_reg_email.countByExample", example);
        return count;
    }

    public int deleteByCriteria(LunaRegEmailCriteria example) {
        int rows = getSqlMapClientTemplate().delete("luna_reg_email.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(String token) {
        LunaRegEmail _key = new LunaRegEmail();
        _key.setToken(token);
        int rows = getSqlMapClientTemplate().delete("luna_reg_email.deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(LunaRegEmail record) {
        getSqlMapClientTemplate().insert("luna_reg_email.insert", record);
    }

    public void insertSelective(LunaRegEmail record) {
        getSqlMapClientTemplate().insert("luna_reg_email.insertSelective", record);
    }

    @SuppressWarnings("unchecked")
    public List<LunaRegEmail> selectByCriteriaWithBLOBs(LunaRegEmailCriteria example) {
        List<LunaRegEmail> list = getSqlMapClientTemplate().queryForList("luna_reg_email.selectByExampleWithBLOBs", example);
        return list;
    }

    @SuppressWarnings("unchecked")
    public List<LunaRegEmail> selectByCriteriaWithoutBLOBs(LunaRegEmailCriteria example) {
        List<LunaRegEmail> list = getSqlMapClientTemplate().queryForList("luna_reg_email.selectByExample", example);
        return list;
    }

    public LunaRegEmail selectByPrimaryKey(String token) {
        LunaRegEmail _key = new LunaRegEmail();
        _key.setToken(token);
        LunaRegEmail record = (LunaRegEmail) getSqlMapClientTemplate().queryForObject("luna_reg_email.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(LunaRegEmail record, LunaRegEmailCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("luna_reg_email.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteriaWithBLOBs(LunaRegEmail record, LunaRegEmailCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("luna_reg_email.updateByExampleWithBLOBs", parms);
        return rows;
    }

    public int updateByCriteriaWithoutBLOBs(LunaRegEmail record, LunaRegEmailCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("luna_reg_email.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(LunaRegEmail record) {
        int rows = getSqlMapClientTemplate().update("luna_reg_email.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKeyWithBLOBs(LunaRegEmail record) {
        int rows = getSqlMapClientTemplate().update("luna_reg_email.updateByPrimaryKeyWithBLOBs", record);
        return rows;
    }

    public int updateByPrimaryKeyWithoutBLOBs(LunaRegEmail record) {
        int rows = getSqlMapClientTemplate().update("luna_reg_email.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends LunaRegEmailCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, LunaRegEmailCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}