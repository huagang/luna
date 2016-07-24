package ms.luna.biz.dao;

import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.MsFunction;
import ms.luna.biz.dao.model.MsFunctionCriteria;

import java.util.List;

public abstract class MsFunctionDAOBaseImpl extends MsBaseDAO implements MsFunctionDAOBase {

    public MsFunctionDAOBaseImpl() {
        super();
    }

    public int countByCriteria(MsFunctionCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("ms_function.countByExample", example);
        return count;
    }

    public int deleteByCriteria(MsFunctionCriteria example) {
        int rows = getSqlMapClientTemplate().delete("ms_function.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(String msFunctionCode) {
        MsFunction _key = new MsFunction();
        _key.setMsFunctionCode(msFunctionCode);
        int rows = getSqlMapClientTemplate().delete("ms_function.deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(MsFunction record) {
        getSqlMapClientTemplate().insert("ms_function.insert", record);
    }

    public void insertSelective(MsFunction record) {
        getSqlMapClientTemplate().insert("ms_function.insertSelective", record);
    }

    @SuppressWarnings("unchecked")
    public List<MsFunction> selectByCriteria(MsFunctionCriteria example) {
        List<MsFunction> list = getSqlMapClientTemplate().queryForList("ms_function.selectByExample", example);
        return list;
    }

    public MsFunction selectByPrimaryKey(String msFunctionCode) {
        MsFunction _key = new MsFunction();
        _key.setMsFunctionCode(msFunctionCode);
        MsFunction record = (MsFunction) getSqlMapClientTemplate().queryForObject("ms_function.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(MsFunction record, MsFunctionCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_function.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(MsFunction record, MsFunctionCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_function.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(MsFunction record) {
        int rows = getSqlMapClientTemplate().update("ms_function.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MsFunction record) {
        int rows = getSqlMapClientTemplate().update("ms_function.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends MsFunctionCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, MsFunctionCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}