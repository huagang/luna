package ms.luna.biz.dao;

import java.util.List;
import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.RoomOtherInfo;
import ms.luna.biz.dao.model.RoomOtherInfoCriteria;
import ms.luna.biz.dao.model.RoomOtherInfoKey;

public abstract class RoomOtherInfoDAOBaseImpl extends MsBaseDAO implements RoomOtherInfoDAOBase {

    public RoomOtherInfoDAOBaseImpl() {
        super();
    }

    public int countByCriteria(RoomOtherInfoCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("room_other_info.countByExample", example);
        return count;
    }

    public int deleteByCriteria(RoomOtherInfoCriteria example) {
        int rows = getSqlMapClientTemplate().delete("room_other_info.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(RoomOtherInfoKey _key) {
        int rows = getSqlMapClientTemplate().delete("room_other_info.deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(RoomOtherInfo record) {
        getSqlMapClientTemplate().insert("room_other_info.insert", record);
    }

    public void insertSelective(RoomOtherInfo record) {
        getSqlMapClientTemplate().insert("room_other_info.insertSelective", record);
    }

    @SuppressWarnings("unchecked")
    public List<RoomOtherInfo> selectByCriteria(RoomOtherInfoCriteria example) {
        List<RoomOtherInfo> list = getSqlMapClientTemplate().queryForList("room_other_info.selectByExample", example);
        return list;
    }

    public RoomOtherInfo selectByPrimaryKey(RoomOtherInfoKey _key) {
        RoomOtherInfo record = (RoomOtherInfo) getSqlMapClientTemplate().queryForObject("room_other_info.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(RoomOtherInfo record, RoomOtherInfoCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("room_other_info.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(RoomOtherInfo record, RoomOtherInfoCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("room_other_info.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(RoomOtherInfo record) {
        int rows = getSqlMapClientTemplate().update("room_other_info.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(RoomOtherInfo record) {
        int rows = getSqlMapClientTemplate().update("room_other_info.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends RoomOtherInfoCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, RoomOtherInfoCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}