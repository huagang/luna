package ms.luna.schedule.task;

import ms.luna.cache.ModuleMenuCache;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.TimerTask;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-21
 */
public class ModuleMenuTask extends TimerTask {

    private final static Logger logger = Logger.getLogger(ModuleMenuTask.class);

    private ModuleMenuCache moduleMenuCache;

    public ModuleMenuTask(ModuleMenuCache moduleMenuCache) {
        this.moduleMenuCache = moduleMenuCache;
    }

    @Override
    public void run() {
        try {
            moduleMenuCache.refresh();
        } catch (SQLException e) {
            logger.error("Failed to refresh moduleMenuCache", e);
        }
    }

}
