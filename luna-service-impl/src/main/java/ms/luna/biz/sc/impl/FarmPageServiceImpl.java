package ms.luna.biz.sc.impl;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.sc.FarmPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by greek on 16/7/25.
 */
@Transactional(rollbackFor=Exception.class)
@Service("farmFageService")
public class FarmPageServiceImpl implements FarmPageService {

    @Autowired
    private Ms

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
        // 思路:先取字段后取初始数值



    }


}
