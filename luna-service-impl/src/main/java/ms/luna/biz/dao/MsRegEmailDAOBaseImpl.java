package ms.luna.biz.dao;

import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.MsRegEmail;
import ms.luna.biz.dao.model.MsRegEmailCriteria;

import java.util.List;

public abstract class MsRegEmailDAOBaseImpl extends MsBaseDAO implements MsRegEmailDAOBase {

    public MsRegEmailDAOBaseImpl() {
        super();
    }

    public int countByCriteria(MsRegEmailCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("ms_reg_email.countByExample", example);
        return count;
    }

    public int deleteByCriteria(MsRegEmailCriteria example) {
        int rows = getSqlMapClientTemplate().delete("ms_reg_email.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(String token) {
        MsRegEmail _key = new MsRegEmail();
        _key.setToken(token);
        int rows = getSqlMapClientTemplate().delete("ms_reg_email.deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(MsRegEmail record) {
        getSqlMapClientTemplate().insert("ms_reg_email.insert", record);
    }

    public void insertSelective(MsRegEmail record) {
        getSqlMapClientTemplate().insert("ms_reg_email.insertSelective", record);
    }

    @SuppressWarnings("unchecked")
    public List<MsRegEmail> selectByCriteria(MsRegEmailCriteria example) {
        List<MsRegEmail> list = getSqlMapClientTemplate().queryForList("ms_reg_email.selectByExample", example);
        return list;
    }

    public MsRegEmail selectByPrimaryKey(String token) {
        MsRegEmail _key = new MsRegEmail();
        _key.setToken(token);
        MsRegEmail record = (MsRegEmail) getSqlMapClientTemplate().queryForObject("ms_reg_email.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(MsRegEmail record, MsRegEmailCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_reg_email.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(MsRegEmail record, MsRegEmailCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_reg_email.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(MsRegEmail record) {
        int rows = getSqlMapClientTemplate().update("ms_reg_email.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MsRegEmail record) {
        int rows = getSqlMapClientTemplate().update("ms_reg_email.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends MsRegEmailCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, MsRegEmailCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}