package ms.luna.cache;

import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.custom.LunaMenuDAO;
import ms.luna.biz.dao.custom.LunaModuleDAO;
import ms.luna.biz.dao.model.LunaMenu;
import ms.luna.biz.dao.model.LunaMenuCriteria;
import ms.luna.biz.dao.model.LunaModule;
import ms.luna.biz.dao.model.LunaModuleCriteria;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-21
 */

@Repository("moduleMenuCache")
public class ModuleMenuCache extends MsBaseDAO {

    private final static Logger logger = Logger.getLogger(ModuleMenuCache.class);

    @Autowired
    private LunaModuleDAO lunaModuleDAO;
    @Autowired
    private LunaMenuDAO lunaMenuDAO;

    private volatile List<LunaModule> lunaModuleList;
    private volatile List<LunaMenu> lunaMenuList;
    private Map<Integer, LunaModule> moduleMap;
    private Map<Integer, LunaMenu> menuMap;
    private Map<Integer, Integer> menu2Module;
    //swap new copy when refresh
    private volatile Map<Integer, List<Integer>> module2Menu;

    @PostConstruct
    public void init() {
        moduleMap = new ConcurrentHashMap<>();
        menuMap = new ConcurrentHashMap<>();
        menu2Module = new ConcurrentHashMap<>();
        module2Menu = new ConcurrentHashMap<>();
    }

    public void refresh() throws SQLException {

        LunaModuleCriteria lunaModuleCriteria = new LunaModuleCriteria();
        lunaModuleCriteria.setOrderByClause("display_order asc");
        List<LunaModule> newLunaModuleList = lunaModuleDAO.selectByCriteria(lunaModuleCriteria);

        if(newLunaModuleList != null) {
            for(LunaModule lunaModule : newLunaModuleList) {
                moduleMap.put(lunaModule.getId(), lunaModule);
            }
        }
        lunaModuleList = newLunaModuleList;
        LunaMenuCriteria lunaMenuCriteria = new LunaMenuCriteria();
        lunaMenuCriteria.setOrderByClause("display_order asc");
        List<LunaMenu> newLunaMenuList = lunaMenuDAO.selectByCriteria(new LunaMenuCriteria());
        Map<Integer, List<Integer>> newModule2Menu = new ConcurrentHashMap<>();
        if(newLunaMenuList != null) {
            for(LunaMenu lunaMenu : newLunaMenuList) {
                int menuId = lunaMenu.getId();
                int moduleId = lunaMenu.getModuleId();
                menuMap.put(menuId, lunaMenu);
                menu2Module.put(menuId, moduleId);
                List<Integer> menuIdList = newModule2Menu.get(moduleId);
                if(menuIdList == null) {
                    menuIdList = new ArrayList<>();
                    newModule2Menu.put(moduleId, menuIdList);
                }
                menuIdList.add(menuId);
            }
        }

        lunaMenuList = newLunaMenuList;
        module2Menu = newModule2Menu;
        logger.info("ModuleMenuCache refreshed");
    }

    /**
     *  order by display_order
     */
    public List<Pair<LunaModule, List<LunaMenu>>> getAllModuleAndMenu() {

        List<Pair<LunaModule, List<LunaMenu>>> moduleAndMenuInfoList = new ArrayList<>();

        for(LunaModule lunaModule : lunaModuleList) {
            List<LunaMenu> crtLunaMenuList = new ArrayList<>();
            int moduleId = lunaModule.getId();
            List<Integer> menuIdList = module2Menu.get(moduleId);
            for(int menuId : menuIdList) {
                crtLunaMenuList.add(menuMap.get(menuId));
            }
            Pair<LunaModule, List<LunaMenu>> pair = Pair.of(lunaModule, crtLunaMenuList);
            moduleAndMenuInfoList.add(pair);
        }

        return moduleAndMenuInfoList;
    }

    public List<LunaModule> getAllModule() {
        return Collections.unmodifiableList(lunaModuleList);
    }

    public List<LunaMenu> getAllMenu() {
        return Collections.unmodifiableList(lunaMenuList);
    }

    public LunaModule getModule(int moduleId) {
        return moduleMap.get(moduleId);
    }

    public LunaMenu getMenu(int menuId) {
        return menuMap.get(menuId);
    }

    public List<LunaModule> getModuleByMenuIds(Collection<Integer> menuIds) {
        List<LunaModule> retModules = new ArrayList<>();
        if(menuIds == null) {
            return retModules;
        }
        Set<Integer> moduleSet = new HashSet<>();
        for(int menuId: menuIds) {
            Integer moduleId = menu2Module.get(menuId);
            moduleSet.add(moduleId);
        }
        // lunaModuleList is well sorted
        for(LunaModule lunaModule : lunaModuleList) {
            if(moduleSet.contains(lunaModule.getId())) {
                retModules.add(lunaModule);
            }
        }
        return retModules;
    }

    public List<Pair<LunaModule, List<LunaMenu>>> getModuleAndMenuByMenuIds(Collection<Integer> menuIds) {

        List<Pair<LunaModule, List<LunaMenu>>> moduleAndMenuInfoList = new ArrayList<>();
        if(menuIds == null) {
            return moduleAndMenuInfoList;
        }
        Map<Integer, List<LunaMenu>> moduleId2Menu = new HashMap<>();

        Set<Integer> menuIdSet = new HashSet<>(menuIds.size());
        menuIdSet.addAll(menuIds);
        // 全局有序保障了模块内相对也有序
        for(LunaMenu lunaMenu : lunaMenuList) {
            if(menuIdSet.contains(lunaMenu.getId())) {
                Integer moduleId = lunaMenu.getModuleId();
                List<LunaMenu> lunaMenus = moduleId2Menu.get(moduleId);
                if(lunaMenus == null) {
                    lunaMenus = new ArrayList<>();
                    moduleId2Menu.put(moduleId, lunaMenus);
                }
                lunaMenus.add(lunaMenu);

            }
        }

        Set<Integer> moduleIdSet = moduleId2Menu.keySet();
        for(LunaModule lunaModule : lunaModuleList) {
            Integer moduleId = lunaModule.getId();
            if(moduleIdSet.contains(moduleId)) {
                Pair<LunaModule, List<LunaMenu>> pair = Pair.of(lunaModule, moduleId2Menu.get(moduleId));
                moduleAndMenuInfoList.add(pair);
            }
        }

        return moduleAndMenuInfoList;

    }



}
