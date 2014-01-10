package egovframework.eam.api.sso;

import static org.junit.Assert.assertNotNull;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import egovframework.eam.api.config.SystemInfoConfig;
import egovframework.eam.api.internal.TicketStorage;
import egovframework.eam.api.system.Ticket;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath*:/META-INF/egovam/sso-service.xml",
		"classpath*:/META-INF/egovam/security-remote.xml",
		"classpath*:/META-INF/spring/test-common.xml",
		"classpath*:/META-INF/spring/test-sso.xml",
		})
public class SSOServiceTest {
	@Resource(name="SSOService")
	private SSOService ssoService;
	
	@Resource(name="ticketStorage")
	private TicketStorage ticketStorage;
	
	@Resource(name="systemInfo")
	private SystemInfoConfig systemInfo;
	
	@Before
	public void setUp() {
		Ticket ticket = new Ticket();
		
		ticket.setSystemId(systemInfo.getSystemId());
		ticket.setSessionKey("sessionKey");
		ticket.setIssueDate(new Date());
		
		ticketStorage.put(systemInfo.getSystemId(), ticket);
	}
	
	@Test
	public void testGetUserTicketString() {
		String ticket = ssoService.getUserTicketString("test");
		
		System.out.println(ticket);
		
		assertNotNull(ticket);
	}
}
