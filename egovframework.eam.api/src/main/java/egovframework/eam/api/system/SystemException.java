package egovframework.eam.api.system;

public class SystemException extends RuntimeException {
	/**
	 * SerialVer UID.
	 */
	private static final long serialVersionUID = 1L;
	
	public SystemException(String message) {
		super(message);
	}
	
	public SystemException(Throwable cause) {
		super(cause);
	}
	
	public SystemException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
