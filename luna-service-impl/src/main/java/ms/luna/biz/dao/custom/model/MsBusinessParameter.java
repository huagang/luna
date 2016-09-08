package ms.luna.biz.dao.custom.model;

import java.util.List;

public class MsBusinessParameter extends BasicModel {
	
	private String provinceId = null;
	private String cityId = null;
	private String countyId = null;
	private String categoryId = null;
	private List<Integer> businessIds = null;

	private String merchantDelFlg = null; // 商户关闭标志,

	private Integer registHhmmssOrder = -1; // 注册时间顺序,1 升序, -1 降序 .默认降序

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
	public String getMerchantDelFlg() {
		return merchantDelFlg;
	}

	public void setMerchantDelFlg(String merchantDelFlg) {
		this.merchantDelFlg = merchantDelFlg;
	}

	public Integer getRegistHhmmssOrder() {
		return registHhmmssOrder;
	}

	public void setRegistHhmmssOrder(Integer registHhmmssOrder) {
		this.registHhmmssOrder = registHhmmssOrder;
	}
}
