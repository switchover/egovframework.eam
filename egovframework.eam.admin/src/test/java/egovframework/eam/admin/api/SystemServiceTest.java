package egovframework.eam.admin.api;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import egovframework.eam.api.system.SystemService;
import egovframework.eam.api.system.Ticket;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath*:/META-INF/spring/context-common.xml",
		"classpath*:/META-INF/spring/context-datasource.xml",
		"classpath*:/META-INF/spring/context-jpa.xml",
		"classpath*:/META-INF/spring/context-crypto.xml",
		"classpath*:/META-INF/spring/test-service.xml",
		})
public class SystemServiceTest {
	@Resource(name="systemService")
	private SystemService systemService;
	
	@Test
	public void testGetTicket() {
		Ticket ticket = systemService.getTicket("sample", "LU+u6EVHXFosN1hV7j4KD4pt6CeYyEoXstnHcDMcnpc=", "sample123");
		
		assertNotNull(ticket);
		assertEquals("sample", ticket.getSystemId());
		assertNotNull(ticket.getSessionKey());
	}
}
