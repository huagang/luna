package ms.luna.web.model.common;

import java.io.Serializable;

public class SimpleModel implements Serializable {
	/**
	 * serialVersionUID
	 */
	protected static final long serialVersionUID = -4252972249916800480L;

	/**
	 * label
	 */
	protected String label;
	/**
	 * code
	 */
	protected String value;

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
