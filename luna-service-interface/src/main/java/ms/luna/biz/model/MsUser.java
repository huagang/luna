package ms.luna.biz.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * luna系统被认证的用户
 * 
 * 
 * @author Mark
 *
 */
public class MsUser implements AuthenticatedUser, Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 美剧权限
	 * @author Mark
	 *
	 */
	public enum MsUserEnum {
		读("read only..."),
		写("write only..."),
		读写("read & write..."),
		无("none auth");

		/**
		 * 描述
		 */
		private String describe;
		/**
		 * 获取权限对象
		 * @return
		 */
		public String toString() {
			return this.describe;
		}
		/**
		 * 构造权限对象
		 * @param context
		 */
		private MsUserEnum(String context) {
			this.describe = context;
		}
	}

	/** 微景内部统一ID */
	private String uniqueId;

	/** 角色code */
	private String msRoleCode;

	/**
	 * 角色名称
	 */
	private String msRoleName;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 头像
	 */
	private String headImgUrl;

	/**
	 * 登录方式
	 */
	private Integer mode;

	/**
	 * 登录时间
	 */
	private Date loginTime;

	/**
	 * 可以访问的资源
	 */
	private Map<String, MsUserEnum> accessUris = new HashMap<String, MsUserEnum>();

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "VbUserImpl [uniqueId=" + uniqueId
				+ ", msRoleCode=" + msRoleCode
				+ ", msRoleName=" + msRoleName
				+ "]";
	}

	/**
	 * @return the uniqueId
	 */
	public String getUniqueId() {
		return uniqueId;
	}

	/**
	 * @param uniqueId the uniqueId to set
	 */
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	/**
	 * @return the msRoleCode
	 */
	public String getMsRoleCode() {
		return msRoleCode;
	}

	/**
	 * @param msRoleCode the msRoleCode to set
	 */
	public void setMsRoleCode(String msRoleCode) {
		this.msRoleCode = msRoleCode;
	}

	/**
	 * @return the msRoleName
	 */
	public String getMsRoleName() {
		return msRoleName;
	}

	/**
	 * @param msRoleName the msRoleName to set
	 */
	public void setMsRoleName(String msRoleName) {
		this.msRoleName = msRoleName;
	}

	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * @param nickName the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * @return the mode
	 */
	public Integer getMode() {
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(Integer mode) {
		this.mode = mode;
	}

	/**
	 * @return the accessUris
	 */
	public Map<String, MsUserEnum> getAccessUris() {
		return accessUris;
	}

	/**
	 * @param accessUris the accessUris to set
	 */
	public void setAccessUris(Map<String, MsUserEnum> accessUris) {
		this.accessUris = accessUris;
	}

	/**
	 * @return the headImgUrl
	 */
	public String getHeadImgUrl() {
		return headImgUrl;
	}

	/**
	 * @param headImgUrl the headImgUrl to set
	 */
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	/**
	 * @return the loginTime
	 */
	public Date getLoginTime() {
		return loginTime;
	}

	/**
	 * @param loginTime the loginTime to set
	 */
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

}
