package egovframework.eam.admin.main.client.role;

import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.types.FieldType;

import egovframework.eam.admin.main.client.datasource.AbstractRestDataSource;

public class RoleOptionDataSource extends AbstractRestDataSource {
	private static RoleOptionDataSource instance = null;

	public static RoleOptionDataSource getInstance() {
		if (instance == null) {
			instance = new RoleOptionDataSource("roleOptionEditDS");
		}

		return instance;
	}

	private RoleOptionDataSource(String id) {
		super(id);

		setFetchDataURL("/service/roleOptions");
		setAddDataURL("/service/roleOption");
		setUpdateDataURL("/service/roleOption");
		setRemoveDataURL("/service/roleOptions");
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