package ms.luna.biz.serdes.deal;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-09-14
 */
public class DealSerDesFactory {

    private final static Logger logger = Logger.getLogger(DealSerDesFactory.class);

    private static Map<Integer, String> categoryId2DealSerDes = new HashMap<>();

    static {
        categoryId2DealSerDes.put(0, "");
    }

    public static DealSerDes getDealSerDes(int dealCategoryId) throws Exception {

        String dealSerDesClass = categoryId2DealSerDes.get(dealCategoryId);

        if(StringUtils.isNotBlank(dealSerDesClass)) {
            try {
                return (DealSerDes) Class.forName(dealSerDesClass).newInstance();
            } catch (Exception ex) {
                logger.error("Failed to load DealSerDesClass: " + dealSerDesClass);
                throw ex;
            }
        }

        return new DefaultDealSerDes();
    }

}
