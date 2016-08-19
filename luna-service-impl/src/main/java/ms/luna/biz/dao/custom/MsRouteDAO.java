package ms.luna.biz.dao.custom;

import java.util.List;

import ms.luna.biz.dao.MsRouteDAOBase;
import ms.luna.biz.dao.custom.model.MsRouteParameter;
import ms.luna.biz.dao.custom.model.MsRouteResult;
import org.bson.Document;

public interface MsRouteDAO extends MsRouteDAOBase {

	Integer countRoutes(MsRouteParameter msRouteParameter);
	
	List<MsRouteResult> selectRoutes(MsRouteParameter msRouteParameter);

}