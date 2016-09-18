package ms.luna.biz.dao.custom;

import java.util.List;

import ms.luna.biz.dao.MsRUserRoleDAOBase;
import ms.luna.biz.dao.custom.model.UsersParameter;
import ms.luna.biz.dao.custom.model.UsersResult2;

public interface MsRUserRoleDAO extends MsRUserRoleDAOBase {

	public int countUsersByModuleCode(UsersParameter usersParameter);

	public int countUsersByRoleAuth(UsersParameter usersParameter);
	
	public List<UsersResult2> selectUsersByModuleCode(UsersParameter usersParameter);

	public List<UsersResult2> selectUsersByRoleAuth(UsersParameter usersParameter);
	
}