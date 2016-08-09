package ms.luna.biz.dao;

import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.MsRRoleFunction;
import ms.luna.biz.dao.model.MsRRoleFunctionCriteria;
import ms.luna.biz.dao.model.MsRRoleFunctionKey;

import java.util.List;

public abstract class MsRRoleFunctionDAOBaseImpl extends MsBaseDAO implements MsRRoleFunctionDAOBase {

    public MsRRoleFunctionDAOBaseImpl() {
        super();
    }

    public int countByCriteria(MsRRoleFunctionCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("ms_r_role_function.countByExample", example);
        return count;
    }

    public int deleteByCriteria(MsRRoleFunctionCriteria example) {
        int rows = getSqlMapClientTemplate().delete("ms_r_role_function.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(MsRRoleFunctionKey _key) {
        int rows = getSqlMapClientTemplate().delete("ms_r_role_function.deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(MsRRoleFunction record) {
        getSqlMapClientTemplate().insert("ms_r_role_function.insert", record);
    }

    public void insertSelective(MsRRoleFunction record) {
        getSqlMapClientTemplate().insert("ms_r_role_function.insertSelective", record);
    }

    @SuppressWarnings("unchecked")
    public List<MsRRoleFunction> selectByCriteria(MsRRoleFunctionCriteria example) {
        List<MsRRoleFunction> list = getSqlMapClientTemplate().queryForList("ms_r_role_function.selectByExample", example);
        return list;
    }

    public MsRRoleFunction selectByPrimaryKey(MsRRoleFunctionKey _key) {
        MsRRoleFunction record = (MsRRoleFunction) getSqlMapClientTemplate().queryForObject("ms_r_role_function.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(MsRRoleFunction record, MsRRoleFunctionCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_r_role_function.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(MsRRoleFunction record, MsRRoleFunctionCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_r_role_function.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(MsRRoleFunction record) {
        int rows = getSqlMapClientTemplate().update("ms_r_role_function.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MsRRoleFunction record) {
        int rows = getSqlMapClientTemplate().update("ms_r_role_function.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends MsRRoleFunctionCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, MsRRoleFunctionCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}