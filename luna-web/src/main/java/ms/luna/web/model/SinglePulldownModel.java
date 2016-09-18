package ms.luna.web.model;

import java.io.Serializable;

public class SinglePulldownModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String selectedValue;

	/**
	 * @return the selectedValue
	 */
	public String getSelectedValue() {
		return selectedValue;
	}

	/**
	 * @param selectedValue the selectedValue to set
	 */
	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}
	

}
