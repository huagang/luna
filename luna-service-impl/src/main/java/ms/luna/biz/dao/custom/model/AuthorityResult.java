package ms.luna.biz.dao.custom.model;

import java.io.Serializable;

/** 
 * @author  Greek 
 * @date create time：2016年3月25日 下午9:51:46 
 * @version 1.0 
 */
public class AuthorityResult implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String role_code = null;		//角色id
	
	private String role_name = null;	//权限角色名称
	
	private String module_name = null;	//所属业务（模块）
	
	private String role_type = null;	//类型

	public AuthorityResult() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AuthorityResult(String role_code, String role_name, String module_name, String role_type) {
		super();
		this.role_code = role_code;
		this.role_name = role_name;
		this.module_name = module_name;
		this.role_type = role_type;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AuthorityResult [role_code=" + role_code + ", role_name=" + role_name + ", module_name=" + module_name
				+ ", role_type=" + role_type + "]";
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
	 * @return the role_type
	 */
	public String getRole_type() {
		return role_type;
	}

	/**
	 * @param role_type the role_type to set
	 */
	public void setRole_type(String role_type) {
		this.role_type = role_type;
	}

}
