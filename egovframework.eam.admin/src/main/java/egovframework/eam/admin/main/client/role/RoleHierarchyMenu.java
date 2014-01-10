package egovframework.eam.admin.main.client.role;

import com.smartgwt.client.widgets.Canvas;

import egovframework.eam.admin.main.client.MenuPanel;
import egovframework.eam.admin.main.client.PanelFactory;

public class RoleHierarchyMenu extends MenuPanel {
	private static final String DESCRIPTION = "Role hierarchy management. Add and delete role hierarchy.";

	public static class Factory implements PanelFactory {
		private String id;

		public Canvas create() {
			RoleHierarchyMenu panel = new RoleHierarchyMenu();
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
		final RoleHierarchyManagement mgmt = new RoleHierarchyManagement();

		return mgmt;
	}

	public String getIntro() {
		return DESCRIPTION;
	}
}