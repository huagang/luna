package ms.luna.biz.sc.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.cons.VbConstant.FieldTypes;
import ms.luna.biz.dao.custom.*;
import ms.luna.biz.dao.custom.model.FarmFieldParameter;
import ms.luna.biz.dao.custom.model.FarmFieldResult;
import ms.luna.biz.dao.model.*;
import ms.luna.biz.sc.FarmPageService;
import ms.luna.biz.sc.PoiApiService;
import ms.luna.biz.table.MsFarmFieldTable;
import ms.luna.biz.table.MsShowAppTable;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;
import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

/**
 * Created: by greek on 16/7/25.
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

    @Autowired
    private PoiApiService poiApiService;

    private static int[] divider = new int[]{1, 3, 5, 7, 8, 9};// 组件间的分割线插入位置.2,4表示组件相对顺序display_order.
    // 目前农+页页面固定,以后涉及到配置页面的时候,需要另外的配置信息

    @Override
    public JSONObject getPageDefAndInfo(Integer appId) {

        // 创建微景展(ms_show_app)
        // 获取页面字段定义和初始值
        try {
            // 检查app_id是否存在
            if (!this.isAppIdExist(appId)) {
                return FastJsonUtil.error(ErrorCode.NOT_FOUND, "app_id not found");
            }
            // 获取mongo数据
            Document document = msFarmPageDAO.selectPageByAppId(appId);

            // 获取页面字段定义和数值
            JSONObject fields = getFarmFields(document);

            JSONObject data = new JSONObject();
            data.put(MsShowAppTable.FIELD_APP_ID, appId);
            data.put(MsFarmFieldTable.FIELDS, fields.getJSONArray(MsFarmFieldTable.FIELDS));
            data.put(MsFarmFieldTable.DIVIDER, divider);
            MsLogger.debug("Init app page. " + data.toString());
            return FastJsonUtil.sucess("success", data);
        } catch (Exception e) {
            MsLogger.error("Failed to create app: " + e.getMessage());
            // 手动添加回滚设置
            // TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Fail to create app");
        }

    }

    @Override
    public JSONObject updatePage(String json, Integer appId, String lunaName) {
        try {
            JSONObject param = JSONObject.parseObject(json);
            // 检查app_id是否创建
            if (!this.isAppIdExist(appId)) {
                return FastJsonUtil.error(ErrorCode.NOT_FOUND, "app_id not found");
            }

            Document document = convertJson2DocumentWithFieldDef(param, getFarmFieldNames());
            document.put(MsShowAppTable.FIELD_APP_ID, appId);

            // 检查mongo数据是否存在
            if (this.isPageExist(appId)) { // --exist
                msFarmPageDAO.updatePage(document, appId, lunaName);
            } else { // --not exist
                msFarmPageDAO.insertPage(document, lunaName);
            }
            return FastJsonUtil.sucess("success");
        } catch (Exception e) {
            MsLogger.debug("Fail to edit page." + e.getMessage());
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Fail to edit page");
        }

    }

    @Override
    public JSONObject delPage(Integer app_id) {
        try {
            msShowAppDAO.deleteByPrimaryKey(app_id);
        } catch (Exception e) {
            MsLogger.error("Fail to delete app", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Fail to delete app");
        }
        msFarmPageDAO.deletePage(app_id);
        return FastJsonUtil.sucess("success");
    }

    @Override
    public JSONObject getFarmFields() {
        try {
            JSONObject fields = getFarmFields(null);
            return FastJsonUtil.sucess("success", fields);
        } catch (Exception e) {
            MsLogger.error("Failed to get farm fields def", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to get farm fields def");
        }

    }

    @Override
    public JSONObject previewPage(Integer appId) {
        return null;
    }

    @Override
    public JSONObject publishPage(String json) {
        return null;
    }

    @Override
    public JSONObject getPageInfo(Integer appId) {
        try {

            // 检查app_id是否存在
            if (!this.isAppIdExist(appId)) {
                return FastJsonUtil.error(ErrorCode.NOT_FOUND, "app_id not found");
            }
            // 获取mongo数据
            Document document = msFarmPageDAO.selectPageByAppId(appId);

            // 获取农+页面数据.考虑到后续新的字段的添加和更改,需要传入需求字段信息
            List<String> fields = getFarmFieldNames();
            JSONObject json = msFarmPageDAO.getPageInfo(document, fields);

            // poi_info 字段, facility字段只存取了id,需要额外读取信息
            // 获取POI详细信息
            if (fields.contains(MsFarmPageDAO.POI_INFO) && !StringUtils.isBlank(document.getString(MsFarmPageDAO.POI_INFO))) {
                String poiId = document.getString(MsFarmPageDAO.POI_INFO);
                JSONObject param = new JSONObject();
                param.put("poi_id", poiId);
                param.put("lang", "zh");
                JSONObject poiInfo = poiApiService.getPoiInfoById(param.toString());
                if (!"0".equals(poiInfo.getString("code"))) {
                    return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, poiInfo.getString("msg"));
                }
                json.put(MsFarmPageDAO.POI_INFO, poiInfo.getJSONObject("data").get("zh"));
            }

            // 获取场地设施信息
            if (fields.contains(MsFarmPageDAO.FACILITY)) {
                // 先拿options数据,再选择
                MsFarmFieldCriteria msFarmFieldCriteria = new MsFarmFieldCriteria();
                MsFarmFieldCriteria.Criteria criteria = msFarmFieldCriteria.createCriteria();
                criteria.andNameEqualTo(MsFarmPageDAO.FACILITY);
                List<MsFarmField> list = msFarmFieldDAO.selectByCriteria(msFarmFieldCriteria);
                if (list != null && list.size() != 0) {
                    JSONArray array1 = JSONArray.parseArray(list.get(0).getOptions());// 场地设施总集合

                    JSONArray array2 = FastJsonUtil.parse2Array(document.get(MsFarmPageDAO.FACILITY));
//                    Set<String> facilityIds = getFacilityValues(array2);// 农家页场地设施id集合
                    Set<Integer> facilityIds = getFacilityValues(array2);// 农家页场地设施id集合

                    JSONArray res = new JSONArray();
                    for (int i = 0; i < array1.size(); i++) {
//                        String value = array1.getJSONObject(i).getString("value");
                        Integer value = array1.getJSONObject(i).getInteger("value");
                        if (facilityIds.contains(value)) {
                            res.add(array1.getJSONObject(i));
                        }
                    }
                    json.put(MsFarmPageDAO.FACILITY, res);
                }
            }
            return FastJsonUtil.sucess("success", json);
        } catch (Exception e) {
            MsLogger.error("Failed to get page info", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to get page info");
        }
    }

    //-----------------------------------------------------------------------------------------------

    /**
     * 获取场地设施id集合(mongo)
     *
     * @param array
     * @return
     */
    Set<Integer> getFacilityValues(JSONArray array) {
        Set<Integer> list = new HashSet<>();
        for (int i = 0; i < array.size(); i++) {
            list.add(array.getInteger(i));
        }
        return list;
    }

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
            String default_value = field.getDefaultValue();
            field_def.put(MsFarmFieldTable.FIELD_LIMITS, JSONObject.parseObject(field_limit)); // field_limit 数据为json格式数据
            field_def.put(MsFarmFieldTable.FIELD_PLACEHOLDER, JSONObject.parseObject(field_placeholder));
            field_def.put(MsFarmFieldTable.FIELD_OPTIONS, convertOptionsString2Json(extension_attrs, field_type));

            // 添加字段值
            Object field_val = getFieldValFromDoc(document, field_name, field_type, default_value);

            JSONObject json = new JSONObject();
            json.put(MsFarmFieldTable.FIELD_DEFINITION, field_def);
            json.put(MsFarmFieldTable.FIELD_VALUE, field_val);
            array.add(json);
        }
        fieldArray.put(MsFarmFieldTable.FIELDS, array);
        return fieldArray;
    }

    /**
     * 获取农+字段名字
     */
    private List<String> getFarmFieldNames() {
        List<FarmFieldResult> farmFieldResults = msFarmFieldDAO.selectFieldNames(new FarmFieldParameter());
        List<String> list = new ArrayList<>();
        for (FarmFieldResult farmFieldResult : farmFieldResults) {
            list.add(farmFieldResult.getName());

        }
        return list;
    }

    /**
     * 将extension_attrs字段数据根据不同类型转化为指定格式信息.jsonarry, jsonobject, String
     *
     * @param options 拓展属性
     * @param type    字段类型
     * @return Object
     */
    private Object convertOptionsString2Json(String options, String type) {
        if (FieldTypes.RADIO_TEXT.equals(type)) { // eg:全景
            return JSONArray.parseArray(options);
        }
        if (FieldTypes.COUNTRY_ENJOYMENT.equals(type)) { // eg:乡村野趣
            return JSONArray.parseArray(options);
        }
        if (FieldTypes.CHECKBOX.equals(type)) { // eg:场地设施
            return JSONArray.parseArray(options);
        }
        // TODO 不同类型需要不同处理方式
        return new JSONArray();
    }

//    // 将[[{"1","a"},{"2","b"}],[{"1","a"},{"2","b"}]]这种形式变为
//    // [[{"key":"1","value":"a"},{"key":"2","value":"b"}],[{"key":"1","value":"a"},{"key":"2","value":"b"}]]
//    private JSONArray convertOptions2JSONArray(JSONArray array, String key, String value) {
//        for(int i = 0; i< array.size(); i++) {
//            JSONArray subArray = array.getJSONArray(0);
//            subArray = modifyJSONArray(subArray, key, value);
//            array.set(i, subArray);
//        }
//        return array;
//    }
//
//    // 将{"1":"a"}变为{"key":"1", "value":"a"}
//    private JSONArray modifyJSONArray(JSONArray jsonArray, String key, String value) {
//        JSONArray result = new JSONArray();
//        for(int i = 0; i<jsonArray.size(); i++) {
//            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//            Set<String> sets = jsonObject1.keySet();
//            JSONObject jsonObject2 = new JSONObject();
//            for(String set : sets) {
//                jsonObject2.put(key, set);
//                jsonObject2.put(value, jsonObject1.getString(set));
//            }
//            result.add(jsonObject2);
//        }
//        return result;
//
//    }


    /**
     * 获取字段值
     *
     * @param document   mongodb 信息
     * @param field_name 字段名称
     * @param field_type 字段类型
     * @param default_value 默认值
     * @return Object
     */
    private Object getFieldValFromDoc(Document document, String field_name, String field_type, String default_value) {
        if (document != null && document.containsKey(field_name)) {
            return document.get(field_name);
        }
        // 初始化或者不含字段信息
        if (FieldTypes.RADIO_TEXT.equals(field_type))
            return JSONObject.parseObject(default_value);
        if (FieldTypes.TEXT_PIC.equals(field_type))
            return JSONObject.parseArray(default_value);
        if (FieldTypes.COUNTRY_ENJOYMENT.equals(field_type))
            return JSONObject.parseArray(default_value);
        if (FieldTypes.CHECKBOX.equals(field_type))
            return JSONObject.parseArray(default_value);
        return "";
    }

    /**
     * 检查微景展是否创建
     *
     * @param app_id 微景展ID
     * @return Boolean
     */
    private boolean isAppIdExist(Integer app_id) {
        return msShowAppDAO.selectByPrimaryKey(app_id) != null;
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

    /**
     * 将前端数据转化为Document类型数据
     *
     * @param param  前端返回数据
     * @param fields 字段定义
     * @return Document
     */
    private Document convertJson2DocumentWithFieldDef(JSONObject param, List<String> fields) {
        Document doc = new Document();
        for (String field : fields) {
            doc.put(field, param.containsKey(field) ? param.get(field) : "");
        }
        return doc;
    }


}
