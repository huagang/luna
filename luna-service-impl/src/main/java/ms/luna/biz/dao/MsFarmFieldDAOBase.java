package ms.luna.biz.dao;

import java.util.List;
import ms.luna.biz.dao.model.MsFarmField;
import ms.luna.biz.dao.model.MsFarmFieldCriteria;

public interface MsFarmFieldDAOBase {
    int countByCriteria(MsFarmFieldCriteria example);

    int deleteByCriteria(MsFarmFieldCriteria example);

    void insert(MsFarmField record);

    void insertSelective(MsFarmField record);

    List<MsFarmField> selectByCriteria(MsFarmFieldCriteria example);

    int updateByCriteriaSelective(MsFarmField record, MsFarmFieldCriteria example);

    int updateByCriteria(MsFarmField record, MsFarmFieldCriteria example);
}