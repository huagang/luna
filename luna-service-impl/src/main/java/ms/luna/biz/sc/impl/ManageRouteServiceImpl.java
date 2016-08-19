package ms.luna.biz.sc.impl;

import java.util.ArrayList;
import java.util.List;

import ms.luna.biz.dao.custom.MsRouteCollectionDAO;
import ms.luna.biz.table.MsRouteTable;
import ms.luna.biz.util.MsLogger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.dao.custom.MsRouteDAO;
import ms.luna.biz.dao.custom.model.MsRouteParameter;
import ms.luna.biz.dao.custom.model.MsRouteResult;
import ms.luna.biz.dao.model.MsRoute;
import ms.luna.biz.dao.model.MsRouteCriteria;
import ms.luna.biz.sc.ManageRouteService;
import ms.luna.biz.util.FastJsonUtil;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * @author Greek
 *
 */
@Transactional(rollbackFor = Exception.class)
@Service("manageRouteService")
public class ManageRouteServiceImpl implements ManageRouteService {

	@Autowired
	private MsRouteDAO msRouteDAO;

	@Autowired
	private MsRouteCollectionDAO msRouteCollectionDAO;

	@Override
	public JSONObject createRoute(JSONObject json) {
		try {
			String name = json.getString(MsRouteTable.FIELD_NAME);
			// 检测名称是否存在
			boolean flag = checkRouteNmExist(name, null);
			if(flag) {
				return FastJsonUtil.error(ErrorCode.ALREADY_EXIST, "route name exists");
			}

			MsRoute msRoute = JSONObject.toJavaObject(json, MsRoute.class);
			msRouteDAO.insertSelective(msRoute);
			return FastJsonUtil.sucess("success");
		} catch (Exception e){
			MsLogger.error("Failed to insert route: " + e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to insert route");
		}

	}

	@Override
	public JSONObject editRoute(JSONObject json) {
		try {
			Integer id = json.getInteger(MsRouteTable.FIELD_ID);
			String name = json.getString(MsRouteTable.FIELD_NAME);

			// 检测名称是否存在
			boolean flag = checkRouteNmExist(name, id);
			if(flag) {
				return FastJsonUtil.error(ErrorCode.ALREADY_EXIST, "route name exists");
			}
			MsRoute msRoute = JSONObject.toJavaObject(json, MsRoute.class);
			Integer count = msRouteDAO.updateByPrimaryKeySelective(msRoute);
			if(count <= 0) {
				return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "invalid route id");
			}
			return FastJsonUtil.sucess("success");
		} catch (Exception e) {
			MsLogger.error("Failed to update route: " + e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to update route" + e.getMessage());
		}

	}

	@Override
	public JSONObject delRoute(int id) {
		if(id < 0) {
			return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "invalid route id");
		}
		try {
			msRouteDAO.deleteByPrimaryKey(id);
			// mongoDb删除id对应的线路树
			// TODO
			return FastJsonUtil.sucess("success");
		} catch (Exception e) {
			MsLogger.error("Failed to delete route: " + e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to delete route" + e.getMessage());
		}
	}

	@Override
	public JSONObject loadRoutes(JSONObject json) {
		MsRouteParameter msRouteParameter = new MsRouteParameter();
		msRouteParameter.setLimit(json.getInteger("limit"));
		msRouteParameter.setOffset(json.getInteger("offset"));
		msRouteParameter.setRange(true);
		try{
			Integer total = msRouteDAO.countRoutes(new MsRouteParameter());
			JSONArray rows = new JSONArray();
			if(total > 0)
			{
				List<MsRouteResult> lst = msRouteDAO.selectRoutes(msRouteParameter);
				for(int i = 0; i < lst.size(); i++) {
					JSONObject row = new JSONObject();
					MsRouteResult msRouteResult = lst.get(i);
					row.put(MsRouteTable.FIELD_ID, msRouteResult.getId());
					row.put(MsRouteTable.FIELD_NAME, msRouteResult.getName());
					row.put(MsRouteTable.FIELD_BUSINESS_ID, msRouteResult.getBusiness_id());
					row.put(MsRouteTable.FIELD_BUSINESS_NAME, msRouteResult.getBusiness_name());
					row.put(MsRouteTable.FIELD_COST_ID, msRouteResult.getCost_id());
					row.put(MsRouteTable.FIELD_LUNA_NAME, msRouteResult.getLuna_name());
					row.put(MsRouteTable.FIELD_DESCRIPTION, msRouteResult.getDescription());
					row.put(MsRouteTable.FIELD_COVER, msRouteResult.getCover());
					rows.add(row);
				}
			}
			JSONObject result = new JSONObject();
			result.put("rows", rows);
			result.put("total", total);
			result.put("msg", "success");
			result.put("code", 0);
			return result;
		} catch (Exception e) {
			MsLogger.error("Failed to load routes: " + e.getMessage());
			return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to load routes" + e.getMessage());
		}
	}

	@Override
	public JSONObject isRouteNmExist(String json) {
		try {
			JSONObject param = JSONObject.parseObject(json);
			String name = param.getString(MsRouteTable.FIELD_NAME);
			Integer id = param.containsKey(MsRouteTable.FIELD_ID)? param.getInteger(MsRouteTable.FIELD_ID) : null;
			if(!checkRouteNmExist(name, id)) {
				return FastJsonUtil.sucess("route name does not exist");
			} else {
				return FastJsonUtil.error(ErrorCode.ALREADY_EXIST, "route name exists");
			}
		} catch (Exception e) {
			MsLogger.error("Failed to judge whether route is existed: " + e.getMessage());
			return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to judge whether route is existed" + e.getMessage());
		}

	}

	private boolean checkRouteNmExist(String name, Integer id) {
		MsRouteCriteria msRouteCriteria = new MsRouteCriteria();
		MsRouteCriteria.Criteria criteria = msRouteCriteria.createCriteria();
		criteria.andNameEqualTo(name);
		List<MsRoute> lst = msRouteDAO.selectByCriteria(msRouteCriteria);

		// 创建状态
		if(id == null) {
			return (lst != null && lst.size() !=0 );
		} else {//  编辑状态
			// 不存在或者id对应的name 未发生改变
			return (lst != null && lst.size() != 0 && !id.equals(lst.get(0).getId()));
		}
	}

	@Override
	public JSONObject viewRouteConfiguration(Integer routeId) {
		try {
			JSONObject result = new JSONObject();
			// mongo获取线路配置
			Document document = msRouteCollectionDAO.getRoute(routeId);
			JSONArray c_list = document.get("c_list", JSONArray.class);
			result.put("routeDate", c_list);
			// 获取线路poi数据
			List<String> poiIdList = readPoiId2List(c_list);
			JSONArray poiInfoList = getPoiForRoute(c_list);
			result.put("poiDef", poiIdList);

			return FastJsonUtil.sucess("success");
		} catch (Exception e) {
			MsLogger.error("Failed to view route configuration: " + e.getMessage());
			return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to view route configuration.");
		}
	}

	@Override
	public JSONObject saveRouteConfiguration(JSONObject json) {
		try {
			return null;
		} catch (Exception e) {
			MsLogger.error("Failed to save route configuration: " + e.getMessage());
			return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to save route configuration.");
		}
	}


	/**
	 * 从线路中获取poi id列表
	 *
	 * @param c_list
	 * @return
	 */
	private List<String> readPoiId2List(JSONArray c_list) {
		List<String> list = new ArrayList<>();
		for (int i = 0; i < c_list.size(); i++) {
			String poi_id = c_list.getJSONObject(i).getString("poi_id");
			list.add(poi_id);
		}
		return list;
	}

	/**
	 * 根据poi id列表获取信息
	 *
	 * @param c_list
	 * @return
	 */
	private JSONArray getPoiForRoute(JSONArray c_list) {
		return null;
	}
}
