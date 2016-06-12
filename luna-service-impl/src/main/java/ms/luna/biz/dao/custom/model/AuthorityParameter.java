package ms.luna.biz.dao.custom.model;

import java.io.Serializable;

/** 
 * @author  Greek 
 * @date create time：2016年3月26日 下午2:14:45 
 * @version 1.0 
 */
public class AuthorityParameter implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer min = null;
	
	private Integer max = null;
	
	private String range = null;

	/**
	 * @return the min
	 */
	public Integer getMin() {
		return min;
	}

	/**
	 * @param min the min to set
	 */
	public void setMin(Integer min) {
		this.min = min;
	}

	/**
	 * @return the max
	 */
	public Integer getMax() {
		return max;
	}

	/**
	 * @param max the max to set
	 */
	public void setMax(Integer max) {
		this.max = max;
	}

	/**
	 * @return the range
	 */
	public String getRange() {
		return range;
	}

	/**
	 * @param range the range to set
	 */
	public void setRange(String range) {
		this.range = range;
	}

	
}
