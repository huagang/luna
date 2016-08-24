package ms.luna.biz.dao.custom.model;

import java.util.List;

public class MsBusinessParameter extends BasicModel {
	
	private String provinceId = null;
	private String cityId = null;
	private String countyId = null;
	private String categoryId = null;
	private List<Integer> businessIds = null;
	
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
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public List<Integer> getBusinessIds() {
		return businessIds;
	}

	public void setBusinessIds(List<Integer> businessIds) {
		this.businessIds = businessIds;
	}
}
