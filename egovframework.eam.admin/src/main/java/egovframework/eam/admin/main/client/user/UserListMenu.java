package egovframework.eam.admin.main.client.user;

import com.smartgwt.client.widgets.Canvas;

import egovframework.eam.admin.main.client.MenuPanel;
import egovframework.eam.admin.main.client.PanelFactory;

public class UserListMenu extends MenuPanel {
	private static final String DESCRIPTION = "User management for authentication. Find, add, modify, and delete user info.";

	public static class Factory implements PanelFactory {
		private String id;

		public Canvas create() {
			UserListMenu panel = new UserListMenu();
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
		final UserManagement mgmt = new UserManagement();

		return mgmt;
	}

	public String getIntro() {
		return DESCRIPTION;
	}
}
