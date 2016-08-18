package ms.luna.biz.dao;

import java.util.List;
import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.MsOperationLog;
import ms.luna.biz.dao.model.MsOperationLogCriteria;

public abstract class MsOperationLogDAOBaseImpl extends MsBaseDAO implements MsOperationLogDAOBase {

    public MsOperationLogDAOBaseImpl() {
        super();
    }

    public int countByCriteria(MsOperationLogCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("ms_operation_log.countByExample", example);
        return count;
    }

    public int deleteByCriteria(MsOperationLogCriteria example) {
        int rows = getSqlMapClientTemplate().delete("ms_operation_log.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Integer id) {
        MsOperationLog _key = new MsOperationLog();
        _key.setId(id);
        int rows = getSqlMapClientTemplate().delete("ms_operation_log.deleteByPrimaryKey", _key);
        return rows;
    }

    public Integer insert(MsOperationLog record) {
        Object newKey = getSqlMapClientTemplate().insert("ms_operation_log.insert", record);
        return (Integer) newKey;
    }

    public Integer insertSelective(MsOperationLog record) {
        Object newKey = getSqlMapClientTemplate().insert("ms_operation_log.insertSelective", record);
        return (Integer) newKey;
    }

    @SuppressWarnings("unchecked")
    public List<MsOperationLog> selectByCriteria(MsOperationLogCriteria example) {
        List<MsOperationLog> list = getSqlMapClientTemplate().queryForList("ms_operation_log.selectByExample", example);
        return list;
    }

    public MsOperationLog selectByPrimaryKey(Integer id) {
        MsOperationLog _key = new MsOperationLog();
        _key.setId(id);
        MsOperationLog record = (MsOperationLog) getSqlMapClientTemplate().queryForObject("ms_operation_log.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(MsOperationLog record, MsOperationLogCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_operation_log.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(MsOperationLog record, MsOperationLogCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_operation_log.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(MsOperationLog record) {
        int rows = getSqlMapClientTemplate().update("ms_operation_log.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MsOperationLog record) {
        int rows = getSqlMapClientTemplate().update("ms_operation_log.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends MsOperationLogCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, MsOperationLogCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}