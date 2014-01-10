package egovframework.eam.admin.main.client.role;

import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.types.FieldType;

import egovframework.eam.admin.main.client.datasource.AbstractRestDataSource;

public class RoleDataSource extends AbstractRestDataSource {
	private static RoleDataSource instance = null;

	public static RoleDataSource getInstance() {
		if (instance == null) {
			instance = new RoleDataSource("roleEditDS");
		}

		return instance;
	}

	private RoleDataSource(String id) {
		super(id);

		setFetchDataURL("/service/roles");
		setAddDataURL("/service/role");
		setUpdateDataURL("/service/role");
		setRemoveDataURL("/service/role");
	}

	protected void init() {
		setDataFormat(DSDataFormat.XML);
		setXmlRecordXPath("/roles/data/*");
		
		DataSourceField idField = new DataSourceField("authority", FieldType.TEXT, "Authority");
		idField.setPrimaryKey(true);
		idField.setCanEdit(false);
		DataSourceField nameField = new DataSourceField("roleName", FieldType.TEXT, "Role Name");

		setFields(idField, nameField);
	}
}