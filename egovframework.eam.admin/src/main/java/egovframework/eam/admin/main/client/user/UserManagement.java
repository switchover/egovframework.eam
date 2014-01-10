package egovframework.eam.admin.main.client.user;

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
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
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

import egovframework.eam.admin.main.shared.user.User;
import egovframework.eam.admin.main.shared.user.UserService;
import egovframework.eam.admin.main.shared.user.UserServiceAsync;


public class UserManagement extends HLayout {

	private SearchForm searchForm;

	private ItemListGrid userList;

	private UserDetailTabPane userDetailTabPane;

	private Menu userListMenu;

	public UserManagement() {
		setWidth100();
		setHeight100();
		setLayoutMargin(20);

		UserDataSource userDS = UserDataSource.getInstance();
		
		searchForm = new SearchForm(userDS);

		//final ComboBoxItem searchConditions = searchForm.getSearchConditionField();

		setupContextMenu();

		userList = new ItemListGrid(userDS);
		userList.addRecordClickHandler(new RecordClickHandler() {
			public void onRecordClick(RecordClickEvent event) {
				userDetailTabPane.updateDetails();
			}
		});

		userList.addCellSavedHandler(new CellSavedHandler() {
			public void onCellSaved(CellSavedEvent event) {
				userDetailTabPane.updateDetails();
			}
		});

		userList.addCellContextClickHandler(new CellContextClickHandler() {
			public void onCellContextClick(CellContextClickEvent event) {
				userListMenu.showContextMenu();
				event.cancel();
			}
		});

		SectionStack sessionLayout = new SectionStack();
		sessionLayout.setVisibilityMode(VisibilityMode.MULTIPLE);
		sessionLayout.setAnimateSections(true);

		searchForm.setHeight(60);
		searchForm.addFindListener(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
			public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
				findUsers();
			}
		});

		SectionStackSection findSection = new SectionStackSection("Find Users");
		findSection.setItems(searchForm);
		findSection.setExpanded(true);

		SectionStackSection usersSection = new SectionStackSection("User List");
		usersSection.setItems(userList);
		usersSection.setExpanded(true);

		userDetailTabPane = new UserDetailTabPane(userDS, userList);
		SectionStackSection userDetailsSection = new SectionStackSection("User Details");
		userDetailsSection.setItems(userDetailTabPane);
		userDetailsSection.setExpanded(true);

		sessionLayout.setSections(findSection, usersSection, userDetailsSection);

		addMember(sessionLayout);
	}

	private void setupContextMenu() {
		userListMenu = new Menu();
		userListMenu.setCellHeight(22);

		MenuItem detailsMenuItem = new MenuItem("Show Details", "silk/application_form.png");
		detailsMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			public void onClick(MenuItemClickEvent event) {
				userDetailTabPane.selectTab(0);
				userDetailTabPane.updateDetails();
			}
		});

		final MenuItem editMenuItem = new MenuItem("Edit User", "demoApp/icon_edit.png");
		editMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			public void onClick(MenuItemClickEvent event) {
				userDetailTabPane.selectTab(1);
				userDetailTabPane.updateDetails();
			}
		});

		final MenuItem deleteMenuItem = new MenuItem("Delete User", "silk/delete.png");
		deleteMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			public void onClick(MenuItemClickEvent event) {
				
				SC.ask("Delete", "Are you sure?", new BooleanCallback() {
					public void execute(Boolean value) {
						if (value == Boolean.TRUE) {
							//userList.removeSelectedData();
							userDetailTabPane.deleteDetail(userList.getSelectedRecord().getAttribute("userId"));
							userDetailTabPane.clearDetails();
							
							userList.invalidateCache();
						}
					}
				});
			}
		});

		userListMenu.setData(detailsMenuItem, editMenuItem, deleteMenuItem);
	}

	public void findUsers() {
		Criteria findValues;

		String condition = searchForm.getValueAsString("searchCondition");
		String keyword = searchForm.getValueAsString("searchWord");
		
		/*
		System.out.println("condition ======> " + condition);
		System.out.println("keyword ======> " + keyword);
		*/
		
		if ("User ID".equals(condition)) {
			findValues = searchForm.getValuesAsCriteria();
			findValues.addCriteria("userId", keyword);
		} else if ("User Name".equals(condition)) {
			findValues = searchForm.getValuesAsCriteria();
			findValues.addCriteria("userName", keyword);
		} else {
			findValues = searchForm.getValuesAsCriteria();
		}
		
		userList.filterData(findValues);
		userDetailTabPane.clearDetails();
	}
}

class SearchForm extends DynamicForm {
	private ComboBoxItem searchCondition;

	private ButtonItem findUser;

	public SearchForm(DataSource userDS) {

		setDataSource(userDS);
		setTop(20);
		setCellPadding(6);
		setNumCols(7);
		setStyleName("defaultBorder");

		findUser = new ButtonItem("Find");
		findUser.setIcon("silk/find.png");
		findUser.setWidth(70);
		findUser.setEndRow(false);

		TextItem searchWord = new TextItem("searchWord", "Search Word");
		searchWord.setEndRow(false);
		searchWord.addKeyPressHandler(new KeyPressHandler() {

			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					findUser.fireEvent(new ClickEvent(null));
				}
			}
			
		});

		searchCondition = new ComboBoxItem("searchCondition", "Select");
		LinkedHashMap<String, String> data = new LinkedHashMap<String, String>();
		data.put("userId", "User ID");
		data.put("userName", "User Name");
		searchCondition.setValueMap(data);
		searchCondition.setValue("userId");

		setItems(findUser, searchCondition, searchWord);
	}

	public ComboBoxItem getSearchConditionField() {
		return searchCondition;
	}

	public void addFindListener(ClickHandler handler) {
		findUser.addClickHandler(handler);
	}

}

class ItemListGrid extends ListGrid {

	public ItemListGrid(DataSource userDS) {

		setDataSource(userDS);
		setUseAllDataSourceFields(false);

		ListGridField userId = new ListGridField("userId", "User ID");
		userId.setCanEdit(false);
		userId.setShowHover(false);

		ListGridField userName = new ListGridField("userName", "Name");
		userName.setShowHover(false);

		ListGridField enabled = new ListGridField("enabled", "Enabled");
		enabled.setShowHover(false);

		setFields(userId, userName, enabled);
		
		setCanEdit(false);
		setAlternateRecordStyles(true);
		setCanDragRecordsOut(true);
		setHoverWidth(200);
		setHoverHeight(20);
		setSelectionType(SelectionStyle.SINGLE);
		
		setRecordEnabledProperty("canEdit");
	}
}

class UserDetailTabPane extends TabSet {
	
    /** Create a remote service proxy to talk to the server-side user service. */
	private final UserServiceAsync userService = GWT.create(UserService.class);

	private DetailViewer userViewer;

	private DynamicForm editorForm;

	private Label editorLabel;
	private Label insertLabel;

	private ItemListGrid userListGrid;
	
	private StaticTextItem userId;
	private TextItem userName;
	private PasswordItem password;
	private CheckboxItem enabled;
	
	private DynamicForm insertForm;
	
	private TextItem userId4add;
	private TextItem userName4add;
	private PasswordItem password4add;
	private CheckboxItem enabled4add;

	public UserDetailTabPane(DataSource userDS, final ItemListGrid userListGrid) {

		this.userListGrid = userListGrid;
		setStyleName("defaultBorder");
		
		userViewer = new DetailViewer();
		userViewer.setDataSource(userDS);
		userViewer.setWidth100();
		userViewer.setMargin(25);
		userViewer.setEmptyMessage("Select an user to view its details");

		editorLabel = new Label();
		editorLabel.setWidth100();
		editorLabel.setHeight100();
		editorLabel.setAlign(Alignment.CENTER);
		editorLabel.setContents("Select an user to edit, or insert a new user into");
		
		insertLabel = new Label();
		insertLabel.setWidth100();
		insertLabel.setHeight100();
		insertLabel.setAlign(Alignment.CENTER);
		insertLabel.setContents("Insert a new user into");

		// update form
		editorForm = new DynamicForm();
		editorForm.setWidth(650);
		editorForm.setMargin(25);
		editorForm.setNumCols(4);
		editorForm.setCellPadding(5);
		editorForm.setAutoFocus(false);
		editorForm.setDataSource(userDS);
		editorForm.setUseAllDataSourceFields(true);

		userId = new StaticTextItem("userId", "User ID");
		userName = new TextItem("userName", "User Name");
		password = new PasswordItem("password", "Password");
		enabled = new CheckboxItem("enabled", "Enabled");
		
		userName.setRequired(true);
		password.setRequired(true);

		ButtonItem saveButton = new ButtonItem("saveUser", "Update user info.");
		saveButton.setAlign(Alignment.CENTER);
		saveButton.setWidth(100);
		saveButton.setColSpan(4);
		saveButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//editorForm.saveData();
				
				if (userId.getValue() == null) {
					return;
				}
				
				if (! editorForm.validate(false)) {
					return;
				}
				User user = new User();
				
				user.setUserId((String)userId.getValue());
				user.setUserName(userName.getValueAsString());
				user.setPassword(password.getValueAsString());
				user.setEnabled(enabled.getValueAsBoolean());
				
				
				userService.updateUser(user, new AsyncCallback<Void>() {
                    public void onFailure(Throwable caught) {
                    	SC.say("Error", "Server side error occured. Contact your administrator.");
                    }

                    public void onSuccess(Void noAnswer) {
                    	SC.say("Updated", "User info. is updated.");
                    	
                    	userListGrid.invalidateCache();
                    }
                });

				
			}
		});

		editorForm.setFields(userId, userName, password, enabled, saveButton);
		editorForm.setColWidths(100, 200, 100, 200);
		
		// insert form
		insertForm = new DynamicForm();
		insertForm.setWidth(650);
		insertForm.setMargin(25);
		insertForm.setNumCols(4);
		insertForm.setCellPadding(5);
		insertForm.setAutoFocus(false);
		insertForm.setDataSource(userDS);
		insertForm.setUseAllDataSourceFields(true);

		userId4add = new TextItem("userId", "User ID");
		userName4add = new TextItem("userName", "User Name");
		password4add = new PasswordItem("password", "Password");
		enabled4add = new CheckboxItem("enabled", "Enabled");
		
		enabled4add.setValue(true);
		userId4add.setRequired(true);
		userName4add.setRequired(true);
		password4add.setRequired(true);

		ButtonItem addButton = new ButtonItem("addUser", "Add user info.");
		addButton.setAlign(Alignment.CENTER);
		addButton.setWidth(100);
		addButton.setColSpan(4);
		addButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				if (! insertForm.validate(false)) {
					return;
				}
				
				User user = new User();
				
				user.setUserId(userId4add.getValueAsString());
				user.setUserName(userName4add.getValueAsString());
				user.setPassword(password4add.getValueAsString());
				user.setEnabled(enabled4add.getValueAsBoolean());
				
				userService.addUser(user, new AsyncCallback<Void>() {
                    public void onFailure(Throwable caught) {
                    	SC.say("Error", "Server side error occured. Contact your administrator.");
                    }

                    public void onSuccess(Void noAnswer) {
                    	SC.say("Added", "User info. is added.");
                    	
                    	userListGrid.invalidateCache();
                    	insertForm.clearValues();
                    }
                });
			}
		});

		insertForm.setFields(userId4add, userName4add, password4add, enabled4add, addButton);
		insertForm.setColWidths(100, 200, 100, 200);

		// tab
		Tab viewTab = new Tab("View");
		viewTab.setIcon("silk/application_form.png");
		viewTab.setWidth(70);
		viewTab.setPane(userViewer);

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
		userService.deleteUser(id, new AsyncCallback<Void>() {
            public void onFailure(Throwable caught) {
            	SC.say("Error", "Server side error occured. Contact your administrator.");
            }

            public void onSuccess(Void noAnswer) {
            	SC.say("Deleted", "User info. is deleted.");
            	
            	userListGrid.invalidateCache();
            }
        });
	}

	public void clearDetails() {
		int selectedTab = getSelectedTabNumber();
		if (selectedTab == 0) {
			// view tab : show empty message
			userViewer.setData((Record[]) null);
		} else if (selectedTab == 1){
			// edit tab : show new record editor, or empty message
			updateTab(1, editorLabel);
		} else {
			updateTab(2, insertLabel);
		}
	}

	public void updateDetails() {
		Record selectedRecord = userListGrid.getSelectedRecord();
		
		if (selectedRecord == null) {
			return;
		}
		
		int selectedTab = getSelectedTabNumber();
		if (selectedTab == 0) {
			// view tab : show empty message
			userViewer.setData(new Record[] { selectedRecord });
		} else if (selectedTab == 1) {
			// edit tab : show record editor
			editorForm.editRecord(selectedRecord);
		} else {
			// insertForm.clearValues();
		}
	}
}
