package egovframework.eam.api.user;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath*:/META-INF/egovam/security-config.xml", 
		"classpath*:/META-INF/egovam/security-service.xml",
		"classpath*:/META-INF/spring/test-datasource.xml",
		"classpath*:/META-INF/spring/test-common.xml",
		})
public class UserServiceTest {

	@Resource(name="userService")
	UserService userService;
	
	private UserVO makeUser() {
		UserVO user = new UserVO();
		
		user.setUserId("testUser");
		user.setPassword("testpassword");
		user.setUserName("Test User");
		
		return user;
	}
	
	@Before
	public void setUp() {
		userService.addUser(makeUser());
	}
	
	@After
	public void tearDown() {
		userService.deleteUser("testUser");
		userService.deleteUser("testAdmin");
	}
	
	@Test
	public void testAddUserAndIsExisted() {
		assertFalse(userService.isExisted("testAdmin"));
		
		UserVO user = makeUser();
		user.setUserId("testAdmin");
		
		userService.addUser(user);
		
		assertTrue(userService.isExisted("testAdmin"));
		
	}
	
	@Test
	public void testModifyUser() {
		UserVO user = userService.getUser("testUser");
		
		user.setUserName("Tester");
		userService.modifyUser(user);
		
		user = userService.getUser("testUser");
		
		assertEquals("Tester", user.getUserName());
	}
	
	@Test
	public void testDeleteUser() {
		userService.deleteUser("testUser");
		
		assertFalse(userService.isExisted("testUser"));
	}
	
	@Test
	public void testDisableUser() {
		userService.disableUser("testUser");
		
		UserVO user = userService.getUser("testUser");
		assertFalse(user.isEnabled());
	}
}