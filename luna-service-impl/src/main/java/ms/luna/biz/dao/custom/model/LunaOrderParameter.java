package ms.luna.biz.dao.custom.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: greek@visualbusiness.com
 * @Date: 2016-09-14
 */
public class LunaOrderParameter {
    @JSONField(name = "offset")
    private int offset;
    @JSONField(name = "limit")
    private int limit;
    @JSONField(name = "range")
    private boolean range = true;

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public boolean isRange() {
        return range;
    }

    public void setRange(boolean range) {
        this.range = range;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @JSONField(name = "keyword")
    private String keyword;
    @JSONField(name = "merchant_id")
    private String merchant_id;

}
