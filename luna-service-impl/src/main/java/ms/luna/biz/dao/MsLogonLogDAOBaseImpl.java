package ms.luna.biz.dao;

import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.MsLogonLog;
import ms.luna.biz.dao.model.MsLogonLogCriteria;

import java.util.List;

public abstract class MsLogonLogDAOBaseImpl extends MsBaseDAO implements MsLogonLogDAOBase {

    public MsLogonLogDAOBaseImpl() {
        super();
    }

    public int countByCriteria(MsLogonLogCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("ms_logon_log.countByExample", example);
        return count;
    }

    public int deleteByCriteria(MsLogonLogCriteria example) {
        int rows = getSqlMapClientTemplate().delete("ms_logon_log.deleteByExample", example);
        return rows;
    }

    public void insert(MsLogonLog record) {
        getSqlMapClientTemplate().insert("ms_logon_log.insert", record);
    }

    public void insertSelective(MsLogonLog record) {
        getSqlMapClientTemplate().insert("ms_logon_log.insertSelective", record);
    }

    @SuppressWarnings("unchecked")
    public List<MsLogonLog> selectByCriteria(MsLogonLogCriteria example) {
        List<MsLogonLog> list = getSqlMapClientTemplate().queryForList("ms_logon_log.selectByExample", example);
        return list;
    }

    public int updateByCriteriaSelective(MsLogonLog record, MsLogonLogCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_logon_log.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(MsLogonLog record, MsLogonLogCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_logon_log.updateByExample", parms);
        return rows;
    }

    protected static class UpdateByExampleParms extends MsLogonLogCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, MsLogonLogCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}