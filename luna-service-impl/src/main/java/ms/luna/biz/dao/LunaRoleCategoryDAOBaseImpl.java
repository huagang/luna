package ms.luna.biz.dao;

import java.util.List;
import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.LunaRoleCategory;
import ms.luna.biz.dao.model.LunaRoleCategoryCriteria;

public abstract class LunaRoleCategoryDAOBaseImpl extends MsBaseDAO implements LunaRoleCategoryDAOBase {

    public LunaRoleCategoryDAOBaseImpl() {
        super();
    }

    public int countByCriteria(LunaRoleCategoryCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("luna_role_category.countByExample", example);
        return count;
    }

    public int deleteByCriteria(LunaRoleCategoryCriteria example) {
        int rows = getSqlMapClientTemplate().delete("luna_role_category.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Integer id) {
        LunaRoleCategory _key = new LunaRoleCategory();
        _key.setId(id);
        int rows = getSqlMapClientTemplate().delete("luna_role_category.deleteByPrimaryKey", _key);
        return rows;
    }

    public Integer insert(LunaRoleCategory record) {
        Object newKey = getSqlMapClientTemplate().insert("luna_role_category.insert", record);
        return (Integer) newKey;
    }

    public Integer insertSelective(LunaRoleCategory record) {
        Object newKey = getSqlMapClientTemplate().insert("luna_role_category.insertSelective", record);
        return (Integer) newKey;
    }

    @SuppressWarnings("unchecked")
    public List<LunaRoleCategory> selectByCriteriaWithBLOBs(LunaRoleCategoryCriteria example) {
        List<LunaRoleCategory> list = getSqlMapClientTemplate().queryForList("luna_role_category.selectByExampleWithBLOBs", example);
        return list;
    }

    @SuppressWarnings("unchecked")
    public List<LunaRoleCategory> selectByCriteriaWithoutBLOBs(LunaRoleCategoryCriteria example) {
        List<LunaRoleCategory> list = getSqlMapClientTemplate().queryForList("luna_role_category.selectByExample", example);
        return list;
    }

    public LunaRoleCategory selectByPrimaryKey(Integer id) {
        LunaRoleCategory _key = new LunaRoleCategory();
        _key.setId(id);
        LunaRoleCategory record = (LunaRoleCategory) getSqlMapClientTemplate().queryForObject("luna_role_category.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(LunaRoleCategory record, LunaRoleCategoryCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("luna_role_category.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteriaWithBLOBs(LunaRoleCategory record, LunaRoleCategoryCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("luna_role_category.updateByExampleWithBLOBs", parms);
        return rows;
    }

    public int updateByCriteriaWithoutBLOBs(LunaRoleCategory record, LunaRoleCategoryCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("luna_role_category.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(LunaRoleCategory record) {
        int rows = getSqlMapClientTemplate().update("luna_role_category.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKeyWithBLOBs(LunaRoleCategory record) {
        int rows = getSqlMapClientTemplate().update("luna_role_category.updateByPrimaryKeyWithBLOBs", record);
        return rows;
    }

    public int updateByPrimaryKeyWithoutBLOBs(LunaRoleCategory record) {
        int rows = getSqlMapClientTemplate().update("luna_role_category.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends LunaRoleCategoryCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, LunaRoleCategoryCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}