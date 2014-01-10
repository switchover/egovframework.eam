package egovframework.eam.admin.main.client.resource;

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
import com.smartgwt.client.widgets.form.fields.SpinnerItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
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

import egovframework.eam.admin.main.shared.resource.Resource;
import egovframework.eam.admin.main.shared.resource.ResourceService;
import egovframework.eam.admin.main.shared.resource.ResourceServiceAsync;

public class ResourceManagement extends HLayout {
	private SearchForm searchForm;

	private ItemListGrid itemList;

	private DetailTabPane detailTabPane;

	private Menu listMenu;

	public ResourceManagement() {
		setWidth100();
		setHeight100();
		setLayoutMargin(20);

		ResourceDataSource dataSource = ResourceDataSource.getInstance();
		
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

		SectionStackSection findSection = new SectionStackSection("Find Resources");
		findSection.setItems(searchForm);
		findSection.setExpanded(true);

		SectionStackSection listSection = new SectionStackSection("Resource List");
		listSection.setItems(itemList);
		listSection.setExpanded(true);

		detailTabPane = new DetailTabPane(dataSource, itemList);
		SectionStackSection detailsSection = new SectionStackSection("Resource Details");
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
							detailTabPane.deleteDetail(itemList.getSelectedRecord().getAttribute("resourceId"));
							detailTabPane.clearDetails();
							
							itemList.invalidateCache();
						}
					}
				});
			}
		});

		listMenu.setData(detailsMenuItem, editMenuItem, deleteMenuItem);
	}

	public void findResources() {
		Criteria findValues;

		String condition = searchForm.getValueAsString("searchCondition");
		String keyword = searchForm.getValueAsString("searchWord");
		String sub = searchForm.getValueAsString("subCondition");
		
		/*
		System.out.println("condition ======> " + condition);
		System.out.println("keyword ======> " + keyword);
		System.out.println("type ======> " + type);
		*/
		
		if ("resourceName".equals(condition)) {
			findValues = searchForm.getValuesAsCriteria();
			findValues.addCriteria("resourceName", keyword);
		} else if ("resourceType".equals(condition)) {
			findValues = searchForm.getValuesAsCriteria();
			findValues.addCriteria("resourceType", condition);
			findValues.addCriteria("subCondition", sub);
		} else {
			findValues = searchForm.getValuesAsCriteria();
		}
		
		itemList.filterData(findValues);
		detailTabPane.clearDetails();
	}
}

class SearchForm extends DynamicForm {
	private ComboBoxItem searchCondition;
	
	private TypeComboBoxItem subCondition;
	
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
		data.put("resourceName", "Resource Name");
		data.put("resourceType", "Resource Type");
		searchCondition.setValueMap(data);
		searchCondition.setValue("resourceName");
		
		subCondition = new TypeComboBoxItem("subCondition", "Type");
		subCondition.setVisible(false);
		
		searchCondition.addChangedHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				if ("resourceName".equals((String)event.getValue())) {
					searchWord.show();
					subCondition.hide();
				} else {
					searchWord.hide();
					subCondition.show();
				}
			}
		});
		
		setItems(findButton, searchCondition, subCondition, searchWord);
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

		ListGridField resourceId = new ListGridField("resourceId", "Resource ID");
		resourceId.setCanEdit(false);
		resourceId.setShowHover(false);

		ListGridField resourceName = new ListGridField("resourceName", "Resource Name");
		resourceName.setShowHover(false);
		
		ListGridField resourceType = new ListGridField("resourceType", "Resource Type");
		resourceType.setShowHover(false);
		
		ListGridField resourcePattern = new ListGridField("resourcePattern", "Resource Pattern");
		resourcePattern.setShowHover(false);
		
		ListGridField sortOrder = new ListGridField("sortOrder", "Sort Order");
		sortOrder.setShowHover(false);
		
		setFields(resourceId, resourceName, resourceType, resourcePattern, sortOrder);
		
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
	private final ResourceServiceAsync resourceService = GWT.create(ResourceService.class);

	private DetailViewer detailViewer;

	private DynamicForm editorForm;

	private Label editorLabel;
	private Label insertLabel;

	private ItemListGrid listGrid;
	
	private StaticTextItem resourceId;
	private TextItem resourceName;
	private ComboBoxItem resourceType;
	private TextItem resourcePattern;
	private SpinnerItem sortOrder;
	
	private DynamicForm insertForm;
	
	private TextItem resourceId4add;
	private TextItem resourceName4add;
	private ComboBoxItem resourceType4add;
	private TextItem resourcePattern4add;
	private SpinnerItem sortOrder4add;

	public DetailTabPane(DataSource dataSource, final ItemListGrid listGrid) {

		this.listGrid = listGrid;
		setStyleName("defaultBorder");
		
		detailViewer = new DetailViewer();
		detailViewer.setDataSource(dataSource);
		detailViewer.setWidth100();
		detailViewer.setMargin(25);
		detailViewer.setEmptyMessage("Select a resource to view its details");

		editorLabel = new Label();
		editorLabel.setWidth100();
		editorLabel.setHeight100();
		editorLabel.setAlign(Alignment.CENTER);
		editorLabel.setContents("Select a resource to edit, or insert a new resource into");
		
		insertLabel = new Label();
		insertLabel.setWidth100();
		insertLabel.setHeight100();
		insertLabel.setAlign(Alignment.CENTER);
		insertLabel.setContents("Insert a new resource into");

		// update form
		editorForm = new DynamicForm();
		editorForm.setWidth(650);
		editorForm.setMargin(25);
		editorForm.setNumCols(4);
		editorForm.setCellPadding(5);
		editorForm.setAutoFocus(false);
		editorForm.setDataSource(dataSource);
		editorForm.setUseAllDataSourceFields(true);

		resourceId = new StaticTextItem("resourceId", "Resource ID");
		resourceName = new TextItem("resourceName", "Resource Name");
		resourceType = new TypeComboBoxItem("resourceType", "Resource Type");
		resourcePattern = new TextItem("resourcePattern", "Resource Pattern");
		sortOrder = new SpinnerItem("sortOrder", "Sort Order");
		
		resourceName.setRequired(true);
		resourceType.setRequired(true);
		resourcePattern.setRequired(true);
		resourcePattern.setWidth(400);
		sortOrder.setDefaultValue(1);  
		sortOrder.setMin(1);  
		sortOrder.setMax(100);  
		sortOrder.setStep(1); 

		ButtonItem saveButton = new ButtonItem("saveItem", "Update resource");
		saveButton.setAlign(Alignment.CENTER);
		saveButton.setWidth(100);
		saveButton.setColSpan(4);
		saveButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//editorForm.saveData();
				
				if (resourceId.getValue() == null) {
					return;
				}
				
				if (! editorForm.validate(false)) {
					return;
				}
				Resource resource = new Resource();
				
				resource.setResourceId((String)resourceId.getValue());
				resource.setResourceName(resourceName.getValueAsString());
				resource.setResourceType(resourceType.getValueAsString());
				resource.setResourcePattern(resourcePattern.getValueAsString());
				resource.setSortOrder(Integer.parseInt(sortOrder.getValueAsString()));
				
				resourceService.updateResource(resource, new AsyncCallback<Void>() {
                    public void onFailure(Throwable caught) {
                    	SC.say("Error", "Server side error occured. Contact your administrator.");
                    }

                    public void onSuccess(Void noAnswer) {
                    	SC.say("Updated", "Resource info. is updated.");
                    	
                    	listGrid.invalidateCache();
                    }
                });

				
			}
		});

		editorForm.setFields(resourceId, resourceName, resourceType, resourcePattern, sortOrder, saveButton);
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

		resourceId4add = new TextItem("resourceId", "Resource ID");
		resourceName4add = new TextItem("resourceName", "Resource Name");
		resourceType4add = new TypeComboBoxItem("resourceType", "Resource Type");
		resourcePattern4add = new TextItem("resourcePattern", "Resource Pattern");
		sortOrder4add = new SpinnerItem("sortOrder", "Sort Order");
		
		resourceId4add.setRequired(true);
		resourceName4add.setRequired(true);
		resourceType4add.setRequired(true);
		resourcePattern4add.setRequired(true);
		resourcePattern4add.setWidth(400);
		sortOrder4add.setDefaultValue(1);  
		sortOrder4add.setMin(1);  
		sortOrder4add.setMax(100);  
		sortOrder4add.setStep(1); 

		ButtonItem addButton = new ButtonItem("addItem", "Add resource");
		addButton.setAlign(Alignment.CENTER);
		addButton.setWidth(100);
		addButton.setColSpan(4);
		addButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				if (! insertForm.validate(false)) {
					return;
				}
				
				Resource resource = new Resource();
				
				resource.setResourceId(resourceId4add.getValueAsString());
				resource.setResourceName(resourceName4add.getValueAsString());
				resource.setResourceType(resourceType4add.getValueAsString());
				resource.setResourcePattern(resourcePattern4add.getValueAsString());
				resource.setSortOrder(Integer.parseInt(sortOrder4add.getValueAsString()));
				
				resourceService.addResource(resource, new AsyncCallback<Void>() {
                    public void onFailure(Throwable caught) {
                    	SC.say("Error", "Server side error occured. Contact your administrator.");
                    }

                    public void onSuccess(Void noAnswer) {
                    	SC.say("Added", "Resource info. is added.");
                    	
                    	listGrid.invalidateCache();
                    	insertForm.clearValues();
                    }
                });

				
			}
		});

		insertForm.setFields(resourceId4add, resourceName4add, resourceType4add, resourcePattern4add, sortOrder4add, addButton);
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
		resourceService.deleteResource(id, new AsyncCallback<Void>() {
            public void onFailure(Throwable caught) {
            	SC.say("Error", "Server side error occured. Contact your administrator.");
            }

            public void onSuccess(Void noAnswer) {
            	SC.say("Deleted", "Resource info. is deleted.");
            	
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

class TypeComboBoxItem extends ComboBoxItem {
	public TypeComboBoxItem(String name, String title) {
		super(name, title);
		
		LinkedHashMap<String, String> options = new LinkedHashMap<String, String>();
		options.put("url", "URL");
		options.put("method", "Method");
		setValueMap(options);
		setValue("url");
	}
}
