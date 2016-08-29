package ms.luna.biz.sc;

import com.alibaba.fastjson.JSONObject;

/**
 * Created: by greek on 16/8/29.
 */
public interface LunaGoodsService {

    // 获取商品列表
    JSONObject loadGoods(JSONObject param);

    // 创建商品
    JSONObject createGoods(JSONObject param);

    // 编辑商品
    JSONObject updateGoods(JSONObject param);

    // 删除商品
    JSONObject deleteGoods(Integer id);

    // 获取商品信息
    JSONObject getGoodsInfo(Integer id);

    // 检查商品名称
    JSONObject checkGoodsName(String name, Integer id, Integer business_id);
}
