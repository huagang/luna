package ms.luna.biz.sc.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.cons.VbConstant;
import ms.luna.biz.cons.VbConstant.fieldType;
import ms.luna.biz.dao.custom.MsFarmFieldDAO;
import ms.luna.biz.dao.custom.MsFarmPageDAO;
import ms.luna.biz.dao.custom.MsShowAppDAO;
import ms.luna.biz.dao.custom.MsShowPageDAO;
import ms.luna.biz.dao.model.MsFarmField;
import ms.luna.biz.dao.model.MsFarmFieldCriteria;
import ms.luna.biz.dao.model.MsShowApp;
import ms.luna.biz.dao.model.MsShowAppCriteria;
import ms.luna.biz.sc.FarmPageService;
import ms.luna.biz.table.MsFarmFieldTable;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;
import org.apache.commons.lang3.tuple.Pair;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;
import java.util.Set;

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

    private static int[] divider = new int[]{1,3,5,7,8,9};// 组件间的分割线插入位置.2,4表示组件相对顺序display_order.
    // 目前农+页页面固定,以后涉及到配置页面的时候,需要另外的配置信息

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
            data.put(MsFarmFieldTable.FIELDS, fields.getJSONArray(MsFarmFieldTable.FIELDS));
            data.put(MsFarmFieldTable.DIVIDER, divider);
            MsLogger.debug("Init app page. " + data.toString());
            return FastJsonUtil.sucess("success", data);
        } catch (Exception e) {
            MsLogger.error("Failed to create app: " + e.getMessage());
            // 手动添加回滚设置
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Fail to create app");
        }

    }

    @Override
    public JSONObject editPage(String json) {
        try {
            JSONObject param = JSONObject.parseObject(json);
            Integer app_id = param.getInteger("app_id");

            // 检查app_id是否创建
            if (!this.isAppIdExist(app_id)) {
                return FastJsonUtil.error(ErrorCode.NOT_FOUND, "app_id not found");
            }

            // 检查mongo数据是否存在
            if (this.isPageExist(app_id)) { // --exist
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
    public JSONObject loadPage(Integer app_id) {
        try{
            // 检查app_id是否存在
            if (!this.isAppIdExist(app_id)) {
                return FastJsonUtil.error(ErrorCode.NOT_FOUND, "app_id not found");
            }
            // 获取mongo数据
            Document document = msFarmPageDAO.selectPageByAppId(app_id);

            // 获取页面字段定义和数值
            JSONObject fields = getFarmFields(document);

            JSONObject data = new JSONObject();
            data.put(MsShowPageDAO.FIELD_APP_ID, app_id);
            data.put(MsFarmFieldTable.FIELDS, fields.getJSONArray("fields"));
            MsLogger.debug("load page info." + data.toString());
            return FastJsonUtil.sucess("success", data);
        } catch (Exception e) {
            MsLogger.error("Fail to load page. " + e.getMessage());
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Fail to load page");
        }

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
            String field_name = field.getName();
            String field_type = field.getType();
            String field_limit = field.getLimits();
            String field_placeholder = field.getPlaceholder();
            String extension_attrs = field.getOptions();
            field_def.put(MsFarmFieldTable.FIELD_LIMITS, JSONObject.parseObject(field_limit)); // field_limit 数据为json格式数据
            field_def.put(MsFarmFieldTable.FIELD_PLACEHOLDER, JSONObject.parseObject(field_placeholder));
            field_def.put(MsFarmFieldTable.FIELD_OPTIONS, convertOptionsString2Json(extension_attrs, field_type));

            // 添加字段值
            Object field_val = getFieldValFromDoc(document, field_name, field_type);

            JSONObject json = new JSONObject();
            json.put(MsFarmFieldTable.FIELD_DEFINITION, field_def);
            json.put(MsFarmFieldTable.FIELD_VALUE, field_val);
            array.add(json);
        }
        fieldArray.put(MsFarmFieldTable.FIELDS, array);
        return fieldArray;
    }

    /**
     * 将extension_attrs字段数据根据不同类型转化为指定格式信息.jsonarry, jsonobject, String
     *
     * @param options 拓展属性
     * @param type 字段类型
     * @return Object
     */
    private Object convertOptionsString2Json(String options, String type) {
        // 把{"1":"a"}这种形式变为{"value":"1", "label":"a"}

        if (fieldType.RADIO_TEXT.equals(type)) { // eg:全景
//            return this.convertOptions2JSONArray(JSONArray.parseArray(options), "value", "label");
            return JSONArray.parseArray(options);
        }
        if (fieldType.COUNTRY_ENJOYMENT.equals(type)) { // eg:乡村野趣
//            return this.modifyJSONArray(JSONArray.parseArray(options), "name", "pic");
            return JSONArray.parseArray(options);
        }
        if (fieldType.CHECKBOX.equals(type)) { // eg:场地设施
//            return this.modifyJSONArray(JSONArray.parseArray(options), "value", "label");
            JSONArray.parseArray(options);
        }
        // TODO
        return new JSONArray();
    }

    // 将[[{"1","a"},{"2","b"}],[{"1","a"},{"2","b"}]]这种形式变为
    // [[{"key":"1","value":"a"},{"key":"2","value":"b"}],[{"key":"1","value":"a"},{"key":"2","value":"b"}]]
    private JSONArray convertOptions2JSONArray(JSONArray array, String key, String value) {
        for(int i = 0; i< array.size(); i++) {
            JSONArray subArray = array.getJSONArray(0);
            subArray = modifyJSONArray(subArray, key, value);
            array.set(i, subArray);
        }
        return array;
    }

    // 将{"1":"a"}变为{"key":"1", "value":"a"}
    private JSONArray modifyJSONArray(JSONArray jsonArray, String key, String value) {
        JSONArray result = new JSONArray();
        for(int i = 0; i<jsonArray.size(); i++) {
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            Set<String> sets = jsonObject1.keySet();
            JSONObject jsonObject2 = new JSONObject();
            for(String set : sets) {
                jsonObject2.put(key, set);
                jsonObject2.put(value, jsonObject1.getString(set));
            }
            result.add(jsonObject2);
        }
        return result;

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
        } else if (fieldType.COUNTRY_ENJOYMENT.equals(field_type)) {
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
        return msShowAppDAO.selectByPrimaryKey(app_id) == null;
    }

    /**
     * 检查mongoDB微景展ID是否存在
     *
     * @param app_id 微景展ID
     * @return boolean
     */
    private boolean isPageExist(Integer app_id) {
        Document doc = new Document();
        doc.append(MsShowPageDAO.FIELD_APP_ID, app_id);

        Document result = msFarmPageDAO.selectPageByAppId(app_id);
        return result != null;
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
