package egovframework.eam.admin.main.server.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import egovframework.eam.admin.api.resource.JpaResourceDAO;
import egovframework.eam.admin.api.resource.JpaResourceRoleDAO;
import egovframework.eam.admin.api.resource.ResourceInfo;
import egovframework.eam.admin.api.resource.ResourceRoleInfo;
import egovframework.eam.admin.api.resource.ResourceRolePK;
import egovframework.eam.admin.api.role.JpaRoleDAO;
import egovframework.eam.admin.api.role.RoleInfo;
import egovframework.eam.admin.main.server.util.BeanOperator;
import egovframework.eam.admin.main.shared.resource.Resource;
import egovframework.eam.admin.main.shared.resource.ResourceRole;
import egovframework.eam.admin.main.shared.resource.ResourceRoleService;
import egovframework.eam.admin.main.shared.role.Role;

@Service("resourceRoleService")
public class ResourceRoleServiceImpl implements ResourceRoleService {
	//@Resource(name = "jpaResourceDao")
	@Autowired
	private JpaResourceRoleDAO resourceRoleDao;
	
	@Autowired
	private JpaResourceDAO resourceDao;
	
	@Autowired
	private JpaRoleDAO roleDao;
	
	private BeanOperator<ResourceRoleInfo, ResourceRole> bean = new BeanOperator<ResourceRoleInfo, ResourceRole>(ResourceRole.class);
	private BeanOperator<ResourceInfo, Resource> resourceBean = new BeanOperator<ResourceInfo, Resource>(Resource.class);
	private BeanOperator<RoleInfo, Role> roleBean = new BeanOperator<RoleInfo, Role>(Role.class);
	
	
	public List<ResourceRole> findAll() {
		List<ResourceRoleInfo> resourceRoles = resourceRoleDao.findAll();
		
		for (ResourceRoleInfo resourceRole : resourceRoles) {
			resourceRole.setResourceId(resourceRole.getResourceRolePK().getResourceId());
			resourceRole.setAuthority(resourceRole.getResourceRolePK().getAuthority());
			
			resourceRole.setResource(resourceBean.cloneBean(resourceDao.findById(resourceRole.getResourceId())));
			resourceRole.setRole(roleBean.cloneBean(roleDao.findById(resourceRole.getAuthority())));
		}
		
		return bean.cloneList(resourceRoles);
	}
	
	public List<ResourceRole> findWithLike(String fieldName, String value) {
		List<ResourceRoleInfo> resourceRoles = resourceRoleDao.findWithLike(fieldName, value);
		
		for (ResourceRoleInfo resourceRole : resourceRoles) {
			resourceRole.setResourceId(resourceRole.getResourceRolePK().getResourceId());
			resourceRole.setAuthority(resourceRole.getResourceRolePK().getAuthority());
			
			resourceRole.setResource(resourceBean.cloneBean(resourceDao.findById(resourceRole.getResourceId())));
			resourceRole.setRole(roleBean.cloneBean(roleDao.findById(resourceRole.getAuthority())));
		}
		
		return bean.cloneList(resourceRoles);
	}
	
	public ResourceRole findById(ResourceRole resourceRole) {
		ResourceRoleInfo entity = getEntity(resourceRole);
		
		entity.setResourceId(entity.getResourceRolePK().getResourceId());
		entity.setAuthority(entity.getResourceRolePK().getAuthority());
		
		entity.setResource(resourceBean.cloneBean(resourceDao.findById(entity.getResourceId())));
		entity.setRole(roleBean.cloneBean(roleDao.findById(entity.getAuthority())));
		
		return bean.cloneBean(entity);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void addResourceRole(ResourceRole resourceRole) {
		ResourceRoleInfo entity = getEntity(resourceRole);
		
		if (entity == null) {
			entity = new ResourceRoleInfo();
			bean.copyBean(resourceRole, entity);
			
			ResourceRolePK ids = new ResourceRolePK(resourceRole.getResourceId(), resourceRole.getAuthority());
			entity.setResourceRolePK(ids);
			
			resourceRoleDao.persist(entity);
		}
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void updateResourceRole(ResourceRole resourceRole) {
		ResourceRoleInfo entity = getEntity(resourceRole);
		
		if (entity != null) {
			bean.copyBean(resourceRole, entity);
			
			ResourceRolePK ids = new ResourceRolePK(resourceRole.getResourceId(), resourceRole.getAuthority());
			entity.setResourceRolePK(ids);

			resourceRoleDao.merge(entity);
		}
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void deleteResourceRole(ResourceRole resourceRole) {
		ResourceRoleInfo entity = getEntity(resourceRole);
		
		if (entity != null) {
			resourceRoleDao.remove(entity);
		}	
	}

	private ResourceRoleInfo getEntity(ResourceRole resourceRole) {
		ResourceRolePK ids = new ResourceRolePK(resourceRole.getResourceId(), resourceRole.getAuthority());
		
		ResourceRoleInfo entity = resourceRoleDao.findByMultiId(ids);
		return entity;
	}
}
