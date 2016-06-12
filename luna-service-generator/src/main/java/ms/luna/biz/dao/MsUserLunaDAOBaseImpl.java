package ms.luna.biz.dao;

import java.util.List;
import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.MsUserLuna;
import ms.luna.biz.dao.model.MsUserLunaCriteria;

public abstract class MsUserLunaDAOBaseImpl extends MsBaseDAO implements MsUserLunaDAOBase {

    public MsUserLunaDAOBaseImpl() {
        super();
    }

    public int countByCriteria(MsUserLunaCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("ms_user_luna.countByExample", example);
        return count;
    }

    public int deleteByCriteria(MsUserLunaCriteria example) {
        int rows = getSqlMapClientTemplate().delete("ms_user_luna.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(String uniqueId) {
        MsUserLuna _key = new MsUserLuna();
        _key.setUniqueId(uniqueId);
        int rows = getSqlMapClientTemplate().delete("ms_user_luna.deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(MsUserLuna record) {
        getSqlMapClientTemplate().insert("ms_user_luna.insert", record);
    }

    public void insertSelective(MsUserLuna record) {
        getSqlMapClientTemplate().insert("ms_user_luna.insertSelective", record);
    }

    @SuppressWarnings("unchecked")
    public List<MsUserLuna> selectByCriteria(MsUserLunaCriteria example) {
        List<MsUserLuna> list = getSqlMapClientTemplate().queryForList("ms_user_luna.selectByExample", example);
        return list;
    }

    public MsUserLuna selectByPrimaryKey(String uniqueId) {
        MsUserLuna _key = new MsUserLuna();
        _key.setUniqueId(uniqueId);
        MsUserLuna record = (MsUserLuna) getSqlMapClientTemplate().queryForObject("ms_user_luna.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(MsUserLuna record, MsUserLunaCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_user_luna.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(MsUserLuna record, MsUserLunaCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_user_luna.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(MsUserLuna record) {
        int rows = getSqlMapClientTemplate().update("ms_user_luna.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MsUserLuna record) {
        int rows = getSqlMapClientTemplate().update("ms_user_luna.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends MsUserLunaCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, MsUserLunaCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}