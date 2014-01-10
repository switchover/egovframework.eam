package egovframework.eam.admin.main.server.role;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import egovframework.eam.admin.api.role.JpaRoleDAO;
import egovframework.eam.admin.api.role.RoleInfo;
import egovframework.eam.admin.main.server.util.BeanOperator;
import egovframework.eam.admin.main.shared.role.Role;
import egovframework.eam.admin.main.shared.role.RoleService;

@Service("roleService")
public class RoleServiceImpl implements RoleService {
	@Resource(name = "jpaRoleDao")
	private JpaRoleDAO roleDao;
	
	private BeanOperator<RoleInfo, Role> bean = new BeanOperator<RoleInfo, Role>(Role.class);
	
	public List<Role> findAll() {
		return bean.cloneList(roleDao.findAll());
	}
	
	public List<Role> findWithLike(String fieldName, String value) {
		return bean.cloneList(roleDao.findWithLike(fieldName, value));
	}
	
	public Role findById(String authority) {
		return bean.cloneBean(roleDao.findById(authority));
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void addRole(Role role) {
		RoleInfo entity = roleDao.findById(role.getAuthority());
		
		if (entity == null) {
			entity = new RoleInfo();
			bean.copyBean(role, entity);
			
			roleDao.persist(entity);
		}
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void updateRole(Role role) {
		RoleInfo entity = roleDao.findById(role.getAuthority());
		
		
		if (entity != null) {
			bean.copyBean(role, entity);
			
			roleDao.merge(entity);
		}
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void deleteRole(String authority) {
		RoleInfo entity = roleDao.findById(authority);
		
		if (entity != null) {
			roleDao.remove(entity);
		}	
	}

}
