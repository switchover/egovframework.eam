package egovframework.eam.admin.main.client.user;

import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.types.FieldType;

import egovframework.eam.admin.main.client.datasource.AbstractRestDataSource;

public class UserDataSource extends AbstractRestDataSource {
	private static UserDataSource instance = null;

	public static UserDataSource getInstance() {
		if (instance == null) {
			instance = new UserDataSource("userEditDS");
		}

		return instance;
	}

	private UserDataSource(String id) {
		super(id);

		setFetchDataURL("/service/users");
		setAddDataURL("/service/user");
		setUpdateDataURL("/service/user");
		setRemoveDataURL("/service/user");
	}

	protected void init() {
		//setDataFormat(DSDataFormat.JSON);
		//setJsonRecordXPath("/");
		setDataFormat(DSDataFormat.XML);
		setXmlRecordXPath("/users/data/*");
		
		DataSourceField idField = new DataSourceField("userId", FieldType.TEXT, "User ID");
		idField.setPrimaryKey(true);
		idField.setCanEdit(false);
		DataSourceField nameField = new DataSourceField("userName", FieldType.TEXT, "Name");
		DataSourceField enabledField = new DataSourceField("enabled", FieldType.BOOLEAN, "Enabled");
		DataSourceField passwordField = new DataSourceField("password", FieldType.PASSWORD, "Password");

		setFields(idField, nameField, passwordField, enabledField);
	}

	/*
	@Override
	protected String getServiceRoot() {
		return "user/";
	}
	*/
}
