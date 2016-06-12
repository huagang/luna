package ms.luna.biz.dao.custom.model;

import java.io.Serializable;

/** 
 * @author  Greek 
 * @date create time：2016年3月24日 下午7:24:14 
 * @version 1.0 
 */
public class MerchantsResult implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String merchant_id = null;
	
	private String merchant_nm = null;
	
	private String category_id = null;
	
	private String category_nm = null;
	
	private String contact_nm = null;
	
	private String contact_phonenum = null;
			
	private String salesman_nm = null;
	
	private String status_id = null;

	private String province_id = null;

	private String city_id = null;
	
	private String county_id = null;
	
	private String del_flg = null;

	/**
	 * @return the merchant_id
	 */
	public String getMerchant_id() {
		return merchant_id;
	}

	/**
	 * @param merchant_id the merchant_id to set
	 */
	public void setMerchant_id(String merchant_id) {
		this.merchant_id = merchant_id;
	}

	/**
	 * @return the merchant_nm
	 */
	public String getMerchant_nm() {
		return merchant_nm;
	}

	/**
	 * @param merchant_nm the merchant_nm to set
	 */
	public void setMerchant_nm(String merchant_nm) {
		this.merchant_nm = merchant_nm;
	}

	/**
	 * @return the category_id
	 */
	public String getCategory_id() {
		return category_id;
	}

	/**
	 * @param category_id the category_id to set
	 */
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}

	/**
	 * @return the category_nm
	 */
	public String getCategory_nm() {
		return category_nm;
	}

	/**
	 * @param category_nm the category_nm to set
	 */
	public void setCategory_nm(String category_nm) {
		this.category_nm = category_nm;
	}

	/**
	 * @return the contact_nm
	 */
	public String getContact_nm() {
		return contact_nm;
	}

	/**
	 * @param contact_nm the contact_nm to set
	 */
	public void setContact_nm(String contact_nm) {
		this.contact_nm = contact_nm;
	}

	/**
	 * @return the contact_phonenum
	 */
	public String getContact_phonenum() {
		return contact_phonenum;
	}

	/**
	 * @param contact_phonenum the contact_phonenum to set
	 */
	public void setContact_phonenum(String contact_phonenum) {
		this.contact_phonenum = contact_phonenum;
	}

	/**
	 * @return the salesman_nm
	 */
	public String getSalesman_nm() {
		return salesman_nm;
	}

	/**
	 * @param salesman_nm the salesman_nm to set
	 */
	public void setSalesman_nm(String salesman_nm) {
		this.salesman_nm = salesman_nm;
	}

	/**
	 * @return the status_id
	 */
	public String getStatus_id() {
		return status_id;
	}

	/**
	 * @param status_id the status_id to set
	 */
	public void setStatus_id(String status_id) {
		this.status_id = status_id;
	}

	/**
	 * @return the province_id
	 */
	public String getProvince_id() {
		return province_id;
	}

	/**
	 * @param province_id the province_id to set
	 */
	public void setProvince_id(String province_id) {
		this.province_id = province_id;
	}

	/**
	 * @return the city_id
	 */
	public String getCity_id() {
		return city_id;
	}

	/**
	 * @param city_id the city_id to set
	 */
	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}

	/**
	 * @return the county_id
	 */
	public String getCounty_id() {
		return county_id;
	}

	/**
	 * @param county_id the county_id to set
	 */
	public void setCounty_id(String county_id) {
		this.county_id = county_id;
	}

	/**
	 * @return the del_flg
	 */
	public String getDel_flg() {
		return del_flg;
	}

	/**
	 * @param del_flg the del_flg to set
	 */
	public void setDel_flg(String del_flg) {
		this.del_flg = del_flg;
	}


}
