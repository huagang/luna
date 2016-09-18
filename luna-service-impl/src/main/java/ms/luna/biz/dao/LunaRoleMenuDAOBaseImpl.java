package ms.luna.biz.dao;

import java.util.List;
import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.LunaRoleMenu;
import ms.luna.biz.dao.model.LunaRoleMenuCriteria;
import ms.luna.biz.dao.model.LunaRoleMenuKey;

public abstract class LunaRoleMenuDAOBaseImpl extends MsBaseDAO implements LunaRoleMenuDAOBase {

    public LunaRoleMenuDAOBaseImpl() {
        super();
    }

    public int countByCriteria(LunaRoleMenuCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("luna_role_menu.countByExample", example);
        return count;
    }

    public int deleteByCriteria(LunaRoleMenuCriteria example) {
        int rows = getSqlMapClientTemplate().delete("luna_role_menu.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(LunaRoleMenuKey _key) {
        int rows = getSqlMapClientTemplate().delete("luna_role_menu.deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(LunaRoleMenu record) {
        getSqlMapClientTemplate().insert("luna_role_menu.insert", record);
    }

    public void insertSelective(LunaRoleMenu record) {
        getSqlMapClientTemplate().insert("luna_role_menu.insertSelective", record);
    }

    @SuppressWarnings("unchecked")
    public List<LunaRoleMenu> selectByCriteria(LunaRoleMenuCriteria example) {
        List<LunaRoleMenu> list = getSqlMapClientTemplate().queryForList("luna_role_menu.selectByExample", example);
        return list;
    }

    public LunaRoleMenu selectByPrimaryKey(LunaRoleMenuKey _key) {
        LunaRoleMenu record = (LunaRoleMenu) getSqlMapClientTemplate().queryForObject("luna_role_menu.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(LunaRoleMenu record, LunaRoleMenuCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("luna_role_menu.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(LunaRoleMenu record, LunaRoleMenuCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("luna_role_menu.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(LunaRoleMenu record) {
        int rows = getSqlMapClientTemplate().update("luna_role_menu.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(LunaRoleMenu record) {
        int rows = getSqlMapClientTemplate().update("luna_role_menu.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends LunaRoleMenuCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, LunaRoleMenuCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}