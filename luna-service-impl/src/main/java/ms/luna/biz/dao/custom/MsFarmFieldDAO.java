package ms.luna.biz.dao.custom;

import ms.luna.biz.dao.MsFarmFieldDAOBase;
import ms.luna.biz.dao.custom.model.FarmFieldParameter;
import ms.luna.biz.dao.custom.model.FarmFieldResult;

import java.util.List;

public interface MsFarmFieldDAO extends MsFarmFieldDAOBase {
    List<FarmFieldResult> selectFieldNames(FarmFieldParameter farmFieldParameter);
}