package ms.luna.biz.exception;

/**
 * 基本运行时异常类
 * @author frank<frank@visualbusiness.com>
 *
 */
public class LunaRuntimeException extends RuntimeException{
	private static final long serialVersionUID = 4231708332191557893L;

	public LunaRuntimeException() {
	}

	public LunaRuntimeException(String message) {
		super(message);
	}

	public LunaRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public LunaRuntimeException(Throwable cause) {
		super(cause);
	}
}
