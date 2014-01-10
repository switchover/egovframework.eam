package egovframework.eam.api.sso;

public interface SSOService {
	/**
	 * Get user ticket (base64) to send other system.
	 * @return
	 */
	public String getUserTicketString(String userId);
}
