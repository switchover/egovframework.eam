package egovframework.eam.admin.main.client.role;

import com.smartgwt.client.widgets.Canvas;

import egovframework.eam.admin.main.client.MenuPanel;
import egovframework.eam.admin.main.client.PanelFactory;

public class RoleListMenu extends MenuPanel {
	private static final String DESCRIPTION = "Role management for authority. Find, add, modify, and delete role info.";

	public static class Factory implements PanelFactory {
		private String id;

		public Canvas create() {
			RoleListMenu panel = new RoleListMenu();
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
		final RoleManagement mgmt = new RoleManagement();

		return mgmt;
	}

	public String getIntro() {
		return DESCRIPTION;
	}
}