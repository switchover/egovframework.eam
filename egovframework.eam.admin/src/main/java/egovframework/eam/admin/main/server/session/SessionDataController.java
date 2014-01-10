package egovframework.eam.admin.main.server.session;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import egovframework.eam.admin.api.session.SessionDataInfo;
import egovframework.eam.admin.api.session.SessionDataInfoList;
import egovframework.eam.admin.main.server.common.SearchVO;
import egovframework.eam.admin.main.server.util.BeanOperator;
import egovframework.eam.admin.main.shared.session.SessionData;
import egovframework.eam.admin.main.shared.session.SessionDataService;

@Controller
public class SessionDataController {
	private static final String XML_VIEW_NAME = "xml";

	@Resource(name = "sessionDataService")
	private SessionDataService sessionDataService;
	
	private BeanOperator<SessionData, SessionDataInfo> bean = new BeanOperator<SessionData, SessionDataInfo>(SessionDataInfo.class);
	
	@RequestMapping(method=RequestMethod.GET, value="/sessions")
	public ModelAndView getSessions(SearchVO search) {
		List<SessionData> sessions;
		
		if (search.getSearchCondition().equals("systemId")) {
			sessions = sessionDataService.findWithLike("system_id", search.getSearchWord());
		} else if (search.getSearchCondition().equals("systemName")) {
			sessions = sessionDataService.findWithLike("system_name", search.getSearchWord());
		} else {
			sessions = sessionDataService.findAll();
		}
		SessionDataInfoList list = new SessionDataInfoList(bean.cloneList(sessions));
		
		return new ModelAndView(XML_VIEW_NAME, "sessions", list);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/session/{systemId}")
	public ModelAndView getSession(@PathVariable String systemId) {
		SessionData system = sessionDataService.findById(systemId);

		return new ModelAndView(XML_VIEW_NAME, "object", bean.cloneBean(system));
	}
}
