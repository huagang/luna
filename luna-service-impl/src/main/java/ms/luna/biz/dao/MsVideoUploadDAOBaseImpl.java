package ms.luna.biz.dao;

import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.MsVideoUpload;
import ms.luna.biz.dao.model.MsVideoUploadCriteria;

import java.util.List;

public abstract class MsVideoUploadDAOBaseImpl extends MsBaseDAO implements MsVideoUploadDAOBase {

    public MsVideoUploadDAOBaseImpl() {
        super();
    }

    public int countByCriteria(MsVideoUploadCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("ms_video_upload.countByExample", example);
        return count;
    }

    public int deleteByCriteria(MsVideoUploadCriteria example) {
        int rows = getSqlMapClientTemplate().delete("ms_video_upload.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(String vodFileId) {
        MsVideoUpload _key = new MsVideoUpload();
        _key.setVodFileId(vodFileId);
        int rows = getSqlMapClientTemplate().delete("ms_video_upload.deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(MsVideoUpload record) {
        getSqlMapClientTemplate().insert("ms_video_upload.insert", record);
    }

    public void insertSelective(MsVideoUpload record) {
        getSqlMapClientTemplate().insert("ms_video_upload.insertSelective", record);
    }

    @SuppressWarnings("unchecked")
    public List<MsVideoUpload> selectByCriteria(MsVideoUploadCriteria example) {
        List<MsVideoUpload> list = getSqlMapClientTemplate().queryForList("ms_video_upload.selectByExample", example);
        return list;
    }

    public MsVideoUpload selectByPrimaryKey(String vodFileId) {
        MsVideoUpload _key = new MsVideoUpload();
        _key.setVodFileId(vodFileId);
        MsVideoUpload record = (MsVideoUpload) getSqlMapClientTemplate().queryForObject("ms_video_upload.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(MsVideoUpload record, MsVideoUploadCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_video_upload.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(MsVideoUpload record, MsVideoUploadCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_video_upload.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(MsVideoUpload record) {
        int rows = getSqlMapClientTemplate().update("ms_video_upload.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MsVideoUpload record) {
        int rows = getSqlMapClientTemplate().update("ms_video_upload.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends MsVideoUploadCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, MsVideoUploadCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}