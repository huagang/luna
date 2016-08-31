package ms.luna.biz.dao;

import java.util.List;
import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.LunaBankBranch;
import ms.luna.biz.dao.model.LunaBankBranchCriteria;

public abstract class LunaBankBranchDAOBaseImpl extends MsBaseDAO implements LunaBankBranchDAOBase {

    public LunaBankBranchDAOBaseImpl() {
        super();
    }

    public int countByCriteria(LunaBankBranchCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("luna_bank_branch.countByExample", example);
        return count;
    }

    public int deleteByCriteria(LunaBankBranchCriteria example) {
        int rows = getSqlMapClientTemplate().delete("luna_bank_branch.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(String bnkcode) {
        LunaBankBranch _key = new LunaBankBranch();
        _key.setBnkcode(bnkcode);
        int rows = getSqlMapClientTemplate().delete("luna_bank_branch.deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(LunaBankBranch record) {
        getSqlMapClientTemplate().insert("luna_bank_branch.insert", record);
    }

    public void insertSelective(LunaBankBranch record) {
        getSqlMapClientTemplate().insert("luna_bank_branch.insertSelective", record);
    }

    @SuppressWarnings("unchecked")
    public List<LunaBankBranch> selectByCriteria(LunaBankBranchCriteria example) {
        List<LunaBankBranch> list = getSqlMapClientTemplate().queryForList("luna_bank_branch.selectByExample", example);
        return list;
    }

    public LunaBankBranch selectByPrimaryKey(String bnkcode) {
        LunaBankBranch _key = new LunaBankBranch();
        _key.setBnkcode(bnkcode);
        LunaBankBranch record = (LunaBankBranch) getSqlMapClientTemplate().queryForObject("luna_bank_branch.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(LunaBankBranch record, LunaBankBranchCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("luna_bank_branch.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(LunaBankBranch record, LunaBankBranchCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("luna_bank_branch.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(LunaBankBranch record) {
        int rows = getSqlMapClientTemplate().update("luna_bank_branch.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(LunaBankBranch record) {
        int rows = getSqlMapClientTemplate().update("luna_bank_branch.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends LunaBankBranchCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, LunaBankBranchCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}