package egovframework.eam.admin.main.client.system;

import java.util.LinkedHashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.CloseClientEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
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

import egovframework.eam.admin.main.shared.system.SystemData;
import egovframework.eam.admin.main.shared.system.SystemDataService;
import egovframework.eam.admin.main.shared.system.SystemDataServiceAsync;

public class SystemManagement extends HLayout {
	private SearchForm searchForm;

	private ItemListGrid itemList;

	private DetailTabPane detailTabPane;

	private Menu listMenu;
	
	private final SystemDataServiceAsync systemDataService = GWT.create(SystemDataService.class);

	public SystemManagement() {
		setWidth100();
		setHeight100();
		setLayoutMargin(20);

		SystemDataSource dataSource = SystemDataSource.getInstance();
		
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
				findResources();
			}
		});

		SectionStackSection findSection = new SectionStackSection("Find Systems");
		findSection.setItems(searchForm);
		findSection.setExpanded(true);

		SectionStackSection listSection = new SectionStackSection("System List");
		listSection.setItems(itemList);
		listSection.setExpanded(true);

		detailTabPane = new DetailTabPane(dataSource, itemList);
		SectionStackSection detailsSection = new SectionStackSection("System Details");
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

		final MenuItem editMenuItem = new MenuItem("Edit System", "demoApp/icon_edit.png");
		editMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			public void onClick(MenuItemClickEvent event) {
				detailTabPane.selectTab(1);
				detailTabPane.updateDetails();
			}
		});

		final MenuItem deleteMenuItem = new MenuItem("Delete System", "silk/delete.png");
		deleteMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			public void onClick(MenuItemClickEvent event) {
				
				SC.ask("Delete", "Are you sure?", new BooleanCallback() {
					public void execute(Boolean value) {
						if (value == Boolean.TRUE) {
							//itemList.removeSelectedData();
							detailTabPane.deleteDetail(itemList.getSelectedRecord().getAttribute("systemId"));
							detailTabPane.clearDetails();
							
							itemList.invalidateCache();
						}
					}
				});
			}
		});
		
		final MenuItem viewAuthKeyMenuItem = new MenuItem("View Auth. Key", "silk/server_connect.png");
		viewAuthKeyMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			public void onClick(MenuItemClickEvent event) {
				
				String systemId = itemList.getSelectedRecord().getAttribute("systemId");
				
				systemDataService.getAuthKey(systemId, new AsyncCallback<String>() {
                    public void onFailure(Throwable caught) {
                    	SC.say("Error", "Server side error occured. Contact your administrator.");
                    }

                    public void onSuccess(String answer) {
                    	final Window winModal = new Window();  
                        winModal.setWidth(400);  
                        winModal.setHeight(65);  
                        winModal.setTitle("AuthKey");  
                        winModal.setShowMinimizeButton(false);  
                        winModal.setIsModal(true);  
                        winModal.setShowModalMask(true);  
                        winModal.centerInPage();  
                        winModal.addCloseClickHandler(new CloseClickHandler() {
							public void onCloseClick(CloseClientEvent event) {
								winModal.destroy();
							}  
                           
                        });  
                        
                        DynamicForm form = new DynamicForm();  
                        form.setHeight100();  
                        form.setWidth100();  
                        form.setPadding(5);  
                        form.setLayoutAlign(VerticalAlignment.BOTTOM);  
                        TextItem textItem = new TextItem();  
                        textItem.setTitle("AuthKey"); 
                        textItem.setValue(answer);
                        textItem.setWidth(300);
                        
                        form.setFields(textItem);  
                        winModal.addItem(form);  
                        winModal.show();  
                    	
                   }
                });	
			}
		});

		listMenu.setData(detailsMenuItem, editMenuItem, deleteMenuItem, viewAuthKeyMenuItem);
	}

	public void findResources() {
		Criteria findValues;

		String condition = searchForm.getValueAsString("searchCondition");
		String keyword = searchForm.getValueAsString("searchWord");
				
		if ("systemId".equals(condition)) {
			findValues = searchForm.getValuesAsCriteria();
			findValues.addCriteria("systemId", keyword);
		} else if ("systemName".equals(condition)) {
			findValues = searchForm.getValuesAsCriteria();
			findValues.addCriteria("systemName", condition);
		} else {
			findValues = searchForm.getValuesAsCriteria();
		}
		
		itemList.filterData(findValues);
		detailTabPane.clearDetails();
	}
}

class SearchForm extends DynamicForm {
	private ComboBoxItem searchCondition;
	
	private TextItem searchWord;

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

		searchWord = new TextItem("searchWord", "Search Word");
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
		data.put("systemId", "System ID");
		data.put("systemName", "System Name");
		searchCondition.setValueMap(data);
		searchCondition.setValue("systemId");
		
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

		ListGridField systemId = new ListGridField("systemId", "System ID");
		systemId.setCanEdit(false);
		systemId.setShowHover(false);

		ListGridField systemName = new ListGridField("systemName", "System Name");
		systemName.setShowHover(false);
		
		ListGridField password = new ListGridField("password", "System Key");
		password.setShowHover(false);
		
		ListGridField createdDatetime = new ListGridField("createdDatetime", "Created Date & Time");
		createdDatetime.setShowHover(false);
		
		ListGridField ip = new ListGridField("ip", "System IP");
		ip.setShowHover(false);
		
		setFields(systemId, systemName, password, createdDatetime, ip);
		
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
	
    /** Create a remote service proxy to talk to the server-side system service. */
	private final SystemDataServiceAsync systemDataService = GWT.create(SystemDataService.class);

	private DetailViewer detailViewer;

	private DynamicForm editorForm;

	private Label editorLabel;
	private Label insertLabel;

	private ItemListGrid listGrid;
	
	private StaticTextItem systemId;
	private TextItem systemName;
	private TextItem password;
	private TextItem ip;
	private HiddenItem createdDatetime;
	
	private DynamicForm insertForm;
	
	private TextItem systemId4add;
	private TextItem systemName4add;
	private TextItem password4add;
	private TextItem ip4add;
	private HiddenItem createdDatetime4add;

	public DetailTabPane(DataSource dataSource, final ItemListGrid listGrid) {

		this.listGrid = listGrid;
		setStyleName("defaultBorder");
		
		detailViewer = new DetailViewer();
		detailViewer.setDataSource(dataSource);
		detailViewer.setWidth100();
		detailViewer.setMargin(25);
		detailViewer.setEmptyMessage("Select a system to view its details");

		editorLabel = new Label();
		editorLabel.setWidth100();
		editorLabel.setHeight100();
		editorLabel.setAlign(Alignment.CENTER);
		editorLabel.setContents("Select a system to edit, or insert a new system into");
		
		insertLabel = new Label();
		insertLabel.setWidth100();
		insertLabel.setHeight100();
		insertLabel.setAlign(Alignment.CENTER);
		insertLabel.setContents("Insert a new system into");

		// update form
		editorForm = new DynamicForm();
		editorForm.setWidth(650);
		editorForm.setMargin(25);
		editorForm.setNumCols(4);
		editorForm.setCellPadding(5);
		editorForm.setAutoFocus(false);
		editorForm.setDataSource(dataSource);
		editorForm.setUseAllDataSourceFields(true);

		systemId = new StaticTextItem("systemId", "System ID");
		systemName = new TextItem("systemName", "System Name");
		password = new TextItem("password", "System key");
		ip = new TextItem("ip", "System IP");
		createdDatetime = new HiddenItem("createdDatetime");
		
		systemName.setRequired(true);
		password.setRequired(true);
		ip.setRequired(true);

		ButtonItem saveButton = new ButtonItem("saveItem", "Update system");
		saveButton.setAlign(Alignment.CENTER);
		saveButton.setWidth(100);
		saveButton.setColSpan(4);
		saveButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//editorForm.saveData();
				
				if (systemId.getValue() == null) {
					return;
				}
				
				if (! editorForm.validate(false)) {
					return;
				}
				
				SystemData system = new SystemData();
				
				system.setSystemId((String)systemId.getValue());
				system.setSystemName(systemName.getValueAsString());
				system.setPassword(password.getValueAsString());
				system.addIpList(ip.getValueAsString());
				
				systemDataService.updateSystem(system, new AsyncCallback<Void>() {
                    public void onFailure(Throwable caught) {
                    	SC.say("Error", "Server side error occured. Contact your administrator.");
                    }

                    public void onSuccess(Void noAnswer) {
                    	SC.say("Updated", "System info. is updated.");
                    	
                    	listGrid.invalidateCache();
                    }
                });
			}
		});

		editorForm.setFields(systemId, systemName, password, ip, createdDatetime, saveButton);
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

		systemId4add = new TextItem("systemId", "System ID");
		systemName4add = new TextItem("systemName", "System Name");
		password4add = new TextItem("password", "System Key");
		ip4add = new TextItem("ip", "System IP");
		createdDatetime4add = new HiddenItem("createdDatetime");
		
		systemId4add.setRequired(true);
		systemName4add.setRequired(true);
		password4add.setRequired(true);
		ip4add.setRequired(true);

		ButtonItem addButton = new ButtonItem("addItem", "Add system");
		addButton.setAlign(Alignment.CENTER);
		addButton.setWidth(100);
		addButton.setColSpan(4);
		addButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				if (! insertForm.validate(false)) {
					return;
				}
				
				SystemData system = new SystemData();
				
				system.setSystemId(systemId4add.getValueAsString());
				system.setSystemName(systemName4add.getValueAsString());
				system.setPassword(password4add.getValueAsString());
				system.addIpList(ip4add.getValueAsString());
			
				
				systemDataService.addSystem(system, new AsyncCallback<Void>() {
                    public void onFailure(Throwable caught) {
                    	SC.say("Error", "Server side error occured. Contact your administrator.");
                    }

                    public void onSuccess(Void noAnswer) {
                    	SC.say("Added", "System info. is added.");
                    	
                    	listGrid.invalidateCache();
                    	insertForm.clearValues();
                    }
                });

				
			}
		});

		insertForm.setFields(systemId4add, systemName4add, password4add, ip4add, createdDatetime4add, addButton);
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
		systemDataService.deleteSystem(id, new AsyncCallback<Void>() {
            public void onFailure(Throwable caught) {
            	SC.say("Error", "Server side error occured. Contact your administrator.");
            }

            public void onSuccess(Void noAnswer) {
            	SC.say("Deleted", "System info. is deleted.");
            	
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
