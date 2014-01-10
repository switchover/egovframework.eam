package egovframework.eam.admin.main.server.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import egovframework.eam.admin.api.resource.ResourceRoleInfo;
import egovframework.eam.admin.api.resource.ResourceRoleInfoList;
import egovframework.eam.admin.main.server.common.SearchVO;
import egovframework.eam.admin.main.server.util.BeanOperator;
import egovframework.eam.admin.main.shared.resource.ResourceRole;
import egovframework.eam.admin.main.shared.resource.ResourceRoleService;

@Controller
public class ResourceRoleController {
	private static final String XML_VIEW_NAME = "xml";

	//@Resource(name = "resourceRoleService")
	@Autowired
	private ResourceRoleService resourceRoleService;
	
	private BeanOperator<ResourceRole, ResourceRoleInfo> bean = new BeanOperator<ResourceRole, ResourceRoleInfo>(ResourceRoleInfo.class);
	
	@RequestMapping(method=RequestMethod.GET, value="/resourceRoles")
	public ModelAndView getResourceRoles(SearchVO search) {
		List<ResourceRole> resourceRoles;
		
		if (search.getSearchCondition().equals("resourceName")) {
			resourceRoles = resourceRoleService.findWithLike("resource_name", search.getSearchWord());
		} else if (search.getSearchCondition().equals("resourceType")) {
			resourceRoles = resourceRoleService.findWithLike("resource_type", search.getSubCondition());
		} else {
			resourceRoles = resourceRoleService.findAll();
		}
		ResourceRoleInfoList list = new ResourceRoleInfoList(bean.cloneList(resourceRoles));
		
		return new ModelAndView(XML_VIEW_NAME, "resourceRoles", list);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/resource/{resourceId}/{authority}")
	public ModelAndView getResourceRole(@PathVariable String resourceId, @PathVariable String authority) {
		ResourceRole ids = new ResourceRole();
		
		ids.setResourceId(resourceId);
		ids.setAuthority(authority);
		
		ResourceRole resourceRole = resourceRoleService.findById(ids);

		return new ModelAndView(XML_VIEW_NAME, "object", bean.cloneBean(resourceRole));
	}
}
