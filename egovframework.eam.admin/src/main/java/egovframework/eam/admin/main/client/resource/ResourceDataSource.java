package egovframework.eam.admin.main.client.resource;

import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.types.FieldType;

import egovframework.eam.admin.main.client.datasource.AbstractRestDataSource;

public class ResourceDataSource extends AbstractRestDataSource {
	private static ResourceDataSource instance = null;

	public static ResourceDataSource getInstance() {
		if (instance == null) {
			instance = new ResourceDataSource("resourceEditDS");
		}

		return instance;
	}

	private ResourceDataSource(String id) {
		super(id);

		setFetchDataURL("/service/resources");
		setAddDataURL("/service/resource");
		setUpdateDataURL("/service/resource");
		setRemoveDataURL("/service/resource");
	}

	protected void init() {
		setDataFormat(DSDataFormat.XML);
		setXmlRecordXPath("/resources/data/*");
		
		DataSourceField idField = new DataSourceField("resourceId", FieldType.TEXT, "Resource ID");
		idField.setPrimaryKey(true);
		idField.setCanEdit(false);
		DataSourceField nameField = new DataSourceField("resourceName", FieldType.TEXT, "Resource Name");
		DataSourceField patternField = new DataSourceField("resourcePattern", FieldType.TEXT, "Resource Pattern");
		DataSourceField typeField = new DataSourceField("resourceType", FieldType.TEXT, "Resource Type");
		DataSourceField sortOrderField = new DataSourceField("sortOrder", FieldType.TEXT, "Sort Order");

		setFields(idField, nameField, typeField, patternField, sortOrderField);
	}
}