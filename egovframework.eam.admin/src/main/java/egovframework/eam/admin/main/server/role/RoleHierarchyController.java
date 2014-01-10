package egovframework.eam.admin.main.server.role;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import egovframework.eam.admin.api.role.RoleHierarchyInfo;
import egovframework.eam.admin.api.role.RoleHierarchyInfoList;
import egovframework.eam.admin.main.server.util.BeanOperator;
import egovframework.eam.admin.main.shared.role.RoleHierarchy;
import egovframework.eam.admin.main.shared.role.RoleHierarchyService;

@Controller
public class RoleHierarchyController {
	private static final String XML_VIEW_NAME = "xml";

	@Resource(name = "roleHierarchyService")
	private RoleHierarchyService roleHierarchyService;
	
	private BeanOperator<RoleHierarchy, RoleHierarchyInfo> bean = new BeanOperator<RoleHierarchy, RoleHierarchyInfo>(RoleHierarchyInfo.class);
	
	@RequestMapping(method=RequestMethod.GET, value="/roleHierarchies")
	public ModelAndView getRoleHierarchies() {
		List<RoleHierarchy> roleHierarchies;
		
		roleHierarchies = roleHierarchyService.findAll();
	
		RoleHierarchyInfoList list = new RoleHierarchyInfoList(bean.cloneList(roleHierarchies));
		
		return new ModelAndView(XML_VIEW_NAME, "roleHierarchies", list);
	}
}
