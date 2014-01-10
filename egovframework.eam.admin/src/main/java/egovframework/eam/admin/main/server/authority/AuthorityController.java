package egovframework.eam.admin.main.server.authority;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import egovframework.eam.admin.api.authority.AuthorityInfo;
import egovframework.eam.admin.api.authority.AuthorityInfoList;
import egovframework.eam.admin.main.server.common.SearchVO;
import egovframework.eam.admin.main.server.util.BeanOperator;
import egovframework.eam.admin.main.shared.authority.Authority;
import egovframework.eam.admin.main.shared.authority.AuthorityService;

@Controller
public class AuthorityController {
	private static final String XML_VIEW_NAME = "xml";

	//@Resource(name = "authorityService")
	@Autowired
	private AuthorityService authorityService;
	
	private BeanOperator<Authority, AuthorityInfo> bean = new BeanOperator<Authority, AuthorityInfo>(AuthorityInfo.class);
	
	@RequestMapping(method=RequestMethod.GET, value="/authorities")
	public ModelAndView getAuthorities(SearchVO search) {
		List<Authority> authorities;
		
		if (search.getSearchCondition().equals("userName")) {
			authorities = authorityService.findWithLike("user_name", search.getSearchWord());
		} else if (search.getSearchCondition().equals("authority")) {
			authorities = authorityService.findWithLike("authority", search.getSubCondition());
		} else {
			authorities = authorityService.findAll();
		}
		AuthorityInfoList list = new AuthorityInfoList(bean.cloneList(authorities));
		
		return new ModelAndView(XML_VIEW_NAME, "authorities", list);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/authority/{userId}/{authority}")
	public ModelAndView getAuthority(@PathVariable String userId, @PathVariable String authority) {
		Authority ids = new Authority();
		
		ids.setUserId(userId);
		ids.setAuthority(authority);
		
		Authority role = authorityService.findById(ids);

		return new ModelAndView(XML_VIEW_NAME, "object", bean.cloneBean(role));
	}
}
