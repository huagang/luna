package ms.biz.common;

import ms.luna.biz.dao.model.LunaMenu;
import ms.luna.biz.dao.model.LunaModule;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-24
 */
public class MenuHelper {

    public static String formatMenuUrl(LunaModule module, LunaMenu menu) {

        return String.format("/%s/%s", module.getCode(), menu.getCode());

    }
}
