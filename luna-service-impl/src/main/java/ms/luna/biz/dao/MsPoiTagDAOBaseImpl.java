package ms.luna.biz.dao;

import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.MsPoiTag;
import ms.luna.biz.dao.model.MsPoiTagCriteria;

import java.util.List;

public abstract class MsPoiTagDAOBaseImpl extends MsBaseDAO implements MsPoiTagDAOBase {

    public MsPoiTagDAOBaseImpl() {
        super();
    }

    public int countByCriteria(MsPoiTagCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("ms_poi_tag.countByExample", example);
        return count;
    }

    public int deleteByCriteria(MsPoiTagCriteria example) {
        int rows = getSqlMapClientTemplate().delete("ms_poi_tag.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Integer tagId) {
        MsPoiTag _key = new MsPoiTag();
        _key.setTagId(tagId);
        int rows = getSqlMapClientTemplate().delete("ms_poi_tag.deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(MsPoiTag record) {
        getSqlMapClientTemplate().insert("ms_poi_tag.insert", record);
    }

    public void insertSelective(MsPoiTag record) {
        getSqlMapClientTemplate().insert("ms_poi_tag.insertSelective", record);
    }

    @SuppressWarnings("unchecked")
    public List<MsPoiTag> selectByCriteria(MsPoiTagCriteria example) {
        List<MsPoiTag> list = getSqlMapClientTemplate().queryForList("ms_poi_tag.selectByExample", example);
        return list;
    }

    public MsPoiTag selectByPrimaryKey(Integer tagId) {
        MsPoiTag _key = new MsPoiTag();
        _key.setTagId(tagId);
        MsPoiTag record = (MsPoiTag) getSqlMapClientTemplate().queryForObject("ms_poi_tag.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(MsPoiTag record, MsPoiTagCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_poi_tag.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(MsPoiTag record, MsPoiTagCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_poi_tag.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(MsPoiTag record) {
        int rows = getSqlMapClientTemplate().update("ms_poi_tag.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MsPoiTag record) {
        int rows = getSqlMapClientTemplate().update("ms_poi_tag.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends MsPoiTagCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, MsPoiTagCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}