package ms.luna.biz.exception;

/**
 * 服务层异常基类
 * @author frank<frank@visualbusiness.com>
 *
 */
public class LunaServiceException extends LunaRuntimeException {
	private static final long serialVersionUID = 1L;

	public LunaServiceException() {
	}

	public LunaServiceException(String message) {
		super(message);
	}

	public LunaServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public LunaServiceException(Throwable cause) {
		super(cause);
	}
}
