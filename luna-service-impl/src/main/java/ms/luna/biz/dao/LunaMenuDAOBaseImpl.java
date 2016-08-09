package ms.luna.biz.dao;

import java.util.List;
import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.LunaMenu;
import ms.luna.biz.dao.model.LunaMenuCriteria;

public abstract class LunaMenuDAOBaseImpl extends MsBaseDAO implements LunaMenuDAOBase {

    public LunaMenuDAOBaseImpl() {
        super();
    }

    public int countByCriteria(LunaMenuCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("luna_menu.countByExample", example);
        return count;
    }

    public int deleteByCriteria(LunaMenuCriteria example) {
        int rows = getSqlMapClientTemplate().delete("luna_menu.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Integer id) {
        LunaMenu _key = new LunaMenu();
        _key.setId(id);
        int rows = getSqlMapClientTemplate().delete("luna_menu.deleteByPrimaryKey", _key);
        return rows;
    }

    public Integer insert(LunaMenu record) {
        Object newKey = getSqlMapClientTemplate().insert("luna_menu.insert", record);
        return (Integer) newKey;
    }

    public Integer insertSelective(LunaMenu record) {
        Object newKey = getSqlMapClientTemplate().insert("luna_menu.insertSelective", record);
        return (Integer) newKey;
    }

    @SuppressWarnings("unchecked")
    public List<LunaMenu> selectByCriteria(LunaMenuCriteria example) {
        List<LunaMenu> list = getSqlMapClientTemplate().queryForList("luna_menu.selectByExample", example);
        return list;
    }

    public LunaMenu selectByPrimaryKey(Integer id) {
        LunaMenu _key = new LunaMenu();
        _key.setId(id);
        LunaMenu record = (LunaMenu) getSqlMapClientTemplate().queryForObject("luna_menu.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(LunaMenu record, LunaMenuCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("luna_menu.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(LunaMenu record, LunaMenuCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("luna_menu.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(LunaMenu record) {
        int rows = getSqlMapClientTemplate().update("luna_menu.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(LunaMenu record) {
        int rows = getSqlMapClientTemplate().update("luna_menu.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends LunaMenuCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, LunaMenuCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}