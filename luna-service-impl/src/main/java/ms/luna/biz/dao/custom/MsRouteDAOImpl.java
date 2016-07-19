package ms.luna.biz.dao.custom;

import ms.luna.biz.dao.MsRouteDAOBaseImpl;
import ms.luna.biz.dao.custom.model.MsRouteParameter;
import ms.luna.biz.dao.custom.model.MsRouteResult;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository("msRouteDAO")
public class MsRouteDAOImpl extends MsRouteDAOBaseImpl implements MsRouteDAO {
	
	public Integer countRoutes(MsRouteParameter msRouteParameter){
		
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject("ms_route.countRoutes",msRouteParameter);
		return count;
	}
	
	public List<MsRouteResult> selectRoutes(MsRouteParameter msRouteParameter){
		
		@SuppressWarnings("unchecked")
		List<MsRouteResult> list =  getSqlMapClientTemplate().queryForList("ms_route.selectRoutes", msRouteParameter);
		return list;
		
	}
	
}