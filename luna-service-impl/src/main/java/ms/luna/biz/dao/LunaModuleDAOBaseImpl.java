package ms.luna.biz.dao;

import java.util.List;
import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.LunaModule;
import ms.luna.biz.dao.model.LunaModuleCriteria;

public abstract class LunaModuleDAOBaseImpl extends MsBaseDAO implements LunaModuleDAOBase {

    public LunaModuleDAOBaseImpl() {
        super();
    }

    public int countByCriteria(LunaModuleCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("luna_module.countByExample", example);
        return count;
    }

    public int deleteByCriteria(LunaModuleCriteria example) {
        int rows = getSqlMapClientTemplate().delete("luna_module.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Integer id) {
        LunaModule _key = new LunaModule();
        _key.setId(id);
        int rows = getSqlMapClientTemplate().delete("luna_module.deleteByPrimaryKey", _key);
        return rows;
    }

    public Integer insert(LunaModule record) {
        Object newKey = getSqlMapClientTemplate().insert("luna_module.insert", record);
        return (Integer) newKey;
    }

    public Integer insertSelective(LunaModule record) {
        Object newKey = getSqlMapClientTemplate().insert("luna_module.insertSelective", record);
        return (Integer) newKey;
    }

    @SuppressWarnings("unchecked")
    public List<LunaModule> selectByCriteriaWithBLOBs(LunaModuleCriteria example) {
        List<LunaModule> list = getSqlMapClientTemplate().queryForList("luna_module.selectByExampleWithBLOBs", example);
        return list;
    }

    @SuppressWarnings("unchecked")
    public List<LunaModule> selectByCriteriaWithoutBLOBs(LunaModuleCriteria example) {
        List<LunaModule> list = getSqlMapClientTemplate().queryForList("luna_module.selectByExample", example);
        return list;
    }

    public LunaModule selectByPrimaryKey(Integer id) {
        LunaModule _key = new LunaModule();
        _key.setId(id);
        LunaModule record = (LunaModule) getSqlMapClientTemplate().queryForObject("luna_module.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(LunaModule record, LunaModuleCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("luna_module.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteriaWithBLOBs(LunaModule record, LunaModuleCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("luna_module.updateByExampleWithBLOBs", parms);
        return rows;
    }

    public int updateByCriteriaWithoutBLOBs(LunaModule record, LunaModuleCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("luna_module.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(LunaModule record) {
        int rows = getSqlMapClientTemplate().update("luna_module.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKeyWithBLOBs(LunaModule record) {
        int rows = getSqlMapClientTemplate().update("luna_module.updateByPrimaryKeyWithBLOBs", record);
        return rows;
    }

    public int updateByPrimaryKeyWithoutBLOBs(LunaModule record) {
        int rows = getSqlMapClientTemplate().update("luna_module.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends LunaModuleCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, LunaModuleCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}