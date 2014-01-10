package egovframework.eam.admin.main.client.sample;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.layout.VLayout;

import egovframework.eam.admin.main.client.MenuPanel;
import egovframework.eam.admin.main.client.PanelFactory;

public class UnderConstructionMenu extends MenuPanel {
    private static final String DESCRIPTION = "Under construction.";

     public static class Factory implements PanelFactory {
         private String id;

         public Canvas create() {
        	 UnderConstructionMenu panel = new UnderConstructionMenu();
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

    	HTMLPane html = new HTMLPane();
    	 
    	html.setContentsURL("under/default.html");
    	html.setOverflow(Overflow.AUTO);
    	html.setStyleName("defaultBorder");
    	html.setPadding(10);

		VLayout layout = new VLayout();

		layout.setHeight100();
		layout.setWidth100();
		
		layout.setMembersMargin(30);
		layout.addMember(html);

		return layout;
     }

     public String getIntro() {
         return DESCRIPTION;
     }
}
