package ms.luna.web.model.authorityset;

import java.util.ArrayList;
import java.util.List;

import ms.luna.web.model.common.SimpleModel;

public class AuthoritySetModuleModel extends SimpleModel {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5041767476317512232L;

	/**
	 * 功能列表
	 */
	private List<AuthoritySetFunctionModel> functions = new ArrayList<AuthoritySetFunctionModel>();

	/**
	 * @return the fuctions
	 */
	public List<AuthoritySetFunctionModel> getFunctions() {
		return functions;
	}

	/**
	 * @param fuctions the fuctions to set
	 */
	public void setFunctions(List<AuthoritySetFunctionModel> functions) {
		this.functions = functions;
	}
}
