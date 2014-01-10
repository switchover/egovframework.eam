package egovframework.eam.admin.main.client.role;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.CloseClientEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.grid.events.CellContextClickEvent;
import com.smartgwt.client.widgets.grid.events.CellContextClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.events.DataArrivedEvent;
import com.smartgwt.client.widgets.tree.events.DataArrivedHandler;

import egovframework.eam.admin.main.shared.role.RoleHierarchy;
import egovframework.eam.admin.main.shared.role.RoleHierarchyService;
import egovframework.eam.admin.main.shared.role.RoleHierarchyServiceAsync;

public class RoleHierarchyManagement extends VLayout {
	private ItemTreeGrid treeGrid;

	private Menu treeMenu;
	
    /** Create a remote service proxy to talk to the server-side role hierarchy service. */
	private final RoleHierarchyServiceAsync roleHierarchyService = GWT.create(RoleHierarchyService.class);

	public RoleHierarchyManagement() {
		setWidth100();
		setHeight100();
		setLayoutMargin(20);

		RoleHierarchyDataSource dataSource = RoleHierarchyDataSource.getInstance();
		
		setupContextMenu();
		
		treeGrid = new ItemTreeGrid(dataSource);
		
		treeGrid.addCellContextClickHandler(new CellContextClickHandler() {
			public void onCellContextClick(CellContextClickEvent event) {
				treeMenu.showContextMenu();
				event.cancel();
			}
		});

	    ToolStripButton refreshButton = new ToolStripButton("Refresh", "icons/16/export1.png");  
		refreshButton.setAutoFit(true);
		refreshButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				treeGrid.fetchData();
				treeGrid.invalidateCache();
			}
		});
	        
		ToolStrip toolStrip = new ToolStrip();
		toolStrip.setWidth(500);
		toolStrip.addFill();
		toolStrip.setAlign(Alignment.RIGHT);
		toolStrip.addButton(refreshButton);  
		
		addMember(toolStrip);
		addMember(treeGrid);
	}

	private void setupContextMenu() {
		treeMenu = new Menu();
		treeMenu.setCellHeight(22);

		final MenuItem addMenuItem = new MenuItem("Add Role Hierarchy", "demoApp/icon_add.png");
		addMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			public void onClick(MenuItemClickEvent event) {
				
				final AddWindow winModal = new AddWindow(treeGrid.getSelectedRecord().getAttribute("childRole"), treeGrid);
				
				winModal.setRoleHierarchyService(roleHierarchyService);
				
				winModal.show();
			}
		});

		final MenuItem deleteMenuItem = new MenuItem("Delete Role Hierarchy", "silk/delete.png");
		deleteMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			public void onClick(MenuItemClickEvent event) {
				
				//String parentRole = treeGrid.getSelectedRecord().getAttribute("parentRole");
				String childRole = treeGrid.getSelectedRecord().getAttribute("childRole");
				
				if (childRole.equals("ROLE_RESTRICTED") || childRole.equals("ROLE_USER") || childRole.equals("ROLE_ADMIN")) {
					SC.say("Alert", "'ROLE_RESTRICTED', 'ROLE_USER' or 'ROLE_ADMIN' child role can't be deleted!");
					return;
				}
				
				SC.ask("Delete", "Are you sure?", new BooleanCallback() {
					public void execute(Boolean value) {
						if (value == Boolean.TRUE) {
							
							String parentRole = treeGrid.getSelectedRecord().getAttribute("parentRole");
							String childRole = treeGrid.getSelectedRecord().getAttribute("childRole");
							
							RoleHierarchy roleHierachy = new RoleHierarchy();
							
							roleHierachy.setParentRole(parentRole);
							roleHierachy.setChildRole(childRole);
							
							roleHierarchyService.deleteRoleHierarchy(roleHierachy, new AsyncCallback<Void>() {
			                    public void onFailure(Throwable caught) {
			                    	SC.say("Error", "Server side error occured. Contact your administrator.");
			                    }

			                    public void onSuccess(Void noAnswer) {
			                    	SC.say("Deleted", "Role hierarchy is deleted.");
			                    	
			                    	treeGrid.invalidateCache();
			                    }
							});
						}
					}
				});
			}
		});

		treeMenu.setData(addMenuItem, deleteMenuItem);
	}
}

class ItemTreeGrid extends TreeGrid {

	public ItemTreeGrid(DataSource dataSource) {
		
		setLoadDataOnDemand(false);          
        setWidth(500);  
        setHeight(400);  
        setDataSource(dataSource);  
        setAutoFetchData(true);  
        setShowConnectors(true);         
        setNodeIcon("icons/16/world.png");  
        setFolderIcon("icons/16/world.png");  
        setShowOpenIcons(false);  
        setShowDropIcons(false);  
        setClosedIconSuffix("");
        setBaseStyle("noBorderCell");  
        setFields(new TreeGridField("childRole", "Role Hierarchy"));  
  
        addDataArrivedHandler(new DataArrivedHandler() {  
            public void onDataArrived(DataArrivedEvent event) {  
                getData().openAll();  
            }  
        });  
          
        draw();
	}
}

class AddWindow extends Window {
	private DynamicForm form = new DynamicForm();
	private ComboBoxItem cbItem;
	
	private final RoleHierarchy hierarchy = new RoleHierarchy();
	
	private RoleHierarchyServiceAsync service;
	
	public AddWindow(String parentRole, final ItemTreeGrid treeGrid) {
		hierarchy.setParentRole(parentRole);
		
		setWidth(360);
		setHeight(115);
		setTitle("Add Role Hierarchy");
		setShowMinimizeButton(false);
		setIsModal(true);
		setShowModalMask(true);
		centerInPage();
		
		addCloseClickHandler(new CloseClickHandler() {
			public void onCloseClick(CloseClientEvent event) {
				destroy();
			}
		});

		form.setHeight100();
		form.setWidth100();
		form.setPadding(5);
		form.setLayoutAlign(VerticalAlignment.BOTTOM);
		
		cbItem = new ComboBoxItem("childRole");  
        cbItem.setTitle("Child role");  
        cbItem.setType("comboBox");  
        cbItem.setRequired(true);
        cbItem.setOptionDataSource(RoleOptionDataSource.getInstance());
        
        ButtonItem addButton = new ButtonItem("addItem", "Add Hierarchy");
		addButton.setAlign(Alignment.CENTER);
		addButton.setWidth(100);
		addButton.setColSpan(2);
		
		addButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				if (! form.validate(false)) {
					return;
				}
				
				hierarchy.setChildRole(cbItem.getDisplayValue());

				//SC.say(hierarchy.getParentRole() + " : " + hierarchy.getChildRole());
				
				if (hierarchy.getParentRole().equals(hierarchy.getChildRole())) {
					SC.say("Warning", "Sample roles are selected!!");
					return;
				} else {
				
					RoleHierarchy roleHierarchy = new RoleHierarchy();
					
					roleHierarchy.setParentRole(hierarchy.getParentRole());
					roleHierarchy.setChildRole(hierarchy.getChildRole());
					
					service.addRoleHierarchy(roleHierarchy, new AsyncCallback<Void>() {
	                    public void onFailure(Throwable caught) {
	                    	SC.say("Error", "Server side error occured. Contact your administrator.");
	                    }
	
	                    public void onSuccess(Void noAnswer) {
	                    	SC.say("Added", "Role hierarchy is added.");
	                    	
	                    	treeGrid.invalidateCache();
	                    }
	                });
				}
				
				destroy();
			}
		});
		
		form.setFields(cbItem, addButton);
		
		addItem(form);
	}
	
	public void setRoleHierarchyService(RoleHierarchyServiceAsync service) {
		this.service = service;
	}
}