package ms.luna.biz.sc.impl;

import java.util.List;

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

/**
 * @author Greek
 *
 */
@Transactional(rollbackFor = Exception.class)
@Service("manageRouteService")
public class ManageRouteServiceImpl implements ManageRouteService {

	@Autowired
	private MsRouteDAO msRouteDAO;

	@Override
	public JSONObject createRoute(String json) {
		JSONObject param = JSONObject.parseObject(json);
		String name = param.getString("name");
		String description = param.getString("description");
		String cover = param.getString("cover");
		String unique_id = param.getString("unique_id");
		Integer cost_id = param.getInteger("cost_id");
		Integer business_id = param.getInteger("business_id");

		// 检测名称是否存在
		boolean flag = checkRouteNmExist(name, null);
		if(flag) {
			return FastJsonUtil.error(ErrorCode.ALREADY_EXIST, "route name exists");
		}

		MsRoute msRoute = new MsRoute();
		msRoute.setName(name);
		msRoute.setCover(cover);
		msRoute.setDescription(description);
		msRoute.setCostId(cost_id);
		msRoute.setBusinessId(business_id);
		msRoute.setUniqueId(unique_id);
		try {
			msRouteDAO.insertSelective(msRoute);
			return FastJsonUtil.sucess("success");
		} catch (Exception e){
			return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to insert route" + e.getMessage());
		}

	}

	@Override
	public JSONObject editRoute(String json) {
		JSONObject param = JSONObject.parseObject(json);
		Integer id = param.getInteger("id");

		String name = param.getString("name");
		String description = param.getString("description") ;
		Integer cost_id = param.getInteger("cost_id");
		String cover = param.getString("cover");
		String unique_id = param.getString("unique_id");

		// 检测名称是否存在
		boolean flag = checkRouteNmExist(name, id);
		if(flag) {
			return FastJsonUtil.error(ErrorCode.ALREADY_EXIST, "route name exists");
		}

		MsRoute msRoute = new MsRoute();
		msRoute.setName(name);
		msRoute.setDescription(description);
		msRoute.setCostId(cost_id);
		msRoute.setCover(cover);
		msRoute.setId(id);
		msRoute.setUniqueId(unique_id);

		try {
			Integer count = msRouteDAO.updateByPrimaryKeySelective(msRoute);
			if(count <= 0) {
				return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "invalid route id");
			}
			return FastJsonUtil.sucess("success");
		} catch (Exception e) {
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
			return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to delete route" + e.getMessage());
		}
	}

	@Override
	public JSONObject loadRoutes(String json) {
		JSONObject param = JSONObject.parseObject(json);
		Integer offset = null;
		Integer limit = null;
		if(param.containsKey("offset")) {
			offset = param.getInteger("offset");
		}
		if(param.containsKey("limit")) {
			limit = param.getInteger("limit");
		}
		MsRouteParameter msRouteParameter = new MsRouteParameter();
		if(offset == null ) {
			offset = 0;
		}
		if(limit == null) {
			limit = Integer.MAX_VALUE;
		}
		if(offset != null || limit != null) {
			msRouteParameter.setRange(true);
		}
		msRouteParameter.setLimit(limit);
		msRouteParameter.setOffset(offset);
		try{
			Integer count = msRouteDAO.countRoutes(msRouteParameter);
			JSONArray rows = JSONArray.parseArray("[]");
			if(count > 0) {
				 List<MsRouteResult> lst = msRouteDAO.selectRoutes(msRouteParameter);
				 for(int i = 0; i < lst.size(); i++) {
					 JSONObject row = new JSONObject();
					 MsRouteResult msRouteResult = lst.get(i);
					 row.put("id", msRouteResult.getId());
					 row.put("name", msRouteResult.getName());
					 row.put("business_id", msRouteResult.getBusiness_id());
					 row.put("business_name", msRouteResult.getBusiness_name());
					 row.put("cost_id", msRouteResult.getCost_id());
					 row.put("luna_name", msRouteResult.getLuna_name());
					 row.put("description", msRouteResult.getDescription());
					 row.put("cover", msRouteResult.getCover());
					 rows.add(row);
				 }
			}
			JSONObject result = new JSONObject();
			result.put("total", count);
			result.put("rows", rows);
			result.put("code", 0);
			result.put("msg", "success");

			return result;
		} catch (Exception e) {
			return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to load routes" + e.getMessage());
		}
	}

	@Override
	public JSONObject isRouteNmExist(String json) {
		JSONObject param = JSONObject.parseObject(json);
		String name = param.getString("name");
		Integer id = null;
		if(param.containsKey("id")) {
			id = param.getInteger("id");
		}
		try {
			boolean flag = checkRouteNmExist(name, id);
			if(!flag) {
				return FastJsonUtil.sucess("route name does not exist");
			} else {
				return FastJsonUtil.error(ErrorCode.ALREADY_EXIST, "route name exists");
			}

		} catch (Exception e) {
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

}
