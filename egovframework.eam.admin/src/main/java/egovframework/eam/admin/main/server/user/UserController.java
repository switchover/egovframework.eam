package egovframework.eam.admin.main.server.user;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import egovframework.eam.admin.api.user.UserInfo;
import egovframework.eam.admin.api.user.UserInfoList;
import egovframework.eam.admin.main.server.common.SearchVO;
import egovframework.eam.admin.main.server.util.BeanOperator;
import egovframework.eam.admin.main.shared.user.User;
import egovframework.eam.admin.main.shared.user.UserService;

@Controller
public class UserController {
	private static final String XML_VIEW_NAME = "xml";

	//@Resource(name = "marshaller")
	//private Jaxb2Marshaller marshaller;

	@Resource(name = "userService")
	private UserService userService;
	
	private BeanOperator<User, UserInfo> bean = new BeanOperator<User, UserInfo>(UserInfo.class);
	
	@RequestMapping(method=RequestMethod.GET, value="/users")
	public ModelAndView getUsers(SearchVO search) {
		List<User> users;
		
		if (search.getSearchCondition().equals("userId")) {
			users = userService.findWithLike("user_id", search.getSearchWord());
		} else if (search.getSearchCondition().equals("userName")) {
			users = userService.findWithLike("user_name", search.getSearchWord());
		} else {
			users = userService.findAll();
		}
		UserInfoList list = new UserInfoList(bean.cloneList(users));
		
		return new ModelAndView(XML_VIEW_NAME, "users", list);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/user/{userId}")
	public ModelAndView getUser(@PathVariable String userId) {
		User user = userService.findById(userId);

		return new ModelAndView(XML_VIEW_NAME, "object", bean.cloneBean(user));
	}

//	@RequestMapping(method = RequestMethod.POST, value = "/user")
//	public ModelAndView addUser(@RequestBody String body) {
//		Source source = new StreamSource(new StringReader(body));
//		UserInfo user = (UserInfo) marshaller.unmarshal(source);
//
//		user.setPassword(DefaultUserHelper.encodePassword(user.getPassword()));
//
//		userService.addUser(user);
//
//		return new ModelAndView(XML_VIEW_NAME, "object", user);
//	}
//
//	/*
//	@RequestMapping(method = RequestMethod.POST, value = "/user")
//	public ModelAndView addUser(@RequestBody UserInfo user) {
//		user.setPassword(DefaultUserHelper.encodePassword(user.getPassword()));
//
//		userDao.persist(user);
//
//		return new ModelAndView(XML_VIEW_NAME, "object", user);
//	}
//	*/
//
//	@RequestMapping(method=RequestMethod.PUT, value="/user/{userId}")
//	public ModelAndView updateUser(@RequestBody String body) {
//		System.out.println("----->" + body);
//		
//		Source source = new StreamSource(new StringReader(body));
//		UserInfo user = (UserInfo) marshaller.unmarshal(source);
//		
//		UserInfo found = userService.findById(user.getUserId());
//		copyUserInfo(user, found);
//		
//		userService.updateUser(found);
//		
//		return new ModelAndView(XML_VIEW_NAME, "object", user);
//	}
//	
//	/*
//	@RequestMapping(method=RequestMethod.PUT, value="/user/{userId}")
//	public ModelAndView updateUser(@RequestBody UserInfo user) {
//		UserInfo found = userDao.findById(user.getUserId());
//		copyUserInfo(user, found);
//		
//		userDao.merge(found);
//		
//		return new ModelAndView(XML_VIEW_NAME, "object", user);
//	}
//	*/
//
//	@RequestMapping(method = RequestMethod.DELETE, value = "/user/{userId}")
//	public ModelAndView removeUser(@PathVariable String userId) {
//		userService.deleteUser(userId);
//		
//		List<UserInfo> users = userService.findAll();
//		UserInfoList list = new UserInfoList(users);
//		
//		return new ModelAndView(XML_VIEW_NAME, "users", list);
//	}
//
//	private UserInfo copyUserInfo(UserInfo source, UserInfo target) {
//		target.setUserId(source.getUserId());
//		target.setUserName(target.getUserName());
//		
//		if (! source.getPassword().equals(target.getPassword())) {
//			target.setPassword(DefaultUserHelper.encodePassword(target.getPassword()));
//		}
//		target.setEnabled(source.isEnabled());
//
//		return target;
//	}
}
