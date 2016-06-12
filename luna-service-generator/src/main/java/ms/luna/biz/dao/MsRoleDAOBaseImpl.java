package ms.luna.biz.dao;

import java.util.List;
import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.MsRole;
import ms.luna.biz.dao.model.MsRoleCriteria;

public abstract class MsRoleDAOBaseImpl extends MsBaseDAO implements MsRoleDAOBase {

    public MsRoleDAOBaseImpl() {
        super();
    }

    public int countByCriteria(MsRoleCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("ms_role.countByExample", example);
        return count;
    }

    public int deleteByCriteria(MsRoleCriteria example) {
        int rows = getSqlMapClientTemplate().delete("ms_role.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(String msRoleCode) {
        MsRole _key = new MsRole();
        _key.setMsRoleCode(msRoleCode);
        int rows = getSqlMapClientTemplate().delete("ms_role.deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(MsRole record) {
        getSqlMapClientTemplate().insert("ms_role.insert", record);
    }

    public void insertSelective(MsRole record) {
        getSqlMapClientTemplate().insert("ms_role.insertSelective", record);
    }

    @SuppressWarnings("unchecked")
    public List<MsRole> selectByCriteria(MsRoleCriteria example) {
        List<MsRole> list = getSqlMapClientTemplate().queryForList("ms_role.selectByExample", example);
        return list;
    }

    public MsRole selectByPrimaryKey(String msRoleCode) {
        MsRole _key = new MsRole();
        _key.setMsRoleCode(msRoleCode);
        MsRole record = (MsRole) getSqlMapClientTemplate().queryForObject("ms_role.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(MsRole record, MsRoleCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_role.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(MsRole record, MsRoleCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_role.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(MsRole record) {
        int rows = getSqlMapClientTemplate().update("ms_role.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MsRole record) {
        int rows = getSqlMapClientTemplate().update("ms_role.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends MsRoleCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, MsRoleCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}