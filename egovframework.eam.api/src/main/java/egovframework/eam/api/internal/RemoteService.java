package egovframework.eam.api.internal;

public interface RemoteService {
	public byte[] getTicket(String systemId, byte[] data);	// send Token, receive Ticket
	
	public void login(String systemId, byte[] data);	// send UserTicket
	public void logout(String systemId, byte[] data);	// send UserTicket
}
