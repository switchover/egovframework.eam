package egovframework.eam.admin.main.server.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import egovframework.eam.admin.api.resource.ResourceInfo;
import egovframework.eam.admin.api.resource.ResourceInfoList;
import egovframework.eam.admin.main.server.common.SearchVO;
import egovframework.eam.admin.main.server.util.BeanOperator;
import egovframework.eam.admin.main.shared.resource.Resource;
import egovframework.eam.admin.main.shared.resource.ResourceService;

@Controller
public class ResourceController {
	private static final String XML_VIEW_NAME = "xml";

	//@Resource(name = "resourceService")
	@Autowired
	private ResourceService resourceService;
	
	private BeanOperator<Resource, ResourceInfo> bean = new BeanOperator<Resource, ResourceInfo>(ResourceInfo.class);
	
	@RequestMapping(method=RequestMethod.GET, value="/resources")
	public ModelAndView getResources(SearchVO search) {
		List<Resource> resources;
		
		if (search.getSearchCondition().equals("resourceName")) {
			resources = resourceService.findWithLike("resource_name", search.getSearchWord());
		} else if (search.getSearchCondition().equals("resourceType")) {
			resources = resourceService.findWithLike("resource_type", search.getSubCondition());
		} else {
			resources = resourceService.findAll();
		}
		ResourceInfoList list = new ResourceInfoList(bean.cloneList(resources));
		
		return new ModelAndView(XML_VIEW_NAME, "resources", list);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/resource/{resourceId}")
	public ModelAndView getResource(@PathVariable String resourceId) {
		Resource resource = resourceService.findById(resourceId);

		return new ModelAndView(XML_VIEW_NAME, "object", bean.cloneBean(resource));
	}
}
