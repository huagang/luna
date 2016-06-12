package ms.luna.biz.dao.custom;

import java.util.List;

import org.springframework.stereotype.Repository;

import ms.luna.biz.dao.MsRoleDAOBaseImpl;
import ms.luna.biz.dao.custom.model.AuthorityParameter;
import ms.luna.biz.dao.custom.model.AuthorityResult;

@Repository("msRoleDAO")
public class MsRoleDAOImpl extends MsRoleDAOBaseImpl implements MsRoleDAO {

	@Override
	public List<AuthorityResult> selectAuthority(AuthorityParameter authorityParameter) {
		@SuppressWarnings("unchecked")
		List<AuthorityResult> list = getSqlMapClientTemplate().queryForList("ms_role.selectAllAuthority",
				authorityParameter);
		return list;
	}

	@Override
	public int countAuthority(AuthorityParameter authorityParameter) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject("ms_role.countAuthority",
				authorityParameter);
		return count;
	}

}