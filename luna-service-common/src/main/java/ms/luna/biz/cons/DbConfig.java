package ms.luna.biz.cons;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-08-07
 */
public class DbConfig {

    public final static int ROOT_ROLE_ID = 1;
    public final static int BUSINESS_ALL = 0;
    public final static int MERCHANT_ADMIN_ROLE_ID = 6;//商家服务管理员id
    public final static int MERCHANT_OPERATOR_ROLE_ID = 7;//商家服务运营员id
    public final static int MERCHANT_CATAGORY_ID = 3;

    public static Set<Integer> MERCHANT_ROLE_ID_SET = Sets.newHashSet(MERCHANT_ADMIN_ROLE_ID, MERCHANT_OPERATOR_ROLE_ID);

    public static Set<Integer> INVISIBLE_MENU_TRADE_ON = Sets.newHashSet(23);
    public static Set<Integer> INVISIBLE_MENU_TRADE_OFF = Sets.newHashSet(10,11,12);

    public static int GOODS_CATEGORY_ROOM = 1;

}
