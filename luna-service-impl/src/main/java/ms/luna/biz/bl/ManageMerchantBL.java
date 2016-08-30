package ms.luna.biz.bl;

import com.alibaba.fastjson.JSONObject;

/**
 * @author Greek
 * @version 1.0
 * @date create time：2016年3月23日 下午8:01:55
 */
public interface ManageMerchantBL {

    /**
     * 创建商户信息
     *
     * @param json
     * @return
     */
    JSONObject createMerchant(String json);

    /**
     * @param json
     * @return
     */
    JSONObject getMerchantTradeStatus(String json);

    /**
     * @param json
     * @return
     */
    JSONObject changeMerchantTradeStatus(String json);

    /**
     * 根据具体ID获取单个商户信息
     *
     * @param json
     * @return
     */
    JSONObject loadMerchantById(String json);

    /**
     * 根据  部分名称  获取多个商户信息
     *
     * @param json
     * @return
     */
    JSONObject loadMerchants(String json);

    /**
     * 删除商户信息
     *
     * @param json
     * @return
     */
    JSONObject deleteMerchantById(String json);

    /**
     * 统计商户数量
     *
     * @param merchantNm
     * @return
     */
    Integer countMerchantNm(String merchantNm);

    /**
     * 更新商户信息
     *
     * @param json
     * @return
     */
    JSONObject updateMerchant(String json);

    /**
     * 检测业务员是否存在
     *
     * @param json
     * @return
     */
    JSONObject isSalesmanNmExit(String json);

    /**
     * 检测商户名称是否存在（新建商户）
     *
     * @param json
     * @return
     */
    JSONObject isAddedMerchantNmEist(String json);

    /**
     * 检测商户名称是否存在（编辑商户）
     *
     * @param json
     * @return
     */
    JSONObject isEditedMerchantNmEist(String json);

    /**
     * 关闭商户
     *
     * @param json
     * @return
     */
    JSONObject closeMerchantById(String json);

    /**
     * 开启商户
     *
     * @param json
     * @return
     */
    JSONObject openMerchantById(String json);

    /**
     * 商户签署直通车协议
     *
     * @param jsonObject
     * @return
     */
    JSONObject signAgreement(JSONObject jsonObject);


}
