package egovframework.eam.api.system;

public interface SystemService {
	/**
	 * Get Ticket from TicketStorage or remote admin Server.
	 * @param systemId
	 * @param authKey
	 * @param password
	 * @return
	 */
	public Ticket getTicket(String systemId, String authKey, String password);
	/**
	 * Get Ticket from TicketStorage or remote admin server using SystemInfoConfig bean.
	 * @return
	 */
	public Ticket getTicket();
	
	/**
	 * Register login action to admin server.
	 * @param ticket
	 * @param userId
	 */
	public void login(Ticket ticket, String userId);
	/**
	 * Register logout action to admin server.
	 * @param ticket
	 * @param userId
	 */
	public void logout(Ticket ticket, String userId);
}
