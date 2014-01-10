package egovframework.eam.admin.main.server.role;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import egovframework.eam.admin.api.role.RoleInfo;
import egovframework.eam.admin.api.role.RoleInfoList;
import egovframework.eam.admin.main.server.common.SearchVO;
import egovframework.eam.admin.main.server.util.BeanOperator;
import egovframework.eam.admin.main.shared.role.Role;
import egovframework.eam.admin.main.shared.role.RoleService;

@Controller
public class RoleController {
	private static final String XML_VIEW_NAME = "xml";

	@Resource(name = "roleService")
	private RoleService roleService;
	
	private BeanOperator<Role, RoleInfo> bean = new BeanOperator<Role, RoleInfo>(RoleInfo.class);
	
	@RequestMapping(method=RequestMethod.GET, value="/roles")
	public ModelAndView getRoles(SearchVO search) {
		List<Role> roles;
		
		if (search.getSearchCondition().equals("authority")) {
			roles = roleService.findWithLike("authority", search.getSearchWord());
		} else if (search.getSearchCondition().equals("roleName")) {
			roles = roleService.findWithLike("role_name", search.getSearchWord());
		} else {
			roles = roleService.findAll();
		}
		RoleInfoList list = new RoleInfoList(bean.cloneList(roles));
		
		return new ModelAndView(XML_VIEW_NAME, "roles", list);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/role/{authority}")
	public ModelAndView getRole(@PathVariable String authority) {
		Role role = roleService.findById(authority);

		return new ModelAndView(XML_VIEW_NAME, "object", bean.cloneBean(role));
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/roleOptions")
	public ModelAndView getRoleOptions() {
		List<Role> roles = roleService.findAll();
		
		List<Role> filteredRoles = new ArrayList<Role>();
			
		
		for (Role role : roles) {
			if (role.getAuthority().equals("ROLE_RESTRICTED") ||
					role.getAuthority().equals("ROLE_USER") ||
					role.getAuthority().equals("ROLE_ADMIN")) {
				// no-op
			} else {
				filteredRoles.add(role);
			}
		}
		
		RoleInfoList list = new RoleInfoList(bean.cloneList(filteredRoles));
		
		return new ModelAndView(XML_VIEW_NAME, "roleOptions", list);
	}
}
