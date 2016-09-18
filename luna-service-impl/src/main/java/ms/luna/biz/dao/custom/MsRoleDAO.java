package ms.luna.biz.dao.custom;

import java.util.List;

import ms.luna.biz.dao.MsRoleDAOBase;
import ms.luna.biz.dao.custom.model.AuthorityParameter;
import ms.luna.biz.dao.custom.model.AuthorityResult;

public interface MsRoleDAO extends MsRoleDAOBase {
	
	List<AuthorityResult> selectAuthority(AuthorityParameter authorityParameter);
	
	int countAuthority(AuthorityParameter authorityParameter);
	
}