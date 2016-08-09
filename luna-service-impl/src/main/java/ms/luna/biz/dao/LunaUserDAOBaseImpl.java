package ms.luna.biz.dao;

import java.util.List;
import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.LunaUser;
import ms.luna.biz.dao.model.LunaUserCriteria;

public abstract class LunaUserDAOBaseImpl extends MsBaseDAO implements LunaUserDAOBase {

    public LunaUserDAOBaseImpl() {
        super();
    }

    public int countByCriteria(LunaUserCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("luna_user.countByExample", example);
        return count;
    }

    public int deleteByCriteria(LunaUserCriteria example) {
        int rows = getSqlMapClientTemplate().delete("luna_user.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(String uniqueId) {
        LunaUser _key = new LunaUser();
        _key.setUniqueId(uniqueId);
        int rows = getSqlMapClientTemplate().delete("luna_user.deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(LunaUser record) {
        getSqlMapClientTemplate().insert("luna_user.insert", record);
    }

    public void insertSelective(LunaUser record) {
        getSqlMapClientTemplate().insert("luna_user.insertSelective", record);
    }

    @SuppressWarnings("unchecked")
    public List<LunaUser> selectByCriteria(LunaUserCriteria example) {
        List<LunaUser> list = getSqlMapClientTemplate().queryForList("luna_user.selectByExample", example);
        return list;
    }

    public LunaUser selectByPrimaryKey(String uniqueId) {
        LunaUser _key = new LunaUser();
        _key.setUniqueId(uniqueId);
        LunaUser record = (LunaUser) getSqlMapClientTemplate().queryForObject("luna_user.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(LunaUser record, LunaUserCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("luna_user.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(LunaUser record, LunaUserCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("luna_user.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(LunaUser record) {
        int rows = getSqlMapClientTemplate().update("luna_user.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(LunaUser record) {
        int rows = getSqlMapClientTemplate().update("luna_user.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends LunaUserCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, LunaUserCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}