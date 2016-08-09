package ms.luna.biz.dao.custom;

import ms.luna.biz.dao.MsFarmFieldDAOBaseImpl;
import ms.luna.biz.dao.custom.model.FarmFieldParameter;
import ms.luna.biz.dao.custom.model.FarmFieldResult;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("msFarmFieldDAO")
public class MsFarmFieldDAOImpl extends MsFarmFieldDAOBaseImpl implements MsFarmFieldDAO {
    @Override
    public List<FarmFieldResult> selectFieldNames(FarmFieldParameter farmFieldParameter) {
        return getSqlMapClientTemplate().queryForList("ms_farm_field.searchFieldNames", farmFieldParameter);
    }
}