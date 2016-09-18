/**
 * 
 */
package ms.luna.biz.dao.custom.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Mark
 *
 */
public class UsersResult implements Serializable {
	private static final long serialVersionUID = 1L;

	private String wjNm = null;
	private String roleId = null;
	private String roleNm = null;
	private String roleAuth = null;
	private String cityId = null;
	private String regionId = null;
	private String regionNmZh = null;
	private String categoryId = null;
	private String categoryNm = null;
	private Date registHhmmss = null;
	/**
	 * @return the wjNm
	 */
	public String getWjNm() {
		return wjNm;
	}
	/**
	 * @param wjNm the wjNm to set
	 */
	public void setWjNm(String wjNm) {
		this.wjNm = wjNm;
	}
	/**
	 * @return the roleId
	 */
	public String getRoleId() {
		return roleId;
	}
	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	/**
	 * @return the roleNm
	 */
	public String getRoleNm() {
		return roleNm;
	}
	/**
	 * @param roleNm the roleNm to set
	 */
	public void setRoleNm(String roleNm) {
		this.roleNm = roleNm;
	}
	/**
	 * @return the roleAuth
	 */
	public String getRoleAuth() {
		return roleAuth;
	}
	/**
	 * @param roleAuth the roleAuth to set
	 */
	public void setRoleAuth(String roleAuth) {
		this.roleAuth = roleAuth;
	}
	/**
	 * @return the cityId
	 */
	public String getCityId() {
		return cityId;
	}
	/**
	 * @param cityId the cityId to set
	 */
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	/**
	 * @return the regionId
	 */
	public String getRegionId() {
		return regionId;
	}
	/**
	 * @param regionId the regionId to set
	 */
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	/**
	 * @return the regionNmZh
	 */
	public String getRegionNmZh() {
		return regionNmZh;
	}
	/**
	 * @param regionNmZh the regionNmZh to set
	 */
	public void setRegionNmZh(String regionNmZh) {
		this.regionNmZh = regionNmZh;
	}
	/**
	 * @return the categoryId
	 */
	public String getCategoryId() {
		return categoryId;
	}
	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * @return the categoryNm
	 */
	public String getCategoryNm() {
		return categoryNm;
	}
	/**
	 * @param categoryNm the categoryNm to set
	 */
	public void setCategoryNm(String categoryNm) {
		this.categoryNm = categoryNm;
	}
	/**
	 * @return the registHhmmss
	 */
	public Date getRegistHhmmss() {
		return registHhmmss;
	}
	/**
	 * @param registHhmmss the registHhmmss to set
	 */
	public void setRegistHhmmss(Date registHhmmss) {
		this.registHhmmss = registHhmmss;
	}
}
