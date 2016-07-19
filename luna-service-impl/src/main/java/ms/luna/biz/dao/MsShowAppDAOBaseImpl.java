package ms.luna.biz.dao;

import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.MsShowApp;
import ms.luna.biz.dao.model.MsShowAppCriteria;

import java.util.List;

public abstract class MsShowAppDAOBaseImpl extends MsBaseDAO implements MsShowAppDAOBase {

    public MsShowAppDAOBaseImpl() {
        super();
    }

    public int countByCriteria(MsShowAppCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("ms_show_app.countByExample", example);
        return count;
    }

    public int deleteByCriteria(MsShowAppCriteria example) {
        int rows = getSqlMapClientTemplate().delete("ms_show_app.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Integer appId) {
        MsShowApp _key = new MsShowApp();
        _key.setAppId(appId);
        int rows = getSqlMapClientTemplate().delete("ms_show_app.deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(MsShowApp record) {
        getSqlMapClientTemplate().insert("ms_show_app.insert", record);
    }

    public void insertSelective(MsShowApp record) {
        getSqlMapClientTemplate().insert("ms_show_app.insertSelective", record);
    }

    @SuppressWarnings("unchecked")
    public List<MsShowApp> selectByCriteria(MsShowAppCriteria example) {
        List<MsShowApp> list = getSqlMapClientTemplate().queryForList("ms_show_app.selectByExample", example);
        return list;
    }

    public MsShowApp selectByPrimaryKey(Integer appId) {
        MsShowApp _key = new MsShowApp();
        _key.setAppId(appId);
        MsShowApp record = (MsShowApp) getSqlMapClientTemplate().queryForObject("ms_show_app.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(MsShowApp record, MsShowAppCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_show_app.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(MsShowApp record, MsShowAppCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_show_app.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(MsShowApp record) {
        int rows = getSqlMapClientTemplate().update("ms_show_app.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MsShowApp record) {
        int rows = getSqlMapClientTemplate().update("ms_show_app.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends MsShowAppCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, MsShowAppCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}