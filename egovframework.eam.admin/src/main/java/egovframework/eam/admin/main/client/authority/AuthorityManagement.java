package egovframework.eam.admin.main.client.authority;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.GroupStartOpen;
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
import egovframework.eam.admin.main.client.user.UserDataSource;
import egovframework.eam.admin.main.shared.authority.Authority;
import egovframework.eam.admin.main.shared.authority.AuthorityService;
import egovframework.eam.admin.main.shared.authority.AuthorityServiceAsync;

public class AuthorityManagement extends VLayout {
	private MappingListGrid itemList;

	private Menu listMenu;
	
    /** Create a remote service proxy to talk to the server-side resource role service. */
	private final AuthorityServiceAsync authorityService = GWT.create(AuthorityService.class);

	public AuthorityManagement() {
		setWidth100();
		setHeight100();
		setLayoutMargin(20);

		AuthorityDataSource dataSource = AuthorityDataSource.getInstance();
		

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

		final MenuItem addMenuItem = new MenuItem("Add Authority", "demoApp/icon_add.png");
		addMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			public void onClick(MenuItemClickEvent event) {
				
				final AddWindow winModal = new AddWindow(itemList);
				
				winModal.setAuthorityService(authorityService);
				
				winModal.show();
			}
		});

		final MenuItem deleteMenuItem = new MenuItem("Delete Authority", "silk/delete.png");
		deleteMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			public void onClick(MenuItemClickEvent event) {
				
				SC.ask("Delete", "Are you sure?", new BooleanCallback() {
					public void execute(Boolean value) {
						if (value == Boolean.TRUE) {
							
							String userId = itemList.getSelectedRecord().getAttribute("userId");
							String authority = itemList.getSelectedRecord().getAttribute("authority");
							
							Authority role = new Authority();
							
							role.setUserId(userId);
							role.setAuthority(authority);
							
							authorityService.deleteAuthority(role, new AsyncCallback<Void>() {
			                    public void onFailure(Throwable caught) {
			                    	SC.say("Error", "Server side error occured. Contact your administrator.");
			                    }

			                    public void onSuccess(Void noAnswer) {
			                    	SC.say("Deleted", "Authority is deleted.");
			                    	
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

		ListGridField userId = new ListGridField("userId", "User ID");
		userId.setCanEdit(false);
		userId.setShowHover(false);

		ListGridField userName = new ListGridField("userName", "User Name");
		userName.setShowHover(false);
		
		ListGridField authority = new ListGridField("authority", "Authority");
		authority.setShowHover(false);
		
		ListGridField roleName = new ListGridField("roleName", "Role Name");
		roleName.setShowHover(false);
		
		this.setShowFilterEditor(true);
		
		setFields(userId, userName, authority, roleName);
		
		setCanEdit(false);
		setAlternateRecordStyles(true);
		setCanDragRecordsOut(true);
		setHoverWidth(200);
		setHoverHeight(20);
		setSelectionType(SelectionStyle.SINGLE);
		
		setRecordEnabledProperty("canEdit");
		
		setHeaderHeight(50);
		setHeaderSpans(
				new HeaderSpan("User", new String[] {"userId", "userName"}),
				new HeaderSpan("Role", new String[] {"authority", "roleName"}));
		
		setGroupStartOpen(GroupStartOpen.ALL);  
        setGroupByField("userId"); 
	}
}

class AddWindow extends Window {
	private DynamicForm form = new DynamicForm();
	private ComboBoxItem userItems;
	private ComboBoxItem roleItems;
	
	private AuthorityServiceAsync service;
	
	public AddWindow(final MappingListGrid listGrid) {
		
		setWidth(360);
		setHeight(115);
		setTitle("Add Authority");
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
		
		userItems = new ComboBoxItem("userId");  
		userItems.setTitle("User");  
		userItems.setType("comboBox");  
		userItems.setRequired(true);
		userItems.setOptionDataSource(UserDataSource.getInstance());
		
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
				
				Authority authority = new Authority();
				
				authority.setUserId(userItems.getDisplayValue());
				authority.setAuthority(roleItems.getDisplayValue());
				
				service.addAuthority(authority, new AsyncCallback<Void>() {
                    public void onFailure(Throwable caught) {
                    	SC.say("Error", "Server side error occured. Contact your administrator.");
                    }

                    public void onSuccess(Void noAnswer) {
                    	SC.say("Added", "Authority Mapping is added.");
                    	
                    	listGrid.invalidateCache();
                    }
                });
				
				destroy();
			}
		});
		
		form.setFields(userItems, roleItems, addButton);
		
		addItem(form);
	}
	
	public void setAuthorityService(AuthorityServiceAsync service) {
		this.service = service;
	}
}
