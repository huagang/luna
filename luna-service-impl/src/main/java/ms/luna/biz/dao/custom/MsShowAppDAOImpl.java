package ms.luna.biz.dao.custom;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import ms.luna.biz.dao.MsShowAppDAOBaseImpl;
import ms.luna.biz.dao.custom.model.MsShowAppParameter;
import ms.luna.biz.dao.custom.model.MsShowAppResult;

@Repository("msShowAppDAO")
public class MsShowAppDAOImpl extends MsShowAppDAOBaseImpl implements MsShowAppDAO {

	private final static Logger logger = Logger.getLogger(MsShowAppDAOImpl.class);

	@Override
	public List<MsShowAppResult> selectShowAppWithFilter(MsShowAppParameter parameter) {
		// TODO Auto-generated method stub
		List<MsShowAppResult> results = getSqlMapClientTemplate().queryForList("ms_show_app.selectShowAppWithFilter", parameter);
		return results;
	}

	@Override
	public int selectIdByName(String name) {
		// TODO Auto-generated method stub
		List<Integer> results = getSqlMapClientTemplate().queryForList("ms_show_app.selectIdByName", name);
		if(results != null && results.size() == 1) {
			return results.get(0);
		}

		return -1;
	}

	@Override
	public List<MsShowAppResult> selectShowAppByCategoryId(MsShowAppParameter parameter) {
		List<MsShowAppResult> results = getSqlMapClientTemplate().queryForList("ms_show_app.selectShowAppByCategoryId", parameter);
		return results;
	}

	@Override
	public Integer countShowAppsByCategoryId(MsShowAppParameter msShowAppParameter) {
		Integer count = (Integer)getSqlMapClientTemplate().queryForObject("ms_show_app.countShowAppsByCategoryId", msShowAppParameter);
		return count;
	}

}