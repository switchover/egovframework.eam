package egovframework.eam.admin.main.client.session;

import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.types.FieldType;

import egovframework.eam.admin.main.client.datasource.AbstractRestDataSource;

public class SessionDataSource extends AbstractRestDataSource {
	private static SessionDataSource instance = null;

	public static SessionDataSource getInstance() {
		if (instance == null) {
			instance = new SessionDataSource("sessionEditDS");
		}

		return instance;
	}

	private SessionDataSource(String id) {
		super(id);

		setFetchDataURL("/service/sessions");
		setAddDataURL("/service/session");
		setUpdateDataURL("/service/session");
		setRemoveDataURL("/service/session");
	}

	protected void init() {
		setDataFormat(DSDataFormat.XML);
		setXmlRecordXPath("/sessions/data/*");
		
		DataSourceField idField = new DataSourceField("systemId", FieldType.TEXT, "System ID");
		idField.setPrimaryKey(true);
		idField.setCanEdit(false);
		
		DataSourceField nameField = new DataSourceField("systemName", FieldType.TEXT, "System Name");
		DataSourceField sessionKeyField = new DataSourceField("sessionKey", FieldType.TEXT, "Session Key");
		DataSourceField issueDatetimeField = new DataSourceField("issueDatetime", FieldType.TEXT, "Issue Date & Time");
		DataSourceField dueDatetimeField = new DataSourceField("dueDatetime", FieldType.TEXT, "Due Date & Time");

		setFields(idField, nameField, sessionKeyField, issueDatetimeField, dueDatetimeField);
	}
}