package egovframework.eam.admin.main.client.resource;

import com.smartgwt.client.widgets.Canvas;

import egovframework.eam.admin.main.client.MenuPanel;
import egovframework.eam.admin.main.client.PanelFactory;

public class ResourceListMenu extends MenuPanel {
	private static final String DESCRIPTION = "Resource management for authority. Find, add, modify, and delete secured resource";
	
	public static class Factory implements PanelFactory {
		private String id;

		public Canvas create() {
			ResourceListMenu panel = new ResourceListMenu();
			id = panel.getID();
			return panel;
		}

		public String getID() {
			return id;
		}

		public String getDescription() {
			return DESCRIPTION;
		}
	}

	public Canvas getViewPanel() {
		final ResourceManagement mgmt = new ResourceManagement();

		return mgmt;
	}

	public String getIntro() {
		return DESCRIPTION;
	}
}