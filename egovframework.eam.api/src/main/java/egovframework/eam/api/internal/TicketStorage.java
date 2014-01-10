package egovframework.eam.api.internal;

import org.springframework.stereotype.Repository;

import egovframework.eam.api.system.Ticket;

@Repository("ticketStorage")
public class TicketStorage extends Storage<Ticket> {
	
}
