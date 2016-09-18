package ms.biz.common;

import ms.luna.biz.model.AuthenticatedUser;

/**
 * login中 保持User
 * @author Mark
 *
 */
public final class AuthenticatedUserHolder {

	private static final ThreadLocal<AuthenticatedUser> LOGGED_IN_USERS = new ThreadLocal<AuthenticatedUser>();
	
	private AuthenticatedUserHolder(){}
	
	/**
	 * Login中返回User
	 * <p>没有被认证的场合下返回<code>null</code></p>
	 * @return 认证了的用户
	 */
	public static AuthenticatedUser get(){
		return LOGGED_IN_USERS.get();
	}
	
	/**
	 *设置Login中的用户
	 * @param authenticatedUser 被认证过的用户
	 */
	public static void set(AuthenticatedUser authenticatedUser){
		LOGGED_IN_USERS.set(authenticatedUser);
	}
	
	/**
	 * 删除Login用户
	 */
	public static void remove(){
		LOGGED_IN_USERS.set(null);
	}
}
