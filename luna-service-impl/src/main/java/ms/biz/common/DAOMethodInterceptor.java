package ms.biz.common;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Date;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import ms.luna.biz.model.AuthenticatedUser;
import ms.luna.biz.model.MsUser;
import ms.luna.biz.util.FieldCache;


/**
 * DAO层AOP处理
 * <p>regist_hhmmss,uphhmmss,updated_by_unique_id
 * @author Mark
 */
public class DAOMethodInterceptor implements MethodInterceptor {

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

		before(method.getMethod().getName(), method.getArguments());
		Object res = method.proceed();
		after(method.getMethod().getName(), method.getArguments());

		return res;
	}

	public void before(String methodName, Object[] args) {
		Timestamp timestamp = new java.sql.Timestamp(new Date().getTime());
		if (methodName.startsWith("insert")) {
			//insert系列。第1,2个参数是对象
			setInsertColumns(args[0], timestamp);
			setUpdateColumns(args[0], timestamp);
			setUpdateUserColumns(args[0]);
		} else if (methodName.startsWith("update")) {
			//update系列。第一个参数是对象
			setUpdateColumns(args[0], timestamp);
			setUpdateUserColumns(args[0]);
		}
	}
	
	/**
	 * Insert时，管理项目设定。
	 * @param o 对象Object
	 * @param timestamp 现在时间戳
	 */
	private void setUpdateUserColumns(Object o) {
		Field f = FieldCache.getInstance().getField(o.getClass(), "updatedByUniqueId");
		if (f != null) {
			MsUser msUser = (MsUser)AuthenticatedUserHolder.get();
			if (msUser != null) {
				set(f, o, msUser.getUniqueId());
			}
		}
	}
	
	/**
	 * Insert时，管理项目设定。
	 * @param o 对象Object
	 * @param timestamp 现在时间戳
	 */
	private void setInsertColumns(Object o, Timestamp timestamp) {
		Field f = FieldCache.getInstance().getField(o.getClass(), "registHhmmss");
		if (f != null) {
			set(f, o, timestamp);
		}
		f = FieldCache.getInstance().getField(o.getClass(), "delFlg");
		if (f != null) {
			set(f, o, "0");
		}
	}

	/**
	 * 更新时管理项目设定。
	 * @param o 对象Object
	 * @param timestamp 现在时间戳
	 */
	private void setUpdateColumns(Object o, Timestamp timestamp) {
		Field f = FieldCache.getInstance().getField(o.getClass(), "upHhmmss");
		if (f != null) {
			set(f, o, timestamp);
		}
	}

	 /** 
	 * @param f
	 * @param target
	 * @param obj
	 */
	private static void set(Field f,Object target,Object obj){
		try {
			f.setAccessible(true);
			f.set(target, obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/*
	 * (non-Javadoc)
	 * @see ms.luna.common.dao.DAOBehavior#after(java.lang.String, java.lang.Object[])
	 */
	public void after(String methodName, Object[] args) {

	}
}
