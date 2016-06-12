package ms.luna.biz.dao.custom;

import java.util.List;

import org.springframework.stereotype.Repository;

import ms.luna.biz.dao.MsRUserRoleDAOBaseImpl;
import ms.luna.biz.dao.custom.model.UsersParameter;
import ms.luna.biz.dao.custom.model.UsersResult2;

@Repository("msRUserRoleDAO")
public class MsRUserRoleDAOImpl extends MsRUserRoleDAOBaseImpl implements MsRUserRoleDAO {

	@Override
	public int countUsersByModuleCode(UsersParameter usersParameter) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject("ms_r_user_role.countUsersByModuleCode",
				usersParameter);
		return count;
	}

	@Override
	public int countUsersByRoleAuth(UsersParameter usersParameter) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject("ms_r_user_role.countUsersByRoleAuth",
				usersParameter);
		return count;
	}
	
	@Override
	public List<UsersResult2> selectUsersByModuleCode(UsersParameter usersParameter) {
		@SuppressWarnings("unchecked")
		List<UsersResult2> list = getSqlMapClientTemplate().queryForList("ms_r_user_role.selectUsersByModuleCode",
				usersParameter);
		return list;
	}
	
	@Override
	public List<UsersResult2> selectUsersByRoleAuth(UsersParameter usersParameter) {
		@SuppressWarnings("unchecked")
		List<UsersResult2> list = getSqlMapClientTemplate().queryForList("ms_r_user_role.selectUsersByRoleAuth",
				usersParameter);
		return list;
	}	
	
}