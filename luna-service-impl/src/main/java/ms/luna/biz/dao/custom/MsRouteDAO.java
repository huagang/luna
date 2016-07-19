package ms.luna.biz.dao.custom;

import java.util.List;

import ms.luna.biz.dao.MsRouteDAOBase;
import ms.luna.biz.dao.custom.model.MsRouteParameter;
import ms.luna.biz.dao.custom.model.MsRouteResult;

public interface MsRouteDAO extends MsRouteDAOBase {
	
	public Integer countRoutes(MsRouteParameter msRouteParameter);
	
	public List<MsRouteResult> selectRoutes(MsRouteParameter msRouteParameter);
}