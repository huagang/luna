package ms.luna.biz.dao.custom;

import ms.luna.biz.dao.MsColumnDAOBaseImpl;
import ms.luna.biz.dao.custom.model.MsColumnParameter;
import ms.luna.biz.dao.custom.model.MsColumnResult;
import ms.luna.biz.dao.model.MsColumn;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("msColumnDAO")
public class MsColumnDAOImpl extends MsColumnDAOBaseImpl implements MsColumnDAO {
    @Override
    public List<MsColumnResult> selectColumnWithFilter(MsColumnParameter parameter) {
        List<MsColumnResult> results = getSqlMapClientTemplate().queryForList("ms_column.selectColumnWithFilter", parameter);
        return results;
    }
}