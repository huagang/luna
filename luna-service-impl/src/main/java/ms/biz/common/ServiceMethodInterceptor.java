package ms.biz.common;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import ms.luna.biz.model.AuthenticatedUser;
import ms.luna.biz.model.MsUser;

/**
 * Service层增加登录的用户信息管理
 * @author Mark
 *
 */
public class ServiceMethodInterceptor implements MethodInterceptor {

	/**
	 * MsUser的class名称
	 */
	private static final String clazzMsUserName = MsUser.class.getName();
	@Override
	public Object invoke(MethodInvocation method) throws Throwable {

		Object[] args = method.getArguments();
		if (args != null) {
			for (int i = 0; i < args.length && args[i]!= null; i++) {
				if (clazzMsUserName.equals(args[i].getClass().getName())) {
					AuthenticatedUserHolder.set((AuthenticatedUser)args[i]);
				}
			}
		}

		Object res = method.proceed();

		return res;
	}
}
