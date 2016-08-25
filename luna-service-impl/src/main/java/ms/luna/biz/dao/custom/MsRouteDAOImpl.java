package ms.luna.biz.dao.custom;

import com.mongodb.client.MongoCollection;
import ms.luna.biz.dao.MsRouteDAOBaseImpl;
import ms.luna.biz.dao.custom.model.MsRouteParameter;
import ms.luna.biz.dao.custom.model.MsRouteResult;

import java.util.List;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository("msRouteDAO")
public class MsRouteDAOImpl extends MsRouteDAOBaseImpl implements MsRouteDAO {

	@Override
	public Integer countRoutes(MsRouteParameter msRouteParameter){
		
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject("ms_route.countRoutes",msRouteParameter);
		return count;
	}

	@Override
	public List<MsRouteResult> selectRoutes(MsRouteParameter msRouteParameter){
		
		@SuppressWarnings("unchecked")
		List<MsRouteResult> list =  getSqlMapClientTemplate().queryForList("ms_route.selectRoutes", msRouteParameter);
		return list;
		
	}

}