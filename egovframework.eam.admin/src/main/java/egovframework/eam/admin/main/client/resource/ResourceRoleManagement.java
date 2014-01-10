package egovframework.eam.admin.main.client.resource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionStyle;
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
import com.smartgwt.client.widgets.grid.HeaderSpan;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.CellContextClickEvent;
import com.smartgwt.client.widgets.grid.events.CellContextClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import egovframework.eam.admin.main.client.role.RoleDataSource;
import egovframework.eam.admin.main.shared.resource.ResourceRole;
import egovframework.eam.admin.main.shared.resource.ResourceRoleService;
import egovframework.eam.admin.main.shared.resource.ResourceRoleServiceAsync;

public class ResourceRoleManagement extends VLayout {
	private MappingListGrid itemList;

	private Menu listMenu;
	
    /** Create a remote service proxy to talk to the server-side resource role service. */
	private final ResourceRoleServiceAsync resourceRoleService = GWT.create(ResourceRoleService.class);

	public ResourceRoleManagement() {
		setWidth100();
		setHeight100();
		setLayoutMargin(20);

		ResourceRoleDataSource dataSource = ResourceRoleDataSource.getInstance();
		

		setupContextMenu();

		itemList = new MappingListGrid(dataSource);

		itemList.addCellContextClickHandler(new CellContextClickHandler() {
			public void onCellContextClick(CellContextClickEvent event) {
				listMenu.showContextMenu();
				event.cancel();
			}
		});
		
		ToolStripButton refreshButton = new ToolStripButton("Find all", "icons/16/export1.png");  
		refreshButton.setAutoFit(true);
		refreshButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				itemList.fetchData();
				itemList.invalidateCache();
			}
		});

		ToolStrip toolStrip = new ToolStrip();
		//toolStrip.setWidth(500);
		toolStrip.setWidth100();
		toolStrip.addFill();
		toolStrip.setAlign(Alignment.RIGHT);
		toolStrip.addButton(refreshButton);  
		
		addMember(toolStrip);
		addMember(itemList);
	}

	private void setupContextMenu() {
		listMenu = new Menu();
		listMenu.setCellHeight(22);

		final MenuItem addMenuItem = new MenuItem("Add Resource Mapping", "demoApp/icon_add.png");
		addMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			public void onClick(MenuItemClickEvent event) {
				
				final AddWindow winModal = new AddWindow(itemList);
				
				winModal.setResourceRoleService(resourceRoleService);
				
				winModal.show();
			}
		});

		final MenuItem deleteMenuItem = new MenuItem("Delete Resource Mapping", "silk/delete.png");
		deleteMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			public void onClick(MenuItemClickEvent event) {
				
				SC.ask("Delete", "Are you sure?", new BooleanCallback() {
					public void execute(Boolean value) {
						if (value == Boolean.TRUE) {
							
							String resourceId = itemList.getSelectedRecord().getAttribute("resourceId");
							String authority = itemList.getSelectedRecord().getAttribute("authority");
							
							ResourceRole resourceRole = new ResourceRole();
							
							resourceRole.setResourceId(resourceId);
							resourceRole.setAuthority(authority);
							
							resourceRoleService.deleteResourceRole(resourceRole, new AsyncCallback<Void>() {
			                    public void onFailure(Throwable caught) {
			                    	SC.say("Error", "Server side error occured. Contact your administrator.");
			                    }

			                    public void onSuccess(Void noAnswer) {
			                    	SC.say("Deleted", "Resource mapping is deleted.");
			                    	
			                    	itemList.invalidateCache();
			                    }
							});
						}
					}
				});
			}
		});

		listMenu.setData(addMenuItem, deleteMenuItem);
	}
}

class MappingListGrid extends ListGrid {

	public MappingListGrid(DataSource dataSource) {

		setDataSource(dataSource);
		setUseAllDataSourceFields(false);

		ListGridField resourceId = new ListGridField("resourceId", "Resource ID");
		resourceId.setCanEdit(false);
		resourceId.setShowHover(false);

		ListGridField resourceName = new ListGridField("resourceName", "Resource Name");
		resourceName.setShowHover(false);
		
		ListGridField resourceType = new ListGridField("resourceType", "Resource Type");
		resourceType.setShowHover(false);
		
		ListGridField resourcePattern = new ListGridField("resourcePattern", "Resource Pattern");
		resourcePattern.setShowHover(false);
		
		ListGridField authority = new ListGridField("authority", "Authority");
		authority.setShowHover(false);
		
		ListGridField roleName = new ListGridField("roleName", "Role Name");
		roleName.setShowHover(false);
		
		this.setShowFilterEditor(true);
		
		setFields(resourceId, resourceName, resourceType, resourcePattern, authority, roleName);
		
		setCanEdit(false);
		setAlternateRecordStyles(true);
		setCanDragRecordsOut(true);
		setHoverWidth(200);
		setHoverHeight(20);
		setSelectionType(SelectionStyle.SINGLE);
		
		setRecordEnabledProperty("canEdit");
		
		setHeaderHeight(50);
		setHeaderSpans(
				new HeaderSpan("Resource", new String[] {"resourceId", "resourceName", "resourceType", "resourcePattern"}),
				new HeaderSpan("Role", new String[] {"authority", "roleName"}));
	}
}

class AddWindow extends Window {
	private DynamicForm form = new DynamicForm();
	private ComboBoxItem resourceItems;
	private ComboBoxItem roleItems;
	
	private ResourceRoleServiceAsync service;
	
	public AddWindow(final MappingListGrid listGrid) {
		
		setWidth(360);
		setHeight(115);
		setTitle("Add Resource Mapping");
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
		
		resourceItems = new ComboBoxItem("resourceId");  
		resourceItems.setTitle("Resource");  
		resourceItems.setType("comboBox");  
		resourceItems.setRequired(true);
		resourceItems.setOptionDataSource(ResourceDataSource.getInstance());
		
		roleItems = new ComboBoxItem("authority");  
		roleItems.setTitle("Role");  
		roleItems.setType("comboBox");  
		roleItems.setRequired(true);
		roleItems.setOptionDataSource(RoleDataSource.getInstance());
		roleItems.setValue("ROLE_USER");
        
        ButtonItem addButton = new ButtonItem("addItem", "Add Mapping");
		addButton.setAlign(Alignment.CENTER);
		addButton.setWidth(100);
		addButton.setColSpan(2);
		
		addButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				if (! form.validate(false)) {
					return;
				}
				
				ResourceRole resourceRole = new ResourceRole();
				
				resourceRole.setResourceId(resourceItems.getDisplayValue());
				resourceRole.setAuthority(roleItems.getDisplayValue());
				
				service.addResourceRole(resourceRole, new AsyncCallback<Void>() {
                    public void onFailure(Throwable caught) {
                    	SC.say("Error", "Server side error occured. Contact your administrator.");
                    }

                    public void onSuccess(Void noAnswer) {
                    	SC.say("Added", "Resource Mapping is added.");
                    	
                    	listGrid.invalidateCache();
                    }
                });
				
				destroy();
			}
		});
		
		form.setFields(resourceItems, roleItems, addButton);
		
		addItem(form);
	}
	
	public void setResourceRoleService(ResourceRoleServiceAsync service) {
		this.service = service;
	}
}
