package egovframework.eam.admin.main.client.system;

import com.smartgwt.client.widgets.Canvas;

import egovframework.eam.admin.main.client.MenuPanel;
import egovframework.eam.admin.main.client.PanelFactory;

public class SystemListMenu extends MenuPanel {
	private static final String DESCRIPTION = "System management for SSO. Find, add, modify, and delete system.";
	
	public static class Factory implements PanelFactory {
		private String id;

		public Canvas create() {
			SystemListMenu panel = new SystemListMenu();
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
		final SystemManagement mgmt = new SystemManagement();

		return mgmt;
	}

	public String getIntro() {
		return DESCRIPTION;
	}
}