package egovframework.eam.admin.main.server.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import egovframework.eam.admin.api.resource.JpaResourceDAO;
import egovframework.eam.admin.api.resource.ResourceInfo;
import egovframework.eam.admin.main.server.util.BeanOperator;
import egovframework.eam.admin.main.shared.resource.Resource;
import egovframework.eam.admin.main.shared.resource.ResourceService;

@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {
	//@Resource(name = "jpaResourceDao")
	@Autowired
	private JpaResourceDAO resourceDao;
	
	private BeanOperator<ResourceInfo, Resource> bean = new BeanOperator<ResourceInfo, Resource>(Resource.class);
	
	public List<Resource> findAll() {
		return bean.cloneList(resourceDao.findAll());
	}
	
	public List<Resource> findWithLike(String fieldName, String value) {
		return bean.cloneList(resourceDao.findWithLike(fieldName, value));
	}
	
	public Resource findById(String authority) {
		return bean.cloneBean(resourceDao.findById(authority));
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void addResource(Resource resource) {
		ResourceInfo entity = resourceDao.findById(resource.getResourceId());
		
		if (entity == null) {
			entity = new ResourceInfo();
			bean.copyBean(resource, entity);
			
			resourceDao.persist(entity);
		}
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void updateResource(Resource resource) {
		ResourceInfo entity = resourceDao.findById(resource.getResourceId());
		
		if (entity != null) {
			bean.copyBean(resource, entity);
			
			resourceDao.merge(entity);
		}
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void deleteResource(String resourceId) {
		ResourceInfo entity = resourceDao.findById(resourceId);
		
		if (entity != null) {
			resourceDao.remove(entity);
		}	
	}
}
