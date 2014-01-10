package egovframework.eam.admin.main.server.user;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import egovframework.eam.admin.api.user.JpaUserDAO;
import egovframework.eam.admin.api.user.UserInfo;
import egovframework.eam.admin.main.server.util.BeanOperator;
import egovframework.eam.admin.main.shared.user.User;
import egovframework.eam.admin.main.shared.user.UserService;
import egovframework.eam.api.user.DefaultUserHelper;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Resource(name = "jpaUserDao")
	private JpaUserDAO userDao;
	
	private BeanOperator<UserInfo, User> bean = new BeanOperator<UserInfo, User>(User.class);
	
	public List<User> findAll() {
		return bean.cloneList(userDao.findAll());
	}
	
	public List<User> findWithLike(String fieldName, String value) {
		return bean.cloneList(userDao.findWithLike(fieldName, value));
	}
	
	public User findById(String userId) {
		return bean.cloneBean(userDao.findById(userId));
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void addUser(User user) {
		UserInfo entity = userDao.findById(user.getUserId());
		
		if (entity == null) {
			entity = new UserInfo();
			bean.copyBean(user, entity);
			
			entity.setPassword(DefaultUserHelper.encodePassword(user.getPassword()));
			
			userDao.persist(entity);
		}
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void updateUser(User user) {
		UserInfo entity = userDao.findById(user.getUserId());
		
		String orginalPassword = entity.getPassword();
		
		if (entity != null) {
			bean.copyBean(user, entity);
			
			if (! orginalPassword.equals(user.getPassword())) {
				entity.setPassword(DefaultUserHelper.encodePassword(user.getPassword()));
			}
			
			userDao.merge(entity);
		}
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void deleteUser(String userId) {
		UserInfo entity = userDao.findById(userId);
		
		if (entity != null) {
			userDao.remove(entity);
		}	
	}

}
