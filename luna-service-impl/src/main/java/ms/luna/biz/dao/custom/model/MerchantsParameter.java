package ms.luna.biz.dao.custom.model;

import java.io.Serializable;

/** 
 * @author  Greek 
 * @date create time：2016年3月24日 下午7:18:45 
 * @version 1.0 
 */
public class MerchantsParameter implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String likeFilterNm = null;
	
	private String provinceId = null;
	private String cityId = null;
	private String countyId = null;
	
	private String range ;
	
	private Integer max = null;
	
	private Integer min = null;

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

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCountyId() {
		return countyId;
	}

	public void setCountyId(String countyId) {
		this.countyId = countyId;
	}

	@Override
	public String toString() {
		return "MerchantsParameter [likeFilterNm=" + likeFilterNm + ", provinceId=" + provinceId + ", cityId=" + cityId
				+ ", countyId=" + countyId + ", range=" + range + ", max=" + max + ", min=" + min + "]";
	}
	
	
}
