package ms.luna.biz.sc.impl;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.bl.ManageColumnBL;
import ms.luna.biz.sc.ManageColumnService;
import ms.luna.biz.util.FastJsonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: chenshangan@microscene.com
 * @Date: 2016-06-14
 */

@Service("manageColumnService")
public class ManageColumnServiceImpl implements ManageColumnService {

    private static final Logger logger = Logger.getLogger(ManageColumnServiceImpl.class);

    @Autowired
    private ManageColumnBL manageColumnBL;

    @Override
    public JSONObject createColumn(String json) {
        try{
            return manageColumnBL.createColumn(json);
        } catch (Exception ex) {
            logger.error("Failed to create column", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "创建栏目失败");
        }
    }

    @Override
    public JSONObject getColumnById(int id) {
        try{
            return manageColumnBL.getColumnById(id);
        } catch (Exception ex) {
            logger.error("Failed to get column", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取栏目失败");
        }
    }

    @Override
    public JSONObject updateColumn(String json) {
        try{
            return manageColumnBL.updateColumn(json);
        } catch (Exception ex) {
            logger.error("Failed to update column", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "更新栏目失败");
        }
    }

    @Override
    public JSONObject deleteColumn(int id) {
        try{
            return manageColumnBL.deleteColumn(id);
        } catch (Exception ex) {
            logger.error("Failed to delete column", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "删除栏目失败");
        }
    }

    @Override
    public JSONObject loadColumn(String json) {
        try{
            return manageColumnBL.loadColumn(json);
        } catch (Exception ex) {
            logger.error("Failed to load column", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "创建栏目列表失败");
        }
    }
}
