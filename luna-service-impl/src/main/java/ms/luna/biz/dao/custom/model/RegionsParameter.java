package ms.luna.biz.dao.custom.model;

import java.io.Serializable;

public class RegionsParameter implements Serializable {

	private static final long serialVersionUID = 1L;

	private String likeFilterNm = null;

	private String range = null;
	
	private Integer max = null;
	
	private Integer min = null;

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
	 * @return the likeFilterNm
	 */
	public String getLikeFilterNm() {
		return likeFilterNm;
	}

	/**
	 * @param likeFilterNm the likeFilterNm to set
	 */
	public void setLikeFilterNm(String likeFilterNm) {
		this.likeFilterNm = likeFilterNm;
	}

}
