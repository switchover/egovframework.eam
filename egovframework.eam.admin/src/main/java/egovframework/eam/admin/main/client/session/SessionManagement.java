package egovframework.eam.admin.main.client.session;

import java.util.LinkedHashMap;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;

public class SessionManagement extends HLayout {
	private SearchForm searchForm;

	private ItemListGrid itemList;

	public SessionManagement() {
		setWidth100();
		setHeight100();
		setLayoutMargin(20);

		SessionDataSource dataSource = SessionDataSource.getInstance();
		
		searchForm = new SearchForm(dataSource);

		itemList = new ItemListGrid(dataSource);

		SectionStack sessionLayout = new SectionStack();
		sessionLayout.setVisibilityMode(VisibilityMode.MULTIPLE);
		sessionLayout.setAnimateSections(true);

		searchForm.setHeight(60);
		searchForm.addFindListener(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
			public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
				findSessions();
			}
		});

		SectionStackSection findSection = new SectionStackSection("Find Sessions");
		findSection.setItems(searchForm);
		findSection.setExpanded(true);

		SectionStackSection listSection = new SectionStackSection("Session List");
		listSection.setItems(itemList);
		listSection.setExpanded(true);

		sessionLayout.setSections(findSection, listSection);

		addMember(sessionLayout);
	}

	
	public void findSessions() {
		Criteria findValues;

		String condition = searchForm.getValueAsString("searchCondition");
		String keyword = searchForm.getValueAsString("searchWord");
		
		if ("systemId".equals(condition)) {
			findValues = searchForm.getValuesAsCriteria();
			findValues.addCriteria("systemId", keyword);
		} else if ("systemName".equals(condition)) {
			findValues = searchForm.getValuesAsCriteria();
			findValues.addCriteria("systemName", keyword);
		} else {
			findValues = searchForm.getValuesAsCriteria();
		}
		
		itemList.filterData(findValues);
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
		
		ListGridField sessionKey = new ListGridField("sessionKey", "Session Key");
		sessionKey.setShowHover(false);
		
		ListGridField issueDatetime = new ListGridField("issueDatetime", "Issue Date & Time");
		issueDatetime.setShowHover(false);
		
		ListGridField dueDatetime = new ListGridField("dueDatetime", "Due Date & Time");
		dueDatetime.setShowHover(false);
		
		setFields(systemId, systemName, sessionKey, issueDatetime, dueDatetime);
		
		setCanEdit(false);
		setAlternateRecordStyles(true);
		setCanDragRecordsOut(true);
		setHoverWidth(200);
		setHoverHeight(20);
		setSelectionType(SelectionStyle.SINGLE);
		
		setRecordEnabledProperty("canEdit");
	}
}
