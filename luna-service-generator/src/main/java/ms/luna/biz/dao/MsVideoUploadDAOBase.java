package ms.luna.biz.dao;

import java.util.List;
import ms.luna.biz.dao.model.MsVideoUpload;
import ms.luna.biz.dao.model.MsVideoUploadCriteria;

public interface MsVideoUploadDAOBase {
    int countByCriteria(MsVideoUploadCriteria example);

    int deleteByCriteria(MsVideoUploadCriteria example);

    int deleteByPrimaryKey(String vodFileId);

    void insert(MsVideoUpload record);

    void insertSelective(MsVideoUpload record);

    List<MsVideoUpload> selectByCriteria(MsVideoUploadCriteria example);

    MsVideoUpload selectByPrimaryKey(String vodFileId);

    int updateByCriteriaSelective(MsVideoUpload record, MsVideoUploadCriteria example);

    int updateByCriteria(MsVideoUpload record, MsVideoUploadCriteria example);

    int updateByPrimaryKeySelective(MsVideoUpload record);

    int updateByPrimaryKey(MsVideoUpload record);
}