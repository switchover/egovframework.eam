package egovframework.eam.admin.main.client.data;

import egovframework.eam.admin.main.client.authority.AuthorityListMenu;
import egovframework.eam.admin.main.client.resource.ResourceListMenu;
import egovframework.eam.admin.main.client.resource.ResourceRoleListMenu;
import egovframework.eam.admin.main.client.role.RoleHierarchyMenu;
import egovframework.eam.admin.main.client.role.RoleListMenu;
import egovframework.eam.admin.main.client.sample.UnderConstructionMenu;
import egovframework.eam.admin.main.client.session.SessionListMenu;
import egovframework.eam.admin.main.client.system.SystemListMenu;
import egovframework.eam.admin.main.client.user.UserListMenu;

public class MenuData {
	    private String idSuffix;

	    public MenuData(String idSuffix) {
	        this.idSuffix = idSuffix;
	    }

	    private ExplorerTreeNode[] data;

	    private ExplorerTreeNode[] getData() {
	        if (data == null) {
	            data = new ExplorerTreeNode[]{
	            		new ExplorerTreeNode("User Management", "user", "root", "silk/application_cascade.png", null, true, idSuffix),
	                    new ExplorerTreeNode("User List", "user-user", "user", "icons/16/person.png", new UserListMenu.Factory(), true, idSuffix),
	            		
	                    new ExplorerTreeNode("Role Management", "role", "root", "silk/application_view_detail.png", null, true, idSuffix),
	                    new ExplorerTreeNode("Role List", "role-role", "role", "silk/brick.png", new RoleListMenu.Factory(), true, idSuffix),
	                    new ExplorerTreeNode("Role Hierarchy", "role-hierarchy", "role", "silk/chart_organisation.png", new RoleHierarchyMenu.Factory(), true, idSuffix),
	                    
	                    new ExplorerTreeNode("Resource Management", "resource", "root", "silk/application_form.png", null, true, idSuffix),
	                    new ExplorerTreeNode("Resource List", "resource-resource", "resource", "silk/coins.png", new ResourceListMenu.Factory(), true, idSuffix),
	                    new ExplorerTreeNode("Resource Mapping", "resource-mapping", "resource", "silk/arrow_refresh_small.png", new ResourceRoleListMenu.Factory(), true, idSuffix),
	                    
	                    new ExplorerTreeNode("Authority Management", "authority", "root", "silk/application_put.png", null, true, idSuffix),
	                    new ExplorerTreeNode("Authority List", "authority-authority", "authority", "icons/16/approved.png", new AuthorityListMenu.Factory(), true, idSuffix),
	                    
	                    new ExplorerTreeNode("System Management", "system", "root", "silk/application_side_boxes.png", null, true, idSuffix),
	                    new ExplorerTreeNode("System List", "system-system", "system", "icons/16/printer3.png", new SystemListMenu.Factory(), true, idSuffix),
	                    new ExplorerTreeNode("Session List", "system-session", "system", "silk/connect.png", new SessionListMenu.Factory(), true, idSuffix),
	                    
	                    new ExplorerTreeNode("Log & Audit Management", "log", "root", "silk/application_side_list.png", null, true, idSuffix),
	                    new ExplorerTreeNode("Authentication List", "authentication-log", "log", "silk/database_table.png", new UnderConstructionMenu.Factory(), true, idSuffix),
	                    new ExplorerTreeNode("Log List", "log-log", "log", "silk/database_table.png", new UnderConstructionMenu.Factory(), true, idSuffix),
	                    new ExplorerTreeNode("Audit List", "log-audit", "log", "silk/layers.png", new UnderConstructionMenu.Factory(), true, idSuffix),
	                    
	                    new ExplorerTreeNode("Configuration Management", "config", "root", "silk/application_view_tile.png", null, true, idSuffix),
	                    new ExplorerTreeNode("Admin. Console Setting", "config-setting", "config", "silk/overlays.png", new UnderConstructionMenu.Factory(), true, idSuffix),
	                    new ExplorerTreeNode("Admin. User List", "config-admin", "config", "icons/16/person_into.png", new UnderConstructionMenu.Factory(), true, idSuffix)
	            };
	        }
	        return data;
	    }

	    public static ExplorerTreeNode[] getData(String idSuffix) {
	        return new MenuData(idSuffix).getData();
	    }
	}
