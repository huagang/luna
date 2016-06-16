package ms.luna.biz.dao.custom;

import ms.luna.biz.dao.MsColumnDAOBase;
import ms.luna.biz.dao.custom.model.MsColumnParameter;
import ms.luna.biz.dao.custom.model.MsColumnResult;

import java.util.List;

public interface MsColumnDAO extends MsColumnDAOBase {

    public List<MsColumnResult> selectColumnWithFilter(MsColumnParameter parameter);
}