package egovframework.eam.admin.main.server.role;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import egovframework.eam.admin.api.role.JpaRoleHierarchyDAO;
import egovframework.eam.admin.api.role.RoleHierarchyInfo;
import egovframework.eam.admin.api.role.RoleHierarchyPK;
import egovframework.eam.admin.main.server.util.BeanOperator;
import egovframework.eam.admin.main.shared.role.RoleHierarchy;
import egovframework.eam.admin.main.shared.role.RoleHierarchyService;

@Service("roleHierarchyService")
public class RoleHierarchyServiceImpl implements RoleHierarchyService {
	@Resource(name = "jpaRoleHierarchyDao")
	private JpaRoleHierarchyDAO roleHierarchyDao;
	
	private BeanOperator<RoleHierarchyInfo, RoleHierarchy> bean = new BeanOperator<RoleHierarchyInfo, RoleHierarchy>(RoleHierarchy.class);
	
	public List<RoleHierarchy> findAll() {
		List<RoleHierarchy> list = bean.cloneList(roleHierarchyDao.findAll());
		
		RoleHierarchy root = new RoleHierarchy();
		root.setParentRole("root");
		root.setChildRole("ROLE_RESTRICTED");
		
		list.add(root);
		
		return list;
	}
	
	public RoleHierarchy findById(RoleHierarchy roleHierarchy) {
		return bean.cloneBean(getEntity(roleHierarchy));
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void addRoleHierarchy(RoleHierarchy roleHierarchy) {
		RoleHierarchyInfo entity = getEntity(roleHierarchy);
		
		if (entity == null) {
			entity = new RoleHierarchyInfo();
			bean.copyBean(roleHierarchy, entity);
			
			roleHierarchyDao.persist(entity);
		}
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void deleteRoleHierarchy(RoleHierarchy roleHierarchy) {
		RoleHierarchyInfo entity = getEntity(roleHierarchy);
		
		if (entity != null) {
			roleHierarchyDao.remove(entity);
		}	
	}
	
	private RoleHierarchyInfo getEntity(RoleHierarchy roleHierarchy) {
		RoleHierarchyPK ids = new RoleHierarchyPK(roleHierarchy.getParentRole(), roleHierarchy.getChildRole());
		
		RoleHierarchyInfo entity = roleHierarchyDao.findByMultiId(ids);
		return entity;
	}
}
