package ms.luna.biz.dao;

import java.util.List;
import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.MsColumn;
import ms.luna.biz.dao.model.MsColumnCriteria;

public abstract class MsColumnDAOBaseImpl extends MsBaseDAO implements MsColumnDAOBase {

    public MsColumnDAOBaseImpl() {
        super();
    }

    public int countByCriteria(MsColumnCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("ms_column.countByExample", example);
        return count;
    }

    public int deleteByCriteria(MsColumnCriteria example) {
        int rows = getSqlMapClientTemplate().delete("ms_column.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Integer id) {
        MsColumn _key = new MsColumn();
        _key.setId(id);
        int rows = getSqlMapClientTemplate().delete("ms_column.deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(MsColumn record) {
        getSqlMapClientTemplate().insert("ms_column.insert", record);
    }

    public void insertSelective(MsColumn record) {
        getSqlMapClientTemplate().insert("ms_column.insertSelective", record);
    }

    @SuppressWarnings("unchecked")
    public List<MsColumn> selectByCriteria(MsColumnCriteria example) {
        List<MsColumn> list = getSqlMapClientTemplate().queryForList("ms_column.selectByExample", example);
        return list;
    }

    public MsColumn selectByPrimaryKey(Integer id) {
        MsColumn _key = new MsColumn();
        _key.setId(id);
        MsColumn record = (MsColumn) getSqlMapClientTemplate().queryForObject("ms_column.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(MsColumn record, MsColumnCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_column.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(MsColumn record, MsColumnCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_column.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(MsColumn record) {
        int rows = getSqlMapClientTemplate().update("ms_column.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MsColumn record) {
        int rows = getSqlMapClientTemplate().update("ms_column.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends MsColumnCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, MsColumnCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}