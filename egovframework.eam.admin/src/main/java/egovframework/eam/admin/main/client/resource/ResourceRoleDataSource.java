package egovframework.eam.admin.main.client.resource;

import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.types.FieldType;

import egovframework.eam.admin.main.client.datasource.AbstractRestDataSource;

public class ResourceRoleDataSource extends AbstractRestDataSource {
	private static ResourceRoleDataSource instance = null;

	public static ResourceRoleDataSource getInstance() {
		if (instance == null) {
			instance = new ResourceRoleDataSource("resourceRoleEditDS");
		}

		return instance;
	}

	private ResourceRoleDataSource(String id) {
		super(id);

		setFetchDataURL("/service/resourceRoles");
		setAddDataURL("/service/resourceRole");
		setUpdateDataURL("/service/resourceRole");
		setRemoveDataURL("/service/resourceRole");
	}

	protected void init() {
		setDataFormat(DSDataFormat.XML);
		setXmlRecordXPath("/resourceRoles/data/*");
		
		DataSourceField resourceIdField = new DataSourceField("resourceId", FieldType.TEXT, "Resource ID");
		resourceIdField.setPrimaryKey(true);
		resourceIdField.setCanEdit(false);
		resourceIdField.setValueXPath("resource/resourceId");
		
		DataSourceField nameField = new DataSourceField("resourceName", FieldType.TEXT, "Resource Name");
		DataSourceField patternField = new DataSourceField("resourcePattern", FieldType.TEXT, "Resource Pattern");
		DataSourceField typeField = new DataSourceField("resourceType", FieldType.TEXT, "Resource Type");
		
		nameField.setValueXPath("resource/resourceName");
		patternField.setValueXPath("resource/resourcePattern");
		typeField.setValueXPath("resource/resourceType");
		
		DataSourceField authorityField = new DataSourceField("authority", FieldType.TEXT, "Authority");
		authorityField.setPrimaryKey(true);
		authorityField.setCanEdit(false);
		authorityField.setValueXPath("role/authority");
		
		DataSourceField roleNameField = new DataSourceField("roleName", FieldType.TEXT, "Role Name");
		
		roleNameField.setValueXPath("role/roleName");

		setFields(resourceIdField, nameField, typeField, patternField, authorityField, roleNameField);
	}
}