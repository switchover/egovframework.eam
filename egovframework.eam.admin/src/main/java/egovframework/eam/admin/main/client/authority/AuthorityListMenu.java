package egovframework.eam.admin.main.client.authority;

import com.smartgwt.client.widgets.Canvas;

import egovframework.eam.admin.main.client.MenuPanel;
import egovframework.eam.admin.main.client.PanelFactory;

public class AuthorityListMenu extends MenuPanel {
	private static final String DESCRIPTION = "Authority (user & role mapping) management. Find, add, modify, and delete mapping.";

	public static class Factory implements PanelFactory {
		private String id;

		public Canvas create() {
			AuthorityListMenu panel = new AuthorityListMenu();
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
		final AuthorityManagement mgmt = new AuthorityManagement();

		return mgmt;
	}

	public String getIntro() {
		return DESCRIPTION;
	}
}