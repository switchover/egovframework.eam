package egovframework.eam.admin.main.client.session;

import com.smartgwt.client.widgets.Canvas;

import egovframework.eam.admin.main.client.MenuPanel;
import egovframework.eam.admin.main.client.PanelFactory;

public class SessionListMenu extends MenuPanel {
	private static final String DESCRIPTION = "System session management for remote communication. Search action only is possible.";

	public static class Factory implements PanelFactory {
		private String id;

		public Canvas create() {
			SessionListMenu panel = new SessionListMenu();
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
		final SessionManagement mgmt = new SessionManagement();

		return mgmt;
	}

	public String getIntro() {
		return DESCRIPTION;
	}
}