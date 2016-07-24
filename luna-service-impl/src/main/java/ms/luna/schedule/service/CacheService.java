package ms.luna.schedule.service;

import ms.luna.cache.ModuleMenuCache;
import ms.luna.schedule.task.ModuleMenuTask;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Random;
import java.util.Timer;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-22
 */
public class CacheService {

    public static final Logger logger = Logger.getLogger(CacheService.class);

    private ClassPathXmlApplicationContext context;
    private Timer timer;
    private Random random;
    private final static int DEFAULT_SCHEDULE_PERIOD = 300000;

    public CacheService(ClassPathXmlApplicationContext context) {
        this.context = context;
        this.timer = new Timer("timer-cache-service", true);
        random = new Random();
    }

    public void start() {
        ModuleMenuCache moduleMenuCache = (ModuleMenuCache)context.getBean("moduleMenuCache");
        ModuleMenuTask moduleMenuTask = new ModuleMenuTask(moduleMenuCache);
        int delay = random.nextInt(60000) + DEFAULT_SCHEDULE_PERIOD;
        timer.schedule(moduleMenuTask, 0, delay);
    }

    public void stop() {

    }
}
