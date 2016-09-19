package ms.luna.biz.dao;

import java.util.List;
import ms.luna.biz.dao.model.FormField;
import ms.luna.biz.dao.model.FormFieldCriteria;
import ms.luna.biz.dao.model.FormFieldWithBLOBs;

public interface FormFieldDAOBase {
    int countByCriteria(FormFieldCriteria example);

    int deleteByCriteria(FormFieldCriteria example);

    int deleteByPrimaryKey(Integer id);

    Integer insert(FormFieldWithBLOBs record);

    Integer insertSelective(FormFieldWithBLOBs record);

    List<FormFieldWithBLOBs> selectByCriteriaWithBLOBs(FormFieldCriteria example);

    List<FormField> selectByCriteriaWithoutBLOBs(FormFieldCriteria example);

    FormFieldWithBLOBs selectByPrimaryKey(Integer id);

    int updateByCriteriaSelective(FormFieldWithBLOBs record, FormFieldCriteria example);

    int updateByCriteria(FormFieldWithBLOBs record, FormFieldCriteria example);

    int updateByCriteria(FormField record, FormFieldCriteria example);

    int updateByPrimaryKeySelective(FormFieldWithBLOBs record);

    int updateByPrimaryKey(FormFieldWithBLOBs record);

    int updateByPrimaryKey(FormField record);
}