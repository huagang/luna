package ms.luna.biz.dao;

import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.MsUserThirdLogin;
import ms.luna.biz.dao.model.MsUserThirdLoginCriteria;
import ms.luna.biz.dao.model.MsUserThirdLoginKey;

import java.util.List;

public abstract class MsUserThirdLoginDAOBaseImpl extends MsBaseDAO implements MsUserThirdLoginDAOBase {

    public MsUserThirdLoginDAOBaseImpl() {
        super();
    }

    public int countByCriteria(MsUserThirdLoginCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("ms_user_third_login.countByExample", example);
        return count;
    }

    public int deleteByCriteria(MsUserThirdLoginCriteria example) {
        int rows = getSqlMapClientTemplate().delete("ms_user_third_login.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(MsUserThirdLoginKey _key) {
        int rows = getSqlMapClientTemplate().delete("ms_user_third_login.deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(MsUserThirdLogin record) {
        getSqlMapClientTemplate().insert("ms_user_third_login.insert", record);
    }

    public void insertSelective(MsUserThirdLogin record) {
        getSqlMapClientTemplate().insert("ms_user_third_login.insertSelective", record);
    }

    @SuppressWarnings("unchecked")
    public List<MsUserThirdLogin> selectByCriteria(MsUserThirdLoginCriteria example) {
        List<MsUserThirdLogin> list = getSqlMapClientTemplate().queryForList("ms_user_third_login.selectByExample", example);
        return list;
    }

    public MsUserThirdLogin selectByPrimaryKey(MsUserThirdLoginKey _key) {
        MsUserThirdLogin record = (MsUserThirdLogin) getSqlMapClientTemplate().queryForObject("ms_user_third_login.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(MsUserThirdLogin record, MsUserThirdLoginCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_user_third_login.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(MsUserThirdLogin record, MsUserThirdLoginCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_user_third_login.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(MsUserThirdLogin record) {
        int rows = getSqlMapClientTemplate().update("ms_user_third_login.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MsUserThirdLogin record) {
        int rows = getSqlMapClientTemplate().update("ms_user_third_login.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends MsUserThirdLoginCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, MsUserThirdLoginCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}