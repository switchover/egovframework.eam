package egovframework.eam.api.system;

public class UserTicket extends Ticket {
	/**
	 * SerialVer UID.
	 */
	private static final long serialVersionUID = 3579524421574295229L;
	
	private String userId;

	public static UserTicket getUserTicket(Ticket ticket, String userId) {
		UserTicket userTicket = new UserTicket();
		
		userTicket.setSystemId(ticket.getSystemId());
		userTicket.setSessionKey(ticket.getSessionKey());
		userTicket.setIssueDate(ticket.getIssueDate());
		userTicket.setLastContactDate(ticket.getLastContactDate());
		
		userTicket.setUserId(userId);
		
		return userTicket;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
