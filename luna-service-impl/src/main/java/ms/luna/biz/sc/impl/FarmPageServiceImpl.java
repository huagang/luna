package ms.luna.biz.sc.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.bl.impl.ManageShowAppBLImpl;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.cons.VbConstant;
import ms.luna.biz.cons.VbConstant.fieldType;
import ms.luna.biz.dao.custom.MsFarmFieldDAO;
import ms.luna.biz.dao.custom.MsFarmPageDAO;
import ms.luna.biz.dao.custom.MsShowAppDAO;
import ms.luna.biz.dao.model.MsFarmField;
import ms.luna.biz.dao.model.MsFarmFieldCriteria;
import ms.luna.biz.dao.model.MsShowApp;
import ms.luna.biz.dao.model.MsShowAppCriteria;
import ms.luna.biz.sc.FarmPageService;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;
import org.apache.commons.lang3.tuple.Pair;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

/**
 * Created by greek on 16/7/25.
 */
@Transactional(rollbackFor = Exception.class)
@Service("farmPageService")
public class FarmPageServiceImpl implements FarmPageService {

    @Autowired
    private MsFarmFieldDAO msFarmFieldDAO;

    @Autowired
    private MsShowAppDAO msShowAppDAO;

    @Autowired
    private MsFarmPageDAO msFarmPageDAO;

    @Override
    public JSONObject initPage(String json) {

        // 创建微景展(ms_show_app)
        // 获取页面字段定义和初始值

        JSONObject jsonObject = JSONObject.parseObject(json);
        try {
            // 创建微景展(ms_show_app),获取app_id
            Pair<Boolean, Pair<Integer, String>> createResult = createAppInfo(jsonObject);
            if (!createResult.getLeft()) {
                return FastJsonUtil.error(createResult.getRight().getLeft(), createResult.getRight().getRight());
            }
            int appId = msShowAppDAO.selectIdByName(FastJsonUtil.getString(jsonObject, "app_name"));

            // 获取页面字段定义和初始值
            JSONObject fields = getFarmFields(null);

            JSONObject data = new JSONObject();
            data.put(MsShowAppDAO.FIELD_APP_ID, appId);
            data.put("fields", fields.getJSONArray("fields"));
            return FastJsonUtil.sucess("成功添加微景展", data);
        } catch (Exception e) {
            MsLogger.error("Failed to create app: " + e.getMessage());

            // 手动添加回滚设置
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "创建微景展失败");
        }

    }

    @Override
    public JSONObject editPage(String json) {
        try {

            JSONObject param = JSONObject.parseObject(json);
            Integer app_id = param.getInteger("app_id");

            // 检查app_id是否创建
            if (isAppIdExist(app_id)) {
                return FastJsonUtil.error(ErrorCode.NOT_FOUND, "app_id not found");
            }

            // 检查数据库是否存在
            if (msFarmPageDAO.isPageExist(app_id)) { // --exist
                msFarmPageDAO.updatePage(param);
            } else { // --not exit
                msFarmPageDAO.insertPage(param);
            }
            return FastJsonUtil.sucess("success");
        } catch (Exception e) {
            MsLogger.debug("Fail to edit page." + e.getMessage());
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Fail to edit page");
        }

    }

    @Override
    public JSONObject delPage(Integer app_id) {
        return null;
    }

    @Override
    public JSONObject getFarmFields() {
        return getFarmFields(null);
    }

    @Override
    public JSONObject loadPage(String json) {
        return null;
    }

    @Override
    public JSONObject previewPage(String json) {
        return null;
    }


    //-----------------------------------------------------------------------------------------------

    /**
     * 获得农+字段定义和数值
     *
     * @param document mongodb信息.当参数为null时返回初始值
     * @return JSONObject
     */
    private JSONObject getFarmFields(Document document) {
        // 先取字段后取初始数值
        JSONObject fieldArray = new JSONObject();
        JSONArray array = new JSONArray();
        List<MsFarmField> fieldLst = msFarmFieldDAO.selectByCriteria(new MsFarmFieldCriteria());
        for (MsFarmField field : fieldLst) {

            // 添加字段定义
            JSONObject field_def = (JSONObject) JSON.toJSON(field);
            String field_name = field.getFieldName();
            String field_type = field.getFieldType();
            String field_limit = field.getFieldLimit();
            String extension_attrs = field.getExtensionAttrs();
            field_def.put("field_limit", JSONObject.parseObject(field_limit)); // field_limit 数据为json格式数据
            field_def.put("extension_attrs", convertExtensionAttrs2Json(extension_attrs, field_type));

            // 添加字段值
            Object field_val = getFieldValFromDoc(document, field_name, field_type);

            JSONObject json = new JSONObject();
            json.put("field_def", field_def);
            json.put("field_val", field_val);
            array.add(json);
        }
        fieldArray.put("fields", array);
        return fieldArray;
    }

    /**
     * 将extension_attrs字段数据根据不同类型转化为指定格式信息.jsonarry, jsonobject, String
     *
     * @param extension_attrs 拓展属性
     * @param type 字段类型
     * @return Object
     */
    private Object convertExtensionAttrs2Json(String extension_attrs, String type) {
        if (fieldType.RADIO_TEXT.equals(type)) { // eg:全景
            return JSONArray.parseArray(extension_attrs);
        }
        if (fieldType.XIANGCUNYEQU.equals(type)) { // eg:乡村野趣
            return JSONArray.parseArray(extension_attrs);
        }
        if (fieldType.CHECKBOX.equals(type)) { // eg:场地设施
            return JSONArray.parseArray(extension_attrs);
        }
        // TODO
        return new JSONArray();
    }

    /**
     * 获取字段值
     *
     * @param document mongodb 信息
     * @param field_name 字段名称
     * @param field_type 字段类型
     * @return Object
     */
    private Object getFieldValFromDoc(Document document, String field_name, String field_type) {
        if (document != null && document.containsKey(field_name)) {
            return document.get(field_name);
        }

        // 初始化或者不含字段信息
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        if (fieldType.RADIO_TEXT.equals(field_type)) {
            return jsonObject;
        } else if (fieldType.TEXT_PIC.equals(field_type)) {
            return jsonArray;
        } else if (fieldType.XIANGCUNYEQU.equals(field_type)) {
            return jsonArray;
        } else if (fieldType.CHECKBOX.equals(field_type)) {
            return jsonArray;
        } else {
            return "";
        }
    }

    /**
     * 检查微景展是否创建
     *
     * @param app_id 微景展ID
     * @return Boolean
     */
    private boolean isAppIdExist(Integer app_id) {
        // TODO
        return true;
    }

    // --------------------------------------------------------------------------------------------
    // copy from ManageShowAppBlImpl.java  --- 后续涉及到基础版,开发版和数据版,都共用创建微景展的代码

    private Pair<Boolean, Pair<Integer, String>> createAppInfo(JSONObject jsonObject) {
        String appName = FastJsonUtil.getString(jsonObject, "app_name");
        int businessId = FastJsonUtil.getInteger(jsonObject, "business_id");
        //用于存储用户微景展资源路径
        String appCode = appName;
        String owner = FastJsonUtil.getString(jsonObject, "owner");

        if(existAppName(appName)) {
            return Pair.of(false, Pair.of(ErrorCode.INVALID_PARAM, "微景展名称已经存在"));
        }
        MsShowApp msShowApp = new MsShowApp();
        msShowApp.setAppName(appName);
        msShowApp.setAppCode(appCode);
        msShowApp.setBusinessId(businessId);
        msShowApp.setOwner(owner);
        msShowApp.setAppStatus(VbConstant.MsShowAppConfig.AppStatus.NOT_AUDIT);
        try {
            msShowAppDAO.insertSelective(msShowApp);
        } catch (Exception ex) {
            MsLogger.error("Failed to create app:" + ex.getMessage());
            return Pair.of(false, Pair.of(ErrorCode.INTERNAL_ERROR, "创建微景展失败"));
        }
        return Pair.of(true, null);
    }

    public boolean existAppName(String appName) {
        // TODO Auto-generated method stub
        MsShowAppCriteria msShowAppCriteria = new MsShowAppCriteria();
        MsShowAppCriteria.Criteria criteria = msShowAppCriteria.createCriteria();
        criteria.andAppNameEqualTo(appName);
        int count = msShowAppDAO.countByCriteria(msShowAppCriteria);
        return count != 0;
    }

}
