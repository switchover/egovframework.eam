package egovframework.eam.api.user.impl;

import org.springframework.beans.factory.annotation.Autowired;

import egovframework.eam.api.user.UserDAO;
import egovframework.eam.api.user.UserService;
import egovframework.eam.api.user.UserVO;

public class UserServiceImpl implements UserService {
	@Autowired
	private UserDAO userDao;

	public boolean isExisted(String userId) {
		UserVO user = userDao.select(userId);
		
		return (user == null ? false : true);
	}
	
	public UserVO getUser(String userId) {
		return userDao.select(userId);
	}

	public void addUser(UserVO user) {
		user.setEnabled(true);
		
		userDao.insert(user);
	}

	public void modifyUser(UserVO user) {
		userDao.update(user);
	}

	public void deleteUser(String userId) {
		userDao.delete(userId);
	}

	public void disableUser(String userId) {
		UserVO user = userDao.select(userId);
		
		user.setEnabled(false);
		
		userDao.update(user);
	}
}
