package ms.luna.biz.dao;

import java.util.List;
import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.MsRUserRole;
import ms.luna.biz.dao.model.MsRUserRoleCriteria;
import ms.luna.biz.dao.model.MsRUserRoleKey;

public abstract class MsRUserRoleDAOBaseImpl extends MsBaseDAO implements MsRUserRoleDAOBase {

    public MsRUserRoleDAOBaseImpl() {
        super();
    }

    public int countByCriteria(MsRUserRoleCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("ms_r_user_role.countByExample", example);
        return count;
    }

    public int deleteByCriteria(MsRUserRoleCriteria example) {
        int rows = getSqlMapClientTemplate().delete("ms_r_user_role.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(MsRUserRoleKey _key) {
        int rows = getSqlMapClientTemplate().delete("ms_r_user_role.deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(MsRUserRole record) {
        getSqlMapClientTemplate().insert("ms_r_user_role.insert", record);
    }

    public void insertSelective(MsRUserRole record) {
        getSqlMapClientTemplate().insert("ms_r_user_role.insertSelective", record);
    }

    @SuppressWarnings("unchecked")
    public List<MsRUserRole> selectByCriteria(MsRUserRoleCriteria example) {
        List<MsRUserRole> list = getSqlMapClientTemplate().queryForList("ms_r_user_role.selectByExample", example);
        return list;
    }

    public MsRUserRole selectByPrimaryKey(MsRUserRoleKey _key) {
        MsRUserRole record = (MsRUserRole) getSqlMapClientTemplate().queryForObject("ms_r_user_role.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(MsRUserRole record, MsRUserRoleCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_r_user_role.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(MsRUserRole record, MsRUserRoleCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_r_user_role.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(MsRUserRole record) {
        int rows = getSqlMapClientTemplate().update("ms_r_user_role.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MsRUserRole record) {
        int rows = getSqlMapClientTemplate().update("ms_r_user_role.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends MsRUserRoleCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, MsRUserRoleCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}