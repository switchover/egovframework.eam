package egovframework.eam.admin.main.client.resource;

import com.smartgwt.client.widgets.Canvas;

import egovframework.eam.admin.main.client.MenuPanel;
import egovframework.eam.admin.main.client.PanelFactory;

public class ResourceRoleListMenu extends MenuPanel {
	private static final String DESCRIPTION = "Resource role mapping management. Find, add, modify, and delete mapping.";

	public static class Factory implements PanelFactory {
		private String id;

		public Canvas create() {
			ResourceRoleListMenu panel = new ResourceRoleListMenu();
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
		final ResourceRoleManagement mgmt = new ResourceRoleManagement();

		return mgmt;
	}

	public String getIntro() {
		return DESCRIPTION;
	}
}