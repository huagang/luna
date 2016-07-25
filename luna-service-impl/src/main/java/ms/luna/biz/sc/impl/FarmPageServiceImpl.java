package ms.luna.biz.sc.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.VbConstant.fieldType;
import ms.luna.biz.dao.custom.MsFarmFieldDAO;
import ms.luna.biz.dao.model.MsFarmField;
import ms.luna.biz.dao.model.MsFarmFieldCriteria;
import ms.luna.biz.sc.FarmPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by greek on 16/7/25.
 */
@Transactional(rollbackFor=Exception.class)
@Service("farmFageService")
public class FarmPageServiceImpl implements FarmPageService {

    @Autowired
    private MsFarmFieldDAO msFarmFieldDAO;

    @Override
    public JSONObject initPage(String json) {
        JSONObject param = JSONObject.parseObject(json);
        String unique_id = param.getString("unique_id");
        String business_id = param.getString("business_id");
        String app_name = param.getString("app_name");

        // TODO


        return null;
    }

    @Override
    public JSONObject editPage(String json) {
        return null;
    }

    @Override
    public JSONObject delPage(Integer app_id) {
        return null;
    }

    @Override
    public JSONObject loadPage(String json) {
        return null;
    }

    @Override
    public JSONObject previewPage(String json) {
        return null;
    }

    // 获得农+字段定义和初始数值
    private JSONObject getFarmFields(){
        // 先取字段后取初始数值
        List<MsFarmField> fieldLst = msFarmFieldDAO.selectByCriteria(new MsFarmFieldCriteria());
        for(MsFarmField field : fieldLst) {
            JSONObject field_def = (JSONObject) JSON.toJSON(field);
            String field_limit = field.getFieldLimit();
            String extension_attrs = field.getExtensionAttrs();
            field_def.put("field_limit", JSONObject.parseObject(field_limit));
            JSONObject extension = convertExtensionAttrs2Json(extension_attrs, field.getFieldType());
            field_def.put("extension_attrs", extension);

        }

    }

    private JSONObject convertExtensionAttrs2Json(String extension_attrs, String type) {
        if(fieldType.RADIO_TEXT.equals(type)) { // eg:全景

        }
    }


}
