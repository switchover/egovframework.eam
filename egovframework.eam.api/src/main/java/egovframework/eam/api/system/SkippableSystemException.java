package egovframework.eam.api.system;

public class SkippableSystemException extends SystemException {
	/**
	 * SerialVer UID.
	 */
	private static final long serialVersionUID = 1L;
	
	public SkippableSystemException(String message) {
		super(message);
	}
	
	public SkippableSystemException(Throwable cause) {
		super(cause);
	}
	
	public SkippableSystemException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
