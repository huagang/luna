package ms.luna.web.model.authorityset;

import ms.luna.web.model.common.SimpleModel;

public class AuthoritySetFunctionModel extends SimpleModel {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5041767476317512232L;

	private Boolean editable;

	private Boolean checked;

	/**
	 * @return the editable
	 */
	public Boolean getEditable() {
		return editable;
	}

	/**
	 * @param editable the editable to set
	 */
	public void setEditable(Boolean editable) {
		this.editable = editable;
	}

	/**
	 * @return the checked
	 */
	public Boolean getChecked() {
		return checked;
	}

	/**
	 * @param checked the checked to set
	 */
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
}
