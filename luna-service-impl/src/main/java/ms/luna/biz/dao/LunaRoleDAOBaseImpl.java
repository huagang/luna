package ms.luna.biz.dao;

import java.util.List;
import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.LunaRole;
import ms.luna.biz.dao.model.LunaRoleCriteria;

public abstract class LunaRoleDAOBaseImpl extends MsBaseDAO implements LunaRoleDAOBase {

    public LunaRoleDAOBaseImpl() {
        super();
    }

    public int countByCriteria(LunaRoleCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("luna_role.countByExample", example);
        return count;
    }

    public int deleteByCriteria(LunaRoleCriteria example) {
        int rows = getSqlMapClientTemplate().delete("luna_role.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Integer id) {
        LunaRole _key = new LunaRole();
        _key.setId(id);
        int rows = getSqlMapClientTemplate().delete("luna_role.deleteByPrimaryKey", _key);
        return rows;
    }

    public Integer insert(LunaRole record) {
        Object newKey = getSqlMapClientTemplate().insert("luna_role.insert", record);
        return (Integer) newKey;
    }

    public Integer insertSelective(LunaRole record) {
        Object newKey = getSqlMapClientTemplate().insert("luna_role.insertSelective", record);
        return (Integer) newKey;
    }

    @SuppressWarnings("unchecked")
    public List<LunaRole> selectByCriteria(LunaRoleCriteria example) {
        List<LunaRole> list = getSqlMapClientTemplate().queryForList("luna_role.selectByExample", example);
        return list;
    }

    public LunaRole selectByPrimaryKey(Integer id) {
        LunaRole _key = new LunaRole();
        _key.setId(id);
        LunaRole record = (LunaRole) getSqlMapClientTemplate().queryForObject("luna_role.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(LunaRole record, LunaRoleCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("luna_role.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(LunaRole record, LunaRoleCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("luna_role.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(LunaRole record) {
        int rows = getSqlMapClientTemplate().update("luna_role.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(LunaRole record) {
        int rows = getSqlMapClientTemplate().update("luna_role.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends LunaRoleCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, LunaRoleCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}