package egovframework.eam.api.system;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.ExpectedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath*:/META-INF/egovam/security-remote.xml",
		"classpath*:/META-INF/spring/test-common.xml",
		"classpath*:/META-INF/spring/test-remote.xml",
		})
public class SystemServiceTest {

	@Resource(name="systemService")
	private SystemService systemService;
	
	@Test
	@ExpectedException(SystemException.class)
	public void testGetTicketForSystemIdError() {
		systemService.getTicket("error", "authKey", "password");
	}
	
	@Test
	@ExpectedException(SystemException.class)
	public void testGetTicketForPasswordError() {
		systemService.getTicket("sample", "sample123", "sample123");
	}
	
	@Test
	public void testGetTicket() {
		Ticket ticket = null;
		
		try {
			ticket = systemService.getTicket("sample", "LU+u6EVHXFosN1hV7j4KD4pt6CeYyEoXstnHcDMcnpc=", "sample123");
		} catch (SkippableSystemException sse) {
			return;
		}
		
		assertNotNull(ticket);
		assertEquals("sample", ticket.getSystemId());
		assertNotNull(ticket.getSessionKey());
	}
}
