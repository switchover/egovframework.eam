package egovframework.eam.admin.main.client.authority;

import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.types.FieldType;

import egovframework.eam.admin.main.client.datasource.AbstractRestDataSource;

public class AuthorityDataSource extends AbstractRestDataSource {
	private static AuthorityDataSource instance = null;

	public static AuthorityDataSource getInstance() {
		if (instance == null) {
			instance = new AuthorityDataSource("authorityEditDS");
		}

		return instance;
	}

	private AuthorityDataSource(String id) {
		super(id);

		setFetchDataURL("/service/authorities");
		setAddDataURL("/service/authority");
		setUpdateDataURL("/service/authority");
		setRemoveDataURL("/service/authority");
	}

	protected void init() {
		setDataFormat(DSDataFormat.XML);
		setXmlRecordXPath("/authorities/data/*");
		
		DataSourceField userIdField = new DataSourceField("userId", FieldType.TEXT, "User ID");
		userIdField.setPrimaryKey(true);
		userIdField.setCanEdit(false);
		userIdField.setValueXPath("user/userId");
		
		DataSourceField nameField = new DataSourceField("userName", FieldType.TEXT, "User Name");
		
		nameField.setValueXPath("user/userName");
		
		DataSourceField authorityField = new DataSourceField("authority", FieldType.TEXT, "Authority");
		authorityField.setPrimaryKey(true);
		authorityField.setCanEdit(false);
		authorityField.setValueXPath("role/authority");
		
		DataSourceField roleNameField = new DataSourceField("roleName", FieldType.TEXT, "Role Name");
		
		roleNameField.setValueXPath("role/roleName");

		setFields(userIdField, nameField, authorityField, roleNameField);
	}
}