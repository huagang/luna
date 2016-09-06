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
import ms.luna.biz.dao.custom.LunaCityDAO;
import ms.luna.biz.dao.model.LunaCity;
import ms.luna.biz.dao.model.LunaCityCriteria;
import ms.luna.biz.sc.LunaCityService;
import ms.luna.biz.util.FastJsonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by SDLL18 on 16/8/31.
 */
@Service("lunaCityService")
public class LunaCityServiceImpl implements LunaCityService {

    private final static Logger logger = Logger.getLogger(LunaCityServiceImpl.class);

    @Autowired
    private LunaCityDAO lunaCityDAO;

    private JSONArray assembleCityList(List<LunaCity> cityList) {
        JSONArray toReturn = new JSONArray();
        for (LunaCity city : cityList) {
            JSONObject object = new JSONObject();
            object.put("cityCode", city.getCityNo());
            object.put("cityName", city.getCityName());
            toReturn.add(object);
        }
        return toReturn;
    }

    @Override
    public JSONObject getCityListByProvinceCodeAndName(JSONObject jsonObject) {
        try {
            String cityName = jsonObject.getString("cityName");
            Integer provinceCode = jsonObject.getInteger("provinceCode");
            LunaCityCriteria cityCriteria = new LunaCityCriteria();
            cityCriteria.createCriteria().andCityNameLike("%" + cityName + "%").andCityRootEqualTo(provinceCode);
            List<LunaCity> cityList = lunaCityDAO.selectByCriteria(cityCriteria);
            JSONArray toReturn = assembleCityList(cityList);
            return FastJsonUtil.sucess("success", toReturn);
        } catch (Exception e) {
            logger.error("Failed to get city list by provinceCode and name.", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
    }

    @Override
    public JSONObject getCityListByProvinceCode(JSONObject jsonObject) {
        try {
            Integer provinceCode = jsonObject.getInteger("provinceCode");
            LunaCityCriteria cityCriteria = new LunaCityCriteria();
            cityCriteria.createCriteria().andCityRootEqualTo(provinceCode);
            List<LunaCity> cityList = lunaCityDAO.selectByCriteria(cityCriteria);
            JSONArray toReturn = assembleCityList(cityList);
            return FastJsonUtil.sucess("success", toReturn);
        } catch (Exception e) {
            logger.error("Failed to get city list by provinceCode.", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
        }
    }
}
