package egovframework.eam.admin.main.client.role;

import java.util.LinkedHashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.CellContextClickEvent;
import com.smartgwt.client.widgets.grid.events.CellContextClickHandler;
import com.smartgwt.client.widgets.grid.events.CellSavedEvent;
import com.smartgwt.client.widgets.grid.events.CellSavedHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;
import com.smartgwt.client.widgets.viewer.DetailViewer;

import egovframework.eam.admin.main.shared.role.Role;
import egovframework.eam.admin.main.shared.role.RoleService;
import egovframework.eam.admin.main.shared.role.RoleServiceAsync;

public class RoleManagement extends HLayout {
	private SearchForm searchForm;

	private ItemListGrid itemList;

	private DetailTabPane detailTabPane;

	private Menu listMenu;

	public RoleManagement() {
		setWidth100();
		setHeight100();
		setLayoutMargin(20);

		RoleDataSource dataSource = RoleDataSource.getInstance();
		
		searchForm = new SearchForm(dataSource);

		//final ComboBoxItem searchConditions = searchForm.getSearchConditionField();

		setupContextMenu();

		itemList = new ItemListGrid(dataSource);
		itemList.addRecordClickHandler(new RecordClickHandler() {
			public void onRecordClick(RecordClickEvent event) {
				detailTabPane.updateDetails();
			}
		});

		itemList.addCellSavedHandler(new CellSavedHandler() {
			public void onCellSaved(CellSavedEvent event) {
				detailTabPane.updateDetails();
			}
		});

		itemList.addCellContextClickHandler(new CellContextClickHandler() {
			public void onCellContextClick(CellContextClickEvent event) {
				listMenu.showContextMenu();
				event.cancel();
			}
		});

		SectionStack sessionLayout = new SectionStack();
		sessionLayout.setVisibilityMode(VisibilityMode.MULTIPLE);
		sessionLayout.setAnimateSections(true);

		searchForm.setHeight(60);
		searchForm.addFindListener(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
			public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
				findRoles();
			}
		});

		SectionStackSection findSection = new SectionStackSection("Find Roles");
		findSection.setItems(searchForm);
		findSection.setExpanded(true);

		SectionStackSection listSection = new SectionStackSection("Role List");
		listSection.setItems(itemList);
		listSection.setExpanded(true);

		detailTabPane = new DetailTabPane(dataSource, itemList);
		SectionStackSection detailsSection = new SectionStackSection("Role Details");
		detailsSection.setItems(detailTabPane);
		detailsSection.setExpanded(true);

		sessionLayout.setSections(findSection, listSection, detailsSection);

		addMember(sessionLayout);
	}

	private void setupContextMenu() {
		listMenu = new Menu();
		listMenu.setCellHeight(22);

		MenuItem detailsMenuItem = new MenuItem("Show Details", "silk/application_form.png");
		detailsMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			public void onClick(MenuItemClickEvent event) {
				detailTabPane.selectTab(0);
				detailTabPane.updateDetails();
			}
		});

		final MenuItem editMenuItem = new MenuItem("Edit Role", "demoApp/icon_edit.png");
		editMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			public void onClick(MenuItemClickEvent event) {
				detailTabPane.selectTab(1);
				detailTabPane.updateDetails();
			}
		});

		final MenuItem deleteMenuItem = new MenuItem("Delete Role", "silk/delete.png");
		deleteMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			public void onClick(MenuItemClickEvent event) {
				
				SC.ask("Delete", "Are you sure?", new BooleanCallback() {
					public void execute(Boolean value) {
						if (value == Boolean.TRUE) {
							//itemList.removeSelectedData();
							detailTabPane.deleteDetail(itemList.getSelectedRecord().getAttribute("authority"));
							detailTabPane.clearDetails();
							
							itemList.invalidateCache();
						}
					}
				});
			}
		});

		listMenu.setData(detailsMenuItem, editMenuItem, deleteMenuItem);
	}

	public void findRoles() {
		Criteria findValues;

		String condition = searchForm.getValueAsString("searchCondition");
		String keyword = searchForm.getValueAsString("searchWord");
		
		/*
		System.out.println("condition ======> " + condition);
		System.out.println("keyword ======> " + keyword);
		*/
		
		if ("authority".equals(condition)) {
			findValues = searchForm.getValuesAsCriteria();
			findValues.addCriteria("authority", keyword);
		} else if ("Role Name".equals(condition)) {
			findValues = searchForm.getValuesAsCriteria();
			findValues.addCriteria("roleName", keyword);
		} else {
			findValues = searchForm.getValuesAsCriteria();
		}
		
		itemList.filterData(findValues);
		detailTabPane.clearDetails();
	}
}

class SearchForm extends DynamicForm {
	private ComboBoxItem searchCondition;

	private ButtonItem findButton;

	public SearchForm(DataSource dataSource) {

		setDataSource(dataSource);
		setTop(20);
		setCellPadding(6);
		setNumCols(7);
		setStyleName("defaultBorder");

		findButton = new ButtonItem("Find");
		findButton.setIcon("silk/find.png");
		findButton.setWidth(70);
		findButton.setEndRow(false);

		TextItem searchWord = new TextItem("searchWord", "Search Word");
		searchWord.setEndRow(false);
		searchWord.addKeyPressHandler(new KeyPressHandler() {

			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					findButton.fireEvent(new ClickEvent(null));
				}
			}
			
		});

		searchCondition = new ComboBoxItem("searchCondition", "Select");
		LinkedHashMap<String, String> data = new LinkedHashMap<String, String>();
		data.put("authority", "Authority");
		data.put("roleName", "Role Name");
		searchCondition.setValueMap(data);
		searchCondition.setValue("authority");

		setItems(findButton, searchCondition, searchWord);
	}

	public ComboBoxItem getSearchConditionField() {
		return searchCondition;
	}

	public void addFindListener(ClickHandler handler) {
		findButton.addClickHandler(handler);
	}

}

class ItemListGrid extends ListGrid {

	public ItemListGrid(DataSource dataSource) {

		setDataSource(dataSource);
		setUseAllDataSourceFields(false);

		ListGridField authority = new ListGridField("authority", "Authority");
		authority.setCanEdit(false);
		authority.setShowHover(false);

		ListGridField roleName = new ListGridField("roleName", "Role");
		roleName.setShowHover(false);
		
		setFields(authority, roleName);
		
		setCanEdit(false);
		setAlternateRecordStyles(true);
		setCanDragRecordsOut(true);
		setHoverWidth(200);
		setHoverHeight(20);
		setSelectionType(SelectionStyle.SINGLE);
		
		setRecordEnabledProperty("canEdit");
	}
}

class DetailTabPane extends TabSet {
	
    /** Create a remote service proxy to talk to the server-side role service. */
	private final RoleServiceAsync roleService = GWT.create(RoleService.class);

	private DetailViewer detailViewer;

	private DynamicForm editorForm;

	private Label editorLabel;
	private Label insertLabel;

	private ItemListGrid listGrid;
	
	private StaticTextItem authority;
	private TextItem roleName;
	
	private DynamicForm insertForm;
	
	private TextItem authority4add;
	private TextItem roleName4add;

	public DetailTabPane(DataSource dataSource, final ItemListGrid listGrid) {

		this.listGrid = listGrid;
		setStyleName("defaultBorder");
		
		detailViewer = new DetailViewer();
		detailViewer.setDataSource(dataSource);
		detailViewer.setWidth100();
		detailViewer.setMargin(25);
		detailViewer.setEmptyMessage("Select a role to view its details");

		editorLabel = new Label();
		editorLabel.setWidth100();
		editorLabel.setHeight100();
		editorLabel.setAlign(Alignment.CENTER);
		editorLabel.setContents("Select a role to edit, or insert a new role into");
		
		insertLabel = new Label();
		insertLabel.setWidth100();
		insertLabel.setHeight100();
		insertLabel.setAlign(Alignment.CENTER);
		insertLabel.setContents("Insert a new role into");

		// update form
		editorForm = new DynamicForm();
		editorForm.setWidth(650);
		editorForm.setMargin(25);
		editorForm.setNumCols(4);
		editorForm.setCellPadding(5);
		editorForm.setAutoFocus(false);
		editorForm.setDataSource(dataSource);
		editorForm.setUseAllDataSourceFields(true);

		authority = new StaticTextItem("authority", "Authority");
		roleName = new TextItem("roleName", "Role Name");
		
		roleName.setRequired(true);

		ButtonItem saveButton = new ButtonItem("saveItem", "Update role info.");
		saveButton.setAlign(Alignment.CENTER);
		saveButton.setWidth(100);
		saveButton.setColSpan(4);
		saveButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//editorForm.saveData();
				
				if (authority.getValue() == null) {
					return;
				}
				
				if (! editorForm.validate(false)) {
					return;
				}
				Role role = new Role();
				
				role.setAuthority((String)authority.getValue());
				role.setRoleName(roleName.getValueAsString());
				
				roleService.updateRole(role, new AsyncCallback<Void>() {
                    public void onFailure(Throwable caught) {
                    	SC.say("Error", "Server side error occured. Contact your administrator.");
                    }

                    public void onSuccess(Void noAnswer) {
                    	SC.say("Updated", "Role info. is updated.");
                    	
                    	listGrid.invalidateCache();
                    }
                });

				
			}
		});

		editorForm.setFields(authority, roleName, saveButton);
		editorForm.setColWidths(100, 200, 100, 200);
		
		// insert form
		insertForm = new DynamicForm();
		insertForm.setWidth(650);
		insertForm.setMargin(25);
		insertForm.setNumCols(4);
		insertForm.setCellPadding(5);
		insertForm.setAutoFocus(false);
		insertForm.setDataSource(dataSource);
		insertForm.setUseAllDataSourceFields(true);

		authority4add = new TextItem("authority", "Authority");
		roleName4add = new TextItem("roleName", "Role Name");

		
		authority4add.setRequired(true);
		roleName4add.setRequired(true);

		ButtonItem addButton = new ButtonItem("addItem", "Add role info.");
		addButton.setAlign(Alignment.CENTER);
		addButton.setWidth(100);
		addButton.setColSpan(4);
		addButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				if (! insertForm.validate(false)) {
					return;
				}
				
				Role role = new Role();
				
				role.setAuthority(authority4add.getValueAsString());
				role.setRoleName(roleName4add.getValueAsString());
				
				roleService.addRole(role, new AsyncCallback<Void>() {
                    public void onFailure(Throwable caught) {
                    	SC.say("Error", "Server side error occured. Contact your administrator.");
                    }

                    public void onSuccess(Void noAnswer) {
                    	SC.say("Added", "Role info. is added.");
                    	
                    	listGrid.invalidateCache();
                    	insertForm.clearValues();
                    }
                });

				
			}
		});

		insertForm.setFields(authority4add, roleName4add, addButton);
		insertForm.setColWidths(100, 200, 100, 200);

		// tab
		Tab viewTab = new Tab("View");
		viewTab.setIcon("silk/application_form.png");
		viewTab.setWidth(70);
		viewTab.setPane(detailViewer);

		Tab editTab = new Tab("Edit");
		editTab.setIcon("demoApp/icon_edit.png");
		editTab.setWidth(70);
		editTab.setPane(editorForm);
		
		Tab insertTab = new Tab("Add");
		insertTab.setIcon("demoApp/icon_add.png");
		insertTab.setWidth(70);
		insertTab.setPane(insertForm);

		setTabs(viewTab, editTab, insertTab);

		addTabSelectedHandler(new TabSelectedHandler() {
			public void onTabSelected(TabSelectedEvent event) {
				updateDetails();
			}
		});
	}
	
	public void deleteDetail(String id) {
		roleService.deleteRole(id, new AsyncCallback<Void>() {
            public void onFailure(Throwable caught) {
            	SC.say("Error", "Server side error occured. Contact your administrator.");
            }

            public void onSuccess(Void noAnswer) {
            	SC.say("Deleted", "Role info. is deleted.");
            	
            	listGrid.invalidateCache();
            }
        });
	}

	public void clearDetails() {
		int selectedTab = getSelectedTabNumber();
		if (selectedTab == 0) {
			// view tab : show empty message
			detailViewer.setData((Record[]) null);
		} else if (selectedTab == 1){
			// edit tab : show new record editor, or empty message
			updateTab(1, editorLabel);
		} else {
			// updateTab(2, insertLabel);
		}
	}

	public void updateDetails() {
		Record selectedRecord = listGrid.getSelectedRecord();
		
		if (selectedRecord == null) {
			return;
		}
		
		int selectedTab = getSelectedTabNumber();
		if (selectedTab == 0) {
			// view tab : show empty message
			detailViewer.setData(new Record[] { selectedRecord });
		} else if (selectedTab == 1) {
			// edit tab : show record editor
			editorForm.editRecord(selectedRecord);
		} else {
			// insertForm.clearValues();
		}
	}
}
