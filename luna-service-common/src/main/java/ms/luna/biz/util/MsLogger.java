package ms.luna.biz.util;

import org.apache.log4j.Logger;

public class MsLogger {
	private static Logger logger = Logger.getLogger(MsLogger.class);
	private static final short debug = 1;
	private static final short info = 2;
	private static final short warn = 3;
	private static final short error = 4;
	private static final short fatal = 5;

	private static final int defaultStackLevel = 3;

	public static void debug(String message) {
		common(message, debug, null, defaultStackLevel);
	}
	public static void debug(String message, int stackLevel) {
		common(message, debug, null, defaultStackLevel + stackLevel);
	}
	public static void debug(String message, int stackLevel, Throwable th) {
		common(message, debug, th, defaultStackLevel + stackLevel);
	}

	public static void debug(String message, Throwable th) {
		common(message, debug, th, defaultStackLevel);
	}
	public static void debug(Exception exception) {
		common(exception, debug, defaultStackLevel);
	}
	public static void debug(Exception exception, int stackLevel) {
		common(exception, debug, defaultStackLevel + stackLevel);
	}

	public static void info(String message) {
		common(message, info, null, defaultStackLevel);
	}
	public static void info(String message, int stackLevel) {
		common(message, info, null, defaultStackLevel + stackLevel);
	}
	public static void info(String message, int stackLevel, Throwable th) {
		common(message, info, th, defaultStackLevel + stackLevel);
	}

	public static void info(String message, Throwable th) {
		common(message, info, th, defaultStackLevel);
	}
	public static void info(Exception exception) {
		common(exception, info, defaultStackLevel);
	}
	public static void info(Exception exception, int stackLevel) {
		common(exception, info, defaultStackLevel + stackLevel);
	}

	public static void warn(String message) {
		common(message, warn, null, defaultStackLevel);
	}
	public static void warn(String message, int stackLevel) {
		common(message, warn, null, defaultStackLevel + stackLevel);
	}
	public static void warn(String message, int stackLevel, Throwable th) {
		common(message, warn, th, defaultStackLevel + stackLevel);
	}
	public static void warn(String message, Throwable th) {
		common(message, warn, th, defaultStackLevel);
	}
	public static void warn(Exception exception) {
		common(exception, warn, defaultStackLevel);
	}
	public static void warn(Exception exception, int stackLevel) {
		common(exception, warn, defaultStackLevel + stackLevel);
	}
	
	public static void error(String message) {
		common(message, error, null, defaultStackLevel);
	}
	public static void error(String message, int stackLevel) {
		common(message, error, null, defaultStackLevel + stackLevel);
	}
	public static void error(String message, int stackLevel, Throwable th) {
		common(message, error, th, defaultStackLevel + stackLevel);
	}
	public static void error(String message, Throwable th) {
		common(message, error, th, defaultStackLevel);
	}
	public static void error(Exception exception) {
		common(exception, error, defaultStackLevel);
	}
	public static void error(Exception exception, int stackLevel) {
		common(exception, error, defaultStackLevel + stackLevel);
	}

	public static void fatal(String message) {
		common(message, fatal, null, defaultStackLevel);
	}
	public static void fatal(String message, int stackLevel) {
		common(message, fatal, null, defaultStackLevel + stackLevel);
	}
	public static void fatal(String message, int stackLevel, Throwable th) {
		common(message, fatal, th, defaultStackLevel + stackLevel);
	}
	public static void fatal(String message, Throwable th) {
		common(message, fatal, th, defaultStackLevel);
	}
	public static void fatal(Exception exception) {
		common(exception, fatal, defaultStackLevel);
	}
	public static void fatal(Exception exception, int stackLevel) {
		common(exception, fatal, defaultStackLevel + stackLevel);
	}

	private static void common(Exception exception, int level, int stackLevel) {
		StackTraceElement stack[] = Thread.currentThread().getStackTrace(); 
		if (stackLevel < defaultStackLevel || stack.length <= stackLevel) {
			stackLevel = defaultStackLevel;
		}

		String callName = stack[stackLevel].getClassName();  
		if (!callName.endsWith("SelectDefBindingContainer")){
			String className = callName;
			String methodName = stack[stackLevel].getMethodName();
			int lineName = stack[stackLevel].getLineNumber();
			switch (level) {
				case debug:
					logger.debug(className + "#" + methodName+ ":(" + lineName
							+ ")\t", exception);
					break;
				case info:
					logger.info(className + "#" + methodName+ ":(" + lineName
							+ ")\t", exception);
					break;
				case warn:
					logger.warn(className + "#" + methodName+ ":(" + lineName
							+ ")\t", exception);
					break;
				case error:
					logger.error(className + "#" + methodName+ ":(" + lineName
							+ ")\t", exception);
					break;
				case fatal:
					logger.fatal(className + "#" + methodName+ ":(" + lineName
							+ ")\t", exception);
					break;
				default:
					break;
			}
		}
	}
	private static void common(String message, int logLevel, Throwable th, int stackLevel) {
		StackTraceElement stack[] = Thread.currentThread().getStackTrace();
		if (stackLevel < defaultStackLevel || stack.length <= stackLevel) {
			stackLevel = defaultStackLevel;
		}

		String callName = stack[stackLevel].getClassName();  
		if (!callName.endsWith("SelectDefBindingContainer")){
			String className = callName;
			String methodName = stack[stackLevel].getMethodName();
			int lineName = stack[stackLevel].getLineNumber();
			switch (logLevel) {
				case debug:
					if (th == null) {
						logger.debug(className + "#" + methodName+ ":(" + lineName
								+ ")\t"+ message);
					} else {
						logger.debug(className + "#" + methodName+ ":(" + lineName
								+ ")\t"+ message, th);
					}
					break;
				case info:
					if (th == null) {
						logger.info(className + "#" + methodName+ ":(" + lineName
								+ ")\t"+ message);
					} else {
						logger.info(className + "#" + methodName+ ":(" + lineName
								+ ")\t"+ message, th);
					}
					break;
				case warn:
					if (th == null) {
						logger.warn(className + "#" + methodName+ ":(" + lineName
								+ ")\t"+ message);
					} else {
						logger.warn(className + "#" + methodName+ ":(" + lineName
								+ ")\t"+ message, th);
					}
					break;
				case error:
					if (th == null) {
						logger.error(className + "#" + methodName+ ":(" + lineName
								+ ")\t"+ message);
					} else {
						logger.error(className + "#" + methodName+ ":(" + lineName
								+ ")\t"+ message, th);
					}
					break;
				case fatal:
					if (th == null) {
						logger.fatal(className + "#" + methodName+ ":(" + lineName
								+ ")\t"+ message);
					} else {
						logger.fatal(className + "#" + methodName+ ":(" + lineName
								+ ")\t"+ message, th);
					}
					break;
				default:
					break;
			}
		}
	}
}
