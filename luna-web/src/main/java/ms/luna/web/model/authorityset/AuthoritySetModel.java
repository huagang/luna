package ms.luna.web.model.authorityset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AuthoritySetModel implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5041767476317512232L;

	private List<String> checkeds = null;

	private List<String> editables = null;

	private List<AuthoritySetModuleModel> modules = new ArrayList<AuthoritySetModuleModel>();

	/**
	 * @return the modules
	 */
	public List<AuthoritySetModuleModel> getModules() {
		return modules;
	}

	/**
	 * @param modules the modules to set
	 */
	public void setModules(List<AuthoritySetModuleModel> modules) {
		this.modules = modules;
	}

	/**
	 * @return the checkeds
	 */
	public List<String> getCheckeds() {
		return checkeds;
	}

	/**
	 * @param checkeds the checkeds to set
	 */
	public void setCheckeds(List<String> checkeds) {
		this.checkeds = checkeds;
	}

	/**
	 * @return the editables
	 */
	public List<String> getEditables() {
		return editables;
	}

	/**
	 * @param editables the editables to set
	 */
	public void setEditables(List<String> editables) {
		this.editables = editables;
	}

}
