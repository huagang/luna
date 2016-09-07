package ms.luna.biz.table;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-22
 */
public class LunaMenuTable {

    public static final String TABLE_NAME = "luna_menu";

    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_CODE = "code";
    public static final String FIELD_MODULE_ID = "module_id";
    public static final String FIELD_URL = "url";
    public static final String FIELD_AUTH = "auth";
    public static final String FIELD_DISPLAY_ORDER = "display_order";
    public static final String FIELD_UPDATE_TIME = "update_time";

    //衍生字段,根据url是否为空判断
    public static final String FIELD_IS_OUTER = "is_outer";
    public static final String FIELD_OUTER_URL = "out_url";
    public static final String FEILD_VISIBLE = "visible";
}
