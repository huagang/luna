package ms.luna.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.util.MsLogger;

import javax.servlet.http.HttpServletRequest;

/**
 * Created: by greek on 16/7/28.
 */
public class FarmCommon {
    private FarmCommon (){}

    private static FarmCommon instance = new FarmCommon();

    public static FarmCommon getInStance() {
        return instance;
    }

//    /**
//     * 返回字段数据
//     *
//     * @param request HTTP请求
//     * @param fields 字段定义数组
//     * @return JSONObject
//     */
//    public JSONObject param2json(HttpServletRequest request, JSONArray fields) {
//        checkFields(request, fields);
//        JSONObject json = convertParams2Json(request, fields);
//        MsLogger.debug(json.toString());
//        return json;
//    }

    /**
     * 检查农+页字段数值
     *
     * @param fieldsVal 字段值
     * @param fieldsDef 字段定义
     */
    // 目前认为没有必要做.不同于权限,商户注册,农+页只是一些展示的信息,而且前端已经做了检查
    public void checkFieldsVal(JSONObject fieldsVal, JSONArray fieldsDef) {

    }
}
