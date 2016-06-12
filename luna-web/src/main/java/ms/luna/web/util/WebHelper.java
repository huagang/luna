package ms.luna.web.util;

import org.springframework.stereotype.Component;

import ms.luna.biz.model.MsUser;
@Component(value="webHelper")
public class WebHelper {
	public WebHelper() {
	}
	public Boolean hasRoles(MsUser msUser, String... msRoleCodes) {
		String msRoleCode = msUser.getMsRoleCode();
		for(String roleCode : msRoleCodes){
			if (msRoleCode.equals(roleCode)) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
}
