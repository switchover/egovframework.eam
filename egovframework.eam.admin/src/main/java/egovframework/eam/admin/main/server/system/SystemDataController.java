package egovframework.eam.admin.main.server.system;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import egovframework.eam.admin.api.system.SystemDataInfo;
import egovframework.eam.admin.api.system.SystemDataInfoList;
import egovframework.eam.admin.main.server.common.SearchVO;
import egovframework.eam.admin.main.server.util.BeanOperator;
import egovframework.eam.admin.main.shared.system.SystemData;
import egovframework.eam.admin.main.shared.system.SystemDataService;

@Controller
public class SystemDataController {
	private static final String XML_VIEW_NAME = "xml";

	@Resource(name = "systemDataService")
	private SystemDataService systemDataService;
	
	private BeanOperator<SystemData, SystemDataInfo> bean = new BeanOperator<SystemData, SystemDataInfo>(SystemDataInfo.class);
	
	@RequestMapping(method=RequestMethod.GET, value="/systems")
	public ModelAndView getSystems(SearchVO search) {
		List<SystemData> systems;
		
		if (search.getSearchCondition().equals("systemId")) {
			systems = systemDataService.findWithLike("system_id", search.getSearchWord());
		} else if (search.getSearchCondition().equals("systemName")) {
			systems = systemDataService.findWithLike("system_name", search.getSearchWord());
		} else {
			systems = systemDataService.findAll();
		}
		SystemDataInfoList list = new SystemDataInfoList(bean.cloneList(systems));
		
		return new ModelAndView(XML_VIEW_NAME, "systems", list);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/system/{systemId}")
	public ModelAndView getSystem(@PathVariable String systemId) {
		SystemData system = systemDataService.findById(systemId);

		return new ModelAndView(XML_VIEW_NAME, "object", bean.cloneBean(system));
	}
}
