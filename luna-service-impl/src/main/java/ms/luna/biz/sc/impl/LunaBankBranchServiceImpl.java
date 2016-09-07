/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package ms.luna.biz.sc.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.dao.custom.LunaBankBranchDAO;
import ms.luna.biz.dao.model.LunaBankBranch;
import ms.luna.biz.dao.model.LunaBankBranchCriteria;
import ms.luna.biz.sc.LunaBankBranchService;
import ms.luna.biz.util.FastJsonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by SDLL18 on 16/8/31.
 */
@Service("lunaBankBranchService")
public class LunaBankBranchServiceImpl implements LunaBankBranchService {

    private final static Logger logger = Logger.getLogger(LunaBankBranchServiceImpl.class);


    @Autowired
    private LunaBankBranchDAO lunaBankBranchDAO;

    @Override
    public JSONObject getBranchListByNameAndBank(JSONObject jsonObject) {
        try {
            String branchName = jsonObject.getString("branchName");
            String bankCode = jsonObject.getString("bankCode");
            String cityCode = jsonObject.getString("cityCode");
            LunaBankBranchCriteria criteria = new LunaBankBranchCriteria();
            criteria.createCriteria().andLnameLike("%" + branchName + "%").andBnkcodeLike(bankCode.substring(0, 3) + cityCode + "%");
            List<LunaBankBranch> branchList = lunaBankBranchDAO.selectByCriteria(criteria);
            JSONArray toReturn = new JSONArray();
            for (LunaBankBranch branch : branchList) {
                JSONObject object = new JSONObject();
                object.put("branchCode", branch.getBnkcode());
                object.put("branchName", branch.getLname());
                toReturn.add(object);
            }
            return FastJsonUtil.sucess("success", toReturn);
        } catch (Exception e) {
            logger.error("Failed to get branch list.", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
    }

    @Override
    public JSONObject getBranchByCode(JSONObject jsonObject) {
        try {
            String branchCode = jsonObject.getString("branchCode");
            LunaBankBranchCriteria criteria = new LunaBankBranchCriteria();
            criteria.createCriteria().andBnkcodeEqualTo(branchCode);
            List<LunaBankBranch> branchList = lunaBankBranchDAO.selectByCriteria(criteria);
            if (branchList.size() == 0) {
                return FastJsonUtil.error(ErrorCode.NOT_FOUND, "分行行号不存在");
            }
            JSONObject object = new JSONObject();
            object.put("branchCode", branchList.get(0).getBnkcode());
            object.put("branchName", branchList.get(0).getLname());
            return FastJsonUtil.sucess("success", object);
        } catch (Exception e) {
            logger.error("Failed to get branch.", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
    }
}
