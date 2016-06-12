package ms.luna.biz.dao;

import java.util.List;
import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.MsBizModule;
import ms.luna.biz.dao.model.MsBizModuleCriteria;

public abstract class MsBizModuleDAOBaseImpl extends MsBaseDAO implements MsBizModuleDAOBase {

    public MsBizModuleDAOBaseImpl() {
        super();
    }

    public int countByCriteria(MsBizModuleCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("ms_biz_module.countByExample", example);
        return count;
    }

    public int deleteByCriteria(MsBizModuleCriteria example) {
        int rows = getSqlMapClientTemplate().delete("ms_biz_module.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(String bizModuleCode) {
        MsBizModule _key = new MsBizModule();
        _key.setBizModuleCode(bizModuleCode);
        int rows = getSqlMapClientTemplate().delete("ms_biz_module.deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(MsBizModule record) {
        getSqlMapClientTemplate().insert("ms_biz_module.insert", record);
    }

    public void insertSelective(MsBizModule record) {
        getSqlMapClientTemplate().insert("ms_biz_module.insertSelective", record);
    }

    @SuppressWarnings("unchecked")
    public List<MsBizModule> selectByCriteria(MsBizModuleCriteria example) {
        List<MsBizModule> list = getSqlMapClientTemplate().queryForList("ms_biz_module.selectByExample", example);
        return list;
    }

    public MsBizModule selectByPrimaryKey(String bizModuleCode) {
        MsBizModule _key = new MsBizModule();
        _key.setBizModuleCode(bizModuleCode);
        MsBizModule record = (MsBizModule) getSqlMapClientTemplate().queryForObject("ms_biz_module.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(MsBizModule record, MsBizModuleCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_biz_module.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(MsBizModule record, MsBizModuleCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_biz_module.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(MsBizModule record) {
        int rows = getSqlMapClientTemplate().update("ms_biz_module.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MsBizModule record) {
        int rows = getSqlMapClientTemplate().update("ms_biz_module.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends MsBizModuleCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, MsBizModuleCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}