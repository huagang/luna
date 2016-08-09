package ms.luna.biz.dao;

import java.util.List;
import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.MsShowPageShare;
import ms.luna.biz.dao.model.MsShowPageShareCriteria;

public abstract class MsShowPageShareDAOBaseImpl extends MsBaseDAO implements MsShowPageShareDAOBase {

    public MsShowPageShareDAOBaseImpl() {
        super();
    }

    public int countByCriteria(MsShowPageShareCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("ms_show_page_share.countByExample", example);
        return count;
    }

    public int deleteByCriteria(MsShowPageShareCriteria example) {
        int rows = getSqlMapClientTemplate().delete("ms_show_page_share.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Integer id) {
        MsShowPageShare _key = new MsShowPageShare();
        _key.setId(id);
        int rows = getSqlMapClientTemplate().delete("ms_show_page_share.deleteByPrimaryKey", _key);
        return rows;
    }

    public Integer insert(MsShowPageShare record) {
        Object newKey = getSqlMapClientTemplate().insert("ms_show_page_share.insert", record);
        return (Integer) newKey;
    }

    public Integer insertSelective(MsShowPageShare record) {
        Object newKey = getSqlMapClientTemplate().insert("ms_show_page_share.insertSelective", record);
        return (Integer) newKey;
    }

    @SuppressWarnings("unchecked")
    public List<MsShowPageShare> selectByCriteria(MsShowPageShareCriteria example) {
        List<MsShowPageShare> list = getSqlMapClientTemplate().queryForList("ms_show_page_share.selectByExample", example);
        return list;
    }

    public MsShowPageShare selectByPrimaryKey(Integer id) {
        MsShowPageShare _key = new MsShowPageShare();
        _key.setId(id);
        MsShowPageShare record = (MsShowPageShare) getSqlMapClientTemplate().queryForObject("ms_show_page_share.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(MsShowPageShare record, MsShowPageShareCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_show_page_share.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(MsShowPageShare record, MsShowPageShareCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_show_page_share.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(MsShowPageShare record) {
        int rows = getSqlMapClientTemplate().update("ms_show_page_share.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MsShowPageShare record) {
        int rows = getSqlMapClientTemplate().update("ms_show_page_share.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends MsShowPageShareCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, MsShowPageShareCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}