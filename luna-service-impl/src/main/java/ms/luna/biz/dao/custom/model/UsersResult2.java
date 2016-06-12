package ms.luna.biz.dao.custom.model;

import java.io.Serializable;

/** 
 * @author  Greek 
 * @date create time：2016年4月10日 下午1:49:32 
 * @version 1.0 
 */
public class UsersResult2 implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String luna_name = null;
	private String role_code = null;
	private String role_name = null;
	private String module_code = null;
	private String module_name= null;
	private String ds_order = null;
	/**
	 * @return the luna_name
	 */
	public String getLuna_name() {
		return luna_name;
	}
	/**
	 * @param luna_name the luna_name to set
	 */
	public void setLuna_name(String luna_name) {
		this.luna_name = luna_name;
	}
	/**
	 * @return the role_code
	 */
	public String getRole_code() {
		return role_code;
	}
	/**
	 * @param role_code the role_code to set
	 */
	public void setRole_code(String role_code) {
		this.role_code = role_code;
	}
	/**
	 * @return the role_name
	 */
	public String getRole_name() {
		return role_name;
	}
	/**
	 * @param role_name the role_name to set
	 */
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	/**
	 * @return the module_code
	 */
	public String getModule_code() {
		return module_code;
	}
	/**
	 * @param module_code the module_code to set
	 */
	public void setModule_code(String module_code) {
		this.module_code = module_code;
	}
	/**
	 * @return the module_name
	 */
	public String getModule_name() {
		return module_name;
	}
	/**
	 * @param module_name the module_name to set
	 */
	public void setModule_name(String module_name) {
		this.module_name = module_name;
	}
	/**
	 * @return the ds_order
	 */
	public String getDs_order() {
		return ds_order;
	}
	/**
	 * @param ds_order the ds_order to set
	 */
	public void setDs_order(String ds_order) {
		this.ds_order = ds_order;
	}

	
}