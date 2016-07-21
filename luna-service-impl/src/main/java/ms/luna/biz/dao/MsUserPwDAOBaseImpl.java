package ms.luna.biz.dao;

import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.MsUserPw;
import ms.luna.biz.dao.model.MsUserPwCriteria;

import java.util.List;

public abstract class MsUserPwDAOBaseImpl extends MsBaseDAO implements MsUserPwDAOBase {

    public MsUserPwDAOBaseImpl() {
        super();
    }

    public int countByCriteria(MsUserPwCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("ms_user_pw.countByExample", example);
        return count;
    }

    public int deleteByCriteria(MsUserPwCriteria example) {
        int rows = getSqlMapClientTemplate().delete("ms_user_pw.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(String lunaName) {
        MsUserPw _key = new MsUserPw();
        _key.setLunaName(lunaName);
        int rows = getSqlMapClientTemplate().delete("ms_user_pw.deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(MsUserPw record) {
        getSqlMapClientTemplate().insert("ms_user_pw.insert", record);
    }

    public void insertSelective(MsUserPw record) {
        getSqlMapClientTemplate().insert("ms_user_pw.insertSelective", record);
    }

    @SuppressWarnings("unchecked")
    public List<MsUserPw> selectByCriteria(MsUserPwCriteria example) {
        List<MsUserPw> list = getSqlMapClientTemplate().queryForList("ms_user_pw.selectByExample", example);
        return list;
    }

    public MsUserPw selectByPrimaryKey(String lunaName) {
        MsUserPw _key = new MsUserPw();
        _key.setLunaName(lunaName);
        MsUserPw record = (MsUserPw) getSqlMapClientTemplate().queryForObject("ms_user_pw.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(MsUserPw record, MsUserPwCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_user_pw.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(MsUserPw record, MsUserPwCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_user_pw.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(MsUserPw record) {
        int rows = getSqlMapClientTemplate().update("ms_user_pw.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MsUserPw record) {
        int rows = getSqlMapClientTemplate().update("ms_user_pw.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends MsUserPwCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, MsUserPwCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}