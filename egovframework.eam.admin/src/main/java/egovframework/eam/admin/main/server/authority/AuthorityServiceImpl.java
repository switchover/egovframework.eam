package egovframework.eam.admin.main.server.authority;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import egovframework.eam.admin.api.authority.AuthorityInfo;
import egovframework.eam.admin.api.authority.AuthorityPK;
import egovframework.eam.admin.api.authority.JpaAuthorityDAO;
import egovframework.eam.admin.api.role.JpaRoleDAO;
import egovframework.eam.admin.api.role.RoleInfo;
import egovframework.eam.admin.api.user.JpaUserDAO;
import egovframework.eam.admin.api.user.UserInfo;
import egovframework.eam.admin.main.server.util.BeanOperator;
import egovframework.eam.admin.main.shared.authority.Authority;
import egovframework.eam.admin.main.shared.authority.AuthorityService;
import egovframework.eam.admin.main.shared.role.Role;
import egovframework.eam.admin.main.shared.user.User;

@Service("authorityService")
public class AuthorityServiceImpl implements AuthorityService {
	//@Resource(name = "jpaAuthorityDao")
	@Autowired
	private JpaAuthorityDAO authorityDao;
	
	@Autowired
	private JpaUserDAO userDao;
	
	@Autowired
	private JpaRoleDAO roleDao;
	
	private BeanOperator<AuthorityInfo, Authority> bean = new BeanOperator<AuthorityInfo, Authority>(Authority.class);
	private BeanOperator<UserInfo, User> userBean = new BeanOperator<UserInfo, User>(User.class);
	private BeanOperator<RoleInfo, Role> roleBean = new BeanOperator<RoleInfo, Role>(Role.class);
	
	public List<Authority> findAll() {
		List<AuthorityInfo> authorities = authorityDao.findAll();
		
		for (AuthorityInfo authority : authorities) {
			authority.setUserId(authority.getAuthorityPK().getUserId());
			authority.setAuthority(authority.getAuthorityPK().getAuthority());
			
			authority.setUser(userBean.cloneBean(userDao.findById(authority.getUserId())));
			authority.setRole(roleBean.cloneBean(roleDao.findById(authority.getAuthority())));
		}
		
		return bean.cloneList(authorities);
	}
	
	public List<Authority> findWithLike(String fieldName, String value) {
		List<AuthorityInfo> authorities = authorityDao.findWithLike(fieldName, value);
		
		for (AuthorityInfo authority : authorities) {
			authority.setUserId(authority.getAuthorityPK().getUserId());
			authority.setAuthority(authority.getAuthorityPK().getAuthority());
			
			authority.setUser(userBean.cloneBean(userDao.findById(authority.getUserId())));
			authority.setRole(roleBean.cloneBean(roleDao.findById(authority.getAuthority())));
		}
		
		return bean.cloneList(authorities);
	}
	
	public Authority findById(Authority authority) {
		AuthorityInfo entity = getEntity(authority);
		
		entity.setUserId(entity.getAuthorityPK().getUserId());
		entity.setAuthority(entity.getAuthorityPK().getAuthority());
		
		entity.setUser(userBean.cloneBean(userDao.findById(entity.getUserId())));
		entity.setRole(roleBean.cloneBean(roleDao.findById(entity.getAuthority())));
		
		return bean.cloneBean(entity);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void addAuthority(Authority authority) {
		AuthorityInfo entity = getEntity(authority);
		
		if (entity == null) {
			entity = new AuthorityInfo();
			bean.copyBean(authority, entity);
			
			AuthorityPK ids = new AuthorityPK(authority.getUserId(), authority.getAuthority());
			entity.setAuthorityPK(ids);
			
			authorityDao.persist(entity);
		}
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void updateAuthority(Authority authority) {
		AuthorityInfo entity = getEntity(authority);
		
		if (entity != null) {
			bean.copyBean(authority, entity);
			
			AuthorityPK ids = new AuthorityPK(authority.getUserId(), authority.getAuthority());
			entity.setAuthorityPK(ids);

			authorityDao.merge(entity);
		}
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void deleteAuthority(Authority authority) {
		AuthorityInfo entity = getEntity(authority);
		
		if (entity != null) {
			authorityDao.remove(entity);
		}	
	}

	private AuthorityInfo getEntity(Authority authority) {
		AuthorityPK ids = new AuthorityPK(authority.getUserId(), authority.getAuthority());
		
		AuthorityInfo entity = authorityDao.findByMultiId(ids);
		return entity;
	}
}
